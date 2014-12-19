package model;

import java.io.Serializable;



/**
 * This class creates Player objects which are primarily responsible for
 * holding 2 instance variables, amounts of health points and money.
 * These objects will be stored in files and retrieved when games are saved and loaded.
 * 
 * Instance Variables:
 * long serialVersionUID	- For Serializable
 * 
 * int money				- The amount of money the player currently has
 * int healthPoints			- The amount of health points (HP) the player currently has
 * 
 * 
 * Methods:
 * Player()
 * int getMoney()
 * void setMoney(int amount)
 * void gainMoney(int amount)
 * void setHealth(int amount)
 * int getHealthPoints()
 * void loseHealth(int hpLost)
 * void spendMoney(int moneySpent)
 * boolean isAlive()
 * 
 * 
 * @author Peter Hanson
 * @version 1.0
 *
 */
public class Player implements Serializable{
	
	
	
	private static final long serialVersionUID = 5408589771290624312L;
	private int money;
	private int healthPoints;

	
	/**
	 * The constructor.
	 * 
	 */
	public Player(){
		
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
}
