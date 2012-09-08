// %168276825:de.hattrickorganizer.logik.xml%
/*
 * xmlTeamDetailsParser.java
 *
 * Created on 12. Januar 2004, 10:26
 */
package ho.core.file.xml;

import ho.core.util.HOLogger;

import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 
 * @author thomas.werth
 */
public class XMLTeamDetailsParser {

	/**
	 * Utility class - private constructor enforces noninstantiability.
	 */
	private XMLTeamDetailsParser() {
	}

	public static String fetchRegionID(String xmlFile) {
		try {
			Document doc = XMLManager.parseString(xmlFile);

			if (doc == null) {
				return "-1";
			}

			// Tabelle erstellen
			Element root = doc.getDocumentElement();

			// Root wechseln
			root = (Element) root.getElementsByTagName("Team").item(0);
			root = (Element) root.getElementsByTagName("Region").item(0);
			Element ele = (Element) root.getElementsByTagName("RegionID").item(0);
			return XMLManager.getFirstChildNodeValue(ele);
		} catch (Exception ex) {
			HOLogger.instance().log(XMLTeamDetailsParser.class, ex);
		}

		return "-1";
	}

	public static Map<String, String> parseTeamdetailsFromString(String inputStream) {
		return parseDetails(XMLManager.parseString(inputStream));
	}

	private static Map<String, String> parseDetails(Document doc) {
		Element ele = null;
		Element root = null;
		Map<String, String> hash = new ho.core.file.xml.MyHashtable();

		if (doc == null) {
			return hash;
		}

		// Tabelle erstellen
		root = doc.getDocumentElement();

		try {
			// Daten füllen
			// Fetchdate
			ele = (Element) root.getElementsByTagName("FetchedDate").item(0);
			hash.put("FetchedDate", (XMLManager.getFirstChildNodeValue(ele)));

			// Root wechseln
			root = (Element) root.getElementsByTagName("User").item(0);
			ele = (Element) root.getElementsByTagName("Loginname").item(0);
			hash.put("Loginname", (XMLManager.getFirstChildNodeValue(ele)));
			ele = (Element) root.getElementsByTagName("LastLoginDate").item(0);
			hash.put("LastLoginDate", (XMLManager.getFirstChildNodeValue(ele)));
			ele = (Element) root.getElementsByTagName("ActivationDate").item(0);
			hash.put("ActivationDate", (XMLManager.getFirstChildNodeValue(ele)));

			// Is this in the xml? - Blaghaid
			ele = (Element) root.getElementsByTagName("Email").item(0);
			hash.put("Email", (XMLManager.getFirstChildNodeValue(ele)));

			ele = (Element) root.getElementsByTagName("ICQ").item(0);
			hash.put("ICQ", (XMLManager.getFirstChildNodeValue(ele)));

			ele = (Element) root.getElementsByTagName("HasSupporter").item(0);
			hash.put("HasSupporter", (XMLManager.getFirstChildNodeValue(ele)));

			// Root wechseln
			root = (Element) doc.getDocumentElement().getElementsByTagName("Team").item(0);
			ele = (Element) root.getElementsByTagName("TeamID").item(0);
			hash.put("TeamID", (XMLManager.getFirstChildNodeValue(ele)));
			ele = (Element) root.getElementsByTagName("TeamName").item(0);
			hash.put("TeamName", (XMLManager.getFirstChildNodeValue(ele)));
			ele = (Element) root.getElementsByTagName("HomePage").item(0);
			hash.put("HomePage", (XMLManager.getFirstChildNodeValue(ele)));
			ele = (Element) root.getElementsByTagName("LogoURL").item(0);
			hash.put("LogoURL", (XMLManager.getFirstChildNodeValue(ele)));

			// Root wechseln
			root = (Element) root.getElementsByTagName("League").item(0);
			ele = (Element) root.getElementsByTagName("LeagueID").item(0);
			hash.put("LeagueID", (XMLManager.getFirstChildNodeValue(ele)));

			try {
				// Root wechseln
				root = (Element) doc.getDocumentElement().getElementsByTagName("Team").item(0);
				root = (Element) root.getElementsByTagName("LeagueLevelUnit").item(0);
				ele = (Element) root.getElementsByTagName("LeagueLevel").item(0);
				hash.put("LeagueLevel", (XMLManager.getFirstChildNodeValue(ele)));
				ele = (Element) root.getElementsByTagName("LeagueLevelUnitName").item(0);
				hash.put("LeagueLevelUnitName", (XMLManager.getFirstChildNodeValue(ele)));
				ele = (Element) root.getElementsByTagName("LeagueLevelUnitID").item(0);
				hash.put("LeagueLevelUnitID", (XMLManager.getFirstChildNodeValue(ele)));
			} catch (Exception ex) {
				HOLogger.instance().log(XMLTeamDetailsParser.class, ex);
			}

			try {
				// Root wechseln
				root = (Element) doc.getDocumentElement().getElementsByTagName("Team").item(0);
				ele = (Element) root.getElementsByTagName("NumberOfVictories").item(0);
				hash.put("NumberOfVictories", (XMLManager.getFirstChildNodeValue(ele)));
				ele = (Element) root.getElementsByTagName("NumberOfUndefeated").item(0);
				hash.put("NumberOfUndefeated", (XMLManager.getFirstChildNodeValue(ele)));
			} catch (Exception exp) {
				HOLogger.instance().log(XMLTeamDetailsParser.class, exp);
			}

			// Root wechseln //TrainerID adden
			root = (Element) doc.getDocumentElement().getElementsByTagName("Team").item(0);
			root = (Element) root.getElementsByTagName("Trainer").item(0);
			ele = (Element) root.getElementsByTagName("PlayerID").item(0);
			hash.put("TrainerID", (XMLManager.getFirstChildNodeValue(ele)));

			// Root wechseln //StadionName adden
			root = (Element) doc.getDocumentElement().getElementsByTagName("Team").item(0);
			root = (Element) root.getElementsByTagName("Arena").item(0);
			ele = (Element) root.getElementsByTagName("ArenaName").item(0);
			hash.put("ArenaName", (XMLManager.getFirstChildNodeValue(ele)));

			// Root wechseln //RegionId
			root = (Element) doc.getDocumentElement().getElementsByTagName("Team").item(0);
			root = (Element) root.getElementsByTagName("Region").item(0);
			ele = (Element) root.getElementsByTagName("RegionID").item(0);
			hash.put("RegionID", (XMLManager.getFirstChildNodeValue(ele)));
		} catch (Exception e) {
			HOLogger.instance().log(XMLTeamDetailsParser.class, e);
		}

		return hash;
	}
}
