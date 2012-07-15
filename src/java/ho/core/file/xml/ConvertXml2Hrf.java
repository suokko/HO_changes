// %929884203:de.hattrickorganizer.net%
/*
 * ConvertXml2Hrf.java
 *
 * Created on 12. Januar 2004, 09:44
 */
package ho.core.file.xml;

import ho.core.constants.TeamConfidence;
import ho.core.constants.TeamSpirit;
import ho.core.constants.TrainingType;
import ho.core.constants.player.PlayerAggressiveness;
import ho.core.constants.player.PlayerAgreeability;
import ho.core.constants.player.PlayerHonesty;
import ho.core.constants.player.PlayerSpeciality;
import ho.core.model.match.MatchKurzInfo;
import ho.core.model.match.MatchLineup;
import ho.core.model.match.MatchLineupTeam;
import ho.core.model.match.MatchType;
import ho.core.model.match.Matchdetails;
import ho.core.model.player.ISpielerPosition;
import ho.core.net.MyConnector;
import ho.core.net.login.LoginWaitDialog;
import ho.core.util.HOLogger;
import ho.core.util.IOUtils;
import ho.module.lineup.substitution.model.Substitution;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

/**
 * Convert the necessary xml data into a HRF file.
 * 
 * @author thomas.werth
 */
public class ConvertXml2Hrf {

	/**
	 * Utility class - private constructor enforces noninstantiability.
	 */
	private ConvertXml2Hrf() {
	}

	/**
	 * Create the HRF data and return it in one string.
	 * 
	 * @throws IOException
	 */
	public static String createHrf(LoginWaitDialog waitDialog)
			throws IOException {
		// init
		StringBuilder buffer = new StringBuilder();

		// Hashtable's füllen
		final MyConnector mc = MyConnector.instance();
		waitDialog.setValue(5);
		Map<String, String> teamdetailsDataMap = XMLTeamDetailsParser
				.parseTeamdetailsFromString(mc.getTeamdetails(-1));
		waitDialog.setValue(10);
		Map<String, String> clubDataMap = XMLClubParser.parseClubFromString(mc.getVerein());
		waitDialog.setValue(15);
		Map<String, String> ligaDataMap = XMLLeagueDetailsParser.parseLeagueDetailsFromString(mc.getLeagueDetails(),
						teamdetailsDataMap.get("TeamID").toString());
		waitDialog.setValue(20);
		Map<String, String> worldDataMap = XMLWorldDetailsParser.parseWorldDetailsFromString(
						mc.getWorldDetails(Integer.parseInt(teamdetailsDataMap
								.get("LeagueID").toString())),
						teamdetailsDataMap.get("LeagueID").toString());
		waitDialog.setValue(25);
		MatchLineup matchLineup = XMLMatchLineupParser.parseMatchLineupFromString(mc.getMatchLineup(-1, -1,
						MatchType.LEAGUE).toString());
		waitDialog.setValue(30);
		List<MyHashtable> playersData = new xmlPlayersParser()
				.parsePlayersFromString(mc.getPlayers());
		waitDialog.setValue(35);
		Map<String, String> economyDataMap = XMLEconomyParser
				.parseEconomyFromString(mc.getEconomy());
		waitDialog.setValue(40);
		Map<?, ?> trainingDataMap = new XMLTrainingParser()
				.parseTrainingFromString(mc.getTraining());
		waitDialog.setValue(45);
		Map<String, String> arenaDataMap = XMLArenaParser
				.parseArenaFromString(mc.getArena());

		// MatchOrder
		waitDialog.setValue(50);
		List<MatchKurzInfo> matches = XMLMatchesParser
				.parseMatchesFromString(mc.getMatches(Integer
						.parseInt(teamdetailsDataMap.get("TeamID").toString()),
						false, true));
		waitDialog.setValue(52);

		// Automatisch alle MatchLineups runterladen
		Map<?, ?> nextLineupDataMap = null;
		for (MatchKurzInfo match : matches) {
			if (match.getMatchStatus() == MatchKurzInfo.UPCOMING) {
				waitDialog.setValue(54);
				// Match is always from the normal system, and league will do
				// the trick as the type.
				nextLineupDataMap = XMLMatchOrderParser
						.parseMatchOrderFromString(mc.getMatchOrder(
								match.getMatchID(), MatchType.LEAGUE));
				break;
			}
		}

		waitDialog.setValue(55);

		MatchLineupTeam matchLineupTeam = null;
		int lastAttitude = 0;
		int lastTactic = 0;
		// Team ermitteln, für Ratings der Player wichtig
		if (matchLineup != null) {
			Matchdetails md = XMLMatchdetailsParser
					.parseMachtdetailsFromString(
							mc.getMatchdetails(matchLineup.getMatchID(),
									matchLineup.getMatchTyp()), null);

			if (matchLineup.getHeimId() == Integer.parseInt(teamdetailsDataMap
					.get("TeamID").toString())) {
				matchLineupTeam = (MatchLineupTeam) matchLineup.getHeim();
				if (md != null) {
					lastAttitude = md.getHomeEinstellung();
					lastTactic = md.getHomeTacticType();
				}
			} else {
				matchLineupTeam = (MatchLineupTeam) matchLineup.getGast();
				if (md != null) {
					lastAttitude = md.getGuestEinstellung();
					lastTactic = md.getGuestTacticType();
				}
			}
		}

		// Abschnitte erstellen
		waitDialog.setValue(60);

		// basics
		createBasics(teamdetailsDataMap, worldDataMap, buffer);
		waitDialog.setValue(65);

		// Liga
		createLeague(ligaDataMap, buffer);
		waitDialog.setValue(70);

		// Club
		createClub(clubDataMap, economyDataMap, teamdetailsDataMap, buffer);
		waitDialog.setValue(75);

		// team
		createTeam(trainingDataMap, buffer);
		waitDialog.setValue(80);

		// lineup
		buffer.append(createLineUp(
				String.valueOf(teamdetailsDataMap.get("TrainerID")),
				nextLineupDataMap));
		waitDialog.setValue(85);

		// economy
		createEconemy(economyDataMap, buffer);
		waitDialog.setValue(90);

		// Arena
		createArena(arenaDataMap, buffer);
		waitDialog.setValue(93);

		// players
		createPlayers(matchLineupTeam, playersData, buffer);
		waitDialog.setValue(96);

		// xtra Data
		createWorld(clubDataMap, teamdetailsDataMap, trainingDataMap,
				worldDataMap, buffer);
		waitDialog.setValue(99);

		// lineup from the last match
		createLastLineUp(teamdetailsDataMap, matchLineupTeam, lastAttitude,
				lastTactic, buffer);
		waitDialog.setValue(100);

		// dialog zum Saven anzeigen
		// speichern
		// writeHRF( dateiname );
		return buffer.toString();
	}

