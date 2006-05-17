// %1127326824134:net.rmiHOFriendly%
/*
 * iRMIChat.java
 *
 * Created on 10. Juli 2003, 08:01
 */
package de.hattrickorganizer.net.rmiHOFriendly;

/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public interface Chat {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public static final byte NET_CHAT_MSG = 0;

    /** TODO Missing Parameter Documentation */
    public static final byte NET_CREATEFRIENDLY_MSG = 1;

    /** TODO Missing Parameter Documentation */
    public static final byte NET_TORCHANCE_MSG = 2;

    /** TODO Missing Parameter Documentation */
    public static final byte NET_INFO_MSG = 3;

    /** TODO Missing Parameter Documentation */
    public static final byte NET_KARTE_MSG = 4;

    /** TODO Missing Parameter Documentation */
    public static final byte NET_VERLETZUNG_MSG = 5;

    /** TODO Missing Parameter Documentation */
    public static final byte NET_AUSWECHSLUNG_MSG = 6;

    /** TODO Missing Parameter Documentation */
    public static final byte NET_FANGESANG_MSG = 7;

    /** TODO Missing Parameter Documentation */
    public static final byte NET_SPIELMINUTE_MSG = 8;

    /** TODO Missing Parameter Documentation */
    public static final byte NET_SPIELENDE_MSG = 9;

    /** TODO Missing Parameter Documentation */
    public static final byte NET_BEREIT_MSG = 10;

    /** TODO Missing Parameter Documentation */
    public static final byte NET_PAUSE_MSG = 11;

    /** TODO Missing Parameter Documentation */
    public static final byte NET_ABBRUCH_MSG = 12;

    /** TODO Missing Parameter Documentation */
    public static final byte NET_SPIELBEGINN_MSG = 13;

    //~ Methods ------------------------------------------------------------------------------------

    //Chat
    public void recieveMsg(String trainer, String msg, boolean heim);

    /**
     * TODO Missing Method Documentation
     *
     * @param bool TODO Missing Method Parameter Documentation
     */
    public void sendAbbruch(boolean bool);

    /**
     * TODO Missing Method Documentation
     *
     * @param bool TODO Missing Method Parameter Documentation
     */
    public void sendBereit(boolean bool);

    /**
     * TODO Missing Method Documentation
     *
     * @param trainer TODO Missing Method Parameter Documentation
     * @param msg TODO Missing Method Parameter Documentation
     * @param heim TODO Missing Method Parameter Documentation
     */
    public void sendChatMsg(String trainer, String msg, boolean heim);

    //Eigentlich nur für Client relevant...
    public void sendPause(boolean bool);

    /**
     * TODO Missing Method Documentation
     */
    public void shutdown();
}
