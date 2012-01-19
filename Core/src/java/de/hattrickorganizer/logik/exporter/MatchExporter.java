// %1127327738353:hoplugins%
package de.hattrickorganizer.logik.exporter;

import ho.core.db.DBManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import plugins.IExportMatchData;
import plugins.IMatchDetails;
import plugins.IMatchHighlight;
import plugins.IMatchKurzInfo;
import plugins.IMatchLineup;
import plugins.IMatchLineupPlayer;
import plugins.ISpieler;
import plugins.ISpielerPosition;
import de.hattrickorganizer.model.HOMiniModel;
import de.hattrickorganizer.model.matches.MatchHelper;
import de.hattrickorganizer.tools.HOLogger;

public class MatchExporter {
	//~ Static fields/initializers -----------------------------------------------------------------

	//~ Constructors -------------------------------------------------------------------------------

	//~ Methods ------------------------------------------------------------------------------------

	/**
	 * List of useful data for export
	 *
	 * @param startingDate starting data to export from (for all matchTypes)
	 *
	 * @return List of ExportMatchData objects
	 */
	public static List<IExportMatchData> getDataUsefullMatches (Date startingDate) {
		return getDataUsefullMatches(startingDate, startingDate);
	}

	public static List<IExportMatchData> getDataUsefullMatches(Date startingDate, Date startingDateForFriendlies) {
		return getDataUsefullMatches(startingDate, startingDateForFriendlies, true);
	}

	public static List<IExportMatchData> getDataUsefullMatches(Date startingDate, Date startingDateForFriendlies, boolean strict) {
		return getDataUsefullMatches(startingDate, startingDateForFriendlies, strict, false);
	}
	
	/**
	 * List of useful data for export
	 *
	 * @param startingDate starting data to export from (for non friendlies)
	 * @param startingDateForFriendlies starting data to export from (for friendlies)
	 * @param strict is true, export only matches *without* cards, injuries, tactical problems / overconfidence / weather SE...
	 * @param skipPullBack is true, skip matches with pull back event
	 *
	 * @return List of ExportMatchData objects
	 */
	public static List<IExportMatchData> getDataUsefullMatches(Date startingDate, Date startingDateForFriendlies, boolean strict, boolean skipPullBack) {		
		HOLogger.instance().log(MatchExporter.class, "Collecting MatchData");		
		List<IExportMatchData> export = new ArrayList<IExportMatchData>();

		IMatchKurzInfo[] matches = DBManager.instance().getMatchesKurzInfo(HOMiniModel.instance().getBasics().getTeamId());

		//Alle matches prï¿½fen        
		for (int i = 0;(matches != null) && (i < matches.length); i++) {
			//details holen
			IMatchDetails details = HOMiniModel.instance().getMatchDetails(matches[i].getMatchID());
			boolean isFriendly = (matches[i].getMatchTyp() == IMatchLineup.TESTSPIEL
					|| matches[i].getMatchTyp() == IMatchLineup.INT_TESTSPIEL 
					|| matches[i].getMatchTyp() == IMatchLineup.TESTPOKALSPIEL
					|| matches[i].getMatchTyp() == IMatchLineup.INT_TESTCUPSPIEL);
			if (isValidMatch(matches[i], details, startingDateForFriendlies, strict, skipPullBack) && isFriendly
					|| isValidMatch(matches[i], details, startingDate, strict, skipPullBack) && !isFriendly ) {				

				//Nun lineup durchlaufen und Spielerdaten holen
				Vector<IMatchLineupPlayer> aufstellung = DBManager.instance().getMatchLineupPlayers(details.getMatchID(),HOMiniModel.instance().getBasics().getTeamId());
				Hashtable<Integer,ISpieler> lineUpISpieler = new Hashtable<Integer,ISpieler>();

				boolean dataOK = true;

				for (int k = 0;(aufstellung != null) && (k < aufstellung.size()); k++) {
					//MatchDaten zum Spieler holen
					plugins.IMatchLineupPlayer player = aufstellung.get(k);

					//Alte Werte zum Spieler holen fï¿½r das Matchdate
					plugins.ISpieler formerPlayerData = null;

					//Bankl + verlketzte ï¿½berspringen
					if (player.getId() >= ISpielerPosition.startReserves) {
						continue;
					}

					formerPlayerData =
						HOMiniModel.instance().getSpielerAtDate(player.getSpielerId(),matches[i].getMatchDateAsTimestamp());


					//Keine Daten verfï¿½gbar ?
					if (formerPlayerData == null) {
						//Abbruch
						dataOK = false;
						lineUpISpieler.clear();
						break;
					}

					//ISpieler in ht ablegen
					lineUpISpieler.put(Integer.valueOf(player.getSpielerId()), formerPlayerData);
				} //end for aufstellung

				//Matchdaten ablegen da einwandfrei
				if (dataOK) {
					ExportMatchData data = new ExportMatchData();
					data.setDetails(details);
					data.setInfo(matches[i]);
					data.setPlayers(lineUpISpieler);
					export.add(data);					
				}
			} //end For usefull Matches        
		}
		return export;
	}

