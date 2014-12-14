package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import GameController.BulbasaurEnemy;
import GameController.GrowlitheEnemy;
import GameController.Enemy;
import GameController.KoffingEnemy;
import GameController.McCannEnemy;
import GameController.MewEnemy;
import GameController.PikachuEnemy;
import GameController.RattataEnemy;
import GameController.SquirtleEnemy;
import server.GameServer;
import client.Player;

public class Level1 extends Level implements Serializable{
	
	private static final long serialVersionUID = 8553831735812259923L;
	private static final int numbOfWaves = 4;

	public Level1(Player player, GameServer server) {
		super(player, server);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createWaves() {
		Random r = new Random();
		Random pathRandom= new Random();
		// TODO Auto-generated method stub
		ArrayList<ArrayList<Enemy>> waveList = new ArrayList<ArrayList<Enemy>>(); //A temporary 2D array list of Enemy
		for (int i = 0; i < numbOfWaves; i++){
			ArrayList<Enemy> wave = new ArrayList<Enemy>();
			for (int j = 0; j < 5; j++){
				int enemyGenerator = r.nextInt(20); // choose a value between 0 and 19
				if (enemyGenerator <= 4){
					PikachuEnemy pika = new PikachuEnemy(getMap1());
					int pathDecider = pathRandom.nextInt(getMap1().getNumberOfPaths());
					pika.setPathTravelingCode(pathDecider); //Will walk along path 0
					wave.add(pika);
				}
				else if (enemyGenerator >= 5 && enemyGenerator <= 7){
					BulbasaurEnemy bulb = new BulbasaurEnemy(getMap1());
					int pathDecider = pathRandom.nextInt(getMap1().getNumberOfPaths());
					bulb.setPathTravelingCode(pathDecider); //Will walk along path 0
					wave.add(bulb);
				}
				else if (enemyGenerator >= 8 && enemyGenerator <= 11){
					SquirtleEnemy squirt = new SquirtleEnemy(getMap1());
					int pathDecider = pathRandom.nextInt(getMap1().getNumberOfPaths());
					squirt.setPathTravelingCode(pathDecider);
					wave.add(squirt);
				}
				else if (enemyGenerator >= 12 && enemyGenerator <= 14){
					GrowlitheEnemy charm = new GrowlitheEnemy(getMap1());
					int pathDecider = pathRandom.nextInt(getMap1().getNumberOfPaths());
					charm.setPathTravelingCode(pathDecider);
					wave.add(charm);
				}
				else if (enemyGenerator >= 15 && enemyGenerator <= 16){
					MewEnemy mew = new MewEnemy(getMap1());
					int pathDecider = pathRandom.nextInt(getMap1().getNumberOfPaths());
					mew.setPathTravelingCode(pathDecider);
					wave.add(mew);
				}
				else if (enemyGenerator >= 99 && enemyGenerator <= 100){
					KoffingEnemy koff =new KoffingEnemy(getMap1());
					int pathDecider = pathRandom.nextInt(getMap1().getNumberOfPaths());
					koff.setPathTravelingCode(pathDecider);
					wave.add(koff);
				}
				else if (enemyGenerator >= 17 && enemyGenerator <= 19){
					RattataEnemy ratt = new RattataEnemy(getMap1());
					int pathDecider = pathRandom.nextInt(getMap1().getNumberOfPaths());
					ratt.setPathTravelingCode(pathDecider);
					wave.add(ratt);
				}
				else if (enemyGenerator >= 99 && enemyGenerator <= 100){
					McCannEnemy doctor = new McCannEnemy(getMap1());
					int pathDecider = pathRandom.nextInt(getMap1().getNumberOfPaths());
					doctor.setPathTravelingCode(pathDecider);
					wave.add(doctor);
				}
			}
			waveList.add(wave);
		}
		setWavesList(waveList);

	}

	@Override
	public void setPlayerStartingHP() {
		getPlayer1().setHealth(100);
		notifyPlayerInfoUpdated(getPlayer1().getHealthPoints(), getPlayer1().getMoney(), true);
		// TODO Auto-generated method stub

	}

	@Override
	public void setPlayerStartingMoney() {
		getPlayer1().setMoney(1000);
		notifyPlayerInfoUpdated(getPlayer1().getHealthPoints(), getPlayer1().getMoney(), true);
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
		Map levelsMap = MapFactory.generateMap(getPlayer1(), 0);
		 //the int mapCode is 0 because this is level 1 and we want Map 0 Earth Map
		levelsMap.setServer(this.getServer()); //Must set the map's server so it knows to send first update
		this.setMap(levelsMap);							//and where to send updates thereafter
		// TODO Auto-generated method stub

	}

}
