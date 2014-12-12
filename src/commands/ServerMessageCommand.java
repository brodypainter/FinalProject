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
	private String name, message;
	
	/**
	 * 
	 * @param message A message to be sent to the server for chat purposes
	 * @param message2 
	 */
	public ServerMessageCommand(String name, String message){
		this.name = name;
		this.message = message;
	}

	@Override
	public void execute(GameServer executeOn) {
		executeOn.newMessage(this.message, this.name);
	}
}