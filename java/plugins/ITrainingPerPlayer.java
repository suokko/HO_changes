// %1127327012447:plugins%
/*
 * ITrainingPerPlayer.java
 *
 * Created on 21. Oktober 2004, 15:17
 */
package plugins;

/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public interface ITrainingPerPlayer {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Getter for property FL.
     *
     * @return Value of property FL.
     */
    public double getFL();

    /**
     * returns stamania
     *
     * @return TODO Missing Return Method Documentation
     */
    public double getKO();

    /**
     * Getter for property PS.
     *
     * @return Value of property PS.
     */
    public double getPS();

    /**
     * Getter for property SA.
     *
     * @return Value of property SA.
     */
    public double getSA();

    /**
     * Getter for property ST.
     *
     * @return Value of property ST.
     */
    public double getST();

    /**
     * Getter for property spieler.
     *
     * @return Value of property spieler.
     */
    public plugins.ISpieler getSpieler();

    /**
     * Getter for property TS.
     *
     * @return Value of property TS.
     */
    public double getTS();

    /**
     * Getter for property TW.
     *
     * @return Value of property TW.
     */
    public double getTW();

    /**
     * Getter for property VE.
     *
     * @return Value of property VE.
     */
    public double getVE();
}
