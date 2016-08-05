

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public interface Missile {
	
	public boolean onScreen();
	public void move();
	public int getX();
	public int getY();
	public int getVX();
	public int getVY();
	public int getWidth();
	public int getHeight();
	public void destroy();
	public void draw(Graphics g);
	
}
