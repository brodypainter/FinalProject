
package commands;

import client.GameClient;

public class SendClientGameOver extends Command<GameClient>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 66783472132893423L;

	private boolean levelWon;
	
	public SendClientGameOver(boolean result){
		levelWon = result;
	}
	
	@Override
	public void execute(GameClient executeOn) {

		if(levelWon){
			executeOn.notifyLevelWasWon();
		}else{
			executeOn.notifyLevelWasLost();
		}
	}

}