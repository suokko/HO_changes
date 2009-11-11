// %1117664848296:hoplugins.conv%
/*
 * Created on 16.05.2004
 *
 */
package hoplugins.conv;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


/**
 * DOCUMENT ME!
 *
 * @author Thorsten Dietz
 */
public final class CListener implements ActionListener {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param e TODO Missing Method Parameter Documentation
     */
    public void actionPerformed(ActionEvent e) {
        String comand = e.getActionCommand();

        if (comand.equals(RSC.BUDDY_TO_HRF)) {
            JOptionPane.showMessageDialog(null, RSC.getProperty("select_source_file"));

            CFileChooser chooser = CFileChooser.getInstance();

            int returnOpen = chooser.showOpenDialog(RSC.MINIMODEL.getGUI().getOwner4Dialog());

            if (returnOpen == JFileChooser.APPROVE_OPTION) {
                byte type = ((CFilter) chooser.getFileFilter()).getType();
                HrfMaker.convert(type, chooser.getSelectedFiles());
            } // if ok 
        }

        if (comand.equals(RSC.DB_TO_HRF)) {
            DBToHrf conv = new DBToHrf();
            conv.start(null, conv.getDestinationFolder(RSC.MINIMODEL.getGUI().getOwner4Dialog()));
        }

        if (comand.equals(RSC.PROP_PLAYERS)) {
            TxtExpDialog dialog = new TxtExpDialog(RSC.MINIMODEL.getGUI().getOwner4Dialog());
            dialog.setVisible(true);
        }
    }
}
