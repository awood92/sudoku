/**
 * Solver.java
 * Used in solving a Sudoku puzzles and determines whether a Sudoku puzzle has
 * solution, a unique solution or multiple solutions. 
 * 
 * @author Blake Ambrose (z3373623)
 */
public class Solver {
	
   private static final int BOARD_SIZE = 9;
   private Board board1, board2;
   
   /**
    * Constructs a Solver object.
    */
	public Solver() {
		board1 = new Board();
        board2 = new Board();
	}
   
   /**
    * Takes in a board, it attempts to solve it using Backtracking and determines
    * whether the Sudoku puzzle is solvable and unique.
    * @param newBoard is the Sudoku grid being solved 
    * @return true if the board is solvable and is unique, false otherwise
    */
   public boolean solve(Board newBoard) {
      copyBoard(newBoard);
      // If solver does not find a solution on the first run, there is no need for a second pass
      if(!solveFront(0, 0)) {
         return false;
      }
      solveBack(0, 0);
      return compareBoards();
   }
   
   /**
    * Copies the contents of the board into the board variables that are to be solved.
    * @param newBoard is the board that is being copied
    */
   private void copyBoard(Board newBoard) {
      for(int y = 0; y < BOARD_SIZE; y++) {
         for(int x = 0; x < BOARD_SIZE; x++) {
            board1.getTile(x,y).setValue(newBoard.getTile(x,y).getValue());
            board2.getTile(x,y).setValue(newBoard.getTile(x,y).getValue());
         }
      }
   }
   
   /**
    * Compares the two boards and see if they are equal. If so, we have a unique solution.
    * @return true if the boards are equal, false otherwise
    */
   private boolean compareBoards() {
      boolean same = true;
      for(int y = 0; (y < BOARD_SIZE && same); y++) {
         for(int x = 0; (x < BOARD_SIZE && same); x++) {
            if(board1.getTile(x,y).getValue() != board2.getTile(x,y).getValue()) {
               same = false;
            }
         }
      }
      return same;
   }
   
   /**
    * The first pass of the Backtrack solving. Solves the Sudoku grid in ascending
    * number values.
    * @param col is the current column number being tested
    * @param row is the the current row number being tested
    * @return true if a solution is found, false otherwise
    */
   private boolean solveFront(int col, int row) {
      if(col == BOARD_SIZE) {
         col = 0;
         if(++row == BOARD_SIZE) {
            return true;
         }
      }
      
      if(board1.getTile(col, row).getValue() != 0) {
         return solveFront(col+1, row);
      }
      
      for(int val = 1; val <= BOARD_SIZE; ++val) {
         if(board1.isLegal(col, row, val)) {
        	 board1.getTile(col, row).setValue(val);
            if(solveFront(col+1, row)) {
               return true;
            }
         }
      }
      board1.getTile(col, row).setValue(0);
      return false;
   }
   
   /**
    * The second pass of the Backtrack solving. Solves the Sudoku grid in descending
    * number values.
    * @param col is the current column number being tested
    * @param row is the the current row number being tested
    * @return true if a solution is found, false otherwise
    */
   private boolean solveBack(int col, int row) {
      if(col == BOARD_SIZE) {
         col = 0;
         if(++row == BOARD_SIZE) {
            return true;
         }
      }
      
      if(board2.getTile(col, row).getValue() != 0) {
         return solveBack(col+1, row);
      }
      
      for(int val = BOARD_SIZE; val >= 1; --val) {
         if(board2.isLegal(col, row, val)) {
            board2.getTile(col, row).setValue(val);
            if(solveBack(col+1, row)) {
               return true;
            }
         }
      }
      board2.getTile(col, row).setValue(0);
      return false;
   }
}

