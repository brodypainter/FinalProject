package GUI;

import javax.swing.JLabel;

public class Bone implements Projectile
{
	int progress = 0;
	JLabel label;
	
	public int getProgress()
	{
		return progress;
	}
	
	public void setProgress(int progress)
	{
		this.progress = progress;
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
}
