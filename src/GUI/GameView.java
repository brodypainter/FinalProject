package GUI;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class GameView extends JFrame implements MouseListener, MouseWheelListener
{
	JFrame frame;
	Image bg;
	Image origBG;
	ImageIcon towerStoreBG;
	Image towerStoreBGOrig;
	JPanel board;
	JPanel towerStorePanel;
	double viewScale = 1;
	boolean repaintGUI = true;
	
	Image pik;
	
	public static void main(String[] args)
	{
		new GameView();
	}
	
	public GameView()
	{
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(screen.width/8, screen.height/8, (3*screen.width)/4, (3*screen.height)/4);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		addMouseListener(this);
		addMouseWheelListener(this);
		addComponentListener(new resizeListener());
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e)
		{
			System.out.println("Unable to set operating system look and feel");
		}
		
		ImageIcon mapTemp = createImageIcon("/images/map1.png");
		bg = (mapTemp.getImage()).getScaledInstance(getSize().width, (3*getSize().height)/4, Image.SCALE_SMOOTH);
		
		
		towerStoreBG = createImageIcon("/images/towerStore.png");
		//towerStoreBG = tempTowerStoreBG.getImage();
		towerStoreBGOrig = towerStoreBG.getImage();
		towerStoreBG.setImage(towerStoreBGOrig.getScaledInstance(getSize().width, getSize().height/4, Image.SCALE_SMOOTH));
		JLabel temp = new JLabel(towerStoreBG);
		temp.setBounds(0, 0, getSize().width, getSize().height/4);
		
		towerStorePanel = new JPanel();
		towerStorePanel.setBounds(0, (int) ((2*getSize().height)/4), getSize().width, getSize().height/4 );
		towerStorePanel.add(temp);
		
		board = new JPanel();
		board.setBounds(0,0,getSize().width, (3*getSize().height)/4);
		board.setBackground(Color.WHITE);
		
		add(towerStorePanel);
		//add(board);
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
		g.drawImage(bg, 0, 30, this);
		//g.drawImage(towerStoreBG, 0, (3*frame.getSize().height)/4, this);
		
	}

	
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
	
	class resizeListener implements ComponentListener
	{
		public void componentResized(ComponentEvent arg0)
		{
			towerStorePanel.setBounds(0, (3*frame.getSize().height)/4, frame.getSize().width, frame.getSize().height/4);
			towerStorePanel.removeAll();
			towerStoreBG.setImage(towerStoreBGOrig.getScaledInstance(frame.getSize().width, frame.getSize().height/5, Image.SCALE_SMOOTH));
			JLabel temp = new JLabel(towerStoreBG);
			temp.setBounds(0, 0, frame.getSize().width, frame.getSize().height/5);
			towerStorePanel.add(temp);
			board.setBounds(0, 0, frame.getSize().width, (3*frame.getSize().height)/4);
			ImageIcon mapTemp = createImageIcon("/images/map1.png");
			bg = (mapTemp.getImage()).getScaledInstance(getSize().width, (3*getSize().height)/4, Image.SCALE_SMOOTH);
			//repaintGUI = true;
			repaint();
		}
		public void componentShown(ComponentEvent arg0){}
		public void componentHidden(ComponentEvent arg0){}
		public void componentMoved(ComponentEvent arg0){}
	}
	
	public void windowActivated(WindowEvent arg0){}
	public void windowClosed(WindowEvent arg0){}
	public void windowClosing(WindowEvent arg0){}
	public void windowDeactivated(WindowEvent arg0){}
	public void windowDeiconified(WindowEvent arg0){}
	public void windowIconified(WindowEvent arg0){}
	public void windowOpened(WindowEvent arg0){}
	
	public void mouseClicked(MouseEvent arg0)
	{
		System.out.println("Got some clickin");
	}
	public void mouseEntered(MouseEvent arg0){}
	public void mouseExited(MouseEvent arg0){}
	public void mousePressed(MouseEvent arg0){}
	public void mouseReleased(MouseEvent arg0){}
	public void mouseWheelMoved(MouseWheelEvent arg0)
	{
		System.out.println("Got some scrollin " + arg0.getWheelRotation());
		switch(arg0.getWheelRotation())
		{
		case 1:
			viewScale *= 0.75;
		case -1:
			viewScale *= 1.25;
		default:
			;
		}
		System.out.println(viewScale + "");
		//bg = pik.getScaledInstance((int) (((100) * viewScale)/1), 100, Image.SCALE_FAST);
	}
}
