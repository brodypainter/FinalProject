package model;

import java.io.Serializable;
import java.util.ArrayList;
import GameController.Tower;
import GameController.Enemy;

/**
 * This class creates Tile objects which represent single squares in the model grid
 * modeled by the Map class. Tiles can be updated and can tell which Map they belong to,
 * if they are part of the enemy path, and if so if they are the first or last tile, and
 * also know if they contain a Gym tower or a Pokemon enemy on them.
 * 
 * Instance Variables:
 * long serialVersionUID		- For Serializable interface
 * Map map						- The Map which the tile is part of
 * boolean partOfPath			- True if the tile is part of the enemy path
 * boolean containsGym;			- True if tile contains a Gym tower
 * boolean containsEnemy		- True if at least 1 enemy Pokemon occupies the tile
 * boolean firstPathTile		- True if the tile is the first along the enemy path
 * boolean lastPathTile			- True if the tile is the last along the enemy path
 * Tower gymTower				- The Tower on this tile if there is one
 * ArrayList<Enemy> enemies		- A list of all enemy Pokemon present on the tile if any
 * 
 * Methods:
 * public Tile()
 * void setAsPath()
 * boolean isPartOfPath()
 * boolean containsGym()
 * boolean setGym(Tower tower)
 * Tower getGym()
 * boolean containsPokemon()
 * void addPokemon(Enemy enemyPoke)
 * void removePokemon(Enemy enemyPoke)
 * ArrayList<Enemy> getPokemon()
 * boolean isFirstPathTile()
 * void setFirstPathTile()
 * boolean isLastPathTile()
 * void setLastPathTile()
 * Map getMap()
 * void setMap(Map map)
 * void removeGym()
 * 
 * @author Peter Hanson
 * @version 1.0
 */

public class Tile implements Serializable{
	
	private static final long serialVersionUID = -5228502357038228598L;
	private Map map;
	private boolean partOfPath;
	private boolean containsGym;
	private boolean containsEnemy;
	private boolean firstPathTile;
	private boolean lastPathTile;
	private Tower gymTower;
	private ArrayList<Enemy> enemies;
	
	/**
	 * Constructor
	 */
	public Tile(){
		partOfPath = false;
		containsGym = false;
		containsEnemy = false;
		firstPathTile = false;
		lastPathTile = false;
		gymTower = null;
		enemies = new ArrayList<Enemy>();
	}

	/**
	 * Sets tile as a part of the path
	 */
	public void setAsPath(){
		partOfPath = true;
	}
	
	/**
	 * Checks if tile is a part of path
	 * @return partOfPath, true if it is
	 */
	public boolean isPartOfPath(){
		return partOfPath;
	}
		
	/**
	 * Checks if tile already contains a Tower
	 * @return containsGym, true if it does
	 */
	public boolean containsGym(){
		return containsGym;
	}
	
	/**
	 * Places a Tower at tile if possible
	 * @param tower the Tower to be placed
	 * @return true if placement was successful, false if not
	 */
	public boolean setGym(Tower tower){
		if(!containsGym && !partOfPath){
			containsGym = true;
			this.gymTower = tower;
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * returns the Tower at the tile location
	 * @return gymTower, the Tower at location else null if there is none
	 */
	public Tower getGym(){
		if(containsGym){
			return gymTower;
		}else{
				return null;
		}
	}
	
	/**
	 * Checks if there are any enemies at this location
	 * @return true if there are, else false
	 */
	public boolean containsPokemon(){
		return containsEnemy;
	}
	
	/**
	 * Adds an enemy pokemon to this tile. If this tile is the
	 * last tile in the enemy path and an enemy is added to it
	 * this tile will alert its map that it has lost health
	 * equal to the breaching enemy's attack power. It will
	 * then remove that enemy
	 * @param enemyPoke the Enemy to be added
	 */
	public void addPokemon(Enemy enemyPoke){
		enemies.add(enemyPoke);
		containsEnemy = true;
			
		//If an enemy makes it to the last tile in the path alert the tile's Map
		//so that it can cause player to lose health equal to the enemy's attack power
		if(lastPathTile){
			map.lostHealth(enemyPoke.getAttackPower());
			map.removeDeadEnemy(enemyPoke.getLocation(), enemyPoke);
		}
	}
		
	/**
	 * Removes an Enemy from this tile location
	 * @param enemyPoke the Enemy to remove
	 */
	public void removePokemon(Enemy enemyPoke){
		if(containsEnemy){
		enemies.remove(enemyPoke);
		}
		if(enemies.size() == 0){
			containsEnemy = false;
		}
	}
		
	/**
	 * Returns all Enemies at location
	 * @return enemies an ArrayList<Enemy> of all enemies here
	 */
	public ArrayList<Enemy> getPokemon(){
		return enemies;
	}

	/**
	 * Check if this tile is the first in its path
	 * @return true if it is
	 */
	public boolean isFirstPathTile(){
		return firstPathTile;
	}

	/**
	 * Set this tile as the first in its path
	 */
	public void setFirstPathTile(){
		firstPathTile = true;
	}

	/**
	 * Check if this tile is the last in its path
	 * @return true if it is
	 */
	public boolean isLastPathTile(){
		return lastPathTile;
	}

	/**
	 * Set this tile as the last in its path
	 */
	public void setLastPathTile(){
		lastPathTile = true;
	}

	/**
	 * Returns the Map that this tile belongs on
	 * @return map this Tile's Map
	 */
	public Map getMap(){
		return map;
	}
	
	/**
	 * Sets this Tile's Map
	 * @param map the Map to set to
	 */
	public void setMap(Map map){
		this.map = map;
	}

	/**
	 * Removes any Tower at this tile location
	 */
	public void removeGym(){
		gymTower = null;
		containsGym = false;
	}
}