	/**
	 * Create the arena data.
	 */
	private static void createArena(Map<?, ?> arenaDataMap, StringBuilder buffer) {
		buffer.append("[arena]").append('\n');
		buffer.append("arenaname=").append(arenaDataMap.get("ArenaName"))
				.append('\n');
		buffer.append("arenaid=").append(arenaDataMap.get("ArenaID"))
				.append('\n');
		buffer.append("antalStaplats=").append(arenaDataMap.get("Terraces"))
				.append('\n');
		buffer.append("antalSitt=").append(arenaDataMap.get("Basic"))
				.append('\n');
		buffer.append("antalTak=").append(arenaDataMap.get("Roof"))
				.append('\n');
		buffer.append("antalVIP=").append(arenaDataMap.get("VIP")).append('\n');
		buffer.append("seatTotal=").append(arenaDataMap.get("Total"))
				.append('\n');
		buffer.append("expandingStaplats=")
				.append(arenaDataMap.get("ExTerraces")).append('\n');
		buffer.append("expandingSitt=").append(arenaDataMap.get("ExBasic"))
				.append('\n');
		buffer.append("expandingTak=").append(arenaDataMap.get("ExRoof"))
				.append('\n');
		buffer.append("expandingVIP=").append(arenaDataMap.get("ExVIP"))
				.append('\n');
		buffer.append("expandingSseatTotal=")
				.append(arenaDataMap.get("ExTotal")).append('\n');
		buffer.append("isExpanding=").append(arenaDataMap.get("isExpanding"))
				.append('\n');

		// Achtung bei keiner Erweiterung = 0!
		buffer.append("ExpansionDate=")
				.append(arenaDataMap.get("ExpansionDate")).append('\n');
	}

	// //////////////////////////////////////////////////////////////////////////////
	// Helper
	// //////////////////////////////////////////////////////////////////////////////

	/**
	 * Create the basic data.
	 */
	private static void createBasics(Map<String, String> teamdetailsDataMap,
			Map<?, ?> worldDataMap, StringBuilder buffer) {
		buffer.append("[basics]\n");
		buffer.append("application=HO\n");
		buffer.append("appversion=").append(ho.HO.VERSION).append('\n');
		buffer.append("date=").append(teamdetailsDataMap.get("FetchedDate"))
				.append('\n');
		buffer.append("season=").append(worldDataMap.get("Season"))
				.append('\n');
		buffer.append("matchround=").append(worldDataMap.get("MatchRound"))
				.append('\n');
		buffer.append("teamID=").append(teamdetailsDataMap.get("TeamID"))
				.append('\n');
		buffer.append("teamName=").append(teamdetailsDataMap.get("TeamName"))
				.append('\n');
		buffer.append("owner=").append(teamdetailsDataMap.get("Loginname"))
				.append('\n');
		buffer.append("ownerEmail=").append(teamdetailsDataMap.get("Email"))
				.append('\n');
		buffer.append("ownerICQ=").append(teamdetailsDataMap.get("ICQ"))
				.append('\n');
		buffer.append("ownerHomepage=")
				.append(teamdetailsDataMap.get("HomePage")).append('\n');
		buffer.append("countryID=").append(worldDataMap.get("CountryID"))
				.append('\n');
		buffer.append("leagueID=").append(teamdetailsDataMap.get("LeagueID"))
				.append('\n');
		buffer.append("regionID=").append(teamdetailsDataMap.get("RegionID"))
				.append('\n');
		buffer.append("hasSupporter=")
				.append(teamdetailsDataMap.get("HasSupporter")).append('\n');
	}

