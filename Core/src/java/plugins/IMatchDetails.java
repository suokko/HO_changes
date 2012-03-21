// %2077366746:plugins%
/*
 * IMatchDetails.java
 *
 * Created on 18. Oktober 2004, 07:18
 */
package plugins;

import ho.module.matches.model.TeamLineup;

import java.util.List;
import java.util.Vector;


/**
 * Interface for severy match details.
 *
 * @author thomas.werth
 */
public interface IMatchDetails {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public static final int EINSTELLUNG_UNBEKANNT = -1000;
    /** Play it cool */
    public static final int EINSTELLUNG_PIC = -1;
    /** Normal */
    public static final int EINSTELLUNG_NORMAL = 0;
    /** Match of the Season */
    public static final int EINSTELLUNG_MOTS = 1;

    /** rain */
    public static final int WETTER_REGEN = 0;
    /** overcast */
    public static final int WETTER_BEWOELKT = 1;
    /** cloudy */
    public static final int WETTER_WOLKIG = 2;
    /** sun */
    public static final int WETTER_SONNE = 3;

    /** Normal tactic */
    public static final int TAKTIK_NORMAL = 0;
    /** Pressing tactic */
    public static final int TAKTIK_PRESSING = 1;
    /** Counter attack tactic */
    public static final int TAKTIK_KONTER = 2;
    /** AiM - Attack On Middle */
    public static final int TAKTIK_MIDDLE = 3;
    /** AoW - Attack On Wings */
    public static final int TAKTIK_WINGS = 4;
    /** Play creatively */
    public static final int TAKTIK_CREATIVE = 7;
    /** Long shots */
    public static final int TAKTIK_LONGSHOTS = 8;

    /** away match */
    public static final short LOCATION_AWAY = 0;
    /** home match */
    public static final short LOCATION_HOME = 1;
    /** away derby */
    public static final short LOCATION_AWAYDERBY = 2;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Getter for property m_iArenaID.
     *
     * @return Value of property m_iArenaID.
     */
    public int getArenaID();

    /**
     * Getter for property m_sArenaName.
     *
     * @return Value of property m_sArenaName.
     */
    public java.lang.String getArenaName();

    /**
     * Getter for property m_clFetchDatum.
     *
     * @return Value of property m_clFetchDatum.
     */
    public java.sql.Timestamp getFetchDatum();

    /**
     * Method that extract the formation from the full match comment
     *
     * @param home true for home lineup, false for away
     *
     * @return The formation type 4-4-2 or 5-3-2 etc
     */
    public String getFormation(boolean home);

    /**
     * Getter for property AWAY Team ID
     *
     * @return Value of property m_iGastId.
     */
    public int getGastId();

    /**
     * Getter for property m_sGastName.
     *
     * @return Value of property m_sGastName.
     */
    public java.lang.String getGastName();

    /**
     * PIC=-1/NORM=0/MOTS=1
     *
     * @return Value of property m_iGuestEinstellung.
     */
    public int getGuestEinstellung();

    /**
     * Getter for property m_iGuestGoals.
     *
     * @return Value of property m_iGuestGoals.
     */
    public int getGuestGoals();

    /**
     * Getter for property m_iGuestLeftAtt.
     *
     * @return Value of property m_iGuestLeftAtt.
     */
    public int getGuestLeftAtt();

    /**
     * Getter for property m_iGuestLeftDef.
     *
     * @return Value of property m_iGuestLeftDef.
     */
    public int getGuestLeftDef();

    /**
     * Getter for property m_iGuestMidAtt.
     *
     * @return Value of property m_iGuestMidAtt.
     */
    public int getGuestMidAtt();

    /**
     * Getter for property m_iGuestMidDef.
     *
     * @return Value of property m_iGuestMidDef.
     */
    public int getGuestMidDef();

    /**
     * Getter for property m_iGuestMidfield.
     *
     * @return Value of property m_iGuestMidfield.
     */
    public int getGuestMidfield();

    /**
     * Getter for property m_iGuestRightAtt.
     *
     * @return Value of property m_iGuestRightAtt.
     */
    public int getGuestRightAtt();

    /**
     * Getter for property m_iGuestRightDef.
     *
     * @return Value of property m_iGuestRightDef.
     */
    public int getGuestRightDef();

    /**
     * Getter for property m_iGuestTacticSkill.
     *
     * @return Value of property m_iGuestTacticSkill.
     */
    public int getGuestTacticSkill();

