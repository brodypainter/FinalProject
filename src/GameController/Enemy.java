package GameController;

import java.awt.Point;
import java.io.Serializable;
import model.Map;

/**
 * This is our Enemy class called Pokemon.  It our enemy abstract class containg the attributes
 * shared across enemies.  It calls the constructor which initializes health, attack and defense
 * power.  It also declares the worth of the pokemon that the player wins when the pokemon
 * dies.
 * 
 * NOTE: A POINT LOCATION IS NOT DECLARED BY DEFAULT.  MEANING IF YOU DON'T CALL 
 * SETPOINT() THE POKEMON WILL NOT BE PLACED ON THE BOARD
 * 
 * TODO figure out the speed of the pokemon and its units.  Figure out other methods that might be needed.
 * @author Max Justice
 * 
 * Instance Variables
 * int Health			- Stores the health of the pokemon
 * int AttackPower		- stores the attack power though this might be unused
 * int Defense			- the defense of the pokemon.  it used to calculate the attack power
 * int Speed			- The number (or fraction) of tiles the enemy moves through each second
 * String Pokemon		- this is just the name of the pokemon
 * int Worth			- this is how much the pokemon is worth when it is killed
 * Point Location		- this gets the current location of the pokemon on the board as a Point
 * String imageURL		- this gets the image URL for the Pokemon
 * Map map				- The map on which the pokemon is placed
 * 
 * Abstract Methods
 * None
 * 
 * Methods
 * Point getLocation()			- get the location
 * boolean setLocation(Point x)	- set the location as it moves
 * int getMoney()				- this gets the value the pokemon has
 * String getPokemon()			- get pokemon name
 * int getAttackPower()
 * boolean isDead()
 * boolean incomingAttack(int incomingAttack)
 * int getSpeed()
 * boolean setSpeed(int speed)
 * String getImageURL()
 * Int getProgress()
 * void setProgress()
 */
public abstract class Enemy implements Serializable{
	private int Health;
	private int AttackPower;
	private int Defense;
	private double Speed; //The number (or fraction) of tiles the enemy moves through each second
	private String Pokemon;
	private int Worth;
	private Point previousLocation; //The immediately previous location of the enemy
	private Point location; //The current location of the enemy
	private Point nextLocation; //The location the enemy will go to next when it is ready
	//private String[] allImageURLs; //The list of all URLs of all images associated with the enemy
	private String imageURL;
	private String leftURL;
	private String rightURL;
	private String upURL;
	private String downURL;
	private Map map;
	private int timePerTile; //The time in ms the enemy will spend on each tile before moving to the next
	private int timeSinceLastMovement; //The time in ms since the enemy has last moved a tile
	private int progress;		// requested by Desone track progress (timeSinceLastMovement/timePerTile)
	private directionFacing orientation; //The way the enemy is facing based on it's Location nextLocation
	private int stepsTaken; //The amount of tiles the enemy has taken along the path, useful for targeting farthest
	private int distanceLeftOnPath;
	private int maxHealth; //The initial, maximum health of Enemy
	private int healthPercentage; //The percentage of current Health / maxHealth
	private int pathTravelingCode; //The path # that the enemy is traveling on, starting at 0. Set when Level spawns enemies

	/**
	 * The constructor for Pokemon it takes the following variables
	 * @param health for the initial state of the pokemons health
	 * @param attackPower for the attack power should we use attacks to take player health
	 * @param defense The defense modifier.  It takes the attack incoming minus the defense and subtracts from health
	 * @param speed The number (or fraction) of tiles the enemy moves through each second
	 * @param name the name of the monster
	 * @param worth the worth of the monster as it is created
	 * @param Image
	 */
	public Enemy (int health, int attackPower, int defense, double speed, String name, int worth, Map mapRef, String south, String north, String west, String east){
		this.Health = health;
		this.maxHealth = health;
		this.AttackPower = attackPower;
		this.Defense = defense;
		this.Speed = speed;
		calculateTimePerTile();
		this.Pokemon = name;
		this.Worth = worth;
		//Max 12/14 to get the images to appear
		this.imageURL = south;
		this.downURL = south;
		this.upURL = north;
		this.leftURL = west;
		this.rightURL = east;
		orientation = directionFacing.SOUTH; //By default
		timeSinceLastMovement = 0;
		stepsTaken = 0;
		healthPercentage = 100;
		this.map = mapRef;
		//distanceLeftOnPath = mapRef.lengthOfPath();
	} // end constructor
	
