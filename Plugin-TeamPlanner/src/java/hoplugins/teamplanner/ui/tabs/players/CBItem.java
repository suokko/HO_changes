// %1787728142:hoplugins.teamplanner.ui.tabs.players%
/*
 * CBItem.java
 *
 * Created on 8. Mai 2002, 08:37
 */
package hoplugins.teamplanner.ui.tabs.players;

/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 * @version
 */
public class CBItem {
    //~ Instance fields ----------------------------------------------------------------------------

    private String m_sText;
    private int m_iId;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates new CBItem
     *
     * @param text Missing Constructuor Parameter Documentation
     * @param id Missing Constructuor Parameter Documentation
     */
    public CBItem(String text, int id) {
        m_sText = text;
        m_iId = id;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     *
     * @param id Missing Method Parameter Documentation
     */
    public final void setId(int id) {
        m_iId = id;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public final int getId() {
        return m_iId;
    }

    /**
     * Missing Method Documentation
     *
     * @param text Missing Method Parameter Documentation
     */
    public final void setText(String text) {
        m_sText = text;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public final String getText() {
        return m_sText;
    }

    /**
     * Missing Method Documentation
     *
     * @param obj Missing Method Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    public final boolean equals(Object obj) {
        if (obj instanceof CBItem) {
            final CBItem temp = (CBItem) obj;

            if (this.getId() == temp.getId()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public final String toString() {
        return m_sText;
    }
}
