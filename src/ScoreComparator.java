import java.util.Comparator;

/**
 * Used to compare players based off time in completing the Sudoku puzzle.
 * @author Blake Ambrose (z3373623)
 */
public class ScoreComparator implements Comparator<Player> {
	
	@Override
	public int compare(Player player1, Player player2) {
		if(player1 == null || player2 == null) {
			return 1;
		} else {
			return (player1.time() - player2.time());
		}
	}
}
