package GUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.UIManager;

import GameController.Enemy.directionFacing;
import client.GameClient;
import client.Player;



//Personal stuff:
//Find grid coordinate of point: ((arg0.get!() - scrollLocation.!) * 20)/(bg.get!(this))
/**
 * This is the class that runs the GUI, receives info from the GameClient
 * @author Desone
 * 
 * public GameView(gameType type, String user, GameClient client, Player player)
 * void tempAttack()
 */

public class GameView extends JFrame implements MouseListener, MouseMotionListener
{
	private JFrame frame;
	private Image bg;
	private Image origBG;
	private ImageIcon towerStoreBG;
	private Image towerStoreBGOrig;
	private JLabel selectedTowerFromStore;
	private JPanel board;
	private JPanel towerStorePanel;
	private Point scrollLocation;
	private Point scrollLast;
	private Point mouseLoc;
	private JPanel playerMoneyPanel;
	private JLabel playerMoneyLabel;
	private JPanel playerHealthPanel;
	private JProgressBar playerHealthLabel;
	double viewScale = 1;
	boolean repaintGUI = true;
	boolean clickedTowerStore = false;
	boolean trueForShrink;
	private Timer animationTimer;
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	int tileWidth = 102;
	int tileHeight = 155; 
	
	
	
	private String pewterProjectile = "/images/spinningBone.gif";
	
	private String pewterTower = "/images/cuboneStatic.png";
	
	private String enemyPathTileImage = "/images/enemyTile.png"; //Switch this later, only
																		//set it to this for convenience to not add another image
	
	private String enemy1ImageUp = "/images/pikachuUp.gif";
	private String enemy1ImageDn = "/images/pikachuDown.gif";
	private String enemy1ImageL = "/images/pikachuLeft.gif";
	private String enemy1ImageR = "/images/pikachuRight.gif";
	
	//ArrayList<JLabel> towerList;
	//HashMap<Enemy, JLabel> enemyMap;
	
	private String user;
	
	private Player player;
	
	int selectedTowerType; //Should replace this with the enum towerType -PH
	
	//No need to hardcode now, I update them when map is created
	//with the setGridSize() method -PH
	private int levelWidth;
	private int levelHeight;
	
	private String mapBackgroundImageURL;
	
	private List<Point> enemyPathCoords; //The list of points containing the coordinates
	//(rowsdown, columnsacross) of where the enemy path tiles should be painted in the grid
	
	private int playerHP; //The player's current HP, display in corner. it is updated
	
	private int playerMoney; //The player's current $, display in corner. it is updated
	
	//updated by model at every tick
	//private List<TowerImage> towersLast; //A list of all the TowerImages
	//private List<EnemyImage> enemiesLast; //A list of all the EnemyImages
	private List<JLabel> towers = new ArrayList<JLabel>(); //A list of all the JLabels on board based on sent TowerImages
	private List<JLabel> enemies = new ArrayList<JLabel>(); //A list of all the JLabels on board based on sent EnemyImages
	private List<JLabel> pathTiles = new ArrayList<JLabel>(); //A list of all the JLabels on board based on enemyPathCoords
	
	
	private ImageIcon tower1Image;
	private ImageIcon tower2Image;
	private ImageIcon tower3Image;
	private ImageIcon tower4Image;
	
	
	
	//double testSpriteProgress = 0;
	
	//map1Path path;
	//Map map;
	
	private Image pik;
	private JLabel pikLabel;
	
	int qualitySetting;
	
	private GameClient client;
	
	//Temp for attacking
	//int tempAttackTimerCounter = 0;
	//JLabel tempProjectile;
	//JLabel tempCubone;
	//Path tempAttackPath;
	//Point towerLocation;
	
	//End temp for attacking
	
	//Removed main method, class shouldn't have one
		
		/*
		GameView temp = new GameView(gameType.SINGLE, "Billy", new GameClient(), new Player("Billy", 10, 10));
		List tempTowers = new ArrayList<>();
		List tempEnemies = new ArrayList<>();
		CeruleanGym tempImage = new CeruleanGym("");
		tempImage.setPlaceOnBoard(new Point(5,2));
		tempTowers.add(new TowerImage(tempImage));
		//tempImage.
		//tempTowers.add();
		System.out.println(((TowerImage) tempTowers.get(0)).getLocation().x);
		temp.update(tempTowers, tempEnemies);
		*/
	
	
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
		//addMouseWheelListener(this);
		//addComponentListener(new resizeListener());
		addMouseMotionListener(this);
		
