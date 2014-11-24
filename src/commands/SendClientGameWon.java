package commands;

import client.GameClient;

public class SendClientGameWon extends Command<GameClient> {

	public SendClientGameWon(){
		 
	}
	
	@Override
	public void execute(GameClient executeOn) {
		executeOn.gameWon();
	}

}
