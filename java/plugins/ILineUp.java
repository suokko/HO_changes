// %1127326955931:plugins%
/*
 * ILineUp.java
 *
 * Created on 26. April 2004, 07:26
 */
package plugins;

import java.util.Vector;

import de.hattrickorganizer.model.Spieler;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
/**
 * Interface for LineUp Access Once LineUp has been changed by plugin , you need to call
 * IGUI.doLineupRefresh() to let HO refresh internal lineup screen
 */
public interface ILineUp {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** Systems */
    public static final byte SYS_433 = 0;

    /** TODO Missing Parameter Documentation */
    public static final byte SYS_442 = 1;

    /** TODO Missing Parameter Documentation */
    public static final byte SYS_532 = 2;

    /** TODO Missing Parameter Documentation */
    public static final byte SYS_541 = 3;

    /** TODO Missing Parameter Documentation */
    public static final byte SYS_352 = 4;

    /** TODO Missing Parameter Documentation */
    public static final byte SYS_343 = 5;

    /** TODO Missing Parameter Documentation */
    public static final byte SYS_451 = 6;

    /** TODO Missing Parameter Documentation */
    public static final byte SYS_MURKS = 7;

    /** DEfaultNAme for LineUP */
    public static final String DEFAULT_NAME = "HO!";

    /** FLAG LineUp is independend to a hrf */
    public static final int NO_HRF_VERBINDUNG = -1;

    /** Order for lineup assistent */
    public static final byte AW_MF_ST = 0;

    /** TODO Missing Parameter Documentation */
    public static final byte AW_ST_MF = 1;

    /** TODO Missing Parameter Documentation */
    public static final byte MF_ST_AW = 2;

    /** TODO Missing Parameter Documentation */
    public static final byte MF_AW_ST = 3;

    /** TODO Missing Parameter Documentation */
    public static final byte ST_AW_MF = 4;

    /** TODO Missing Parameter Documentation */
    public static final byte ST_MF_AW = 5;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * get the tactic level for AiM/AoW
     *
     * @return tactic level
     */
    public float getTacticLevelAimAow();

    /**
     * get the tactic level for counter
     *
     * @return tactic level
     */
    public float getTacticLevelCounter();

    /**
     * get the tactic level for pressing
     *
     * @return tactic level
     */
    public float getTacticLevelPressing();

    /**
     * get the tactic level for Long Shots
     *
     * @return tactic level
     */
    public float getTacticLevelLongShots();
    
    /**
     * get Value for Defense
     *
     * @param spieler TODO Missing Constructuor Parameter Documentation
     * @param mitForm TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public float getAWTeamStk(Vector<Spieler> spieler, boolean mitForm);

    /**
     * Setter for property m_iAttitude.
     *
     * @param m_iAttitude New value of property m_iAttitude.
     */
    public void setAttitude(int m_iAttitude);

    /**
     * Getter for property m_iAttitude.
     *
     * @return Value of property m_iAttitude.
     */
    public int getAttitude();

    /**
     * set Caption via assistent
     *
     * @param spieler TODO Missing Constructuor Parameter Documentation
     */
    public void setAutoKapitaen(Vector<Spieler> spieler);

    /**
     * assistent sets Kicker
     *
     * @param spieler TODO Missing Constructuor Parameter Documentation
     */
    public void setAutoKicker(Vector<Spieler> spieler);

    /**
     * Get the average experience of all players in lineup
     * using the formula from kopsterkespits:
     * teamxp = ((sum of teamxp + xp of captain)/12)*(1-(7-leadership of captain)*5%)
     */
    public float getAverageExperience();

    /**
     * get PlayerId for best penalty shooter in lineup
     *
     * @return TODO Missing Return Method Documentation
     */
    public int[] getBestElferKicker();

    /**
     * Predicts Central Attack-Rating
     *
     * @return TODO Missing Return Method Documentation
     */
    public double getCentralAttackRating();

    /**
     * Predicts cd-Rating
     *
     * @return TODO Missing Return Method Documentation
     */
    public double getCentralDefenseRating();

