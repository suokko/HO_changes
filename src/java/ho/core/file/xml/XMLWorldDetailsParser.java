// %3205245740:de.hattrickorganizer.logik.xml%
/*
 * xmlLeagureFixturesMiniParser.java
 *
 * Created on 12. Januar 2004, 13:38
 */
package ho.core.file.xml;


import ho.core.model.WorldDetailLeague;
import ho.core.util.HOLogger;

import java.util.ArrayList;
import java.util.Hashtable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;



class XMLWorldDetailsParser {
 
    XMLWorldDetailsParser() {
    }

    final Hashtable<String,String> parseWorldDetailsFromString(String inputStream, String leagueID) {
        Document doc = null;

        doc = XMLManager.instance().parseString(inputStream);

        return parseDetails(doc, leagueID);
    }

    final Hashtable<String,String> parseDetails(Document doc, String leagueID) {
        Element ele = null;
        Element root = null;
        final MyHashtable hash = new MyHashtable();
        NodeList list = null;
        String tempLeagueID = null;

        if (doc == null) {
            return hash;
        }

        //Tabelle erstellen
        root = doc.getDocumentElement();

        try {
            //Daten füllen            
            root = (Element) root.getElementsByTagName("LeagueList").item(0);

            //Einträge adden
            list = root.getElementsByTagName("League");

            for (int i = 0; (list != null) && (i < list.getLength()); i++) {
                tempLeagueID = XMLManager.instance().getFirstChildNodeValue((Element) ((Element) list
                                                                                       .item(i)).getElementsByTagName("LeagueID")
                                                                                       .item(0));

                //Liga suchen
                if (tempLeagueID.equals(leagueID)) {
                    root = (Element) list.item(i);

                    //Land
                    ele = (Element) root.getElementsByTagName("LeagueID").item(0);
                    hash.put("LeagueID", (XMLManager.instance().getFirstChildNodeValue(ele)));
                    ele = (Element) root.getElementsByTagName("Season").item(0);
                    hash.put("Season", (XMLManager.instance().getFirstChildNodeValue(ele)));
                    ele = (Element) root.getElementsByTagName("MatchRound").item(0);
                    hash.put("MatchRound", (XMLManager.instance().getFirstChildNodeValue(ele)));

                    //Dati
                    ele = (Element) root.getElementsByTagName("TrainingDate").item(0);
                    hash.put("TrainingDate", (XMLManager.instance().getFirstChildNodeValue(ele)));
                    ele = (Element) root.getElementsByTagName("EconomyDate").item(0);
                    hash.put("EconomyDate", (XMLManager.instance().getFirstChildNodeValue(ele)));
                    ele = (Element) root.getElementsByTagName("SeriesMatchDate").item(0);
                    hash.put("SeriesMatchDate", (XMLManager.instance().getFirstChildNodeValue(ele)));

                    //Country
                    root = (Element) root.getElementsByTagName("Country").item(0);
                    ele = (Element) root.getElementsByTagName("CountryID").item(0);
                    hash.put("CountryID", (XMLManager.instance().getFirstChildNodeValue(ele)));
                    ele = (Element) root.getElementsByTagName("CurrencyName").item(0);
                    hash.put("CurrencyName", (XMLManager.instance().getFirstChildNodeValue(ele)));
                    ele = (Element) root.getElementsByTagName("CurrencyRate").item(0);
                    hash.put("CurrencyRate", (XMLManager.instance().getFirstChildNodeValue(ele)));

                    //fertig
                    break;
                }
            }
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"XMLTeamDetailsParser.parseDetails Exception gefangen: " + e);
            HOLogger.instance().log(getClass(),e);
        }

        return hash;
    }
    
    final WorldDetailLeague[] parseDetails(Document doc) {
    	Element ele = null;
        Element root = null;
        final ArrayList<WorldDetailLeague> arrayList = new ArrayList<WorldDetailLeague>();
        NodeList list = null;
        XMLManager xml = XMLManager.instance();
        if (doc == null) {
            return new WorldDetailLeague[0];
        }

        //Tabelle erstellen
        root = doc.getDocumentElement();
        
        try {
            root = (Element) root.getElementsByTagName("LeagueList").item(0);
            list = root.getElementsByTagName("League");

            for (int i = 0; (list != null) && (i < list.getLength()); i++) {
                 root = (Element) list.item(i);
                 WorldDetailLeague tmp = new WorldDetailLeague();
                 ele = (Element) root.getElementsByTagName("LeagueID").item(0);
                 tmp.setLeagueId(Integer.parseInt(xml.getFirstChildNodeValue(ele)));
                 ele = (Element) root.getElementsByTagName("EnglishName").item(0);
                 tmp.setCountryName(xml.getFirstChildNodeValue(ele));
                 ele = (Element) root.getElementsByTagName("ActiveUsers").item(0);
                 tmp.setActiveUsers(Integer.parseInt(xml.getFirstChildNodeValue(ele)));
                 
                 root = (Element) root.getElementsByTagName("Country").item(0);
                  ele = (Element) root.getElementsByTagName("CountryID").item(0);
                  tmp.setCountryId(Integer.parseInt(xml.getFirstChildNodeValue(ele)));
                  arrayList.add(tmp);
                }
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"XMLTeamDetailsParser.parseDetails Exception gefangen: " + e);
            HOLogger.instance().log(getClass(),e);
        }

        return arrayList.toArray(new WorldDetailLeague[arrayList.size()]);
    }
}
