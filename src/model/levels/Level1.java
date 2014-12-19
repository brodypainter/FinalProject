package model.levels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import model.Player;
import model.enemies.BulbasaurEnemy;
import model.enemies.Enemy;
import model.enemies.GrowlitheEnemy;
import model.enemies.KoffingEnemy;
import model.enemies.McCannEnemy;
import model.enemies.MewEnemy;
import model.enemies.PikachuEnemy;
import model.enemies.RattataEnemy;
import model.enemies.SquirtleEnemy;
import model.maps.Map;
import model.maps.MapFactory;
import controller.GameServer;

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
				if (enemyGenerator <= 2){
					PikachuEnemy pika = new PikachuEnemy(getMap());
					int pathDecider = pathRandom.nextInt(getMap().getNumberOfPaths());
					pika.setPathTravelingCode(pathDecider); //Will walk along path 0
					wave.add(pika);
				}
				else if (enemyGenerator >= 3 && enemyGenerator <= 5){
					BulbasaurEnemy bulb = new BulbasaurEnemy(getMap());
					int pathDecider = pathRandom.nextInt(getMap().getNumberOfPaths());
					bulb.setPathTravelingCode(pathDecider); //Will walk along path 0
					wave.add(bulb);
				}
				else if (enemyGenerator >= 6 && enemyGenerator <= 7){
					SquirtleEnemy squirt = new SquirtleEnemy(getMap());
					int pathDecider = pathRandom.nextInt(getMap().getNumberOfPaths());
					squirt.setPathTravelingCode(pathDecider);
					wave.add(squirt);
				}
				else if (enemyGenerator >= 8 && enemyGenerator <= 9){
					GrowlitheEnemy charm = new GrowlitheEnemy(getMap());
					int pathDecider = pathRandom.nextInt(getMap().getNumberOfPaths());
					charm.setPathTravelingCode(pathDecider);
					wave.add(charm);
				}
				else if (enemyGenerator >= 10 && enemyGenerator <= 12){
					MewEnemy mew = new MewEnemy(getMap());
					int pathDecider = pathRandom.nextInt(getMap().getNumberOfPaths());
					mew.setPathTravelingCode(pathDecider);
					wave.add(mew);
				}
				else if (enemyGenerator >= 13 && enemyGenerator <= 15){
					KoffingEnemy koff =new KoffingEnemy(getMap());
					int pathDecider = pathRandom.nextInt(getMap().getNumberOfPaths());
					koff.setPathTravelingCode(pathDecider);
					wave.add(koff);
				}
				else if (enemyGenerator >= 16 && enemyGenerator <= 18){
					RattataEnemy ratt = new RattataEnemy(getMap());
					int pathDecider = pathRandom.nextInt(getMap().getNumberOfPaths());
					ratt.setPathTravelingCode(pathDecider);
					wave.add(ratt);
				}
				else if (enemyGenerator >= 19 && enemyGenerator <= 19){
					McCannEnemy doctor = new McCannEnemy(getMap());
					int pathDecider = pathRandom.nextInt(getMap().getNumberOfPaths());
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
		getPlayer().setHealth(100);
		notifyPlayerInfoUpdated(getPlayer().getHealthPoints(), getPlayer().getMoney());
		// TODO Auto-generated method stub

	}

	@Override
	public void setPlayerStartingMoney() {
		getPlayer().setMoney(2000);
		notifyPlayerInfoUpdated(getPlayer().getHealthPoints(), getPlayer().getMoney());
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
		Map levelsMap = MapFactory.generateMap(getPlayer(), 1);
		 //the int mapCode is 0 because this is level 1 and we want Map 0 Earth Map
		levelsMap.setServer(this.getServer()); //Must set the map's server so it knows to send first update
		this.setMap(levelsMap);							//and where to send updates thereafter
		// TODO Auto-generated method stub

	}

}
