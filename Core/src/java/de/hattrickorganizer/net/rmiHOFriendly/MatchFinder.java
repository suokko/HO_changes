// %3447615898:de.hattrickorganizer.net.rmiHOFriendly%
/*
 * MatchFinder.java
 *
 * Created on 13. Oktober 2003, 07:30
 */
package de.hattrickorganizer.net.rmiHOFriendly;

import de.hattrickorganizer.net.MyConnector;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class MatchFinder implements Runnable {
    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    protected String m_sInfo = "";

    /** TODO Missing Parameter Documentation */
    protected String m_sIp = "";

    /** TODO Missing Parameter Documentation */
    protected boolean m_bRunning;

    /** TODO Missing Parameter Documentation */
    protected int m_iMatchId = -1;

    /** TODO Missing Parameter Documentation */
    protected int m_iPort = -1;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of MatchFinder
     *
     * @param ip TODO Missing Constructuor Parameter Documentation
     * @param port TODO Missing Constructuor Parameter Documentation
     * @param info TODO Missing Constructuor Parameter Documentation
     */
    public MatchFinder(String ip, int port, String info) {
        m_sIp = ip.trim();
        m_iPort = port;
        m_sInfo = info;

        //, ',' und ';' aus info entfernen        
        m_sInfo.replace(',', '§');
        m_sInfo.replace(';', '§');
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Setter for property m_iMatchId.
     *
     * @param m_iMatchId New value of property m_iMatchId.
     */
    public final void setMatchId(int m_iMatchId) {
        this.m_iMatchId = m_iMatchId;
    }

    /**
     * Getter for property m_iMatchId.
     *
     * @return Value of property m_iMatchId.
     */
    public final int getMatchId() {
        return m_iMatchId;
    }

    /**
     * Setter for property m_bRunning.
     *
     * @param m_bRunning New value of property m_bRunning.
     */
    public final void setRunning(boolean m_bRunning) {
        this.m_bRunning = m_bRunning;

        if (!m_bRunning) {
            //Server registrierung aufheben
            MyConnector.instance().unregisterServer(m_iMatchId);
        }
    }

    /**
     * Getter for property m_bRunning.
     *
     * @return Value of property m_bRunning.
     */
    public final boolean isRunning() {
        return m_bRunning;
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void run() {
        //Server ins Netz registrieren
        m_iMatchId = MyConnector.instance().registerServer(m_sIp, m_iPort, m_sInfo);

        if (m_iMatchId > -1) {
            m_bRunning = true;

            while (m_bRunning) {
                try {
                    //Keep alive senden
                    if (!MyConnector.instance().sendAlive(m_iMatchId)) {
                        m_bRunning = false;
                        doMsg(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("ERROR_MATCHFINDER"));
                    }

                    //knapp unter 3 min, (wegen meinem Router) heia machen
                    Thread.sleep(175000);
                } catch (Exception e) {
                }
            }
        } else {
            doMsg(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("ERROR_MATCHFINDER"));
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param msg TODO Missing Method Parameter Documentation
     */
    protected final void doMsg(String msg) {
        javax.swing.JOptionPane.showMessageDialog(de.hattrickorganizer.gui.HOMainFrame.instance(),
                                                  msg,
                                                  de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Fehler"),
                                                  javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}
