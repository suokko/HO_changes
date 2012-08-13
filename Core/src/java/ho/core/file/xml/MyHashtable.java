// %808475447:de.hattrickorganizer.model%
/*
 * MyHashtable.java
 *
 * Created on 13. Januar 2004, 10:45
 */
package ho.core.file.xml;

import java.util.Hashtable;

/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class MyHashtable extends Hashtable<String,String> {
    //~ Constructors -------------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -4952614135098302927L;

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
    @Override
	public final String put(String key, String value) {
        return (value != null) ? super.put(key, value) : super.put(key, "");
    }
}