	/**
	 * Create the club data.
	 */
	private static void createClub(Map<String, String> clubDataMap,
			Map<String, String> economyDataMap, Map<?, ?> teamdetailsDataMap,
			StringBuilder buffer) {
		buffer.append("[club]\n");
		buffer.append("hjTranare=")
				.append(clubDataMap.get("AssistantTrainers")).append('\n');
		buffer.append("psykolog=").append(clubDataMap.get("Psychologists"))
				.append('\n');
		buffer.append("presstalesman=")
				.append(clubDataMap.get("PressSpokesmen")).append('\n');
		buffer.append("massor=").append(clubDataMap.get("Physiotherapists"))
				.append('\n');
		buffer.append("lakare=").append(clubDataMap.get("Doctors"))
				.append('\n');
		buffer.append("juniorverksamhet=")
				.append(clubDataMap.get("YouthLevel")).append('\n');
		buffer.append("undefeated=")
				.append(teamdetailsDataMap.get("NumberOfUndefeated"))
				.append('\n');
		buffer.append("victories=")
				.append(teamdetailsDataMap.get("NumberOfVictories"))
				.append('\n');
		buffer.append("fanclub=").append(economyDataMap.get("FanClubSize"))
				.append('\n');
	}

	/**
	 * Create the economy data.
	 */
	private static void createEconemy(Map<?, ?> economyDataMap,
			StringBuilder buffer) {
		// wahrscheinlich in Training.asp fehlt noch
		buffer.append("[economy]").append('\n');

		if (economyDataMap.get("SponsorsPopularity") != null) {
			buffer.append("supporters=")
					.append(economyDataMap.get("SupportersPopularity"))
					.append('\n');
			buffer.append("sponsors=")
					.append(economyDataMap.get("SponsorsPopularity"))
					.append('\n');
			// es wird grad gespielt flag setzen
		} else {
			buffer.append("playingMatch=true");
		}

		buffer.append("cash=").append(economyDataMap.get("Cash")).append('\n');
		buffer.append("IncomeSponsorer=")
				.append(economyDataMap.get("IncomeSponsors")).append('\n');
		buffer.append("incomePublik=")
				.append(economyDataMap.get("IncomeSpectators")).append('\n');
		buffer.append("incomeFinansiella=")
				.append(economyDataMap.get("IncomeFinancial")).append('\n');
		buffer.append("incomeTillfalliga=")
				.append(economyDataMap.get("IncomeTemporary")).append('\n');
		buffer.append("incomeSumma=").append(economyDataMap.get("IncomeSum"))
				.append('\n');
		buffer.append("costsSpelare=")
				.append(economyDataMap.get("CostsPlayers")).append('\n');
		buffer.append("costsPersonal=")
				.append(economyDataMap.get("CostsStaff")).append('\n');
		buffer.append("costsArena=").append(economyDataMap.get("CostsArena"))
				.append('\n');
		buffer.append("costsJuniorverksamhet=")
				.append(economyDataMap.get("CostsYouth")).append('\n');
		buffer.append("costsRantor=")
				.append(economyDataMap.get("CostsFinancial")).append('\n');
		buffer.append("costsTillfalliga=")
				.append(economyDataMap.get("CostsTemporary")).append('\n');
		buffer.append("costsSumma=").append(economyDataMap.get("CostsSum"))
				.append('\n');
		buffer.append("total=")
				.append(economyDataMap.get("ExpectedWeeksTotal")).append('\n');
		buffer.append("lastIncomeSponsorer=")
				.append(economyDataMap.get("LastIncomeSponsors")).append('\n');
		buffer.append("lastIncomePublik=")
				.append(economyDataMap.get("LastIncomeSpectators"))
				.append('\n');
		buffer.append("lastIncomeFinansiella=")
				.append(economyDataMap.get("LastIncomeFinancial")).append('\n');
		buffer.append("lastIncomeTillfalliga=")
				.append(economyDataMap.get("LastIncomeTemporary")).append('\n');
		buffer.append("lastIncomeSumma=")
				.append(economyDataMap.get("LastIncomeSum")).append('\n');
		buffer.append("lastCostsSpelare=")
				.append(economyDataMap.get("LastCostsPlayers")).append('\n');
		buffer.append("lastCostsPersonal=")
				.append(economyDataMap.get("LastCostsStaff")).append('\n');
		buffer.append("lastCostsArena=")
				.append(economyDataMap.get("LastCostsArena")).append('\n');
		buffer.append("lastCostsJuniorverksamhet=")
				.append(economyDataMap.get("LastCostsYouth")).append('\n');
		buffer.append("lastCostsRantor=")
				.append(economyDataMap.get("LastCostsFinancial")).append('\n');
		buffer.append("lastCostsTillfalliga=")
				.append(economyDataMap.get("LastCostsTemporary")).append('\n');
		buffer.append("lastCostsSumma=")
				.append(economyDataMap.get("LastCostsSum")).append('\n');
		buffer.append("lastTotal=")
				.append(economyDataMap.get("LastWeeksTotal")).append('\n');
	}

