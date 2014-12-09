package model;

import client.Player;
import GUI.GameView.towerType;
import GameController.AbraTower;
import GameController.CuboneTower;
import GameController.GhastlyTower;
import GameController.MagnemiteTower;
import GameController.MewtwoTower;
import GameController.OddishTower;
import GameController.PoliwagTower;
import GameController.Tower;

/**
 * This class contains a static method generateTower(towerType, Player)
 * that creates and returns a tower based on the given towerType passed.
 * @author Peter Hanson
 *
 */

//The Player and String Player Name seem unnecessary, Max any reason for this? -PWH

public class TowerFactory {

	//NORMAL,WATER,ELECTRIC,GRASS,POISON,PSYCHIC,FIRE,MEWTWO
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
			tower = new CuboneTower(player.getName());
			break;
		case WATER:
			tower = new PoliwagTower(player.getName());
			break;
		case ELECTRIC:
			tower = new MagnemiteTower(player.getName());
			break;
		case GRASS:
			tower = new OddishTower(player.getName());
			break;
		case PSYCHIC:
			tower = new GhastlyTower(player.getName());
			break;
		case FIRE:
			tower = new AbraTower(player.getName());
			break;
		case MEWTWO:
			tower = new MewtwoTower(player.getName());
			break;
		default:
			tower = new CuboneTower(player.getName());
			break;
		}
		return tower;
	}
}
