import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * The Main class of the sudoku program, contains the main JFrame used to play.
 */
public class MainMenu {
	
	//the grid gui, this is static, and is reused for all games
	public static BoardGui grid;
	//the main frame used to play the game
	public static JFrame mainFrame;
	//the current game being played
	public static Game game;
	public static Options options;
	private static Clock clock;
	private static Timer timer;
	private static HighScoreManager hm;
	private static HighScoreGui hsGui;
	
	private static JLabel timerLabel = new JLabel("00:00:00");
	public static JLabel msgLabel = new JLabel("Press Start Game");
	private static JButton highScore = new JButton("High Scores");
	private static JPanel gridPanel;
	/**
	 * The main function of the Sudoku game. Sets up the GUI ready to start a new game
	 * @param args
	 */
	public static void main(String[] args) {
		
		hm = new HighScoreManager();
		mainFrame = new JFrame("Sudoku");
		mainFrame.add(createButtons(), BorderLayout.NORTH);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		grid = new BoardGui();
		gridPanel = grid.generateBoardGUI();
		mainFrame.add(gridPanel, BorderLayout.CENTER);
		addKeyListener(mainFrame);
		
		mainFrame.add(createStatusPanel(), BorderLayout.SOUTH);
		
		options = new Options();
		mainFrame.add(options, BorderLayout.EAST);
		mainFrame.setFocusable(true);
		mainFrame.pack();

		mainFrame.setVisible(true);

		hsGui = new HighScoreGui(hm);
		clock = new Clock();
		timer = new Timer();
		timer.schedule(new ClockTicker(), 0, 250);
	}
	    
	/**
	 * Increments the clock if the game is not paused.
	 */
	private static class ClockTicker extends TimerTask {
		public void run() {
			if (!clock.paused() && game != null) {
				timerLabel.setText(clock.time());
				timerLabel.repaint();
			}
		}
	}

	/**
	 * Add a key listener to the main frame. Listens for number keys for board input, 
	 * delete keys for removing values, and arrow keys for changing the currently selected tiles.
	 * @param frame The main frame for the key listener to be added to
	 */
	private static void addKeyListener(JFrame frame) {
		frame.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				int c = event.getKeyCode();
				if (c == KeyEvent.VK_LEFT | c == KeyEvent.VK_A) {
					grid.moveSelectedLeft();
				} else if (c == KeyEvent.VK_RIGHT | c == KeyEvent.VK_D) {
					grid.moveSelectedRight();
				} else if (c == KeyEvent.VK_UP | c == KeyEvent.VK_W) {
					grid.moveSelectedUp();
				} else if (c == KeyEvent.VK_DOWN | c == KeyEvent.VK_S) {
					grid.moveSelectedDown();
				} else if (c >= KeyEvent.VK_0 && c <= KeyEvent.VK_9) {
				 																
					//48 = ascii offset for 0-9			
					if(game != null) {											
						Tile selected = game.getBoard().getTile(
								grid.getSelectedX(), grid.getSelectedY());
						
						Move m = new Move(selected, c - 48, selected.getValue());
						game.makeMove(m);
						
						if (game.finished()) {
							msgLabel.setText("Game Completed!");
							clock.stop();
							hsGui.display(clock.seconds(), game.getDifficulty().ordinal());
						}
					}
				} else if (c == KeyEvent.VK_DELETE
						| c == KeyEvent.VK_BACK_SPACE) {
					Tile selected = game.getBoard().getTile(
							grid.getSelectedX(), grid.getSelectedY());
					Move m = new Move(selected, 0, selected.getValue()); 
					game.makeMove(m);
				}
			}

		});
	}
	public static void newGame(Difficulty diff) {
		game = new Game(diff);
    	grid.setupBoard(game.getBoard());
    	if(options.isShowPossible()) {
    		grid.textPossible();
    	}
    	
    	int possibleHighlight = options.getPossibleHighlight();
    	
    	if(possibleHighlight > 0) {
    		MainMenu.grid.highlightPossible(possibleHighlight);
    	} else {
    		MainMenu.grid.highlightPossible(0);
    	}
    	
    	clock.reset();
	    clock.start();
	    msgLabel.setText("Time to beat: " + hm.fastestTimeDiff(1));
    	mainFrame.requestFocus();
	}
	/**
	 * Create the main buttons along the top of the main GUI and places them in a JPanel
	 * @return The JPanel containing the buttons along the top of the GUI.
	 */
	private static JPanel createButtons() {
	   
	      JButton undoButton = new JButton("Undo");
	      undoButton.addActionListener(new
	         ActionListener()
	         {
	            public void actionPerformed(ActionEvent event)
	            {
	            	
	            	if(game != null) {
	            		game.undoMove();
	            	}
	            	mainFrame.requestFocus();
	            }
	         });
	      
	      JButton redoButton = new JButton("Redo");
	      redoButton.addActionListener(new
	         ActionListener()
	         {
	            public void actionPerformed(ActionEvent event)
	            {
	            	if(game != null) {
	            		game.redoMove();
	            	}
	            	mainFrame.requestFocus();
	            }
	         });
	      
	      JButton resetButton = new JButton("Reset");
	      resetButton.addActionListener(new
	    		  ActionListener()
	      	{
	    	  public void actionPerformed(ActionEvent event)
	    	  {
	    		  for (int i = 0; i < 1000; i++) {
	    			  if(game != null) {
		    			  game.undoMove();
		    		  }  
	    		  }
	    		  
	    		  mainFrame.requestFocus();
	    	  }
	      	});
	      
	      JButton hintButton = new JButton("Hint");
	      hintButton.addActionListener(new
	         ActionListener()
	         {
	            public void actionPerformed(ActionEvent event)
	            {
	            	if(game != null) {
	            		if(game.getHint()) {
	            			clock.penalty();
	            		}
	            	}
	            	mainFrame.requestFocus();
	            
	            }
	         });
	      
	      JButton helpButton = new JButton("Help");
	      helpButton.addActionListener(new
	         ActionListener()
	      	{
	    	   public void actionPerformed(ActionEvent event)
	    	   {
	    		   new HelpPage();
	    	   }
	      	});
	     
	      JPanel buttons = new JPanel();
	     
	      buttons.add(undoButton);
	      buttons.add(redoButton);
	      buttons.add(resetButton);
	      buttons.add(hintButton);
	      buttons.add(helpButton);
	      
	      return buttons;
	}


	/**
	 * Creates the status panel, used to display the current time of the game.
	 * @return The status panel
	 */
	private static JPanel createStatusPanel() {
		JPanel status = new JPanel(new BorderLayout());

		status.add(timerLabel, BorderLayout.NORTH);
		status.add(msgLabel, BorderLayout.WEST);
		status.add(highScore, BorderLayout.SOUTH);
		
		//Initialise
		timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timerLabel.setFont(new Font("San Serif", Font.PLAIN, 30));
		highScore.addActionListener(new
		         ActionListener()
		         {
		            public void actionPerformed(ActionEvent event)
		            {
		            	//show highscore
		            	hsGui.show();
		            	hsGui.requestFocus();
		            }
		         });

		return status;
	}

}