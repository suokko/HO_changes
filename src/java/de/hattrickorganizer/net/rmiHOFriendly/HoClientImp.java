// %994687206:de.hattrickorganizer.net.rmiHOFriendly%
/*
 * HoClientImp.java
 *
 * Created on 29. Juli 2003, 10:19
 */
package de.hattrickorganizer.net.rmiHOFriendly;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import de.hattrickorganizer.model.MatchScreen;
import de.hattrickorganizer.model.ServerTeam;
import de.hattrickorganizer.model.Spielbericht;
import de.hattrickorganizer.tools.HOLogger;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class HoClientImp implements Chat {
    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    protected DataOutputStream m_clOutput;

    /** TODO Missing Parameter Documentation */
    protected HOClientWorker m_clWorker;

    //braucht noch Referenz auf den Client , um an diesen die Msg weiterleite

    /** TODO Missing Parameter Documentation */
    protected MatchScreen m_clClient;

    /** TODO Missing Parameter Documentation */
    protected Socket m_clSocket;

    /** TODO Missing Parameter Documentation */
    protected Spielbericht m_clSpielbericht;

    /** TODO Missing Parameter Documentation */
    protected boolean m_bWriting;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of HoClientImp
     *
     * @param client TODO Missing Constructuor Parameter Documentation
     */
    public HoClientImp(MatchScreen client) {
        m_clClient = client;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean isAbbruch() {
        return m_clClient.isAbbruch();
    }

    /**
     * Setter for property m_clClient.
     *
     * @param m_clClient New value of property m_clClient.
     */
    public final void setClient(de.hattrickorganizer.model.MatchScreen m_clClient) {
        this.m_clClient = m_clClient;
    }

    /**
     * Getter for property m_clClient.
     *
     * @return Value of property m_clClient.
     */
    public final de.hattrickorganizer.model.MatchScreen getClient() {
        return m_clClient;
    }

    /**
     * Setter for property m_clOutput.
     *
     * @param m_clOutput New value of property m_clOutput.
     */
    public final void setOutput(java.io.DataOutputStream m_clOutput) {
        this.m_clOutput = m_clOutput;
    }

    /**
     * Getter for property m_clOutput.
     *
     * @return Value of property m_clOutput.
     */
    public final java.io.DataOutputStream getOutput() {
        return m_clOutput;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param pause TODO Missing Method Parameter Documentation
     */
    public final void setPause(boolean pause) {
        m_clClient.setPause(pause);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean isPause() {
        return m_clClient.isPause();
    }

    /**
     * gibt an ob der Screen bereit zum zeichnen ist
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean isScreenBereit() {
        return m_clClient.isScreenBereit();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param bericht TODO Missing Method Parameter Documentation
     */
    public final void setSpielbericht(Spielbericht bericht) {
        m_clSpielbericht = bericht;
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
     * TODO Missing Method Documentation
     *
     * @param ip TODO Missing Method Parameter Documentation
     * @param port TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean connect2Server(String ip, int port) {
        try {
            m_clSocket = new Socket(ip, port);

            /*
               if ( m_clSocket.isConnected() )
               {
                   HOLogger.instance().log(getClass(),"Verbunden" );
               }
               else
               {
                   HOLogger.instance().log(getClass(),"Nö" );
               }
             */
            m_clOutput = new DataOutputStream(m_clSocket.getOutputStream());
            m_clWorker = new HOClientWorker(m_clSocket, this);
            new Thread(m_clWorker).start();
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
            m_clWorker = null;

            return false;
        }

        return m_clSocket.isConnected();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param trainer TODO Missing Method Parameter Documentation
     * @param msg TODO Missing Method Parameter Documentation
     * @param heim TODO Missing Method Parameter Documentation
     */
    public final void recieveMsg(String trainer, String msg, boolean heim) {
        m_clClient.recieveMsg(trainer, msg, heim);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param bool TODO Missing Method Parameter Documentation
     */
    public final void sendAbbruch(boolean bool) {
        requestWriteAccess();

        try {
            m_clOutput.writeByte(Chat.NET_ABBRUCH_MSG);
            m_clOutput.writeBoolean(bool);
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }

        releaseWriteAccess();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param bool TODO Missing Method Parameter Documentation
     */
    public final void sendBereit(boolean bool) {
        requestWriteAccess();

        try {
            m_clOutput.writeByte(Chat.NET_BEREIT_MSG);
            m_clOutput.writeBoolean(bool);
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }

        releaseWriteAccess();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param trainer TODO Missing Method Parameter Documentation
     * @param msg TODO Missing Method Parameter Documentation
     * @param heim TODO Missing Method Parameter Documentation
     */
    public final void sendChatMsg(String trainer, String msg, boolean heim) {
        requestWriteAccess();

        try {
            m_clOutput.writeByte(Chat.NET_CHAT_MSG);
            m_clOutput.writeUTF(trainer);
            m_clOutput.writeUTF(msg);
            m_clOutput.writeBoolean(heim);

            //HOLogger.instance().log(getClass(),"Chat gesendet: " + trainer + " " + msg );
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }

        releaseWriteAccess();
        m_clClient.recieveMsg(trainer, msg, heim);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param bool TODO Missing Method Parameter Documentation
     */
    public final void sendPause(boolean bool) {
        requestWriteAccess();

        try {
            m_clOutput.writeByte(Chat.NET_PAUSE_MSG);
            m_clOutput.writeBoolean(bool);
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }

        releaseWriteAccess();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param clientTeam TODO Missing Method Parameter Documentation
     */
    public final void sendStarteFriendly(ServerTeam clientTeam) {
        requestWriteAccess();

        try {
            m_clOutput.writeByte(Chat.NET_CREATEFRIENDLY_MSG);
            clientTeam.save(m_clOutput);
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }

        releaseWriteAccess();
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void shutdown() {
        //Clean up
        try {
            m_clWorker.setRun(false);
            m_clOutput.close();
            m_clSocket.close();
            m_clWorker = null;
        } catch (IOException e) {
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    protected final void finalize() {
        shutdown();
    }

    /**
     * gibt die Schreibrechte wieder frei
     */
    protected final synchronized void releaseWriteAccess() {
        try {
            m_clOutput.flush();
        } catch (Exception e) {
        }

        m_bWriting = false;
    }

    /**
     * fordert Schreibrechte für den Output!
     */
    protected final synchronized void requestWriteAccess() {
        while (m_bWriting) {
            try {
                Thread.sleep(20);
            } catch (Exception e) {
            }
        }

        m_bWriting = true;
    }
}
