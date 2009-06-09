// %1127326956384:plugins%
/*
 * IMatchPredictionManager.java
 *
 * Created on 22. Dezember 2004, 14:09
 */
package plugins;

import java.util.Vector;


/**
 * Manager for the Matchprediction. Use IGUI.createMatchPredictionPanel to show a Matchprediction
 *
 * @author thomas.werth
 */
public interface IMatchPredictionManager {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * get MatchSimulation Engine
     *
     * @param home TODO Missing Constructuor Parameter Documentation
     * @param away TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public IMPMatchData getMatchData(IMPTeamData home, IMPTeamData away);

    /**
     * calculates a match ( 90 minutes ) and returns list of events for both teams.
     *
     * @param home TODO Missing Constructuor Parameter Documentation
     * @param away TODO Missing Constructuor Parameter Documentation
     *
     * @return Vector holding IMPActions for that match
     */
    public Vector<IMPActions> calculateMatch(IMPTeamData home, IMPTeamData away);

    /**
     * calculates a number of matches match ( 90 minutes ) and returns list of events for both
     * teams.
     *
     * @param numberOfMatches TODO Missing Constructuor Parameter Documentation
     * @param home TODO Missing Constructuor Parameter Documentation
     * @param away TODO Missing Constructuor Parameter Documentation
     *
     * @return IMatchResult object 
     */
    public IMatchResult calculateNMatches(int numberOfMatches, IMPTeamData home, IMPTeamData away);

    /**
     * DOCUMENT ME!
     *
     * @param name TODO Missing Constructuor Parameter Documentation
     * @param _ratings create an IMPTeamRatings with genereateTeamRatings()
     * @param _tactic choosen tactic for team, like IMatchDetails.TAKTIK_NORMAL
     * @param _level level from 1-20. You can use the constants from ISpieler: ISpieler.gut
     *
     * @return TODO Missing Return Method Documentation
     */
    public IMPTeamData generateTeamData(String name, IMPTeamRatings _ratings, int _tactic,
                                        int _level);

    /**
     * DOCUMENT ME!
     *
     * @param midfield to be filled with values from 1-80. You can use Constants from ISpieler 4 +
     *        Subskill:  ISpieler.gut  4 + 0.25 for 7.25 -> good low
     * @param leftDef TODO Missing Constructuor Parameter Documentation
     * @param middleDef TODO Missing Constructuor Parameter Documentation
     * @param rightDef TODO Missing Constructuor Parameter Documentation
     * @param leftAttack TODO Missing Constructuor Parameter Documentation
     * @param middleAttack TODO Missing Constructuor Parameter Documentation
     * @param rightAttack TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public IMPTeamRatings generateTeamRatings(double midfield, double leftDef, double middleDef,
                                              double rightDef, double leftAttack,
                                              double middleAttack, double rightAttack);
}
