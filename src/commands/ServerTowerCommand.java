package commands;

import java.awt.Point;

import GUI.GameView.towerType;
import server.GameServer;

/**
 * This class is a Command used to send a Tower object from the GameClient to the GameServer. This class is used for sending
 * new towers that the client has placed to the server, from the client.
 * 
 * @author brodypainter
 *
 */
public class ServerTowerCommand extends Command<GameServer>{

	private static final long serialVersionUID = 4504506147509198509L;
	private towerType tower;
	private Point loc;
	private String name;
	
	public ServerTowerCommand(String name, towerType normal, Point loc){
		this.tower = normal;
		this.loc = loc;
		this.name = name;
	}

	@Override
	public void execute(GameServer executeOn) {
		executeOn.addTower(this.name, this.tower, this.loc);
	}
}