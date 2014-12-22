package model.towers;

import java.util.ArrayList;

import model.enemies.Enemy;
import GUI.GameView.towerType;

public class PoliwagTower extends Tower{

	
	private static final long serialVersionUID = -3686302097423973732L;
	
	private String level1= "src/images/tower2Level1.png";
	private String level2= "src/images/tower2Level2.png";
	private String level3= "src/images/tower2level3.png";
	
	public static final int Cost = 100;
	private final int costOfUpgrade1 = 200;
	private final int costOfUpgrade2 = 1200;
	private final int maxLevelAttainable = 3;
	//String Name, int Attack, int Radius, int FireRateSec, String PlayersName
	/**
	 * The default settings for the cerulean gym a attack power of 25, a radius range of 3 tiles,
	 * 3 shots per a second and the Image URL to the gym
	 * @param PlayersName is the only value the gym 
	 */
	public PoliwagTower(){
		/* String Name, int Attack, int Radius, double FireRateSec, String PlayersName,
					String Image, int cost */
		super("Poliwag", 10, 1, 3.0, "src/images/tower2Level1.png", Cost);	
		 setTowerType(towerType.WATER);
	}

	 
	 //This method will be called by map every tick() as long as tower is not on cooldown from last attack and passed
	 //a list of all enemies currently on the map
	@Override
	public boolean attackEnemy(ArrayList<Enemy> enemies) {
		
		Enemy target;
		
		target = super.findFarthestEnemyInRange(enemies);
		
		if (target == null){
			return false;
		}
		target.incomingAttack(super.getAttackPower());
		getMap().notifyOfAttack(this.getType(), this.getPosition(), target.getLocation());
				
		return true;
	}

	// This sets modifers when we figure it out
	@Override
	public boolean setModifer() {
		// TODO Auto-generated method stub
		return false;
	}

	// This method is for later time when we figure out modifiers
	@Override
	public boolean getModifer() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean levelUp() {
		this.levelIncrease(); 		// increases the level by one
		if (super.getCurrentLevel() == 2){
			super.setImageURL(level2);
			super.setPokemonName("Poliwhirl");
			this.gainAttackPower(5); 	// increase attack power by 5 points
			this.increaseRange(1);// increase attack radius by 1
			this.increaseFireRate(0.5); 	// increase the fire rate by 0,5
		}else if (super.getCurrentLevel() == 3){
			super.setImageURL(level3);
			super.setPokemonName("Poliwrath");
			this.gainAttackPower(5); 	// increase attack power by 5 points
			this.increaseRange(0);// increase attack radius by none
			this.increaseFireRate(0.5); 	// increase the fire rate by 0.5
		}
		return true;
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
		if (super.getCurrentLevel() == 1)
			return this.costOfUpgrade1;
		return this.costOfUpgrade2;
	}

	@Override
	public boolean upgradeCurrentTower(int playersCoins) {
		if ( getCostOfLevelingUp() <= playersCoins && super.getCurrentLevel() < maxLevelAttainable){
			levelUp();
			return true;
		}
		return false;
	}
}


