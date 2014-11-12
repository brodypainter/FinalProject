package model;

import java.awt.Image;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import model.Tile;
import client.Player;
import GameController.Gym;
import GameController.Pokemon;

//Created Map class hopefully in right folder ....Test comment PWH

//Testing push with this comment 11/11 9:42pm PWH

public abstract class Map implements Serializable{
	
	
	
	/**
	 * 
	 */
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
	private ArrayList<Pokemon> enemies; //A list of all the enemies currently on the map
	private ArrayList<Gym> towers; //A list of all the towers currently placed on the map
	
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
		enemies = new ArrayList<Pokemon>();
		towers = new ArrayList<Gym>();
	}
	
	private void setTilesMap() {
		for(int r = 0; r < grid.length; r++){
			for(int c = 0; c < grid[r].length; c++){
				grid[r][c].setMap(this);
			}
		}
		
	}

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
		
	public boolean spawnEnemy(Pokemon enemy){
		enemy.setMap(this);
		grid[firstPathTile.x][firstPathTile.y].addPokemon(enemy);
		enemy.setLocation(firstPathTile);
		enemies.add(enemy);
		currentEnemies++;
		
		
		
		//Also may need to do other things here for GUI/threads, ...
		
		return true;
	}
	
	public boolean updateEnemyPosition(Pokemon enemy){
		
		//Each Pokemon running in its own thread will call this method, passing a reference
		//to itself, to make Map update its position when appropriate. Can only move in
		//1 square discrete amounts along the path currently.
		
		//get enemy's current coordinates, determine what his next coordinates will be
		Point enemyCoords = enemy.getLocation();
		int i = enemyPath.indexOf(enemyCoords);
		Point nextCoords = enemyPath.get(++i);
		
		//remove enemy from current tile, update his position, and add him to the next one
		grid[enemyCoords.x][enemyCoords.y].removePokemon(enemy);
		enemy.setLocation(nextCoords);
		grid[nextCoords.x][nextCoords.y].addPokemon(enemy);
		
		return true;
	}
	
	public void removeDeadEnemy(Point location, Pokemon enemy){
		grid[location.x][location.y].removePokemon(enemy);
		enemies.remove(enemy);
		currentEnemies--;
	}
	
	public void lostHealth(int hpLost){
		//called by the last tile in the path every time an enemy gets to it
		//Map just passes on this information to the Player object.
		player.loseHealth(hpLost);
	}
	
	public void gainedGold(int goldGained){
		//called by any enemy Pokemon that dies
		//Map just passes on this information to the Player object.
		player.gainMoney(goldGained);
	}
	
	public boolean addTower(Gym gym, Point location){
		if(!grid[location.x][location.y].containsGym()){
			gym.setPlaceOnBoard(location);
			towers.add(gym);
		return grid[location.x][location.y].setGym(gym);
		}else{
			return false;
		}
	}
	
	//Not implemented yet, could pass either just the tower, or the tower's location on
	//the grid, whichever is easier. Could also have it automatically add gold to player
	//assuming we only use this method when tower is being resold.
	public void removeTower(Gym gym){
		Point p = gym.getPosition();
		grid[p.x][p.y].removeGym();
		towers.remove(gym);
		//If tower is being resold for $ we could figure out how much money to send
		//to Player object and do so here as well
	}
	
	
	
		
}
