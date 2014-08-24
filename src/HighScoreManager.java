import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

/**
 * HighScoreManager.java
 * Manages the highscores for the Sudoku game.
 * @author Blake Ambrose (z3373623)
 */
public class HighScoreManager {
	
	private static Player[][] players;
	private static final String HS_FILE = "highscores.dat";
	private static final int DIFFICULTIES = 4;
	private static final int MAX_PLAYERS = 5;
	
	ObjectInputStream in = null;
	ObjectOutputStream out = null;
	
	/**
	 * Constructs a HighScoreManager object.
	 */
	public HighScoreManager() {
		players = new Player[DIFFICULTIES][MAX_PLAYERS];
		readFromFile();
	}
	
	/**
	 * Checks if a player's time is a new highscore.
	 * @param time is the time taken to complete the puzzle
	 * @param diff is the difficulty of the Sudoku puzzle
	 * @return true if the player has achieved a new highscore, false otherwise
	 */
	public boolean isHighScore(int time, int diff) {
		boolean highscore = false;
		for(int i = 0; (i < MAX_PLAYERS && !highscore); i++) {
			if(players[diff][i] == null) {
				highscore = true;
			} else if(time < players[diff][i].time()) {
				highscore = true;
			}
		}
		return highscore;
	}
	
	/**
	 * Adds a player to the highscore list and writes it to file.
	 * @param player is the player that has achieved the highscore
	 */
	public void addPlayer(Player player) {
		int longest = -1;
		int longestIndex = 0;
		boolean added = false;
		int diff = player.difficulty();
		for(int i = 0; (i < MAX_PLAYERS && !added); i++) {
			if(players[diff][i] == null) {
				players[diff][i] = player;
				added = true;
			} else {
				if(players[diff][i].time() > longest) {
					longest = players[diff][i].time();
					longestIndex = i;
				}
			}
		}
		if(!added) {
			players[diff][longestIndex] = player;
		}
		sort(diff);
		writeToFile();
	}
	
	/**
	 * Reads in highscore data from a .dat file
	 */
	private void readFromFile() {
		try {
			in = new ObjectInputStream(new FileInputStream(HS_FILE));
			players = (Player[][]) in.readObject();
			if(out != null) {
				in.close();
			}
		} catch(Exception e) {
			// print error trace
		} finally {
			try {
				if(out != null) {
					out.flush();
					out.close();
				}
			} catch(IOException e) {}
		}
	}
	
	/**
	 * Writes new highscore data to the .dat file
	 */
	private void writeToFile() {
		try {
			out = new ObjectOutputStream(new FileOutputStream(HS_FILE));
			out.writeObject(players);
		} catch(Exception e) {
			// print error trace
		} finally {
			try {
				if(out != null) {
					out.flush();
					out.close();
				}
			} catch(IOException e) {}
		}
	}
	
	/**
	 * Sorts the players, separated by difficulty and sorted based on time
	 * @param diff is the difficulty that is being sorted
	 */
	private void sort(int diff) {
		ScoreComparator comparator = new ScoreComparator();
		Arrays.sort(players[diff], comparator);
	}
	
	/**
	 * Gets a list of players who have achieved highscores for a specific difficulty.
	 * @return the list of highscore players for that difficutly
	 */
	public Player[] highscores(int diff) {
		return players[diff];
	}
	
	/**
	 * Returns the fastest time for a specific difficulty, formatted as a string
	 * @param diff is the difficulty that is set
	 */
	public String fastestTimeDiff(int diff) {
		if(players[diff][0] != null) {
			return players[diff][0].timeString();
		}
		return null;
	}
}
