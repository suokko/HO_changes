//// %658891718:de.hattrickorganizer.logik%
///*
// * HOFriendlyManager.java
// *
// * Created on 23. September 2004, 07:14
// */
//package de.hattrickorganizer.logik;
//
//import javax.swing.JOptionPane;
//
//import plugins.IHOFriendlyManager;
//import de.hattrickorganizer.gui.HOMainFrame;
//import de.hattrickorganizer.model.HOVerwaltung;
//import de.hattrickorganizer.model.ServerTeam;
//import de.hattrickorganizer.net.MyConnector;
//
//
///**
// * DOCUMENT ME!
// *
// * @author thomas.werth
// */
//public class HOFriendlyManager implements IHOFriendlyManager {
//    //~ Constructors -------------------------------------------------------------------------------
//
//    /**
//     * Creates a new instance of HOFriendlyManager
//     */
//    public HOFriendlyManager() {
//    }
//
//    //~ Methods ------------------------------------------------------------------------------------
//
//    /**
//     * TODO Missing Method Documentation
//     *
//     * @param serverIP TODO Missing Method Parameter Documentation
//     * @param serverPort TODO Missing Method Parameter Documentation
//     */
//    public final void doClient(String serverIP, int serverPort) {
//        de.hattrickorganizer.net.rmiHOFriendly.HoClientImp hoClientImp = null;
//        final ServerTeam team = HOVerwaltung.instance().getModel().getVerein().erstelleServerTeam();
//
//        if (team.getAnzAufgestellteSpieler() < 8) {
//            de.hattrickorganizer.tools.Helper.showMessage(HOMainFrame.instance(),
//                                                          de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("ZuWenigSpieler"),
//                                                          de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Fehler"),
//                                                          JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        //Client Screen erzeugen
//        final de.hattrickorganizer.gui.hoFriendly.HOFriendlyDialog soms = new de.hattrickorganizer.gui.hoFriendly.HOFriendlyDialog(HOMainFrame
//                                                                                                                                   .instance(),
//                                                                                                                                   false);
//        final de.hattrickorganizer.net.rmiHOFriendly.NetMatchScreen nms = new de.hattrickorganizer.net.rmiHOFriendly.NetMatchScreen(soms);
//
//        hoClientImp = new de.hattrickorganizer.net.rmiHOFriendly.HoClientImp(nms);
//        soms.setChat(hoClientImp);
//
//        if (hoClientImp.connect2Server(serverIP, serverPort)) {
//            //Spiel starten
//            hoClientImp.sendStarteFriendly(team);
//
//            //man könnte mit hoClientImp.getSpielbericht () nach Ende den Spielbericht abfragen...
//        } else {
//            //Fehlermeldung
//            de.hattrickorganizer.tools.Helper.showMessage(HOMainFrame.instance(),
//                                                          de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("KeinServer")
//                                                          + serverIP + ":" + serverPort,
//                                                          de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Fehler"),
//                                                          JOptionPane.ERROR_MESSAGE);
//
//            //Dialog shutdown
//            soms.setVisible(false);
//            soms.dispose();
//
//            if (hoClientImp != null) {
//                hoClientImp.shutdown();
//                hoClientImp = null;
//            }
//        }
//    }
//
//    /**
//     * TODO Missing Method Documentation
//     *
//     * @param serverIP TODO Missing Method Parameter Documentation
//     * @param serverPort TODO Missing Method Parameter Documentation
//     * @param isInternetServer TODO Missing Method Parameter Documentation
//     */
//    public final void doServer(String serverIP, int serverPort, boolean isInternetServer) {
//        final ServerTeam team = HOVerwaltung.instance().getModel().getVerein().erstelleServerTeam();
//
//        if (team.getAnzAufgestellteSpieler() < 8) {
//            de.hattrickorganizer.tools.Helper.showMessage(HOMainFrame.instance(),
//                                                          de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("ZuWenigSpieler"),
//                                                          de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Fehler"),
//                                                          JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        final de.hattrickorganizer.model.Server server = new de.hattrickorganizer.model.Server(serverIP,
//                                                                                               serverPort);
//
//        if (server.noError()) {
//            de.hattrickorganizer.net.rmiHOFriendly.HOServerImp hoServerImp = null;
//
//            //Server Screen erzeugen
//            final de.hattrickorganizer.gui.hoFriendly.HOFriendlyDialog soms = new de.hattrickorganizer.gui.hoFriendly.HOFriendlyDialog(HOMainFrame
//                                                                                                                                       .instance(),
//                                                                                                                                       true);
//            final de.hattrickorganizer.net.rmiHOFriendly.NetMatchScreen nms = new de.hattrickorganizer.net.rmiHOFriendly.NetMatchScreen(soms);
//            String info = null;
//
//            //String[]    find        ={ " " };
//            //String[]    replace =   { "%20%" };
//            //Werte übergeben
//            server.setScreen1(nms);
//
//            //Server Team noch setzen
//            server.setHeimTeam(team);
//
//            //Socket Server anlegen
//            hoServerImp = new de.hattrickorganizer.net.rmiHOFriendly.HOServerImp(server);
//            server.setHOServer(hoServerImp);
//            soms.setChat(hoServerImp);
//
//            //Info String vorbereiten
//            info = team.getTeamName() + " (" + team.getManagerName() + ")  "
//                   + de.hattrickorganizer.model.HOVerwaltung.instance().getModel().getLiga()
//                                                            .getLiga();
//            info.trim();
//
//            //tools.MyHelper.replaceSubString( find, replace, request );
//            try {
//                info = java.net.URLEncoder.encode(info, "UTF-8");
//
//                //             HOLogger.instance().log(getClass(),"Register request : " + info );
//            } catch (Exception e) {
//            }
//
//            //Socket starten
//            hoServerImp.createServer(isInternetServer, serverIP, serverPort, info);
//
//            //man könnte mit server.getSpielbericht () nach Ende den Spielbericht abfragen...
//        } else {
//            //Fehlermeldung
//        }
//    }
//
//    /**
//     * TODO Missing Method Documentation
//     */
//    public final void doStandardFriendly() {
//        final ServerTeam team = HOVerwaltung.instance().getModel().getVerein().erstelleServerTeam();
//
//        if (team.getAnzAufgestellteSpieler() < 8) {
//            de.hattrickorganizer.tools.Helper.showMessage(HOMainFrame.instance(),
//                                                          de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("ZuWenigSpieler"),
//                                                          de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Fehler"),
//                                                          JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        final de.hattrickorganizer.gui.hoFriendly.RMIDialog rmiDialog = new de.hattrickorganizer.gui.hoFriendly.RMIDialog(HOMainFrame
//                                                                                                                          .instance());
//
//        if (!rmiDialog.isAbgebrochen()) {
//            if (rmiDialog.isServer()) {
//                doServer(rmiDialog.getServerIP(), rmiDialog.getPort(), rmiDialog.isInternetServer());
//
//                //Client
//            } else {
//                doClient(rmiDialog.getClientIP(), rmiDialog.getPort());
//            }
//        }
//    }
//
//    /**
//     * TODO Missing Method Documentation
//     */
//    public final void showIpAdressPage() {
//        try {
//            de.hattrickorganizer.tools.BrowserLauncher.openURL(MyConnector.getHOSite()+"/IPAdresse.html");
//        } catch (java.io.IOException ioex) {
//        }
//    }
//}
