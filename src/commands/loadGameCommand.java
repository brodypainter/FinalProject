package commands;

import server.GameServer;

public class loadGameCommand extends Command<GameServer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4720835393874240806L;

	@Override
	public void execute(GameServer executeOn) {
		executeOn.loadGame();
	}

}
