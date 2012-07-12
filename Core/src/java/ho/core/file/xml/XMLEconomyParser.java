// %2960699267:de.hattrickorganizer.logik.xml%
/*
 * xmlEconomyParser.java
 *
 * Created on 7. Mai 2004, 16:29
 */
package ho.core.file.xml;

import ho.core.model.HOVerwaltung;
import ho.core.util.HOLogger;

import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 
 * @author thetom
 */
public class XMLEconomyParser {

	/**
	 * Utility class - private constructor enforces noninstantiability.
	 */
	private XMLEconomyParser() {
	}

	public static Map<String, String> parseEconomyFromString(String inputStream) {
		return parseDetails(XMLManager.parseString(inputStream));
	}

	private static Map<String, String> parseDetails(Document doc) {
		Map<String, String> map = new MyHashtable();

		if (doc == null) {
			return map;
		}

		try {
			Element root = doc.getDocumentElement();
			Element ele = (Element) root.getElementsByTagName("FetchedDate").item(0);
			map.put("FetchedDate", (XMLManager.getFirstChildNodeValue(ele)));

			// Root wechseln
			root = (Element) root.getElementsByTagName("Team").item(0);
			ele = (Element) root.getElementsByTagName("TeamID").item(0);
			map.put("TeamID", (XMLManager.getFirstChildNodeValue(ele)));
			ele = (Element) root.getElementsByTagName("TeamName").item(0);
			map.put("TeamName", (XMLManager.getFirstChildNodeValue(ele)));
			ele = (Element) root.getElementsByTagName("Cash").item(0);
			map.put("Cash", (XMLManager.getFirstChildNodeValue(ele)));

			ele = (Element) root.getElementsByTagName("ExpectedCash").item(0);
			map.put("ExpectedCash", (XMLManager.getFirstChildNodeValue(ele)));

			ele = (Element) root.getElementsByTagName("SponsorsPopularity")
					.item(0);

			if (XMLManager.getAttributeValue(ele, "Available").trim()
					.equalsIgnoreCase("true")) {
				map.put("SponsorsPopularity",
						(XMLManager.getFirstChildNodeValue(ele)));
			}

			ele = (Element) root.getElementsByTagName("SupportersPopularity")
					.item(0);

			if (XMLManager.getAttributeValue(ele, "Available").trim()
					.equalsIgnoreCase("true")) {
				// workaround for HT bug from 19.02.2008: copy current supporter
				// mood level
				String supPop = XMLManager.getFirstChildNodeValue(ele);
				if (supPop == null || supPop.trim().equals("")) {
					supPop = ""
							+ HOVerwaltung.instance().getModel().getFinanzen()
									.getSupporter();
				}
				map.put("SupportersPopularity", supPop);
			}

			ele = (Element) root.getElementsByTagName("FanClubSize").item(0);
			map.put("FanClubSize", (XMLManager.getFirstChildNodeValue(ele)));
			ele = (Element) root.getElementsByTagName("IncomeSpectators").item(
					0);
			map.put("IncomeSpectators",
					(XMLManager.getFirstChildNodeValue(ele)));
			ele = (Element) root.getElementsByTagName("IncomeSponsors").item(0);
			map.put("IncomeSponsors", (XMLManager.getFirstChildNodeValue(ele)));

			// Root wechseln
			ele = (Element) root.getElementsByTagName("IncomeFinancial")
					.item(0);
			map.put("IncomeFinancial",
					(XMLManager.getFirstChildNodeValue(ele)));

			ele = (Element) root.getElementsByTagName("IncomeTemporary")
					.item(0);
			map.put("IncomeTemporary",
					(XMLManager.getFirstChildNodeValue(ele)));
			ele = (Element) root.getElementsByTagName("IncomeSum").item(0);
			map.put("IncomeSum", (XMLManager.getFirstChildNodeValue(ele)));

			ele = (Element) root.getElementsByTagName("CostsArena").item(0);
			map.put("CostsArena", (XMLManager.getFirstChildNodeValue(ele)));
			ele = (Element) root.getElementsByTagName("CostsPlayers").item(0);
			map.put("CostsPlayers", (XMLManager.getFirstChildNodeValue(ele)));
			ele = (Element) root.getElementsByTagName("CostsFinancial").item(0);
			map.put("CostsFinancial", (XMLManager.getFirstChildNodeValue(ele)));
			ele = (Element) root.getElementsByTagName("CostsTemporary").item(0);
			map.put("CostsTemporary", (XMLManager.getFirstChildNodeValue(ele)));
			ele = (Element) root.getElementsByTagName("CostsStaff").item(0);
			map.put("CostsStaff", (XMLManager.getFirstChildNodeValue(ele)));

			ele = (Element) root.getElementsByTagName("CostsYouth").item(0);
			map.put("CostsYouth", (XMLManager.getFirstChildNodeValue(ele)));
			ele = (Element) root.getElementsByTagName("CostsSum").item(0);
			map.put("CostsSum", (XMLManager.getFirstChildNodeValue(ele)));
			ele = (Element) root.getElementsByTagName("ExpectedWeeksTotal")
					.item(0);
			map.put("ExpectedWeeksTotal",
					(XMLManager.getFirstChildNodeValue(ele)));

			ele = (Element) root.getElementsByTagName("LastIncomeSpectators")
					.item(0);
			map.put("LastIncomeSpectators",
					(XMLManager.getFirstChildNodeValue(ele)));

			ele = (Element) root.getElementsByTagName("LastIncomeSponsors")
					.item(0);
			map.put("LastIncomeSponsors",
					(XMLManager.getFirstChildNodeValue(ele)));
			ele = (Element) root.getElementsByTagName("IncomeSpectators").item(
					0);
			map.put("IncomeSpectators",
					(XMLManager.getFirstChildNodeValue(ele)));
			ele = (Element) root.getElementsByTagName("IncomeSponsors").item(0);
			map.put("IncomeSponsors", (XMLManager.getFirstChildNodeValue(ele)));
			ele = (Element) root.getElementsByTagName("LastIncomeFinancial")
					.item(0);
			map.put("LastIncomeFinancial",
					(XMLManager.getFirstChildNodeValue(ele)));

			ele = (Element) root.getElementsByTagName("LastIncomeTemporary")
					.item(0);
			map.put("LastIncomeTemporary",
					(XMLManager.getFirstChildNodeValue(ele)));
			ele = (Element) root.getElementsByTagName("LastIncomeSum").item(0);
			map.put("LastIncomeSum", (XMLManager.getFirstChildNodeValue(ele)));

			ele = (Element) root.getElementsByTagName("LastCostsArena").item(0);
			map.put("LastCostsArena", (XMLManager.getFirstChildNodeValue(ele)));
			ele = (Element) root.getElementsByTagName("LastCostsPlayers").item(
					0);
			map.put("LastCostsPlayers",
					(XMLManager.getFirstChildNodeValue(ele)));
			ele = (Element) root.getElementsByTagName("LastCostsFinancial")
					.item(0);
			map.put("LastCostsFinancial",
					(XMLManager.getFirstChildNodeValue(ele)));
			ele = (Element) root.getElementsByTagName("LastCostsTemporary")
					.item(0);
			map.put("LastCostsTemporary",
					(XMLManager.getFirstChildNodeValue(ele)));
			ele = (Element) root.getElementsByTagName("LastCostsStaff").item(0);
			map.put("LastCostsStaff", (XMLManager.getFirstChildNodeValue(ele)));
			ele = (Element) root.getElementsByTagName("LastCostsYouth").item(0);
			map.put("LastCostsYouth", (XMLManager.getFirstChildNodeValue(ele)));
			ele = (Element) root.getElementsByTagName("LastCostsSum").item(0);
			map.put("LastCostsSum", (XMLManager.getFirstChildNodeValue(ele)));
			ele = (Element) root.getElementsByTagName("LastWeeksTotal").item(0);
			map.put("LastWeeksTotal", (XMLManager.getFirstChildNodeValue(ele)));
		} catch (Exception e) {
			HOLogger.instance().log(XMLEconomyParser.class, e);
		}

		return map;
	}
}
