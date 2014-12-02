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

	// max 12/14 made transient to keep from interfering with serializable
	private transient Enemy enemy;

	public EnemyImage(Enemy enemy){
		this.imageURL = enemy.getImageURL();
		this.location = enemy.getLocation();
		this.orientation = enemy.getOrientation();
		this.progress = enemy.getProgress();
		this.healthPercentage = enemy.getHealthPercentage();
		// Max 12/1 stored the enemy in here that is transient so it won't mess up the serializable
		this.enemy= enemy;
	}
	
	public String getImageURL(){
		return imageURL;
	}

	public Point getLocation(){
		return location;
	}
	// Max 12/1 trying to get the image direction to show
	public directionFacing getOrientation(){
		//System.out.println(this.enemy.getImageURL());
		return orientation;
	}
	
	public int getProgress(){
		return progress;
	}
	
	public int getHealthPercentage(){
		return healthPercentage;
	}
}
