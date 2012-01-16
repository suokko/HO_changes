// %1126721330260:hoplugins.transfers.ui.controller%
package ho.modul.transfer;



import gui.UserParameter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


/**
 * Dividend Listener that store in the Database the position of the varous SplitPane
 *
 * @author <a href=mailto:nethyperon@users.sourceforge.net>Boy van der Werf</a>
 */
public class DividerListener implements PropertyChangeListener {
    //~ Instance fields ----------------------------------------------------------------------------

    /** Key to be used for access the DB */
    private String key = ""; //$NON-NLS-1$

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new DividerListener object.
     *
     * @param key Key value
     */
    public DividerListener(String key) {
        this.key = key;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Method invoked when the splitpane divisor is moved Store the new position value in the DB
     *
     * @param e Event
     */
    public final void propertyChange(PropertyChangeEvent e) {
        final Number value = (Number) e.getNewValue();
        if(key.equals("HistoryTabDivider"))
        	UserParameter.instance().transferHistoryPane_splitPane = value.intValue();
        else
        	UserParameter.instance().transferTypePane_splitPane = value.intValue();
    }
}
