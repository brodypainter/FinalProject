package commands;

import server.GameServer;
import GameController.Enemy;

/**
 * This class is a Command used to send a Tower object from the GameClient to the GameServer. This class is used for sending
 * new towers that the client has placed to the server, from the client.
 * 
 * @author brodypainter
 *
 */
public class SendServerCreateLevelCommand extends Command<GameServer>{

	private static final long serialVersionUID = 4504506147509198509L;
	private int levelCode;
	
	/**
	 * 
	 * @param message A message to be sent to the server for chat purposes
	 */
	public SendServerCreateLevelCommand(int i){
		this.levelCode = i;
	}

	@Override
	public void execute(GameServer executeOn) {
		// TODO addEnemy in Server
		executeOn.createLevel(this.levelCode);
	}
}