    /**
     * gets effective Position for a specified Position. example position id 3 = inner Defense +
     * tactic offense = effective :  offense ID
     *
     * @param positionsid taken from ISpielerPosition (Keeper(1) to  ausgewechselt(19) )
     *
     * @return byte from ISpielerPosition ( Normal(0) to ZUS_INNENV(7) )
     */
    public byte getEffectivePos4PositionID(int positionsid);

    /**
     * get Value for Teamstrength
     *
     * @param spieler TODO Missing Constructuor Parameter Documentation
     * @param mitForm TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public float getGesamtStaerke(Vector<Spieler> spieler, boolean mitForm);

    /**
     * Calc HatStats for currentlineup
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getHATStats();

    /**
     * Setter for property m_sHeimspiel.
     *
     * @param m_sHeimspiel New value of property m_sHeimspiel.
     */
    public void setHeimspiel(short m_sHeimspiel);

    /**
     * Getter for property m_sHeimspiel.
     *
     * @return Value of property m_sHeimspiel.
     */
    public short getHeimspiel();

    /**
     * Creates a int from 1-80 for a Rating, to use it at IHelper.getNameForBewertung()
     *
     * @param rating TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getIntValue4Rating(double rating);

    /**
     * setPlayerID for Capitan
     *
     * @param m_iKapitaen TODO Missing Constructuor Parameter Documentation
     */
    public void setKapitaen(int m_iKapitaen);

    /////////////////////////////////////////////////////////////////////////////////    
    //    Accessor
    /////////////////////////////////////////////////////////////////////////////////           

    /**
     * getPlayerId of Capitan
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getKapitaen();

    /**
     * set ID for Kicker
     *
     * @param m_iKicker TODO Missing Constructuor Parameter Documentation
     */
    public void setKicker(int m_iKicker);

    /**
     * get ID for Kicker
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getKicker();

    /**
     * Predicts LeftAttack-Rating
     *
     * @return TODO Missing Return Method Documentation
     */
    public double getLeftAttackRating();

    /**
     * Predicts ld-Rating
     *
     * @return TODO Missing Return Method Documentation
     */
    public double getLeftDefenseRating();

    /**
     * Calc LoddarStats for currentlineup
     *
     * @return TODO Missing Return Method Documentation
     */
    public float getLoddarStats();

    /**
     * get Value for Midfield
     *
     * @param spieler TODO Missing Constructuor Parameter Documentation
     * @param mitForm TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public float getMFTeamStk(Vector<Spieler> spieler, boolean mitForm);

    /////////////////////////////////////////////////////////////////////////////////    
    //    Ratings
    /////////////////////////////////////////////////////////////////////////////////           

    /**
     * Predicts MF-Rating
     *
     * @return TODO Missing Return Method Documentation
     */
    public double getMidfieldRating();

    /**
     * gets a Player for given Position
     *
     * @param positionsid taken from ISpielerPosition (Keeper(1) to  ausgewechselt(19) )
     *
     * @return TODO Missing Return Method Documentation
     */
    public plugins.ISpieler getPlayerByPositionID(int positionsid);

    /**
     * Predicts Right-Attack-Rating
     *
     * @return TODO Missing Return Method Documentation
     */
    public double getRightAttackRating();

    /**
     * Predicts rd-Rating
     *
     * @return TODO Missing Return Method Documentation
     */
    public double getRightDefenseRating();

    /**
     * get Value for Attack
     *
     * @param spieler TODO Missing Constructuor Parameter Documentation
     * @param mitForm TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public float getSTTeamStk(Vector<Spieler> spieler, boolean mitForm);

    /**
     * sets a Player at given Positione with given tactic example setSpielerAtPosition (
     * ISpielerPosition.insideMid2, aPlayerID, ISpielerPosition.OFFENSIV ); sets a player in
     * Midfield Pos 2 as offensive
     *
     * @param positionsid ID of Position ( ex. ISpielerPosition.insideMid2 )
     * @param spielerid Id of Player       ( player.getSpielerID() )
     * @param tactic TacticType for Position ( ex. ISpielerPosition.NORMAL )
     *
     * @return TODO Missing Return Method Documentation
     */
    public byte setSpielerAtPosition(int positionsid, int spielerid, byte tactic);

