package model.enemies;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import model.enemies.Enemy.directionFacing;
import model.maps.Map;

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
	
	private static final long serialVersionUID = -6737505326016172175L;
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
	private int maxHealth; //The initial, maximum health of Enemy
	private int healthPercentage; //The percentage of current Health / maxHealth
	private int pathTravelingCode; //The path # that the enemy is traveling on, starting at 0. Set when Level spawns enemies
	private String imageID; //A randomized, constant ID code for each Enemy to pass to their Image requested by Desone
	private boolean isAsleep = false, isBurnt = false, isSlowed = false;
	private ArrayList<enemyStatus> status;
	private boolean taunt; //only true for squirtle
	private boolean resourceful; //only true for ratatta
	
	public enum enemyType {NORMAL,WATER,ELECTRIC,GRASS,POISON,PSYCHIC,FIRE,MCCANN}
	
	public enum enemyStatus {NORMAL, TELEPORTED, BURNED, CURSED, SLOWED, SLEEPING}
	
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
		orientation = directionFacing.EAST; //By default
		timeSinceLastMovement = 0;
		stepsTaken = 0;
		healthPercentage = 100;
		this.map = mapRef;
		this.generateImageID();
		this.status = new ArrayList<enemyStatus>();
		this.taunt = false;
		this.resourceful = false;
	} // end constructor
	
	private void generateImageID() {
		this.imageID = "";
		Random r = new Random();
		Integer a;
		for(int i = 0; i < 4; i++){
			a = r.nextInt(10);
			imageID = imageID + a.toString();
		}
		
	}

	// Max 12/2 this is for the pokemon's unique abilities
	abstract boolean specialPower();
	

	/**
	 * Sets the enemy's location to a point
	 * @param p the location (row, column) to set to
	 */
	public void setLocation(Point p){
		this.location = p;
	}
	
	/**
	 * This is just a getter returning the current location
	 * @return the point of the current location on the screen
	 */
	public Point getLocation(){
		return this.location;
	}
	
	public void calculateHealthPercentage(){
		this.healthPercentage = (int) Math.ceil(100*((double)Health / (double) maxHealth));
	}
	
	//I moved a copy of this and changed name slightly to the Enemy Class
	//Remember Point.x = how many rows down, Point.y = how many columns right the location is in the model
