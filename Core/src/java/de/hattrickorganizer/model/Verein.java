// %163934374:de.hattrickorganizer.model%
package de.hattrickorganizer.model;

import java.sql.Timestamp;
import java.util.Vector;

import plugins.ISpieler;
import plugins.ISpielerPosition;
import de.hattrickorganizer.tools.HOLogger;


/**
 * Allgemeine Informationen über den Verein
 */
public final class Verein implements plugins.IVerein {
    //~ Instance fields ----------------------------------------------------------------------------

    /** Team Name */
    private String m_sTeamName;

    //NEU XML

    /** Datum */
    private Timestamp m_clDate;

    /** Jungendspieler gezogen */
    private boolean m_bYouthPull;

    /** Ärzte */
    private int m_iAerzte;

    /** Co-Trainer */
    private int m_iCoTrainer;

    /** FanClub */

    //bald im Team
    private int m_iFans;

    /** Finanzberater */
    private int m_iFinanzberater;

    /** Jugendmannschaft */
    private int m_iJugend;

    /** Investition */
    private int m_iJugendGeld;

    /** Physiotherapeuten */
    private int m_iMasseure;

    /** Pressesprecher */
    private int m_iPRManager;

    /** Psychologen */
    private int m_iPsychologen;

    /** Siege */
    private int m_iSiege;

    /** TeamID */
    private int m_iTeamID = -1;

    //Bleibt in Verein

    /** Torwarttrainer */
    private int m_iTorwartTrainer;

    /** Ungeschlagen für # Spiele */
    private int m_iUngeschlagen;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new Verein object.
     */
    public Verein() {
    }

    ////////////////////////////////////////////////////////////////////////////////
    //Konstruktor
    ////////////////////////////////////////////////////////////////////////////////    
    public Verein(java.util.Properties properties) throws Exception {
        m_iTorwartTrainer = Integer.parseInt(properties.getProperty("mvtranare", "0"));
        m_iCoTrainer = Integer.parseInt(properties.getProperty("hjtranare", "0"));
        m_iPsychologen = Integer.parseInt(properties.getProperty("psykolog", "0"));
        m_iPRManager = Integer.parseInt(properties.getProperty("presstalesman", "0"));
        m_iFinanzberater = Integer.parseInt(properties.getProperty("ekonom", "0"));
        m_iMasseure = Integer.parseInt(properties.getProperty("massor", "0"));
        m_iAerzte = Integer.parseInt(properties.getProperty("lakare", "0"));
        m_iJugend = Integer.parseInt(properties.getProperty("juniorverksamhet", "0"));
        m_iFans = Integer.parseInt(properties.getProperty("fanclub", "0"));
        m_iUngeschlagen = Integer.parseInt(properties.getProperty("undefeated", "0"));
        m_iSiege = Integer.parseInt(properties.getProperty("victories", "0"));
    }

    /**
     * Creates a new Verein object.
     *
     * @param rs TODO Missing Constructuor Parameter Documentation
     *
     * @throws Exception TODO Missing Constructuor Exception Documentation
     */
    public Verein(java.sql.ResultSet rs) throws Exception {
        try {
            m_iTorwartTrainer = rs.getInt("TWTrainer");
            m_iCoTrainer = rs.getInt("COTrainer");
            m_iPsychologen = rs.getInt("Pschyologen");
            m_iPRManager = rs.getInt("PRManager");
            m_iFinanzberater = rs.getInt("Finanzberater");
            m_iMasseure = rs.getInt("Physiologen");
            m_iAerzte = rs.getInt("Aerzte");
            m_iJugend = rs.getInt("Jugend");
            m_iFans = rs.getInt("Fans");
            m_iUngeschlagen = rs.getInt("Ungeschlagen");
            m_iSiege = rs.getInt("Siege");
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"Konstruktor Verein: " + e.toString());
        }
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Setter for property m_iAerzte.
     *
     * @param m_iAerzte New value of property m_iAerzte.
     */
    public void setAerzte(int m_iAerzte) {
        this.m_iAerzte = m_iAerzte;
    }

    /**
     * Getter for property m_iAerzte.
     *
     * @return Value of property m_iAerzte.
     */
    public int getAerzte() {
        return m_iAerzte;
    }

    /**
     * Setter for property m_iCoTrainer.
     *
     * @param m_iCoTrainer New value of property m_iCoTrainer.
     */
    public void setCoTrainer(int m_iCoTrainer) {
        this.m_iCoTrainer = m_iCoTrainer;
    }

