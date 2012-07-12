// %2960699267:de.hattrickorganizer.logik.xml%
/*
 * xmlEconomyParser.java
 *
 * Created on 7. Mai 2004, 16:29
 */
package ho.core.file.xml;


import ho.core.model.HOVerwaltung;
import ho.core.util.HOLogger;

import java.util.Hashtable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;



/**
 * DOCUMENT ME!
 *
 * @author thetom
 */
public class XMLEconomyParser {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of xmlEconomyParser
     */
    public XMLEconomyParser() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param dateiname TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Hashtable<?, ?> parseEconomy(String dateiname) {
        Document doc = null;

        doc = XMLManager.parseFile(dateiname);

        return parseDetails(doc);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param datei TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Hashtable<?, ?> parseEconomy(java.io.File datei) {
        Document doc = null;

        doc = XMLManager.parseFile(datei);

        return parseDetails(doc);
    }

    /////////////////////////////////////////////////////////////////////////////////
    //parse public
    ////////////////////////////////////////////////////////////////////////////////
    public final Hashtable<?, ?> parseEconomyFromString(String inputStream) {
        Document doc = null;

        doc = XMLManager.parseString(inputStream);

        return parseDetails(doc);
    }

    /////////////////////////////////////////////////////////////////////////////////
    //Parser Helper private
    ////////////////////////////////////////////////////////////////////////////////

    /**
     * erstellt das MAtchlineup Objekt
     *
     * @param doc TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected final Hashtable<?, ?> parseDetails(Document doc) {
        Element ele = null;
        Element root = null;
        final ho.core.file.xml.MyHashtable hash = new ho.core.file.xml.MyHashtable();

        if (doc == null) {
            return hash;
        }

        //Tabelle erstellen
        root = doc.getDocumentElement();

        try {
            //Daten füllen
            //Fetchdate
            ele = (Element) root.getElementsByTagName("FetchedDate").item(0);
            hash.put("FetchedDate", (XMLManager.getFirstChildNodeValue(ele)));

            //Root wechseln
            root = (Element) root.getElementsByTagName("Team").item(0);
            ele = (Element) root.getElementsByTagName("TeamID").item(0);
            hash.put("TeamID", (XMLManager.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("TeamName").item(0);
            hash.put("TeamName", (XMLManager.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("Cash").item(0);
            hash.put("Cash", (XMLManager.getFirstChildNodeValue(ele)));

            ele = (Element) root.getElementsByTagName("ExpectedCash").item(0);
            hash.put("ExpectedCash", (XMLManager.getFirstChildNodeValue(ele)));

            //Root wechseln
            // root =  (Element) doc.getDocumentElement().getElementsByTagName( "Team").item( 0 );
            ele = (Element) root.getElementsByTagName("SponsorsPopularity").item(0);

            if (XMLManager.getAttributeValue(ele, "Available").trim().equalsIgnoreCase("true")) {
                hash.put("SponsorsPopularity", (XMLManager.getFirstChildNodeValue(ele)));
            }

            ele = (Element) root.getElementsByTagName("SupportersPopularity").item(0);

            if (XMLManager.getAttributeValue(ele, "Available").trim().equalsIgnoreCase("true")) {
            	// workaround for HT bug from 19.02.2008: copy current supporter mood level
            	String supPop = XMLManager.getFirstChildNodeValue(ele);
            	if (supPop == null || supPop.trim().equals("")) {
            		supPop = ""+HOVerwaltung.instance().getModel().getFinanzen().getSupporter();
            	}
            	hash.put("SupportersPopularity", supPop);
            }

            ele = (Element) root.getElementsByTagName("FanClubSize").item(0);
            hash.put("FanClubSize", (XMLManager.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("IncomeSpectators").item(0);
            hash.put("IncomeSpectators", (XMLManager.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("IncomeSponsors").item(0);
            hash.put("IncomeSponsors", (XMLManager.getFirstChildNodeValue(ele)));

            //Root wechseln
            //root =  (Element) root.getElementsByTagName( "League").item( 0 );
            ele = (Element) root.getElementsByTagName("IncomeFinancial").item(0);
            hash.put("IncomeFinancial", (XMLManager.getFirstChildNodeValue(ele)));

            ele = (Element) root.getElementsByTagName("IncomeTemporary").item(0);
            hash.put("IncomeTemporary", (XMLManager.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("IncomeSum").item(0);
            hash.put("IncomeSum", (XMLManager.getFirstChildNodeValue(ele)));

            ele = (Element) root.getElementsByTagName("CostsArena").item(0);
            hash.put("CostsArena", (XMLManager.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("CostsPlayers").item(0);
            hash.put("CostsPlayers", (XMLManager.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("CostsFinancial").item(0);
            hash.put("CostsFinancial", (XMLManager.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("CostsTemporary").item(0);
            hash.put("CostsTemporary", (XMLManager.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("CostsStaff").item(0);
            hash.put("CostsStaff", (XMLManager.getFirstChildNodeValue(ele)));

            ele = (Element) root.getElementsByTagName("CostsYouth").item(0);
            hash.put("CostsYouth", (XMLManager.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("CostsSum").item(0);
            hash.put("CostsSum", (XMLManager.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("ExpectedWeeksTotal").item(0);
            hash.put("ExpectedWeeksTotal", (XMLManager.getFirstChildNodeValue(ele)));

            ele = (Element) root.getElementsByTagName("LastIncomeSpectators").item(0);
            hash.put("LastIncomeSpectators", (XMLManager.getFirstChildNodeValue(ele)));

            ele = (Element) root.getElementsByTagName("LastIncomeSponsors").item(0);
            hash.put("LastIncomeSponsors", (XMLManager.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("IncomeSpectators").item(0);
            hash.put("IncomeSpectators", (XMLManager.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("IncomeSponsors").item(0);
            hash.put("IncomeSponsors", (XMLManager.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("LastIncomeFinancial").item(0);
            hash.put("LastIncomeFinancial", (XMLManager.getFirstChildNodeValue(ele)));

            ele = (Element) root.getElementsByTagName("LastIncomeTemporary").item(0);
            hash.put("LastIncomeTemporary", (XMLManager.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("LastIncomeSum").item(0);
            hash.put("LastIncomeSum", (XMLManager.getFirstChildNodeValue(ele)));

            ele = (Element) root.getElementsByTagName("LastCostsArena").item(0);
            hash.put("LastCostsArena", (XMLManager.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("LastCostsPlayers").item(0);
            hash.put("LastCostsPlayers", (XMLManager.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("LastCostsFinancial").item(0);
            hash.put("LastCostsFinancial", (XMLManager.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("LastCostsTemporary").item(0);
            hash.put("LastCostsTemporary", (XMLManager.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("LastCostsStaff").item(0);
            hash.put("LastCostsStaff", (XMLManager.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("LastCostsYouth").item(0);
            hash.put("LastCostsYouth", (XMLManager.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("LastCostsSum").item(0);
            hash.put("LastCostsSum", (XMLManager.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("LastWeeksTotal").item(0);
            hash.put("LastWeeksTotal", (XMLManager.getFirstChildNodeValue(ele)));
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"XMLExonom<Parser.parseDetails Exception gefangen: " + e);
            HOLogger.instance().log(getClass(),e);
        }

        return hash;
    }
}
