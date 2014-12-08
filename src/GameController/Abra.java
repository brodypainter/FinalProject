package GameController;

import model.Map;

public class Abra extends Enemy{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1618919839702832946L;

	public Abra(Map mapRef) {
		//health, attackPower, defense, speed, name, worth, Image, mapRef
		super(50, 5, 5, 3.0, "Abra", 20, mapRef, "","","","");
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
									"Special: Abra's special is it can\n" +
									"teleport to dodge attacks\n");
		return stats;
	}
		
	/**
	 * Abra's special ability is it can teleport to either a new location on the board
	 * or teleport that the towers attack misses it and its fast
	 */
	@Override
	boolean specialPower() {
		// TODO Auto-generated method stub
		return false;
	}
}
