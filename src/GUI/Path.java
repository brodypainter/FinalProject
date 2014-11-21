package GUI;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class Path
{
	Point initial;
	Point terminal;
	double slope;
	double intercept;
	double distance;
	
	public static void main(String[] args)
	{
		new Path(-5, 0, 10, 10).getLocation(50);
		
	}
	
	public Path(double x1, double y1, double x2, double y2)
	{
		initial = new Point((int) x1,(int) y1);
		terminal = new Point((int) x2,(int) y2);
		slope = (double) ((y2-y1)/(x2-x1));
		intercept = y1 - (slope*x1);
		distance = initial.distance(terminal);
		//System.out.println("Slope: " + slope);
		//System.out.println("Intercept: " + intercept);
		//System.out.println("Distance: " + distance);
	}
	
	public Point2D.Double getLocation(double progress)
	{
		double currentDistance = distance * (progress/100);
		//System.out.println("Distance from origin: " + currentDistance);
		Point2D.Double output = new Point2D.Double( -(((terminal.x-initial.x) * (currentDistance / distance)) - terminal.x), -(((terminal.y-initial.y) * (currentDistance / distance)) - terminal.y));
		//System.out.println("New point: (" + output.x + ", " + output.y + ")");
		return output;
	}
}
