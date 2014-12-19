package GUI;

import java.awt.Point;
import java.io.Serializable;

import model.towers.Tower;

/**
 * This class will be sent to the GUI inside an ArrayList of TowerImages
 * inside a command object from server to client every time we want to repaint.
 * @author Peter Hanson
 *
 */

public class TowerImage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3104951236268857001L;
	private String imageURL; //The URL of the image to paint
	private Point location; //The coordinates (rowsDown, column) in grid to paint image
	private int level; //The evolution stage
	
	public TowerImage(Tower tower){
		imageURL = tower.getImageURL();
		location = tower.getPosition();
		level = tower.getCurrentLevel();
	}
	
	public String getImageURL(){
		return imageURL;
	}
	
	public Point getLocation(){
		return location;
	}

	/**
	 * Returns level
	 * @return level, the evolution stage of the tower
	 */
	public int getLevel(){
		return level;
	}
}
