package GUI;

import javax.swing.JLabel;

import GameController.Enemy.directionFacing;

public class EnemyTile extends JLabel
{
	int health;
	String ID;
	int maxHealth;
	int attack;
	int defense;
	double speed;
	int worth;
	directionFacing dir;
	
	void setHealth(int health)
	{
		this.health = health;
	}
	
	void setID(String str)
	{
		this.ID = str;
	}
	
	void setMaxHealth(int maxHealth)
	{
		this.maxHealth = maxHealth;
	}
	
	void setAttack(int attack)
	{
		this.attack = attack;
	}
	
	void setDefense(int defense)
	{
		this.defense = defense;
	}
	
	void setSpeed(double speed)
	{
		this.speed = speed;
	}
	
	void setWorth(int worth)
	{
		this.worth = worth;
	}
	
	void setDirection(directionFacing dir)
	{
		this.dir = dir;
	}
	
	int getHealth()
	{
		return health;
	}
	
	String getID()
	{
		return ID;
	}
	
	int getMaxHealth()
	{
		return maxHealth;
	}
	
	int getHealthLeft()
	{
		return (int) (maxHealth * 0.01 * health);
	}
	
	int getAttack()
	{
		return attack;
	}
	
	double getSpeed()
	{
		return speed;
	}
	
	int getWorth()
	{
		return worth;
	}
	
	int getDefense()
	{
		return defense;
	}
	
	directionFacing getDirection()
	{
		return dir;
	}
	
	/**
	 * Sets the stats of the EnemyTile such as health and attack
	 * @param health The percentage of health left
	 * @param maxHealth The maximium health
	 * @param attack Attack power
	 * @param defense Defense power
	 * @param speed Speed
	 * @param worth What the enemy is worth when killed
	 */
	void setStats(int health, int maxHealth, int attack, int defense, double speed, int worth)
	{
		this.health = health;
		this.maxHealth = maxHealth;
		this.attack = attack;
		this.defense = defense;
		this.speed = speed;
		this.worth = worth;
	}
}
