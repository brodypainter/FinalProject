package GUI;

import java.awt.Point;
import java.io.Serializable;

import GameController.Enemy;
import GameController.Enemy.directionFacing;

public class EnemyImage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4456803491165705554L;
	
	private String imageURL; //The URL of the image to paint
	private Point location; //The coordinates (rowsdown, column) in the grid to paint image
	private directionFacing orientation; //The orientation NORTH SOUTH EAST WEST enemy faces
	private int progress; //The percentage that enemy is across the tile. 0 to 100
						  //Use this to offset the enemy from its top left corner of grid location
	private int healthPercentage; //For future use to display HP bar of enemies, 0 to 100

	public EnemyImage(Enemy enemy){
		this.imageURL = enemy.getImageURL();
		this.location = enemy.getLocation();
		this.orientation = enemy.getOrientation();
		this.progress = enemy.getProgress();
		this.healthPercentage = enemy.getHealthPercentage();
	}
	
	public String getImageURL(){
		return imageURL;
	}

	public Point getLocation(){
		return location;
	}
	
	public directionFacing getOrientation(){
		return orientation;
	}
	
	public int getProgress(){
		return progress;
	}
	
	public int getHealthPercentage(){
		return healthPercentage;
	}
}
