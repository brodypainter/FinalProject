package GUI;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import javax.swing.JLabel;

public class GhostBall extends Projectile
{
	double progress = 0;
	JLabel label;
	Path path;
	
	public double getProgress()
	{
		return progress;
	}
	
	public void setProgress(double progress)
	{
		this.progress = progress;
	}
	
	public void setPath(Path path)
	{
		this.path = path;
	}
	
	public Point2D.Double getLocationInGrid()
	{
		return path.getLocation(progress);
	}
	
	public JLabel getLabel()
	{
		return null;
	}
	
	public void setLabel(JLabel label)
	{
		this.label = label;
	}

	public boolean isValid()
	{
		if(progress >= 100)
		{
			return false;
		}
		return true;
	}

	public void setProgress(int progress)
	{
		this.progress = progress;
	}
}
