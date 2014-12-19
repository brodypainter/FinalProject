package model.levels;

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
		wave.add(new PikachuEnemy(this.getMap()));
		wave.add(new MewEnemy(this.getMap()));
		wave.add(new KoffingEnemy(this.getMap()));
		wave.add(new KoffingEnemy(this.getMap()));
		wave.add(new KoffingEnemy(this.getMap()));
		wave.add(new SquirtleEnemy(this.getMap()));
		wave.add(new SquirtleEnemy(this.getMap()));
		wave.add(new SquirtleEnemy(this.getMap()));
		wave.add(new BulbasaurEnemy(this.getMap()));
		waveList.add(wave);
		setWavesList(waveList); //Set the master wavesList inherited instance variable
	}
	
	@Override
	public void setPlayerStartingHP() {
		getPlayer().setHealth(100);
		notifyPlayerInfoUpdated(getPlayer().getHealthPoints(), getPlayer().getMoney());
	}

	@Override
	public void setPlayerStartingMoney() {
		getPlayer().setMoney(90000);
		notifyPlayerInfoUpdated(getPlayer().getHealthPoints(), getPlayer().getMoney());
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
		Map levelsMap = MapFactory.generateMap(getPlayer(), 0);
		 //the int mapCode is 0 because this is multiplayerlevel debugging and we want Map 0
		levelsMap.setServer(this.getServer()); //Must set the map's server so it knows to send first update
		super.setMap(levelsMap);							//and where to send updates thereafter
	}
	
}
