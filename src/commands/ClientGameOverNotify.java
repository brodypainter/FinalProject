package commands;

import client.GameClient;

public class ClientGameOverNotify extends Command<GameClient>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5536044329610028729L;
	private boolean gameWon;
	
	public ClientGameOverNotify(boolean gameResult){
		this.gameWon = gameResult;
	}
	
	@Override
	public void execute(GameClient executeOn) {
		if(gameWon){
			executeOn.notifyLevelWasWon();
		}else{
			executeOn.notifyLevelWasLost();
		}
	}
}
