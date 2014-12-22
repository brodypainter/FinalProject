package model.towers;

import java.util.ArrayList;
import java.util.Random;

import model.enemies.Enemy;
import GUI.GameView.towerType;

public class CharmanderTower extends Tower{
	private static final long serialVersionUID = -8363667321504505956L;
	
	private String level1= "src/images/tower7Level1.png";
	private String level2= "src/images/tower7Level2.png";
	private String level3= "src/images/tower7level3.png"; //Level2 or 3?
	
	public static final int Cost = 130;
	private final int costOfUpgrade1 = 470;
	private final int costOfUpgrade2 = 600;
	private final int maxLevelAttainable = 3;
	
	private int burnPower = 10;
	private int durationOfBurnEffect = 3; // in seconds

	//String Name, int Attack, int Radius, int FireRateSec
	/**
	 * The default settings for the cerulean gym a attack power of 25, a radius range of 3 tiles,
	 * 3 shots per a second and the Image URL to the gym
	 * @param PlayersName is the only value the gym 
	 */
	public CharmanderTower(){
		/* String Name, int Attack, int Radius, double FireRateSec, String PlayersName,
					String Image, int cost */
		super("Charmander", 10, 1, 1.5, "src/images/tower7Level1.png", Cost);	
		 setTowerType(towerType.FIRE);
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
			
		if(super.getCurrentLevel() == 1){
			Random r1 = new Random();
			int chanceOfEffect1 = r1.nextInt(10);
				if (chanceOfEffect1 < 3)
					target.setBurnt(durationOfBurnEffect, burnPower);
		}
		else if(super.getCurrentLevel() == 2){
			Random r2 = new Random();
			int chanceOfEffect2 = r2.nextInt(10);
			if (chanceOfEffect2 < 4)
				target.setBurnt(durationOfBurnEffect, burnPower);
		}
		else if(super.getCurrentLevel() == 3){
			Random r3 = new Random();
			int chanceOfEffect3 = r3.nextInt(10);
			if (chanceOfEffect3 < 5)
			target.setBurnt(durationOfBurnEffect, burnPower);
		}	
						
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
			super.setPokemonName("Charmeleon");
			this.gainAttackPower(10); 	// increase attack power by 10 points
			this.increaseRange(1);// increase attack radius by 1
			this.increaseFireRate(0.25); 	// increase the fire rate by one
			this.burnPower = burnPower + 3;
		}else if (super.getCurrentLevel() == 3){
			super.setImageURL(level3);
			super.setPokemonName("Charizard");
			this.gainAttackPower(5); 	// increase attack power by 5 points
			this.increaseRange(0);// increase attack radius by none
			this.increaseFireRate(0.25); 	// increase the fire rate by
			this.burnPower = burnPower + 4;
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
