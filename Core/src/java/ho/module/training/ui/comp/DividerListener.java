// %1126721451182:hoplugins.trainingExperience.ui.component%
package ho.module.training.ui.comp;



import ho.module.training.DividerDAO;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


/**
 * Dividend Listener that store in the Database the position of the varous SplitPane
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class DividerListener implements PropertyChangeListener {
    //~ Instance fields ----------------------------------------------------------------------------

    /** Key to be used for access the DB */
    private String key = ""; //$NON-NLS-1$

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new DividerListener object.
     *
     * @param _key
     */
    public DividerListener(String _key) {
        key = _key;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Method invoked when the splitpane divisor is moved Store the new position value in the DB
     *
     * @param e
     */
    public void propertyChange(PropertyChangeEvent e) {
        Number value = (Number) e.getNewValue();
        int newDivLoc = value.intValue();

        DividerDAO.setDividerPosition(key, newDivLoc);
    }
}
