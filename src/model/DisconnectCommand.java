package model;

import server.GameServer;

/**
 * This class extends Command, and it is specified to be sent to a GameServer by a GameClient
 * 	It's purpose is to notify the Server that the client is closing the connection, so that the server can safely disconnect from the client
 * 
 * @author brodypainter
 *
 */
public class DisconnectCommand extends Command<GameServer>{

	@Override
	public void execute(GameServer executeOn) {
		// TODO Auto-generated method stub
		
	}

}
