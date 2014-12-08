package commands;

import server.GameServer;

public class playPauseCommand extends Command<GameServer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2910464328994418301L;

	@Override
	public void execute(GameServer executeOn) {
		executeOn.playPauseGame();
		
	}

}
