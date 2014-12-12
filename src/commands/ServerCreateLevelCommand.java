package commands;

import server.GameServer;

/**
 * This class is a Command used to send a Tower object from the GameClient to the GameServer. This class is used for sending
 * new towers that the client has placed to the server, from the client.
 * 
 * @author brodypainter
 *
 */
public class ServerCreateLevelCommand extends Command<GameServer>{

	private static final long serialVersionUID = 4504506147509198509L;
	private int levelCode;
	
	/**
	 * 
	 * @param message A message to be sent to the server for chat purposes
	 */
	public ServerCreateLevelCommand(int i){
		this.levelCode = i;
	}

	@Override
	public void execute(GameServer executeOn) {
		executeOn.createLevel(this.levelCode);
	}
}