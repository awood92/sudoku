import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * The GUI of the board. This class is only generated once and reused for every game.
 * @author Aaron Wood
 */
public class BoardGui {

		//The panels used for each tile in the grid.
        private static JPanel[][] panels;
        //The text labels used to display the number of each tile.
        private static JLabel[][] labels;
        
        //The panel of the tile that is currently selected
        private static JPanel selected;
        //The text label of the tile that is currently selected
        private static JLabel selectedTile;
        //The coordinates of the selected tile
        private static int selectedX;
        private static int selectedY;
        
        //The panel of the tile that is being hinted at
        private static JPanel hintedPanel;
        //The default font used for tiles that have values in them.
        private static Font f = new Font("San Serif", Font.PLAIN, 26);
        
        /**
         * BoardGui constructor.
         */
        public BoardGui() {
                panels = new JPanel[9][9];
                labels = new JLabel[9][9];
        }
        
        /**
         * Generates the GUI used for the board, contained within a JPanel
         * @return The JPanel that contains the entire board.
         */
    public JPanel generateBoardGUI() {               
            	
           JPanel myPanel = new JPanel();
           //Make the board panel a 3x3 in order to store 9 smaller 3x3 grids, in order
           //to achieve the effect of thicker borders around each 3x3 square.
           myPanel.setLayout(new GridLayout(3,3));
                        
           int windowWidth = 600;
           int windowHeight = 600;
           myPanel.setBounds(200, 200, windowWidth, windowHeight);
                
           for(int i = 0; i < 9; i++) { 
    	   		//Create the smaller JPanel to be used for a 3x3 square of tiles
               JPanel box = new JPanel();
               box.setLayout(new GridLayout(3,3));
               
               box.setBounds(50,50, 200, 200);
               box.setBorder(BorderFactory.createLineBorder(Color.black, 2));
               //Add the 9 tiles to the JPanel box.
               for(int j = 0; j < 9; j++) {
            	   	   //The x and y coordinates for the entire 9x9 grid.
                       int gridx = i % 3 * 3 + j % 3; 
                       int gridy = i / 3 * 3 + j / 3; 
                       panels[gridx][gridy] = createTile(50);
                       labels[gridx][gridy] = createLabel();
                       panels[gridx][gridy].add(labels[gridx][gridy]);
                       addListener(panels[gridx][gridy], gridx, gridy);
                       box.add(panels[gridx][gridy]);
               }
               myPanel.add(box);
                   
        }
           
        return myPanel;
    }
    /**
     * Create a single tile of the grid.
     * @param size The size of the tile.
     * @return The JPanel for the created tile.
     */
    private JPanel createTile(int size) {
                
        JPanel temp = new JPanel();

        temp.setBackground(Color.white);
        temp.setMinimumSize(new Dimension(size, size));
        temp.setMaximumSize(new Dimension(size, size));
        temp.setPreferredSize(new Dimension(size, size));
        temp.setBorder(BorderFactory.createLineBorder(Color.black, 1));

        return temp;
    }
    

    /**
     * Add a mouseListener to a tile.
     * @param panel The panel that the listener is being added to
     * @param x The x coordinate of the tile
     * @param y The y coordinate of the tile
     */
    private void addListener(final JPanel panel, final int x, final int y) {
        panel.addMouseListener(new
                 MouseAdapter()
                 {
                    public void mousePressed(MouseEvent event)
                    {
                       setSelected(panel, x, y);
                       
                    }
                    
                 });    
        

    }
    
    /**
     * Creates a label to be used for a board tile. 
     * @return The label that is created.
     */
    private JLabel createLabel() {
        JLabel label = new JLabel("");
        label.setPreferredSize(new Dimension(40,40)); //change to make variable
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(f);
        return label;
    }
    
    /**
     * Set the tile to a given value
     * @param x The x coordinate of the tile being set
     * @param y The y coordinate of the tile being set
     * @param number The number that the tile is being set to
     */
    public void setTile(int x, int y, int number) {
	   if(number > 0) {
       	   labels[x][y].setFont(f);
           labels[x][y].setText(number + "");
       } else {
           labels[x][y].setText("");
       }
           
	   //If show possibilities is enabled, update the possibilities text.
       if(MainMenu.options.isShowPossible()) {
    	   textPossible();
       }
       	
       //If the highlighting of a numbers possible tiles is selected, update highlighting.
       int possibleHighlight = MainMenu.options.getPossibleHighlight();
       if(possibleHighlight > 0) {
    	   highlightPossible(possibleHighlight);
       }
       	
       updateColors();
        
    }
    
