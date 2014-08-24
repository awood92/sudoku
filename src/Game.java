/**
 * The Game class is used to maintain each game that is played. 
 * It contains all the functionality required to play interact with the game state.
 *
 */
public class Game {
	//The board of this game.
	private Board board;
	//The solution of this game.
	private Board solution;
	//The helpper used by this game.
	private HelperAI helper;
	//The history of this game.
	private MoveHistory history;
	private Difficulty difficulty;
	/**
	 * Game constructor generates a game based on the difficulty selected in the Options class,
	 * stores its solutions and initialises its history.
	 */
	public Game(Difficulty diff) {
		difficulty = diff;
		
		board = new Board();
		Generator gen = new Generator(board, (int)(Math.random()*1000));
		
		
		board = gen.fill();
		solution = board.clone();
		
		if(diff == Difficulty.EASY) {
			board = gen.digOut(45);
		} else if (diff == Difficulty.MEDIUM) {
			board = gen.digOut(30);
		} else if (diff == Difficulty.HARD) {
			board = gen.digOut(17);
		} else if (diff == Difficulty.EVIL) {
			MainMenu.options.setEvilMode();
			board = gen.digOut(17);
		}
				
		if(MainMenu.options.isEvilMode() & diff != Difficulty.EVIL){
			MainMenu.options.disableEvilMode();
		}

		board.generatePossible();
		
		history = new MoveHistory();
		helper = new HelperAI();
	
	}
	
	/**
	 * Gets the board that is being played on.
	 * @return the board used by this game to play on.
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * Get the difficulty of this game
	 * @return the difficulty of the game
	 */
	public Difficulty getDifficulty() {
		return difficulty;
	}
	/**
	 * Makes a move in the game. Checks are done in order to avoid equal moves to be made,
	 * the history of the game is updated, and the display is updated if necessary.
	 * @param m The move to be made
	 */
	public void makeMove(Move m) {
		
		Tile t = m.getTile();
		int x = t.getX();
		int y = t.getY();
		if(t.isStarter() == false) {
			//Ensure multiple move, equal moves are not made, in order to make full use of the 
			//history stacks, as well as maintain correctness with coloring.
			if(board.getTile(x, y).getValue() != m.getN()) {
					
				t.setValue(m.getN()); 
				board.generatePossible();
				MainMenu.grid.setTile(m.getN());
			
				history.addMove(m);
			}
		}
	}
	
	/**
	 * Undo a move if it can be done.
	 */
	public void undoMove() {
		Move m = history.undo();
		if(m != null) {
			Tile tile = m.getTile();
			tile.setValue(m.getPrevious());
			board.generatePossible();
			MainMenu.grid.setTile(tile.getX(), tile.getY(), m.getPrevious());
		
		}
	}
	
	/**
	 * Redo a move if it can be done.
	 */
	public void redoMove() {
		Move m = history.redo();
		if(m != null) {
			Tile tile = m.getTile();
			tile.setValue(m.getN());
			board.generatePossible();
			MainMenu.grid.setTile(tile.getX(), tile.getY(), m.getN());
			
		}
	}
	
	/**
	 * Get a hint for the current game state. 
	 * @return a tile to be hinted at.
	 */
	public boolean getHint() {
		boolean hintFound = false;
		Tile[] t = helper.getHint(board);
				
		if(t != null) {
			for(int i = 0; i < t.length; i++) {
				MainMenu.grid.giveHint(t[0].getX(), t[0].getY());
				hintFound = true;
			}
		} else {
			hintFound = false;
		}
		return hintFound;
	}
	
	/**
	 * Determines whether the given coordinates contain the correct value.
	 * @param x The x coordinate of the sudoku grid
	 * @param y The y coordinate of the sudoku grid
	 * @return true if the coordinates value is equal to the solution.
	 */
	private boolean isCorrect(int x, int y) {
		return board.getTile(x, y).getValue() == solution.getTile(x,y).getValue();
	}
	
	/**
	 * Gets the correct value of the given coordinate.
	 * @param x The x coordinate of the sudoku grid
	 * @param y The y coordinate of the sudoku grid
	 * @return The correct value of the give coordinate
	 */
	public int getCorrectValue(int x, int y) {
		return solution.getTile(x,y).getValue();
	}
	
	/**
	 * Determines if the game is finished by checking that every tile is correct.
	 * @return true if the game is finished with the correct solution
	 */
	public boolean finished() {
		boolean finished = true;
		for(int y = 0; (y < 9 && finished); y++) {
			for(int x = 0; (x < 9 && finished); x++) {
				if(!isCorrect(x, y)) {
					finished = false;
				}
			}
		}
		return finished;
	}
	
}
