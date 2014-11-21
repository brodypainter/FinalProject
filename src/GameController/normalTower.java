package GameController;

import java.util.ArrayList;

public class normalTower extends Tower
{
	String name;
	int attack;
	int range;
	int rate;
	String player;
	String image;

	public normalTower(String name, int attack, int range, int rate, String player, String image)
	{
		
	}

	@Override
	public int AttackEnemy(ArrayList<Enemy> enemies)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean setModifer()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getModifer()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean increaseFireRate(double amountToIncrease)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkBuy(int PlayerCurrency)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean levelUp()
	{
		// TODO Auto-generated method stub
		return false;
	}
}
