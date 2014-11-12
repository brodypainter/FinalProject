package client;

import java.io.Serializable;

//Created the Player.java class in right folder hopefully


public class Player implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -937778023185001965L;
	private String name;
	private int money;
	private int healthPoints;
	
	//May possibly want to include Map and Level instance variables for ease of save state
	
	public Player(String name, int money, int healthPoints){
		this.name = name;
		this.money = money;
		this.healthPoints = healthPoints;
	}

	public String getName(){
		return name;
	}
	
	public int getMoney(){
		return money;
	}
	
	public void setMoney(int amount){
		money = amount;
	}
	
	public void gainMoney(int amount){
		money = money + amount;
	}
	
	public void setHealth(int amount){
		healthPoints = amount;
	}
	
	
	public int getHealthPoints(){
		return healthPoints;
	}
	
	
	public void loseHealth(int hpLost) {
		healthPoints = healthPoints - hpLost;
		if(healthPoints < 0){
			healthPoints = 0;
		}
	}
	
	public void spendMoney(int moneySpent){
		money = money - moneySpent;
		if(money < 0){
			money = 0;
		}
	}
	
	public boolean isAlive(){
		if(healthPoints > 0){
			return true;
		}else{
			return false;
		}
	}
	
	
	
}
