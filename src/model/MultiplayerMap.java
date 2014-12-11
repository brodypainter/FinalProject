package model;

import java.awt.Point;
import java.util.LinkedList;

import client.Player;

public class MultiplayerMap extends Map{

	
	private static final long serialVersionUID = 495511242070790242L;

	public MultiplayerMap(Tile[][] gridDimensions,
			LinkedList<LinkedList<Point>> paths, String mapType,
			String backgroundImageURL, int mapTypeCode, Player player) {
		super(gridDimensions, paths, mapType, backgroundImageURL, mapTypeCode, player);
		
	}

}
