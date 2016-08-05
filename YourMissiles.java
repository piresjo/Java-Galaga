
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class YourMissiles implements Missile {
	
public static final String img_file = "YourMissile.png";
	
	private int xCoordinate;
	private int yCoordinate;
	private int v_x;
	private int v_y;
	private int width;
	private int height;
	private PlayerShip ship;
	private int playfieldHeight;
	private int playfieldWidth;
	private boolean exists;
	
	private static BufferedImage img;
	
	public YourMissiles(int x, int y, PlayerShip ship,
						 int playfieldY, int playfieldX) {
		this.xCoordinate = x;
		this.yCoordinate = y;
		this.ship = ship;
		this.v_x = 0;
		this.v_y = 14;
		this.playfieldHeight = playfieldY;
		this.playfieldWidth = playfieldX;
		this.exists = true;
		
		try {
			if (img == null) {
				img = ImageIO.read(new File(img_file));
			}
		} catch (IOException e) {
			System.out.println("INTERNAL ERROR: " + e.getMessage());
		}
		
		this.width = img.getWidth();
		this.height = img.getHeight();
	}
	
	@Override
	public boolean onScreen() {
		// TODO Auto-generated method stub
		boolean isOn = (this.xCoordinate > 0 && this.xCoordinate < this.playfieldWidth
				&& this.yCoordinate > 0 && this.yCoordinate < this.playfieldHeight);
		if (!(isOn)) {
			this.ship.addMissile();
		}
		return isOn;
	}

	@Override
	public void move() {
		if (this.yCoordinate > (this.playfieldHeight - this.v_y)) {
			img = null;
			this.exists = false;
		}
		this.yCoordinate -= this.v_y;
		
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return this.xCoordinate;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return this.yCoordinate;
	}

	@Override
	public int getVX() {
		// TODO Auto-generated method stub
		return this.v_x;
	}

	@Override
	public int getVY() {
		// TODO Auto-generated method stub
		return this.v_y;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return this.width;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return this.height;
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		this.img = null;
		this.exists = false;
		this.ship.addMissile();
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(img, this.xCoordinate, this.yCoordinate, this.width, this.height, null);
	}


	

}