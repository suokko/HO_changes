// %3859586867:de.hattrickorganizer.gui.model%
package de.hattrickorganizer.gui.model;

import java.util.Vector;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class Ergebnis {
    //~ Instance fields ----------------------------------------------------------------------------

    private Vector m_vActions;
    private int m_iGuestGoals;
    private int m_iHomeGoals;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new Ergebnis object.
     *
     * @param homegoals TODO Missing Constructuor Parameter Documentation
     * @param guestgoals TODO Missing Constructuor Parameter Documentation
     * @param vActions TODO Missing Constructuor Parameter Documentation
     */
    public Ergebnis(int homegoals, int guestgoals, Vector vActions) {
        m_iHomeGoals = homegoals;
        m_iGuestGoals = guestgoals;
        m_vActions = vActions;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Vector getActions() {
        return m_vActions;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getGuestGoals() {
        return m_iGuestGoals;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getHomeGoals() {
        return m_iHomeGoals;
    }

    /**
     * 1 = win, 0 = drawn, -1 = lose for the hometeam
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getResult() {
        if (m_iHomeGoals > m_iGuestGoals) {
            return 1;
        } else if (m_iHomeGoals < m_iGuestGoals) {
            return -1;
        } else {
            return 0;
        }
    }
}
