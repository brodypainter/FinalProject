package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class Board extends JPanel implements MouseListener
{
	ArrayList<TowerTile> towers;
	ArrayList<EnemyTile> enemies;
	ArrayList<JLabel> enemyTiles;
	ArrayList<JProgressBar> enemyHealth;
	volatile ArrayList<Line> lines;
	JButton upgrade;
	JLabel upgradePanel;
	JLabel towerStatPanel;
	JTextArea towerStats;
	TowerTile selectedTower;
	Image tower1Proj;
	JProgressBar temp;
	JLabel background;
	boolean towerSelected;
	int tileHeight = 155;
	int tileWidth = 102;
	int timesUpdated = 0;
	GameView view;
	
	public Board(GameView view)
	{
		this.view = view;
		towers = new ArrayList<TowerTile>();
		enemies = new ArrayList<EnemyTile>();
		enemyTiles = new ArrayList<JLabel>();
		lines = new ArrayList<Line>();
		tower1Proj = new BufferedImage(tileWidth, tileHeight, BufferedImage.TYPE_INT_ARGB);
		enemyHealth = new ArrayList<JProgressBar>();
		background = new JLabel("Waiting for image");
		background.setIcon(new ImageIcon());
		upgrade = new JButton("Upgrade");
		upgradePanel = new JLabel();
		towerStatPanel = new JLabel();
		towerStats = new JTextArea();
		addMouseListener(this);
	}
	
	public void setTileSize(int width, int height)
	{
		tileHeight = height;
		tileWidth = width;
		tower1Proj = new ImageIcon("/images/spinningBone.gif").getImage().getScaledInstance(tileWidth, tileHeight, Image.SCALE_DEFAULT);
		upgrade.setSize(tileWidth, tileHeight/3);
		upgradePanel.setSize(tileWidth, tileHeight);
		upgradePanel.setIcon(new ImageIcon(createImageIcon("/images/towerInfoPanel.png").getImage().getScaledInstance(tileWidth, tileHeight, Image.SCALE_DEFAULT)));
		upgradePanel.setLocation(0, 0);
		upgradePanel.setVisible(false);
		upgrade.setSize(tileWidth - 10, tileHeight - 10);
		upgrade.setIcon(new ImageIcon(createImageIcon("/images/upgrade.png").getImage().getScaledInstance(tileWidth-10, tileHeight-10, Image.SCALE_DEFAULT)));
		upgrade.setHorizontalAlignment(SwingConstants.CENTER);
		upgrade.setLocation(0,0);
		upgrade.setVisible(false);
		upgrade.addActionListener(new UpgradeAction());
		towerStatPanel.setSize(tileWidth,(int) (tileHeight * 1.5));
		towerStatPanel.setIcon(new ImageIcon(createImageIcon("/images/towerInfoPanel.png").getImage().getScaledInstance(tileWidth,(int) (tileHeight * 1.5), Image.SCALE_DEFAULT)));
		towerStatPanel.setLocation(0,0);
		towerStatPanel.setVisible(false);
		towerStats.setSize(tileWidth-5, tileHeight-5);
		towerStats.setEditable(false);
		towerStats.setLocation(0,0);
		towerStats.setText("TEST");
		Font tempFont = new Font("Comic Sans MS", Font.PLAIN, 8);
		towerStats.setFont(tempFont);
		towerStats.setOpaque(false);
		towerStats.setForeground(Color.WHITE);
		towerSelected = false;
		this.add(upgrade);
		this.add(towerStats);
		this.add(upgradePanel);
		this.add(towerStatPanel);
		System.out.println("Setting tile size in board");
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
		int i = 0;
		for(TowerTile tile : towers)
		{
			tile = towers.get(i);
			i++;
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
		upgradePanel.repaint();
		towerStatPanel.repaint();
		upgrade.repaint();
		towerStats.repaint();
		view.repaint();
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
	
	class UpgradeAction implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			view.upgrade(new Point(((int) (selectedTower.getX()/tileWidth)), ((int) (selectedTower.getY()/tileHeight))));
		}
	}

	public void mouseClicked(MouseEvent arg0)
	{
		System.out.println("Mouse clicked on board at (" + ((int) (arg0.getX()/tileWidth)) + " ," + ((int) (arg0.getY()/tileHeight)) + ")");
		for(TowerTile label : towers)
		{
			selectedTower = label;
			if((label.getX()/tileWidth) == ((int) (arg0.getX()/tileWidth)) && (label.getY()/tileHeight) == ((int) (arg0.getY()/tileHeight)))	
			{
				towerStatPanel.setVisible(true);
				upgradePanel.setVisible(true);
				upgrade.setVisible(true);
				towerStats.setVisible(true);
				if(label.getX() == 0)
				{
					upgrade.setLocation(label.getX() + tileWidth + 5, label.getY() + 5);
					upgradePanel.setLocation(label.getX() + tileWidth, label.getY());
					towerStatPanel.setLocation(label.getX() + (2*tileWidth), label.getY());
					towerStats.setLocation(label.getX() + (2*tileWidth) + 5, label.getY() + 5);
				}
				else if(label.getX()-1 >= this.getWidth() - tileWidth - 10)
				{
					upgrade.setLocation(label.getX() - tileWidth + 5, label.getY() + 5);
					upgradePanel.setLocation(label.getX() - tileWidth, label.getY());
					towerStatPanel.setLocation(label.getX() - (2*tileWidth), label.getY());
					towerStats.setLocation(label.getX() - (2*tileWidth) + 5, label.getY() + 5);
				}
				else
				{
					upgrade.setLocation(label.getX() + tileWidth + 5, label.getY() + 5);
					upgradePanel.setLocation(label.getX() + tileWidth, label.getY());
					towerStatPanel.setLocation(label.getX() - tileWidth, label.getY());
					towerStats.setLocation(label.getX() - tileWidth + 5, label.getY() + 5);
				}
				towerStats.setText("Level: " + label.getLevel() + "\nRange: " + label.getRange() + "\nPower: " + label.getPower() + "\nRate: " + label.getRate() + "\nSp.:" + label.getSpecial());
				System.out.println("Displaying tower information");
				repaint();
				return;
			}
			else
			{
				towerSelected = false;
				towerStatPanel.setVisible(false);
				upgradePanel.setVisible(false);
				upgrade.setVisible(false);
				towerStats.setVisible(false);
			}
			repaint();
		}
	}
	public void mouseEntered(MouseEvent arg0){}
	public void mouseExited(MouseEvent arg0){}
	public void mousePressed(MouseEvent arg0){}
	public void mouseReleased(MouseEvent arg0){}
}
