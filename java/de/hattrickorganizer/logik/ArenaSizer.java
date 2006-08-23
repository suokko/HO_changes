// %2354158977:de.hattrickorganizer.logik%
/*
 * ArenaSizer.java
 *
 * Created on 21. März 2003, 08:19
 */
package de.hattrickorganizer.logik;

import de.hattrickorganizer.model.Stadium;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class ArenaSizer {
    //~ Static fields/initializers -----------------------------------------------------------------

    //Einnahmen

    /** TODO Missing Parameter Documentation */
    public static final float STEH_EINTRITT = 55f;

    /** TODO Missing Parameter Documentation */
    public static final float SITZ_EINTRITT = 80f;

    /** TODO Missing Parameter Documentation */
    public static final float DACH_EINTRITT = 110f;

    /** TODO Missing Parameter Documentation */
    public static final float LOGEN_EINTRITT = 275f;

    //laufende Kosten

    /** TODO Missing Parameter Documentation */
    public static final float STEH_UNTERHALT = 5f;

    /** TODO Missing Parameter Documentation */
    public static final float SITZ_UNTERHALT = 7f;

    /** TODO Missing Parameter Documentation */
    public static final float DACH_UNTERHALT = 10f;

    /** TODO Missing Parameter Documentation */
    public static final float LOGEN_UNTERHALT = 25f;

    //Bau

    /** TODO Missing Parameter Documentation */
    public static float STEH_AUSBAU = 600f;

    /** TODO Missing Parameter Documentation */
    public static float SITZ_AUSBAU = 1000f;

    /** TODO Missing Parameter Documentation */
    public static float DACH_AUSBAU = 1200f;

    /** TODO Missing Parameter Documentation */
    public static float LOGEN_AUSBAU = 4000f;

    /** TODO Missing Parameter Documentation */
    public static float ABRISS = 80f;

    /** TODO Missing Parameter Documentation */
    public static float FIXKOSTEN = 100000f;

    //Verteilung

    /** TODO Missing Parameter Documentation */
    public static final float STEH_PROZENT = 62.5f;

    /** TODO Missing Parameter Documentation */
    public static final float SITZ_PROZENT = 25.0f;

    /** TODO Missing Parameter Documentation */
    public static final float DACH_PROZENT = 10.0f;

    /** TODO Missing Parameter Documentation */
    public static final float LOGEN_PROZENT = 2.5f;

    //Fan-FAKTOREn

    /** TODO Missing Parameter Documentation */
    public static final int FANS_GUT = 30;

    /** TODO Missing Parameter Documentation */
    public static final int FANS_NORMAL = 25;

    /** TODO Missing Parameter Documentation */
    public static final int FANS_SCHLECHT = 20;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of ArenaSizer
     */
    public ArenaSizer() {
        /* Neue Kosten für die nächste Saison schon angepasst
           if( updatetest )
           {
               java.util.GregorianCalendar aktuCalendar    = new java.util.GregorianCalendar(  );
               java.util.GregorianCalendar umstell2Calendar = new java.util.GregorianCalendar( 2005, 4, 31 );
               //Und nochmal verdoppeln wenn nach 2.ter Umstellung
               if( aktuCalendar.after( umstell2Calendar ) )
               {
                   STEH_AUSBAU     *=  2;
                   SITZ_AUSBAU     *=  2;
                   DACH_AUSBAU     *=  2;
                   LOGEN_AUSBAU    *=  2;
                   ABRISS          *=  2;
                   FIXKOSTEN       *=  2;
               }
               updatetest  = false;
           }
         **/
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * berechnet die möglichen Ausbaustufen
     *
     * @param aktuell TODO Missing Constructuor Parameter Documentation
     * @param anzFans TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Stadium[] berechneAusbaustufen(Stadium aktuell, int anzFans) {
        Stadium arenaGut = null;
        Stadium arenaNormal = null;
        Stadium arenaSchlecht = null;
        final float sizeGut = calcOptimalSize(FANS_GUT, anzFans);
        final float sizeNormal = calcOptimalSize(FANS_NORMAL, anzFans);
        final float sizeSchlecht = calcOptimalSize(FANS_SCHLECHT, anzFans);

        //gut
        arenaGut = new Stadium();
        arenaGut.setStehplaetze(calcStehVerteilung(sizeGut));
        arenaGut.setAusbauStehplaetze(arenaGut.getStehplaetze() - aktuell.getStehplaetze());
        arenaGut.setSitzplaetze(calcSitzVerteilung(sizeGut));
        arenaGut.setAusbauSitzplaetze(arenaGut.getSitzplaetze() - aktuell.getSitzplaetze());
        arenaGut.setUeberdachteSitzplaetze(calcDachVerteilung(sizeGut));
        arenaGut.setAusbauUeberdachteSitzplaetze(arenaGut.getUeberdachteSitzplaetze()
                                                 - aktuell.getUeberdachteSitzplaetze());
        arenaGut.setLogen(calcLogenVerteilung(sizeGut));
        arenaGut.setAusbauLogen(arenaGut.getLogen() - aktuell.getLogen());

        arenaGut.setAusbauKosten(calcBauKosten(arenaGut.getAusbauStehplaetze(),
                                               arenaGut.getAusbauSitzplaetze(),
                                               arenaGut.getAusbauUeberdachteSitzplaetze(),
                                               arenaGut.getAusbauLogen()));

        //nmormal
        arenaNormal = new Stadium();
        arenaNormal.setStehplaetze(calcStehVerteilung(sizeNormal));
        arenaNormal.setAusbauStehplaetze(arenaNormal.getStehplaetze() - aktuell.getStehplaetze());
        arenaNormal.setSitzplaetze(calcSitzVerteilung(sizeNormal));
        arenaNormal.setAusbauSitzplaetze(arenaNormal.getSitzplaetze() - aktuell.getSitzplaetze());
        arenaNormal.setUeberdachteSitzplaetze(calcDachVerteilung(sizeNormal));
        arenaNormal.setAusbauUeberdachteSitzplaetze(arenaNormal.getUeberdachteSitzplaetze()
                                                    - aktuell.getUeberdachteSitzplaetze());
        arenaNormal.setLogen(calcLogenVerteilung(sizeNormal));
        arenaNormal.setAusbauLogen(arenaNormal.getLogen() - aktuell.getLogen());

        arenaNormal.setAusbauKosten(calcBauKosten(arenaNormal.getAusbauStehplaetze(),
                                                  arenaNormal.getAusbauSitzplaetze(),
                                                  arenaNormal.getAusbauUeberdachteSitzplaetze(),
                                                  arenaNormal.getAusbauLogen()));

        //Schlecht
        arenaSchlecht = new Stadium();
        arenaSchlecht.setStehplaetze(calcStehVerteilung(sizeSchlecht));
        arenaSchlecht.setAusbauStehplaetze(arenaSchlecht.getStehplaetze()
                                           - aktuell.getStehplaetze());
        arenaSchlecht.setSitzplaetze(calcSitzVerteilung(sizeSchlecht));
        arenaSchlecht.setAusbauSitzplaetze(arenaSchlecht.getSitzplaetze()
                                           - aktuell.getSitzplaetze());
        arenaSchlecht.setUeberdachteSitzplaetze(calcDachVerteilung(sizeSchlecht));
        arenaSchlecht.setAusbauUeberdachteSitzplaetze(arenaSchlecht.getUeberdachteSitzplaetze()
                                                      - aktuell.getUeberdachteSitzplaetze());
        arenaSchlecht.setLogen(calcLogenVerteilung(sizeSchlecht));
        arenaSchlecht.setAusbauLogen(arenaSchlecht.getLogen() - aktuell.getLogen());

        arenaSchlecht.setAusbauKosten(calcBauKosten(arenaSchlecht.getAusbauStehplaetze(),
                                                    arenaSchlecht.getAusbauSitzplaetze(),
                                                    arenaSchlecht.getAusbauUeberdachteSitzplaetze(),
                                                    arenaSchlecht.getAusbauLogen()));

        return new Stadium[]{arenaGut, arenaNormal, arenaSchlecht};
    }

    /**
     * berehnet die Baukosten
     *
     * @param steh TODO Missing Constructuor Parameter Documentation
     * @param sitz TODO Missing Constructuor Parameter Documentation
     * @param dach TODO Missing Constructuor Parameter Documentation
     * @param logen TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final float calcBauKosten(float steh, float sitz, float dach, float logen) {
        float kosten = FIXKOSTEN / gui.UserParameter.instance().faktorGeld;

        if (steh > 0) {
            kosten += ((steh * STEH_AUSBAU) / gui.UserParameter.instance().faktorGeld);
        } else {
            kosten -= ((steh * ABRISS) / gui.UserParameter.instance().faktorGeld);
        }

        if (sitz > 0) {
            kosten += ((sitz * SITZ_AUSBAU) / gui.UserParameter.instance().faktorGeld);
        } else {
            kosten -= ((sitz * ABRISS) / gui.UserParameter.instance().faktorGeld);
        }

        if (dach > 0) {
            kosten += ((dach * DACH_AUSBAU) / gui.UserParameter.instance().faktorGeld);
        } else {
            kosten -= ((dach * ABRISS) / gui.UserParameter.instance().faktorGeld);
        }

        if (logen > 0) {
            kosten += ((logen * LOGEN_AUSBAU) / gui.UserParameter.instance().faktorGeld);
        } else {
            kosten -= ((logen * ABRISS) / gui.UserParameter.instance().faktorGeld);
        }

        return kosten;
    }

    /**
     * berechnet die Platzverteilung
     *
     * @param stadionGr TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int calcDachVerteilung(float stadionGr) {
        return (int) ((stadionGr / 100.0f) * DACH_PROZENT);
    }

    /**
     * berechnet die Platzverteilung
     *
     * @param stadionGr TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int calcLogenVerteilung(float stadionGr) {
        return (int) ((stadionGr / 100.0f) * LOGEN_PROZENT);
    }

    /**
     * berechnet die optimale stadion grösse
     *
     * @param fanFaktor TODO Missing Constructuor Parameter Documentation
     * @param anzahlFans TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int calcOptimalSize(int fanFaktor, int anzahlFans) {
        return (int) fanFaktor * anzahlFans;
    }

    /**
     * berechnet die Platzverteilung
     *
     * @param stadionGr TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int calcSitzVerteilung(float stadionGr) {
        return (int) ((stadionGr / 100.0f) * SITZ_PROZENT);
    }

    /**
     * berechnet die Platzverteilung
     *
     * @param stadionGr TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int calcStehVerteilung(float stadionGr) {
        return (int) ((stadionGr / 100.0f) * STEH_PROZENT);
    }

    /**
     * berechnet den Unterhalt
     *
     * @param arena TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final float calcUnterhalt(Stadium arena) {
        float kosten = 0.0f;

        kosten += ((arena.getStehplaetze() * STEH_UNTERHALT) / gui.UserParameter.instance().faktorGeld);
        kosten += ((arena.getSitzplaetze() * SITZ_UNTERHALT) / gui.UserParameter.instance().faktorGeld);
        kosten += ((arena.getUeberdachteSitzplaetze() * DACH_UNTERHALT) / gui.UserParameter
                                                                          .instance().faktorGeld);
        kosten += ((arena.getLogen() * LOGEN_UNTERHALT) / gui.UserParameter.instance().faktorGeld);

        return de.hattrickorganizer.tools.Helper.round(kosten, 1);
    }
}
