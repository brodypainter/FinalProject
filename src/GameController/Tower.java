package GameController;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import model.Map;
/**
 * 
 * @author Max Justice
 * This is the tower abstract class for the 3 or more types of towers we will have in our
 * Pokemon themed tower defense game.  We need the towerName which is the gym type.  Every
 * tower will have an attack level, radius with which to attack and amount of Firerate that
 * can be displayed in Seconds.  All gyms currently start at level one and can go up for 
 * Infinity or until it changes.  Each tower will also have a cost associated with it, but
 * this will be determined by the concrete type of the gym.
 * 
 * Instance Variables:
 * String TowerName 	- Name of the gym
 * Int RadiusPxls		- Radius of attack in pixels.   Can't be less than zero
 * Int AttackPts		- The attack power of the gym.  Can't be less that zero.
 * Int FireRateSecs		- The amount of shots that can be fired per second.  Can't be less than zero.
 * Int Level			- The initial level is 1
 * Point BoardLocation	- The board location stores where the tower is stored
 * String GymOwner		- The gym owner is the owner of the Gym
 * String ImageURL		- This is the string that contains the URL to the image.  It allows easy server-client transfer
 * int Modifier			- this variable has yet to be determined what is used for
 * 
 * Abstract Methods:
 * boolean AttackEnemy(ArrayList<Pokemon> enemies)  - attack algorithm
 * boolean setModifer()							- the modifier to what ever we want it to be
 * boolean getModifer()							- get the current state of the modifier
 * 
 * Methods:
 * boolean setPlaceOnBoard (Point x)
 * boolean canAttackEnemy(Point EnemysLocation)
 * int getCurrentLevel()
 * String getGymName()
 * int getFireRate()
 * Boolean setFireRate (int fireRate)
 * int getAttackPower()
 * boolean setAttackPower(int attackPower)
 * boolean levelUp()
 * String getOwnerName()
 * String getImageURL()
 * int getAttackRadius()
 * boolean modifyAttackRadius()
 * boolean levelIncrease()
 * Point getPosition();
 */

public abstract class Tower implements Serializable{
	private String TowerName;
	private int AttackPts;
	private int RadiusPxls;
	private double FireRateSecs;
	private int Level = 1;
	private Point BoardLocation; 
	private String GymOwner;
	private String ImageURL;
	private int Modifier;  //A modifier that might be used later on
	private int timeSinceLastFire; //The time, in ms, since Tower last fired.
	private int coolDownTime; // (1/FireRateSecs)*1000, the minimum time in ms allowed between attacks
	private Map map; //The map on which the tower is placed
	
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
	public Tower (String Name, int Attack, int Radius, double FireRateSec, String PlayersName, String Image){
		this.TowerName = Name;
		this.AttackPts = Attack;
		this.RadiusPxls = Radius;
		this.FireRateSecs = FireRateSec;
		this.GymOwner = PlayersName;
		this.ImageURL = Image;
		timeSinceLastFire = 0;
	} // end Currency
	
	/**
	 * The following four methdods lines 53 to 72 are the abstract methods of the Gym Class
	 * @return
	 */
	
	// attack an enemy!!!  this is could be dependent on the Gym
	public abstract boolean AttackEnemy(ArrayList<Enemy> enemies);
	
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
	public abstract boolean increaseFireRate(double amountToIncrease);/*{
		
		this.AttackPts += amountToIncrease;
		return true;
		//this.AttackPts += 3 or another random value and make the increase a static amount
	}// end increaseFireRate */
	
	/**
	 * 
	 * @param PlayerCurrency is the incoming total amount of currency the player has
	 * @return Boolean
	 * It compares it with the cost of the Gym and returns true if the player has the money
	 * to buy it with else false
	 */
	/*public abstract boolean checkBuy(int PlayerCurrency);{
		if (PlayerCurrency >= this.GymCost)
			return true;
		return false;
	} // end checkBuy*/
	
	/**
	 * Level Up is a per
	 * @return
	 */
	public abstract boolean levelUp();/*{
		this.Level++;
		this.AttackPts += 5;
		this.RadiusPxls += 25;
		this.FireRateSecs++;
		return true;
	}*/
	
	/**
	 * Sets the location of the gym on the board. Point.x = the -y coordinate, Point.y = the x coordinate
	 * from a Euclidian coordinate view.
	 * @param x just a point with where the gym was set on the board.
	 *
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
		
		//TODO:Currently we are only moving the enemies/towers in 1x1 discrete squares on model
		//so RadiusPxls should be replaced by something like "squareRange" for now. -PWH
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
	public double getFireRate(){
		return this.FireRateSecs;
	}
	
	// Getter to return the current owner of the gym
	public String getGymOwner(){
		return this.GymOwner;
	}
	
	// returns the current firerate
	public Boolean setFireRate (double fireRate){
		this.FireRateSecs = fireRate;
		return true;
	}
	
	// get the attack power of the gym
	public int getAttackPower(){
		return this.AttackPts;
	}
	
	// this is set attack.  it prevents from attack from being less or equal to 0 by setting it to 1
	public boolean setAttackPower(int attackPower){
		int attack = this.AttackPts + attackPower;
		if (attack <= 0){
			attack = 1;
		}
		this.AttackPts = attack;
		return true;
	}
	
	/**
	 * Changes the attack radius of the tower.  If the value is negative, or go below 25 it sets the radius at 25
	 * @param radius
	 * @return true that the radius was modified
	 */
	public boolean modifyAttackRadius(int radius){
		int newRadius = this.RadiusPxls + radius;
		if (newRadius < 25){
			newRadius = 25;
		}
		this.RadiusPxls = newRadius;
		return true;
		
	}
	
	// returns the attack radius of the tower
	public int getAttackRadius(){
		return this.RadiusPxls;
	}
	
	// returns the image URL
	public String getImageURL(){
		 return this.ImageURL;
	 }
	
	public boolean levelIncrease(){
		this.Level++;
		return true;
	}
	
	public Point getPosition(){
		return this.BoardLocation;
	}
	
	/**
	 * Updates the coolDownTime variable, must be called every time FireRateSecs is changed
	 */
	private void calculateCoolDown(){
		coolDownTime = (int)(1.0/FireRateSecs)*1000;
	}
	
	public void setMap(Map map){
		this.map = map;
	}

	/**
	 * Called by the Tower's Map every time the master Timer ticks (20 ms). Updates
	 * the timeSinceLastFire variable and checks/attacks if the Tower is ready to fire.
	 */
	public void tick() {
		timeSinceLastFire = timeSinceLastFire + 20; //20 because the Timer ticks every 20 ms
		if(timeSinceLastFire >= coolDownTime){
			AttackEnemy(map.getEnemies());
			timeSinceLastFire = 0;
		}
		
	}
	
	/**
	 * This takes the list of enemies and in that list find a single enemy that is the closest
	 * @param enemies
	 * @return
	 */
	public Enemy findClosestEnemy(ArrayList<Enemy> enemies){
		Enemy closests = null;
		Double shortestDist = null;
		if (enemies.isEmpty()){
			return closests;
		}
			for (Enemy enemy: enemies){
				Point EnemysLocation = enemy.getLocation();
				Double Distance = Math.sqrt( Math.pow((EnemysLocation.getX() - this.BoardLocation.getX()), 2) +
						Math.pow((EnemysLocation.getY() - this.BoardLocation.getY()), 2 ));
				if (shortestDist == null){
					closests = enemy;
					shortestDist = Distance;
				} else if ( Distance < shortestDist){
					closests = enemy;
					shortestDist = Distance;
				}
			}
		
		return closests;
	}

}
