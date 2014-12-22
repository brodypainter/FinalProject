package model.towers;

import java.util.ArrayList;

import model.enemies.Enemy;
import GUI.GameView.towerType;

/**
 * This is a concrete gym type that extends Gym and Implements serializable
 * @author Max Justice
 * 
 * instance variables
 * final int Cost = 300; 		- these establishes the Gym as a cost of 300
 * 
 * methods
 * Constructor declares a the name of the player and a few other
 * ArrayList<Pokemon> AttackEnemy(ArrayList<Pokemon> enemies)
 * setModifier
 * getModifer
 * boolean increaseFireRate(int amountToIncrease)
 *  boolean checkBuy(int PlayerCurrency)
 *  boolean levelUp()
 */
public class CuboneTower extends Tower{

	/**
	 * 
	 */
	private static final long serialVersionUID = -264213046314837558L;
	
	private String level1= "src/images/tower1Level1.png";
	private String level2= "src/images/tower1Level2.png";
	
	
	public static final int Cost = 300;
	private final int costOfUpgrade1 = 1500;
	private final int maxLevelAttainable = 2;
	
	//String Name, int Attack, int Radius, int FireRateSec, String PlayersName

	public CuboneTower(){
		/* String Name, int Attack, int Radius, double FireRateSec,
					String Image, int cost */
		super("Cubone", 15, 2, 1.0, "src/images/tower1Level1.png", Cost);	
		 setTowerType(towerType.NORMAL);
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
			super.setPokemonName("Marowak");
			this.gainAttackPower(10); 	// increase attack power by 10 points
			this.increaseRange(0);// increase attack radius by 0
			this.increaseFireRate(0.3); 	// increase the fire rate by 0.75
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
		return this.costOfUpgrade1;
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
