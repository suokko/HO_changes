// %955353293:de.hattrickorganizer.tools.xml%
package de.hattrickorganizer.tools.xml;

import java.io.File;
import java.io.StringWriter;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import plugins.IMatchLineup;

import de.hattrickorganizer.logik.xml.XMLMatchLineupParser;
import de.hattrickorganizer.logik.xml.xmlTeamDetailsParser;
import de.hattrickorganizer.logik.xml.xmlWorldDetailsParser;
import de.hattrickorganizer.tools.HOLogger;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class XMLManager implements plugins.IXMLParser {
    //~ Static fields/initializers -----------------------------------------------------------------

    private static XMLManager m_clInstance;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of XMLManager
     */
    private XMLManager() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static XMLManager instance() {
        if (m_clInstance == null) {
            m_clInstance = new XMLManager();
        }

        return m_clInstance;
    }

    /**
     * liefert den Value des attributes sonst ""
     *
     * @param ele TODO Missing Constructuor Parameter Documentation
     * @param attributeName TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getAttributeValue(Element ele, String attributeName) {
        try {
            if ((ele != null) && (attributeName != null)) {
                return ele.getAttribute(attributeName);
            }
        } catch (Exception e) {
            return "";
        }

        return "";
    }

    /////////////////////////////////////////////////////////////////////////////////
    //Helper
    ///////////////////////////////////////////////////////////////////////////////

    /**
     * liefert den Value des ersten childes falls kein child vorhanden liefert ""
     *
     * @param ele TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getFirstChildNodeValue(Element ele) {
        try {
            if (ele.getFirstChild() != null) {
                return ele.getFirstChild().getNodeValue();
            }
        } catch (Exception e) {
        }

        return "";
    }

    /**
     * parsed eine übergebene Datei
     *
     * @param dateiname TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public Document parseFile(String dateiname) {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        Document doc = null;

        try {
            //Validierung, Namensräume einschalten
            //factory.setValidating ( false );
            //factory.setNamespaceAware ( true );
            builder = factory.newDocumentBuilder();

            doc = builder.parse(new File(dateiname));
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"Parser fehler: " + e);
            HOLogger.instance().log(getClass(),e);
        }

        return doc;
    }

    /**
     * parsed eine übergebene Datei
     *
     * @param datei TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public Document parseFile(java.io.File datei) {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        Document doc = null;

        try {
            //Validierung, Namensräume einschalten
            //factory.setValidating ( false );
            //factory.setNamespaceAware ( true );
            builder = factory.newDocumentBuilder();

            doc = builder.parse(datei);
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"Parser fehler: " + e);
            HOLogger.instance().log(getClass(),e);
        }

        return doc;
    }

    /**
     * parsed eine übergebene Datei
     *
     * @param inputString TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public Document parseString(String inputString) {
        //Fix to remove commented tag
        int indexComm = inputString.indexOf("<!--");

        while (indexComm > -1) {
            final int endComm = inputString.indexOf("-->");
            final String comment = inputString.substring(indexComm, endComm + 3);
            inputString = inputString.replaceAll(comment, "");
            indexComm = inputString.indexOf("<!--");
        }

        Document doc = null;

        try {
            final java.io.ByteArrayInputStream input = new java.io.ByteArrayInputStream(inputString
                                                                                        .getBytes("UTF-8"));
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;

            //Validierung, Namensräume einschalten
            //factory.setValidating ( false );
            //factory.setNamespaceAware ( true );
            builder = factory.newDocumentBuilder();

            doc = builder.parse(input);
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"Parser fehler: " + e);
            HOLogger.instance().log(getClass(),e);
        }

        return doc;
    }

    /**
     * speichert das übergebene Dokument in der angegebenen Datei Datei wird überschrieben falls
     * vorhanden
     *
     * @param doc TODO Missing Constructuor Parameter Documentation
     * @param dateiname TODO Missing Constructuor Parameter Documentation
     */
    public void writeXML(Document doc, String dateiname) {
        //Transformer creation for DOM rewriting into XML file
        Transformer serializer = null;
        DOMSource source = null;
        File datei = null;
        StreamResult result = null;

        try {
            //You can do any modification to the document here
            serializer = TransformerFactory.newInstance().newTransformer();
            source = new DOMSource(doc);
            datei = new File(dateiname);
            result = new StreamResult(datei);

            serializer.transform(source, result);
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"XMLManager.writeXML: " + e);
            HOLogger.instance().log(getClass(),e);
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param doc TODO Missing Method Parameter Documentation
     */
    protected void parseTabelle(Document doc) {
        Element ele = null;
        Element tmpEle = null;
        String titel = "";

        //gucken was es so gibt :
        ele = doc.getDocumentElement();

        HOLogger.instance().log(getClass(),"Root Name: " + ele.getTagName());

        HOLogger.instance().log(getClass(),"Root Attr: " + ele.getAttributes().getLength());
        HOLogger.instance().log(getClass(),"Root Sub Team Nodes: " + ele.getElementsByTagName("Team").getLength());
        HOLogger.instance().log(getClass(),"Root Gesamt Nodes: " + ele.getChildNodes().getLength());

        tmpEle = (Element) ele.getElementsByTagName("LeagueName").item(0);

        HOLogger.instance().log(getClass(),"tmp Name: " + tmpEle.getTagName());
        HOLogger.instance().log(getClass(),"tmp Namespace: " + tmpEle.getNamespaceURI());
        HOLogger.instance().log(getClass(),"tmp first Child Value: " + tmpEle.getFirstChild().getNodeValue());
        HOLogger.instance().log(getClass(),"tmp first Child Value: " + tmpEle.getFirstChild().getNodeName());
        HOLogger.instance().log(getClass(),"tmp Anz Childs: " + tmpEle.getChildNodes().getLength());
        titel = " " + ele.getElementsByTagName("LeagueName").getLength();
        titel += (" " + ele.getElementsByTagName("LeagueName").item(0).getNodeType());
        titel += (" "
        + ele.getElementsByTagName("LeagueLevelUnitName").item(0).getAttributes().getLength());

        HOLogger.instance().log(getClass(),"Titel: " + titel);
    }

	/**
	 * speichert das übergebene Dokument in der angegebenen Datei Datei wird überschrieben falls
	 * vorhanden
	 *
	 * @param doc TODO Missing Constructuor Parameter Documentation
	 * @param dateiname TODO Missing Constructuor Parameter Documentation
	 */
	public String getXML(Document doc) {
		//Transformer creation for DOM rewriting into XML String
		Transformer serializer = null;
		DOMSource source = null;
		StreamResult result = null;
		String xml = "";
		try {
			//You can do any modification to the document here
			serializer = TransformerFactory.newInstance().newTransformer();
			source = new DOMSource(doc);
			StringWriter sw = new StringWriter();
			result = new StreamResult(sw);
			serializer.transform(source, result);
			xml = sw.toString();
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"XMLManager.writeXML: " + e);
			HOLogger.instance().log(getClass(),e);
		}
		return xml;
	}

	/**
	 * Parse the teamDetails from the given xml string.
	 */
	public Hashtable parseTeamDetails(String teamDetails) {
		return new xmlTeamDetailsParser().parseTeamdetails(teamDetails);
	}

	/**
	 * Parse the worldDetails from the given xml string.
	 */
	public Hashtable parseWorldDetails(String worldDetails, String leagueID) {
		return new xmlWorldDetailsParser().parseWorldDetailsFromString(worldDetails, leagueID);
	}

	/**
	 * Parse the match lineup from the given xml string.
	 */
	public IMatchLineup parseMatchLineup(String lineup) {
		return new XMLMatchLineupParser().parseMatchLineupFromString(lineup);
	}
}
