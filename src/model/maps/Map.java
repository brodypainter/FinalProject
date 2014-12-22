package model.maps;


import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import controller.GameServer;
import model.Player;
import model.enemies.Enemy;
import model.enemies.Enemy.directionFacing;
import model.towers.Tower;
import GUI.EnemyImage;
import GUI.GameView.towerType;
import GUI.TowerImage;


/**
 * This is the abstract Map class that all our concrete maps for each level will
 * inherit from. All Map objects contain instance variables and methods that allow
 * them to serve as the fundamental unit for the gamestate's model, representing
 * the location of all towers and enemies and connecting every element to each other.
 * Locations are being stored as Point objects (-y,x), or (rowsDown, column), representing 
 * coordinates to the squares in the grid from a Cartesian viewpoint.
 * 
 * Instance Variables:
 * long serialVersionUID		- For Serializable interface
 * Tile[][] grid				- A 2-D array of Tile objects to model the game map as a grid	
 * Image img					- The background image of the map, determined by mapType
 * LinkedList<LinkedList<Point> enemyPath>	- A 2D list of every path, each of which contains coordinates enemies will pass through
 * LinkedList<Point> firstPathTiles	- The path tiles on which enemies spawn
 * LinkedList<Point> lastPathTiles	- The path tiles on which enemies stop and do damage to health
 * int currentEnemies			- The current total amount of enemies on the map
 * String mapType				- A description of the map level, can be used for theme differentiation
 * int mapTypeCode				- A code # to differentiate each level
 * Player player				- The associated Player object playing this map
 * ArrayList<Enemy> enemies		- A list of all the enemies currently on the map
 * ArrayList<Tower> towers		- A list of all the towers currently placed on the map
 * GameServer server			- The GameServer that the player is on, map will send it notify update calls
 * 
 * Methods:
 * Map(Tile[][] gridDimensions, LinkedList<LinkedList<Point>> paths, String mapType, Image background, int mapTypeCode, Player player)
 * boolean spawnEnemy(Enemy enemy)
 * boolean updateEnemyPosition(Enemy enemy)
 * void removeDeadEnemy(Point location, Enemy enemy)
 * void lostHealth(int hpLost)
 * void gainedGold(int goldGained)
 * boolean addTower(Tower gym, Point location)
 * void sellTower(Point l)
 * ArrayList<Tower> getTowers()
 * ArrayList<Enemy> getEnemies()
 * void tick(int timePerTick, GameServer gameServer)
 * void setServer(GameServer server)
 * public void notifyOfAttack(towerType type, Point towerLocation, Point enemyLocation)
 * public String getImageURL()
 * 
 * @author Peter Hanson
 * @version 1.0
 */

public abstract class Map implements Serializable{
	
	private static final long serialVersionUID = -6337999339368538419L;
	private Tile[][] grid;
	private int numOfRows;
	private int numOfColumns;
	private String imageURL; //The background image of the map
	private LinkedList<LinkedList<Point>> enemyPaths; //A 2D list of all the lists of the tile coordinates that the
	                                     //enemies will attempt to pass through
	private LinkedList<Point> firstPathTiles; //The path tiles on which enemies spawn
	private LinkedList<directionFacing> spawnOrientations; //The default orientation for spawned enemies for each path
	private LinkedList<Point> lastPathTiles; //The path tiles on which enemies stop and do damage to health
	private int currentEnemies; //The current total amount of enemies on the map (necessary?)
	private String mapType; //A description of the map level, can be used for theme differentiation
	private int mapTypeCode; //A code # to differentiate each level
	private Player player; //The associated player object for this map
	private ArrayList<Enemy> enemies; //A list of all the enemies currently on the map
	private ArrayList<Tower> towers; //A list of all the towers currently placed on the map
	private transient GameServer server; //The GameServer that the player is on, map will send it notify update calls

	
	
