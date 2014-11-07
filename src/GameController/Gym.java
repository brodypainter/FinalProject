package GameController;

import java.awt.Point;
import java.io.Serializable;
/**
 * 
 * @author Max
 * This is the tower abstract class for the 3 or more types of towers we will have in our
 * Pokemon themed tower defense game.  We need the towerName which is the gym type.  Every
 * tower will have an attack level, radius with which to attack and amount of Firerate that
 * can be displayed in Seconds.  All gyms currently start at level one and can go up for 
 * Infinity or until it changes.  Each tower will also have a cost associated with it, but
 * this will be determined by the concrete type of the gym
 *
 */

public abstract class Gym implements Serializable{
	private String TowerName;
	private int AttackPts;
	private int RadiusPxls;
	private int FireRateSecs;
	private int Level = 1;
	private int GymCost;
	private Point BoardLocation; 
	private String GymOwner;
	private int Modifier;  //A modifier that might be used later on
	
	// for image load the location of the image here
	/**
	 * 
	 * @param Name is the name of the Gym
	 * @param Attack is the amount of attack the gym can perform
	 * @param Radius is the distance with which the gym can perform it is in points which relate to pixels
	 * @param FireRateSec that amount of Firerate per sec the gym can perform
	 * @param Cost is the cost of the gym to buy
	 * @param PlayersName is the name of the player who owns the tower (Might be unnecessary)
	 * This is our constructor
	 */
	public Gym (String Name, int Attack, int Radius, int FireRateSec, int Cost, String PlayersName){
		this.TowerName = Name;
		this.AttackPts = Attack;
		this.RadiusPxls = Radius;
		this.FireRateSecs = FireRateSec;
		this.GymCost = Cost;
		this.GymOwner = PlayersName;
	} // end Currency
	
	/**
	 * The following four methdods lines 53 to 72 are the abstract methods of the Gym Class
	 * @return
	 */
	
	// attack an enemy!!!  this is dependent on the Gym
	public abstract int AttackEnemy();
	
	// a modifier method that might be used later on and is dependent on the Gyms Attributes
	public abstract boolean setModifer();
	
	// a modifer method to get the current modifier
	public abstract boolean getModifer();
	
	/**
	 * This method can pass a value to increase or we can come back and make it a static value
	 * that a user can buy to increase at the same amount everytime
	 * @param amountToIncrease the amount to increase the 
	 * @return true
	 */
	public abstract boolean increaseFireRate(int amountToIncrease);/*{
		
		this.AttackPts += amountToIncrease;
		return true;
		//this.AttackPts += 3 or another random value and make the increase a static amount
	}// end increaseFireRate */
	
	/**
	 * 
	 * @param PlayerCurrency is the incoming total amount of currency the player has
	 * @return Boolen
	 * It compares it with the cost of the Gym and returns true if the player has the money
	 * to buy it with else false
	 */
	public boolean checkBuy(int PlayerCurrency){
		if (PlayerCurrency >= this.GymCost)
			return true;
		return false;
	} // end checkBuy
	
	/**
	 * 
	 * @param x just a point with where the gym was set on the board
	 * @return
	 */
	public boolean setPlaceOnBoard (Point x){
		this.BoardLocation = x;
		return true;
	}// end setPlaceOnBoard
	
	/**
	 * This method uses the distance between two points on a Euclidean coordinate system
	 * 
	 *                  _______________________________
	 *  Distance = Sqrt -|( (x2 - x1)^2 +  (y2 - y1)^2 )
	 * 			  
	 * It then compares it the radius in pixels and returns true if it can attack 
	 * else false
	 * @param EnemysLocation is a point on the board
	 * @return boolean
	 */
	public boolean canAttackEnemy(Point EnemysLocation){
		Double Distance = Math.sqrt( Math.pow((EnemysLocation.getX() - this.BoardLocation.getX()), 2) +
				Math.pow((EnemysLocation.getY() - this.BoardLocation.getY()), 2 ));
		//System.out.println("Distance fromCanAttackEnemy is %.2f", Distance);
		if(Distance <= this.RadiusPxls){
			return true;
		}
		return false;
	}
	
	// Getter to return the current level
	public int getCurrentLevel(){
		return this.Level;
	}
	
	// Getter to return the Gym's Name
	public String getGymName(){
		return this.TowerName;
	}
	 
	// Getter to return the gym's FireRate fires per second
	public int getFireRate(){
		return this.FireRateSecs;
	}
	
	// Getter to return the current owner of the gym
	public String getGymOwner(){
		return this.GymOwner;
	}
	
	// return the cost of the gym
	public int getGymCost(){
		return this.GymCost;
	}
	
}
