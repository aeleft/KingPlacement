package kings;

/**
 * This class is just a wrapper for an integer
 * that represents the column where a king is placed. *
 */
public class King {
	private int aColumn;
	
	public King(int pColumn) {
		aColumn = pColumn;
	}
	
	public int getColumn() {
		return aColumn;
	}
}
