// %3966794376:de.hattrickorganizer.gui.model%
/*
 * CBItem.java
 *
 * Created on 8. Mai 2002, 08:37
 */
package ho.core.datatype;

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
     * @param text TODO Missing Constructuor Parameter Documentation
     * @param id TODO Missing Constructuor Parameter Documentation
     */
    public CBItem(String text, int id) {
        m_sText = text;
        m_iId = id;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getId() {
        return m_iId;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final String getText() {
        return m_sText;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param obj TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
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
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	public final String toString() {
        return m_sText;
    }
}