	/**
	 * Constructs the Map object and initializes and populates some instance variables.
	 * @param gridDimensions A 2D Tile array with the desired dimensions for the map grid
	 * @param path A 2D list of paths that each contain all the coordinates along the grid through which enemies will pass on that path
	 * @param mapType A description of the map's level
	 * @param background The background image of the map to be displayed in the GUI
	 * @param mapTypeCode The level number of this map, can be used to differentiate map events according to level
	 * @param player The Player object associated with who is playing this map
	 */
	public Map(Tile[][] gridDimensions, LinkedList<LinkedList<Point>> paths, String mapType, String backgroundImageURL, int mapTypeCode, Player player){
		grid = gridDimensions;
		numOfRows = grid.length;
		numOfColumns = grid[0].length;
		enemyPaths = paths;
		this.mapType = mapType;
		imageURL = backgroundImageURL;
		
		this.mapTypeCode = mapTypeCode;
		this.player = player;
		currentEnemies = 0;
		
		enemies = new ArrayList<Enemy>();
		towers = new ArrayList<Tower>();
		firstPathTiles = new LinkedList<Point>();
		lastPathTiles = new LinkedList<Point>();
		setPath();
		setTilesMap();
		setSpawnOrientations();
	}
	
	// returns the number of paths in this concrete Map
	public abstract int getNumberOfPaths();

	/**
	 * Sets the Map instance variable in all the tiles in the 2D array grid to this map
	 */
	private void setTilesMap() {
		for(int r = 0; r < grid.length; r++){
			for(int c = 0; c < grid[r].length; c++){
				grid[r][c].setMap(this);
			}
		}
		
	}
	
	/**
	 * Sets the tiles in grid whose coordinates are part of an enemy path as path tiles.
	 * Also stores the first and last coordinates of each enemy path as Points in LinkedLists for convenience.
	 */
	private void setPath(){
		
		Point tempCoords;
		for(int p = 0; p < enemyPaths.size(); p++){
			for(int i = 0; i < enemyPaths.get(p).size(); i++){
				tempCoords = enemyPaths.get(p).get(i);
				grid[tempCoords.x][tempCoords.y].setAsPath();
				if(i == 0){
					grid[tempCoords.x][tempCoords.y].setFirstPathTile();
					firstPathTiles.add(tempCoords);
				}
				if(i == enemyPaths.get(p).size() - 1){
					grid[tempCoords.x][tempCoords.y].setLastPathTile();
					lastPathTiles.add(tempCoords);
				}
			
			}
		}
	}
	
	private void setSpawnOrientations(){
		spawnOrientations = new LinkedList<directionFacing>();
		for(int i = 0; i < enemyPaths.size(); i++){
			Point first = enemyPaths.get(i).get(0);
			Point second = enemyPaths.get(i).get(1);
			if((first.y - second.y) < 0){
				spawnOrientations.add(directionFacing.EAST);
			}
			if((first.y - second.y) > 0){
				spawnOrientations.add(directionFacing.WEST);
			}
			if((first.x - second.x) < 0){
				spawnOrientations.add(directionFacing.SOUTH);
			}
			if((first.x - second.x) > 0){
				spawnOrientations.add(directionFacing.NORTH);
			}
		}
	}
	
	/**
	 * Adds an enemy Pokemon to this map, sets its location to the first tile in 
	 * the appropriate enemy path, and adds it to the map's enemies list.
	 * @param enemy The pokemon to be spawned
	 */
	public void spawnEnemy(Enemy enemy){
		enemy.setMap(this);
		int pathNumber = enemy.getPathTravelingCode();
		grid[firstPathTiles.get(pathNumber).x][firstPathTiles.get(pathNumber).y].addPokemon(enemy);
		enemy.setLocation(firstPathTiles.get(pathNumber));
		enemy.setOrientation(this.spawnOrientations.get(pathNumber));
		enemies.add(enemy);
		currentEnemies++;
	}
	
	
	/**
	 * Updates an enemy pokemon's position from the current path tile to the next one.
	 * To be called by the Enemy every timePerTile milliseconds/movement.
	 * Enemies can only move 1 discrete square along the grid at a time in this model.
	 * @param enemy The Enemy whose position is to be updated
	 * 
	 */
	public void updateEnemyPosition(Enemy enemy){
		
		//Each Enemy will call this method when it is ready to move, passing a reference
		//to itself, to make Map update its position when appropriate. Can only move in
		//1 square discrete amounts along the path.
		
		//get enemy's current coordinates, determine what his next coordinates will be
		Point enemyCoords = enemy.getLocation();
		int pathNumber = enemy.getPathTravelingCode();
		int i = enemyPaths.get(pathNumber).indexOf(enemyCoords);
		int iLess = i - 1;
		if(iLess == -1){
			iLess = 0;
		}
		int iMore = i + 2;
		if(iMore >= enemyPaths.get(pathNumber).size()){
			iMore = enemyPaths.get(pathNumber).size() - 1; //The last tile in that path
		}
		Point nextCoords;
		if(i < enemyPaths.get(pathNumber).size() - 1){
			nextCoords = enemyPaths.get(pathNumber).get(++i);
		}else{
			nextCoords = this.lastPathTiles.get(pathNumber);
		}
		
		//remove enemy from current tile, update his position, and add him to the next one
		grid[enemyCoords.x][enemyCoords.y].removePokemon(enemy);
		enemy.setPreviousLocation(enemyCoords);
		enemy.setLocation(nextCoords);
		grid[nextCoords.x][nextCoords.y].addPokemon(enemy);
		enemy.setNextLocation(enemyPaths.get(pathNumber).get(iMore));
		enemy.takeStep();//Increments step counter to see how many tiles it has gone total		
	}
	
