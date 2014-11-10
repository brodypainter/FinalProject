package model;

import java.awt.Image;
import java.awt.Point;
import java.util.LinkedList;

import client.Player;

public class Level0Map extends Map{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8007542926289934852L;

	public Level0Map(Tile[][] gridDimensions, LinkedList<Point> path, String mapType, Image background, int mapTypeCode, Player player){
		super(gridDimensions, path, mapType, background, mapTypeCode, player);
		
	}
}
