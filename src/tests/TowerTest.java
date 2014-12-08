package tests;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.Test;

import GameController.*;

public class TowerTest {

	public static void main (String args[]){}

	@Test
	public void testGetters(){
		
		//TODO change below code to reflect refactor from Gym to Tower
		
		//super("Cerulean Gym", 25, 150, 3, PlayersName,"ImageURL Here");
		//String Name, int Attack, int Radius, int FireRateSec, String PlayersName

		TowerOne gym = new TowerOne("Max Justice");
		
		gym.setPlaceOnBoard(new Point (25,25));
		
		assertEquals(gym.getAttackPower(), 25);
		assertEquals(gym.getCurrentLevel(), 1);
		assertEquals(gym.getFireRate(), 3);
		assertEquals(gym.getGymName(), "Cerulean Gym");
		assertEquals(gym.getRange(), 150);
		assertEquals(gym.getGymOwner(), "Max Justice");
		assertEquals(gym.getImageURL(), "ImageURL Here");
		assertEquals(gym.getPosition().x, 25);
		assertEquals(gym.getPosition().y, 25);
		
	}
	
	@Test
	public void testSetters(){
		
	}
	
	@Test
	public void testAttacks(){
		
		
		TowerOne gym = new TowerOne("Max Justice");
		
		gym.setPlaceOnBoard(new Point (25,25));
		
		ArrayList<Enemy> badguys = new ArrayList<Enemy>();
		Pikachu pika1 = new Pikachu();
		pika1.setLocation( new Point(5,5));
		Pikachu pika2 = new Pikachu();
		pika2.setLocation( new Point(50,50));
		Pikachu pika3 = new Pikachu();
		pika3.setLocation( new Point(100,100));
		Pikachu pika4 = new Pikachu();
		pika4.setLocation( new Point(300,300));	
		badguys.add(pika1);
		badguys.add(pika2);
		badguys.add(pika3);
		//badguys.add(pika4);
		
		assertEquals(gym.AttackEnemy(badguys), 0);
		
		assertTrue(badguys.isEmpty());
		
		assertTrue(!gym.canAttackEnemy(pika4.getLocation()));
		
	}
	
	@Test
	public void testBuy(){
		TowerOne gym = new TowerOne("Max Justice");

		assertEquals(TowerOne.Cost, 300);
		assertTrue(gym.checkBuy(3000));
	}

	@Test
	public void testIncreasesToAtt(){
		TowerOne gym = new TowerOne("Max Justice");
		
		assertTrue(gym.increaseFireRate(5));
		assertEquals(gym.getFireRate(),8);
		
		gym.levelUp();
		/*
		this.levelIncrease(); 		// increases the leve by one
		this.setAttackPower(5); 	// increase attack power by 5 poins
		this.modifyAttackRadius(25);// increase attack radius to 25 pixels
		this.increaseFireRate(1); 	// increase the fire rate by one*/
		
		assertEquals(gym.getCurrentLevel(),2);
		assertEquals(gym.getAttackPower(), 30);		
		assertEquals(gym.getRange(), 175);
		assertEquals(gym.getFireRate(), 9);
	}
}
