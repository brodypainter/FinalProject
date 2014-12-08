package commands;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import client.GameClient;

public class SendClientMapBackground extends Command<GameClient>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6895704955746837941L;
	String s;
	LinkedList<LinkedList<Point>> l;
	int r;
	int c;
		
	public SendClientMapBackground(String s, LinkedList<LinkedList<Point>> paths, int r, int c){
		this.s = s;
		this.l = paths;
		this.r = r;
		this.c = c;
	}
	
	@Override
	public void execute(GameClient executeOn) {
		executeOn.mapBackgroundUpdate(s, l, r, c);
	}

}
