package de.hattrickorganizer.gui.lineup;

import plugins.ISpieler;
import de.hattrickorganizer.gui.Updateable;
import de.hattrickorganizer.model.Lineup;
import de.hattrickorganizer.model.HOVerwaltung;

public class SwapPositionsManager {

	private final Updateable parentPanel;
	private SwapPositionFeature swapCandidate = null;

	public SwapPositionsManager(Updateable parentPanel) {
		this.parentPanel = parentPanel;
	}

	boolean hasSwapCandidate() {
		return getSwapCandidate() != null;
	}

	SwapPositionFeature getSwapCandidate() {
		return swapCandidate;
	}

	void swapWithCandidate(SwapPositionFeature swapPositionA) {
		SwapPositionFeature swapPositionB = getSwapCandidate();

		if (positionsAreSwapable(swapPositionA, swapPositionB)) {
			swapPositionsInLineup(swapPositionA, swapPositionB);
			unmarkSwapCandidate();
			updateGui(swapPositionA, swapPositionB);
		}
	}

	private void updateGui(SwapPositionFeature swapPositionA,
			SwapPositionFeature swapPositionB) {
		swapPositionA.unpressButton();
		swapPositionB.unpressButton();
		parentPanel.update();
	}

	private boolean positionsAreSwapable(SwapPositionFeature swapPositionA,
			SwapPositionFeature swapPositionB) {
		return swapPositionA.getPositionsID() != swapPositionB.getPositionsID();
	}

	private void swapPositionsInLineup(SwapPositionFeature swapPositionA,
			SwapPositionFeature swapPositionB) {
		Lineup lineup = HOVerwaltung.instance().getModel()
				.getAufstellung();

		int positionA = swapPositionA.getPositionsID();
		int positionB = swapPositionB.getPositionsID();
		ISpieler playerA = lineup.getPlayerByPositionID(positionA);
		ISpieler playerB = lineup.getPlayerByPositionID(positionB);
		
		// Changed to allow swapping players to empty positions
		int playerA_id = -1;
		int playerB_id = -1;
		
		if  (playerA != null) {
			playerA_id = playerA.getSpielerID();
		}
		
		if  (playerB != null) {
			playerB_id = playerB.getSpielerID();
		}
		
		lineup.setSpielerAtPosition(positionA, playerB_id);
		lineup.setSpielerAtPosition(positionB, playerA_id);
	}

	void markAsSwapCandidate(SwapPositionFeature swapPositionFeature) {
		swapCandidate = swapPositionFeature;
	}

	void unmarkSwapCandidate() {
		swapCandidate = null;
	}

	/**
	 * Initializes the swap feature for this player position.
	 * 
	 * @param swapPositionsManager
	 *            the {@link SwapPositionsManager} to register this position to.
	 */
	public void addSwapCapabilityTo(PlayerPositionPanel spielerPositionsPanel) {
		SwapPositionFeature swapPositionFeature = new SwapPositionFeature(
				spielerPositionsPanel, this);
	}
}
