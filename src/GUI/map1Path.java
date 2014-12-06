package GUI;

import java.awt.Point;
import java.awt.geom.Point2D;

//Desone's test Hardcoded class do not use for anything else-PH
public class map1Path
{
	public Point2D.Double getLocation(double progress)
	{
		if(progress <= 6)
		{
			return new Point2D.Double(progress, 1);
		}
		else if(progress <= 11)
		{
			return new Point2D.Double(6, progress - 5);
		}
		else if(progress <= 14)
		{
			return new Point2D.Double((-progress) + 17, 6);
		}
		else if(progress <= 15)
		{
			return new Point2D.Double(3, (-progress) + 20);
		}
		else if(progress <= 16)
		{
			return new Point2D.Double(progress - 12, 5);
		}
		else if(progress <= 19)
		{
			return new Point2D.Double(4, (-progress) + 21);
		}
		else if(progress <= 26)
		{
			return new Point2D.Double((-progress) + 23, 2.5);
		}
		else if(progress <= 33)
		{
			
		}
		else if(progress <= 50)
		{
			
		}
		else if(progress <= 52)
		{
			
		}
		else if(progress <= 62)
		{
			
		}
		else if(progress <= 64)
		{
			
		}
		else if(progress <= 74)
		{
			
		}
		else if(progress <= 76)
		{
			
		}
		else if(progress <= 86)
		{
			
		}
		else if(progress <= 88)
		{
			
		}
		else if(progress <= 98)
		{
			
		}
		else if(progress <= 100)
		{
			
		}
		return new Point2D.Double(0,0);
	}
}