	// Max 12/2 this is for the pokemon's unique abilities
	abstract boolean specialPower();
	
	/**
	 * This is just a getter returning the current location
	 * @return the point of the current location on the screen
	 */
	public Point getLocation(){
		return this.location;
	}
	
	/**
	 * This is the setLocation and it updates the location on the screen as the 
	 * creature moves
	 * @param x is the location it is being moved to
	 * @return returns true that this method successful ran
	 */
	public boolean setLocation(Point x){
		this.location = x;
		return true;
	}
	
	public void calculateHealthPercentage(){
		this.healthPercentage = Health / maxHealth;
	}
	
	//I moved a copy of this and changed name slightly to the Enemy Class
	//Remember Point.x = how many rows down, Point.y = how many columns right the location is in the model
public enum directionFacing{NORTH, EAST, SOUTH, WEST};
	
	public directionFacing direction(Enemy enemy)
	{
		// right, left, up, down
		if(enemy.getLocation().x - enemy.getNextLocation().x > 0)
		{
			this.imageURL = this.upURL;
			return directionFacing.NORTH;
		}
		else if(enemy.getLocation().x - enemy.getNextLocation().x < 0)
		{
			this.imageURL = this.downURL;
			return directionFacing.SOUTH;
		}
		else if(enemy.getLocation().y - enemy.getNextLocation().y < 0)
		{
			this.imageURL = this.rightURL;
			return directionFacing.EAST;
		}
		else if(enemy.getLocation().y - enemy.getNextLocation().y < 0)
		{
			this.imageURL = this.leftURL;
			return directionFacing.WEST;
		}
		return directionFacing.SOUTH;//By default, shouldn't ever get to this point
	}
	
	// increase the number of steps taken each tick
	public void takeStep(){
		stepsTaken++;
	}
	
	// return the number of steps taken
	public int getStepsTaken(){
		return stepsTaken;
	}
	
	// Upon death is returns the worth of the Pokemon
	public int getMoney(){
		return this.Worth;
	}
	
	// returns the name of the pokemon
	public String getPokemon(){
		return this.Pokemon;
	}
	
	// this method just returns the current attack power of the pokemon
	public int getAttackPower(){
		return this.AttackPower;
	}
	
	// this method just checks if the enemy is dead and returns a boolean depending on it
	public boolean isDead(){
		if(this.Health <= 0){
			map.gainedGold(Worth); // this calls the maps gainedGold method passing it he worth of the Enemy
			return true;
		}
		return false;
	}
	
	/**
	 * THis method handles the attack from the gym.  it Takes the incoming attack from the tower
	 * and subtracts the attack from the defense then subtracts this value from the 
	 * enemies overall health and returns a boolean that it was successfully carried out.
	 * If the value is less then zero is sets the attack to zero
	 * @param incomingAttack
	 * @return true 
	 */
	public boolean incomingAttack(int incomingAttack){
		int healthToSubtract = incomingAttack - this.Defense;
		if(healthToSubtract < 0)
			healthToSubtract = 0;
		this.Health -= healthToSubtract;
		
		if(isDead()){
			this.map.removeDeadEnemy(this.location, this);
		}
		this.calculateHealthPercentage();
		return true;
	}
	
	// gets the current speed of the pokemon
	public double getSpeed(){
		return this.Speed;
	}
	
	// set the speed of the pokemon is we need to later
	public boolean setSpeed(int speed){
		this.Speed = speed;
		calculateTimePerTile();
		return true;
	}
	
	/**
	 * Updates the timePerTile variable, must be called every time Speed is changed
	 */
	private void calculateTimePerTile(){
		timePerTile = (int) (1.0/Speed)*1000;
	}
	
