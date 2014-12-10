package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import server.GameServer;
import GameController.Enemy;
import client.Player;

/**
* An abstract class that will hold a concrete extension of Map, and an arraylist of arraylists of enemies as waves
* to spawn on the map. Each extending Level class will have
* their own unique waves/times/starting gold amount and follow the naming template: "Level<#><Difficulty>" for instance
* Level0. Use MapFactory to create the appropriate Map object. Creating and running an extension of this class will
* actually cause the game model to run. Should be selected by player in GUI level selection which level for Server to instantiate.
*/

public abstract class Level implements Serializable{

  /**
	 * 
	 */
	private static final long serialVersionUID = -623636919171014876L;
private GameServer server; //Needs to know which server it is on so that it can call the server to start its global timer
  private Player player; //The person playing this level, passed in constructor
  private Map map; //The map of the level to which enemy waves will be spawned, create with MapFactory class
  private ArrayList<ArrayList<Enemy>> wavesList; //A list of lists of enemies, each a wave. ex: wave1, wave2, etc...
  private long waveIntervals; //Use this for consistent changeable intervals between waves.
                              //It is in milliseconds so it would have to be say 30000 for 30 secs between waves.
  private long enemySpawnIntervals; //The time in milliseconds between each spawning of an enemy within a wave
  private boolean playerIsAlive; //Can be used to tell if the game is still going and enemies should still be spawned or not
  private long timeSinceLastWave; //The time in milliseconds since the last enemy in last wave spawned
  private long timeSinceLastEnemySpawned; //The time in ms since last enemy within the wave was spawned
  private boolean waveInProgress; //True if a wave is still in progress, false if not
  private int enemyIndexCounter;//The index of the enemy to spawn next in the wave
  private int waveIndexCounter;//The index of the enemy wave to send next in the wavesList
  private boolean enemiesLeftToSpawn;//True if enemies left to spawn on the level, false if not
  
  public Level(Player player, GameServer server){
	  this.player = player;
	  this.server = server;
	  timeSinceLastWave = 0;
	  waveInProgress = false;
	  enemiesLeftToSpawn = true;
	  setPlayerIsAlive(true);
	  levelSpecificSetup();
	  levelStart();
  }
  
  

  //Instantiate the rest of the needed instance variables according to specific level and difficulty
  //This will include things like setting player's initial HP and $, creating the waves of enemies you want to
  //send on this level, the time delay in between each enemy wave, the map to play on (use MapFactory to create Maps)
  public void levelSpecificSetup(){
	  createWaves();
	  setPlayerStartingHP();
	  setPlayerStartingMoney();
	  setWaveDelayIntervals();
	  setEnemySpawnDelayIntervals();
	  setMap();
  }
  
  public abstract void createWaves(); //create wavesList and populate it with enemies specific to each Level
  public abstract void setPlayerStartingHP();//Self explanatory...etc.
  public abstract void setPlayerStartingMoney();
  public abstract void setWaveDelayIntervals();
  public abstract void setEnemySpawnDelayIntervals();
  public abstract void setMap(); //Use MapFactory to generate a map to set the map to; say MapFactory.generateMap(...)
  
  //Call server to start its global timer
  public void levelStart(){
	  server.startTimer(); //Starts the Master Timer on the server	 
  }
  
  //That game loop doe -PH
  /**
   * Every time the Master Timer in GameServer ticks (after it has been started
   * by this newly instantiated Level), this method will be called. Whether
   * the game has been won or lost is first checked, then if there are still
   * enemies left to spawn, 1 of 2 cooldown timers are incremented.
   * If a wave is in progress, the timeSinceLastEnemySpawned tracker is incremented.
   * If a wave is not in progress, the timeSinceLastWave tracker is incremented.
   * Once either of these trackers become greater than their set cooldown interval,
   * either another enemy is spawned or another wave is started. Once the last
   * enemy of a wave has been spawned, it checks if there are any more waves
   * and sets waveInProgress to false, and enemiesLeftToSpawn to false
   * if there are no more waves. The game ends when there are no more enemies
   * to spawn and no more enemies alive on the board, or when the players HP falls to 0.
   */
  public void tick(int timePerTick){
	  if(!gameOver()){
	  if(enemiesLeftToSpawn){
		  if(!waveInProgress){
			  timeSinceLastWave = timeSinceLastWave + timePerTick;
			  if(timeSinceLastWave >= waveIntervals){
				  waveInProgress = true;
				  timeSinceLastWave = 0;
			  }
		  }	  
		  if(waveInProgress){
			  timeSinceLastEnemySpawned = timeSinceLastEnemySpawned + timePerTick;
			  if(timeSinceLastEnemySpawned >= enemySpawnIntervals){
				  if(enemyIndexCounter < wavesList.get(waveIndexCounter).size()){
					  map.spawnEnemy(wavesList.get(waveIndexCounter).get(enemyIndexCounter));
					  timeSinceLastEnemySpawned = 0; //reset time counter
					  enemyIndexCounter++;
				  }else{//All the enemies in the wave have been spawned
					  waveInProgress = false;
					  timeSinceLastEnemySpawned = 0;
					  enemyIndexCounter = 0;
					  waveIndexCounter++;
					  if(waveIndexCounter == wavesList.size()){
						  //All enemies in the level have been spawned
						  enemiesLeftToSpawn = false;
					  }
				  }
			  }
		  }
	  }
	  } //if Game is not over...
 }
  
  // gameover method checks if player health is less than 0 and calls you lose or you win methods to advance
  public boolean gameOver(){
		if(this.player.getHealthPoints() <= 0){
			youLose();
			return true;
		}else if (!enemiesLeftToSpawn && map.getEnemies().isEmpty() && this.player.getHealthPoints() > 0){
			youWin();
			return true;
		}
		return false;
	}
	
	public boolean youLose(){
		server.gameLost();
		return false;
	}
	
	public boolean youWin(){
		server.gameWon();
		return false;
	}
	
	//I think this method is being bypassed, Map just calls GameServer directly -PWH
	// this updates the player info after a game is won or lost and saves it
	public void notifyPlayerInfoUpdated(){
		server.updateClients(player.getHealthPoints(), player.getMoney());
	}
  
	// gets the map of the current level
	public Map getMap(){
	  return map;
	}
  
	public void setMap(Map map){
	  this.map = map;
	}
	
	public GameServer getServer(){
		return server;
	}

	public Player getPlayer(){
	  return this.player;
  }

  public ArrayList<ArrayList<Enemy>> getWavesList() {
	return wavesList;
  }

  public void setWavesList(ArrayList<ArrayList<Enemy>> wavesList) {
	this.wavesList = wavesList;
  }

  
  public long getWaveIntervals() {
	return waveIntervals;
  }

  public void setWaveIntervals(long waveIntervals) {
	this.waveIntervals = waveIntervals;
  }

  public boolean isPlayerIsAlive() {
	return playerIsAlive;
  }

  //set the spawn intervals for the pokemon
  public void setEnemySpawnIntervals(long length){
	  this.enemySpawnIntervals = length;
  }
  
  //sets the current player to being alive
  private void setPlayerIsAlive(boolean b) {
	this.playerIsAlive = b;
	
  }
}
