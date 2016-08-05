

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Game implements Runnable {
	public void run() {
		// TODO Auto-generated method stub
		
		
		// Frame for whole game
		final JFrame frame = new JFrame("JavaGalaga");
		frame.setLocation(1000, 1000);
		
		// Panels to display status, number of lives
		// stage number and score
		// also high score
		final JPanel status_panel = new JPanel();

		
		frame.add(status_panel, BorderLayout.SOUTH);

		
		String statusText = "Running...\n" +
						" Lives: \n" +
						" Stage: \n" +
						" Score: \n" +
						" High Score: ";
		
		final JLabel status = new JLabel(statusText);

		
		status_panel.add(status);
		
		// For the actual game
		// This will need to be edited...a lot
		final GalagaEngine playfield = new GalagaEngine(status);
		frame.add(playfield, BorderLayout.CENTER);
		
		// Reset Button, plus implementation
		final JPanel control_panel = new JPanel();
		frame.add(control_panel, BorderLayout.NORTH);
		final JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playfield.reset();
			}
		});
		
		
		// Player 1 button. Starts the game with single player
		
		final JButton singlePlayer = new JButton("1 Player");
		singlePlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playfield.reset();
			}
		});
		
		
		// Player 2 button. Starts the game with two players
		
		/*final JButton doublePlayer = new JButton("2 Player");
		doublePlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playfield.reset();
			}
		});*/
		
		final JButton instructions = new JButton("Instructions");
		instructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playfield.showInstructions();
			}
		});
		
		//Add buttons to panel
		control_panel.add(reset);
		control_panel.add(singlePlayer);
		control_panel.add(instructions);
		//control_panel.add(doublePlayer);
		
		// Put frame on the screen
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		// Start game
		playfield.reset();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Game());
	}

}
