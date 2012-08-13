// %214693493:de.hattrickorganizer.model%
package ho.core.file.hrf;

import ho.core.util.HOLogger;

import java.sql.Timestamp;


/**
 * Benutzerdaten
 */
public final class HRF {
	
    //~ Instance fields ----------------------------------------------------------------------------

	private int hrfId = -1;
	private Timestamp datum = new Timestamp(System.currentTimeMillis());
	private String name = null;

    //~ Constructors -------------------------------------------------------------------------------

	/**
	 * Creates a new Hrf object.
	 *
	 * @param rs TODO Missing Constructuor Parameter Documentation
	 *
	 */
	public HRF() {						
	}
	
	/**
	 * Creates a new Hrf object.
	 * 
	 * @param _hrfId
	 * @param _name
	 * @param _datum
	 */
	public HRF(int _hrfId, String _name, Timestamp _datum) {
		this.hrfId = _hrfId;
		this.name = _name;
		this.datum = _datum;
	}	
    /**
     * Creates a new Hrf object.
     *
     * @param rs TODO Missing Constructuor Parameter Documentation
     *
     * @throws Exception TODO Missing Constructuor Exception Documentation
     */
    public HRF(java.sql.ResultSet rs) throws Exception {
        try {
            hrfId = rs.getInt("HRF_ID");
            name = ho.core.db.DBManager.deleteEscapeSequences(rs.getString("Name"));
            datum = rs.getTimestamp("Datum");
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"Konstruktor HRF: " + e.toString());
        }
    }

    //~ Methods ------------------------------------------------------------------------------------


	public Timestamp getDatum() {
		return datum;
	}

	public int getHrfId() {
		return hrfId;
	}

	public String getName() {
		return name;
	}

}
