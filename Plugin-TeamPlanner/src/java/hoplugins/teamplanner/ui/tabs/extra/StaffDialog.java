// %1410642405:hoplugins.teamplanner.ui.tabs.extra%
package hoplugins.teamplanner.ui.tabs.extra;

import hoplugins.Commons;

import hoplugins.teamplanner.ui.tabs.extra.listener.StaffListener;

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
import javax.swing.SwingConstants;


/**
 * Missing Class Documentation
 *
 * @author Draghetto
 */
public class StaffDialog extends JDialog {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 4273550051406968958L;
	private StaffInner original;
    private StaffListener caller;
    private JTextField[] newValues = new JTextField[6];

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new StadiumSizeDialog object.
     *
     * @param list Missing Constructuor Parameter Documentation
     * @param inner Missing Constructuor Parameter Documentation
     */
    public StaffDialog(StaffListener list, StaffInner inner) {
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
    private void jbInit(StaffInner inner) {
        StaffInner total = inner.getTotal();
        Container c = getContentPane();
        c.setLayout(new BorderLayout());

        JPanel p = new JPanel();
        p.setLayout(new GridLayout(7, 3));

        p.add(new JLabel("Assistant Coach", SwingConstants.LEFT));
        p.add(new JLabel((total.getAssistantCoaches() - inner.getAssistantCoaches()) + "",
                         SwingConstants.LEFT));
        newValues[0] = new JTextField(inner.getAssistantCoaches() + "");
        p.add(newValues[0]);

        p.add(new JLabel("Assistant Keeper", SwingConstants.LEFT));
        p.add(new JLabel((total.getAssistantKeeper() - inner.getAssistantKeeper()) + "", SwingConstants.LEFT));
        newValues[1] = new JTextField(inner.getAssistantKeeper() + "");
        p.add(newValues[1]);

        p.add(new JLabel("Phisio", SwingConstants.LEFT));
        p.add(new JLabel((total.getPhisio() - inner.getPhisio()) + "", SwingConstants.LEFT));
        newValues[2] = new JTextField(inner.getPhisio() + "");
        p.add(newValues[2]);

        p.add(new JLabel("Psico", SwingConstants.LEFT));
        p.add(new JLabel((total.getPsico() - inner.getPsico()) + "", SwingConstants.LEFT));
        newValues[3] = new JTextField(inner.getPsico() + "");
        p.add(newValues[3]);

        p.add(new JLabel("Doctor", SwingConstants.LEFT));
        p.add(new JLabel((total.getDoctor() - inner.getDoctor()) + "", SwingConstants.LEFT));
        newValues[4] = new JTextField(inner.getDoctor() + "");
        p.add(newValues[4]);

        p.add(new JLabel("Spokesman", SwingConstants.LEFT));
        p.add(new JLabel((total.getSpokesman() - inner.getSpokesman()) + "", SwingConstants.LEFT));
        newValues[5] = new JTextField(inner.getSpokesman() + "");
        p.add(newValues[5]);

        p.add(new JLabel());

        JButton b1 = new JButton("OK");
        b1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    StaffInner ss = new StaffInner();
                    ss.setAssistantCoaches(getIntValue(0) - original.getAssistantCoaches());
                    ss.setAssistantKeeper(getIntValue(1) - original.getAssistantKeeper());
                    ss.setPhisio(getIntValue(2) - original.getPhisio());
                    ss.setPsico(getIntValue(3) - original.getPsico());
                    ss.setDoctor(getIntValue(4) - original.getDoctor());
                    ss.setSpokesman(getIntValue(5) - original.getSpokesman());
                    caller.setStaff(ss);
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
