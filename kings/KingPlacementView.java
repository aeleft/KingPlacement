package kings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class KingPlacementView {

	private final int N;
	private final int k;
	private final King[] aColumns;//Holds the first k kings that have been placed.
	private Stack<King> addedKings = new Stack<>();//Holds the additional kings that will be placed.
	private Stack<Move> aMoves = new Stack<>();//Holds the moves that are performed.
	private List<KingPlacementListener> aListeners = new ArrayList<>();
	// Holds the Listeners that are interested in the state of the view. In our case,
	//it's only the KingPlacementPresenter that will be interested.
	private boolean[][] wasRemoved;
	//For each square on the board, 
	//this array tells us whether a king has been placed and then removed from that square.

	/**
	 * Constructor
	 * @param N, the size of the board
	 * @param k, the number of kings that have already been placed
	 * @param pColumns, an array that includes the columns where k kings have been placed
	 */
    public KingPlacementView(int N, int k, int[] pColumns) {
    	if (N < 1 || N > 16) {
            throw new IllegalArgumentException("N must be between 1 and 16");
        }
        if (k < 0 || k > N) {
        	throw new IllegalArgumentException("k must be between 0 and N");
        }
        if(k != pColumns.length) {
        	throw new IllegalArgumentException("the size of cols must be equal to k");
        }
        this.N = N;
        this.k = k;
        aColumns = new King[pColumns.length];
        for(int i=0; i<aColumns.length; i++) {
        	if(pColumns[i] >= this.N || pColumns[i] < 0) {
        		throw new IllegalArgumentException("each king position must be between 0 and "+(this.N-1));
        	}
        	aColumns[i] = new King(pColumns[i]);     	
        }
        wasRemoved = new boolean[this.N][this.N];
        for(int i=0; i<wasRemoved.length; i++) {
        	Arrays.fill(wasRemoved[i], false);
        }        
    }
    
    public int getN() {
    	return N;
    }
    
    public int getk() {
    	return k;
    }
    
    /**
     * Getter method.
     * @return a copy of aColumns
     */
    public King[] getColumns() {
    	return Arrays.copyOf(aColumns,aColumns.length);    			
    }
    
    /**
     * Getter method. Copied addedKings to a King array and
     * @return that array
     */
    public King[] getAddedKings() {
    	return addedKings.toArray(new King[addedKings.size()]);
    }
    
    public int addedKingsLength() {
    	return addedKings.size();
    }
    
    /**
     * Tells us whether a King has been placed and then removed from a specific square on the board.
     * @param row, the row of the square we are interested in
     * @param col the column of the square we are interested in
     * @return whether a King has been placed and then removed from that square.
     */
    public boolean wasRemoved(int row, int col) {
    	return wasRemoved[row][col];
    }
    
    /**
     * Fills an entire row of the wasRemoved array with the boolean value, b.
     * @param row the row we want to fill
     * @param b, the value we want to fill the row with.
     */
    public void fillWasRemoved(int row, boolean b) {
    	Arrays.fill(wasRemoved[row], b);
    }
    
    /**
	 * Registers an observer for the state of the KingPlacementView.
	 * @param pListener A listener to register.
	 */
    
	public void addListener(KingPlacementListener pListener)
	{
		aListeners.add(pListener);
	}
	
	private void notifyListeners()
	{
		for( KingPlacementListener listener : aListeners )
		{
			listener.viewStateChanged();
		}
	}
    
	/**
	 * Undoes the last move.
	 */
	public void undoLast()
	{
		if( !aMoves.isEmpty() )
		{
			aMoves.pop().undo();
		}
	}
	
	/**
	 * A move that involves placing a King on a square.	 *
	 */
	private class KingMove implements Move
	{
		private final int aRow;
		private final int aColumn;
		
		/**
		 * Constructor
		 * @param pRow the row of the square where the king will be placed
		 * @param pColumn the column of the square where the king will be placed
		 */
		KingMove(int pRow, int pColumn)	{
			aRow = pRow;
			aColumn = pColumn;
		}
		
		@Override
		public void perform()
		{
			addedKings.add(new King(aColumn));
			//Creates a king at the given column, and adds it to the stack.		
			aMoves.push(this);
			//Adds the move
			notifyListeners();
			//Updates the canPlace boolean array in the KingPlacementPresenter
		}

		@Override
		public void undo()
		{
			addedKings.pop();//Removes the king from the stack.
			wasRemoved[aRow][aColumn] = true;
			//Marks that a king has been removed from the square [aRow][aColumn]
			notifyListeners();
			//Updates the canPlace boolean array in the KingPlacementPresenter
		}
	}
	
	/**
	 * Getter method for the KingMove class since it is a private class
	 * @param row, the row to be passed as a parameter to the new KingMove instance
	 * @param col, the column to be passed as a parameter to the new KingMove instance
	 * @return a new instance of KingMove with parameters row and col.
	 */
	public Move getKingMove(int row, int col) {
		return new KingMove(row, col);
	}
    
}
