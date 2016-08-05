

import static org.junit.Assert.*;
import org.junit.*;

public class PlayerShipTests {

	private PlayerShip ship1;
	private PlayerShip ship2;
	private PlayerShip ship3;
	private GreenEnemy green;
	private RedEnemy red;
	private YellowEnemy yellow;
	
	@Before
	public void setUp() {
		ship1 = new PlayerShip(50, 40, 250, 250);
		ship2 = new PlayerShip(100, 40, 250, 250);
		ship3 = new PlayerShip(150, 40, 250, 250);
		green = new GreenEnemy(50, 100, 250, 250, 100, 50);
		red = new RedEnemy(100, 100, 250, 250, 100, 100);
		yellow = new YellowEnemy(150, 100, 250, 250, 100, 150);
	}
	
	@Test
	public void moveTest() {
		assertEquals(100, ship2.getX());
		assertEquals(40, ship2.getY());
		ship2.move("RIGHT");
		assertEquals(110, ship2.getX());
		ship2.move("LEFT");
		assertEquals(100, ship2.getX());
		assertEquals(50, ship1.getX());
		ship1.move("LEFT");
		assertEquals(40, ship1.getX());
		ship1.move("LEFT");
		assertEquals(40, ship1.getX());
		
		for (int i = 0; i < 6; i++) {
			ship3.move("RIGHT");
		}
		
		assertEquals(210, ship3.getX());
		ship3.move("RIGHT");
		assertEquals(210, ship3.getX());
		
		for (int i = 0; i < 6; i++) {
			ship3.move("LEFT");
		}
		
		assertEquals(150, ship3.getX());
	}
	
	@Test
	public void fireTest() {
		assertEquals(2, ship3.getMissileCount());
		YourMissiles missile3 = ship3.fire();
		assertEquals(1, ship3.getMissileCount());
		assertEquals(150, missile3.getX());
		assertEquals(40, missile3.getY());
		
		for (int i = 0; i < 5; i++) {
			missile3.move();
		}
		
		assertEquals(true, yellow.collisionMissile(missile3));
		
		missile3.destroy();
		
		assertEquals(2, ship3.getMissileCount());
		ship3.fire();
		ship3.fire();
		assertEquals(0, ship3.getMissileCount());
		ship3.fire();
		assertEquals(0, ship3.getMissileCount());
		
	}
	
	@Test
	public void collisionTest() {
		red.modifyFlight(4);
		for(int i = 0; i < 7; i++) {
			red.move(ship2);
		}
		assertEquals(true, red.collisionShip(ship2));
		red.destroyed();
		ship2.explode();
		assertEquals(true, ship2.isDestroyed());
		
	}
	
	@Test
	public void willCollideTest() {
		
	}
	
}
