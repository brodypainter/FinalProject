package model;

import java.awt.Point;
import java.util.LinkedList;

import client.Player;

/**
 * 
 * @author Max
 *
 */

public class LavaMap extends Map{
	public LavaMap(Tile[][] gridDimensions, LinkedList<LinkedList<Point>> paths, String mapType, String background, int mapTypeCode, Player player){
		super(gridDimensions, paths, mapType, background, mapTypeCode, player);	
	}
}
