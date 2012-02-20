// %959489154:de.hattrickorganizer.logik.matchengine%
/*
 * MatchPredictionManager.java
 *
 * Created on 22. Dezember 2004, 14:01
 */
package ho.tool.matchPrediction.engine;


import plugins.IMPActions;
import plugins.IMPMatchData;
import plugins.IMPTeamData;
import plugins.IMPTeamRatings;
import plugins.IMatchResult;

import java.util.Vector;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class MatchPredictionManager implements plugins.IMatchPredictionManager {
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
    public IMPMatchData getMatchData(IMPTeamData home, IMPTeamData away) {
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
    public Vector<IMPActions> calculateMatch(IMPTeamData home, IMPTeamData away) {
        final Vector<IMPActions> actions = new Vector<IMPActions>();
        final MatchData matchengine = new MatchData((TeamData) home, (TeamData) away);

        for (int i = 0; i < 91; i++) {
            de.hattrickorganizer.tools.HelperWrapper.instance().copyArray2Vector(matchengine
                                                                                 .advance(), actions);
        }
        return actions;
    }
    
	public IMatchResult calculateMatchResult(IMPTeamData home, IMPTeamData away) {
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
    public IMatchResult calculateNMatches(int numberOfMatches, IMPTeamData home,
                                              IMPTeamData away) {
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
    public IMPTeamData generateTeamData(String name, IMPTeamRatings _ratings, int _tactic,
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
    public IMPTeamRatings generateTeamRatings(double midfield, double leftDef, double middleDef,
                                              double rightDef, double leftAttack,
                                              double middleAttack, double rightAttack) {
        return new TeamRatings(midfield, leftDef, middleDef, rightDef, leftAttack, middleAttack,
                               rightAttack);
    }
}
