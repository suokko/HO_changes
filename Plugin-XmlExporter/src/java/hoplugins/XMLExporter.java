package hoplugins;

import hoplugins.xmlExporter.ExampleFileFilter;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSpinner;
import javax.swing.JWindow;
import javax.swing.SpinnerDateModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import plugins.IHOMiniModel;
import plugins.IMatchDetails;
import plugins.IMatchHighlight;
import plugins.IMatchKurzInfo;
import plugins.IMatchLineup;
import plugins.IMatchLineupPlayer;
import plugins.IMatchLineupTeam;
import plugins.IOfficialPlugin;
import plugins.IPlugin;
import plugins.ISpieler;

public class XMLExporter implements IPlugin, ActionListener, IOfficialPlugin {
	private IHOMiniModel m_clModel;
	private static String m_sUserRegionID = "-1";
	private Timestamp m_clStartZeit;
	private SpinnerDateModel m_clSpinnerModel;
	private JSpinner m_jsSpinner;

	public XMLExporter() {
		m_clModel = null;
		m_clStartZeit = new Timestamp((new GregorianCalendar(2005, 0, 31)).getTimeInMillis());
		m_clSpinnerModel = new SpinnerDateModel();
		m_jsSpinner = new JSpinner(m_clSpinnerModel);
	}

	public String getName() {
		return "XML Exporter";
	}

	public void start(IHOMiniModel hOMiniModel) {
		m_clModel = hOMiniModel;
		JMenu ratingDumpMenu = new JMenu("XMLExport");
		JMenuItem ratingDumpMenuItem = new JMenuItem("Export Team and Matchdata to XML");
		ratingDumpMenuItem.addActionListener(this);
		ratingDumpMenu.add(ratingDumpMenuItem);
		m_clModel.getGUI().addMenu(ratingDumpMenu);
	}

