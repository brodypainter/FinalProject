package commands;

import java.awt.Point;

import GUI.GameView.towerType;
import client.GameClient;

public class SendClientTowerAttack extends Command<GameClient>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6895704955746837941L;
	towerType t;
	Point towerLoc;
	Point enemyLoc;
		
	public SendClientTowerAttack(towerType type, Point towerLocation, Point enemyLocation){
		this.t = type;
		this.towerLoc = towerLocation;
		this.enemyLoc = enemyLocation;
	}
	
	@Override
	public void execute(GameClient executeOn) {
		executeOn.towerAttack(t, towerLoc, enemyLoc);
	}

}
