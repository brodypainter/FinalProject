package commands;

import server.GameServer;
import GameController.Enemy;

public class SendServerEnemyRemoveCommand extends Command<GameServer>{
	
	private Enemy enemy;

	@Override
	public void execute(GameServer executeOn) {
		executeOn.removeEnemy(this.enemy);
		
	}

}
