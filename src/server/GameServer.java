package server;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.Vector;

import model.Level;
import model.Level0;
import model.Level0Map;
import model.LevelFactory;
import model.Map;
import model.TowerFactory;
import GUI.EnemyImage;
import GUI.GameView.towerType;
import GUI.TowerImage;
import GameController.Enemy;
import GameController.Tower;
import client.GameClient;
import client.Player;
import commands.*;

/**
 * This class is the server side of the tower defense game. The server keeps track of all client outputs, and manages
 * communication between them. It also takes care of the global timer that synchronizes the clients' games.
 * 
 * @author Brody Painter
 */
public class GameServer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2161825695191929679L;

	private ServerSocket socket; // the server socket
	
	private String latestMessage;	// the chat log
	private HashMap<String, ObjectOutputStream> outputs; // map of all connected users' output streams
	private Timer timer; //The master timer
	private Player player;
	//private Vector<Enemy> enemyList; //Use currentLevel.getMap().getEnemies() and similar for towers
	//private Vector<Tower> towerList;
	//private Map map = new Level0Map(); //If you need the map use currentLevel.getMap()
	private Level currentLevel; //to be set by a command object from server
	private GameServer thisServer = this; //A reference to itself, the server
	private int timePerTick = 500; //The time in ms per tick, will be set to 20 ms (50 fps) after debugging
	
	/**
	 *	This thread reads and executes commands sent by a client
	 */
	private class ClientHandler implements Runnable{
		private ObjectInputStream input; // the input stream from the client
		private String name;
		
		public ClientHandler(ObjectInputStream input, String name){
			this.input = input;
			this.name = name;
		}
		
		public void run() {
			try{
				while(true){
					// read a command from the client, execute on the server
					Command<GameServer> command = (Command<GameServer>)input.readObject();
					System.out.println("\t\t Command " + command + " received");
					command.execute(thisServer);
					
					// When there is a command from a client, update all of the clients
					//GameServer.this.updateClients();
					// terminate if client is disconnecting
					if (command instanceof DisconnectCommand){
						input.close();
						return;
					}
				}
			} catch(ArrayIndexOutOfBoundsException e){
				System.out.println("Array index is still out of bounds");
				e.printStackTrace();
			} catch(IOException e){
				// will be thrown if client does not safely disconnect, then we will remove the client
				e.printStackTrace();
				GameServer.this.outputs.remove(name);
				System.out.println("\t\t This client did not safely disconnect");
			}catch(Exception e){
				System.out.println("Something egiot lse is still wrong!");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 *	This thread listens for and sets up connections to new clients
	 */
	private class ClientAccepter implements Runnable{
		public void run() {
			while(true){
				try{
					// accept a new client, get output & input streams
					System.out.println("\t socket accepter");
					Socket s = socket.accept();
					ObjectOutputStream output = new ObjectOutputStream(s.getOutputStream());
					ObjectInputStream input = new ObjectInputStream(s.getInputStream());
					
					// read the client's name
					String clientName = (String)input.readObject();
					
					// create the single player, will need to change this for multiplayer games
					// for multiplayer, this will need to check if the player already exists
					player = new Player(clientName, 100, 100);
					createLevel(0);
					
					System.out.println("Player Send Try");
					System.out.println("Player is: " + player.toString());
					output.writeObject(player);
					System.out.println("Player Send Success");
					
					// map client name to output stream
					outputs.put(clientName, output);
					
					//send the client the level and player, only once per player

					
					// spawn a thread to handle communication with this client
					new Thread(new ClientHandler(input, clientName)).start();
					
					// add a notification message to the chat log
					System.out.println("\t new client: " + clientName);
					
					// for now we wont send a message
					//newMessage(clientName + " connected");
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	public GameServer(){
		this.outputs = new HashMap<String, ObjectOutputStream>(); // setup the map
		
		try{
			// start a new server on port 9001
			socket = new ServerSocket(9001);
			System.out.println("GameServer started on port 9001");
			
			// spawn a client accepter thread
			new Thread(new ClientAccepter()).start();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Writes an UpdateClientCommand to every connected user.
	 */
	public void updateClientMessages() {
		// make an UpdateClientCommmand, write to all connected users
		System.out.println("updateClients");
		SendClientMessageCommand update = new SendClientMessageCommand(latestMessage);
		try{
			for (ObjectOutputStream out : outputs.values())
				out.writeObject(update);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Starts the master Timer, every 20 ms it will call this GameServer to
	 * call the tickClients() command
	 * 
	 * SLOWED TO 500ms TO DEBUG
	 */
	public void startTimer(){
		TimerTaskUpdate task = new TimerTaskUpdate(thisServer);
		this.timer = new Timer();
		timer.scheduleAtFixedRate(task, 0, timePerTick);
	}
	
	public void tickModel(){
		currentLevel.tick(); //spawn enemies when ready
		currentLevel.getMap().tick(this.timePerTick, this); //towers fire and enemies move when ready, modified with a reference to the server
	}
	
	
	//tickClients may no longer be necessary since the model is stored
	//entirely on the server and updated with tickModel. We do not want to hold on to
	//2 models and try to update them both. Instead use updateClients(enemies,towers) method
	/**
	 * Writes a TimeCommand to every connected user, to be called by a
	 * master Timer every 20 ms.
	 */
	/*public void tickClients() {
		// make an TimeCommmand, write to all connected users
		TimeCommand update = new TimeCommand();
		if(!outputs.isEmpty()){
			
			for (ObjectOutputStream out : outputs.values()){
				try{
					//System.out.println("Tick try on " + out);
					//out.writeObject(update);
					//System.out.println("Tick sent");
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	*/
	/**
	 * Stops the GameServer's Timer
	 */
	public void stopTimer(){
		timer.cancel();
	}
	
	//call when level is finished
	public void removeLevel(){
		currentLevel = null;
	}
	
	//run this class to create a GameServer.
	public static void main(String[] args){
		new GameServer();
	}

	/**
	 * Disconnects a given user from the server gracefully
	 * @param clientName	user to disconnect
	 */
	public void disconnect(String clientName) {
		try{
			outputs.get(clientName).close(); // close output stream
			outputs.remove(clientName); // remove from map
			
			// add notification message
			// dont send a message for now
			// newMessage(clientName + " disconnected");
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void newMessage(String message) {
		// TODO Send this message to all of our clients
		System.out.println("newMessage");
		this.latestMessage = message;
		updateClientMessages();
	}
	
	public void execute(Command<GameServer> command){
		command.execute(this);
	}
	
	//These 2 methods won't be necessary except until multi-player possibly
	//public void addEnemy() {
		
	//}
	
	//public void removeEnemy() {
		
	//}
	
	// Implement this in a few
	public void sendCommand(Command<GameClient> c){
		if(!outputs.isEmpty()){
			for (ObjectOutputStream out : outputs.values()){
				try{
					System.out.println("Send command try on " + out);
					out.writeObject(c);
					System.out.println("command sent");
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	
	//These following "notify" methods will be called by Map every time model changes in a way
	//that requires animation, such as an enemy spawning, moving tiles, dying, or a
	//tower being created, removed, upgraded, or removed.
	//When Player is called to notify it will tell its GameServer to 
	
	
	/**
	 * This method will be called by map every time a tick occurs
	 * @param enemies
	 * @param towers
	 */
	public void updateClients(ArrayList<EnemyImage> enemyImages, ArrayList<TowerImage> towerImages){
		SendClientUpdate c = new SendClientUpdate(enemyImages, towerImages);
		sendCommand(c);
	}
	
	/**
	 * Brody changed Sun 2:18
	 * 
	 * This method will be called every time player's info changes
	 * @param playerHealth
	 * @param playerMoney
	 */
	public void updateClients(int playerHealth, int playerMoney){
		Command<GameClient> c = new SendClientHPandMoney(playerHealth, playerMoney);
		sendCommand(c);
	}
	
	/**
	 * Brody changed Sun 2:29
	 * 
	 * This method is called by the currentLevel's Map whenever a tower attacks
	 * @param attackingTower
	 * @param victim
	 */
	public void updateClientsOfAttack(towerType type, Point towerLocation, Point enemyLocation){
		Command<GameClient> c = new SendClientTowerAttack(type, towerLocation, enemyLocation);
		sendCommand(c);
	}
	
	/**
	 * PH changed Sun 4:06
	 * 
	 * This method is called once when the currentLevel's Map is first instantiated
	 * Client and GUI should hold on to this unchanging Map Background image url and its enemy path
	 * as well as its number of rows and columns
	 * @param mapBackgroundURL
	 * @param path
	 */
	public void updateClientsOfMapBackground(String mapBackgroundURL, List<Point> path, int numOfRows, int numOfColumns){
		Command<GameClient> c = new SendClientMapBackground(mapBackgroundURL, path, numOfRows, numOfColumns);
		sendCommand(c);
	}
	
	
	
	//These methods below will be called by Command objects passed from client to server
	//call level.getMap.appropriateMethod() in each case
	
	//TODO: Brody call this method with a command object from client when the level is selected
	public void createLevel(int levelCode){
		this.currentLevel = LevelFactory.generateLevel(this.player, thisServer, levelCode);
	}
	
	
	
	public void addTower(towerType type, Point loc) {
		System.out.println(loc.toString());
		Tower towerToAdd = TowerFactory.generateTower(type, player);		
		System.out.println("addTower command received, adding tower to current level");
		if(currentLevel.getMap().addTower(towerToAdd, loc)){
			System.out.println("successfully added tower");
		}else{
			System.out.println("Adding tower failed!");
		}
	}
	
	
	public void sellTower(Point location) {
		currentLevel.getMap().sellTower(location);
	}

	//May be useful method for multiplayer but would have to be Player specific
	public void addEnemy(Enemy enemy) {
		currentLevel.getMap().spawnEnemy(enemy);
	}

	//Do not use this method it is already inside Map and does so by itself -PH
	/*
	public void removeEnemy(Enemy enemy) {
		currentLevel.getMap().removeDeadEnemy(enemy.getLocation(), enemy);
		//enemyList.remove(enemyList.indexOf(enemy));
	}*/
	
	public long getTickLength(){
		return timePerTick;
	}
	
	public void gameLost() {
		// TODO Auto-generated method stub
		//Stop the GameServer master Timer, create a GameOver Command object that contains a
		//boolean value gameWon set to false, that causes GUI to print out a game over pic
		//and return to the main menu
		
		stopTimer();
		removeLevel();
		Command<GameClient> c = new SendClientGameOver(false);
		sendCommand(c);
		
	}

	public void gameWon() {
		// TODO Auto-generated method stub
		//Stop the GameServer master Timer, create a GameOver Command object that contains a
		//boolean value gameWon set to true, that causes GUI to print out a game won pic
		//and return to the main menu
		
		stopTimer();
		removeLevel();
	}
}
