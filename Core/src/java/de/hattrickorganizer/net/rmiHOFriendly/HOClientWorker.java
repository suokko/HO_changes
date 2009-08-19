// %4264443665:de.hattrickorganizer.net.rmiHOFriendly%
/*
 * HOClientWorker.java
 *
 * Created on 29. Juli 2003, 10:23
 */
package de.hattrickorganizer.net.rmiHOFriendly;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import de.hattrickorganizer.model.Spielbericht;
import de.hattrickorganizer.tools.HOLogger;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class HOClientWorker implements Runnable {
    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    protected DataInputStream m_clInput;

    /** TODO Missing Parameter Documentation */
    protected HoClientImp m_clHOClient;

    /** TODO Missing Parameter Documentation */
    protected Socket m_clSocket;

    /** TODO Missing Parameter Documentation */
    protected boolean m_bRun = true;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of HOClientWorker
     *
     * @param clientSocket TODO Missing Constructuor Parameter Documentation
     * @param client TODO Missing Constructuor Parameter Documentation
     */
    public HOClientWorker(Socket clientSocket, HoClientImp client) {
        try {
            m_clHOClient = client;
            m_clSocket = clientSocket;
            m_clInput = new DataInputStream(m_clSocket.getInputStream());
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * gibt eine Auswechslung an
     */
    public final void getAuswechslung() {
        byte textKey = 0;
        String teamName = "";
        String auswechselSpieler = "";
        String einwechselSpieler = "";
        int variante = 0;
        int spielminute = 0;
        boolean heim = false;

        try {
            textKey = m_clInput.readByte();
            teamName = m_clInput.readUTF();
            auswechselSpieler = m_clInput.readUTF();
            einwechselSpieler = m_clInput.readUTF();
            variante = m_clInput.readInt();
            spielminute = m_clInput.readInt();
            heim = m_clInput.readBoolean();
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }

        m_clHOClient.getClient().doAuswechslung(textKey, teamName, auswechselSpieler,
                                                einwechselSpieler, variante, spielminute, heim);
    }

    /**
     * Fangesang
     */
    public final void getFangesang() {
        int key = 0;
        int art = 0;
        int variante = 0;
        String fanTeam = "";
        String fanManager = "";
        String gegnerTeam = "";
        String gegnerManager = "";
        boolean heim = false;

        try {
            key = m_clInput.readInt();
            art = m_clInput.readInt();
            variante = m_clInput.readInt();
            fanTeam = m_clInput.readUTF();
            fanManager = m_clInput.readUTF();
            gegnerTeam = m_clInput.readUTF();
            gegnerManager = m_clInput.readUTF();
            heim = m_clInput.readBoolean();
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }

        m_clHOClient.getClient().doFangesang(key, art, variante, fanTeam, fanManager, gegnerTeam,
                                             gegnerManager, heim);
    }

    /**
     * Gibt einen Infotext wieder z.B: gleich geht's los, Halbzeit, ElferSchießen, Spielende...
     */
    public final void getInfoText() {
        byte textKey = 0;
        int variante = 0;

        try {
            textKey = m_clInput.readByte();
            variante = m_clInput.readInt();
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }

        m_clHOClient.getClient().drawInfoText(textKey, variante);
    }

    /**
     * übermittelt Inforamtionen zu einer Karte
     */
    public final void getKarte() {
        byte textKey = 0;
        String spielerName = "";
        int spielminute = 0;
        int variante = 0;
        int trainerVariante = 0;
        boolean heim = false;

        try {
            textKey = m_clInput.readByte();
            spielerName = m_clInput.readUTF();
            spielminute = m_clInput.readInt();
            variante = m_clInput.readInt();
            trainerVariante = m_clInput.readInt();
            heim = m_clInput.readBoolean();
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }

        m_clHOClient.getClient().doKarte(textKey, spielerName, spielminute, variante,
                                         trainerVariante, heim);
    }

    /**
     * gibt an das ne Minute rum ist :)
     */
    public final void getMinuteRum() {
        int spielminute = 0;

        try {
            spielminute = m_clInput.readInt();
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }

        m_clHOClient.getClient().doMinuteRum(spielminute);
    }

    /**
     * Setter for property m_bRun.
     *
     * @param m_bRun New value of property m_bRun.
     */
    public final void setRun(boolean m_bRun) {
        this.m_bRun = m_bRun;
    }

    /**
     * Getter for property m_bRun.
     *
     * @return Value of property m_bRun.
     */
    public final boolean isRun() {
        return m_bRun;
    }

    /**
     * Spielende
     */
    public final void getSpielbeginn() {
        Spielbericht sb = null;

        try {
            sb = new Spielbericht(m_clInput);
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }

        m_clHOClient.getClient().doSpielbeginn(sb);
        ;
    }

    /**
     * Spielende
     */
    public final void getSpielende() {
        Spielbericht sb = null;

        try {
            sb = new Spielbericht(m_clInput);
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }

        //Client Bericht geben
        m_clHOClient.setSpielbericht(sb);

        //Bericht anzeigen
        m_clHOClient.getClient().doSpielende(sb);
    }

    /**
     * übermittelt den Status des Client Screens
     */

    /*    public void checkBereit()
       {
           m_clHOClient.sendBereit ( m_clHOClient.isScreenBereit () );
       }
     */

    /**
     * zeigt eine Torchance an Inhalt am besten in thread auslagern! Wenn Thread fertig aus diesem
     * Heraus bool isTorchancefertig auf true setzen  sofort return dieser MEthode weil ist ja 'n
     * Thread
     */
    public final void getTorchance() {
        boolean heimTeam = false;
        String schuetze = "";
        String vorbereiter1 = "";
        String verteidiger1 = "";
        String verteidiger2 = "";
        String torwart = "";
        byte torart = 0;
        byte torchance = 0;
        int variante = 0;
        int trainerVariante = 0;
        int trainerVariante2 = 0;
        int[] aktionen = null;
        int anzahl = 0;
        int spielminute = 0;

        try {
            heimTeam = m_clInput.readBoolean();
            schuetze = m_clInput.readUTF();
            vorbereiter1 = m_clInput.readUTF();
            verteidiger1 = m_clInput.readUTF();
            verteidiger2 = m_clInput.readUTF();
            torwart = m_clInput.readUTF();
            torart = m_clInput.readByte();
            torchance = m_clInput.readByte();
            variante = m_clInput.readInt();
            trainerVariante = m_clInput.readInt();
            trainerVariante2 = m_clInput.readInt();
            anzahl = m_clInput.readInt();
            aktionen = new int[anzahl];

            for (int i = 0; i < anzahl; i++) {
                aktionen[i] = m_clInput.readInt();
            }

            spielminute = m_clInput.readInt();
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }

        m_clHOClient.getClient().doTorchance(heimTeam, schuetze, vorbereiter1, verteidiger1,
                                             verteidiger2, torwart, torart, torchance, variante,
                                             trainerVariante, trainerVariante2, aktionen,
                                             spielminute);
    }

    /**
     * stellt eine Verletzung dar
     */
    public final void getVerletzung() {
        byte textKey = 0;
        String spielerName = "";
        int spielminute = 0;
        int variante = 0;
        int trainerVariante = 0;
        boolean heim = false;

        try {
            textKey = m_clInput.readByte();
            spielerName = m_clInput.readUTF();
            spielminute = m_clInput.readInt();
            variante = m_clInput.readInt();
            trainerVariante = m_clInput.readInt();
            heim = m_clInput.readBoolean();
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }

        m_clHOClient.getClient().doVerletzung(textKey, spielerName, spielminute, variante,
                                              trainerVariante, heim);
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void run() {
        byte data = 0;

        //Lauschen...
        while (m_bRun) {
            try {
                if (m_clInput.available() > 0) {
                    //Daten Falg auswerten
                    data = m_clInput.readByte();

                    switch (data) {
                        case Chat.NET_CHAT_MSG:
                            reciveChatMsg();
                            break;

                        case Chat.NET_TORCHANCE_MSG:
                            getTorchance();
                            break;

                        case Chat.NET_INFO_MSG:
                            getInfoText();
                            break;

                        case Chat.NET_KARTE_MSG:
                            getKarte();
                            break;

                        case Chat.NET_VERLETZUNG_MSG:
                            getVerletzung();
                            break;

                        case Chat.NET_AUSWECHSLUNG_MSG:
                            getAuswechslung();
                            break;

                        case Chat.NET_FANGESANG_MSG:
                            getFangesang();
                            break;

                        case Chat.NET_SPIELMINUTE_MSG:
                            getMinuteRum();
                            break;

                        case Chat.NET_SPIELENDE_MSG:
                            getSpielende();
                            break;

                        case Chat.NET_SPIELBEGINN_MSG:
                            getSpielbeginn();
                            break;

                        /*                        case Chat.NET_BEREIT_MSG:   //Status Request vom Server!
                           checkBereit();
                           break;
                         */
                    }
                }

                Thread.sleep(100);
            } catch (Exception e) {
                HOLogger.instance().log(getClass(),e);
            }
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    @Override
	protected final void finalize() {
        //Clean up
        try {
            //in.close ();
            m_clInput.close();
        } catch (IOException e) {
            HOLogger.instance().log(getClass(),e);
        }
    }

    /*
       liest die Chat Msg ein
     */
    protected final synchronized void reciveChatMsg() {
        String msg = "";
        String trainer = "";
        boolean heim = true;

        try {
            trainer = m_clInput.readUTF();
            msg = m_clInput.readUTF();
            heim = m_clInput.readBoolean();

            //Chat Msg an Client weitergeben
            m_clHOClient.recieveMsg(trainer, msg, heim);

            //HOLogger.instance().log(getClass(),"Chat empfangen: " + trainer + " " + msg );
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }
    }
}
