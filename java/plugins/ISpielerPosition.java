// %18907463:plugins%
/*
 * ISpielerPosition.java
 *
 * Created on 19. April 2004, 07:40
 */
package plugins;

/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
/**
 * Interface Konst Definition for Pos Values
 */
public interface ISpielerPosition {
    //~ Static fields/initializers -----------------------------------------------------------------

    //Konstanten f�r die ID's

    /** ht-Keeper-Field */
    public static final int keeper = 1;

    /** ht-rightBack-Field */
    public static final int rightBack = 2;

    /** ht-insideBack1-Field */
    public static final int insideBack1 = 3;

    /** ht-insideBack2-Field */
    public static final int insideBack2 = 4;

    /** ht-leftBack-Field */
    public static final int leftBack = 5;

    /** ht-rightWinger-Field */
    public static final int rightWinger = 6;

    /** ht-insideMid1-Field */
    public static final int insideMid1 = 7;

    /** ht-insideMid2-Field */
    public static final int insideMid2 = 8;

    /** ht-leftWinger-Field */
    public static final int leftWinger = 9;

    /** ht-forward1-Field */
    public static final int forward1 = 10;

    /** ht-forward2-Field */
    public static final int forward2 = 11;

    /** ht-substKeeper-Field */
    public static final int substKeeper = 12;

    /** ht-substBack-Field */
    public static final int substBack = 13;

    /** ht-substInsideMid-Field */
    public static final int substInsideMid = 14;

    /** ht-substWinger-Field */
    public static final int substWinger = 15;

    /** ht-substForward-Field */
    public static final int substForward = 16;

    /** ht-setpieces-Field */
    public static final int standard = 17;

    /** ht-Capitan-Field */
    public static final int spielfuehrer = 18;

    /** ht-subsituded-Field */

    //oder gr�sser
    public static final int ausgewechselt = 19;

    /** ab welccher PositionsID geh�rt Pos zur Reserve Bank */

    //First id of an the reserve
    public static final int beginnReservere = 12;

    //Konstanten f�rs verhalten

    /** Normal */
    public static final byte NORMAL = 0;

    /** Offensive */
    public static final byte OFFENSIV = 1;

    /** Defensive */
    public static final byte DEFENSIV = 2;

    /** towards center */
    public static final byte ZUR_MITTE = 3;

    /** towards wing */
    public static final byte NACH_AUSSEN = 4;

    /** additional forward */
    public static final byte ZUS_STUERMER = 5;

    /** additional midfield */
    public static final byte ZUS_MITTELFELD = 6;

    /** additional defender */
    public static final byte ZUS_INNENV = 7;

    //Konstanten f�r Position

    /** Number of Positions [0 (Keeper) - 24 (extraCD) => 25] **/
    public static final byte NUM_POSITIONS = 25;
    
    /** Keeper */
    public static final byte TORWART = 0;

    /** Central Defender */
    public static final byte INNENVERTEIDIGER = 1;

    /** Central Defender offensive */
    public static final byte INNENVERTEIDIGER_OFF = 2;

    /** Central Defender toward wing */

    public static final byte INNENVERTEIDIGER_AUS = 3;

    /** Wingback */
    public static final byte AUSSENVERTEIDIGER = 4;

    /** Wingback offensive */
    public static final byte AUSSENVERTEIDIGER_OFF = 5;

    /** Wingback toward center */
    public static final byte AUSSENVERTEIDIGER_IN = 6;

    /** Wingback defensive */

    public static final byte AUSSENVERTEIDIGER_DEF = 7;

    /** Midfield */

    public static final byte MITTELFELD = 8;

    /** Midfield offensive */
    public static final byte MITTELFELD_OFF = 9;

    /** Midfield defensive */
    public static final byte MITTELFELD_DEF = 10;

    /** Midfield towards Wing */
    public static final byte MITTELFELD_AUS = 11;

    /** Winger */

    public static final byte FLUEGELSPIEL = 12;

    /** Winger offensive */
    public static final byte FLUEGELSPIEL_OFF = 13;

    /** Winger defensive */
    public static final byte FLUEGELSPIEL_DEF = 14;

    /** Winger towards Midfield */
    public static final byte FLUEGELSPIEL_IN = 15;

    /** Forward */
    public static final byte STURM = 16;

    /** Forward defensive */
    public static final byte STURM_DEF = 17;

    /** substited */
    public static final byte AUSGEWECHSELT1 = 18;

    /** substited */
    public static final byte AUSGEWECHSELT2 = 19;

    /** substited */
    public static final byte AUSGEWECHSELT3 = 20;

    /** forward toward wing Thorsten Dietz 25-09-2005 */
    public static final byte STURM_AUS = 21;

    /** additional forward */
    public static final byte POS_ZUS_STUERMER = 22;

    /** additional midfield */
    public static final byte POS_ZUS_MITTELFELD = 23;

    /** additional defender */
    public static final byte POS_ZUS_INNENV = 24;

    /** Unknown */
    public static final byte UNBESTIMMT = -1;

    /** Coach */
    public static final byte TRAINER = 99;
}
