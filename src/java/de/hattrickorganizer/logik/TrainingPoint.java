// %1043029125:de.hattrickorganizer.logik%
package de.hattrickorganizer.logik;

import java.util.Hashtable;

import plugins.ITeam;
import plugins.ITrainingPoint;


/**
 * Class that manages the relation between position and training points
 *
 * @author Draghetto HO
 */
public class TrainingPoint implements ITrainingPoint {
    //~ Instance fields ----------------------------------------------------------------------------

    //	Abwehrverhalten alle Verteidiger und Mittelfeldspieler in Verteidigung mit 50% 
    //Defensive Positions: all defenders and playmakers 50% defense
    private Hashtable AV;

    //	Chancenauswertung -> Torschuss 100% St?rmer 
    //Scoring: 100% scoring forwards 
    private Hashtable CA;

    private Hashtable LATOFF = null; //External Attacker
    
    //Flankel?ufe // Wing
    private Hashtable FL;

    //Passspiel //Passing
    private Hashtable PS;

    //Spielaufbau // playmaking
    private Hashtable SA;

    //scoring
    private Hashtable SCHT;

    //Steilpaesse : Passspiel f?r alle Verteidiger und MFler mit 85% // Through passess: passing for all defenders and midfielders with 85%
    private Hashtable SP;

    //Torwart //Keeper
    private Hashtable TW;

    //Verteidigung //Defense
    private Hashtable VE;

    //Order is the same as in Data. As value only SpielerIDS (do i have to translate this?) -> same index for same players
    private Hashtable p_Ht_trainPositionen = new Hashtable();

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TrainingPoint object.
     */
    public TrainingPoint() {
        init();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Mathod that returns the correct training point
     *
     * @param trtype Training type code
     * @param playerMatchPosition player position code
     *
     * @return training points earned in that match
     */
    public final Double getTrainingPoint(int trtype, Integer playerMatchPosition) {
        if ((trtype == ITeam.TA_STANDARD) || (trtype == ITeam.TA_KONDITION)) {
            return new Double(1.0);
        }

        final Hashtable positions = (Hashtable) p_Ht_trainPositionen.get(new Integer(trtype));

        if (positions == null) {
            return new Double(0);
        }

        Double value = (Double) positions.get(playerMatchPosition);

        if (value == null) {
            value = new Double(0);
        }

        return value;
    }

    /**
     * Method that initialize the Class Data
     */
    private void init() {
        //F?r jedes TRainingsart die Positionen und Werte festlegen
        //for every traintype position and values definition
        SA = new Hashtable();
        SA.put(new Integer(6), new Double(0.5));
        SA.put(new Integer(7), new Double(1.0));
        SA.put(new Integer(8), new Double(1.0));
        SA.put(new Integer(9), new Double(0.5));

        FL = new Hashtable();
        FL.put(new Integer(2), new Double(0.5));
        FL.put(new Integer(5), new Double(0.5));
        FL.put(new Integer(6), new Double(1.0));
        FL.put(new Integer(9), new Double(1.0));

        VE = new Hashtable();
        VE.put(new Integer(2), new Double(1.0));
        VE.put(new Integer(3), new Double(1.0));
        VE.put(new Integer(4), new Double(1.0));
        VE.put(new Integer(5), new Double(1.0));

        PS = new Hashtable();
        PS.put(new Integer(6), new Double(1.0));
        PS.put(new Integer(7), new Double(1.0));
        PS.put(new Integer(8), new Double(1.0));
        PS.put(new Integer(9), new Double(1.0));
        PS.put(new Integer(10), new Double(1.0));
        PS.put(new Integer(11), new Double(1.0));

        TW = new Hashtable();
        TW.put(new Integer(1), new Double(1.0));

        CA = new Hashtable();
        CA.put(new Integer(10), new Double(1.0));
        CA.put(new Integer(11), new Double(1.0));

        SCHT = new Hashtable();
        SCHT.put(new Integer(2), new Double(0.6));
        SCHT.put(new Integer(3), new Double(0.6));
        SCHT.put(new Integer(4), new Double(0.6));
        SCHT.put(new Integer(5), new Double(0.6));
        SCHT.put(new Integer(6), new Double(0.6));
        SCHT.put(new Integer(7), new Double(0.6));
        SCHT.put(new Integer(8), new Double(0.6));
        SCHT.put(new Integer(9), new Double(0.6));
        SCHT.put(new Integer(10), new Double(0.6));
        SCHT.put(new Integer(11), new Double(0.6));

        AV = new Hashtable();
        AV.put(new Integer(2), new Double(0.5));
        AV.put(new Integer(3), new Double(0.5));
        AV.put(new Integer(4), new Double(0.5));
        AV.put(new Integer(5), new Double(0.5));
        AV.put(new Integer(6), new Double(0.5));
        AV.put(new Integer(7), new Double(0.5));
        AV.put(new Integer(8), new Double(0.5));
        AV.put(new Integer(9), new Double(0.5));

        SP = new Hashtable();
        SP.put(new Integer(2), new Double(0.85));
        SP.put(new Integer(3), new Double(0.85));
        SP.put(new Integer(4), new Double(0.85));
        SP.put(new Integer(5), new Double(0.85));
        SP.put(new Integer(6), new Double(0.85));
        SP.put(new Integer(7), new Double(0.85));
        SP.put(new Integer(8), new Double(0.85));
        SP.put(new Integer(9), new Double(0.85));

        LATOFF = new Hashtable();
        LATOFF.put(new Integer(6), new Double(0.6));
        LATOFF.put(new Integer(9), new Double(0.6));        
        LATOFF.put(new Integer(10), new Double(0.6));
        LATOFF.put(new Integer(11), new Double(0.6));
        
        //die einzelnen Trainingsarten hinzuf?gen
        //add all traintypes to one hashtable with all trainingstypes
        p_Ht_trainPositionen.put(new Integer(ITeam.TA_SPIELAUFBAU), SA);
        p_Ht_trainPositionen.put(new Integer(ITeam.TA_FLANKEN), FL);
        p_Ht_trainPositionen.put(new Integer(ITeam.TA_VERTEIDIGUNG), VE);
        p_Ht_trainPositionen.put(new Integer(ITeam.TA_PASSSPIEL), PS);
        p_Ht_trainPositionen.put(new Integer(ITeam.TA_TORWART), TW);
        p_Ht_trainPositionen.put(new Integer(ITeam.TA_CHANCEN), CA);
        p_Ht_trainPositionen.put(new Integer(ITeam.TA_SCHUSSTRAINING), SCHT);
        p_Ht_trainPositionen.put(new Integer(ITeam.TA_ABWEHRVERHALTEN), AV);
        p_Ht_trainPositionen.put(new Integer(ITeam.TA_STEILPAESSE), SP);
        p_Ht_trainPositionen.put(new Integer(ITeam.TA_EXTERNALATTACK), LATOFF);
    }
}
