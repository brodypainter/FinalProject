package model.maps;

import java.awt.Point;
import java.util.LinkedList;

import model.Player;

public class MiscMap extends Map{

	
	private static final long serialVersionUID = 495511242070790242L;

	public MiscMap(Tile[][] gridDimensions,
			LinkedList<LinkedList<Point>> paths, String mapType,
			String backgroundImageURL, int mapTypeCode, Player player) {
		super(gridDimensions, paths, mapType, backgroundImageURL, mapTypeCode, player);
		
	}

	@Override
	public int getNumberOfPaths() {
		// TODO Auto-generated method stub
		return 1;
	}

}
