package de.hattrickorganizer.simulation;

import de.hattrickorganizer.core.HOSetup;
import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.tools.extension.FileExtensionManager;
import de.hattrickorganizer.tools.extension.StandingCreator;

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
public class ExtensionTest extends HOSetup  {

	public static void main(String[] args) {
		new ExtensionTest();
	}

	public ExtensionTest() {
		FileExtensionManager.modelUpdate();
		//FileExtensionManager.trainingUpdate();
		//FileExtensionManager.economyUpate();
		//StadiumCreator.extractHistoric();
		//StandingCreator.extractActual();
		DBZugriff.instance().disconnect();
	}



}
