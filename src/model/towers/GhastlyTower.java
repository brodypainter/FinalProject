package model.towers;

import java.util.ArrayList;
import java.util.Random;

import model.enemies.Enemy;
import GUI.GameView.towerType;

public class GhastlyTower extends Tower {

	
	private static final long serialVersionUID = 1056434548730766900L;
	private String level1= "src/images/tower5Level1.png";
	private String level2= "src/images/tower5Level2.png";
	private String level3= "src/images/tower5level3.png";


	public static final int Cost = 150;
	private final int costOfUpgrade1 = 130;
	private final int costOfUpgrade2 = 720;
	private final int maxLevelAttainable = 3;
	
	private int cursedDefence = 2;
	
	public GhastlyTower(){
		/* String Name, int Attack, int Radius, double FireRateSec,
					String Image, int cost */
		super("Ghastly", 32, 3, 0.7, "src/images/tower5Level1.png", Cost);	
		 setTowerType(towerType.POISON);
	
	}

	 
	 public boolean attackEnemy(ArrayList<Enemy> enemies) {
		
		Enemy target;
		
		target = super.findFarthestEnemyInRange(enemies);
		
		if (target == null)
			return false;
		
		if ( canAttackEnemy(target.getLocation())){
			target.incomingAttack(super.getAttackPower());
			
			if ( super.getCurrentLevel() == 1)
			{
				Random r1 = new Random();
				int chanceOfEffect1 = r1.nextInt(10);
				if (chanceOfEffect1 < 5){
					target.setCurse();
				}
					
			}
			else if( super.getCurrentLevel() == 2)
			{
					Random r2 = new Random();
					int chanceOfEffect2 = r2.nextInt(10);
					if (chanceOfEffect2 < 6){
						target.setCurse();
					}
						
			}
				else if(super.getCurrentLevel() == 3)
				{
					Random r3 = new Random();
					int chanceOfEffect3 = r3.nextInt(10);
					if (chanceOfEffect3 < 7){
						target.setCurse();
					}
						
				}
			
			getMap().notifyOfAttack(this.getType(), this.getPosition(), target.getLocation());
				
			
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
		this.levelIncrease(); 		// increases the leve by one

		if (super.getCurrentLevel() == 2){
			super.setImageURL(level2);
			super.setPokemonName("Haunter");
			this.gainAttackPower(13); 	// increase attack power by 5 poins
			this.increaseRange(0);// increase attack radius to 25 pixels
			this.increaseFireRate(0.15); 	// increase the fire rate by one
		}
		else if (super.getCurrentLevel() == 3){
			super.setImageURL(level3);
			super.setPokemonName("Gengar");
			this.gainAttackPower(20); 	// increase attack power by 5 poins
			this.increaseRange(1);// increase attack radius to 25 pixels
			this.increaseFireRate(0.15); 	// increase the fire rate by one
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
		if (this.getCurrentLevel() == 1)
			return this.costOfUpgrade1;
		return this.costOfUpgrade2;
	}

	@Override
	public boolean upgradeCurrentTower(int playersCoins) {
		if (getCostOfLevelingUp() <= playersCoins && super.getCurrentLevel() < maxLevelAttainable){
			levelUp();
			return true;
		}
		return false;
	}
}
