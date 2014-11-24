package GUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;

import GameController.Pikachu;
import GameController.CeruleanGym;
import GameController.Enemy;
import GameController.Tower;
import model.Level0Map;
import model.Map;
import client.GameClient;
import client.Player;



//Personal stuff:
//Find grid coordinate of point: ((arg0.get!() - scrollLocation.!) * 20)/(bg.get!(this))
/**
 * This is the class that runs the GUI, recieves info from the GameClient
 * @author Desone
 * 
 * public GameView(gameType type, String user, GameClient client, Player player)
 * void tempAttack()
 */

public class GameView extends JFrame implements MouseListener, MouseWheelListener, MouseMotionListener
{
	JFrame frame;
	Image bg;
	Image origBG;
	ImageIcon towerStoreBG;
	Image towerStoreBGOrig;
	JLabel selectedTowerFromStore;
	JPanel board;
	JPanel towerStorePanel;
	Point scrollLocation;
	Point scrollLast;
	Point mouseLoc;
	double viewScale = 1;
	boolean repaintGUI = true;
	boolean clickedTowerStore = false;
	boolean trueForShrink;
	Timer animationTimer;
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	int tileWidth;
	int tileHeight;
	
	String pewterProjectile = "/images/spinningBone.gif";
	
	String pewterTower = "/images/cuboneStatic.png";
	
	String enemy1ImageUp = "/images/pikachuUp.gif";
	String enemy1ImageDn = "/images/pikachuDown.gif";
	String enemy1ImageL = "/images/pikachuLeft.gif";
	String enemy1ImageR = "/images/pikachuRight.gif";
	
	HashMap<Tower, JLabel> towerMap;
	HashMap<Enemy, JLabel> enemyMap;
	
	String user;
	
	Player player;
	
	int selectedTowerType;
	
	int levelWidth = 20;
	int levelHeight = 13;
	
	Image tower1Image;
	Image tower2Image;
	Image tower3Image;
	Image tower4Image;
	
	List towers;
	List enemies;
	
	double testSpriteProgress = 0;
	
	map1Path path;
	Map map;
	
	Image pik;
	JLabel pikLabel;
	
	int qualitySetting;
	
	GameClient client;
	
	//Temp for attacking
	int tempAttackTimerCounter = 0;
	JLabel tempProjectile;
	JLabel tempCubone;
	Path tempAttackPath;
	Point towerLocation;
	//End temp for attacking
	
	public static void main(String[] args)
	{
		new GameClient();
		//GameView temp = new GameView(gameType.SINGLE, "Billy", new GameClient(), new Player("Billy", 10, 10));
		//temp.update(null, null);
	}
	
	/**
	 * Creates and runs the gui with the given parameters
	 * @param type Specifies if the game is multiplayer or single player
	 * @param user The username of the player
	 * @param client The client under which the gui will run
	 * @param player The player that is playing the game
	 */
	public GameView(gameType type, String user, GameClient client, Player player)
	{
		//Setup for the JFrame, sets size, closeOperation, adds listeners, and so on
		//Have setting in settings to choose between smooth and fast scaling
		this.user = user;
		qualitySetting = Image.SCALE_FAST;
		this.client = client;
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(screen.width/8, screen.height/8, (3*screen.width)/4, (3*screen.height)/4);
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		setBackground(Color.BLUE);
		setLayout(null);
		setTitle("Pokemon Tower Defense - " + user);
		addMouseListener(this);
		addMouseWheelListener(this);
		addComponentListener(new resizeListener());
		addMouseMotionListener(this);
		
		animationTimer = new Timer(50, null);
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		Cursor cursor = kit.createCustomCursor(createImageIcon("/images/cursor.png").getImage(), new Point(0,0), "Cursor");
		setCursor(cursor);
		
		//Initialize scrollLocation for scrolling the map
		scrollLocation = new Point(0,0);
		
		//Set the lookAndFeel to be of the operating system
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e)
		{
			System.out.println("Unable to set operating system look and feel");
		}
		
