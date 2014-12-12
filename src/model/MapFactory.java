package model;


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
		LinkedList<LinkedList<Point>> paths;	//The sequence of Points representing coordinates that enemies will follow, holds possibly multiple paths
		String levelDescription;//A short description of the Map of the given level
		String backgroundImage;	//The URL of the background image of the map
		
		//The coordinate system I am using is (-y,x) with (0,0) being the top left corner in the grid
		//Point.x will return the -y value (rows down) and Point.y will return the x value (column) from a cartesian coordinate viewpoint
		//I know this is "backwards" but that is how 2D arrays are set up so I went with this system.
		
		switch(mapCode){
		case 0: //The EarthMap
			
			//Set the size of the map
			horizontalSize = 15;
			verticalSize = 10;
			grid = new Tile[verticalSize][horizontalSize];
			
			//Populate the Tile[][] with appropriate amount of tiles
			for(int r = 0; r < verticalSize; r++){
				for(int c = 0; c < horizontalSize; c++){
					grid[r][c] = new Tile();
				}
			}
			
			//Initialize and populate paths
			LinkedList<Point> path = new LinkedList<Point>();
			path.add(new Point(2,0));
			path.add(new Point(2,1));
			path.add(new Point(3,1));
			path.add(new Point(4,1));
			path.add(new Point(5,1));
			path.add(new Point(6,1));
			path.add(new Point(6,2));
			path.add(new Point(6,3));
			path.add(new Point(6,4));
			path.add(new Point(6,5));
			/*path.add(new Point(5,5));
			path.add(new Point(4,5));
			path.add(new Point(3,5));
			path.add(new Point(2,5));*/
			path.add(new Point(6,6));
			path.add(new Point(6,7));
			path.add(new Point(6,8));
			path.add(new Point(6,9));
			path.add(new Point(6,10));
			path.add(new Point(6,11));
			path.add(new Point(6,12));
			path.add(new Point(6,13));
			path.add(new Point(6,14));
			
			//add (all) newly created enemy paths to the paths 2D list
			paths = new LinkedList<LinkedList<Point>>();
			paths.add(path);
			
			//assign the levelDesciption String
			levelDescription = "This is the EarthMap. It is a 10x15 tile map";
			
			//assign the backgroundImage for the level
			backgroundImage = "/images/Cerulean_Gym.png"; //TODO:Rename this image file to EarthMap and make sure the path graphics match path coords
			
			//instantiate a XMap object that extends Map class
			map = new EarthMap(grid, paths, levelDescription, backgroundImage, mapCode, player);
			break;
			//TODO: Max finish making case 1 and 2 for WaterMap and LavaMap, give one multiple paths
		case 1:
			
			//Set the size of the map
			horizontalSize = 30;
			verticalSize = 20;
			grid = new Tile[verticalSize][horizontalSize];
			
			//Populate the Tile[][] with appropriate amount of tiles
			for(int r = 0; r < verticalSize; r++){
				for(int c = 0; c < horizontalSize; c++){
					grid[r][c] = new Tile();
				}
			}
			/* 	  0 1 2 3 4 5 6 7 8 9
			 * 	# # # # # # # # # # #
			 *0	#   @ @ @ @ @
			 *1	#   @ @     @     @ @
			 *2	#     @     @     @
			 *3	# @ @ @     @     @
			 *4	#           @ @ @ @
			 */
			//Initialize and populate path point r,c
			path = new LinkedList<Point>();
			path.add(new Point(0,2));
			path.add(new Point(1,2));
			path.add(new Point(2,2));
			path.add(new Point(3,2));
			path.add(new Point(4,2));
			path.add(new Point(5,2));
			path.add(new Point(6,2));
			path.add(new Point(7,2));
			path.add(new Point(7,3));
			path.add(new Point(7,4));
			path.add(new Point(7,5));
			path.add(new Point(7,6));
			path.add(new Point(7,7));
			path.add(new Point(6,7));
			path.add(new Point(5,7));
			path.add(new Point(5,6));//MAYBE ERROR HERE
			path.add(new Point(4,6));
			path.add(new Point(3,6));
			path.add(new Point(2,6));
			path.add(new Point(2,7));
			path.add(new Point(2,8));
			path.add(new Point(2,9));
			path.add(new Point(2,10));
			path.add(new Point(2,11));
			path.add(new Point(3,11));
			path.add(new Point(4,11));
			path.add(new Point(5,11));
			path.add(new Point(5,10));
			path.add(new Point(6,10));
			path.add(new Point(7,10));
			path.add(new Point(8,10));
			path.add(new Point(9,10));
			path.add(new Point(10,10));
			path.add(new Point(10,11));
			path.add(new Point(10,12));
			path.add(new Point(10,13));
			path.add(new Point(10,14));
			path.add(new Point(10,15));
			path.add(new Point(9,15));
			path.add(new Point(8,15));
			path.add(new Point(7,15));
			path.add(new Point(6,15));
			path.add(new Point(5,15));
			path.add(new Point(4,15));
			path.add(new Point(4,16));
			path.add(new Point(4,17));
			path.add(new Point(4,18));
			path.add(new Point(4,19));
			path.add(new Point(4,20));
			path.add(new Point(4,21));
			path.add(new Point(4,22));
			path.add(new Point(4,23));
			path.add(new Point(4,24));
			path.add(new Point(4,25));
			path.add(new Point(5,25));
			path.add(new Point(6,25));
			path.add(new Point(7,25));
			path.add(new Point(8,25));
			path.add(new Point(9,25));
			path.add(new Point(10,25));
			path.add(new Point(10,26));
			path.add(new Point(10,27));
			path.add(new Point(10,28));
			path.add(new Point(11,28));
			path.add(new Point(12,28));
			path.add(new Point(13,28));
			path.add(new Point(14,28));
			path.add(new Point(15,28));
			path.add(new Point(16,28));
			path.add(new Point(16,27));
			path.add(new Point(16,26));
			path.add(new Point(16,25));
			path.add(new Point(16,22));
			path.add(new Point(16,21));
			path.add(new Point(15,21));
			path.add(new Point(14,21));
			path.add(new Point(13,21));
			path.add(new Point(12,21));
			path.add(new Point(11,21));
			path.add(new Point(11,20));
			path.add(new Point(11,19));
			path.add(new Point(11,18));
			path.add(new Point(12,18));
			path.add(new Point(13,18));
			path.add(new Point(14,18));
			path.add(new Point(15,18));
			path.add(new Point(16,18));
			path.add(new Point(17,18));
			path.add(new Point(18,18));
			path.add(new Point(18,17));
			path.add(new Point(18,16));
			path.add(new Point(18,15));
			path.add(new Point(18,14));
			path.add(new Point(18,13));
			path.add(new Point(18,12));
			path.add(new Point(17,12));
			path.add(new Point(16,12));
			path.add(new Point(15,12));
			path.add(new Point(14,12));
			path.add(new Point(14,11));
			path.add(new Point(14,10));
			path.add(new Point(14,9));
			path.add(new Point(14,8));
			path.add(new Point(14,7));
			path.add(new Point(13,7));
			path.add(new Point(12,7));
			path.add(new Point(11,7));
			path.add(new Point(11,6));
			path.add(new Point(11,5));
			path.add(new Point(11,4));
			path.add(new Point(11,3));
			path.add(new Point(12,3));
			path.add(new Point(13,3));
			path.add(new Point(15,3));
			path.add(new Point(16,3));
			path.add(new Point(16,2));
			path.add(new Point(16,1));
			path.add(new Point(16,0));
			//path.add(new Point(,));



			
			//add (all) newly created enemy paths to the paths 2D list
			paths = new LinkedList<LinkedList<Point>>();
			paths.add(path);
			
			//assign the levelDesciption String
			levelDescription = "This is the Lava map and is 20 x 30. It has a single path";
			
			//assign the backgroundImage for the level
			
			 // @ Max Justice Sun 10:50 added ceruleanGym
			 
			backgroundImage = null; //"src/images/Cerulean_Gym.png"; 							//and place the image's location here <---
			
			//instantiate a LevelXMap object that extends Map class
			map = new LavaMap(grid, paths, levelDescription, backgroundImage, mapCode, player);
			break;
			//case other levels...
		 case 2:
		  //this will have a second path to traverse
		 			
			//Set the size of the map
			horizontalSize = 45;
			verticalSize = 30;
			grid = new Tile[verticalSize][horizontalSize];
			
			//Populate the Tile[][] with appropriate amount of tiles
			for(int r = 0; r < verticalSize; r++){
				for(int c = 0; c < horizontalSize; c++){
					grid[r][c] = new Tile();
				}
			}
			
			paths = new LinkedList<LinkedList<Point>>();
			
			//Initialize and populate paths
			
				LinkedList<Point> path1 = new LinkedList<Point>();
			
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));
				path1.add(new Point(2,0));

				LinkedList<Point> path2 = new LinkedList<Point>();
			
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));
				path2.add(new Point(2,0));

			//add (all) newly created enemy paths to the paths 2D list
			paths.add(path1);
			paths.add(path2);
			
			//assign the levelDesciption String
			levelDescription = "This is the biggest map of 30 x 45 with two paths";
			
			//assign the backgroundImage for the level
			backgroundImage = "/images/Cerulean_Gym.png"; //
									
			
			//instantiate a LevelXMap object that extends Map class
			map = new WaterMap(grid, paths, levelDescription, backgroundImage, mapCode, player);
			break;
		 		 
		default:
			map = null; //A non-existent level for mapCode was passed, should never happen
		}
		return map;
	}	
}
