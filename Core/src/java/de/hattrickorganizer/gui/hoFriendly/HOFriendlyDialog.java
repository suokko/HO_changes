// %1458893480:de.hattrickorganizer.gui.hoFriendly%
package de.hattrickorganizer.gui.hoFriendly;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;

import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.MatchScreen;
import de.hattrickorganizer.model.Spielbericht;
import de.hattrickorganizer.model.SpielerPosition;


/**
 * Ein Dialog für die Freundschaftsspiele
 */
public class HOFriendlyDialog extends JFrame implements de.hattrickorganizer.model.MatchScreen,
                                                        java.awt.event.WindowListener
{
	private static final long serialVersionUID = 5146409894948790465L;
	
    //~ Instance fields ----------------------------------------------------------------------------
	/** TODO Missing Parameter Documentation */
    public boolean m_bAbbruch;

    /** TODO Missing Parameter Documentation */
    public boolean m_bPause;

    /** TODO Missing Parameter Documentation */
    public boolean m_bScreenBereit = true;
    private de.hattrickorganizer.net.rmiHOFriendly.Chat m_clChat;
    private SpielstandPanel m_jpSpielstandPanel;
    private String ALLGEMEIN = "<b>";
    private String BREAK = "<br>";
    private String ENDEALLGEMEIN = "</b>";
    private String ENDEFANS = "</i>";
    private String ENDETEAM = "</b>";
    private String FANS = "<i>";
    private String GASTTEAM = "<b color=#990000>";
    private String HEIMTEAM = "<b color=#000099>";
    private String TOR = "<b color=#333333>"
                         + de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("TOR") + "</b>";
    private TextModusPanel m_jpTextModusPanel;
    private ZusatzInfoPanel m_jpZusatzInfoPanel;
    private boolean m_bServer = true;
    private int SLEEP_MEDIUM = 3000;
    private int SLEEP_SHORT = 1500;
    private int SLEEP_VERYSHORT = 500;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new HOFriendlyDialog object.
     *
     * @param owner TODO Missing Constructuor Parameter Documentation
     * @param server TODO Missing Constructuor Parameter Documentation
     */
    public HOFriendlyDialog(JFrame owner, boolean server) {
        super("Waiting... - "
              + de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("HoFriendly"));

        //setDefaultCloseOperation ( DO_NOTHING_ON_CLOSE );
        //setDefaultCloseOperation ( this.DISPOSE_ON_CLOSE );
        m_bServer = server;
        initComponents();

        this.setIconImage(de.hattrickorganizer.tools.Helper.loadImage("gui/bilder/Logo-16px.png"));

        this.addWindowListener(this);

        //setSize( 1024, 750 );
        setSize(new java.awt.Dimension(owner.getToolkit().getScreenSize().width,
                                       owner.getToolkit().getScreenSize().height - 32));
        setLocation(0, 0);

        setVisible(true);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean isAbbruch() {
        return m_bAbbruch;
    }

    /**
     * Setter for property m_clChat.
     *
     * @param m_clChat New value of property m_clChat.
     */
    public final void setChat(de.hattrickorganizer.net.rmiHOFriendly.Chat m_clChat) {
        this.m_clChat = m_clChat;
    }

    /**
     * Getter for property m_clChat.
     *
     * @return Value of property m_clChat.
     */
    public final de.hattrickorganizer.net.rmiHOFriendly.Chat getChat() {
        return m_clChat;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param pause TODO Missing Method Parameter Documentation
     */
    public final void setPause(boolean pause) {
        m_bPause = pause;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean isPause() {
        return m_bPause;
    }

    /**
     * gibt an ob die Chance zuende gezeichnet wurde
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean isScreenBereit() {
        return m_bScreenBereit;
    }

    /**
     * Aktualisiert den Spielstand
     *
     * @param schuetze TODO Missing Constructuor Parameter Documentation
     * @param spielminute TODO Missing Constructuor Parameter Documentation
     * @param heim TODO Missing Constructuor Parameter Documentation
     */
    public final void setSpielstand(String schuetze, int spielminute, boolean heim) {
        if (heim) {
            m_jpSpielstandPanel.addHeimtor(spielminute + ".  " + schuetze);
            m_jpSpielstandPanel.setHeimtorAnzahl(m_jpSpielstandPanel.getHeimtorAnzahl() + 1);
        } else {
            m_jpSpielstandPanel.addGasttor(spielminute + ".  " + schuetze);
            m_jpSpielstandPanel.setGasttorAnzahl(m_jpSpielstandPanel.getGasttorAnzahl() + 1);
        }
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
        try {
            m_bScreenBereit = false;
            m_clChat.sendBereit(m_bScreenBereit);

            String msg = "";
            String[] tokenmsg = null;
            String aktuKey = "";
            String auswechselspielername = "";

            if (heim) {
                auswechselspielername = HEIMTEAM + auswechselSpieler + ENDETEAM;
            } else {
                auswechselspielername = GASTTEAM + auswechselSpieler + ENDETEAM;
            }

            String einwechselspielername = "";

            if (heim) {
                einwechselspielername = HEIMTEAM + einwechselSpieler + ENDETEAM;
            } else {
                einwechselspielername = GASTTEAM + einwechselSpieler + ENDETEAM;
            }

            final String[] varConst = {
                                          MatchScreen.VAR_AUSWECHSELSPIELER,
                                          MatchScreen.VAR_EINWECHSELSPIELER,
                                          MatchScreen.VAR_SPIELMINUTE, MatchScreen.VAR_TEAMNAME
                                      };
            final String[] varValue = {
                                          auswechselspielername, einwechselspielername,
                                          "" + spielminute, teamName
                                      };

            drawSpielMinute(spielminute);

            //Erste AktionsKey bestimmen        
            aktuKey = "" + textKey + "_" + variante;

            try {
                msg = de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString(aktuKey);

                //Variablen mit Spielernamen füllen
                tokenmsg = replaceAllVars(varConst, varValue, msg);

                //Message anzeigen
                drawString(tokenmsg);
                drawString("");
            } catch (Exception e) {
                drawString("Message not found: " + aktuKey);
            }

            m_bScreenBereit = true;
            m_clChat.sendBereit(m_bScreenBereit);
        } catch (Exception e) {
            m_bScreenBereit = true;
            m_clChat.sendBereit(m_bScreenBereit);
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
        String msg = "";
        String[] tokenmsg = null;
        String aktuKey = "";
        final String[] varConst = {
                                      MatchScreen.VAR_FANTEAM, MatchScreen.VAR_FANMANAGER,
                                      MatchScreen.VAR_GEGNERTEAM, MatchScreen.VAR_GEGNERMANAGER
                                  };
        final String[] varValue = {fanTeam, fanManager, gegnerTeam, gegnerManager};

        m_bScreenBereit = false;
        m_clChat.sendBereit(m_bScreenBereit);

        //Erste AktionsKey bestimmen        
        aktuKey = "" + key + "_" + art + "_" + variante;

        try {
            msg = de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString(aktuKey);

            //Variablen mit Spielernamen füllen
            tokenmsg = replaceAllVars(varConst, varValue, msg);

            //Message anzeigen
            drawString(FANS + tokenmsg[0] + ENDEFANS + BREAK);
        } catch (Exception e) {
            drawString("Message not found: " + aktuKey);
        }

        m_bScreenBereit = true;
        m_clChat.sendBereit(m_bScreenBereit);
    }

    /**
     * übermittelt Inforamtionen zu einer Karte
     *
     * @param textKey TODO Missing Constructuor Parameter Documentation
     * @param spieler TODO Missing Constructuor Parameter Documentation
     * @param spielminute TODO Missing Constructuor Parameter Documentation
     * @param variante TODO Missing Constructuor Parameter Documentation
     * @param trainerVariante TODO Missing Constructuor Parameter Documentation
     * @param heim TODO Missing Constructuor Parameter Documentation
     */
    public final void doKarte(byte textKey, String spieler, int spielminute, int variante,
                              int trainerVariante, boolean heim) {
        m_bScreenBereit = false;
        m_clChat.sendBereit(m_bScreenBereit);

        String msg = "";
        String[] tokenmsg = null;
        String aktuKey = "";
        String spielername = "";

        if (heim) {
            spielername = HEIMTEAM + spieler + ENDETEAM;
        } else {
            spielername = GASTTEAM + spieler + ENDETEAM;
        }

        final String[] varConst = {MatchScreen.VAR_SPIELERNAME, MatchScreen.VAR_SPIELMINUTE};
        final String[] varValue = {spielername, "" + spielminute};

        drawSpielMinute(spielminute);

        //Erste AktionsKey bestimmen        
        aktuKey = "" + textKey + "_" + variante;

        try {
            msg = de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString(aktuKey);

            //Variablen mit Spielernamen füllen
            tokenmsg = replaceAllVars(varConst, varValue, msg);

            //Message anzeigen
            drawString(tokenmsg);
            drawString("");
        } catch (Exception e) {
            drawString("Message not found: " + aktuKey);
        }

        if (heim) {
            m_jpZusatzInfoPanel.getHeimTrainer().showSequence(TrainerLibrary.getKarte(heim,
                                                                                      trainerVariante));
        } else {
            m_jpZusatzInfoPanel.getGastTrainer().showSequence(TrainerLibrary.getKarte(heim,
                                                                                      trainerVariante));
        }

        m_bScreenBereit = true;
        m_clChat.sendBereit(m_bScreenBereit);
    }

    /**
     * gibt an das ne Minute rum ist :)
     *
     * @param spielminute TODO Missing Constructuor Parameter Documentation
     */
    public final void doMinuteRum(int spielminute) {
        m_jpSpielstandPanel.setSpielminute(spielminute);
        m_clChat.sendBereit(true);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param sb TODO Missing Method Parameter Documentation
     */
    public final void doSpielbeginn(Spielbericht sb) {
        setExtendedState(NORMAL);
        toFront();
        setTitle(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("HoFriendly"));

        m_jpSpielstandPanel.setHeimmannschaft(sb.Heim().getTeamName());
        m_jpSpielstandPanel.setGastmannschaft(sb.Gast().getTeamName());

        m_bScreenBereit = false;
        m_clChat.sendBereit(m_bScreenBereit);

        //Tabelle!
        final StringBuffer buffer = new StringBuffer();

        //Kopfzeile
        buffer.append("<Table border=0 cellpadding=2 cellspacing=0><tr><td></td><td>");
        buffer.append(HEIMTEAM);
        buffer.append(sb.Heim().getTeamName());
        buffer.append(ENDETEAM);
        buffer.append("</td><td width=50></td><td></td><td>");
        buffer.append(GASTTEAM);
        buffer.append(sb.Gast().getTeamName());
        buffer.append(ENDETEAM);
        buffer.append("</td></tr>");

        final SpielerPosition[] heim = sb.Heim().getPositionen();
        final SpielerPosition[] gast = sb.Gast().getPositionen();

        //Beide Positionen gleichlang
        for (int i = 0; i < heim.length; i++) {
            if (sb.Heim().getSpielerById(heim[i].getSpielerId()) != null) {
                buffer.append("<tr><td><b>");

                if (heim[i].getId() >= de.hattrickorganizer.model.SpielerPosition.beginnReservere) {
                    buffer.append(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Reserve"));
                    buffer.append(" ");
                }

                buffer.append(de.hattrickorganizer.model.SpielerPosition.getNameForPosition(heim[i]
                                                                                            .getPosition()));
                buffer.append("</b></td><td>");
                buffer.append(sb.Heim().getSpielerById(heim[i].getSpielerId()).getName());
                buffer.append("</td><td></td>");
            } else {
                buffer.append("<tr><td></td><td></td><td></td>");
            }

            if (sb.Gast().getSpielerById(gast[i].getSpielerId()) != null) {
                buffer.append("<td><b>");

                if (gast[i].getId() >= de.hattrickorganizer.model.SpielerPosition.beginnReservere) {
                    buffer.append(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Reserve"));
                    buffer.append(" ");
                }

                buffer.append(de.hattrickorganizer.model.SpielerPosition.getNameForPosition(gast[i]
                                                                                            .getPosition()));
                buffer.append("</b></td><td>");
                buffer.append(sb.Gast().getSpielerById(gast[i].getSpielerId()).getName());
                buffer.append("</td></tr>");
            } else {
                buffer.append("<td></td><td></td><td></td></tr>");
            }
        }

        buffer.append("</table><br>");

        drawString(buffer.toString());

        m_bScreenBereit = true;
        m_clChat.sendBereit(m_bScreenBereit);
    }

    /**
     * Spielende
     *
     * @param sb TODO Missing Constructuor Parameter Documentation
     */
    public final void doSpielende(Spielbericht sb) {
        //setSpielstand( sb );
        //Message anzeigen
        //Tabelle!
        final StringBuffer buffer = new StringBuffer();
        //Kopfzeile
        buffer.append("<Table border=0><tr><th></th><th>");
        buffer.append(HEIMTEAM);
        buffer.append(sb.Heim().getTeamName());
        buffer.append(ENDETEAM);
        buffer.append("</th><th>");
        buffer.append(GASTTEAM);
        buffer.append(sb.Gast().getTeamName());
        buffer.append(ENDETEAM);
        buffer.append("</th></tr>");

        //Tore
        buffer.append("<tr><td><b>");
        buffer.append(HOVerwaltung.instance().getLanguageString("Tore"));
        buffer.append("</b></td><td align=\"right\">");
        buffer.append(HEIMTEAM);
        buffer.append(sb.ToreHeim());
        buffer.append(ENDETEAM);
        buffer.append("</td><td align=\"right\">");
        buffer.append(GASTTEAM);
        buffer.append(sb.ToreGast());
        buffer.append(ENDETEAM);
        buffer.append("</td></tr>");

        //Torchancen
        buffer.append("<tr><td>");
        buffer.append(HOVerwaltung.instance().getLanguageString("Chancen"));
        buffer.append("</td><td align=\"right\">");
        buffer.append(HEIMTEAM);
        buffer.append(sb.TorchancenHeim());
        buffer.append(ENDETEAM);
        buffer.append("</td><td align=\"right\">");
        buffer.append(GASTTEAM);
        buffer.append(sb.TorchancenGast());
        buffer.append(ENDETEAM);
        buffer.append("</td></tr>");

        //Abseitsfalle
        buffer.append("<tr><td>");
        buffer.append(HOVerwaltung.instance().getLanguageString("Abseitsfalle"));
        buffer.append("</td><td align=\"right\">");
        buffer.append(HEIMTEAM);
        buffer.append(sb.getAbseitsfalleHeim());
        buffer.append(" %");
        buffer.append(ENDETEAM);
        buffer.append("</td><td align=\"right\">");
        buffer.append(GASTTEAM);
        buffer.append(sb.getAbseitsfalleGast());
        buffer.append(" %");
        buffer.append(ENDETEAM);
        buffer.append("</td></tr>");

        //Zweikampfwerte
        buffer.append("<tr><td>");
        buffer.append(HOVerwaltung.instance().getLanguageString("Zweikampfwerte"));
        buffer.append("</td><td align=\"right\">");
        buffer.append(HEIMTEAM);
        buffer.append(sb.getZweikampfBilanzHeim());
        buffer.append(" %");
        buffer.append(ENDETEAM);
        buffer.append("</td><td align=\"right\">");
        buffer.append(GASTTEAM);
        buffer.append(sb.getZweikampfBilanzGast());
        buffer.append(" %");
        buffer.append(ENDETEAM);
        buffer.append("</td></tr>");

        //Ecken
        buffer.append("<tr><td>");
        buffer.append(HOVerwaltung.instance().getLanguageString("Ecken"));
        buffer.append("</td><td align=\"right\">");
        buffer.append(HEIMTEAM);
        buffer.append(sb.getAnzEckenHeim());
        buffer.append(ENDETEAM);
        buffer.append("</td><td align=\"right\">");
        buffer.append(GASTTEAM);
        buffer.append(sb.getAnzEckenGast());
        buffer.append(ENDETEAM);
        buffer.append("</td></tr>");

        //GelbeKarten
        buffer.append("<tr><td>");
        buffer.append(HOVerwaltung.instance().getLanguageString("GelbeKarten"));
        buffer.append("</td><td align=\"right\">");
        buffer.append(HEIMTEAM);
        buffer.append(sb.getAnzGelbeKartenHeim());
        buffer.append(ENDETEAM);
        buffer.append("</td><td align=\"right\">");
        buffer.append(GASTTEAM);
        buffer.append(sb.getAnzGelbeKartenGast());
        buffer.append(ENDETEAM);
        buffer.append("</td></tr>");

        //RoteKarten
        buffer.append("<tr><td>");
        buffer.append(HOVerwaltung.instance().getLanguageString("RoteKarten"));
        buffer.append("</td><td align=\"right\">");
        buffer.append(HEIMTEAM);
        buffer.append(sb.getAnzRoteKartenHeim());
        buffer.append(ENDETEAM);
        buffer.append("</td><td align=\"right\">");
        buffer.append(GASTTEAM);
        buffer.append(sb.getAnzRoteKartenGast());
        buffer.append(ENDETEAM);
        buffer.append("</td></tr>");

        //Auswechslungen
        buffer.append("<tr><td>");
        buffer.append(HOVerwaltung.instance().getLanguageString("Auswechslungen"));
        buffer.append("</td><td align=\"right\">");
        buffer.append(HEIMTEAM);
        buffer.append(sb.getAnzWechselHeim());
        buffer.append(ENDETEAM);
        buffer.append("</td><td align=\"right\">");
        buffer.append(GASTTEAM);
        buffer.append(sb.getAnzWechselGast());
        buffer.append(ENDETEAM);
        buffer.append("</td></tr>");

        //Ende
        buffer.append("</Table>");

        drawString(buffer.toString());

        //Trainer
        m_jpZusatzInfoPanel.getHeimTrainer().showSequence(TrainerLibrary.getSpielende(true,
                                                                                      sb.ToreHeim() > sb
                                                                                                      .ToreGast(),
                                                                                      100));
        m_jpZusatzInfoPanel.getGastTrainer().showSequence(TrainerLibrary.getSpielende(false,
                                                                                      sb.ToreHeim() > sb
                                                                                                      .ToreGast(),
                                                                                      100));

        //werte sichern
        /*        String timeoutconnect   =    System.getProperty("sun.net.client.defaultConnectTimeout" );
           String timeoutread   =   System.getProperty ( "sun.net.client.defaultReadTimeout" );
           //Timeout anpassen...
           System.setProperty("sun.net.client.defaultConnectTimeout", "3000" );
           System.setProperty("sun.net.client.defaultReadTimeout", "6000" );
         */
        de.hattrickorganizer.net.MyConnector.instance().sendSpielbericht(sb, m_bServer);

        /*
           System.setProperty("sun.net.client.defaultConnectTimeout", timeoutconnect);
           System.setProperty("sun.net.client.defaultReadTimeout", timeoutread);
         **/
    }

    /**
     * zeigt eine Torchance an
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
        String msg = "";
        String[] tokenmsg = null;
        m_bScreenBereit = false;

        String keyBeginn = "";
        String aktuKey = "";
        String schuetzeName = "";
        String vorbereiter1Name = "";
        String verteidiger2Name = "";
        String verteidiger1Name = "";
        String torwartName = "";

        //wait senden
        m_clChat.sendBereit(m_bScreenBereit);

        //Trainer gespannt!
        m_jpZusatzInfoPanel.getHeimTrainer().showSequence(TrainerLibrary.getTorschussAnfang(true,
                                                                                            100));
        m_jpZusatzInfoPanel.getGastTrainer().showSequence(TrainerLibrary.getTorschussAnfang(false,
                                                                                            100));

        if (schuetze != null) {
            if (heimTeam) {
                schuetzeName = HEIMTEAM + schuetze + ENDETEAM;
            } else {
                schuetzeName = GASTTEAM + schuetze + ENDETEAM;
            }
        }

        if (vorbereiter1 != null) {
            if (heimTeam) {
                vorbereiter1Name = HEIMTEAM + vorbereiter1 + ENDETEAM;
            } else {
                vorbereiter1Name = GASTTEAM + vorbereiter1 + ENDETEAM;
            }
        }

        if (verteidiger2 != null) {
            if (heimTeam) {
                verteidiger2Name = GASTTEAM + verteidiger2 + ENDETEAM;
            } else {
                verteidiger2Name = HEIMTEAM + verteidiger2 + ENDETEAM;
            }
        }

        if (verteidiger1 != null) {
            if (heimTeam) {
                verteidiger1Name = GASTTEAM + verteidiger1 + ENDETEAM;
            } else {
                verteidiger1Name = HEIMTEAM + verteidiger1 + ENDETEAM;
            }
        }

        if (torwart != null) {
            if (heimTeam) {
                torwartName = GASTTEAM + torwart + ENDETEAM;
            } else {
                torwartName = HEIMTEAM + torwart + ENDETEAM;
            }
        }

        drawSpielMinute(spielminute);

        //Standard beginn ermitteln
        keyBeginn = "" + torart + "_" + variante + "_";

        //Erste AktionsKey bestimmen
        aktuKey = keyBeginn + MatchScreen.KEY_START + "_" + aktionen[0];

        try {
            msg = de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString(aktuKey);

            //Variablen mit Spielernamen füllen
            tokenmsg = replaceVars(schuetzeName, vorbereiter1Name, verteidiger1Name,
                                   verteidiger2Name, torwartName, msg);

            //Message anzeigen
            drawString(tokenmsg);
        } catch (Exception e) {
            drawString("Message not found: " + aktuKey);
        }

        try {
            Thread.sleep(SLEEP_MEDIUM);
        } catch (Exception e) {
        }

        //Weitere aktionen anzeigen
        for (int i = 1; i < (aktionen.length - 1); i++) {
            aktuKey = keyBeginn + MatchScreen.KEY_AKTION + "_" + aktionen[i];

            try {
                msg = de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString(aktuKey);

                //Variablen mit Spielernamen füllen
                tokenmsg = replaceVars(schuetzeName, vorbereiter1Name, verteidiger1Name,
                                       verteidiger2Name, torwartName, msg);

                //Message anzeigen
                drawString(tokenmsg);
            } catch (Exception e) {
                drawString("Message not found: " + aktuKey);
            }

            try {
                Thread.sleep(SLEEP_MEDIUM);
            } catch (Exception e) {
            }
        }

        //Abschluss anzeigen
        if (torchance < 4) {
            aktuKey = keyBeginn + MatchScreen.KEY_ABSCHLUSS_VERGEBEN + "_"
                      + aktionen[aktionen.length - 1];
        } else {
            aktuKey = keyBeginn + MatchScreen.KEY_ABSCHLUSS_TOR + "_"
                      + aktionen[aktionen.length - 1];
        }

        try {
            msg = de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString(aktuKey);

            //Variablen mit Spielernamen füllen
            tokenmsg = replaceVars(schuetzeName, vorbereiter1Name, verteidiger1Name,
                                   verteidiger2Name, torwartName, msg);

            //Message anzeigen
            drawString(tokenmsg);
        } catch (Exception e) {
            drawString("Message not found: " + aktuKey);
        }

        try {
            Thread.sleep(SLEEP_VERYSHORT);
        } catch (Exception e) {
        }

        //Trainer
        m_jpZusatzInfoPanel.getHeimTrainer().showSequence(TrainerLibrary.getTorschussAbschluss(true,
                                                                                               heimTeam,
                                                                                               torchance > 3,
                                                                                               trainerVariante));
        m_jpZusatzInfoPanel.getGastTrainer().showSequence(TrainerLibrary.getTorschussAbschluss(false,
                                                                                               heimTeam,
                                                                                               torchance > 3,
                                                                                               trainerVariante2));

        //TOOR
        if (torchance > 3) {
            //Spielstand aktualisieren
            drawString(TOR);

            if (torart == de.hattrickorganizer.logik.SpielLogik.TA_EIGENTOR) {
                setSpielstand(" --- ", spielminute, heimTeam);
            }
            //Normales Tor
            else {
                setSpielstand(schuetze, spielminute, heimTeam);
            }
        }

        drawString("");

        //setSpielstand( sb.ToreHeim (), sb.ToreGast () );
        m_bScreenBereit = true;

        //wait aufheben
        m_clChat.sendBereit(m_bScreenBereit);
    }

    /**
     * stellt eine Verletzung dar
     *
     * @param textKey TODO Missing Constructuor Parameter Documentation
     * @param spieler TODO Missing Constructuor Parameter Documentation
     * @param spielminute TODO Missing Constructuor Parameter Documentation
     * @param variante TODO Missing Constructuor Parameter Documentation
     * @param trainerVariante TODO Missing Constructuor Parameter Documentation
     * @param heim TODO Missing Constructuor Parameter Documentation
     */
    public final void doVerletzung(byte textKey, String spieler, int spielminute, int variante,
                                   int trainerVariante, boolean heim) {
        m_bScreenBereit = false;
        m_clChat.sendBereit(m_bScreenBereit);

        String msg = "";
        String[] tokenmsg = null;
        String aktuKey = "";
        String spielername = "";

        if (heim) {
            spielername = HEIMTEAM + spieler + ENDETEAM;
        } else {
            spielername = GASTTEAM + spieler + ENDETEAM;
        }

        final String[] varConst = {MatchScreen.VAR_SPIELERNAME, MatchScreen.VAR_SPIELMINUTE};
        final String[] varValue = {spielername, "" + spielminute};

        drawSpielMinute(spielminute);

        //Erste AktionsKey bestimmen        
        aktuKey = "" + textKey + "_" + variante;

        try {
            msg = de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString(aktuKey);

            //Variablen mit Spielernamen füllen
            tokenmsg = replaceAllVars(varConst, varValue, msg);

            //Message anzeigen
            drawString(tokenmsg);
            drawString("");
        } catch (Exception e) {
            drawString("Message not found: " + aktuKey);
        }

        if (heim) {
            m_jpZusatzInfoPanel.getHeimTrainer().showSequence(TrainerLibrary.getVerletztung(heim,
                                                                                            trainerVariante));
        } else {
            m_jpZusatzInfoPanel.getGastTrainer().showSequence(TrainerLibrary.getVerletztung(heim,
                                                                                            trainerVariante));
        }

        m_bScreenBereit = true;
        m_clChat.sendBereit(m_bScreenBereit);
    }

    /**
     * Gibt einen Infotext wieder z.B: gleich geht's los, Halbzeit, ElferSchießen, Spielende...
     *
     * @param msgType TODO Missing Constructuor Parameter Documentation
     * @param variante TODO Missing Constructuor Parameter Documentation
     */
    public final void drawInfoText(byte msgType, int variante) {
        String msg = "";
        String aktuKey = "";

        m_bScreenBereit = false;
        m_clChat.sendBereit(m_bScreenBereit);

        //Erste AktionsKey bestimmen        
        aktuKey = "" + msgType + "_" + variante;
        msg = de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString(aktuKey);

        //Message anzeigen
        drawString(ALLGEMEIN + msg + ENDEALLGEMEIN + BREAK);

        m_bScreenBereit = true;
        m_clChat.sendBereit(m_bScreenBereit);
    }

    //-----Methoden des MatchScreens    

    /**
     * passt Spielstand an
     *
     * @param sb TODO Missing Constructuor Parameter Documentation
     */
    public final void drawSpielstand(Spielbericht sb) {
        drawString(HEIMTEAM + sb.Heim().getTeamName() + ENDETEAM + "&nbsp;&nbsp;" + ALLGEMEIN
                   + sb.ToreHeim() + ENDEALLGEMEIN + " : " + GASTTEAM + sb.Gast().getTeamName()
                   + ENDETEAM + "&nbsp;&nbsp;" + ALLGEMEIN + sb.ToreGast() + ENDEALLGEMEIN + BREAK);
    }

    /**
     * Stellt Text dar
     *
     * @param msg TODO Missing Constructuor Parameter Documentation
     */
    public final void drawString(String msg) {
        m_jpTextModusPanel.append(msg);
    }

    /**
     * Stellt Text dar, mit warteZeit zwischen den einzelnen Elementen
     *
     * @param msg TODO Missing Constructuor Parameter Documentation
     */
    public final void drawString(String[] msg) {
        for (int i = 0; i < (msg.length - 1); i++) {
            m_jpTextModusPanel.append(msg[i].toString());

            try {
                Thread.sleep(SLEEP_SHORT);
            } catch (Exception e) {
            }
        }

        m_jpTextModusPanel.append(msg[msg.length - 1].toString());
    }

    //----------Chat------------------------------------    

    /**
     * empfängt Msg und stellt sie dar
     *
     * @param trainer TODO Missing Constructuor Parameter Documentation
     * @param msg TODO Missing Constructuor Parameter Documentation
     * @param heim TODO Missing Constructuor Parameter Documentation
     */
    public final void recieveMsg(String trainer, String msg, boolean heim) {
        if (heim) {
            trainer = HEIMTEAM + trainer + ENDETEAM;
        } else {
            trainer = GASTTEAM + trainer + ENDETEAM;
        }

        m_jpZusatzInfoPanel.receiveMessage(trainer, msg);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param msg TODO Missing Method Parameter Documentation
     */
    public final void sendMsg(String msg) {
        final String trainer = de.hattrickorganizer.model.HOVerwaltung.instance().getModel()
                                                                      .getBasics().getManager();

        try {
            m_clChat.sendChatMsg(trainer, msg, m_bServer);
        } catch (Exception e) {
        }
    }

    //--------WindowListener-------------------
    public void windowActivated(java.awt.event.WindowEvent windowEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param windowEvent TODO Missing Method Parameter Documentation
     */
    public final void windowClosed(java.awt.event.WindowEvent windowEvent) {
        m_jpZusatzInfoPanel.stopAnimation();
        m_bAbbruch = true;
        m_clChat.sendAbbruch(m_bAbbruch);
        m_clChat.shutdown();
        setVisible(false);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param windowEvent TODO Missing Method Parameter Documentation
     */
    public final void windowClosing(java.awt.event.WindowEvent windowEvent) {
        m_jpZusatzInfoPanel.stopAnimation();
        m_bAbbruch = true;
        m_clChat.sendAbbruch(m_bAbbruch);
        m_clChat.shutdown();
        setVisible(false);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param windowEvent TODO Missing Method Parameter Documentation
     */
    public void windowDeactivated(java.awt.event.WindowEvent windowEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param windowEvent TODO Missing Method Parameter Documentation
     */
    public void windowDeiconified(java.awt.event.WindowEvent windowEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param windowEvent TODO Missing Method Parameter Documentation
     */
    public void windowIconified(java.awt.event.WindowEvent windowEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param windowEvent TODO Missing Method Parameter Documentation
     */
    public void windowOpened(java.awt.event.WindowEvent windowEvent) {
    }

    /**
     * ersetzt die Konstanten
     *
     * @param varConst Array der Constanten
     * @param varValue Inhalt mit dem ersetzt werden soll
     * @param msg TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected static String[] replaceAllVars(String[] varConst, String[] varValue, String msg) {
        final StringBuffer buf = new StringBuffer(msg);
        int index = buf.indexOf(VAR_SCHUETZE);

        for (int i = 0;
             (varConst != null) && (varValue != null) && (varValue.length == varConst.length)
             && (i < varConst.length); i++) {
            index = buf.indexOf(varConst[i]);

            //Schuütze ersetzen
            while (index > -1) {
                buf.replace(index, index + varConst[i].length(), varValue[i]);
                index = buf.indexOf(varConst[i]);
            }
        }

        final String[] returntext = buf.toString().split("%wait%");

        return returntext;
    }

    //-------------------------    Replace --------------------------------------
    protected final String[] replaceVars(String schuetze, String vorbereiter1, String verteidiger1,
                                         String verteidiger2, String torwart, String msg) {
        final StringBuffer buf = new StringBuffer(msg);
        int index = buf.indexOf(VAR_SCHUETZE);

        //Schuütze ersetzen
        while (index > -1) {
            buf.replace(index, index + VAR_SCHUETZE.length(), schuetze);
            index = buf.indexOf(VAR_SCHUETZE);
        }

        //Vorbereiter      
        index = buf.indexOf(VAR_VORBEREITER1);

        while (index > -1) {
            buf.replace(index, index + VAR_VORBEREITER1.length(), vorbereiter1);
            index = buf.indexOf(VAR_VORBEREITER1);
        }

        //ver1
        index = buf.indexOf(VAR_VERTEIDIGER1);

        while (index > -1) {
            buf.replace(index, index + VAR_VERTEIDIGER1.length(), verteidiger1);
            index = buf.indexOf(VAR_VERTEIDIGER1);
        }

        //ver2
        index = buf.indexOf(VAR_VERTEIDIGER2);

        while (index > -1) {
            buf.replace(index, index + VAR_VERTEIDIGER2.length(), verteidiger2);
            index = buf.indexOf(VAR_VERTEIDIGER2);
        }

        //tw
        index = buf.indexOf(VAR_TORWART);

        while (index > -1) {
            buf.replace(index, index + VAR_TORWART.length(), torwart);
            index = buf.indexOf(VAR_TORWART);
        }

        final String[] returntext = buf.toString().split("%wait%");

        return returntext;
    }

    /**
     * Gibt die Spielminute aus
     *
     * @param minute TODO Missing Constructuor Parameter Documentation
     */
    private void drawSpielMinute(int minute) {
        m_jpSpielstandPanel.setSpielminute(minute);

        final String string = ALLGEMEIN + minute + ". "
                              + de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Spielminute")
                              + ENDEALLGEMEIN;
        drawString(string);
    }

    /**
     * TODO Missing Method Documentation
     */
    private void initComponents() {
        setContentPane(new de.hattrickorganizer.gui.templates.RasenPanel());
        getContentPane().setLayout(new BorderLayout());

        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(8, 8, 8, 8);

        getContentPane().setLayout(layout);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 0.1;
        constraints.gridx = 1;
        constraints.gridy = 0;
        m_jpSpielstandPanel = new SpielstandPanel();
        layout.setConstraints(m_jpSpielstandPanel, constraints);
        getContentPane().add(m_jpSpielstandPanel);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.gridx = 1;
        constraints.gridy = 1;
        m_jpTextModusPanel = new TextModusPanel();
        layout.setConstraints(m_jpTextModusPanel, constraints);
        getContentPane().add(m_jpTextModusPanel);

        m_jpZusatzInfoPanel = new ZusatzInfoPanel(this);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.weighty = 0.01;
        constraints.gridx = 1;
        constraints.gridy = 2;
        layout.setConstraints(m_jpZusatzInfoPanel, constraints);
        getContentPane().add(m_jpZusatzInfoPanel);

        //-------
    }
}
