package GameController;

import java.awt.Point;

import model.Map;

public class GrowlitheEnemy extends Enemy{

	
	private int attackPower;
	public GrowlitheEnemy(Map currentMap) {
		
		//  health, attack, defense, speed, name, worth, image and map
		super(80, 18, 0, 1.0, "Growlithe", 110
				, currentMap, "src/images/enemy7Down.gif","src/images/enemy7Up.gif","src/images/enemy7Left.gif","src/images/enemy7Right.gif");
		// TODO Auto-generated constructor stub
		this.attackPower=15;
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
	 * Growlithe special ability is if health is above 50% by the time it reaches the
	 * end of the map its attack increases to 2x
	 */
	
	@Override
	boolean specialPower() {
		// TODO Auto-generated metod stub
		Point current = super.getLocation();
		Point previous = super.getPreviousLocation();
		if (current.x == previous.x && current.y == previous.y){
			if (super.getHealthPercentage() >= 50){
				super.levelUpAttackPower(this.attackPower*2);
				return true;
			}
		}
		
		return false;
	}
}
