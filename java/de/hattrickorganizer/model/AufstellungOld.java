// %2598623053:de.hattrickorganizer.model%
/*
 * Aufstellung.java
 *
 * Created on 20. März 2003, 14:35
 */
package de.hattrickorganizer.model;

import plugins.ISpieler;

/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public final class AufstellungOld extends Aufstellung implements plugins.ILineUp {
    //~ Static fields/initializers -----------------------------------------------------------------

	private ISpieler[] players = new ISpieler[11];

    /**
     * TODO Missing Method Documentation
     *
     * @param positionsid TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final plugins.ISpieler getPlayerByPositionID(int positionsid) {
    	return players[positionsid-1];
    }

	public void setSpieler(int position, byte taktic, ISpieler player) {
		if (position>11) {
			return;
		}
		setSpielerAtPosition(position,player.getSpielerID(),taktic);
		players[position-1]=player;
		
	}

}
