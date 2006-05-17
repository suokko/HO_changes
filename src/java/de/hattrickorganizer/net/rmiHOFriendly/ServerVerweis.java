// %2392347522:de.hattrickorganizer.net.rmiHOFriendly%
/*
 * ServerVerweis.java
 *
 * Created on 13. Oktober 2003, 07:35
 */
package de.hattrickorganizer.net.rmiHOFriendly;

import java.sql.Timestamp;

import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.MyHelper;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class ServerVerweis {
    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    protected String m_sInfo = "";

    /** TODO Missing Parameter Documentation */
    protected String m_sIp = "";

    /** TODO Missing Parameter Documentation */
    protected Timestamp m_clDate;

    /** TODO Missing Parameter Documentation */
    protected int m_iMatchId = -1;

    /** TODO Missing Parameter Documentation */
    protected int m_iPort = -1;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of ServerVerweis
     */
    public ServerVerweis() {
    }

    /**
     * enthält einen zu parsenden String Format : id,ip,port,info,Timestamp
     *
     * @param parse TODO Missing Constructuor Parameter Documentation
     */
    public ServerVerweis(String parse) {
        String[] inhalt = null;

        try {
            inhalt = MyHelper.generateStringArray(parse, ',');

            //ID
            try {
                m_iMatchId = Integer.parseInt(inhalt[0]);
            } catch (Exception e) {
                m_iMatchId = -1;
            }

            m_sIp = inhalt[1];

            try {
                m_iPort = Integer.parseInt(inhalt[2]);
            } catch (Exception e) {
                m_iPort = 1099;
            }

            m_sInfo = MyHelper.replaceChar(inhalt[3], '§', ' ');

            m_clDate = MyHelper.parseDate(inhalt[4]);
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"Error in ServerVerweis Konstruktor : " + e);
        }
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Setter for property m_sInfo.
     *
     * @param m_sInfo New value of property m_sInfo.
     */
    public final void setInfo(java.lang.String m_sInfo) {
        this.m_sInfo = m_sInfo;
    }

    /**
     * Getter for property m_sInfo.
     *
     * @return Value of property m_sInfo.
     */
    public final java.lang.String getInfo() {
        return m_sInfo;
    }

    /**
     * Setter for property m_sIp.
     *
     * @param m_sIp New value of property m_sIp.
     */
    public final void setIp(java.lang.String m_sIp) {
        this.m_sIp = m_sIp;
    }

    /**
     * Getter for property m_sIp.
     *
     * @return Value of property m_sIp.
     */
    public final java.lang.String getIp() {
        return m_sIp;
    }

    /**
     * Setter for property m_iPort.
     *
     * @param m_iPort New value of property m_iPort.
     */
    public final void setPort(int m_iPort) {
        this.m_iPort = m_iPort;
    }

    /**
     * Getter for property m_iPort.
     *
     * @return Value of property m_iPort.
     */
    public final int getPort() {
        return m_iPort;
    }

    ///////////////////Overwrite////////////////////////////////////////////////7
    public final String toString() {
        return m_sInfo;
    }
}
