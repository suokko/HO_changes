// %16308149:de.hattrickorganizer.model.matches%
/*
 * MatchKurzInfo.java
 *
 * Created on 22. Oktober 2003, 09:03
 */
package ho.core.model.match;

import ho.core.util.HOLogger;
import ho.core.util.StringUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * DOCUMENT ME!
 * 
 * @author thomas.werth
 */
public class MatchKurzInfo implements Comparable<Object> {
	// ~ Instance fields
	// ----------------------------------------------------------------------------

	/** Name des Teams zu dem die Matchinfo gehört */
	private String m_sGastName = "";

	/** Name des Teams zu dem die Matchinfo gehört */
	private String m_sHeimName = "";

	/** Datum des spiels */
	private String m_sMatchDate = "";

	/** orders given for this match? */
	private boolean ordersGiven = true;

	/** ID des Teams zu dem die info gehört */
	private int m_iGastID = -1;

	/** Gast Tore */
	private int m_iGastTore = -1;

	/** ID des Teams zu dem die info gehört */
	private int m_iHeimID = -1;

	/** Heim Tore */
	private int m_iHeimTore = -1;

	/** ID des MAtches */
	private int m_iMatchID = -1;

	/** Status des Spiels */
	private int m_iMatchStatus = -1;

	/** Typ des Spiels */
	private MatchType m_mtMatchTyp = MatchType.NONE;

	private Timestamp matchDateTimestamp;

	/** TODO Missing Parameter Documentation */
	public static final int ONGOING = 3;

	/** TODO Missing Parameter Documentation */
	public static final int UPCOMING = 2;

	/** TODO Missing Parameter Documentation */
	public static final int FINISHED = 1;

	/**
	 * Setter for the ordersGiven property which indicates if orders for this
	 * match are given or not.
	 * 
	 * @param ordersGiven
	 *            <code>true</code> if orders for this match are given,
	 *            <code>false</code> otherwise.
	 */
	public final void setOrdersGiven(boolean ordersGiven) {
		this.ordersGiven = ordersGiven;
	}

	/**
	 * Indicates if orders are given for this match. From HT only supplied for
	 * upcoming matches (haven't started yet).
	 * 
	 * @return <code>true</code> if orders for this match are given,
	 *         <code>false</code> otherwise.
	 */
	public final boolean isOrdersGiven() {
		return ordersGiven;
	}

	/**
	 * Setter for property m_iGastID.
	 * 
	 * @param m_iGastID
	 *            New value of property m_iGastID.
	 */
	public final void setGastID(int m_iGastID) {
		this.m_iGastID = m_iGastID;
	}

	/**
	 * Getter for property m_iGastID.
	 * 
	 * @return Value of property m_iGastID.
	 */
	public final int getGastID() {
		return m_iGastID;
	}

	/**
	 * Setter for property m_sGastName.
	 * 
	 * @param m_sGastName
	 *            New value of property m_sGastName.
	 */
	public final void setGastName(java.lang.String m_sGastName) {
		this.m_sGastName = m_sGastName;
	}

	/**
	 * Getter for property m_sGastName.
	 * 
	 * @return Value of property m_sGastName.
	 */
	public final java.lang.String getGastName() {
		return m_sGastName;
	}

	/**
	 * Setter for property m_iGastTore.
	 * 
	 * @param m_iGastTore
	 *            New value of property m_iGastTore.
	 */
	public final void setGastTore(int m_iGastTore) {
		this.m_iGastTore = m_iGastTore;
	}

	/**
	 * Getter for property m_iGastTore.
	 * 
	 * @return Value of property m_iGastTore.
	 */
	public final int getGastTore() {
		return m_iGastTore;
	}

	/**
	 * Setter for property m_iHeimID.
	 * 
	 * @param m_iHeimID
	 *            New value of property m_iHeimID.
	 */
	public final void setHeimID(int m_iHeimID) {
		this.m_iHeimID = m_iHeimID;
	}

	/**
	 * Getter for property m_iHeimID.
	 * 
	 * @return Value of property m_iHeimID.
	 */
	public final int getHeimID() {
		return m_iHeimID;
	}

	/**
	 * Setter for property m_sHeimName.
	 * 
	 * @param m_sHeimName
	 *            New value of property m_sHeimName.
	 */
	public final void setHeimName(java.lang.String m_sHeimName) {
		this.m_sHeimName = m_sHeimName;
	}

	/**
	 * Getter for property m_sHeimName.
	 * 
	 * @return Value of property m_sHeimName.
	 */
	public final java.lang.String getHeimName() {
		return m_sHeimName;
	}

	/**
	 * Setter for property m_iHeimTore.
	 * 
	 * @param m_iHeimTore
	 *            New value of property m_iHeimTore.
	 */
	public final void setHeimTore(int m_iHeimTore) {
		this.m_iHeimTore = m_iHeimTore;
	}

