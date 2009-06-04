// %2917309115:de.hattrickorganizer.net.rmiHOFriendly%
/*
 * HoServerWorker.java
 *
 * Created on 29. Juli 2003, 11:12
 */
package de.hattrickorganizer.net.rmiHOFriendly;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import de.hattrickorganizer.tools.HOLogger;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class HoServerWorker implements Runnable {
    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    protected DataInputStream m_clInput;

    /** TODO Missing Parameter Documentation */
    protected HOServerImp m_clHOServer;

    /** TODO Missing Parameter Documentation */
    protected Socket m_clSocket;

    /** TODO Missing Parameter Documentation */
    protected boolean m_bRun = true;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of HoServerWorker
     *
     * @param server TODO Missing Constructuor Parameter Documentation
     */
    public HoServerWorker(HOServerImp server) {
        try {
            m_clHOServer = server;
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }
    }

    //~ Methods ------------------------------------------------------------------------------------

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
     * TODO Missing Method Documentation
     */
    public final void run() {
        byte data = 0;
        boolean realconnect = false;
        byte attempts = 0;

        while (!realconnect) {
            attempts = 0;

            try {
                //Lauschen...        
                m_clSocket = m_clHOServer.getServerSocket().accept();

                //output kanal merken
                m_clHOServer.setOutput(new DataOutputStream(m_clSocket.getOutputStream()));

                //ClientSocket noch setzen
                m_clHOServer.setClient(m_clSocket);

                //Input Stream holen
                m_clInput = new DataInputStream(m_clSocket.getInputStream());
            } catch (Exception e) {
                HOLogger.instance().log(getClass(),e);

                //Abbruch
                return;
            }

            //Botschaften verarbeiten
            while (m_bRun) {
                try {
                    if (m_clInput.available() > 0) {
                        //Daten Falg auswerten
                        data = m_clInput.readByte();

                        switch (data) {
                            case Chat.NET_CHAT_MSG:
                                reciveChatMsg();
                                break;

                            case Chat.NET_CREATEFRIENDLY_MSG:
                                realconnect = true;
                                reciveStartFriendlyMsg();

                                //HOLogger.instance().log(getClass(),"Starte Friendly kommt" );
                                break;

                            case Chat.NET_BEREIT_MSG:

                                try {
                                    m_clHOServer.setBereit(m_clInput.readBoolean());
                                } catch (Exception e) {
                                    HOLogger.instance().log(getClass(),e);
                                }

                                //HOLogger.instance().log(getClass(),"bereit kommt" );
                                break;

                            case Chat.NET_ABBRUCH_MSG:

                                try {
                                    m_clHOServer.setAbbruch(m_clInput.readBoolean());
                                } catch (Exception e) {
                                    HOLogger.instance().log(getClass(),e);
                                }

                                //HOLogger.instance().log(getClass(),"Abbruch kommt" );
                                break;

                            case Chat.NET_PAUSE_MSG:

                                try {
                                    m_clHOServer.setPause(m_clInput.readBoolean());
                                } catch (Exception e) {
                                    HOLogger.instance().log(getClass(),e);
                                }

                                break;
                        }
                    } else if (!realconnect) {
                        if (attempts > 30) {
                            break;
                        } else {
                            attempts++;
                        }
                    }

                    Thread.sleep(100);
                } catch (Exception e) {
                    HOLogger.instance().log(getClass(),e);
                }
            }
        }
    }

    /**
     * TODO Missing Method Documentation
     */
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

            //Chat Msg vom Client an Server weitergeben
            m_clHOServer.recieveMsg(trainer, msg, heim);

            //HOLogger.instance().log(getClass(),"Chat empfangen: " + trainer + " " + msg );
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }
    }

    /**
     * liest die Daten zum start eines Friendly's ein
     */
    protected final synchronized void reciveStartFriendlyMsg() {
        de.hattrickorganizer.model.ServerTeam clientteam = null;

        try {
            clientteam = new de.hattrickorganizer.model.ServerTeam(m_clInput);

            //Chat Msg an Client weitergeben
            m_clHOServer.createFriendly(clientteam);
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }
    }
}
