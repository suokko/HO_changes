// %955353293:de.hattrickorganizer.tools.xml%
package de.hattrickorganizer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.hattrickorganizer.tools.xml.XMLManager;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class TestXMLManager {

    public static void main(String[] args) {
        try {
            //parsen testen
            final XMLManager xm = XMLManager.instance();
            Document doc = null;
            Element ele = null;
            Element tmpEle = null;
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();

            //doc =   xm.parseFile ( "c://tabelle.xml" );        
            //xm.parseTabelle( doc ); 
            doc = builder.newDocument();
            tmpEle = doc.createElement("HO_Data");
            doc.appendChild(tmpEle);
            ele = doc.createElement("Test");
            tmpEle.appendChild(ele);

            //ele.setNodeValue ( "Mains" );               
            ele.appendChild(doc.createTextNode("dada"));
            ele.setAttribute("index", "0");

            //child2
            ele = doc.createElement("Test");
            tmpEle.appendChild(ele);

            //ele.setNodeValue ( "Mains" );               
            ele.appendChild(doc.createTextNode("dada2"));
            ele.setAttribute("index", "1");

            //doc.appendChild ( ele );
            xm.writeXML(doc, "c:\\test.xml");
        } catch (Exception e) {
            System.out.println("XMLManager.writeXML: " + e);
            e.printStackTrace();
        }
    }
}
