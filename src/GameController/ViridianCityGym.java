package GameController;

import java.util.ArrayList;

public class ViridianCityGym extends Tower{

	public ViridianCityGym(String Name, int Attack, int Radius,
			double FireRateSec, String PlayersName, String Image, int cost) {
		super(Name, Attack, Radius, FireRateSec, PlayersName, Image, cost);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean AttackEnemy(ArrayList<Enemy> enemies) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setModifer() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getModifer() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean increaseFireRate(double amountToIncrease) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean levelUp() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String printTowerStats() {
		// TODO Auto-generated method stub
		return null;
	}

}
