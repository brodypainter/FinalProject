package commands;

import java.util.LinkedList;

import client.GameClient;


/**
 * This class is a Command used to send a text String from the GameServer to the GameClient. This class is used for sending
 * chat messages and game status updates to the client, from the server.
 * 
 * @author brodypainter
 *
 */
public class ClientMessageCommand extends Command<GameClient>{

	private static final long serialVersionUID = 4413135669815516633L;
	private LinkedList<String> messages;
	
	/**
	 * 
	 * @param messages A message to be sent to the server for 
	 */
	public ClientMessageCommand(LinkedList<String> messages){
		this.messages = messages;
	}

	/**
	 * 
	 * @param executeOn The Client that is getting the message
	 */
	@Override
	public void execute(GameClient executeOn) {
		executeOn.updateMessages(this.messages);
	}
}