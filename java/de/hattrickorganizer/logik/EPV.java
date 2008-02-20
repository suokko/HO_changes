// %1598857424:de.hattrickorganizer.logik%
package de.hattrickorganizer.logik;

import plugins.IEPVData;
import plugins.IPlayerData;
import plugins.ISpieler;
import de.hattrickorganizer.model.EPVData;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.tools.HOLogger;
import prediction.EPVCalculator;

/**
 * Main EPV manager class
 *
 * @author draghetto
 */
public class EPV implements plugins.IEPV {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new EPV object.
     */
    public EPV() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param spieler TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final IEPVData getEPVData(ISpieler spieler) {
        final EPVData data = new EPVData(spieler);
        return data;
    }

	public final IEPVData getEPVData(IPlayerData player) {
		final EPVData data = new EPVData(player);
		return data;
	}

    ////////////////////////////////////////////////////////////////////////////////    
    //Accessor
    ////////////////////////////////////////////////////////////////////////////////    
    public final double getPrice(IEPVData data) {
        return getPrice(data, HOVerwaltung.instance().getModel().getBasics().getSpieltag());
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param data TODO Missing Method Parameter Documentation
     * @param week TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final double getPrice(IEPVData data, int week) {
    	try {
    		return EPVCalculator.getInstance().getPrice( data, week, HOVerwaltung.instance().getModel().getXtraDaten().getCurrencyRate() );
    	} catch (Exception e) {
    		HOLogger.instance().log(EPV.class, e);
    		return 0d;
    	}
    }
}
