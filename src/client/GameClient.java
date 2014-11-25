package client;

import java.awt.Point;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import javax.swing.JOptionPane;

import model.Level0Map;
import server.GameServer;
import GUI.EnemyImage;
import GUI.GameView.towerType;
import GUI.MainMenu;
import GUI.TowerImage;
import GameController.Enemy;
import GameController.Tower;
import commands.Command;
import commands.DisconnectCommand;
import commands.SendServerCreateLevelCommand;
import commands.SendServerTowerCommand;
import commands.SendServerTowerRemoveCommand;

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
	
	public void newMessage(String message) {
		// TODO Display a new message to our text/chat panel. May need to add a local list of messages	
		System.out.println(message);
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
			
			// add a listener that sends a disconnect command to when closing
//			this.addWindowListener(new WindowAdapter(){
//				public void windowClosing(WindowEvent arg0) {
//					try {
//						out.writeObject(null);// TODO send a disconnect command to the server to safely disconnect
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
			
			// add a listener that sends a disconnect command to when closing
//			this.addWindowListener(new WindowAdapter(){
//				public void windowClosing(WindowEvent arg0) {
//					try {
//						out.writeObject(null);// TODO send a disconnect command to the server to safely disconnect
//						out.close();
//						in.close();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//			});
			
			// TODO set up the GUI
			
			// start a thread for handling server events
			new Thread(new ServerHandler()).start();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void createLevel(int i){
		// Hardcoded as level 0 for now
		SendServerCreateLevelCommand c = new SendServerCreateLevelCommand(0);
		sendCommand(c);
	}
	
	public void sendCommand(Command<GameServer> command){
		try {
			out.writeObject(command);
		} catch (IOException e) {
			//System.out.println("sendCommand FAILED");
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
		new GameClient();
	}

	/**
	 * Updates the ChatPanel with the updated message log
	 * 
	 * @param messages	the log of messages to display
	 */
	public void update(List<String> messages) {
		// TODO update the gui when called
		// GUI.update(messages);
	}

	

	

	
	
	//Called by GUI when player attempts to add a tower
	public void addTower(towerType normal, Point loc){
		//System.out.println("Constructing SendServerTowerCommand");
		SendServerTowerCommand c = new SendServerTowerCommand(normal, loc);
		//System.out.println("Sending SendServerTowerCommand");
		this.sendCommand(c);
		//System.out.println("Command Sent");
	}
	
	
	//called by the GUI, sends command to server to attempt to sell any Tower at a point
	//The point should contain coordinates (rowsdown, columnsacross) in the grid model
	public void removeTower(Point p){
		SendServerTowerRemoveCommand c = new SendServerTowerRemoveCommand(p);
		this.sendCommand(c);
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

	//Called from server via command when a Map is first instantiated on Server
	public void mapBackgroundUpdate(String backgroundImageURL, List<Point> enemyPathCoords, int rowsInMap, int columnsInMap) {
		mainMenu.getView().setMapBackgroundImageURL(backgroundImageURL);
		mainMenu.getView().setEnemyPathCoords(enemyPathCoords);
		Point mapSize = new Point(columnsInMap,rowsInMap);
		mainMenu.getView().setGridSize(mapSize);
	}

	//Called from server via command every tick to pass updated enemy/tower image locations/states
	/**
	 * Receives a tower and enemy list and sends it on to the GUI's model.
	 * @param enemyImages
	 * @param towerImages
	 */
	public void update(List<EnemyImage> enemyImages, List<TowerImage> towerImages){
		//System.out.println("Client update being called"); //Testing purposes
		mainMenu.getView().update(towerImages, enemyImages);
		//GUI shouldn't hold enemies or towers, instead hold their image classes
	}
	
	//Called from server via command whenever any of these variables change in model
	public void updateHPandMoney(int hp, int money) {
		mainMenu.getView().setPlayerHP(hp);
		mainMenu.getView().setPlayerMoney(money);
	}

	//Called from Server via command whenever a tower attacks an enemy
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
}
