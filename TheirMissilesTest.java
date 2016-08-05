

import static org.junit.Assert.*;
import org.junit.*;

public class TheirMissilesTest {
	private TheirMissiles stray;
	private GreenEnemy green;
	private YellowEnemy yellow;
	private RedEnemy red;
	//private PlayerShip ship;
	
	
	@Before
	public void setUp() {
		stray = new TheirMissiles(100, 100, null, null, null, 250, 250);
		green = new GreenEnemy(50, 40, 250, 250, 40, 50);
		yellow = new YellowEnemy(100, 40, 250, 250, 40, 100);
		red = new RedEnemy(150, 40, 250, 250, 40, 150);
	}
	
	@Test
	public void fireTest() {
		TheirMissiles greenMissile = green.fire();
		TheirMissiles yellowMissile = yellow.fire();
		TheirMissiles redMissile = red.fire();
		
		assertEquals(50, greenMissile.getX());
		assertEquals(100, yellowMissile.getX());
		assertEquals(150, redMissile.getX());
		
		assertEquals(40, greenMissile.getY());
		assertEquals(40, yellowMissile.getY());
		assertEquals(40, redMissile.getY());
		
		assertEquals(1, green.getMissile());
		assertEquals(0, yellow.getMissile());
		assertEquals(0, red.getMissile());
		
		greenMissile.move();
		yellowMissile.move();
		redMissile.move();
		
		assertEquals(52, greenMissile.getY());
		assertEquals(52, yellowMissile.getY());
		assertEquals(52, redMissile.getY());
		
		/*greenMissile.move();
		yellowMissile.move();
		redMissile.move();
		
		greenMissile.move();
		yellowMissile.move();
		redMissile.move();
		
		greenMissile.move();
		yellowMissile.move();
		redMissile.move();
		
		assertEquals(false, greenMissile.onScreen());
		assertEquals(false, yellowMissile.onScreen());
		assertEquals(false, redMissile.onScreen()); */
		
		assertEquals(2, green.getMissile());
		assertEquals(1, yellow.getMissile());
		assertEquals(1, red.getMissile());
		
		red.fire();
		assertEquals(0, red.getMissile());
		red.fire();
		assertEquals(0, red.getMissile());
	}
	
	@Test 
	public void moveTest() {
		assertEquals(100, stray.getX());
		assertEquals(100, stray.getY());
	}
	
	@Test 
	public void destroyTest() {
		
	}
}