		//Create correctly scaled image to use as background (map)
		//TODO: make this a variable, so that different maps can be used.
		ImageIcon mapTemp = createImageIcon("/images/map1.png");
		bg = (mapTemp.getImage()).getScaledInstance(getSize().width, (3*getSize().height)/4, Image.SCALE_SMOOTH);
		mapTemp.setImage(bg);
		JLabel labelTemp = new JLabel(mapTemp);
		labelTemp.setBounds(0, 0, getSize().width, (3*getSize().height)/4);
		
		//Create and size the towerStore background image
		towerStoreBG = createImageIcon("/images/towerStore.png");
		towerStoreBGOrig = towerStoreBG.getImage();
		towerStoreBG.setImage(towerStoreBGOrig.getScaledInstance(getSize().width, getSize().height/4, Image.SCALE_SMOOTH));
		JLabel temp = new JLabel(towerStoreBG);
		temp.setBounds(0, 0, getSize().width, getSize().height/4);
		towerStorePanel = new JPanel();
		towerStorePanel.setLayout(null);
		towerStorePanel.setBounds(0, (int) ((getSize().height)/2), getSize().width, getSize().height/4 );
		towerStorePanel.setBackground(null);
		towerStorePanel.add(temp);
		
		//Create the panel for the game "board"
		board = new JPanel();
		board.setBounds(0,0,getSize().width, (3*getSize().height)/4);
		board.setLayout(null);
		board.add(labelTemp);
		board.setBackground(Color.BLUE);
		
		selectedTowerFromStore = new JLabel(new ImageIcon(createImageIcon("/images/cuboneStatic.png").getImage().getScaledInstance(getSize().width/20, getSize().height/12, Image.SCALE_SMOOTH)));
		selectedTowerFromStore.setBounds(0,0,getSize().width/20, getSize().height/12);
		selectedTowerFromStore.setVisible(false);
		
		
		pik = createImageIcon("/images/pikachuStatic.png").getImage().getScaledInstance(getSize().width/20, board.getSize().height/13, Image.SCALE_SMOOTH);
		path = new map1Path();
		
		//More Temp stuff
		tempProjectile = new JLabel(new ImageIcon(createImageIcon("/images/spinningBone.gif").getImage().getScaledInstance(this.getWidth()/40, this.getHeight()/26, Image.SCALE_FAST)));
		tempProjectile.setBounds(100, 100, 25, 25);
		tempCubone = new JLabel(new ImageIcon(createImageIcon("/images/cuboneStatic.png").getImage().getScaledInstance(this.getWidth()/levelWidth, this.getHeight()/levelHeight, qualitySetting)));
		tempCubone.setBounds((5*(board.getWidth()/levelWidth)), (6*(board.getHeight()/levelHeight)), (board.getWidth()/levelWidth), (board.getHeight()/levelHeight));
		//No more temp stuff
		
		attackAnimation attackAnimationTimer = new attackAnimation();
		Timer timer = new Timer(50, new TimerListener());
		timer.addActionListener(attackAnimationTimer);
		timer.start();
		
		
		add(selectedTowerFromStore);
		add(towerStorePanel);
		//temp adding
		add(tempProjectile);
		add(tempCubone);
		//done
		add(board);
		
		tempAttack();
		
		frame = this;
		repaint();
		setVisible(true);
		
		
		
