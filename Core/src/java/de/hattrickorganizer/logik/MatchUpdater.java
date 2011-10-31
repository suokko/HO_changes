// %4165694865:de.hattrickorganizer.logik%
package de.hattrickorganizer.logik;

import java.util.Vector;

import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.model.matches.MatchLineupPlayer;
import de.hattrickorganizer.tools.Helper;

import plugins.IHOMiniModel;
import plugins.IMatchLineup;
import plugins.IMatchLineupPlayer;
import plugins.IMatchLineupTeam;
import plugins.ISubstitution;


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
    	// Why would we want to limit updates to matches where "our" team participates?
        // Matches allows for browsing "foreign" matches, and such a match would be in the db.
        // It needs updating too. - blaghaid

//    	final IMatchDetails matchDetail = model.getMatchDetails(matchId);
//      
//     	final IMatchDetails matchDetail = model.getMatchDetails(matchId);
//        
//       if ((matchDetail.getHeimId() != model.getBasics().getTeamId())
//           && (matchDetail.getGastId() != model.getBasics().getTeamId())) {
//           return;
//        }

        
        
        IMatchLineup lineup = model.getMatchLineup(matchId);
        
        // HOLogger.instance().debug(new Object().getClass(), "Updating match: " + matchId +  " Home: " + lineup.getGastName() + " Away: " + lineup.getHeimName()  );
        
        updateTeamInMatch(lineup.getGast(), matchId);
        updateTeamInMatch(lineup.getHeim(), matchId);
    }
    
    private static void updateTeamInMatch(IMatchLineupTeam team, int matchID) {
    	
    	// Refresh normal players
    	Vector<IMatchLineupPlayer> players = team.getAufstellung();
    	for (int i = 0 ; (i < players.size()) ; i++)  {
    		DBZugriff.instance().updateMatchLineupPlayer( (MatchLineupPlayer)players.get(i), matchID, team.getTeamID());
    	}
    
//    	 Not in 1.431 first dev
//    	// Refresh the starting lineup. They need a 1000 bump in roleID
//    	MatchLineupPlayer tmp = null;
//    	MatchLineupPlayer p = null;
//    	players = team.getStartingPlayers();
//    	for (int i = 0 ; (i < players.size()) ; i++)  {
//    		tmp = new MatchLineupPlayer((MatchLineupPlayer)players.get(i));
//    		tmp.setFieldPos(tmp.getFieldPos()+1000);
//    		DBZugriff.instance().updateMatchLineupPlayer( tmp, matchID, team.getTeamID());
//    	}
//    	
//    	// Store substitutions
//    	ISubstitution[] subs = new ISubstitution[team.getSubstitutions().size()];
//    	Helper.copyVector2Array(team.getSubstitutions(), subs);
//    	DBZugriff.instance().storeMatchSubstitutionsByMatchTeam(matchID, team.getTeamID(), subs);
    }
}
