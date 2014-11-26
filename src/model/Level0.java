package model;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import GameController.Enemy;
import GameController.Pikachu;
import GameController.Tower;
import server.GameServer;
import client.Player;


//created Level0

public class Level0 extends Level implements Serializable{
	
	private static final int numbOfWaves = 3;
	
	public Level0(Player player, GameServer server) {
		super(player, server);	
	}

	//Method below no longer needs to be overridden, the game loop is now in Level class.
	/*
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
		/*	ArrayList<Enemy> wave = getWavesList().get(waveCounter);
			int enemiesInWave = 0;
			
			do{
				// as we add enemies to the board take them out of this arrayList
				if(!wave.isEmpty()){
					getMap().spawnEnemy(wave.remove(enemiesInWave));
					enemiesInWave++;
				}
				
			}while (!getMap().getEnemies().isEmpty());
			
			waveCounter++;
		}
	}/*
	
	
	
	/**
	 * 
	 * @return
	 */
	
	
	/**@ Max Justice
	 * this method takes the tower that was selected by the player and the position they want to set it at
	 * I envision the player picking a tower but the only way to set it is if they have the money to buy it.
	 * 
	 * it gets the current player's money and the tower tell if it can be bought.  If so the tower is added to the map
	 * with the postion it set at.  the tower position itself it set and returns
	 * 
	 */


	/**
	 * 
	 */
	public void createWaves(){
		ArrayList<ArrayList<Enemy>> waveList = new ArrayList<ArrayList<Enemy>>(); //A temporary 2D array list of Enemy
		for (int i = 0; i < 3; i++){
			ArrayList<Enemy> wave = new ArrayList<Enemy>();
			for (int j = 0; j < 20; j++){
				Pikachu pika = new Pikachu(getMap());
				wave.add(pika);
			}
			waveList.add(wave);
		}
		setWavesList(waveList); //Set the master wavesList inherited instance variable
	}
	
	@Override
	public void setPlayerStartingHP() {
		getPlayer().setHealth(100);
		notifyPlayerInfoUpdated();
	}

	@Override
	public void setPlayerStartingMoney() {
		getPlayer().setMoney(1000);
		notifyPlayerInfoUpdated();
	}

	@Override
	public void setWaveDelayIntervals() {
		setWaveIntervals(10000L); //10 seconds between the last and first enemy of 2 successive waves
	}

	@Override
	public void setEnemySpawnDelayIntervals() {
		setEnemySpawnIntervals(500L); //0.5 second between each enemy spawning in a wave
	}

	@Override
	public void setMap() {
		Map levelsMap = MapFactory.generateMap(getPlayer(), 0);
		 //the int mapCode is 0 because this is level 0 and we want Map 0
		levelsMap.setServer(this.getServer()); //Must set the map's server so it knows to send first update
		this.setMap(levelsMap);							//and where to send updates thereafter
	}
	
}

