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
		
		Enemy myClosestEnemy;
		
		myClosestEnemy = super.findClosestEnemy(enemies);
		
		if (myClosestEnemy == null)
			return false;
		
		if ( canAttackEnemy(myClosestEnemy.getLocation())){
			myClosestEnemy.incomingAttack(super.getAttackPower());
			/*
			 * GUI here
			 */
			getMap().notifyOfAttack(this.getType(), this.getPosition(), myClosestEnemy.getLocation());
		}	
		
		return true;
		
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
		double newFire = super.getFireRate()+ amountToIncrease;
		if (newFire <= 0)
			newFire = 1;
		super.setFireRate(newFire);
		return true;
	}

	@Override
	public boolean levelUp() {
		this.levelIncrease(); 		// increases the leve by one
		this.setAttackPower(5); 	// increase attack power by 5 poins
		this.modifyAttackRadius(25);// increase attack radius to 25 pixels
		this.increaseFireRate(1); 	// increase the fire rate by one
		return true;
	}

	@Override
	public String printTowerStats() {
		
		String stats = new String ("Name: "+ getGymName() + "\n" +
									"Owner: "+ getGymOwner() +
									"Attack: " + getAttackPower() + "\n"+
									"Rate of Fire: "+ getFireRate() + "\n" +
									"Cost: " + getCost() + "\n"+
									"Modifier: " + getModifer() + "\n"
									);
		return stats;
	}

}
