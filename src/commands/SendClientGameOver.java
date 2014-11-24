 package commands;

import client.GameClient;

public class SendClientGameOver extends Command<GameClient> {
	private boolean gameOver;
	
	public void SendClientGameOver(boolean b){
		this.gameOver = b;
	}
	
	@Override
	public void execute(GameClient executeOn) {
		executeOn.gameOver(gameOver);
	}
	
}
