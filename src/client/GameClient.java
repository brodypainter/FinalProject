package client;

import java.awt.Point;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import server.GameServer;
import GUI.EnemyImage;
import GUI.GameView.towerType;
import GUI.MainMenu;
import GUI.TowerImage;
import commands.Command;
import commands.DisconnectCommand;
import commands.ServerCreateLevelCommand;
import commands.ServerMessageCommand;
import commands.ServerTowerCommand;
import commands.ServerTowerRemoveCommand;
import commands.loadGameCommand;
import commands.normalSpeedCommand;
import commands.playPauseCommand;
import commands.saveGameCommand;
import commands.speedUpCommand;
//import commands.upgradeTowerCommand;

public class GameClient{
	private String clientName = "Tester"; // user name of the client
	
	// Hardcoded values for testing
	private String host = "localhost";
	private String port = "9001";
	
	private Socket server; // connection to server
	private ObjectOutputStream out; // output stream
	private ObjectInputStream in; // input stream
	private MainMenu mainMenu;
	private Player player;
	
	public static void main(String[] args){
		new GameClient();
	}
	
	/**
	 * Sends a String message to the Server
	 * 
	 * @param message
	 */
	public void newMessage(String message) {
		Command<GameServer> c = new ServerMessageCommand(message);
		this.sendCommand(c);
	}

