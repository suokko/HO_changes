// %844311304:gui%
package de.hattrickorganizer.model;

import java.util.HashMap;




/**
 * User configuration. Loaded when HO starts and saved when HO! exits.
 */
public final class HOParameter extends Configuration {
    //~ Static fields/initializers -----------------------------------------------------------------

    private static HOParameter m_clUserParameter;

	//~ Constructors -------------------------------------------------------------------------------

	/**
	 * Creates a new HOParameter object.
	 */
	private HOParameter() {
	}

	//~ Methods ------------------------------------------------------------------------------------

	/**
	 * DOCUMENT ME!
	 *
	 * @return singelton instance
	 */
	public static HOParameter instance() {
		if (m_clUserParameter == null) {
			m_clUserParameter = new HOParameter();
		}

		return m_clUserParameter;
	}

    //------Konstanten-----------------------------------------------


	/** TODO Missing Parameter Documentation */
	public int lastNews = -1;

	/** TODO Missing Parameter Documentation */
	public float EpvRelease = 1f;
	
	/** TODO Missing Parameter Documentation */
	public float RatingsRelease = 1f;	
	
    /** TODO Missing Parameter Documentation */
    public int DBVersion = 4;

    /** TODO Missing Parameter Documentation */
    public int HOTotalUsers;

    /** TODO Missing Parameter Documentation */
    public int HOUsers;

	public HashMap getValues() {
		HashMap map = new HashMap();
		map.put("lastNews",String.valueOf(lastNews));
		map.put("EpvRelease",String.valueOf(EpvRelease));
		map.put("RatingsRelease",String.valueOf(RatingsRelease));		
		map.put("DBVersion",String.valueOf(DBVersion));
		map.put("HOTotalUsers",String.valueOf(HOTotalUsers));
		map.put("HOUsers",String.valueOf(HOUsers));
		return map;
	}

	public void setValues(HashMap values) {
		DBVersion = getIntValue(values,"DBVersion");
		HOTotalUsers = getIntValue(values,"HOTotalUsers");
		HOUsers = getIntValue(values,"HOUsers");		
		lastNews = getIntValue(values,"lastNews");
		EpvRelease = getFloatValue(values,"EpvRelease");
		RatingsRelease = getFloatValue(values,"RatingsRelease");
	}


}
