package commands;

import java.awt.Point;
import java.util.List;

import GameController.Enemy;
import GameController.Tower;
import client.GameClient;

public class SendClientMapBackground extends Command<GameClient>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6895704955746837941L;
	String s;
	List<Point> l;
		
	public SendClientMapBackground(String s, List<Point> l){
		this.s = s;
		this.l = l;
	}
	
	@Override
	public void execute(GameClient executeOn) {
		//TODO need to execute on something in the game client
		//executeOn.mapBackgroundUpdate(s, l);
	}

}
