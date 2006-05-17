package de.hattrickorganizer;

//DOM
import java.io.File;
import java.util.Hashtable;
import java.util.Vector;

import de.hattrickorganizer.logik.xml.XMLClubParser;
import de.hattrickorganizer.logik.xml.XMLMatchLineupParser;
import de.hattrickorganizer.logik.xml.XMLMatchesParser;
import de.hattrickorganizer.logik.xml.XMLNewsParser;
import de.hattrickorganizer.logik.xml.XMLPlayerParser;
import de.hattrickorganizer.logik.xml.XMLSpielplanParser;
import de.hattrickorganizer.logik.xml.XMLTabelleParser;
import de.hattrickorganizer.logik.xml.xmlMatchdetailsParser;
import de.hattrickorganizer.logik.xml.xmlTeamDetailsParser;
import de.hattrickorganizer.model.HOModel;
import de.hattrickorganizer.model.News;
import de.hattrickorganizer.model.Verein;
import de.hattrickorganizer.model.lineup.LigaTabelle;
import de.hattrickorganizer.model.matches.MatchKurzInfo;
import de.hattrickorganizer.model.matches.MatchLineup;
import de.hattrickorganizer.model.matches.Matchdetails;
import de.hattrickorganizer.model.matchlist.Paarung;
import de.hattrickorganizer.model.matchlist.Spielplan;
import de.hattrickorganizer.tools.HRFFileParser;

/**
 * Main XML Parser Test Classes
 * 
 * @author draghetto
 */
public class ParserTest {
	//~ Constructors
	// -------------------------------------------------------------------------------

	//~ Methods
	// ------------------------------------------------------------------------------------

	public static void main(String[] args) {
//		testClub();
//		testMatch();
//		testMatchDetails();
//		testMatchLineup();
//		testPlayers();
//		testSpielplan();
//		testTabelle();
//		testTeamDetails();
		testNews();
	}

	private static void testClub() {
		final Verein lt = new XMLClubParser().parseClub("//matchLineup.xml");
		System.out.print(lt.getTeamID());
	}

	private static void testNews() {
		final News news = new XMLNewsParser().parseNews(new File("news.xml"));
		System.out.print(news);
	}
	
	private static void testMatchDetails() {
		final Matchdetails md = new xmlMatchdetailsParser()
				.parseMatchdetails("//matchDetails2.xml");
		System.out.print(md.getSpielDatum().toString());
	}

	private static void testMatch() {
		final XMLMatchesParser sp = new XMLMatchesParser();
		MatchKurzInfo[] liste = sp.parseMatches("//matches.xml");
		System.out.println(liste.length);
	}

	private static void testMatchLineup() {
		final XMLMatchLineupParser tp = new XMLMatchLineupParser();
		final MatchLineup lt = tp.parseMatchLineup("//matchLineup.xml");
		System.out.print(lt.getHeimName());
	}

	private static void testPlayers() {
		final XMLPlayerParser tp = new XMLPlayerParser();
		final Vector lt = tp.parsePlayer("//players.xml");
		System.out.print(lt.size());
	}

	private static void testSpielplan() {
		final XMLSpielplanParser sp = new XMLSpielplanParser();
		Spielplan plan = null;

		plan = sp.parseSpielplan("//Spielplan.xml");

		System.out.println(((Paarung) plan.getEintraege().elementAt(0))
				.getDatum().toString());
		System.out.println(plan.getSaison());

		final plugins.ILigaTabelle tmp = plan.getTabelle();
		System.out.println(tmp.getLigaName());

	}

	private static void testTabelle() {
		final XMLTabelleParser tp = new XMLTabelleParser();
		final LigaTabelle lt = tp.parseTabelle("//tabelle.xml");
		System.out.print(lt.getLigaName());
	}

	private static void testTeamDetails() {
		xmlTeamDetailsParser tp = new xmlTeamDetailsParser();
		Hashtable lt = tp.parseTeamdetails("//matchLineup.xml");
		System.out.print(lt);
	}

	private static void testHRFParser() {
		HRFFileParser parser = new HRFFileParser();
		HOModel model = parser.parse(new File("e:/java/HO!/2003-03-17.hrf"));
		System.out.println(model);
	}

}