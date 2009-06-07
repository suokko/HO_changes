// %1127326956431:plugins%
/*
 * IMatchPredictionPanel.java
 *
 * Created on 7. Januar 2005, 11:10
 */
package plugins;

/**
 * DOCUMENT ME!
 *
 * @author Pirania
 */
public interface IMatchPredictionPanel {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Change the guestteam
     *
     * @param guestteam TODO Missing Constructuor Parameter Documentation
     */
    public void setGuestteam(plugins.IMPTeamData guestteam);

    /**
     * Change the hometeam
     *
     * @param hometeam TODO Missing Constructuor Parameter Documentation
     */
    public void setHometeam(plugins.IMPTeamData hometeam);

    /**
     * Returns the Number of Matches the User has choose in the gui
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getNumberOfMatches();

    /**
     * Calculates numberOfMatches matches and show the results uses getNumberOfMatches() as
     * parameter
     *
     * @param numberOfMatches TODO Missing Constructuor Parameter Documentation
     */
    public void calculateNMatches(int numberOfMatches);

    /**
     * Use this methode, if you have created your own matchresults.  To calculate n matches use
     * calculateNMatches.
     *
     * @param matchresults TODO Missing Constructuor Parameter Documentation
     */
    public void refresh(IMatchResult matchresults);
}
