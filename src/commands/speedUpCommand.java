package commands;

import server.GameServer;

public class speedUpCommand extends Command<GameServer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7779491686258206829L;

	@Override
	public void execute(GameServer executeOn) {
		executeOn.speedUp();
	}

}
