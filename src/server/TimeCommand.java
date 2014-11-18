package server;

import model.Command;
import client.GameClient;

/**
 * This class extends Command, and it is specified to be sent to a GameClient by a GameServer
 * every time the Timer ticks (20 ms)
 * 	
 * 
 * @author brodypainter
 *
 */
public class TimeCommand extends Command<GameClient>{

	private static final long serialVersionUID = 3616301702607884736L;

	@Override
	public void execute(GameClient executeOn) {
		// TODO Auto-generated method stub
		
	}

}
