package model;


import java.awt.Image;
import java.awt.Point;
import java.util.LinkedList;

import client.Player;


/**
 * This uninstantiable class generates Map objects with a static method. The Maps it 
 * returns are based on the int mapCode representing the level that you pass to the method.
 * 
 * Methods:
 * static Map generateMap(Player player, int mapCode)
 * 
 * @author Peter Hanson
 * @version 1.0
*/
public class MapFactory {

	/**
	 * This static method returns Map objects linked to a Player that are constructed
	 * according to which level, represented by the mapCode argument, the Player is on.
	 * @param player The Player object to be linked to the generated Map.
	 * @param mapCode The code, corresponding to the Level, determines the Map that is created and returned.
	 * @return map A concrete instance extending the abstract Map class and based on the mapCode level argument.
	 */
	public static Map generateMap(Player player, int mapCode){
		
		Map map;				//The Map to be constructed and returned
		int horizontalSize;		//The amount of columns to be in the Map's grid size
		int verticalSize;		//The amount of rows to be in the Map's grid size
		Tile[][] grid;			//A 2D array of Tile objects whose size is set and then is populated
		LinkedList<Point> path;	//The sequence of Points representing coordinates that enemies will follow
		String levelDescription;//A short description of the Map of the given level
		String backgroundImage;	//The URL of the background image of the map
		
		//The coordinate system I am using is (-y,x) with (0,0) being the top left corner in the grid
		//Point.x will return the -y value (rows down) and Point.y will return the x value (column) from a cartesian coordinate viewpoint
		//I know this is "backwards" but that is how 2D arrays are set up so I went with this system.
		
		switch(mapCode){
		case 0: //The beta test level
			
			//Set the size of the map
			horizontalSize = 10;
			verticalSize = 5;
			grid = new Tile[verticalSize][horizontalSize];
			
			//Populate the Tile[][] with appropriate amount of tiles
			for(int r = 0; r < verticalSize; r++){
				for(int c = 0; c < horizontalSize; c++){
					grid[r][c] = new Tile();
				}
			}
			
			//Initialize and populate path
			path = new LinkedList<Point>();
			path.add(new Point(2,0));
			path.add(new Point(2,1));
			path.add(new Point(2,2));
			path.add(new Point(2,3));
			path.add(new Point(2,4));
			//created a straight line along y = 2, x = 0 -> 4
			
			//assign the levelDesciption String
			levelDescription = "This is the beta testing level. It is a 5x10 tile map with a "
					+ "straight path across the middle.";
			
			//assign the backgroundImage for the level
			
			backgroundImage = null; //TODO:Desone, make an image of this 5 row x 10 column map with path straight across middle
									//and place the image's location here <---
			
			//instantiate a LevelXMap object that extends Map class
			map = new Level0Map(grid, path, levelDescription, backgroundImage, mapCode, player);
			break;
			
			//case other levels...
		
		default:
			map = null; //A non-existent level for mapCode was passed, should never happen
		}
		return map;
	}	
}
