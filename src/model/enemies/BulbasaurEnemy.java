package model.enemies;

import java.util.ArrayList;
import java.util.Random;

import model.maps.Map;

public class BulbasaurEnemy extends Enemy{

	/**
	 * 
	 */
	private int health;
	private static final long serialVersionUID = 9072421647945278536L;

	public BulbasaurEnemy(Map mapRef) {
		//health, attackPower, defense, speed, name, worth, Image, mapRef
		// images are down, up, left, right in the constructor
		
																				// down, up, left, right

		super(400, 2, 7, 0.65, "Bulbasaur", 120, mapRef, "src/images/enemy2Down.gif", "src/images/enemy2Up.gif", "src/images/enemy2Left.gif", "src/images/enemy2Right.gif");
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
	 * Bulbasaur's special ability is can regen itself and allies every 33% of step
	 */
	@Override
	boolean specialPower() {
		Random r = new Random();
		int value = r.nextInt(3);
		if (value == 0){
			ArrayList<Enemy> allies = new ArrayList<Enemy>(this.getMap().getEnemies());
			for(Enemy a:allies){
				a.heal(20);
			}
		}
		
		return true;
	}
}
