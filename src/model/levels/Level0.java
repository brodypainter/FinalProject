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


//created Level0

public class Level0 extends Level implements Serializable{
	
	private static final long serialVersionUID = -2776248289168724657L;
	private static final int numbOfWaves = 3;
	
	public Level0(Player player, GameServer server) {
		super(player, server);	
	}
	
	// enemies for the first level is a 50/50 chance of being bulbasaur or pikachu
	public void createWaves(){
		Random r = new Random();
		Random pathRandom= new Random();
		ArrayList<ArrayList<Enemy>> waveList = new ArrayList<ArrayList<Enemy>>(); //A temporary 2D array list of Enemy
		for (int i = 0; i < numbOfWaves; i++){
			ArrayList<Enemy> wave = new ArrayList<Enemy>();
			for (int j = 0; j < 5; j++){
				int enemyGenerator = r.nextInt(20);
				if (enemyGenerator <= 2){
					PikachuEnemy pika = new PikachuEnemy(getMap());
					int numberOfPaths = getMap().getNumberOfPaths();
					int pathDecider = pathRandom.nextInt(numberOfPaths);
					pika.setPathTravelingCode(pathDecider); //Will walk along path 0
					wave.add(pika);
				}
				else if (enemyGenerator >= 3 && enemyGenerator <= 5){
					BulbasaurEnemy bulb = new BulbasaurEnemy(getMap());
					int numberOfPaths = getMap().getNumberOfPaths();
					int pathDecider = pathRandom.nextInt(numberOfPaths);
					bulb.setPathTravelingCode(pathDecider); //Will walk along path 0
					wave.add(bulb);
				}
				else if (enemyGenerator >= 6 && enemyGenerator <= 7){
					SquirtleEnemy squirt = new SquirtleEnemy(getMap());
					int numberOfPaths = getMap().getNumberOfPaths();
					int pathDecider = pathRandom.nextInt(numberOfPaths);
					squirt.setPathTravelingCode(pathDecider);
					wave.add(squirt);
				}
				else if (enemyGenerator >= 8 && enemyGenerator <= 10){
					GrowlitheEnemy charm = new GrowlitheEnemy(getMap());
					int numberOfPaths = getMap().getNumberOfPaths();
					int pathDecider = pathRandom.nextInt(numberOfPaths);
					charm.setPathTravelingCode(pathDecider);
					wave.add(charm);
				}
				else if (enemyGenerator >= 11 && enemyGenerator <= 12){
					MewEnemy mew = new MewEnemy(getMap());
					int numberOfPaths = getMap().getNumberOfPaths();
					int pathDecider = pathRandom.nextInt(numberOfPaths);
					mew.setPathTravelingCode(pathDecider);
					wave.add(mew);
				}
				else if (enemyGenerator >= 13 && enemyGenerator <= 14){
					KoffingEnemy koff =new KoffingEnemy(getMap());
					int numberOfPaths = getMap().getNumberOfPaths();
					int pathDecider = pathRandom.nextInt(numberOfPaths);
					koff.setPathTravelingCode(pathDecider);
					wave.add(koff);
				}
				else if (enemyGenerator >= 15 && enemyGenerator <= 18){
					RattataEnemy ratt = new RattataEnemy(getMap());
					int numberOfPaths = getMap().getNumberOfPaths();
					int pathDecider = pathRandom.nextInt(numberOfPaths);
					ratt.setPathTravelingCode(pathDecider);
					wave.add(ratt);
				}
				else if (enemyGenerator >= 19 && enemyGenerator <= 19){
					McCannEnemy doctor = new McCannEnemy(getMap());
					int numberOfPaths = getMap().getNumberOfPaths();
					int pathDecider = pathRandom.nextInt(numberOfPaths);
					doctor.setPathTravelingCode(pathDecider);
					wave.add(doctor);
				}
			}
			waveList.add(wave);
		}
		setWavesList(waveList); //Set the master wavesList inherited instance variable
	}
	
	@Override
	public void setPlayerStartingHP() {
		getPlayer().setHealth(100);
		notifyPlayerInfoUpdated(getPlayer().getHealthPoints(), getPlayer().getMoney());
	}

	@Override
	public void setPlayerStartingMoney() {
		getPlayer().setMoney(2000);
		notifyPlayerInfoUpdated(getPlayer().getHealthPoints(), getPlayer().getMoney());;
	}

	@Override
	public void setWaveDelayIntervals() {
		setWaveIntervals(10000L); //10 seconds between the last and first enemy of 2 successive waves
	}

	@Override
	public void setEnemySpawnDelayIntervals() {
		setEnemySpawnIntervals(1500L); //1.5 second between each enemy spawning in a wave
	}

	@Override
	public void setMap() {
		Map levelsMap = MapFactory.generateMap(getPlayer(), 0);
		 //the int mapCode is 0 because this is level 0 and we want Map 0 EarthMap
		levelsMap.setServer(this.getServer()); //Must set the map's server so it knows to send first update
		this.setMap(levelsMap);							//and where to send updates thereafter
	}
	
}

