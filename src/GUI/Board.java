package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class Board extends JPanel implements MouseListener
{
	ArrayList<TowerTile> towers;
	ArrayList<EnemyTile> enemies;
	ArrayList<JLabel> enemyTiles;
	ArrayList<JProgressBar> enemyHealth;
	volatile ArrayList<Line> lines;
	Image tower1Proj;
	JProgressBar temp;
	JLabel background;
	int tileHeight = 155;
	int tileWidth = 102;
	int timesUpdated = 0;
	
	public Board()
	{
		towers = new ArrayList<TowerTile>();
		enemies = new ArrayList<EnemyTile>();
		enemyTiles = new ArrayList<JLabel>();
		lines = new ArrayList<Line>();
		tower1Proj = new BufferedImage(tileWidth, tileHeight, BufferedImage.TYPE_INT_ARGB);
		enemyHealth = new ArrayList<JProgressBar>();
		background = new JLabel("Waiting for image");
		background.setIcon(new ImageIcon());
		addMouseListener(this);
	}
	
	public void setTileSize(int width, int height)
	{
		tileHeight = height;
		tileWidth = width;
		tower1Proj = new ImageIcon("/images/spinningBone.gif").getImage().getScaledInstance(tileWidth, tileHeight, Image.SCALE_DEFAULT);
	}

	
	public Component addBackground(Component c)
	{
		//System.out.println(c.getName());
		if(c.getName().equals("Background"))
		{
			background = (JLabel) c;
		}
		return c;
	}
	
	
	public void add(Line line)
	{
		lines.add(line);
		System.out.println(enemies.get(0).getHealth());
		repaint();
	}
	
	public void removeLine()
	{
		lines.remove(0);
		repaint();
	}
	
	public void removeTowers()
	{
		towers = new ArrayList<TowerTile>();
	}
	
	public void addTowers(List<TowerTile> towers)
	{
		while(this.towers.size() > towers.size())
		{
			towers.remove(0);
		}
		while(this.towers.size() < towers.size())
		{
			this.towers.add(towers.get(this.towers.size()));
		}
	}
	
	public void addEnemies(List<JLabel> enemies)
	
	{
			while(this.enemies.size() > enemies.size())
			{
				this.enemies.remove(0);
				this.remove(this.enemyHealth.remove(0));
				repaint();
			}
			while(this.enemies.size() < enemies.size())
			{
				this.enemies.add((EnemyTile) enemies.get(this.enemies.size()));
				this.enemyHealth.add(new JProgressBar(0,100));
				enemyHealth.get(enemyHealth.size()-1).setBackground(Color.RED);
				enemyHealth.get(enemyHealth.size()-1).setForeground(Color.GREEN);
				enemyHealth.get(enemyHealth.size()-1).setSize(tileWidth, tileHeight/4);
				enemyHealth.get(enemyHealth.size()-1).setVisible(true);
				this.add(enemyHealth.get(enemyHealth.size()-1));
			}
			int i = 0;
			for(JLabel label : this.enemies)
			{
				label.setLocation(enemies.get(i).getLocation().x, enemies.get(i).getLocation().y);
				label.setIcon(enemies.get(i).getIcon());
				if(label.getLocation().x > this.getWidth() || label.getLocation().y > this.getHeight() || label.getLocation().x < 0 || label.getLocation().y < 0)
				{
					this.enemies.remove(label);
				}
				i++;
			}
			i = 0;
			for(JProgressBar bar : this.enemyHealth)
			{
				bar.setLocation(enemies.get(i).getLocation().x, enemies.get(i).getLocation().y - (tileHeight/4));
				bar.setValue(((EnemyTile) enemies.get(i)).getHealth());
				i++;
			}
			repaint();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage((((ImageIcon) background.getIcon()).getImage()), 0, 0, this);
		//g.drawImage(temp.createImage(tileWidth, 20), 100, 100, this);
		//temp.paint(g);
		for(JLabel label : enemies)
		{
			g.drawImage(((ImageIcon) label.getIcon()).getImage(), label.getX(), label.getY(), this);
		}
		for(JLabel label : towers)
		{
			g.drawImage(((ImageIcon) label.getIcon()).getImage(), label.getX(), label.getY(), this);
		}
		for(Line line : lines)
		{
			g.drawLine(line.getStart().x, line.getStart().y, line.getEnd().x, line.getEnd().y);
		}
	}

	public void mouseClicked(MouseEvent arg0)
	{
		System.out.println("Mouse clicked on board at (" + ((int) (arg0.getX()/tileWidth)) + " ," + ((int) (arg0.getY()/tileHeight)) + ")");
		for(TowerTile label : towers)
		{
			if((label.getX()/tileWidth) == ((int) (arg0.getX()/tileWidth)) && (label.getY()/tileHeight) == ((int) (arg0.getY()/tileHeight)))	
			{
				System.out.println(label.getType() + ":" + label.getLevel());
			}
		}
	}
	public void mouseEntered(MouseEvent arg0){}
	public void mouseExited(MouseEvent arg0){}
	public void mousePressed(MouseEvent arg0){}
	public void mouseReleased(MouseEvent arg0){}
}
