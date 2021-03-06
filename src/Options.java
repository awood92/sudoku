import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


/**
 * Options class that creates the panel with all the option buttons on the right hand side and 
 * maintains all the options that can be changed.
 */
//suppressed serial warning as there is no intent to serialize this class.
@SuppressWarnings("serial")
public class Options extends JPanel {
	//The 9 buttons that allow possibilities to be highlighted.
	private static JButton possibleButtons[];
	//The button that clears the highlighted possibilities.
	private static JButton possibleClearButton;
	//The numbers currently being highlighted.
	private static int possibleHighlight;
	//The button that allows deviants from solution to be shown in orange text.
	private static JToggleButton showDeviantsButton;
	//The button that allows errors to be shown in red text.
	private static JToggleButton showErrorButton;
	//The button that allows the possibilities of each blank tile to be filled with the possibilities.
	private static JToggleButton showPossibleButton;
	
	private static boolean showPossible;
	private static boolean showError;
	private static boolean showDeviants;
	private static boolean evilMode;

	/**
	 * Option constructor, creates the option panel on the right hand side and 
	 * initializes all options to off, and difficulty level to easy.
	 */

	public Options() {
		
		
		showPossible = false;
		showError = false;
		showDeviants = false;
		evilMode = false;
		possibleHighlight = 0;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new EmptyBorder(20, 20, 20, 20) );
		JLabel pLabel = new JLabel("Possibilities");
		pLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(pLabel);
		
		add(Box.createRigidArea(new Dimension(0,10)));		
		add(createNumberButtons());
		
		add(Box.createRigidArea(new Dimension(0,10)));	
		possibleClearButton = createPossibleClearButton();
		add(possibleClearButton);
		
		add(Box.createRigidArea(new Dimension(0,50)));
		add(createPossibleText());
		
		add(Box.createRigidArea(new Dimension(0,10)));
		add(createShowErrors());
		
		add(Box.createRigidArea(new Dimension(0,10)));
		add(createShowDeviants());
		
