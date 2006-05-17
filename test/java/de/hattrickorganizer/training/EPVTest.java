package de.hattrickorganizer.training;

import java.util.Iterator;

import plugins.IEPVData;
import plugins.ISpieler;
import de.hattrickorganizer.core.HOSetup;

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
public class EPVTest extends HOSetup {

	public static void main(String[] args) {
		new EPVTest();					
	}
	
	private EPVTest() {
		super();
		Iterator it = MODEL.getAllSpieler().iterator();
		for (; it.hasNext();) {
			ISpieler element = (ISpieler) it.next();
			IEPVData data = MODEL.getEPV().getEPVData(element);
			double price = MODEL.getEPV().getPrice(data);
			System.out.println(element.getName() + " " + price);
		}
	}

}
