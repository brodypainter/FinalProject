package server;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Timer;

import model.Level;
import model.LevelFactory;
import model.TowerFactory;
import GUI.EnemyImage;
import GUI.GameView.towerType;
import GUI.TowerImage;
import GameController.Enemy;
import GameController.Tower;
import client.GameClient;
import client.Player;
import commands.ClientGameLost;
import commands.ClientGameWon;
import commands.ClientHPandMoney;
import commands.ClientMapBackground;
import commands.ClientMessageCommand;
import commands.ClientTowerAttack;
import commands.ClientUpdate;
import commands.Command;
import commands.DisconnectCommand;
import commands.changeStateCommand;

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
	
	private LinkedList<String> messages;	// the chat log
	private HashMap<String, ObjectOutputStream> outputs; // map of all connected users' output streams
	private Timer timer; //The master timer
	private Player player1, player2;
	//private Vector<Enemy> enemyList; //Use currentLevel.getMap().getEnemies() and similar for towers
	//private Vector<Tower> towerList;
	//private Map map = new Level0Map(); //If you need the map use currentLevel.getMap()
	private Level currentLevel; //to be set by a command object from server
	private GameServer thisServer = this; //A reference to itself, the server
	private int timePerTick = 20; //The time in ms per tick, will be set to 20 ms (50 fps) after debugging
	private int tickDiluter = 1; //The multiplier of the timePerTick, 1 on normal speed, 2 on fast
	private Boolean paused = false; //True if the game is paused, false if not
	private Boolean fast = false; //True if the game is in fast mode, false if normal speed.
	
	/**
	 *	This thread reads and executes commands sent by a client
	 * 
	 * @author brodypainter
	 */
	private class ClientHandler implements Runnable{
		private ObjectInputStream input; // the input stream from the client
		private String name;
		
		/**
		 * Constructor for ClientHandler
		 * 
		 * @param input The ObjectInputStream object for a specific client
		 * @param name The String user name associated with the input
		 */
		public ClientHandler(ObjectInputStream input, String name){
			this.input = input;
			this.name = name;
		}
		
		/**
		 * 
		 */
		public void run() {
			try{
				while(true){
					
					// read a command from the client, execute on the server
					@SuppressWarnings("unchecked")
					Command<GameServer> c = (Command<GameServer>)input.readObject();
					System.out.println("\t\t Command " + c + " received");
					c.execute(thisServer);
					
					// terminate if client is disconnecting
					if (c instanceof DisconnectCommand){
						input.close();
						return;
					}
				}
			} catch(ArrayIndexOutOfBoundsException e){
				e.printStackTrace();
			} catch(IOException e){
				e.printStackTrace(); // Will be thrown if client does not safely disconnect, then we will remove the client
				currentLevel = null; // Remove the current level
				GameServer.this.outputs.remove(name); // Remove this client from the outputs list
				System.out.println("\t\t This client did not safely disconnect");
			}catch(Exception e){
				System.out.println("Something else is still wrong!");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * This thread listens for and sets up connections to new clients
	 * @author brodypainter
	 *
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
					player1 = new Player(clientName, 100, 100);
										
					System.out.println("Player Send Try");
					System.out.println("Player is: " + player1.toString());
					output.writeObject(player1);
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
	
	/**
	 * 
	 */
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
		System.out.println("updateClients");
		// make an UpdateClientCommmand, try to write to all connected users
		Command<GameClient> c = new ClientMessageCommand(messages);
		this.sendCommand(c);
	}
	
	/**
	 * Starts the master Timer, every timePerTick it will call this GameServer to
	 * call the tickClients() command
	 * 
	 * Set GameServer timePerTick as needed
	 */
	public void startTimer(){
		TimerTaskUpdate task = new TimerTaskUpdate(thisServer);
		this.timer = new Timer();
		timer.scheduleAtFixedRate(task, 0, timePerTick);
	}
	
	public void tickModel(){
		currentLevel.tick(this.timePerTick*this.tickDiluter); //spawn enemies when ready
		currentLevel.getMap().tick(this.timePerTick*this.tickDiluter); //towers fire and enemies move when ready, modified with a reference to the server
	}
	
	/**
	 * Stops the GameServer's Timer
	 */
	public void stopTimer(){
		timer.cancel();
	}
	
	
	/**
	 * Removes the current level
	 */
	public void removeLevel(){
		currentLevel = null;
	}
	
	/**
	 * Start this GameServer
	 * @param args Command Line args
	 */
	public static void main(String[] args){
		new GameServer();
	}

	/**
	 * Disconnects a given user from the server gracefully
	 * @param clientName	User name to disconnect
	 */
	public void disconnect(String clientName) {
		try{
			outputs.get(clientName).close(); // close output stream
			outputs.remove(clientName); // remove from map
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the servers latest message and calls the method to send it to all clients
	 * 
	 * @param message The message to be sent to all clients
	 */
	public void newMessage(String message) {
		// TODO Parse money transfers
		this.messages.add(message);
		updateClientMessages();
	}
	
	/**
	 * Executes the appropriate method on the server as dictated by the command argument
	 * 
	 * @param command The command that was received
	 */
	public void execute(Command<GameServer> command){
		command.execute(this);
	}
	
	/**
	 * Sends a command to the clients
	 * 
	 * @param c The command that is to be sent to all of the clients
	 */
	public void sendCommand(Command<GameClient> c){
		if(!outputs.isEmpty()){
			for (ObjectOutputStream out : outputs.values()){
				try{
					out.writeObject(c); // Write the command out
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
	 * 
	 * @param enemies The Server's list of EnemyImages
	 * @param towers The Server's list of TowerImages
	 */
	public void updateClients(ArrayList<EnemyImage> enemyImages, ArrayList<TowerImage> towerImages){
		//System.out.println(enemyImages.toString());
		ClientUpdate c = new ClientUpdate(enemyImages, towerImages);
		sendCommand(c);
	}
	
	/**
	 * Sends the given player health and money to clients
	 * 
	 * @param playerHealth The player's current health
	 * @param playerMoney The player's current money
	 */
	public void updateClients(int playerHealth, int playerMoney){
		Command<GameClient> c = new ClientHPandMoney(playerHealth, playerMoney);
		sendCommand(c);
	}
	
	/**
	 * Notifies clients of an attack, giving the client the TowerType and Location of the attacking tower, and the enemy location
	 * 
	 * @param type The tower's type
	 * @param towerLocation The tower's location 
	 * @param enemyLocation The enemy's location
	 */
	public void updateClientsOfAttack(towerType type, Point towerLocation, Point enemyLocation){
		Command<GameClient> c = new ClientTowerAttack(type, towerLocation, enemyLocation);
		sendCommand(c);
	}
	
	/**
	 * Send the clients the mapBackground path, the path list, and the number of rows and columns for the map
	 * 
	 * @param mapBackgroundURL Path to the mapBackground image
	 * @param path The list representing the path for the enemies to take
	 * @param numOfRows Number of rows on map
	 * @param numOfColumns Number of columns on map
	 */
	public void updateClientsOfMapBackground(String mapBackgroundURL, LinkedList<LinkedList<Point>> paths, int numOfRows, int numOfColumns){
		Command<GameClient> c = new ClientMapBackground(mapBackgroundURL, paths, numOfRows, numOfColumns);
		sendCommand(c);
	}
	
	
	
	//These methods below will be called by Command objects passed from client to server
	
	/**
	 * Uses the LevelFactory to create a level with the specified difficulty and sets it as the current level
	 * 
	 * @param levelCode Int code identifying difficulty level which specifies which actual level to load
	 */
	public void createLevel(int levelCode){
		this.currentLevel = LevelFactory.generateLevel(this.player1, thisServer, levelCode);
	}
	
	
	/**
	 * Adds a tower to the map, using the TowerFactory validation with the level
	 * 
	 * @param type Which tower type is to be placed
	 * @param loc The location at which to place the tower
	 */
	public void addTower(towerType type, Point loc) {
		//System.out.println("GameServer attempting to add tower to row: " + loc.x + " col: " + loc.y);
		Tower towerToAdd = TowerFactory.generateTower(type, player1); // Generate a tower	
		//System.out.println("addTower command received, adding tower to current level");
		if(currentLevel.getMap().addTower(towerToAdd, loc)){ // Ask the level to add the tower
			//System.out.println("successfully added tower");
		}else{
			//System.out.println("Adding tower failed due to position/lack of $!");
		}
	}
	
	/**
	 * Sell the tower
	 * 
	 * @param location The location that the tower to be sold is located
	 */
	public void sellTower(Point location) {
		currentLevel.getMap().sellTower(location);
	}
	
	/**
	 * Add and enemy to the map. NOTE: not currently used
	 * 
	 * @param enemy The enemy that is to be spawned on the map
	 */
	public void addEnemy(Enemy enemy) {
		currentLevel.getMap().spawnEnemy(enemy);
	}
	
	/**
	 * @return The server's current time between ticks
	 */
	/*unnecessary now? -PWH
	public long getTickLength(){
		return timePerTick;
	}
	/*
	
	/**
	 * Stop the GameServer master Timer, create a GameOver Command object notifying client of 
	 * game lost, that causes GUI to print out a game over pic and return to the main menu
	 */
	public void gameLost() {
		stopTimer();
		removeLevel();
		Command<GameClient> c = new ClientGameLost();
		sendCommand(c);
	}

	/**
	 * Stop the GameServer master Timer, create a GameOver Command object notifying client of 
	 * game won, that causes GUI to print out a game won pic and return to the main menu
	 */
	public void gameWon() {
		stopTimer();
		removeLevel();
		Command<GameClient> c = new ClientGameWon();
		sendCommand(c);
	}

	/**
	 * Toggle whether the game is playing normally or is paused, as well as starting the game
	 */
	public void playPauseGame() {
		this.paused = !this.paused; //If game is playing, flip to paused, and vice versa
		if(paused){
			this.stopTimer();
		}else{
			this.startTimer();
		}
		this.changeState(this.paused, this.fast);
	}

	/**
	 * Save the state of the current game to the server
	 */
	public void saveGame() {
		try{
			FileOutputStream f_out = new FileOutputStream("currentLevel.data");
			ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
			obj_out.writeObject(currentLevel);
			obj_out.close();
		}catch(Exception e){
			System.out.println("There was a problem when saving, here is some info:");
			e.printStackTrace();
		}
	}

	/**
	 * Attempts to load a saved game.
	 */
	public void loadGame() {
		// LOGIC: attempt to load the game using a file name that wont be changing, if it exists, set that game as the current one
		//		and continue as normal, if there isnt a game, what exactly should we do?
		try{
			FileInputStream f_in = new FileInputStream("currentLevel.data");
			ObjectInputStream obj_in = new ObjectInputStream(f_in);
			currentLevel = (Level) obj_in.readObject();
			obj_in.close();
			//reset currentLevel and it's map's transient variables GameServer
			currentLevel.setServer(thisServer);
			currentLevel.getMap().setServer(thisServer);
			
		}catch(Exception e){
			// TODO Probably start a new game here, let the player know that there was
			//	not a saved game present?
			e.printStackTrace();
		}
	}

	/**
	 * Changes game behavior to play at a faster rate
	 */
	public void speedUp() {
		this.fast = true;
		this.tickDiluter = 2;
		changeState(this.paused, this.fast);
	}

	/**
	 * Changes game behavior to play at the default rate
	 */
	public void normalSpeed() {
		this.fast = false;
		this.tickDiluter = 1;
		changeState(this.paused, this.fast);
	}
	
	/**
	 * Server will notify the client that the state has changed based on a message received from the client
	 * 
	 * @param paused
	 * @param fast
	 */
	public void changeState(Boolean paused, Boolean fast){
		Command<GameClient> c = new changeStateCommand(paused, fast);
		this.sendCommand(c);
	}

	/**
	 * Attempts to upgrade the tower at point p
	 * @param p
	 */
	public void upgradeTower(Point p) {
		currentLevel.getMap().upgradeTower(p);
	}

}
