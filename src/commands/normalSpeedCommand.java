package commands;

import server.GameServer;

public class normalSpeedCommand extends Command<GameServer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6206599354892983321L;

	@Override
	public void execute(GameServer executeOn) {
		executeOn.normalSpeed();
	}

}
