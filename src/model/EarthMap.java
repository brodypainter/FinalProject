package model;


import java.awt.Point;
import java.util.LinkedList;
import client.Player;

/**
 * This class creates Level0Map objects which are just concrete extensions of the
 * abstract Map class specifically designed to be the beta testing level 0 map.
 * They are by default 5x10 grids with the enemy path straight across the middle going left to right.
 * This class should be used to instantiate Map objects for basic testing and displays.
 * Once it is working it could be kept as a tutorial level.
 * Create this class easier by calling the static method in MapFactory class and
 * passing the int argument mapCode as 0 since this is level 0.
 * 
 * @author Peter Hanson
 * @version 1.0
 */

public class EarthMap extends Map{
	
	private static final long serialVersionUID = 8007542926289934852L;

	public EarthMap(Tile[][] gridDimensions, LinkedList<LinkedList<Point>> paths, String mapType, String background, int mapTypeCode, Player player){
		super(gridDimensions, paths, mapType, background, mapTypeCode, player);	
	}
}
