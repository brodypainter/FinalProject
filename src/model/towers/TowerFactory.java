package model.towers;

import GUI.GameView.towerType;

/**
 * This class contains a static method generateTower(towerType, Player)
 * that creates and returns a tower based on the given towerType passed.
 * @author Peter Hanson
 *
 * Edits 12/8 by Max Justice the other types were added
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
	public static Tower generateTower(towerType type){
		Tower tower;
		switch(type){
		case NORMAL:
			tower = new CuboneTower();
			break;
		case WATER:
			tower = new PoliwagTower();
			break;
		case ELECTRIC:
			tower = new MagnemiteTower();
			break;
		case GRASS:
			tower = new OddishTower();
			break;
		case PSYCHIC:
			tower = new AbraTower();
			break;
		case POISON:
			tower = new GhastlyTower();
			break;
		case FIRE:
			tower = new CharmanderTower();
			break;
		case MEWTWO:
			tower = new MewtwoTower();
			break;
		default:
			tower = new CuboneTower();
			break;
		}
		return tower;
	}
}
