// %2751235623:de.hattrickorganizer.model%
package de.hattrickorganizer.model;

import plugins.ISpieler;


/**
 * DOCUMENT ME!
 *
 * @author
 */
public class TrainingPerPlayer implements plugins.ITrainingPerPlayer {
    //~ Instance fields ----------------------------------------------------------------------------

    private ISpieler spieler;

    //Flügespiel
    private double FL;

    //Stamania
    private double KO;

    //Passspiel
    private double PS;

    //Spielaufbau
    private double SA;

    //Standards
    private double ST;

    //Torschuss
    private double TS;

    //Torwart
    private double TW;

    //Verteidigung
    private double VE;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TrainingPerPlayer object.
     */
    public TrainingPerPlayer() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Setter for property FL.
     *
     * @param FL New value of property FL.
     */
    public final void setFL(double FL) {
        this.FL = FL;
    }

    /**
     * Getter for property FL.
     *
     * @return Value of property FL.
     */
    public final double getFL() {
        return FL;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param d TODO Missing Method Parameter Documentation
     */
    public final void setKO(double d) {
        KO = d;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final double getKO() {
        return KO;
    }

    /**
     * Setter for property PS.
     *
     * @param PS New value of property PS.
     */
    public final void setPS(double PS) {
        this.PS = PS;
    }

    /**
     * Getter for property PS.
     *
     * @return Value of property PS.
     */
    public final double getPS() {
        return PS;
    }

    /**
     * Setter for property SA.
     *
     * @param SA New value of property SA.
     */
    public final void setSA(double SA) {
        this.SA = SA;
    }

    /**
     * Getter for property SA.
     *
     * @return Value of property SA.
     */
    public final double getSA() {
        return SA;
    }

    /**
     * Setter for property ST.
     *
     * @param ST New value of property ST.
     */
    public final void setST(double ST) {
        this.ST = ST;
    }

    /**
     * Getter for property ST.
     *
     * @return Value of property ST.
     */
    public final double getST() {
        return ST;
    }

    /**
     * Setter for property spieler.
     *
     * @param spieler New value of property spieler.
     */
    public final void setSpieler(plugins.ISpieler spieler) {
        this.spieler = spieler;
    }

    /**
     * Getter for property spieler.
     *
     * @return Value of property spieler.
     */
    public final plugins.ISpieler getSpieler() {
        return spieler;
    }

    /**
     * Setter for property TS.
     *
     * @param TS New value of property TS.
     */
    public final void setTS(double TS) {
        this.TS = TS;
    }

    /**
     * Getter for property TS.
     *
     * @return Value of property TS.
     */
    public final double getTS() {
        return TS;
    }

    /**
     * Setter for property TW.
     *
     * @param TW New value of property TW.
     */
    public final void setTW(double TW) {
        this.TW = TW;
    }

    /**
     * Getter for property TW.
     *
     * @return Value of property TW.
     */
    public final double getTW() {
        return TW;
    }

    /**
     * Setter for property VE.
     *
     * @param VE New value of property VE.
     */
    public final void setVE(double VE) {
        this.VE = VE;
    }

    /**
     * Getter for property VE.
     *
     * @return Value of property VE.
     */
    public final double getVE() {
        return VE;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public final String toString() {
        return spieler.getSpielerID() + ":" + TW + ":" + VE + ":" + SA + ":" + PS + ":" + FL + ":"
               + TS + ":" + ST + "\n";
    }
}
