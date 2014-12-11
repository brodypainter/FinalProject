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
import javax.swing.Timer;

import GUI.GameView.towerType;

public class Board extends JPanel implements MouseListener
{
	ArrayList<TowerTile> towers;
	ArrayList<EnemyTile> enemies;
	ArrayList<JLabel> enemyTiles;
	ArrayList<JProgressBar> enemyHealth;
	ArrayList<Projectile> projectiles;
	volatile ArrayList<Line> lines;
	JButton upgrade;
	JLabel upgradePanel;
	JLabel towerStatPanel;
	JLabel towerRange;
	JTextArea towerStats;
	TowerTile selectedTower;
	EnemyTile selectedEnemy;
	ImageIcon tower1Proj;
	JProgressBar temp;
	JLabel background;
	boolean towerSelected;
	boolean enemySelected = false;
	int tileHeight = 155;
	int tileWidth = 102;
	int timesUpdated = 0;
	GameView view;
	Board board;
	
	public Board(GameView view)
	{
		System.out.println("Creating board");
		board = this;
		this.view = view;
		towers = new ArrayList<TowerTile>();
		enemies = new ArrayList<EnemyTile>();
		enemyTiles = new ArrayList<JLabel>();
		lines = new ArrayList<Line>();
		tower1Proj = new ImageIcon();
		enemyHealth = new ArrayList<JProgressBar>();
		background = new JLabel("Waiting for image");
		background.setIcon(new ImageIcon());
		upgrade = new JButton("Upgrade");
		upgradePanel = new JLabel();
		towerStatPanel = new JLabel();
		towerStats = new JTextArea();
		towerRange = new JLabel();
		projectiles = new ArrayList<Projectile>();
		addMouseListener(this);
		Timer animationTimer = new Timer(50, new AnimationTimer());
		animationTimer.start();
		System.out.println("Board initialization complete");
	}
	
