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
import javax.swing.JTextArea;
import javax.swing.JTextPane;
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
	private JPanel towerInfo;
	private JTextPane towerInfoText;
	double viewScale = 1;
	boolean repaintGUI = true;
	boolean clickedTowerStore = false;
	boolean trueForShrink;
	private Timer animationTimer;
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	int tileWidth = 102;
	int tileHeight = 155; 
	ArrayList<Line> lines = new ArrayList<Line>();
	
	
	
	private String pewterProjectile = "/images/spinningBone.gif";
	
	private String pewterTower = "/images/cuboneStatic.png";
	
	private String enemyPathTileImage = "/images/enemyTile.png"; //Switch this later, only
																		//set it to this for convenience to not add another image
	
	private String enemy1ImageUp = "/images/pikachuUp.gif";
	private String enemy1ImageDn = "/images/pikachuDown.gif";
	private String enemy1ImageL = "/images/pikachuLeft.gif";
	private String enemy1ImageR = "/images/pikachuRight.gif";
	
	private String user;
	
	private Player player;
	
	towerType selectedTowerType;
	private int levelWidth;
	private int levelHeight;
	
	private String mapBackgroundImageURL;
	
	private List<Point> enemyPathCoords; //The list of points containing the coordinates
	private int playerHP; //The player's current HP, display in corner. it is updated
	private int playerMoney; //The player's current $, display in corner. it is updated
	//updated by model at every tick
	//private List<TowerImage> towersLast; //A list of all the TowerImages
	//private List<EnemyImage> enemiesLast; //A list of all the EnemyImages
	private List<TowerTile> towers = new ArrayList<TowerTile>(); //A list of all the JLabels on board based on sent TowerImages
	private List<JLabel> enemies = new ArrayList<JLabel>(); //A list of all the JLabels on board based on sent EnemyImages
	private List<JLabel> pathTiles = new ArrayList<JLabel>(); //A list of all the JLabels on board based on enemyPathCoords
	
	private ImageIcon tower1Image;
	private ImageIcon tower2Image;
	private ImageIcon tower3Image;
	private ImageIcon tower4Image;
	
	private ImageIcon enemy1ImageN = new ImageIcon();
	private ImageIcon enemy1ImageE = new ImageIcon();
	private ImageIcon enemy1ImageS = new ImageIcon();
	private ImageIcon enemy1ImageW = new ImageIcon();
	private ImageIcon enemy2Image;
	private ImageIcon enemy3Image;
	
	int qualitySetting;
	
	private GameClient client;
	
	/**
	 * Allows game to be started from GameView
	 * @param args Not used
	 */
	public static void main(String[] args)
	{
		new GameClient();
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
		setTitle("Pokemon Tower Defense");
		this.setIconImage(createImageIcon("/images/icon.png").getImage());
		addMouseListener(this);
		//addMouseWheelListener(this);  //Temporarily disabled to stop scaling
		//addComponentListener(new resizeListener());  //Temporarily disabled to stop scaling
		addMouseMotionListener(this);
		
		//animationTimer = new Timer(50, null);
		//towersLast = new ArrayList<TowerImage>();
		
		//Change the cursor to pokeball
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
		
		//Sets the draggable tower image and hides it
		selectedTowerFromStore = new JLabel(new ImageIcon(createImageIcon("/images/tower1Level1.png").getImage().getScaledInstance(getSize().width/20, getSize().height/12, Image.SCALE_SMOOTH)));
		selectedTowerFromStore.setBounds(0,0,tileWidth, tileHeight);
		selectedTowerFromStore.setVisible(false);
		this.add(selectedTowerFromStore);
		
		towerInfo = new JPanel();
		towerInfo.setBounds(0,0,getSize().width/4, getSize().height/4);
		towerInfo.setLayout(null);
		towerInfoText = new JTextPane();
		towerInfoText.setText("Text about tower\nStats will be updated\nOr constant, either way.");
		towerInfoText.setEditable(false);
		towerInfoText.setOpaque(false);
		towerInfoText.setForeground(Color.WHITE);
		towerInfoText.setBounds(10, 10, towerInfo.getWidth()-20, towerInfo.getHeight()-20);
		towerInfo.add(towerInfoText);
		JLabel tempBG = new JLabel(new ImageIcon(createImageIcon("/images/towerInfoPanel.png").getImage().getScaledInstance(towerInfo.getWidth(), towerInfo.getHeight()-10, Image.SCALE_SMOOTH)));
		tempBG.setBounds(0, 0, towerInfo.getWidth(), towerInfo.getHeight());
		towerInfo.add(tempBG);
		towerInfo.setOpaque(false);
		towerInfo.setVisible(false);
		this.add(towerInfo);
	
		playerMoneyPanel = new JPanel();
		playerMoneyPanel.setBounds(0,0, 100, 25);
		playerMoneyPanel.setLayout(null);
		playerMoneyLabel = new JLabel("$0");
		playerMoneyLabel.setBounds(0,0,100,25);
		playerMoneyLabel.setHorizontalAlignment(JLabel.CENTER);
		playerMoneyLabel.setForeground(Color.WHITE);
		playerMoneyPanel.add(playerMoneyLabel);
		tempBG = new JLabel(new ImageIcon(createImageIcon("/images/towerInfoPanel.png").getImage().getScaledInstance(playerMoneyPanel.getWidth(), playerMoneyPanel.getHeight(), Image.SCALE_SMOOTH)));
		tempBG.setBounds(0, 0, playerMoneyPanel.getWidth(), playerMoneyPanel.getHeight());
		playerMoneyPanel.add(tempBG);
		playerMoneyPanel.setOpaque(false);
		this.add(playerMoneyPanel);
		
		playerHealthPanel = new JPanel();
		playerHealthPanel.setBounds(getWidth()-200, 0, 200, 25);
		playerHealthPanel.setOpaque(false);
		playerHealthLabel = new JProgressBar();
		playerHealthLabel.setValue(100);
		playerHealthLabel.setBackground(Color.RED);
		playerHealthLabel.setForeground(Color.GREEN);
		playerHealthLabel.setStringPainted(false);
		playerHealthLabel.setMaximum(100);
		playerHealthLabel.setMinimum(0);
		Font tempFont = new Font("Comic Sans MS", Font.PLAIN, 10);
		playerHealthLabel.setFont(tempFont);
		playerHealthPanel.add(playerHealthLabel);
		this.add(playerHealthPanel);
		
		//Create and size the towerStore background image
		towerStoreBG = createImageIcon("/images/towerStore.png");
		towerStoreBGOrig = towerStoreBG.getImage();
		towerStoreBG.setImage(towerStoreBGOrig.getScaledInstance(getSize().width, getSize().height/4 - 20, Image.SCALE_SMOOTH));
		JLabel temp = new JLabel(towerStoreBG);
		temp.setBounds(0, 0, getSize().width, getSize().height/4);
		towerStorePanel = new JPanel();
		towerStorePanel.setLayout(null);
		towerStorePanel.setBounds(0, (int) (3*(getSize().height)/4) - 10, getSize().width, getSize().height/4 - 20 );
		towerStorePanel.setOpaque(false);
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
		
		frame = this;

		setVisible(true);
	}
	
	//Call this once we get the mapBackgroundImageURL set
	public void createScaledBackgroundImage()
	{	
		//Create correctly scaled image to use as background (map)
				System.out.println(mapBackgroundImageURL);
				ImageIcon mapTemp = createImageIcon(this.mapBackgroundImageURL);
				bg = (mapTemp.getImage()).getScaledInstance(getSize().width, (3*getSize().height)/4, Image.SCALE_SMOOTH);
				mapTemp.setImage(bg);
				JLabel labelTemp = new JLabel(mapTemp);
				labelTemp.setName("Background");
				labelTemp.setBounds(0, 0, getSize().width, (3*getSize().height)/4);
				((Board) board).addBackground(labelTemp); //Added this line -PH
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
			//System.out.println("Tower at row " + tower.x + " column " + tower.y + " attacks!");
			//JLabel proj = new JLabel(new ImageIcon(createImageIcon(pewterProjectile).getImage().getScaledInstance(this.getWidth()/(levelWidth*2), this.getHeight()/(levelHeight*2), Image.SCALE_FAST)));
			Line line = new Line(new Point(tower.y * tileWidth + (tileWidth/2), tower.x * tileHeight + (tileHeight/2)), new Point(enemy.y * tileWidth + (tileWidth/2), enemy.x * tileHeight + (tileHeight/2)));
			//TODO:^Tower.x is the row of tower, tower.y is column, make sure to scale and keep
			//track of which one you really want as first and second parameter
						
			//Bone newProjectile = new Bone();
			//newProjectile.setLabel(proj);
			//Also set newProjectile's destination point here based on Point enemy
			//again make sure you know what is in point 
			//projectiles.add(newProjectile);
			//proj.setName("Projectile");
			lines.add(line);
			((Board) board).add(line);
		}
		return true;
	}
	
	class attackAnimation implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			int ticksToWait = 2;
			for(int i = 0; i < lines.size(); i++)
			{
				if(lines.get(0).getIterations() > ticksToWait)
				{
					System.out.println("Removing a line. " + lines.size() + " lines left.");
					lines.remove(0);
					((Board) board).removeLine();
				}
				else
				{
					for(int j = i; i < lines.size(); i++)
					{
						//lines.get(j).iterate();
						lines.add(j, lines.get(j).iterate());
					}
				}
			}
			
			
			
			/*
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
			*/
			
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
		((Board) board).setTileSize(tileWidth, tileHeight);
		System.out.println("Updated grid size");
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
			tempTile.setSize(tileWidth, tileHeight);
			int x = p.y * tileWidth; //Reverses are due to how p is (rowsdown, columns) in model -PH
			int y = p.x * tileHeight;
			tempTile.setLocation(x, y);
			tempTile.setName("EnemyPathTile");
			//pathTiles.add(tempTile);
			//board.add(tempTile);
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
	
	/**
	 * Simple enum for game type
	 * @author Desone
	 *
	 */
	enum gameType{SINGLE, MULTI}
	
	/**
	 * Simple enum for the tower type
	 * @author Desone
	 *
	 */
	public enum towerType{NORMAL,WATER,ELECTRIC,GRASS,POISON,PSYCHIC,FIRE,MEWTWO}
	
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
			towers = new ArrayList<TowerTile>();
			
			TowerTile tempTowerLabel;
			for(TowerImage ti: newTowers){
				tempTowerLabel = new TowerTile();
				if(ti.getImageURL().endsWith("cuboneStatic.png"));
				{
					tempTowerLabel = new TowerTile();
					tempTowerLabel.setIcon(tower1Image);
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
			repaint();
			System.out.println("Updating board towers");
		}
		
		enemies = new ArrayList<JLabel>();
		
		EnemyTile tempEnemyLabel;
		for(EnemyImage ei: newEnemies){
			//tempEnemyLabel = new JLabel(new ImageIcon(ei.getImageURL()));
			tempEnemyLabel = new EnemyTile();
			Point eiLocation = ei.getLocation();//this point contains the rowsdown in x and the columnsacross in y
			directionFacing orientation = ei.getOrientation();
			int progress = ei.getProgress();
			//System.out.println(orientation);
			//get eiLocation and offset
			//it by progress fraction of tilewidth/height in direction of orientation
			//to put the image in the right place
			
			int x = eiLocation.y * tileWidth;
			
			if(orientation == directionFacing.WEST){
				x = x - ((tileWidth * progress) / 100);
				tempEnemyLabel.setIcon(enemy1ImageW);
			}
			if(orientation == directionFacing.EAST){
				x = x + ((tileWidth * progress) / 100);
				tempEnemyLabel.setIcon(enemy1ImageE);
			}
			
			int y = eiLocation.x * tileHeight;
			
			if(orientation == directionFacing.NORTH){
				y = y - ((tileHeight * progress) / 100);
				tempEnemyLabel.setIcon(enemy1ImageN);
			}
			if(orientation == directionFacing.SOUTH){
				y = y + ((tileHeight * progress) / 100);
				tempEnemyLabel.setIcon(enemy1ImageS);
			}
			tempEnemyLabel.setHealth(ei.getHealthPercentage());
			tempEnemyLabel.setLocation(x, y);
			tempEnemyLabel.setSize(tileWidth, tileHeight); //Idk how big we want each enemy, use this for now
			
			enemies.add(tempEnemyLabel);
		}
		//System.out.println("Progress of furthest enemy: " + newEnemies.get(0).getProgress());
		((Board) board).addEnemies(enemies);
	}
	
	void updateTileSize()
	{
		tileWidth = (int) ((board.getWidth()/levelWidth));
		System.out.println("Level height " + levelHeight);
		tileHeight = (int) ((board.getHeight()/levelHeight));
		tower1Image = new ImageIcon(createImageIcon("/images/tower1Level1.png").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_SMOOTH));
		enemy1ImageN = new ImageIcon(createImageIcon("/images/enemy1Up.gif").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_DEFAULT));
		enemy1ImageE = new ImageIcon(createImageIcon("/images/enemy1Right.gif").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_DEFAULT));
		enemy1ImageS = new ImageIcon(createImageIcon("/images/enemy1Down.gif").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_DEFAULT));
		enemy1ImageW = new ImageIcon(createImageIcon("/images/enemy1Left.gif").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_DEFAULT));
		createScaledBackgroundImage();
		board.setBounds(board.getX(), board.getY(), (int) (getSize().width * viewScale), (int) ((3*getSize().height)/4 * viewScale));
		((Board) board).setTileSize(tileWidth, tileHeight);
		System.out.println("Tile Width: " + tileWidth + " \nTile Height:" + tileHeight);
	}
	
	public void removeTower(Point removeTowerAtCoordinates)
	{
		//TODO://Just send me the Point(rowsdown, columns) of where you want to try to sell a tower.
		//I would recommend you have a place/icon on GUI you can click to make your cursor
		//appear like a hammer and set a boolean to "sellTower = true" or something
		//and then on next click in mouselistener if sellTower == true then send the
		//calculated coordinates the mouse was at in this method. -PH
	}
	

	
	/**
	 * serves to initialize where the mouse starts from in mouseDragged
	 */
	public void mousePressed(MouseEvent arg0)
	{
		if(arg0.getY() >= (3*getSize().height)/4)
		{
			clickedTowerStore = true;
			System.out.println("Tower selected");
			Rectangle tower1 = new Rectangle(getSize().width/15 + 10,(int) ((3*getSize().height)/3.8) + 30, (int) (getSize().width / 9.5), getSize().height / 8);
			if(tower1.contains(arg0.getPoint()))
			{
				System.out.println("Attempting to build tower1Level1");
				selectedTowerFromStore.setIcon(tower1Image);
				selectedTowerFromStore.setBounds(arg0.getX()-(tileWidth/2), arg0.getY()-(tileHeight/2), tileWidth, tileHeight);
				selectedTowerFromStore.setVisible(true);
				selectedTowerType = towerType.NORMAL;
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
	 * Zooms in and out of the map based on direction of scrolling.
	 * Currently not used.
	 */
	public void mouseWheelMoved(MouseWheelEvent arg0)
	{
		/*
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
		*/
	}

	/**
	 * Moves board or tower depending on if a tower is selected
	 */
	public void mouseDragged(MouseEvent arg0)
	{
		if(clickedTowerStore)
		{
			mouseLoc = arg0.getPoint();
			selectedTowerFromStore.setBounds(mouseLoc.x - (tileWidth/2), mouseLoc.y - (tileHeight/2), tileWidth, tileHeight);
			return;
		}
		scrollLocation.x += arg0.getX() - scrollLast.x;
		scrollLocation.y += arg0.getY() - scrollLast.y;
		scrollLast = arg0.getPoint();
		board.setLocation(scrollLocation.x, scrollLocation.y);
	}
	
	public void mouseMoved(MouseEvent arg0)
	{
		mouseLoc = arg0.getPoint();
		towerInfo.setLocation(mouseLoc.x + 10, mouseLoc.y - towerInfo.getHeight() - 10);
		Rectangle tower1 = new Rectangle(getSize().width/15 + 10,(int) ((3*getSize().height)/3.8) + 30, (int) (getSize().width / 9.5), getSize().height / 8);
		Rectangle tower2 = new Rectangle((int)(2.6*getSize().width)/15 + 10,(int) ((3*getSize().height)/3.8) + 30, (int) (getSize().width / 9.5), getSize().height / 8);
		Rectangle tower3 = new Rectangle((int)(4.25*getSize().width)/15 + 10,(int) ((3*getSize().height)/3.8) + 30, (int) (getSize().width / 9.5), getSize().height / 8);
		Rectangle tower4 = new Rectangle((int)(5.85*getSize().width)/15 + 10,(int) ((3*getSize().height)/3.8) + 30, (int) (getSize().width / 9.5), getSize().height / 8);
		Rectangle tower5 = new Rectangle((int)(7.45*getSize().width)/15 + 10,(int) ((3*getSize().height)/3.8) + 30, (int) (getSize().width / 9.5), getSize().height / 8);
		Rectangle tower6 = new Rectangle((int)(9.05*getSize().width)/15 + 10,(int) ((3*getSize().height)/3.8) + 30, (int) (getSize().width / 9.5), getSize().height / 8);
		Rectangle tower7 = new Rectangle((int)(10.67*getSize().width)/15 + 10,(int) ((3*getSize().height)/3.8) + 30, (int) (getSize().width / 9.5), getSize().height / 8);
		Rectangle tower8 = new Rectangle((int)(12.3*getSize().width)/15 + 10,(int) ((3*getSize().height)/3.8) + 30, (int) (getSize().width / 9.5), getSize().height / 8);
		
		if(tower1.contains(arg0.getPoint()))
		{
			towerInfo.setVisible(true);
			towerInfoText.setText("Cubone Tower info");
		}
		else if(tower2.contains(arg0.getPoint()))
		{
			towerInfo.setVisible(true);
			towerInfoText.setText("Poliwhirl Tower info");
		}
		else if(tower3.contains(arg0.getPoint()))
		{
			towerInfo.setVisible(true);
			towerInfoText.setText("Magenemite Tower info");
		}
		else if(tower4.contains(arg0.getPoint()))
		{
			towerInfo.setVisible(true);
			towerInfoText.setText("Oddish Tower info");
		}
		else if(tower5.contains(arg0.getPoint()))
		{
			towerInfo.setVisible(true);
			towerInfoText.setText("Ghastly Tower info");
		}
		else if(tower6.contains(arg0.getPoint()))
		{
			towerInfo.setVisible(true);
			towerInfoText.setText("Abra Tower info");
		}
		else if(tower7.contains(arg0.getPoint()))
		{
			towerInfo.setLocation(mouseLoc.x - towerInfo.getWidth(), mouseLoc.y - towerInfo.getHeight() - 10);
			towerInfo.setVisible(true);
			towerInfoText.setText("Charmander Tower info");
		}

		else if(tower8.contains(arg0.getPoint()))
		{
			towerInfo.setLocation(mouseLoc.x - towerInfo.getWidth(), mouseLoc.y - towerInfo.getHeight() - 10);
			towerInfo.setVisible(true);
			towerInfoText.setText("Mewtwo Tower info");
		}
		else
		{
			towerInfoText.setText("No Tower selected");
			towerInfo.setVisible(false);
		}
	}
	
	
	public void mouseReleased(MouseEvent arg0)
	{
		if(clickedTowerStore)
		{
			clickedTowerStore = false;
			selectedTowerFromStore.setVisible(false);
			Point loc = new Point((int) ((arg0.getY() - scrollLocation.y)/tileHeight),(int) ((arg0.getX() - scrollLocation.x)/tileWidth));
			client.addTower(selectedTowerType, loc);
		}
	}
	public void windowClosing(WindowEvent arg0)
	{
		client.disconnect();
	}
	
	/**
	 * Listens for the window resizing, and scales elements as needed, currently not used
	 * @author Desone
	 *
	 */
	class resizeListener implements ComponentListener
	{
		public void componentResized(ComponentEvent arg0)
		{
			
		}
		public void componentShown(ComponentEvent arg0){}
		public void componentHidden(ComponentEvent arg0){}
		public void componentMoved(ComponentEvent arg0){}
	}
	
	public void mouseClicked(MouseEvent arg0){}
	public void mouseEntered(MouseEvent arg0){}
	public void mouseExited(MouseEvent arg0){}
	public void windowActivated(WindowEvent arg0){}
	public void windowClosed(WindowEvent arg0){}
	public void windowDeactivated(WindowEvent arg0){}
	public void windowDeiconified(WindowEvent arg0){}
	public void windowIconified(WindowEvent arg0){}
	public void windowOpened(WindowEvent arg0){}

	
}
