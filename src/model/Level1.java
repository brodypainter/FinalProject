package model;

import java.io.Serializable;
import java.util.ArrayList;

import GameController.Enemy;
import GameController.Pikachu;
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
		// TODO Auto-generated method stub
		ArrayList<ArrayList<Enemy>> waveList = new ArrayList<ArrayList<Enemy>>(); //A temporary 2D array list of Enemy
		for (int i = 0; i < 10; i++){
			ArrayList<Enemy> wave = new ArrayList<Enemy>();
			for (int j = 0; j < 4; j++){
				Pikachu pika = new Pikachu(getMap());
				wave.add(pika);
			}
			waveList.add(wave);
		}
		setWavesList(waveList);
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
		setWaveIntervals(15000L); //15 seconds between the last and first enemy of 2 successive waves
	}

	@Override
	public void setEnemySpawnDelayIntervals() {
		setEnemySpawnIntervals(2000L); //2 second between each enemy spawning in a wave
	}

	@Override
	public void setMap() {
		Map levelsMap = MapFactory.generateMap(getPlayer(), 0);
		 //the int mapCode is 0 because this is level 0 and we want Map 0
		levelsMap.setServer(this.getServer()); //Must set the map's server so it knows to send first update
		this.setMap(levelsMap);							//and where to send updates thereafter
	}

}
