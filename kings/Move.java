package kings;

/**
 * Represents one possible action.
 */
public interface Move
{
	/**
	 * Performs the move.
	 */
	void perform();
	
	/**
	 * Undoes the move by reversing
	 * its effect.
	 */
	void undo();
}