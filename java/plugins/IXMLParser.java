// %1127327012587:plugins%
/*
 * IXMLParser.java
 *
 * Created on 29. März 2004, 07:45
 */
package plugins;

import java.util.Hashtable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth Interface for xml Parser
 */
/**
 * Interface to a simple xml Parser , see dummyplugin for example
 */
public interface IXMLParser {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Get's value of first node of passed elemente
     *
     * @param ele TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     *
     * @throws Exception TODO Missing Constructuor Exception Documentation
     */
    public String getFirstChildNodeValue(Element ele) throws Exception;

    /**
     * parse XML from File by Filename
     *
     * @param dateiname TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public Document parseFile(String dateiname);

    /**
     * parse XML from File by file
     *
     * @param datei TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public Document parseFile(java.io.File datei);

    /**
     * parse XML from String
     *
     * @param inputString TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public Document parseString(String inputString);

    /**
     * speichert das übergebene Dokument in der angegebenen Datei Datei wird überschrieben falls
     * vorhanden
     *
     * @param doc TODO Missing Constructuor Parameter Documentation
     * @param dateiname TODO Missing Constructuor Parameter Documentation
     */
    public void writeXML(Document doc, String dateiname);

    /**
     * Parse the teamDetails from the given xml string.
     */
    public Hashtable parseTeamDetails(String teamDetailString);

    /**
	 * Parse the worldDetails from the given xml string.
	 */
	public Hashtable parseWorldDetails(String worldDetails, String leagueID);

	/**
	 * Parse the match lineup from the given xml string.
	 */
	public IMatchLineup parseMatchLineup(String lineup);
}
