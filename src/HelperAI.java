import java.util.*;

/**
 * HelperAI.java
 * This is a logical AI that helps the user identify possible candidate cells they should
 * focus on to progress onwards with a sudoku puzzle.
 * @author Blake Ambrose (z3373623)
 */
public class HelperAI {

	private final static int BOARD_SIZE = 9;
	
	/**
	 * Finds a possible hint for the user
	 * @param board is the current state of the board
	 * @return the tiles that compose of a single hint
	 */
	public Tile[] getHint(Board board) {
		Tile[] tile = null;
		if((tile = nakedSingle(board)) != null) {
		} else if((tile = hiddenSingle(board)) != null) {
		} else if((tile = nakedPair(board)) != null) {}
		return tile;
	}

	/***********************************************************
	 * NAKED SINGLE -
	 * A cell in the Sudoku grid that has only one candidate.
	 **********************************************************/

	/**
	 * Initiates the search for a naked single on the current board
	 * state.
	 * @param board is the current board state being searched
	 * @return the tile that has only one possible candidate left
	 */
	private Tile[] nakedSingle(Board board) {
		boolean found = false;
		Tile[] single = null;

		for(int y = 0; (y < BOARD_SIZE && !found); y++) {
			for(int x = 0; (x < BOARD_SIZE && !found); x++) {
				if(board.getTile(x,y).numberPossible() == 1 && board.getTile(x,y).getValue() == 0) {
					single = new Tile[]{board.getTile(x,y)};
					found = true;
				}
			}
		}
		return single;
	}

	/***********************************************************
	 * HIDDEN SINGLE -
	 * A cell in the Sudoku grid that has a unique candidate in
	 * either its row, column or region.
	 **********************************************************/

	/**
	 * Searches through all squares and finds a tile that has a hidden single.
	 * @param board is the current board state being searched
	 * @return the tile that has a hidden single present
	 */
	private Tile[] hiddenSingle(Board board) {
		boolean found = false;
		Tile[] single = null;

		for(int y = 0; (y < BOARD_SIZE && !found); y++) {
			for(int x = 0; (x < BOARD_SIZE && !found); x++) {
				if(board.getTile(x,y).getValue() == 0) {
					if(hiddenSingleAll(board, x, y)) {
						System.out.println("test");
						single = new Tile[]{board.getTile(x,y)};
						found = true;
					}
				}
			}
		}
		return single;
	}

	/**
	 * For a specific tile, searches its region, column and row and determines whether
	 * it has a hidden single present.
	 * @param board is the current board state being searched
	 * @param col is the column number of the tile
	 * @param row is the row number of the tile
	 * @return true if the tile has a hidden single, false otherwise
	 */
	private boolean hiddenSingleAll(Board board, int col, int row) {
		boolean found = false;
		if(hiddenSingleBox(board, col, row)) {
			found = true;
		} else if(hiddenSingleRowCol(board, col, row)) {
			found = true;
		}
		return found;
	}

	/**
	 * Eliminates candidates in other tiles in a region from the current tile
	 * being searched. If one candidate remains, then the tile has a hidden single present.
	 * @param board is the current board state being searched
	 * @param col is the column number of the tile
	 * @param row is the row number of the tile
	 * @return true if the tile has a hidden single in a region, false otherwise
	 */
	private boolean hiddenSingleBox(Board board, int col, int row) {
		boolean found = false;

		ArrayList<Integer> reduce = new ArrayList<Integer>(board.getTile(col,row).getPossible());

	
		for(int y = 0; y < (BOARD_SIZE/3); y++) {
			for(int x = 0; x < (BOARD_SIZE/3); x++) {
				for(Integer val : board.getTile(x,y).getPossible()) {
					reduce.remove((Integer)val);
				}
			}
		}
		if(reduce.size() == 1) {
			found = true;
		}
		return found;
	}
	
