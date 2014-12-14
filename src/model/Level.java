package model;

import java.io.Serializable;
import java.util.ArrayList;
import server.GameServer;
import GameController.Enemy;
import client.Player;

/**
* An abstract class that will hold a concrete extension of Map, and a 2D arraylist of enemies as waves
* to spawn on the map. Each extending Level class will have
* their own unique waves/times/starting gold amount and follow the naming template: "Level<#><Difficulty>" for instance
* Level0. Use MapFactory to create the appropriate Map object. Creating and running an extension of this class will
* actually cause the game model to run. Should be selected by player in GUI level selection which level for Server to instantiate.
* 
* @author Peter Hanson
* @version 1.0
*/


public abstract class Level implements Serializable {
 
  private static final long serialVersionUID = 4903194688398376628L;
  private transient GameServer server; //Needs to know which server it is on so that it can call the server to start its global timer
  private Player player1; //The person playing this level, passed in constructor
  private Player player2; //The partner Player if on multiplayer
  private Map map1; //The map of the level to which enemy waves will be spawned, create with MapFactory class
  private Map map2; //The map for player2, should be the same type as map1
  private ArrayList<ArrayList<Enemy>> wavesList1; //A list of lists of enemies, each a wave. ex: wave1, wave2, etc...
  private ArrayList<ArrayList<Enemy>> wavesList2; //The enemies to spawn for player2's map
  private boolean multiplayer; //True if multiplayer, false if not
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
	  this.player1 = player;
	  this.server = server;
	  multiplayer = server.isMultiplayer();
	  if(multiplayer){
		  this.player2 = player1.getPartner();
	  }
	  timeSinceLastWave = 0;
	  waveInProgress = false;
	  enemiesLeftToSpawn = true;
	  setPlayerIsAlive(true);
	  levelSpecificSetup();
	  this.setPlayer2StartingValues();
	  levelStart();
  }
  
  

  //Instantiate the rest of the needed instance variables according to specific level and difficulty
  //This will include things like setting player's initial HP and $, creating the waves of enemies you want to
  //send on this level, the time delay in between each enemy wave, the map to play on (use MapFactory to create Maps)
  public void levelSpecificSetup(){
	  setMap();
	  createWaves();
	  setPlayerStartingHP();
	  setPlayerStartingMoney();
	  setWaveDelayIntervals();
	  setEnemySpawnDelayIntervals();
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
				  if(enemyIndexCounter < wavesList1.get(waveIndexCounter).size()){
					  map1.spawnEnemy(wavesList1.get(waveIndexCounter).get(enemyIndexCounter));
					  if(multiplayer){
						  map2.spawnEnemy(wavesList2.get(waveIndexCounter).get(enemyIndexCounter)); 
					  //I hope this works, each wave must be same size for it do work otherwise index out of bounds errors may occur
					  }
					  timeSinceLastEnemySpawned = 0; //reset time counter
					  enemyIndexCounter++;
				  }else{//All the enemies in the wave have been spawned
					  waveInProgress = false;
					  timeSinceLastEnemySpawned = 0;
					  enemyIndexCounter = 0;
					  waveIndexCounter++;
					  if(waveIndexCounter == wavesList1.size()){
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
		if(this.player1.getHealthPoints() <= 0){
			youLose();
			return true;
		}else if (!enemiesLeftToSpawn && map1.getEnemies().isEmpty() && this.player1.getHealthPoints() > 0){
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
	public void notifyPlayerInfoUpdated(int hp, int m, boolean b){
		server.updateClients(hp, m, b);
	}
  
	// gets the map of the current level
	public Map getMap1(){
	  return map1;
	}
	
	public Map getMap2(){
		return map2;
	}
  
	public void setMap1(Map map){
	  this.map1 = map;
	}
	
	public GameServer getServer(){
		return server;
	}

	public Player getPlayer1(){
	  return this.player1;
  }

  public ArrayList<ArrayList<Enemy>> getWavesList() {
	return wavesList1;
  }

  public void setWavesList(ArrayList<ArrayList<Enemy>> wavesList) {
	this.wavesList1 = wavesList;
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

  /**
   * To be called by GameServer during Loading to reset transient GameServer variable
   * @param thisServer the GameServer to set
   */
  public void setServer(GameServer thisServer) {
	server = thisServer;
  }

  private void setPlayer2StartingValues(){
	  if(multiplayer){
		player2.setHealth(player1.getHealthPoints());
		player2.setMoney(player1.getMoney());
		notifyPlayerInfoUpdated(player2.getHealthPoints(), player2.getMoney(), false);
		map2 = MapFactory.generateMap(player2, map1.getMapTypeCode());
		map2.setServer(server);
		wavesList2 = new ArrayList<ArrayList<Enemy>>();
		//wavesList2 = wavesList1 backwards to vary between players
		for(int i = wavesList1.size() - 1; i > -1; i--){
			ArrayList<Enemy> tempList = new ArrayList<Enemy>();
			for(int j = wavesList1.get(i).size() - 1; j > -1; j-- ){
				Enemy e = wavesList1.get(i).get(j);
				tempList.add(e);
			}
			wavesList2.add(tempList);
		}
	  }
  }
  
}
