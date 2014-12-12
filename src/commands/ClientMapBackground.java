package commands;

import java.awt.Point;
import java.util.LinkedList;

import client.GameClient;

public class ClientMapBackground extends Command<GameClient>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6895704955746837941L;
	String s;
	LinkedList<LinkedList<Point>> l;
	int r;
	int c;
	private boolean fromPlayer1;
		
	public ClientMapBackground(String s, LinkedList<LinkedList<Point>> paths, int r, int c, boolean fromPlayer1){
		this.s = s;
		this.l = paths;
		this.r = r;
		this.c = c;
		this.fromPlayer1 = fromPlayer1;
	}
	
	@Override
	public void execute(GameClient executeOn) {
		executeOn.mapBackgroundUpdate(s, l, r, c, fromPlayer1);
	}

}