public enum directionFacing{NORTH, EAST, SOUTH, WEST};
	
	private directionFacing direction(Enemy enemy)
	{
		// right, left, up, down
		if(enemy.getLocation().x - enemy.getNextLocation().x > 0)
		{
			//this.imageURL = this.upURL;
			enemy.setImageNorth();
			return directionFacing.NORTH;
		}
		else if(enemy.getLocation().x - enemy.getNextLocation().x < 0)
		{
			//this.imageURL = this.downURL;
			enemy.setImageSouth();
			return directionFacing.SOUTH;
		}
		else if(enemy.getLocation().y - enemy.getNextLocation().y < 0)
		{
			//this.imageURL = this.rightURL;
			enemy.setImageEast();
			return directionFacing.EAST;
		}
		else if(enemy.getLocation().y - enemy.getNextLocation().y < 0)
		{
			//this.imageURL = this.leftURL;
			enemy.setImageWest();
			return directionFacing.WEST;
		}
		return directionFacing.SOUTH;//By default, shouldn't ever get to this point
	}
	
	private void setImageNorth(){
		this.setImageURL(this.upURL);
	}
	private void setImageSouth(){
		this.setImageURL(this.downURL);
	}
	private void setImageEast(){
		this.setImageURL(this.rightURL);
	}
	private void setImageWest(){
		this.setImageURL(this.leftURL);
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
	
	public void setPokemonName(String pokemon){
		this.Pokemon=pokemon;
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
	public boolean setSpeed(double d){
		this.Speed = d;
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
	
	public void setImageURL(String evolvedEnemy){
		this.imageURL = evolvedEnemy;
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
	
	public void setHealth(int newHealth){
		this.Health = newHealth;
	}
	
	public void levelUpAttackPower(int newAttack){
		this.AttackPower += newAttack;
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
	 * Updates the timeSinceLastMovement variable and checks/moves if the Enemy can move.
	 * Also applies status effects
	 */
	public void tick(int timePerTick) {
		
		//if enemy is asleep, make it progress towards waking up and check if it wakes up
		if(isAsleep){
			timeSpentSleeping += timePerTick;
			if(timeSpentSleeping >= sleepTimeDuration){
				isAsleep = false;
			}
		}
		
		//if enemy is slowed, make it progress towards returning to normal speed and check if it gets normal
		if(isSlowed){
			timeSpentSlowed += timePerTick;
			if(timeSpentSlowed >= slowTimeDuration){
				isSlowed = false;
			}
		}
		
		//if enemy is burned, make progress toward next burn tick, do damage when reached, and remove burn when its over
		if(isBurnt){
			timeSpentBurned += timePerTick;
			if(timeSpentBurned >= burnTickTimeDuration){
				this.subtractBurnAttack();
				timeSpentBurned = 0;
				numOfBurnsSuffered++;
				if(numOfBurnsSuffered == numOfBurnTicksTotal){
					isBurnt = false;
				}
			}
		}
		
		//if is being teleported, progress towards the actual teleport and then do so when it is time. Reinitialize progress values
		if(isTeleporting){
			this.timeSpentTeleporting += timePerTick;
			if(timeSpentTeleporting >= this.teleportLength){
				timeSinceLastMovement = 0;
				this.setProgress(0);
				map.teleportedEnemy(this);
				isTeleporting = false;
			}
		}
		
		//Can only move when not asleep or being teleported
		if(!isAsleep && !isTeleporting){
			timeSinceLastMovement += timePerTick; //20 because master Timer ticks every 20 ms, make sure it is equal
			if(isSlowed){
				timeSinceLastMovement -= (int)(timePerTick * 0.25); //25% slow if slowed
			}
		}

		if(timeSinceLastMovement >= timePerTile){
			map.updateEnemyPosition(this);
			orientation = this.direction(this);//Enemy has just moved, update it's orientation
			timeSinceLastMovement = 0;
			this.specialPower();//Activate special power on moving to next tile if applicable
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

	// returns the location the enemy is headed towards
	public Point getNextLocation() {
			return nextLocation;
	}
	
	public void setNextLocation(Point nextLocation) {
		this.nextLocation = nextLocation;
	}
	
	// for desone and the GUI
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
		if(prog > 100){
			this.progress = 100;
		}
		else
			this.progress = (int)(prog*100);
	}
	
	public directionFacing getOrientation(){
		return orientation;
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
	
	/**
	 * @Max the special abilities are what follows
	 */
	public void setAsleep(int amountOfSecsAsleep){
		if(!isAsleep && !resourceful){
			sleepTimeDuration = amountOfSecsAsleep * 1000;
			timeSpentSleeping = 0;
			isAsleep = true;
		}
	}
	private int sleepTimeDuration; //The time enemy must stay asleep in ms
	private int timeSpentSleeping; //The time enemy has spent sleeping so far
	
	public boolean setSlowed(int amountOfSecsSlowed){
		if (!isSlowed && !resourceful){
			slowTimeDuration = amountOfSecsSlowed * 1000;
			timeSpentSlowed = 0;
			isSlowed = true;
		}
		return true;
	}
	private int slowTimeDuration;
	private int timeSpentSlowed;
	
	public boolean setBurnt(int amountOfSecsBurnt, int burningDamage){
		if(!isBurnt && !resourceful){
			numOfBurnTicksTotal = amountOfSecsBurnt;
			numOfBurnsSuffered = 0;
			burnTickTimeDuration = (amountOfSecsBurnt / 3) * 1000;
			timeSpentBurned = 0;
			burnDamage = burningDamage;
			isBurnt = true;
		}
		return true;
	}
	private int burnDamage;
	private int burnTickTimeDuration;
	private int timeSpentBurned;
	private int numOfBurnTicksTotal;
	private int numOfBurnsSuffered;
	
	//called every time a burn does damage
	private void subtractBurnAttack(){
		this.Health -= burnDamage;
		
		if(isDead()){
			this.map.removeDeadEnemy(this.location, this);
		}
		this.calculateHealthPercentage();
	}
	
	public void setCurse() {
		if(!resourceful){
			this.Defense = Defense - 2;
			if(this.Defense < 0){
				this.Defense = 0;
			}
			isCursed = true;
		}
	}
	
	private boolean isCursed = false;
	
	//Teleports the enemy to the beginning after a short delay. Even hits ratatta
	public void teleportToBeginning(){
		if(!isTeleporting){
			timeSpentTeleporting = 0;
			isTeleporting = true;
			//map.teleportedEnemy(this) will be called in 300 ms
		}
	}
	
	private boolean isTeleporting = false;
	private int teleportLength = 300; //Length of teleport animation in ms
	private int timeSpentTeleporting;

	//called by map.teleportedEnemy
	public void resetStepsTaken() {
		this.stepsTaken = 0;
	}
	
	public void heal(int regenHP){
		int newHP = this.getHealth() + regenHP;
		if(newHP <= this.getMaxHealth()){
			this.setHealth(newHP);
		}else{
			this.setHealth(this.getMaxHealth());
		}
	}
	
	public void setDefence(int d){
		this.Defense = d;
	}
	
	public void setWorth(int w){
		this.Worth = w;
	}
	
	public String getImageID() {
		return this.imageID;
	}

	public int getMaxHealth() {
		return this.maxHealth;
	}

	public String getName() {
		return this.Pokemon;
	}
	
	public ArrayList<enemyStatus> getStatus(){
		return this.status;
	}
	
	public Map getMap(){
		return this.map;
	}
	
	public void rally(){
		this.levelUpAttackPower(2);
		this.setDefence(this.getDefense() + 2);
	}
	
	public void isTaunter(){
		this.taunt = true;
	}
	
	public boolean gotTaunted(){
		return this.taunt;
	}
	
	public void setResourceful(){
		this.resourceful = true;
	}
	
	public boolean isImmune(){
		return resourceful;
	}

	public void setOrientation(directionFacing o) {
		this.orientation = o;
	}

	

	
}
