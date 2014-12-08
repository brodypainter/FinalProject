package commands;

import server.GameServer;

/**
 * This class is a Command used to send a text String from the GameClient to the GameServer. This class is used for sending
 * chat messages to the server, from the client.
 * 
 * @author brodypainter
 *
 */
public class ServerMessageCommand extends Command<GameServer>{

	private static final long serialVersionUID = 4504506147509198509L;
	private String message;
	
	/**
	 * 
	 * @param message A message to be sent to the server for chat purposes
	 */
	public ServerMessageCommand(String message){
		this.message = message;
	}

	@Override
	public void execute(GameServer executeOn) {
		// TODO Auto-generated method stub
		executeOn.newMessage(this.message);
	}
}