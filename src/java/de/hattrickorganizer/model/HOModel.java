// %2418151228:de.hattrickorganizer.model%
package de.hattrickorganizer.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import plugins.IHTCalendar;
import plugins.IHelper;
import plugins.ISpieler;
import plugins.ITrainingWeek;
import plugins.IVerein;
import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.logik.EPV;
import de.hattrickorganizer.logik.TrainingsManager;
import de.hattrickorganizer.logik.TrainingsWeekManager;
import de.hattrickorganizer.model.matchlist.Spielplan;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.HelperWrapper;


/**
 * Die Klasse bündelt alle anderen Model, die zu einer HRF Datei gehören. Die Daten können
 * natürlich auch aus der Datenbank kommen
 */
public class HOModel {
    //~ Instance fields ----------------------------------------------------------------------------

    private Lineup m_clAufstellung;
    private Lineup m_clLastAufstellung;
    private Basics m_clBasics;
    private EPV epv = new EPV();
    private Finanzen m_clFinanzen;
//    private HOFriendlyManager m_clFriendlyManager = new HOFriendlyManager();
    private Liga m_clLiga;
    private Spielplan m_clSpielplan;
    private Stadium m_clStadium;
    private Team m_clTeam;
    private Vector<ISpieler> m_vOldSpieler = new Vector<ISpieler>();
    private Vector<ISpieler> m_vSpieler = new Vector<ISpieler>();
    private IVerein m_clVerein;
    private XtraData m_clXtraDaten;
    private int m_iID = -1;

    //~ Constructors -------------------------------------------------------------------------------

