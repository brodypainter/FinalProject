package model;

import client.GameClient;


/**
 * This class is a Command used to send a Tower object from the GameServer to the GameClient. This class is used for sending
 * new towers in the server to the client, from the server.
 * 
 * @author brodypainter
 *
 */
public class SendClientTowerCommand extends Command<GameClient>{

	private static final long serialVersionUID = 4413135669815516633L;
	private String message;
	
	/**
	 * 
	 * @param message A message to be sent to the server for 
	 */
	public SendClientTowerCommand(String message){
		this.message = message;
	}

	/**
	 * 
	 * @param executeOn The Client that is getting the message
	 */
	@Override
	public void execute(GameClient executeOn) {
		executeOn.newMessage(this.message);
	}
}