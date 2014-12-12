package commands;

import server.GameServer;

public class JoinMultiplayerCommand extends Command<GameServer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3206445856532855200L;
	private	String name;
	
	public JoinMultiplayerCommand(String name) {
		this.name = name;
	}

	@Override
	public void execute(GameServer executeOn) {
		executeOn.joinMultiplayer(this.name);
	}

}
