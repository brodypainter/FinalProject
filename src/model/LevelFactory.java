package model;

import server.GameServer;
import client.Player;



//Created LevelFactory, use to create Level objects easily with a single type of Command object passing an int levelCode

public class LevelFactory{

	
	//possibly add another parameter int difficultyCode where 0 = easy, 1 = normal, 2 = hard mode.
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
		default: 
			return new Level0(player, server);
		}
	}
	
}