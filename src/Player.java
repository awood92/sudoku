import java.io.Serializable;

/**
 * Player.java
 * Represents a player that currently holds a highscore in the game.
 * @author Blake Ambrose (z3373623)
 */
public class Player implements Serializable {
	
	private String name;
	private int difficulty, time;
	
	/**
	 * Constructs a player object
	 * @param n is the name of the player
	 * @param dif is the difficulty of the completed Sudoku puzzle
	 * @param t is the time take to complete the Sudoku puzzle
	 */
	public Player(String n, int dif, int t) {
		this.name = n;
		this.difficulty = dif;
		this.time = t;
	}
	
	/**
	 * Gets the player's name
	 * @return the player's name
	 */
	public String name() {
		return this.name;
	}
	
	/**
	 * Gets the difficulty of the completed puzzle
	 * @return the difficulty of the completed puzzle
	 */
	public int difficulty() {
		return this.difficulty;
	}
	
	/**
	 * Gets the time taken for the player to completed the Sudoku puzzle
	 * @return the time taken to complete the Sudoku puzzle
	 */
	public int time() {
		return time;
	}
	
	/**
	 * Formats the time to a string format for display
	 * @return the formatted time
	 */
	public String timeString() {
		return String.format("%02d:%02d:%02d",(time/3600),((time/60)%60),(time%60)); 
	}
}
