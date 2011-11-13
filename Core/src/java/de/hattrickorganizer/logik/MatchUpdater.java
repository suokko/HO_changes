// %4165694865:de.hattrickorganizer.logik%
package de.hattrickorganizer.logik;

import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.model.matches.MatchLineup;

import plugins.IHOMiniModel;
import plugins.IMatchLineup;

/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class MatchUpdater {
	
    /**
     * Updates the lineups of the given match with the contents of the provided model.
     * 
     * 553ified and totally changed during that process by Blaghaid.
     *
     * @param model TODO Missing Method Parameter Documentation
     * @param matchId TODO Missing Method Parameter Documentation
     */
    public static void updateMatch(IHOMiniModel model, int matchId) {
        
        IMatchLineup lineup = model.getMatchLineup(matchId);
        DBZugriff.instance().updateMatchLineup((MatchLineup) lineup);
    }
}
