package model;

import java.awt.Point;
import java.util.ArrayList;

import GameController.Enemy;
import GameController.Pikachu;
import GameController.Tower;
import server.GameServer;
import client.Player;


//created Level0

public class Level0 extends Level{
	
	private static final int numbOfWaves = 3;
	private int countDown = 180;
	private int waveCounter = 0;
	
	ArrayList<ArrayList<Enemy> > waveList;

	public Level0(Player player, GameServer server) {
		super(player, server);
		waveList = new ArrayList<ArrayList<Enemy>>();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void levelSpecificSetup() {
		map = MapFactory.generateMap(player, 0);
		
	}

	@Override
	public void levelStart() {
		//assume the map will always have already been set in constructor Max - PH
		createWaves();
		
		// TODO Auto-generated method stub
		while (!gameOver()){
			
			// TODO add the tick as to when to add enemies to the board
			/**
			 * this gets the arraylist<enemy> in the arraylist<arrayList>
			 * it sets the enemiesInWave to.  it then adds the enemies from the waveList to the board
			 * TODO Peter place you tick here for spawning and adding the board
			 * it does this while the arraylist on the map is not empty
			 * an empty arraylist on the map signifies the wave is dead and it is time
			 * to move to the next one
			 */
			ArrayList<Enemy> wave = waveList.get(waveCounter);
			int enemiesInWave = 0;
			
			do{
				// as we add enemies to the board take them out of this arrayList
				if(!wave.isEmpty()){
					map.spawnEnemy(wave.remove(enemiesInWave));
					enemiesInWave++;
				}
				
			}while (!map.getEnemies().isEmpty());
			
			waveCounter++;
		}
	}
	
	/**
	 * 
	 */
	public void createWaves( ){
		for (int i = 0; i < 3; i++){
			ArrayList<Enemy> wave = new ArrayList<Enemy>();
			for (int j = 0; j < 20; j++){
				Pikachu pika = new Pikachu(getMap());
				wave.add(pika);
			}
			waveList.add(wave);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean gameOver(){
		if(this.getPlayer().getHealthPoints() < 0){
			youLose();
			return true;
		}else if (waveList.isEmpty() && this.getPlayer().getHealthPoints() >= 0){
			youWin();
			return true;
		}
		return false;
	}
	
	public boolean youLose(){
		
		return false;
	}
	
	public boolean youWin(){
		
		return false;
	}
	
	/**@ Max Justice
	 * this method takes the tower that was selected by the player and the position they want to set it at
	 * I envision the player picking a tower but the only way to set it is if they have the money to buy it.
	 * 
	 * it gets the current player's money and the tower tell if it can be bought.  If so the tower is added to the map
	 * with the postion it set at.  the tower position itself it set and returns
	 * 
	 */

	
	public boolean SetTower(Tower newTower, Point position){
		if(newTower.checkBuy(getPlayer().getMoney())){
			getPlayer().spendMoney(newTower.getCost());
			getMap().addTower(newTower, position);
			newTower.setPlaceOnBoard(position);
			return true;
		}
		return false;
	}
	
}

