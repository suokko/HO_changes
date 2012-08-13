// %2953851721:hoplugins.trainingExperience.ui%
/*
 * Created on 12.10.2005
 */
package ho.module.training.ui;

import ho.core.model.HOVerwaltung;
import ho.core.util.HOLogger;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;



/**
 * DOCUMENT ME!
 *
 * @author <a href="mailto:kenmooda@users.sourceforge.net">Tommi Rautava</a>
 */
public class PlayerSelectionListener implements ListSelectionListener {
    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    JTable table;

    /** TODO Missing Parameter Documentation */
    int playerIdColumn = 0;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param table Table
     * @param col player ID column
     */
    public PlayerSelectionListener(JTable table, int col) {
        super();
        this.table = table;
        playerIdColumn = col;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /* (non-Javadoc)
     * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
     */
    public void valueChanged(ListSelectionEvent e) {
        try {
            if (e.getValueIsAdjusting()) {
                return;
            }

            int index = table.getSelectedRow();

            if (index >= 0) {
                String playerId = (String) table.getValueAt(index, playerIdColumn);
                ho.module.training.TrainingPanel.selectPlayer(HOVerwaltung.instance().getModel().getSpieler(Integer.parseInt(playerId)));
            }
        } catch (Exception e2) {
           HOLogger.instance().log(this.getClass(), e2);
        }
    }
}
