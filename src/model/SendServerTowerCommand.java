package model;

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
	private Tower tower;
	
	/**
	 * 
	 * @param message A message to be sent to the server for chat purposes
	 */
	public SendServerTowerCommand(Tower tower){
		this.tower = tower;
	}

	@Override
	public void execute(GameServer executeOn) {
		// TODO Auto-generated method stub
		executeOn.newTower(this.tower);
	}
}