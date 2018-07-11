package kings;

import java.util.Arrays;

public class KingPlacementPresenter implements KingPlacementListener{

	private final KingPlacementView view;//Holds the state of the board at any moment
    private boolean[][] canPlace;//For any square, tells us whether we can place a king there at any moment.
    private int score = 0;//Holds the number of possible king placements/permutations.

    /**
     * Constructor
     * @param view, an instance of KingPlacementView, holds the state of the board
     */
    public KingPlacementPresenter(final KingPlacementView view) {
    	if (view == null) {
            throw new IllegalArgumentException("view cannot be null");
        }
        this.view = view;
        this.view.addListener(this);//Add this instance to the listeners of the view.
        this.canPlace = new boolean[view.getN()][view.getN()];
        
	    viewStateChanged();//Updates the canPlace array
    }
    
    public void start() {
    	//If k==N (with the assumption that the k kings have been properly placed),
    	//there is no need to do anything. The kings have been placed across the entire board,
    	//and that is the only possible placement.
    	if(view.getk() == view.getN()) {
    		System.out.println("The number of possible placements is 1");
    	}
    	else {
    		int currentRow = view.getk();   	
        	while(true) {
        		//Holds whether the current row of the board has no more spots to place a king.
        		//True meaning there are no spots and false meaning there are 1 or more spots.
        		boolean noMoreSpots = true;        		
        		//Checks the current row and places a king if there is a spot
        		for(int col=0; col<view.getN(); col++) {
        			if(canPlace[currentRow][col]) {
        				noMoreSpots = false;//because now we found a spot
        				view.getKingMove(currentRow, col).perform();//places the king
        				currentRow++;//Moves on to the next row
        				break;//No need to check the rest of this row since we found a spot.
        			}        			
        		}
        		if(currentRow == view.getN()) {//In other words, if all the kings have been placed
        			score++;//Because we found a valid king placement
        			view.undoLast();//Undo the last move so we can look for more placements
        			currentRow--;//Go to the previous row
        			continue;
        		}
        		if(noMoreSpots) {//If we cannot place a king in the current row
        			if(currentRow == view.getk() && view.addedKingsLength() == 0) {
        				//if the current row is k, then we have backtracked 
        				//all the way to where we started and we are finished.
        				System.out.println("The number of possible placements is "+score);
                		break;
        			}
        			else {
        				view.undoLast();//Otherwise, undo the last move to look for more placements
        				view.fillWasRemoved(currentRow, false);
        				currentRow--;//Go to the previous row     				
        			}
        		}        		       		
        	}
    	} 
    	
    }
    
    /**
     * Returns whether a king array contains a king in a specific column
     * @param array the king array to search
     * @param element the column to search for
     * @return true if the array contains a king whose aColumn property is equal to element
     * false otherwise
     */
    private boolean contains(King[] array, int element) {
    	for(int i=0; i<array.length; i++) {
    		if(array[i].getColumn() == element) {
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * This method updates the canPlace boolean array by accessing the state of the KingPlacementView.
     * Called after initializing the class fields in the constructor,
     * and whenever a move is performed or undone. 
     */
    public void viewStateChanged() {
    	// sets false array
	    for (int row = 0; row < view.getN(); row++) {
	        Arrays.fill(canPlace[row], false);
	    }
	    
	    King[] occupiedCols = view.getColumns();
    	King[] addedKings = view.getAddedKings();
    	int currentRow = view.getk()+view.addedKingsLength();
    	
	    if(currentRow < view.getN()) {
	    		int lastOccupiedCol;
	    		if(currentRow == 0) {
	    			lastOccupiedCol = -100;
	    		}
	    		else if(addedKings.length == 0) {
		    		lastOccupiedCol = occupiedCols[occupiedCols.length-1].getColumn();
		    	}
		    	else {
		    		lastOccupiedCol = addedKings[addedKings.length-1].getColumn();
		    	}
			    for(int col = 0; col < view.getN(); col++) {		    	
			    	if(col < lastOccupiedCol-1 || col > lastOccupiedCol + 1) {
			    		if(!contains(occupiedCols,col) && !contains(addedKings,col) && !view.wasRemoved(currentRow,col)) {
			    			canPlace[currentRow][col] = true;
			    		}
			    	}
			    }   	
	    }
    }
}
