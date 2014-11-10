package GameController;

import java.util.ArrayList;

/**
 * This is a concrete gym type that extends Gym and Implements serializable
 * @author brennan
 *
 */
public class CeruleanGym extends Gym{

	// the Cerulean Gym cost 300 but can be changed if desired
	public static final int Cost = 300;
	//String Name, int Attack, int Radius, int FireRateSec, String PlayersName
	/**
	 * The default settings for the cerulean gym a attack power of 25, a radius of 150 pixels
	 * 3 shots per a second and the Image URL to the gym
	 * @param PlayersName is the only value the gym 
	 */
	public CeruleanGym( String PlayersName){
		super("Cerulean Gym", 25, 150, 3, PlayersName,"ImageURL Here");
		// TODO Auto-generated constructor stub
	}

	// this method sets the animation
	@Override
	public int AttackEnemy(ArrayList<Pokemon> enemies) {
		//TODO is set up the graphics call for attack
		for (Pokemon pokemon : enemies){
			if (canAttackEnemy(enemies.pokemon.getlocation())){
				//TODO Attack enemy with graphics commands
				// Figure out how to implement the fireRate in here??
				pokemon.incomingAttack(super.getAttackPower());
			}
		}
		return 0;
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
	public boolean increaseFireRate(int amountToIncrease) {
		int newFire = super.getFireRate()+ amountToIncrease;
		if (newFire <= 0)
			newFire = 1;
		super.setFireRate(newFire);
		return true;
	}

	@Override
	public boolean checkBuy(int PlayerCurrency) {
		if (PlayerCurrency >= this.Cost)
			return true;
		return false;
	} // end checkBuy

}
