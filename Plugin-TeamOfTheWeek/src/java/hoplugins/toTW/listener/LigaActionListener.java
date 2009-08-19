// %680165750:hoplugins.toTW.listener%
package hoplugins.toTW.listener;

import hoplugins.TotW;

import hoplugins.toTW.vo.LigaItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class LigaActionListener implements ActionListener {
    //~ Instance fields ----------------------------------------------------------------------------

    private JSpinner weekSpinner;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new LigaActionListener object.
     *
     * @param _weekSpinner TODO Missing Constructuor Parameter Documentation
     */
    public LigaActionListener(JSpinner _weekSpinner) {
        super();
        weekSpinner = _weekSpinner;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param arg0 TODO Missing Method Parameter Documentation
     */
    public void actionPerformed(ActionEvent arg0) {
        SpinnerNumberModel cSNM = null;
        JComboBox league = (JComboBox) arg0.getSource();
        LigaItem item = (LigaItem) league.getSelectedItem();
        TotW.setSeason(item.getSeason());
        TotW.setLiga(item.getLigaId());
        TotW.setWeek(1);

        if (TotW.getModel().getBasics().getSeason() == TotW.getSeason()) {
            cSNM = new SpinnerNumberModel(1, 1, TotW.getMaxWeek(), 1);
        } else {
            cSNM = new SpinnerNumberModel(1, 1, 14, 1);
        }

        weekSpinner.setModel(cSNM);
        TotW.forceRefresh(true);
    }
}
