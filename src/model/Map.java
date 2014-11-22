package model;

import java.awt.Image;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import model.Tile;
import client.Player;
import GameController.Enemy;
import GameController.Tower;


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
 * LinkedList<Point> enemyPath	- A list of all of the path coordinates enemies will pass through
 * Point firstPathTile			- The path tile on which enemies spawn
 * Point lastPathTile			- The path tile on which enemies stop and do damage to health
 * int currentEnemies			- The current total amount of enemies on the map
 * String mapType				- A description of the map level, can be used for theme differentiation
 * int mapTypeCode				- A code # to differentiate each level
 * Player player				- The associated Player object playing this map
 * ArrayList<Enemy> enemies		- A list of all the enemies currently on the map
 * ArrayList<Tower> towers		- A list of all the towers currently placed on the map
 * 
 * 
 * Methods:
 * Map(Tile[][] gridDimensions, LinkedList<Point> path, String mapType, Image background, int mapTypeCode, Player player)
 * boolean spawnEnemy(Enemy enemy)
 * boolean updateEnemyPosition(Enemy enemy)
 * void removeDeadEnemy(Point location, Enemy enemy)
 * void lostHealth(int hpLost)
 * void gainedGold(int goldGained)
 * boolean addTower(Tower gym, Point location)
 * void removeTower(Tower gym)
 * 
 * @author Peter Hanson
 * @version 1.0
 */

public abstract class Map implements Serializable{
	
	private static final long serialVersionUID = -6337999339368538419L;
	private Tile[][] grid;
	private Image img; //The background image of the map, determined by mapType (did I import the right one?)
	private LinkedList<Point> enemyPath; //A list of all of the tile coordinates that the
	                                     //enemies will attempt to pass through
	private Point firstPathTile; //The path tile on which enemies spawn
	private Point lastPathTile; //The path tile on which enemies stop and do damage to health
	private int currentEnemies; //The current total amount of enemies on the map (necessary?)
	private String mapType; //A description of the map level, can be used for theme differentiation
	private int mapTypeCode; //A code # to differentiate each level
	private Player player; //The associated player object for this map
	private ArrayList<Enemy> enemies; //A list of all the enemies currently on the map
	private ArrayList<Tower> towers; //A list of all the towers currently placed on the map
	
	
	/**
	 * Constructs the Map object and initializes and populates some instance variables.
	 * @param gridDimensions A 2D Tile array with the desired dimensions for the map grid
	 * @param path A list of the coordinates along the grid through which enemies will pass
	 * @param mapType A description of the map's level
	 * @param background The background image of the map to be displayed in the GUI
	 * @param mapTypeCode The level number of this map, can be used to differentiate map events according to level
	 * @param player The Player object associated with who is playing this map
	 */
	public Map(Tile[][] gridDimensions, LinkedList<Point> path, String mapType, Image background, int mapTypeCode, Player player){
		grid = gridDimensions;
		enemyPath = path;
		this.mapType = mapType;
		img = background;
		this.mapTypeCode = mapTypeCode;
		this.player = player;
		currentEnemies = 0;
		setPath();
		setTilesMap();
		player.setMap(this);
		enemies = new ArrayList<Enemy>();
		towers = new ArrayList<Tower>();
	}
	
	public Map() {
		// TODO Auto-generated constructor stub
	}

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
	 * Sets the tiles in grid whose coordinates are part of the enemy path as path tiles.
	 * Also stores the first and last coordinates of the enemy path as Points for convenience.
	 */
	private void setPath(){
		Point tempCoords;
		for(int i = 0; i < enemyPath.size(); i++){
			tempCoords = enemyPath.get(i);
			grid[tempCoords.x][tempCoords.y].setAsPath();
			if(i == 0){
				grid[tempCoords.x][tempCoords.y].setFirstPathTile();
				firstPathTile = tempCoords;
			}
			if(i == enemyPath.size() - 1){
				grid[tempCoords.x][tempCoords.y].setLastPathTile();
				lastPathTile = tempCoords;
			}
			
		}
	}
	
