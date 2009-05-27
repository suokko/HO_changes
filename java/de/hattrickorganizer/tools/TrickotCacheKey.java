// %4199955696:de.hattrickorganizer.tools%
package de.hattrickorganizer.tools;

/**
 * Key für den TrickotCache
 */
public class TrickotCacheKey {
    //~ Instance fields ----------------------------------------------------------------------------

    private byte m_bTaktik;
    private int m_iId;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TrickotCacheKey object.
     *
     * @param id TODO Missing Constructuor Parameter Documentation
     * @param taktik TODO Missing Constructuor Parameter Documentation
     */
    public TrickotCacheKey(int id, byte taktik) {
        m_iId = id;
        m_bTaktik = taktik;
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
    public final byte getTaktik() {
        return m_bTaktik;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param obj TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean equals(Object obj) {
        if (obj instanceof TrickotCacheKey) {
            if ((((TrickotCacheKey) obj).getId() == getId())
                && (((TrickotCacheKey) obj).getTaktik() == getTaktik())) {
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
    public final int hashCode() {
        return (int) m_iId + (int) m_bTaktik;
    }
}
