package model;

import java.awt.Point;
import java.util.LinkedList;

import client.Player;

public class WaterMap extends Map{
	
	public WaterMap(Tile[][] gridDimensions, LinkedList<LinkedList<Point>> paths, String mapType, String background, int mapTypeCode, Player player){
		super(gridDimensions, paths, mapType, background, mapTypeCode, player);
	}

	@Override
	int getNumberOfPaths() {
		// TODO Auto-generated method stub
		return 2;
	}

}
