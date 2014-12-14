package GUI;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 * Displays instructions about how to run/play the game.
 * @author Peter Hanson
 *
 */

public class GameInstructions extends JFrame{
	private JFrame frame;
	private JTextArea textArea;
	private static final String instructions = 
			"Welcome to Pokemon Tower Defense, a game designed by the Justice League as their Final Project\nfor CSC 335 at the UofA 2014.\n"
			+ "To play, make sure a GameServer is instantiated and running, then launch a GameClient.\n"
			+ "For the network host address, put \'localhost\' and for the port number put \'9001\'. Select a unique username.\n\n"
			+ "To start a single player game, click on the single player button in the main menu and then select a level.\n"
			+ "You will be given a set amount of HP (bar in top right corner) and money (top left corner) to start with.\n"
			+ "You must use the money to buy towers (at the bottom of the screen) which can be placed on the map by dragging\n"
			+ "and dropping them anywhere except where a tower already is or where the enemy path is, provided you can afford them.\n"
			+ "Towers have different costs and abilities and can be upgraded to evolve. Read their info. Strategic placement is useful.\n"
			+ "Enemy Pokemon will spawn at the start of the path in waves, and will attempt to make it to the last tile to damage you.\n"
			+ "Each felled Pokemon gives you gold. In order to win you must defeat all enemy waves without losing all your HP.\n"
			+ "Losing all of your HP results in a game over.\n\n"
			+ "To start a multiplayer game, click online in the main menu and have your second client do the same.\n"
			+ "In this mode both players share the same health and can send money to each other by typing a message\n"
			+ "starting with the character '$' and followed by the integer amount of money they wish to send.\n"
			+ "A mini map of the partner player's screen is also displayed, with green squares representing towers\n"
			+ "and red squares representing enemies." 
			+ "";
	
	public GameInstructions(){
		frame = this;
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setTitle("Pokemon Tower Defense Instructions");
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		//line below sets frame to be half of width and height of screen, and puts it in the middle
		frame.setBounds(100, 100, (int) (screen.getWidth()/1.2), (int) (screen.getHeight()/1.5));
		frame.setLayout(null);
		frame.setResizable(false);
		
		textArea = new JTextArea(instructions);
		textArea.setEditable(false);
		textArea.setBounds(0, 0, (int) (screen.getWidth()/1.2), (int) (screen.getHeight()/1.5));
		
		frame.add(textArea);
		setVisible(true);
	}
	
	/*
	 * For testing purposes
	 */
	public static void main(String[] args){
		GameInstructions gi = new GameInstructions();
	}

}
