package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import GameController.BulbasaurEnemy;
import GameController.GrowlitheEnemy;
import GameController.Enemy;
import GameController.PikachuEnemy;
import GameController.SquirtleEnemy;
import server.GameServer;
import client.Player;

public class Level1 extends Level implements Serializable{
	
	private static final int numbOfWaves = 4;

	public Level1(Player player, GameServer server) {
		super(player, server);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createWaves() {
		Random r = new Random();
		// TODO Auto-generated method stub
		ArrayList<ArrayList<Enemy>> waveList = new ArrayList<ArrayList<Enemy>>(); //A temporary 2D array list of Enemy
		for (int i = 0; i < numbOfWaves; i++){
			ArrayList<Enemy> wave = new ArrayList<Enemy>();
			for (int j = 0; j < 5; j++){
				int enemyGenerator = r.nextInt(10);
				if (enemyGenerator <= 3){
					PikachuEnemy pika = new PikachuEnemy(getMap());
					pika.setPathTravelingCode(0); //Will walk along path 0
					wave.add(pika);
				}
				else if (enemyGenerator >= 4 && enemyGenerator <=  5){
					BulbasaurEnemy bulb = new BulbasaurEnemy(getMap());
					bulb.setPathTravelingCode(0); //Will walk along path 0
					wave.add(bulb);
				}
				else if (enemyGenerator >= 6 && enemyGenerator <= 7 ){
					SquirtleEnemy squirt = new SquirtleEnemy(getMap());
					squirt.setPathTravelingCode(0);
					wave.add(squirt);
				}
				else if (enemyGenerator >= 8 && enemyGenerator <= 9){
					GrowlitheEnemy charm = new GrowlitheEnemy(getMap());
					charm.setPathTravelingCode(0);
					wave.add(charm);
				}
			}
			waveList.add(wave);
		}
		setWavesList(waveList);

	}

	@Override
	public void setPlayerStartingHP() {
		getPlayer().setHealth(100);
		notifyPlayerInfoUpdated();
		// TODO Auto-generated method stub

	}

	@Override
	public void setPlayerStartingMoney() {
		getPlayer().setMoney(1000);
		notifyPlayerInfoUpdated();
		// TODO Auto-generated method stub

	}

	@Override
	public void setWaveDelayIntervals() {
		setWaveIntervals(15000L); //15 seconds between the last and first enemy of 2 successive waves
		// TODO Auto-generated method stub

	}

	@Override
	public void setEnemySpawnDelayIntervals() {
		setEnemySpawnIntervals(2000L); //2 second between each enemy spawning in a wave
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
