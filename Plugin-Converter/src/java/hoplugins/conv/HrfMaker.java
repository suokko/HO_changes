/*
 * Created on 27.04.2005
 *
 */
package hoplugins.conv;

import java.awt.Dimension;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import plugins.IDebugWindow;

/**
 * @author Thorsten Dietz
 *
 */
abstract class HrfMaker {

	protected String[][] clubValue = null;
	protected String[][] basicsValue;
	protected String[][] arenaValue = null;
	protected String[][] teamValue = null;
	protected String[][] leagueValue = null;
	protected String[][] linupValue = null;
	protected String[][] lastlinupValue = null;
	protected String[][] xtraValue = null;
	protected String[][] economyValue = null;
	protected String[][] playerValue = null;
	protected String type = " ";

	protected IDebugWindow window;

	protected final String convertNumber(int number) {
		return number < 10 ? "0" + number : number + "";
	}

	protected void debug(String txt) {
		if (window == null)
			window = RSC.MINIMODEL.getGUI().createDebugWindow(
					new Point(100, 200), new Dimension(700, 400));
		window.setVisible(true);
		window.append(txt);
	}

	protected void debug(Exception e) {
		if (window == null)
			window = RSC.MINIMODEL.getGUI().createDebugWindow(
					new Point(100, 200), new Dimension(700, 400));
		window.setVisible(true);
		window.append(e);
	}

	protected abstract void start(File[] selectedFiles, File targetDir);

	public void handleException(Exception e, String txt) {
		if (e != null)
			debug(e);
		else
			debug(txt);
	}

	protected File getDestinationFolder(JFrame owner) {

		File dir = null;
		JOptionPane.showMessageDialog(null, RSC
				.getProperty("select_destinated_directory"));
		JFileChooser saver = new JFileChooser();
		saver.setDialogTitle(RSC.getProperty("select_destinated_directory"));
		saver.setMultiSelectionEnabled(false);
		saver.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnSave = saver.showSaveDialog(owner);
		if (returnSave == JFileChooser.APPROVE_OPTION) {
			dir = saver.getSelectedFile();
		}
		return dir;
	}

