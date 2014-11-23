package model;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import server.GameServer;
import GameController.Enemy;
import client.Player;

//Not finished by any means this is just a start, Max feel free to take over this class ask me if you have any questions -Peter
//I did not write this in eclipse so import all the needed classes whoever updates next please.

/**
* An abstract class that will hold a concrete extention of Map, and an arraylist of arraylists of enemies as waves
* to spawn on the map. May also want its own timer to schedule wave spawning. Each extending Level class will have
* their own unique waves/times/starting gold amount and follow the naming template: "Level<#><Difficulty>" for instance
* Level0Easy. Use MapFactory to create the appropriate Map object. Creating and running an extention of this class will
* actually cause the game model to run. Should be selected by player in GUI level selection which level for Server to instantiate.
*/

public abstract class Level {

  private GameServer server; //Needs to know which server it is on so that it can call the server to start its global timer
  private Player player; //The person playing this level, passed in constructor
  private Map map; //The map of the level to which enemy waves will be spawned, create with MapFactory class
  private ArrayList<ArrayList<Enemy>> wavesList; //A list of lists of enemies, each a wave. ex: wave1, wave2, etc...
  private Timer timer;//Use scheduleAtFixedRate() method and create a TimerTask that will spawn waves at intervals
  private TimerTask enemySpawnTask;
  private long waveIntervals; //May not be necessary, but could use this for consistent changeable intervals in timer method
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
  public abstract void setPlayerStartingHP();
  public abstract void setPlayerStartingMoney();
  public abstract void setWaveDelayIntervals();
  public abstract void setEnemySpawnDelayIntervals();
  public abstract void setMap(); //Use MapFactory to say MapFactory.generateMap(...)
  
  //Call server to start its global timer
  //check for if player is dead at any point in time (while loop?) to stop game
  //if player survives till end call a method to indicate player won the level
  public void levelStart(){
	  server.startTimer(); //Starts the Master Timer on the server	 
  }
  
  //That game loop doe -PH
  public void tick(){
	  if(!gameOver()){
	  if(enemiesLeftToSpawn){
		  if(!waveInProgress){
			  timeSinceLastWave = timeSinceLastWave + server.getTickLength();
			  if(timeSinceLastWave >= waveIntervals){
				  waveInProgress = true;
				  timeSinceLastWave = 0;
			  }
		  }	  
		  if(waveInProgress){
			  timeSinceLastEnemySpawned = timeSinceLastEnemySpawned + server.getTickLength();
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
	  }
  }
  
  public boolean gameOver(){
		if(this.player.getHealthPoints() < 0){
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
 
  
  
  
  public Map getMap(){
	  return map;
  }
  
  public void setMap(Map map){
	  this.map = map;
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

  public TimerTask getEnemySpawnTask() {
	return enemySpawnTask;
  }

  public void setEnemySpawnTask(TimerTask enemySpawnTask) {
	this.enemySpawnTask = enemySpawnTask;
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

  public void setEnemySpawnIntervals(long length){
	  this.enemySpawnIntervals = length;
  }
  private void setPlayerIsAlive(boolean b) {
	this.playerIsAlive = b;
	
  }
}
