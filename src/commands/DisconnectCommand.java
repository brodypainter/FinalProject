package commands;

import server.GameServer;

/**
 * This class extends Command, and it is specified to be sent to a GameServer by a GameClient
 * 	It's purpose is to notify the Server that the client is closing the connection, so that the server can safely disconnect from the client
 * 
 * @author brodypainter
 *
 */
public class DisconnectCommand extends Command<GameServer>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3105967455662534541L;
	String name;

	public DisconnectCommand(String name) {
		this.name = name;
	}

	@Override
	public void execute(GameServer executeOn) {
		executeOn.disconnect(this.name);
	}

}
