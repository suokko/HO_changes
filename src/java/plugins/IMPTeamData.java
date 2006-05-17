// %1127326956478:plugins%
/*
 * IMPTeamData.java
 *
 * Created on 22. Dezember 2004, 14:10
 */
package plugins;

/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public interface IMPTeamData {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public IMPTeamRatings getRatings();

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getTacticLevel();

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getTacticType();

    //	public void addAction(IMPActions action) ;
    public String getTeamName();
}
