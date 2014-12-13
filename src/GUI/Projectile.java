package GUI;

import java.awt.Point;
import java.awt.geom.Point2D;

import javax.swing.JLabel;

public abstract class Projectile extends JLabel
{	
	abstract double getProgress();
	abstract Point2D.Double getLocationInGrid();
	abstract void setProgress(int progress);
	abstract JLabel getLabel();
	abstract void setLabel(JLabel label);
	public abstract boolean isValid();
}
