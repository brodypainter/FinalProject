package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.GameClient;
import server.GameServer;

public class MiniMap extends JFrame
{
	/**
	 * Creates a new JFrame mini map, paints the scaled Board background image, red
	 * squares where enemies are and green squares where towers of partner are
	 * @param towers
	 * @param enemies
	 */
	private Panel panel;
	private JLabel background;
	private int tileWidth;
	private int tileHeight;
	private int windowWidth = 300;
	private int windowHeight = 200;
	private ArrayList<Point> enemies = new ArrayList<>();
	private ArrayList<Point> towers = new ArrayList<Point>();
	
	public static void main(String[] args)
	{
		new GameServer();
		new GameClient();
	}
	
	public MiniMap(int tileWidth, int tileHeight, int levelWidth, int levelHeight)
	{
		this.setLayout(null);
		this.setSize(windowWidth, windowHeight);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.tileWidth = (int) this.getWidth()/levelWidth;
		this.tileHeight = (int) this.getHeight()/levelHeight;
		
		panel = new Panel();
		panel.setSize(this.getWidth() - 20, this.getHeight() - 40);
		panel.setLocation(0,0);
		this.add(panel);
		
		background = new JLabel(new ImageIcon(createImageIcon("/images/towerInfoPanel.png").getImage().getScaledInstance(panel.getWidth(), panel.getHeight(), Image.SCALE_DEFAULT)));
		background.setSize(panel.getWidth(), panel.getHeight());
		background.setLocation(0,0);
		panel.add(background);
		
		this.setVisible(true);
	}
	
	public void updateTowers(List<Point> newTowers)
	{
		System.out.println("Updating towers on MiniMap");
		towers = (ArrayList<Point>) newTowers;
	}
	
	public void updateEnemies(List<Point> newEnemies)
	{
		enemies = (ArrayList<Point>) newEnemies;
	}
	
	/**
	 * Creates an image icon based on the given URL, used to avoid nullPointers
	 * @param url The location of the target image
	 * @return An ImageIcon created from the image found at the url
	 */
	public ImageIcon createImageIcon(String url)
	{
		URL imageURL = getClass().getResource(url);
		if(imageURL != null)
		{
			return new ImageIcon(imageURL, "");
		}
		else
		{
			System.out.println("Error loading image at " + url);
			return null;
		}
	}
	
	private class Panel extends JPanel
	{
		public void paintComponent(Graphics g)
		{
			//panel.repaint();
			g.setColor(Color.GREEN);
			for(Point p : towers)
			{
				g.fillOval((int) (tileWidth * p.getX()),(int) (tileHeight * p.getY()), tileWidth, tileHeight);
			}
			g.setColor(Color.RED);
			for(Point p : enemies)
			{
				g.fillOval((int) (tileWidth * p.getX()),(int) (tileHeight * p.getY()), tileWidth, tileHeight);
			}
		}
	}
}