	/**
	 * Create last lineup section.
	 */
	private static void createLastLineUp(Map<?, ?> teamdetailsDataMap,
			MatchLineupTeam matchLineupTeam, int lastAttitude, int lastTactic,
			StringBuilder buffer) {
		buffer.append("[lastlineup]").append('\n');
		buffer.append("trainer=").append(teamdetailsDataMap.get("TrainerID"))
				.append('\n');

		try {
			buffer.append("installning=").append(lastAttitude).append('\n');
			buffer.append("tactictype=").append(lastTactic).append('\n');
			buffer.append("keeper=")
					.append(matchLineupTeam.getPlayerByPosition(
							ISpielerPosition.keeper).getSpielerId())
					.append('\n');
			buffer.append("rightBack=")
					.append(matchLineupTeam.getPlayerByPosition(
							ISpielerPosition.rightBack).getSpielerId())
					.append('\n');
			buffer.append("insideBack1=")
					.append(matchLineupTeam.getPlayerByPosition(
							ISpielerPosition.rightCentralDefender)
							.getSpielerId()).append('\n');
			buffer.append("insideBack2=")
					.append(matchLineupTeam.getPlayerByPosition(
							ISpielerPosition.leftCentralDefender)
							.getSpielerId()).append('\n');
			buffer.append("insideBack3=")
					.append(matchLineupTeam.getPlayerByPosition(
							ISpielerPosition.middleCentralDefender)
							.getSpielerId()).append('\n');
			buffer.append("leftBack=")
					.append(matchLineupTeam.getPlayerByPosition(
							ISpielerPosition.leftBack).getSpielerId())
					.append('\n');
			buffer.append("rightWinger=")
					.append(matchLineupTeam.getPlayerByPosition(
							ISpielerPosition.rightWinger).getSpielerId())
					.append('\n');
			buffer.append("insideMid1=")
					.append(matchLineupTeam.getPlayerByPosition(
							ISpielerPosition.rightInnerMidfield).getSpielerId())
					.append('\n');
			buffer.append("insideMid2=")
					.append(matchLineupTeam.getPlayerByPosition(
							ISpielerPosition.leftInnerMidfield).getSpielerId())
					.append('\n');
			buffer.append("insideMid3=")
					.append(matchLineupTeam.getPlayerByPosition(
							ISpielerPosition.centralInnerMidfield)
							.getSpielerId()).append('\n');
			buffer.append("leftWinger=")
					.append(matchLineupTeam.getPlayerByPosition(
							ISpielerPosition.leftWinger).getSpielerId())
					.append('\n');
			buffer.append("forward1=")
					.append(matchLineupTeam.getPlayerByPosition(
							ISpielerPosition.rightForward).getSpielerId())
					.append('\n');
			buffer.append("forward2=")
					.append(matchLineupTeam.getPlayerByPosition(
							ISpielerPosition.leftForward).getSpielerId())
					.append('\n');
			buffer.append("forward3=")
					.append(matchLineupTeam.getPlayerByPosition(
							ISpielerPosition.centralForward).getSpielerId())
					.append('\n');
			buffer.append("substBack=")
					.append(matchLineupTeam.getPlayerByPosition(
							ISpielerPosition.substDefender).getSpielerId())
					.append('\n');
			buffer.append("substInsideMid=")
					.append(matchLineupTeam.getPlayerByPosition(
							ISpielerPosition.substInnerMidfield).getSpielerId())
					.append('\n');
			buffer.append("substWinger=")
					.append(matchLineupTeam.getPlayerByPosition(
							ISpielerPosition.substWinger).getSpielerId())
					.append('\n');
			buffer.append("substKeeper=")
					.append(matchLineupTeam.getPlayerByPosition(
							ISpielerPosition.substKeeper).getSpielerId())
					.append('\n');
			buffer.append("substForward=")
					.append(matchLineupTeam.getPlayerByPosition(
							ISpielerPosition.substForward).getSpielerId())
					.append('\n');
			buffer.append("captain=")
					.append(matchLineupTeam.getPlayerByPosition(
							ISpielerPosition.captain).getSpielerId())
					.append('\n');
			buffer.append("kicker1=")
					.append(matchLineupTeam.getPlayerByPosition(
							ISpielerPosition.setPieces).getSpielerId())
					.append('\n');

			buffer.append("behrightBack=")
					.append(matchLineupTeam.getPlayerByPosition(
							ISpielerPosition.rightBack).getTaktik())
					.append('\n');
			buffer.append("behinsideBack1=")
					.append(matchLineupTeam.getPlayerByPosition(
							ISpielerPosition.rightCentralDefender).getTaktik())
					.append('\n');
			buffer.append("behinsideBack2=")
					.append(matchLineupTeam.getPlayerByPosition(
							ISpielerPosition.leftCentralDefender).getTaktik())
					.append('\n');
			buffer.append("behinsideBack3=")
					.append(matchLineupTeam.getPlayerByPosition(
							ISpielerPosition.middleCentralDefender).getTaktik())
					.append('\n');
			buffer.append("behleftBack=")
					.append(matchLineupTeam.getPlayerByPosition(
							ISpielerPosition.leftBack).getTaktik())
					.append('\n');
			buffer.append("behrightWinger=")
					.append(matchLineupTeam.getPlayerByPosition(
							ISpielerPosition.rightWinger).getTaktik())
					.append('\n');
			buffer.append("behinsideMid1=")
					.append(matchLineupTeam.getPlayerByPosition(
							ISpielerPosition.rightInnerMidfield).getTaktik())
					.append('\n');
			buffer.append("behinsideMid2=")
					.append(matchLineupTeam.getPlayerByPosition(
							ISpielerPosition.leftInnerMidfield).getTaktik())
					.append('\n');
			buffer.append("behinsideMid3=")
					.append(matchLineupTeam.getPlayerByPosition(
							ISpielerPosition.centralInnerMidfield).getTaktik())
					.append('\n');
			buffer.append("behleftWinger=")
					.append(matchLineupTeam.getPlayerByPosition(
							ISpielerPosition.leftWinger).getTaktik())
					.append('\n');
			buffer.append("behforward1=")
					.append(matchLineupTeam.getPlayerByPosition(
							ISpielerPosition.rightForward).getTaktik())
					.append('\n');
			buffer.append("behforward2=")
					.append(matchLineupTeam.getPlayerByPosition(
							ISpielerPosition.leftForward).getTaktik())
					.append('\n');
			buffer.append("behforward3=")
					.append(matchLineupTeam.getPlayerByPosition(
							ISpielerPosition.centralForward).getTaktik())
					.append('\n');

			int i = 0;
			for (Substitution sub : matchLineupTeam.getSubstitutions()) {
				if (sub != null) {
					buffer.append("subst").append(i).append("playerOrderID=")
							.append(sub.getPlayerOrderId()).append('\n');
					buffer.append("subst").append(i).append("playerIn=")
							.append(sub.getObjectPlayerID()).append('\n');
					buffer.append("subst").append(i).append("playerOut=")
							.append(sub.getSubjectPlayerID()).append('\n');
					buffer.append("subst").append(i).append("orderType=")
							.append(sub.getOrderType().getId()).append('\n');
					buffer.append("subst").append(i)
							.append("matchMinuteCriteria=")
							.append(sub.getMatchMinuteCriteria()).append('\n');
					buffer.append("subst").append(i).append("pos=")
							.append(sub.getPos()).append('\n');
					buffer.append("subst").append(i).append("behaviour=")
							.append(sub.getBehaviour()).append('\n');
					buffer.append("subst").append(i).append("card=")
							.append(sub.getRedCardCriteria().getId())
							.append('\n');
					buffer.append("subst").append(i).append("standing=")
							.append(sub.getStanding().getId()).append('\n');
					i++;
				}
			}
		} catch (Exception e) {
			HOLogger.instance().debug(ConvertXml2Hrf.class,
					"Error(last lineup): " + e);
		}
	}

