
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class GreenEnemy {
	
	public static final String img_file = "BlueGalagaOpen.png";
	public static final String green_img_file = "GreenGalagaOpen.png";
	
	private int scoreStationary;
	private int scoreHitOnce;
	private int scoreFlying;
	private int flyingState;
	private int xCoordinate;
	private int yCoordinate;
	private int v_x;
	private int v_y;
	private int originalX;
	private int originalY;
	private int healthState;
	private int width;
	private int height;
	private int missileScreenCount;
	private boolean goRight;
	private boolean onScreen;
	private int getPlayfieldW;
	private int getPlayfieldH;
	private boolean isDestroyed;
	private int maxRight;
	private int maxLeft;
	private Random randomizer;
	
	private BufferedImage img;
	
	public GreenEnemy(int x, int y, int playfield_h, int playfield_w, int origY, int origX) {
		this.scoreStationary = 150;
		this.scoreHitOnce = 50;
		this.scoreFlying = 400;
		this.flyingState = 1;
		this.xCoordinate = x;
		this.yCoordinate = y;
		this.originalX = origX;
		this.originalY = origY;
		this.healthState = 2;
		this.v_x = 4;
		this.v_y = 4;
		this.missileScreenCount = 2;
		this.goRight = true;
		this.getPlayfieldW = playfield_w;
		this.getPlayfieldH = playfield_h;
		this.isDestroyed = false;
		
		try {
			if (this.img == null) {
				this.img = ImageIO.read(new File(green_img_file));
			}
		} catch (IOException e) {
			System.out.println("INTERNAL ERROR: " + e.getMessage());
		}
		
		this.width = img.getWidth();
		this.height = img.getHeight();
		
		this.maxRight = x + this.width;
		this.maxLeft = x - this.width;
	}
	
	public int getY() {
		return this.yCoordinate;
	}
	
	public int getX() {
		return this.xCoordinate;
	}
	
	public void modifyOriginalX() {
		int modVX = 4;
		int distanceFromRight = Math.abs(this.maxRight - this.originalX);
		int distanceFromLeft = Math.abs(this.maxLeft - this.originalX);
		
		if (distanceFromRight < 4) {
			this.goRight = false;
			this.originalX -= modVX;
		} else if (distanceFromLeft < 4) {
			this.goRight = true;
			this.originalX += modVX;
		} else {
			if (this.goRight) {
				this.originalX += modVX;
			} else {
				this.originalX -= modVX;
			}
		}
	}
	
	public boolean onScreen() {
		return (this.xCoordinate > 0 && this.xCoordinate < this.getPlayfieldW
				&& this.yCoordinate > 0 && this.yCoordinate < this.getPlayfieldH);
	}
	
	public void move(PlayerShip ship) {
		if (this.flyingState == 1) {
			int distanceFromRight = Math.abs(this.maxRight - this.xCoordinate);
			int distanceFromLeft = Math.abs(this.maxLeft - this.xCoordinate);
			
			if (distanceFromRight < 4) {
				this.goRight = false;
				this.xCoordinate -= this.v_x;
				this.originalX -= this.v_x;
			} else if (distanceFromLeft < 4) {
				this.goRight = true;
				this.xCoordinate += this.v_x;
				this.originalX += this.v_x;
			} else {
				if (this.goRight) {
					this.xCoordinate += this.v_x;
					this.originalX += this.v_x;
				} else {
					this.xCoordinate -= this.v_x;
					this.originalX -= this.v_x;
				}
			}
			
		} else if (this.flyingState == 2) {
			this.modifyOriginalX();
			int newUp = this.yCoordinate + this.v_y;
			int newDown = this.yCoordinate - this.v_y;
			int newLeft = this.xCoordinate - this.v_x;
			int newRight = this.xCoordinate + this.v_x;
			
			int distUp = Math.abs(newUp - this.originalY);
			int distDown = Math.abs(newDown - this.originalY);
			int distLeft = Math.abs(newLeft - this.originalX);
			int distRight = Math.abs(newRight - this.originalX);
			
			if (distUp < distDown) {
				if (distUp < this.v_y){
					this.yCoordinate = this.originalY;
				} else {
					this.yCoordinate += this.v_y;
				}
			} else {
				if (distDown < this.v_y){
					this.yCoordinate = this.originalY;
				} else {
					this.yCoordinate -= this.v_y;
				}
			}
			
			if (distRight < distLeft) {
				if (distRight < this.v_x){
					this.xCoordinate = this.originalX;
				} else {
					this.xCoordinate += this.v_x;
				}
			} else {
				if (distLeft < this.v_x){
					this.xCoordinate = this.originalX;
				} else {
					this.xCoordinate -= this.v_x;
				}
			}
			
		} else if (this.flyingState == 3) {
			// HOLD
			// OFF
			// ON
			// THIS
			// UNTIL
			// REST
			// OF
			// GAME 
			// IS
			// DONE
		} else if (this.flyingState == 4) {
			int shipX = ship.getX();
			
			if (!(this.onScreen()) || this.yCoordinate < this.v_y) {
				this.yCoordinate = this.getPlayfieldH - 10;
				this.flyingState = 2;
			} else {
				int newLeft = this.xCoordinate + this.v_x;
				int newRight = this.xCoordinate - this.v_x;
				int distLeft = Math.abs(newLeft - shipX);
				int distRight = Math.abs(newRight - shipX);
				
				if (distRight < distLeft) {
					this.xCoordinate += this.v_x;
				} else if (distLeft < distRight) {
					this.xCoordinate -= this.v_x;
				} else {
					this.xCoordinate += 0;
				}
				this.yCoordinate -= this.v_y;
				int value = this.randomizer.nextInt(17);
				if (value == 0) {
					this.fire();
				}
			}
		}
	} 
	
	public void modifyFlight(int newState) {
		if (newState > 0 && newState < 5) {
			this.flyingState = newState;
		}
	}
	
	public int getFlight() {
		return this.flyingState;
	}
	
	public int getHealthState() {
		return this.healthState;
	}
	
	public void destroyed() {
		this.img = null;
		this.isDestroyed = true;
	}
	
	public void hit() {
		if (healthState != 0) {
			healthState--;
			if (healthState == 1) {
				try {
					this.img = ImageIO.read(new File(img_file));
				} catch (IOException e) {
					System.out.println("INTERNAL ERROR: " + e.getMessage());
				}
			}
		}
	}
	
	public void tractorBeam() {
		
	}

	public int getMissile() {
		return this.missileScreenCount;
	}
	
	public void addMissile() {
		this.missileScreenCount += 1;
	}
	
	public TheirMissiles fire() {
		// TODO Auto-generated method stub
		if (this.missileScreenCount != 0) {
			missileScreenCount--;
			TheirMissiles missile = new TheirMissiles(this.xCoordinate, this.yCoordinate, 
									null, this, null, this.getPlayfieldH, this.getPlayfieldW);
			
			return missile;
		}
		return null;
	}
	
	public boolean hasShipBeenDestroyed() {
		return this.isDestroyed;
	}
	
	public int getScore() {
		if (healthState == 1) {
			return this.scoreHitOnce;
		}
		if (healthState == 0 && this.flyingState != 1) {
			return this.scoreFlying;
		}
		if (healthState == 0 && this.flyingState == 1) {
			return this.scoreStationary;
		}
		return 0;
	}
	
	public int getOX() {
		// TODO Auto-generated method stub
		return this.originalX;
	}
	
	public boolean collisionMissile(YourMissiles missile) {
		// TODO Auto-generated method stub
		return (this.xCoordinate + this.width >= missile.getX()
				&& this.yCoordinate + this.height >= missile.getY()
				&& missile.getX() + missile.getWidth() >= this.xCoordinate 
				&& missile.getY() + missile.getHeight() >= this.yCoordinate
				&& (!(this.isDestroyed)));
	}


	public boolean collisionShip(PlayerShip ship) {
		// TODO Auto-generated method stub
		return (this.xCoordinate + this.width >= ship.getX()
				&& this.yCoordinate + this.height >= ship.getY()
				&& ship.getX() + ship.getWidth() >= this.xCoordinate 
				&& ship.getY() + ship.getHeight() >= this.yCoordinate
				&& (!(this.isDestroyed)));
	}


	public boolean willCollideMissile(YourMissiles missile) {
		// TODO Auto-generated method stub
		int next_x = this.xCoordinate + this.v_x;
		int next_y = this.yCoordinate + this.v_y;
		int next_obj_x = missile.getX() + missile.getVX();
		int next_obj_y = missile.getY() + missile.getVY();
		return (next_x + this.width >= next_obj_x
				&& next_y + this.height >= next_obj_y
				&& next_obj_x + missile.getWidth() >= next_x 
				&& next_obj_y + missile.getHeight() >= next_y
				&& (!(this.isDestroyed)));
	}


	public boolean willCollideShip(PlayerShip ship) {
		// TODO Auto-generated method stub
		int next_x = this.xCoordinate + this.v_x;
		int next_y = this.yCoordinate + this.v_y;
		int next_obj_x = ship.getX() + ship.getVX();
		int next_obj_y = ship.getY() + ship.getVY();
		return (next_x + this.width >= next_obj_x
				&& next_y + this.height >= next_obj_y
				&& next_obj_x + ship.getWidth() >= next_x 
				&& next_obj_y + ship.getHeight() >= next_y
				&& (!(this.isDestroyed)));
	}
	
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(this.img, this.xCoordinate, this.yCoordinate, this.width, this.height, null);
	}
	
}
