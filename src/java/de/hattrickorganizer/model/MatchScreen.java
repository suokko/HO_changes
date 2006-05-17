// %1364880782:de.hattrickorganizer.model%
/*
 * MatchScreen.java
 *
 * Created on 6. Mai 2003, 13:53
 */
package de.hattrickorganizer.model;

/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public interface MatchScreen {
    //~ Static fields/initializers -----------------------------------------------------------------

    /*
       //Welle rauscht durch's Stadion
       public static final byte    MSG_KOMMENTAR           =   0;
       //hat den ball und
       public static final byte    MSG_TORCHANCE           =   1;
       //genau To
       public static final byte    MSG_TOR                 =   2;
       //NEIN Gehalten
       public static final byte    MSG_TORCHANCE_VERGEBEN  =   3;
       //Suff Suff Suff Hurrrraaaa
       public static final byte    MSG_FANGESANG           =   4;
       //Vortext halt
       public static final byte    MSG_VORTEXT             =   5;
     */

    //Key Notation

    /** TODO Missing Parameter Documentation */
    public static final int KEY_START = 0;

    /** TODO Missing Parameter Documentation */
    public static final int KEY_AKTION = 1;

    /** TODO Missing Parameter Documentation */
    public static final int KEY_ABSCHLUSS_TOR = 2;

    /** TODO Missing Parameter Documentation */
    public static final int KEY_ABSCHLUSS_VERGEBEN = 3;

    //Variablen 

    /** TODO Missing Parameter Documentation */
    public static final String VAR_SCHUETZE = "%schuetze%";

    /** TODO Missing Parameter Documentation */
    public static final String VAR_VORBEREITER1 = "%vorbereiter1%";

    //public static final String  VAR_VORBEREITER2        =   "%vorbereiter2%";

    /** TODO Missing Parameter Documentation */
    public static final String VAR_VERTEIDIGER1 = "%verteidiger1%";

    /** TODO Missing Parameter Documentation */
    public static final String VAR_VERTEIDIGER2 = "%verteidiger2%";

    /** TODO Missing Parameter Documentation */
    public static final String VAR_TORWART = "%torwart%";

    /** TODO Missing Parameter Documentation */
    public static final String VAR_FANTEAM = "%fanteam%";

    /** TODO Missing Parameter Documentation */
    public static final String VAR_FANMANAGER = "%fanManager%";

    /** TODO Missing Parameter Documentation */
    public static final String VAR_GEGNERTEAM = "%gegnerTeam%";

    /** TODO Missing Parameter Documentation */
    public static final String VAR_GEGNERMANAGER = "%gegnerManager%";

    /** TODO Missing Parameter Documentation */
    public static final String VAR_AUSWECHSELSPIELER = "%auswechsel%";

    /** TODO Missing Parameter Documentation */
    public static final String VAR_EINWECHSELSPIELER = "%einwechsel%";

    /** TODO Missing Parameter Documentation */
    public static final String VAR_SPIELMINUTE = "%minute%";

    /** TODO Missing Parameter Documentation */
    public static final String VAR_TEAMNAME = "%teamname%";

    /** TODO Missing Parameter Documentation */
    public static final String VAR_SPIELERNAME = "%spielername%";

    /** TODO Missing Parameter Documentation */
    public static final String CMD_WAIT = "%wait%";

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public boolean isAbbruch();

    /**
     * TODO Missing Method Documentation
     *
     * @param pause TODO Missing Method Parameter Documentation
     */
    public void setPause(boolean pause);

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public boolean isPause();

    /**
     * gibt an ob der Screen bereit zum zeichnen ist
     *
     * @return TODO Missing Return Method Documentation
     */
    public boolean isScreenBereit();

    /**
     * gibt eine Auswechslung an
     *
     * @param textKey TODO Missing Constructuor Parameter Documentation
     * @param teamName TODO Missing Constructuor Parameter Documentation
     * @param auswechselSpieler TODO Missing Constructuor Parameter Documentation
     * @param einwechselSpieler TODO Missing Constructuor Parameter Documentation
     * @param variante TODO Missing Constructuor Parameter Documentation
     * @param spielminute TODO Missing Constructuor Parameter Documentation
     * @param heim TODO Missing Constructuor Parameter Documentation
     */
    public void doAuswechslung(byte textKey, String teamName, String auswechselSpieler,
                               String einwechselSpieler, int variante, int spielminute, boolean heim);

    /**
     * Fangesang
     *
     * @param key FanGesangsArt ,steht für Anfeuerung, verhöhnen
     * @param art (Spieler, Team, Trainer )
     * @param variante welche Variante
     * @param fanTeam Füllmaterial für die Variable
     * @param fanManager TODO Missing Constructuor Parameter Documentation
     * @param gegnerTeam TODO Missing Constructuor Parameter Documentation
     * @param gegnerManager TODO Missing Constructuor Parameter Documentation
     * @param heim TODO Missing Constructuor Parameter Documentation
     */
    public void doFangesang(int key, int art, int variante, String fanTeam, String fanManager,
                            String gegnerTeam, String gegnerManager, boolean heim);

    /**
     * übermittelt Inforamtionen zu einer Karte
     *
     * @param textKey TODO Missing Constructuor Parameter Documentation
     * @param spielerName TODO Missing Constructuor Parameter Documentation
     * @param spielminute TODO Missing Constructuor Parameter Documentation
     * @param variante TODO Missing Constructuor Parameter Documentation
     * @param trainerVariante TODO Missing Constructuor Parameter Documentation
     * @param heim TODO Missing Constructuor Parameter Documentation
     */
    public void doKarte(byte textKey, String spielerName, int spielminute, int variante,
                        int trainerVariante, boolean heim);

    /**
     * gibt an das ne Minute rum ist :)
     *
     * @param spielminute TODO Missing Constructuor Parameter Documentation
     */
    public void doMinuteRum(int spielminute);

    /**
     * TODO Missing Method Documentation
     *
     * @param sb TODO Missing Method Parameter Documentation
     */
    public void doSpielbeginn(Spielbericht sb);

    /**
     * Spielende
     *
     * @param sb TODO Missing Constructuor Parameter Documentation
     */
    public void doSpielende(Spielbericht sb);

    /**
     * Stellt Text dar
     *
     * @param heimTeam TODO Missing Constructuor Parameter Documentation
     * @param schuetze TODO Missing Constructuor Parameter Documentation
     * @param vorbereiter1 TODO Missing Constructuor Parameter Documentation
     * @param verteidiger1 TODO Missing Constructuor Parameter Documentation
     * @param verteidiger2 TODO Missing Constructuor Parameter Documentation
     * @param torwart TODO Missing Constructuor Parameter Documentation
     * @param torart TODO Missing Constructuor Parameter Documentation
     * @param torchance TODO Missing Constructuor Parameter Documentation
     * @param variante TODO Missing Constructuor Parameter Documentation
     * @param trainerVariante TODO Missing Constructuor Parameter Documentation
     * @param trainerVariante2 TODO Missing Constructuor Parameter Documentation
     * @param aktionen TODO Missing Constructuor Parameter Documentation
     * @param spielminute TODO Missing Constructuor Parameter Documentation
     */

    // public void drawString( String msg, byte msgTyp );

    /**
     * Stellt blinkenden Text dar
     *
     * @param heimTeam TODO Missing Constructuor Parameter Documentation
     * @param schuetze TODO Missing Constructuor Parameter Documentation
     * @param vorbereiter1 TODO Missing Constructuor Parameter Documentation
     * @param verteidiger1 TODO Missing Constructuor Parameter Documentation
     * @param verteidiger2 TODO Missing Constructuor Parameter Documentation
     * @param torwart TODO Missing Constructuor Parameter Documentation
     * @param torart TODO Missing Constructuor Parameter Documentation
     * @param torchance TODO Missing Constructuor Parameter Documentation
     * @param variante TODO Missing Constructuor Parameter Documentation
     * @param trainerVariante TODO Missing Constructuor Parameter Documentation
     * @param trainerVariante2 TODO Missing Constructuor Parameter Documentation
     * @param aktionen TODO Missing Constructuor Parameter Documentation
     * @param spielminute TODO Missing Constructuor Parameter Documentation
     */

    //    public void drawBlinkingString( String msg, byte msgTyp, byte sec );

    /**
     * läßt die Anzeige der Heimtore Rot blinken
     *
     * @param heimTeam TODO Missing Constructuor Parameter Documentation
     * @param schuetze TODO Missing Constructuor Parameter Documentation
     * @param vorbereiter1 TODO Missing Constructuor Parameter Documentation
     * @param verteidiger1 TODO Missing Constructuor Parameter Documentation
     * @param verteidiger2 TODO Missing Constructuor Parameter Documentation
     * @param torwart TODO Missing Constructuor Parameter Documentation
     * @param torart TODO Missing Constructuor Parameter Documentation
     * @param torchance TODO Missing Constructuor Parameter Documentation
     * @param variante TODO Missing Constructuor Parameter Documentation
     * @param trainerVariante TODO Missing Constructuor Parameter Documentation
     * @param trainerVariante2 TODO Missing Constructuor Parameter Documentation
     * @param aktionen TODO Missing Constructuor Parameter Documentation
     * @param spielminute TODO Missing Constructuor Parameter Documentation
     */

    //    public void blinkHeimTore( byte sec );

    /**
     * läßt die Anzeige der Heimtore Rot blinken
     *
     * @param heimTeam TODO Missing Constructuor Parameter Documentation
     * @param schuetze TODO Missing Constructuor Parameter Documentation
     * @param vorbereiter1 TODO Missing Constructuor Parameter Documentation
     * @param verteidiger1 TODO Missing Constructuor Parameter Documentation
     * @param verteidiger2 TODO Missing Constructuor Parameter Documentation
     * @param torwart TODO Missing Constructuor Parameter Documentation
     * @param torart TODO Missing Constructuor Parameter Documentation
     * @param torchance TODO Missing Constructuor Parameter Documentation
     * @param variante TODO Missing Constructuor Parameter Documentation
     * @param trainerVariante TODO Missing Constructuor Parameter Documentation
     * @param trainerVariante2 TODO Missing Constructuor Parameter Documentation
     * @param aktionen TODO Missing Constructuor Parameter Documentation
     * @param spielminute TODO Missing Constructuor Parameter Documentation
     */

    //    public void blinkHeimGast( byte sec );

    /**
     * passt Spielstand an
     *
     * @param heimTeam TODO Missing Constructuor Parameter Documentation
     * @param schuetze TODO Missing Constructuor Parameter Documentation
     * @param vorbereiter1 TODO Missing Constructuor Parameter Documentation
     * @param verteidiger1 TODO Missing Constructuor Parameter Documentation
     * @param verteidiger2 TODO Missing Constructuor Parameter Documentation
     * @param torwart TODO Missing Constructuor Parameter Documentation
     * @param torart TODO Missing Constructuor Parameter Documentation
     * @param torchance TODO Missing Constructuor Parameter Documentation
     * @param variante TODO Missing Constructuor Parameter Documentation
     * @param trainerVariante TODO Missing Constructuor Parameter Documentation
     * @param trainerVariante2 TODO Missing Constructuor Parameter Documentation
     * @param aktionen TODO Missing Constructuor Parameter Documentation
     * @param spielminute TODO Missing Constructuor Parameter Documentation
     */

    //    public void setSpielstand( byte toreHeim, byte ToreGast );

    /**
     * passt Torchancen an
     *
     * @param heimTeam TODO Missing Constructuor Parameter Documentation
     * @param schuetze TODO Missing Constructuor Parameter Documentation
     * @param vorbereiter1 TODO Missing Constructuor Parameter Documentation
     * @param verteidiger1 TODO Missing Constructuor Parameter Documentation
     * @param verteidiger2 TODO Missing Constructuor Parameter Documentation
     * @param torwart TODO Missing Constructuor Parameter Documentation
     * @param torart TODO Missing Constructuor Parameter Documentation
     * @param torchance TODO Missing Constructuor Parameter Documentation
     * @param variante TODO Missing Constructuor Parameter Documentation
     * @param trainerVariante TODO Missing Constructuor Parameter Documentation
     * @param trainerVariante2 TODO Missing Constructuor Parameter Documentation
     * @param aktionen TODO Missing Constructuor Parameter Documentation
     * @param spielminute TODO Missing Constructuor Parameter Documentation
     */

    //    public void setTorchancenHeim( byte anzahl );

    /**
     * passt Torchancen an
     *
     * @param heimTeam TODO Missing Constructuor Parameter Documentation
     * @param schuetze TODO Missing Constructuor Parameter Documentation
     * @param vorbereiter1 TODO Missing Constructuor Parameter Documentation
     * @param verteidiger1 TODO Missing Constructuor Parameter Documentation
     * @param verteidiger2 TODO Missing Constructuor Parameter Documentation
     * @param torwart TODO Missing Constructuor Parameter Documentation
     * @param torart TODO Missing Constructuor Parameter Documentation
     * @param torchance TODO Missing Constructuor Parameter Documentation
     * @param variante TODO Missing Constructuor Parameter Documentation
     * @param trainerVariante TODO Missing Constructuor Parameter Documentation
     * @param trainerVariante2 TODO Missing Constructuor Parameter Documentation
     * @param aktionen TODO Missing Constructuor Parameter Documentation
     * @param spielminute TODO Missing Constructuor Parameter Documentation
     */

    //    public void setTorchancenGast( byte anzahl );

    /**
     * zeigt eine Torchance an Inhalt am besten in thread auslagern! Wenn Thread fertig aus diesem
     * Heraus bool isTorchancefertig auf true setzen  sofort return dieser MEthode weil ist ja 'n
     * Thread
     *
     * @param heimTeam TODO Missing Constructuor Parameter Documentation
     * @param schuetze TODO Missing Constructuor Parameter Documentation
     * @param vorbereiter1 TODO Missing Constructuor Parameter Documentation
     * @param verteidiger1 TODO Missing Constructuor Parameter Documentation
     * @param verteidiger2 TODO Missing Constructuor Parameter Documentation
     * @param torwart TODO Missing Constructuor Parameter Documentation
     * @param torart TODO Missing Constructuor Parameter Documentation
     * @param torchance TODO Missing Constructuor Parameter Documentation
     * @param variante TODO Missing Constructuor Parameter Documentation
     * @param trainerVariante TODO Missing Constructuor Parameter Documentation
     * @param trainerVariante2 TODO Missing Constructuor Parameter Documentation
     * @param aktionen TODO Missing Constructuor Parameter Documentation
     * @param spielminute TODO Missing Constructuor Parameter Documentation
     */

    /*String vorbereiter2, */
    public void doTorchance(boolean heimTeam, String schuetze, String vorbereiter1,
                            String verteidiger1, String verteidiger2, String torwart, byte torart,
                            byte torchance, int variante, int trainerVariante,
                            int trainerVariante2, int[] aktionen, int spielminute);

    /**
     * stellt eine Verletzung dar
     *
     * @param textKey TODO Missing Constructuor Parameter Documentation
     * @param spielerName TODO Missing Constructuor Parameter Documentation
     * @param spielminute TODO Missing Constructuor Parameter Documentation
     * @param variante TODO Missing Constructuor Parameter Documentation
     * @param trainerVariante TODO Missing Constructuor Parameter Documentation
     * @param heim TODO Missing Constructuor Parameter Documentation
     */
    public void doVerletzung(byte textKey, String spielerName, int spielminute, int variante,
                             int trainerVariante, boolean heim);

    /**
     * Gibt einen Infotext wieder z.B: gleich geht's los, Halbzeit, ElferSchießen, Spielende...
     *
     * @param textKey == Info, Vortext usw.
     * @param variante TODO Missing Constructuor Parameter Documentation
     */
    public void drawInfoText(byte textKey, int variante);

    /**
     * empfängt Msg und stellt sie dar
     *
     * @param trainer der Trainername
     * @param msg die Msg
     * @param heim , gibt an ob heim oder Gast der Absender ist
     */
    public void recieveMsg(String trainer, String msg, boolean heim);
}
