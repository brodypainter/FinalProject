package commands;

import java.awt.Point;

import server.GameServer;

public class ServerTowerRemoveCommand extends Command<GameServer>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1163276526057672705L;
	private String name;
	private Point p;
	
	public ServerTowerRemoveCommand(String name, Point p){
		this.name = name;
		this.p = p;
	}
	
	@Override
	public void execute(GameServer executeOn) {
		executeOn.sellTower(this.name, this.p);
		//should pass just a point, not a tower -PH
	}

}
