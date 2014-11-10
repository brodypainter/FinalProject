package GameController;

import java.awt.Image;
import java.awt.Point;
import java.io.Serializable;

/**
 * This is our Enemy class called Pokemon.  It our enemy abstract class containg the attributes
 * shared across enemies.  It calls the constructor which initializes health, attack and defense
 * power.  It also declares the worth of the pokemon that the player wins when the pokemon
 * dies.  
 * @author Max Justice
 *
 */
public abstract class Pokemon implements Serializable{
	private int Health;
	private int AttackPower;
	private int Defense;
	private int Speed; // amount of pixels traversed per second
	private String Pokemon;
	private int Worth;
	private Point Location;
	private String imageURL;
	
	/**
	 * The constructor for Pokemon it takes the following variables
	 * @param health for the initial state of the pokemons health
	 * @param attackPower for the attack power should we use attacks to take player health
	 * @param defense The defense modifier.  It takes the attack incoming minus the defense and subtracts from health
	 * @param speed the Speed of the enemy as they move across the screen in pixels per second
	 * @param name the name of the monster
	 * @param worth the worth of the monster as it is created
	 * @param Image
	 */
	public Pokemon (int health, int attackPower, int defense, int speed, String name, int worth, String Image){
		this.Health = health;
		this.AttackPower = attackPower;
		this.Defense = defense;
		this.Speed = speed;
		this.Pokemon = name;
		this.Worth = worth;
		this.imageURL = Image;
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
	public int getSpeed(){
		return this.Speed;
	}
	
	// set the speed of the pokemon is we need to later
	public boolean setSpeed(int speed){
		this.Speed = speed;
		return true;
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
}
