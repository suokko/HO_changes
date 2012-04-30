// %2418151228:de.hattrickorganizer.model%
package ho.core.model;

import ho.core.constants.TrainingType;
import ho.core.constants.player.PlayerSkill;
import ho.core.db.DBManager;
import ho.core.epv.EPV;
import ho.core.model.misc.Basics;
import ho.core.model.misc.Finanzen;
import ho.core.model.misc.Verein;
import ho.core.model.player.Spieler;
import ho.core.model.series.Liga;
import ho.core.training.TrainingPerWeek;
import ho.core.training.TrainingManager;
import ho.core.training.TrainingWeekManager;
import ho.core.util.HOLogger;
import ho.core.util.HTCalendar;
import ho.core.util.HTCalendarFactory;
import ho.core.util.HelperWrapper;
import ho.module.lineup.Lineup;
import ho.module.series.Spielplan;
import ho.tool.arenasizer.Stadium;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;


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
    private Liga m_clLiga;
    private Spielplan m_clSpielplan;
    private Stadium m_clStadium;
    private Team m_clTeam;
    private Vector<Spieler> m_vOldSpieler = new Vector<Spieler>();
    private Vector<Spieler> m_vSpieler = new Vector<Spieler>();
    private Verein m_clVerein;
    private XtraData m_clXtraDaten;
    private int m_iID = -1;

    //~ Constructors -------------------------------------------------------------------------------

    //gibts über den Spielplan
    //private LigaTabelle         m_clLigaTabelle =   null;
    public HOModel() {
        //erst einbauen wenn db angebunden ist
        try {
            m_iID = DBManager.instance().getMaxHrfId() + 1;
        } catch (Exception e) {
        }
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Alle Spieler werden übergeben und die noch aktuellen Spieler entfernt
     *
     * @param spielerVector TODO Missing Constructuor Parameter Documentation
     */
    public final void setAllOldSpieler(Vector<Spieler> spielerVector) {
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
    public final Vector<Spieler> getAllOldSpieler() {
        return m_vOldSpieler;
    }

    //---------Spieler--------------------------------------

    /**
     * Gibt alle Spieler zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Vector<Spieler> getAllSpieler() {
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
    public final void setSpieler(Vector<Spieler> spielerVector) {
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
    public final void setSpielplan(ho.module.series.Spielplan m_clSpielplan) {
        this.m_clSpielplan = m_clSpielplan;
    }

    //-----------------------Spielplan----------------------------------------//

    /**
     * Getter for property m_clSpielplan.
     *
     * @return Value of property m_clSpielplan.
     */
    public final ho.module.series.Spielplan getSpielplan() {
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
    public final void setVerein(Verein verein) {
        m_clVerein = verein;
    }

    //----------Verein----------------------------------------

    /**
     * Gibt den Verein zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Verein getVerein() {
        return m_clVerein;
    }

    /**
     * Setter for property m_clXtraDaten.
     *
     * @param m_clXtraDaten New value of property m_clXtraDaten.
     */
    public final void setXtraDaten(ho.core.model.XtraData m_clXtraDaten) {
        this.m_clXtraDaten = m_clXtraDaten;
    }

    /**
     * Getter for property m_clXtraDaten.
     *
     * @return Value of property m_clXtraDaten.
     */
    public final ho.core.model.XtraData getXtraDaten() {
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
        final Vector<Spieler> vSpieler = getAllSpieler();
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
        final int previousHrfId = DBManager.instance().getPreviousHRF(m_iID);
        final Timestamp previousTrainingDate = DBManager.instance()
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

        final Map<String,Spieler> players = new HashMap<String,Spieler>();

        for (Iterator<Spieler> iter = DBManager.instance().getSpieler(previousHrfId).iterator();
             iter.hasNext();) {
            final Spieler element = iter.next();
            players.put(element.getSpielerID() + "", element);
        }

        for (int i = 0; i < vSpieler.size(); i++) {
            try {
                final Spieler player = (ho.core.model.player.Spieler) vSpieler.get(i);
                Spieler old = players.get("" + player.getSpielerID());

                if (old == null) {
                	if (TrainingManager.TRAININGDEBUG)
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

				if (TrainingManager.TRAININGDEBUG) {
	                /**
	                 * Start of debug
	                 */
					HelperWrapper helper = HelperWrapper.instance();
					HTCalendar htcP;
	                String htcPs = "";
	                if (previousTrainingDate != null) {
	                	htcP = HTCalendarFactory.createTrainingCalendar(new Date(previousTrainingDate.getTime()));
	                	htcPs = " ("+htcP.getHTSeason()+"."+htcP.getHTWeek()+")";
	                }
	                HTCalendar htcA = HTCalendarFactory.createTrainingCalendar(new Date((actualTrainingDate.getTime())));
	            	String htcAs = " ("+htcA.getHTSeason()+"."+htcA.getHTWeek()+")";
	                HTCalendar htcC = HTCalendarFactory.createTrainingCalendar(new Date((calcDate.getTime())));
	            	String htcCs = " ("+htcC.getHTSeason()+"."+htcC.getHTWeek()+")";

	            	TrainingPerWeek trWeek = TrainingWeekManager.instance().getTrainingWeek(m_iID);
	                HOLogger.instance().debug(HOModel.class,
	                		"TrainingType="+trainingType+", trArt="+(trWeek==null?"null":""+trWeek.getTrainingType())
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
        DBManager.instance().saveSpieler(m_iID, m_vSpieler, m_clBasics.getDatum());
    }

    private void logPlayerProgress (Spieler before, Spieler after) {
    	int playerID = after.getSpielerID();
    	String playerName = after.getName();
    	TrainingPerWeek train = TrainingWeekManager.instance().getTrainingWeek(m_iID);
    	int trLevel = train.getTrainingIntensity();
    	int trArt = train.getTrainingType();
    	String trArtString = TrainingType.toString(trArt);
    	int trStPart = train.getStaminaPart();
    	int age = after.getAlter();
    	int skill = -1;
		int beforeSkill = 0;
		int afterSkill = 0;
    	switch (trArt) {
    	case TrainingType.WING_ATTACKS:
    	case TrainingType.CROSSING_WINGER:
    		skill = PlayerSkill.WINGER;
    		break;
    	case TrainingType.SET_PIECES:
    		skill = PlayerSkill.SET_PIECES;
    		break;
    	case TrainingType.DEF_POSITIONS:
    	case TrainingType.DEFENDING:
    		skill = PlayerSkill.DEFENDING;
    		break;
    	case TrainingType.SHOOTING:
    	case TrainingType.SCORING:
    		skill = PlayerSkill.SCORING;
    		break;
    	case TrainingType.SHORT_PASSES:
    	case TrainingType.THROUGH_PASSES:
    		skill = PlayerSkill.PASSING;
    		break;
    	case TrainingType.PLAYMAKING:
    		skill = PlayerSkill.PLAYMAKING;
    		break;
    	case TrainingType.GOALKEEPING:
    		skill = PlayerSkill.KEEPER;
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
    				+ beforeSkill + "|" + ho.core.util.Helper.round(beforeSub, 2) + "|"
    				+ afterSkill + "|" + ho.core.util.Helper.round(afterSub, 2)
    				);
    	}
    }
    /**
     * TODO Missing Method Documentation
     */
    public final void loadStdAufstellung() {
        m_clAufstellung = DBManager.instance().getAufstellung(-1,
                                                                                            Lineup.DEFAULT_NAME);

        //prüfen ob alle aufgstellen Spieler noch existieren
        m_clAufstellung.checkAufgestellteSpieler();
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void loadStdLastAufstellung() {
        m_clLastAufstellung = DBManager.instance().getAufstellung(-1,
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
   
    /**
     * speichert das Model in der DB
     */

    //java.sql.Timestamp hrfDateiDatum )
    public final synchronized void saveHRF() {
        //HRF //TODO Datum angabe in Modell aus file last modified übernehmen ersetzen
        DBManager.instance().saveHRF(m_iID,
        		java.text.DateFormat.getDateTimeInstance().format(new java.util.Date(
        				System.currentTimeMillis())), m_clBasics.getDatum());

        //basics
        DBManager.instance().saveBasics(m_iID, m_clBasics);
        //Verein
        DBManager.instance().saveVerein(m_iID, m_clVerein);
        //Team
        DBManager.instance().saveTeam(m_iID, m_clTeam);
        //Finanzen
        DBManager.instance().saveFinanzen(m_iID, m_clFinanzen, m_clBasics.getDatum());
        //Stadion
        DBManager.instance().saveStadion(m_iID, m_clStadium);
        //Liga
        DBManager.instance().saveLiga(m_iID, m_clLiga);
        //Aufstellung + aktu Sys als Standard saven
        DBManager.instance().saveAufstellung(m_iID, m_clAufstellung, Lineup.DEFAULT_NAME);
        //Aufstellung + aktu Sys als Standard saven
        DBManager.instance().saveAufstellung(m_iID, m_clLastAufstellung, Lineup.DEFAULT_NAMELAST);
        //Xtra Daten
        DBManager.instance().saveXtraDaten(m_iID, m_clXtraDaten);
        //Spieler
        DBManager.instance().saveSpieler(m_iID, m_vSpieler, m_clBasics.getDatum());
    }

    /**
     * Speichert den Spielplan in der DB
     */
    public final synchronized void saveSpielplan2DB() {
        if (m_clSpielplan != null) 
            DBManager.instance().storeSpielplan(m_clSpielplan);
    }
}