		//animationTimer = new Timer(50, null);
		//towersLast = new ArrayList<TowerImage>();
		
		
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
		
		//Moved createScaledBackgroundImage lines to the same named method, call when we know
		//the map's background image from first update- PH
		
		selectedTowerFromStore = new JLabel(new ImageIcon(createImageIcon("/images/cuboneStatic.png").getImage().getScaledInstance(getSize().width/20, getSize().height/12, Image.SCALE_SMOOTH)));
		selectedTowerFromStore.setBounds(0,0,getSize().width/20, getSize().height/12);
		selectedTowerFromStore.setVisible(false);
		this.add(selectedTowerFromStore);
		
		playerMoneyPanel = new JPanel();
		playerMoneyPanel.setBounds(0,0, 100, 25);
		playerMoneyLabel = new JLabel("$0");
		playerMoneyPanel.add(playerMoneyLabel);
		this.add(playerMoneyPanel);
		
		playerHealthPanel = new JPanel();
		playerHealthPanel.setBounds(getWidth()-200, 0, 200, 25);
		playerHealthLabel = new JProgressBar();
		playerHealthLabel.setValue(100);
		playerHealthLabel.setBackground(Color.RED);
		playerHealthLabel.setForeground(Color.GREEN);
		playerHealthLabel.setMaximum(100);
		playerHealthLabel.setMinimum(0);
		playerHealthLabel.setStringPainted(true);
		Font tempFont = new Font("Comic Sans MS", Font.PLAIN, 10);
		playerHealthLabel.setFont(tempFont);
		playerHealthPanel.add(playerHealthLabel);
		this.add(playerHealthPanel);
		
		//Create and size the towerStore background image
		towerStoreBG = createImageIcon("/images/towerStore.png");
		towerStoreBGOrig = towerStoreBG.getImage();
		towerStoreBG.setImage(towerStoreBGOrig.getScaledInstance(getSize().width, getSize().height/4, Image.SCALE_SMOOTH));
		JLabel temp = new JLabel(towerStoreBG);
		temp.setBounds(0, 0, getSize().width, getSize().height/4);
		towerStorePanel = new JPanel();
		towerStorePanel.setLayout(null);
		towerStorePanel.setBounds(0, (int) (3*(getSize().height)/4)-20, getSize().width, getSize().height/4 );
		towerStorePanel.setBackground(null);
		towerStorePanel.add(temp);
		this.add(towerStorePanel); //Added this line -PH
		
		//Create the panel for the game "board"
		board = new Board();
		board.setBounds(0,0,getSize().width, (3*getSize().height)/4);
		board.setLayout(null);
		//board.add(labelTemp);
		board.setBackground(Color.BLUE);
		this.add(board); //moved this line here -PH
		
		
		
		
		setResizable(false);
		
		
		//pik = createImageIcon("/images/pikachuStatic.png").getImage().getScaledInstance(getSize().width/20, board.getSize().height/13, Image.SCALE_SMOOTH);
		//path = new map1Path();
		
		/*
		//More Temp stuff
		tempProjectile = new JLabel(new ImageIcon(createImageIcon("/images/spinningBone.gif").getImage().getScaledInstance(this.getWidth()/40, this.getHeight()/26, Image.SCALE_FAST)));
		tempProjectile.setBounds(100, 100, 25, 25);
		tempCubone = new JLabel(new ImageIcon(createImageIcon("/images/cuboneStatic.png").getImage().getScaledInstance(this.getWidth()/levelWidth, this.getHeight()/levelHeight, qualitySetting)));
		tempCubone.setBounds((5*(board.getWidth()/levelWidth)), (6*(board.getHeight()/levelHeight)), (board.getWidth()/levelWidth), (board.getHeight()/levelHeight));
		//No more temp stuff
		*/
		//not sure what stuff below does -PH
		/*
		attackAnimation attackAnimationTimer = new attackAnimation();
		Timer timer = new Timer(50, new TimerListener());
		timer.addActionListener(attackAnimationTimer);
		timer.start();
		
		
		add(selectedTowerFromStore);
		add(towerStorePanel);
		*/
		//temp adding
		//add(tempProjectile);
		//add(tempCubone);
		//done
		//add(board);
		
		//tempAttack();
		
		frame = this;
		//repaint();
		
		//updateTileSize(); //Call this after levelsize has been set, not in constructor -PH
		
