package commands;

import client.GameClient;

public class ClientGameWon extends Command<GameClient> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5811457192403143928L;

	public ClientGameWon(){
		 
	}
	
	@Override
	public void execute(GameClient executeOn) {
		executeOn.notifyLevelWasWon();
	}

}
