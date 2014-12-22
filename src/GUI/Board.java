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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import GUI.GameView.towerType;
import GUI.projectiles.Bone;
import GUI.projectiles.ElectricBall;
import GUI.projectiles.FireBall;
import GUI.projectiles.GhostBall;
import GUI.projectiles.GrassBall;
import GUI.projectiles.Path;
import GUI.projectiles.Projectile;
import GUI.projectiles.PsychicBall;
import GUI.projectiles.WaterBall;

public class Board extends JPanel implements MouseListener
{
	ArrayList<TowerTile> towers;
	ArrayList<EnemyTile> enemies;
	ArrayList<EnemyTile> enemiesPrefFrame;
	ArrayList<EnemyTile> enemiesThisFrame;
	ArrayList<JLabel> enemyTiles;
	ArrayList<JProgressBar> enemyHealth;
	ArrayList<Projectile> projectiles;
	boolean upgrading;
	JButton upgrade;
	JLabel upgradePanel;
	JLabel towerStatPanel;
	JLabel towerRange;
	JTextArea towerStats;
	TowerTile selectedTower;
	EnemyTile selectedEnemy;
	String selectedEnemyID = "";
	ImageIcon tower1Proj;
	ImageIcon tower2Proj;
	ImageIcon tower3Proj;
	ImageIcon tower4Proj;
	ImageIcon tower5Proj;
	ImageIcon tower6Proj;
	ImageIcon tower7Proj;
	ImageIcon tower8Proj;
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
		this.enemiesPrefFrame = new ArrayList<EnemyTile>(); //PH Testing
		this.enemiesThisFrame = new ArrayList<EnemyTile>(); //PH testing
		enemyTiles = new ArrayList<JLabel>();
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
		tower1Proj = new ImageIcon(createImageIcon("/images/tower1Projectile.gif").getImage().getScaledInstance(tileWidth/2, tileHeight/2, Image.SCALE_DEFAULT));
		tower2Proj = new ImageIcon(createImageIcon("/images/tower2Projectile.gif").getImage().getScaledInstance(tileWidth/2, tileHeight/2, Image.SCALE_DEFAULT));
		tower3Proj = new ImageIcon(createImageIcon("/images/tower3Projectile.gif").getImage().getScaledInstance(tileWidth/2, tileHeight/2, Image.SCALE_DEFAULT));
		tower4Proj = new ImageIcon(createImageIcon("/images/tower4Projectile.gif").getImage().getScaledInstance(tileWidth/2, tileHeight/2, Image.SCALE_DEFAULT));
		tower5Proj = new ImageIcon(createImageIcon("/images/tower5Projectile.gif").getImage().getScaledInstance(tileWidth/2, tileHeight/2, Image.SCALE_DEFAULT));
		tower6Proj = new ImageIcon(createImageIcon("/images/tower6Projectile.gif").getImage().getScaledInstance(tileWidth/2, tileHeight/2, Image.SCALE_DEFAULT));
		tower7Proj = new ImageIcon(createImageIcon("/images/tower7Projectile.gif").getImage().getScaledInstance(tileWidth/2, tileHeight/2, Image.SCALE_DEFAULT));
		tower8Proj = new ImageIcon(createImageIcon("/images/tower8Projectile.gif").getImage().getScaledInstance(tileWidth/2, tileHeight/2, Image.SCALE_DEFAULT));
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
		towerStats.setSize(tileWidth-5, (int) (tileHeight * 1.5));
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
			if(tile.getLevel() != this.towers.get(i).getLevel())
			{
				System.out.println("Upgrade detected");
				TowerTile tempTile = this.towers.get(i);
				tempTile.setSpecial(tile.getSpecial());
				tempTile.setRate(tile.getRate());
				tempTile.setRange(tile.getRange());
				tempTile.setPower(tile.getPower());
				tempTile.setCost(tile.getCost());
				tempTile.setLevel(tile.getLevel());
				tempTile.setIcon(tile.getIcon());
				this.towers.set(i, tempTile);
			}
			i++;
		}
	}
	
	/**
	 * Test method PH for repainting enemies
	 * @param enemiesInUpdate
	 */
	public void updateEnemyTiles(List<EnemyTile> enemiesInUpdate){
		for(EnemyTile e: enemiesPrefFrame){
			this.remove(e);
		}
		this.enemiesPrefFrame.clear();
		this.enemiesPrefFrame.addAll(this.enemiesThisFrame);
		this.enemiesThisFrame.clear();
		this.enemiesThisFrame.addAll(enemiesInUpdate);
		for(EnemyTile e: enemiesThisFrame){
			this.add(e);
		}
		repaint();
	}
	
	
	/**
	 * Takes in a list of EnemyTiles. Updates Board
	 * @param enemiesInUpdate
	 */
	public void addEnemies(List<EnemyTile> enemiesInUpdate)
	
	{
		//Why is this stat display method here? -PH
		if(enemySelected)
		{
			for(EnemyTile enemy : enemiesInUpdate)
			{
				if(enemy.getID().equals(selectedEnemyID))
				{
					selectedEnemy.setHealth(enemy.getHealth());
					towerStats.setText("Health: " + selectedEnemy.getHealthLeft() + "/" + selectedEnemy.getMaxHealth() + "\nSpeed: " + selectedEnemy.getSpeed() + "\nAttack: " + selectedEnemy.getAttack() + "\nDefense: " + selectedEnemy.getAttack() + "\nWorth: " + selectedEnemy.getWorth());
					//System.out.println("Setting enemy health");
					break;
				}
			}
		}
		
		
		
		
		//for(int i = 0; i < enemies.size() && i < this.enemies.size(); i++)
		//{
		//	System.out.println("Enemy ID: " + ((EnemyTile) enemies.get(i)).getID());
		//	if(!((EnemyTile) (enemies.get(i))).getID().equals(((EnemyTile) (this.enemies.get(i))).getID()))
		//	{
		//		this.enemies.remove(i);
		//		this.enemyHealth.remove(i);
		//		i--;
		//		System.out.println("Removing enemy");
		//	 }
		//}
		
		//If there are no EnemyTiles in the update then remove all from Board
		
		// commented out for testing
		if(enemiesInUpdate.size() == 0)
		{
			for(EnemyTile tile : this.enemies)
			{
				this.remove(tile);
				this.remove(enemyHealth.get(this.enemies.indexOf(tile)));
			}
			//System.out.println("Removed all enemies from view and list");
			enemyHealth.clear();
			this.enemies.clear();
		}
		
	
	
		//Check which EnemyTiles to keep on the board, remove ones that do not have a matching
		//ID with any of the EnemyTiles in the update
		for(int i = 0; i < this.enemies.size(); i++)
		{
			//System.out.println("Enemy ID (Exists): " + ((EnemyTile) this.enemies.get(i)).getID());
			
			for(int j = 0; j < enemiesInUpdate.size(); j++)
			{
				//System.out.println("Enemy ID (Adding): " + ((EnemyTile) enemies.get(j)).getID());
				if(this.enemies.get(i).getID().equals(((EnemyTile) enemiesInUpdate.get(j)).getID()))
					break;
				if(j == enemiesInUpdate.size() - 1)
				{
					System.out.println("Removing enemy: " + this.enemies.get(i).getPokeName());
					this.remove(this.enemies.remove(i));
					this.remove(enemyHealth.remove(i));
					
				}
			}
		}
		//Creates an EnemyTile in Board's list for each one it is missing from the update?
		while(this.enemies.size() < enemiesInUpdate.size())
		{
			EnemyTile tempTile = (EnemyTile) enemiesInUpdate.get(this.enemies.size());
			tempTile.addMouseListener(new EnemyClickListener());
			this.enemies.add(tempTile);
			System.out.println("Added a new EnemyImage to board: " + tempTile.getPokeName());
			this.enemyHealth.add(new JProgressBar(0,100));
			enemyHealth.get(enemyHealth.size()-1).setBackground(Color.RED);
			enemyHealth.get(enemyHealth.size()-1).setForeground(Color.GREEN);
			enemyHealth.get(enemyHealth.size()-1).setSize(tileWidth, tileHeight/4);
			enemyHealth.get(enemyHealth.size()-1).setVisible(true);
			this.add(enemyHealth.get(enemyHealth.size()-1));
			this.add(this.enemies.get(this.enemies.size()-1));
		}
		
		
		//	System.out.println("Invalid array size, " + this.enemies.size() + ", " + enemiesInUpdate.size());
			//for(EnemyTile tile : this.enemies)
			//{
				//System.out.println(tile.getID());
			//}
		//	System.out.println("End of list.");
			
		//	System.out.println("Invalid array size[], " + this.enemies.size() + ", " + enemiesInUpdate.size() + "{}");
			//for(EnemyTile tile : enemiesInUpdate)
		//	{
			//	System.out.println(tile.getID());
		//	}
			//System.out.println("End of list.");
			  
			 
			
			int i = 0;
			for(JLabel label : this.enemies)
			{
				if(i < enemies.size()){//Peter added this if statement to try to avoid index out of bounds errors but they still came
					label.setLocation(enemiesInUpdate.get(i).getLocation().x, enemiesInUpdate.get(i).getLocation().y); //TODO: Desone this is giving indexoutofbounds occasionally
					label.setIcon(enemiesInUpdate.get(i).getIcon());
				}
				if(label.getLocation().x > this.getWidth() || label.getLocation().y > this.getHeight() || label.getLocation().x < 0 || label.getLocation().y < 0)
				{
					this.enemies.remove(label);
				}
				i++;
			}
			i = 0;
			for(JProgressBar bar : this.enemyHealth)
			{
				bar.setLocation(enemiesInUpdate.get(i).getLocation().x, enemiesInUpdate.get(i).getLocation().y - (tileHeight/4));
				bar.setValue(((EnemyTile) enemiesInUpdate.get(i)).getHealth());
				i++;
			}
			repaint();
	}
	
	public void animateAttack(Point start, Point end, towerType type)
	{
		//System.out.println("Animating attack from (" + start.getX() + ", " + start.getY() + ") -> (" + end.getX() + ", " + end.getY() + ").");
		switch(type)
		{
		case NORMAL:
			Bone temp1 = new Bone();
			temp1.setIcon(tower1Proj);
			temp1.setPath(new Path(start.x, start.y, end.x, end.y));
			temp1.setSize(tileWidth/2, tileHeight/2);
			projectiles.add(temp1);
			this.add(temp1);
			break;
		case ELECTRIC:
			ElectricBall temp2 = new ElectricBall();
			temp2.setIcon(tower3Proj);
			temp2.setPath(new Path(start.x, start.y, end.x, end.y));
			temp2.setSize(tileWidth/2, tileHeight/2);
			projectiles.add(temp2);
			this.add(temp2);
			break;
		case FIRE:
			FireBall temp3 = new FireBall();
			temp3.setIcon(tower7Proj);
			temp3.setPath(new Path(start.x, start.y, end.x, end.y));
			temp3.setSize(tileWidth/2, tileHeight/2);
			projectiles.add(temp3);
			this.add(temp3);
			break;
		case GRASS:
			GrassBall temp4 = new GrassBall();
			temp4.setIcon(tower4Proj);
			temp4.setPath(new Path(start.x, start.y, end.x, end.y));
			temp4.setSize(tileWidth/2, tileHeight/2);
			projectiles.add(temp4);
			this.add(temp4);
			break;
		case MEWTWO:
			Bone temp5 = new Bone();
			temp5.setIcon(tower8Proj);
			temp5.setPath(new Path(start.x, start.y, end.x, end.y));
			temp5.setSize(tileWidth/2, tileHeight/2);
			projectiles.add(temp5);
			this.add(temp5);
			break;
		case POISON:
			GhostBall temp6 = new GhostBall();
			temp6.setIcon(tower5Proj);
			temp6.setPath(new Path(start.x, start.y, end.x, end.y));
			temp6.setSize(tileWidth/2, tileHeight/2);
			projectiles.add(temp6);
			this.add(temp6);
			break;
		case PSYCHIC:
			PsychicBall temp7 = new PsychicBall();
			temp7.setIcon(tower6Proj);
			temp7.setPath(new Path(start.x, start.y, end.x, end.y));
			temp7.setSize(tileWidth/2, tileHeight/2);
			projectiles.add(temp7);
			this.add(temp7);
			break;
		case WATER:
			WaterBall temp8 = new WaterBall();
			temp8.setIcon(tower2Proj);
			temp8.setPath(new Path(start.x, start.y, end.x, end.y));
			temp8.setSize(tileWidth/2, tileHeight/2);
			projectiles.add(temp8);
			this.add(temp8);
			break;
		default:
			break;
			
		}
	}
	


	public void towerUpgrade()
	{
		upgrading = true;
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
		
		//Test lists by peter
		for(JLabel l : this.enemiesPrefFrame){
			l.repaint();
		}
		for(JLabel l : this.enemiesThisFrame){
			l.repaint();
		}
		
		
		
		
		towerRange.repaint();
		for(JLabel label : towers)
		{
			g.drawImage(((ImageIcon) label.getIcon()).getImage(), label.getX(), label.getY(), this);
		}
		for(Projectile proj : projectiles)
		{
			proj.repaint();
		}
		
		if(enemySelected)
		{
			towerStatPanel.setLocation(selectedEnemy.getX()-tileWidth, selectedEnemy.getY());
			towerStats.setLocation(selectedEnemy.getX()-tileWidth + 5, selectedEnemy.getY() + 5);
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
			//pass row, column
			view.upgrade(new Point((((int) (selectedTower.getY()/tileHeight))),(int) (selectedTower.getX()/tileWidth)));
		}
	}
	
	class AnimationTimer implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			for(int i = 0; i < projectiles.size(); i++)
			{
				Projectile proj = projectiles.get(i);
				proj.setProgress((int) (proj.getProgress() + 20));
				proj.setLocation((int)  (proj.getLocationInGrid().getY() * tileWidth), (int) (proj.getLocationInGrid().getX() * tileHeight)); 
				//System.out.println(proj.getLocationInGrid().x + "," + proj.getLocationInGrid().y);
				if(!proj.isValid())
				{
					//System.out.println("Removing projectile");
					board.remove(projectiles.get(i));
					projectiles.remove(i);
				}
			}
		}
	}
	
	class EnemyClickListener implements MouseListener
	{
		public void mouseClicked(MouseEvent e)
		{
			selectedEnemyID = ((EnemyTile) e.getSource()).getID();
			if(enemySelected)
			{
				selectedEnemy = (EnemyTile) (e.getSource());
				towerStatPanel.setLocation(((EnemyTile) e.getSource()).getX()-tileWidth, ((EnemyTile) e.getSource()).getY());
				towerStats.setText("Health: " + selectedEnemy.getHealthLeft() + "/" + selectedEnemy.getMaxHealth() + "\nSpeed: " + selectedEnemy.getSpeed() + "\nAttack: " + selectedEnemy.getAttack() + "\nDefense: " + selectedEnemy.getAttack() + "\nWorth: " + selectedEnemy.getWorth());
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
				towerStats.setText("Health: " + selectedEnemy.getHealthLeft() + "/" + selectedEnemy.getMaxHealth() + "\nSpeed: " + selectedEnemy.getSpeed() + "\nAttack: " + selectedEnemy.getAttack() + "\nDefense: " + selectedEnemy.getAttack() + "\nWorth: " + selectedEnemy.getWorth());
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
			enemySelected = false;
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
				//towerStats.setText("Health: " + label.getHealth() + "\nNeed getSpeed()" + "\nNeed getAttack()" + "\nNeed getMaxHealth()");
				selectedEnemyID = label.getID();
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
