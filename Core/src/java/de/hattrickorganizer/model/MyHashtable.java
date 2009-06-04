// %808475447:de.hattrickorganizer.model%
/*
 * MyHashtable.java
 *
 * Created on 13. Januar 2004, 10:45
 */
package de.hattrickorganizer.model;

/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class MyHashtable extends java.util.Hashtable {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of MyHashtable
     */
    public MyHashtable() {
        super();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param key TODO Missing Method Parameter Documentation
     * @param value TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Object put(Object key, Object value) {
        return (value != null) ? super.put(key, value) : super.put(key, "");
    }
}
