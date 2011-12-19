// %2354158977:de.hattrickorganizer.logik%
/*
 * ArenaSizer.java
 *
 * Created on 21. März 2003, 08:19
 */
package ho.modul.arenasizer;

import java.math.BigDecimal;

import de.hattrickorganizer.tools.Helper;

class ArenaSizer {

    //INCOME
    static final float STEH_EINTRITT = 65f;
    static final float SITZ_EINTRITT = 95f;
    static final float DACH_EINTRITT = 180f;
    static final float LOGEN_EINTRITT = 325f;

    //COSTS
    static final float STEH_UNTERHALT = 5f;
    static final float SITZ_UNTERHALT = 7f;
    static final float DACH_UNTERHALT = 10f;
    static final float LOGEN_UNTERHALT = 25f;

    //CREATE
    static float STEH_AUSBAU = 450f;
    static float SITZ_AUSBAU = 750f;
    static float DACH_AUSBAU = 900f;
    static float LOGEN_AUSBAU = 3000f;
    static float ABRISS = 60f;
    static float FIXKOSTEN = 100000f;

    //DISTRIBUTION
    static final float STEH_PROZENT = 60.0f;
    static final float SITZ_PROZENT = 23.5f;
    static final float DACH_PROZENT = 14.0f;
    static final float LOGEN_PROZENT = 2.5f;
    
    static final BigDecimal TERRACES_PERCENT = new BigDecimal(0.60).setScale(3, BigDecimal.ROUND_HALF_DOWN);
    static final BigDecimal BASICS_PERCENT = new BigDecimal(0.235).setScale(3, BigDecimal.ROUND_HALF_DOWN);
    static final BigDecimal ROOF_PERCENT = new BigDecimal(0.14).setScale(3, BigDecimal.ROUND_HALF_DOWN);
    static final BigDecimal VIP_PERCENT = new BigDecimal(0.025).setScale(3, BigDecimal.ROUND_HALF_DOWN);

    //SUPPORTER-DISTRIBUTION
    static final int FANS_GUT = 25;
    static final int FANS_NORMAL = 20;
    static final int FANS_SCHLECHT = 15;

    static final Integer SUPPORTER_MAX = 25;
    static final Integer SUPPORTER_NORMAL = 20;
    static final Integer SUPPORTER_MIN = 15;
    //~ Constructors -------------------------------------------------------------------------------

    float currencyFactor = gui.UserParameter.instance().faktorGeld;
    /**
     * Creates a new instance of ArenaSizer
     */
    ArenaSizer() {

    }

    //~ Methods ------------------------------------------------------------------------------------

    final Stadium[] berechneAusbaustufen(Stadium aktuell, int anzFans) {
        Stadium arenaMax = createStadium(FANS_GUT * anzFans,aktuell);
        Stadium arenaNormal = createStadium(FANS_NORMAL * anzFans,aktuell);
        Stadium arenaMin = createStadium(FANS_SCHLECHT * anzFans,aktuell);
        return new Stadium[]{arenaMax, arenaNormal, arenaMin};
    }
    
    final Stadium[] calcConstructionArenas(Stadium currentArena, int maxSupporter, int normalSupporter, int minSupporter){
        Stadium arenaMax = createStadium(maxSupporter,currentArena);
        Stadium arenaNormal = createStadium(normalSupporter,currentArena);
        Stadium arenaMin = createStadium(minSupporter,currentArena);
        return new Stadium[]{arenaMax, arenaNormal, arenaMin};
    }

    private Stadium createStadium(int size,Stadium aktuell){
    	Stadium tmp = new Stadium();
    	BigDecimal sizeNumber = new BigDecimal(size);
    	tmp.setStehplaetze(TERRACES_PERCENT.multiply(sizeNumber).intValue());
    	tmp.setAusbauStehplaetze(tmp.getStehplaetze() - aktuell.getStehplaetze());
    	tmp.setSitzplaetze(BASICS_PERCENT.multiply(sizeNumber).intValue());
    	tmp.setAusbauSitzplaetze(tmp.getSitzplaetze() - aktuell.getSitzplaetze());
    	tmp.setUeberdachteSitzplaetze(ROOF_PERCENT.multiply(sizeNumber).intValue());
    	tmp.setAusbauUeberdachteSitzplaetze(tmp.getUeberdachteSitzplaetze()- aktuell.getUeberdachteSitzplaetze());
    	tmp.setLogen(VIP_PERCENT.multiply(sizeNumber).intValue());
    	tmp.setAusbauLogen(tmp.getLogen() - aktuell.getLogen());

    	tmp.setAusbauKosten(calcBauKosten(tmp.getAusbauStehplaetze(),
    			tmp.getAusbauSitzplaetze(),
    			tmp.getAusbauUeberdachteSitzplaetze(),
    			tmp.getAusbauLogen()));
        return tmp;
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
    final float calcBauKosten(float steh, float sitz, float dach, float logen) {
        float kosten = FIXKOSTEN / currencyFactor;

        if (steh > 0) {
            kosten += ((steh * STEH_AUSBAU) / currencyFactor);
        } else {
            kosten -= ((steh * ABRISS) / currencyFactor);
        }

        if (sitz > 0) {
            kosten += ((sitz * SITZ_AUSBAU) / currencyFactor);
        } else {
            kosten -= ((sitz * ABRISS) / currencyFactor);
        }

        if (dach > 0) {
            kosten += ((dach * DACH_AUSBAU) / currencyFactor);
        } else {
            kosten -= ((dach * ABRISS) / currencyFactor);
        }

        if (logen > 0) {
            kosten += ((logen * LOGEN_AUSBAU) / currencyFactor);
        } else {
            kosten -= ((logen * ABRISS) / currencyFactor);
        }

        return kosten;
    }

    final int calcDistribution(float stadionGr,float percent) {
        return (int) ((stadionGr / 100.0f) * percent);
    }

    /**
     * berechnet den Unterhalt
     *
     * @param arena TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    final float calcMaintenance(Stadium arena) {
        float costs = 0.0f;

        costs += ((arena.getStehplaetze() * STEH_UNTERHALT) / currencyFactor);
        costs += ((arena.getSitzplaetze() * SITZ_UNTERHALT) / currencyFactor);
        costs += ((arena.getUeberdachteSitzplaetze() * DACH_UNTERHALT) / currencyFactor);
        costs += ((arena.getLogen() * LOGEN_UNTERHALT) / currencyFactor);

        return Helper.round(costs, 1);
    }
}