    /**
     * Getter for property m_iGuestTacticType.
     *
     * @return Value of property m_iGuestTacticType.
     */
    public int getGuestTacticType();

    ////////////////////////////////////////////////////////////////////////////////
    //Accessor
    ////////////////////////////////////////////////////////////////////////////////

    /**
     * Getter for property Home Team ID
     *
     * @return Value of property m_iHeimId.
     */
    public int getHeimId();

    /**
     * Getter for property m_sHeimName.
     *
     * @return Value of property m_sHeimName.
     */
    public java.lang.String getHeimName();

    /**
     * Getter for property m_vHighlights.
     *
     * @return Value of property m_vHighlights.
     */
    public Vector<IMatchHighlight> getHighlights();

    /**
     * Getter for property m_iHomeEinstellung.
     *
     * @return Value of property m_iHomeEinstellung.
     */
    public int getHomeEinstellung();

    /**
     * Getter for property m_iHomeGoals.
     *
     * @return Value of property m_iHomeGoals.
     */
    public int getHomeGoals();

    /**
     * Getter for property m_iHomeLeftAtt.
     *
     * @return Value of property m_iHomeLeftAtt.
     */
    public int getHomeLeftAtt();

    /**
     * Getter for property m_iHomeLeftDef.
     *
     * @return Value of property m_iHomeLeftDef.
     */
    public int getHomeLeftDef();

    /**
     * Getter for property m_iHomeMidAtt.
     *
     * @return Value of property m_iHomeMidAtt.
     */
    public int getHomeMidAtt();

    /**
     * Getter for property m_iHomeMidDef.
     *
     * @return Value of property m_iHomeMidDef.
     */
    public int getHomeMidDef();

    /**
     * Getter for property m_iHomeMidfield.
     *
     * @return Value of property m_iHomeMidfield.
     */
    public int getHomeMidfield();

    /**
     * Getter for property m_iHomeRightAtt.
     *
     * @return Value of property m_iHomeRightAtt.
     */
    public int getHomeRightAtt();

    /**
     * Getter for property m_iHomeRightDef.
     *
     * @return Value of property m_iHomeRightDef.
     */
    public int getHomeRightDef();

    /**
     * Getter for property m_iHomeTacticSkill.
     *
     * @return Value of property m_iHomeTacticSkill.
     */
    public int getHomeTacticSkill();

    /**
     * Getter for property m_iHomeTacticType.
     *
     * @return Value of property m_iHomeTacticType.
     */
    public int getHomeTacticType();

    /**
     * Method that extract a lineup from the full match comment
     *
     * @param home true for home lineup, false for away
     *
     * @return The list of last names that started the match
     */
    public List<String> getLineup(boolean home);

    /**
     * Getter for property m_iMatchID.
     *
     * @return Value of property m_iMatchID.
     */
    public int getMatchID();

    /**
     * Getter for property m_sMatchreport.
     *
     * @return Value of property m_sMatchreport.
     */
    public java.lang.String getMatchreport();

    /**
     * Getter for property m_clSpielDatum.
     *
     * @return Value of property m_clSpielDatum.
     */
    public java.sql.Timestamp getSpielDatum();

    /**
     * Method that extract a lineup from the full match comment
     *
     * @param home true for home lineup, false for away
     *
     * @return The Lineup object
     */
    public TeamLineup getTeamLineup(boolean home);

    /**
     * ID of Weather
     *
     * @return Value of property m_iWetterId.
     */
    public int getWetterId();

    /**
     * Number of crowd
     *
     * @return Value of property m_iZuschauer.
     */
    public int getZuschauer();

    /**
     * Get the region ID of this match's arena
     * @return
     */
    public int getRegionId ();
    
	/**
	 * @return the soldTerraces
	 */
	public int getSoldTerraces();

	/**
	 * @param soldTerraces the soldTerraces to set
	 */
	public void setSoldTerraces(int soldTerraces);

	/**
	 * @return the soldBasic
	 */
	public int getSoldBasic();

	/**
	 * @param soldBasic the soldBasic to set
	 */
	public void setSoldBasic(int soldBasic);

	/**
	 * @return the soldRoof
	 */
	public int getSoldRoof();

	/**
	 * @param soldRoof the soldRoof to set
	 */
	public void setSoldRoof(int soldRoof);

	/**
	 * @return the soldVIP
	 */
	public int getSoldVIP();
	/**
	 * @param soldVIP the soldVIP to set
	 */
	public void setSoldVIP(int soldVIP);
}
