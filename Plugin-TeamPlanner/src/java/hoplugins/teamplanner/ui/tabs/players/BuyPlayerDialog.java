// %1165085624:hoplugins.teamplanner.ui.tabs.players%
package hoplugins.teamplanner.ui.tabs.players;

import hoplugins.Commons;

import hoplugins.teamplanner.manager.TeamPlayerManager;
import hoplugins.teamplanner.ui.controller.input.CallableListener;
import hoplugins.teamplanner.vo.HTWeek;

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
public class BuyPlayerDialog extends JDialog {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -7735787983138954335L;
	// ----------------------------------------------------------------------------
    private CallableListener caller;
    private HTWeek week;
    private JPanel panel = new JPanel();
    private List players = null;
    private int row = -1;
    private int selected;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new StadiumSizeDialog object.
     *
     * @param listener Missing Constructuor Parameter Documentation
     */
    public BuyPlayerDialog(CallableListener listener) {
        super(Commons.getModel().getGUI().getOwner4Dialog(), true);
        this.caller = listener;
        jbInit();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public ISpieler getPlayer() {
        if (selected < players.size()) {
            return (ISpieler) players.get(selected);
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public HTWeek getWeek() {
        return this.week;
    }

    /**
     * DOCUMENT ME!
     *
     * @param week
     */
    public void reload(HTWeek week) {
        this.week = week;
        this.players = TeamPlayerManager.instance().getBoughtPlayersForWeek(week);
        panel.removeAll();
        panel.setLayout(new GridLayout(players.size() + 1, 2));
        row = 0;

        for (Iterator iter = players.iterator(); iter.hasNext();) {
            ISpieler tp = (ISpieler) iter.next();
            JLabel label = new JLabel();
            label.setText(tp.getName());
            panel.add(label);

            JButton actionButton = new JButton("Cancel");
            panel.add(actionButton);
            actionButton.addActionListener(new ActionListener() {
                    int id = row;

                    public void actionPerformed(ActionEvent arg0) {
                        setVisible(false);
                        selected = id;
                        caller.doTriggerEvent(CallableListener.CANCEL);
                    }
                });
            row++;
        }

        JLabel label = new JLabel();
        label.setText("Buy new Player");
        panel.add(label);

        JButton actionButton = new JButton("Buy");
        panel.add(actionButton);
        actionButton.addActionListener(new ActionListener() {
                int id = row;

                public void actionPerformed(ActionEvent arg0) {
                    setVisible(false);
                    selected = id;
                    caller.doTriggerEvent(CallableListener.SELECTION);
                }
            });
        pack();
    }

    // ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     */
    private void jbInit() {
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        c.add(panel, BorderLayout.NORTH);
        pack();
    }
}
