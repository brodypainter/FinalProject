package model;


import java.awt.Point;
import java.util.LinkedList;
import client.Player;

/**
 * This class creates EarthMap objects which are just concrete extensions of the
 * abstract Map class. They are by default 10x15 grids with the enemy path.
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
