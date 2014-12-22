package model.towers;

import java.util.ArrayList;

import model.enemies.Enemy;
import GUI.GameView.towerType;

public class MewtwoTower extends Tower{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6774459183746215045L;

	public static final int Cost = 1200;
	
	private String level1 = "src/images/tower8Level1.png";
	
	public MewtwoTower(){
		/* String Name, int Attack, int Radius, double FireRateSec, String PlayersName,
					String Image, int cost */
		super("Mewtwo", 90, 8, 0.6, "src/images/tower8Level1.png", Cost);	
		 setTowerType(towerType.MEWTWO);
	}
	 
	 //This method will be called by map every tick() as long as tower is not on cooldown from last attack and passed
	 //a list of all enemies currently on the map
	@Override
	public boolean attackEnemy(ArrayList<Enemy> enemies) {
		
		Enemy target;
		
		target = super.findFarthestEnemyInRange(enemies);
		
		if (target == null)
			return false;
		
		target.incomingAttack(super.getAttackPower());
		getMap().notifyOfAttack(this.getType(), this.getPosition(), target.getLocation());
		return true;
	}

	// This sets modifers when we figure it out
	@Override
	public boolean setModifer() {
		
		return false;
	}

	// This method is for later time when we figure out modifiers
	@Override
	public boolean getModifer() {
		
		return false;
	}

	@Override
	public boolean levelUp() {
		return false; //mewtwo cant be leveled up
	}

	@Override
	public String printTowerStats() {
		
		String stats = new String ("Name: "+ getTowerName() + "\n" +
									"Attack: " + getAttackPower() + "\n"+
									"Rate of Fire: "+ getFireRate() + "\n" +
									"Cost: " + getCost() + "\n"+
									"Modifier: " + getModifer() + "\n"
									);
		return stats;
	}

	@Override
	public int getCostOfLevelingUp() {
		return 10000000; //hardcoded to prevent trying to level up
	}

	@Override
	public boolean upgradeCurrentTower(int playersCoins) {
		return false; //Mewtwo cannot evolve
	}
}
