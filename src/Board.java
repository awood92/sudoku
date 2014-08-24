
public class Board {
	/*
	 * Responsible for generating a board filled with Tile
	 * and checks the legality of a move
	 */
   private static final int BOARD_SIZE = 9;
   private Tile[][] board;
	
   public Board() {
		board = constructMatrix();
	}
   
   private Tile[][] constructMatrix() {
      Tile[][] matrix = new Tile[BOARD_SIZE][BOARD_SIZE];
      for(int y = 0; y < BOARD_SIZE; y++) {
         for(int x = 0; x < BOARD_SIZE; x++) {
            matrix[y][x] = new Tile(x, y);
         }
      }
      return matrix;
   }
   
   public Tile getTile(int x, int y) {
      return board[y][x];
   }
   
   /*
    * Ensures row, col and square do not contain
    * the same val
    */
   public boolean isLegal(int col, int row, int val) {
      if(checkRow(row, col, val) && checkColumn(col, row, val) && checkSquare(col, row, val)) {
         return true;
      }
      return false;
   }
   
   /*
    * Ensures row does not contain the same val
    */
   private boolean checkRow(int row, int col, int val) {
		for(int x = 0; x < BOARD_SIZE; x++) {
			if(this.getTile(x, row).getValue() == val && x != col) {
				return false;
			}
		}
		return true;
	}
   
	/*
	 * Ensures col does not contain the same val
	 */
	private boolean checkColumn(int col, int row, int val) {
		for(int y = 0; y < BOARD_SIZE; y++) {
			if(this.getTile(col, y).getValue() == val && y != row) {
				return false;
			}
		}
		return true;
	}
	
	/*
	 * Ensures square does not contain the same val
	 */
	private boolean checkSquare(int col, int row, int val) {
		int xb = col % 3;
		int yb = row % 3;
		
		for(int y = 0; y < (BOARD_SIZE/3); y++) {
			for(int x = 0; x < (BOARD_SIZE/3); x++) {
				if(this.getTile(col-xb + x, row-yb + y).getValue() == val && !(y == yb & x == xb)) {
					return false;
				}
			}
		}
		return true;
	}
	/*
	 * Updates gui for possible values
	 */
	public void generatePossible() {
		for(int y = 0; y < BOARD_SIZE; y++) {
			for(int x = 0; x < BOARD_SIZE; x++) {
				if(this.getTile(x, y).getValue() == 0) {
					this.getTile(x, y).resetTile();
					updateTilePossibilities(this.getTile(x,y));
				}
			}
		}
	}
	/*
	 * @param tile the selected tile
	 */
	private void updateTilePossibilities(Tile tile) {
		for(int i = 0; i < BOARD_SIZE; i++) {
			tile.removePossible(this.getTile(tile.getX(),i).getValue());
			tile.removePossible(this.getTile(i,tile.getY()).getValue());
		}
		
		int xb = tile.getX() % 3;
		int yb = tile.getY() % 3;
		for(int y = 0; y < (BOARD_SIZE/3); y++) {
			for(int x = 0; x < (BOARD_SIZE/3); x++) {
				tile.removePossible(this.getTile(tile.getX()-xb + x, tile.getY()-yb + y).getValue());
			}
		}
	}
	
	/*
	 * creates another copy of board
	 */
	public Board clone() {
		Board b = new Board();
		for(int y = 0; y < 9; y++) {
			for(int x = 0; x < 9; x++) {
				b.getTile(x,y).setValue(this.getTile(x, y).getValue());
			}
		}
		return b;
	}
	
	/*
	 * Prints a representation of the board
	 * to console
	 * @deprecated replaced by BoardGui class
	 */
	public void printBoard() {
		for(int y = 0; y < 9; y++) {
			for(int x = 0; x < 9; x++) {
				System.out.print(board[x][y].getValue());
			}
			System.out.println();
		}
		System.out.println();
	}
}

