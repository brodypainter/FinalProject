package GUI;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

import GameController.Enemy.directionFacing;

public class EnemyTileData
{
	private int tileWidth;
	private int tileHeight;
	private ImageIcon enemy1Down;
	private ImageIcon enemy1Up;
	private ImageIcon enemy1Left;
	private ImageIcon enemy1Right;
	private ImageIcon enemy2Down;
	private ImageIcon enemy2Up;
	private ImageIcon enemy2Left;
	private ImageIcon enemy2Right;
	private ImageIcon enemy3Down;
	private ImageIcon enemy3Up;
	private ImageIcon enemy3Left;
	private ImageIcon enemy3Right;
	private ImageIcon enemy4Down;
	private ImageIcon enemy4Up;
	private ImageIcon enemy4Left;
	private ImageIcon enemy4Right;
	private ImageIcon enemy5Down;
	private ImageIcon enemy5Up;
	private ImageIcon enemy5Left;
	private ImageIcon enemy5Right;
	private ImageIcon enemy6Down;
	private ImageIcon enemy6Up;
	private ImageIcon enemy6Left;
	private ImageIcon enemy6Right;
	private ImageIcon enemy7Down;
	private ImageIcon enemy7Up;
	private ImageIcon enemy7Left;
	private ImageIcon enemy7Right;
	private ImageIcon enemy8;
	
	
	public EnemyTileData(int width, int height)
	{
		tileWidth = width;
		tileHeight = height;
		enemy1Down = new ImageIcon(createImageIcon("/images/enemy1Down.gif").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_DEFAULT));
		enemy1Up = new ImageIcon(createImageIcon("/images/enemy1Up.gif").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_DEFAULT));
		enemy1Left = new ImageIcon(createImageIcon("/images/enemy1Left.gif").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_DEFAULT));
		enemy1Right = new ImageIcon(createImageIcon("/images/enemy1Right.gif").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_DEFAULT));
		enemy2Down = new ImageIcon(createImageIcon("/images/enemy2Down.gif").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_DEFAULT));
		enemy2Up = new ImageIcon(createImageIcon("/images/enemy2Up.gif").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_DEFAULT));
		enemy2Left = new ImageIcon(createImageIcon("/images/enemy2Left.gif").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_DEFAULT));
		enemy2Right = new ImageIcon(createImageIcon("/images/enemy2Right.gif").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_DEFAULT));
		enemy3Down = new ImageIcon(createImageIcon("/images/enemy3Down.gif").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_DEFAULT));
		enemy3Up = new ImageIcon(createImageIcon("/images/enemy3Up.gif").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_DEFAULT));
		enemy3Left = new ImageIcon(createImageIcon("/images/enemy3Left.gif").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_DEFAULT));
		enemy3Right = new ImageIcon(createImageIcon("/images/enemy3Right.gif").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_DEFAULT));
		enemy4Down = new ImageIcon(createImageIcon("/images/enemy4Down.gif").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_DEFAULT));
		enemy4Up = new ImageIcon(createImageIcon("/images/enemy4Up.gif").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_DEFAULT));
		enemy4Left = new ImageIcon(createImageIcon("/images/enemy4Left.gif").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_DEFAULT));
		enemy4Right = new ImageIcon(createImageIcon("/images/enemy4Right.gif").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_DEFAULT));
		enemy5Down = new ImageIcon(createImageIcon("/images/enemy5Down.gif").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_DEFAULT));
		enemy5Up = new ImageIcon(createImageIcon("/images/enemy5Up.gif").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_DEFAULT));
		enemy5Left = new ImageIcon(createImageIcon("/images/enemy5Left.gif").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_DEFAULT));
		enemy5Right = new ImageIcon(createImageIcon("/images/enemy5Right.gif").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_DEFAULT));
		enemy6Down = new ImageIcon(createImageIcon("/images/enemy6Down.gif").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_DEFAULT));
		enemy6Up = new ImageIcon(createImageIcon("/images/enemy6Up.gif").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_DEFAULT));
		enemy6Left = new ImageIcon(createImageIcon("/images/enemy6Left.gif").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_DEFAULT));
		enemy6Right = new ImageIcon(createImageIcon("/images/enemy6Right.gif").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_DEFAULT));
		enemy7Down = new ImageIcon(createImageIcon("/images/enemy7Down.gif").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_DEFAULT));
		enemy7Up = new ImageIcon(createImageIcon("/images/enemy7Up.gif").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_DEFAULT));
		enemy7Left = new ImageIcon(createImageIcon("/images/enemy7Left.gif").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_DEFAULT));
		enemy7Right = new ImageIcon(createImageIcon("/images/enemy7Right.gif").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_DEFAULT));
		enemy8 = new ImageIcon(createImageIcon("/images/enemy8.gif").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_DEFAULT));	
	}
	
	public EnemyTile getTile(String name, directionFacing dir)
	{
		EnemyTile tempEnemyTile = new EnemyTile();
		if(name.equals("Pikachu"))
		{
			//System.out.println("Makin a pikachu");
			switch(dir)
			{
			case NORTH:
				tempEnemyTile.setIcon(enemy1Up);
				break;
			case SOUTH:
				tempEnemyTile.setIcon(enemy1Down);
				break;
			case EAST:
				tempEnemyTile.setIcon(enemy1Right);
				break;
			case WEST:
				tempEnemyTile.setIcon(enemy1Left);
				break;
			default:
				break;	
			}
		}
		else if(name.equals("Bulbasaur"))
		{
			switch(dir)
			{
			case NORTH:
				tempEnemyTile.setIcon(enemy2Up);
				break;
			case SOUTH:
				tempEnemyTile.setIcon(enemy2Down);
				break;
			case EAST:
				tempEnemyTile.setIcon(enemy2Right);
				break;
			case WEST:
				tempEnemyTile.setIcon(enemy2Left);
				break;
			default:
				break;	
			}
		}
		else if(name.equals("Squirtle"))
		{
			switch(dir)
			{
			case NORTH:
				tempEnemyTile.setIcon(enemy3Up);
				break;
			case SOUTH:
				tempEnemyTile.setIcon(enemy3Down);
				break;
			case EAST:
				tempEnemyTile.setIcon(enemy3Right);
				break;
			case WEST:
				tempEnemyTile.setIcon(enemy3Left);
				break;
			default:
				break;	
			}
		}
		else if(name.equals("Mew"))
		{
			switch(dir)
			{
			case NORTH:
				tempEnemyTile.setIcon(enemy4Up);
				break;
			case SOUTH:
				tempEnemyTile.setIcon(enemy4Down);
				break;
			case EAST:
				tempEnemyTile.setIcon(enemy4Right);
				break;
			case WEST:
				tempEnemyTile.setIcon(enemy4Left);
				break;
			default:
				break;	
			}
		}
		else if(name.equals("Koffing"))
		{
			switch(dir)
			{
			case NORTH:
				tempEnemyTile.setIcon(enemy5Up);
				break;
			case SOUTH:
				tempEnemyTile.setIcon(enemy5Down);
				break;
			case EAST:
				tempEnemyTile.setIcon(enemy5Right);
				break;
			case WEST:
				tempEnemyTile.setIcon(enemy5Left);
				break;
			default:
				break;	
			}
		}
		else if(name.equals("Rattata"))
		{
			switch(dir)
			{
			case NORTH:
				tempEnemyTile.setIcon(enemy6Up);
				break;
			case SOUTH:
				tempEnemyTile.setIcon(enemy6Down);
				break;
			case EAST:
				tempEnemyTile.setIcon(enemy6Right);
				break;
			case WEST:
				tempEnemyTile.setIcon(enemy6Left);
				break;
			default:
				break;	
			}
		}
		else if(name.equals("Growlithe"))
		{
			switch(dir)
			{
			case NORTH:
				tempEnemyTile.setIcon(enemy7Up);
				break;
			case SOUTH:
				tempEnemyTile.setIcon(enemy7Down);
				break;
			case EAST:
				tempEnemyTile.setIcon(enemy7Right);
				break;
			case WEST:
				tempEnemyTile.setIcon(enemy7Left);
				break;
			default:
				break;	
			}
		}
		else if(name.equals("Doc McCann"))
		{
			tempEnemyTile.setIcon(enemy8);
		}
		else
		{
			System.out.println("No sprite match for \"" + name + "\"");
			switch(dir)
			{
			case NORTH:
				tempEnemyTile.setIcon(enemy1Up);
				break;
			case SOUTH:
				tempEnemyTile.setIcon(enemy1Down);
				break;
			case EAST:
				tempEnemyTile.setIcon(enemy1Right);
				break;
			case WEST:
				tempEnemyTile.setIcon(enemy1Left);
				break;
			default:
				break;	
			}
		}
		//System.out.println(name);
		return tempEnemyTile;
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
}