    /**
     * Set the selected tile to the given value.
     * @param number The value to be set
     */
    public void setTile(int number) {
        if(number > 0) {
        	selectedTile.setFont(f);
            selectedTile.setText(number + "");
        } else {
            selectedTile.setText("");
        }
        
      //If show possibilities is enabled, update the possibilities text.
    	if(MainMenu.options.isShowPossible()) {
    		textPossible();
    	}
    	
    	//If the highlighting of a numbers possible tiles is selected, update highlighting.
    	int possibleHighlight = MainMenu.options.getPossibleHighlight();
    	if(possibleHighlight > 0) {
    		highlightPossible(possibleHighlight);
    	}
    	
    	highlightNumber(number);
    	selected.setBorder(BorderFactory.createLineBorder(Color.yellow, 3));
        updateColors();
    }
    
    /**
     * Updates the colors of the text. This depends on the game options set, and the state of the game.
     */
    public void updateColors() {
	   for(int y = 0; y < 9; y++) {
           for(int x = 0; x < 9; x++) {
               int value = MainMenu.game.getBoard().getTile(x, y).getValue();
               //If there is a value in the tile
               if(value > 0) {
            	   //and it is not a tile that cannot be updated
            	   if(!MainMenu.game.getBoard().getTile(x,y).isStarter()) { 
            		   //If the tile conflicts with another in its row, column or square
            		   //and the option to show errors is selected, then make the text red.
		               if (!MainMenu.game.getBoard().isLegal(x,  y, value) && 
		            		   MainMenu.options.isShowError() == true) {
		                     labels[x][y].setForeground(Color.red);
		                     
		               //If the tile doesn't conflict or show errors is not selected, and the value is not the 
		               //correct value for the solution, AND show deviants is selected, then make the text orange.
		               } else if (value !=  MainMenu.game.getCorrectValue(x, y) && 
		            		   MainMenu.options.isShowDeviants() == true) {
		                     labels[x][y].setForeground(Color.orange);
		                     
		               //Otherwise, make the text black.
		               } else {	            	   
		                     labels[x][y].setForeground(Color.black);
		               }
            	   }
	              
	           }
    	   }
	   }
    }
    	 
