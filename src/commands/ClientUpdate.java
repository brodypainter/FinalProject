package commands;

import java.util.ArrayList;
import java.util.List;

import client.GameClient;
import GUI.EnemyImage;
import GUI.TowerImage;

/**
 * This class is a Command used to send a Tower object from the GameClient to the GameServer. This class is used for sending
 * new towers that the client has placed to the server, from the client.
 * 
 * @author brodypainter
 *
 */
public class ClientUpdate extends Command<GameClient>{

	private static final long serialVersionUID = 4504506147509198509L;
	
	private List<EnemyImage> enemyImages;
	private List<TowerImage> towerImages;
	private boolean fromPlayer1;

	
	/**
	 * 
	 * @param fromPlayer1 
	 * @param message A message to be sent to the server for chat purposes
	 */

	public ClientUpdate(ArrayList<EnemyImage> enemyImages, ArrayList<TowerImage> towerImages, boolean fromPlayer1){
		this.enemyImages = enemyImages;
		this.towerImages = towerImages;
		this.fromPlayer1 = fromPlayer1;
	}

	@Override
	public void execute(GameClient executeOn) {
		// TODO Auto-generated method stub
		executeOn.update(enemyImages, towerImages, fromPlayer1);
	}
}