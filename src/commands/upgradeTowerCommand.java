package commands;

import java.awt.Point;

import server.GameServer;

public class upgradeTowerCommand extends Command<GameServer> {
	private Point p;
	private String s;
	/**
	 * 
	 */
	private static final long serialVersionUID = -7958835726375844840L;

	public upgradeTowerCommand(String s, Point p){
		this.s = s;
		this.p = p;
	}
	
	@Override
	public void execute(GameServer executeOn) {
		executeOn.upgradeTower(p, s);
	}

}
