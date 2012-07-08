// %3934770789:de.hattrickorganizer.logik.xml%
/*
 * XMLMAtchesParser.java
 *
 * Created on 23. Oktober 2003, 07:57
 */
package ho.core.file.xml;

import ho.core.model.match.MatchKurzInfo;
import ho.core.model.match.MatchType;
import ho.core.util.HOLogger;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * DOCUMENT ME!
 * 
 * @author thomas.werth
 */
public class XMLMatchesParser {

	/**
	 * Utility class - private constructor enforces noninstantiability.
	 */
	private XMLMatchesParser() {
	}

	/**
	 * TODO Missing Method Documentation
	 * 
	 * @param input
	 *            TODO Missing Method Parameter Documentation
	 * 
	 * @return TODO Missing Return Method Documentation
	 */
	public static List<MatchKurzInfo> parseMatchesFromString(String input) {
		return createMatches(XMLManager.parseString(input));
	}

	/**
	 * Wertet den StatusString aus und liefert einen INT
	 * 
	 * @param status
	 *            TODO Missing Constructuor Parameter Documentation
	 * 
	 * @return TODO Missing Return Method Documentation
	 */
	private static int getStatus(String status) {
		if (status.equalsIgnoreCase("FINISHED")) {
			return MatchKurzInfo.FINISHED;
		} else if (status.equalsIgnoreCase("ONGOING")) {
			return MatchKurzInfo.ONGOING;
		} else if (status.equalsIgnoreCase("UPCOMING")) {
			return MatchKurzInfo.UPCOMING;
		}

		return -1;
	}

	/**
	 * erstellt das MAtchlineup Objekt
	 * 
	 * @param doc
	 *            TODO Missing Constructuor Parameter Documentation
	 * 
	 * @return TODO Missing Return Method Documentation
	 */
	private static List<MatchKurzInfo> createMatches(Document doc) {

		MatchKurzInfo spiel = null;
		List<MatchKurzInfo> liste = new ArrayList<MatchKurzInfo>();

		if (doc != null) {
			Element root = doc.getDocumentElement();
			try {
				NodeList list = root.getElementsByTagName("Match");

				Element ele = null;
				Element tmp = null;
				for (int i = 0; (list != null) && (i < list.getLength()); i++) {
					spiel = new MatchKurzInfo();
					ele = (Element) list.item(i);

					// Daten füllen
					tmp = (Element) ele.getElementsByTagName("MatchDate").item(
							0);
					spiel.setMatchDate(tmp.getFirstChild().getNodeValue());
					tmp = (Element) ele.getElementsByTagName("MatchID").item(0);
					spiel.setMatchID(Integer.parseInt(tmp.getFirstChild()
							.getNodeValue()));
					tmp = (Element) ele.getElementsByTagName("MatchType").item(
							0);
					spiel.setMatchTyp(MatchType.getById(Integer.parseInt(tmp
							.getFirstChild().getNodeValue())));
					tmp = (Element) ele.getElementsByTagName("HomeTeam")
							.item(0);
					spiel.setHeimID(Integer.parseInt(((Element) tmp
							.getElementsByTagName("HomeTeamID").item(0))
							.getFirstChild().getNodeValue()));
					spiel.setHeimName(((Element) tmp.getElementsByTagName(
							"HomeTeamName").item(0)).getFirstChild()
							.getNodeValue());
					tmp = (Element) ele.getElementsByTagName("AwayTeam")
							.item(0);
					spiel.setGastID(Integer.parseInt(((Element) tmp
							.getElementsByTagName("AwayTeamID").item(0))
							.getFirstChild().getNodeValue()));
					spiel.setGastName(((Element) tmp.getElementsByTagName(
							"AwayTeamName").item(0)).getFirstChild()
							.getNodeValue());
					tmp = (Element) ele.getElementsByTagName("Status").item(0);
					spiel.setMatchStatus(getStatus(tmp.getFirstChild()
							.getNodeValue()));

					if (spiel.getMatchStatus() == MatchKurzInfo.FINISHED) {
						tmp = (Element) ele.getElementsByTagName("HomeGoals")
								.item(0);
						spiel.setHeimTore(Integer.parseInt(tmp.getFirstChild()
								.getNodeValue()));
						tmp = (Element) ele.getElementsByTagName("AwayGoals")
								.item(0);
						spiel.setGastTore(Integer.parseInt(tmp.getFirstChild()
								.getNodeValue()));
					} else if (spiel.getMatchStatus() == MatchKurzInfo.UPCOMING) {
						try {
							tmp = (Element) ele.getElementsByTagName(
									"OrdersGiven").item(0);
							spiel.setOrdersGiven(tmp.getFirstChild()
									.getNodeValue().equalsIgnoreCase("TRUE"));
						} catch (Exception e) {
							// We will end up here if the match is not the
							// user's
							spiel.setOrdersGiven(false);
						}
					}

					liste.add(spiel);
				}
			} catch (Exception e) {
				HOLogger.instance().log(XMLMatchesParser.class, e);
				liste.clear();
			}
		}
		return liste;
	}
}
