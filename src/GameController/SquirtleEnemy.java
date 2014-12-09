package GameController;

import model.Map;

public class SquirtleEnemy extends Enemy{
	
	private String idleImg,
					moveLeft,
					moveRight,
					moveUp,
					moveDown;
					
	public SquirtleEnemy(Map mapRef) {
		//health, attackPower, defense, speed, name, worth, Image, mapRef
		// down, up, left, right

		super(95, 8, 25, 0.5, "Squirtle", 30, mapRef, "src/images/enemy3Down.gif","src/images/enemy3Up.gif","src/images/enemy3Left.gif","src/images/enemy3Right.gif");
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
									"Special: Squirtle has a very high\n" +
									"defense\n");
		return stats;
	}

	@Override
	boolean specialPower() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Squirtle's special ability is its defense is very high
	 */
}