	/**
	 * Removes a dead enemy Pokemon from both the tile it was on at death and the
	 * Map's enemies list. To be called by the Pokemon on death.
	 * @param location The enemy Pokemon's location at death
	 * @param enemy The dead enemy Pokemon to remove from the Map
	 */
	public void removeDeadEnemy(Point location, Enemy enemy){
		grid[location.x][location.y].removePokemon(enemy);
		enemies.remove(enemy);
		currentEnemies--;
	}
	
	public void teleportedEnemy(Enemy e){
		grid[e.getLocation().x][e.getLocation().y].removePokemon(e);
		int pathNumber = e.getPathTravelingCode();
		grid[firstPathTiles.get(pathNumber).x][firstPathTiles.get(pathNumber).y].addPokemon(e);
		e.setLocation(firstPathTiles.get(pathNumber));
		e.setOrientation(this.spawnOrientations.get(pathNumber));
		e.resetStepsTaken();
	}
	
	/**
	 * Called by the last Tile along the enemy path when an enemy gets to it.
	 * Passes on the amount of hp lost, based on the attacking Pokemon's AttackPower.
	 * Map then passes this along to the Player object keeping track of the HP.
	 * @param hpLost The amount of HP to lose, based on attacking Pokemon's AttackPower.
	 */
	public void lostHealth(int hpLost){
		//called by the last tile in the path every time an enemy gets to it
		//Map just passes on this information to the Player object.
		player.loseHealth(hpLost);
		server.updateClients(player.getHealthPoints(), player.getMoney());
	}
	
	/**
	 * Called by any enemy Pokemon that dies, passing its bounty worth.
	 * Map just passes on this information to the Player object keeping track of the gold.
	 * @param goldGained The amount of gold to gain, based on killed Pokemon's Worth.
	 */
	public void gainedGold(int goldGained){
		player.gainMoney(goldGained);
		server.updateClients(player.getHealthPoints(), player.getMoney());
	}
	