	public void setTileSize(int width, int height)
	{
		tileHeight = height;
		tileWidth = width;
		tower1Proj = new ImageIcon(createImageIcon("/images/spinningBone.gif").getImage().getScaledInstance(tileWidth/2, tileHeight/2, Image.SCALE_DEFAULT));
		upgradePanel.setSize(tileWidth, tileHeight);
		upgradePanel.setIcon(new ImageIcon(createImageIcon("/images/towerInfoPanel.png").getImage().getScaledInstance(tileWidth, tileHeight, Image.SCALE_DEFAULT)));
		upgradePanel.setLocation(0, 0);
		upgradePanel.setVisible(false);
		upgrade = new JButton(new ImageIcon(createImageIcon("/images/upgrade.png").getImage().getScaledInstance(tileWidth-10, tileHeight-10, Image.SCALE_DEFAULT)));
		upgrade.setSize(tileWidth - 10, tileHeight - 10);
		upgrade.setHorizontalAlignment(SwingConstants.CENTER);
		upgrade.setLocation(0,0);
		upgrade.setBorderPainted(false);
		upgrade.setVisible(false);
		upgrade.setOpaque(false);
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
		towerRange.setVisible(false);
		towerSelected = false;
		
		this.add(upgrade);
		this.add(towerStats);
		this.add(upgradePanel);
		this.add(towerStatPanel);
		this.add(towerRange);
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
			//TODO: Identify what enemy is actually gone
			for(int i = 0; this.enemies.size() > enemies.size() && i < enemies.size(); i++)
			{
				if(Math.abs(this.enemies.get(i).getX() - enemies.get(i).getX()) < 5)
				{
					if(Math.abs(this.enemies.get(i).getY() - enemies.get(i).getY()) < 5)
					{
						continue;
					}
				}
				System.out.println("Removing enemy at (" + this.enemies.get(i).getX() + ", " + this.enemies.get(i).getY() + ")");
				this.enemies.remove(i);
				this.enemyHealth.remove(i);
				i--;
			}
			
			/*
			 * This is what will work when the labels are assigned an ID
			 * for(int i = 0; i < enemies.size(); i++)
			 * {
			 * 		if((EnemyTile) (enemies.get(i)).getID().equals((EnemyTile) (this.enemies.get(i)).getID()))
			 * 		{
			 * 			this.enemies.remove(i);
			 * 			i--;
			 * 		}
			 * }
			 * 
			 */
			
			while(this.enemies.size() < enemies.size())
			{
				EnemyTile tempTile = (EnemyTile) enemies.get(this.enemies.size());
				tempTile.addMouseListener(new EnemyClickListener());
				this.enemies.add(tempTile);
				this.enemyHealth.add(new JProgressBar(0,100));
				enemyHealth.get(enemyHealth.size()-1).setBackground(Color.RED);
				enemyHealth.get(enemyHealth.size()-1).setForeground(Color.GREEN);
				enemyHealth.get(enemyHealth.size()-1).setSize(tileWidth, tileHeight/4);
				enemyHealth.get(enemyHealth.size()-1).setVisible(true);
				this.add(enemyHealth.get(enemyHealth.size()-1));
				this.add(enemies.get(enemies.size()-1));
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
	
	public void animateAttack(Point start, Point end, towerType type)
	{
		switch(type)
		{
		case NORMAL:
			Bone temp = new Bone();
			temp.setIcon(tower1Proj);
			temp.setPath(new Path(start.x, start.y, end.x, end.y));
			//projectiles.add(temp);
			this.add(temp);
			break;
		case ELECTRIC:
			break;
		case FIRE:
			break;
		case GRASS:
			break;
		case MEWTWO:
			break;
		case POISON:
			break;
		case PSYCHIC:
			break;
		case WATER:
			break;
		default:
			break;
			
		}
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage((((ImageIcon) background.getIcon()).getImage()), 0, 0, this);
		//g.drawImage(temp.createImage(tileWidth, 20), 100, 100, this);
		//temp.paint(g);
		
		for(JLabel label : enemies)
		{
			label.repaint();
		}
		towerRange.repaint();
		for(JLabel label : towers)
		{
			g.drawImage(((ImageIcon) label.getIcon()).getImage(), label.getX(), label.getY(), this);
		}
		for(Line line : lines)
		{
			g.drawLine(line.getStart().x, line.getStart().y, line.getEnd().x, line.getEnd().y);
		}
		for(Projectile proj : projectiles)
		{
			proj.repaint();
		}
		
		if(enemySelected)
		{
			towerStatPanel.setLocation(selectedEnemy.getX()-tileWidth, selectedEnemy.getY());
			towerStats.setLocation(selectedEnemy.getX()-tileWidth + 5, selectedEnemy.getY() + 5);
			towerStats.setText("Health: " + selectedEnemy.getHealth() + "\nNeed getSpeed()" + "\nNeed getAttack()" + "\nNeed getMaxHealth()");
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
	
	class AnimationTimer implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			for(Projectile proj : projectiles)
			{
				proj.setProgress((int) (proj.getProgress() + 5));
				proj.setLocation((int)  proj.getLocationInGrid().x * tileWidth, (int) proj.getLocationInGrid().y * tileHeight); 
				if(!proj.isValid())
				{
					projectiles.remove(proj);
					board.remove((JLabel) proj);
				}	
			}
		}
	}
	
	class EnemyClickListener implements MouseListener
	{
		public void mouseClicked(MouseEvent e)
		{
			if(enemySelected)
			{
				selectedEnemy = (EnemyTile) (e.getSource());
				towerStatPanel.setLocation(((EnemyTile) e.getSource()).getX()-tileWidth, ((EnemyTile) e.getSource()).getY());
				towerStats.setText("Health: " + selectedEnemy.getHealth() + "\nNeed getSpeed()" + "\nNeed getAttack()" + "\nNeed getMaxHealth()");
				upgradePanel.setVisible(false);
				upgrade.setVisible(false);
				towerRange.setVisible(false);
			}
			else
			{
				selectedEnemy = (EnemyTile) (e.getSource());
				enemySelected = true;
				towerStatPanel.setLocation(((EnemyTile) e.getSource()).getX()-tileWidth, ((EnemyTile) e.getSource()).getY());
				towerStatPanel.setVisible(true);
				towerStats.setVisible(true);
				towerStats.setText("Health: " + selectedEnemy.getHealth() + "\nNeed getSpeed()" + "\nNeed getAttack()" + "\nNeed getMaxHealth()");
				upgradePanel.setVisible(false);
				upgrade.setVisible(false);
				towerRange.setVisible(false);
			}
			System.out.println("Recieved click event at (" + ((EnemyTile) e.getSource()).getX());
		}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		public void mousePressed(MouseEvent e){}
		public void mouseReleased(MouseEvent e){}
	}

	public void mouseClicked(MouseEvent arg0)
	{
		System.out.println("Mouse clicked on board at (" + ((int) (arg0.getX()/tileWidth)) + " ," + ((int) (arg0.getY()/tileHeight)) + ")");
		for(TowerTile label : towers)
		{
			selectedTower = label;
			if((label.getX()/tileWidth) == ((int) (arg0.getX()/tileWidth)) && (label.getY()/tileHeight) == ((int) (arg0.getY()/tileHeight)))	
			{
				enemySelected = false;
				towerSelected = true;
				towerRange.setIcon(new ImageIcon(createImageIcon("/images/towerRange.png").getImage().getScaledInstance((int) (2 * label.getRange()  * tileWidth) + tileWidth,(int) (2 * label.getRange()  * tileHeight) + tileHeight, Image.SCALE_SMOOTH)));
				towerRange.setSize((int) (2 * label.getRange()  * tileWidth) + tileWidth,(int) (2 * label.getRange()  * tileHeight) + tileHeight);
				towerRange.setLocation(label.getX() - (label.getRange() * tileWidth), label.getY() - (label.getRange() * tileHeight));
				towerRange.setVisible(true);
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
				towerRange.setVisible(false);
			}
		}
		for(EnemyTile label : enemies)
		{
			if((label.getX()/tileWidth) == ((int) (arg0.getX()/tileWidth)) && (label.getY()/tileHeight) == ((int) (arg0.getY()/tileHeight)))	
			{
				enemySelected = true;
				selectedEnemy = label;
				upgradePanel.setVisible(false);
				upgrade.setVisible(false);
				towerRange.setVisible(false);
				towerStatPanel.setLocation(label.getX() - tileWidth, label.getY());
				towerStatPanel.setVisible(true);
				towerStats.setVisible(true);
				towerStats.setText("Health: " + label.getHealth() + "\nNeed getSpeed()" + "\nNeed getAttack()" + "\nNeed getMaxHealth()");
				return;
			}
			else
			{
				enemySelected = false;
				towerStatPanel.setVisible(false);
				towerRange.setVisible(false);
				upgradePanel.setVisible(false);
				upgrade.setVisible(false);
				towerStats.setVisible(false);
			}
		}
	}
	public void mouseEntered(MouseEvent arg0){
		view.hideTowerStats();
	}
	public void mouseExited(MouseEvent arg0){}
	public void mousePressed(MouseEvent arg0){}
	public void mouseReleased(MouseEvent arg0){}
}
