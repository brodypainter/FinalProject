package model;

import java.util.ArrayList;
import java.util.Random;

import GameController.BulbasaurEnemy;
import GameController.Enemy;
import GameController.GrowlitheEnemy;
import GameController.KoffingEnemy;
import GameController.McCannEnemy;
import GameController.MewEnemy;
import GameController.PikachuEnemy;
import GameController.RattataEnemy;
import GameController.SquirtleEnemy;
import server.GameServer;
import client.Player;

/**
 * May be unnecessary after changes i just made ignore for now
 * @author Peter Hanson
 *
 */
public class LevelMultiplayer extends Level {

	
	private static final long serialVersionUID = -4356179074259053436L;
	private static final int numbOfWaves = 3;
	private Map map2;
	private Player player2;
	
	public LevelMultiplayer(Player player, GameServer server) {
		super(player, server);
		
		//Multiplayer addition: add the 2nd player and give them a map as well
		this.player2 = getPlayer1().getPartner();
		player2.setHealth(this.getPlayer1().getHealthPoints());
		player2.setMoney(this.getPlayer1().getMoney());
		this.map2 = MapFactory.generateMap(player2, 4);
		map2.setServer(server);
	}
	


	
	// enemies for the first level is a 50/50 chance of being bulbasaur or pikachu
	public void createWaves(){
		Random r = new Random();
		ArrayList<ArrayList<Enemy>> waveList = new ArrayList<ArrayList<Enemy>>(); //A temporary 2D array list of Enemy
		for (int i = 0; i < numbOfWaves; i++){
			ArrayList<Enemy> wave = new ArrayList<Enemy>();
			for (int j = 0; j < 5; j++){
				int enemyGenerator = r.nextInt(20);
				if (enemyGenerator <= 6){
					PikachuEnemy pika = new PikachuEnemy(getMap1());
					pika.setPathTravelingCode(0); //Will walk along path 0
					wave.add(pika);
				}
				else if (enemyGenerator >= 7 && enemyGenerator <= 12){
					BulbasaurEnemy bulb = new BulbasaurEnemy(getMap1());
					bulb.setPathTravelingCode(0); //Will walk along path 0
					wave.add(bulb);
				}
				else if (enemyGenerator >= 13 && enemyGenerator <= 15){
					SquirtleEnemy squirt = new SquirtleEnemy(getMap1());
					squirt.setPathTravelingCode(0);
					wave.add(squirt);
				}
				else if (enemyGenerator >= 100 && enemyGenerator <= 100){
					GrowlitheEnemy charm = new GrowlitheEnemy(getMap1());
					charm.setPathTravelingCode(0);
					wave.add(charm);
				}
				else if (enemyGenerator >= 100 && enemyGenerator <= 100){
					MewEnemy mew = new MewEnemy(getMap1());
					mew.setPathTravelingCode(0);
					wave.add(mew);
				}
				else if (enemyGenerator >= 15 && enemyGenerator <= 19){
					KoffingEnemy koff =new KoffingEnemy(getMap1());
					koff.setPathTravelingCode(0);
					wave.add(koff);
				}
				else if (enemyGenerator >= 99 && enemyGenerator <= 100){
					RattataEnemy ratt = new RattataEnemy(getMap1());
					ratt.setPathTravelingCode(0);
					wave.add(ratt);
				}
				else if (enemyGenerator >= 99 && enemyGenerator <= 100){
					McCannEnemy doctor = new McCannEnemy(getMap1());
					doctor.setPathTravelingCode(0);
					wave.add(doctor);
				}
			}
			waveList.add(wave);
		}
		setWavesList(waveList); //Set the master wavesList inherited instance variable
	}
	
	@Override
	public void setPlayerStartingHP() {
		getPlayer1().setHealth(100);
		notifyPlayerInfoUpdated(getPlayer1().getHealthPoints(), getPlayer1().getMoney(), true);
	}

	@Override
	public void setPlayerStartingMoney() {
		getPlayer1().setMoney(1000);
		notifyPlayerInfoUpdated(getPlayer1().getHealthPoints(), getPlayer1().getMoney(), true);
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
		Map levelsMap = MapFactory.generateMap(getPlayer1(), 4);
		 //the int mapCode is 4 because this is multiplayerlevel and we want Map 4
		levelsMap.setServer(this.getServer()); //Must set the map's server so it knows to send first update
		this.setMap(levelsMap);							//and where to send updates thereafter
	}
	
}
