package model;

import java.awt.Image;
import java.awt.Point;
import java.util.LinkedList;
import client.Player;

/**
 * This class creates Level0Map objects which are just concrete extensions of the
 * abstract Map class specifically designed to be the beta testing level 0 map.
 * They are by default 5x5 grids with the enemy path straight across the middle going left to right.
 * This class should be used to instantiate Map objects for basic testing and displays.
 * Once it is working it could be kept as a tutorial level.
 * Create this class easier by calling the static method in MapFactory class and
 * passing the int argument mapCode as 0 since this is level 0.
 * 
 * @author Peter Hanson
 * @version 1.0
 */

public class Level0Map extends Map{
	
	private static final long serialVersionUID = 8007542926289934852L;

	public Level0Map(Tile[][] gridDimensions, LinkedList<Point> path, String mapType, String background, int mapTypeCode, Player player){
		super(gridDimensions, path, mapType, background, mapTypeCode, player);	
	}

	public Level0Map() {
		super();
	}
}
