// %1127327738353:hoplugins%
package ho.tool.export;

import ho.core.db.DBManager;
import ho.core.db.backup.HOZip;
import ho.core.file.ExampleFileFilter;
import ho.core.file.xml.ExportMatchData;
import ho.core.file.xml.MatchExporter;
import ho.core.file.xml.XMLManager;
import ho.core.gui.HOMainFrame;
import ho.core.model.HOMiniModel;
import ho.core.model.HOVerwaltung;
import ho.core.model.Team;
import ho.core.net.MyConnector;
import ho.core.net.login.LoginWaitDialog;
import ho.core.rating.RatingPredictionManager;
import ho.core.util.HOLogger;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import plugins.IExportMatchData;
import plugins.ILineUp;
import plugins.IMatchDetails;
import plugins.IMatchLineupPlayer;
import plugins.IMatchLineupTeam;
import plugins.ISpieler;
import plugins.ISpielerPosition;
import plugins.ITeam;

//implement IPlugin for integration into HO
//Refreshable to get informed by data updates
//Actionlistner for Button interaction
public class XMLExporter  {
    //~ Static fields/initializers -----------------------------------------------------------------

    private static String m_sUserRegionID = "-1";

    //~ Instance fields ----------------------------------------------------------------------------

    private SpinnerDateModel m_clSpinnerModel = new SpinnerDateModel();
   

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of DummyPlugIn
     */
    public XMLExporter() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Do the export.
     */
    public void doExport() {
        javax.swing.JWindow waitDialog = null;
		JSpinner m_jsSpinner = new JSpinner(m_clSpinnerModel);
        try {
            // Date           
            m_clSpinnerModel.setCalendarField(java.util.Calendar.MONTH);
            ((JSpinner.DateEditor) m_jsSpinner.getEditor()).getFormat().applyPattern("dd.MM.yyyy");
            m_clSpinnerModel.setValue(RatingPredictionManager.LAST_CHANGE);
	
            JFrame owner = HOMainFrame.instance();
            final JDialog dialog = new JDialog(owner, "Export starts at date:");
            dialog.getContentPane().setLayout(new BorderLayout());
            dialog.getContentPane().add(m_jsSpinner, BorderLayout.CENTER);

            JButton button = new JButton("OK");
            button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dialog.setVisible(false);
                    }
                });
            dialog.getContentPane().add(button, BorderLayout.SOUTH);
            dialog.pack();
            dialog.setLocation((owner.getLocation().x + (owner.getWidth() / 2))
                               - (dialog.getWidth() / 2),
                               (owner.getLocation().y + (owner.getHeight() / 2))
                               - (dialog.getHeight() / 2));
            dialog.setModal(true);
            dialog.setVisible(true);
            
            // File
            java.io.File file = new java.io.File(HOVerwaltung.instance().getModel().getBasics().getTeamName() + ".zip");

            javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
            fileChooser.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
            fileChooser.setDialogTitle("XML Export");

            ExampleFileFilter filter = new ExampleFileFilter();
            filter.addExtension("zip");
            filter.setDescription("ZIP Dump File");
            fileChooser.setFileFilter(filter);
            fileChooser.setSelectedFile(file);

            int returnVal = fileChooser.showSaveDialog(HOMainFrame.instance());

            if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
                waitDialog =new LoginWaitDialog(HOMainFrame.instance());
                waitDialog.setVisible(true);
				saveXML(file.getAbsolutePath(),new Timestamp(m_clSpinnerModel.getDate().getTime()));
            }
        } catch (Exception ex) {
            HOLogger.instance().log(getClass(),ex);
        }

        if (waitDialog != null) {
            waitDialog.setVisible(false);
        }
    }


	////////////////////////////////XML schreiben/////////////////////////////
	///
	// *XML Format
	// <MatchList>
	// *<Match>
	// *  <Datum>
	// *  <MatchID>
	// *  <Derby>
	// *  <MatchTyp> //Cup usw...
	// *  <Heim/Ausw>
	// *  <Team>  
	// *      <TeamID>
	// *      <System>
	// *      <EingespieltHeit>
	// *      <Einstellung>
	// *      <Spezialtaktik>
	// *      <Stimmung>
	// *      <Selbstvertrauen>
	// *      <Ratings>...
	// *      <lineup>
	// *          <Spieler>
	// *              <SpielerID>
	// *              <Position>
	// *              <Taktik>
	// *              <ResultingPosition>
	// *              <SpielerDaten>....
	// *          </Spieler>
	// *          ...
	// *      </Lineup>
	// *  </Team>
	// *</Match>
	// </MatchList>
	// */
    /**


	/**
	 * Save XMP file.
	 */
	public void saveXML(String filename, Timestamp startingDate) {
				
		//Alle Matches holen			
		List<IExportMatchData> matches = MatchExporter.getDataUsefullMatches(startingDate);
		
		//XML schreiben
		try {
			Document doc = null;
			Element ele = null;
			Element tmpEle = null;
			Element root = null;
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			doc = builder.newDocument();
			root = doc.createElement("MatchList");
			doc.appendChild(root);

			///Team  Info + ManagerName adden
			tmpEle = doc.createElement("TeamName");
			root.appendChild(tmpEle);
			tmpEle.appendChild(doc.createTextNode("" + HOMiniModel.instance().getBasics().getTeamName()));
			tmpEle = doc.createElement("ManagerName");
			root.appendChild(tmpEle);
			tmpEle.appendChild(doc.createTextNode("" + HOMiniModel.instance().getBasics().getManager()));

			//Exporter Version adden
			tmpEle = doc.createElement("XMLExporterVersion");
			root.appendChild(tmpEle);
			tmpEle.appendChild(doc.createTextNode("1.05"));

			for (Iterator<IExportMatchData> iter = matches.iterator(); iter.hasNext();) {
				ExportMatchData matchData = (ExportMatchData) iter.next();

				//Matchdaten
				tmpEle = doc.createElement("Match");
				root.appendChild(tmpEle);
				ele = doc.createElement("MatchID");
				tmpEle.appendChild(ele);
				ele.appendChild(doc.createTextNode("" + matchData.getInfo().getMatchID()));
				ele = doc.createElement("Datum");
				tmpEle.appendChild(ele);
				ele.appendChild(doc.createTextNode(matchData.getInfo().getMatchDate()));
				ele = doc.createElement("Derby");
				tmpEle.appendChild(ele);

				if (getRegionID4Team(matchData.getInfo().getGastID()).equals(getRegionID4Team(matchData.getInfo().getHeimID()))) {
					ele.appendChild(doc.createTextNode("1"));
				} else {
					ele.appendChild(doc.createTextNode("0"));
				}

				ele = doc.createElement("MatchType");
				tmpEle.appendChild(ele);
				ele.appendChild(doc.createTextNode("" + matchData.getInfo().getMatchTyp()));
				ele = doc.createElement("Heimspiel");
				tmpEle.appendChild(ele);

				boolean heimspiel = true;
				if (matchData.getInfo().getHeimID() == HOMiniModel.instance().getBasics().getTeamId()) {
					ele.appendChild(doc.createTextNode("1"));
					heimspiel = true;
				} else {
					ele.appendChild(doc.createTextNode("0"));
					heimspiel = false;
				}

				//Teamdaten
				ele = doc.createElement("Team");
				tmpEle.appendChild(ele);

				//tmpRoot wechseln
				tmpEle = ele;

				//Details holen
				//details = (IMatchDetails) usefulMatches.get(new Integer(matchID));
				int hrfID = DBManager.instance().getHrfIDSameTraining(matchData.getInfo().getMatchDateAsTimestamp());

				//HRF ID vermerken
				ele = doc.createElement("HRFID");
				tmpEle.appendChild(ele);
				ele.appendChild(doc.createTextNode("" + hrfID));

				IMatchLineupTeam lineupTeam = null;
				IMatchDetails details = matchData.getDetails();
				if (details.getHeimId() == HOMiniModel.instance().getBasics().getTeamId()) {
					lineupTeam = HOMiniModel.instance().getMatchLineup(details.getMatchID()).getHeim();
				} else {
					lineupTeam = HOMiniModel.instance().getMatchLineup(details.getMatchID()).getGast();
				}

				Team team = DBManager.instance().getTeam(hrfID);

				//Daten schreiben
				ele = doc.createElement("TeamID");
				tmpEle.appendChild(ele);
				ele.appendChild(doc.createTextNode("" + HOMiniModel.instance().getBasics().getTeamId()));
				ele = doc.createElement("System");
				tmpEle.appendChild(ele);
				ele.appendChild(doc.createTextNode("" + lineupTeam.determinateSystem()));
				ele = doc.createElement("Eingespieltheit");
				tmpEle.appendChild(ele);
				ele.appendChild(doc.createTextNode("" + getTeamErfahrung(team, lineupTeam.determinateSystem())));
				ele = doc.createElement("TrainerType");
				tmpEle.appendChild(ele);
				ele.appendChild(doc.createTextNode("" + DBManager.instance().getTrainerType(hrfID)));
				ele.appendChild(doc.createComment(" 0=Defense, 2= Normal, 1=Offense Trainer, -99 NOT Found "));

				if (heimspiel) {
					ele = doc.createElement("Einstellung");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + details.getHomeEinstellung()));
					ele = doc.createElement("Spezialtaktik");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + details.getHomeTacticType()));
					ele = doc.createElement("SpezialtaktikSkill");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + details.getHomeTacticSkill()));
					ele = doc.createElement("LeftAtt");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + details.getHomeLeftAtt()));
					ele = doc.createElement("LeftDef");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + details.getHomeLeftDef()));
					ele = doc.createElement("MidAtt");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + details.getHomeMidAtt()));
					ele = doc.createElement("MidDef");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + details.getHomeMidDef()));
					ele = doc.createElement("Midfield");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + details.getHomeMidfield()));
					ele = doc.createElement("RightAtt");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + details.getHomeRightAtt()));
					ele = doc.createElement("RightDef");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + details.getHomeRightDef()));
				} else {
					ele = doc.createElement("Einstellung");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + details.getGuestEinstellung()));
					ele = doc.createElement("Spezialtaktik");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + details.getGuestTacticType()));
					ele = doc.createElement("SpezialtaktikSkill");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + details.getGuestTacticSkill()));
					ele = doc.createElement("LeftAtt");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + details.getGuestLeftAtt()));
					ele = doc.createElement("LeftDef");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + details.getGuestLeftDef()));
					ele = doc.createElement("MidAtt");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + details.getGuestMidAtt()));
					ele = doc.createElement("MidDef");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + details.getGuestMidDef()));
					ele = doc.createElement("Midfield");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + details.getGuestMidfield()));
					ele = doc.createElement("RightAtt");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + details.getGuestRightAtt()));
					ele = doc.createElement("RightDef");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + details.getGuestRightDef()));
				}

				ele = doc.createElement("Stimmung");
				tmpEle.appendChild(ele);
				ele.appendChild(doc.createTextNode("" + getTeamStimmung(team)));
				ele = doc.createElement("Selbstvertrauen");
				tmpEle.appendChild(ele);
				ele.appendChild(doc.createTextNode("" + getTeamSelbstvertrauen(team)));
				ele = doc.createElement("Erfahrung");
				tmpEle.appendChild(ele);
				ele.appendChild(doc.createTextNode("" + lineupTeam.getErfahrung()));

				//lineup
				Element lineupEle = doc.createElement("Lineup");
				tmpEle.appendChild(lineupEle);

				//tmpRoot wechseln
				tmpEle = lineupEle;

				//Spieler schreiben
				for (int k = 0;(lineupTeam.getAufstellung() != null) && (k < lineupTeam.getAufstellung().size()); k++) {					
					IMatchLineupPlayer playerMatch = (IMatchLineupPlayer) lineupTeam.getAufstellung().get(k);
					ISpieler playerData = (ISpieler) matchData.getPlayers().get(Integer.valueOf(playerMatch.getSpielerId()));

					//Bank + verletzte überspringen
					if (playerMatch.getId() >= ISpielerPosition.startReserves) {
						continue;
					}

					ele = doc.createElement("SpielerDaten");
					lineupEle.appendChild(ele);

					//tmpRoot wechseln
					tmpEle = ele;

					ele = doc.createElement("SpielerID");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + playerMatch.getSpielerId()));
					ele = doc.createElement("Spezialitaet");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + playerData.getSpezialitaet()));
					ele = doc.createElement("RoleID");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + playerMatch.getId()));
					ele = doc.createElement("Tactic");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + playerMatch.getTaktik()));
					ele = doc.createElement("HOPosition");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + playerMatch.getPosition()));
					ele = doc.createElement("HTPositionCode");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + playerMatch.getPositionCode()));
					ele = doc.createElement("Bewertung");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + playerMatch.getRating()));
					ele = doc.createElement("Name");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + playerMatch.getSpielerName()));
					ele = doc.createElement("Alter");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + playerData.getAlter()));
					ele = doc.createElement("AgeDays");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + playerData.getAgeDays()));
					ele = doc.createElement("TSI");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + playerData.getTSI()));
					ele = doc.createElement("Form");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + playerData.getForm()));
					ele = doc.createElement("Kondition");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + playerData.getKondition()));
					ele = doc.createElement("Erfahrung");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + playerData.getErfahrung()));
					ele = doc.createElement("Torwart");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + playerData.getTorwart()));
					ele = doc.createElement("Verteidigung");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + playerData.getVerteidigung()));
					ele = doc.createElement("Passspiel");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + playerData.getPasspiel()));
					ele = doc.createElement("Fluegel");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + playerData.getFluegelspiel()));
					ele = doc.createElement("Torschuss");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + playerData.getTorschuss()));
					ele = doc.createElement("Standards");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + playerData.getStandards()));
					ele = doc.createElement("Spielaufbau");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + playerData.getSpielaufbau()));
					ele = doc.createElement("SubTorwart");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + playerData.getSubskill4Pos(ISpieler.SKILL_TORWART)));
					ele = doc.createElement("SubTorwartHadLevelUp");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + hadSkillup(ISpieler.SKILL_TORWART, playerData, details.getSpielDatum())));
					ele = doc.createElement("SubVerteidigung");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + playerData.getSubskill4Pos(ISpieler.SKILL_VERTEIDIGUNG)));
					ele = doc.createElement("SubVerteidigungHadLevelUp");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + hadSkillup(ISpieler.SKILL_VERTEIDIGUNG, playerData, details.getSpielDatum())));
					ele = doc.createElement("SubPassspiel");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + playerData.getSubskill4Pos(ISpieler.SKILL_PASSSPIEL)));
					ele = doc.createElement("SubPassspielHadLevelUp");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + hadSkillup(ISpieler.SKILL_PASSSPIEL, playerData, details.getSpielDatum())));
					ele = doc.createElement("SubFluegel");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + playerData.getSubskill4Pos(ISpieler.SKILL_FLUEGEL)));
					ele = doc.createElement("SubFluegelHadLevelUp");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + hadSkillup(ISpieler.SKILL_FLUEGEL, playerData, details.getSpielDatum())));
					ele = doc.createElement("SubTorschuss");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + playerData.getSubskill4Pos(ISpieler.SKILL_TORSCHUSS)));
					ele = doc.createElement("SubTorschussHadLevelUp");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + hadSkillup(ISpieler.SKILL_TORSCHUSS, playerData, details.getSpielDatum())));
					ele = doc.createElement("SubStandards");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + playerData.getSubskill4Pos(ISpieler.SKILL_STANDARDS)));
					ele = doc.createElement("SubStandardsHadLevelUp");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + hadSkillup(ISpieler.SKILL_STANDARDS, playerData, details.getSpielDatum())));
					ele = doc.createElement("SubSpielaufbau");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + playerData.getSubskill4Pos(ISpieler.SKILL_SPIELAUFBAU)));
					ele = doc.createElement("SubSpielaufbauHadLevelUp");
					tmpEle.appendChild(ele);
					ele.appendChild(doc.createTextNode("" + hadSkillup(ISpieler.SKILL_SPIELAUFBAU, playerData, details.getSpielDatum())));
				}
			}

			//Fertig -> saven
			String xml = XMLManager.instance().getXML(doc);
			HOZip zip = new HOZip(filename);
			String xmlfile = HOMiniModel.instance().getBasics().getTeamName() + ".xml";
			zip.addStringEntry(xmlfile,xml);
			zip.closeArchive();
		} catch (Exception e) {
			HOLogger.instance().log(getClass(), "pickupData.writeXML: " + e);
			HOLogger.instance().log(getClass(), e);
		}

		//        HOMiniModel.instance().getGUI ().getInfoPanel ().clearAll ();   
		HOMiniModel.instance().getHelper().showMessage(
			HOMiniModel.instance().getGUI().getOwner4Dialog(),
			"" + matches.size() + " Matches exportet.\n Regarding to CHPP rules: Any App that uses this XML-File has to be CHPP approved!",
			"Finished",
			javax.swing.JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Check for skillup.
	 */
	private String hadSkillup(int skill, ISpieler player, Timestamp matchdate) {
		Object[] value = player.getLastLevelUp(skill);

		if ((value != null) && ((value[0] != null) && (value[1] != null))) {
			if ((((Boolean) value[1]).booleanValue()) && ((Timestamp) value[0]).before(matchdate)) {
				return "1";
			}
		}

		return "0";
	}
	
	// -------------------------------- Helper -----------------------------------------------------------

	private String getRegionID4Team(int teamID) {
		if (teamID == HOMiniModel.instance().getBasics().getTeamId()) {
			if (m_sUserRegionID.equals("-1")) {
				if (HOMiniModel.instance().getBasics().getRegionId() > 0) {
					// Since HO 1.401, the regionId exists in Basics
					m_sUserRegionID = "" + HOMiniModel.instance().getBasics().getRegionId();
				} else {
					//saugen
					m_sUserRegionID = MyConnector.instance().fetchRegionID(teamID);
				}
				return m_sUserRegionID;
			}
			return m_sUserRegionID;
		} 
		return MyConnector.instance().fetchRegionID(teamID);
	}

	/**
	 * Get the formation experience for the given system.
	 */
	private int getTeamErfahrung(ITeam team, byte system) {
		if (team == null) {
			return -1;
		}
		switch (system) {
			case ILineUp.SYS_MURKS :
				return -1;

			case ILineUp.SYS_451 :
				return team.getFormationExperience451();

			case ILineUp.SYS_352 :
				return team.getFormationExperience352();

			case ILineUp.SYS_442 :
				return team.getFormationExperience442();

			case ILineUp.SYS_343 :
				return team.getFormationExperience343();

			case ILineUp.SYS_433 :
				return team.getFormationExperience433();

			case ILineUp.SYS_532 :
				return team.getFormationExperience532();

			case ILineUp.SYS_541 :
				return team.getFormationExperience541();
				
			case ILineUp.SYS_523 :
				return team.getFormationExperience523();
				
			case ILineUp.SYS_550 :
				return team.getFormationExperience550();
				
			case ILineUp.SYS_253 :
				return team.getFormationExperience253();

		}
		return -1;
	}

	/**
	 * Get the confidence.
	 */
	private int getTeamSelbstvertrauen(ITeam team) {
		if (team == null) {
			return -1;
		}
		return team.getSelbstvertrauenAsInt();

	}

	/**
	 * Get the team spirit.
	 */
	private int getTeamStimmung(ITeam team) {
		if (team == null) {
			return -1;
		}
		return team.getStimmungAsInt();
	}
	
}
