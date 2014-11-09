package GameController;

import java.awt.Point;
import java.io.Serializable;

/**
 * This is our Enemy class called Pokemon.  It 
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
	
	public Pokemon (int health, int attackPower, int defense, int speed, String name, int worth){
		this.Health = health;
		this.AttackPower = attackPower;
		this.Defense = defense;
		this.Speed = speed;
		this.Pokemon = name;
		this.Worth = worth;
	} // end constructor
	
	public Point getLocation(){
		return this.Location;
	}
	
	public boolean setLocation(Point x){
		this.Location = x;
		return true;
	}
	
	public int getAttackPower(){
		return AttackPower;
	}
	
	public int getDefense(){
		return Defense;
	}
	
	public int getSpeed(){
		return Speed;
	}
	
	public int getBounty(){
		return Worth;
	}
	
	public void damage(int hplost){
		Health = Health - hplost;
	}
}
