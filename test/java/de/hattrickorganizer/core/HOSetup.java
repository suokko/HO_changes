// %3176130593:hoplugins%
package de.hattrickorganizer.core;

import gui.UserParameter;

import java.util.Vector;

import de.hattrickorganizer.model.HOMiniModel;


/**
 * Plugin regarding transfer information.
 *
 * @author <a href=mailto:nethyperon@users.sourceforge.net>Boy van der Werf</a>
 */
public class HOSetup {
	//~ Static fields/initializers -----------------------------------------------------------------
	protected HOMiniModel MODEL;

	public HOSetup() {
		//		Standardparameter aus der DB holen
		de.hattrickorganizer.database.DBZugriff.instance().loadUserParameter();

		//Init!
		de.hattrickorganizer.model.HOVerwaltung.instance(); //.setArgs ( args );

		//Check -> Sprachdatei in Ordnung?
		//checkSprachFile( UserParameter.instance ().sprachDatei );
		ClassLoader loader = new de.hattrickorganizer.gui.templates.ImagePanel().getClass().getClassLoader();
		//java.net.URL    url     =   loader.getResource ("sprache/"+UserParameter.instance ().sprachDatei+".properties");
		de.hattrickorganizer.model.HOVerwaltung.instance().setResource(UserParameter.instance().sprachDatei, loader);
		de.hattrickorganizer.model.HOVerwaltung.instance().loadLatestHoModel();
		//Die Währung auf die aus dem HRF setzen
		float faktorgeld = (float) de.hattrickorganizer.model.HOVerwaltung.instance().getModel().getXtraDaten().getCurrencyRate();
		if (faktorgeld > -1) {
			gui.UserParameter.instance().faktorGeld = faktorgeld;
		}

		Vector trainingsVector = de.hattrickorganizer.database.DBZugriff.instance().getTrainingsVector();
		Vector trainingStatus = de.hattrickorganizer.logik.TrainingsManager.instance().calculateTrainings( trainingsVector );

		MODEL = HOMiniModel.instance();
	}
	
}
