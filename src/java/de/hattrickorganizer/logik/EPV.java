// %1598857424:de.hattrickorganizer.logik%
package de.hattrickorganizer.logik;

import plugins.IEPVData;
import plugins.IPlayerData;
import plugins.ISpieler;
import de.hattrickorganizer.model.EPVData;
import de.hattrickorganizer.model.HOVerwaltung;


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
        double price = EPVCalculator.getInstance().calculate(data, week);
		double curr_rate = HOVerwaltung.instance().getModel().getXtraDaten().getCurrencyRate();			
		price = (price * 10d) / curr_rate;	
		double minvalue= 1000d / curr_rate;
		int minaccu = (int) Math.pow( 10, (int)(Math.log(minvalue)/Math.log(10d)+0.5) );         
		int accuracy = (int) Math.max(minaccu , Math.pow(10, (int)(Math.log(price) / Math.log(10d)) - 2));     
		price =  ( (int) (price / accuracy + 0.5d ) ) * accuracy;
        return price;
    }
}
