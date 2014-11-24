package commands;

import java.awt.Point;

import GUI.GameView.towerType;
import GameController.Tower;
import server.GameServer;

/**
 * This class is a Command used to send a Tower object from the GameClient to the GameServer. This class is used for sending
 * new towers that the client has placed to the server, from the client.
 * 
 * @author brodypainter
 *
 */
public class SendServerTowerCommand extends Command<GameServer>{

	private static final long serialVersionUID = 4504506147509198509L;
	private towerType tower;
	private Point loc;
	
	public SendServerTowerCommand(towerType normal, Point loc){
		this.tower = normal;
		this.loc = loc;
	}

	@Override
	public void execute(GameServer executeOn) {
		executeOn.addTower(this.tower, this.loc);
	}
}