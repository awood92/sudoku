import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;


/**
 * Generator for generating a new game.
 * @author Aaron Wood (z3372891)
 *
 */
public class Generator {
	private Board b;
	private Random random;
	
	//The hashmap that maps each tile to a HashSet of integers that are the values
	//that have been tried when filling the board.
	HashMap<Tile, HashSet<Integer>> tried;
	
	/**
	 * Generator constructor 
	 * @param board The board that a valid game is to be generated on.
	 * @param seed The seed that the random number generator is to use.
	 */
	public Generator(Board board, int seed) {
		tried = new HashMap<Tile, HashSet<Integer>>();
		for(int y = 0; y < 9; y++) {
			for(int x = 0; x < 9; x++) {
				tried.put(board.getTile(x, y), new HashSet<Integer>());
			}
		}
		this.b = board;
		random = new Random(seed);
	}
	/**
	 * Fills the board with a complete, valid grid.
	 * @return the filled board.
	 */
	public Board fill() {
		//loop over every coordinate in the grid.
		int i = 0;
		while(i < 81) {
			//Get the x and y coordinate of the index.
			int x = i%9;
			int y = i/9;
		
			//Set the value of the current tile to 0, necessary in the case of backtracking.
			b.getTile(x, y).setValue(0);
			//while there is a number left to try in the current tile
			while(numberTried(x,y) < 9) {
				int n = getRandomNumber();
				if(!isTried(x, y, n)) {
					tryNumber(x, y, n);
					if(b.isLegal(x, y, n)) {
						b.getTile(x,y).setValue(n);
						break;
					}
				}
			}
			//if there are no more numbers to try
			if(numberTried(x, y) == 9) {
				//move back a space
				i--;
				resetTile(x, y);
				b.getTile(x, y).setValue(0);
			} else {
				i++;
			}
		}
		return b;
	}
	
	/**
	 * Digs out random tiles, ensuring each time that there is only one, unique solution 
	 * @param numberOfClues The number of clues that will remain.
	 * @return the board with tiles taken out
	 */
	public Board digOut(int numberOfClues) {
		//loop through the number of tiles to be taken out.
		for(int i = 0; i < 81-numberOfClues; i++) {
			int x, y;
			//The number of tiles that have been tried to dig. 
			//This is incremented each time the tile cannot be taken out due to the
			//formation of multiple solutions, and reset to 0 each time a tile is taken.
			int triedToDig = 0;
			
			while(true) {
				int n = getRandomIndex();
				//The coordinates of the random value
				x = n % 9;
				y = n / 9;
				
				//Continue with this tile only if it is not empty.
				if(!(b.getTile(x, y).getValue() == 0)) {
					triedToDig++;
					//store the old value in case the tile has to be refilled.
					int oldValue = b.getTile(x, y).getValue();
					
					b.getTile(x, y).setValue(0);
					
					Solver solver = new Solver();
				 	//break if the tile can be taken out without multiple solutions forming.
					if(solver.solve(b)) {
				    	break;
				    } else {
				    	//Re-add the tiles value and continue with the loop to find the next tile to dig.
				    	b.getTile(x,y).setValue(oldValue);
				    }
				 	
					//If the number of tiles that have been tried to dig is equal to the number of tiles on the 
					//board (81-i), then return the board, as it cannot be reduced any further without 
					//adding previous tiles in in order to reduce further. Speeds up the generation in order to not
					//require backtracking to increase responsiveness.
				 	if(triedToDig == 81-i) {
				 		return b;
				 	}
				}
			}
		}
		return b;
	}
	
	/**
	 * Get a random value that is valid in the game of sudoku, ie, 1-9
	 * @return a random value between 1 and 9
	 */
	private int getRandomNumber() {
		return (int) (random.nextFloat() * 10) % 9 + 1;
	}
	
	/**
	 * Get a random index in the sudoku grid, as an integer between 0 and 80.
	 * @return an index between 0 and 80.
	 */
	private int getRandomIndex() {
		return (int) (random.nextFloat() * 81);
	}

	
	/**
	 * Try a number for the given tile coordinate when generating a game. 
	 * @param x The x coordinate of the tile
	 * @param y The y coordinate of the tile 
	 * @param n The value to try.
	 */
	public void tryNumber(int x, int y, int n) {
		tried.get(b.getTile(x, y)).add(n); 
	}
	
	/**
	 * Determines whether the value has been tried in the given tile.
	 * @param x The x coordinate of the tile
	 * @param y The y coordinate of the tile
	 * @param n The value to test
	 * @return
	 */
	public boolean isTried(int x, int y, int n) {
		return tried.get(b.getTile(x, y)).contains(n);
	}
	
	/**
	 * Reset the given tiles list of tried numbers.
	 * @param x the x coordinate of the tile
	 * @param y the y coordinate of the tile
	 */
	public void resetTile(int x, int y) {
		tried.get(b.getTile(x, y)).clear();
	}
	
	/**
	 * The number of values tried for the gi ven coordinate
	 * @param x the x coordinate of the tile
	 * @param y the y coordinate fo the tile
	 * @return
	 */
	public int numberTried(int x, int y) {
		return tried.get(b.getTile(x, y)).size(); 
	}	
	
		
}
