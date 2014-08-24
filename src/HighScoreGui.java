import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class HighScoreGui extends JFrame {

	private JFrame hsFrame;
	private JPanel scorePanel;
	private HighScoreManager hm;
	private final String[] difficulties = new String[]{"EASY", "MEDIUM", "HARD", "EVIL"};
	/**
	 * HighScoreGui.java
	 * Displays different components such as
	 * labels, buttons and dialogue boxes for the
	 * HS gui
	 * @param hsm HighScoreManager object to generate the gui
	 */
	public HighScoreGui(HighScoreManager hsm) {
		hm = hsm;

		// Displays HighScore Header. Might remove and replace with image background
		JLabel titleLabel = new JLabel("Highscore", JLabel.CENTER);
		titleLabel.setFont(new Font("San Serif", Font.PLAIN, 26));
		titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		scorePanel = new JPanel();
		createLabels();
		scorePanel.setLayout(new GridLayout(0, 2, 5, 5));
		scorePanel.setBorder(BorderFactory.createEmptyBorder(10, 90, 0, 0));
		
		hsFrame = new JFrame("High Score");
		hsFrame.add(titleLabel, BorderLayout.NORTH);
		hsFrame.add(scorePanel, BorderLayout.CENTER);
		hsFrame.add(createButtons(), BorderLayout.SOUTH);
		hsFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		hsFrame.setSize(400, 700);
		hsFrame.setResizable(false);
	}
	/**
	* Displays High Score gui
	* used for main menu button
	*/
	public void show() {
		hsFrame.setVisible(true);
	}

	/**
	* Display high score dialogue box if player
	* manages to earn a place in high scores
	*/
	public void display(int time, int difficulty) {
		if (hm.isHighScore(time, difficulty)) {
			String name = (String) JOptionPane.showInputDialog(hsFrame,
					"High Score! \n Please enter your name below:",
					"High Score", JOptionPane.DEFAULT_OPTION);
			if (name != null) {
				if (!name.equals("")) {
					hm.addPlayer(new Player(name, difficulty, time));
				}
			}
		}
		scorePanel.removeAll();
		createLabels();
		scorePanel.revalidate();
		scorePanel.repaint();
		hsFrame.setVisible(true);
	}
	/**
	* Closing the HS gui window
	* @return close button
	*/
	private JPanel createButtons() {
		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				hsFrame.dispose();
			}
		});
		JPanel buttons = new JPanel();
		buttons.add(closeButton);
		return buttons;
	}
	/**
	* Creates labels for each player
	* and their times
	*/
	private void createLabels() {
		int counter;
		for(int i = 0; i < difficulties.length; i++) {
			counter = 0;
			scorePanel.add(difficultyLabel(difficulties[i]));
			scorePanel.add(new JLabel());
			scorePanel.add(headerLabel("Player"));
			scorePanel.add(headerLabel("Time"));
			for (Player player : hm.highscores(i)) {
				if(player != null) {
					scorePanel.add(fieldLabel(player.name()));
					scorePanel.add(fieldLabel(player.timeString()));
					counter++;
				}
			}
			for (int j = counter; j <= 5; j++) {
				scorePanel.add(new JLabel());
				scorePanel.add(new JLabel());
			}
		}
	}
	
	/**
	 * Creates a difficulty label
	 * @param difficulty the difficulty
	 * @return a label containing the difficulty
	 */
	private JLabel difficultyLabel(String difficulty) {
		JLabel label = new JLabel(difficulty);
		label.setFont(new Font("San Serif", Font.BOLD, 15));
		return label;
	}

	private JLabel headerLabel(String header) {
		JLabel label = new JLabel(header);
		label.setFont(new Font("San Serif", Font.BOLD, 13));
		return label;
	}

	private JLabel fieldLabel(String text) {
		JLabel label = new JLabel(text, JLabel.LEFT);
		label.setFont(new Font("San Serif", Font.PLAIN, 11));
		return label;
	}

}