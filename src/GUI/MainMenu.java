package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.text.IconView;

import server.GameServer;
import client.GameClient;
import client.Player;

public class MainMenu extends JFrame implements WindowListener
{
	JFrame frame;
	double xScale;
	double yScale;
	JLabel bg;
	Image orig;
	JButton singlePlayer;
	JButton multiPlayer;
	JButton settings;
	GameView view;
	GameClient client;
	String username;
	Player player;
	String map;
	
	public static void main(String[] args)
	{
		//new GameServer();
		new GameClient(); 
	}
	
	public MainMenu(GameClient client)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e)
		{
			System.out.println("Unable to set operating system look and feel");
		}
		
		//this.username = JOptionPane.showInputDialog("Enter username");
		//if(username == null || username.equals(""))
		//{
			//Handle empty username
		//}
		//String[] levels = {"Map0"};
		//map = JOptionPane.showOptionDialog(this, "Please select a map", JOptionPane.PLAIN_MESSAGE, new ImageIcon(), JOptionPane.OK_OPTION, levels);
		
		this.client = client;
		setTitle("Pokemon Tower Defense");
		//setDefaultCloseOperation(client.disconnect());
		this.setLayout(null);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		
		
		//line below sets frame to be half of width and height of screen, and puts it in the middle
		setBounds((int) (screen.getWidth()/4), (int) (screen.getHeight()/4), (int) (screen.getWidth()/2), (int) (screen.getHeight()/2));
		addComponentListener(new resizeListener());
		ImageIcon bgIcon = createImageIcon("/images/mainMenuBackground.png");
		orig = bgIcon.getImage();
		///bg = new JLabel(bgIcon.getImage().getSca);
		//orig = bg.g;
		//new ImageIcon(orig.getScaledInstance(frame.getSize().width, frame.getSize().height-30, Image.SCALE_FAST))
		bg = new JLabel(new ImageIcon(createImageIcon("/images/mainMenuBackground.png").getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_FAST)));
		bg.setBounds(0, 0, this.getWidth(), this.getHeight());
		
		//Creating buttons and sizing them, and adding listeners
		singlePlayer = new JButton("Single Player");
		singlePlayer.setBounds(this.getWidth()/4, this.getHeight()/5, this.getWidth()/2, this.getHeight()/10);
		singlePlayer.addActionListener(new buttonListener());
		multiPlayer = new JButton("Online");
		multiPlayer.setBounds(this.getWidth()/4, (this.getHeight()*2)/5, this.getWidth()/2, this.getHeight()/10);
		multiPlayer.addActionListener(new buttonListener());
		settings = new JButton("Settings");
		settings.setBounds(this.getWidth()/4, (this.getHeight()*3)/5, this.getWidth()/2, this.getHeight()/10);
		settings.addActionListener(new buttonListener());
		/*
		//Set title
		title = new JLabel("Pokemon Tower Defense");
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Serif", Font.PLAIN, 24));
		title.setOpaque(false);
		title.setBounds(this.getWidth() / 2 - (title.getText().length() * 6), this.getHeight() / 30, this.getWidth(), 50);
		
		this.add(title);
		*/
		
		this.add(singlePlayer);
		this.add(multiPlayer);
		this.add(settings);
		this.add(bg);
		
		frame = this;
		
		setVisible(true);
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
	 * Listens to the GUI and resizes the buttons
	 * and image while the frame is being resized
	 * @author Desone
	 *
	 */
	class resizeListener implements ComponentListener
	{
		public void componentResized(ComponentEvent arg0)
		{
			//bg = new JLabel(new ImageIcon(createImageIcon("/images/mainMenuBackground.png").getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_FAST)));
			bg.setBounds(0, 0, frame.getWidth(), frame.getHeight());
			singlePlayer.setBounds(frame.getWidth()/4, frame.getHeight()/5, frame.getWidth()/2, frame.getHeight()/10);
			multiPlayer.setBounds(frame.getWidth()/4, (frame.getHeight()*2)/5, frame.getWidth()/2, frame.getHeight()/10);
			settings.setBounds(frame.getWidth()/4, (frame.getHeight()*3)/5, frame.getWidth()/2, frame.getHeight()/10);
			repaint();
		}
		public void componentShown(ComponentEvent arg0){}
		public void componentHidden(ComponentEvent arg0){}
		public void componentMoved(ComponentEvent arg0){}
	}
	
	class buttonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			if(arg0.getSource().equals(singlePlayer))
			{
				frame.setVisible(false);
				view = new GameView(GameView.gameType.SINGLE, username, client, player);
				client.createLevel(0); //Call AFTER view is created
			}
			if(arg0.getSource().equals(multiPlayer))
			{
				frame.setVisible(false);
			
				view = new GameView(GameView.gameType.MULTI, username, client, player);
			}
			if(arg0.getSource().equals(settings))
			{
				JOptionPane.showMessageDialog(frame, "Settings menu not yet available", "Pokemon Tower Defense", JOptionPane.OK_OPTION);
			}
		}
	}
	
	public GameView getView()
	{
		return view;
	}
	
	public void setPlayer(Player player)
	{
		this.player = player;
	}
	
	public void windowActivated(WindowEvent arg0){}
	public void windowClosed(WindowEvent arg0){}
	public void windowClosing(WindowEvent arg0){}
	public void windowDeactivated(WindowEvent arg0){}
	public void windowDeiconified(WindowEvent arg0){}
	public void windowIconified(WindowEvent arg0){}
	public void windowOpened(WindowEvent arg0){}
	
	public void paintComponent(Graphics g)
	{
		super.paint(g);
		//g.drawImage(bg, 0, 30, this);
		multiPlayer.repaint();
		settings.repaint();
		singlePlayer.repaint();
		/*
		title.setOpaque(true);
		title.setBackground(null);
		title.repaint();
		*/
	}

}
