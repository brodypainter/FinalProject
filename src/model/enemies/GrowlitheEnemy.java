package model.enemies;

import java.awt.Point;

import model.maps.Map;

public class GrowlitheEnemy extends Enemy{

	
	private static final long serialVersionUID = -514019104475232652L;

	public GrowlitheEnemy(Map currentMap) {
		
		//  health, attack, defense, speed, name, worth, image and map
		super(160, 15, 3, 1.0, "Growlithe", 110
				, currentMap, "src/images/enemy7Down.gif","src/images/enemy7Up.gif","src/images/enemy7Left.gif","src/images/enemy7Right.gif");
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
									"Special: Growlithe has its attack\n"+
									" doubled if it reaches your base\n");
		return stats;
	}
	
	/*
	 * Growlithe special ability is if health is higher its attack is too
	 */
	
	@Override
	public boolean specialPower() {
		//set attack = to 8 + 7(hp%) every step
		
		return true;
	}
}