		add(Box.createRigidArea(new Dimension(0,10)));
		add(createDifficulties());

		
	}

	/**
	 * Creates the JPanel containing the 4 difficulty buttons.
	 * @return The JPanel to be placed at the bottom of the options panel.
	 */
	private static JPanel createDifficulties() {
		
		JPanel newGamePanel = new JPanel();
		JButton easyButton = new JButton("Easy");
		
		easyButton.addActionListener(new ActionListener()
		         {
		            public void actionPerformed(ActionEvent event)
		            {
	
		            	MainMenu.newGame(Difficulty.EASY);
		            	 
		            }
		         });
		
		JButton mediumButton = new JButton("Medium");
		mediumButton.addActionListener(new ActionListener()
		         {
		            public void actionPerformed(ActionEvent event)
		            {
		           
		            	
		            	MainMenu.newGame(Difficulty.MEDIUM);
		            }
		         });
		
		JButton hardButton = new JButton("Hard");
		hardButton.addActionListener(new ActionListener()
		         {
		            public void actionPerformed(ActionEvent event)
		            {
		            	 
		       
		            	 MainMenu.newGame(Difficulty.HARD);
		            }
		         });
		
		JButton evilButton = new JButton("Evil");
		evilButton.addActionListener(new ActionListener()
		         {
		            public void actionPerformed(ActionEvent event)
		            {
		            	 
		            	MainMenu.newGame(Difficulty.EVIL);
		            }
		         });
			
		GridBagLayout layout = new GridBagLayout();
		newGamePanel.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 0;
		JLabel l = new JLabel("Difficulty");
		l.setHorizontalAlignment(SwingConstants.CENTER);
		newGamePanel.add(l, c);

		c.gridy++;
		c.gridwidth = 1;
		newGamePanel.add(easyButton,c);
		c.gridx++;
		newGamePanel.add(mediumButton, c);
		c.gridy++;
		c.gridx--;
		newGamePanel.add(hardButton, c);
		c.gridx++;
		newGamePanel.add(evilButton, c);
		c.gridy++;
		c.gridx = 0;
		c.gridwidth = 2;

		return newGamePanel;
	}
	
	/**
	 * Creates the Show Errors button, which displays errors made in red text.
	 * Note: Errors meaning errors in the current state, ie, Two 5's in the same square. 
	 * Does not necessarily mean differences with the solution.
	 * @return The Toggle button that allows the option of errors to be shown.
	 */
	private static JToggleButton createShowErrors() {
		showErrorButton = new JToggleButton("Show Errors");
		
		showErrorButton.addActionListener(new ActionListener()
		         {
		            public void actionPerformed(ActionEvent event)
		            {
		            	 
		            	 showError = !showError;
		            	 if(MainMenu.game != null) {
		                    MainMenu.grid.updateColors();
		            	 }
		            	
		            	
		            	 MainMenu.mainFrame.requestFocus();
		            }
		         });
		showErrorButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		return showErrorButton;
	}

	/**
	 * Creates the Show Deviants button, which displays deviants from the solution in orange text.
	 * @return The Toggle button that allows the option of deviants to be shown.
	 */
	private static JToggleButton createShowDeviants() {
		showDeviantsButton = new JToggleButton("Show Deviant");
		
		showDeviantsButton.addActionListener(new ActionListener()
		         {
		            public void actionPerformed(ActionEvent event)
		            {
		            	 
		            	 showDeviants = !showDeviants;
		            	 if(MainMenu.game != null) {
		                    MainMenu.grid.updateColors();
		            	 }
		            	 
		            	
		            	 MainMenu.mainFrame.requestFocus();
		            }
		         });
		showDeviantsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		return showDeviantsButton;
	}
	
	/**
	 * Creates the button that allows the possibilities to be displayed as text in each empty tile. 
	 * @return The toggle button that allows the option of displaying possibilities to be changed.
	 */
	private static JToggleButton createPossibleText() {
		showPossibleButton = new JToggleButton("Show Possibilities");
		
		showPossibleButton.addActionListener(new ActionListener()
		         {
		            public void actionPerformed(ActionEvent event)
		            {
		            	 
		            	 JToggleButton toggle = (JToggleButton)event.getSource();	
		            	 showPossible = !showPossible;
		            	 if(MainMenu.game != null) {
		            		 if(toggle.isSelected()) {
		            			 MainMenu.grid.textPossible();
		            		 } else {
		            			 MainMenu.grid.removeTextPossible();
		            		 }
		            	 }
		            
		            	 MainMenu.mainFrame.requestFocus();
		            }
		         });
		showPossibleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		return showPossibleButton;
	}
	
	/**
	 * Creates the buttons from 1 to 9, that allows the highlighting of possibilities.
	 * @return The JPanel that contains the 9 buttons that allow highlighting.
	 */
	private static JPanel createNumberButtons() {
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(3,3));
		
		//Array size 10 to allow the clear button to be added at index 0, 
		//Simplifies the enabling and highlighting of buttons, based on selection.
		possibleButtons = new JButton[10];
		
		for (int i = 1; i <= 9; i++) {
			possibleButtons[i] = new JButton((i) + "");
			createActionListener(possibleButtons[i], i);
			buttons.add(possibleButtons[i]);
		}
		return buttons;
	}
	
	/**
	 * Creates the button that allows the highlighting of possibilities to be cleared.
	 * @return The button that clears highlighting.
	 */
	private static JButton createPossibleClearButton() {
		final JButton button = new JButton("Clear");
		button.setEnabled(false);
		possibleButtons[0] = button;
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(MainMenu.game != null) {
					MainMenu.grid.highlightPossible(0);
					MainMenu.mainFrame.requestFocus();
				}
				possibleButtons[possibleHighlight].setEnabled(true);
				possibleButtons[possibleHighlight].setBackground(null);
				possibleHighlight = 0;
				button.setEnabled(false);
			}
		});
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		return button;
	}
	
	/**
	 * Creates the action listener for a number buttons that allow highlighting.
	 * @param button The button that the action listener is being added to.
	 * @param number The number that is to be highlighted by the button.
	 */
	private static void createActionListener(final JButton button, final int number) {
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				if(MainMenu.game != null) { 
					MainMenu.grid.highlightPossible(number);	
					MainMenu.mainFrame.requestFocus();
				}
				
				possibleButtons[possibleHighlight].setEnabled(true);
				possibleButtons[possibleHighlight].setBackground(null);
				button.setEnabled(false);
				button.setBackground(Color.cyan);
				
				possibleHighlight = number;
				
				possibleClearButton.setEnabled(true);
			}
		});
	}
	
	/**
	 * Returns true if the option to show possibilities as text is enabled.
	 * @return true is possibilities are to be shown.
	 */
	public boolean isShowPossible() {
		return showPossible;
	}
	
	/**
	 * Get the number that is being highlighted. 0 if none are being highlighted.
	 * @return the number being highlighted.
	 */
	public int getPossibleHighlight() {
		return possibleHighlight;
	}
	
	/** 
	 * Return true if errors are being shown as red text.
	 * @return Whether errors are being shown.
	 */
	public boolean isShowError() {
		return showError;
	}
	
	/**
	 * Returns true if deviants are being shown as orange text.
	 * @return Whether deviants are being shown.
	 */
	public boolean isShowDeviants() {
		return showDeviants;
	}
	
	
	/**
	 * Set the options panel up for evil mode. This disables all helper buttons apart from hint and turns them
	 * off.
	 */
	public void setEvilMode() {
		
		for(JButton b : possibleButtons) {
			b.setEnabled(false);
		}
		possibleClearButton.setEnabled(false);
		possibleClearButton.setSelected(false);
		
		possibleHighlight = 0;
		showDeviantsButton.setEnabled(false);
		showDeviantsButton.setSelected(false);
		
		showErrorButton.setEnabled(false);
		showErrorButton.setSelected(false);
		
		showPossibleButton.setEnabled(false);
		showPossibleButton.setSelected(false);
		
		showPossible = false;
		showError = false;
		showDeviants = false;
		evilMode = true;
	}
	
	/**
	 * Disables Evil mode. This enables all the helper buttons.
	 */
	public void disableEvilMode() {
		
		for(JButton b : possibleButtons) {
			b.setEnabled(true);
		}
		possibleClearButton.setEnabled(true);
		possibleHighlight = 0;
		showDeviantsButton.setEnabled(true);
		showErrorButton.setEnabled(true);
		showPossibleButton.setEnabled(true);
		evilMode = false;
	}
	
	/**
	 * Return whether evil mode is currently on.
	 * @return true is evil mode is on, false otherwise.
	 */
	public boolean isEvilMode() {
		return evilMode;
	}
}