	/**
	 * Create the league data.
	 */
	private static void createLeague(Map<String, String> ligaDataMap, StringBuilder buffer) {
		buffer.append("[league]\n");
		buffer.append("serie=").append(ligaDataMap.get("LeagueLevelUnitName"))
				.append('\n');
		buffer.append("spelade=").append(ligaDataMap.get("Matches"))
				.append('\n');
		buffer.append("gjorda=").append(ligaDataMap.get("GoalsFor"))
				.append('\n');
		buffer.append("inslappta=").append(ligaDataMap.get("GoalsAgainst"))
				.append('\n');
		buffer.append("poang=").append(ligaDataMap.get("Points")).append('\n');
		buffer.append("placering=").append(ligaDataMap.get("Position"))
				.append('\n');
	}

	private static String getPlayerForNextLineup(String position, Map<?, ?> next) {
		if (next != null) {
			final Object ret = next.get(position);
			if (ret != null) {
				return ret.toString();
			}
		}
		return "0";
	}

	private static String getPlayerOrderForNextLineup(String position,
			Map<?, ?> map) {
		if (map != null) {
			String ret = (String) map.get(position);

			if (ret != null) {
				ret = ret.trim();
				if (!"null".equals(ret) && !"".equals(ret)) {
					return ret.trim();
				}
			}
		}
		return "0";
	}

	/**
	 * Creates the lineup data.
	 * 
	 * @param buffer
	 *            The string buffer that will be modified. Lineup info added.
	 * @param trainerId
	 *            The playerId of the trainer of the club.
	 * @param nextLineup
	 *            The lineup info hashmap from the parser.
	 * @return
	 * @throws Exception
	 */
	public static String createLineUp(String trainerId, Map<?, ?> nextLineup) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("[lineup]").append('\n');

