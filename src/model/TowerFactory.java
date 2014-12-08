package model;

import client.Player;
import GUI.GameView.towerType;
import GameController.CeruleanGym;
import GameController.Tower;

/**
 * This class contains a static method generateTower(towerType, Player)
 * that creates and returns a tower based on the given towerType passed.
 * @author Peter Hanson
 *
 */

//The Player and String Player Name seem unnecessary, Max any reason for this? -PWH

public class TowerFactory {

	
	/**
	 * A static Tower Factory method.
	 * @param type the towerType enum of the tower to be created
	 * @param player the player linked to the tower (unnecessary?)
	 * @return Tower tower, the specified Tower object
	 */
	public static Tower generateTower(towerType type, Player player){
		Tower tower;
		switch(type){
		case NORMAL:
			tower = new CeruleanGym(player.getName());
		default:
			tower = new CeruleanGym(player.getName());
		}
		return tower;
	}
}
