package model;

import java.util.ArrayList;

import GameController.Enemy;
import GameController.Pikachu;
import server.GameServer;
import client.Player;


//created Level0

public class Level0 extends Level{
	
	private int countDown = 180;
	
	ArrayList<Object> waveList;

	public Level0(Player player, GameServer server) {
		super(player, server);
		waveList = new ArrayList<Object>();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void levelSpecificSetup() {
		map = MapFactory.generateMap(player, 0);
		
	}

	@Override
	public void levelStart() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 
	 */
	public void createWaves( ){
		for (int i = 0; i < 3; i++){
			ArrayList<Enemy> wave = new ArrayList<Enemy>();
			for (int j = 0; j < 20; j++){
				Pikachu pika = new Pikachu(getMap());
				wave.add(pika);
			}
			waveList.add(wave);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean gameOver(){
		if(this.getPlayer().getHealthPoints() < 0){
			youLose();
			return true;
		}else if (waveList.isEmpty() && this.getPlayer().getHealthPoints() <= 0){
			youWin();
			return true;
		}
		return false;
	}
	
	public boolean youLose(){
		
		return false;
	}
	
	public boolean youWin(){
		
		return false;
	}
	
}