		try {
			buffer.append("trainer=").append(trainerId).append('\n');
			buffer.append("installning=").append(nextLineup.get("Attitude"))
					.append('\n');
			buffer.append("tactictype=")
					.append((nextLineup.get("TacticType").toString().trim()
							.equals("") ? "0" : nextLineup.get("TacticType")
							.toString().trim())).append('\n');

			buffer.append("keeper=")
					.append(getPlayerForNextLineup("KeeperID", nextLineup))
					.append('\n');
			buffer.append("rightBack=")
					.append(getPlayerForNextLineup("RightBackID", nextLineup))
					.append('\n');
			buffer.append("insideBack1=")
					.append(getPlayerForNextLineup("RightCentralDefenderID",
							nextLineup)).append('\n');
			buffer.append("insideBack2=")
					.append(getPlayerForNextLineup("LeftCentralDefenderID",
							nextLineup)).append('\n');
			buffer.append("insideBack3=")
					.append(getPlayerForNextLineup("MiddleCentralDefenderID",
							nextLineup)).append('\n');
			buffer.append("leftBack=")
					.append(getPlayerForNextLineup("LeftBackID", nextLineup))
					.append('\n');
			buffer.append("rightWinger=")
					.append(getPlayerForNextLineup("RightWingerID", nextLineup))
					.append('\n');
			buffer.append("insideMid1=")
					.append(getPlayerForNextLineup("RightInnerMidfieldID",
							nextLineup)).append('\n');
			buffer.append("insideMid2=")
					.append(getPlayerForNextLineup("LeftInnerMidfieldID",
							nextLineup)).append('\n');
			buffer.append("insideMid3=")
					.append(getPlayerForNextLineup("CentralInnerMidfieldID",
							nextLineup)).append('\n');
			buffer.append("leftWinger=")
					.append(getPlayerForNextLineup("LeftWingerID", nextLineup))
					.append('\n');
			buffer.append("forward1=")
					.append(getPlayerForNextLineup("RightForwardID", nextLineup))
					.append('\n');
			buffer.append("forward2=")
					.append(getPlayerForNextLineup("LeftForwardID", nextLineup))
					.append('\n');
			buffer.append("forward3=")
					.append(getPlayerForNextLineup("CentralForwardID",
							nextLineup)).append('\n');
			buffer.append("substBack=")
					.append(getPlayerForNextLineup("SubstBackID", nextLineup))
					.append('\n');
			buffer.append("substInsideMid=")
					.append(getPlayerForNextLineup("SubstInsideMidID",
							nextLineup)).append('\n');
			buffer.append("substWinger=")
					.append(getPlayerForNextLineup("SubstWingerID", nextLineup))
					.append('\n');
			buffer.append("substKeeper=")
					.append(getPlayerForNextLineup("SubstKeeperID", nextLineup))
					.append('\n');
			buffer.append("substForward=")
					.append(getPlayerForNextLineup("SubstForwardID", nextLineup))
					.append('\n');
			buffer.append("captain=")
					.append(getPlayerForNextLineup("CaptainID", nextLineup))
					.append('\n');
			buffer.append("kicker1=")
					.append(getPlayerForNextLineup("KickerID", nextLineup))
					.append('\n');

			buffer.append("behrightBack=")
					.append(getPlayerOrderForNextLineup("RightBackOrder",
							nextLineup)).append('\n');
			buffer.append("behinsideBack1=")
					.append(getPlayerOrderForNextLineup(
							"RightCentralDefenderOrder", nextLineup))
					.append('\n');
			buffer.append("behinsideBack2=")
					.append(getPlayerOrderForNextLineup(
							"LeftCentralDefenderOrder", nextLineup))
					.append('\n');
			buffer.append("behinsideBack3=")
					.append(getPlayerOrderForNextLineup(
							"MiddleCentralDefenderOrder", nextLineup))
					.append('\n');
			buffer.append("behleftBack=")
					.append(getPlayerOrderForNextLineup("LeftBackOrder",
							nextLineup)).append('\n');
			buffer.append("behrightWinger=")
					.append(getPlayerOrderForNextLineup("RightWingerOrder",
							nextLineup)).append('\n');
			buffer.append("behinsideMid1=")
					.append(getPlayerOrderForNextLineup(
							"RightInnerMidfieldOrder", nextLineup))
					.append('\n');
			buffer.append("behinsideMid2=")
					.append(getPlayerOrderForNextLineup(
							"LeftInnerMidfieldOrder", nextLineup)).append('\n');
			buffer.append("behinsideMid3=")
					.append(getPlayerOrderForNextLineup(
							"CentralInnerMidfieldOrder", nextLineup))
					.append('\n');
			buffer.append("behleftWinger=")
					.append(getPlayerOrderForNextLineup("LeftWingerOrder",
							nextLineup)).append('\n');
			buffer.append("behforward1=")
					.append(getPlayerOrderForNextLineup("RightForwardOrder",
							nextLineup)).append('\n');
			buffer.append("behforward2=")
					.append(getPlayerOrderForNextLineup("LeftForwardOrder",
							nextLineup)).append('\n');
			buffer.append("behforward3=")
					.append(getPlayerOrderForNextLineup("CentralForwardOrder",
							nextLineup)).append('\n');

			for (int i = 0; i < 5; i++) {
				String substNext = "subst" + i;
				if (nextLineup.get(substNext + "playerOrderID") != null) {
					buffer.append(substNext)
							.append("playerOrderID=")
							.append(nextLineup.get(substNext + "playerOrderID"))
							.append('\n');
					buffer.append(substNext).append("playerIn=")
							.append(nextLineup.get(substNext + "playerIn"))
							.append('\n');
					buffer.append(substNext).append("playerOut=")
							.append(nextLineup.get(substNext + "playerOut"))
							.append('\n');
					buffer.append(substNext).append("orderType=")
							.append(nextLineup.get(substNext + "orderType"))
							.append('\n');
					buffer.append(substNext)
							.append("matchMinuteCriteria=")
							.append(nextLineup.get(substNext
									+ "matchMinuteCriteria")).append('\n');
					buffer.append(substNext).append("pos=")
							.append(nextLineup.get(substNext + "pos"))
							.append('\n');
					buffer.append(substNext).append("behaviour=")
							.append(nextLineup.get(substNext + "behaviour"))
							.append('\n');
					buffer.append(substNext).append("card=")
							.append(nextLineup.get(substNext + "card"))
							.append('\n');
					buffer.append(substNext).append("standing=")
							.append(nextLineup.get(substNext + "standing"))
							.append('\n');
				}
			}

			for (int i = 0; i < 11; i++) {
				String key = "PenaltyTaker" + i;
				buffer.append("penalty").append(i).append("=")
						.append(getPlayerForNextLineup(key, nextLineup))
						.append('\n');
			}

		} catch (Exception e) {
			HOLogger.instance().debug(ConvertXml2Hrf.class,
					"Error(lineup): " + e);
		}
		return buffer.toString();
	}

	/**
	 * Create the player data.
	 */
	private static void createPlayers(MatchLineupTeam matchLineupTeam,
			List<MyHashtable> playersData, StringBuilder buffer) {
		Map<?, ?> ht = null;

		for (int i = 0; (playersData != null) && (i < playersData.size()); i++) {
			ht = (Map<?, ?>) playersData.get(i);

			buffer.append("[player").append(ht.get("PlayerID").toString())
					.append(']').append('\n');

			if (ht.get("NickName").toString().length() > 0) {
				buffer.append("name=");
				buffer.append(ht.get("FirstName").toString()).append(" '");
				buffer.append(ht.get("NickName").toString()).append("' ");
				buffer.append(ht.get("LastName").toString()).append('\n');
			} else {
				buffer.append("name=");
				buffer.append(ht.get("FirstName").toString()).append(' ');
				buffer.append(ht.get("LastName").toString()).append('\n');
			}
			buffer.append("firstname=").append(ht.get("FirstName").toString())
					.append('\n');
			buffer.append("nickname=").append(ht.get("NickName").toString())
					.append('\n');
			buffer.append("lastname=").append(ht.get("LastName").toString())
					.append('\n');
			buffer.append("ald=").append(ht.get("Age").toString()).append('\n');
			buffer.append("agedays=").append(ht.get("AgeDays").toString())
					.append('\n');
			buffer.append("ska=").append(ht.get("InjuryLevel").toString())
					.append('\n');
			buffer.append("for=").append(ht.get("PlayerForm").toString())
					.append('\n');
			buffer.append("uth=").append(ht.get("StaminaSkill").toString())
					.append('\n');
			buffer.append("spe=").append(ht.get("PlaymakerSkill").toString())
					.append('\n');
			buffer.append("mal=").append(ht.get("ScorerSkill").toString())
					.append('\n');
			buffer.append("fra=").append(ht.get("PassingSkill").toString())
					.append('\n');
			buffer.append("ytt=").append(ht.get("WingerSkill").toString())
					.append('\n');
			buffer.append("fas=").append(ht.get("SetPiecesSkill").toString())
					.append('\n');
			buffer.append("bac=").append(ht.get("DefenderSkill").toString())
					.append('\n');
			buffer.append("mlv=").append(ht.get("KeeperSkill").toString())
					.append('\n');
			buffer.append("rut=").append(ht.get("Experience").toString())
					.append('\n');
			buffer.append("loy=").append(ht.get("Loyalty").toString())
					.append('\n');
			buffer.append("homegr=")
					.append(ht.get("MotherClubBonus").toString()).append('\n');
			buffer.append("led=").append(ht.get("Leadership").toString())
					.append('\n');
			buffer.append("sal=").append(ht.get("Salary").toString())
					.append('\n');
			buffer.append("mkt=").append(ht.get("MarketValue").toString())
					.append('\n');
			buffer.append("gev=").append(ht.get("CareerGoals").toString())
					.append('\n');
			buffer.append("gtl=").append(ht.get("LeagueGoals").toString())
					.append('\n');
			buffer.append("gtc=").append(ht.get("CupGoals").toString())
					.append('\n');
			buffer.append("gtt=").append(ht.get("FriendliesGoals").toString())
					.append('\n');
			buffer.append("hat=").append(ht.get("CareerHattricks").toString())
					.append('\n');
			buffer.append("CountryID=").append(ht.get("CountryID").toString())
					.append('\n');
			buffer.append("warnings=").append(ht.get("Cards").toString())
					.append('\n');
			buffer.append("speciality=").append(ht.get("Specialty").toString())
					.append('\n');
			buffer.append("specialityLabel=")
					.append(PlayerSpeciality.toString(Integer.parseInt(ht.get(
							"Specialty").toString()))).append('\n');
			buffer.append("gentleness=")
					.append(ht.get("Agreeability").toString()).append('\n');
			buffer.append("gentlenessLabel=")
					.append(PlayerAgreeability.toString(Integer.parseInt(ht
							.get("Agreeability").toString()))).append('\n');
			buffer.append("honesty=").append(
					ht.get("Honesty").toString() + "\n");
			buffer.append("honestyLabel=")
					.append(PlayerHonesty.toString(Integer.parseInt(ht.get(
							"Honesty").toString()))).append('\n');
			buffer.append("Aggressiveness=")
					.append(ht.get("Aggressiveness").toString()).append('\n');
			buffer.append("AggressivenessLabel=")
					.append(PlayerAggressiveness.toString(Integer.parseInt(ht
							.get("Aggressiveness").toString()))).append('\n');

			if (ht.get("TrainerSkill") != null) {
				buffer.append("TrainerType=")
						.append(ht.get("TrainerType").toString()).append('\n');
				buffer.append("TrainerSkill=")
						.append(ht.get("TrainerSkill").toString()).append('\n');
			} else {
				buffer.append("TrainerType=").append('\n');
				buffer.append("TrainerSkill=").append('\n');
			}

			if ((matchLineupTeam != null)
					&& (matchLineupTeam.getPlayerByID(Integer.parseInt(ht.get(
							"PlayerID").toString())) != null)
					&& (matchLineupTeam.getPlayerByID(
							Integer.parseInt(ht.get("PlayerID").toString()))
							.getRating() >= 0)) {
				buffer.append("rating=")
						.append((int) (matchLineupTeam
								.getPlayerByID(
										Integer.parseInt(ht.get("PlayerID")
												.toString())).getRating() * 2))
						.append('\n');
			} else {
				buffer.append("rating=0").append('\n');
			}

			// Bonus
			if ((ht.get("PlayerNumber") != null)
					|| (!ht.get("PlayerNumber").equals(""))) {
				buffer.append("PlayerNumber=").append(ht.get("PlayerNumber"))
						.append('\n');
			}

			buffer.append("TransferListed=").append(ht.get("TransferListed"))
					.append('\n');
			buffer.append("NationalTeamID=").append(ht.get("NationalTeamID"))
					.append('\n');
			buffer.append("Caps=").append(ht.get("Caps")).append('\n');
			buffer.append("CapsU20=").append(ht.get("CapsU20")).append('\n');
		}
	}

	/**
	 * Create team related data (training, confidence, formation experience,
	 * etc.).
	 */
	private static void createTeam(Map<?, ?> trainingDataMap,
			StringBuilder buffer) {
		buffer.append("[team]" + "\n");
		buffer.append("trLevel=").append(trainingDataMap.get("TrainingLevel"))
				.append('\n');
		buffer.append("staminaTrainingPart=")
				.append(trainingDataMap.get("StaminaTrainingPart"))
				.append('\n');
		buffer.append("trTypeValue=")
				.append(trainingDataMap.get("TrainingType")).append('\n');
		buffer.append("trType=")
				.append(TrainingType.toString(Integer.parseInt(trainingDataMap
						.get("TrainingType").toString()))).append('\n');

		if ((trainingDataMap.get("Morale") != null)
				&& (trainingDataMap.get("SelfConfidence") != null)) {
			buffer.append("stamningValue=")
					.append(trainingDataMap.get("Morale")).append('\n');
			buffer.append("stamning=")
					.append(TeamSpirit.toString(Integer
							.parseInt(trainingDataMap.get("Morale").toString())))
					.append('\n');
			buffer.append("sjalvfortroendeValue=")
					.append(trainingDataMap.get("SelfConfidence")).append('\n');
			buffer.append("sjalvfortroende=")
					.append(TeamConfidence.toString(Integer
							.parseInt(trainingDataMap.get("SelfConfidence")
									.toString()))).append('\n');
		} else {
			buffer.append("playingMatch=true");
		}

		buffer.append("exper433=").append(trainingDataMap.get("Experience433"))
				.append('\n');
		buffer.append("exper451=").append(trainingDataMap.get("Experience451"))
				.append('\n');
		buffer.append("exper352=").append(trainingDataMap.get("Experience352"))
				.append('\n');
		buffer.append("exper532=").append(trainingDataMap.get("Experience532"))
				.append('\n');
		buffer.append("exper343=").append(trainingDataMap.get("Experience343"))
				.append('\n');
		buffer.append("exper541=").append(trainingDataMap.get("Experience541"))
				.append('\n');
		if (trainingDataMap.get("Experience442") != null) {
			buffer.append("exper442=")
					.append(trainingDataMap.get("Experience442")).append('\n');
		}
		if (trainingDataMap.get("Experience523") != null) {
			buffer.append("exper523=")
					.append(trainingDataMap.get("Experience523")).append('\n');
		}
		if (trainingDataMap.get("Experience550") != null) {
			buffer.append("exper550=")
					.append(trainingDataMap.get("Experience550")).append('\n');
		}
		if (trainingDataMap.get("Experience253") != null) {
			buffer.append("exper253=")
					.append(trainingDataMap.get("Experience253")).append('\n');
		}
	}

	/**
	 * Create the world data.
	 */
	private static void createWorld(Map<String, String> clubDataMap,
			Map<?, ?> teamdetailsDataMap, Map<?, ?> trainingDataMap,
			Map<?, ?> worldDataMap, StringBuilder buffer) {
		buffer.append("[xtra]\n");
		buffer.append("TrainingDate=").append(worldDataMap.get("TrainingDate"))
				.append('\n');
		buffer.append("EconomyDate=").append(worldDataMap.get("EconomyDate"))
				.append('\n');
		buffer.append("SeriesMatchDate=")
				.append(worldDataMap.get("SeriesMatchDate")).append('\n');
		buffer.append("CurrencyName=").append(worldDataMap.get("CurrencyName"))
				.append('\n');
		buffer.append("CurrencyRate=")
				.append(worldDataMap.get("CurrencyRate").toString()
						.replace(',', '.')).append('\n');
		buffer.append("LogoURL=").append(teamdetailsDataMap.get("LogoURL"))
				.append('\n');
		buffer.append("HasPromoted=").append(clubDataMap.get("HasPromoted"))
				.append('\n');

		buffer.append("TrainerID=").append(trainingDataMap.get("TrainerID"))
				.append('\n');
		buffer.append("TrainerName=")
				.append(trainingDataMap.get("TrainerName")).append('\n');
		buffer.append("ArrivalDate=")
				.append(trainingDataMap.get("ArrivalDate")).append('\n');
		buffer.append("LeagueLevelUnitID=")
				.append(teamdetailsDataMap.get("LeagueLevelUnitID"))
				.append('\n');
	}

	/**
	 * Save the HRF file.
	 */
	private void writeHRF(String dateiname, StringBuilder buffer) {
		BufferedWriter out = null;

		try {
			File f = new File(dateiname);

			if (f.exists()) {
				f.delete();
			}

			f.createNewFile();

			// write utf 8
			OutputStreamWriter outWrit = new OutputStreamWriter(
					new FileOutputStream(f), "UTF-8");
			out = new BufferedWriter(outWrit);

			// write ansi
			out.write(buffer.toString());
		} catch (Exception except) {
			HOLogger.instance().log(getClass(), except);
		} finally {
			IOUtils.closeQuietly(out);
		}
	}
}
