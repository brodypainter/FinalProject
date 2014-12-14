package commands;

import client.GameClient;

public class YouArePlayer1Command extends Command<GameClient> {
	boolean p1;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7025141278681491239L;

	public YouArePlayer1Command(boolean p1) {
		this.p1 = p1;
	}

	@Override
	public void execute(GameClient executeOn) {
		executeOn.setIsPlayer1(p1);
	}

}
