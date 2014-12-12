package commands;

import server.GameServer;
import GameController.Enemy;

/**
 * This class is a Command used to send an Enemy object from the GameClient to the GameServer. This class is used for sending
 * is not used currently.
 * 
 * @author brodypainter
 *
 */
public class ServerEnemyCommand extends Command<GameServer>{

	private static final long serialVersionUID = 4504506147509198509L;
	private Enemy enemy;
	private String clientName;
	
	/**
	 * 
	 * @param message A message to be sent to the server for chat purposes
	 */
	public ServerEnemyCommand(Enemy enemy, String clientName){
		this.enemy = enemy;
		this.clientName = clientName;
	}

	@Override
	public void execute(GameServer executeOn) {
		// TODO addEnemy in Server
		executeOn.addEnemy(this.enemy, clientName);
	}
}