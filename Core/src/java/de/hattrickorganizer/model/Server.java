// %4047620839:de.hattrickorganizer.model%
/*
 * Server.java
 *
 * Created on 7. Mai 2003, 07:56
 */
package de.hattrickorganizer.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import plugins.ISpielerPosition;

import de.hattrickorganizer.logik.SpielLogik;
import de.hattrickorganizer.net.rmiHOFriendly.HOServerImp;
import de.hattrickorganizer.tools.HOLogger;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class Server implements java.lang.Runnable {
    //~ Instance fields ----------------------------------------------------------------------------

    /** Kommunikations Socket Interface */
    protected HOServerImp m_clHOServer;

    /** Server Screen */
    protected MatchScreen m_clScreen1;

    /** Der Spielbericht */
    protected Spielbericht m_clSpielbericht;

    /** TODO Missing Parameter Documentation */
    protected int m_iAnzAktionsVarianten = 5;

    /** TODO Missing Parameter Documentation */
    protected int m_iAnzAllgemeineVarianten = 3;

    //Minimum von Heim und GastTeam Property File!
    //protected int           m_iAnzTorVarianten          =   1;

    /** TODO Missing Parameter Documentation */
    protected int m_iAnzSelteneTorVarianten = 2;
    private boolean noError;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of Server
     *
     * @param ip TODO Missing Constructuor Parameter Documentation
     * @param port TODO Missing Constructuor Parameter Documentation
     */
    public Server(String ip, int port) {
        m_clSpielbericht = new Spielbericht();
        noError = true;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean isAbbruch() {
        return ((m_clScreen1.isAbbruch()) && (m_clHOServer.isClientAbbruch()));
    }

    /**
     * Setter for property m_iAnzAktionsVarianten.
     *
     * @param m_iAnzAktionsVarianten New value of property m_iAnzAktionsVarianten.
     */
    public final void setAnzAktionsVarianten(int m_iAnzAktionsVarianten) {
        this.m_iAnzAktionsVarianten = m_iAnzAktionsVarianten;
    }

    /**
     * Getter for property m_iAnzAktionsVarianten.
     *
     * @return Value of property m_iAnzAktionsVarianten.
     */
    public final int getAnzAktionsVarianten() {
        return m_iAnzAktionsVarianten;
    }

    /**
     * Setter for property m_iAnzAllgemeineVarianten.
     *
     * @param m_iAnzAllgemeineVarianten New value of property m_iAnzAllgemeineVarianten.
     */
    public final void setAnzAllgemeineVarianten(int m_iAnzAllgemeineVarianten) {
        this.m_iAnzAllgemeineVarianten = m_iAnzAllgemeineVarianten;
    }

    /**
     * Getter for property m_iAnzAllgemeineVarianten.
     *
     * @return Value of property m_iAnzAllgemeineVarianten.
     */
    public final int getAnzAllgemeineVarianten() {
        return m_iAnzAllgemeineVarianten;
    }

    /**
     * Setter for property m_iAnzSelteneTorVarianten.
     *
     * @param m_iAnzSelteneTorVarianten New value of property m_iAnzSelteneTorVarianten.
     */
    public final void setAnzSelteneTorVarianten(int m_iAnzSelteneTorVarianten) {
        this.m_iAnzSelteneTorVarianten = m_iAnzSelteneTorVarianten;
    }

    /**
     * Getter for property m_iAnzSelteneTorVarianten.
     *
     * @return Value of property m_iAnzSelteneTorVarianten.
     */
    public final int getAnzSelteneTorVarianten() {
        return m_iAnzSelteneTorVarianten;
    }

    /**
     * erstellt ServObj für GastTeam
     *
     * @param sTeam TODO Missing Constructuor Parameter Documentation
     */
    public final void setGastTeam(ServerTeam sTeam) {
        m_clSpielbericht.Gast(sTeam);
    }

    /**
     * Setter for property m_clHOServer.
     *
     * @param m_clHOServer New value of property m_clHOServer.
     */
    public final void setHOServer(de.hattrickorganizer.net.rmiHOFriendly.HOServerImp m_clHOServer) {
        this.m_clHOServer = m_clHOServer;
    }

    /**
     * Getter for property m_clHOServer.
     *
     * @return Value of property m_clHOServer.
     */
    public final de.hattrickorganizer.net.rmiHOFriendly.HOServerImp getHOServer() {
        return m_clHOServer;
    }

    /**
     * erstellt ServObj für HeimTeam
     *
     * @param team TODO Missing Constructuor Parameter Documentation
     */
    public final void setHeimTeam(ServerTeam team) {
        m_clSpielbericht.Heim(team);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean isPause() {
        return ((m_clScreen1.isPause()) || (m_clHOServer.isClientPause()));
    }

    /**
     * Setter for property m_clScreen1.
     *
     * @param m_clScreen1 New value of property m_clScreen1.
     */
    public final void setScreen1(de.hattrickorganizer.model.MatchScreen m_clScreen1) {
        this.m_clScreen1 = m_clScreen1;
    }

    /**
     * Getter for property m_clScreen1.
     *
     * @return Value of property m_clScreen1.
     */
    public final de.hattrickorganizer.model.MatchScreen getScreen1() {
        return m_clScreen1;
    }

    /**
     * gibt an ob der Screen bereit zum zeichnen ist
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean isScreenBereit() {
        return ((m_clScreen1.isScreenBereit()) && (m_clHOServer.isClientBereit()));
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Spielbericht getSpielbericht() {
        return m_clSpielbericht;
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
        if ((einwechselSpieler != null) && (!einwechselSpieler.trim().equals(""))) {
            m_clHOServer.sendAuswechslung(textKey, teamName, auswechselSpieler, einwechselSpieler,
                                          variante, spielminute, heim);
            m_clScreen1.doAuswechslung(textKey, teamName, auswechselSpieler, einwechselSpieler,
                                       variante, spielminute, heim);
            sync();
        }
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
        m_clHOServer.sendFangesang(key, art, variante, fanTeam, fanManager, gegnerTeam,
                                   gegnerManager, heim);
        m_clScreen1.doFangesang(key, art, variante, fanTeam, fanManager, gegnerTeam, gegnerManager,
                                heim);
        sync();
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
        m_clHOServer.sendKarte(textKey, spielerName, spielminute, variante, trainerVariante, heim);
        m_clScreen1.doKarte(textKey, spielerName, spielminute, variante, trainerVariante, heim);
        sync();
    }

    /**
     * gibt an das ne Minute rum ist :)
     *
     * @param spielminute TODO Missing Constructuor Parameter Documentation
     */
    public final void doMinuteRum(int spielminute) {
        m_clHOServer.sendMinuteRum(spielminute);
        m_clScreen1.doMinuteRum(spielminute);
        sync();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param sb TODO Missing Method Parameter Documentation
     */
    public final void doSpielbeginn(Spielbericht sb) {
        m_clHOServer.sendSpielbeginn(sb);
        m_clScreen1.doSpielbeginn(sb);

        //2 sec heia machen, damit Spieler dargestellt werden können ohne
        //das ne Meldung mitten rein haut...
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }
    }

    /**
     * Spielende
     *
     * @param sb TODO Missing Constructuor Parameter Documentation
     */
    public final void doSpielende(Spielbericht sb) {
        m_clHOServer.sendSpielende(sb);
        m_clScreen1.doSpielende(sb);
        sync();
    }

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
    public final void doTorchance(boolean heimTeam, String schuetze, String vorbereiter1,
                                  String verteidiger1, String verteidiger2, String torwart,
                                  byte torart, byte torchance, int variante, int trainerVariante,
                                  int trainerVariante2, int[] aktionen, int spielminute) {
        m_clHOServer.sendTorchance(heimTeam, schuetze, vorbereiter1, verteidiger1, verteidiger2,
                                   torwart, torart, torchance, variante, trainerVariante,
                                   trainerVariante2, aktionen, spielminute);
        m_clScreen1.doTorchance(heimTeam, schuetze, vorbereiter1, verteidiger1, verteidiger2,
                                torwart, torart, torchance, variante, trainerVariante,
                                trainerVariante2, aktionen, spielminute);
        sync();
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
        m_clHOServer.sendVerletzung(textKey, spielerName, spielminute, variante, trainerVariante,
                                    heim);
        m_clScreen1.doVerletzung(textKey, spielerName, spielminute, variante, trainerVariante, heim);
        sync();
    }

    /**
     * Gibt einen Infotext wieder z.B: gleich geht's los, Halbzeit, ElferSchießen, Spielende...
     *
     * @param textKey == Info, Vortext usw.
     * @param variante TODO Missing Constructuor Parameter Documentation
     */
    public final void drawInfoText(byte textKey, int variante) {
        m_clHOServer.sendInfoText(textKey, variante);
        m_clScreen1.drawInfoText(textKey, variante);
        sync();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean noError() {
        return noError;
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void run() {
        final String[] args = de.hattrickorganizer.model.HOVerwaltung.instance().getArgs();

        //prüfen ob gesavt werden soll
        if ((args != null)
            && (args.length > 0)
            && (de.hattrickorganizer.tools.MyHelper.decryptString("k[gmn").equals(args[0]))) {
            write2File();
        }

        startMatch();
    }

    ////////////////////////////////Netz-Funktionen/////////////////////////
    ///*senden die Daten an Client und Server SCreen weiter...
    //TODO Funktionalit#t aus Spielogik übernehmen und Daten an eigenen "Server-Screen" direkt
    // und an Client via NEtz senden, warten bis recieved wurde dann return der Func...
    //D.H. Server muss Var haben ob Screen bereit , diese nach senden auf false
    //Client sendet wenn bereit, empfängt Server dann diese Daten
    // Var wieder auf True setzen...    
    //////////////////////////////////////////////////////////////////////////    

    /**
     * verschickt Msg
     *
     * @param trainer TODO Missing Constructuor Parameter Documentation
     * @param msg Die Msg
     * @param heim gibt an ob Heim oder Gast der Absender ist
     */
    public final void sendChatMsg(String trainer, String msg, boolean heim) {
        //nur an Server Screen weiterleiten, wird nur für diesen Fall aufgerufen..

        /*//MSG an Client
           if ( heim )
           {
        
               m_clHOServer.sendChatMsg ( trainer, msg, heim );
           }
           else    //MSG an Server
           {*/
        m_clScreen1.recieveMsg(trainer, msg, heim);

        //}
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void startMatch() {
        final SpielLogik spLogik = new SpielLogik();

        if ((m_clSpielbericht.Heim().getAnzAufgestellteSpieler() < 8)
            || (m_clSpielbericht.Gast().getAnzAufgestellteSpieler() < 8)) {
            return;
        }

        spLogik.doEinfacheSpielberechnung(m_clSpielbericht, this);
    }

    /**
     * TODO Missing Method Documentation
     */
    protected final void sync() {
        int count = 0;

        while (!isScreenBereit()) {
            try {
                Thread.sleep(375);
            } catch (Exception e) {
            }

            count++;

            //Lange genug gewwartet von Hand bereit setzen
            if (count > 6) {
                m_clHOServer.setBereit(true);
            }
        }
    }

    /**
     * erstellt den Inahlt als Stringarray
     *
     * @return TODO Missing Return Method Documentation
     */
    protected final String[] toMyString() {
        final ServerTeam tmp = m_clSpielbericht.Gast();

        //TeamName usw. + Auf Feld + Reserve        
        final SpielerPosition[] sp = tmp.getPositionen();
        String[] txt = null;
        ServerSpieler pl = null;

        if (sp != null) {
            txt = new String[6 + sp.length];
        } else {
            txt = new String[6];

            //Name
            txt[0] = "Team : " + tmp.getTeamName();

            //TW
            txt[1] = "Torwart : " + tmp.getTWTeamStk();

            //AB STK
            txt[2] = "Abwehr : " + tmp.getAWTeamStk();

            //MF STK
            txt[3] = "Mittelfeld : " + tmp.getMFTeamStk();

            //ST STK
            txt[4] = "Sturm : " + tmp.getSTTeamStk();

            //Gesamt    
            txt[5] = "Gesamt : " + tmp.getGesamtStk();
        }

        for (int i = 0; (sp != null) && (i < sp.length); i++) {
            if (sp[i] == null) {
                txt[i] = "";
                continue;
            }

            pl = tmp.getSpielerById(sp[i].getSpielerId());

            if (pl == null) {
                continue;
            }

            txt[i] = pl.getName() + " spielt "
                     + SpielerPosition.getNameForPosition(sp[i].getPosition()) + " mit STK : "
                     + pl.getStk(sp[i].getPosition()) + ", TW: "
                     + pl.getStk(ISpielerPosition.KEEPER) + ", IV: "
                     + pl.getStk(ISpielerPosition.CENTRAL_DEFENDER) + ", AV: "
                     + pl.getStk(ISpielerPosition.BACK) + ", FL: "
                     + pl.getStk(ISpielerPosition.WINGER) + ", IM: "
                     + pl.getStk(ISpielerPosition.MIDFIELDER) + ", ST: "
                     + pl.getStk(ISpielerPosition.FORWARD);
        }

        if (sp != null) {
            //Name
            txt[sp.length + 0] = "Team : " + tmp.getTeamName();

            //TW
            txt[sp.length + 1] = "Torwart : " + tmp.getTWTeamStk();

            //AB STK
            txt[sp.length + 2] = "Abwehr : " + tmp.getAWTeamStk();

            //MF STK
            txt[sp.length + 3] = "Mittelfeld : " + tmp.getMFTeamStk();

            //ST STK
            txt[sp.length + 4] = "Sturm : " + tmp.getSTTeamStk();

            //Gesamt    
            txt[sp.length + 5] = "Gesamt : " + tmp.getGesamtStk();
        }

        return txt;
    }

    /**
     * schreibt die datei
     */
    protected final void write2File() {
        BufferedWriter out = null;
        File datei = null;
        String[] inhalt = null;

        // + m_clSpielbericht.Gast ().getTeamName () + "_" + new java.sql.Timestamp( System.currentTimeMillis () ).toString () + ".txt";
        final String dateiname = "game.txt";

        inhalt = toMyString();

        try {
            datei = new File(dateiname);

            if (datei.exists()) {
                datei.delete();
            }

            datei.createNewFile();

            out = new BufferedWriter(new FileWriter(datei));

            for (int i = 0; i < inhalt.length; i++) {
                if (inhalt[i] != null) {
                    out.write(inhalt[i]);
                }

                out.newLine();
            }

            out.close();
        } catch (Exception except) {
            HOLogger.instance().log(getClass(),except);
        }
    }
}
