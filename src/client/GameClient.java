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
import GUI.GameView.towerType;
import GUI.MainMenu;
import GameController.Enemy;
import GameController.Tower;
import commands.Command;
import commands.DisconnectCommand;
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
	
	private Level0Map level;
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
					System.out.println("Read Object");
					//Command<GameClient> c = (Command<GameClient>)in.readObject();
					//c.execute(GameClient.this);
					System.out.println(in.readObject());
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
			System.out.println("Player Received");
			
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
	
	public void sendCommand(Command<GameServer> command){
		try {
			out.writeObject(command);
		} catch (IOException e) {
			System.out.println("sendCommand FAILED");
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

	/**
	 * Receives a tower and enemy list and sends it on to the GUI's model.
	 * @param enemyList
	 * @param towerList
	 */
	public void update(List<Enemy> enemyList, List<Tower> towerList){
		mainMenu.getView().update(towerList, enemyList);
	}
	
	public void addTower(towerType normal, Point loc){
		System.out.println("Constructing SendServerTowerCommand");
		SendServerTowerCommand c = new SendServerTowerCommand(normal, loc);
		System.out.println("Sending SendServerTowerCommand");
		this.sendCommand(c);
		System.out.println("Command Sent");
	}
	
	public void removeTower(Tower t){
		SendServerTowerRemoveCommand c = new SendServerTowerRemoveCommand(t);
		this.sendCommand(c);
	}
	
	/*//Unnecessary method for now unless we make a player click remove enemy type thing
	public void removeEnemy(Enemy e){
		SendServerEnemyRemoveCommand c = new SendServerEnemyRemoveCommand(e);
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
	
	//Server should never receive orders to tick from client, do not use this method below. -PH
	/**
	 * To be called by the TimeCommand objects every time they are executed (every ~20 ms)
	 */
	/* public void tick(){
		System.out.println("Tick Received!");
	}
	*/
}
