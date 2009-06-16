// %1129130641:hoplugins.teamplanner.ui.tabs.players%
package hoplugins.teamplanner.ui.tabs.players;

import hoplugins.Commons;

import hoplugins.teamplanner.ui.controller.input.CallableListener;
import hoplugins.teamplanner.ui.model.OperationData;

import plugins.ISpieler;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * Missing Class Documentation
 *
 * @author Draghetto
 */
public class TraineesDialog extends JDialog {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -4688213301998533458L;
	private CallableListener caller;
    private JPanel p = new JPanel();
    private List<OperationData> players;
    private int row;
    private int selected;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new StadiumSizeDialog object.
     *
     * @param list Missing Constructuor Parameter Documentation
     */
    public TraineesDialog(CallableListener list) {
        super(Commons.getModel().getGUI().getOwner4Dialog(), true);
        this.caller = list;
        jbInit();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public ISpieler getPlayer() {
        OperationData data = (OperationData) players.get(selected);
        PlayersInner inner = (PlayersInner) data.getInner();
        return inner.getPlayer();
    }

    /**
     * DOCUMENT ME!
     *
     * @param players
     */
    public void reload(List<OperationData> players) {
        this.players = players;
        p.removeAll();
        p.setLayout(new GridLayout(players.size(), 2));
        row = 0;

        for (Iterator<OperationData> iter = players.iterator(); iter.hasNext();) {
            OperationData data = iter.next();
            JLabel label = new JLabel();
            label.setText(data.getInner().getDescription());
            p.add(label);

            JButton actionButton = new JButton("Sell");
            p.add(actionButton);
            actionButton.addActionListener(new ActionListener() {
                    private int line = row;

                    public void actionPerformed(ActionEvent arg0) {
                        selected = line;
                        caller.doTriggerEvent(CallableListener.SELECTION);
                    }
                });
            row++;
        }

        pack();
    }

    /**
     * Missing Method Documentation
     */
    private void jbInit() {
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        c.add(p, BorderLayout.NORTH);
        pack();
    }
}
