package model.maps;

import java.awt.Point;
import java.util.LinkedList;

import model.Player;

public class WaterMap extends Map{
	
	private static final long serialVersionUID = -1097512901820509329L;

	public WaterMap(Tile[][] gridDimensions, LinkedList<LinkedList<Point>> paths, String mapType, String background, int mapTypeCode, Player player){
		super(gridDimensions, paths, mapType, background, mapTypeCode, player);
	}

	@Override
	public int getNumberOfPaths() {
		// TODO Auto-generated method stub
		return 2;
	}

}