	/**
	 * Adds an enemy Pokemon to this map, sets its location to the first tile in 
	 * the enemy path, and adds it to the map's enemies list.
	 * @param enemy The pokemon to be spawned
	 * @return boolean, true if successful, false if not
	 */
	public boolean spawnEnemy(Enemy enemy){
		enemy.setMap(this);
		grid[firstPathTile.x][firstPathTile.y].addPokemon(enemy);
		enemy.setLocation(firstPathTile);
		enemies.add(enemy);
		currentEnemies++;
		
		
		
		//Also may need to do other things here for GUI/threads, ...
		
		return true;
	}
	
	
	/**
	 * Updates an enemy pokemon's position from the current path tile to the next one.
	 * To be called by the Pokemon in its own thread every x seconds/movement.
	 * Currently enemies can only move 1 discrete square along the grid at a time in this model.
	 * @param enemy The Pokemon whose position is to be updated
	 * @return true if the movement was successful, false if not.
	 */
	public boolean updateEnemyPosition(Enemy enemy){
		
		//Each Pokemon running in its own thread will call this method, passing a reference
		//to itself, to make Map update its position when appropriate. Can only move in
		//1 square discrete amounts along the path currently.
		
		//get enemy's current coordinates, determine what his next coordinates will be
		Point enemyCoords = enemy.getLocation();
		int i = enemyPath.indexOf(enemyCoords);
		int iLess = i - 1;
		if(iLess == -1){
			iLess = 0;
		}
		int iMore = i + 2;
		if(iMore >= enemyPath.size()){
			iMore = enemyPath.size() - 1;
		}
		Point nextCoords = enemyPath.get(++i);
		
		//remove enemy from current tile, update his position, and add him to the next one
		grid[enemyCoords.x][enemyCoords.y].removePokemon(enemy);
		enemy.setPreviousLocation(enemyCoords);
		enemy.setLocation(nextCoords);
		grid[nextCoords.x][nextCoords.y].addPokemon(enemy);
		enemy.setNextLocation(enemyPath.get(iMore));
		
		return true;
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
	}
	
	/**
	 * Called by any enemy Pokemon that dies, passing its bounty worth.
	 * Map just passes on this information to the Player object keeping track of the gold.
	 * @param goldGained The amount of gold to gain, based on killed Pokemon's Worth.
	 */
	public void gainedGold(int goldGained){
		player.gainMoney(goldGained);
	}
	
	/**
	 * Attempts to add a Gym tower to a given location on the map.
	 * @param tower The Gym tower to be added.
	 * @param location The location to add the Gym tower to.
	 * @return true if successful (no preexisting tower there and not part of path)
	 * or false if tower cannot be placed.
	 */
	public boolean addTower(Tower tower, Point location){
		if(!grid[location.x][location.y].containsGym()){
			tower.setPlaceOnBoard(location);
			towers.add(tower);
			tower.setMap(this);
		return grid[location.x][location.y].setGym(tower);
		}else{
			return false;
		}
	}
	
	/**
	 * Removes a Gym tower from the Map. Unfinished, may be useful for resale feature.
	 * @param tower The Gym tower to be removed.
	 */
	//Not implemented yet, could pass either just the tower, or the tower's location on
	//the grid, whichever is easier. Could also have it automatically add gold to player
	//assuming we only use this method when tower is being resold.
	public void removeTower(Tower tower){
		Point p = tower.getPosition();
		grid[p.x][p.y].removeGym();
		towers.remove(tower);
		//If tower is being resold for $ we could figure out how much money to send
		//to Player object and do so here as well
	}
	
	//Maybe we should add methods that each Gym tower running in their own thread would
	//be able to call every x sec/atk which would give them access to the enemies list to look
	//for nearby in range target-able enemies, and then attack them?
	
	public ArrayList<Enemy> getEnemies(){
		return enemies;
	}
	
	public void tick(){
		//call all enemies and towers to call their tick() method, which will increment their
		//cool down timers, causing them to move/shoot if they are ready
		for(Tower tower : towers){
			tower.tick();
		}
		for(Enemy enemy : enemies){
			enemy.tick();
		}
	}
	
	
	
}
