package GameController;

import model.Map;

public class Bulbasaur extends Enemy{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9072421647945278536L;

	public Bulbasaur(int health, int attackPower, int defense, double speed,
			String name, int worth, String Image, Map mapRef) {
		//health, attackPower, defense, speed, name, worth, Image, mapRef
		super(health, attackPower, defense, speed, name, worth, Image, mapRef);
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
									"Special: Bulbasaur's special ability is\n" +
									"it can regain is health slowly\n");
		return stats;
	}

	/**
	 * Bulbasaur's special ability is can regain health slowly as it progresses
	 */
}
