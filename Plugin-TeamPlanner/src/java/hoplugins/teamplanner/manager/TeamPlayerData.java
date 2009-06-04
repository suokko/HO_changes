// %1841100288:hoplugins.teamplanner.manager%
/*
 * Created on Nov 26, 2005
 */
package hoplugins.teamplanner.manager;

import hoplugins.teamplanner.vo.HTWeek;

import plugins.ISpieler;


/**
 * DOCUMENT ME!
 *
 * @author mcapitanio
 */
public class TeamPlayerData {
    //~ Instance fields ----------------------------------------------------------------------------

    private HTWeek finalWeek;
    private HTWeek startWeek;
    private ISpieler data;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param data The data to set.
     */
    public void setData(ISpieler data) {
        this.data = data;
    }

    /**
     * DOCUMENT ME!
     *
     * @return Returns the data.
     */
    public ISpieler getData() {
        return data;
    }

    /**
     * DOCUMENT ME!
     *
     * @param finalWeek The finalWeek to set.
     */
    public void setFinalWeek(HTWeek finalWeek) {
        this.finalWeek = finalWeek;
    }

    /**
     * DOCUMENT ME!
     *
     * @return Returns the finalWeek.
     */
    public HTWeek getFinalWeek() {
        return finalWeek;
    }

    /**
     * DOCUMENT ME!
     *
     * @param startWeek The startWeek to set.
     */
    public void setStartWeek(HTWeek startWeek) {
        this.startWeek = startWeek;
    }

    /**
     * DOCUMENT ME!
     *
     * @return Returns the startWeek.
     */
    public HTWeek getStartWeek() {
        return startWeek;
    }
}
