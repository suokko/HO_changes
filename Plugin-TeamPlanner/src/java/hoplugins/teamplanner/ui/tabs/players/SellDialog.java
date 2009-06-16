// %444388664:hoplugins.teamplanner.ui.tabs.players%
package hoplugins.teamplanner.ui.tabs.players;

import hoplugins.Commons;

import hoplugins.teamplanner.manager.TeamPlayerManager;
import hoplugins.teamplanner.ui.controller.input.CallableListener;
import hoplugins.teamplanner.ui.tabs.players.listener.SellPlayerListener;
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
public class SellDialog extends JDialog {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 5742156913550903426L;
	// ----------------------------------------------------------------------------
    private CallableListener caller;
    private HTWeek week;
    private JPanel panel = new JPanel();
    private List<ISpieler> players = null;
    private List<ISpieler> soldPlayers = null;
    private int row = -1;
    private int selected;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new StadiumSizeDialog object.
     *
     * @param listener Missing Constructuor Parameter Documentation
     */
    public SellDialog(SellPlayerListener listener) {
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
        if (selected >= players.size()) {
            return (ISpieler) soldPlayers.get(selected - players.size());
        }

        return (ISpieler) players.get(selected);
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
        this.players = TeamPlayerManager.instance().getPlayersAvailableAtWeek(week);
        this.soldPlayers = TeamPlayerManager.instance().getSoldPlayersForWeek(week);
        panel.removeAll();
        panel.setLayout(new GridLayout(players.size() + soldPlayers.size(), 2));
        row = 0;

        for (Iterator<ISpieler> iter = players.iterator(); iter.hasNext();) {
            ISpieler tp = iter.next();
            JLabel label = new JLabel();
            label.setText(tp.getName());
            panel.add(label);

            JButton actionButton = new JButton("Sell");
            panel.add(actionButton);
            actionButton.addActionListener(new ActionListener() {
                    int id = row;

                    public void actionPerformed(ActionEvent arg0) {
                        setVisible(false);
                        selected = id;
                        caller.doTriggerEvent(CallableListener.SELECTION);
                    }
                });
            row++;
        }

        for (Iterator<ISpieler> iter = soldPlayers.iterator(); iter.hasNext();) {
            ISpieler tp = iter.next();
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
