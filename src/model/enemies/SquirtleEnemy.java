package model.enemies;

import model.maps.Map;

public class SquirtleEnemy extends Enemy{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8663719648002595599L;

					
	public SquirtleEnemy(Map mapRef) {
		//health, attackPower, defense, speed, name, worth, Image, mapRef
		// down, up, left, right

		super(300, 3, 12, 0.6, "Squirtle", 140, mapRef, "src/images/enemy3Down.gif","src/images/enemy3Up.gif","src/images/enemy3Left.gif","src/images/enemy3Right.gif");
		this.isTaunter();
	}

	@Override
	public String printEnemyStats() {
		
		String stats = new String ("Name: "+ getPokemon() + "\n" +
									"Current Health: " + getHealthPercentage() + "%\n" +
									"Attack: " + getAttackPower() + "\n"+
									"Defense: " + getDefense() + "\n"+
									"Speed: "+ getSpeed() + "\n" +
									"Value: " + getMoney() + "\n"+
									"Special: Squirtle has a very high\n" +
									"defense\n");
		return stats;
	}

	@Override
	boolean specialPower() {
		//taunt
		return false;
	}
	
	/**
	 * Squirtle's special ability is its defense is very high
	 */
}
