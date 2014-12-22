package model.towers;

import java.util.ArrayList;
import java.util.Random;

import model.enemies.Enemy;
import GUI.GameView.towerType;

public class OddishTower extends Tower{
	
	private static final long serialVersionUID = -8363667321503405956L;
	
	private String level1= "src/images/tower4Level1.png";
	private String level2= "src/images/tower4Level2.png";
	private String level3= "src/images/tower4level3.png";
	
	
	public static final int Cost = 250;
	private final int costOfUpgrade1 = 125;
	private final int costOfUpgrade2 = 185;
	private final int maxLevelAttainable = 3;
	
	private int durationOfSleep = 1;
	//String Name, int Attack, int Radius, int FireRateSec, String PlayersName
	
	public OddishTower(){
		/* String Name, int Attack, int Radius, double FireRateSec, String PlayersName,
					String Image, int cost */
		super("Oddish", 10, 2, 1.0, "src/images/tower4Level1.png", Cost);	
		 setTowerType(towerType.GRASS);
	}

	 //This method will be called by map every tick() as long as tower is not on cooldown from last attack and passed
	 //a list of all enemies currently on the map
	
	@Override
	public boolean attackEnemy(ArrayList<Enemy> enemies) {
		ArrayList<Enemy> targets;
		
		targets = super.findEnemiesInAOERange(enemies);
		
		if (targets.size() == 0){
			return false;
		}
		
		for(Enemy t: targets){
			t.incomingAttack(super.getAttackPower());
			
			if ( super.getCurrentLevel() == 1)
			{
				Random r1 = new Random();
				int chanceOfEffect1 = r1.nextInt(100);
				if (chanceOfEffect1 < 3)
					t.setAsleep(durationOfSleep);
			}
			else if( super.getCurrentLevel() == 2)
			{
					Random r2 = new Random();
					int chanceOfEffect2 = r2.nextInt(100);
					if (chanceOfEffect2 < 5)
						t.setAsleep(durationOfSleep);
			}
				else if(super.getCurrentLevel() == 3)
				{
					Random r3 = new Random();
					int chanceOfEffect3 = r3.nextInt(100);
					if (chanceOfEffect3 < 7)
						t.setAsleep(durationOfSleep);
				}
			
			this.getMap().notifyOfAttack(this.getType(), this.getPosition(), t.getLocation());
		}
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
		this.levelIncrease(); 		// increases the level by one
		if (super.getCurrentLevel() == 2){
			super.setImageURL(level2);
			super.setPokemonName("Gloom");
			this.gainAttackPower(5); 	// increase attack power by 5 points
			this.increaseRange(0);// increase attack radius by none
			this.increaseFireRate(0.1); 	// increase the fire rate by 0.1
		}else if (super.getCurrentLevel() == 3){
			super.setImageURL(level3);
			super.setPokemonName("Vileplume");
			this.gainAttackPower(5); 	// increase attack power by 5 points
			this.increaseRange(0);// increase attack radius by 1
			this.increaseFireRate(0.1); 	// increase the fire rate by 0.1
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
