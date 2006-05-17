// %2732728450:de.hattrickorganizer.logik%
package de.hattrickorganizer.logik;

import gui.UserParameter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import javax.swing.JOptionPane;

import plugins.IHOMiniModel;
import plugins.ISpieler;
import plugins.ITeam;
import plugins.ITrainingPerPlayer;
import plugins.ITrainingWeek;
import plugins.ITrainingsManager;
import de.hattrickorganizer.gui.HOMainFrame;
import de.hattrickorganizer.model.HOMiniModel;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.TrainingPerPlayer;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.HelperWrapper;


/**
 * Class that extract data from Database and calculates TrainingWeek and TrainingPoints earned from
 * players
 *
 * @author humorlos, Dragettho, thetom
 */
public class TrainingsManager implements ITrainingsManager {
    //~ Static fields/initializers -----------------------------------------------------------------

    private static TrainingsManager m_clInstance;

    //~ Instance fields ----------------------------------------------------------------------------

    /** HO Model */
    private IHOMiniModel p_IHMM_HOMiniModel;
    private Map matchMap;
    private Map rosterMap;

    /** Training Point */
    private TrainingPoint points = new TrainingPoint();
    private TrainingsWeekManager weekManager;
    private float p_f_schusstraining_Standard = 0.6f;

    //~ Constructors -------------------------------------------------------------------------------

    //variable for adjusting amount of set pieces when training scoring

