// %1507739964:de.hattrickorganizer.gui.utils%
/*
 * OnlineWorker.java
 *
 * Created on 10. Oktober 2003, 07:53
 */
package ho.core.net;

import ho.core.db.DBManager;
import ho.core.file.ExampleFileFilter;
import ho.core.file.extension.FileExtensionManager;
import ho.core.file.hrf.HRFStringParser;
import ho.core.file.xml.ConvertXml2Hrf;
import ho.core.file.xml.XMLArenaParser;
import ho.core.file.xml.XMLMatchLineupParser;
import ho.core.file.xml.XMLMatchesParser;
import ho.core.file.xml.XMLSpielplanParser;
import ho.core.file.xml.xmlMatchArchivParser;
import ho.core.file.xml.xmlMatchdetailsParser;
import ho.core.gui.HOMainFrame;
import ho.core.gui.InfoPanel;
import ho.core.gui.model.AufstellungCBItem;
import ho.core.model.HOModel;
import ho.core.model.HOVerwaltung;
import ho.core.model.UserParameter;
import ho.core.model.match.MatchKurzInfo;
import ho.core.model.match.MatchLineup;
import ho.core.model.match.MatchLineupTeam;
import ho.core.model.match.Matchdetails;
import ho.core.model.player.ISpielerPosition;
import ho.core.model.player.Spieler;
import ho.core.net.login.LoginWaitDialog;
import ho.core.training.TrainingsManager;
import ho.core.util.HOLogger;
import ho.core.util.Helper;
import ho.module.lineup.AufstellungsVergleichHistoryPanel;
import ho.module.lineup.substitution.ISubstitution;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class OnlineWorker {
    //~ Static fields/initializers -----------------------------------------------------------------

	private final static SimpleDateFormat HT_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
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
     * Get and optionally save HRF
     *
     * @return True if all OK, false if something went wrong
     */
    public final boolean getHrf() 
    {
        String hrf = "";
        boolean bOK = false;
        int value;
        HOMainFrame homf = HOMainFrame.instance();
        HOVerwaltung hov = HOVerwaltung.instance();
        UserParameter up = ho.core.model.UserParameter.instance();
        InfoPanel info = homf.getInfoPanel();
        HOModel homodel = null;
        // Show wait dialog
        waitDialog = new LoginWaitDialog(homf, false);
        waitDialog.setVisible(true);
        try 
        {
            hrf = new ConvertXml2Hrf().createHrf(waitDialog);
            bOK = true;
        } 
        catch (Exception e) {
            //Info
            info.setLangInfoText(hov.getLanguageString("Downloadfehler") 
            		+ " : Error converting xml 2 HRF. Corrupt/Missing Data : ", 
            		InfoPanel.FEHLERFARBE);
            Helper.showMessage(homf, hov.getLanguageString("Downloadfehler")
                               + " : Error converting xml 2 HRF. Corrupt/Missing Data : \n"
                               + e.toString() + "\n",
                               hov.getLanguageString("Fehler"),
                               JOptionPane.ERROR_MESSAGE);
            bOK = false;
        }
        if (bOK)
        {
	        if (hrf.indexOf("playingMatch=true") > -1) {
	            JOptionPane.showMessageDialog(homf, hov.getLanguageString("NO_HRF_Spiel"), hov.getLanguageString("NO_HRF_ERROR"), 1);
	            bOK = false;
	        } else if (hrf.indexOf("NOT AVAILABLE") > -1) {
	            JOptionPane.showMessageDialog(homf, hov.getLanguageString("NO_HRF_ERROR"), hov.getLanguageString("NO_HRF_ERROR"), 1);
	            bOK = false;
	        }
	        if(bOK)
	        {
	        	// Create HOModelo from the hrf data
	    		homodel = new HRFStringParser().parse(hrf);
	    		if (homodel == null) 
	    		{
	    			//Info
	    			info.setLangInfoText(hov.getLanguageString("Importfehler"), InfoPanel.FEHLERFARBE);
	    			//Error
	    			Helper.showMessage(homf, hov.getLanguageString("Importfehler"),
	                                   hov.getLanguageString("Fehler"), JOptionPane.ERROR_MESSAGE);
	    		} 
	    		else 
	    		{
	    			homodel.saveHRF();
	    			homodel.setSpielplan(hov.getModel().getSpielplan());
	
    				// Add old players to the model
    				homodel.setAllOldSpieler(DBManager.instance().getAllSpieler());
    				// Only update when the model is newer than existing
    				if ((homodel != null) && ((hov.getModel() == null)
    						|| (homodel.getBasics().getDatum().after(hov.getModel().getBasics().getDatum())))) 
    				{
    					Date lastTrainingDate = Calendar.getInstance().getTime();
    					Date lastEconomyDate = lastTrainingDate;
    					if (hov.getModel().getXtraDaten().getTrainingDate() != null) 
    					{
    						lastTrainingDate = new Date(hov.getModel().getXtraDaten().getTrainingDate().getTime());
    						lastEconomyDate = new Date(hov.getModel().getXtraDaten().getEconomyDate().getTime());                            	
    					} 
    					// Reimport Skillup
    					DBManager.instance().checkSkillup(homodel);
    					//Show
    					hov.setModel(homodel);
    					//Recalculate Training
    					//Training->Subskill calculation
    					TrainingsManager.instance().calculateTrainings(DBManager.instance().getTrainingsVector());
    					homodel.calcSubskills();
    					AufstellungsVergleichHistoryPanel.setHRFAufstellung(homodel.getAufstellung(), homodel.getLastAufstellung());
    					AufstellungsVergleichHistoryPanel.setAngezeigteAufstellung(
    							new AufstellungCBItem(hov.getLanguageString("AktuelleAufstellung"), homodel.getAufstellung()));
    					homf.getAufstellungsPanel().getAufstellungsPositionsPanel().exportOldLineup("Actual");
    					FileExtensionManager.extractLineup("Actual");
												
    					// If training update happened, regenerate HOE Files
    					if (homodel.getXtraDaten().getTrainingDate().after(lastTrainingDate)) 
    					{
    						HOLogger.instance().log(getClass(),"Regenerate HOE Training Files");
    						FileExtensionManager.trainingUpdate();	
    					}
    					// If economy update happened, regenerate HOE Files
    					if (homodel.getXtraDaten().getEconomyDate().after(lastEconomyDate)) 
    					{
    						HOLogger.instance().log(getClass(),"Regenerate HOE Economy Files");
    						FileExtensionManager.economyUpate();	
    					}
    				}
    				//Info
    				info.setLangInfoText(HOVerwaltung.instance().getLanguageString("HRFErfolg"));
		        	info.setLangInfoText(hov.getLanguageString("HRFSave"));
	    			final GregorianCalendar calendar = (GregorianCalendar) Calendar.getInstance();
					String month = ((calendar.get(Calendar.MONTH)) + 1) + "";
					if (month.length() < 2) 
						month = "0" + month;
					String day = calendar.get(Calendar.DAY_OF_MONTH) + "";
					if (day.length() < 2)
						day = "0" + day;
					final String name = calendar.get(Calendar.YEAR) + "-" + month + "-" + day + ".hrf";
					final File pfad = new File(up.hrfImport_HRFPath);
					File file = new File(up.hrfImport_HRFPath + java.io.File.separator + name);
					// Show dialog if path not set or the file already exists
					if (up.showHRFSaveDialog || !pfad.exists() || !pfad.isDirectory() || file.exists()) 
					{
					    final JFileChooser fileChooser = new JFileChooser();
					    fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
					    fileChooser.setDialogTitle(hov.getLanguageString("FileExport"));
					    final ExampleFileFilter filter = new ExampleFileFilter();
					    filter.addExtension("hrf");
					    filter.setDescription("Hattrick HRF");
					    fileChooser.setFileFilter(filter);
					    try 
					    {
					        if (pfad.exists() && pfad.isDirectory()) 
					            fileChooser.setCurrentDirectory(new File(up.hrfImport_HRFPath));
					    } 
					    catch (Exception e) {}
					    fileChooser.setSelectedFile(file);
					    final int returnVal = fileChooser.showSaveDialog(homf);
					    if (returnVal == JFileChooser.APPROVE_OPTION) 
					        file = fileChooser.getSelectedFile();
					    else 
					        file = null;
					}
					
					if ((file != null) && (file.getPath() != null)) 
					{
					    
					    // File doesn't end with .hrf?
					    if (!file.getPath().endsWith(".hrf")) 
					        file = new java.io.File(file.getAbsolutePath() + ".hrf");
					    // File exists?
					    if (file.exists()) 
					        value = JOptionPane.showConfirmDialog(homf, hov.getLanguageString("overwrite"), "", JOptionPane.YES_NO_OPTION);
					    else 
					        value = JOptionPane.OK_OPTION;
					
					    // Save Path
					    up.hrfImport_HRFPath = file.getParentFile().getAbsolutePath();
					    // Save
					    if (value == JOptionPane.OK_OPTION) 
					    {
					    	
					    	File datei = null;
					    	try
					    	{
					    		 datei = saveFile(file.getPath(), hrf);
					    		 bOK = true;
					    	}
					    	catch (Exception e)
					    	{
					    		Helper.showMessage(homf, "Failed to save downloaded file.\nError: " + e.getMessage(),
					                    hov.getLanguageString("Fehler"), JOptionPane.ERROR_MESSAGE);
					    	}
					    }
					 // Canceled
		    			else 
		    				info.setLangInfoText(HOVerwaltung.instance().getLanguageString("HRFAbbruch"), InfoPanel.FEHLERFARBE);
					}
	    		}
			}
        }
        waitDialog.setVisible(false);
        return bOK;
    }

    /**
     * saugt das Archiv
     *
     * @param teamId null falls unnötig sonst im Format 2004-02-01
     * @param firstDate null falls unnötig sonst im Format 2004-02-01
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean getMatchArchive(int teamId, GregorianCalendar firstDate) {
        String matchesString = "";
        MatchKurzInfo[] matches = null;
        final Vector<MatchKurzInfo> allMatches = new Vector<MatchKurzInfo>();
        final GregorianCalendar tempBeginn = firstDate;
        final GregorianCalendar endDate = new GregorianCalendar();
        endDate.setTimeInMillis(System.currentTimeMillis());

        final GregorianCalendar tempEnd = new GregorianCalendar();
        tempEnd.setTimeInMillis(tempBeginn.getTimeInMillis());
        tempEnd.add(Calendar.MONTH, 3);

        if (!tempEnd.before(endDate)) {
            tempEnd.setTimeInMillis(endDate.getTimeInMillis());
        }

        String strDateFirst = HT_FORMAT.format(tempBeginn.getTime());
        String strDateLast = HT_FORMAT.format(tempEnd.getTime());

        // Show wait Dialog 
        waitDialog = new LoginWaitDialog(HOMainFrame.instance());
        waitDialog.setVisible(true);

        while (tempBeginn.before(endDate)) {
            try {
                waitDialog.setValue(10);
                matchesString = MyConnector.instance().getMatchArchiv(teamId, strDateFirst, strDateLast);
                waitDialog.setValue(20);
            } catch (Exception e) {
                //Info
                HOMainFrame.instance().getInfoPanel().setLangInfoText(HOVerwaltung.instance().getLanguageString("Downloadfehler")
                                                                                               + " : Error fetching MatchArchiv : "
                                                                                               , InfoPanel.FEHLERFARBE);
                Helper.showMessage(HOMainFrame.instance(),
                		HOVerwaltung.instance().getLanguageString("Downloadfehler")
                                                              + " : Error fetching MatchArchiv : "
                                                              ,HOVerwaltung.instance().getLanguageString("Fehler"),
                                                              JOptionPane.ERROR_MESSAGE);
                waitDialog.setVisible(false);
                return false;
            }

            final xmlMatchArchivParser parser = new xmlMatchArchivParser();
            waitDialog.setValue(40);
            matches = parser.parseMatchesFromString(matchesString);

            //zu Vector adden
            for (int i = 0; i < matches.length; i++) 
				allMatches.add(matches[i]);

            //Zeitfenster neu setzen
            tempBeginn.add(Calendar.MONTH, 3);
            tempEnd.add(Calendar.MONTH, 3);

            if (!tempEnd.before(endDate)) {
                tempEnd.setTimeInMillis(endDate.getTimeInMillis());
            }

            strDateFirst = HT_FORMAT.format(tempBeginn.getTime());
            strDateLast = HT_FORMAT.format(tempEnd.getTime());
        }

        waitDialog.setValue(60);
        matches = new MatchKurzInfo[allMatches.size()];
        Helper.copyVector2Array(allMatches, matches);
        waitDialog.setValue(80);

        //Ab in die DB packen
        if (matches != null)
            DBManager.instance().storeMatchKurzInfos(matches);

        waitDialog.setValue(100);
        waitDialog.setVisible(false);

        //Automatisch alle MatchLineups runterladen
        for (int i = 0; (matches != null) && (i < matches.length); i++) {
            //Match noch nicht in der DB
            if ((DBManager.instance().isMatchVorhanden(matches[i].getMatchID()))
                && (!DBManager.instance().isMatchLineupVorhanden(matches[i].getMatchID()))
                && (matches[i].getMatchStatus() == MatchKurzInfo.FINISHED)) {
                getMatchlineup(matches[i].getMatchID(), matches[i].getHeimID(),
                               matches[i].getGastID());
                if (getMatchDetails(matches[i].getMatchID())) {
                	DBManager.instance().updateMatch(matches[i].getMatchID());
                }
                else
                	return false;
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

        //Wait Dialog zeigen
        waitDialog = new LoginWaitDialog(HOMainFrame.instance(), false);
        waitDialog.setVisible(true);
        waitDialog.setValue(10);
        details = fetchDetails(matchId, waitDialog);

        if (details != null) {
            DBManager.instance().storeMatchDetails(details);
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
        String matchesString = "";
        MatchKurzInfo[] matches = null;
        boolean bOK = false;
        waitDialog = new LoginWaitDialog(HOMainFrame.instance());
        waitDialog.setVisible(true);
        waitDialog.setValue(10);

        try {
            matchesString = MyConnector.instance().getMatches(teamId, forceRefresh);
            bOK = (matchesString != null && matchesString.length() > 0);
            if (bOK)
            	waitDialog.setValue(50);
            else
            	waitDialog.setVisible(false);
        } catch (Exception e) {
            //Info
			HOMainFrame.instance().getInfoPanel().setLangInfoText(
					HOVerwaltung.instance().getLanguageString("Downloadfehler") + " : Error fetching matches: " + e,
					InfoPanel.FEHLERFARBE);
            Helper.showMessage(HOMainFrame.instance(), HOVerwaltung.instance().getLanguageString("Downloadfehler")
            		+ " : Error fetching matches : " + e,
            		HOVerwaltung.instance().getLanguageString("Fehler"), JOptionPane.ERROR_MESSAGE);
            HOLogger.instance().log(getClass(), e);
            waitDialog.setVisible(false);
            return false;
        }
        if (bOK)
        {
	        final XMLMatchesParser parser = new XMLMatchesParser();
	        waitDialog.setValue(70);
	        matches = parser.parseMatchesFromString(matchesString);
	
	        waitDialog.setValue(80);
	
	        // Store in DB
	        if (matches != null) {
	            DBManager.instance().storeMatchKurzInfos(matches);
	        }
	
	        waitDialog.setValue(100);
	        waitDialog.setVisible(false);
	
	        // Automatically download all MatchLineups
	        for (int i = 0; (matches != null) && (i < matches.length); i++) {
	        	int curMatchId = matches[i].getMatchID();
	        	Matchdetails curDetails = DBManager.instance().getMatchDetails(curMatchId); 
	            // No match in DB
	        	if (DBManager.instance().isMatchVorhanden(curMatchId)
	            		&& matches[i].getMatchStatus() == MatchKurzInfo.FINISHED
	            		&& (!DBManager.instance().isMatchLineupVorhanden(curMatchId) ||
	            				curDetails == null ||
	            				curDetails.getMatchreport() == null ||
	            				curDetails.getMatchreport().trim().length() == 0
	            				))
	        	{
	                boolean retLineup = getMatchlineup(curMatchId, matches[i].getHeimID(), matches[i].getGastID());
	                boolean retDetails = getMatchDetails(curMatchId);
	                if (retDetails) {
	                	HOLogger.instance().debug(getClass(), "Match " + curMatchId + ", getMatchLineup(): "+retLineup+", getMatchDetails(): "+retDetails);
	                	DBManager.instance().updateMatch(matches[i].getMatchID());
	                }
	                else {
	                	bOK = false;
	                	break;
	                }
	            }
	        }
        }
        return bOK;
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
    	boolean bOK = false;
        MatchLineup lineUp1 = null;
        MatchLineup lineUp2 = null;

        //Wait Dialog zeigen
        waitDialog = new LoginWaitDialog(HOMainFrame.instance());
        waitDialog.setVisible(true);
        waitDialog.setValue(10);

        //Lineups holen
        lineUp1 = fetchLineup(matchId, teamId1);
        if (lineUp1 != null)
        {
        	bOK = true;
	        waitDialog.setValue(50);
	        if (teamId2 > 0)
	            lineUp2 = fetchLineup(matchId, teamId2);
	
	        // Merge the two
	        if ((lineUp2 != null)) 
	        {
	            if (lineUp1.getHeim() == null) 
	                lineUp1.setHeim((MatchLineupTeam) lineUp2.getHeim());
	            else if (lineUp1.getGast() == null)
	                lineUp1.setGast((MatchLineupTeam) lineUp2.getGast());
	        } 
	        else 
	        {
	            // Get the 2nd lineup
	            if (lineUp1.getHeim() == null) 
	            {
	                lineUp2 = fetchLineup(matchId, lineUp1.getHeimId());
	                if (lineUp2 != null)
	                    lineUp1.setHeim((MatchLineupTeam)lineUp2.getHeim());
	            } 
	            else 
	            {
	                lineUp2 = fetchLineup(matchId, lineUp1.getGastId());
	                if (lineUp2 != null) 
	                    lineUp1.setGast((MatchLineupTeam)lineUp2.getGast());
	            }
	        }
	        DBManager.instance().storeMatchLineup(lineUp1);
        }
        waitDialog.setVisible(false);
        return bOK;
    }

    ////////////////////////////////////////////////////////////////////////////////
    // Fixtures
    ////////////////////////////////////////////////////////////////////////////////

    /**
     * Get the Fixtures list
     *
     * @param season - The season, -1 for current
     * @param leagueID - The ID of the league to get the fixtures for
     *
     * @return true on sucess, false on failure
     */
    public final boolean getSpielplan(int season, int leagueID) {
    	boolean bOK = false;
        String leagueFixtures = "";
        HOVerwaltung hov = HOVerwaltung.instance();
        waitDialog = new LoginWaitDialog(HOMainFrame.instance(), false);
        waitDialog.setVisible(true);
        try 
        {
            waitDialog.setValue(10);
            leagueFixtures = MyConnector.instance().getLeagueFixtures(season, leagueID);
            bOK = (leagueFixtures != null && leagueFixtures.length() > 0);
            waitDialog.setValue(50);
        }
        catch (Exception e) 
        {
            HOLogger.instance().log(getClass(),e);
            HOMainFrame.instance().getInfoPanel().setLangInfoText(
            		hov.getLanguageString("Downloadfehler")
            		+ " : Error fetching leagueFixture: " + e.getMessage(),
            		InfoPanel.FEHLERFARBE);
            Helper.showMessage(HOMainFrame.instance(),
            		hov.getLanguageString("Downloadfehler")
                    + " : Error fetching leagueFixture: " + e.getMessage(),
                    hov.getLanguageString("Fehler"),
                    JOptionPane.ERROR_MESSAGE);
            waitDialog.setVisible(false);
            return false;
        }
        if (bOK)
        {
        	HOModel hom = hov.getModel();
	        final XMLSpielplanParser parser = new XMLSpielplanParser();
	        hom.setSpielplan(parser.parseSpielplanFromString(leagueFixtures));
	        waitDialog.setValue(70);
	        //Save to DB
	        hom.saveSpielplan2DB();
	        waitDialog.setValue(90);
        }
        waitDialog.setVisible(false);
        return bOK;
    }
    
    /**
     * Uploads the given lineup to Hattrick
     * 
     * @param matchId The id of the match in question. If left at 0 the match ID from the model will be used (next match).
     * @param lineup The lineup object to be uploaded
     * @param clearOrders If true will submit 5 empty match orders
     * @param setPenShooters If true will submit the shooters as suggested by the lineup tool
     *
     * @return true if success, false if something failed
     */
    
    public final String setMatchOrder(int matchId, ho.module.lineup.Lineup lineup) {
    	
    	String result;
    	// Tell the Connector that we will require match order rights.
    	
    	// Building the order string as described in the match order API piece by piece.
    	
    	String orders = "{\"positions\":[" + createPositionString(ISpielerPosition.keeper, lineup);
    	
    	
    	for (int i = ISpielerPosition.rightBack ; i <= ISpielerPosition.substForward ; i++) {
    		orders += "," + createPositionString(i, lineup);
    	}
    	
    	orders += "," + "{\"id\":\"" + lineup.getKapitaen() + "\",\"behaviour\":\"0\"}";
    	orders += "," + "{\"id\":\"" + lineup.getKicker() + "\",\"behaviour\":\"0\"}";
    	
    	// Some better source wanted...
    	int[] shooters = lineup.getBestElferKicker();
        
    	for (int i = 0 ; i < shooters.length ; i++) {
    		orders += "," + "{\"id\":\"" + shooters[i] + "\" , \"behaviour\":\"0\"}";
    	}


    	orders += "], \"settings\":{\"tactic\": \"" + lineup.getTacticType() + "\","
    	+ "\"speechLevel\":\"" + lineup.getAttitude() + "\", \"newLineup\":\"\"},";


    	orders += "\"substitutions\":[";
    	
    	Iterator<ISubstitution> iter = lineup.getSubstitutionList().iterator();
    	while (iter.hasNext()) {
    		ISubstitution sub = iter.next();
    		orders+= "{\"playerin\":\"" + sub.getPlayerIn() + "\","  
    		+ "\"playerout\":\"" + sub.getPlayerOut() + "\","
    		+ "\"orderType\":\"" + sub.getOrderType().getId() + "\","
    		+ "\"min\":\"" + sub.getMatchMinuteCriteria() + "\","
    		+ "\"pos\":\"" + sub.getPos() + "\","
    		+ "\"beh\":\"" + sub.getBehaviour() + "\","
    		+ "\"card\":\"" + sub.getCard() + "\","
    		+ "\"standing\":\"" + sub.getStanding() + "\"}";
    		if (iter.hasNext()) {
    			orders+= ",";
    		}
    	}
    	orders += "]}";

    	try {
    		result = uploadLineup(matchId, orders);
    	} catch (Exception e) {
    		result = "";
    	}
    	
    	HOLogger.instance().debug(getClass(), "Upload done:\n" + result);
    	
    	return result;
    }
    
    private String createPositionString(int roleId, ho.module.lineup.Lineup lineup) {
    	
    	int id = 0;
    	int behaviour = 0;
    	
    	Spieler spieler = lineup.getPlayerByPositionID(roleId);
    	if (spieler != null) {
    		id = spieler.getSpielerID();
    		behaviour = lineup.getTactic4PositionID(roleId);
    	}
    	
    	return "{\"id\":\"" + id + "\",\"behaviour\":\"" + behaviour + "\"}";
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
            if (matchDetails.length() == 0) {
            	HOLogger.instance().warning(getClass(), "Unable to fetch details for match " + matchID); 
            	return null;
            }
            waitDialog.setValue(20);
            final xmlMatchdetailsParser parser = new xmlMatchdetailsParser();
            details = parser.parseMachtdetailsFromString(matchDetails);
            waitDialog.setValue(40);
            if (details == null) 
            {
            	HOLogger.instance().warning(getClass(), "Unable to fetch details for match " + matchID);            	
            	return null;
            }
            String arenaString = MyConnector.instance().getArena(details.getArenaID());
            waitDialog.setValue(50);
            String regionIdAsString = (String)new XMLArenaParser().parseArenaFromString(arenaString).get("RegionID");
            int regionId = Integer.parseInt(regionIdAsString);
            details.setRegionId(regionId);
        } 
        catch (Exception e) 
        {
            //Info
            HOMainFrame.instance().getInfoPanel().setLangInfoText(
            		HOVerwaltung.instance().getLanguageString("Downloadfehler")
            		+ ": Error fetching Matchdetails XML.: ", InfoPanel.FEHLERFARBE);
            Helper.showMessage(HOMainFrame.instance(),
            		HOVerwaltung.instance().getLanguageString("Downloadfehler")
                    + ": Error fetching Matchdetails XML.: ",
                    HOVerwaltung.instance().getLanguageString("Fehler"),
                    JOptionPane.ERROR_MESSAGE);
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
        boolean bOK = false;
        try 
        {
            matchLineup = MyConnector.instance().getMatchLineup(matchID, teamID);
            bOK = (matchLineup != null && matchLineup.length() > 0);
        } 
        catch (Exception e) 
        {
            //Info
            HOMainFrame.instance().getInfoPanel().
            	setLangInfoText(HOVerwaltung.instance().getLanguageString("Downloadfehler")
            			+ " : Error fetching Matchlineup :", InfoPanel.FEHLERFARBE);
            Helper.showMessage(HOMainFrame.instance(),
            		HOVerwaltung.instance().getLanguageString("Downloadfehler")
            		+ " : Error fetching Matchlineup :",
            		HOVerwaltung.instance().getLanguageString("Fehler"),
            		JOptionPane.ERROR_MESSAGE);
            waitDialog.setVisible(false);
            return null;
        }
        if (bOK)
        {
	        final XMLMatchLineupParser parser = new XMLMatchLineupParser();
	        lineUp = parser.parseMatchLineupFromString(matchLineup);
        }
        return lineUp;
    }
    
    protected String uploadLineup(int matchId, String orderString ) {
	    String result;
    	try {
	        result = MyConnector.instance().setMatchOrder(matchId, orderString);
	        return result;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return "";
    	}
	        
    } 
    

    ////////////////////////////////////////////////////////////////////////////////
    //Helper
    ////////////////////////////////////////////////////////////////////////////////

    /**
     * Save the passed in data to the passed in file
     *
     * @param fileName Name of the file to save the data to
     * @param content	The content to write to the file
     *
     * @return The saved file
     * @throws IOException 
     */
    protected final File saveFile(String fileName, String content) throws IOException {
        java.io.OutputStreamWriter outWrit = null;
        java.io.File outFile = null;
        java.io.BufferedWriter out = null;
    	outFile = new java.io.File(fileName);
        if (outFile.exists()) 
        	outFile.delete();
        outFile.createNewFile();
        outWrit = new java.io.OutputStreamWriter(new java.io.FileOutputStream(outFile), "UTF-8");
        out = new java.io.BufferedWriter(outWrit);
        out.write(content);
        out.newLine();
        out.close();
        return outFile;
    }

	/**
	 * Get all lineups for MatchKurzInfos, if they're not there already
	 */
	public void getAllLineups() {
		final MatchKurzInfo[] infos = DBManager.instance().getMatchesKurzInfo(-1);
		String haveLineups = "";
		boolean bOK = false;
		OnlineWorker ow = HOMainFrame.instance().getOnlineWorker();
		for (int i = 0; i < infos.length; i++) {
			int curMatchId = infos[i].getMatchID();
			if (!DBManager.instance().isMatchLineupVorhanden(curMatchId)) 
			{
				// Check if the lineup is available
				if (infos[i].getMatchStatus() == MatchKurzInfo.FINISHED) 
				{
					HOLogger.instance().log(getClass(),"Get Lineup : " + curMatchId);
					bOK = ow.getMatchlineup(curMatchId, infos[i].getHeimID(), infos[i].getGastID());
					if (bOK){ 
						bOK = ow.getMatchDetails(curMatchId);
						if (!bOK){
							break;
						}
					}
					else
						break;															
				} 
				else 
					HOLogger.instance().log(getClass(),"Not Played : " + curMatchId);
			} 
			else 
			{
				// Match lineup already available
				if (haveLineups.length() > 0)
					haveLineups += ", ";
				haveLineups += curMatchId;
			}
		}
		if (haveLineups.length() > 0)
			HOLogger.instance().log(getClass(),"Have Lineups : " + haveLineups);
	}
}
