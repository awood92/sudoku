/**
 * A move in the Sudoku game, used for making moves as well as the undo and re-do functionality.
 * @author Aaron Wood (z3372891)
 */
public class Move {
	
	
	// The tile that the move is being made on. 
	Tile tile;
	
	//The value that is being placed on the tile.
	private int N;
	//The previous value that was stored on the tile.
	private int previous;
	
	/**
	 * Constructs a move to be used.
	 * @param t The tile that the move is being made on
	 * @param n The value that is being placed on the tile
	 * @param previousValue The previous value of the tile, used for history
	 */
	public Move(Tile t, int n, int previousValue) {
		this.tile = t;
		this.N = n;
		this.previous = previousValue;
	}
	
	/**
	 * Gets the value that is being made by the move.
	 * @return The value of the move
	 */
	public int getN() {
		return N;
	}
	
	/**
	 * Gets the tile that the move is being performed on
	 * @return The tile that the move is being made on
	 */
	public Tile getTile() {
		return tile;
	}
	
	/**
	 * Gets the previous value of the tile. 
	 * @return The previous value of the tile.
	 */
	public int getPrevious() {
		return this.previous;
	}


	
	
}
