import java.util.Stack;

/**
 *  The history of moves made during a game. The history is maintained by using 
 *  stacks for both the moves made, and the moves undone.
 * @author Aaron Wood (z3372891)
 */
public class MoveHistory {

	//The stack of moves that can be made, which maintains the moves that can be undone.
	private Stack<Move> movesMade;
	//The stack of moves which have been undone, which maintains the moves that can be redone.
	private Stack<Move> movesUndone;
	
	//The amount of moves that are stored in history.
	private static int historySize = 100;
	/**
	 * MoveHistory constructor, allows the history of a game to be maintained and used by Undo and Redo functions.
	 */
	public MoveHistory() {
		movesMade = new Stack<Move>();
		movesUndone = new Stack<Move>();
	}
	
	/**
	 * Undo the last move to be made, removing it from the movesMade stack. 
	 * @return The move to be undone, that is, the last move to be made.
	 */
	public Move undo() {
		Move m = null;
		if(movesMade.size() > 0) {
			m = movesMade.pop();
			ensureHistorySize(movesUndone); //probably change this.
			movesUndone.push(m);
		}
		return m;
	}
	
	/**
	 * Redo a move, gets the last move undone to be redone.
	 * @return The last move that was undone.
	 */
	public Move redo() {
		Move m = null;
		if(movesUndone.size() > 0) {
			m = movesUndone.pop();
			//add move back to the movesMade stack?
			this.addMove(m);
		}
		return m;
		
	}
	
	/**
	 * Add a move to be made to the history.
	 * @param m Move that is being made.
	 */
	public void addMove(Move m) {
		ensureHistorySize(movesMade);
		movesMade.push(m);
	}
	
	/**
	 * Ensures that the amount of moves stored does not exceed the historySize. Removes the
	 * least recent element from the stack.
	 * @param s Which stack to ensure does not go over the history size.
	 */
	private void ensureHistorySize(Stack<Move> s) {
		if(movesMade.size() == historySize) {
			movesMade.removeElementAt(0);
		}
	}
}
