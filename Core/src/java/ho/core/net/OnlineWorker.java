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
import ho.core.file.xml.XMLMatchArchivParser;
import ho.core.file.xml.XMLMatchLineupParser;
import ho.core.file.xml.XMLMatchOrderParser;
import ho.core.file.xml.XMLMatchesParser;
import ho.core.file.xml.XMLSpielplanParser;
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
import ho.core.model.match.MatchType;
import ho.core.model.match.Matchdetails;
import ho.core.model.player.ISpielerPosition;
import ho.core.model.player.Spieler;
import ho.core.model.player.SpielerPosition;
import ho.core.net.login.LoginWaitDialog;
import ho.core.training.TrainingManager;
import ho.core.util.HOLogger;
import ho.core.util.Helper;
import ho.core.util.StringUtils;
import ho.module.lineup.AufstellungsVergleichHistoryPanel;
import ho.module.lineup.Lineup;
import ho.module.lineup.substitution.model.Substitution;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * DOCUMENT ME!
 * 
 * @author thomas.werth
 */
public class OnlineWorker {

	private final static SimpleDateFormat HT_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static LoginWaitDialog waitDialog;

	/**
	 * Utility class - private constructor enforces noninstantiability.
	 */
	private OnlineWorker() {
	}

	/**
	 * Get and optionally save HRF
	 * 
	 */
	public static void getHrf() {
		// Show wait dialog
		LoginWaitDialog waitDlg = new LoginWaitDialog(HOMainFrame.instance(),
				false);
		waitDlg.setVisible(true);
		try {
			HOMainFrame homf = HOMainFrame.instance();
			HOVerwaltung hov = HOVerwaltung.instance();
			UserParameter up = ho.core.model.UserParameter.instance();

			String hrf = null;
			try {
				hrf = ConvertXml2Hrf.createHrf(waitDlg);
			} catch (IOException e) {
				waitDlg.setVisible(false);
				// Info
				String msg = getLangString("Downloadfehler")
						+ " : Error converting xml 2 HRF. Corrupt/Missing Data : ";
				setInfoMsg(msg, InfoPanel.FEHLERFARBE);
				Helper.showMessage(homf, msg + "\n" + e.toString() + "\n",
						getLangString("Fehler"), JOptionPane.ERROR_MESSAGE);
			}

			if (hrf != null) {
				if (hrf.indexOf("playingMatch=true") > -1) {
					waitDlg.setVisible(false);
					JOptionPane.showMessageDialog(homf,
							getLangString("NO_HRF_Spiel"),
							getLangString("NO_HRF_ERROR"), 1);
				} else if (hrf.indexOf("NOT AVAILABLE") > -1) {
					waitDlg.setVisible(false);
					JOptionPane.showMessageDialog(homf,
							getLangString("NO_HRF_ERROR"),
							getLangString("NO_HRF_ERROR"), 1);
				} else {
					// Create HOModelo from the hrf data
					HOModel homodel = new HRFStringParser().parse(hrf);
					if (homodel == null) {
						// Info
						setInfoMsg(getLangString("Importfehler"),
								InfoPanel.FEHLERFARBE);
						// Error
						Helper.showMessage(homf, getLangString("Importfehler"),
								getLangString("Fehler"),
								JOptionPane.ERROR_MESSAGE);
					} else {
						homodel.saveHRF();
						homodel.setSpielplan(hov.getModel().getSpielplan());

						// Add old players to the model
						homodel.setAllOldSpieler(DBManager.instance()
								.getAllSpieler());
						// Only update when the model is newer than existing
						if (isNewModel(homodel)) {
							Date lastTrainingDate = Calendar.getInstance()
									.getTime();
							Date lastEconomyDate = lastTrainingDate;
							if (hov.getModel().getXtraDaten().getTrainingDate() != null) {
								lastTrainingDate = new Date(hov.getModel()
										.getXtraDaten().getTrainingDate()
										.getTime());
								lastEconomyDate = new Date(hov.getModel()
										.getXtraDaten().getEconomyDate()
										.getTime());
							}
							// Reimport Skillup
							DBManager.instance().checkSkillup(homodel);
							// Show
							hov.setModel(homodel);
							// Recalculate Training
							// Training->Subskill calculation
							TrainingManager.instance().calculateTraining(
									DBManager.instance().getTrainingsVector());
							homodel.calcSubskills();
							AufstellungsVergleichHistoryPanel
									.setHRFAufstellung(
											homodel.getAufstellung(),
											homodel.getLastAufstellung());
							AufstellungsVergleichHistoryPanel
									.setAngezeigteAufstellung(new AufstellungCBItem(
											getLangString("AktuelleAufstellung"),
											homodel.getAufstellung()));
							homf.getAufstellungsPanel()
									.getAufstellungsPositionsPanel()
									.exportOldLineup("Actual");
							FileExtensionManager.extractLineup("Actual");

							// If training update happened, regenerate HOE Files
							if (homodel.getXtraDaten().getTrainingDate()
									.after(lastTrainingDate)) {
								HOLogger.instance().log(OnlineWorker.class,
										"Regenerate HOE Training Files");
								FileExtensionManager.trainingUpdate();
							}
							// If economy update happened, regenerate HOE Files
							if (homodel.getXtraDaten().getEconomyDate()
									.after(lastEconomyDate)) {
								HOLogger.instance().log(OnlineWorker.class,
										"Regenerate HOE Economy Files");
								FileExtensionManager.economyUpate();
							}
						}
						// Info
						setInfoMsg(getLangString("HRFErfolg"));

						try {
							waitDlg.setVisible(false);
							saveHRFToFile(hrf);
						} catch (IOException e) {
							Helper.showMessage(HOMainFrame.instance(),
									"Failed to save downloaded file.\nError: "
											+ e.getMessage(),
									getLangString("Fehler"),
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		} finally {
			waitDlg.setVisible(false);
		}
	}

	/**
	 * saugt das Archiv
	 * 
	 * @param teamId
	 *            null falls unnötig sonst im Format 2004-02-01
	 * @param firstDate
	 *            null falls unnötig sonst im Format 2004-02-01
	 * @param store
	 *            True if matches are to be downloaded and stored. False if only
	 *            a match list is wanted.
	 * 
	 * @return The list of MatchKurzInfo. This can be null on error, or empty.
	 */
	public static List<MatchKurzInfo> getMatchArchive(int teamId,
			Date firstDate, boolean store) {

		List<MatchKurzInfo> allMatches = new ArrayList<MatchKurzInfo>();
		GregorianCalendar tempBeginn = new GregorianCalendar();
		tempBeginn.setTime(firstDate);
		GregorianCalendar tempEnd = new GregorianCalendar();
		tempEnd.setTimeInMillis(tempBeginn.getTimeInMillis());
		tempEnd.add(Calendar.MONTH, 3);

		Date endDate = new Date();
		if (!tempEnd.before(endDate)) {
			tempEnd.setTimeInMillis(endDate.getTime());
		}

		String strDateFirst = HT_FORMAT.format(tempBeginn.getTime());
		String strDateLast = HT_FORMAT.format(tempEnd.getTime());

		// Show wait Dialog
		waitDialog = getWaitDialog();
		waitDialog.setVisible(true);
		try {
			String matchesString = "";
			while (tempBeginn.before(endDate)) {
				try {
					waitDialog.setValue(10);
					matchesString = MyConnector.instance().getMatchArchiv(
							teamId, strDateFirst, strDateLast);
					waitDialog.setValue(20);
				} catch (Exception e) {
					// Info
					String msg = getLangString("Downloadfehler")
							+ " : Error fetching MatchArchiv : ";
					setInfoMsg(msg, InfoPanel.FEHLERFARBE);
					Helper.showMessage(HOMainFrame.instance(), msg,
							getLangString("Fehler"), JOptionPane.ERROR_MESSAGE);
					waitDialog.setVisible(false);
					return null;
				}

				waitDialog.setValue(40);
				List<MatchKurzInfo> matches = XMLMatchArchivParser
						.parseMatchesFromString(matchesString);

				// Add the new matches to the list of all matches
				allMatches.addAll(matches);

				// Zeitfenster neu setzen
				tempBeginn.add(Calendar.MONTH, 3);
				tempEnd.add(Calendar.MONTH, 3);

				if (!tempEnd.before(endDate)) {
					tempEnd.setTimeInMillis(endDate.getTime());
				}

				strDateFirst = HT_FORMAT.format(tempBeginn.getTime());
				strDateLast = HT_FORMAT.format(tempEnd.getTime());
			}

			// Store in the db if store is true
			if (store && (allMatches.size() > 0)) {

				waitDialog.setValue(80);
				DBManager.instance().storeMatchKurzInfos(
						allMatches.toArray(new MatchKurzInfo[0]));

				// Store full info for all matches
				for (MatchKurzInfo match : allMatches) {
					// Only if not in the db
					if ((DBManager.instance().isMatchVorhanden(match
							.getMatchID()))
							&& (!DBManager.instance().isMatchLineupVorhanden(
									match.getMatchID()))
							&& (match.getMatchStatus() == MatchKurzInfo.FINISHED)) {
						downloadMatchData(match.getMatchID(),
								match.getMatchTyp(), false);
					}
				}
			}
		} finally {
			waitDialog.setVisible(false);
		}
		return allMatches;
	}

	/**
	 * Downloads a match with the given criteria and stores it in the database.
	 * If a match is already in the db, and refresh is false, nothing is
	 * downloaded.
	 * 
	 * @param matchId
	 *            ID for the match to be downloaded
	 * @param matchType
	 *            matchType for the match to be downloaded.
	 * @param refresh
	 *            If true the match will always be downloaded.
	 * 
	 * @return true if the match is in the db afterwards
	 */
	public static boolean downloadMatchData(int matchid, MatchType matchType,
			boolean refresh) {
		waitDialog = getWaitDialog();
		// Only download if not present in the database, or if refresh is true
		if (refresh || !DBManager.instance().isMatchVorhanden(matchid)
				|| !DBManager.instance().isMatchLineupVorhanden(matchid)) {
			try {
				int heimId = 0;
				int gastId = 0;
				MatchKurzInfo info = null;
				Matchdetails details;

				// Check if teams IDs are stored somewhere
				if (DBManager.instance().isMatchVorhanden(matchid)) {
					info = DBManager.instance().getMatchesKurzInfoByMatchID(
							matchid);
					heimId = info.getHeimID();
					gastId = info.getGastID();
				}

				// If ids not found, download matchdetails to obtain them.
				// Highlights will be missing.
				if ((heimId == 0) || (gastId == 0)) {
					waitDialog.setValue(10);
					details = fetchDetails(matchid, matchType, null, waitDialog);
					heimId = details.getHeimId();
					gastId = details.getGastId();
				}

				final MatchLineup lineup = getMatchlineup(matchid, matchType,
						heimId, gastId);

				if (lineup == null) {
					// Info
					HOMainFrame
							.instance()
							.getInfoPanel()
							.setLangInfoText(
									HOVerwaltung.instance().getLanguageString(
											"Downloadfehler")
											+ " : Error fetching Matchlineup :",
									InfoPanel.FEHLERFARBE);
					Helper.showMessage(HOMainFrame.instance(), HOVerwaltung
							.instance().getLanguageString("Downloadfehler")
							+ " : Error fetching Matchlineup :", HOVerwaltung
							.instance().getLanguageString("Fehler"),
							JOptionPane.ERROR_MESSAGE);

					return false;
				}

				// Get details with highlights.
				waitDialog.setValue(10);
				details = fetchDetails(matchid, matchType, lineup, waitDialog);

				if (details == null) {
					HOLogger.instance().error(
							OnlineWorker.class,
							"Error downloading match. Details is null: "
									+ matchid);
					return false;
				}

				// Create the MatchKurzInfo even if we got an old one.
				info = new MatchKurzInfo();
				info.setOrdersGiven(true);
				info.setGastID(lineup.getGastId());
				info.setGastName(lineup.getGastName());
				info.setGastTore(details.getGuestGoals());
				info.setHeimID(lineup.getHeimId());
				info.setHeimName(lineup.getHeimName());
				info.setHeimTore(details.getHomeGoals());
				info.setMatchDate(lineup.getStringSpielDate());
				info.setMatchID(matchid);
				info.setMatchStatus(MatchKurzInfo.FINISHED);
				info.setMatchTyp(lineup.getMatchTyp());

				boolean success = DBManager.instance().storeMatch(info,
						details, lineup);
				if (!success) {
					waitDialog.setVisible(false);
					return false;
				}
			} catch (Exception ex) {
				HOLogger.instance()
						.error(OnlineWorker.class,
								"downloadMatchData:  Error in downloading match: "
										+ ex);
				waitDialog.setVisible(false);
				return false;
			}
		}
		waitDialog.setVisible(false);
		return true;
	}

	/**
	 * Loads the data for the given match from HT and updates the data for this
	 * match in the DB.
	 * 
	 * @param teamId
	 *            the id of the team
	 * @param match
	 *            the match to update
	 * @return a new MatchKurzInfo object with the current data from HT or null
	 *         if match could not be downloaded.
	 */
	public static MatchKurzInfo updateMatch(int teamId, MatchKurzInfo match) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(match.getMatchDateAsTimestamp());
		cal.add(Calendar.MINUTE, 1);
		// At the moment, HT does not support getting a single match.
		List<MatchKurzInfo> matches = getMatches(teamId, cal.getTime());
		for (MatchKurzInfo m : matches) {
			if (m.getMatchID() == match.getMatchID()) {
				DBManager.instance().updateMatchKurzInfo(m);
				return m;
			}
		}
		return null;
	}

	/**
	 * Gets the most recent and upcoming matches for a given teamId and up to a
	 * specific date. Nothing is stored to DB.
	 * 
	 * @param teamId
	 *            the id of the team.
	 * @param date
	 *            last date (+time) to get matches to.
	 * @return the most recent and upcoming matches up to the given date.
	 */
	public static List<MatchKurzInfo> getMatches(int teamId, Date date) {
		String matchesString = null;
		try {
			matchesString = MyConnector.instance().getMatches(teamId, true,
					date);
		} catch (IOException e) {
			HOVerwaltung hov = HOVerwaltung.instance();
			Helper.showMessage(HOMainFrame.instance(),
					hov.getLanguageString("Downloadfehler")
							+ " : Error fetching matches : " + e,
					hov.getLanguageString("Fehler"), JOptionPane.ERROR_MESSAGE);
			HOLogger.instance().log(OnlineWorker.class, e);
		}

		if (!StringUtils.isEmpty(matchesString)) {
			return XMLMatchesParser.parseMatchesFromString(matchesString);
		}

		return new ArrayList<MatchKurzInfo>();
	}

	/**
	 * saugt den Spielplan
	 * 
	 * @param teamId
	 *            angabe der Saison ( optinal &lt; 1 für aktuelle
	 * @param forceRefresh
	 *            TODO Missing Constructuor Parameter Documentation
	 * @param store
	 *            true if the full match details are to be stored, false if not.
	 * @param upcoming
	 *            true if upcoming matches should be included
	 * 
	 * @return The list of MatchKurzInfos found or null if an exception
	 *         occurred.
	 */
	public static List<MatchKurzInfo> getMatches(int teamId,
			boolean forceRefresh, boolean store, boolean upcoming) {
		String matchesString = "";
		List<MatchKurzInfo> matches = new ArrayList<MatchKurzInfo>();
		boolean bOK = false;
		waitDialog = getWaitDialog();
		waitDialog.setVisible(true);
		waitDialog.setValue(10);

		try {
			matchesString = MyConnector.instance().getMatches(teamId,
					forceRefresh, upcoming);
			bOK = (matchesString != null && matchesString.length() > 0);
			if (bOK)
				waitDialog.setValue(50);
			else
				waitDialog.setVisible(false);
		} catch (Exception e) {
			// Info
			HOMainFrame
					.instance()
					.getInfoPanel()
					.setLangInfoText(
							HOVerwaltung.instance().getLanguageString(
									"Downloadfehler")
									+ " : Error fetching matches: " + e,
							InfoPanel.FEHLERFARBE);
			Helper.showMessage(HOMainFrame.instance(), HOVerwaltung.instance()
					.getLanguageString("Downloadfehler")
					+ " : Error fetching matches : " + e, HOVerwaltung
					.instance().getLanguageString("Fehler"),
					JOptionPane.ERROR_MESSAGE);
			HOLogger.instance().log(OnlineWorker.class, e);
			waitDialog.setVisible(false);
			return null;
		}
		if (bOK) {
			matches.addAll(XMLMatchesParser
					.parseMatchesFromString(matchesString));

			// Store in DB if store is true
			if (store) {
				waitDialog.setValue(80);
				DBManager.instance().storeMatchKurzInfos(
						matches.toArray(new MatchKurzInfo[matches.size()]));

				waitDialog.setValue(100);

				// Automatically download all MatchLineups
				for (MatchKurzInfo match : matches) {
					int curMatchId = match.getMatchID();
					if (DBManager.instance().isMatchVorhanden(curMatchId)
							&& match.getMatchStatus() == MatchKurzInfo.FINISHED
							&& (!DBManager.instance().isMatchLineupVorhanden(
									curMatchId))) {

						// No match in DB
						boolean result = downloadMatchData(curMatchId,
								match.getMatchTyp(), false);
						if (!result) {
							bOK = false;
							break;
						}
					}
				}
			}
		}
		waitDialog.setVisible(false);
		return matches;
	}

	/**
	 * saugt das Matchlineup
	 * 
	 * @param matchId
	 *            Die ID des Matches
	 * @param teamId1
	 *            Erste Teamid (pflicht)
	 * @param teamId2
	 *            Zweite Teamid (optional auch -1)
	 * 
	 * @return TODO Missing Return Method Documentation
	 */
	private static MatchLineup getMatchlineup(int matchId, MatchType matchType,
			int teamId1, int teamId2) {
		boolean bOK = false;
		MatchLineup lineUp1 = null;
		MatchLineup lineUp2 = null;

		// Wait Dialog zeigen
		waitDialog = getWaitDialog();
		waitDialog.setVisible(true);
		waitDialog.setValue(10);

		// Lineups holen
		lineUp1 = fetchLineup(matchId, teamId1, matchType);
		if (lineUp1 != null) {
			bOK = true;
			waitDialog.setValue(50);
			if (teamId2 > 0)
				lineUp2 = fetchLineup(matchId, teamId2, matchType);

			// Merge the two
			if ((lineUp2 != null)) {
				if (lineUp1.getHeim() == null)
					lineUp1.setHeim((MatchLineupTeam) lineUp2.getHeim());
				else if (lineUp1.getGast() == null)
					lineUp1.setGast((MatchLineupTeam) lineUp2.getGast());
			} else {
				// Get the 2nd lineup
				if (lineUp1.getHeim() == null) {
					lineUp2 = fetchLineup(matchId, lineUp1.getHeimId(),
							matchType);
					if (lineUp2 != null)
						lineUp1.setHeim((MatchLineupTeam) lineUp2.getHeim());
				} else {
					lineUp2 = fetchLineup(matchId, lineUp1.getGastId(),
							matchType);
					if (lineUp2 != null)
						lineUp1.setGast((MatchLineupTeam) lineUp2.getGast());
				}
			}
		}
		waitDialog.setVisible(false);
		return lineUp1;
	}

	/**
	 * Get the Fixtures list
	 * 
	 * @param season
	 *            - The season, -1 for current
	 * @param leagueID
	 *            - The ID of the league to get the fixtures for
	 * 
	 * @return true on sucess, false on failure
	 */
	public static boolean getSpielplan(int season, int leagueID) {
		boolean bOK = false;
		String leagueFixtures = "";
		HOVerwaltung hov = HOVerwaltung.instance();
		waitDialog = getWaitDialog();
		waitDialog.setVisible(true);
		try {
			waitDialog.setValue(10);
			leagueFixtures = MyConnector.instance().getLeagueFixtures(season,
					leagueID);
			bOK = (leagueFixtures != null && leagueFixtures.length() > 0);
			waitDialog.setValue(50);
		} catch (Exception e) {
			HOLogger.instance().log(OnlineWorker.class, e);
			HOMainFrame
					.instance()
					.getInfoPanel()
					.setLangInfoText(
							hov.getLanguageString("Downloadfehler")
									+ " : Error fetching leagueFixture: "
									+ e.getMessage(), InfoPanel.FEHLERFARBE);
			Helper.showMessage(
					HOMainFrame.instance(),
					hov.getLanguageString("Downloadfehler")
							+ " : Error fetching leagueFixture: "
							+ e.getMessage(), hov.getLanguageString("Fehler"),
					JOptionPane.ERROR_MESSAGE);
			waitDialog.setVisible(false);
			return false;
		}
		if (bOK) {
			HOModel hom = hov.getModel();
			final XMLSpielplanParser parser = new XMLSpielplanParser();
			hom.setSpielplan(parser.parseSpielplanFromString(leagueFixtures));
			waitDialog.setValue(70);
			// Save to DB
			hom.saveSpielplan2DB();
			waitDialog.setValue(90);
		}
		waitDialog.setVisible(false);
		return bOK;
	}

	protected static LoginWaitDialog getWaitDialog() {
		if (waitDialog != null) {
			return waitDialog;
		}
		return new LoginWaitDialog(HOMainFrame.instance(), false);
	}

	/**
	 * Uploads the given order to Hattrick
	 * 
	 * @param matchId
	 *            The id of the match in question. If left at 0 the match ID
	 *            from the model will be used (next match).
	 * @param lineup
	 *            The lineup object to be uploaded
	 * @return A string response with any error message
	 */

	public static String uploadMatchOrder(int matchId, MatchType matchType,
			Lineup lineup) {

		String result;
		// Tell the Connector that we will require match order rights.

		// Building the order string as described in the match order API piece
		// by piece.

		StringBuilder orders = new StringBuilder();
		orders.append("{\"positions\":[");
		orders.append(createPositionString(ISpielerPosition.keeper, lineup));

		for (int i = ISpielerPosition.rightBack; i <= ISpielerPosition.substInnerMidfield; i++) {
			orders.append(',').append(createPositionString(i, lineup));
		}
		orders.append(',').append(
				createPositionString(ISpielerPosition.substForward, lineup));
		orders.append(',').append(
				createPositionString(ISpielerPosition.substWinger, lineup));

		orders.append(',').append("{\"id\":\"").append(lineup.getKapitaen());
		orders.append("\",\"behaviour\":\"0\"}");
		orders.append(',').append("{\"id\":\"").append(lineup.getKicker());
		orders.append("\",\"behaviour\":\"0\"}");

		// penalty takers
		List<SpielerPosition> shooters = lineup.getPenaltyTakers();
		for (SpielerPosition pos : shooters) {
			orders.append(',').append("{\"id\":\"").append(pos.getSpielerId());
			orders.append("\" , \"behaviour\":\"0\"}");
		}

		orders.append("], \"settings\":{\"tactic\": \"").append(
				lineup.getTacticType());
		orders.append("\",\"speechLevel\":\"").append(lineup.getAttitude());
		orders.append("\", \"newLineup\":\"\"},");
		orders.append("\"substitutions\":[");

		Iterator<Substitution> iter = lineup.getSubstitutionList().iterator();
		while (iter.hasNext()) {
			Substitution sub = iter.next();
			orders.append("{\"playerin\":\"").append(sub.getObjectPlayerID())
					.append("\",");
			orders.append("\"playerout\":\"").append(sub.getSubjectPlayerID())
					.append("\",");
			orders.append("\"orderType\":\"")
					.append(sub.getOrderType().getId()).append("\",");
			orders.append("\"min\":\"").append(sub.getMatchMinuteCriteria())
					.append("\",");
			orders.append("\"pos\":\"").append(sub.getPos()).append("\",");
			orders.append("\"beh\":\"").append(sub.getBehaviour())
					.append("\",");
			orders.append("\"card\":\"")
					.append(sub.getRedCardCriteria().getId()).append("\",");
			orders.append("\"standing\":\"").append(sub.getStanding().getId())
					.append("\"}");
			if (iter.hasNext()) {
				orders.append(',');
			}
		}
		orders.append("]}");

		try {
			result = MyConnector.instance().setMatchOrder(matchId, matchType,
					orders.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		HOLogger.instance()
				.debug(OnlineWorker.class, "Upload done:\n" + result);
		return result;
	}

	private static String createPositionString(int roleId,
			ho.module.lineup.Lineup lineup) {

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
	 * @param matchID
	 *            TODO Missing Method Parameter Documentation
	 * @param waitDialog
	 *            TODO Missing Method Parameter Documentation
	 * 
	 * @return TODO Missing Return Method Documentation
	 */
	private static Matchdetails fetchDetails(int matchID, MatchType matchType,
			MatchLineup lineup, LoginWaitDialog waitDialog) {
		String matchDetails = "";
		Matchdetails details = null;

		try {
			matchDetails = MyConnector.instance().getMatchdetails(matchID,
					matchType);
			if (matchDetails.length() == 0) {
				HOLogger.instance().warning(OnlineWorker.class,
						"Unable to fetch details for match " + matchID);
				return null;
			}
			waitDialog.setValue(20);
			final xmlMatchdetailsParser parser = new xmlMatchdetailsParser();
			details = parser.parseMachtdetailsFromString(matchDetails, lineup);
			waitDialog.setValue(40);
			if (details == null) {
				HOLogger.instance().warning(OnlineWorker.class,
						"Unable to fetch details for match " + matchID);
				return null;
			}
			String arenaString = MyConnector.instance().getArena(
					details.getArenaID());
			waitDialog.setValue(50);
			String regionIdAsString = XMLArenaParser.parseArenaFromString(
					arenaString).get("RegionID");
			int regionId = Integer.parseInt(regionIdAsString);
			details.setRegionId(regionId);
		} catch (Exception e) {
			// Info
			HOMainFrame
					.instance()
					.getInfoPanel()
					.setLangInfoText(
							HOVerwaltung.instance().getLanguageString(
									"Downloadfehler")
									+ ": Error fetching Matchdetails XML.: ",
							InfoPanel.FEHLERFARBE);
			Helper.showMessage(HOMainFrame.instance(), HOVerwaltung.instance()
					.getLanguageString("Downloadfehler")
					+ ": Error fetching Matchdetails XML.: ", HOVerwaltung
					.instance().getLanguageString("Fehler"),
					JOptionPane.ERROR_MESSAGE);
			waitDialog.setVisible(false);
			return null;
		}
		waitDialog.setVisible(false);
		return details;
	}

	/**
	 * TODO Missing Method Documentation
	 * 
	 * @param matchID
	 *            TODO Missing Method Parameter Documentation
	 * @param teamID
	 *            TODO Missing Method Parameter Documentation
	 * 
	 * @return TODO Missing Return Method Documentation
	 */
	protected static MatchLineup fetchLineup(int matchID, int teamID,
			MatchType matchType) {
		String matchLineup = "";
		MatchLineup lineUp = null;
		boolean bOK = false;
		try {
			matchLineup = MyConnector.instance().getMatchLineup(matchID,
					teamID, matchType);
			bOK = (matchLineup != null && matchLineup.length() > 0);
		} catch (Exception e) {
			// Info
			HOMainFrame
					.instance()
					.getInfoPanel()
					.setLangInfoText(
							HOVerwaltung.instance().getLanguageString(
									"Downloadfehler")
									+ " : Error fetching Matchlineup :",
							InfoPanel.FEHLERFARBE);
			Helper.showMessage(HOMainFrame.instance(), HOVerwaltung.instance()
					.getLanguageString("Downloadfehler")
					+ " : Error fetching Matchlineup :", HOVerwaltung
					.instance().getLanguageString("Fehler"),
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		if (bOK) {
			final XMLMatchLineupParser parser = new XMLMatchLineupParser();
			lineUp = parser.parseMatchLineupFromString(matchLineup);
		}
		return lineUp;
	}

	/**
	 * Get all lineups for MatchKurzInfos, if they're not there already
	 */
	public static void getAllLineups() {
		final MatchKurzInfo[] infos = DBManager.instance().getMatchesKurzInfo(
				-1);
		String haveLineups = "";
		boolean bOK = false;
		for (int i = 0; i < infos.length; i++) {
			int curMatchId = infos[i].getMatchID();
			if (!DBManager.instance().isMatchLineupVorhanden(curMatchId)) {
				// Check if the lineup is available
				if (infos[i].getMatchStatus() == MatchKurzInfo.FINISHED) {
					HOLogger.instance().debug(OnlineWorker.class,
							"Get Lineup : " + curMatchId);
					bOK = downloadMatchData(curMatchId, infos[i].getMatchTyp(),
							false);
					if (!bOK) {
						break;
					}
				} else
					HOLogger.instance().debug(OnlineWorker.class,
							"Not Played : " + curMatchId);
			} else {
				// Match lineup already available
				if (haveLineups.length() > 0)
					haveLineups += ", ";
				haveLineups += curMatchId;
			}
		}
		if (haveLineups.length() > 0)
			HOLogger.instance().debug(OnlineWorker.class,
					"Have Lineups : " + haveLineups);
	}

	/**
	 * 
	 * @param matchId
	 *            The match ID for the match to download
	 * @param matchType
	 *            The matchTyp for the match to download
	 * @return The Lineup object with the downloaded match data
	 */
	public static Lineup getLineupbyMatchId(int matchId, MatchType matchType) {

		try {
			String xml = MyConnector.instance().getMatchOrder(matchId,
					matchType);
			if (!StringUtils.isEmpty(xml)) {
				Map<String, String> map = XMLMatchOrderParser
						.parseMatchOrderFromString(xml);
				String trainerID = String.valueOf(HOVerwaltung.instance()
						.getModel().getTrainer().getSpielerID());
				String lineupData = ConvertXml2Hrf.createLineUp(trainerID, map);
				return new Lineup(getProperties(lineupData));
			}
		} catch (Exception e) {
			HOMainFrame
					.instance()
					.getInfoPanel()
					.setLangInfoText(
							HOVerwaltung.instance().getLanguageString(
									"Downloadfehler")
									+ " : Error fetching Matchorder :",
							InfoPanel.FEHLERFARBE);
			Helper.showMessage(HOMainFrame.instance(), HOVerwaltung.instance()
					.getLanguageString("Downloadfehler")
					+ " : Error fetching Matchorder :", HOVerwaltung.instance()
					.getLanguageString("Fehler"), JOptionPane.ERROR_MESSAGE);
			HOLogger.instance().error(OnlineWorker.class, e.getMessage());
		}

		return null;
	}

	private static Properties getProperties(String data) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(
				data.getBytes("UTF-8"));
		InputStreamReader isr = new InputStreamReader(bis, "UTF-8");
		BufferedReader hrfReader = new BufferedReader(isr);
		Properties properties = new Properties();

		// Lose the first line
		hrfReader.readLine();
		while (hrfReader.ready()) {
			String lineString = hrfReader.readLine();
			// Ignore empty lines
			if (!StringUtils.isEmpty(lineString)) {
				int indexEqualsSign = lineString.indexOf('=');
				if (indexEqualsSign > 0) {
					properties.setProperty(
							lineString.substring(0, indexEqualsSign)
									.toLowerCase(java.util.Locale.ENGLISH),
							lineString.substring(indexEqualsSign + 1));
				}
			}
		}
		return properties;
	}

	private static boolean isNewModel(HOModel homodel) {
		return (homodel != null && ((HOVerwaltung.instance().getModel() == null) || (homodel
				.getBasics().getDatum().after(HOVerwaltung.instance()
				.getModel().getBasics().getDatum()))));
	}

	/**
	 * Shows a file chooser asking for the location for the HRF file and saves
	 * it to the location chosen by the user.
	 * 
	 * @param hrfData
	 *            the HRF data as string
	 * @throws IOException
	 */
	private static void saveHRFToFile(String hrfData) throws IOException {
		setInfoMsg(getLangString("HRFSave"));

		File path = new File(UserParameter.instance().hrfImport_HRFPath);
		File file = new File(path, getHRFFileName());
		// Show dialog if path not set or the file already exists
		if (UserParameter.instance().showHRFSaveDialog || !path.exists()
				|| !path.isDirectory() || file.exists()) {
			file = askForHRFPath(file);
		}

		if ((file != null) && (file.getPath() != null)) {
			// Save Path
			UserParameter.instance().hrfImport_HRFPath = file.getParentFile()
					.getAbsolutePath();

			// File exists?
			int value = JOptionPane.OK_OPTION;
			if (file.exists()) {
				value = JOptionPane.showConfirmDialog(HOMainFrame.instance(),
						getLangString("overwrite"), "",
						JOptionPane.YES_NO_OPTION);
			}

			// Save
			if (value == JOptionPane.OK_OPTION) {
				saveFile(file.getPath(), hrfData);
			} else {
				// Canceled
				setInfoMsg(getLangString("HRFAbbruch"), InfoPanel.FEHLERFARBE);
			}
		}

	}

	/**
	 * Gets a HRF file name, based on the current date.
	 * 
	 * @return the HRF file name.
	 */
	private static String getHRFFileName() {
		GregorianCalendar calendar = (GregorianCalendar) Calendar.getInstance();
		StringBuilder builder = new StringBuilder();
		builder.append(calendar.get(Calendar.YEAR));
		builder.append('-');
		int month = calendar.get(Calendar.MONTH) + 1;
		if (month < 10) {
			builder.append('0');
		}
		builder.append(month);
		builder.append('-');
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		if (day < 10) {
			builder.append('0');
		}
		builder.append(day);
		builder.append(".hrf");
		String name = calendar.get(Calendar.YEAR) + "-" + month + "-" + day
				+ ".hrf";
		return builder.toString();
	}

	/**
	 * Shows a file chooser dialog to ask the user for the location to save the
	 * HRF file.
	 * 
	 * @param file
	 *            the recommendation for the file name/location.
	 * @return the file location choosen by the user or null if the canceled the
	 *         dialog.
	 */
	private static File askForHRFPath(File file) {
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
		fileChooser.setDialogTitle(getLangString("FileExport"));
		ExampleFileFilter filter = new ExampleFileFilter();
		filter.addExtension("hrf");
		filter.setDescription("Hattrick HRF");
		fileChooser.setFileFilter(filter);
		File path = file.getParentFile();
		if (path.exists() && path.isDirectory()) {
			fileChooser.setCurrentDirectory(path);
		}
		fileChooser.setSelectedFile(file);
		int returnVal = fileChooser.showSaveDialog(HOMainFrame.instance());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File f = fileChooser.getSelectedFile();
			// File doesn't end with .hrf?
			if (!f.getPath().endsWith(".hrf")) {
				f = new java.io.File(file.getAbsolutePath() + ".hrf");
			}
			return f;
		}
		return null;
	}

	/**
	 * Convenience method for
	 * HOMainFrame.instance().getInfoPanel().setLangInfoText(msg);
	 * 
	 * @param msg
	 *            the message to show
	 */
	private static void setInfoMsg(String msg) {
		HOMainFrame.instance().getInfoPanel().setLangInfoText(msg);
	}

	/**
	 * Convenience method for
	 * HOMainFrame.instance().getInfoPanel().setLangInfoText(msg, color);
	 * 
	 * @param msg
	 *            the message to show
	 * @param color
	 *            the color
	 */
	private static void setInfoMsg(String msg, Color color) {
		HOMainFrame.instance().getInfoPanel().setLangInfoText(msg, color);
	}

	/**
	 * Convenience method for HOVerwaltung.instance().getLanguageString(key)
	 * 
	 * @param key
	 *            the key for the language string
	 * @return the string for the current language
	 */
	private static String getLangString(String key) {
		return HOVerwaltung.instance().getLanguageString(key);
	}

	/**
	 * Save the passed in data to the passed in file
	 * 
	 * @param fileName
	 *            Name of the file to save the data to
	 * @param content
	 *            The content to write to the file
	 * 
	 * @return The saved file
	 * @throws IOException
	 */
	private static File saveFile(String fileName, String content)
			throws IOException {
		File outFile = new File(fileName);
		if (outFile.exists()) {
			outFile.delete();
		}
		outFile.createNewFile();
		OutputStreamWriter outWrit = new OutputStreamWriter(
				new FileOutputStream(outFile), "UTF-8");
		BufferedWriter out = new BufferedWriter(outWrit);
		out.write(content);
		out.newLine();
		out.close();
		return outFile;
	}
}
