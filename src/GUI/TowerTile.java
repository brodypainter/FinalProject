package GUI;

import javax.swing.JLabel;

public class TowerTile extends JLabel
{
	int range;
	int power;
	int rate;
	boolean upgradable;
	
	void setRange(int range)
	{
		this.range = range;
	}
	
	void setPower(int power)
	{
		this.power = power;
	}
	
	void setRate(int rate)
	{
		this.rate = rate;
	}
	
	void setUpgradable(boolean upgradable)
	{
		this.upgradable = upgradable;
	}
	
	int getRange()
	{
		return range;
	}
	
	int getPower()
	{
		return power;
	}
	
	int getRate()
	{
		return rate;
	}
	
	boolean isUpgradable()
	{
		return upgradable;
	}
}