	/**
	 * Getter for property m_iHeimTore.
	 * 
	 * @return Value of property m_iHeimTore.
	 */
	public final int getHeimTore() {
		return m_iHeimTore;
	}

	/**
	 * Setter for property m_sMatchDate.
	 * 
	 * @param m_sMatchDate
	 *            New value of property m_sMatchDate.
	 */
	public final void setMatchDate(java.lang.String m_sMatchDate) {
		this.m_sMatchDate = m_sMatchDate;
		// ensures that getMatchDateAsTimestamp() will regenerate the timestamp
		this.matchDateTimestamp = null;
	}

	/**
	 * Getter for property m_sMatchDate.
	 * 
	 * @return Value of property m_sMatchDate.
	 */
	public final java.lang.String getMatchDate() {
		return m_sMatchDate;
	}

	/**
	 * Getter for property m_lDatum.
	 * 
	 * @return Value of property m_lDatum.
	 */
	public final java.sql.Timestamp getMatchDateAsTimestamp() {
		if (this.matchDateTimestamp == null) {
			if (!StringUtils.isEmpty(this.m_sMatchDate)) {
				try {
					// Hattrick
					SimpleDateFormat simpleFormat = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					this.matchDateTimestamp = new Timestamp(simpleFormat.parse(
							m_sMatchDate).getTime());
				} catch (Exception e) {
					try {
						// Hattrick
						SimpleDateFormat simpleFormat = new SimpleDateFormat(
								"yyyy-MM-dd");
						this.matchDateTimestamp = new Timestamp(simpleFormat
								.parse(m_sMatchDate).getTime());
					} catch (Exception ex) {
						HOLogger.instance().log(getClass(), ex);
					}
				}
			} else {
				this.matchDateTimestamp = null;
			}
		}

		return this.matchDateTimestamp;
	}

	/**
	 * Setter for property m_iMatchID.
	 * 
	 * @param m_iMatchID
	 *            New value of property m_iMatchID.
	 */
	public final void setMatchID(int m_iMatchID) {
		this.m_iMatchID = m_iMatchID;
	}

	// //////////////////////////////////////////////////////////////////////////////
	// Accessor
	// //////////////////////////////////////////////////////////////////////////////

	/**
	 * Getter for property m_iMatchID.
	 * 
	 * @return Value of property m_iMatchID.
	 */
	public final int getMatchID() {
		return m_iMatchID;
	}

	/**
	 * Setter for property m_iMatchStatus.
	 * 
	 * @param m_iMatchStatus
	 *            New value of property m_iMatchStatus.
	 */
	public final void setMatchStatus(int m_iMatchStatus) {
		this.m_iMatchStatus = m_iMatchStatus;
	}

	/**
	 * Getter for property m_iMatchStatus.
	 * 
	 * @return Value of property m_iMatchStatus.
	 */
	public final int getMatchStatus() {
		return m_iMatchStatus;
	}

	/**
	 * Setter for property m_iMatchTyp.
	 * 
	 * @param m_iMatchTyp
	 *            New value of property m_iMatchTyp.
	 */
	public final void setMatchTyp(MatchType matchTyp) {
		this.m_mtMatchTyp = matchTyp;
	}

	/**
	 * Getter for property m_iMatchTyp.
	 * 
	 * @return Value of property m_iMatchTyp.
	 */
	public final MatchType getMatchTyp() {
		return m_mtMatchTyp;
	}

	// --------------------------------------------------------------
	@Override
	public final int compareTo(Object obj) {
		if (obj instanceof MatchKurzInfo) {
			final MatchKurzInfo info = (MatchKurzInfo) obj;

			if (info.getMatchDateAsTimestamp().before(
					this.getMatchDateAsTimestamp())) {
				return -1;
			} else if (info.getMatchDateAsTimestamp().after(
					this.getMatchDateAsTimestamp())) {
				return 1;
			} else {
				return 0;
			}
		}

		return 0;
	}

	/**
	 * Merges the data from the given <code>MatchKurzInfo</code> into this
	 * <code>MatchKurzInfo</code>. This method should be used e.g. when a model
	 * has to be updated with data from a different <code>MatchKurzInfo</code>
	 * instance but and object identity has to be preserved.
	 * 
	 * @param other
	 *            the <code>MatchKurzInfo</code> to get the data from.
	 */
	public void merge(MatchKurzInfo match) {
		if (match.getMatchID() != getMatchID()) {
			throw new IllegalArgumentException(
					"Could not merge matches with different IDs");
		}
		setGastID(match.getGastID());
		setGastName(match.getGastName());
		setGastTore(match.getGastTore());
		setHeimID(match.getHeimID());
		setHeimName(match.getHeimName());
		setHeimTore(match.getHeimTore());
		setMatchDate(match.getMatchDate());
		setMatchStatus(match.getMatchStatus());
		setOrdersGiven(match.isOrdersGiven());
		setMatchTyp(match.getMatchTyp());
	}
}
