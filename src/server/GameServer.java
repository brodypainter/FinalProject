package server;

import java.awt.Point;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.Vector;

import model.Level;
import model.Level0Map;
import model.Map;
import GameController.Enemy;
import GameController.Tower;
import client.Player;
import commands.Command;
import commands.DisconnectCommand;
import commands.SendClientMessageCommand;
import commands.SendClientUpdate;
import commands.TimeCommand;

/**
 * This class is the server side of the tower defense game. The server keeps track of all client outputs, and manages
 * communication between them. It also takes care of the global timer that synchronizes the clients' games.
 * 
 * @author Brody Painter
 */
public class GameServer {
	private ServerSocket socket; // the server socket
	
	private String latestMessage;	// the chat log
	private HashMap<String, ObjectOutputStream> outputs; // map of all connected users' output streams
	private Timer timer; //The master timer
	private Player player;
	//private Vector<Enemy> enemyList;
	//private Vector<Tower> towerList;
	//private Map map = new Level0Map();
	private Level currentLevel; //to be set by a command object from server
	private GameServer thisServer = this;
	
	/**
	 *	This thread reads and executes commands sent by a client
	 */
	private class ClientHandler implements Runnable{
		private ObjectInputStream input; // the input stream from the client
		
		public ClientHandler(ObjectInputStream input){
			this.input = input;
		}
		
		public void run() {
			try{
				while(true){
					// read a command from the client, execute on the server
					Command<GameServer> command = (Command<GameServer>)input.readObject();
					System.out.println("\t\t Command " + command + " received");
					command.execute(GameServer.this);
					
					// When there is a command from a client, update all of the clients
					//GameServer.this.updateClients();
					// terminate if client is disconnecting
					if (command instanceof DisconnectCommand){
						input.close();
						return;
					}
				}
			} catch(Exception e){
				// will be thrown if client does not safely disconnect
				//e.printStackTrace();
				System.out.println("\t\t This client did not safely disconnect");
			}
		}
	}
	
	/**
	 *	This thread listens for and sets up connections to new clients
	 */
	private class ClientAccepter implements Runnable{
		public void run() {
			try{
				while(true){
					// accept a new client, get output & input streams
					System.out.println("\t socket accepter");
					Socket s = socket.accept();
					ObjectOutputStream output = new ObjectOutputStream(s.getOutputStream());
					ObjectInputStream input = new ObjectInputStream(s.getInputStream());
					
					// read the client's name
					String clientName = (String)input.readObject();
					
					// create the single player, will need to change this for multiplayer games
					// for multiplayer, this will need to check if the player already exists
					player = new Player(clientName, thisServer, 100, 100);
					
					// map client name to output stream
					outputs.put(clientName, output);
					
					//send the client the level and player, only once per player
					System.out.println("Level Send Try");
					output.writeObject(currentLevel);
					System.out.println("Player Send Try");
					output.writeObject(player);
					
					// spawn a thread to handle communication with this client
					new Thread(new ClientHandler(input)).start();
					
					// add a notification message to the chat log
					System.out.println("\t new client: " + clientName);
					newMessage(clientName + " connected");
				}
			}catch(Exception e){
				e.printStackTrace();
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
			this.startTimer();
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
		TimerTaskUpdate task = new TimerTaskUpdate(this);
		timer = new Timer();
		//timer.scheduleAtFixedRate(task, 0, 20);
		timer.scheduleAtFixedRate(task, 0, 500);
	}
	
	
	/**
	 * Writes a TimeCommand to every connected user, to be called by a
	 * master Timer every 20 ms.
	 */
	public void tickClients() {
		// make an TimeCommmand, write to all connected users
		TimeCommand update = new TimeCommand();
		if(!outputs.isEmpty()){
			
			for (ObjectOutputStream out : outputs.values()){
				try{
					System.out.println("Tick try on " + out);
					out.writeObject(update);
					System.out.println("Tick sent");
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Stops the GameServer's Timer
	 */
	public void stopTimer(){
		timer.cancel();
	}
	
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
			newMessage(clientName + " disconnected");
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
	
	
	
	//These following "notify" methods will be called by Map every time model changes in a way
	//that requires animation, such as an enemy spawning, moving tiles, dying, or a
	//tower being created, removed, upgraded, or removed.
	//When Player is called to notify it will tell its GameServer to 
	
	public void updateClients(ArrayList<Enemy> enemies, ArrayList<Tower> towers){
		SendClientUpdate c = new SendClientUpdate(enemies, towers);
		
		if(!outputs.isEmpty()){
			for (ObjectOutputStream out : outputs.values()){
				try{
					System.out.println("Update try on " + out);
					out.writeObject(c);
					System.out.println("Update sent");
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		
	}
	


	public void updateClientsOfAttack(Tower attackingTower, Enemy victim){
		//Brody create and send a command object to the clients to animate the attack
	}

	//These methods will be called by Command objects passed from client to server
	//call level.getMap.appropriateMethod() in each case
	
	//TODO Flesh out this method
	public void addTower(Tower tower, Point loc) {
		currentLevel.getMap().addTower(tower, loc);
	}
	
	//perhaps should be renamed to "sellTower"
	public void removeTower(Tower tower) {
		currentLevel.getMap().removeTower(tower);
	}

	public void addEnemy(Enemy enemy) {
		// TODO Auto-generated method stub
		currentLevel.getMap().spawnEnemy(enemy);
		}

	public void removeEnemy(Enemy enemy) {
		// TODO Auto-generated method stub
		currentLevel.getMap().removeDeadEnemy(enemy.getLocation(), enemy);
		//enemyList.remove(enemyList.indexOf(enemy));
	}
	
}
