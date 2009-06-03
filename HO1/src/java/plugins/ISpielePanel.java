// %1127326956619:plugins%
/*
 * ISpielePanel.java
 *
 * Created on 21. Oktober 2004, 07:20
 */
package plugins;

/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public interface ISpielePanel {
    //~ Static fields/initializers -----------------------------------------------------------------

    /*ALL Games*/

    /** TODO Missing Parameter Documentation */
    public static final int ALLE_SPIELE = 0;

    /** Only Matches of suplied team */
    public static final int NUR_EIGENE_SPIELE = 1;

    /** Only cup +league + quali Matches of suplied team */
    public static final int NUR_EIGENE_PFLICHTSPIELE = 2;

    /** Only cup Matches of suplied team */
    public static final int NUR_EIGENE_POKALSPIELE = 3;

    /** Only league Matches of suplied team */
    public static final int NUR_EIGENE_LIGASPIELE = 4;

    /** Only friendly Matches of suplied team */
    public static final int NUR_EIGENE_FREUNDSCHAFTSSPIELE = 5;

    /** Only Matches without suplied team */
    public static final int NUR_FREMDE_SPIELE = 6;

    /** Only played Matches of suplied team (unsupported for now) */
    public static final int NUR_GESPIELTEN_SPIELE = 10;
}
