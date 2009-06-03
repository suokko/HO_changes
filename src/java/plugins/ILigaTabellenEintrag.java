// %1127326955837:plugins%
/*
 * ILigaTabellenEintrag.java
 *
 * Created on 21. Oktober 2004, 08:36
 */
package plugins;

/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public interface ILigaTabellenEintrag extends java.lang.Comparable {
    //~ Static fields/initializers -----------------------------------------------------------------

    /*SerienDefines*/
    /*HOME Victory*/

    /** TODO Missing Parameter Documentation */
    public static final byte H_SIEG = 1;

    /** TODO Missing Parameter Documentation */
    public static final byte A_SIEG = 2;

    /** HOME DRAW */
    public static final byte H_UN = 3;

    /** TODO Missing Parameter Documentation */
    public static final byte A_UN = 4;

    /** Home Loose */
    public static final byte H_NIED = 5;

    /** TODO Missing Parameter Documentation */
    public static final byte A_NIED = 6;

    /** TODO Missing Parameter Documentation */
    public static final byte UNKOWN = 0;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Getter for property m_iA_Nied.
     *
     * @return Value of property m_iA_Nied.
     */
    public int getA_Nied();

    /**
     * Getter for property m_iA_Punkte.
     *
     * @return Value of property m_iA_Punkte.
     */
    public int getA_Punkte();

    /**
     * Getter for property m_iA_Siege.
     *
     * @return Value of property m_iA_Siege.
     */
    public int getA_Siege();

    /**
     * Getter for property m_iA_ToreFuer.
     *
     * @return Value of property m_iA_ToreFuer.
     */
    public int getA_ToreFuer();

    /**
     * Getter for property m_iA_ToreGegen.
     *
     * @return Value of property m_iA_ToreGegen.
     */
    public int getA_ToreGegen();

    /**
     * Getter for property m_iA_Un.
     *
     * @return Value of property m_iA_Un.
     */
    public int getA_Un();

    /**
     * Getter for property m_iAltePosition.
     *
     * @return Value of property m_iAltePosition.
     */
    public int getAltePosition();

    /**
     * Getter for property m_iAnzSpiele.
     *
     * @return Value of property m_iAnzSpiele.
     */
    public int getAnzSpiele();

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getAwayTorDiff();

    /**
     * Getter for property m_iG_Nied.
     *
     * @return Value of property m_iG_Nied.
     */
    public int getG_Nied();

    /**
     * Getter for property m_iG_Siege.
     *
     * @return Value of property m_iG_Siege.
     */
    public int getG_Siege();

    /**
     * Getter for property m_iG_Un.
     *
     * @return Value of property m_iG_Un.
     */
    public int getG_Un();

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getGesamtTorDiff();

    /**
     * Getter for property m_iH_Nied.
     *
     * @return Value of property m_iH_Nied.
     */
    public int getH_Nied();

    /**
     * Getter for property m_iH_Punkte.
     *
     * @return Value of property m_iH_Punkte.
     */
    public int getH_Punkte();

    /**
     * Getter for property m_iH_Siege.
     *
     * @return Value of property m_iH_Siege.
     */
    public int getH_Siege();

    /**
     * Getter for property m_iH_ToreFuer.
     *
     * @return Value of property m_iH_ToreFuer.
     */
    public int getH_ToreFuer();

    /**
     * Getter for property m_iH_ToreGegen.
     *
     * @return Value of property m_iH_ToreGegen.
     */
    public int getH_ToreGegen();

    /**
     * Getter for property m_iH_Un.
     *
     * @return Value of property m_iH_Un.
     */
    public int getH_Un();

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getHeimTorDiff();

    /**
     * liefert die letzen XXX Spiele als Serie
     *
     * @param anzahl wie viele der letzen Spiele sollen angezeigt werden,  -1 = alle, 0 = nur das
     *        akuelle, 1-x = anzahl Spiele vor dem aktuellen
     *
     * @return TODO Missing Return Method Documentation
     */
    public byte[] getLastSerie(int anzahl);

    /**
     * Getter for property m_bPosition.
     *
     * @return Value of property m_bPosition.
     */
    public int getPosition();

    /**
     * Getter for property m_iPunkte.
     *
     * @return Value of property m_iPunkte.
     */
    public int getPunkte();

    /**
     * Getter for property m_aSerie.
     *
     * @return Value of property m_aSerie.
     */
    public byte[] getSerie();

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getSerieAsString();

    /**
     * Getter for property m_iTeamId.
     *
     * @return Value of property m_iTeamId.
     */
    public int getTeamId();

    /**
     * Getter for property m_sTeamName.
     *
     * @return Value of property m_sTeamName.
     */
    public java.lang.String getTeamName();

    /**
     * Getter for property m_iToreFuer.
     *
     * @return Value of property m_iToreFuer.
     */
    public int getToreFuer();

    /**
     * Getter for property m_iToreGegen.
     *
     * @return Value of property m_iToreGegen.
     */
    public int getToreGegen();
}