	protected Document getDocument(String xmlString)
			throws ParserConfigurationException, IOException, SAXException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		ByteArrayInputStream inputStream = new ByteArrayInputStream(xmlString
				.getBytes());
		return builder.parse(inputStream);
	}

	protected final String getUTF8String(File file) {
		StringBuffer buffer = new StringBuffer();
		try {
			FileInputStream inputStream = new FileInputStream(file);
			InputStreamReader reader = new InputStreamReader(inputStream,
					"UTF8");

			Reader in = new BufferedReader(reader);
			int ch;
			boolean first = true;
			while ((ch = in.read()) > -1) {
				if (!first)
					buffer.append((char) ch);
				else
					first = false;

			}
		} catch (Exception e) {
			handleException(e, "Can�t read xml-file");
		}
		return buffer.toString();
	}

	protected void clearArrays() {
		clubValue = RSC.clubValueDefault;
		basicsValue = RSC.basicsValueDefault;
		arenaValue = RSC.arenaValueDefault;
		teamValue = RSC.teamValueDefault;
		leagueValue = RSC.leagueValueDefault;
		linupValue = RSC.linupValueDefault;
		lastlinupValue = RSC.lastlinupValueDefault;
		xtraValue = RSC.xtraValueDefault;
		economyValue = RSC.economyValue;
	}

	protected void writeHrf(String filter, File dir) {
		if (dir == null)
			return;
		try {
			StringBuffer filename = new StringBuffer();
			filename.append(dir.getAbsolutePath());
			filename.append(File.separator);
			filename.append(filter.substring(0, 4));
			filename.append("-");
			filename.append(filter.substring(4, 6));
			filename.append("-");
			filename.append(filter.substring(6, 8));
			filename.append("-");
			filename.append(type);
			filename.append(".hrf");

			FileOutputStream out = new FileOutputStream(filename.toString(),
					false);
			OutputStreamWriter fileWriter = new OutputStreamWriter(out, "UTF8");
			writeArray(basicsValue, fileWriter);
			writeArray(leagueValue, fileWriter);
			writeArray(clubValue, fileWriter);
			writeArray(teamValue, fileWriter);
			writeArray(linupValue, fileWriter);
			writeArray(economyValue, fileWriter);
			writeArray(arenaValue, fileWriter);
			writePlayerArray(playerValue, fileWriter);
			writePlayerArray(xtraValue, fileWriter);
			writePlayerArray(lastlinupValue, fileWriter);
			fileWriter.flush();
			fileWriter.close();

		} catch (Exception innerEx) {
			IDebugWindow debugWindow = RSC.MINIMODEL.getGUI()
					.createDebugWindow(new java.awt.Point(100, 200),
							new java.awt.Dimension(700, 400));
			debugWindow.setVisible(true);
			debugWindow.append(innerEx);
		}
	}

	protected static final void writeArray(String[][] value,
			OutputStreamWriter file) throws IOException {
		for (int i = 0; i < value.length; i++) {
			file.write(value[i][0] + value[i][1] + "\n");
		}
	}

	protected static final void writePlayerArray(String[][] value,
			OutputStreamWriter file) throws IOException {
		for (int x = 1; x < value[0].length; x++) {
			for (int i = 0; i < value.length; i++) {
				file.write(value[i][0] + value[i][x] + "\n");
			}//zeilen
		}// Spalten
	}

	protected final void addBasics() {
		if (RSC.MINIMODEL != null) {
			basicsValue[2][1] = "" + RSC.MINIMODEL.getHOVersion();
		}
	}

	protected void setTag(Element element, String[][] value, int index) {
		setTag(element, value, index, 1);
	}

	protected void setTag(Element element, String[][] value, int index, int index2) {
		Text txt = (Text) element.getFirstChild();
		if (txt == null)
			return;
		String tagValue = txt.getData();
		if (tagValue == null)
			return;
		value[index][index2] = tagValue.trim();
	}

	protected static void convert(byte type, File[] selectedFiles) {
		HrfMaker tmp = null;
		switch (type) {
		case RSC.TYPE_BUDDY:
			tmp = new Buddy();
			break;
		case RSC.TYPE_HAM:
			tmp = new HAM();
			break;
		case RSC.TYPE_HTFOREVER:
			tmp = new HTForever();
			break;
		case RSC.TYPE_HTCOACH:
			tmp = new HTCoach();
			break;
		}

		tmp.start(selectedFiles, tmp.getDestinationFolder(RSC.MINIMODEL
				.getGUI().getOwner4Dialog()));
	}

	protected void analyzeTeamDetails(NodeList nodelist) {

		for (int i = 0; i < nodelist.getLength(); i++) {
			if (nodelist.item(i) instanceof Element) {
				Element element = (Element) nodelist.item(i);

				if (element.getTagName().equals("HattrickData")) {
					analyzeTeamDetails(nodelist.item(i).getChildNodes());
					continue;
				}
				if (element.getTagName().equals("FetchedDate")) {
					setTag(element, basicsValue, 3);
					continue;
				}
				if (element.getTagName().equals("Team")) {
					analyzeTeamDetails(nodelist.item(i).getChildNodes());
					continue;
				}
				if (element.getTagName().equals("League")) {
					analyzeTeamDetails(nodelist.item(i).getChildNodes());
					continue;
				}
				if (element.getTagName().equals("LeagueLevelUnit")) {
					analyzeTeamDetails(nodelist.item(i).getChildNodes());
					continue;
				}
				if (element.getTagName().equals("User")) {
					analyzeTeamDetails(nodelist.item(i).getChildNodes());
					continue;
				}
				if (element.getTagName().equals("TeamID")) {
					setTag(element, basicsValue, 6);
					continue;
				}
				if (element.getTagName().equals("TeamName")) {
					setTag(element, basicsValue, 7);
					continue;
				}
				if (element.getTagName().equals("HomePage")) {
					setTag(element, basicsValue, 11);
					continue;
				}
				if (element.getTagName().equals("LogoURL")) {
					setTag(element, xtraValue, 6);
					continue;
				}
				if (element.getTagName().equals("Email")) {
					setTag(element, basicsValue, 9);
					continue;
				}
				if (element.getTagName().equals("Name")) {
					setTag(element, basicsValue, 8);
					continue;
				}
				if (element.getTagName().equals("ICQ")) {
					setTag(element, basicsValue, 10);
					continue;
				}
				if (element.getTagName().equals("Loginname")) {
					setTag(element, basicsValue, 8);
					continue;
				}
				if (element.getTagName().equals("PlayerID")) {
					setTag(element, xtraValue, 8);
					continue;
				}
				if (element.getTagName().equals("NumberOfVictories")) {
					setTag(element, clubValue, 10);
					continue;
				}
				if (element.getTagName().equals("NumberOfUndefeated")) {
					setTag(element, clubValue, 9);
					continue;
				}
				if (element.getTagName().equals("LeagueLevelUnitName")) {
					setTag(element, leagueValue, 1);
					continue;
				}
				if (element.getTagName().equals("LeagueID")) {
					setTag(element, basicsValue, 13);
					continue;
				}
				if (element.getTagName().equals("LeagueLevelUnitID")) {
					setTag(element, xtraValue, 11);
					continue;
				}
			} // if Element
		} // for
	}

	protected void analyzeClub(NodeList nodelist) {
		for (int i = 0; i < nodelist.getLength(); i++) {
			if (nodelist.item(i) instanceof Element) {
				Element element = (Element) nodelist.item(i);
				if (element.getTagName().equals("HattrickData")) {
					analyzeClub(nodelist.item(i).getChildNodes());
				}
				if (element.getTagName().equals("Team")) {
					analyzeClub(nodelist.item(i).getChildNodes());
				}
				if (element.getTagName().equals("YouthSquad")) {
					analyzeClub(nodelist.item(i).getChildNodes());
				}

				if (element.getTagName().equals("Specialists")) {
					analyzeClub(nodelist.item(i).getChildNodes());
				}
				if (element.getTagName().equals("AssistantTrainers")) {
					setTag(element, clubValue, 2);
				}
				if (element.getTagName().equals("Psychologists")) {
					setTag(element, clubValue, 3);
				}
				if (element.getTagName().equals("PressSpokesmen")) {
					setTag(element, clubValue, 4);
				}
				if (element.getTagName().equals("Physiotherapists")) {
					setTag(element, clubValue, 6);
				}
				if (element.getTagName().equals("Doctors")) {
					setTag(element, clubValue, 7);
				}
				if (element.getTagName().equals("YouthLevel")) {
					setTag(element, clubValue, 8);
				}
				if (element.getTagName().equals("HasPromoted")) {
					setTag(element, xtraValue, 7);
				}

			} // if
		}// for
	}

	/**
	 * @param childNodes
	 */
	protected void analyzeArena(NodeList nodelist) {

		for (int i = 0; i < nodelist.getLength(); i++) {
			if (nodelist.item(i) instanceof Element) {
				Element element = (Element) nodelist.item(i);
				if (element.getTagName().equals("HattrickData")) {
					analyzeArena(nodelist.item(i).getChildNodes());
				}
				if (element.getTagName().equals("Team")) {
					analyzeArena(nodelist.item(i).getChildNodes());
				}
				if (element.getTagName().equals("CurrentCapacity")) {
					analyzeArena(nodelist.item(i).getChildNodes());
				}
				if (element.getTagName().equals("Arena")) {
					analyzeArena(nodelist.item(i).getChildNodes());
					continue;
				}
				if (element.getTagName().equals("League")) {
					analyzeArena(nodelist.item(i).getChildNodes());
					continue;
				}

				if (element.getTagName().equals("ArenaName")) {
					setTag(element, arenaValue, 1);
					continue;
				}
				if (element.getTagName().equals("RebuiltDate")) {
					setTag(element, arenaValue, 13);
					continue;
				}
				if (element.getTagName().equals("Terraces")) {
					setTag(element, arenaValue, 2);
					continue;
				}
				if (element.getTagName().equals("Basic")) {
					setTag(element, arenaValue, 3);
					continue;
				}
				if (element.getTagName().equals("Roof")) {
					setTag(element, arenaValue, 4);
					continue;
				}
				if (element.getTagName().equals("VIP")) {
					setTag(element, arenaValue, 5);
					continue;
				}
				if (element.getTagName().equals("Total")) {
					setTag(element, arenaValue, 6);
					continue;
				}

			} // if
		} // for
	}

	protected void analyzeEconomy(NodeList nodelist) {

		for (int i = 0; i < nodelist.getLength(); i++) {
			if (nodelist.item(i) instanceof Element) {
				Element element = (Element) nodelist.item(i);
				if (element.getTagName().equals("HattrickData")) {
					analyzeEconomy(nodelist.item(i).getChildNodes());
				}
				if (element.getTagName().equals("Team")) {
					analyzeEconomy(nodelist.item(i).getChildNodes());
				}
				if (element.getTagName().equals("Cash")) {
					setTag(element, economyValue, 3);
				}

				if (element.getTagName().equals("SponsorsPopularity")) {
					setTag(element, economyValue, 2);
				}
				if (element.getTagName().equals("SupportersPopularity")) {
					setTag(element, economyValue, 1);
				}
				if (element.getTagName().equals("FanClubSize")) {
					setTag(element, clubValue, 11);
				}
				if (element.getTagName().equals("IncomeSpectators")) {
					setTag(element, economyValue, 5);
				}
				if (element.getTagName().equals("IncomeSponsors")) {
					setTag(element, economyValue, 4);
				}
				if (element.getTagName().equals("IncomeFinancial")) {
					setTag(element, economyValue, 6);
				}
				if (element.getTagName().equals("IncomeTemporary")) {
					setTag(element, economyValue, 7);
				}
				if (element.getTagName().equals("IncomeSum")) {
					setTag(element, economyValue, 8);
				}
				if (element.getTagName().equals("CostsArena")) {
					setTag(element, economyValue, 11);
				}
				if (element.getTagName().equals("CostsPlayers")) {
					setTag(element, economyValue, 9);
				}
				if (element.getTagName().equals("CostsFinancial")) {
					setTag(element, economyValue, 13);
				}
				if (element.getTagName().equals("CostsTemporary")) {
					setTag(element, economyValue, 14);
				}
				if (element.getTagName().equals("CostsStaff")) {
					setTag(element, economyValue, 10);
				}
				if (element.getTagName().equals("CostsYouth")) {
					setTag(element, economyValue, 12);
				}
				if (element.getTagName().equals("CostsSum")) {
					setTag(element, economyValue, 15);
				}
				if (element.getTagName().equals("ExpectedWeeksTotal")) {
					setTag(element, economyValue, 16);
				}
				if (element.getTagName().equals("LastIncomeSpectators")) {
					setTag(element, economyValue, 18);
				}
				if (element.getTagName().equals("LastIncomeSponsors")) {
					setTag(element, economyValue, 17);
				}
				if (element.getTagName().equals("LastIncomeFinancial")) {
					setTag(element, economyValue, 19);
				}
				if (element.getTagName().equals("LastIncomeTemporary")) {
					setTag(element, economyValue, 20);
				}
				if (element.getTagName().equals("LastIncomeSum")) {
					setTag(element, economyValue, 21);
				}
				if (element.getTagName().equals("LastCostsArena")) {
					setTag(element, economyValue, 24);
				}
				if (element.getTagName().equals("LastCostsPlayers")) {
					setTag(element, economyValue, 22);
				}
				if (element.getTagName().equals("LastCostsFinancial")) {
					setTag(element, economyValue, 26);
				}
				if (element.getTagName().equals("LastCostsTemporary")) {
					setTag(element, economyValue, 27);
				}
				if (element.getTagName().equals("LastCostsStaff")) {
					setTag(element, economyValue, 23);
				}
				if (element.getTagName().equals("LastCostsYouth")) {
					setTag(element, economyValue, 25);
				}
				if (element.getTagName().equals("LastCostsSum")) {
					setTag(element, economyValue, 28);
				}
				if (element.getTagName().equals("LastWeeksTotal")) {
					setTag(element, economyValue, 29);
				}
				//      ExpectedCash

			} // if element
		}//for
	}

	/**
	 * @param childNodes
	 */
	protected void analyzeTraining(NodeList nodelist) {

		for (int i = 0; i < nodelist.getLength(); i++) {
			if (nodelist.item(i) instanceof Element) {
				Element element = (Element) nodelist.item(i);
				if (element.getTagName().equals("HattrickData")) {
					analyzeTraining(nodelist.item(i).getChildNodes());
				}
				if (element.getTagName().equals("Team")) {
					analyzeTraining(nodelist.item(i).getChildNodes());
				}
				if (element.getTagName().equals("Trainer")) {
					analyzeTraining(nodelist.item(i).getChildNodes());
				}
				if (element.getTagName().equals("TrainingLevel")) {
					setTag(element, teamValue, 1);
					continue;
				}
				if (element.getTagName().equals("TrainingType")) {
					setTag(element, teamValue, 2);
					Integer skill = new Integer(teamValue[2][1]);
					teamValue[3][1] = RSC.MINIMODEL.getHelper()
							.getNameForTraining(skill.intValue());
					continue;
				}
				if (element.getTagName().equals("TrainerID")) {
					setTag(element, xtraValue, 8);
					continue;
				}
				if (element.getTagName().equals("TrainerName")) {
					setTag(element, xtraValue, 9);
					continue;
				}
				if (element.getTagName().equals("ArrivalDate")) {
					setTag(element, xtraValue, 10);
					continue;
				}
				if (element.getTagName().equals("Morale")) {
					setTag(element, teamValue, 4);
					Integer skill = new Integer(teamValue[4][1]);
					teamValue[5][1] = RSC.MINIMODEL.getHelper()
							.getNameForTeamspirit(skill.intValue());
					continue;
				}
				if (element.getTagName().equals("SelfConfidence")) {
					setTag(element, teamValue, 7);
					Integer skill = new Integer(teamValue[7][1]);
					teamValue[7][1] = RSC.MINIMODEL.getHelper()
							.getNameForConfidence(skill.intValue());
					continue;
				}
				if (element.getTagName().equals("Experience433")) {
					setTag(element, teamValue, 8);
					continue;
				}
				if (element.getTagName().equals("Experience451")) {
					setTag(element, teamValue, 9);
					continue;
				}
				if (element.getTagName().equals("Experience352")) {
					setTag(element, teamValue, 10);
					continue;
				}
				if (element.getTagName().equals("Experience532")) {
					setTag(element, teamValue, 11);
					continue;
				}
				if (element.getTagName().equals("Experience343")) {
					setTag(element, teamValue, 12);
					continue;
				}
				if (element.getTagName().equals("Experience541")) {
					setTag(element, teamValue, 13);
					continue;
				}

			} // if
		} // for

	}

	/**
	 * @param childNodes
	 */
	protected void analyzeWorldDetail(NodeList nodelist) {

		for (int i = 0; i < nodelist.getLength(); i++) {
			if (nodelist.item(i) instanceof Element) {
				Element element = (Element) nodelist.item(i);
				if (element.getTagName().equals("League")) {
					analyzeWorldDetail(nodelist.item(i).getChildNodes());
					continue;
				}
				if (element.getTagName().equals("LeagueList")) {
					analyzeWorldDetail(nodelist.item(i).getChildNodes());
					continue;
				}
				if (element.getTagName().equals("Country")) {
					analyzeWorldDetail(nodelist.item(i).getChildNodes());
					continue;
				}
				if (element.getTagName().equals("CurrencyName")) {
					setTag(element, xtraValue, 4);
					continue;
				}
				if (element.getTagName().equals("CurrencyRate")) {
					setTag(element, xtraValue, 5);
					continue;
				}
				if (element.getTagName().equals("CountryID")) {
					setTag(element, basicsValue, 12);
					continue;
				}
				if (element.getTagName().equals("MatchRound")) {
					setTag(element, basicsValue, 5);
					continue;
				}
				if (element.getTagName().equals("Season")) {
					setTag(element, basicsValue, 4);
					continue;
				}
				if (element.getTagName().equals("LeagueID")) {
					setTag(element, basicsValue, 13);
					continue;
				}
				if (element.getTagName().equals("TrainingDate")) {
					setTag(element, xtraValue, 1);
					continue;
				}
				if (element.getTagName().equals("EconomyDate")) {
					setTag(element, xtraValue, 2);
					continue;
				}
				if (element.getTagName().equals("SeriesMatchDate")) {
					setTag(element, xtraValue, 3);
					continue;
				}
			} // if
		}// for
	}

	protected void analyzeSinglePlayer(NodeList nodelist, int index) {
		for (int i = 0; i < nodelist.getLength(); i++) {
			if (nodelist.item(i) instanceof Element) {
				Element element = (Element) nodelist.item(i);

				if (element.getTagName().equals("PlayerID")) {
					setTag(element, playerValue, 0, index);
					playerValue[0][index] = playerValue[0][index] + "]";
					continue;
				}
				if (element.getTagName().equals("PlayerName")) {
					setTag(element, playerValue, 1, index);
					continue;
				}
				if (element.getTagName().equals("PlayerNumber")) {
					setTag(element, playerValue, 35, index);
					continue;
				}
				if (element.getTagName().equals("Age")) {
					setTag(element, playerValue, 2, index);
					continue;
				}
				if (element.getTagName().equals("InjuryLevel")) {
					setTag(element, playerValue, 3, index);
					continue;
				}
				if (element.getTagName().equals("TSI")) {
					setTag(element, playerValue, 16, index);
					continue;
				}
				if (element.getTagName().equals("PlayerForm")) {
					setTag(element, playerValue, 4, index);
					continue;
				}
				if (element.getTagName().equals("Experience")) {
					setTag(element, playerValue, 13, index);
					continue;
				}
				if (element.getTagName().equals("Leadership")) {
					setTag(element, playerValue, 14, index);
					continue;
				}
				if (element.getTagName().equals("Salary")) {
					setTag(element, playerValue, 15, index);
					continue;
				}
				if (element.getTagName().equals("Agreeability")) {
					setTag(element, playerValue, 26, index);
					continue;
				}
				if (element.getTagName().equals("Aggressiveness")) {
					setTag(element, playerValue, 30, index);
					continue;
				}
				if (element.getTagName().equals("Honesty")) {
					setTag(element, playerValue, 28, index);
					continue;
				}
				if (element.getTagName().equals("LeagueGoals")) {
					setTag(element, playerValue, 18, index);
					continue;
				}
				if (element.getTagName().equals("CupGoals")) {
					setTag(element, playerValue, 19, index);
					continue;
				}
				if (element.getTagName().equals("FriendliesGoals")) {
					setTag(element, playerValue, 20, index);
					continue;
				}
				if (element.getTagName().equals("CareerHattricks")) {
					setTag(element, playerValue, 21, index);
					continue;
				}
				if (element.getTagName().equals("CareerGoals")) {
					setTag(element, playerValue, 17, index);
					continue;
				}
				if (element.getTagName().equals("Cards")) {
					setTag(element, playerValue, 23, index);
					continue;
				}
				if (element.getTagName().equals("StaminaSkill")) {
					setTag(element, playerValue, 5, index);
					continue;
				}
				if (element.getTagName().equals("KeeperSkill")) {
					setTag(element, playerValue, 12, index);
					continue;
				}
				if (element.getTagName().equals("PlaymakerSkill")) {
					setTag(element, playerValue, 6, index);
					continue;
				}
				if (element.getTagName().equals("ScorerSkill")) {
					setTag(element, playerValue, 7, index);
					continue;
				}
				if (element.getTagName().equals("PassingSkill")) {
					setTag(element, playerValue, 8, index);
					continue;
				}
				if (element.getTagName().equals("WingerSkill")) {
					setTag(element, playerValue, 9, index);
					continue;
				}
				if (element.getTagName().equals("DefenderSkill")) {
					setTag(element, playerValue, 11, index);
					continue;
				}
				if (element.getTagName().equals("SetPiecesSkill")) {
					setTag(element, playerValue, 10, index);
					continue;
				}
				if (element.getTagName().equals("CountryID")) {
					setTag(element, playerValue, 22, index);
					continue;
				}
				if (element.getTagName().equals("Caps")) {
					setTag(element, playerValue, 38, index);
					continue;
				}
				if (element.getTagName().equals("CapsU20")) {
					setTag(element, playerValue, 39, index);
					continue;
				}
				if (element.getTagName().equals("Specialty")) {
					setTag(element, playerValue, 24, index);
					continue;
				}
				if (element.getTagName().equals("TransferListed")) {
					setTag(element, playerValue, 36, index);
					continue;
				}
				if (element.getTagName().equals("NationalTeamID")) {
					setTag(element, playerValue, 37, index);
					continue;
				}
				if (element.getTagName().equals("TrainerData")) {
					analyzeSinglePlayer(nodelist.item(i).getChildNodes(), index);
					continue;
				}
				if (element.getTagName().equals("TrainerType")) {
					setTag(element, playerValue, 32, index);
					continue;
				}
				if (element.getTagName().equals("TrainerSkill")) {
					setTag(element, playerValue, 33, index);
					continue;
				}
			}// if Element
		} // for
		playerValue[31][index] = RSC.MINIMODEL.getHelper()
				.getNameForAggressivness(
						Integer.parseInt(playerValue[30][index]));
		playerValue[29][index] = RSC.MINIMODEL.getHelper().getNameForCharacter(
				Integer.parseInt(playerValue[28][index]));
		playerValue[27][index] = RSC.MINIMODEL.getHelper()
				.getNameForGentleness(Integer.parseInt(playerValue[26][index]));
		if (!playerValue[24][index].equals("0"))
			playerValue[25][index] = RSC.MINIMODEL.getHelper()
					.getNameForSpeciality(
							Integer.parseInt(playerValue[24][index]));
	}

	// unused?
	protected final void analyzeAllPlayers(NodeList nodelist) {
		for (int i = 0; i < nodelist.getLength(); i++) {
			if (nodelist.item(i) instanceof Element) {
				if (nodelist.item(i).hasChildNodes()) {
					Element element = (Element) nodelist.item(i);
					if (element.getTagName().equals("Player")) {
						int index = Integer.parseInt(element.getAttribute("Index")); // TODO
						analyzeSinglePlayer(nodelist.item(i).getChildNodes(), index + 1);
						analyzeAllPlayers(nodelist.item(i).getChildNodes());
					}
				}
			} // if element
		} // for
	}

	protected final void initPlayersArray(NodeList nodelist) {
		for (int i = 0; i < nodelist.getLength(); i++) {
			if (nodelist.item(i) instanceof Element) {
				Element element = (Element) nodelist.item(i);
				//Downloader.writeToLog("Element",element.getTagName());
				if (element.getTagName().equals("HattrickData")) {
					initPlayersArray(nodelist.item(i).getChildNodes());
				}
				if (element.getTagName().equals("Team")) {
					initPlayersArray(nodelist.item(i).getChildNodes());
					continue;
				}
				if (element.getTagName().equals("PlayerList")) {
					//int tmp = Integer.parseInt(element.getAttribute("Count"));
					int tmp = element.getElementsByTagName("Player").getLength();
					initPlayerArray(tmp);
					setPlayerSkills(nodelist.item(i).getChildNodes());
					break;
				}
//				if (element.getTagName().equals("Player")) {
//					int index = Integer.parseInt(element.getAttribute("Index"));
//					analyzeSinglePlayer(nodelist.item(i).getChildNodes(),
//							index + 1);
//				}
			}// if Element
		} // for
	}

	protected final void setPlayerSkills(NodeList nodelist) {
		int playerCount = 0;
		for (int i = 0; i < nodelist.getLength(); i++) {
			if (nodelist.item(i) instanceof Element) {
				Element element = (Element) nodelist.item(i);
				if (element.getTagName().equals("Player")) {
					playerCount++;
					//System.out.println("Player " + playerCount + " - " + element.getElementsByTagName("PlayerName").item(0).getTextContent());
					analyzeSinglePlayer(nodelist.item(i).getChildNodes(), playerCount);
				}
			}
		}
	}

	protected void initPlayerArray(int playerCount) {
		playerValue = new String[40][playerCount + 1];
		playerValue[0][0] = "[player";
		playerValue[1][0] = "name=";
		playerValue[2][0] = "ald=";
		playerValue[3][0] = "ska=";
		playerValue[4][0] = "for=";
		playerValue[5][0] = "uth=";
		playerValue[6][0] = "spe=";
		playerValue[7][0] = "mal=";
		playerValue[8][0] = "fra=";
		playerValue[9][0] = "ytt=";
		playerValue[10][0] = "fas=";

		playerValue[11][0] = "bac=";
		playerValue[12][0] = "mlv=";
		playerValue[13][0] = "rut=";
		playerValue[14][0] = "led=";
		playerValue[15][0] = "sal=";
		playerValue[16][0] = "mkt=";
		playerValue[17][0] = "gev=";
		playerValue[18][0] = "gtl=";
		playerValue[19][0] = "gtc=";
		playerValue[20][0] = "gtt=";
		playerValue[21][0] = "hat=";

		playerValue[22][0] = "CountryID=";
		playerValue[23][0] = "warnings=";
		playerValue[24][0] = "speciality=";
		playerValue[25][0] = "specialityLabel=";
		playerValue[26][0] = "gentleness=";
		playerValue[27][0] = "gentlenessLabel=";
		playerValue[28][0] = "honesty=";
		playerValue[29][0] = "honestyLabel=";
		playerValue[30][0] = "Aggressiveness=";
		playerValue[31][0] = "AggressivenessLabel=";
		playerValue[32][0] = "TrainerType=";

		playerValue[33][0] = "TrainerSkill=";
		playerValue[34][0] = "rating=";
		playerValue[35][0] = "PlayerNumber=";
		playerValue[36][0] = "TransferListed=";
		playerValue[37][0] = "NationalTeamID=";
		playerValue[38][0] = "Caps=";
		playerValue[39][0] = "CapsU20=";

		for (int i = 1; i < playerValue[0].length; i++) {
			playerValue[0][i] = "";
			playerValue[1][i] = "";
			playerValue[2][i] = RSC.DEFAULT;
			playerValue[3][i] = RSC.DEFAULT;
			playerValue[4][i] = RSC.DEFAULT;
			playerValue[5][i] = RSC.DEFAULT;
			playerValue[6][i] = RSC.DEFAULT;
			playerValue[7][i] = RSC.DEFAULT;
			playerValue[8][i] = RSC.DEFAULT;
			playerValue[9][i] = RSC.DEFAULT;
			playerValue[10][i] = RSC.DEFAULT;

			playerValue[10][i] = RSC.DEFAULT;
			playerValue[11][i] = RSC.DEFAULT;
			playerValue[12][i] = RSC.DEFAULT;
			playerValue[13][i] = RSC.DEFAULT;
			playerValue[14][i] = RSC.DEFAULT;
			playerValue[15][i] = RSC.DEFAULT;
			playerValue[16][i] = RSC.DEFAULT;
			playerValue[17][i] = RSC.DEFAULT;
			playerValue[18][i] = RSC.DEFAULT;
			playerValue[19][i] = RSC.DEFAULT;
			playerValue[20][i] = RSC.DEFAULT;

			playerValue[20][i] = RSC.DEFAULT;
			playerValue[21][i] = RSC.DEFAULT;
			playerValue[22][i] = RSC.DEFAULT;
			playerValue[23][i] = "";
			playerValue[24][i] = RSC.DEFAULT;
			playerValue[25][i] = "";
			playerValue[26][i] = RSC.DEFAULT;
			playerValue[27][i] = "";
			playerValue[28][i] = RSC.DEFAULT;
			playerValue[29][i] = "";
			playerValue[30][i] = "";

			playerValue[30][i] = "";
			playerValue[31][i] = "";
			playerValue[32][i] = "";
			playerValue[33][i] = "";
			playerValue[34][i] = RSC.DEFAULT;
			playerValue[35][i] = RSC.DEFAULT;
		} // for
		// } // if
	}

	protected static final Document getDocument(File file)
			throws ParserConfigurationException, IOException, SAXException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(file);
	}
}
