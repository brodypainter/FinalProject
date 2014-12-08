package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import GameController.Bulbasaur;
import GameController.Enemy;
import GameController.Pikachu;
import server.GameServer;
import client.Player;

public class Level3 extends Level implements Serializable{
	
	private static final int numbOfWaves = 8;

	public Level3(Player player, GameServer server) {
		super(player, server);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createWaves() {
		Random r = new Random();
		// TODO Auto-generated method stub
		ArrayList<ArrayList<Enemy>> waveList = new ArrayList<ArrayList<Enemy>>(); //A temporary 2D array list of Enemy
		for (int i = 0; i < 10; i++){
			ArrayList<Enemy> wave = new ArrayList<Enemy>();
			for (int j = 0; j < numbOfWaves; j++){
				int enemyGenerator = r.nextInt(10);
				if (enemyGenerator <= 4){
					Pikachu pika = new Pikachu(getMap());
					pika.setPathTravelingCode(0); //Will walk along path 0
					wave.add(pika);
				}
				else if (enemyGenerator >=5){
					Bulbasaur bulb = new Bulbasaur(getMap());
					bulb.setPathTravelingCode(0); //Will walk along path 0
					wave.add(bulb);
				}
			}
			waveList.add(wave);
		}
		setWavesList(waveList);
	}

	@Override
	public void setPlayerStartingHP() {
		// TODO Auto-generated method stub
		getPlayer().setHealth(100);
		notifyPlayerInfoUpdated();
	}

	@Override
	public void setPlayerStartingMoney() {
		// TODO Auto-generated method stub
		getPlayer().setMoney(1000);
		notifyPlayerInfoUpdated();
	}

	@Override
	public void setWaveDelayIntervals() {
		setWaveIntervals(15000L); //15 seconds between the last and first enemy of 2 successive waves
		// TODO Auto-generated method stub
	}

	@Override
	public void setEnemySpawnDelayIntervals() {
		setEnemySpawnIntervals(1500L); //2 second between each enemy spawning in a wave
		// TODO Auto-generated method stub
	}

	@Override
	public void setMap() {
		Map levelsMap = MapFactory.generateMap(getPlayer(), 0);
		 //the int mapCode is 0 because this is level 0 and we want Map 0
		levelsMap.setServer(this.getServer()); //Must set the map's server so it knows to send first update
		this.setMap(levelsMap);							//and where to send updates thereafter
		// TODO Auto-generated method stub
	}

}