    /**
     * Getter for property m_iCoTrainer.
     *
     * @return Value of property m_iCoTrainer.
     */
    public int getCoTrainer() {
        return m_iCoTrainer;
    }

    /**
     * Setter for property m_clDate.
     *
     * @param m_clDate New value of property m_clDate.
     */
    public void setDate(java.sql.Timestamp m_clDate) {
        this.m_clDate = m_clDate;
    }

    /**
     * Getter for property m_clDate.
     *
     * @return Value of property m_clDate.
     */
    public java.sql.Timestamp getDate() {
        return m_clDate;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param date TODO Missing Method Parameter Documentation
     */
    public void setDateFromString(String date) {
        try {
            //Hattrick
            final java.text.SimpleDateFormat simpleFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                                                                                           java.util.Locale.GERMANY);

            m_clDate = new java.sql.Timestamp(simpleFormat.parse(date).getTime());
        } catch (Exception e) {
            try {
                //Hattrick
                final java.text.SimpleDateFormat simpleFormat = new java.text.SimpleDateFormat("yyyy-MM-dd",
                                                                                               java.util.Locale.GERMANY);

                m_clDate = new java.sql.Timestamp(simpleFormat.parse(date).getTime());
            } catch (Exception ex) {
                HOLogger.instance().log(getClass(),ex);
            }
        }
    }

    /**
     * Setter for property m_iFans.
     *
     * @param m_iFans New value of property m_iFans.
     */
    public void setFans(int m_iFans) {
        this.m_iFans = m_iFans;
    }

    /**
     * Getter for property m_iFans.
     *
     * @return Value of property m_iFans.
     */
    public int getFans() {
        return m_iFans;
    }

    /**
     * Setter for property m_iFinanzberater.
     *
     * @param m_iFinanzberater New value of property m_iFinanzberater.
     */
    public void setFinanzberater(int m_iFinanzberater) {
        this.m_iFinanzberater = m_iFinanzberater;
    }

    /**
     * Getter for property m_iFinanzberater.
     *
     * @return Value of property m_iFinanzberater.
     */
    public int getFinanzberater() {
        return m_iFinanzberater;
    }

    /**
     * Setter for property m_iJugend.
     *
     * @param m_iJugend New value of property m_iJugend.
     */
    public void setJugend(int m_iJugend) {
        this.m_iJugend = m_iJugend;
    }

    /**
     * Getter for property m_iJugend.
     *
     * @return Value of property m_iJugend.
     */
    public int getJugend() {
        return m_iJugend;
    }

    /**
     * Setter for property m_iJugendGeld.
     *
     * @param m_iJugendGeld New value of property m_iJugendGeld.
     */
    public void setJugendGeld(int m_iJugendGeld) {
        this.m_iJugendGeld = m_iJugendGeld;
    }

    /**
     * Getter for property m_iJugendGeld.
     *
     * @return Value of property m_iJugendGeld.
     */
    public int getJugendGeld() {
        return m_iJugendGeld;
    }

    /**
     * Setter for property m_iMasseure.
     *
     * @param m_iMasseure New value of property m_iMasseure.
     */
    public void setMasseure(int m_iMasseure) {
        this.m_iMasseure = m_iMasseure;
    }

    /**
     * Getter for property m_iMasseure.
     *
     * @return Value of property m_iMasseure.
     */
    public int getMasseure() {
        return m_iMasseure;
    }

    /**
     * Setter for property m_iPRManager.
     *
     * @param m_iPRManager New value of property m_iPRManager.
     */
    public void setPRManager(int m_iPRManager) {
        this.m_iPRManager = m_iPRManager;
    }

    /**
     * Getter for property m_iPRManager.
     *
     * @return Value of property m_iPRManager.
     */
    public int getPRManager() {
        return m_iPRManager;
    }

    /**
     * Setter for property m_iPsychologen.
     *
     * @param m_iPsychologen New value of property m_iPsychologen.
     */
    public void setPsychologen(int m_iPsychologen) {
        this.m_iPsychologen = m_iPsychologen;
    }

    /**
     * Getter for property m_iPsychologen.
     *
     * @return Value of property m_iPsychologen.
     */
    public int getPsychologen() {
        return m_iPsychologen;
    }

    /**
     * Setter for property m_iSiege.
     *
     * @param m_iSiege New value of property m_iSiege.
     */
    public void setSiege(int m_iSiege) {
        this.m_iSiege = m_iSiege;
    }

    /**
     * Getter for property m_iSiege.
     *
     * @return Value of property m_iSiege.
     */
    public int getSiege() {
        return m_iSiege;
    }

