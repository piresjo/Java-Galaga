

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics;
import javax.imageio.ImageIO;


public class PlayerShip {

	public static final String img_file = "GalagaShip.png";
	
	private int xCoordinate;
	private int yCoordinate;
	private int missileScreenCount;
	private int v_x;
	private int v_y;
	private int width;
	private int height;
	private int starterX;
	private int starterY;
	private int getPlayfieldW;
	private int getPlayfieldH;
	private boolean exists;
	
	private static BufferedImage img;
	
	public PlayerShip(int x, int y, int playfield_h, int playfield_w) {
		this.xCoordinate = x;
		this.yCoordinate = y;
		this.missileScreenCount = 2;
		this.v_x = 10;
		this.v_y = 10;
		this.getPlayfieldW = playfield_w;
		this.getPlayfieldH = playfield_h;
		this.starterX = x;
		this.starterY = y;
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
	
	public int getX() {
		return this.xCoordinate;
	}
	
	public int getY() {
		return this.yCoordinate;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public int getVX() {
		return this.v_x;
	}
	
	public int getVY() {
		return this.v_y;
	}
	
	public boolean isDestroyed() {
		return (!(this.exists));
	}
	
	public void move(String direction) {
		if (direction.equals("LEFT")) {
			if (this.xCoordinate <= this.width) {
				this.xCoordinate += 0;
			} else {
				this.xCoordinate -= this.v_x;
			}
		}
		else if (direction.equals("RIGHT")) {
			if (this.xCoordinate >= this.getPlayfieldW - this.width) {
				this.xCoordinate += 0;
			} else {
				this.xCoordinate += this.v_x;
			}
		}
		else if (direction.equals("STOP")) {
			this.xCoordinate += 0;
		}
	}
	
	public void addMissile() {
		this.missileScreenCount += 1;
	}
	
	public int getMissileCount() {
		return this.missileScreenCount;
	}
	
	public YourMissiles fire() {
		if (missileScreenCount != 0) {
			missileScreenCount--;
			YourMissiles missile = new YourMissiles(this.xCoordinate, this.yCoordinate,
								   this, this.getPlayfieldH, this.getPlayfieldW);
			return missile;
		}
		return null;
	}
	
	public void explode() {
		img = null;
		this.exists = false;
		
	}
	
	public boolean collisionMissile(TheirMissiles missile) {
		// TODO Auto-generated method stub
		return (this.xCoordinate + this.width >= missile.getX()
				&& this.yCoordinate + this.height >= missile.getY()
				&& missile.getX() + missile.getWidth() >= this.xCoordinate 
				&& missile.getY() + missile.getHeight() >= this.yCoordinate
				&& (!(this.isDestroyed())));
	}
	
	public boolean willCollideMissile(TheirMissiles missile) {
		// TODO Auto-generated method stub
		int next_x = this.xCoordinate + this.v_x;
		int next_y = this.yCoordinate + this.v_y;
		int next_obj_x = missile.getX() + missile.getVX();
		int next_obj_y = missile.getY() + missile.getVY();
		return (next_x + this.width >= next_obj_x
				&& next_y + this.height >= next_obj_y
				&& next_obj_x + missile.getWidth() >= next_x 
				&& next_obj_y + missile.getHeight() >= next_y
				&& (!(this.isDestroyed())));
	}
	
	public void draw(Graphics g) {
		g.drawImage(img, this.xCoordinate, this.yCoordinate, this.width, this.height, null);
	}
	
}
