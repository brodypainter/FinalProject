package GUI;

import javax.swing.JLabel;

public class TowerTile extends JLabel
{
	int range;
	int power;
	double rate;
	boolean upgradable;
	int type;
	int level;
	int cost;
	String special = "";
	String ID = "";
	
	void setRange(int range)
	{
		this.range = range;
	}
	
	void setPower(int power)
	{
		this.power = power;
	}
	
	void setRate(double rate)
	{
		this.rate = rate;
	}
	
	void setUpgradable(boolean upgradable)
	{
		this.upgradable = upgradable;
	}
	
	void setType(int type)
	{
		this.type = type;
	}
	
	void setLevel(int level)
	{
		this.level = level;
	}
	
	void setCost(int cost)
	{
		this.cost = cost;
	}
	
	void setSpecial(String special)
	{
		this.special = special;
	}
	
	void setID(String ID)
	{
		this.ID = ID;
	}
	
	int getRange()
	{
		return range;
	}
	
	int getPower()
	{
		return power;
	}
	
	double getRate()
	{
		return rate;
	}
	
	boolean isUpgradable()
	{
		return upgradable;
	}
	
	int getType()
	{
		return type;
	}
	
	int getLevel()
	{
		return level;
	}
	
	int getCost()
	{
		return cost;
	}

	public String getSpecial()
	{
		return special;
	}
	
	public String getID()
	{
		return ID;
	}
}
