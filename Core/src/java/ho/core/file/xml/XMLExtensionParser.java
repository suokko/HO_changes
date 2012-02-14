// %2675225597:de.hattrickorganizer.logik.xml%
/*
 * XMLArenaParser.java
 *
 * Created on 5. Juni 2004, 15:40
 */
package ho.core.file.xml;


import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.hattrickorganizer.model.Extension;
import de.hattrickorganizer.tools.HOLogger;


/**
 * DOCUMENT ME!
 *
 * @author thetom
 */
public class XMLExtensionParser {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of XMLArenaParser
     */
    public XMLExtensionParser() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param dateiname TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Extension parseExtension(String dateiname) {
        Document doc = null;

        doc = XMLManager.instance().parseString(dateiname);

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
    protected final Extension parseDetails(Document doc) {
		Extension ext = new Extension();	
        if (doc == null) {
            return ext;
        }

        //Tabelle erstellen
		Element root = doc.getDocumentElement();

        try {
            //Fetchdate
			ext.setRelease(Float.parseFloat(getTagValue(root, "release")));
			ext.setMinimumHOVersion(Float.parseFloat(getTagValue(root, "hoNeeded")));
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"XMLExonom<Parser.parseDetails Exception gefangen: " + e);            
        }
        return ext;
    }

	private String getTagValue(Element root, String tag) {
		final Element ele = (Element) root.getElementsByTagName(tag).item(0);
		return (XMLManager.instance().getFirstChildNodeValue(ele));
	}
}