    /**
     * check if player is in lineup
     *
     * @param spielerId TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public boolean isSpielerAufgestellt(int spielerId);

    /**
     * check if player is in lineup and plays from beginning of match
     *
     * @param spielerId TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public boolean isSpielerInAnfangsElf(int spielerId);

    /**
     * check if player is in lineup and is reserve
     *
     * @param spielerId TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public boolean isSpielerInReserve(int spielerId);

    /**
     * Get Name for specific System
     *
     * @param system TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getSystemName(byte system);

    /**
     * get Value for Keeper
     *
     * @param spieler TODO Missing Constructuor Parameter Documentation
     * @param mitForm TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public float getTWTeamStk(Vector<Spieler> spieler, boolean mitForm);

    /**
     * gets tactic for a specified Position
     *
     * @param positionsid taken from ISpielerPosition (Keeper(1) to  ausgewechselt(19) )
     *
     * @return byte from ISpielerPosition ( TORWART(0) to  AUSGEWECHSELT3(20), UNBESTIMMT==-1 )
     */
    public byte getTactic4PositionID(int positionsid);

    /////////////////////////////////////////////////////////////////////////////////    
    //    STK Funcs
    /////////////////////////////////////////////////////////////////////////////////        

    /**
     * DOCUMENT ME!
     *
     * @param type TODO Missing Constructuor Parameter Documentation
     *
     * @return level for choosen Tactic ( Defs in IMatchDetails )
     */
    public float getTacticLevel(int type);

    /**
     * Setter for property m_iTacticType.
     *
     * @param m_iTacticType New value of property m_iTacticType.
     */
    public void setTacticType(int m_iTacticType);

    /**
     * Getter for property m_iTacticType.
     *
     * @return Value of property m_iTacticType.
     */
    public int getTacticType();

    /**
     * get Value for expierience of current System
     *
     * @return -1 for unknown system
     */
    public int getTeamErfahrung4AktuellesSystem();

    /**
     * check if all player in lineup are still in team
     */
    public void checkAufgestellteSpieler();

    /////////////////////////////////////////////////////////////////////////////////    
    //    Aktions Funcs
    ///////////////////////////////////////////////////////////////////////////////// 

    /**
     * start auto-lineup
     *
     * @param spieler vector of player's ( normally IHOMiniModel.getAllSpieler() )
     * @param reihenfolge ORDER of "teamPart-filling" for assistent logic
     * @param mitForm regard to player form
     * @param idealPosFirst set best Pos first
     * @param ignoreVerletzung TODO Missing Constructuor Parameter Documentation
     * @param ignoreSperren don't regard to red cards
     * @param wetterBonus effect of weather , should be
     *        IHOMiniModel.getUserSettings().WetterEffektBonus
     * @param wetter wheather type ( ISpieler.SONNIG)
     */
    public void doAufstellung(Vector<Spieler> spieler, byte reihenfolge, boolean mitForm,
                              boolean idealPosFirst, boolean ignoreVerletzung,
                              boolean ignoreSperren, float wetterBonus, int wetter);

    /**
     * determinate current system
     *
     * @return TODO Missing Return Method Documentation
     */
    public byte ermittelSystem();

    /**
     * loads Lineup with given Name
     *
     * @param name TODO Missing Constructuor Parameter Documentation
     */
    public void load(String name);

    /////////////////////////////////////////////////////////////////////////////////    
    //    Helper Funcs
    /////////////////////////////////////////////////////////////////////////////////        

    /**
     * clear lineup
     */
    public void resetAufgestellteSpieler();

    /////////////////////////////////////////////////////////////////////////////////    
    //    Datenbank Funcs
    /////////////////////////////////////////////////////////////////////////////////           

    /**
     * saves lineup under given Name in DB
     *
     * @param name TODO Missing Constructuor Parameter Documentation
     */
    public void save(String name);
}
