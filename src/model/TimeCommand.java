package model;

import client.GameClient;

/**
 * This class extends Command, and it is specified to be sent to a GameServer by a GameClient
 * 	It's purpose is to notify the Server that the client is closing the connection, so that the server can safely disconnect from the client
 * 
 * @author brodypainter
 *
 */
public class TimeCommand extends Command<GameClient>{

	private static final long serialVersionUID = 3616301702607884736L;

	@Override
	public void execute(GameClient executeOn) {
		// TODO Auto-generated method stub
		
	}

}
