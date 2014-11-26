package GUI;

import java.awt.Component;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel
{
	ArrayList<JLabel> towers;
	ArrayList<JLabel> enemies;
	ArrayList<JLabel> enemyTiles;
	JLabel background;
	int tileHeight = 155;
	int tileWidth = 102;
	int timesUpdated = 0;
	
	public Board()
	{
		towers = new ArrayList<JLabel>();
		enemies = new ArrayList<JLabel>();
		enemyTiles = new ArrayList<JLabel>();
		background = new JLabel("Waiting for image");
		background.setIcon(new ImageIcon());
	}
	
	public void setTileSize(int width, int height)
	{
		tileHeight = height;
		tileWidth = width;
	}
	
	public Component add(Component c)
	{
		System.out.println(c.getName());
		if(c.getName().equals("Background"))
		{
			background = (JLabel) c;
		}
		if(c.getName().equals("EnemyPathTile"))
		{
			enemyTiles.add((JLabel) c);
		}
		return c;
	}
	
	public void removeTowers()
	{
		towers = new ArrayList<JLabel>();
	}
	
	public void addTowers(List<JLabel> towers)
	{
		this.towers = (ArrayList<JLabel>) towers;
	}
	
	public void removeEnemies()
	{
		enemies = new ArrayList<JLabel>();
	}
	
	public void addEnemies(List<JLabel> enemies)
	{
		timesUpdated++;
		if(timesUpdated >= 2)
		{
			timesUpdated = 0;
			this.enemies = (ArrayList<JLabel>) enemies;
			System.out.println("Updating enemies");
		}
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(new ImageIcon("/images/cuboneStatic.png").getImage(), 0, 0, this);
		g.drawImage((((ImageIcon) background.getIcon()).getImage()), 0, 0, this);
		//g.drawImage(((ImageIcon) enemies.get(0).getIcon()).getImage(), enemies.get(0).getX(), enemies.get(0).getY(), this);
		for(JLabel label : enemies)
		{
			g.drawImage(((ImageIcon) label.getIcon()).getImage(), label.getX(), label.getY(), this);
		}
		for(JLabel label : enemyTiles)
		{
			g.drawImage(((ImageIcon) label.getIcon()).getImage(), label.getX(), label.getY(), this);
		}
		for(JLabel label : towers)
		{
			g.drawImage(((ImageIcon) label.getIcon()).getImage(), label.getX(), label.getY(), this);
		}
	
	}
}
