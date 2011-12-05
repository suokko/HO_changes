// %3578830361:hoplugins.teamAnalyzer.ui.controller%
package hoplugins.teamAnalyzer.ui.controller;

import hoplugins.teamAnalyzer.dao.DividerDAO;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


/**
 * Properry listener for the divider of the JSPlitPanes
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class DividerListener implements PropertyChangeListener {
    //~ Instance fields ----------------------------------------------------------------------------

    private String key = "";

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new DividerListener object.
     *
     * @param _key the key to be used for storing
     */
    public DividerListener(String _key) {
        key = _key;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Action performed event listener, store the key in the Database
     *
     * @param e the event
     */
    public void propertyChange(PropertyChangeEvent e) {
        Number value = (Number) e.getNewValue();
        int newDivLoc = value.intValue();

        DividerDAO.setDividerPosition(key, newDivLoc);
    }
}
