

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.*;

@SuppressWarnings("serial")
public class GalagaEngine extends JPanel {

	private PlayerShip ship;
	private int numLives;
	private int score;
	private int hiScore;
	private int numEnemies;
	private int stageNumber;
	private LinkedList<GreenEnemy> greenEnemies;
	private LinkedList<RedEnemy> redEnemies;
	private LinkedList<YellowEnemy> yellowEnemies;
	private LinkedList<TheirMissiles> theirMissiles;
	private LinkedList<YourMissiles> yourMissiles;
	private String direction;
	
	public boolean playing = false;
	private JLabel status;
	
	public static final int COURT_WIDTH = 448;
	public static final int COURT_HEIGHT = 512;
	public static final int INTERVAL = 35; 
	
	private boolean paused = false;
	
	public GalagaEngine(JLabel status) {
		
		this.numEnemies = 0;
		this.direction = "STOP";
		this.greenEnemies = new LinkedList<>();
		this.redEnemies = new LinkedList<>();
		this.yellowEnemies = new LinkedList<>();
		this.theirMissiles = new LinkedList<>();
		this.yourMissiles = new LinkedList<>();
		
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setBackground(Color.BLACK);
		
		Timer timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		timer.start();
		
		setFocusable(true);
		
		addKeyListener(new KeyAdapter() {
			
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					direction = "LEFT";
				}
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					direction = "RIGHT";
				}

				else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					if (yourMissiles.size() < 2) {
						YourMissiles missile = ship.fire();
						yourMissiles.add(missile);
					}	
				}
				else if (e.getKeyCode() == KeyEvent.VK_P) {
					if (paused) {
						paused = false;
						timer.start();
						status.setText("Running...");
					} else {
						timer.stop();
						paused = true;
						status.setText("Paused");
					}
				}
				else if (e.getKeyCode() == KeyEvent.VK_Q) {
					System.exit(0);
				}
					
			}
			
			public void keyReleased(KeyEvent e) {
				direction = "STOP";
			}
			
		});
		
		this.status = status;
		this.numLives = 5;
		this.score = 0;
		//NEED TO GET HIGH SCORE
		this.stageNumber = 1;
	}
	
	public void establish() {
		for (int i = 0; i < 4; i++) {
			GreenEnemy green = new GreenEnemy(196 + (i * 30 + 10),(COURT_HEIGHT - 450), COURT_HEIGHT , COURT_WIDTH ,
					(COURT_HEIGHT - 450), 196 + (i * 30 + 10));
			this.greenEnemies.add(green);
			numEnemies++;
		} 
		for (int j = 0; j < 6; j++) {
			RedEnemy red = new RedEnemy(166 + (j * 30 + 10), (COURT_HEIGHT - 400), COURT_HEIGHT, COURT_WIDTH, 
					(COURT_HEIGHT - 400), 166 + (j * 30 + 10));
			this.redEnemies.add(red);
			numEnemies++;
		}
		for (int k = 0; k < 6; k++) {
			RedEnemy red = new RedEnemy(166 + (k * 30 + 10), (COURT_HEIGHT - 350), COURT_HEIGHT, COURT_WIDTH, 
					(COURT_HEIGHT - 350), 166 + (k * 30 + 10));
			this.redEnemies.add(red);
			numEnemies++;
		}
		for (int l = 0; l < 8; l++) {
			YellowEnemy yellow = new YellowEnemy(136 + (l * 30 + 10), (COURT_HEIGHT - 300), COURT_HEIGHT, COURT_WIDTH,
					(COURT_HEIGHT - 300), 136 + (l * 30 + 10));
			this.yellowEnemies.add(yellow);
			numEnemies++;
		} 
		for (int m = 0; m < 8; m++) {
			YellowEnemy yellow = new YellowEnemy(136 + (m * 30 + 10), (COURT_HEIGHT - 250), COURT_HEIGHT, COURT_WIDTH,
					(COURT_HEIGHT - 250), 136 + (m * 30 + 10));
			this.yellowEnemies.add(yellow);
			numEnemies++;
		}
	}
		
	
	public void reset() {
		
		//First, test with just a couple sprites
		establish();
		this.ship = new PlayerShip(256, COURT_HEIGHT - 40, COURT_HEIGHT, COURT_WIDTH);
		
		score = 0;
		numLives = 5;
		stageNumber = 1;
		
		String statusText = "Running...\n" +
				"Lives: " + numLives + "\n" +
				" Stage: " + stageNumber + "\n" +
				" Score: " + score + "\n" +
				" High Score: ";
		
		playing = true;
		status.setText(statusText);
			
		requestFocusInWindow();
			
	}
	
	public void quasiReset() {
		establish();

			
		String statusText = "Running...\n" +
				"Lives: " + numLives + "\n" +
				"Stage: " + stageNumber + "\n" +
				"Score: " + score + "\n" +
				"High Score: ";
		
		playing = true;
		status.setText(statusText);

			
		requestFocusInWindow();
	}
	
	public void updateStatus() {
		String updateText = "Running... " +
				"Lives: " + numLives + " " +
				"Stage: " + stageNumber + " " +
				"Score: " + score + " " +
				"Enemies: " + numEnemies + " " +
				"High Score: ";
		
		status.setText(updateText);
	}
	
	public void showInstructions() {
		String message = "--Galaga--\n"
				+ "--Objective: Shoot Enemies For Score--\n" +
				"--Red (Flying) - 160--\n" +
				"--Red (Stationary) - 80--\n" +
				"--Yellow (Flying) - 100--\n" +
				"--Yellow (Stationary) - 50--\n" +
				"--Green (Hit Once) - 50--\n" +
				"--Green (Stationary) - 150--\n" +
				"--Green (Flying) - 400--\n" +
				"--CONTROLS--\n" +
				"--Left - Left Arrow--\n" +
				"--Right - Right Arrow--\n" +
				"--Stop - Down Arrow--\n" +
				"--Fire - Space Button--\n";
				
		JOptionPane.showMessageDialog(null, message, "InfoBox: Instructions", 
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	void tick() {
		if (playing) {

			int yourCount = 0;
			
					
			if (numEnemies == 0) {
				stageNumber++;
				quasiReset();
			}
		
			this.updateStatus();
			
			ship.move(direction);
			
			for (GreenEnemy entry : greenEnemies) {
				for (YourMissiles entryMissile : yourMissiles) {
					yourCount = yourMissiles.indexOf(entryMissile);
					if (entry.collisionMissile(entryMissile) && entry.getHealthState() == 2) {
						entryMissile.destroy();
						yourMissiles.remove(yourCount);
						entry.hit();
						score += entry.getScore();
					} else if (entry.collisionMissile(entryMissile) && entry.getHealthState() == 1) {
						entryMissile.destroy();
						yourMissiles.remove(yourCount);
						entry.hit();
						score += entry.getScore();
						entry.destroyed();
						numEnemies--;
					}
				}
				yourCount = 0;
				if (!(entry.hasShipBeenDestroyed())) {
					entry.move(ship);
				}
			}
			
			for (RedEnemy entry : redEnemies) {
				for (YourMissiles entryMissile : yourMissiles) {
					yourCount = yourMissiles.indexOf(entryMissile);
					if (entry.collisionMissile(entryMissile)) {
						entryMissile.destroy();
						yourMissiles.remove(yourCount);
						entry.destroyed();
						score += entry.getScore();
						numEnemies--;
					}
				}
				yourCount = 0;
				if (!(entry.hasShipBeenDestroyed())) {
					entry.move(ship);
				}
			}
			for (YellowEnemy entry : yellowEnemies) {
				for (YourMissiles entryMissile : yourMissiles) {
					yourCount = yourMissiles.indexOf(entryMissile);
					if (entry.collisionMissile(entryMissile)) {
						entryMissile.destroy();
						yourMissiles.removeFirst();
						entry.destroyed();
						score += entry.getScore();
						numEnemies--;
					}
				}
				yourCount = 0;
				if (!(entry.hasShipBeenDestroyed())) {
					entry.move(ship);
				}
			}
			for (YourMissiles missile : yourMissiles) {
				yourCount = yourMissiles.indexOf(missile);
				if (missile.onScreen()) {
					missile.move();
				} else {
					yourMissiles.removeFirst();
				}
				yourCount = 0;
			}
			repaint();
			}
	}
		
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		ship.draw(g);
		for (GreenEnemy entry : greenEnemies) {
			entry.draw(g);
		}
		for (RedEnemy entry : redEnemies) {
			entry.draw(g);	
		}
		for (YellowEnemy entry : yellowEnemies) {
			entry.draw(g);
		}
		for (YourMissiles missile : yourMissiles) {
			missile.draw(g);
		}
		
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}
}
