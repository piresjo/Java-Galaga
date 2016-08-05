// NEED TO FLIP DIRECTIONS

import static org.junit.Assert.*;
import org.junit.*;

public class YellowEnemyTests {

	private PlayerShip ship;
	private YellowEnemy enemy;
	private YellowEnemy otherEnemy;
	private YellowEnemy displacedEnemy;
	private YellowEnemy attackEnemy;
	private YellowEnemy belowAttackEnemy;
	private YellowEnemy stationaryEnemy;
	private YellowEnemy movingEnemy;
	private PlayerShip collisionShip;
	
	@Before
	public void setUp() {
		enemy = new YellowEnemy(50, 50, 250, 250, 50, 50);
		otherEnemy = new YellowEnemy(260, 260, 250, 250, 260, 260);
		displacedEnemy = new YellowEnemy(100, 100, 250, 250, 50, 50);
		ship = new PlayerShip(125, 40, 250, 250);
		attackEnemy = new YellowEnemy(100, 100, 250, 250, 100, 100);
		belowAttackEnemy = new YellowEnemy(100, 20, 250, 250, 200, 100);
		stationaryEnemy = new YellowEnemy(200, 200, 250, 250, 200, 200);
		movingEnemy = new YellowEnemy(25, 25, 250, 250, 25, 25);
		collisionShip = new PlayerShip (200, 200, 250, 250);
	}
	
	@Test
	public void testInitialStates() {
		assertEquals(1, enemy.getFlight());
		assertEquals(50, enemy.getX());
		assertEquals(50, enemy.getY());
	}
	
	@Test
	public void testOnScreen() {
		assertEquals(true, enemy.onScreen());
		assertEquals(false, otherEnemy.onScreen());
	}
	
	@Test
	public void testLeftRightMovement() {
		enemy.move(null);
		assertEquals(54, enemy.getX());
		for (int i = 0; i < 6; i++) {
			enemy.move(null);
		}
		assertEquals(78, enemy.getX());
		enemy.move(null);
		assertEquals(74, enemy.getX());
		enemy.move(null);
		assertEquals(70, enemy.getX());
	}
	
	@Test
	public void testReturnFlight() {
		displacedEnemy.modifyFlight(2);
		displacedEnemy.move(null);
		assertEquals(94, displacedEnemy.getX());
		assertEquals(94, displacedEnemy.getY());
		assertEquals(54, displacedEnemy.getOX());
	}
	
	@Test
	public void attackFlight() {
		attackEnemy.modifyFlight(4);
		attackEnemy.move(ship);
		assertEquals(94, attackEnemy.getY());
		
		belowAttackEnemy.modifyFlight(4);
		belowAttackEnemy.move(ship);
		assertEquals(14, belowAttackEnemy.getY());
		belowAttackEnemy.move(ship);
		assertEquals(8, belowAttackEnemy.getY());
		belowAttackEnemy.move(ship);
		assertEquals(2, belowAttackEnemy.getY());
		belowAttackEnemy.move(ship);
		assertEquals(240, belowAttackEnemy.getY());
		
	}
	
	@Test
	public void testFiring() {
		TheirMissiles missile = attackEnemy.fire();
		assertEquals(100, missile.getY());
		assertEquals(0, attackEnemy.getMissile());
		attackEnemy.fire();
		assertEquals(0, attackEnemy.getMissile());
	}
	
	@Test
	public void scoreFromGetScore() {
		movingEnemy.modifyFlight(2);
		assertEquals(50, stationaryEnemy.getScore());
		assertEquals(100, movingEnemy.getScore());
	}
	
	@Test
	public void testCollisions() {
		assertEquals(false, stationaryEnemy.hasShipBeenDestroyed());
		assertEquals(true, stationaryEnemy.collisionShip(collisionShip));
		stationaryEnemy.destroyed();
		assertEquals(false, stationaryEnemy.collisionShip(collisionShip));
	}
	
	@Test
	public void willCollide() {
		
	}
	
}
