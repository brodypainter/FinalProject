package commands;

import java.util.ArrayList;
import java.util.List;

import client.GameClient;
import GUI.EnemyImage;
import GUI.TowerImage;
import GameController.Enemy;
import GameController.Tower;

/**
 * This class is a Command used to send a Tower object from the GameClient to the GameServer. This class is used for sending
 * new towers that the client has placed to the server, from the client.
 * 
 * @author brodypainter
 *
 */
public class SendClientUpdate extends Command<GameClient>{

	private static final long serialVersionUID = 4504506147509198509L;
	private List<EnemyImage> enemyImages;
	private List<TowerImage> towerImages;
	
	/**
	 * 
	 * @param message A message to be sent to the server for chat purposes
	 */
	public SendClientUpdate(ArrayList<EnemyImage> enemyImages, ArrayList<TowerImage> towerImages){
		this.enemyImages = enemyImages;
		this.towerImages = towerImages;
	}

	@Override
	public void execute(GameClient executeOn) {
		// TODO Auto-generated method stub
		executeOn.update(enemyImages, towerImages);
	}
}