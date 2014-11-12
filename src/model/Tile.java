package model;

import java.io.Serializable;
import java.util.ArrayList;
import GameController.Gym;
import GameController.Pokemon;

/**
 * This class creates Tile objects which represent single squares in the gameboard grid
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
 * Gym gymTower					- The Gym tower on this tile if there is one
 * ArrayList<Pokemon> enemies	- A list of all enemy Pokemon present on the tile if any
 * 
 * Methods:
 * public Tile()
 * void setAsPath()
 * boolean isPartOfPath()
 * boolean containsGym()
 * boolean setGym(Gym tower
 * Gym getGym()
 * boolean containsPokemon()
 * void addPokemon(Pokemon enemyPoke)
 * void removePokemon(Pokemon enemyPoke)
 * ArrayList<Pokemon> getPokemon()
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
	private Gym gymTower;
	private ArrayList<Pokemon> enemies;
	
	
		public Tile(){
			partOfPath = false;
			containsGym = false;
			containsEnemy = false;
			firstPathTile = false;
			lastPathTile = false;
			gymTower = null;
			enemies = new ArrayList<Pokemon>();
		}

		public void setAsPath(){
			partOfPath = true;
		}
		
		public boolean isPartOfPath(){
			return partOfPath;
		}
		
		public boolean containsGym(){
			return containsGym;
		}
		
		public boolean setGym(Gym tower){
			if(!containsGym && !partOfPath){
			containsGym = true;
			this.gymTower = tower;
			return true;
			}else{
				return false;
			}
		}
		
		public Gym getGym(){
			if(containsGym){
			return gymTower;
			}else{
				return null;
			}
		}
		
		public boolean containsPokemon(){
			return containsEnemy;
		}
		
		public void addPokemon(Pokemon enemyPoke){
			enemies.add(enemyPoke);
			containsEnemy = true;
			
			//If an enemy makes it to the last tile in the path alert the tile's Map
			//so that it can cause player to lose health equal to the enemy's attack power
			if(lastPathTile){
				map.lostHealth(enemyPoke.getAttackPower());
				
				//TODO:then delete the enemy and everything that does with it here (not finished)
				
				this.removePokemon(enemyPoke);
			}
		}
		
		public void removePokemon(Pokemon enemyPoke){
			if(containsEnemy){
			enemies.remove(enemyPoke);
			}
			if(enemies.size() == 0){
				containsEnemy = false;
			}
		}
		
		public ArrayList<Pokemon> getPokemon(){
			return enemies;
		}

		public boolean isFirstPathTile(){
			return firstPathTile;
		}

		public void setFirstPathTile(){
			firstPathTile = true;
		}

		public boolean isLastPathTile(){
			return lastPathTile;
		}

		public void setLastPathTile(){
			lastPathTile = true;
		}

		public Map getMap(){
			return map;
		}

		public void setMap(Map map){
			this.map = map;
		}

		public void removeGym(){
			gymTower = null;
			containsGym = false;
		}
}
