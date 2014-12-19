package model.maps;

import java.awt.Point;
import java.util.LinkedList;

import model.Player;

public class CaveMap extends Map {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7210711616572357881L;

	public CaveMap(Tile[][] gridDimensions, LinkedList<LinkedList<Point>> paths, String mapType, String background, int mapTypeCode, Player player){
		super(gridDimensions, paths, mapType, background, mapTypeCode, player);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getNumberOfPaths() {
		return 1;
	}

}
