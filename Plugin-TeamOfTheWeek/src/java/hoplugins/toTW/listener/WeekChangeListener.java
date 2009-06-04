// %535575056:hoplugins.potw%
package hoplugins.toTW.listener;

import hoplugins.TotW;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class WeekChangeListener implements ChangeListener {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new WeekChangeListener object.
     */
    public WeekChangeListener() {
        super();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param e TODO Missing Method Parameter Documentation
     */
    public void stateChanged(ChangeEvent e) {
        JSpinner week = (JSpinner) e.getSource();
        TotW.setWeek(((Integer) week.getValue()).intValue());
        TotW.forceRefresh(false);
    }
}
