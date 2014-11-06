package model;

import server.GameServer;

public class SendServerMessageCommand extends Command<GameServer>{

	private static final long serialVersionUID = 4504506147509198509L;
	private String message;
	
	/**
	 * 
	 * @param message A message to be sent to the server for 
	 */
	public SendServerMessageCommand(String message){
		this.message = message;
	}

	@Override
	public void execute(GameServer executeOn) {
		// TODO Auto-generated method stub
		executeOn.newMessage(this.message);
	}
}