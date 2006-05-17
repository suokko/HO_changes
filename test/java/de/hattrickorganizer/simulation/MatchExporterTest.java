package de.hattrickorganizer.simulation;

import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import de.hattrickorganizer.core.HOSetup;
import de.hattrickorganizer.gui.exporter.XMLExporter;

/*
 * Created on 5-lug-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author Mirtillo
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MatchExporterTest extends HOSetup  {

	public static void main(String[] args) {
		new MatchExporterTest();
	}

	public MatchExporterTest() {
		Date LAST_CHANGE = new GregorianCalendar(2005, 7, 6).getTime();
		XMLExporter xml = new XMLExporter();
		xml.saveXML("test.zip",new Timestamp(LAST_CHANGE.getTime()));
	}



}
