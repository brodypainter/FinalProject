package commands;

import server.GameServer;

public class playPauseCommand extends Command<GameServer> {

	@Override
	public void execute(GameServer executeOn) {
		executeOn.playPauseGame();
		
	}

}
