package GameController;

import model.Map;

public class Charmander extends Enemy{

	public Charmander(Map currentMap) {
		//  health, attack, defense, speed, name, worth, image and map
		super(120, 15, 7, 1.5, "Charmander", 35, currentMap, "","","","");
		// TODO Auto-generated constructor stub
	}

	@Override
	public String printEnemyStats() {
		
		String stats = new String ("Name: "+ getPokemon() + "\n" +
									"Current Health: " + getHealthPercentage() + "%\n" +
									"Attack: " + getAttackPower() + "\n"+
									"Defense: " + getDefense() + "\n"+
									"Speed: "+ getSpeed() + "\n" +
									"Value: " + getMoney() + "\n"+
									"Special: Charmander has its attack\n"+
									" doubled if it reaches your base\n");
		return stats;
	}
	
	/*
	 * Charmanders special ability is if health is above 50% by the time it reaches the
	 * end of the map its attack increases to 2x
	 */
	
	@Override
	boolean specialPower() {
		// TODO Auto-generated method stub
		return false;
	}
}
