package GUI;

import java.awt.Point;

public class Line
{
	Point beg;
	Point end;
	int iterations = 0;
	
	public Line(Point beg, Point end)
	{
		this.beg = beg;
		this.end = end;
	}
	
	public Point getStart()
	{
		return beg;
	}
	
	public Point getEnd()
	{
		return end;
	}
	
	public Line iterate()
	{
		iterations++;
		return this;
	}
	
	public int getIterations()
	{
		return iterations;
	}
}