    //gibts über den Spielplan
    //private LigaTabelle         m_clLigaTabelle =   null;
    public HOModel() {
        //erst einbauen wenn db angebunden ist
        try {
            m_iID = de.hattrickorganizer.database.DBZugriff.instance().getMaxHrfId() + 1;
        } catch (Exception e) {
        }
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Alle Spieler werden übergeben und die noch aktuellen Spieler entfernt
     *
     * @param spielerVector TODO Missing Constructuor Parameter Documentation
     */
    public final void setAllOldSpieler(Vector<ISpieler> spielerVector) {
        for (int i = 0; i < spielerVector.size(); i++) {
            //Auf alt setzen, die neuen werden gleich entfernt
            ((Spieler) spielerVector.get(i)).setOld(true);

            for (int j = 0; j < m_vSpieler.size(); j++) {
                //Schon in den aktuellen Spielern vorhanden, dann überspringen
                if (((Spieler) spielerVector.get(i)).equals(m_vSpieler.get(j))) {
                    spielerVector.remove(i);

                    //Index einen zurücksetzen, da ein wert gelöscht wurde
                    i--;
                    break;
                }
            }
        }

        m_vOldSpieler = spielerVector;
    }

    /**
     * Gibt alle alten Spieler (nicht mehr im Team befindliche) zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Vector<ISpieler> getAllOldSpieler() {
        return m_vOldSpieler;
    }

    //---------Spieler--------------------------------------

    /**
     * Gibt alle Spieler zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Vector<ISpieler> getAllSpieler() {
        return m_vSpieler;
    }

    /**
     * Setzt neue Aufstellung
     *
     * @param aufstellung TODO Missing Constructuor Parameter Documentation
     */
    public final void setAufstellung(Lineup aufstellung) {
        m_clAufstellung = aufstellung;
    }

    //---------Aufstellung ----------------------------------

    /**
     * Gibt die Aufstellung zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Lineup getAufstellung() {
        return m_clAufstellung;
    }

    /**
     * Setzt neue Basics
     *
     * @param basics TODO Missing Constructuor Parameter Documentation
     */
    public final void setBasics(Basics basics) {
        m_clBasics = basics;
    }

    //----------Basics----------------------------------------

    /**
     * Gibt die Basics zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Basics getBasics() {
        return m_clBasics;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final EPV getEPV() {
        return epv;
    }

    /**
     * Setzt neue Finanzen
     *
     * @param finanzen TODO Missing Constructuor Parameter Documentation
     */
    public final void setFinanzen(Finanzen finanzen) {
        m_clFinanzen = finanzen;
    }

    //-------Finanzen---------------------------------------

    /**
     * Gibt die Finanzen zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Finanzen getFinanzen() {
        return m_clFinanzen;
    }

//    /**
//     * TODO Missing Method Documentation
//     *
//     * @return TODO Missing Return Method Documentation
//     */
//    public final HOFriendlyManager getHOFriendlyManager() {
//        return m_clFriendlyManager;
//    }

    /**
     * Setter for property m_iID.
     *
     * @param m_iID New value of property m_iID.
     */
    public final void setID(int m_iID) {
        this.m_iID = m_iID;
    }

    //------ID-------------------------

    /**
     * Getter for property m_iID.
     *
     * @return Value of property m_iID.
     */
    public final int getID() {
        return m_iID;
    }

    /**
     * Setzt neue Aufstellung
     *
     * @param aufstellung TODO Missing Constructuor Parameter Documentation
     */
    public final void setLastAufstellung(Lineup aufstellung) {
        m_clLastAufstellung = aufstellung;
    }

    /**
     * Gibt die Aufstellung zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Lineup getLastAufstellung() {
        return m_clLastAufstellung;
    }

    /**
     * Setzt neue Basics
     *
     * @param liga TODO Missing Constructuor Parameter Documentation
     */
    public final void setLiga(Liga liga) {
        m_clLiga = liga;
    }

    //----------Liga----------------------------------------

    /**
     * Gibt die Basics zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Liga getLiga() {
        return m_clLiga;
    }

    /**
     * Setzt einen neuen SpielerVector
     *
     * @param spielerVector TODO Missing Constructuor Parameter Documentation
     */
    public final void setSpieler(Vector<ISpieler> spielerVector) {
        m_vSpieler = spielerVector;
    }

    /**
     * Gibt den Spieler mit der ID zurück
     *
     * @param id TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Spieler getSpieler(int id) {
        for (int i = 0; (m_vSpieler != null) && (i < m_vSpieler.size()); i++) {
            if (((Spieler) m_vSpieler.elementAt(i)).getSpielerID() == id) {
                return (Spieler) m_vSpieler.elementAt(i);
            }
        }

        return null;
    }

    /**
     * Setter for property m_clSpielplan.
     *
     * @param m_clSpielplan New value of property m_clSpielplan.
     */
    public final void setSpielplan(de.hattrickorganizer.model.matchlist.Spielplan m_clSpielplan) {
        this.m_clSpielplan = m_clSpielplan;
    }

    //-----------------------Spielplan----------------------------------------//

    /**
     * Getter for property m_clSpielplan.
     *
     * @return Value of property m_clSpielplan.
     */
    public final de.hattrickorganizer.model.matchlist.Spielplan getSpielplan() {
        return m_clSpielplan;
    }

    /**
     * Setzt neues Stadium
     *
     * @param stadium TODO Missing Constructuor Parameter Documentation
     */
    public final void setStadium(Stadium stadium) {
        m_clStadium = stadium;
    }

    //--------Stadium----------------------------------------

    /**
     * Gibt das Stadium zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Stadium getStadium() {
        return m_clStadium;
    }

    /**
     * Setzt neues Team
     *
     * @param team TODO Missing Constructuor Parameter Documentation
     */
    public final void setTeam(Team team) {
        m_clTeam = team;
    }

    //----------Team----------------------------------------

    /**
     * Gibt das Team zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Team getTeam() {
        return m_clTeam;
    }

    /**
     * Gibt den Trainer zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Spieler getTrainer() {
    	Spieler trainer = null;
        for (int i = 0; (m_vSpieler != null) && (i < m_vSpieler.size()); i++) {
            if (((Spieler) m_vSpieler.elementAt(i)).isTrainer()) {
            	if (trainer==null) {
            		trainer = (Spieler) m_vSpieler.elementAt(i);
            	} else {
					Spieler tmp = (Spieler) m_vSpieler.elementAt(i);
            		if (tmp.getTrainer()>trainer.getTrainer()) {
            			trainer = tmp;
            		}
            	}
            }
        }
        return trainer;
    }

    /**
     * Setzt neuen Verein
     *
     * @param verein TODO Missing Constructuor Parameter Documentation
     */
    public final void setVerein(IVerein verein) {
        m_clVerein = verein;
    }

    //----------Verein----------------------------------------

    /**
     * Gibt den Verein zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public final IVerein getVerein() {
        return m_clVerein;
    }

    /**
     * Setter for property m_clXtraDaten.
     *
     * @param m_clXtraDaten New value of property m_clXtraDaten.
     */
    public final void setXtraDaten(de.hattrickorganizer.model.XtraData m_clXtraDaten) {
        this.m_clXtraDaten = m_clXtraDaten;
    }

    /**
     * Getter for property m_clXtraDaten.
     *
     * @return Value of property m_clXtraDaten.
     */
    public final de.hattrickorganizer.model.XtraData getXtraDaten() {
        return m_clXtraDaten;
    }

    /**
     * Fügt einen Spieler hinzu (wofür auch immer...)
     *
     * @param spieler TODO Missing Constructuor Parameter Documentation
     */
    public final void addSpieler(Spieler spieler) {
        m_vSpieler.add(spieler);
    }

    /**
     * berechnet die Subskills zu allen Spielern players calc subskill func
     */
    public final void calcSubskills() {
        final Vector<ISpieler> vSpieler = getAllSpieler();
        final java.sql.Timestamp calcDate = m_clBasics.getDatum();

        /*
           //null für aktuelles HRF-Model
           java.sql.Timestamp       calcDate    =   null;
           if ( this.getBasics ().getDatum ().before ( database.DBZugriff.instance ().getBasics ( database.DBZugriff.instance ().getHRF_IDByDate ()  ).getDatum () ) )
           {
               //nur Datum setzen wenn nicht aktuellstes HRF berechnet wird
               calcDate    =   m_clBasics.getDatum ();
           }
         */
        final int previousHrfId = de.hattrickorganizer.database.DBZugriff.instance().getPreviousHRF(m_iID);
        final Timestamp previousTrainingDate = de.hattrickorganizer.database.DBZugriff.instance()
                                                                                      .getXtraDaten(previousHrfId)
                                                                                      .getTrainingDate();
        final Timestamp actualTrainingDate = m_clXtraDaten.getTrainingDate();

        int trainingType = 0;

        // Training Happened, check if we have all the hrf or we have a missing week!
        if ((previousTrainingDate != null) && (!previousTrainingDate.equals(actualTrainingDate))) {
            final Calendar cal = Calendar.getInstance(Locale.UK);
            cal.setFirstDayOfWeek(Calendar.SUNDAY);
            cal.setTimeInMillis(actualTrainingDate.getTime());

            final int actWeek = cal.get(Calendar.WEEK_OF_YEAR);
            final int actYear = cal.get(Calendar.YEAR);
            cal.setTimeInMillis(previousTrainingDate.getTime());
            cal.add(Calendar.WEEK_OF_YEAR, 1);

            final int prevWeek = cal.get(Calendar.WEEK_OF_YEAR);
            final int prevYear = cal.get(Calendar.YEAR);

            if ((actWeek == prevWeek) && (actYear == prevYear)) {
                trainingType = 1;
            } else {
                trainingType = 2;
            }
        }

        final Map<String,ISpieler> players = new HashMap<String,ISpieler>();

        for (Iterator<ISpieler> iter = DBZugriff.instance().getSpieler(previousHrfId).iterator();
             iter.hasNext();) {
            final ISpieler element = iter.next();
            players.put(element.getSpielerID() + "", element);
        }

        for (int i = 0; i < vSpieler.size(); i++) {
            try {
                final Spieler player = (de.hattrickorganizer.model.Spieler) vSpieler.get(i);
                ISpieler old = players.get("" + player.getSpielerID());

                if (old == null) {
                	if (TrainingsManager.TRAININGDEBUG)
                		HOLogger.instance().debug(HOModel.class, "Old player for id "+player.getSpielerID()+" = null");
                    old = new Spieler();
                    old.setSpielerID(-1);
                }

                switch (trainingType) {
                    // Same week just copy settings
                    case 0: {
                        player.copySubSkills(old);
                        break;
                    }

                    // Missing week, full recalculation
                    case 2: {
                        player.calcFullSubskills(old, m_clVerein.getCoTrainer(),
                                                 getTrainer().getTrainer(),
                                                 m_clTeam.getTrainingslevel(),
                                                 m_clTeam.getStaminaTrainingPart(),
                                                 calcDate);
                        break;
                    }

                    // Previous week ok, incremental calculation
                    case 1: {
                        player.calcIncrementalSubskills(old, m_clVerein.getCoTrainer(),
                                                        getTrainer().getTrainer(),
                                                        m_clTeam.getTrainingslevel(),
                                                        m_clTeam.getStaminaTrainingPart(),
                                                        m_iID);
                        break;
                    }

                    default:
                        break;
                }

				if (TrainingsManager.TRAININGDEBUG) {
	                /**
	                 * Start of debug
	                 */
					IHelper helper = HelperWrapper.instance();
					IHTCalendar htcP;
	                String htcPs = "";
	                if (previousTrainingDate != null) {
	                	htcP = helper.createTrainingCalendar(previousTrainingDate);
	                	htcPs = " ("+htcP.getHTSeason()+"."+htcP.getHTWeek()+")";
	                }
	                IHTCalendar htcA = helper.createTrainingCalendar(actualTrainingDate);
	            	String htcAs = " ("+htcA.getHTSeason()+"."+htcA.getHTWeek()+")";
	                IHTCalendar htcC = helper.createTrainingCalendar(calcDate);
	            	String htcCs = " ("+htcC.getHTSeason()+"."+htcC.getHTWeek()+")";

	            	ITrainingWeek trWeek = TrainingsWeekManager.instance().getTrainingWeek(m_iID);
	                HOLogger.instance().debug(HOModel.class,
	                		"TrainingType="+trainingType+", trArt="+(trWeek==null?"null":""+trWeek.getTyp())
	                			+ ", numPl="+vSpieler.size()+", calcDate="+calcDate.toLocaleString()+htcCs
	                			+ ", act="+actualTrainingDate.toLocaleString() +htcAs
	                			+ ", prev="+(previousTrainingDate==null?"null":previousTrainingDate.toLocaleString()+htcPs)
	                			+ " ("+previousHrfId+")");

	                if (trainingType > 0)
	                	logPlayerProgress (old, player);

	                /**
	                 * End of debug
	                 */
				}
            } catch (Exception e) {
                HOLogger.instance().log(getClass(),e);
                HOLogger.instance().log(getClass(),"Model calcSubskill: " + e);
            }
        }

        //Spieler
        DBZugriff.instance().saveSpieler(m_iID, m_vSpieler, m_clBasics.getDatum());
    }

    private void logPlayerProgress (ISpieler before, ISpieler after) {
    	int playerID = after.getSpielerID();
    	String playerName = after.getName();
    	ITrainingWeek train = TrainingsWeekManager.instance().getTrainingWeek(m_iID);
    	int trLevel = train.getIntensitaet();
    	int trArt = train.getTyp();
    	String trArtString = HelperWrapper.instance().getNameForTraining(trArt);
    	int trStPart = train.getStaminaTrainingPart();
    	int age = after.getAlter();
    	int skill = -1;
		int beforeSkill = 0;
		int afterSkill = 0;
    	switch (trArt) {
    	case ISpieler.TA_EXTERNALATTACK:
    	case ISpieler.FLUEGELSPIEL:
    		skill = ISpieler.SKILL_FLUEGEL;
    		break;
    	case ISpieler.STANDARDS:
    		skill = ISpieler.SKILL_STANDARDS;
    		break;
    	case ISpieler.TA_ABWEHRVERHALTEN:
    	case ISpieler.VERTEIDIGUNG:
    		skill = ISpieler.SKILL_VERTEIDIGUNG;
    		break;
    	case ISpieler.CHANCENVERWERTUNG:
    	case ISpieler.SCHUSSTRAINING:
    		skill = ISpieler.SKILL_TORSCHUSS;
    		break;
    	case ISpieler.PASSPIEL:
    	case ISpieler.TA_STEILPAESSE:
    		skill = ISpieler.SKILL_PASSSPIEL;
    		break;
    	case ISpieler.SPIELAUFBAU:
    		skill = ISpieler.SKILL_SPIELAUFBAU;
    		break;
    	case ISpieler.TORWART:
    		skill = ISpieler.SKILL_TORWART;
    		break;
    	case ISpieler.KONDITION:
    		skill = ISpieler.SKILL_KONDITION;
    		break;
		}
    	if (skill >= 0) {
    		beforeSkill = before.getValue4Skill4(skill);
    		afterSkill = after.getValue4Skill4(skill);
    		int beforeStamina = before.getKondition();
    		int afterStamina = after.getKondition();
    		double beforeSub = before.getSubskill4Pos(skill);
    		double afterSub = after.getSubskill4Pos(skill);


    		HOLogger.instance().info(getClass(), "TrLog:" + m_iID + "|"
    				+ m_clBasics.getSeason() + "|" + m_clBasics.getSpieltag() + "|"
    				+ playerID + "|" + playerName + "|" + age + "|"
    				+ trArt + "|" + trArtString + "|" + trLevel + "|" + trStPart + "|"
    				+ beforeStamina + "|" + afterStamina + "|"
    				+ beforeSkill + "|" + de.hattrickorganizer.tools.Helper.round(beforeSub, 2) + "|"
    				+ afterSkill + "|" + de.hattrickorganizer.tools.Helper.round(afterSub, 2)
    				);
    	}
    }
    /**
     * TODO Missing Method Documentation
     */
    public final void loadStdAufstellung() {
        m_clAufstellung = de.hattrickorganizer.database.DBZugriff.instance().getAufstellung(-1,
                                                                                            Lineup.DEFAULT_NAME);

        //prüfen ob alle aufgstellen Spieler noch existieren
        m_clAufstellung.checkAufgestellteSpieler();
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void loadStdLastAufstellung() {
        m_clLastAufstellung = de.hattrickorganizer.database.DBZugriff.instance().getAufstellung(-1,
                                                                                                Lineup.DEFAULT_NAMELAST);

        //prüfen ob alle aufgstellen Spieler noch existieren
        m_clLastAufstellung.checkAufgestellteSpieler();
    }

    /**
     * Entfernt einen Spieler
     *
     * @param spieler TODO Missing Constructuor Parameter Documentation
     */
    public final void removeSpieler(Spieler spieler) {
        m_vSpieler.remove(spieler);
    }

    //---------------------------Tabelle---------------------------------------

    /**
     * Getter for property m_clLigaTabelle.
     */

    /*    public model.ligaTabelle.LigaTabelle getLigaTabelle ()
       {
           return m_clLigaTabelle;
       }
     */

    /**
     * Setter for property m_clLigaTabelle.
     */

    /*    public void setLigaTabelle (model.ligaTabelle.LigaTabelle m_clLigaTabelle)
       {
           this.m_clLigaTabelle = m_clLigaTabelle;
       }
     */

    /////////////////////////////Laden/Speichern/////////////////////

    /**
     * speichert das Model in der DB
     */

    //java.sql.Timestamp hrfDateiDatum )
    public final synchronized void saveHRF() {
        //HRF //TODO Datum angabe in Modell aus file last modified übernehmen ersetzen
        de.hattrickorganizer.database.DBZugriff.instance().saveHRF(m_iID,
                                                                   java.text.DateFormat.getDateTimeInstance()
                                                                                       .format(new java.util.Date(System
                                                                                                                  .currentTimeMillis())),
                                                                   m_clBasics.getDatum());

        //java.text.DateFormat.getDateTimeInstance ().format( new java.sql.Timestamp(  System.currentTimeMillis () ) ), hrfDateiDatum );
        //basics
        de.hattrickorganizer.database.DBZugriff.instance().saveBasics(m_iID, m_clBasics);

        //Verein
        de.hattrickorganizer.database.DBZugriff.instance().saveVerein(m_iID, m_clVerein);

        //Team
        de.hattrickorganizer.database.DBZugriff.instance().saveTeam(m_iID, m_clTeam);

        //Finanzen
        de.hattrickorganizer.database.DBZugriff.instance().saveFinanzen(m_iID, m_clFinanzen,
                                                                        m_clBasics.getDatum());

        //Stadion
        de.hattrickorganizer.database.DBZugriff.instance().saveStadion(m_iID, m_clStadium);

        //Liga
        de.hattrickorganizer.database.DBZugriff.instance().saveLiga(m_iID, m_clLiga);

        //Aufstellung + aktu Sys als Standard saven
        de.hattrickorganizer.database.DBZugriff.instance().saveAufstellung(m_iID, m_clAufstellung,
                                                                           Lineup.DEFAULT_NAME);

        //Aufstellung + aktu Sys als Standard saven
        de.hattrickorganizer.database.DBZugriff.instance().saveAufstellung(m_iID,
                                                                           m_clLastAufstellung,
                                                                           Lineup.DEFAULT_NAMELAST);

        //Xtra Daten
        de.hattrickorganizer.database.DBZugriff.instance().saveXtraDaten(m_iID, m_clXtraDaten);

        //Spieler
        de.hattrickorganizer.database.DBZugriff.instance().saveSpieler(m_iID, m_vSpieler,
                                                                       m_clBasics.getDatum());

        //Training->Subskillberechnung, TrainingsVec neu berechnen!
        //        logik.TrainingsManager.instance().fillWithData( logik.TrainingsManager.instance().calculateTrainings( database.DBZugriff.instance().getTrainingsVector() ) );
        //        calcSubskills(); //Spieler werden dann dort gespeichert!
    }

    /**
     * speichert das Model in der DB
     */

    /*    public synchronizd void save(  )
       {
           //HRF //TODO Datum angabe in Modell aus file last modified übernehmen ersetzen
           database.DBZugriff.instance ().saveHRF ( m_iID, java.text.DateFormat.getDateTimeInstance ().format( new java.util.Date(  System.currentTimeMillis () ) ), m_clBasics.getDatum () );
           //basics
           database.DBZugriff.instance ().saveBasics ( m_iID, m_clBasics );
           //Verein
           database.DBZugriff.instance ().saveVerein ( m_iID, m_clVerein );
           //Team
           database.DBZugriff.instance ().saveTeam ( m_iID, m_clTeam );
           //Finanzen
           database.DBZugriff.instance ().saveFinanzen ( m_iID, m_clFinanzen, m_clBasics.getDatum () );
           //Stadion
           database.DBZugriff.instance ().saveStadion ( m_iID, m_clStadium );
           //Liga
           database.DBZugriff.instance ().saveLiga ( m_iID, m_clLiga );
           //Spieler
           database.DBZugriff.instance ().saveSpieler ( m_iID, m_vSpieler, m_clBasics.getDatum () );
           //Aufstellung + aktu Sys als Standard saven
           database.DBZugriff.instance ().saveAufstellung ( Aufstellung.NO_HRF_VERBINDUNG, m_clAufstellung, Aufstellung.DEFAULT_NAME );
           //Aufstellung + aktu Sys als Standard saven
           database.DBZugriff.instance ().saveAufstellung ( Aufstellung.NO_HRF_VERBINDUNG, m_clLastAufstellung, Aufstellung.DEFAULT_NAMELAST );
           //Xtra Daten
           database.DBZugriff.instance ().saveXtraDaten ( m_iID, m_clXtraDaten );
       }
     */

    /**
     * Speichert den Spielplan in der DB
     */
    public final synchronized void saveSpielplan2DB() {
        if (m_clSpielplan != null) {
            de.hattrickorganizer.database.DBZugriff.instance().storeSpielplan(m_clSpielplan);
        }
    }
}
