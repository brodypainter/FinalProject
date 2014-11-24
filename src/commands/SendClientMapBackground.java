package commands;

import java.awt.Point;
import java.util.List;
import client.GameClient;

public class SendClientMapBackground extends Command<GameClient>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6895704955746837941L;
	String s;
	List<Point> l;
	int r;
	int c;
		
	public SendClientMapBackground(String s, List<Point> l, int r, int c){
		this.s = s;
		this.l = l;
		this.r = r;
		this.c = c;
	}
	
	@Override
	public void execute(GameClient executeOn) {
		executeOn.mapBackgroundUpdate(s, l, r, c);
	}

}
