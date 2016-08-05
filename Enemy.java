

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public interface Enemy {
	
	/**
	 * Returns the y coordinate of the enemy
	 * 
	 * @return yCoordinate
	 */
	public int getY();
	
	/**
	 * Returns the x coordinate of the enemy
	 * 
	 * @return xCoordinate
	 */
	public int getX();
	
	/*
	 * For testing purposes, return the original X and Y coordinates
	 */
	public int getOX();
	
	/**
	 * To make sure the flying works correctly, we need to make sure that we can determine 
	 * whether or not the sprite is on the screen. 
	 */
	
	public boolean onScreen();
	
	/**
	 * Since now we're moving the aliens (much like Space Invaders or Galaxian) by the x-axis,
	 * I need to continuously modify the originalX state so that when aliens fly back,
	 * they fly in the correct position
	 */
	
	public void modifyOriginalX();
	
	/**
	 * All movement will be consolidated into a move function. The state will be determined
	 * by an integer
	 */
	
	/**
	 * If in position (state == 1), they will move right/left at a reduced x velocity
	 */
	
	/**
	 * In certain conditions (like tractor capture, gaining captured ship,
	 * or loss of life, enemies are to fly back to their location
	 * once that has happened, things can resume as normal
	 * 
	 * state == 2
	 */
	
	/*
	 * For the beginning of each stage, enemies will fly from the sides of the screen
	 * to their positions, sometimes firing.
	 * 
	 * For sake of simplicity, they will all be single-file, and coming from the 
	 * bottom corners of the stage
	 * 
	 * state == 3
	 */
	
	/* 
	 * Once all enemies have flown into position, they can fly towards the 
	 * player ship. Red enemies will fly into a zig-zag, yellow enemies will loop,
	 * green enemies will use tractor beam sometimes
	 * 
	 * As with the original 1981 game, they will fly past the bottom of the screen.
	 * We will record the x-coordinate, they will appear on top of the screen, then 
	 * fly back to position
	 * 
	 * state  == 4
	 */
	public void move(PlayerShip ship);
	
	/*
	 * Allows for other functions to modify the flight state
	 */
	public void modifyFlight(int newState);
	
	/*
	 * Primarily for testing reasons. Gives us the flight state
	 */
	public int getFlight();
	
	/*
	 * For testing purposes, get the state of the missiles
	 */
	
	public int getMissile();
	
	/*
	 * When missiles are off screen, use missile class to add back to missile count
	 */
	
	public void addMissile();
	
	/*
	 * This can only happen when enemies are in flight. Up to two of their missiles
	 * can be on screen (for green only; the other two have one)
	 * 
	 * Return the missile, so that we can manipulate it
	 */
	public TheirMissiles fire();
	
	/*
	 * For testing. Determins whether the ship is destroyed or not
	 */
	
	public boolean hasShipBeenDestroyed();
	
	
	/*
	 * Changes enemy sprite to enemy explosion sprite
	 * Set velocity to 0
	 * Get score
	 */
	public void destroyed();
	
	/*
	 * The next four deal with collision, either with player ship or player missiles
	 */
	public boolean collisionMissile(YourMissiles missile);
	public boolean collisionShip(PlayerShip ship);
	public boolean willCollideMissile(YourMissiles missile);
	public boolean willCollideShip(PlayerShip ship);
	
	/*
	 * Get score once enemy is destroyed
	 */
	public int getScore();
	
	public void draw(Graphics g);

}
