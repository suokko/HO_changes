// %1127327741290:plugins%
/*
 * ITrainingsManager.java
 *
 * Created on 15. Oktober 2004, 13:14
 */
package plugins;

import java.sql.Timestamp;

import java.util.Vector;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
/**
 * encapsulates Trainingscalculation and data
 */
public interface ITrainingsManager {
    //~ Methods ------------------------------------------------------------------------------------

    /////////////////////// Deprecated, to be removed ///////////////////////////

    /**
     * DOCUMENT ME!
     *
     * @return TODO Missing Return Method Documentation
     *
     * @deprecated returns current PlayerTrainingVector calculates new vector if current is null
     */
    public Vector getPlayerTrainingsVector();

    ////////////////Accessor///////////////    

    /**
     * returns instance of TrainingPoint
     *
     * @return TODO Missing Return Method Documentation
     */
    public ITrainingPoint getTrainingPoint();

    /**
     * returns current TrainingVector calculates new vector if current is null
     *
     * @return TODO Missing Return Method Documentation
     */
    public Vector getTrainingsVector();

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
    public Vector calculateData(Vector inputTrainings, Timestamp zeitpunkt);

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
                                                     Timestamp zeitpunkt);

    ///////////////////////// Lower API, just call if you know what you'return doing :) ///////////////////////////

    /**
     * calculates TrainingWeeks based on given input Vector and sets actual trainingsVector to
     * result of calcultaion
     *
     * @param inputTrainings must be != null, empty vector if no trainings are preset
     *
     * @return Vector of trainingweeks
     */
    public Vector calculateTrainings(Vector inputTrainings);

    /**
     * DOCUMENT ME!
     *
     * @param trainings TODO Missing Constructuor Parameter Documentation
     *
     * @see : for complete recalculation  fillWithData( ITrainingsManager.calculateTrainings(
     *      IHOMiniModel.getDBManualTrainingsVector() ) );
     * @deprecated initializes and calculates All Data for training
     */
    public void fillWithData(Vector trainings);

    //Funktion für einen Spieler -> PlayerID vorhanden -> nach dem Brunnder

    /**
     * Method to force a recalculation of decimal subskills
     *
     * @param showBar TODO Missing Constructuor Parameter Documentation
     */
    public void recalcSubskills(boolean showBar);
}
