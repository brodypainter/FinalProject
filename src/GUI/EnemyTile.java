package GUI;

import javax.swing.JLabel;

public class EnemyTile extends JLabel
{
	int health;
	
	void setHealth(int health)
	{
		this.health = health;
	}
	
	int getHealth()
	{
		return health;
	}
}
