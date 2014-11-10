package model;


import java.awt.Image;
import java.awt.Point;
import java.util.LinkedList;

import client.Player;


//This class generates Map objects with a static method. The maps it returns are based on
//the int code that you send to the method.

public class MapFactory {

	public static Map generateMap(Player player, int mapCode){
		
		Map map;
		int horizontalSize;
		int verticalSize;
		Tile[][] grid;
		LinkedList<Point> path;
		String levelDescription;
		Image backgroundImage;
		
		//The coordinate system I am using is (-y,x) with (0,0) being the top left corner in the grid
		//Point.x will return the -y value and Point.y will return the x value from a cartesian coordinate viewpoint
		
		switch(mapCode){
		case 0: //The beta test level
			
			//Set the size of the map
			horizontalSize = 5;
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
			levelDescription = "This is the beta testing level. It is a 5x5 tile map with a "
					+ "straight path across the middle.";
			
			//assign the backgroundImage for the level
			
			backgroundImage = null; //I haven't figured this out yet
			
			//instantiate a LevelXMap object that extends Map class
			map = new Level0Map(grid, path, levelDescription, backgroundImage, mapCode, player);
			break;
			
			//case other levels...
		
		default:
			map = null;
		}
		
		return map;
	}
	
	
	
}
