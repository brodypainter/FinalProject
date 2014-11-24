
package commands;

import client.GameClient;

public class SendClientGameLost extends Command<GameClient>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 66783472132893423L;

	private boolean levelWon;
	
	public SendClientGameLost(){
	}
	
	@Override
	public void execute(GameClient executeOn) {
		executeOn.notifyLevelWasLost();
	}

}