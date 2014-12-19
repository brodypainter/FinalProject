package controller;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import controller.GameServer;
import GUI.EnemyImage;
import GUI.GameView.towerType;
import GUI.MainMenu;
import GUI.TowerImage;

public class GameClient{
	
	private GameServer server;
	private MainMenu mainMenu;
	private GameClient thisClient = this;
	
	/**
	 * The constructor
	 * @param gameServer this client's GameServer
	 */
	public GameClient(GameServer gameServer){
		this.server = gameServer;
		mainMenu = new MainMenu(thisClient);
	}
	
	
	/**
	 * This method is called by the GUI to tell the GameServer which Level to create
	 * @param i the levelCode
	 */
	public void createLevel(int i){
		server.createLevel(i);
	}	
	
	
	/**
	 * Called by GUI when player attempts to add a tower
	 * 
	 * @param type the towerType enum of the tower to be created
	 * @param loc the Point (rowdown, columnacross) to attempt to add tower to
	 */
	public void addTower(towerType type, Point loc){
		server.addTower(type, loc);
	}
	
	
	/**
	 * called by the GUI, sends command to server to attempt to sell any Tower at a point
	 * 
	 * @param p The point should contain coordinates (rowsdown, columnsacross) in the grid model
	 */
	public void sellTower(Point p){
		server.sellTower(p);
	}
	
	/**
	 * Called by the GUI, sends command to GameServer to attempt to upgrade a Tower at Point p
	 * @param p the location coordinates (rows, columns) of tower to be upgraded
	 */
	public void upgradeTower(Point p){
		server.upgradeTower(p);
	}
	
	/*//Unnecessary method for now unless we make a player click remove enemy in area type thing
	public void removeEnemy(Point p){
		SendServerEnemyRemoveCommand c = new SendServerEnemyRemoveCommand(p);
		this.sendCommand(c);
	}*/
	

	//
	/**
	 * To be called by a Command from server when a Map is first instantiated on Server.
	 * Passes info about the Map to print to the GUI
	 * @param backgroundImageURL
	 * @param l
	 * @param rowsInMap
	 * @param columnsInMap
	 * @param fromPlayer1 
	 */
	public void mapBackgroundUpdate(String backgroundImageURL, LinkedList<LinkedList<Point>> l, int rowsInMap, int columnsInMap) {
			mainMenu.getView().setMapBackgroundImageURL(backgroundImageURL);
			mainMenu.getView().setEnemyPathCoords(l);
			Point mapSize = new Point(columnsInMap,rowsInMap);
			mainMenu.getView().setGridSize(mapSize);
	}

	//Called by server via command every tick to pass updated enemy/tower image locations/states
	/**
	 * Receives a tower and enemy images list and sends it on to the GUI's model.
	 * @param enemyImages
	 * @param towerImages
	 * @param fromPlayer1 
	 */
	public void update(List<EnemyImage> enemyImages, List<TowerImage> towerImages){
			mainMenu.getView().update(towerImages, enemyImages);
	}
	
	
	/**
	 * Called by GameServer via command when either of these variables change in model
	 * Send new values to the GUI
	 * @param hp the Player's new HP
	 * @param money the Player's new Money
	 * @param fromPlayer1 
	 */
	public void updateHPandMoney(int hp, int money) {
			mainMenu.getView().setPlayerHP(hp);
			mainMenu.getView().setPlayerMoney(money);
	}

	//Called by Server via command whenever a tower attacks an enemy
	//The points pass (rowsdown, columnsacross) in the model grid of tower and enemy
	public void towerAttack(towerType t, Point towerLoc, Point enemyLoc) {
			mainMenu.getView().animateAttack(towerLoc, enemyLoc, t);
	}
	
	//called from Server via command when the game is won
	public void notifyLevelWasWon(){
		JOptionPane.showMessageDialog(mainMenu, "You win");
		mainMenu.setVisible(false);
		mainMenu.getView().setVisible(false);
		mainMenu = new MainMenu(this);
		mainMenu.setVisible(true);
	}
	
	//called from Server via command when the game is lost
	public void notifyLevelWasLost(){
		JOptionPane.showMessageDialog(mainMenu, "You lose");
		mainMenu.setVisible(false);
		mainMenu.getView().setVisible(false);
		mainMenu = new MainMenu(this);
		mainMenu.setVisible(true);
	}
	/**
	 * To be called by GUI when the user wants to either play or pause the game. Server will handle what to do.
	 */
	public void playPauseGame(){
		server.playPauseGame();
	}
	
	/**
	 * To be called by the GUI when user wishes to save the game.
	 * Creates and sends a Command to GameServer to save the game.
	 */
	public void saveGame(){
		server.saveGame();
	}
	
	/**
	 * To be called by the GUI from MainMenu when user wishes to load last saved game.
	 * Creates and sends a Command to GameServer to reconstruct model based on loaded data
	 * and resume gameplay in a paused state.
	 */
	public void loadGame(){
		server.loadGame();
	}
	
	/**
	 * To be called by the GUI when user wishes to speed up the game.
	 * Creates and sends a Command to GameServer to speed up the game.
	 */
	public void speedUpGame(){
		server.speedUp();
	}
	
	/**
	 * To be called by the GUI when user wishes to return to normal speed in the game.
	 * Creates and sends a Command to GameServer to do so.
	 */
	public void normalSpeedGame(){
		server.normalSpeed();
	}

	/**
	 * Notifies the GUI of the proper state of speed and paused buttons. 
	 * @param paused True = paused, False = playing
	 * @param fast True = faster speed, False = normal speed
	 */
	public void changedSpeedState(Boolean paused, Boolean fast) {
		//TODO: Desone, where to send these values to you in GameView?
	}
	
}
