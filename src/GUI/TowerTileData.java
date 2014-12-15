package GUI;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

public class TowerTileData
{
	private int tileWidth;
	private int tileHeight;
	private ImageIcon tower1Level1Image;
	private ImageIcon tower1Level2Image;
	private ImageIcon tower2Level1Image;
	private ImageIcon tower2Level2Image;
	private ImageIcon tower2Level3Image;
	private ImageIcon tower3Level1Image;
	private ImageIcon tower3Level2Image;
	private ImageIcon tower4Level1Image;
	private ImageIcon tower4Level2Image;
	private ImageIcon tower4Level3Image;
	private ImageIcon tower5Level1Image;
	private ImageIcon tower5Level2Image;
	private ImageIcon tower5Level3Image;
	private ImageIcon tower6Level1Image;
	private ImageIcon tower6Level2Image;
	private ImageIcon tower6Level3Image;
	private ImageIcon tower7Level1Image;
	private ImageIcon tower7Level2Image;
	private ImageIcon tower7Level3Image;
	private ImageIcon tower8Level1Image;

	
	public TowerTileData(int width, int height)
	{
		tileWidth = width;
		tileHeight = height;
		tower1Level1Image = new ImageIcon(createImageIcon("/images/tower1Level1.png").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_SMOOTH));
		tower1Level2Image = new ImageIcon(createImageIcon("/images/tower1Level2.png").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_SMOOTH));
		tower2Level1Image = new ImageIcon(createImageIcon("/images/tower2Level1.png").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_SMOOTH));
		tower2Level2Image = new ImageIcon(createImageIcon("/images/tower2Level2.png").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_SMOOTH));
		tower2Level3Image = new ImageIcon(createImageIcon("/images/tower2Level3.png").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_SMOOTH));
		tower3Level1Image = new ImageIcon(createImageIcon("/images/tower3Level1.png").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_SMOOTH));
		tower3Level2Image = new ImageIcon(createImageIcon("/images/tower3Level2.png").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_SMOOTH));
		tower4Level1Image = new ImageIcon(createImageIcon("/images/tower4Level1.png").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_SMOOTH));
		tower4Level2Image = new ImageIcon(createImageIcon("/images/tower4Level2.png").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_SMOOTH));
		tower4Level3Image = new ImageIcon(createImageIcon("/images/tower4Level3.png").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_SMOOTH));
		tower5Level1Image = new ImageIcon(createImageIcon("/images/tower5Level1.png").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_SMOOTH));
		tower5Level2Image = new ImageIcon(createImageIcon("/images/tower5Level2.png").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_SMOOTH));
		tower5Level3Image = new ImageIcon(createImageIcon("/images/tower5Level3.png").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_SMOOTH));
		tower6Level1Image = new ImageIcon(createImageIcon("/images/tower6Level1.png").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_SMOOTH));
		tower6Level2Image = new ImageIcon(createImageIcon("/images/tower6Level2.png").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_SMOOTH));
		tower6Level3Image = new ImageIcon(createImageIcon("/images/tower6Level3.png").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_SMOOTH));
		tower7Level1Image = new ImageIcon(createImageIcon("/images/tower7Level1.png").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_SMOOTH));
		tower7Level2Image = new ImageIcon(createImageIcon("/images/tower7Level2.png").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_SMOOTH));
		tower7Level3Image = new ImageIcon(createImageIcon("/images/tower7Level3.png").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_SMOOTH));
		tower8Level1Image = new ImageIcon(createImageIcon("/images/tower8Level1.png").getImage().getScaledInstance(tileWidth, tileHeight,Image.SCALE_SMOOTH));
	}
	
	public TowerTile getTile(String URL)
	{
		TowerTile tempTowerLabel;
		if(URL.endsWith("tower1Level1.png"))
		{
			tempTowerLabel = new TowerTile();
			tempTowerLabel.setType(1);
			tempTowerLabel.setLevel(1);
			tempTowerLabel.setRange(3);
			tempTowerLabel.setPower(15);
			tempTowerLabel.setRate(1.5);
			tempTowerLabel.setUpgradable(true);
			tempTowerLabel.setIcon(tower1Level1Image);
			//System.out.println("Tower 1 level 1 tile generated");
		}
		else if(URL.endsWith("tower1Level2.png"))
		{
			tempTowerLabel = new TowerTile();
			tempTowerLabel.setType(1);
			tempTowerLabel.setLevel(2);
			tempTowerLabel.setRange(5);
			tempTowerLabel.setPower(20);
			tempTowerLabel.setRate(2.5);
			tempTowerLabel.setUpgradable(false);
			tempTowerLabel.setIcon(tower1Level2Image);
		}
		else if(URL.endsWith("tower2Level1.png"))
		{
			tempTowerLabel = new TowerTile();
			tempTowerLabel.setType(2);
			tempTowerLabel.setLevel(1);
			tempTowerLabel.setRange(2);
			tempTowerLabel.setPower(10);
			tempTowerLabel.setRate(4);
			tempTowerLabel.setSpecial("");
			tempTowerLabel.setUpgradable(true);
			tempTowerLabel.setIcon(tower2Level1Image);
		}
		else if(URL.endsWith("tower2Level2.png"))
		{
			tempTowerLabel = new TowerTile();
			tempTowerLabel.setType(2);
			tempTowerLabel.setLevel(2);
			tempTowerLabel.setRange(5);
			tempTowerLabel.setPower(15);
			tempTowerLabel.setRate(6);
			tempTowerLabel.setSpecial("Rapid Fire");
			tempTowerLabel.setUpgradable(true);
			tempTowerLabel.setIcon(tower2Level2Image);
		}
		else if(URL.endsWith("tower2Level3.png"))
		{
			tempTowerLabel = new TowerTile();
			tempTowerLabel.setType(2);
			tempTowerLabel.setLevel(3);
			tempTowerLabel.setRange(7);
			tempTowerLabel.setPower(20);
			tempTowerLabel.setRate(8);
			tempTowerLabel.setSpecial("Rapid Fire");
			tempTowerLabel.setUpgradable(false);
			tempTowerLabel.setIcon(tower2Level3Image);
		}
		else if(URL.endsWith("tower3Level1.png"))
		{
			tempTowerLabel = new TowerTile();
			tempTowerLabel.setType(3);
			tempTowerLabel.setLevel(1);
			tempTowerLabel.setRange(3);
			tempTowerLabel.setPower(12);
			tempTowerLabel.setRate(1.5);
			tempTowerLabel.setSpecial("Slows");
			tempTowerLabel.setUpgradable(true);
			tempTowerLabel.setIcon(tower3Level1Image);
		}
		else if(URL.endsWith("tower3Level2.png"))
		{
			tempTowerLabel = new TowerTile();
			tempTowerLabel.setType(3);
			tempTowerLabel.setLevel(2);
			tempTowerLabel.setRange(5);
			tempTowerLabel.setPower(17);
			tempTowerLabel.setRate(2.5);
			tempTowerLabel.setSpecial("Slows");
			tempTowerLabel.setUpgradable(false);
			tempTowerLabel.setIcon(tower3Level2Image);
		}
		else if(URL.endsWith("tower4Level1.png"))
		{
			tempTowerLabel = new TowerTile();
			tempTowerLabel.setType(4);
			tempTowerLabel.setLevel(1);
			tempTowerLabel.setRange(2);
			tempTowerLabel.setPower(8);
			tempTowerLabel.setRate(1);
			tempTowerLabel.setSpecial("Sleep");
			tempTowerLabel.setUpgradable(true);
			tempTowerLabel.setIcon(tower4Level1Image);
		}
		else if(URL.endsWith("tower4Level2.png"))
		{
			tempTowerLabel = new TowerTile();
			tempTowerLabel.setType(4);
			tempTowerLabel.setLevel(2);
			tempTowerLabel.setRange(4);
			tempTowerLabel.setPower(13);
			tempTowerLabel.setRate(1.5);
			tempTowerLabel.setSpecial("Sleep");
			tempTowerLabel.setUpgradable(true);
			tempTowerLabel.setIcon(tower4Level2Image);
		}
		else if(URL.endsWith("tower4Level3.png"))
		{
			tempTowerLabel = new TowerTile();
			tempTowerLabel.setType(4);
			tempTowerLabel.setLevel(3);
			tempTowerLabel.setRange(6);
			tempTowerLabel.setPower(18);
			tempTowerLabel.setRate(2);
			tempTowerLabel.setSpecial("Sleep");
			tempTowerLabel.setUpgradable(false);
			tempTowerLabel.setIcon(tower4Level3Image);
		}
		else if(URL.endsWith("tower5Level1.png"))
		{
			tempTowerLabel = new TowerTile();
			tempTowerLabel.setType(5);
			tempTowerLabel.setLevel(1);
			tempTowerLabel.setRange(2);
			tempTowerLabel.setPower(20);
			tempTowerLabel.setRate(1);
			tempTowerLabel.setSpecial("Poison");
			tempTowerLabel.setUpgradable(true);
			tempTowerLabel.setIcon(tower5Level1Image);
		}
		else if(URL.endsWith("tower5Level2.png"))
		{
			tempTowerLabel = new TowerTile();
			tempTowerLabel.setType(5);
			tempTowerLabel.setLevel(2);
			tempTowerLabel.setRange(4);
			tempTowerLabel.setPower(25);
			tempTowerLabel.setRate(1.5);
			tempTowerLabel.setSpecial("High Attack");
			tempTowerLabel.setUpgradable(true);
			tempTowerLabel.setIcon(tower5Level2Image);
		}
		else if(URL.endsWith("tower5Level3.png"))
		{
			tempTowerLabel = new TowerTile();
			tempTowerLabel.setType(5);
			tempTowerLabel.setLevel(3);
			tempTowerLabel.setRange(6);
			tempTowerLabel.setPower(30);
			tempTowerLabel.setRate(2);
			tempTowerLabel.setSpecial("High Attack");
			tempTowerLabel.setUpgradable(false);
			tempTowerLabel.setIcon(tower5Level3Image);
		}
		else if(URL.endsWith("tower6Level1.png"))
		{
			tempTowerLabel = new TowerTile();
			tempTowerLabel.setType(6);
			tempTowerLabel.setLevel(1);
			tempTowerLabel.setRange(2);
			tempTowerLabel.setPower(8);
			tempTowerLabel.setRate(1.5);
			tempTowerLabel.setSpecial("Teleports");
			tempTowerLabel.setUpgradable(true);
			tempTowerLabel.setIcon(tower6Level1Image);
		}
		else if(URL.endsWith("tower6Level2.png"))
		{
			tempTowerLabel = new TowerTile();
			tempTowerLabel.setType(6);
			tempTowerLabel.setLevel(2);
			tempTowerLabel.setRange(4);
			tempTowerLabel.setPower(13);
			tempTowerLabel.setRate(2);
			tempTowerLabel.setSpecial("Teleports");
			tempTowerLabel.setUpgradable(true);
			tempTowerLabel.setIcon(tower6Level2Image);
		}
		else if(URL.endsWith("tower6Level3.png"))
		{
			tempTowerLabel = new TowerTile();
			tempTowerLabel.setType(6);
			tempTowerLabel.setLevel(3);
			tempTowerLabel.setRange(6);
			tempTowerLabel.setPower(18);
			tempTowerLabel.setRate(2.5);
			tempTowerLabel.setSpecial("Teleports");
			tempTowerLabel.setUpgradable(false);
			tempTowerLabel.setIcon(tower6Level3Image);
		}
		else if(URL.endsWith("tower7Level1.png"))
		{
			tempTowerLabel = new TowerTile();
			tempTowerLabel.setType(7);
			tempTowerLabel.setLevel(1);
			tempTowerLabel.setRange(5);
			tempTowerLabel.setPower(15);
			tempTowerLabel.setRate(2);
			tempTowerLabel.setSpecial("Burns");
			tempTowerLabel.setUpgradable(true);
			tempTowerLabel.setIcon(tower7Level1Image);
		}
		else if(URL.endsWith("tower7Level2.png"))
		{
			tempTowerLabel = new TowerTile();
			tempTowerLabel.setType(7);
			tempTowerLabel.setLevel(2);
			tempTowerLabel.setRange(7);
			tempTowerLabel.setPower(20);
			tempTowerLabel.setRate(2.5);
			tempTowerLabel.setSpecial("Burns");
			tempTowerLabel.setUpgradable(true);
			tempTowerLabel.setIcon(tower7Level2Image);
		}
		else if(URL.endsWith("tower7Level3.png"))
		{
			tempTowerLabel = new TowerTile();
			tempTowerLabel.setType(7);
			tempTowerLabel.setLevel(3);
			tempTowerLabel.setRange(10);
			tempTowerLabel.setPower(50);
			tempTowerLabel.setRate(3);
			tempTowerLabel.setSpecial("Burns");
			tempTowerLabel.setUpgradable(false);
			tempTowerLabel.setIcon(tower7Level3Image);
		}
		else if(URL.endsWith("tower8Level1.png"))
		{
			tempTowerLabel = new TowerTile();
			tempTowerLabel.setType(8);
			tempTowerLabel.setLevel(0);
			tempTowerLabel.setRange(10);
			tempTowerLabel.setPower(50);
			tempTowerLabel.setRate(5);
			tempTowerLabel.setSpecial("All Powerful");
			tempTowerLabel.setUpgradable(false);
			tempTowerLabel.setIcon(tower8Level1Image);
		}
		else
		{
			tempTowerLabel = new TowerTile();
			tempTowerLabel.setType(1);
			tempTowerLabel.setLevel(1);
			tempTowerLabel.setRange(1);
			tempTowerLabel.setPower(1);
			tempTowerLabel.setRate(1);
			tempTowerLabel.setUpgradable(false);
			tempTowerLabel.setIcon(tower1Level1Image);
		}
		return tempTowerLabel;
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
