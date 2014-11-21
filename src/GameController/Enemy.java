package GameController;

import java.awt.Image;
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
	private Point Location; //The current location of the enemy
	private Point nextLocation; //The location the enemy will go to next when it is ready
	private String imageURL;
	private Map map;
	private int timePerTile; //The time in ms the enemy will spend on each tile before moving to the next
	private int timeSinceLastMovement; //The time in ms since the enemy has last moved a tile
	private int progress;		// requested by Desonne track progress
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
	public Enemy (int health, int attackPower, int defense, int speed, String name, int worth, String Image){
		this.Health = health;
		this.AttackPower = attackPower;
		this.Defense = defense;
		this.Speed = speed;
		calculateTimePerTile();
		this.Pokemon = name;
		this.Worth = worth;
		this.imageURL = Image;
		timeSinceLastMovement = 0;
	} // end constructor
	
	/**
	 * This is just a getter returning the current location
	 * @return the point of the current location on the screen
	 */
	public Point getLocation(){
		return this.Location;
	}
	
	/**
	 * This is the setLocation and it updates the location on the screen as the 
	 * creature moves
	 * @param x is the location it is being moved to
	 * @return returns true that this method successful ran
	 */
	public boolean setLocation(Point x){
		this.Location = x;
		return true;
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
		return this.getAttackPower();
	}
	
	// this method just checks if the enemy is dead and returns a boolean depending on it
	public boolean isDead(){
		if( this.Health <= 0)
			return true;
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
	public void tick() {
		timeSinceLastMovement = timeSinceLastMovement + 20; //20 because master Timer ticks every 20 ms
		if(timeSinceLastMovement >= timePerTile){
			map.updateEnemyPosition(this);
			timeSinceLastMovement = 0;
		}
	}

	public Point getPreviousLocation() {
		return previousLocation;
	}

	public void setPreviousLocation(Point previousLocation) {
		this.previousLocation = previousLocation;
	}

	public Point getNextLocation() {
		return nextLocation;
	}

	public void setNextLocation(Point nextLocation) {
		this.nextLocation = nextLocation;
	}
	
	// for desonne and the GUI
	public int getProgress(){
		return this.progress;
	}
	
	// for desonne and the GUI
	public void setProgress(int prog){
		if (prog < 0)
			this.progress = 0;
		else if (prog > 100)
			this.progress = 100;
		else
			this.progress = prog;
	}
}
