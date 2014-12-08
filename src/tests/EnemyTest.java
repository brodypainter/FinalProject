package tests;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import GameController.*;

public class EnemyTest {
	
	public static void main (String args[]){}
	 /* Point getLocation()			- get the location
	 * boolean setLocation(Point x)	- set the location as it moves
	 * int getMoney()				- this gets the value the pokemon has
	 * String getPokemon()			- get pokemon name
	 * int getAttackPower()
	 * boolean isDead()
	 * boolean incomingAttack(int incomingAttack)
	 * int getSpeed()
	 * boolean setSpeed(int speed)
	 * public String getImageURL()
	 */
	
	/*
	 * test the getters are working correctly
	 */
	@Test
	public void testGetters(){
		PikachuEnemy pika = new PikachuEnemy();
		assertTrue(pika.setLocation( new Point(5,5)));
		assertEquals( pika.getLocation().x, 5);
		assertEquals(pika.getLocation().y, 5);
		assertEquals(pika.getPokemon(),"Pikachu");
		assertEquals(pika.getSpeed(), 5); 	// the default speed is five
	}
	
	/*
	 * Test the setters are working
	 */
	@Test
	public void testSetters(){
		PikachuEnemy pika = new PikachuEnemy();
		assertTrue(pika.setLocation( new Point(5,5)));
		assertTrue(pika.setSpeed(5));
		assertEquals(pika.getSpeed(), 5);
	}
	
	/*
	 * Test that the attack is working correctly
	 */
	@Test
	public void testAttackAndDefense(){
		PikachuEnemy pika = new PikachuEnemy();
		assertTrue(pika.setLocation( new Point(5,5)));
		assertTrue(pika.incomingAttack(25) );
		assertEquals(pika.getHealth(), 87);
		assertTrue(!pika.isDead());
	}
	
	@Test
	public void testDeath(){
		PikachuEnemy pika = new PikachuEnemy();
		assertTrue(pika.setLocation( new Point(5,5)));
		
		assertTrue(pika.incomingAttack(25) ); //25 - 12 = 13  , 100-13 = 87
		assertEquals(pika.getHealth(), 87);
		assertTrue(!pika.isDead());
		
		assertTrue(pika.incomingAttack(25)); // 87 - 13 = 74
		assertEquals(pika.getHealth(), 74);
		assertTrue(!pika.isDead());

		assertTrue(pika.incomingAttack(50)); // 74 - 38 = 36
		assertEquals(pika.getHealth(), 36);
		assertTrue(!pika.isDead());
		
		assertTrue(pika.incomingAttack(50)); // 36 - 38 = -2
		assertEquals(pika.getHealth(), -2);
		assertTrue(pika.isDead());
	}
	
	@Test 
	public void testGetMoney(){
		PikachuEnemy pika = new PikachuEnemy();
		assertTrue(pika.setLocation( new Point(5,5)));
		assertEquals(pika.getMoney(), 25);
	}
	
	@Test
	public void testImage(){
		PikachuEnemy pika = new PikachuEnemy();
		assertTrue(pika.setLocation( new Point(5,5)));
		assertEquals(pika.getImageURL(), "Image URL");
	}

}
