// %1507739964:de.hattrickorganizer.gui.utils%
/*
 * OnlineWorker.java
 *
 * Created on 10. Oktober 2003, 07:53
 */
package de.hattrickorganizer.gui.utils;

import java.util.Calendar;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.gui.HOMainFrame;
import de.hattrickorganizer.gui.login.LoginDialog;
import de.hattrickorganizer.gui.login.LoginWaitDialog;
import de.hattrickorganizer.logik.xml.XMLArenaParser;
import de.hattrickorganizer.logik.xml.XMLMatchLineupParser;
import de.hattrickorganizer.logik.xml.XMLMatchesParser;
import de.hattrickorganizer.logik.xml.XMLSpielplanParser;
import de.hattrickorganizer.logik.xml.xmlMatchArchivParser;
import de.hattrickorganizer.logik.xml.xmlMatchdetailsParser;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.matches.MatchKurzInfo;
import de.hattrickorganizer.model.matches.MatchLineup;
import de.hattrickorganizer.model.matches.Matchdetails;
import de.hattrickorganizer.net.MyConnector;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.extension.FileExtensionManager;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class OnlineWorker {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** wait Dialog */
    protected static LoginWaitDialog waitDialog;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of OnlineWorker
     */
    public OnlineWorker() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    ////////////////////////////////////////////////////////////////////////////////
    //HRF
    ////////////////////////////////////////////////////////////////////////////////

    /**
     * saugt das HRF
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean getHrf() {
        String hrf = "";

        //Sicherstellen das HO! eingeloggt ist
        assureLogin();

        //falls immer noch nicht eingeloggt raus
        if (!MyConnector.instance().isAuthenticated()) {
            return false;
        }

        //Wairt Dialog zeigen
        waitDialog = new LoginWaitDialog(de.hattrickorganizer.gui.HOMainFrame.instance(), false);
        waitDialog.setVisible(true);

        try {
            hrf = new de.hattrickorganizer.net.ConvertXml2Hrf().createHrf(waitDialog);

            //hrf                 =   MyConnector.instance().getHRF();
        } catch (Exception e) {
            //Info
            de.hattrickorganizer.gui.HOMainFrame.instance().getInfoPanel().setLangInfoText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                                                  .getResource()
                                                                                                                                  .getProperty("Downloadfehler")
                                                                                           + " : Error converting xml 2 HRF. Corrupt/Missing Data : "
                                                                                           + gui.UserParameter
                                                                                             .instance().htip,
                                                                                           de.hattrickorganizer.gui.InfoPanel.FEHLERFARBE);
            de.hattrickorganizer.tools.Helper.showMessage(de.hattrickorganizer.gui.HOMainFrame
                                                          .instance(),
                                                          de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                 .getResource()
                                                                                                 .getProperty("Downloadfehler")
                                                          + " : Error converting xml 2 HRF. Corrupt/Missing Data : \n"
                                                          + e.toString() + "\n"
                                                          + gui.UserParameter.instance().htip,
                                                          de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                 .getResource()
                                                                                                 .getProperty("Fehler"),
                                                          JOptionPane.ERROR_MESSAGE);
            MyConnector.instance().setAuthenticated(false);
            waitDialog.setVisible(false);
            return false;
        }

        //Sprachdatei anbinden
        if ((hrf.indexOf("playingMatch=true") > -1) || (hrf.indexOf("NOT AVAILABLE") > -1)) {
            javax.swing.JOptionPane.showMessageDialog(de.hattrickorganizer.gui.HOMainFrame.instance(),
                                                      de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                             .getResource()
                                                                                             .getProperty("NO_HRF_Spiel"),
                                                      de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                             .getResource()
                                                                                             .getProperty("NO_HRF_ERROR"),
                                                      1);
            waitDialog.setVisible(false);
            return false;
        }

        final java.util.GregorianCalendar calendar = (java.util.GregorianCalendar) java.util.GregorianCalendar
                                                     .getInstance();
        String month = ((calendar.get(java.util.GregorianCalendar.MONTH)) + 1) + "";

        if (month.length() < 2) {
            month = "0" + month;
        }

        String day = calendar.get(java.util.GregorianCalendar.DAY_OF_MONTH) + "";

        if (day.length() < 2) {
            day = "0" + day;
        }

        final String name = calendar.get(java.util.GregorianCalendar.YEAR) + "-" + month + "-"
                            + day + ".hrf";

        final java.io.File pfad = new java.io.File(gui.UserParameter.instance().hrfImport_HRFPath);
        java.io.File file = new java.io.File(gui.UserParameter.instance().hrfImport_HRFPath
                                             + java.io.File.separator + name);

        //Anzeigen wenn nicht deaktiviert oder pfad noch nicht gesetzt oder Datei schon existiert
        if (gui.UserParameter.instance().showHRFSaveDialog
            || !pfad.exists()
            || !pfad.isDirectory()
            || file.exists()) {
            final JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
            fileChooser.setDialogTitle(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                              .getResource()
                                                                              .getProperty("FileExport"));

            final de.hattrickorganizer.gui.utils.ExampleFileFilter filter = new de.hattrickorganizer.gui.utils.ExampleFileFilter();
            filter.addExtension("hrf");
            filter.setDescription("Hattrick HRF");
            fileChooser.setFileFilter(filter);

            try {
                if (pfad.exists() && pfad.isDirectory()) {
                    fileChooser.setCurrentDirectory(new java.io.File(gui.UserParameter.instance().hrfImport_HRFPath));
                }
            } catch (Exception e) {
            }

            fileChooser.setSelectedFile(file);

            final int returnVal = fileChooser.showSaveDialog(de.hattrickorganizer.gui.HOMainFrame
                                                             .instance());

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
            } else {
                file = null;
            }
        }

        if ((file != null) && (file.getPath() != null)) {
            int value;

            //Endung nicht dxf?
            if (!file.getPath().endsWith(".hrf")) {
                file = new java.io.File(file.getAbsolutePath() + ".hrf");
            }

            //Datei schon vorhanden?
            if (file.exists()) {
                value = JOptionPane.showConfirmDialog(de.hattrickorganizer.gui.HOMainFrame.instance(),
                                                      de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                             .getResource()
                                                                                             .getProperty("overwrite"),
                                                      "", JOptionPane.YES_NO_OPTION);
            }
            //Datei nicht vorhanden?
            else {
                value = JOptionPane.OK_OPTION;
            }

            //Pfad speichern
            gui.UserParameter.instance().hrfImport_HRFPath = file.getParentFile().getAbsolutePath();

            //Speichern
            if (value == JOptionPane.OK_OPTION) {
                final java.io.File datei = saveFile(file.getPath(), hrf);

                //HRFParser
                final de.hattrickorganizer.model.HOModel homodel = new de.hattrickorganizer.tools.HRFFileParser()
                                                                   .parse(datei);

                if (homodel == null) {
                    //Info
                    de.hattrickorganizer.gui.HOMainFrame.instance().getInfoPanel().setLangInfoText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                                                          .getResource()
                                                                                                                                          .getProperty("Importfehler"),
                                                                                                   de.hattrickorganizer.gui.InfoPanel.FEHLERFARBE);

                    //Fehler
                    de.hattrickorganizer.tools.Helper.showMessage(de.hattrickorganizer.gui.HOMainFrame
                                                                  .instance(),
                                                                  de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                         .getResource()
                                                                                                         .getProperty("Importfehler"),
                                                                  de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                         .getResource()
                                                                                                         .getProperty("Fehler"),
                                                                  javax.swing.JOptionPane.ERROR_MESSAGE);
                } else {
                    //Info
                    de.hattrickorganizer.gui.HOMainFrame.instance().getInfoPanel().setLangInfoText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                                                          .getResource()
                                                                                                                                          .getProperty("HRFSave"));

                    //Datei schon importiert worden?
                    final String oldHRFName = de.hattrickorganizer.database.DBZugriff.instance()
                                                                                     .getHRFName4Date(homodel.getBasics()
                                                                                                             .getDatum());
                    value = javax.swing.JOptionPane.OK_OPTION;

                    //Erneut importieren
                    if (oldHRFName != null) {
                        value = javax.swing.JOptionPane.showConfirmDialog(de.hattrickorganizer.gui.HOMainFrame
                                                                          .instance(),
                                                                          de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                                 .getResource()
                                                                                                                 .getProperty("ErneutImportieren")
                                                                          + " " + oldHRFName,
                                                                          de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                                 .getResource()
                                                                                                                 .getProperty("Frage"),
                                                                          javax.swing.JOptionPane.YES_NO_OPTION);
                    }

                    //Speichern
                    if (value == javax.swing.JOptionPane.OK_OPTION) {
                        //Saven
                        homodel.saveHRF();

                        //Spielplan übernehmen!
                        homodel.setSpielplan(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                    .getModel()
                                                                                    .getSpielplan());

                        //OldPlayer dem Model hinzufügen
                        homodel.setAllOldSpieler(de.hattrickorganizer.database.DBZugriff.instance()
                                                                                        .getAllSpieler());


                        //nur Anzeige aktualisieren wenn neues model aktueller ist
                        if ((homodel != null)
                            && ((de.hattrickorganizer.model.HOVerwaltung.instance().getModel() == null)
                            || (homodel.getBasics().getDatum().after(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                            .getModel()
                                                                                                            .getBasics()
                                                                                                            .getDatum())))) {
                            
                            Date lastTrainingDate = Calendar.getInstance().getTime();
							Date lastEconomyDate = Calendar.getInstance().getTime();
                            if (HOVerwaltung.instance().getModel().getXtraDaten().getTrainingDate() != null) {
								lastTrainingDate = new Date(HOVerwaltung.instance().getModel().getXtraDaten().getTrainingDate().getTime());
								lastEconomyDate = new Date(HOVerwaltung.instance().getModel().getXtraDaten().getEconomyDate().getTime());                            	
                            } 

						    // Reimport Skillup
							DBZugriff.instance().checkSkillup(homodel);
																												                                                                                                            	
                            //Anzeigen
                            de.hattrickorganizer.model.HOVerwaltung.instance().setModel(homodel);
							
														
                            //Training neu berechnen
                            //Training->Subskillberechnung, TrainingsVec neu berechnen!
                            de.hattrickorganizer.logik.TrainingsManager.instance()
                                                                       .calculateTrainings(de.hattrickorganizer.database.DBZugriff.instance()
                                                                                                                                  .getTrainingsVector());

                            // Commented since this is not needed anymore and just slow down the startup
                            //logik.TrainingsManager.instance().fillWithData( logik.TrainingsManager.instance().calculateTrainings( database.DBZugriff.instance().getTrainingsVector() ) );
                            //Subskills berechnen !
                            homodel.calcSubskills();

                            //Aufstellung in liste als Aktuelle Aufstellungsetzen und als Angezeigte Aufstellung
                            de.hattrickorganizer.gui.lineup.AufstellungsVergleichHistoryPanel
                            .setHRFAufstellung(homodel.getAufstellung(),
                                               homodel.getLastAufstellung());
                            de.hattrickorganizer.gui.lineup.AufstellungsVergleichHistoryPanel
                            .setAngezeigteAufstellung(new de.hattrickorganizer.gui.model.AufstellungCBItem(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                                                                  .getResource()
                                                                                                                                                  .getProperty("AktuelleAufstellung"),
                                                                                                           homodel
                                                                                                           .getAufstellung()));
							HOMainFrame.instance().getAufstellungsPanel().getAufstellungsPositionsPanel().exportOldLineup("Actual");
							FileExtensionManager.extractLineup("Actual");
														
							// If training update happened, regenerate HOE Files
							if (homodel.getXtraDaten().getTrainingDate().after(lastTrainingDate)) {
								HOLogger.instance().log(getClass(),"Regenerate HOE Training Files");
								FileExtensionManager.trainingUpdate();	
							}

							// If economy update happened, regenerate HOE Files
							if (homodel.getXtraDaten().getEconomyDate().after(lastEconomyDate)) {
								HOLogger.instance().log(getClass(),"Regenerate HOE Economy Files");
								FileExtensionManager.economyUpate();	
							}

                        }

                        //Info
                        de.hattrickorganizer.gui.HOMainFrame.instance().getInfoPanel()
                                                            .setLangInfoText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                                    .getResource()
                                                                                                                    .getProperty("HRFErfolg"));
                    }
                    //Abbruch
                    else {
                        //Info
                        de.hattrickorganizer.gui.HOMainFrame.instance().getInfoPanel()
                                                            .setLangInfoText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                                    .getResource()
                                                                                                                    .getProperty("HRFAbbruch"),
                                                                             de.hattrickorganizer.gui.InfoPanel.FEHLERFARBE);
                    }
                }
            }
        }

        waitDialog.setVisible(false);

        return true;
    }

    /**
     * saugt das Archiv
     *
     * @param teamId null falls unnötig sonst im Format 2004-02-01
     * @param firstDate null falls unnötig sonst im Format 2004-02-01
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean getMatchArchiv(int teamId,
                                        java.util.GregorianCalendar firstDate /*, java.util.GregorianCalendar lastDate*/) {
        String matchASP = "";
        MatchKurzInfo[] matches = null;
        final java.util.Vector allMatches = new java.util.Vector();
        final java.util.GregorianCalendar tempBeginn = firstDate;
        final java.util.GregorianCalendar endDate = new java.util.GregorianCalendar();
        endDate.setTimeInMillis(System.currentTimeMillis());

        final java.util.GregorianCalendar tempEnd = new java.util.GregorianCalendar();
        tempEnd.setTimeInMillis(tempBeginn.getTimeInMillis());
        tempEnd.add(java.util.Calendar.MONTH, 3);

        if (!tempEnd.before(endDate)) {
            tempEnd.setTimeInMillis(endDate.getTimeInMillis());
        }

        String strDateFirst = de.hattrickorganizer.tools.MyHelper.Calendar2HTString(tempBeginn);
        String strDateLast = de.hattrickorganizer.tools.MyHelper.Calendar2HTString(tempEnd);

        //Sicherstellen das HO! eingeloggt ist
        assureLogin();

        //falls immer noch nicht eingeloggt raus
        if (!MyConnector.instance().isAuthenticated()) {
            return false;
        }

        //Wairt Dialog zeigen
        waitDialog = new LoginWaitDialog(de.hattrickorganizer.gui.HOMainFrame.instance());
        waitDialog.setVisible(true);

        while (tempBeginn.before(endDate)) {
            try {
                waitDialog.setValue(10);
                matchASP = MyConnector.instance().getMatchArchiv(teamId, strDateFirst, strDateLast);
                waitDialog.setValue(20);
            } catch (Exception e) {
                //Info
                de.hattrickorganizer.gui.HOMainFrame.instance().getInfoPanel().setLangInfoText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                                                      .getResource()
                                                                                                                                      .getProperty("Downloadfehler")
                                                                                               + " : Error fetching MatchArchiv : "
                                                                                               + gui.UserParameter
                                                                                                 .instance().htip,
                                                                                               de.hattrickorganizer.gui.InfoPanel.FEHLERFARBE);
                de.hattrickorganizer.tools.Helper.showMessage(de.hattrickorganizer.gui.HOMainFrame
                                                              .instance(),
                                                              de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                     .getResource()
                                                                                                     .getProperty("Downloadfehler")
                                                              + " : Error fetching MatchArchiv : "
                                                              + gui.UserParameter.instance().htip,
                                                              de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                     .getResource()
                                                                                                     .getProperty("Fehler"),
                                                              JOptionPane.ERROR_MESSAGE);
                MyConnector.instance().setAuthenticated(false);
                waitDialog.setVisible(false);
                return false;
            }

            final xmlMatchArchivParser parser = new xmlMatchArchivParser();
            waitDialog.setValue(40);
            matches = parser.parseMatchesFromString(matchASP);

            //zu Vector adden
            de.hattrickorganizer.tools.MyHelper.addArray2Vector(matches, allMatches);

            //Zeitfenster neu setzen
            tempBeginn.add(java.util.Calendar.MONTH, 3);
            tempEnd.add(java.util.Calendar.MONTH, 3);

            if (!tempEnd.before(endDate)) {
                tempEnd.setTimeInMillis(endDate.getTimeInMillis());
            }

            strDateFirst = de.hattrickorganizer.tools.MyHelper.Calendar2HTString(tempBeginn);
            strDateLast = de.hattrickorganizer.tools.MyHelper.Calendar2HTString(tempEnd);
        }

        waitDialog.setValue(60);
        matches = new MatchKurzInfo[allMatches.size()];
        de.hattrickorganizer.tools.MyHelper.copyVector2Array(allMatches, matches);
        waitDialog.setValue(80);

        //Ab in die DB packen
        if (matches != null) {
            de.hattrickorganizer.database.DBZugriff.instance().storeMatchKurzInfos(matches);
        }

        waitDialog.setValue(100);
        waitDialog.setVisible(false);

        //Automatisch alle MatchLineups runterladen
        for (int i = 0; (matches != null) && (i < matches.length); i++) {
            //Match noch nicht in der DB
            if ((de.hattrickorganizer.database.DBZugriff.instance().isMatchVorhanden(matches[i]
                                                                                     .getMatchID()))
                && (!de.hattrickorganizer.database.DBZugriff.instance().isMatchLineupVorhanden(matches[i]
                                                                                               .getMatchID()))
                && (matches[i].getMatchStatus() == MatchKurzInfo.FINISHED)) {
                getMatchlineup(matches[i].getMatchID(), matches[i].getHeimID(),
                               matches[i].getGastID());
                getMatchDetails(matches[i].getMatchID());
                de.hattrickorganizer.logik.MatchUpdater.
                	updateMatch(de.hattrickorganizer.model.HOMiniModel
                			.instance(), matches[i].getMatchID());
            }
        }

        return true;
    }

    /**
     * DOCUMENT ME!
     *
     * @param matchId Die ID des Matches
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean getMatchDetails(int matchId) {
        boolean success = true;
        Matchdetails details = null;

        //Sicherstellen das HO! eingeloggt ist
        assureLogin();

        //falls immer noch nicht eingeloggt raus
        if (!MyConnector.instance().isAuthenticated()) {
            return false;
        }

        //Wait Dialog zeigen
        waitDialog = new LoginWaitDialog(de.hattrickorganizer.gui.HOMainFrame.instance(), false);
        waitDialog.setVisible(true);
        waitDialog.setValue(10);
        details = fetchDetails(matchId, waitDialog);

        //#############
        //details Ab in die DB packen
        if (details != null) {
            de.hattrickorganizer.database.DBZugriff.instance().storeMatchDetails(details);
        } else {
            success = false;
        }

        waitDialog.setValue(100);
        waitDialog.setVisible(false);

        return success;
    }

    ////////////////////////////////////////////////////////////////////////////////
    //MATCHES
    ////////////////////////////////////////////////////////////////////////////////

    /**
     * saugt den Spielplan
     *
     * @param teamId angabe der Saison ( optinal &lt; 1 für aktuelle
     * @param forceRefresh TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean getMatches(int teamId, boolean forceRefresh) {
        String matchASP = "";
        MatchKurzInfo[] matches = null;

        //Sicherstellen das HO! eingeloggt ist
        assureLogin();

        //falls immer noch nicht eingeloggt raus
        if (!MyConnector.instance().isAuthenticated()) {
            return false;
        }

        //Wairt Dialog zeigen
        waitDialog = new LoginWaitDialog(de.hattrickorganizer.gui.HOMainFrame.instance());
        waitDialog.setVisible(true);
        waitDialog.setValue(10);

        try {
            matchASP = MyConnector.instance().getMatchesASP(teamId, forceRefresh);
            waitDialog.setValue(50);
        } catch (Exception e) {
            //Info
            de.hattrickorganizer.gui.HOMainFrame.instance().getInfoPanel().setLangInfoText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                                                  .getResource()
                                                                                                                                  .getProperty("Downloadfehler")
                                                                                           + " : Error fetching MatchesASP : "
                                                                                           + gui.UserParameter
                                                                                             .instance().htip,
                                                                                           de.hattrickorganizer.gui.InfoPanel.FEHLERFARBE);
            de.hattrickorganizer.tools.Helper.showMessage(de.hattrickorganizer.gui.HOMainFrame
                                                          .instance(),
                                                          de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                 .getResource()
                                                                                                 .getProperty("Downloadfehler")
                                                          + " : Error fetching MatchesASP : "
                                                          + gui.UserParameter.instance().htip,
                                                          de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                 .getResource()
                                                                                                 .getProperty("Fehler"),
                                                          JOptionPane.ERROR_MESSAGE);
            MyConnector.instance().setAuthenticated(false);
            waitDialog.setVisible(false);
            return false;
        }

        final XMLMatchesParser parser = new XMLMatchesParser();
        waitDialog.setValue(70);
        matches = parser.parseMatchesFromString(matchASP);

        waitDialog.setValue(80);

        //Ab in die DB packen
        if (matches != null) {
            DBZugriff.instance().storeMatchKurzInfos(matches);
        }

        waitDialog.setValue(100);
        waitDialog.setVisible(false);

        //Automatisch alle MatchLineups runterladen
        for (int i = 0; (matches != null) && (i < matches.length); i++) {
        	int curMatchId = matches[i].getMatchID();
        	Matchdetails curDetails = DBZugriff.instance().getMatchDetails(curMatchId); 
            //Match noch nicht in der DB
        	
            if (DBZugriff.instance().isMatchVorhanden(curMatchId)
            		&& matches[i].getMatchStatus() == MatchKurzInfo.FINISHED
            		&& (!DBZugriff.instance().isMatchLineupVorhanden(curMatchId) ||
            				curDetails == null ||
            				curDetails.getMatchreport() == null ||
            				curDetails.getMatchreport().trim().length() == 0
            				)
                ) {
                boolean retLineup = getMatchlineup(curMatchId, matches[i].getHeimID(), matches[i].getGastID());
                boolean retDetails = getMatchDetails(curMatchId);
                HOLogger.instance().debug(getClass(), "Match " + curMatchId + ", getMatchLineup(): "+retLineup+", getMatchDetails(): "+retDetails);
                de.hattrickorganizer.logik.MatchUpdater.updateMatch(
                		de.hattrickorganizer.model.HOMiniModel.instance(),
                		matches[i].getMatchID());
            }
        }

        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////
    //MATCHLINEUP
    ////////////////////////////////////////////////////////////////////////////////

    /**
     * saugt das Matchlineup
     *
     * @param matchId Die ID des Matches
     * @param teamId1 Erste  Teamid (pflicht)
     * @param teamId2 Zweite Teamid (optional auch -1)
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean getMatchlineup(int matchId, int teamId1, int teamId2) {
        MatchLineup lineUp1 = null;
        MatchLineup lineUp2 = null;

        //Sicherstellen das HO! eingeloggt ist
        assureLogin();

        //falls immer noch nicht eingeloggt raus
        if (!MyConnector.instance().isAuthenticated()) {
            return false;
        }

        //Wait Dialog zeigen
        waitDialog = new LoginWaitDialog(de.hattrickorganizer.gui.HOMainFrame.instance());
        waitDialog.setVisible(true);
        waitDialog.setValue(10);

        //Lineups holen
        lineUp1 = fetchLineup(matchId, teamId1);
        waitDialog.setValue(50);

        if (teamId2 > 0) {
            lineUp2 = fetchLineup(matchId, teamId2);
        }

        //mergen der beiden
        if ((lineUp2 != null) && (lineUp1 != null)) {
            if (lineUp1.getHeim() == null) {
                lineUp1.setHeim((de.hattrickorganizer.model.matches.MatchLineupTeam) lineUp2
                                .getHeim());
            } else if (lineUp1.getGast() == null) {
                lineUp1.setGast((de.hattrickorganizer.model.matches.MatchLineupTeam) lineUp2
                                .getGast());
            }
        } else if (lineUp1 != null) {
            //lineup 2 saugen
            if (lineUp1.getHeim() == null) {
                lineUp2 = fetchLineup(matchId, lineUp1.getHeimId());

                if (lineUp2 != null) {
                    lineUp1.setHeim((de.hattrickorganizer.model.matches.MatchLineupTeam) lineUp2
                                    .getHeim());
                }
            } else {
                lineUp2 = fetchLineup(matchId, lineUp1.getGastId());

                if (lineUp2 != null) {
                    lineUp1.setGast((de.hattrickorganizer.model.matches.MatchLineupTeam) lineUp2
                                    .getGast());
                }
            }
        }

        //lineup1 Ab in die DB packen
        if (lineUp1 != null) {
            DBZugriff.instance().storeMatchLineup(lineUp1);
        }

        waitDialog.setVisible(false);

        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////
    //Spielplan
    ////////////////////////////////////////////////////////////////////////////////

    /**
     * saugt den Spielplan
     *
     * @param season angabe der Saison ( optinal &lt; 1 für aktuelle
     * @param ligaID TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean getSpielplan(int season, int ligaID) {
        String leagueFixtures = "";

        //Sicherstellen das HO! eingeloggt ist
        assureLogin();

        //falls immer noch nicht eingeloggt raus
        if (!MyConnector.instance().isAuthenticated()) {
            return false;
        }

        //Wairt Dialog zeigen
        waitDialog = new LoginWaitDialog(de.hattrickorganizer.gui.HOMainFrame.instance(), false);
        waitDialog.setVisible(true);

        try {
            waitDialog.setValue(10);
            leagueFixtures = MyConnector.instance().getLeagueFixtures(season, ligaID);
            waitDialog.setValue(50);
        } catch (Exception e) {
            //Info
            HOLogger.instance().log(getClass(),e);
            de.hattrickorganizer.gui.HOMainFrame.instance().getInfoPanel().setLangInfoText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                                                  .getResource()
                                                                                                                                  .getProperty("Downloadfehler")
                                                                                           + " : Error fetching leagueFixture xml. Corrupt/Missing Data :"
                                                                                           + gui.UserParameter
                                                                                             .instance().htip,
                                                                                           de.hattrickorganizer.gui.InfoPanel.FEHLERFARBE);
            de.hattrickorganizer.tools.Helper.showMessage(de.hattrickorganizer.gui.HOMainFrame
                                                          .instance(),
                                                          de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                 .getResource()
                                                                                                 .getProperty("Downloadfehler")
                                                          + " : Error fetching leagueFixture xml. Corrupt/Missing Data :"
                                                          + gui.UserParameter.instance().htip,
                                                          de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                 .getResource()
                                                                                                 .getProperty("Fehler"),
                                                          JOptionPane.ERROR_MESSAGE);
            MyConnector.instance().setAuthenticated(false);
            waitDialog.setVisible(false);
            return false;
        }

        final XMLSpielplanParser parser = new XMLSpielplanParser();

        //Ins Modell packen
        de.hattrickorganizer.model.HOVerwaltung.instance().getModel().setSpielplan(parser
                                                                                   .parseSpielplanFromString(leagueFixtures));
        waitDialog.setValue(70);

        //in DB saven
        de.hattrickorganizer.model.HOVerwaltung.instance().getModel().saveSpielplan2DB();
        waitDialog.setValue(90);

        //                }
        //            }
        //}
        waitDialog.setVisible(false);

        return true;
    }

    /**
     * generiert ein String Datum
     *
     * @return TODO Missing Return Method Documentation
     */
    protected final String getDate() {
        final java.util.GregorianCalendar calendar = (java.util.GregorianCalendar) java.util.GregorianCalendar
                                                     .getInstance();
        String month = ((calendar.get(java.util.GregorianCalendar.MONTH)) + 1) + "";

        if (month.length() < 2) {
            month = "0" + month;
        }

        String day = calendar.get(java.util.GregorianCalendar.DAY_OF_MONTH) + "";

        if (day.length() < 2) {
            day = "0" + day;
        }

        return calendar.get(java.util.GregorianCalendar.YEAR) + "-" + month + "-" + day;
    }

    ////////////////////////////////////////////////////////////////////////////////
    //LOGIN
    ////////////////////////////////////////////////////////////////////////////////
    protected final void assureLogin() {
        if (!MyConnector.instance().isAuthenticated()) {
            final LoginDialog ld = new LoginDialog(de.hattrickorganizer.gui.HOMainFrame.instance());
            ld.setVisible(true);
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param matchID TODO Missing Method Parameter Documentation
     * @param waitDialog TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected final Matchdetails fetchDetails(int matchID, LoginWaitDialog waitDialog) {
        String matchDetails = "";
        Matchdetails details = null;

        try {
            matchDetails = MyConnector.instance().getMatchdetails(matchID);
            waitDialog.setValue(20);

            final xmlMatchdetailsParser parser = new xmlMatchdetailsParser();
            
            details = parser.parseMachtdetailsFromString(matchDetails);
            waitDialog.setValue(40);
            if (details == null) {
            	HOLogger.instance().warning(getClass(), "Unable to fetch details for match " + matchID);            	
            	return null;
            }
            String arenaString = MyConnector.instance().getArena(details.getArenaID());
            waitDialog.setValue(50);
            String regionIdAsString = (String)new XMLArenaParser().parseArenaFromString(arenaString).get("RegionID");
            int regionId = Integer.parseInt(regionIdAsString);
            
            details.setRegionId(regionId);
        } catch (Exception e) {
            //Info
            de.hattrickorganizer.gui.HOMainFrame.instance().getInfoPanel().setLangInfoText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                                                  .getResource()
                                                                                                                                  .getProperty("Downloadfehler")
                                                                                           + ": Error fetching Matchdetails XML.: "
                                                                                           + gui.UserParameter
                                                                                             .instance().htip,
                                                                                           de.hattrickorganizer.gui.InfoPanel.FEHLERFARBE);
            de.hattrickorganizer.tools.Helper.showMessage(de.hattrickorganizer.gui.HOMainFrame
                                                          .instance(),
                                                          de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                 .getResource()
                                                                                                 .getProperty("Downloadfehler")
                                                          + ": Error fetching Matchdetails XML.: "
                                                          + gui.UserParameter.instance().htip,
                                                          de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                 .getResource()
                                                                                                 .getProperty("Fehler"),
                                                          JOptionPane.ERROR_MESSAGE);
            MyConnector.instance().setAuthenticated(false);
            waitDialog.setVisible(false);
            return null;
        }

        return details;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param matchID TODO Missing Method Parameter Documentation
     * @param teamID TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected final MatchLineup fetchLineup(int matchID, int teamID) {
        String matchLineup = "";
        MatchLineup lineUp = null;

        try {
            matchLineup = MyConnector.instance().getMatchLineup(matchID, teamID);
        } catch (Exception e) {
            //Info
            de.hattrickorganizer.gui.HOMainFrame.instance().getInfoPanel().setLangInfoText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                                                  .getResource()
                                                                                                                                  .getProperty("Downloadfehler")
                                                                                           + " : Error fetching Matchlineup :"
                                                                                           + gui.UserParameter
                                                                                             .instance().htip,
                                                                                           de.hattrickorganizer.gui.InfoPanel.FEHLERFARBE);
            de.hattrickorganizer.tools.Helper.showMessage(de.hattrickorganizer.gui.HOMainFrame
                                                          .instance(),
                                                          de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                 .getResource()
                                                                                                 .getProperty("Downloadfehler")
                                                          + " : Error fetching Matchlineup :"
                                                          + gui.UserParameter.instance().htip,
                                                          de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                 .getResource()
                                                                                                 .getProperty("Fehler"),
                                                          JOptionPane.ERROR_MESSAGE);
            MyConnector.instance().setAuthenticated(false);
            waitDialog.setVisible(false);
            return null;
        }

        final XMLMatchLineupParser parser = new XMLMatchLineupParser();

        lineUp = parser.parseMatchLineupFromString(matchLineup);

        return lineUp;
    }

    ////////////////////////////////////////////////////////////////////////////////
    //Helper
    ////////////////////////////////////////////////////////////////////////////////

    /**
     * speichert in die Angegebene Datei den übergebenen Inhalt
     *
     * @param dateiname TODO Missing Constructuor Parameter Documentation
     * @param inhalt TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected final java.io.File saveFile(String dateiname, String inhalt) {
        //utf-8
        java.io.OutputStreamWriter outWrit = null;
        java.io.File datei = null;
        java.io.BufferedWriter out = null;

        try {
            datei = new java.io.File(dateiname);

            if (datei.exists()) {
                datei.delete();
            }

            datei.createNewFile();

            //utf 8 schreiben
            outWrit = new java.io.OutputStreamWriter(new java.io.FileOutputStream(datei), "UTF-8");
            out = new java.io.BufferedWriter(outWrit);

            //ansi schreiben
            //            out =   new java.io.BufferedWriter ( new java.io.FileWriter ( datei ) );
            out.write(inhalt);
            out.newLine();
            out.close();
        } catch (Exception e) {
        }

        return datei;
    }

	/**
	 * Holt für alle Kurzinfos die Lineups, wenn diese noch nicht vorhanden sind
	 */
	public void getAllLineups() {
		final MatchKurzInfo[] infos = DBZugriff.instance()
																			 .getMatchesKurzInfo(-1);
		String haveLineups = "";
		
		for (int i = 0; i < infos.length; i++) {
			int curMatchId = infos[i].getMatchID();
			if (!DBZugriff.instance().isMatchLineupVorhanden(curMatchId)) {
				//Prüfen, ob Lineup schon gezogen werden kann!
				if (infos[i].getMatchDateAsTimestamp().before(new java.sql.Timestamp(System
																					 .currentTimeMillis()))) {
					HOLogger.instance().log(getClass(),"Get Lineup : " + curMatchId);

					//Nur weiter, wenn das Lineup gezogen wurde
					if (de.hattrickorganizer.gui.HOMainFrame.instance().getOnlineWorker()
															.getMatchlineup(curMatchId,
																			infos[i].getHeimID(),
																			infos[i].getGastID())) {
						de.hattrickorganizer.gui.HOMainFrame.instance().getOnlineWorker()
															.getMatchDetails(curMatchId);
					}
				} else {
					HOLogger.instance().log(getClass(),"Not Played : " + curMatchId);
				}
			} else {
				// Match lineup already available
				if (haveLineups.length() > 0)
					haveLineups += ", ";
				haveLineups += curMatchId;
			}
		}
		if (haveLineups.length() > 0)
			HOLogger.instance().log(getClass(),"Have Lineups : " + haveLineups);
	}

    /////////////////////////////////////////////////////////////////////////////////
    //TEST
    /////////////////////////////////////////////////////////////////////////////////
}
