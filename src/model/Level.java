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

  private Player player; //The person playing this level, passed in constructor
  private Map map; //The map of the level to which enemy waves will be spawned, create with MapFactory class
  private ArrayList<ArrayList<Enemy>> wavesList; //A list of lists of enemies, each a wave. ex: wave1, wave2, etc...
  private Timer timer//Use scheduleAtFixedRate() method and create a TimerTask that will spawn waves at intervals
  private long waveIntervals; //May not be necessary, but could use this for consistent changeable intervals in timer method
                              //It is in miliseconds so it would have to be say 30000 for 30 secs between waves.
  private boolean playerIsAlive; //Can be used to tell if the game is still going and enemies should still be spawned or not
  private int startingHP;
  private int startingMoney;
  
  
  public Level(Player player){
  this.player = player;
  timer = new Timer();
  playerIsAlive = true;
  levelSpecificSetup();
  levelStart();
  }
  
  //Instantiate the rest of the needed instance variables according to specific level and difficulty
  //This will include things like setting player's initial HP and $, creating the waves of enemies you want to
  //send on this level, the time delay in between each enemy wave, the map to play on (use MapFactory to create Maps)
  private abstract void levelSpecificSetup();  
  
  
  //Set the timer with a level specific TimerTask method to call spawnEnemy on map with the waves in waveslist
  //maybe thread.sleep for 1 sec or so between each enemy spawn in the wave or make another timer for that?
  //check for if player is dead at any point in time (while loop?) to stop game
  //if player survives till end call a method to indicate player won the level
  private abstract void levelStart();
  


}
