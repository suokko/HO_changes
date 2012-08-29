// %959489154:de.hattrickorganizer.logik.matchengine%
/*
 * MatchPredictionManager.java
 *
 * Created on 22. Dezember 2004, 14:01
 */
package ho.core.prediction.engine;


import ho.core.util.Helper;

import java.util.Vector;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class MatchPredictionManager {
    //~ Static fields/initializers -----------------------------------------------------------------

    private static MatchPredictionManager m_clInstance;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of MatchPredictionManager
     */
    private MatchPredictionManager() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static MatchPredictionManager instance() {
        if (m_clInstance == null) {
            m_clInstance = new MatchPredictionManager();
        }

        return m_clInstance;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param home TODO Missing Method Parameter Documentation
     * @param away TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public MatchData getMatchData(TeamData home, TeamData away) {
        return new MatchData((TeamData) home, (TeamData) away);
    }

    /**
     * calculates a match ( 90 minutes ) and returns list of events for both teams.
     *
     * @param home TODO Missing Constructuor Parameter Documentation
     * @param away TODO Missing Constructuor Parameter Documentation
     *
     * @return Vector holding IMPActions for that match
     */
    public Vector<Action> calculateMatch(TeamData home, TeamData away) {
        final Vector<Action> actions = new Vector<Action>();
        final MatchData matchengine = new MatchData((TeamData) home, (TeamData) away);

        for (int i = 0; i < 91; i++) {
        	Helper.copyArray2Vector(matchengine.advance(), actions);
        }
        return actions;
    }
    
	public MatchResult calculateMatchResult(TeamData home, TeamData away) {
		final MatchData matchengine = new MatchData((TeamData) home, (TeamData) away);
		MatchResult result = new MatchResult();
		result.addActions(matchengine.simulate());
		return result;
	}    

    /**
     * calculates a number of matches match ( 90 minutes ) and returns list of events for both
     * teams.
     *
     * @param numberOfMatches TODO Missing Constructuor Parameter Documentation
     * @param home TODO Missing Constructuor Parameter Documentation
     * @param away TODO Missing Constructuor Parameter Documentation
     *
     * @return vector contaning Vectors holding IMPActions for each match
     */
    public MatchResult calculateNMatches(int numberOfMatches, TeamData home,
                                              TeamData away) {
		final MatchData matchengine = new MatchData((TeamData) home, (TeamData) away);
		MatchResult result = new MatchResult();		
        for (int i = 0; i < numberOfMatches; i++) {			        	
			result.addActions(matchengine.simulate());
        }
		return result;

    }

    /**
     * TODO Missing Method Documentation
     *
     * @param name TODO Missing Method Parameter Documentation
     * @param _ratings TODO Missing Method Parameter Documentation
     * @param _tactic TODO Missing Method Parameter Documentation
     * @param _level TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public TeamData generateTeamData(String name, TeamRatings _ratings, int _tactic,
                                        int _level) {
        return new TeamData(name, (TeamRatings) _ratings, _tactic, _level);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param midfield TODO Missing Method Parameter Documentation
     * @param leftDef TODO Missing Method Parameter Documentation
     * @param middleDef TODO Missing Method Parameter Documentation
     * @param rightDef TODO Missing Method Parameter Documentation
     * @param leftAttack TODO Missing Method Parameter Documentation
     * @param middleAttack TODO Missing Method Parameter Documentation
     * @param rightAttack TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public TeamRatings generateTeamRatings(double midfield, double leftDef, double middleDef,
                                              double rightDef, double leftAttack,
                                              double middleAttack, double rightAttack) {
        return new TeamRatings(midfield, leftDef, middleDef, rightDef, leftAttack, middleAttack,
                               rightAttack);
    }
}
