package de.hattrickorganizer.logik.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.xml.XMLManager;

public final class XMLCHPPPreParser {

	private XMLCHPPPreParser() {
    }
	
	public static String getError(String xmlIn) {
		String sReturnError = "";
		final HOVerwaltung hov = HOVerwaltung.instance();
		if(xmlIn.length() > 0 ) {
	        Document doc = XMLManager.instance().parseString(xmlIn);;
	        if (doc != null) {
	        	Element ele = null;
	            Element root = doc.getDocumentElement();
	            try {
	            	// See if an error is found
	            	if (root.getElementsByTagName("ErrorCode").getLength() > 0) {
	            		sReturnError = "CHPP" + hov.getLanguageString("Fehler");
	            		ele = (Element) root.getElementsByTagName("ErrorCode").item(0);
	            		if (ele != null) {
	            			sReturnError += " " + XMLManager.instance().getFirstChildNodeValue(ele);
	            		}
	            		ele = (Element) root.getElementsByTagName("Error").item(0);
	            		if (ele != null) {
	            			sReturnError += " - " + XMLManager.instance().getFirstChildNodeValue(ele);
	            		}
	            	}
	            }
	        	catch (Exception ex)
	        	{
	        		 HOLogger.instance().log(XMLCHPPPreParser.class,"XMLCHPPPreParser Exception: " + ex);
	        		 sReturnError = "XMLCHPPPreParser Exception - " + ex.getMessage();
	        	}
	                
	        } else {
	        	sReturnError = hov.getLanguageString("NO_HRF_ERROR");
	        }
		} else {
			sReturnError = hov.getLanguageString("NO_HRF_ERROR");
        }
        return sReturnError;
    }
}
