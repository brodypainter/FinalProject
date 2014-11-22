package commands;

import server.GameServer;
import GameController.Tower;

public class SendServerTowerRemoveCommand extends Command<GameServer>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1163276526057672705L;
	private Tower t;
	
	public SendServerTowerRemoveCommand(Tower t){
		this.t = t;
	}
	
	@Override
	public void execute(GameServer executeOn) {
		executeOn.removeTower(t);
	}

}
