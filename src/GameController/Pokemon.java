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
	private Map map; //the map on which this enemy is spawned
	
	public Pokemon (int health, int attackPower, int defense, int speed, String name, int worth){
		this.Health = health;
		this.AttackPower = attackPower;
		this.Defense = defense;
		this.Speed = speed;
		this.Pokemon = name;
		this.Worth = worth;
		map = null;
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
	
	public Map getMap(){
		return map;
	}
	
	public void setMap(Map map){
		this.map = map; 
	}
	
	public void damage(int hplost){
		Health = Health - hplost;
	}
}
