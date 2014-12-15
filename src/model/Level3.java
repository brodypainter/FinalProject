package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import GameController.KoffingEnemy;
import GameController.McCannEnemy;
import GameController.MewEnemy;
import GameController.BulbasaurEnemy;
import GameController.GrowlitheEnemy;
import GameController.Enemy;
import GameController.PikachuEnemy;
import GameController.RattataEnemy;
import GameController.SquirtleEnemy;
import server.GameServer;
import client.Player;

public class Level3 extends Level implements Serializable{
	
	private static final long serialVersionUID = -5331188673366986472L;
	private static final int numbOfWaves = 8;
	
	public Level3(Player player, GameServer server) {
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
				int enemyGenerator = r.nextInt(20);
				if (enemyGenerator <= 2){
					PikachuEnemy pika = new PikachuEnemy(getMap1());
					int pathDecider = pathRandom.nextInt(getMap1().getNumberOfPaths());
					pika.setPathTravelingCode(pathDecider); //Will walk along path 0
					wave.add(pika);
				}
				else if (enemyGenerator >= 3 && enemyGenerator <= 5){
					BulbasaurEnemy bulb = new BulbasaurEnemy(getMap1());
					int pathDecider = pathRandom.nextInt(getMap1().getNumberOfPaths());
					bulb.setPathTravelingCode(pathDecider); //Will walk along path 0
					wave.add(bulb);
				}
				else if (enemyGenerator >= 6 && enemyGenerator <= 8){
					SquirtleEnemy squirt = new SquirtleEnemy(getMap1());
					int pathDecider = pathRandom.nextInt(getMap1().getNumberOfPaths());
					squirt.setPathTravelingCode(pathDecider);
					wave.add(squirt);
				}
				else if (enemyGenerator >= 9 && enemyGenerator <= 11){
					GrowlitheEnemy charm = new GrowlitheEnemy(getMap1());
					int pathDecider = pathRandom.nextInt(getMap1().getNumberOfPaths());
					charm.setPathTravelingCode(pathDecider);
					wave.add(charm);
				}
				else if (enemyGenerator >=12 && enemyGenerator <= 14){
					MewEnemy mew = new MewEnemy(getMap1());
					int pathDecider = pathRandom.nextInt(getMap1().getNumberOfPaths());
					mew.setPathTravelingCode(pathDecider);
					wave.add(mew);
				}
				else if (enemyGenerator >= 15 && enemyGenerator <= 16){
					KoffingEnemy koff =new KoffingEnemy(getMap1());
					int pathDecider = pathRandom.nextInt(getMap1().getNumberOfPaths());
					koff.setPathTravelingCode(pathDecider);
					wave.add(koff);
				}
				else if (enemyGenerator >= 17 && enemyGenerator <= 18){
					RattataEnemy ratt = new RattataEnemy(getMap1());
					int pathDecider = pathRandom.nextInt(getMap1().getNumberOfPaths());
					ratt.setPathTravelingCode(pathDecider);
					wave.add(ratt);
				}
				else if (enemyGenerator >= 19 && enemyGenerator <= 19){
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
		// TODO Auto-generated method stub
		getPlayer1().setHealth(100);
		notifyPlayerInfoUpdated(getPlayer1().getHealthPoints(), getPlayer1().getMoney(), true);
	}

	@Override
	public void setPlayerStartingMoney() {
		// TODO Auto-generated method stub
		getPlayer1().setMoney(3500);
		notifyPlayerInfoUpdated(getPlayer1().getHealthPoints(), getPlayer1().getMoney(), true);
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
		Map levelsMap = MapFactory.generateMap(getPlayer1(), 3);
		 //the int mapCode is 2 because this is level 3 and we want Map 3 Water Map (starts at 0)
		levelsMap.setServer(this.getServer()); //Must set the map's server so it knows to send first update
		this.setMap1(levelsMap);							//and where to send updates thereafter
		// TODO Auto-generated method stub
	}

}
