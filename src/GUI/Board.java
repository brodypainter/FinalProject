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
	ArrayList<Line> lines;
	JLabel background;
	int tileHeight = 155;
	int tileWidth = 102;
	int timesUpdated = 0;
	
	public Board()
	{
		towers = new ArrayList<JLabel>();
		enemies = new ArrayList<JLabel>();
		enemyTiles = new ArrayList<JLabel>();
		lines = new ArrayList<Line>();
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
	
	public void add(Line line)
	{
		lines.add(line);
		repaint();
	}
	
	public void removeLine()
	{
		lines.remove(0);
		repaint();
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
		//timesUpdated++;
		//if(timesUpdated >= 20)
		//{
		//	timesUpdated = 0;
		//	
		//	repaint();
		//}
	}
	
	public void addEnemies(List<JLabel> enemies)
	{
			
			//enemies = new ArrayList<JLabel>();
			
			//this.enemies = (ArrayList<JLabel>) enemies;
			
			//Temporary testing
			while(this.enemies.size() > enemies.size())
			{
				this.enemies.remove(0);
				
			}
			while(this.enemies.size() < enemies.size())
			{
				this.enemies.add(enemies.get(enemies.size()-1));
				
			}
			int i = 0;
			for(JLabel label : this.enemies)
			{
				label.setLocation(enemies.get(i).getLocation().x, 172);
				if(label.getLocation().x > 1000)
				{
					this.enemies.remove(label);
				}
				i++;
			}
			repaint();
			
			//System.out.println("Updating enemies");
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		//g.drawImage(new ImageIcon("/images/cuboneStatic.png").getImage(), 0, 0, this);
		
		g.drawImage((((ImageIcon) background.getIcon()).getImage()), 0, 0, this);

		//g.drawImage(((ImageIcon) enemies.get(0).getIcon()).getImage(), enemies.get(0).getX(), enemies.get(0).getY(), this);
		for(JLabel label : enemies)
		{
			g.drawImage(((ImageIcon) label.getIcon()).getImage(), label.getX(), label.getY(), this);
		}
		//for(JLabel label : enemyTiles)
		//{
			//g.drawImage(((ImageIcon) label.getIcon()).getImage(), label.getX(), label.getY(), this);
		//}
		for(JLabel label : towers)
		{
			g.drawImage(((ImageIcon) label.getIcon()).getImage(), label.getX(), label.getY(), this);
		}
		
		for(Line line : lines)
		{
			//System.out.println("Number of lines: " + lines.size());
			g.drawLine(line.getStart().x, line.getStart().y, line.getEnd().x, line.getEnd().y);
		}
	}
}
