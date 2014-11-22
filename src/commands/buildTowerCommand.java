package commands;

import java.awt.Point;

import client.GameClient;
import GUI.GameView;
import GUI.GameView.towerType;
import GameController.Tower;

public class buildTowerCommand extends Command<GameClient>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6931755999091078880L;
	Point loc;
	towerType type;
	
	public buildTowerCommand(towerType type, Point loc)
	{
		this.loc = loc;
		this.type = type;
	}
	
	@Override
	public void execute(GameClient executeOn)
	{
		switch(type)
		{
		case NORMAL:
			executeOn.addTower(new Tower("testTower", 3, 3, 1, "testPlayer", "/images/cuboneStatic.png"));
			break;
		default:
			break;
		}
		
	}

}
