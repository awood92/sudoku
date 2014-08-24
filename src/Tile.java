import java.util.*;

/**
   Tile.java
   Constructs a Tile object, representing a square in the sudoku grid.
*/
public class Tile {
	
   private int value;
   private ArrayList<Integer> possible;
   
   private int x;
   private int y;
   
   private boolean isStarter;
   /**
      Constructs a Tile object.
   */
	public Tile(int X, int Y) {
      possible = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9));
      this.value = 0;
      
      this.x = X;
      this.y = Y;
      
      this.isStarter = false;
	}
	
   
   /**
      Gets the possible values the tile can be.
      @return the possible list of values
   */
   public ArrayList<Integer> getPossible() {
      return possible;
   }
   
   /**
      Remove a value from the possible list
   */
   public void removePossible(int val) {
      possible.remove((Integer)val);
   }
   
   /**
      Add a value from the possible list
   */
   public void addPossible(int val) {
      possible.add(val);
      Collections.sort(possible);
   }
   
   /**
      Gets the value the tile is set to.
      @return the value of the tile
   */
   public int getValue() {
      return value;
   }
   
   /**
      Sets the tile value.
   */
   public void setValue(int val) {
      value = val;
   }
   
   /**
    * Get if the given value is in the possible list.
    * @param n The value to check for.
    * @return True if the value is possible.
    */
	public boolean isPossible(int n) {
		return possible.contains(n);
	}
   /**
    * Resets the tile, clearing all the possible values, as well as the current value.
    */
   public void resetTile() {
		possible.clear();
		possible.addAll(Arrays.asList(1,2,3,4,5,6,7,8,9));
		this.setValue(0);
	}
	
   /**
    * Get the number of possible values that can be used in this tile.
    * @return Size of possible array.
    */
	public int numberPossible() {
		return possible.size(); 
	}
	
	/**
	 * Get the X coordinate of the tile
	 * @return the x coordinate
	 */
	public int getX() {
		return x;
	}
	/**
	 * Gets the Y coordinate of the tile
	 * @return the y coordinate
	 */
	public int getY() {
		return y;
	}


	/**
	 * Returns true if this tile is one of the initial clues of the game, and cannot be changed.
	 * @return Whether the tile is a starting tile.
	 */
	public boolean isStarter() {
		return isStarter;
	}

	/**
	 * Sets if the tile is an initial tile that is given as a clue.
	 * @param isStarter Whether the tile is a starting clue or not.
	 */
	public void setStarter(boolean isStarter) {
		this.isStarter = isStarter;
	}
}

