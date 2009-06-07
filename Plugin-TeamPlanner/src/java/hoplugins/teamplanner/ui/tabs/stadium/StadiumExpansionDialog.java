// %1986772807:hoplugins.teamplanner.ui.tabs.stadium%
package hoplugins.teamplanner.ui.tabs.stadium;

import hoplugins.Commons;

import hoplugins.teamplanner.ui.tabs.stadium.listener.StadiumExpansionListener;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * Missing Class Documentation
 *
 * @author Draghetto
 */
public class StadiumExpansionDialog extends JDialog {
    //~ Instance fields ----------------------------------------------------------------------------

    private StadiumExpansionInner original;
    private StadiumExpansionListener caller;
    private JTextField[] newValues = new JTextField[4];

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new StadiumSizeDialog object.
     *
     * @param list Missing Constructuor Parameter Documentation
     * @param inner Missing Constructuor Parameter Documentation
     */
    public StadiumExpansionDialog(StadiumExpansionListener list, StadiumExpansionInner inner) {
        super(Commons.getModel().getGUI().getOwner4Dialog(), true);
        this.caller = list;
        this.original = inner;
        jbInit(inner);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     *
     * @param inner Missing Method Parameter Documentation
     */
    private void jbInit(StadiumExpansionInner inner) {
        StadiumExpansionInner total = inner.getTotal();
        Container c = getContentPane();
        c.setLayout(new BorderLayout());

        JPanel p = new JPanel();
        p.setLayout(new GridLayout(5, 2));

        JLabel l = new JLabel((total.getTerraces() - inner.getTerraces()) + "", JLabel.LEFT);
        l.setEnabled(false);
        p.add(l);

        newValues[0] = new JTextField(inner.getTerraces() + "");
        p.add(newValues[0]);

        JLabel l2 = new JLabel((total.getSeats() - inner.getSeats()) + "", JLabel.LEFT);
        l2.setEnabled(false);
        p.add(l2);

        newValues[1] = new JTextField(inner.getSeats() + "");
        p.add(newValues[1]);

        JLabel l3 = new JLabel((total.getRoofs() - inner.getRoofs()) + "", JLabel.LEFT);
        l3.setEnabled(false);
        p.add(l3);

        newValues[2] = new JTextField(inner.getRoofs() + "");
        p.add(newValues[2]);

        JLabel l4 = new JLabel((total.getVips() - inner.getVips()) + "", JLabel.LEFT);
        l4.setEnabled(false);
        p.add(l4);

        newValues[3] = new JTextField(inner.getVips() + "");
        p.add(newValues[3]);

        JButton b1 = new JButton("OK");
        b1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    StadiumExpansionInner ss = new StadiumExpansionInner();
                    ss.setTerraces(getIntValue(0) - original.getTerraces());
                    ss.setSeats(getIntValue(1) - original.getSeats());
                    ss.setRoofs(getIntValue(2) - original.getRoofs());
                    ss.setVips(getIntValue(3) - original.getVips());
                    caller.setStadiumSize(ss);
                    setVisible(false);
                }

                private int getIntValue(int i) {
                    try {
                        return Integer.parseInt(newValues[i].getText());
                    } catch (NumberFormatException e) {
                    }

                    return 0;
                }
            });
        p.add(b1);

        JButton b2 = new JButton("Close");
        b2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    setVisible(false);
                }
            });
        p.add(b2);

        c.add(p, BorderLayout.NORTH);
        pack();
    }
}
