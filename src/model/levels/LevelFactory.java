package model.levels;

import model.Player;
import controller.GameServer;



//Created LevelFactory, use to create Level objects easily with a single type of Command object passing an int levelCode

/**
 * Use this class' static method to generate Level Objects
 * @author Peter Hanson
 *
 */
public class LevelFactory{

	
	//possibly add another parameter int difficultyCode where 0 = easy, 1 = normal, 2 = hard mode.
	/**
	 * A static method that returns a Level object created and specified by the parameters
	 * @param player the Player linked to this Level
	 * @param server the GameServer that this Level will be played on
	 * @param levelCode the Level number, determines which type of Level is generated
	 * @return
	 */
	public static Level generateLevel(Player player, GameServer server, int levelCode) {
		switch(levelCode){
		case 0:
			return new Level0(player, server);
		case 1:
			return new Level1(player, server);
		case 2:
			return new Level2(player, server);
		case 3:
			return new Level3(player, server);
		case 4: //Only for debugging
			return new LevelMultiplayer(player, server);
		default: 
			return new Level0(player, server);
		}
	}
	
}