	public void actionPerformed(ActionEvent e) {
		JWindow waitDialog = null;
		try {
			m_clSpinnerModel.setCalendarField(2);
			((javax.swing.JSpinner.DateEditor)m_jsSpinner.getEditor()).getFormat().applyPattern("dd.MM.yyyy");
			m_clSpinnerModel.setValue(new Date(m_clStartZeit.getTime()));
			JFrame owner = m_clModel.getGUI().getOwner4Dialog();
			final JDialog dialog = new JDialog(owner, "Export starts at date:");
			dialog.getContentPane().setLayout(new BorderLayout());
			dialog.getContentPane().add(m_jsSpinner, "Center");
			JButton button = new JButton("OK");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					dialog.setVisible(false);
				}
			});
			dialog.getContentPane().add(button, "South");
			dialog.pack();
			dialog.setLocation((owner.getLocation().x + owner.getWidth() / 2) - dialog.getWidth() / 2, (owner.getLocation().y + owner.getHeight() / 2) - dialog.getHeight() / 2);
			dialog.setModal(true);
			dialog.setVisible(true);
			m_clStartZeit.setTime(m_clSpinnerModel.getDate().getTime());
			File file = new File(m_clModel.getBasics().getTeamName() + ".xml");
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogType(1);
			fileChooser.setDialogTitle("XML Export");
			ExampleFileFilter filter = new ExampleFileFilter();
			filter.addExtension("xml");
			filter.setDescription("XML Dump File");
			fileChooser.setFileFilter(filter);
			fileChooser.setSelectedFile(file);
			int returnVal = fileChooser.showSaveDialog(m_clModel.getGUI().getOwner4Dialog());
			if(returnVal == 0)
			{
				file = fileChooser.getSelectedFile();
				waitDialog = m_clModel.getGUI().createWaitDialog(m_clModel.getGUI().getOwner4Dialog());
				waitDialog.setVisible(true);
				pickUpData(file.getAbsolutePath());
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		if(waitDialog != null)
			waitDialog.setVisible(false);
	}

	private void pickUpData(String filename) {
		IMatchKurzInfo[] matches = m_clModel.getMatchesKurzInfo(m_clModel.getBasics().getTeamId());
		Hashtable<Integer, IMatchDetails> usefulMatches = new Hashtable<Integer, IMatchDetails>();
		Hashtable<Integer, Integer> id2Index = new Hashtable<Integer, Integer>();
		Hashtable<Integer, Hashtable<Integer, ISpieler>> id2MatchData = getHt4UsefullMatches(matches, usefulMatches, id2Index);
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
			tmpEle = doc.createElement("XMLExporterVersion");
			root.appendChild(tmpEle);
			tmpEle.appendChild(doc.createTextNode("" + getVersion()));
			tmpEle = doc.createElement("TeamName");
			root.appendChild(tmpEle);
			tmpEle.appendChild(doc.createTextNode("" + m_clModel.getBasics().getTeamName()));
			tmpEle = doc.createElement("ManagerName");
			root.appendChild(tmpEle);
			tmpEle.appendChild(doc.createTextNode("" + m_clModel.getBasics().getManager()));
			for(Enumeration<Integer> keys = id2MatchData.keys(); keys.hasMoreElements();)
			{
				int matchID = keys.nextElement().intValue();
				int index = id2Index.get(new Integer(matchID)).intValue();
				IMatchDetails details = null;
				int hrfID = -1;
				IMatchLineupTeam lineupTeam = null;
				boolean heimspiel = true;
				ISpieler playerData = null;
				IMatchLineupPlayer playerMatch = null;
				tmpEle = doc.createElement("Match");
				root.appendChild(tmpEle);
				ele = doc.createElement("MatchID");
				tmpEle.appendChild(ele);
				ele.appendChild(doc.createTextNode("" + matches[index].getMatchID()));
				ele = doc.createElement("Datum");
				tmpEle.appendChild(ele);
				ele.appendChild(doc.createTextNode(matches[index].getMatchDate()));
				ele = doc.createElement("Derby");
				tmpEle.appendChild(ele);
				if(getRegionID4Team(matches[index].getGastID()).equals(getRegionID4Team(matches[index].getHeimID())))
					ele.appendChild(doc.createTextNode("1"));
				else
					ele.appendChild(doc.createTextNode("0"));
				ele = doc.createElement("MatchType");
				tmpEle.appendChild(ele);
				ele.appendChild(doc.createTextNode("" + matches[index].getMatchTyp()));
				ele = doc.createElement("Heimspiel");
				tmpEle.appendChild(ele);
				if(matches[index].getHeimID() == m_clModel.getBasics().getTeamId())
				{
					ele.appendChild(doc.createTextNode("1"));
					heimspiel = true;
				} else
				{
					ele.appendChild(doc.createTextNode("0"));
					heimspiel = false;
				}
				ele = doc.createElement("Team");
				tmpEle.appendChild(ele);
				tmpEle = ele;
				details = usefulMatches.get(new Integer(matchID));
				hrfID = getHRFID4Date(matches[index].getMatchDateAsTimestamp());
				ele = doc.createElement("HRFID");
				tmpEle.appendChild(ele);
				ele.appendChild(doc.createTextNode("" + hrfID));
				if(details.getHeimId() == m_clModel.getBasics().getTeamId())
					lineupTeam = m_clModel.getMatchLineup(details.getMatchID()).getHeim();
				else
					lineupTeam = m_clModel.getMatchLineup(details.getMatchID()).getGast();
				ele = doc.createElement("TeamID");
				tmpEle.appendChild(ele);
				ele.appendChild(doc.createTextNode("" + m_clModel.getBasics().getTeamId()));
				ele = doc.createElement("System");
				tmpEle.appendChild(ele);
				ele.appendChild(doc.createTextNode("" + lineupTeam.determinateSystem()));
				ele = doc.createElement("Eingespieltheit");
				tmpEle.appendChild(ele);
				ele.appendChild(doc.createTextNode("" + getTeamErfahrung(hrfID, lineupTeam.determinateSystem())));
				ele = doc.createElement("TrainerType");
				tmpEle.appendChild(ele);
				ele.appendChild(doc.createTextNode("" + getTrainerTypeAtDate(hrfID)));
				ele.appendChild(doc.createComment(" 0=Defense, 2= Normal, 1=Offense Trainer, -99 NOT Found "));
				if(heimspiel)
				{
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
				} else
				{
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
				ele.appendChild(doc.createTextNode("" + getTeamStimmung(hrfID)));
				ele = doc.createElement("Selbstvertrauen");
				tmpEle.appendChild(ele);
				ele.appendChild(doc.createTextNode("" + getTeamSelbstvertrauen(hrfID)));
				ele = doc.createElement("Erfahrung");
				tmpEle.appendChild(ele);
				ele.appendChild(doc.createTextNode("" + lineupTeam.getErfahrung()));
				Element lineupEle = doc.createElement("Lineup");
				tmpEle.appendChild(lineupEle);
				tmpEle = lineupEle;
				int k = 0;
				while(lineupTeam.getAufstellung() != null && k < lineupTeam.getAufstellung().size())
				{
					playerMatch = (IMatchLineupPlayer)lineupTeam.getAufstellung().get(k);
					playerData = (ISpieler)id2MatchData.get(new Integer(matchID)).get(new Integer(playerMatch.getSpielerId()));
					if(playerMatch.getId() < 12)
					{
						ele = doc.createElement("SpielerDaten");
						lineupEle.appendChild(ele);
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
						ele = doc.createElement("TSI");
						tmpEle.appendChild(ele);
						ele.appendChild(doc.createTextNode("" + playerData.getMarkwert()));
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
						ele.appendChild(doc.createTextNode("" + playerData.getSubskill4Pos(0)));
						ele = doc.createElement("SubTorwartHadLevelUp");
						tmpEle.appendChild(ele);
						ele.appendChild(doc.createTextNode("" + hadSkillup(0, playerData, details.getSpielDatum())));
						ele = doc.createElement("SubVerteidigung");
						tmpEle.appendChild(ele);
						ele.appendChild(doc.createTextNode("" + playerData.getSubskill4Pos(1)));
						ele = doc.createElement("SubVerteidigungHadLevelUp");
						tmpEle.appendChild(ele);
						ele.appendChild(doc.createTextNode("" + hadSkillup(1, playerData, details.getSpielDatum())));
						ele = doc.createElement("SubPassspiel");
						tmpEle.appendChild(ele);
						ele.appendChild(doc.createTextNode("" + playerData.getSubskill4Pos(5)));
						ele = doc.createElement("SubPassspielHadLevelUp");
						tmpEle.appendChild(ele);
						ele.appendChild(doc.createTextNode("" + hadSkillup(5, playerData, details.getSpielDatum())));
						ele = doc.createElement("SubFluegel");
						tmpEle.appendChild(ele);
						ele.appendChild(doc.createTextNode("" + playerData.getSubskill4Pos(2)));
						ele = doc.createElement("SubFluegelHadLevelUp");
						tmpEle.appendChild(ele);
						ele.appendChild(doc.createTextNode("" + hadSkillup(2, playerData, details.getSpielDatum())));
						ele = doc.createElement("SubTorschuss");
						tmpEle.appendChild(ele);
						ele.appendChild(doc.createTextNode("" + playerData.getSubskill4Pos(4)));
						ele = doc.createElement("SubTorschussHadLevelUp");
						tmpEle.appendChild(ele);
						ele.appendChild(doc.createTextNode("" + hadSkillup(4, playerData, details.getSpielDatum())));
						ele = doc.createElement("SubStandards");
						tmpEle.appendChild(ele);
						ele.appendChild(doc.createTextNode("" + playerData.getSubskill4Pos(8)));
						ele = doc.createElement("SubStandardsHadLevelUp");
						tmpEle.appendChild(ele);
						ele.appendChild(doc.createTextNode("" + hadSkillup(8, playerData, details.getSpielDatum())));
						ele = doc.createElement("SubSpielaufbau");
						tmpEle.appendChild(ele);
						ele.appendChild(doc.createTextNode("" + playerData.getSubskill4Pos(3)));
						ele = doc.createElement("SubSpielaufbauHadLevelUp");
						tmpEle.appendChild(ele);
						ele.appendChild(doc.createTextNode("" + hadSkillup(3, playerData, details.getSpielDatum())));
					}
					k++;
				}
			}

			m_clModel.getXMLParser().writeXML(doc, filename);
		} catch(Exception e) {
			System.out.println("pickupData.writeXML: " + e);
			e.printStackTrace();
		}
		m_clModel.getHelper().showMessage(m_clModel.getGUI().getOwner4Dialog(), "" + id2MatchData.size() + " Matches exportet.\n Regarding to CHPP rules : Any App that uses this XML-File has to be CHPP approved!", "Finished", 1);
	}

	private Hashtable<Integer, Hashtable<Integer, ISpieler>> getHt4UsefullMatches(IMatchKurzInfo[] matches, Hashtable<Integer,IMatchDetails> usefulMatches, Hashtable<Integer, Integer> id2Index) {
		Hashtable<Integer, Hashtable<Integer, ISpieler>> id2MatchData = new Hashtable<Integer, Hashtable<Integer, ISpieler>>();
		System.out.println("Collecting Data");
		for(int i = 0; matches != null && i < matches.length; i++)  {
			System.out.println("Check " + matches[i].getMatchID() + " / " + matches[i].getMatchDateAsTimestamp());
			IMatchDetails details = m_clModel.getMatchDetails(matches[i].getMatchID());
			Vector<IMatchHighlight> highlights = details.getHighlights();
			boolean addMatch = true;
			int hrfCheckId = getHRFID4Date(matches[i].getMatchDateAsTimestamp());
			if(matches[i].getMatchDateAsTimestamp().before(m_clStartZeit) || hrfCheckId == -1) {
				System.out.println("Too old: " + matches[i].getMatchDateAsTimestamp() + " < " + m_clStartZeit + " or hrfcheck: " + hrfCheckId);
				addMatch = false;
			} else {
				int j = 0;
				while(true) {
					if(highlights == null || j >= highlights.size())
						break;
					IMatchHighlight hlight = highlights.get(j);
					if(hlight.getTeamID() == m_clModel.getBasics().getTeamId() &&
							(hlight.getHighlightSubTyp() == 12 || hlight.getHighlightSubTyp() == 13 // yellow/red + direct red cards
							|| hlight.getHighlightSubTyp() == 14 || hlight.getHighlightSubTyp() == 95
							|| hlight.getHighlightSubTyp() == 93 || hlight.getHighlightSubTyp() == 96 // injuries
							|| hlight.getHighlightSubTyp() == 91 || hlight.getHighlightSubTyp() == 92))
					{
						addMatch = false;
						System.out.println("Skipped (highlight type): " + hlight.getHighlightSubTyp());
						break;
					}
					j++;
				}
			}
			if(addMatch && matches[i].getMatchStatus() == 1 && details.getMatchID() != -1) {
				usefulMatches.put(new Integer(details.getMatchID()), details);
				id2Index.put(new Integer(details.getMatchID()), new Integer(i));
			}
		}

		Enumeration<IMatchDetails> j = usefulMatches.elements();
		while(true) {
			if(!j.hasMoreElements())
				break;
			IMatchDetails details2 = j.nextElement();
			IMatchLineup lineup = m_clModel.getMatchLineup(details2.getMatchID());
			IMatchLineupTeam userTeam = null;
			boolean dataOK = true;
			if(details2.getHeimId() == m_clModel.getBasics().getTeamId())
				userTeam = lineup.getHeim();
			else
				userTeam = lineup.getGast();
			Vector<IMatchLineupPlayer> aufstellung = userTeam.getAufstellung();
			Hashtable<Integer,ISpieler> lineUpISpieler = new Hashtable<Integer,ISpieler>();
			for(int k = 0; aufstellung != null && k < aufstellung.size(); k++) {
				IMatchLineupPlayer player = (IMatchLineupPlayer)aufstellung.get(k);
				ISpieler formerPlayerData = null;
				if(player.getId() >= 12)
					continue;
				formerPlayerData = m_clModel.getSpielerAtDate(player.getSpielerId(),
						matches[id2Index.get(new Integer(details2.getMatchID())).intValue()].getMatchDateAsTimestamp());
				if(formerPlayerData == null) {
					dataOK = false;
					System.out.println("Skipped (!dataOK): " + details2.getMatchID());
					lineUpISpieler.clear();
					break;
				}
				lineUpISpieler.put(new Integer(player.getSpielerId()), formerPlayerData);
			}
			if(dataOK)
				id2MatchData.put(new Integer(details2.getMatchID()), lineUpISpieler);
		}
		return id2MatchData;
	}

	private String getRegionID4Team(int teamID) {
		if(teamID == m_clModel.getBasics().getTeamId()) {
			if(m_sUserRegionID.equals("-1")) {
				m_sUserRegionID = m_clModel.getDownloadHelper().fetchRegionID(teamID);
				return m_sUserRegionID;
			} else {
				return m_sUserRegionID;
			}
		} else {
			return m_clModel.getDownloadHelper().fetchRegionID(teamID);
		}
	}

	private int getHRFID4Date(Timestamp time) {
		ResultSet rs = null;
		int hrfID = -1;
		Timestamp mintime = new Timestamp(time.getTime() - 7*24*60*60*1000L); // original 1.05: 0xf731400L = 3*24*60*60*1000
		Timestamp hrfDate = null;
		String sql = "SELECT HRF_ID, Datum FROM Basics WHERE Datum<='" + time.toString()
				+ "' AND Datum>='" + mintime.toString() + "' ORDER BY Datum DESC";
		try {
			rs = m_clModel.getAdapter().executeQuery(sql);
			if(rs.next()) {
				hrfID = rs.getInt("HRF_ID");
				hrfDate = rs.getTimestamp("Datum");
			}
		} catch(Exception e) {
			System.out.println("XMLExporter.getHRFID4Time: " + e);
		} finally {
			try {
				if (rs != null) rs.close();
			} catch (SQLException e) { /* do nothing */ }
		}

		if (hrfID != -1) {
			Timestamp training4Hrf = getTrainingDate(hrfID);
			if(training4Hrf.after(hrfDate) && training4Hrf.before(time)) {
				hrfID = -1;
			}
		}
		return hrfID;
	}

	public Timestamp getTrainingDate(int hrfID) {
		ResultSet rs = null;
		String sql = "SELECT Trainingdate FROM XtraData WHERE HRF_ID = " + hrfID;
		Timestamp trainingDate = null;
		try {
			rs = m_clModel.getAdapter().executeQuery(sql);
			if(rs.next()) {
				trainingDate = rs.getTimestamp("Trainingdate");
			}
		} catch(Exception e) {
			System.out.println("DatenbankZugriff.XtraData: " + e);
		} finally {
			try {
				if (rs != null) rs.close();
			} catch (SQLException e) { /* do nothing */ }
		}
		return trainingDate;
	}

	private int getTeamErfahrung(int hrfID, byte system) {
		int erfahrung = -1;
		ResultSet rs = null;
		String sql = "SELECT * FROM Team WHERE HRF_ID = " + hrfID;
		try {
			rs = m_clModel.getAdapter().executeQuery(sql);
			if(rs.next()) {
				switch(system)
				{
				case 7: // '\007'
				erfahrung = -1;
				break;

				case 6: // '\006'
					erfahrung = rs.getInt("iErfahrung451");
					break;

				case 4: // '\004'
					erfahrung = rs.getInt("iErfahrung352");
					break;

				case 1: // '\001'
					erfahrung = 8;
					break;

				case 5: // '\005'
					erfahrung = rs.getInt("iErfahrung343");
					break;

				case 0: // '\0'
					erfahrung = rs.getInt("iErfahrung433");
					break;

				case 2: // '\002'
					erfahrung = rs.getInt("iErfahrung532");
					break;

				case 3: // '\003'
					erfahrung = rs.getInt("iErfahrung541");
					break;
				}
			}
		}
		catch(Exception e) {
			System.out.println("DatenbankZugriff.getTeam: " + e);
		}
		return erfahrung;
	}

	private int getTeamStimmung(int hrfID) {
		ResultSet rs = null;
		String sql = "SELECT * FROM Team WHERE HRF_ID = " + hrfID;
		try {
			rs = m_clModel.getAdapter().executeQuery(sql);
			if (rs.next()) {
				return rs.getInt("iStimmung");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
			} catch (SQLException e) { /* do nothing */ }
		}
		return -1;
	}

	private int getTeamSelbstvertrauen(int hrfID) {
		ResultSet rs = null;
		String sql = "SELECT * FROM Team WHERE HRF_ID = " + hrfID;
		try {
			rs = m_clModel.getAdapter().executeQuery(sql);
			if (rs.next()) {
				return rs.getInt("iSelbstvertrauen");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
			} catch (SQLException e) { /* do nothing */ }
		}
		return -1;
	}

	public int getPluginID() {
		return 15;
	}

	public String getPluginName() {
		return "XML Exporter";
	}

	public File[] getUnquenchableFiles() {
		return new File[0];
	}

	public double getVersion() {
		return 1.051D;
	}

	private String hadSkillup(int skill, ISpieler player, Timestamp matchdate) {
		Object value[] = player.getLastLevelUp(skill);
		if(value != null && value[0] != null && value[1] != null && ((Boolean)value[1]).booleanValue() && ((Timestamp)value[0]).before(matchdate))
			return "1";
		else
			return "0";
	}

	private String getTrainerTypeAtDate(int hrfID) {
		ResultSet rs = null;
		String sql = "SELECT TrainerTyp FROM Spieler WHERE HRF_ID=" + hrfID + " AND TrainerTyp >=0 AND Trainer >0 ";
		try {
			rs = m_clModel.getAdapter().executeQuery(sql);
			if(rs != null && rs.first())
				return "" + rs.getInt("TrainerTyp");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
			} catch (SQLException e) { /* do nothing */ }
		}
		return "-99";
	}

}