	/**
	 * Instead of passing in the image of the sprite we will instead pass the URL.
	 * THis will allow the data transfer to the server and multiplayer since images
	 * are non serializable
	 * @return
	 */
	public String getImageURL(){
		return this.imageURL;
	}
	
	
	/**
	 * Increase the defense of the pokemon.  If the defense gets below 0, defense is set to default
	 * of 0 to avoid issues in attack
	 * @param defense
	 * @return
	 */
	public boolean increaseDefense(int defense){
		int newDefense = this.Defense + defense;
		if (newDefense < 0)
			newDefense = 0;
		this.Defense = newDefense;
		return true;
	}
	
	public int getHealth(){
		return this.Health;
	}
	
	/**
	 * Sets the pokemon's map instance variable, method to be called by the Map that it is spawned on
	 * 
	 * @param map the map on which the enemy is
	 */
	public void setMap(Map map) {
		this.map = map;
		
	}

	/**
	 * Updates the timeSinceLastMovement variable and checks/moves if the Enemy can move
	 */
	public void tick(int timePerTick) {
		timeSinceLastMovement = timeSinceLastMovement + timePerTick; //20 because master Timer ticks every 20 ms, make sure it is equal
		if(timeSinceLastMovement >= timePerTile){
			map.updateEnemyPosition(this);
			orientation = this.direction(this);//Enemy has just moved, update it's orientation
			timeSinceLastMovement = 0;
		}
		calculateProgress(); //Updates % of tile he is finished with
	}
	
	// get the previous location of the pokemon
	public Point getPreviousLocation() {
		return previousLocation;
	}

	// set the previous location of the pokemon
	public void setPreviousLocation(Point previousLocation) {
		this.previousLocation = previousLocation;
	}

	// dunno
	public Point getNextLocation() {
		return nextLocation;
	}
	/**
	 * In setNextLocation I set the countdown to remaining path left on the map by the enemy
	 * So whenever it moves it subtracts the distance left on enemy
	 * @param nextLocation
	 */
	public void setNextLocation(Point nextLocation) {
		this.nextLocation = nextLocation;
		distanceLeftOnPath--;
	}
	
	// for desonne and the GUI
	public int getProgress(){
		return this.progress;
	}
	
	//The percentage that the enemy is across the tile is progress
	//this method is called on each enemy every tick.
	public void calculateProgress(){
		setProgress((double)timeSinceLastMovement/(double)timePerTile);
	}
	
	// for Desone and the GUI
	public void setProgress(double prog){
		if (prog < 0){
			this.progress = 0;
		}
		if(prog > 1)
			this.progress = 100;
		else
			this.progress = (int)(prog*100);
	}
	
	public directionFacing getOrientation(){
		return orientation;
	}
	
	/**
	 * @ max Justice for attack algorithm
	 */
	public int getDistanceLeftForEnemy(){
		return distanceLeftOnPath;
	}

	/**
	 * Return the percent of health left out of max health
	 * @return an int from 0-100 of percent of health left
	 */
	public int getHealthPercentage() {
		return healthPercentage;
	}
	
	
	public int getDefense (){
		return this.Defense;
	}
	
	/**
	 * Abstract printEnemyStats that is specific to each pokemon
	 * @return string
	 */
	public abstract String printEnemyStats( );/*{
		
		String stats = new String ("Name: "+ getPokemon() + "\n" +
									"Current Health: " + getHealthPercentage() + "%\n" +
									"Attack: " + getAttackPower() + "\n"+
									"Defense: " + getDefense() + "\n"+
									"Speed: "+ getSpeed() + "\n" +
									"Value: " + getMoney() + "\n");
		
		return stats;
	}*/

	/**
	 * Returns the pathTravelingCode for telling which path the enemy is traveling
	 * @return pathTravelingCode that identifies which path, starting at 0, that the enemy travels
	 */
	public int getPathTravelingCode() {
		return pathTravelingCode;
	}

	/**
	 * Level classes will call this method when creating enemies to spawn to separate
	 * enemies into traveling along 1 of possibly many paths.
	 * @param pathTravelingCode the path # you want the enemy to travel, starting at 0
	 */
	public void setPathTravelingCode(int pathTravelingCode) {
		this.pathTravelingCode = pathTravelingCode;
	}
}
