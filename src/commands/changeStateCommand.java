package commands;

import client.GameClient;

public class changeStateCommand extends Command<GameClient> {
	private Boolean paused, fast;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7338700534198990051L;

	public changeStateCommand(Boolean paused, Boolean fast){
		this.paused = paused;
		this.fast = fast;
	}
	
	@Override
	public void execute(GameClient executeOn) {
		executeOn.changedSpeedState(paused, fast);
	}

}
