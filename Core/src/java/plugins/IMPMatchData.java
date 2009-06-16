// %1127326956462:plugins%
/*
 * IMPMachData.java
 *
 * Created on 22. Dezember 2004, 14:10
 */
package plugins;

import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public interface IMPMatchData {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * retrieves a copy of all actions for away time that have been calculated til the call of this
     * func
     *
     * @return TODO Missing Return Method Documentation
     */
    public List<IMPActions> getAwayTeamActionList();

    /**
     * retrieves a copy of all actions for home time that have been calculated til the call of this
     * func
     *
     * @return TODO Missing Return Method Documentation
     */
    public List<IMPActions> getHomeTeamActionList();

    /**
     * calculates next minute of match
     *
     * @return actions occured in that minute
     */
    public IMPActions[] advance();

    /**
     * prints all match events on console output
     */

    //	public void recap();
}