    /**
     * Setter for property m_iTeamID.
     *
     * @param m_iTeamID New value of property m_iTeamID.
     */
    public void setTeamID(int m_iTeamID) {
        this.m_iTeamID = m_iTeamID;
    }

    /**
     * Getter for property m_iTeamID.
     *
     * @return Value of property m_iTeamID.
     */
    public int getTeamID() {
        return m_iTeamID;
    }

    /**
     * Setter for property m_sTeamName.
     *
     * @param m_sTeamName New value of property m_sTeamName.
     */
    public void setTeamName(java.lang.String m_sTeamName) {
        this.m_sTeamName = m_sTeamName;
    }

    /**
     * Getter for property m_sTeamName.
     *
     * @return Value of property m_sTeamName.
     */
    public java.lang.String getTeamName() {
        return m_sTeamName;
    }

    /**
     * Setter for property m_iTorwartTrainer.
     *
     * @param m_iTorwartTrainer New value of property m_iTorwartTrainer.
     */
    public void setTorwartTrainer(int m_iTorwartTrainer) {
        this.m_iTorwartTrainer = m_iTorwartTrainer;
    }

    ////////////////////////////////////////////////////////////////////////////////    
    //Accessor
    ////////////////////////////////////////////////////////////////////////////////    

    /**
     * Getter for property m_iTorwartTrainer.
     *
     * @return Value of property m_iTorwartTrainer.
     */
    public int getTorwartTrainer() {
        return m_iTorwartTrainer;
    }

    /**
     * Setter for property m_iUngeschlagen.
     *
     * @param m_iUngeschlagen New value of property m_iUngeschlagen.
     */
    public void setUngeschlagen(int m_iUngeschlagen) {
        this.m_iUngeschlagen = m_iUngeschlagen;
    }

    /**
     * Getter for property m_iUngeschlagen.
     *
     * @return Value of property m_iUngeschlagen.
     */
    public int getUngeschlagen() {
        return m_iUngeschlagen;
    }

    /**
     * Setter for property m_bYouthPull.
     *
     * @param m_bYouthPull New value of property m_bYouthPull.
     */
    public void setYouthPull(boolean m_bYouthPull) {
        this.m_bYouthPull = m_bYouthPull;
    }

    /**
     * Getter for property m_bYouthPull.
     *
     * @return Value of property m_bYouthPull.
     */
    public boolean isYouthPull() {
        return m_bYouthPull;
    }

    //////////////////////////////////////////////////77    
    //Server Daten erstellen
    /////////////////////////////////////////////////////
    public ServerTeam erstelleServerTeam() {
        final Vector<ServerSpieler> serverSpieler = new Vector<ServerSpieler>();
        final Vector<ISpieler> spieler = HOVerwaltung.instance().getModel().getAllSpieler();
        ServerTeam sTeam = null;
        Spieler player = null;
        SpielerPosition position = null;
        byte pos = (byte) -1;

        for (int i = 0; (spieler != null) && (i < spieler.size()); i++) {
            player = ((Spieler) spieler.elementAt(i));
            position = HOVerwaltung.instance().getModel().getAufstellung().getPositionBySpielerId(player
                                                                                                  .getSpielerID());

            //Keine TMP Player bei Friendly's zulassen
            if ((position != null) && (player.getSpielerID() > 0)) {
                //Abähngig von Id pos speichern oder Reserve Codierung
                switch (position.getId()) {
                    case ISpielerPosition.substBack:
                        pos = ServerSpieler.BANK_AB;
                        break;

                    case ISpielerPosition.substForward:
                        pos = ServerSpieler.BANK_ST;
                        break;

                    case ISpielerPosition.substInsideMid:
                        pos = ServerSpieler.BANK_MF;
                        break;

                    case ISpielerPosition.substKeeper:
                        pos = ServerSpieler.BANK_TW;
                        break;

                    case ISpielerPosition.substWinger:
                        pos = ServerSpieler.BANK_FL;
                        break;

                    default:
                        pos = position.getPosition();
                        break;
                }
            } else {
                //Nur echte Spieler + Aufgestellte + Bank berücksichtigen!
                continue;
            }

            serverSpieler.addElement(new ServerSpieler(player, pos));
        }

        sTeam = new ServerTeam(HOVerwaltung.instance().getModel().getAufstellung(),
                               HOVerwaltung.instance().getModel().getBasics().getTeamName(),
                               HOVerwaltung.instance().getModel().getBasics().getManager(),
                               serverSpieler);

        return sTeam;
    }
}
