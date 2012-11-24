/*
 * Created on 14-gen-2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package ho.core.file.extension;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author Mirtillo
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class XMLCreator {

	
	protected static Node createNode(Document doc, String name, String value) {
		Element ele = doc.createElement(name);
		ele.appendChild(doc.createTextNode(value));
		return ele;
	}
}
