package client;

import java.io.Serializable;

import server.GameServer;




/**
 * This class creates Player objects which are primarily responsible for
 * holding 5 instance variables, a name, the server, the current map, and amounts of health points and money.
 * These objects will be stored in files and retrieved when games are saved and loaded.
 * 
 * Instance Variables:
 * long serialVersionUID	- For Serializable
 * String name				- The player's name, useful for differentiation
 * int money				- The amount of money the player currently has
 * int healthPoints			- The amount of health points (HP) the player currently has
 * Map currentMap			- The Map that the player is currently playing
 * 
 * Methods:
 * Player(String name, GameServer server, int money, int healthPoints)
 * String getName()
 * int getMoney()
 * void setMoney(int amount)
 * void gainMoney(int amount)
 * void setHealth(int amount)
 * int getHealthPoints()
 * void loseHealth(int hpLost)
 * void spendMoney(int moneySpent)
 * boolean isAlive()
 * void setMap(Map map)
 * 
 * @author Peter Hanson
 * @version 1.0
 *
 */
public class Player implements Serializable{
	
	private static final long serialVersionUID = -937778023185031965L;
	private String name;
	private int money;
	private int healthPoints;
	private Player partner;
	private boolean hasPartner;
	//private Map currentMap;
	
	
	/**
	 * The constructor.
	 * @param name The Player's name.
	 * @param money The amount of money the Player starts with
	 * @param healthPoints The amount of HP the player starts with
	 * 
	 * removed GameServer server as the second argument in the constructor.
	 */
	public Player(String name, int money, int healthPoints){
		this.name = name;
		this.money = money;
		this.healthPoints = healthPoints;
		this.hasPartner = false;
	}
	
	/**
	 * Returns the Player's name
	 * @return name The name of the player
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Returns the Player's money
	 * @return money The amount of money
	 */
	public int getMoney(){
		return money;
	}
	
	/**
	 * Sets the amount of money the Player has
	 * @param amount The amount of money to set to
	 */
	public void setMoney(int amount){
		money = amount;
	}
	
	/**
	 * Adds an amount of money to the Player's money
	 * @param amount The amount of money to add
	 */
	public void gainMoney(int amount){
		money = money + amount;
	}
	
	/**
	 * Sets the Player's health
	 * @param amount The HP to set to
	 */
	public void setHealth(int amount){
		healthPoints = amount;
	}
	
	/**
	 * Returns player's HP
	 * @return healthPoints The amount of HP the player has
	 */
	public int getHealthPoints(){
		return healthPoints;
	}
	
	/**
	 * Damages the Player's HP by subtracting the amount passed
	 * @param hpLost The amount of damage to do
	 */
	public void loseHealth(int hpLost){
		healthPoints = healthPoints - hpLost;
		if(healthPoints < 0){
			healthPoints = 0;
		}
		if(hasPartner){
			partner.setHealth(this.healthPoints);
			//Players on multiplayer share the same health
			//Do we have to notify Server of this somehow when it happens or will Map do this already? -PWH
		}
	}
	
	/**
	 * Spends some of the player's money by subtracting the amount passed
	 * @param moneySpent The amount of money to spend
	 */
	public void spendMoney(int moneySpent){
		money = money - moneySpent;
		if(money < 0){
			money = 0;
		}
	}
	
	/**
	 * Determines whether or not the player is still alive based on their HP.
	 * @return boolean true if HP > 0, false if HP = 0.
	 */
	public boolean isAlive(){
		if(healthPoints > 0){
			return true;
		}else{
			return false;
		}
	}

	public Player getPartner() {
		return partner;
	}

	public void setPartner(Player partner) {
		this.partner = partner;
		this.hasPartner = true;
	}
	
	/**
	 * Sets the Player's current map to the passed map. Called by Map
	 * associated with the Player object when it is created.
	 * @param map The Map to set currentMap to.
	 */
	/*public void setMap(Map map){
		currentMap = map;
	}
	*/
}
