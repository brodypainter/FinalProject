package model;

import java.io.Serializable;
import java.util.ArrayList;

import GameController.Gym;
import GameController.Pokemon;

public class Tile implements Serializable{
	
	private Map map;
	
	private boolean partOfPath;
	private boolean containsGym;
	private boolean containsEnemy;
	private boolean firstPathTile;
	private boolean lastPathTile;
	
	private Gym gymTower;
	private ArrayList<Pokemon> enemies;
	
	

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5228502357038228598L;

		public Tile(){
			partOfPath = false;
			containsGym = false;
			containsEnemy = false;
			firstPathTile = false;
			lastPathTile = false;
			gymTower = null;
			enemies = new ArrayList<Pokemon>();
		}

		public void setAsPath() {
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
			
			//If an enemy makes it to the last tile in the path alert the tiles Map
			//so that it can cause player to lose health equal to the enemy's attack power
			if(lastPathTile){
				map.lostHealth(enemyPoke.getAttackPower());
				
				//then delete the enemy and everything that does with it here (not finished)
				
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

		public boolean isFirstPathTile() {
			return firstPathTile;
		}

		public void setFirstPathTile() {
			firstPathTile = true;
		}

		public boolean isLastPathTile() {
			return lastPathTile;
		}

		public void setLastPathTile() {
			lastPathTile = true;
		}

		public Map getMap() {
			return map;
		}

		public void setMap(Map map) {
			this.map = map;
		}

		public void removeGym() {
			gymTower = null;
			containsGym = false;
		}
}
