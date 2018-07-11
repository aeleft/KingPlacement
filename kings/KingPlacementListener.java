package kings;

/**
 * Observer interface to be implemented by classes whose
 * objects are interested in being notified of a change
 * in the state of the KingPlacementView.
 */
public interface KingPlacementListener {
	/**
	 * Called whenever the state of the 
	 * KingPlacementView changes.
	 */
	void viewStateChanged();
}
