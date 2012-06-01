// %2201242558:de.hattrickorganizer.logik%
package ho.core.training;

import ho.core.db.DBManager;
import ho.core.db.JDBCAdapter;
import ho.core.model.HOModel;
import ho.core.model.HOVerwaltung;
import ho.core.model.misc.Basics;
import ho.core.util.HOLogger;
import ho.core.util.Helper;
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
 * @author humorlos, Dragettho, thetom, seb04
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

        Calendar calendar = HelperWrapper.instance().getLastTrainingDate(new Date(tStamp.getTime()),
        		HOVerwaltung.instance().getModel().getXtraDaten().getTrainingDate());

        final int trainWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        final int trainYear = calendar.get(Calendar.YEAR);

        for (Iterator<TrainingPerWeek> iter = m_vTrainings.iterator(); iter.hasNext();) {
            TrainingPerWeek element = iter.next();
            if ((element.getYear() == trainYear) && (element.getWeek() == trainWeek)) {
                return element;
            }
        }
        return null;
    }

    /**
     * returns current TrainingVector calculates new vector if current is null
     *
     * @return Training Vector, vector of ITrainingPerWeek
     */
    public Vector<TrainingPerWeek> getTrainingsVector() {
        if (m_vTrainings == null) {
            return calculateTrainings(DBManager.instance().getTrainingsVector());
        }
        return m_vTrainings;
    }

    /*
     * calculates TrainingWeeks based on given input Vector
     * @param inputTrainings must be != null, empty vector if no trainings are preset
     *
     * @return Vector of trainingweeks
     **/
    public Vector<TrainingPerWeek> calculateTrainings(Vector<TrainingPerWeek> inputTrainings) {
        //reset trainings
        Vector<TrainingPerWeek> output = new Vector<TrainingPerWeek>();

        //get database connection
        final JDBCAdapter ijdbca = DBManager.instance().getAdapter();

        //make timezone
        try {
            //get all hrfs
            final String sdbquery = "SELECT TRAININGSART, TRAININGSINTENSITAET, " + 
            	"STAMINATRAININGPART, HRF_ID, DATUM FROM HRF,TEAM WHERE HRF.HRF_ID=TEAM.HRF_ID ORDER BY DATUM";
            final ResultSet rs = ijdbca.executeQuery(sdbquery);
            rs.beforeFirst();

            int lastTrainWeek = -1;

            boolean isFirstTrain = true;
            int trainIntensitaet = 0;
            int trainStaminaTrainPart = 0;
            int hrfId = 0;
            int trainType = 0;
            Timestamp tStamp = null;
            Calendar calendar = null;
            int trainWeek = 0;
            int trainYear = 0;
            while (rs.next()) {
                //Datum der HRF filtern und schauen, welche hrf m?glichst nah an einem Freitag liegt
                //filter date of the hrf and look, which one of them is closest to the trainings update
                trainType = rs.getInt("TRAININGSART");
                if (trainType != -1) {
                    trainIntensitaet = rs.getInt("TRAININGSINTENSITAET");
                    trainStaminaTrainPart = rs.getInt("STAMINATRAININGPART");
                    hrfId = rs.getInt("HRF_ID");
                    tStamp = rs.getTimestamp("DATUM");
                    
                    try {
                        calendar = HelperWrapper.instance().getLastTrainingDate(
                        		new Date(tStamp.getTime()),
                        		HOVerwaltung.instance().getModel().getXtraDaten().getTrainingDate());
                    } catch (Exception e) {
                        return output;
                    }

                    trainWeek = calendar.get(Calendar.WEEK_OF_YEAR);
                    trainYear = calendar.get(Calendar.YEAR);

                    //if traintype still null -> first run
                    if (isFirstTrain) {
                        lastTrainWeek = trainWeek;
                        isFirstTrain = false;
                    }

                    //in case new week: add actual temporary data the the hashset with the trainings
                    boolean traincalculated = false;
                    if (lastTrainWeek != trainWeek) {
                        //in case of missing data (lost weeks) add empty trainings
                        for (int i = lastTrainWeek + 1; i < trainWeek; i++) {
                            try {
                                //try catch in case of corrupted training data in the input
                                traincalculated = false;

                                for (Iterator<TrainingPerWeek> it = inputTrainings.iterator(); it.hasNext();) {
                                    TrainingPerWeek storedTrain = it.next();

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
                            traincalculated = false;

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
                                TrainingPerWeek t = new TrainingPerWeek(trainWeek, trainYear,
                                                                              trainType,
                                                                              trainIntensitaet,
                                                                              trainStaminaTrainPart);
                                t.setHrfId(hrfId);
                                output.add(t);
                            }
                        } catch (Exception e) {
                            TrainingPerWeek t = new TrainingPerWeek(trainWeek, trainYear,
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

            Helper.showMessage(null, e.getMessage(), "DatenreiheA",
                                                       javax.swing.JOptionPane.INFORMATION_MESSAGE);
            Helper.showMessage(null, msg, "DatenreiheB",
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
    	HOModel hom = HOVerwaltung.instance().getModel();
    	Basics bas = hom.getBasics();
        int actualSeason = bas.getSeason();
        int actualWeek = bas.getSpieltag();
        int trainNumber = output.size();
        try {
            // We are in the middle where season has not been updated!
            if (hom.getXtraDaten().getTrainingDate().after(hom.getXtraDaten().getSeriesMatchDate())) {
                actualWeek++;
                if (actualWeek == 17) {
                    actualWeek = 1;
                    actualSeason++;
                }
            }
        } catch (Exception e) {
        	HOLogger.instance().log(getClass(),"TrainingsWeekManager.getUpdateTraining: " + e);
            return output;
        }
        Vector<TrainingPerWeek> updatedTrainings = new Vector<TrainingPerWeek>();
        TrainingPerWeek newTrain = null;
        TrainingPerWeek train = null;
        HattrickDate htDate = null;
        for (int index = 0; index < trainNumber; index++) {
            train = output.get(index);
            htDate = calculateByDifference(actualSeason, actualWeek, trainNumber - index);
            newTrain = new TrainingPerWeek(train.getWeek(), train.getYear(), 
            		train.getTrainingType(), train.getTrainingIntensity(),
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