    /**
     * Setup the visual display of the board when the game is started.
     * @param b The board to set up.
     */
    public void setupBoard(Board b) {
        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 9; x++) {
                int value = b.getTile(x, y).getValue();
                setTile(x, y, value);
                if(value == 0) {
                        labels[x][y].setForeground(Color.black);
                } else {
                        labels[x][y].setForeground(Color.blue); 
                        labels[x][y].setFont(f);
                        b.getTile(x,y).setStarter(true);
                }
            }
        }
    }

    /**
     * Set the tile that is selected.
     * @param panel The panel that is selected 
     * @param x The X coordinate of the selected tile
     * @param y The y coordinate of the selected tile
     */
    private void setSelected(JPanel panel, int x, int y) {
        if(selected != null) {
        	if(hintedPanel != null) {
        		if(selected == hintedPanel) {
        			giveHint(selectedX, selectedY);
        		} else {
        			selected.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        		}
        	} else {
        		selected.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        	}
        }
        
        selected = panel;
        selectedTile = labels[x][y];
        selectedX = x;
        selectedY = y;
        if(labels[x][y].getText().length() == 1) {
        	highlightNumber(Integer.parseInt(labels[x][y].getText()));
        } else {
        	removeHighlightNumber();
        }
        panel.setBorder(BorderFactory.createLineBorder(Color.yellow, 3));
        
    }
    
    /**
     * Get the X coordinate of the selected tile
     * @return the X coordiante of the selected tile
     */
    public int getSelectedX() {
        return selectedX;
    }
    
    /**
     * Gets the Y coordinate of the selected tile
     * @return the Y coordinate of the selected tile
     */
    public int getSelectedY() {
        return selectedY;
    }
    
    /**
     * Give a hint at the given location
     * @param x The x coordate that the hint is given to.
     * @param y The y coordinate that the hint is given to.
     */
    public void giveHint(int x, int y) {
        panels[x][y].setBorder(BorderFactory.createLineBorder(Color.pink, 3	));
        hintedPanel = panels[x][y];
    }
    
    /**
     * Move the selected tile up by one if there is a tile above the current selected
     */
    public void moveSelectedUp() {
    	if(selectedY > 0) {
    		setSelected(panels[selectedX][selectedY-1], selectedX, selectedY-1);
    	}
    }
    /**
     * Move the selected tile down by one if there is a tile below the current selected
     */
    public void moveSelectedDown() {
    	if(selectedY < 8) {
    		setSelected(panels[selectedX][selectedY+1], selectedX, selectedY+1);
    	}
    }
    
    /**
     * Move the selected tile left by one if there is a tile to the left of the current selected
     */
    public void moveSelectedLeft() {
    	if(selectedX > 0) {
    		setSelected(panels[selectedX-1][selectedY], selectedX-1, selectedY);
    	}
    }
    
    /**
     * Move the selected tile right by one if there is a tile to the right of the the current selected
     */
    public void moveSelectedRight() {
    	if(selectedX < 8) {
    		setSelected(panels[selectedX+1][selectedY], selectedX+1, selectedY);
    	}
    }
    
    /**
     * Highlight the border of each tile that has a value the same as the selected tile. Highlights none if 
     * the selected tile does not have a value.
     * @param number The value of the selected tile.
     */
    public void highlightNumber(int number) {
    	for(int y = 0; y < 9; y++) {
    		for(int x = 0; x < 9; x++) {
    			if(labels[x][y].getText().length() == 1) {   			
	    			if(Integer.parseInt(labels[x][y].getText()) == number) {
	    				panels[x][y].setBorder(BorderFactory.createLineBorder(Color.green, 1));
	    			} else {
	    				panels[x][y].setBorder(BorderFactory.createLineBorder(Color.black, 1));
	    			}
    			}
    		}
    	}
    }
    
    /**
     * Highlight the squares with the possibility of being the given number in the games current state
     * @param number The number to highlight.
     */
    public void highlightPossible(int number) {
    	Board board = MainMenu.game.getBoard();
    	for(int y = 0; y < 9; y++) {
    		for(int x = 0; x < 9; x++) {   			
    			if(board.getTile(x, y).isPossible(number) && board.getTile(x,y).getValue() == 0) {
    				panels[x][y].setBackground(Color.cyan);
    			} else {
    				panels[x][y].setBackground(Color.white);
    			}	
    		}
    	}
    }
    
    /**
     * Removes all the highlighted borders.
     */
    public void removeHighlightNumber() {
    	for(int y = 0; y < 9; y++) {
    		for(int x = 0; x < 9; x++) {  
    			if(labels[x][y].getText().length() == 1) {
    				panels[x][y].setBorder(BorderFactory.createLineBorder(Color.black, 1));			
    			}
    		}
    	}
    }
    
    /**
     * Display the possibilities of each square as text within the tile. 
     */
    public void textPossible() {
    	Board board = MainMenu.game.getBoard();
    	//reduce font size in order to fit all numbers cleanly.
    	Font f = new Font("San Serif", Font.PLAIN, 12);
    	for(int y = 0; y < 9; y++) {
    		for(int x = 0; x < 9; x++) {   			
    			//Only set the text possibilities if the tile has no value
    			if(board.getTile(x,y).getValue() == 0) {
    				//The text of the tile uses html tags in order to allow multiple lined JLabel, 
    				//as well as forced spacing in order to maintain consistency of each number. 
    				//ie, a 5 will allows be in the centre of the tile, as it is the middle number
    				//no matter what other numbers are possible
    				String text = "<html>";
    				for(int i = 1; i <= 9; i++) {
    					if(board.getTile(x,y).isPossible(i)) {
    						text = text + i + "&nbsp;&nbsp;";
    					} else {
    						text = text + "&nbsp;&nbsp;&nbsp;&nbsp;";
    					}
    					if((i % 3) == 0) {
    						text = text + "<br>";
    					}
    				}
    				text = text + "</html>";
    				labels[x][y].setFont(f);
    				labels[x][y].setText(text);
    				labels[x][y].setForeground(Color.black);
    			}
    		}
    	}
    }
    
    /**
     * Remove all the text possibilities.
     */
    public void removeTextPossible() {
    	Board b = MainMenu.game.getBoard();
    	for(int y = 0; y < 9; y++) {
    		for(int x = 0; x < 9; x++) {  
    			if(b.getTile(x, y).getValue() == 0) {
    				labels[x][y].setText("");
    			}
    		}
    	}
    }
    

}
