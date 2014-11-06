package server;

import model.Command;

public class GameServer {

	
	public void execute(Command<GameServer> command){
		command.execute(this);
	}

	public void newMessage(String message) {
		// TODO Send this message to all of our clients
	}
}
