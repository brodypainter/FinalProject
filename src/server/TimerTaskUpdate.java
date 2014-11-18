package server;

import java.util.TimerTask;

public class TimerTaskUpdate extends TimerTask{

	GameServer server; //The server
	
	public TimerTaskUpdate(GameServer server){
		this.server = server;
	}
	
	@Override
	public void run() {
		server.tickClients();
	}

}
