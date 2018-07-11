package kings;

public class KingPlacementApp {

	/**Constructor
	 * @param N, the size of the board
	 * @param k, the number of kings that have already been placed
	 * @param cols, an array that includes the columns where k kings have been placed
	 * Assumption: the placement of k kings is valid. 
	 * In other words, abs(cols[i]-cols[i-1]) > 1, for 1<=i<=N-1.
	 */
	 public KingPlacementApp(final int N, final int k, final int[] cols) {
	        if (N < 1 || N > 16) {
	            throw new IllegalArgumentException("N must be between 1 and 16");
	        }
	        if (k < 0 || k > N) {
	        	throw new IllegalArgumentException("k must be between 0 and N");
	        }
	        if(k != cols.length) {
	        	throw new IllegalArgumentException("the size of cols must be equal to k");
	        }
	        final KingPlacementView kingPlacementView = new KingPlacementView(N,k,cols);
	        final KingPlacementPresenter kingPlacementPresenter = new KingPlacementPresenter(kingPlacementView);
	        kingPlacementPresenter.start();
	    }

	    public static void main(String... args) {
	    	int[] cols = {0,2,4};
	        new KingPlacementApp(6,3,cols);
	    }
}
