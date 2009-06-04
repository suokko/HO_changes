// %1127326889509:plugins%
/*
 * IHOFriendlyManager.java
 *
 * Created on 23. September 2004, 07:33
 */
package plugins;

/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public interface IHOFriendlyManager {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * starts Client
     *
     * @param serverIP IP to which client connects
     * @param serverPort Port used by server
     */
    public void doClient(String serverIP, int serverPort);

    /**
     * Start Server for HO Friendly
     *
     * @param serverIP IPAdress of Server
     * @param serverPort Port Server should use
     * @param isInternetServer register in Matchmaker
     */
    public void doServer(String serverIP, int serverPort, boolean isInternetServer);

    /**
     * Do HoFriendly using standard Dialog
     */
    public void doStandardFriendly();

    /**
     * loads Html Page showing IP Adress of current PC
     */
    public void showIpAdressPage();
}
