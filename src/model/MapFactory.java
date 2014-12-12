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
			
				path1.add(new Point(29,7));
				path1.add(new Point(28,7));
				path1.add(new Point(28,6));
				path1.add(new Point(28,5));
				path1.add(new Point(28,4));
				path1.add(new Point(28,3));
				path1.add(new Point(29,2));
				path1.add(new Point(28,2));
				path1.add(new Point(28,2));
				path1.add(new Point(27,2));
				path1.add(new Point(26,2));
				path1.add(new Point(25,2));
				path1.add(new Point(24,2));
				path1.add(new Point(23,2));
				path1.add(new Point(22,2));
				path1.add(new Point(21,2));
				path1.add(new Point(20,2));
				path1.add(new Point(19,2));
				path1.add(new Point(19,3));
				path1.add(new Point(19,4));
				path1.add(new Point(19,5));
				path1.add(new Point(20,5));
				path1.add(new Point(21,5));
				path1.add(new Point(22,5));
				path1.add(new Point(23,5));
				path1.add(new Point(24,5));
				path1.add(new Point(25,5));
				path1.add(new Point(25,6));
				path1.add(new Point(25,7));
				path1.add(new Point(25,8));
				path1.add(new Point(24,8));
				path1.add(new Point(23,8));
				path1.add(new Point(22,8));
				path1.add(new Point(21,8));
				path1.add(new Point(20,8));
				path1.add(new Point(19,8));
				path1.add(new Point(18,8));
				path1.add(new Point(17,8));
				path1.add(new Point(17,9));
				path1.add(new Point(17,10));
				path1.add(new Point(17,11));
				path1.add(new Point(18,11));
				path1.add(new Point(19,11));
				path1.add(new Point(20,11));
				path1.add(new Point(21,11));
				path1.add(new Point(22,11));
				path1.add(new Point(23,11));
				path1.add(new Point(24,11));
				path1.add(new Point(25,11));
				path1.add(new Point(26,11));
				path1.add(new Point(27,11));
				path1.add(new Point(27,12));
				path1.add(new Point(27,13));
				path1.add(new Point(27,14));
				path1.add(new Point(26,14));
				path1.add(new Point(25,14));
				path1.add(new Point(24,14));
				path1.add(new Point(24,15));
				path1.add(new Point(24,16));
				path1.add(new Point(24,17));
				path1.add(new Point(23,17));
				path1.add(new Point(22,17));
				path1.add(new Point(21,17));
				path1.add(new Point(20,17));
				path1.add(new Point(19,22));
				path1.add(new Point(20,22));
				path1.add(new Point(21,22));
				path1.add(new Point(22,22));
				path1.add(new Point(23,22));
				path1.add(new Point(24,22));
				path1.add(new Point(25,22));
				path1.add(new Point(26,22));
				path1.add(new Point(27,22));
				path1.add(new Point(27,23));
				path1.add(new Point(27,24));
				path1.add(new Point(27,25));
				path1.add(new Point(27,26));
				path1.add(new Point(27,27));
				path1.add(new Point(26,27));
				path1.add(new Point(25,27));
				path1.add(new Point(24,27));
				path1.add(new Point(23,27));
				path1.add(new Point(22,27));
				path1.add(new Point(21,27));
				path1.add(new Point(21,26));
				path1.add(new Point(21,25));
				path1.add(new Point(20,25));
				path1.add(new Point(19,25));
				path1.add(new Point(18,25));
				path1.add(new Point(18,26));
				path1.add(new Point(18,27));
				path1.add(new Point(18,28));
				path1.add(new Point(18,29));
				path1.add(new Point(19,29));
				path1.add(new Point(20,29));
				path1.add(new Point(21,29));
				path1.add(new Point(22,29));
				path1.add(new Point(23,29));
				path1.add(new Point(24,29));
				path1.add(new Point(25,29));
				path1.add(new Point(26,29));
				path1.add(new Point(27,29));
				path1.add(new Point(28,29));
				path1.add(new Point(29,29));


				LinkedList<Point> path2 = new LinkedList<Point>();
			
				path2.add(new Point(6,0));
				path2.add(new Point(6,1));
				path2.add(new Point(6,2));
				path2.add(new Point(6,3));
				path2.add(new Point(6,4));
				path2.add(new Point(7,4));
				path2.add(new Point(8,4));
				path2.add(new Point(9,4));
				path2.add(new Point(10,4));
				path2.add(new Point(11,4));
				path2.add(new Point(11,5));
				path2.add(new Point(11,6));
				path2.add(new Point(11,7));
				path2.add(new Point(11,8));
				path2.add(new Point(11,9));
				path2.add(new Point(11,10));
				path2.add(new Point(11,11));
				path2.add(new Point(10,11));
				path2.add(new Point(9,11));
				path2.add(new Point(8,11));
				path2.add(new Point(7,11));
				path2.add(new Point(6,11));
				path2.add(new Point(5,11));
				path2.add(new Point(5,12));
				path2.add(new Point(5,13));
				path2.add(new Point(5,14));
				path2.add(new Point(4,14));
				path2.add(new Point(3,14));
				path2.add(new Point(2,14));
				path2.add(new Point(2,15));
				path2.add(new Point(2,16));
				path2.add(new Point(2,17));
				path2.add(new Point(2,18));
				path2.add(new Point(2,19));
				path2.add(new Point(3,19));
				path2.add(new Point(4,19));
				path2.add(new Point(5,19));
				path2.add(new Point(6,19));
				path2.add(new Point(7,19));
				path2.add(new Point(8,19));
				path2.add(new Point(9,19));
				path2.add(new Point(10,19));
				path2.add(new Point(11,19));
				path2.add(new Point(12,19));
				path2.add(new Point(13,19));
				path2.add(new Point(14,19));
				path2.add(new Point(15,19));
				path2.add(new Point(15,20));
				path2.add(new Point(15,21));
				path2.add(new Point(15,22));
				path2.add(new Point(15,23));
				path2.add(new Point(15,24));
				path2.add(new Point(15,25));
				path2.add(new Point(15,26));
				path2.add(new Point(15,27));
				path2.add(new Point(15,28));
				path2.add(new Point(15,29));
				path2.add(new Point(14,29));
				path2.add(new Point(13,29));
				path2.add(new Point(12,29));
				path2.add(new Point(11,29));
				path2.add(new Point(10,29));
				path2.add(new Point(10,30));
				path2.add(new Point(10,31));
				path2.add(new Point(10,32));
				path2.add(new Point(10,33));
				path2.add(new Point(10,34));
				path2.add(new Point(11,34));
				path2.add(new Point(12,34));
				path2.add(new Point(13,34));
				path2.add(new Point(14,34));
				path2.add(new Point(15,34));
				path2.add(new Point(16,34));
				path2.add(new Point(17,34));
				path2.add(new Point(18,34));
				path2.add(new Point(19,34));
				path2.add(new Point(20,34));
				path2.add(new Point(21,34));
				path2.add(new Point(22,34));
				path2.add(new Point(23,34));
				path2.add(new Point(24,34));
				path2.add(new Point(25,34));
				path2.add(new Point(25,35));
				path2.add(new Point(25,36));
				path2.add(new Point(25,37));
				path2.add(new Point(25,38));
				path2.add(new Point(25,39));
				path2.add(new Point(25,40));
				path2.add(new Point(25,41));
				path2.add(new Point(25,42));
				path2.add(new Point(24,42));
				path2.add(new Point(23,42));
				path2.add(new Point(22,42));
				path2.add(new Point(21,42));
				path2.add(new Point(20,42));
				path2.add(new Point(19,42));
				path2.add(new Point(18,42));
				path2.add(new Point(18,41));
				path2.add(new Point(18,40));
				path2.add(new Point(19,39));
				path2.add(new Point(18,38));
				path2.add(new Point(18,37));
				path2.add(new Point(17,37));
				path2.add(new Point(16,37));
				path2.add(new Point(15,37));
				path2.add(new Point(14,37));
				path2.add(new Point(13,37));
				path2.add(new Point(12,37));
				path2.add(new Point(11,37));
				path2.add(new Point(11,38));
				path2.add(new Point(11,39));
				path2.add(new Point(11,40));
				path2.add(new Point(10,40));
				path2.add(new Point(9,40));
				path2.add(new Point(8,40));
				path2.add(new Point(7,40));
				path2.add(new Point(6,40));
				path2.add(new Point(5,40));
				path2.add(new Point(5,39));
				path2.add(new Point(5,38));
				path2.add(new Point(5,37));
				path2.add(new Point(5,36));
				path2.add(new Point(5,35));
				path2.add(new Point(5,34));
				path2.add(new Point(5,33));
				path2.add(new Point(5,32));
				path2.add(new Point(5,31));
				path2.add(new Point(5,30));
				path2.add(new Point(5,29));
				path2.add(new Point(5,28));
				path2.add(new Point(5,27));
				path2.add(new Point(5,26));
				path2.add(new Point(5,25));
				path2.add(new Point(4,25));
				path2.add(new Point(3,25));
				path2.add(new Point(2,25));
				path2.add(new Point(1,25));
				path2.add(new Point(0,25));


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