	private static boolean isValidMatch(IMatchKurzInfo info, IMatchDetails details, Date startingDate, boolean strict, boolean skipPullBack) {
		if ((info.getMatchStatus() != IMatchKurzInfo.FINISHED) || (details.getMatchID() == -1)) {
			HOLogger.instance().debug(MatchExporter.class, "Ignoring match " + info.getMatchID() + ": not finished");
			return false;
		}
		// Check for WO
		if (details.getHomeMidfield() <= 1 &&
				details.getGuestMidfield() <= 1) {
			HOLogger.instance().debug(MatchExporter.class, "Ignoring match " + info.getMatchID() + ": Walk over");
			return false;
		}
		Vector<IMatchHighlight> highlights = details.getHighlights();
		//Aussortieren starten...
		if (info.getMatchDateAsTimestamp().before(startingDate)) { //Zu alt !!!
			return false;
		} else if (DBManager.instance().getHrfIDSameTraining(info.getMatchDateAsTimestamp()) == -1) //Kein HRF gefunden
		{
			HOLogger.instance().debug(MatchExporter.class, "Ignoring match " + info.getMatchID() + ": No matching HRF found");
			return false;
		} else if (strict)//Datum i.O. weitere checks fahren
		{
			//Highlights prüfen auf Verletzung, Rote Karte, Verwirrung, Unterschätzung
			// Check Highlights for our team only
			int teamId = HOMiniModel.instance().getBasics().getTeamId();
			if (MatchHelper.instance().hasRedCard(highlights, teamId)) {
				//Karten check
				HOLogger.instance().debug(MatchExporter.class, "Ignoring match " + info.getMatchID() + ": Got a red card");
				return false;
			}
			//injury / tactical problems / overconfidence check
			else if (MatchHelper.instance().hasInjury(highlights, teamId)) {
				HOLogger.instance().debug(MatchExporter.class, "Ignoring match " + info.getMatchID() + ": Injured or bruised player");
				return false;							
			} else if (MatchHelper.instance().hasTacticalProblems(highlights, teamId)) {								
				// Tactical Problems // Verwirrung
				HOLogger.instance().debug(MatchExporter.class, "Ignoring match " + info.getMatchID() + ": Tactical problems");
				return false;							
			} else if (MatchHelper.instance().hasOverConfidence(highlights, teamId)) { 
				// Overconfidence // Unterschaetzen
				HOLogger.instance().debug(MatchExporter.class, "Ignoring match " + info.getMatchID() + ": Overconfidence");
				return false;
			} else if (MatchHelper.instance().hasWeatherSE(highlights, teamId)) {
				// Weather based SpecialEvents check (as this SE alters player ratings)
				HOLogger.instance().debug(MatchExporter.class, "Ignoring match " + info.getMatchID() + ": Weather SE");
				return false;
				// Manual Substitution
			} else if (MatchHelper.instance().hasManualSubstitution(highlights, teamId)) {
				HOLogger.instance().debug(MatchExporter.class, "Ignoring match " + info.getMatchID() + ": Manual Substitution");
				return false;							
			}

		}
		if (skipPullBack && MatchHelper.instance().hasPullBack(highlights, HOMiniModel.instance().getBasics().getTeamId())) {
			HOLogger.instance().debug(MatchExporter.class, "Ignoring match " + info.getMatchID() + ": Pull Back");
			return false;
		}
		//ende for highlight check

		//		HOLogger.instance().debug(MatchExporter.class, "Exporting match " + info.getMatchID());
		return true;

	}



}
