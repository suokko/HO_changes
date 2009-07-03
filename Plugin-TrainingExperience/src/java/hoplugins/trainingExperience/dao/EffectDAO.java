// %1641442024:hoplugins.trainingExperience.dao%
package hoplugins.trainingExperience.dao;

import gui.UserParameter;

import hoplugins.Commons;

import hoplugins.trainingExperience.OldTrainingManager;
import hoplugins.trainingExperience.vo.PlayerValues;
import hoplugins.trainingExperience.vo.TrainWeekEffect;

import plugins.IJDBCAdapter;
import plugins.ISkillup;
import plugins.ISpieler;

import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;


/**
 * This class is used to collect all required data and fill the lists of values with instances of
 * value objects.
 *
 * @author NetHyperon
 */
public class EffectDAO {
    //~ Static fields/initializers -----------------------------------------------------------------

    private static List<TrainWeekEffect> trainWeeks = new Vector<TrainWeekEffect>();

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Return list of TrainingEffects for each week
     *
     * @return
     */
    public static List<TrainWeekEffect> getTrainEffect() {
        return trainWeeks;
    }

    /**
     * Calculates the training weeks and returns a list of TrainWeek instances. These value object
     * contain the last hrf id before the training update and the first hrf id after the update.
     */
    public static void reload() {
        try {
            Map<String,List<ISkillup>> weeklySkillups = new HashMap<String,List<ISkillup>>();

            // Loop through all player (also old players) to get all trained skillups.
            // Group these skillups by season and week.
            List<ISpieler> players = new Vector<ISpieler>(Commons.getModel().getAllSpieler());

            players.addAll(Commons.getModel().getAllOldSpieler());

            for (Iterator<ISpieler> iterPlayers = players.iterator(); iterPlayers.hasNext();) {
                ISpieler player = (ISpieler) iterPlayers.next();
                OldTrainingManager otm = new OldTrainingManager(player);
                List<ISkillup> skillups = otm.getTrainedSkillups();

                for (Iterator<ISkillup> iterSkillups = skillups.iterator(); iterSkillups.hasNext();) {
                    ISkillup skillup = (ISkillup) iterSkillups.next();
                    String key = skillup.getHtSeason() + "-" + skillup.getHtWeek(); //$NON-NLS-1$
                    List<ISkillup> collectedSkillups = weeklySkillups.get(key);

                    if (collectedSkillups == null) {
                        collectedSkillups = new Vector<ISkillup>();
                        weeklySkillups.put(key, collectedSkillups);
                    }

                    collectedSkillups.add(skillup);
                }
            }

            IJDBCAdapter db = Commons.getModel().getAdapter();

            trainWeeks.clear();

            Calendar date = Calendar.getInstance();
            date.add(Calendar.HOUR, UserParameter.instance().TimeZoneDifference);

            Timestamp datum = new Timestamp(date.getTimeInMillis());
            ResultSet tDateset = db.executeQuery("SELECT DISTINCT trainingdate FROM XTRADATA WHERE trainingdate < '"
                                                 + datum.toString() + "'");
            List<Timestamp> trainingDates = new Vector<Timestamp>();

            try {
                while (tDateset.next()) {
                    trainingDates.add(tDateset.getTimestamp("trainingdate"));
                }

                tDateset.close();
            } catch (Exception e) {
            }

            Collections.sort(trainingDates,
                             new Comparator<Timestamp>() {
                    public int compare(Timestamp o1, Timestamp o2) {
                        Timestamp t1 = o1;
                        Timestamp t2 = o2;

                        return t2.compareTo(t1);
                    }
                });

            for (Iterator<Timestamp> iter = trainingDates.iterator(); iter.hasNext();) {
                Timestamp trainDate = iter.next();

                int HTWeek = Commons.getModel().getHelper().getHTWeek(trainDate);
                int HTSeason = Commons.getModel().getHelper().getHTSeason(trainDate);

                Commons.getModel().getHelper().getHTSeason(trainDate);

                StringBuffer minHrf_SQLStmt = new StringBuffer("SELECT HRF.hrf_id FROM HRF, XTRADATA");

                minHrf_SQLStmt.append(" WHERE HRF.hrf_id=XTRADATA.hrf_id");
                minHrf_SQLStmt.append(" AND datum = (SELECT MAX(datum) FROM HRF WHERE datum < '"
                                      + trainDate.toString() + "')");
                minHrf_SQLStmt.append(" AND trainingdate = '" + trainDate.toString() + "'");

                ResultSet minHRFSet = db.executeQuery(minHrf_SQLStmt.toString());

                int hrfBeforeUpdate = 0;

                try {
                    minHRFSet.next();
                    hrfBeforeUpdate = minHRFSet.getInt("hrf_id"); //$NON-NLS-1$
                    minHRFSet.close();
                } catch (Exception e) {
                    hrfBeforeUpdate = 0;
                }

                TrainWeekEffect week = null;

                if (hrfBeforeUpdate > 0) {
                    StringBuffer maxHrf_SQLStmt = new StringBuffer("SELECT HRF.hrf_id FROM HRF, XTRADATA");

                    maxHrf_SQLStmt.append(" WHERE HRF.hrf_id=XTRADATA.hrf_id");
                    maxHrf_SQLStmt.append(" AND datum = (SELECT MIN(datum) FROM HRF WHERE datum > '"
                                          + trainDate.toString() + "')");
                    maxHrf_SQLStmt.append(" AND trainingdate > '" + trainDate.toString() + "'");

                    ResultSet maxHRFSet = db.executeQuery(maxHrf_SQLStmt.toString());

                    int hrfAfterUpdate = 0;

                    try {
                        maxHRFSet.next();
                        hrfAfterUpdate = maxHRFSet.getInt("hrf_id"); //$NON-NLS-1$
                        maxHRFSet.close();
                    } catch (Exception e) {
                        hrfAfterUpdate = 0;
                    }

                    if (hrfAfterUpdate > 0) {
                        week = new TrainWeekEffect(HTWeek,HTSeason,hrfBeforeUpdate,hrfAfterUpdate);

                        ResultSet set = db.executeQuery("SELECT SUM(marktwert) as totaltsi, AVG(marktwert) as avgtsi , SUM(form) as form, COUNT(form) as number FROM SPIELER WHERE trainer = 0 AND hrf_id = " //$NON-NLS-1$
                                                        + Integer.toString(week.getHRFafterUpdate()));

                        if (set != null) {
                            set.next();
                            week.setTotalTSI(set.getInt("totaltsi")); //$NON-NLS-1$
                            week.setAverageTSI(set.getInt("avgtsi")); //$NON-NLS-1$

                            double avgForm = 0.0d;

                            if (set.getInt("number") != 0) { //$NON-NLS-1$
                                avgForm = set.getDouble("form") / set.getInt("number"); //$NON-NLS-1$ //$NON-NLS-2$
                            }

                            week.setAverageForm(avgForm);
                            set.close();
                        }

                        Map<Integer,PlayerValues> valuesBeforeUpdate = new HashMap<Integer,PlayerValues>();

                        set = db.executeQuery("SELECT * FROM SPIELER WHERE trainer = 0 AND hrf_id = " //$NON-NLS-1$
                                              + Integer.toString(week.getHRFbeforeUpdate()));

                        if (set != null) {
                            while (set.next()) {
                                PlayerValues result = new PlayerValues(set.getInt("marktwert"), //$NON-NLS-1$
                                                                       set.getInt("form")); //$NON-NLS-1$

                                valuesBeforeUpdate.put(new Integer(set.getInt("spielerid")), result); //$NON-NLS-1$
                            }

                            set.close();
                        }

                        set = db.executeQuery("SELECT * FROM SPIELER, BASICS WHERE trainer = 0 AND SPIELER.hrf_id = BASICS.hrf_id AND SPIELER.hrf_id = " //$NON-NLS-1$
                                              + Integer.toString(week.getHRFafterUpdate()));

                        if (set != null) {
                            while (set.next()) {
                                Integer playerID = new Integer(set.getInt("spielerid")); //$NON-NLS-1$

                                if (valuesBeforeUpdate.containsKey(playerID)) {
                                    PlayerValues before = (PlayerValues) valuesBeforeUpdate.get(playerID);

                                    week.addTSI(set.getInt("marktwert") - before.getTsi()); //$NON-NLS-1$
                                    week.addForm(set.getInt("form") - before.getForm()); //$NON-NLS-1$
                                }
                            }

                            set.close();
                        }
                    }
                }

                // Set amount of skillups for this training week
                String key = HTSeason + "-" + HTWeek; //$NON-NLS-1$

                if (weeklySkillups.containsKey(key)) {
                    if (week == null) {
                        week = new TrainWeekEffect(HTWeek,HTSeason, 0, 0);
                    }

                    List<ISkillup> wsList = weeklySkillups.get(key);
                    week.setAmountSkillups(wsList.size());

                    if (wsList.size() > 0) {
                        ISkillup su = (ISkillup) wsList.get(0);
                        week.setTrainingType(su.getType());
                    }
                }

                if (week != null) {
                    trainWeeks.add(week);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
