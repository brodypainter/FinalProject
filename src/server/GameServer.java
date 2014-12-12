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
import model.Map;
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

	private static final long serialVersionUID = 2161825695191929679L;
	private ServerSocket socket; // the server socket
	private LinkedList<String> messages = new LinkedList<String>();	// the chat log
	private HashMap<String, ObjectOutputStream> outputs; // map of all connected users' output streams
	private Timer timer; //The master timer
	private Player player1, player2;
	//private Vector<Enemy> enemyList; //Use currentLevel.getMap().getEnemies() and similar for towers
	//private Vector<Tower> towerList;
	//private Map map = new Level0Map(); //If you need the map use currentLevel.getMap()
	private Level levelA; //to be set by a command object from server
	private GameServer thisServer = this; //A reference to itself, the server
	private int timePerTick = 20; //The time in ms per tick, will be set to 20 ms (50 fps) after debugging
	private int tickDiluter = 1; //The multiplier of the timePerTick, 1 on normal speed, 2 on fast
	private boolean paused = false; //True if the game is paused, false if not
	private boolean fast = false; //True if the game is in fast mode, false if normal speed.
	private boolean multiplayer = false; //True if the game is in multiplayer mode
	private boolean waitingFor2ndPlayer = false; //true if waiting for 2nd player
	private HashMap<String, Map> client2Map; //Allows the appropriate map to be updated by a call from a given GameClient
	
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
				levelA = null; // Remove the current level
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
					//check if GameServer is full, if so do not accept client
					if(player2 != null){
						//TODO:reject client, will this do it?
						break;
					}
					
					// accept a new client, get output & input streams
					System.out.println("\t socket accepter");
					Socket s = socket.accept();
					ObjectOutputStream output = new ObjectOutputStream(s.getOutputStream());
					ObjectInputStream input = new ObjectInputStream(s.getInputStream());
					
					// read the client's name
					String clientName = (String)input.readObject();
					
					
					// create the single player, will need to change this for multiplayer games
					// for multiplayer, this will need to check if the player already exists
					
					//TODO: Find a way to set the GameClient with player1's name's boolean isPlayer1Client to true
					//and set it false on other client
					if(player1 == null){
						player1 = new Player(clientName, 100, 100);
						output.writeObject(player1);
						output.writeObject(true);
					}else{
						player2 = new Player(clientName, 100, 100);
						output.writeObject(player2);
						output.writeObject(false);
					}
										
