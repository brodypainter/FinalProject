package commands;

import java.util.List;

import client.GameClient;
import GameController.Enemy;
import GameController.Tower;

/**
 * This class is a Command used to send a Tower object from the GameClient to the GameServer. This class is used for sending
 * new towers that the client has placed to the server, from the client.
 * 
 * @author brodypainter
 *
 */
public class SendClientUpdate extends Command<GameClient>{

	private static final long serialVersionUID = 4504506147509198509L;
	private List<Enemy> enemyList;
	private List<Tower> towerList;
	
	/**
	 * 
	 * @param message A message to be sent to the server for chat purposes
	 */
	public SendClientUpdate(List<Enemy> enemyList, List<Tower> towerList){
		this.enemyList = enemyList;
		this.towerList = towerList;
	}

	@Override
	public void execute(GameClient executeOn) {
		// TODO Auto-generated method stub
		executeOn.update(enemyList, towerList);
	}
}