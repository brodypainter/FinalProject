package GameController;

//package src.model;

import GameController.Enemy;
import model.Map;

/**
 * THis is the first concrete class for our pokemon.  It inherits from Pokemon.java
 * and contains nearly all of the methods to be used
 * 
 * Not to much to report here.
 * @author Max Justice
 *
 */
public class Pikachu extends Enemy{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7198976671070674320L;

	/**
	 * The constructor for Pokemon it takes the following variables
	 * @param health for the initial state of the pokemons health
	 * @param attackPower for the attack power should we use attacks to take player health
	 * @param defense The defense modifier.  It takes the attack incoming minus the defense and subtracts from health
	 * @param speed The number (or fraction) of tiles the enemy moves through each second
	 * @param name the name of the monster
	 * @param worth the worth of the monster as it is created
	 * @param Image
	 *
	 *public Pokemon (int health, int attackPower, int defense, int speed, String name, int worth, String Image, Map map)
	 */
	
	//You could just do enemy.setMap(Map currentMap) instead of doing that in the constructor and
	//with the UDLR images I would just make a method you call in the constructor to populate
	//a String Array of all possible images the enemy will use with them -PWH
	
	public Pikachu(Map currentMap) {
		
		
		super(200, 2, 12, 1, "Pikachu", 25, currentMap, "src/images/pikachuDown.gif", "src/images/pikachuUp.gif","src/images/pikachuLeft.gif", "src/images/pikachuRight.gif");
		/* src/images/pikachuRight.gif
		 * src/images/pikachuLeft.gif
		 * src/images/pikachuRight.gif
		 * src/images/pikachuDown.gif
		 */
		
		//String[] allURLImages = new String[] {"src/images/pikachuRight.gif","src/images/pikachuLeft.gif", "src/images/pikachuUp.gif", "src/images/pikachuDown.gif" };
		//super.setAllImageURL(allURLImages);
	}

	@Override
	public String printEnemyStats() {
		
		String stats = new String ("Name: "+ getPokemon() + "\n" +
									"Current Health: " + getHealthPercentage() + "%\n" +
									"Attack: " + getAttackPower() + "\n"+
									"Defense: " + getDefense() + "\n"+
									"Speed: "+ getSpeed() + "\n" +
									"Value: " + getMoney() + "\n"+
									"Modifier: " + /*getModifier() +*/ "\n"+
									"Ability: Pikachu's ");
		
		return stats;
	}

	
}
