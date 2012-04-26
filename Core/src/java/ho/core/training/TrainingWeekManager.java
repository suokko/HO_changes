// %2201242558:de.hattrickorganizer.logik%
package ho.core.training;

import ho.core.db.DBManager;
import ho.core.db.JDBCAdapter;
import ho.core.model.HOVerwaltung;
import ho.core.util.HOLogger;
import ho.core.util.HelperWrapper;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;


/**
 * Class that extract data from Database and calculates TrainingWeek and TrainingPoints earned from
 * players
 *
 * @author humorlos, Dragettho, thetom
 */
public class TrainingWeekManager {
    //~ Static fields/initializers -----------------------------------------------------------------

    private static TrainingWeekManager m_clInstance;

    /** TrainingWeeks */
    private Vector<TrainingPerWeek> m_vTrainings;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of TrainingsManager
     */
    private TrainingWeekManager() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * liefert instance vom Trainingsmanager  vor der ersten Nutzun noch fillWithData(
     * vorgabeTrainings ) aufrufen
     *
     * @return instance of NewTrainingManager
     */
    public static TrainingWeekManager instance() {
        if (m_clInstance == null) {
            m_clInstance = new TrainingWeekManager();
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
    public TrainingPerWeek getTrainingWeek(int hrfId) {
        // TODO Load last valid training week!
        final Timestamp tStamp = DBManager.instance().getPreviousTrainingDate(hrfId);

        if (tStamp == null) {
            return null;
        }

        Calendar calendar;
        calendar = HelperWrapper.instance().getLastTrainingDate(new Date(tStamp.getTime()),
        		HOVerwaltung.instance().getModel().getXtraDaten().getTrainingDate());

        final int trainWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        final int trainYear = calendar.get(Calendar.YEAR);

        for (Iterator<TrainingPerWeek> iter = m_vTrainings.iterator(); iter.hasNext();) {
            final TrainingPerWeek element = iter.next();

            if ((element.getYear() == trainYear) && (element.getWeek() == trainWeek)) {
                return element;
            }
        }

        return null;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param vector TODO Missing Method Parameter Documentation
     */
    public void setTrainings(Vector<TrainingPerWeek> vector) {
        m_vTrainings = vector;
    }

    /**
     * returns current TrainingVector calculates new vector if current is null
     *
     * @return Training Vector, vector of ITrainingPerWeek
     */
    public Vector<TrainingPerWeek> getTrainingsVector() {
        if (m_vTrainings == null) {
            return calculateTrainings(ho.core.db.DBManager.instance().getTrainingsVector());
        }

        return m_vTrainings;
    }

    /*
     * calculates TrainingWeeks based on given input Vector
     * @param inputTrainings must be != null, empty vector if no trainings are preset
     *
     * @return Vector of trainingweeks
     **/
    public Vector<TrainingPerWeek> calculateTrainings(Vector<?> inputTrainings) {
        //reset trainings
        Vector<TrainingPerWeek> output = new Vector<TrainingPerWeek>();

        //get database connection
        final JDBCAdapter ijdbca = DBManager.instance().getAdapter();

        //make timezone
        try {
            //get all hrfs
            final String sdbquery = "SELECT * FROM HRF,TEAM WHERE HRF.HRF_ID=TEAM.HRF_ID ORDER BY DATUM";
            final ResultSet rs = ijdbca.executeQuery(sdbquery);
            rs.beforeFirst();

            int lastTrainWeek = -1;

            boolean isFirstTrain = true;

            while (rs.next()) {
                //Datum der HRF filtern und schauen, welche hrf m?glichst nah an einem Freitag liegt
                //filter date of the hrf and look, which one of them is closest to the trainings update
                final int trainType = rs.getInt("TRAININGSART");

                if (trainType != -1) {
                    final int trainIntensitaet = rs.getInt("TRAININGSINTENSITAET");
                    final int trainStaminaTrainPart = rs.getInt("STAMINATRAININGPART");
                    final int hrfId = rs.getInt("HRF_ID");

                    final Timestamp tStamp = rs.getTimestamp("DATUM");
                    Calendar calendar;

                    try {
                        calendar = HelperWrapper.instance().getLastTrainingDate(new Date(tStamp.getTime()),
                        		HOVerwaltung.instance().getModel().getXtraDaten().getTrainingDate());
                    } catch (Exception e) {
                        return output;
                    }

                    int trainWeek = calendar.get(Calendar.WEEK_OF_YEAR);
                    final int trainYear = calendar.get(Calendar.YEAR);

                    //if traintype still null -> first run
                    if (isFirstTrain) {
                        lastTrainWeek = trainWeek;
                        isFirstTrain = false;
                    }

                    //in case new week: add actual temporary data the the hashset with the trainings
                    if (lastTrainWeek != trainWeek) {
                        //in case of missing data (lost weeks) add empty trainings
                        for (int i = lastTrainWeek + 1; i < trainWeek; i++) {
                            try {
                                //try catch in case of corrupted training data in the input
                                boolean traincalculated = false;

                                for (Iterator<?> it = inputTrainings.iterator(); it.hasNext();) {
                                    final TrainingPerWeek storedTrain = (TrainingPerWeek) it.next();

                                    //altes Training nehmen
                                    if ((storedTrain.getWeek() == i)
                                        && (storedTrain.getYear() == trainYear)) {
                                        storedTrain.setHrfId(hrfId);
                                        output.add(storedTrain);
                                        traincalculated = true;
                                        break;
                                    }
                                }

                                if (traincalculated == false) {
                                    final TrainingPerWeek tpw = new TrainingPerWeek(i, trainYear, -1, -1, -1);
                                    tpw.setHrfId(hrfId);
                                    output.add(tpw);
                                }
                            } catch (Exception e) {
                                // if training not in the input vector -> add empty train
                                final TrainingPerWeek tpw = new TrainingPerWeek(i, trainYear, -1, -1, -1);
                                tpw.setHrfId(hrfId);
                                output.add(tpw);
                            }
                        }

                        //add train of last week to the collection of trainings
                        //try-catch if inputTrainings not as big
                        try {
                            boolean traincalculated = false;

                            for (Iterator<?> it = inputTrainings.iterator(); it.hasNext();) {
                                final TrainingPerWeek storedTrain = (TrainingPerWeek) it.next();

                                //altes Training nehmen
                                if ((storedTrain.getWeek() == trainWeek)
                                    && (storedTrain.getYear() == trainYear)) {
                                    storedTrain.setHrfId(hrfId);
                                    output.add(storedTrain);
                                    traincalculated = true;
                                    break;
                                }
                            }

                            if (traincalculated == false) {
                                final TrainingPerWeek t = new TrainingPerWeek(trainWeek, trainYear,
                                                                              trainType,
                                                                              trainIntensitaet,
                                                                              trainStaminaTrainPart);
                                t.setHrfId(hrfId);
                                output.add(t);
                            }
                        } catch (Exception e) {
                            final TrainingPerWeek t = new TrainingPerWeek(trainWeek, trainYear,
                                                                          trainType,
                                                                          trainIntensitaet,
                                                                          trainStaminaTrainPart);
                            t.setHrfId(hrfId);
                            output.add(t);
                        }

                        lastTrainWeek = trainWeek;
                        isFirstTrain = false;
                    } else {
                        //In der gleichen Woche wurde ein weiteres HRF gefunden -> Update der Werte
                        // in the same week there is another hrf -> update of the values
                        isFirstTrain = false;
                    }
                }
            }

            rs.close();

            //Vector speichern
            //save vector
            output = getUpdateTraining(output);
            m_vTrainings = output;
            return output;
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);

            final StackTraceElement[] aste = e.getStackTrace();
            String msg = "";

            for (int j = 0; j < aste.length; j++) {
                msg = msg + "\n" + aste[j];
            }

           HelperWrapper.instance().showMessage(null, e.getMessage(), "DatenreiheA",
                                                       javax.swing.JOptionPane.INFORMATION_MESSAGE);
            HelperWrapper.instance().showMessage(null, msg, "DatenreiheB",
                                                       javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }

        m_vTrainings = new Vector<TrainingPerWeek>();
        return m_vTrainings;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param output TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private Vector<TrainingPerWeek> getUpdateTraining(Vector<TrainingPerWeek> output) {
        int actaulSeason = HOVerwaltung.instance().getModel().getBasics().getSeason();
        int actualWeek = HOVerwaltung.instance().getModel().getBasics().getSpieltag();
        final int trainNumber = output.size();

        try {
            // We are in the middle where season has not been updated!
            if (HOVerwaltung.instance().getModel().getXtraDaten().getTrainingDate().after(HOVerwaltung.instance().getModel().getXtraDaten()
                                                                                            .getSeriesMatchDate())) {
                actualWeek++;

                if (actualWeek == 17) {
                    actualWeek = 1;
                    actaulSeason++;
                }
            }
        } catch (Exception e) {
        	HOLogger.instance().log(getClass(),"TrainingsWeekManager.getUpdateTraining: " + e);
            return output;
        }

        final Vector<TrainingPerWeek> updatedTrainings = new Vector<TrainingPerWeek>();

        for (int index = 0; index < trainNumber; index++) {
            final TrainingPerWeek train = output.get(index);
            final HattrickDate htDate = calculateByDifference(actaulSeason, actualWeek,
                                                              trainNumber - index);
            final TrainingPerWeek newTrain = new TrainingPerWeek(train.getWeek(), train.getYear(),
                                                                 train.getTrainingType(),
                                                                 train.getTrainingIntensity(),
                                                                 train.getStaminaPart());
            newTrain.setHattrickSeason(htDate.getSeason());
            newTrain.setHattrickWeek(htDate.getWeek());
            newTrain.setHrfId(train.getHrfId());
            newTrain.setPreviousHrfId(DBManager.instance().getPreviousHRF(train.getHrfId()));
            updatedTrainings.add(newTrain);
        }

        return updatedTrainings;
    }

    /**
     * Method that calculate the HT date for past week before
     *
     * @param actualSeason Actual Hattrick Season
     * @param actualWeek Actual Hattrick Week
     * @param pastWeek How many week to go back
     *
     * @return Hattrick Date
     */
    private HattrickDate calculateByDifference(int actualSeason, int actualWeek, int pastWeek) {
        final HattrickDate date = new HattrickDate();
        actualWeek = (actualWeek - pastWeek) - 1;

        if (actualWeek <= 0) {
            final int a = Math.abs(actualWeek / 16) + 1;
            actualSeason = actualSeason - a;
            actualWeek = actualWeek + (a * 16);
        }

        date.setSeason(actualSeason + (actualWeek / 16));
        date.setWeek((actualWeek % 16) + 1);
        return date;
    }
}
