package server;

import java.util.TimerTask;

/**
 * The TimerTask given to the GameServer's Timer, calls the server to tick the Model
 * which causes the game logic to run.
 * @author Peter Hanson
 *
 */
public class TimerTaskUpdate extends TimerTask{

	GameServer server; //The server
	
	public TimerTaskUpdate(GameServer server){
		this.server = server;
	}
	
	@Override
	public void run() {
		server.tickModel();
	}

}
