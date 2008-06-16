// %1127327738353:hoplugins%
package de.hattrickorganizer.logik.exporter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import plugins.IMatchDetails;
import plugins.IMatchHighlight;
import plugins.IMatchKurzInfo;
import plugins.IMatchLineup;
import plugins.IMatchLineupPlayer;
import plugins.ISpielerPosition;
import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.model.HOMiniModel;
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
	public static List getDataUsefullMatches (Date startingDate) {
		return getDataUsefullMatches(startingDate, startingDate);
	}
	
	/**
	 * List of useful data for export
	 *
	 * @param startingDate starting data to export from (for non friendlies)
	 * @param startingDateForFriendlies starting data to export from (for friendlies)
	 *
	 * @return List of ExportMatchData objects
	 */
	public static List getDataUsefullMatches(Date startingDate, Date startingDateForFriendlies) {		
		HOLogger.instance().log(MatchExporter.class, "Collecting Data");		
		List export = new ArrayList();

		IMatchKurzInfo[] matches = DBZugriff.instance().getMatchesKurzInfo(HOMiniModel.instance().getBasics().getTeamId());

		//Alle matches pr�fen        
		for (int i = 0;(matches != null) && (i < matches.length); i++) {
			//details holen
			IMatchDetails details = HOMiniModel.instance().getMatchDetails(matches[i].getMatchID());
			boolean isFriendly = (matches[i].getMatchTyp() == IMatchLineup.TESTSPIEL
									|| matches[i].getMatchTyp() == IMatchLineup.INT_TESTSPIEL 
									|| matches[i].getMatchTyp() == IMatchLineup.TESTPOKALSPIEL
									|| matches[i].getMatchTyp() == IMatchLineup.INT_TESTCUPSPIEL);
			if (isValidMatch(matches[i], details, startingDateForFriendlies) && isFriendly
					|| isValidMatch(matches[i], details, startingDate) && !isFriendly ) {				
						
				//Nun lineup durchlaufen und Spielerdaten holen
				Vector aufstellung = DBZugriff.instance().getMatchLineupPlayers(details.getMatchID(),HOMiniModel.instance().getBasics().getTeamId());
				Hashtable lineUpISpieler = new Hashtable();

				boolean dataOK = true;
				
				for (int k = 0;(aufstellung != null) && (k < aufstellung.size()); k++) {
					//MatchDaten zum Spieler holen
					plugins.IMatchLineupPlayer player = (IMatchLineupPlayer) aufstellung.get(k);

					//Alte Werte zum Spieler holen f�r das Matchdate
					plugins.ISpieler formerPlayerData = null;

					//Bankl + verlketzte �berspringen
					if (player.getId() >= ISpielerPosition.beginnReservere) {
						continue;
					}

					formerPlayerData =
						HOMiniModel.instance().getSpielerAtDate(player.getSpielerId(),matches[i].getMatchDateAsTimestamp());


					//Keine Daten verf�gbar ?
					if (formerPlayerData == null) {
						//Abbruch
						dataOK = false;
						lineUpISpieler.clear();
						break;
					}

					//ISpieler in ht ablegen
					lineUpISpieler.put(new Integer(player.getSpielerId()), formerPlayerData);
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

	private static boolean isValidMatch(IMatchKurzInfo info, IMatchDetails details, Date startingDate) {
		if ((info.getMatchStatus() != IMatchKurzInfo.FINISHED) || (details.getMatchID() == -1)) {
			return false;
		}
		// Check for WO
		if (details.getHomeMidfield() == 1 &&
			details.getGuestMidfield() == 1) {
			return false;
		}
		Vector highlights = details.getHighlights();
		//Aussortieren starten...
		if ((info.getMatchDateAsTimestamp().before(startingDate)) //Zu alt !!!
		|| (DBZugriff.instance().getHrfIDSameTraining(info.getMatchDateAsTimestamp()) == -1)) //Kein HRF gefunden
			{
			return false;
		} else //Datum i.O. weitere checks fahren
			{
			//Highlights pr�fen auf Verletzung, Rote Karte, Verwirrung, Untersch�tzung
			for (int j = 0;(highlights != null) && (j < highlights.size()); j++) {
				plugins.IMatchHighlight hlight = (IMatchHighlight) highlights.get(j);
				// Check Highlights for our team only
				if (hlight.getTeamID() == HOMiniModel.instance().getBasics().getTeamId()) {
					//Karten check
					if (hlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_KARTEN
						&& (hlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_GELB_ROT_HARTER_EINSATZ
							|| hlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_GELB_ROT_UNFAIR
							|| hlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_ROT)) {
						return false;
					}
					//injury / tactical problems / overconfidence check
					if (hlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_INFORMATION
							// Injured
						&& (hlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_VERLETZT
							|| hlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_VERLETZT_KEIN_ERSATZ_EINS
							|| hlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_VERLETZT_KEIN_ERSATZ_ZWEI
							|| hlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_VERLETZT_LEICHT
							|| hlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_VERLETZT_SCHWER
							// Bruised
							|| hlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_PFLASTER
							|| hlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_PFLASTER_BEHANDLUNG
							// Tactical Problems // Verwirrung
							|| hlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_TAKTISCHE_PROBLEME
							// Overconfidence // Unterschaetzen
							|| hlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_UNTERSCHAETZT)) {
						return false;
					}
					// Weather based SpecialEvents check (as this SE alters player ratings)
					if (hlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_SPEZIAL
						&& (hlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_PLAYER_POWERFUL_RAINY
							|| hlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_PLAYER_POWERFUL_SUNNY
							|| hlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_PLAYER_QUICK_RAINY
							|| hlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_PLAYER_QUICK_SUNNY
							|| hlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_PLAYER_TECHNICAL_RAINY
							|| hlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_PLAYER_TECHNICAL_SUNNY)) {
						return false;
					}
				}
				
			} //ende for highlight check
		}
		return true;

	}
}
