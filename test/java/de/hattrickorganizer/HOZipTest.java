/*
 * Created on 12-nov-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package de.hattrickorganizer;

import de.hattrickorganizer.tools.backup.HOZip;

/**
 * @author Mirtillo
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HOZipTest {
	
	public static void main(String[] args) throws Exception {
		HOZip zip = new HOZip("test.zip");
		String xmlfile = "Desperados.A.C..xml";
		String xml = "<tklasdjk>asfjkdse>/sfklòa";
		zip.addStringEntry(xmlfile,xml);
		zip.closeArchive();
		
	}
}