	/**
	 * Attempts to add a Tower to a given location on the map.
	 * (no pre-existing tower may be there, location is not part of path,
	 * player has enough money to buy tower, location is on the map)
	 * @param tower The Tower to be added.
	 * @param location The location to add the Tower to in (Rows, Columns).
	 * @return true if successful or false if tower cannot be placed.
	 * 
	 */
	public boolean addTower(Tower tower, Point location){
		if(location.x >= numOfRows || location.x < 0 || location.y >= numOfColumns || location.y < 0){
			return false; //Prevent index out of bounds exceptions
		}
		if(!grid[location.x][location.y].containsGym() && !grid[location.x][location.y].isPartOfPath()){
			if(tower.checkBuy(player.getMoney())){
				tower.setPlaceOnBoard(location);
				towers.add(tower);
				tower.setMap(this);
				player.spendMoney(tower.getCost());
				server.updateClients(player.getHealthPoints(), player.getMoney());
				return grid[location.x][location.y].setGym(tower);
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	/**
	 * Removes a Tower from the Map. Sells for half the initial cost.
	 * @param l the Point containing the location (row, column) of tower to be removed. 
	 */
	
	public void sellTower(Point l){
		if(l.x >= numOfRows || l.y >= numOfColumns || l.x < 0 || l.y < 0){
			return;
		}
		if(grid[l.x][l.y].containsGym()){
		Tower towerToRemove = grid[l.x][l.y].getGym();
		grid[l.x][l.y].removeGym();
		towers.remove(towerToRemove);
		int reclaimedGold = towerToRemove.getCost()/2;
		player.gainMoney(reclaimedGold);
		server.updateClients(player.getHealthPoints(), player.getMoney());
		}
	}
	
	/**
	 * Returns all the enemies on the map
	 * @return enemies an ArrayList of all the Enemies on the map
	 */
	public ArrayList<Enemy> getEnemies(){
		return enemies;
	}
	
	/**
	 * Returns all the towers on the map
	 * @return towers an ArrayList of all the towers on the map
	 */
	public ArrayList<Tower> getTowers(){
		return towers;
	}
	
	/**
	 * This method is called by the master Timer on the Server every timePerTick,
	 * it updates the internal state of every tower and enemy on the map, then
	 * generates their respective "Image" objects and sends them to the Clients
	 * @param timePerTick the time in ms between ticks
	 * @param gameServer the GameServer that the map belongs to responsible for notifying clients of this update
	 */
	public void tick(int timePerTick){
		//call all enemies and towers to call their tick() method, which will increment their
		//cool down timers, causing them to move/shoot if they are ready
		ArrayList<EnemyImage> enemyImages = new ArrayList<EnemyImage>();
		ArrayList<Enemy> tempEnemies = new ArrayList<Enemy>(enemies); //To prevent error multithread editing
		for(Enemy enemy : tempEnemies){
			enemy.tick(timePerTick);
			enemyImages.add(new EnemyImage(enemy));
		}
		ArrayList<TowerImage> towerImages = new ArrayList<TowerImage>();
		ArrayList<Tower> tempTowers = new ArrayList<Tower>(towers);
		for(Tower tower : tempTowers){
			tower.tick(timePerTick);
			towerImages.add(new TowerImage(tower));
		}
		//Testing Passed, is sending the right amount of enemy images
		//System.out.println("Server sending enemyImages update of size: " + enemyImages.size());
		this.server.updateClients(enemyImages, towerImages);
		
	}

	/**
	 * Sets the Map's Server and in doing so sends the Server information about the
	 * Map required to paint it on the Clients GUI
	 * @param server the GameServer to set this Map to
	 */
	public void setServer(GameServer server) {
		this.server = server;
		this.server.updateClientsOfMapBackground(this.imageURL, this.enemyPaths, this.numOfRows, this.numOfColumns);
		
	}
	
	/** 
	 * This method is to notify the server an attack happened.  It is then supposed to be passed to client and updated
	 * inside the GUI.  It takes the towerType that is attacking and the current Enemy that is being attacked.
	 * @param type the towerType of the attacking Tower
	 * @param towerLocation the Point containing coordinates (row, column) of tower
	 * @param enemyLocation the Point containing coordinates (row, column) of enemy
	 */
	public void notifyOfAttack(towerType type, Point towerLocation, Point enemyLocation) {
		this.server.updateClientsOfAttack(type, towerLocation, enemyLocation);
		
	}
	
	/**
	 * Returns the String URL to this map's background image to be painted
	 * @return imageURL the String of the URL for this Map's background image
	 */
	public String getImageURL() {
		return imageURL;
	}


	/**
	 * Upgrades tower at point p if possible
	 * @param p
	 */
	public void upgradeTower(Point p) {
		boolean towerExists = grid[p.x][p.y].containsGym();
		Tower towerToUpgrade = grid[p.x][p.y].getGym();
		
		if(towerExists){
			int costOfUpgrade = towerToUpgrade.getCostOfLevelingUp();
			if(costOfUpgrade <= player.getMoney()){
				if(towerToUpgrade.upgradeCurrentTower(player.getMoney())){
					player.spendMoney(costOfUpgrade);
					server.updateClients(player.getHealthPoints(), player.getMoney());
				}
			}
		}
	}

	public int getMapTypeCode(){
		return this.mapTypeCode;
	}
	
	public Player getPlayer(){
		return player;
	}
}
