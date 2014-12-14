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
 * May be unnecessary after changes i just made ignore for now.
 * Only using as a testing/debugging level currently.
 * @author Peter Hanson
 *
 */
public class LevelMultiplayer extends Level {

	
	private static final long serialVersionUID = -4356179074259053436L;
	private static final int numbOfWaves = 3;
	
	public LevelMultiplayer(Player player, GameServer server) {
		super(player, server);
	}
	


	
	// enemies for the first level is pikachu, mew
	public void createWaves(){
		ArrayList<ArrayList<Enemy>> waveList = new ArrayList<ArrayList<Enemy>>();
		ArrayList<Enemy> wave = new ArrayList<Enemy>();	
		wave.add(new PikachuEnemy(this.getMap1()));
		wave.add(new MewEnemy(this.getMap1()));
		waveList.add(wave);
		setWavesList(waveList); //Set the master wavesList inherited instance variable
	}
	
	@Override
	public void setPlayerStartingHP() {
		getPlayer1().setHealth(100);
		notifyPlayerInfoUpdated(getPlayer1().getHealthPoints(), getPlayer1().getMoney(), true);
	}

	@Override
	public void setPlayerStartingMoney() {
		getPlayer1().setMoney(90000);
		notifyPlayerInfoUpdated(getPlayer1().getHealthPoints(), getPlayer1().getMoney(), true);
	}

	@Override
	public void setWaveDelayIntervals() {
		setWaveIntervals(5000L); //5 seconds between the last and first enemy of 2 successive waves
	}

	@Override
	public void setEnemySpawnDelayIntervals() {
		setEnemySpawnIntervals(3000L); //3 second between each enemy spawning in a wave
	}

	@Override
	public void setMap() {
		Map levelsMap = MapFactory.generateMap(getPlayer1(), 0);
		 //the int mapCode is 0 because this is multiplayerlevel debugging and we want Map 0
		levelsMap.setServer(this.getServer()); //Must set the map's server so it knows to send first update
		super.setMap1(levelsMap);							//and where to send updates thereafter
	}
	
}