		//Testing
	}
	
	
	//Temporary methods
	void tempAttack()
	{
		tempAttackPath = new Path(1,1,5,5);
		Timer temp = new Timer(20, new tempAttackTimer());
		temp.start();
	}
	
	class tempAttackTimer implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			tempAttackTimerCounter++;
			if(tempAttackTimerCounter <= 12)
			{
				tempProjectile.setVisible(true);
				tempProjectile.setLocation((int) ((tempAttackPath.getLocation(tempAttackTimerCounter * 8).x) * ((frame.getWidth() / levelWidth) * viewScale) + scrollLocation.x), (int) ((tempAttackPath.getLocation(tempAttackTimerCounter * 8).y) * ((frame.getHeight() / levelHeight) * viewScale) + scrollLocation.y));
			}
			else
			{
				tempProjectile.setVisible(false);
			}

			if(tempAttackTimerCounter >= 50)
			{
				tempAttackTimerCounter = 0;
				
				//System.out.println("Movin da projectile");
			}
		}
	}
	
	/**
	 * Animates a projectile of the given type between the two points
	 * @param tower The point from which to animate
	 * @param enemy The point to which to animate
	 * @param type The type of tower that launched the projectile
	 * @return Whether or not the gui was able to animate
	 */
	public boolean animateAttack(Point tower, Point enemy, towerType type)
	{
		if(type == towerType.NORMAL)
		{
			System.out.println("This tower");
			JLabel proj = new JLabel(new ImageIcon(createImageIcon(pewterProjectile).getImage().getScaledInstance(this.getWidth()/(levelWidth*2), this.getHeight()/(levelHeight*2), Image.SCALE_FAST)));
			proj.setLocation(tower.x, tower.y);
			Bone newProjectile = new Bone();
			newProjectile.setLabel(proj);
			projectiles.add(newProjectile);
			board.add(proj);
		}
		return true;
	}
	
	class attackAnimation implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			for(int i = 0; i < projectiles.size(); i++)
			{
				if(projectiles.get(i).isValid())
				{
					projectiles.get(i).setProgress(projectiles.get(i).getProgress() + 5);
				}
				else
				{
					board.remove(projectiles.get(i).getLabel());
					projectiles.remove(i);
				}
			}
			
		}
		
	}
	
	public void setGridSize(Point size)
	{
		levelWidth = size.x;
		levelHeight = size.y;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paint(g);
		board.repaint();
		selectedTowerFromStore.repaint();
		g.drawImage(pik,(int) ((path.getLocation(testSpriteProgress).x/20) * viewScale * board.getSize().width),(int) ((path.getLocation(testSpriteProgress).y/9) * viewScale * board.getSize().height) + 15, this);
		g.drawRect((int) (getSize().width)/15 + 10,(int) ((3*getSize().height)/3.8) + 30, (int) (getSize().width / 9.5), getSize().height / 8);
		g.drawRect((int) (2.6*getSize().width)/15 + 10,(int) ((3*getSize().height)/3.8) + 30, (int) (getSize().width / 9.5), getSize().height / 8);
	}
	
	/**
	 * Creates an image icon based on the given URL
	 * @param url The location of the target image
	 * @return An ImageIcon created from the image found at the url
	 */
	ImageIcon createImageIcon(String url)
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
	
	class TimerListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			testSpriteProgress += 0.2;
			repaint();
		}
	}
	
	/**
	 * Simple enum for game type
	 * @author Desone
	 *
	 */
	enum gameType{SINGLE, MULTI}
	
	public enum towerType{NORMAL,WATER,ELECTRIC,GRASS,POISON,PHYCHIC,FIRE,GROUND}
	
	/**
	 * Listens for the window resizing, and scales elements as needed
	 * @author Desone
	 *
	 */
	class resizeListener implements ComponentListener
	{
		public void componentResized(ComponentEvent arg0)
		{
			
			tower1Image = createImageIcon("/images/cuboneStatic.png").getImage().getScaledInstance((int) (getSize().width / 9.5), getSize().height / 8,Image.SCALE_SMOOTH);
			ImageIcon tower1Icon = new ImageIcon(tower1Image);
			JLabel tower1temp = new JLabel(tower1Icon);
			tower1temp.setBounds((int) (getSize().width/15),(int) (towerStorePanel.getSize().height/6), (int) (getSize().width / 9.5), getSize().height / 8);
			
			//Temp stuff again
			tempProjectile.setIcon(new ImageIcon(createImageIcon("/images/spinningBone.gif").getImage().getScaledInstance((int) ((frame.getWidth()/40) * viewScale), (int) ((frame.getHeight()/26) * viewScale), Image.SCALE_FAST)));
			tempProjectile.setSize((int) ((frame.getWidth()/40) * viewScale), (int) ((frame.getHeight()/26) * viewScale));
			//Last of it
			
			towerStorePanel.setBounds(0, (3*frame.getSize().height)/4, frame.getSize().width, frame.getSize().height/4);
			towerStorePanel.removeAll();
			towerStoreBG.setImage(towerStoreBGOrig.getScaledInstance(frame.getSize().width, frame.getSize().height/5, Image.SCALE_SMOOTH));
			JLabel temp = new JLabel(towerStoreBG);
			temp.setBounds(0, 0, frame.getSize().width, frame.getSize().height/5);
			towerStorePanel.add(tower1temp);
			towerStorePanel.add(temp);
			
			selectedTowerFromStore.removeAll();
			selectedTowerFromStore.setIcon(new ImageIcon(createImageIcon("/images/cuboneStatic.png").getImage().getScaledInstance(getSize().width/20, getSize().height/12, Image.SCALE_SMOOTH)));
			//selectedTowerFromStore.setBounds(100, 100, selectedTowerFromStore.getWidth(), selectedTowerFromStore.getHeight());
			//repaintGUI = true;
			
			
			ImageIcon mapTemp = createImageIcon("/images/map1.png");
			bg = (mapTemp.getImage()).getScaledInstance((int) (getSize().width * viewScale), (int) ((3*getSize().height)/4 * viewScale), Image.SCALE_SMOOTH);
			board.removeAll();
			
			mapTemp.setImage(bg);
			JLabel labelTemp = new JLabel(mapTemp);
			labelTemp.setBounds(0, 0,(int) (getSize().width * viewScale),(int) (viewScale * (3*getSize().height)/4));
			
			
			board.add(labelTemp);
			
			board.setBounds(board.getX(), board.getY(), (int) (getSize().width * viewScale), (int) ((3*getSize().height)/4 * viewScale));
			//repaint();
		}
		public void componentShown(ComponentEvent arg0){}
		public void componentHidden(ComponentEvent arg0){}
		public void componentMoved(ComponentEvent arg0){}
	}
	
	public void mouseClicked(MouseEvent arg0)
	{
		//Not used yet, will find grid location of click and bring up options for towers
		
	}
	
	/**
	 * serves to initialize there the mouse starts from in mouseDragged
	 */
	public void mousePressed(MouseEvent arg0)
	{
		
		if(arg0.getY() >= (3*getSize().height)/4)
		{
			clickedTowerStore = true;
			
			Toolkit kit = Toolkit.getDefaultToolkit();
			Cursor cursor = kit.createCustomCursor(createImageIcon("/images/transPixel.png").getImage(), new Point(0,0), "Cubone");
			setCursor(cursor);
			
			System.out.println("Building a towa!");
			Rectangle tower1 = new Rectangle(getSize().width/15 + 10,(int) ((3*getSize().height)/3.8) + 30, (int) (getSize().width / 9.5), getSize().height / 8);
			if(tower1.contains(arg0.getPoint()))
			{
				System.out.println("Attempting to build first tower");
				selectedTowerFromStore.setBounds(arg0.getX()-30, arg0.getY()-45, selectedTowerFromStore.getWidth(), selectedTowerFromStore.getHeight());
				selectedTowerFromStore.setVisible(true);
				selectedTowerType = NORMAL;
				return;
			}
			Rectangle tower2 = new Rectangle((int)(2.6*getSize().width)/15 + 10,(int) ((3*getSize().height)/3.8) + 30, (int) (getSize().width / 9.5), getSize().height / 8);
			if(tower2.contains(arg0.getPoint()))
			{
				System.out.println("Attempting to build second tower");
				return;
			}
			Rectangle tower3 = new Rectangle((int)(4.25*getSize().width)/15 + 10,(int) ((3*getSize().height)/3.8) + 30, (int) (getSize().width / 9.5), getSize().height / 8);
			if(tower3.contains(arg0.getPoint()))
			{
				System.out.println("Attempting to build third tower");
				return;
			}
			Rectangle tower4 = new Rectangle((int)(5.85*getSize().width)/15 + 10,(int) ((3*getSize().height)/3.8) + 30, (int) (getSize().width / 9.5), getSize().height / 8);
			if(tower4.contains(arg0.getPoint()))
			{
				System.out.println("Attempting to build fourth tower");
				return;
			}
			Rectangle tower5 = new Rectangle((int)(7.45*getSize().width)/15 + 10,(int) ((3*getSize().height)/3.8) + 30, (int) (getSize().width / 9.5), getSize().height / 8);
			if(tower5.contains(arg0.getPoint()))
			{
				System.out.println("Attempting to build fifth tower");
				return;
			}
			Rectangle tower6 = new Rectangle((int)(9.05*getSize().width)/15 + 10,(int) ((3*getSize().height)/3.8) + 30, (int) (getSize().width / 9.5), getSize().height / 8);
			if(tower6.contains(arg0.getPoint()))
			{
				System.out.println("Attempting to build sixth tower");
				return;
			}
			Rectangle tower7 = new Rectangle((int)(10.67*getSize().width)/15 + 10,(int) ((3*getSize().height)/3.8) + 30, (int) (getSize().width / 9.5), getSize().height / 8);
			if(tower7.contains(arg0.getPoint()))
			{
				System.out.println("Attempting to build seventh tower");
				return;
			}
			Rectangle tower8 = new Rectangle((int)(12.3*getSize().width)/15 + 10,(int) ((3*getSize().height)/3.8) + 30, (int) (getSize().width / 9.5), getSize().height / 8);
			if(tower8.contains(arg0.getPoint()))
			{
				System.out.println("Attempting to build eighth tower");
				return;
			}
		}
		scrollLast = arg0.getPoint();
	}
	
	class tempTower extends Tower
	{
		public tempTower(String Name, int Attack, int Radius, double FireRateSec, String PlayersName, String Image, int cost)
		{
			super(Name, Attack, Radius, FireRateSec, PlayersName, Image, cost);
		}
		public boolean AttackEnemy(ArrayList<Enemy> enemies)
		{
			return false;
		}
		public boolean setModifer()
		{
			return false;
		}
		public boolean getModifer()
		{
			return false;
		}
		public boolean increaseFireRate(double amountToIncrease)
		{
			return false;
		}
		public boolean levelUp()
		{
			return false;
		}
	}
	
	/**
	 * Zooms in and out of the map based on direction of scrolling
	 */
	public void mouseWheelMoved(MouseWheelEvent arg0)
	{
		if((viewScale < 2 && arg0.getWheelRotation() == -1) || (viewScale > 0.5 && arg0.getWheelRotation() == 1))
		{
			switch(arg0.getWheelRotation())
			{
			case 1:
				viewScale *= 0.8;
				trueForShrink = true;
				break;
				//scrollLocation.x = (int) (scrollLocation.x + ((arg0.getX() - getWidth()/2)));
			case -1:
				viewScale *= 1.1;
				trueForShrink = false;
				break;
				//scrollLocation.x = (int) (scrollLocation.x + ((getWidth()/2 - arg0.getX())));
			default:
				break;
			}
		}
		else
		{
			return;
		}
		
		ImageIcon mapTemp = createImageIcon("/images/map1.png");
		bg = (mapTemp.getImage()).getScaledInstance((int) (getSize().width * viewScale), (int) ((3*getSize().height)/4 * viewScale), Image.SCALE_SMOOTH);
		board.removeAll();
		
		tempProjectile.setIcon(new ImageIcon(createImageIcon("/images/spinningBone.gif").getImage().getScaledInstance((int) ((frame.getWidth()/40) * viewScale), (int) ((frame.getHeight()/26) * viewScale), Image.SCALE_FAST)));
		tempProjectile.setSize(tileWidth /2, tileHeight*2);
		tempCubone.setIcon(new ImageIcon(createImageIcon("/images/cuboneStatic.png").getImage().getScaledInstance((int) ((frame.getWidth()/levelWidth) * viewScale), (int) ((frame.getHeight()/levelHeight) * viewScale), qualitySetting)));
		//tempCubone.setLocation((int) (tempCubone.getLocation().x * viewScale),(int) (tempCubone.getLocation().y * viewScale));
		upadteTileSize();
		tempCubone.setSize(tileWidth, tileHeight);
		
		mapTemp.setImage(bg);
		JLabel labelTemp = new JLabel(mapTemp);
		labelTemp.setBounds(0, 0,(int) (getSize().width * viewScale),(int) (viewScale * (3*getSize().height)/4));
		
		
		board.add(labelTemp);
		
		board.setBounds(board.getX(), board.getY(), (int) (getSize().width * viewScale), (int) ((3*getSize().height)/4 * viewScale));
		upadteTileSize();
		
		if(trueForShrink)
		{
			tempCubone.setLocation(scrollLocation.x + (5*tileWidth), scrollLocation.y + (6*tileHeight));
		}
		else
		{
			tempCubone.setLocation(scrollLocation.x + (5*(board.getWidth()/levelWidth)), scrollLocation.y + (6*(board.getHeight()/levelHeight)));
		}
		repaint();
	}

	/**
	 * Moves the map around according to where the mouse is being dragged
	 */
	public void mouseDragged(MouseEvent arg0)
	{
		if(clickedTowerStore)
		{
			mouseLoc = arg0.getPoint();
			selectedTowerFromStore.setBounds(mouseLoc.x - 30, mouseLoc.y - 45, selectedTowerFromStore.getWidth(), selectedTowerFromStore.getHeight());
			repaint();
			//System.out.println("Dragging selected tower to (" + mouseLoc.x + ", " + mouseLoc.y + ").");
			return;
		}
		scrollLocation.x += arg0.getX() - scrollLast.x;
		scrollLocation.y += arg0.getY() - scrollLast.y;
		tempCubone.setLocation(scrollLocation.x + (5*(board.getWidth()/levelWidth)), scrollLocation.y + (6*(board.getHeight()/levelHeight)));
		scrollLast = arg0.getPoint();
		board.setBounds(scrollLocation.x, scrollLocation.y,(int) (frame.getSize().width * viewScale),(int) ((3*frame.getSize().height)/4 * viewScale));
		repaint();
		//towerStorePanel.repaint();
	}
	
	public void update(List<TowerImage> towers, List<EnemyImage> enemies)
	{
		board.removeAll();
		board.add(new JLabel(new ImageIcon(bg)));	
		updateTileSize();
		for(TowerImage image : towers)
		{
			JLabel temp = new JLabel(new ImageIcon(createImageIcon(image.getImageURL()).getImage().getScaledInstance(tileWidth, tileHeight, Image.SCALE_FAST)));
		}
		System.out.println("Updating images");
	}
	
	void updateTileSize()
	{
		tileWidth = (int) ((frame.getWidth()/levelWidth) * viewScale);
		tileHeight = (int) ((frame.getHeight()/levelHeight) * viewScale);
	}

	enum direction{NORTH, EAST, SOUTH, WEST};
	
	public direction direction(Enemy enemy)
	{
		if(enemy.getLocation().x - enemy.getNextLocation().x > 0)
		{
			System.out.println("L");
		}
		else if(enemy.getLocation().x - enemy.getNextLocation().x < 0)
		{
			System.out.println("R");
		}
		else if(enemy.getLocation().y - enemy.getNextLocation().y < 0)
		{
			System.out.println("U");
		}
		else if(enemy.getLocation().y - enemy.getNextLocation().y < 0)
		{
			System.out.println("D");
		}
		return direction.NORTH;
	}
	
	public void addEnemy(Enemy enemy)
	{
		JLabel temp;
		switch(direction(enemy))
		{
		case NORTH:
			temp = new JLabel(new ImageIcon(createImageIcon(enemy1ImageUp).getImage().getScaledInstance((int) ((frame.getWidth()/levelWidth) * viewScale), (int) ((frame.getHeight()/levelHeight) * viewScale),Image.SCALE_FAST)));
		case SOUTH:
			temp = new JLabel(new ImageIcon(createImageIcon(enemy1ImageDn).getImage().getScaledInstance((int) ((frame.getWidth()/levelWidth) * viewScale), (int) ((frame.getHeight()/levelHeight) * viewScale),Image.SCALE_FAST)));
		case EAST:
			temp = new JLabel(new ImageIcon(createImageIcon(enemy1ImageL).getImage().getScaledInstance((int) ((frame.getWidth()/levelWidth) * viewScale), (int) ((frame.getHeight()/levelHeight) * viewScale),Image.SCALE_FAST)));
		case WEST:
			temp = new JLabel(new ImageIcon(createImageIcon(enemy1ImageR).getImage().getScaledInstance((int) ((frame.getWidth()/levelWidth) * viewScale), (int) ((frame.getHeight()/levelHeight) * viewScale),Image.SCALE_FAST)));
		default:
			temp = new JLabel(new ImageIcon(createImageIcon(enemy1ImageUp).getImage().getScaledInstance((int) ((frame.getWidth()/levelWidth) * viewScale), (int) ((frame.getHeight()/levelHeight) * viewScale),Image.SCALE_FAST)));
		}
		enemyMap.put(enemy, temp);
		temp.setLocation(scrollLocation.x + (enemy.getLocation().x*(board.getWidth()/levelWidth) - (enemy.getLocation().x*(board.getWidth()/levelWidth) % enemy.getLocation().x*(board.getWidth()/levelWidth))), scrollLocation.y + (enemy.getLocation().y*(board.getHeight()/levelHeight)));
		board.add(temp);
	}
	
	public void removeEnemy(Enemy enemy)
	{
		
	}
	
	public void addTower(Tower tower)
	{
		if(tower == null)
		{
			System.out.println("Null tower");
			return;
		}
		JLabel temp = new JLabel(new ImageIcon(createImageIcon(pewterTower).getImage().getScaledInstance(this.getWidth()/levelWidth, this.getHeight()/levelHeight, qualitySetting)));
		temp.setLocation(scrollLocation.x + (tower.getPosition().x*(board.getWidth()/levelWidth)), scrollLocation.y + (tower.getPosition().y*(board.getHeight()/levelHeight)));
		temp.setSize(this.getWidth()/levelWidth, this.getHeight()/levelHeight);
		System.out.println("Placing " + tower.getGymName() + " Tower at (" + tower.getPosition().x + ", " + tower.getPosition().y + ")");
		towerMap.put(tower, temp);
		board.add(temp);
	}
	
	//Could also just send me the Point(rows, columns) of where you want to sell tower if that's easier) -PH
	public void removeTower(Tower tower)
	{
		//TODO:
	}
	
	public void mouseMoved(MouseEvent arg0)
	{
		//if(clickedTowerStore)
		//{
			mouseLoc = arg0.getPoint();
		//}
	}
	public void mouseEntered(MouseEvent arg0){}
	public void mouseExited(MouseEvent arg0){}
	
	//This is where I will send commands to the client, to send to the server, requesting to build a tower.
	//Gui should not handle tower building after sending the command, except to update the list of towers
	
	
	public void mouseReleased(MouseEvent arg0)
	{
		if(clickedTowerStore)
		{
			clickedTowerStore = false;
			selectedTowerFromStore.setVisible(false);

			Toolkit kit = Toolkit.getDefaultToolkit();
			Cursor cursor = kit.createCustomCursor(createImageIcon("/images/cursor.png").getImage(), new Point(0,0), "Cursor");
			setCursor(cursor);
			
			Point loc = new Point(((arg0.getX() - scrollLocation.x) * levelWidth)/(bg.getWidth(this)), ((arg0.getY() - scrollLocation.y) * levelHeight)/(bg.getWidth(this)));
			
			switch(selectedTowerType)
			{
			case NORMAL:
				client.addTower(towerType.NORMAL, loc);
				//towerLocation = new Point(arg0.getX(), arg0.getY());
				System.out.println("Attempting to place a normal tower at (" + loc.x + ", " + loc.y + ")");
				//client.addTower(new tempTower("Test Tower", 10, 3, 1, user, pewterTower, 0), new Point(((arg0.getX() - scrollLocation.x) * 20)/(bg.getWidth(this)), ((arg0.getY() - scrollLocation.y) * 20)/(bg.getWidth(this))));
				break;
			default:
				System.out.println("Attempting to place a tower");
			}
			System.out.println(loc);
		}
	}
	public void windowClosing(WindowEvent arg0)
	{
		client.disconnect();
	}
	public void windowActivated(WindowEvent arg0){}
	public void windowClosed(WindowEvent arg0){}
	public void windowDeactivated(WindowEvent arg0){}
	public void windowDeiconified(WindowEvent arg0){}
	public void windowIconified(WindowEvent arg0){}
	public void windowOpened(WindowEvent arg0){}
}
