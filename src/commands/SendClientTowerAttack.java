package commands;

import GameController.Enemy;
import GameController.Tower;
import client.GameClient;

public class SendClientTowerAttack extends Command<GameClient>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6895704955746837941L;
	Tower t;
	Enemy e;
		
	public SendClientTowerAttack(Tower t, Enemy e){
		this.t = t;
		this.e = e;
	}
	
	@Override
	public void execute(GameClient executeOn) {
		//TODO need to execute on something in the game client
		//executeOn.towerAttack(t, e);
	}

}