	/**
	 * Eliminates candidates in other tiles from the current tile in both its row and column.
	 * If either results in one candidate remaining, then the tile has a hidden single.
	 * @param board is the current board state being searched
	 * @param col is the column number of the tile
	 * @param row is the row number of the tile
	 * @return true if the tile has a hidden single in a row or column, false otherwise
	 */
	private boolean hiddenSingleRowCol(Board board, int col, int row) {
		boolean found = false;

		ArrayList<Integer> rowReduced = new ArrayList<Integer>(board.getTile(col,row).getPossible());
		ArrayList<Integer> colReduced = new ArrayList<Integer>(rowReduced);
		for(int i = 0; i < BOARD_SIZE; i++) {
			for(Integer val : board.getTile(i,row).getPossible()) {
				rowReduced.remove((Integer)val);
			}
			for(Integer val : board.getTile(col,i).getPossible()) {
				colReduced.remove((Integer)val);
			}
		}
		if(rowReduced.size() == 1 || colReduced.size() == 1) {
			found = true;
		}
		return found;
	}

	/***********************************************************
	 * NAKED PAIR -
	 * Two cells in a Sudoku grid that share the same two unique
	 * candidates with one another in either a row, column or
	 * region.
	 **********************************************************/
	
	/**
	 * Searches through all squares and finds a tile that has a naked pair.
	 * @param board is the current board state being searched
	 * @return the tiles that form a naked pair
	 */
	private Tile[] nakedPair(Board board) {
		boolean found = false;
		Tile[] pair = null;
		Tile tile;

		for(int y = 0; (y < BOARD_SIZE && !found); y++) {
			for(int x = 0; (x < BOARD_SIZE && !found); x++) {
				if(board.getTile(x,y).getValue() == 0) {
					if(board.getTile(x,y).getPossible().size() == 2) {
						if((tile = nakedPairAll(board, x, y)) != null) {
							pair = new Tile[]{board.getTile(x,y),tile};
							found = true;
						}
					}

				}

			}
		}
		return pair;
	}
	
	/**
	 * For a specific tile, searches its region, column and row and determines whether
	 * it forms part of a naked pair.
	 * @param board is the current board state being searched
	 * @param col is the column number of the tile
	 * @param row is the row number of the tile
	 * @return the tile that completes the naked pair
	 */
	private Tile nakedPairAll(Board board, int col, int row) {
		Tile tile = null;
		if((tile = nakedPairBox(board, col, row)) !=  null) {
		} else if((tile = nakedPairRowCol(board, col, row)) != null) {}
		return tile;
	}

	/**
	 * For a specific tile, searches its region and determines whether there exists a tile
	 * that completes the naked pair.
	 * @param board is the current board state being searched
	 * @param col is the column number of the tile
	 * @param row is the row number of the tile
	 * @return the tile that completes the naked pair
	 */
	private Tile nakedPairBox(Board board, int col, int row) {
		Tile tile = null;

		for(int y = 0; (y < (BOARD_SIZE/3) && tile != null); y++) {
			for(int x = 0; (x < (BOARD_SIZE/3) && tile != null); x++) {
				if(board.getTile(col,row).getPossible().equals(board.getTile(x,y).getPossible())) {
					tile = board.getTile(x, y);
				}
			}
		}
		return tile;
	}
	
	/**
	 * For a specific tile, searches its row and column and determines whether there exists a tile
	 * that completes the naked pair.
	 * @param board is the current board state being searched
	 * @param col is the column number of the tile
	 * @param row is the row number of the tile
	 * @return the tile that completes the naked pair
	 */
	private Tile nakedPairRowCol(Board board, int col, int row) {
		Tile tile = null;

		for(int i = 0; (i < BOARD_SIZE && tile != null); i++) {
			if(board.getTile(col,row).getPossible().equals(board.getTile(i,row).getPossible())) {
				tile = board.getTile(i, row);
			} else if(board.getTile(col,row).getPossible().equals(board.getTile(col,i).getPossible())) {
				tile = board.getTile(col, i);
			}
		}
		return tile;
	}
	
}
