// %1180669679:de.hattrickorganizer.logik.xml%
/*
 * XMLClubParser.java
 *
 * Created on 4. November 2003, 14:37
 */
package ho.core.file.xml;


import ho.core.model.misc.Verein;
import ho.core.util.HOLogger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;



/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class XMLClubParser {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of XMLClubParser
     */
    public XMLClubParser() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /////////////////////////////////////////////////////////////////////////////////    
    //parse public
    ////////////////////////////////////////////////////////////////////////////////    
    public final Verein parseClub(String dateiname) {
    	return parseVerein(XMLManager.parseFile(dateiname));
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param datei TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Verein parseClub(java.io.File datei) {
        return parseVerein(XMLManager.parseFile(datei));
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param inputStream TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final java.util.Hashtable<?, ?> parseClubFromString(String inputStream) {
        return parseDetails(XMLManager.parseString(inputStream));
    }

    /**
     * erstellt das MAtchlineup Objekt
     *
     * @param doc TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected final java.util.Hashtable<?, ?> parseDetails(Document doc) {
        Element ele = null;
        Element root = null;
        ho.core.file.xml.MyHashtable club = new ho.core.file.xml.MyHashtable();

        if (doc == null) {
            return club;
        }

        //Tabelle erstellen
        root = doc.getDocumentElement();

        try {
            //Daten füllen
            ele = (Element) root.getElementsByTagName("FetchedDate").item(0);
            club.put("FetchedDate", XMLManager.getFirstChildNodeValue(ele));

            //Root wechseln
            root = (Element) root.getElementsByTagName("Team").item(0);
            ele = (Element) root.getElementsByTagName("TeamID").item(0);
            club.put("TeamID", XMLManager.getFirstChildNodeValue(ele));
            ele = (Element) root.getElementsByTagName("TeamName").item(0);
            club.put("TeamName", XMLManager.getFirstChildNodeValue(ele));

            //nochmal root wechseln
            root = (Element) doc.getDocumentElement().getElementsByTagName("Specialists").item(0);
            ele = (Element) root.getElementsByTagName("Doctors").item(0);
            club.put("Doctors", XMLManager.getFirstChildNodeValue(ele));
            ele = (Element) root.getElementsByTagName("PressSpokesmen").item(0);
            club.put("PressSpokesmen", XMLManager.getFirstChildNodeValue(ele));
            ele = (Element) root.getElementsByTagName("AssistantTrainers").item(0);
            club.put("AssistantTrainers", XMLManager.getFirstChildNodeValue(ele));
            ele = (Element) root.getElementsByTagName("Physiotherapists").item(0);
            club.put("Physiotherapists", XMLManager.getFirstChildNodeValue(ele));
            ele = (Element) root.getElementsByTagName("Psychologists").item(0);
            club.put("Psychologists", XMLManager.getFirstChildNodeValue(ele));

            //und nochmal root wechseln
            root = (Element) doc.getDocumentElement().getElementsByTagName("YouthSquad").item(0);
            ele = (Element) root.getElementsByTagName("Investment").item(0);
            club.put("Investment", XMLManager.getFirstChildNodeValue(ele));
            ele = (Element) root.getElementsByTagName("HasPromoted").item(0);
            club.put("HasPromoted", XMLManager.getFirstChildNodeValue(ele));
            ele = (Element) root.getElementsByTagName("YouthLevel").item(0);
            club.put("YouthLevel", XMLManager.getFirstChildNodeValue(ele));
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"XMLClubParser.createVerein Exception gefangen: " + e);
            HOLogger.instance().log(getClass(),e);
            club = null;
        }

        return club;
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
    protected final Verein parseVerein(Document doc) {
        Element ele = null;
        Element root = null;
        Verein club = new Verein();

        if (doc == null) {
            return club;
        }

        //Tabelle erstellen
        root = doc.getDocumentElement();

        try {
            //Daten füllen
            ele = (Element) root.getElementsByTagName("FetchedDate").item(0);
            club.setDateFromString(ele.getFirstChild().getNodeValue());

            //Root wechseln
            root = (Element) root.getElementsByTagName("Team").item(0);
            ele = (Element) root.getElementsByTagName("TeamID").item(0);
            club.setTeamID(Integer.parseInt(ele.getFirstChild().getNodeValue()));
            ele = (Element) root.getElementsByTagName("TeamName").item(0);
            club.setTeamName(ele.getFirstChild().getNodeValue());

            //nochmal root wechseln
            root = (Element) doc.getDocumentElement().getElementsByTagName("Specialists").item(0);
            ele = (Element) root.getElementsByTagName("Doctors").item(0);
            club.setAerzte(Integer.parseInt(ele.getFirstChild().getNodeValue()));
            ele = (Element) root.getElementsByTagName("PressSpokesmen").item(0);
            club.setPRManager(Integer.parseInt(ele.getFirstChild().getNodeValue()));
            ele = (Element) root.getElementsByTagName("AssistantTrainers").item(0);
            club.setCoTrainer(Integer.parseInt(ele.getFirstChild().getNodeValue()));
            ele = (Element) root.getElementsByTagName("Physiotherapists").item(0);
            club.setMasseure(Integer.parseInt(ele.getFirstChild().getNodeValue()));
            ele = (Element) root.getElementsByTagName("Psychologists").item(0);
            club.setPsychologen(Integer.parseInt(ele.getFirstChild().getNodeValue()));

            //und nochmal root wechseln
            root = (Element) doc.getDocumentElement().getElementsByTagName("YouthSquad").item(0);
            ele = (Element) root.getElementsByTagName("Investment").item(0);
            club.setJugendGeld(Integer.parseInt(ele.getFirstChild().getNodeValue()));
            ele = (Element) root.getElementsByTagName("HasPromoted").item(0);
            club.setYouthPull(Boolean.valueOf(ele.getFirstChild().getNodeValue()).booleanValue());
            ele = (Element) root.getElementsByTagName("YouthLevel").item(0);
            club.setJugend(Integer.parseInt(ele.getFirstChild().getNodeValue()));
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"XMLClubParser.createVerein Exception gefangen: " + e);
            HOLogger.instance().log(getClass(),e);
            club = null;
        }

        return club;
    }
}
