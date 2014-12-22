package model.towers;

import java.util.ArrayList;
import java.util.Random;

import model.enemies.Enemy;
import GUI.GameView.towerType;

public class MagnemiteTower extends Tower{

	/**
	 * magnemite will slow the enemy
	 */
	private static final long serialVersionUID = 3064458220943780948L;
	
	
	private String level1= "src/images/tower3Level1.png";
	private String level2= "src/images/tower3Level2.png";
	
	
	// the Cerulean Gym cost 300 but can be changed if desired
	public static final int Cost = 130;
	private final int costOfUpgrade1 = 870;
	//private final int costOfUpgrade2 = 100;
	private final int maxLevelAttainable = 2;
	
	private int durationOfSlow = 2;
	private int multiSlow = 1;
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
		// TODO Auto-generated constructor stub
	}

	/**
	 * (non-Javadoc)
	 * @see model.towers.Tower#AttackEnemy(java.util.ArrayList)
	 * Attack Enemy algorithm is simply.  It is passed the arraylist
	 * of current enemies on the board that are alive.  It then runs a 
	 * for loop for each enemy getting their current point on the board
	 * checks if that enemy is within range with canAttackEnemy() method which
	 * returns true if it can be attacked.  It then uses the incomingAttack()
	 * method from the pokemon object and subtracts from the current pokemons
	 * instance of its health.  
	 * 
	 * TODO add the images and sprites for the attack.  Update the list of
	 * when a pokemon dies to the listeners or observers.  If a pokemon faints
	 * add currency to the player.
	 */
	 
	 //This method will be called by map every tick() as long as tower is not on cooldown from last attack and passed
	 //a list of all enemies currently on the map
	@Override
	public boolean AttackEnemy(ArrayList<Enemy> enemies) {
		
		Enemy myClosestEnemy;
		
		//change method to attack enemy shortest along path
		myClosestEnemy = super.findClosestEnemy(enemies);
		
		if (myClosestEnemy == null)
			return false;
		
		if ( canAttackEnemy(myClosestEnemy.getLocation())){
			myClosestEnemy.incomingAttack(super.getAttackPower());
			
			if ( super.getCurrentLevel() == 1)
			{
				Random r1 = new Random();
				int chanceOfEffect1 = r1.nextInt(2);
				if (chanceOfEffect1 == 0)
					myClosestEnemy.setSlowed(durationOfSlow);
			}
			else if( super.getCurrentLevel() == 2)
			{
					Random r2 = new Random();
					int chanceOfEffect2 = r2.nextInt(4);
					if (chanceOfEffect2 < 3)
						myClosestEnemy.setSlowed(durationOfSlow + multiSlow);
			}
				else if(super.getCurrentLevel() == 3)
				{//could make a magnezone evoultion i guess
					Random r3 = new Random();
					int chanceOfEffect3 = r3.nextInt(3);
					if (chanceOfEffect3 == 0)
						myClosestEnemy.setSlowed(durationOfSlow+multiSlow+multiSlow);
				}
			
			getMap().notifyOfAttack(this.getType(), this.getPosition(), myClosestEnemy.getLocation());
				
			
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
		
		if (super.getCurrentLevel() == 2){
			super.setImageURL(level2);
			super.setPokemonName("Magnetite");
			this.setAttackPower(20); 	// increase attack power by 20 poins
			this.modifyAttackRadius(2);// increase attack radius to 25 pixels
			this.increaseFireRate(0.25); 	// increase the fire rate by one
		}
		return true;
	}

	@Override
	public String printTowerStats() {
		
		String stats = new String ("Name: "+ getGymName() + "\n" +
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