    /**
     * Creates a new instance of TrainingsManager
     */
    private TrainingsManager() {
        this.p_IHMM_HOMiniModel = HOMiniModel.instance();
        this.weekManager = TrainingsWeekManager.instance();
        this.rosterMap = new HashMap();
        this.matchMap = new HashMap();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * liefert instance vom Trainingsmanager  vor der ersten Nutzun noch fillWithData(
     * vorgabeTrainings ) aufrufen
     *
     * @return instance of NewTrainingManager
     */
    public static TrainingsManager instance() {
        if (m_clInstance == null) {
            m_clInstance = new TrainingsManager();
        }

        return m_clInstance;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param hrfId TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public ITrainingWeek getLastTrainingWeek(int hrfId) {
        return this.weekManager.getTrainingWeek(hrfId);
    }

    /**
     * DOCUMENT ME!
     *
     * @return TODO Missing Return Method Documentation
     *
     * @deprecated returns current PlayerTrainingVector calculates new vector if current is null
     */
    public Vector getPlayerTrainingsVector() {
        return new Vector();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public plugins.ITrainingPoint getTrainingPoint() {
        return points;
    }

    // ------------------------------ Training Week Calculation ----------------------------------------------------
    public Vector getTrainingsVector() {
        return this.weekManager.getTrainingsVector();
    }

    /**
     * DOCUMENT ME!
     *
     * @param inputTrainings Trainingweeks to use
     * @param zeitpunkt null for current, time til training is calculated
     *
     * @return Vector of ITrainingPerPlayer Object for all Players
     *
     * @deprecated calculates training 4 all players Trainings ( Weeks) must be calculated first!
     */
    public Vector calculateData(Vector inputTrainings, Timestamp zeitpunkt) {
        return new Vector();
    }

    /**
     * DOCUMENT ME!
     *
     * @param inputSpieler Player to use
     * @param inputTrainings preset Trainingweeks
     * @param zeitpunkt estamiated time, null for current
     *
     * @return TrainingPerPlayer
     *
     * @deprecated liefert die komplette Trainings in jedem skill eines Spielers calculates
     *             TRaining for given Player for each skill
     */
    public ITrainingPerPlayer calculateDataForPlayer(ISpieler inputSpieler, Vector inputTrainings,
                                                     Timestamp zeitpunkt) {
        return new TrainingPerPlayer();
    }

    //------------------------------------------ Deprecated Methods -------------------------------------------------

    /**
     * liefert die komplette Trainings in jedem skill eines Spielers calculates TRaining for given
     * Player for each skill
     *
     * @param inputSpieler Player to use
     * @param inputTrainings preset Trainingweeks
     * @param zeitpunkt estamiated time, null for current
     *
     * @return TrainingPerPlayer
     */
    public ITrainingPerPlayer calculateFullTrainingForPlayer(ISpieler inputSpieler,
                                                             Vector inputTrainings,
                                                             Timestamp zeitpunkt) {
        //playerID HIER SETZEN
        final int playerID = inputSpieler.getSpielerID();
        final ISpieler spieler = inputSpieler;

        //int f?r den trainierten skill // int for the trained skill
        int trainskill = -1;
        int trainskill2 = -1;

        //int f?r den trainierten sekund?r skill - momentan nur f?r Schusstraining // int for the trained secondary skill
        double actualTrainValue = 0;

        //bisherige trainingswert f?r den skill //trainingsvalue until now in the trained primary skill
        double actualTrainValue2 = 0;

        if (zeitpunkt == null) {
        	Calendar c = Calendar.getInstance();
        	c.add(Calendar.HOUR,UserParameter.instance().TimeZoneDifference);
            zeitpunkt = new Timestamp(c.getTimeInMillis());
        }

        final TrainingPerPlayer output = new TrainingPerPlayer();
        output.setSpieler(spieler);

        //Vector mit allen aktuellen Spielern
        //alle Trainings durchlaufen
        //vector with all actual players
        //run through all trainings
        for (Iterator i = inputTrainings.iterator(); i.hasNext();) {
            //Reset all Variables
            trainskill = -1;
            trainskill2 = -1;
            actualTrainValue = 0;
            actualTrainValue2 = 0;

            //holen des gerade abzuarbeitenden trainings
            //get training to consider this round of the loop
            final ITrainingWeek train = (ITrainingWeek) i.next();

            if (train.getTyp() == -1) {
                continue;
            }

            //Kalenderwerte setzen
            //set calendar values
            final Calendar trainingDate = Calendar.getInstance(Locale.UK);
            trainingDate.setFirstDayOfWeek(Calendar.SUNDAY);

            try {
                trainingDate.setTime(p_IHMM_HOMiniModel.getXtraDaten().getTrainingDate());
            } catch (Exception e) {
                return output;
            }

            trainingDate.set(Calendar.YEAR, train.getYear());
            trainingDate.set(Calendar.WEEK_OF_YEAR, train.getWeek());

            //zeitpunkt means timestamp
            //falls calendar > zeitpunkt -> keine abfragen
            //in case of calendar> zeitpunt -> no query
            if (trainingDate.getTimeInMillis() >= zeitpunkt.getTime()) {
                //falls aktuelles Training schon nach dem Zeitpunkt w?re -> beenden, da die folgenden Trainings auch nachher sind
                // in case aktuell training already after the timestamp -> quit, because all following trainings would be after the timestamp too
                return output;
            }

            //EINSCHR?NKEN AUF BESTIMMTE POS CODES IN DER ABFRAGE
            //reduce database query to certain position codes
            //Spielaufbau //playmaking
            if (train.getTyp() == ITeam.TA_SPIELAUFBAU) {
                trainskill = ISpieler.SKILL_SPIELAUFBAU;
                actualTrainValue = output.getSA();

                //Verteidigung //defense
            } else if (train.getTyp() == ITeam.TA_VERTEIDIGUNG) {
                trainskill = ISpieler.SKILL_VERTEIDIGUNG;
                actualTrainValue = output.getVE();

                //Flankenl?ufe // wing
            } else if (train.getTyp() == ITeam.TA_FLANKEN) {
                trainskill = ISpieler.SKILL_FLUEGEL;
                actualTrainValue = output.getFL();

                //Passspiel // passing
            } else if (train.getTyp() == ITeam.TA_PASSSPIEL) {
                trainskill = ISpieler.SKILL_PASSSPIEL;
                actualTrainValue = output.getPS();

                //Torwart				//keeper
            } else if (train.getTyp() == ITeam.TA_TORWART) {
                trainskill = ISpieler.SKILL_TORWART;
                actualTrainValue = output.getTW();

                //Chancenverwertung //Torschuss //scorring
            } else if (train.getTyp() == ITeam.TA_CHANCEN) {
                trainskill = ISpieler.SKILL_TORSCHUSS;
                actualTrainValue = output.getTS();

                //Standardsituationen //set pieces
            } else if (train.getTyp() == ITeam.TA_STANDARD) {
                trainskill = ISpieler.SKILL_STANDARDS;
                actualTrainValue = output.getST();
            } else if (train.getTyp() == ITeam.TA_SCHUSSTRAINING) {
                //Schusstraining == ausnahme nachher !!!! //shooting
                trainskill = ISpieler.SKILL_TORSCHUSS;
                trainskill2 = ISpieler.SKILL_STANDARDS;
                actualTrainValue = output.getTS();
                actualTrainValue2 = output.getST();
            } else if (train.getTyp() == ITeam.TA_ABWEHRVERHALTEN) {
                //Abwehrverhalten //defensive positions
                trainskill = ISpieler.SKILL_VERTEIDIGUNG;
                actualTrainValue = output.getVE();

                //Steilpaesse //through passes
            } else if (train.getTyp() == ITeam.TA_STEILPAESSE) {
                trainskill = ISpieler.SKILL_PASSSPIEL;
                actualTrainValue = output.getPS();

                //Stamina
            } else if (train.getTyp() == ITeam.TA_KONDITION) {
                trainskill = ISpieler.SKILL_KONDITION;
                actualTrainValue = output.getKO();
            } else if (train.getTyp() == ITeam.TA_EXTERNALATTACK) {
                //Lateral Offensive
                trainskill = ISpieler.SKILL_FLUEGEL;
                actualTrainValue = output.getFL();
            }

            Date skilluptime = getLastSkillupDate(spieler, trainskill, zeitpunkt);

            //falls aktuelles Training vor dem letztem Skillup
            //in case of aktual training before last skillup
            if (trainingDate.getTimeInMillis() > skilluptime.getTime()) {
                try {
                    final Map rosterStatus = getTeamRosterStatus(train.getHrfId());
                    final Integer status = (Integer) rosterStatus.get(playerID + "");

                    // Player was not on roster at time of training
                    if (status == null) {
                        continue;
                    }

                    // Or injured (+1 player trains)
                    if (status.intValue() > 1) {
                        continue;
                    }

                    double weekPoints = 0;
                    final ResultSet matchRS = p_IHMM_HOMiniModel.getAdapter().executeQuery(createQuery(trainingDate));

                    if (matchRS == null) {
                        //Falls nichts geliefert wurde // in case of no return values
                        continue;
                    }

                    final List matches = new ArrayList();

                    while (matchRS.next()) {
                        final int matchId = matchRS.getInt("MATCHID");
                        matches.add(new Integer(matchId));
                    }

                    try {
                        matchRS.close();
                    } catch (Exception e1) {
                    }

                    //Abarbeiten ob der Spieler in den 2 besagtem Matches auf einem entsprechenden Positionscode gespielt haben
                    //look through if the player has played in the corresponding week on a position with the corresponding pos code.
                    final Iterator iter = matches.iterator();

                    while (iter.hasNext()) {
                        final Integer matchId = (Integer) iter.next();
                        final Map matchData = getMatchData(matchId.intValue());
                                                
                        Integer playerMatchPosition = (Integer) matchData.get(new Integer(playerID));

                        // Player not played, position is 0
                        if (playerMatchPosition == null) {
                            playerMatchPosition = new Integer(0);
                        }

                        // Get point for the position and primary training type
                        final Double value = points.getTrainingPoint(train.getTyp(),
                                                                     playerMatchPosition);

                        // If player received training for this game and has not received points the the previous
                        if ((value.doubleValue() > 0) && (weekPoints == 0)) {
                            double d = actualTrainValue
                                       + ((train.getIntensitaet() * value.doubleValue()) / 100);

                            //Setzen der Berechneten Werte f?r den ersten skill
                            //setting calculated values for the primary skill
                            updateTrainingPerPlayer(output, train.getTyp(), d);

                            // update points earned this week
                            weekPoints = d;
                        }

                        //If scoring training is exercised, all players must have 50% Standards training too
                        if (trainskill2 != -1) {
                            skilluptime = getLastSkillupDate(spieler, trainskill2, zeitpunkt);

                            if (!skilluptime.after(trainingDate.getTime())) {
                                //Spieler aktuell noch vorhanden //player still in squad
                                final double d = actualTrainValue2
                                                 + ((train.getIntensitaet() * p_f_schusstraining_Standard) / 100);

                                //SETZTEN Des neuen trainingswertes
                                //set new train values
                                if (train.getTyp() == ITeam.TA_SCHUSSTRAINING) {
                                    output.setST(d);
                                }
                            }
                        }
                    }

                    HOLogger.instance().log(getClass(),output);
                } catch (SQLException se) {
                    HOLogger.instance().log(getClass(),se);

                    final StackTraceElement[] aste = se.getStackTrace();
                    String msg = "";

                    for (int j = 0; j < aste.length; j++) {
                        msg = msg + "\n" + aste[j];
                    }

                    p_IHMM_HOMiniModel.getHelper().showMessage(null,
                                                               se.getMessage() + ":::"
                                                               + se.getErrorCode() + ":::"
                                                               + se.getNextException() + ":::"
                                                               + se.getSQLState(), "SQLExeption",
                                                               javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    p_IHMM_HOMiniModel.getHelper().showMessage(null, msg, "SQLExeption",
                                                               javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    break;
                } catch (Exception e) {
                    HOLogger.instance().log(getClass(),e);

                    final StackTraceElement[] aste = e.getStackTrace();
                    String msg = "";

                    for (int j = 0; j < aste.length; j++) {
                        msg = msg + "\n" + aste[j];
                    }

                    p_IHMM_HOMiniModel.getHelper().showMessage(null,
                                                               e.getMessage() + ":::"
                                                               + e.getClass().getName(),
                                                               "DatenreiheA",
                                                               javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    p_IHMM_HOMiniModel.getHelper().showMessage(null, msg, "DatenreiheB",
                                                               javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }
        }

        return output;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param inputTrainings TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public Vector calculateTrainings(Vector inputTrainings) {
        return this.weekManager.calculateTrainings(inputTrainings);
    }

    /**
     * liefert die komplette Trainings in jedem skill eines Spielers calculates TRaining for given
     * Player for each skill
     *
     * @param inputSpieler Player to use
     * @param train preset Trainingweeks
     *
     * @return TrainingPerPlayer
     */
    public ITrainingPerPlayer calculateWeeklyTrainingForPlayer(ISpieler inputSpieler,
                                                               ITrainingWeek train) {
        //playerID HIER SETZEN
        final int playerID = inputSpieler.getSpielerID();
        final ISpieler spieler = inputSpieler;

        int trainskill2 = -1;

        final TrainingPerPlayer output = new TrainingPerPlayer();
        output.setSpieler(spieler);

        if (train == null) {
            return output;
        }

        if (train.getTyp() == -1) {
            return output;
        }

        //Kalenderwerte setzen
        //set calendar values
        final Calendar trainingDate = Calendar.getInstance(Locale.UK);
        trainingDate.setFirstDayOfWeek(Calendar.SUNDAY);

        try {
            trainingDate.setTime(p_IHMM_HOMiniModel.getXtraDaten().getTrainingDate());
        } catch (Exception e) {
            return output;
        }

        trainingDate.set(Calendar.YEAR, train.getYear());
        trainingDate.set(Calendar.WEEK_OF_YEAR, train.getWeek());

        //EINSCHR?NKEN AUF BESTIMMTE POS CODES IN DER ABFRAGE
        //reduce database query to certain position codes
        if (train.getTyp() == ITeam.TA_SCHUSSTRAINING) {
            trainskill2 = ISpieler.SKILL_STANDARDS;
        }

        //falls aktuelles Training vor dem letztem Skillup
        //in case of aktual training before last skillup
        //if (trainingDate.getTimeInMillis() > skilluptime.getTime()) {
        try {
            final Map rosterStatus = getTeamRosterStatus(train.getHrfId());
            final Integer status = (Integer) rosterStatus.get(playerID + "");

            // Player was not on roster at time of training
            if (status == null) {
                return output;
            }

            // Or injured (+1 player trains)
            if (status.intValue() > 1) {
                return output;
            }

            double weekPoints = 0;
            final ResultSet matchRS = p_IHMM_HOMiniModel.getAdapter().executeQuery(createQuery(trainingDate));

            if (matchRS == null) {
                //Falls nichts geliefert wurde // in case of no return values
                return output;
            }

            final List matches = new ArrayList();

            while (matchRS.next()) {
                final int matchId = matchRS.getInt("MATCHID");
                matches.add(new Integer(matchId));
            }

            try {
                matchRS.close();
            } catch (Exception e1) {
            }

            //Abarbeiten ob der Spieler in den 2 besagtem Matches auf einem entsprechenden Positionscode gespielt haben
            //look through if the player has played in the corresponding week on a position with the corresponding pos code.
            final Iterator iter = matches.iterator();

            while (iter.hasNext()) {
                final Integer matchId = (Integer) iter.next();
                final Map matchData = getMatchData(matchId.intValue());
                Integer playerMatchPosition = (Integer) matchData.get(new Integer(playerID));

                // Player not played, position is 0
                if (playerMatchPosition == null) {
                    playerMatchPosition = new Integer(0);
                }

                if (playerID == 40591141) {
                    HOLogger.instance().log(getClass(),matchData);
                    HOLogger.instance().log(getClass(),"Position: " + playerMatchPosition);
                }

                // Get point for the position and primary training type
                final Double value = points.getTrainingPoint(train.getTyp(), playerMatchPosition);

                // If player received training for this game and has not received points the the previous
                if ((value.doubleValue() > 0) && (weekPoints == 0)) {
                    double d = ((train.getIntensitaet() * value.doubleValue()) / 100);

                    //Setzen der Berechneten Werte f?r den ersten skill
                    //setting calculated values for the primary skill
                    updateTrainingPerPlayer(output, train.getTyp(), d);

                    // update points earned this week
                    weekPoints = d;
                }

                //If scoring training is exercised, all players must have 50% Standards training too
                if (trainskill2 != -1) {
                    final double d = ((train.getIntensitaet() * p_f_schusstraining_Standard) / 100);

                    //SETZTEN Des neuen trainingswertes
                    //set new train values
                    if (train.getTyp() == ITeam.TA_SCHUSSTRAINING) {
                        output.setST(d);
                    }
                }
            }
        } catch (SQLException se) {
            HOLogger.instance().log(getClass(),se);

            final StackTraceElement[] aste = se.getStackTrace();
            String msg = "";

            for (int j = 0; j < aste.length; j++) {
                msg = msg + "\n" + aste[j];
            }

            p_IHMM_HOMiniModel.getHelper().showMessage(null,
                                                       se.getMessage() + ":::" + se.getErrorCode()
                                                       + ":::" + se.getNextException() + ":::"
                                                       + se.getSQLState(), "SQLExeption",
                                                       javax.swing.JOptionPane.INFORMATION_MESSAGE);
            p_IHMM_HOMiniModel.getHelper().showMessage(null, msg, "SQLExeption",
                                                       javax.swing.JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);

            final StackTraceElement[] aste = e.getStackTrace();
            String msg = "";

            for (int j = 0; j < aste.length; j++) {
                msg = msg + "\n" + aste[j];
            }

            p_IHMM_HOMiniModel.getHelper().showMessage(null,
                                                       e.getMessage() + ":::"
                                                       + e.getClass().getName(), "DatenreiheA",
                                                       javax.swing.JOptionPane.INFORMATION_MESSAGE);
            p_IHMM_HOMiniModel.getHelper().showMessage(null, msg, "DatenreiheB",
                                                       javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }

        return output;
    }

    // ------------------------ Deprecated Methods -----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param trainings TODO Missing Constructuor Parameter Documentation
     *
     * @see : for complete recalculation  fillWithData( ITrainingsManager.calculateTrainings(
     *      IHOMiniModel.getDBManualTrainingsVector() ) );
     * @deprecated initializes and calculates All Data for training
     */
    public void fillWithData(Vector trainings) {
        return;
    }

    /**
     * Recualtes all the subskills
     *
     * @param showBar TODO Missing Constructuor Parameter Documentation
     */
    public void recalcSubskills(boolean showBar) {
    	HOMainFrame.setHOStatus(HOMainFrame.BUSY);
        if (JOptionPane.showConfirmDialog(p_IHMM_HOMiniModel.getGUI().getOwner4Dialog(),
                                          "Depending on database volume this process takes several minutes. Start recalculation ?",
                                          "Subskill Recalculation", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
            HOVerwaltung.instance().recalcSubskills(true, null);

            //tools.Helper.showMessage ( this, model.HOVerwaltung.instance ().getResource ().getProperty( "NeustartErforderlich" ), "", JOptionPane.INFORMATION_MESSAGE );
        }
    }

    /**
     * Calculates the lask skillup for the player in the corrct train
     *
     * @param spieler player to be used
     * @param trainskill Skill we are looking for a skillup
     * @param trainTime Trainingtime
     *
     * @return Last skillup Date, or Date(0) if no skillup were found
     */
    private Date getLastSkillupDate(ISpieler spieler, int trainskill, Date trainTime) {
        //Feststellung wann die relevanten Skillupswaren
        //get relevant skillups for calculation period
        final Vector levelups = spieler.getAllLevelUp(trainskill);
        Date skilluptime = new Date(0);

        for (Iterator it = levelups.iterator(); it.hasNext();) {
            final Object[] aobj = (Object[]) it.next();
            final Boolean bLevel = (Boolean) aobj[1];

            if (bLevel.booleanValue() == true) {
                final Date tmpTime = new Date(((Timestamp) aobj[0]).getTime());

                if ((tmpTime.before(trainTime)) && (tmpTime.after(skilluptime))) {
                    skilluptime = HelperWrapper.instance()
                                               .getLastTrainingDate(tmpTime,
                                                                    p_IHMM_HOMiniModel.getXtraDaten()
                                                                                      .getTrainingDate())
                                               .getTime();
                }
            }
        }

        return skilluptime;
    }

    //----------------------------------- Utility Methods ----------------------------------------------------------
    private Map getMatchData(int matchId) {
        Map matchData = (Map) this.matchMap.get("" + matchId);

        if (matchData == null) {
            matchData = new HashMap();

            final String query =
                "select SPIELERID,FieldPos from MATCHLINEUPPLAYER where FIELDPOS>-1 AND MATCHID = "
                + matchId + "  and TEAMID = " + p_IHMM_HOMiniModel.getBasics().getTeamId();
            final ResultSet rs = p_IHMM_HOMiniModel.getAdapter().executeQuery(query);

            if (rs == null) {
                this.matchMap.put("" + matchId, matchData);
                return matchData;
            }

            try {
                while (rs.next()) {
                    final int id = rs.getInt("SPIELERID");
                    final int pos = rs.getInt("FIELDPOS");
                    matchData.put(new Integer(id), new Integer(pos));
                }
            } catch (SQLException e) {
            }

            this.matchMap.put("" + matchId, matchData);
        }

        return matchData;
    }

    /**
     * Extract the players'id of team players with injuy info for the trainingDate
     *
     * @param hrfId
     *
     * @return list of Team PlayerId's
     */
    private Map getTeamRosterStatus(int hrfId) {
        Map roster = (Map) this.rosterMap.get("" + hrfId);

        if (roster == null) {
            roster = new HashMap();

            final String query = "select SPIELERID,VERLETZT from SPIELER where HRF_ID=" + hrfId;
            final ResultSet rs = p_IHMM_HOMiniModel.getAdapter().executeQuery(query);

            try {
                while (rs.next()) {
                    roster.put(rs.getString("SPIELERID"), new Integer(rs.getInt("VERLETZT")));
                }
            } catch (SQLException e) {
            }

            try {
                rs.close();
            } catch (SQLException e1) {
            }

            this.rosterMap.put("" + hrfId, roster);
        }

        return roster;
    }

    /**
     * Creates the query to extract the list of matchId for each Training
     *
     * @param calendar TrainingDate
     *
     * @return the query
     */
    private String createQuery(Calendar calendar) {
        final Timestamp ts = new Timestamp(calendar.getTimeInMillis());
        final Calendar old = (Calendar) calendar.clone();

        //Set Time 1 Woche zur?ck // set time one week back
        old.add(Calendar.WEEK_OF_YEAR, -1);

        final Timestamp ots = new Timestamp(old.getTimeInMillis());
        final int teamId = p_IHMM_HOMiniModel.getBasics().getTeamId();
        final String sdbquery = "SELECT MATCHID FROM MATCHDETAILS WHERE " + "( HEIMID=" + teamId
                                + " OR GASTID=" + teamId + " ) " + "AND SPIELDATUM BETWEEN '"
                                + ots.toString() + "' AND '" + ts.toString() + "' "
                                + "ORDER BY SPIELDATUM DESC";

        return sdbquery;
    }

    /**
     * Updates the TrainingPerPlayer object for the specified trainingType with the new value
     *
     * @param output TrainingPerPlayer Object to update
     * @param trainType train type
     * @param d new value
     */
    private void updateTrainingPerPlayer(TrainingPerPlayer output, int trainType, double d) {
        //Spielaufbau // playmaking
        if (trainType == ITeam.TA_SPIELAUFBAU) {
            output.setSA(d);

            //Verteidigung //defense
        } else if (trainType == ITeam.TA_VERTEIDIGUNG) {
            output.setVE(d);

            //Flankenl?ufe //wing
        } else if (trainType == ITeam.TA_FLANKEN) {
            output.setFL(d);

            //Passspiel //passing
        } else if (trainType == ITeam.TA_PASSSPIEL) {
            output.setPS(d);

            //Torwart //keeper
        } else if (trainType == ITeam.TA_TORWART) {
            output.setTW(d);

            //Chancenverwertung //Torschuss //scoring
        } else if (trainType == ITeam.TA_CHANCEN) {
            output.setTS(d);

            //Standardsituationen //set pieces
        } else if (trainType == ITeam.TA_STANDARD) {
            output.setST(d);

            //			Schusstraining == ausnahme nachher !!!!	//shooting !!! ATTENTION, see afterwards
        } else if (trainType == ITeam.TA_SCHUSSTRAINING) {
            output.setTS(d);

            //defensive positions
        } else if (trainType == ITeam.TA_ABWEHRVERHALTEN) {
            output.setVE(d);

            // through passes
        } else if (trainType == ITeam.TA_STEILPAESSE) {
            output.setPS(d);

            // through passes
        } else if (trainType == ITeam.TA_KONDITION) {
            output.setKO(d);
			// Lateral offensive            
        } else if (trainType == ITeam.TA_EXTERNALATTACK) { 
            output.setFL(d);
        }
    }

}
