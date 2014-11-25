package commands;

import client.GameClient;

public class SendClientGameOverNotify extends Command<GameClient>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5536044329610028729L;
	private boolean gameWon;
	
	public SendClientGameOverNotify(boolean gameResult){
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
