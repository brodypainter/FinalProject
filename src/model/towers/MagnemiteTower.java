package model.towers;

import java.util.ArrayList;
import java.util.Random;

import model.enemies.Enemy;
import GUI.GameView.towerType;

public class MagnemiteTower extends Tower{

	
	private static final long serialVersionUID = 3064458220943780948L;
	
	private String level1= "src/images/tower3Level1.png";
	private String level2= "src/images/tower3Level2.png";
	
	
	
	public static final int Cost = 130;
	private final int costOfUpgrade1 = 670;
	//private final int costOfUpgrade2 = 100;
	private final int maxLevelAttainable = 2;
	
	private int durationOfSlow = 2;

	//String Name, int Attack, int Radius, int FireRateSec, String PlayersName
	/**
	 * The default settings for the cerulean gym a attack power of 25, a radius range of 3 tiles,
	 * 3 shots per a second and the Image URL to the gym
	 * @param PlayersName is the only value the gym 
	 */
	public MagnemiteTower(){
		/* String Name, int Attack, int Radius, double FireRateSec, String PlayersName,
					String Image, int cost */
		super("Magnemite", 20, 3, 1.25, "src/images/tower3Level1.png", Cost);	
		 setTowerType(towerType.ELECTRIC);
	}

	 
	 //This method will be called by map every tick() as long as tower is not on cooldown from last attack and passed
	 //a list of all enemies currently on the map
	
	@Override
	public boolean attackEnemy(ArrayList<Enemy> enemies){
	
		Enemy target;
		
		target = super.findEarliestEnemyInRange(enemies);
		
		if(target == null){
			return false;
		}
		
		target.incomingAttack(this.getAttackPower());
	
			if ( super.getCurrentLevel() == 1)
			{
				Random r1 = new Random();
				int chanceOfEffect1 = r1.nextInt(2);
				if (chanceOfEffect1 == 0)
					target.setSlowed(durationOfSlow);
			}
			else if( super.getCurrentLevel() == 2)
			{
					Random r2 = new Random();
					int chanceOfEffect2 = r2.nextInt(4);
					if (chanceOfEffect2 < 3)
						target.setSlowed(durationOfSlow + 1);
			}
				else if(super.getCurrentLevel() == 3)
				{//could make a magnezone evoultion i guess
					Random r3 = new Random();
					int chanceOfEffect3 = r3.nextInt(3);
					if (chanceOfEffect3 == 0)
						target.setSlowed(durationOfSlow + 2);
				}
			
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
		this.levelIncrease(); 		// increases the leve by one
		
		if (super.getCurrentLevel() == 2){
			super.setImageURL(level2);
			super.setPokemonName("Magnetite");
			this.gainAttackPower(8); 	// increase attack power by 8 points
			this.increaseRange(2);// increase attack radius by 2
			this.increaseFireRate(0.25); 	// increase the fire rate by 0.25
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
		return costOfUpgrade1;
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
