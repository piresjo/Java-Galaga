
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class RedEnemy implements Enemy {

	public static final String img_file = "RedBadGuy.png";
	
	private int scoreStationary;
	private int scoreFlying;
	private int flyingState;
	private int xCoordinate;
	private int yCoordinate;
	private int v_x;
	private int v_y;
	private int originalX;
	private int originalY;
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
	
	
	public RedEnemy(int x, int y, int playfield_h, int playfield_w, int origY, int origX) {
		this.scoreStationary = 80;
		this.scoreFlying = 160;
		this.flyingState = 1;
		this.xCoordinate = x;
		this.yCoordinate = y;
		this.originalX = origX;
		this.originalY = origY;
		this.v_x = 8;
		this.v_y = 8;
		this.missileScreenCount = 1;
		this.goRight = true;
		this.getPlayfieldW = playfield_w;
		this.getPlayfieldH = playfield_h;
		this.isDestroyed = false;
		this.randomizer = new Random();
		
		
		try {
			if (this.img == null) {
				this.img = ImageIO.read(new File(img_file));
			}
		} catch (IOException e) {
			System.out.println("INTERNAL ERROR: " + e.getMessage());
		}
		
		this.width = img.getWidth();
		this.height = img.getHeight();
		
		this.maxRight = x + this.width;
		this.maxLeft = x - this.width;
		
	}
	
	
	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return yCoordinate;
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return xCoordinate;
	}
	
	@Override
	public int getOX() {
		return this.originalX;
	}
	
	@Override
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
	
	@Override
	public boolean onScreen() {
		return (this.xCoordinate > 0 && this.xCoordinate < this.getPlayfieldW
				&& this.yCoordinate > 0 && this.yCoordinate < this.getPlayfieldH);
	}
	
	@Override
	public void move(PlayerShip ship) {
		if (this.flyingState == 1) {
			this.v_x = 4;
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
			this.v_x = 8;
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
			this.v_x = 8;
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
			this.v_x = 8;
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
	
	@Override
	public void modifyFlight(int newState) {
		if (newState > 0 && newState < 5) {
			this.flyingState = newState;
		}
	}
	
	@Override
	public int getFlight() {
		return this.flyingState;
	}
	
	@Override
	public int getMissile() {
		return this.missileScreenCount;
	}
	
	@Override
	public void addMissile() {
		this.missileScreenCount += 1;
	}

	@Override
	public TheirMissiles fire() {
		// TODO Auto-generated method stub
		if (this.missileScreenCount != 0) {
			missileScreenCount--;
			TheirMissiles missile = new TheirMissiles(this.xCoordinate, this.yCoordinate, 
									this, null, null, this.getPlayfieldH, this.getPlayfieldW);
			return missile;
		}
		return null;
	}

	@Override
	public boolean hasShipBeenDestroyed() {
		return this.isDestroyed;
	}
	
	@Override
	public void destroyed() {
		// TODO Auto-generated method stub
		this.img = null;
		this.isDestroyed = true;
	}


	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		if (this.flyingState != 1) {
			return this.scoreFlying;
		}
		return this.scoreStationary;
	}


	@Override
	public boolean collisionMissile(YourMissiles missile) {
		// TODO Auto-generated method stub
		return (this.xCoordinate + this.width >= missile.getX()
				&& this.yCoordinate + this.height >= missile.getY()
				&& missile.getX() + missile.getWidth() >= this.xCoordinate 
				&& missile.getY() + missile.getHeight() >= this.yCoordinate
				&& (!(this.isDestroyed)));
	}


	@Override
	public boolean collisionShip(PlayerShip ship) {
		// TODO Auto-generated method stub
		return (this.xCoordinate + this.width >= ship.getX()
				&& this.yCoordinate + this.height >= ship.getY()
				&& ship.getX() + ship.getWidth() >= this.xCoordinate 
				&& ship.getY() + ship.getHeight() >= this.yCoordinate
				&& (!(this.isDestroyed)));
	}


	@Override
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


	@Override
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


	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(this.img, this.xCoordinate, this.yCoordinate, this.width, this.height, null);
	}

}
