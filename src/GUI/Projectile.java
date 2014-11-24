package GUI;

import javax.swing.JLabel;

public interface Projectile
{	
	int getProgress();
	void setProgress(int progress);
	JLabel getLabel();
	void setLabel(JLabel label);
	boolean isValid();
}
