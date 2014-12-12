package commands;

import java.awt.Point;

import GUI.GameView.towerType;
import client.GameClient;

public class ClientTowerAttack extends Command<GameClient>{
	
	private static final long serialVersionUID = 6895704955746837941L;
	towerType t;
	Point towerLoc;
	Point enemyLoc;
	boolean fromPlayer1;
		
	public ClientTowerAttack(towerType type, Point towerLocation, Point enemyLocation, boolean fromPlayer1){
		this.t = type;
		this.towerLoc = towerLocation;
		this.enemyLoc = enemyLocation;
		this.fromPlayer1 = fromPlayer1;
	}
	
	@Override
	public void execute(GameClient executeOn) {
		executeOn.towerAttack(t, towerLoc, enemyLoc, fromPlayer1);
	}

}