	/**
	 * This class reads and executes commands sent from the server
	 * 
	 * @author Brody Painter
	 *
	 */
	private class ServerHandler implements Runnable{
		public void run() {
			while(true){
				try{
					// read a command from server and execute it
					/*
					 * GET RID OF THE LOOP HERE AND FIND ANOTHER WAY TO WAIT FOR AN OBJECT!
					 * 
					 */
					//System.out.println("Read Object");
					@SuppressWarnings("unchecked")
					Command<GameClient> c = (Command<GameClient>)in.readObject();
					c.execute(GameClient.this);
					//System.out.println(in.readObject());
				}
	//				return; // "gracefully" terminate after disconnect
	//			}
				catch (EOFException e) {
					return; // "gracefully" terminate
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	public GameClient(){
		// ask the user for a host, port, and user name
		 String host = JOptionPane.showInputDialog("Host address:");
		 String port = JOptionPane.showInputDialog("Host port:");
		 clientName = JOptionPane.showInputDialog("User name:");
		
		
		if (host == null || port == null || clientName == null)
			return;
		
		try{
			// Open a connection to the server
			server = new Socket(host, Integer.parseInt(port));
			out = new ObjectOutputStream(server.getOutputStream());
			in = new ObjectInputStream(server.getInputStream());
			
			// write out the name of this client
			out.writeObject(clientName);
			
			// TODO send a disconnect command to the server to safely disconnect
			// DOES THIS STILL BREAK EVERYTHING?
			// add a listener that sends a disconnect command to when closing
//			this.addWindowListener(new WindowAdapter(){
//				public void windowClosing(WindowEvent arg0) {
//					try {
//						out.writeObject(null);
//						out.close();
//						in.close();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//			});
			
			player = (Player) in.readObject();
			//System.out.println("Player Received");
			
			// start a thread for handling server events
			new Thread(new ServerHandler()).start();
		
			
		}catch(Exception e){
			e.printStackTrace();
		}

		mainMenu = new MainMenu(this);
		mainMenu.setPlayer(player);
		
	}
	
	public GameClient(String string) {
		// ask the user for a host, port, and user name
		// String host = JOptionPane.showInputDialog("Host address:");
		// String port = JOptionPane.showInputDialog("Host port:");
		// clientName = JOptionPane.showInputDialog("User name:");
		this.clientName = string;
		
		if (host == null || port == null || clientName == null)
			return;
		
		try{
			// Open a connection to the server
			server = new Socket(host, Integer.parseInt(port));
			out = new ObjectOutputStream(server.getOutputStream());
			in = new ObjectInputStream(server.getInputStream());
			
			// write out the name of this client
			out.writeObject(clientName);

			new Thread(new ServerHandler()).start();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is called by the GUI to tell the GameServer which Level to create
	 * @param i the levelCode
	 */
	public void createLevel(int i){
		// Hardcoded as level 0 for now
		ServerCreateLevelCommand c = new ServerCreateLevelCommand(0);
		sendCommand(c);
	}
	
	/**
	 * Sends Command objects from this GameClient to its GameServer
	 * @param command the Command object to send to GameServer
	 */
	public void sendCommand(Command<GameServer> command){
		try {
			out.writeObject(command);
		} catch (IOException e) {
			//System.out.println("sendCommand FAILED");
			e.printStackTrace();
		}
	}

	/**
	 * Updates the ChatPanel with the updated message log
	 * 
	 * @param messages	the log of messages to display
	 */
	public void updateMessages(List<String> messages) {
		// TODO update the gui when called
		// GUI.update(messages);
	}

	
	/**
	 * Called by GUI when player attempts to add a tower
	 * 
	 * @param type the towerType enum of the tower to be created
	 * @param loc the Point (rowdown, columnacross) to attempt to add tower to
	 */
	public void addTower(towerType type, Point loc){
		//System.out.println("Constructing SendServerTowerCommand");
		ServerTowerCommand c = new ServerTowerCommand(type, loc);
		//System.out.println("Sending SendServerTowerCommand");
		this.sendCommand(c);
		//System.out.println("Command Sent");
	}
	
	
	/**
	 * called by the GUI, sends command to server to attempt to sell any Tower at a point
	 * 
	 * @param p The point should contain coordinates (rowsdown, columnsacross) in the grid model
	 */
	public void sellTower(Point p){
		ServerTowerRemoveCommand c = new ServerTowerRemoveCommand(p);
		this.sendCommand(c);
	}
	
	/**
	 * Called by the GUI, sends command to GameServer to attempt to upgrade a Tower at Point p
	 * @param p the location coordinates (rows, columns) of tower to be upgraded
	 */
	public void upgradeTower(Point p){
		//Command<GameServer> c = new upgradeTowerCommand(p);
		//this.sendCommand(c);
	}
	
	/*//Unnecessary method for now unless we make a player click remove enemy in area type thing
	public void removeEnemy(Point p){
		SendServerEnemyRemoveCommand c = new SendServerEnemyRemoveCommand(p);
		this.sendCommand(c);
	}*/
	
	public int disconnect(){
		try{
			System.out.println("Disconnecting");
			out.writeObject(new DisconnectCommand(player.getName()));
			return 0;
		}catch(Exception e){
			e.printStackTrace();
			return 1;
		}
	}

	//
	/**
	 * To be called by a Command from server when a Map is first instantiated on Server.
	 * Passes info about the Map to print to the GUI
	 * @param backgroundImageURL
	 * @param l
	 * @param rowsInMap
	 * @param columnsInMap
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
	 */
	public void update(List<EnemyImage> enemyImages, List<TowerImage> towerImages){
		//System.out.println("Client update being called"); //Testing purposes
		mainMenu.getView().update(towerImages, enemyImages);
		//GUI shouldn't hold enemies or towers, instead hold their image classes
	}
	
	
	/**
	 * Called by GameServer via command when either of these variables change in model
	 * Send new values to the GUI
	 * @param hp the Player's new HP
	 * @param money the Player's new Money
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
		mainMenu = new MainMenu(this);
	}
	
	//called from Server via command when the game is lost
	public void notifyLevelWasLost(){
		JOptionPane.showMessageDialog(mainMenu, "You lose");
		mainMenu = new MainMenu(this);
	}
	/**
	 * To be called by GUI when the user wants to either play or pause the game. Server will handle what to do.
	 */
	public void playPauseGame(){
		Command<GameServer> c = new playPauseCommand();
		this.sendCommand(c);
	}
	
	/**
	 * To be called by the GUI when user wishes to save the game.
	 * Creates and sends a Command to GameServer to save the game.
	 */
	public void saveGame(){
		Command<GameServer> c = new saveGameCommand();
		this.sendCommand(c);
	}
	
	/**
	 * To be called by the GUI from MainMenu when user wishes to load last saved game.
	 * Creates and sends a Command to GameServer to reconstruct model based on loaded data
	 * and resume gameplay in a paused state.
	 */
	public void loadGame(){
		Command<GameServer> c = new loadGameCommand();
		this.sendCommand(c);
	}
	
	/**
	 * To be called by the GUI when user wishes to speed up the game.
	 * Creates and sends a Command to GameServer to speed up the game.
	 */
	public void speedUpGame(){
		Command<GameServer> c = new speedUpCommand();
		this.sendCommand(c);
	}
	
	/**
	 * To be called by the GUI when user wishes to return to normal speed in the game.
	 * Creates and sends a Command to GameServer to do so.
	 */
	public void normalSpeedGame(){
		Command<GameServer> c = new normalSpeedCommand();
		this.sendCommand(c);
	}

	/**
	 * Notifies the GUI of the proper state of speed and paused buttons. 
	 * @param paused True = paused, False = playing
	 * @param fast True = faster speed, False = normal speed
	 */
	public void changedSpeedState(Boolean paused, Boolean fast) {
		// TODO Desone, where to send these values to you?
	}
	
	
	
}
