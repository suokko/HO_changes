// %2089341135:de.hattrickorganizer.net.rmiHOFriendly%
package de.hattrickorganizer.net.rmiHOFriendly;

import de.hattrickorganizer.model.Spielbericht;
import de.hattrickorganizer.tools.HOLogger;


/**
 * MatchScreen verbindung zum anderen Rechner
 */
public class NetMatchScreen implements de.hattrickorganizer.model.MatchScreen {
    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    protected de.hattrickorganizer.gui.hoFriendly.HOFriendlyDialog m_clDialog;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new NetMatchScreen object.
     *
     * @param link TODO Missing Constructuor Parameter Documentation
     */
    public NetMatchScreen(de.hattrickorganizer.gui.hoFriendly.HOFriendlyDialog link) {
        m_clDialog = link;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean isAbbruch() {
        try {
            return m_clDialog.isAbbruch();
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }

        return true;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param pause TODO Missing Method Parameter Documentation
     */
    public final void setPause(boolean pause) {
        final boolean fpause = pause;

        new Thread(new Runnable() {
                public void run() {
                    try {
                        m_clDialog.setPause(fpause);
                    } catch (Exception e) {
                        HOLogger.instance().log(getClass(),e);
                    }
                }
            }).start();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean isPause() {
        try {
            return m_clDialog.isPause();
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }

        return false;
    }

    /**
     * gibt an ob der Screen bereit zum zeichnen ist
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean isScreenBereit() {
        try {
            return m_clDialog.isScreenBereit();
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }

        return false;
    }

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
    public final void doAuswechslung(byte textKey, String teamName, String auswechselSpieler,
                                     String einwechselSpieler, int variante, int spielminute,
                                     boolean heim) {
        final byte ftextKey = textKey;
        final String fteamName = teamName;
        final String fauswechselSpieler = auswechselSpieler;
        final String feinwechselSpieler = einwechselSpieler;
        final int fvariante = variante;
        final int fspielminute = spielminute;
        final boolean fheim = heim;

        new Thread(new Runnable() {
                public void run() {
                    try {
                        m_clDialog.doAuswechslung(ftextKey, fteamName, fauswechselSpieler,
                                                  feinwechselSpieler, fvariante, fspielminute, fheim);
                    } catch (Exception e) {
                        HOLogger.instance().log(getClass(),e);
                    }
                }
            }).start();
    }

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
    public final void doFangesang(int key, int art, int variante, String fanTeam,
                                  String fanManager, String gegnerTeam, String gegnerManager,
                                  boolean heim) {
        final int fkey = key;
        final int fart = art;
        final int fvariante = variante;
        final String ffanTeam = fanTeam;
        final String ffanManager = fanManager;
        final String fgegnerTeam = gegnerTeam;
        final String fgegnerManager = gegnerManager;
        final boolean fheim = heim;

        new Thread(new Runnable() {
                public void run() {
                    try {
                        m_clDialog.doFangesang(fkey, fart, fvariante, ffanTeam, ffanManager,
                                               fgegnerTeam, fgegnerManager, fheim);
                    } catch (Exception e) {
                        HOLogger.instance().log(getClass(),e);
                    }
                }
            }).start();
    }

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
    public final void doKarte(byte textKey, String spielerName, int spielminute, int variante,
                              int trainerVariante, boolean heim) {
        final byte ftextKey = textKey;
        final String fspielerName = spielerName;
        final int fspielminute = spielminute;
        final int fvariante = variante;
        final int ftrainervariante = trainerVariante;
        final boolean fheim = heim;

        new Thread(new Runnable() {
                public void run() {
                    try {
                        m_clDialog.doKarte(ftextKey, fspielerName, fspielminute, fvariante,
                                           ftrainervariante, fheim);
                    } catch (Exception e) {
                        HOLogger.instance().log(getClass(),e);
                    }
                }
            }).start();
    }

    /**
     * gibt an das ne Minute rum ist :)
     *
     * @param spielminute TODO Missing Constructuor Parameter Documentation
     */
    public final void doMinuteRum(int spielminute) {
        final int fspielminute = spielminute;

        new Thread(new Runnable() {
                public void run() {
                    try {
                        m_clDialog.doMinuteRum(fspielminute);
                    } catch (Exception e) {
                        HOLogger.instance().log(getClass(),e);
                    }
                }
            }).start();
    }

    /**
     * Spielende
     *
     * @param sb TODO Missing Constructuor Parameter Documentation
     */
    public final void doSpielbeginn(Spielbericht sb) {
        final Spielbericht fsb = sb;

        new Thread(new Runnable() {
                public void run() {
                    try {
                        m_clDialog.doSpielbeginn(fsb);
                    } catch (Exception e) {
                        HOLogger.instance().log(getClass(),e);
                    }
                }
            }).start();
    }

    /**
     * Spielende
     *
     * @param sb TODO Missing Constructuor Parameter Documentation
     */
    public final void doSpielende(Spielbericht sb) {
        final Spielbericht fsb = sb;

        new Thread(new Runnable() {
                public void run() {
                    try {
                        m_clDialog.doSpielende(fsb);
                    } catch (Exception e) {
                        HOLogger.instance().log(getClass(),e);
                    }
                }
            }).start();
    }

    /**
     * zeigt eine Torchance an Inhalt am besten in thread auslagern! Wenn Thread fertig aus diesem
     * Heraus bool isTorchancefertig auf true setzen sofort return dieser MEthode weil ist ja 'n
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
    public final void doTorchance(boolean heimTeam, String schuetze, String vorbereiter1,
                                  String verteidiger1, String verteidiger2, String torwart,
                                  byte torart, byte torchance, int variante, int trainerVariante,
                                  int trainerVariante2, int[] aktionen, int spielminute) {
        final boolean fheimTeam = heimTeam;
        final String fschuetze = schuetze;
        final String fvorbereiter1 = vorbereiter1;
        final String fverteidiger1 = verteidiger1;
        final String fverteidiger2 = verteidiger2;
        final String ftorwart = torwart;
        final byte ftorart = torart;
        final byte ftorchance = torchance;
        final int fvariante = variante;
        final int ftrainervariante = trainerVariante;
        final int ftrainervariante2 = trainerVariante2;
        final int[] faktionen = aktionen;
        final int fspielminute = spielminute;

        new Thread(new Runnable() {
                public void run() {
                    try {
                        m_clDialog.doTorchance(fheimTeam, fschuetze, fvorbereiter1, fverteidiger1,
                                               fverteidiger2, ftorwart, ftorart, ftorchance,
                                               fvariante, ftrainervariante, ftrainervariante2,
                                               faktionen, fspielminute);
                    } catch (Exception e) {
                        HOLogger.instance().log(getClass(),e);
                    }
                }
            }).start();
    }

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
    public final void doVerletzung(byte textKey, String spielerName, int spielminute, int variante,
                                   int trainerVariante, boolean heim) {
        final byte ftextKey = textKey;
        final String fspielerName = spielerName;
        final int fspielminute = spielminute;
        final int fvariante = variante;
        final int ftrainervariante = trainerVariante;
        final boolean fheim = heim;

        new Thread(new Runnable() {
                public void run() {
                    try {
                        m_clDialog.doVerletzung(ftextKey, fspielerName, fspielminute, fvariante,
                                                ftrainervariante, fheim);
                    } catch (Exception e) {
                        HOLogger.instance().log(getClass(),e);
                    }
                }
            }).start();
    }

    /**
     * Gibt einen Infotext wieder z.B: gleich geht's los, Halbzeit, ElferSchießen, Spielende...
     *
     * @param textKey == Info, Vortext usw.
     * @param variante TODO Missing Constructuor Parameter Documentation
     */
    public final void drawInfoText(byte textKey, int variante) {
        final byte ftextKey = textKey;
        final int fvariante = variante;

        new Thread(new Runnable() {
                public void run() {
                    try {
                        m_clDialog.drawInfoText(ftextKey, fvariante);
                    } catch (Exception e) {
                        HOLogger.instance().log(getClass(),e);
                    }
                }
            }).start();
    }

    /**
     * empfängt Msg und stellt sie dar
     *
     * @param trainer TODO Missing Constructuor Parameter Documentation
     * @param msg TODO Missing Constructuor Parameter Documentation
     * @param heim TODO Missing Constructuor Parameter Documentation
     */
    public final void recieveMsg(String trainer, String msg, boolean heim) {
        try {
            m_clDialog.recieveMsg(trainer, msg, heim);
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }
    }
}
