package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class GameView extends JFrame implements MouseListener, MouseWheelListener, MouseMotionListener
{
	JFrame frame;
	Image bg;
	Image origBG;
	ImageIcon towerStoreBG;
	Image towerStoreBGOrig;
	JPanel board;
	JPanel towerStorePanel;
	Point scrollLocation;
	Point scrollLast;
	double viewScale = 1;
	boolean repaintGUI = true;
	
	Image pik;
	
	public static void main(String[] args)
	{
		new GameView(gameType.SINGLE, "Billy");
	}
	
	public GameView(gameType type, String user)
	{
		//Setup for the JFrame, sets size, closeOperation, adds listeners, and so on
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(screen.width/8, screen.height/8, (3*screen.width)/4, (3*screen.height)/4);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setTitle("Pokemon Tower Defense - " + user);
		addMouseListener(this);
		addMouseWheelListener(this);
		addComponentListener(new resizeListener());
		addMouseMotionListener(this);
		
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
		
		//Create and size the towerStore background image
		towerStoreBG = createImageIcon("/images/towerStore.png");
		towerStoreBGOrig = towerStoreBG.getImage();
		towerStoreBG.setImage(towerStoreBGOrig.getScaledInstance(getSize().width, getSize().height/4, Image.SCALE_SMOOTH));
		JLabel temp = new JLabel(towerStoreBG);
		temp.setBounds(0, 0, getSize().width, getSize().height/4);
		towerStorePanel = new JPanel();
		towerStorePanel.setBounds(0, (int) ((2*getSize().height)/4), getSize().width, getSize().height/4 );
		towerStorePanel.add(temp);
		
		//Create the panel for the game "board"
		board = new JPanel();
		board.setBounds(0,0,getSize().width, (3*getSize().height)/4);
		board.setBackground(Color.WHITE);
		
		add(towerStorePanel);
		frame = this;
		repaint();
		setVisible(true);
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		board.repaint();
		System.out.println("Repainting");
		repaintGUI = false;
		g.drawImage(bg, scrollLocation.x, scrollLocation.y + 30, this);
		towerStorePanel.repaint();
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
	
	/**
	 * Simple enum for game type
	 * @author Desone
	 *
	 */
	enum gameType{SINGLE, MULTI}
	
	/**
	 * Listens for the window resizing, and scales elements as needed
	 * @author Desone
	 *
	 */
	class resizeListener implements ComponentListener
	{
		public void componentResized(ComponentEvent arg0)
		{
			towerStorePanel.setBounds(0, (3*frame.getSize().height)/4, frame.getSize().width, frame.getSize().height/4);
			towerStorePanel.removeAll();
			towerStoreBG.setImage(towerStoreBGOrig.getScaledInstance(frame.getSize().width, frame.getSize().height/5, Image.SCALE_SMOOTH));
			JLabel temp = new JLabel(towerStoreBG);
			temp.setBounds(scrollLocation.x, scrollLocation.y, frame.getSize().width, frame.getSize().height/5);
			towerStorePanel.add(temp);
			board.setBounds(scrollLocation.x, scrollLocation.y,(int) (frame.getSize().width * viewScale), (int) ((3*frame.getSize().height)/4 * viewScale));
			ImageIcon mapTemp = createImageIcon("/images/map1.png");
			bg = (mapTemp.getImage()).getScaledInstance(getSize().width, (3*getSize().height)/4, Image.SCALE_SMOOTH);
			//repaintGUI = true;
			repaint();
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
		scrollLast = arg0.getPoint();
	}
	
	/**
	 * Zooms in and out of the map based on direction of scrolling
	 */
	public void mouseWheelMoved(MouseWheelEvent arg0)
	{
		switch(arg0.getWheelRotation())
		{
		case 1:
			viewScale *= 0.8;
			//scrollLocation.x = (int) (scrollLocation.x + ((arg0.getX() - getWidth()/2)));
		case -1:
			viewScale *= 1.1;
			//scrollLocation.x = (int) (scrollLocation.x + ((getWidth()/2 - arg0.getX())));
		default:
			;
		}
		
		ImageIcon mapTemp = createImageIcon("/images/map1.png");
		bg = (mapTemp.getImage()).getScaledInstance((int) (getSize().width * viewScale), (int) ((3*getSize().height)/4 * viewScale), Image.SCALE_SMOOTH);
		repaint();
	}

	/**
	 * Moves the map around according to where the mouse is being dragged
	 */
	public void mouseDragged(MouseEvent arg0)
	{
		scrollLocation.x += arg0.getX() - scrollLast.x;
		scrollLocation.y += arg0.getY() - scrollLast.y;
		scrollLast = arg0.getPoint();
		board.setBounds(scrollLocation.x, scrollLocation.y, frame.getSize().width, (3*frame.getSize().height)/4);
		repaint();
	}
	
	public void mouseMoved(MouseEvent arg0){}
	public void mouseEntered(MouseEvent arg0){}
	public void mouseExited(MouseEvent arg0){}
	public void mouseReleased(MouseEvent arg0){}
	
	public void windowActivated(WindowEvent arg0){}
	public void windowClosed(WindowEvent arg0){}
	public void windowClosing(WindowEvent arg0){}
	public void windowDeactivated(WindowEvent arg0){}
	public void windowDeiconified(WindowEvent arg0){}
	public void windowIconified(WindowEvent arg0){}
	public void windowOpened(WindowEvent arg0){}
}