		/*
		tower1Image = new ImageIcon(createImageIcon(pewterTower).getImage().getScaledInstance(tileWidth, tileHeight, Image.SCALE_FAST));
		//towerList = new ArrayList<JLabel>();
		
		JLabel temp1 = new JLabel(tower1Image);
		temp.setSize(tileWidth, tileHeight);
		temp.setLocation((tileWidth * 7), (tileHeight * 5));
		//towerList.add(temp1);
		board.add(temp1);
		
		
		//
		*/
		 
		//setResizable(false);
		setVisible(true);
		
		
		
		//Testing
		
		//update(tempTowers, tempEnemies);
	}
	
	//Call this once we get the mapBackgroundImageURL set
	public void createScaledBackgroundImage(){
		
		//Create correctly scaled image to use as background (map)
				System.out.println(mapBackgroundImageURL);
				ImageIcon mapTemp = createImageIcon(this.mapBackgroundImageURL);
				bg = (mapTemp.getImage()).getScaledInstance(getSize().width, (3*getSize().height)/4, Image.SCALE_SMOOTH);
				mapTemp.setImage(bg);
				JLabel labelTemp = new JLabel(mapTemp);
				labelTemp.setName("Background");
				labelTemp.setBounds(0, 0, getSize().width, (3*getSize().height)/4);
				board.add(labelTemp); //Added this line -PH
	}
	
	
	
	
	//Temporary methods
	/*
	void tempAttack()
	{
		tempAttackPath = new Path(1,1,5,5);
		Timer temp = new Timer(20, new tempAttackTimer());
		temp.start();
	}
	*/
	
	/*
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
	*/
	
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
			System.out.println("Tower at row " + tower.x + " column " + tower.y + " attacks!");
			JLabel proj = new JLabel(new ImageIcon(createImageIcon(pewterProjectile).getImage().getScaledInstance(this.getWidth()/(levelWidth*2), this.getHeight()/(levelHeight*2), Image.SCALE_FAST)));
			proj.setLocation(tower.x, tower.y); 
			//TODO:^Tower.x is the row of tower, tower.y is column, make sure to scale and keep
			//track of which one you really want as first and second parameter
						
			Bone newProjectile = new Bone();
			newProjectile.setLabel(proj);
			//Also set newProjectile's destination point here based on Point enemy
			//again make sure you know what is in point 
			projectiles.add(newProjectile);
			proj.setName("Projectile");
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
	
	//These 3 set methods are called by GameClient in its mapBackgroundUpdate method
	//which is called 1 time only when the map is created on the Server -PH
	
	/**
	 * This method is called by mapBackgroundUpdate by client when Map is created in model
	 * @param size the point (columnsInMap, rowsInMap)
	 */
	public void setGridSize(Point size)
	{
		levelWidth = size.x;
		levelHeight = size.y;
		this.updateTileSize();
	}
	
	public void setMapBackgroundImageURL(String url){
		this.mapBackgroundImageURL = url;
		this.createScaledBackgroundImage();
	}
	
	
	public void setEnemyPathCoords(List<Point> l){
		this.enemyPathCoords = l;
		this.createEnemyPathJLabels();
	}
	
	private void createEnemyPathJLabels() {
		JLabel tempTile;
		ImageIcon enemyTileTemp = new ImageIcon(createImageIcon(enemyPathTileImage).getImage().getScaledInstance(tileWidth, tileHeight, Image.SCALE_FAST));
		for(Point p: enemyPathCoords){
			tempTile = new JLabel(enemyTileTemp);
			tempTile.setSize(tileHeight, tileWidth);
			int x = p.y * tileWidth; //Reverses are due to how p is (rowsdown, columns) in model -PH
			int y = p.x * tileHeight;
			tempTile.setLocation(x, y);
			tempTile.setName("EnemyPathTile");
			pathTiles.add(tempTile);
			board.add(tempTile);
		}
	}

	//These 2 methods are called by GameClient in its updateHPandMoney method
	//whenever these values are changed in the model
	public void setPlayerHP(int hp) {
		this.playerHP = hp;
		playerHealthLabel.setValue(playerHP);
		System.out.println("Player now has HP: " + playerHP);
	}

	public void setPlayerMoney(int money) {
		this.playerMoney = money;
		playerMoneyLabel.setText("$" + playerMoney);
		System.out.println("Player now has $: " + playerMoney);
	}
	
	
	
	
	
	public void paintComponent(Graphics g)
	{
		super.paint(g);
		
		board.repaint();
		towerStorePanel.repaint();
		selectedTowerFromStore.repaint();
		//g.drawImage(pik,(int) ((path.getLocation(testSpriteProgress).x/20) * viewScale * board.getSize().width),(int) ((path.getLocation(testSpriteProgress).y/9) * viewScale * board.getSize().height) + 15, this);
		g.drawRect((int) (getSize().width)/15 + 10,(int) ((3*getSize().height)/3.8) + 30, (int) (getSize().width / 9.5), getSize().height / 8);
		g.drawRect((int) (2.6*getSize().width)/15 + 10,(int) ((3*getSize().height)/3.8) + 30, (int) (getSize().width / 9.5), getSize().height / 8);
	}
	
	
	//I'm pretty sure you can do this with ImageIcon's constructor, just pass it a String of url
	/**
	 * Creates an image icon based on the given URL
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
	
	class TimerListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			//testSpriteProgress += 0.2;
			//repaint();
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
			System.out.println("Resizing window");
			
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
			//Cursor cursor = kit.createCustomCursor(createImageIcon("/images/transPixel.png").getImage(), new Point(0,0), "Cubone");
			//setCursor(cursor);
			
			System.out.println("Tower selected");
			Rectangle tower1 = new Rectangle(getSize().width/15 + 10,(int) ((3*getSize().height)/3.8) + 30, (int) (getSize().width / 9.5), getSize().height / 8);
			if(tower1.contains(arg0.getPoint()))
			{
				System.out.println("Attempting to build first tower");
				selectedTowerFromStore.setIcon(tower1Image);
				selectedTowerFromStore.setBounds(arg0.getX()-(tileWidth/2), arg0.getY()-(tileHeight/2), tileWidth, tileHeight);
				//board.setVisible(false);
				//towerStorePanel.setVisible(false);
				//frame.add(selectedTowerFromStore);
				selectedTowerFromStore.setVisible(true);
				selectedTowerType = NORMAL;
				//Cursor cursor = kit.createCustomCursor(tower1Image.getImage(), new Point(10,10), "Cubone");
				//setCursor(cursor);
				repaint();
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
	
	/**
	 * Zooms in and out of the map based on direction of scrolling
	 */
	/*
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
		
		//tempProjectile.setIcon(new ImageIcon(createImageIcon("/images/spinningBone.gif").getImage().getScaledInstance((int) ((frame.getWidth()/40) * viewScale), (int) ((frame.getHeight()/26) * viewScale), Image.SCALE_FAST)));
		//tempProjectile.setSize(tileWidth /2, tileHeight*2);
		//tempCubone.setIcon(new ImageIcon(createImageIcon("/images/cuboneStatic.png").getImage().getScaledInstance((int) ((frame.getWidth()/levelWidth) * viewScale), (int) ((frame.getHeight()/levelHeight) * viewScale), qualitySetting)));
		//tempCubone.setLocation((int) (tempCubone.getLocation().x * viewScale),(int) (tempCubone.getLocation().y * viewScale));
		updateTileSize();
		//tempCubone.setSize(tileWidth, tileHeight);
		
		updateTileSize();
		tower1Image = new ImageIcon(createImageIcon("/images/cuboneStatic.png").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_SMOOTH));
		
		mapTemp.setImage(bg);
		JLabel labelTemp = new JLabel(mapTemp);
		labelTemp.setBounds(0, 0,(int) (getSize().width * viewScale),(int) (viewScale * (3*getSize().height)/4));
		
		
		board.add(labelTemp);
		
		board.setBounds(board.getX(), board.getY(), (int) (getSize().width * viewScale), (int) ((3*getSize().height)/4 * viewScale));
		updateTileSize();
		
		if(trueForShrink)
		{
			//tempCubone.setLocation(scrollLocation.x + (5*tileWidth), scrollLocation.y + (6*tileHeight));
		}
		else
		{
			//tempCubone.setLocation(scrollLocation.x + (5*(board.getWidth()/levelWidth)), scrollLocation.y + (6*(board.getHeight()/levelHeight)));
		}
		repaint();
	}
	*/

	/**
	 * Moves the map around according to where the mouse is being dragged
	 */
	public void mouseDragged(MouseEvent arg0)
	{
		if(clickedTowerStore)
		{
			mouseLoc = arg0.getPoint();
			selectedTowerFromStore.setBounds(mouseLoc.x - (tileWidth/2), mouseLoc.y - (tileHeight/2), tileWidth, tileHeight);
			repaint();
			//System.out.println("Dragging selected tower to (" + mouseLoc.x + ", " + mouseLoc.y + ").");
			return;
		}
		scrollLocation.x += arg0.getX() - scrollLast.x;
		scrollLocation.y += arg0.getY() - scrollLast.y;
		//tempCubone.setLocation(scrollLocation.x + (5*(board.getWidth()/levelWidth)), scrollLocation.y + (6*(board.getHeight()/levelHeight)));
		scrollLast = arg0.getPoint();
		board.setBounds(scrollLocation.x, scrollLocation.y,(int) (frame.getSize().width * viewScale),(int) ((3*frame.getSize().height)/4 * viewScale));
		//repaint();
		//towerStorePanel.repaint();
	}
	
	public void setLevelSize(int levelWidth, int levelHeight)
	{
		this.levelWidth = levelWidth;
		this.levelHeight = levelHeight;
	}
	
	//called every tick, passes new tower/enemy locations and images
	public void update(List<TowerImage> newTowers, List<EnemyImage> newEnemies)
	{
		//If there is a different amount of TowerImages sent then there
		//are already JLabels based on TowerImages on the board, remove
		//all these JLabels, create new ones, add them to a new towers list, and put them on board
		if(this.towers.size() != newTowers.size())
		{
			//for(JLabel tower: towers){
			//	board.remove(tower);
			//}
			((Board) board).removeTowers();
			towers = new ArrayList<JLabel>();
			
			JLabel tempTowerLabel;
			for(TowerImage ti: newTowers){
				tempTowerLabel = new JLabel(new ImageIcon(ti.getImageURL()));
				if(ti.getImageURL().endsWith("cuboneStatic.png"));
				{
					tempTowerLabel = new JLabel(tower1Image);
				}
				Point tiLocation = ti.getLocation();//this point contains the rowsdown in x and the columnsacross in y
				int y = tiLocation.x * tileHeight;
				int x = tiLocation.y * tileWidth;
				tempTowerLabel.setLocation(x, y);
				tempTowerLabel.setSize(tileWidth, tileHeight);
				tempTowerLabel.setName("Tower");
				towers.add(tempTowerLabel);
			}
			((Board) board).addTowers(towers);
			//for(JLabel j: towers){
			//	board.add(j);
			//}
			repaint();
			System.out.println("Updating board");
		}
		
		
		//Unconditionally update all enemies because their progress will have updated every tick
		((Board) board).removeEnemies();
		//for(JLabel enemy: enemies){
		//	board.remove(enemy);
		//}
		
		enemies = new ArrayList<JLabel>();
		
		JLabel tempEnemyLabel;
		for(EnemyImage ei: newEnemies){
			tempEnemyLabel = new JLabel(new ImageIcon(ei.getImageURL()));
			Point eiLocation = ei.getLocation();//this point contains the rowsdown in x and the columnsacross in y
			directionFacing orientation = ei.getOrientation();
			int progress = ei.getProgress();
			
			//get eiLocation and offset
			//it by progress fraction of tilewidth/height in direction of orientation
			//to put the image in the right place
			
			int x = eiLocation.y * tileWidth;
			
			if(orientation == directionFacing.WEST){
				x = x - ((tileWidth * progress) / 100);
			}
			if(orientation == directionFacing.EAST){
				x = x + ((tileWidth * progress) / 100);
			}
			
			int y = eiLocation.x * tileHeight;
			
			if(orientation == directionFacing.NORTH){
				y = y - ((tileHeight * progress) / 100);
			}
			if(orientation == directionFacing.SOUTH){
				y = y + ((tileHeight * progress) / 100);
			}
			
			tempEnemyLabel.setLocation(x, y);
			tempEnemyLabel.setSize(tileWidth, tileHeight); //Idk how big we want each enemy, use this for now
			enemies.add(tempEnemyLabel);
		}
		((Board) board).addEnemies(enemies);
		board.repaint();
		//for(JLabel j: enemies){
		//	board.add(j);
		//}
	}
	
	void updateTileSize()
	{
		tileWidth = (int) ((frame.getWidth()/levelWidth) * viewScale);
		tileHeight = (int) ((frame.getHeight()/levelHeight) * viewScale);
		tower1Image = new ImageIcon(createImageIcon("/images/cuboneStatic.png").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_SMOOTH));
		//ImageIcon tower1Icon = new ImageIcon(tower1Image.getImage());
		//JLabel tower1temp = new JLabel(tower1Icon);
		//tower1temp.setBounds((int) (getSize().width/15),(int) (towerStorePanel.getSize().height/6), (int) (getSize().width / 9.5), getSize().height / 8);
		
		//Temp stuff again
		/*
		tempProjectile.setIcon(new ImageIcon(createImageIcon("/images/spinningBone.gif").getImage().getScaledInstance((int) ((frame.getWidth()/40) * viewScale), (int) ((frame.getHeight()/26) * viewScale), Image.SCALE_FAST)));
		tempProjectile.setSize((int) ((frame.getWidth()/40) * viewScale), (int) ((frame.getHeight()/26) * viewScale));
		//Last of it
		*/
		//towerStorePanel.setBounds(0, (3*frame.getSize().height)/4, frame.getSize().width, frame.getSize().height/4);
		//towerStorePanel.removeAll();
		//towerStoreBG.setImage(towerStoreBGOrig.getScaledInstance(frame.getSize().width, frame.getSize().height/5, Image.SCALE_SMOOTH));
		//JLabel temp = new JLabel(towerStoreBG);
		//temp.setBounds(0, 0, frame.getSize().width, frame.getSize().height/5);
		//towerStorePanel.add(tower1temp);
		//towerStorePanel.add(temp);
		
		//selectedTowerFromStore.removeAll();
		//selectedTowerFromStore.setIcon(new ImageIcon(createImageIcon("/images/cuboneStatic.png").getImage().getScaledInstance(getSize().width/20, getSize().height/12, Image.SCALE_SMOOTH)));

		//board.removeAll();
		
		//for(JLabel label : towers)
		//{
		//	board.add(label);
		//}
		createScaledBackgroundImage();
		board.setBounds(board.getX(), board.getY(), (int) (getSize().width * viewScale), (int) ((3*getSize().height)/4 * viewScale));
		((Board) board).setTileSize(tileWidth, tileHeight);
		System.out.println("Tile Width: " + tileWidth + " \nTile Height:" + tileHeight);
		repaint();
	}

	
	//This enum has largely been replaced by the enum directionFacing in Enemy class based on it -PH
	enum direction{NORTH, EAST, SOUTH, WEST};
	
	/* A method based on this already was made in Enemy class, EnemyImage s are made with direction known
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
	}*/
	
	/* Do not use this method anymore -PH
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
	*/
	
	/* Unnecessary now -PH
	public void removeEnemy(Enemy enemy)
	{
		
	}*/
	
	/*Unnecessary now -PH
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
		//towerMap.put(tower, temp);
		board.add(temp);
	}
	*/
	
	
	public void removeTower(Point removeTowerAtCoordinates)
	{
		//TODO://Just send me the Point(rowsdown, columns) of where you want to try to sell a tower.
	//I would recommend you have a place/icon on GUI you can click to make your cursor
	//appear like a hammer and set a boolean to "sellTower = true" or something
	//and then on next click in mouselistener if sellTower == true then send the
	//calculated coordinates the mouse was at in this method. -PH
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
			
			//TODO:Pass me the Point (rowsdown, columnsacross) where player attempts to add tower -PH
			//This isn't sending the right Point to the model on the addTower method, I tried to fix it below
			
			Point loc = new Point((int) ((arg0.getY() - scrollLocation.y)/tileHeight),(int) ((arg0.getX() - scrollLocation.x)/tileWidth));
			//Point loc = new Point((int) ((arg0.getY() - scrollLocation.y)/tileHeight),(int) ((arg0.getX() - scrollLocation.x)/tileWidth));
			
			switch(selectedTowerType)
			{
			case NORMAL:
				client.addTower(towerType.NORMAL, loc);
				//towerLocation = new Point(arg0.getX(), arg0.getY());
				System.out.println("Attempting to place a normal tower at (row " + loc.y + ", column " + loc.x + ")");
				//client.addTower(new tempTower("Test Tower", 10, 3, 1, user, pewterTower, 0), new Point(((arg0.getX() - scrollLocation.x) * 20)/(bg.getWidth(this)), ((arg0.getY() - scrollLocation.y) * 20)/(bg.getWidth(this))));
				break;
			default:
				System.out.println("Attempting to place a tower");
			}
			//System.out.println(loc);
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
