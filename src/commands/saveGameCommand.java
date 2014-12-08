package commands;

import server.GameServer;

public class saveGameCommand extends Command<GameServer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7494794423029088329L;

	@Override
	public void execute(GameServer executeOn) {
		executeOn.saveGame();
	}

}
