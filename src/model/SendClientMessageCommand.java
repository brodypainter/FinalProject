package model;

import client.GameClient;

public class SendClientMessageCommand extends Command<GameClient>{

	private static final long serialVersionUID = 4413135669815516633L;
	private String message;
	
	/**
	 * 
	 * @param message A message to be sent to the server for 
	 */
	public SendClientMessageCommand(String message){
		this.message = message;
	}

	@Override
	public void execute(GameClient executeOn) {
		executeOn.newMessage(this.message);
	}
}