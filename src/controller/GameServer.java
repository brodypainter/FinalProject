package controller;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;

import model.Player;
import model.enemies.Enemy;
import model.levels.Level;
import model.levels.LevelFactory;
import model.towers.Tower;
import model.towers.TowerFactory;
import GUI.EnemyImage;
import GUI.GameView.towerType;
import GUI.TowerImage;

/**
 * This class is the server side of the tower defense game. The server keeps track of all client outputs, and manages
 * communication between them. It also takes care of the global timer that synchronizes the clients' games.
 * 
 * @author Brody Painter
 */
public class GameServer{


	private Timer timer; //The master timer
	private Player player;
	private Level levelA; //to be set by a user
	private GameServer thisServer = this; //A reference to itself, the server
	private int timePerTick = 20; //The time in ms per tick, will be set to 20 ms (50 fps) after debugging
	private int tickDiluter = 1; //The multiplier of the timePerTick, 1 on normal speed, 2 on fast
	private boolean paused = false; //True if the game is paused, false if not
	private boolean fast = false; //True if the game is in fast mode, false if normal speed.
	private GameClient client; //The client that handles the GUI.
	

	public static void main(String[] args){
		new GameServer();
	}
	
	public GameServer(){
		this.client = new GameClient(thisServer);
		this.player = new Player();
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
			
		try{
			levelA.tick(this.timePerTick*this.tickDiluter); //spawn enemies when ready
			levelA.getMap().tick(this.timePerTick*this.tickDiluter); //towers fire and enemies move when ready
		}catch(NullPointerException e){
				System.out.println("Level Over");
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
		levelA = null;
	}
	
	/**
	 * Start this GameServer
	 * @param args Command Line args
	 */

	
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
	public void updateClients(ArrayList<EnemyImage> enemyImages, ArrayList<TowerImage> towerImages){
		client.update(enemyImages, towerImages);
	}
	
	/**
	 * Sends the given player health and money to clients
	 * 
	 * @param playerHealth The player's current health
	 * @param playerMoney The player's current money
	 */
	public void updateClients(int playerHealth, int playerMoney){
		client.updateHPandMoney(playerHealth, playerMoney);
	}
	
	/**
	 * Notifies clients of an attack, giving the client the TowerType and Location of the attacking tower, and the enemy location
	 * 
	 * @param type The tower's type
	 * @param towerLocation The tower's location 
	 * @param enemyLocation The enemy's location
	 */
	public void updateClientsOfAttack(towerType type, Point towerLocation, Point enemyLocation){
		client.towerAttack(type, towerLocation, enemyLocation);
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
		client.mapBackgroundUpdate(mapBackgroundURL, paths, numOfRows, numOfColumns);
		
	}
	
	
	//These methods below will be called by Command objects passed from client to server
	
	/**
	 * Uses the LevelFactory to create a level with the specified difficulty and sets it as the current level
	 * @param name 
	 * 
	 * @param levelCode Int code identifying difficulty level which specifies which actual level to load
	 */
	public void createLevel(int levelCode){
			this.levelA = LevelFactory.generateLevel(this.player, thisServer, levelCode);
	}
	
	
	/**
	 * Adds a tower to the map, using the TowerFactory validation with the level
	 * 
	 * @param type Which tower type is to be placed
	 * @param loc The location at which to place the tower
	 */
	public void addTower(towerType type, Point loc) {
		
		Tower towerToAdd = TowerFactory.generateTower(type); // Generate a tower	
		this.levelA.getMap().addTower(towerToAdd, loc);
		
	}
	
	/**
	 * Sell the tower, not used 
	 * 
	 * @param location The location that the tower to be sold is located
	 */
	public void sellTower(Point location) {
		this.levelA.getMap().sellTower(location);
	}
	
	/**
	 * Add and enemy to the map. NOTE: not currently used
	 * 
	 * @param enemy The enemy that is to be spawned on the map
	 */
	public void addEnemy(Enemy enemy, String clientName) {
		this.levelA.getMap().spawnEnemy(enemy);
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
		client.notifyLevelWasLost();
	}

	/**
	 * Stop the GameServer master Timer, create a GameOver Command object notifying client of 
	 * game won, that causes GUI to print out a game won pic and return to the main menu
	 */
	public void gameWon() {
		stopTimer();
		removeLevel();
		client.notifyLevelWasWon();
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
			levelA.getMap().setServer(thisServer);
			
			
			//Start the game paused and at normal speed, notify GUI of this state.
			//this.paused = true;
			this.normalSpeed();
			
			//start the game unpaused, GUI can't be notified of starting paused yet
			this.paused = false;
			this.startTimer();
			
		}catch(Exception e){
			//TODO: tell the player that there was an issue
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
		client.changedSpeedState(paused, fast);
	}

	/**
	 * Attempts to upgrade the tower at point p
	 * @param p
	 */
	public void upgradeTower(Point p) {
		this.levelA.getMap().upgradeTower(p);
	}
	
}
