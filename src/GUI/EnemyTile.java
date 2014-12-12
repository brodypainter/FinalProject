package GUI;

import javax.swing.JLabel;

public class EnemyTile extends JLabel
{
	int health;
	String ID;
	
	void setHealth(int health)
	{
		this.health = health;
	}
	
	void setID(String str)
	{
		this.ID = str;
	}
	
	int getHealth()
	{
		return health;
	}
	
	String getID()
	{
		return ID;
	}
}
