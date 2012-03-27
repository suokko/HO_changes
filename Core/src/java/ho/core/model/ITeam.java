// %2413336229:plugins%
package ho.core.model;

/**
 * Enthält die Daten des Teams (nicht der Spieler!); Stores data from team section of HRF ( no
 * players are stored here )
 */
public interface ITeam {
    //team spirit
    public static final int TS_paradiesisch = 10;
    public static final int TS_auf_Wolke_sieben = 9;
    public static final int TS_euphorisch = 8;
    public static final int TS_ausgezeichnet = 7;
    public static final int TS_gut = 6;
    public static final int TS_zufrieden = 5;
    public static final int TS_ruhig = 4;
    public static final int TS_irritiert = 3;
    public static final int TS_wuetend = 2;
    public static final int TS_blutruenstig = 1;
    public static final int TS_wie_im_kalten_Krieg = 0;

    //self confidence
    public static final int SV_voellig_abgehoben = 9;
    public static final int SV_voellig_uebertrieben = 8;
    public static final int SV_etwas_ueberheblich = 7;
    public static final int SV_sehr_gross = 6;
    public static final int SV_stark = 5;
    public static final int SV_bescheiden = 4;
    public static final int SV_gering = 3;
    public static final int SV_armselig = 2;
    public static final int SV_katastrophal = 1;
    public static final int SV_nichtVorhanden = 0;

    //training types
    public static final int TA_EXTERNALATTACK = 12;		// wing attacks
    public static final int TA_ABWEHRVERHALTEN = 11;	// def. positions
    public static final int TA_STEILPAESSE = 10;		// through passes
    public static final int TA_TORWART = 9;				// goalkeeping
    public static final int TA_SPIELAUFBAU = 8;			// playmaking
    public static final int TA_PASSSPIEL = 7;			// short passes
    public static final int TA_SCHUSSTRAINING = 6;		// shooting
    public static final int TA_FLANKEN = 5;				// crossing / winger
    public static final int TA_CHANCEN = 4;				// scoring
    public static final int TA_VERTEIDIGUNG = 3;		// defending
    public static final int TA_STANDARD = 2;			// set pieces

}