//					System.out.println("Player Send Try");
//					System.out.println("Player is: " + player1.toString());
//					output.writeObject(player1);
//					System.out.println("Player Send Success");
					
					// map client name to output stream
					outputs.put(clientName, output);
					
					
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
		this.outputs = new HashMap<String, ObjectOutputStream>(); // setup this hashmap
		this.client2Map = new HashMap<String, Map>();
		
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
		System.out.println(this.messages.toString() + "\nupdateClients");
		System.out.println(this.outputs.toString());
		System.out.println("updateClientsMessages");
		
		// make an UpdateClientCommmand, try to write to all connected users
		LinkedList<String> temp = new LinkedList<String>(messages);
		Command<GameClient> c = new ClientMessageCommand(temp);
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
	
	/**
	 * Progresses the game logic model, runs game loop, spawn/moves enemies, towers fire, etc.
	 */
	public void tickModel(){
		levelA.tick(this.timePerTick*this.tickDiluter); //spawn enemies when ready
		levelA.getMap1().tick(this.timePerTick*this.tickDiluter); //towers fire and enemies move when ready
		if(multiplayer){
			levelA.getMap2().tick(this.timePerTick*this.tickDiluter);
		}
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
		//TODO let the server determine which level to remove
		levelA = null;
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
	 * @param message2 
	 */

	public void newMessage(String message, String clientName) {
		//Parse for money transfers
		if((message.charAt(0) == '$') && multiplayer){
			// Send this amount of money to the other player
			int moneyToSend = Integer.parseInt(message.substring(1));
			boolean p1Sending;
			if(player1.getName().equals(clientName)){
				p1Sending = true;
			}else{
				p1Sending = false;
			}
			if(p1Sending){
				if(player1.getMoney() >= moneyToSend){
					player1.spendMoney(moneyToSend);
					player2.gainMoney(moneyToSend);
					this.messages.add(player1.getName() + "Sent $" + moneyToSend + " to " + player2.getName());
					this.updateClients(player1.getHealthPoints(), player1.getMoney(), true);
					this.updateClients(player2.getHealthPoints(), player2.getMoney(), false);
				}
			}else{
				if(player2.getMoney() >= moneyToSend){
					player2.spendMoney(moneyToSend);
					player1.gainMoney(moneyToSend);
					this.messages.add(player2.getName() + "Sent $" + moneyToSend + " to " + player1.getName());
					this.updateClients(player1.getHealthPoints(), player1.getMoney(), true);
					this.updateClients(player2.getHealthPoints(), player2.getMoney(), false);
				}
			}
		}
		
		this.messages.add(clientName + ": " + message);
		System.out.println(this.messages.toString());
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
					System.out.println(out.toString());
					System.out.println("Command:\n" + c.toString());
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
	 * @param fromPlayer1 true if this update is from the map of Player1, false if from Player2
	 */
	public void updateClients(ArrayList<EnemyImage> enemyImages, ArrayList<TowerImage> towerImages, boolean fromPlayer1){
		//System.out.println(enemyImages.toString());
		ClientUpdate c = new ClientUpdate(enemyImages, towerImages, fromPlayer1);
		sendCommand(c);
	}
	
	/**
	 * Sends the given player health and money to clients
	 * 
	 * @param playerHealth The player's current health
	 * @param playerMoney The player's current money
	 */
	public void updateClients(int playerHealth, int playerMoney, boolean fromPlayer1){
		Command<GameClient> c = new ClientHPandMoney(playerHealth, playerMoney, fromPlayer1);
		sendCommand(c);
	}
	
	/**
	 * Notifies clients of an attack, giving the client the TowerType and Location of the attacking tower, and the enemy location
	 * 
	 * @param type The tower's type
	 * @param towerLocation The tower's location 
	 * @param enemyLocation The enemy's location
	 */
	public void updateClientsOfAttack(towerType type, Point towerLocation, Point enemyLocation, boolean fromPlayer1){
		Command<GameClient> c = new ClientTowerAttack(type, towerLocation, enemyLocation, fromPlayer1);
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
	public void updateClientsOfMapBackground(String mapBackgroundURL, LinkedList<LinkedList<Point>> paths, int numOfRows, int numOfColumns, boolean fromPlayer1){
		Command<GameClient> c = new ClientMapBackground(mapBackgroundURL, paths, numOfRows, numOfColumns, fromPlayer1);
		sendCommand(c);
	}
	
	
	
	//These methods below will be called by Command objects passed from client to server
	
	/**
	 * Uses the LevelFactory to create a level with the specified difficulty and sets it as the current level
	 * @param name 
	 * 
	 * @param levelCode Int code identifying difficulty level which specifies which actual level to load
	 */
	public void createLevel(String name, int levelCode){
		if(this.waitingFor2ndPlayer == false){
			this.levelA = LevelFactory.generateLevel(this.player1, thisServer, levelCode);
		}
	}
	
	
	/**
	 * Adds a tower to the map, using the TowerFactory validation with the level
	 * 
	 * @param type Which tower type is to be placed
	 * @param loc The location at which to place the tower
	 */
	public void addTower(String clientName, towerType type, Point loc) {
		//System.out.println("GameServer attempting to add tower to row: " + loc.x + " col: " + loc.y);
		Tower towerToAdd = TowerFactory.generateTower(type, client2Map.get(clientName).getPlayer()); // Generate a tower	
		//System.out.println("addTower command received, adding tower to current level");
		if(client2Map.get(clientName).addTower(towerToAdd, loc)){ // Ask the map to add the tower
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
	public void sellTower(String clientName, Point location) {
		client2Map.get(clientName).sellTower(location);
	}
	
	/**
	 * Add and enemy to the map. NOTE: not currently used
	 * 
	 * @param enemy The enemy that is to be spawned on the map
	 */
	public void addEnemy(Enemy enemy, String clientName) {
		client2Map.get(clientName).spawnEnemy(enemy);
	}
	
	/*/**
	 * @return The server's current time between ticks
	 */
	/*unnecessary now? just have GUI check if fastforward or not? -PWH
	public long getTickLength(){
		return timePerTick;
	}
	*/
	
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
			obj_out.writeObject(levelA);
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
			levelA = (Level) obj_in.readObject();
			obj_in.close();
			//reset currentLevel and it's map's transient variables GameServer
			levelA.setServer(thisServer);
			levelA.getMap1().setServer(thisServer);
			//Start the game paused and at normal speed, notify GUI of this state.
			this.paused = true;
			this.normalSpeed();
			
			
		}catch(Exception e){
			// TODO tell the player that there was an issue
			this.createLevel(player1.getName(), tickDiluter);
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
	public void upgradeTower(Point p, String clientName) {
		client2Map.get(clientName).upgradeTower(p);
	}
	
	/**
	 * Attempting to do multiplayer, to be called by the Client. The first player
	 * to call online will set the boolean waitingFor2ndPlayer to true, the
	 * next player when they call online will link the partner players together and
	 * create a multiplayer level.
	 * @param name 
	 */
	public void joinMultiplayer(String name){
		if(this.waitingFor2ndPlayer){
			player1.setPartner(player2);
			player2.setPartner(player1);
			this.multiplayer = true;
			this.createLevel("Tester", 0); //TODO: Hardcoded for now, see if there is a way to choose, or just make it a random level 0-3
		}else{
			this.waitingFor2ndPlayer = true;
		}
	}
	
	/**
	 * Checks if multiplayer mode is in effect
	 * @return multiplayer, a boolean
	 */
	public boolean isMultiplayer(){
		return this.multiplayer;
	}

	/**
	 * Called by Map every time it is instantiated
	 * @param s the String of GameClient's name
	 * @param m the Map
	 */
	public void putClientToMap(String s, Map m){
		this.client2Map.put(s, m);
	}

	/**
	 * Return's player1, useful for Map to check if it is Map1 or not
	 * @return player1
	 */
	public Player getPlayer1() {
		return player1;
	}
}
