// %2368889305:hoplugins.teamplanner.ui.tabs.players%
package hoplugins.teamplanner.ui.tabs.players;

import hoplugins.Commons;

import hoplugins.teamplanner.manager.TeamPlayerManager;
import hoplugins.teamplanner.ui.controller.input.CallableListener;
import hoplugins.teamplanner.vo.HTWeek;
import hoplugins.teamplanner.vo.PlayerData;

import plugins.ISpieler;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;


/**
 * Missing Class Documentation
 *
 * @author Draghetto
 */
public class PriceDialog extends JDialog {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -7927736864117483677L;
	// ----------------------------------------------------------------------------
    private CallableListener caller;
    private HTWeek week;
    private ISpieler tp;
    private JComboBox type = new JComboBox();
    private JPanel panel = null;
    private PlayerDetail detail = new PlayerDetail();

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new StadiumSizeDialog object.
     *
     * @param list Missing Constructuor Parameter Documentation
     */
    public PriceDialog(CallableListener list) {
        super(Commons.getModel().getGUI().getOwner4Dialog(), true);
        this.caller = list;
        jbInit();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     */
    public void setFixedSell() {
        type.setSelectedIndex(0);
        type.setEnabled(false);
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public ISpieler getPlayer() {
        // Real Player
        if (tp != null) {
            return tp;
        }

        PlayerData data = getPlayerData();
        ISpieler sp = Commons.getModel().createPlayer(data);
        return sp;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public double getPrice() {
        return detail.getPrice();
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public HTWeek getweek() {
        return this.week;
    }

    /**
     * Missing Method Documentation
     *
     * @param player Missing Method Parameter Documentation
     * @param aweek Missing Method Parameter Documentation
     */
    public void reload(ISpieler player, HTWeek aweek) {
        type.setEnabled(true);
        type.setSelectedIndex(0);
        this.week = aweek;
        this.tp = player;

        detail.setPlayer(tp);
        panel.removeAll();
        panel.add(detail, BorderLayout.NORTH);

        JPanel bottomPanel = Commons.getModel().getGUI().createImagePanel();
        bottomPanel.setLayout(new BorderLayout());

        JButton actionButton = new JButton("Ok");
        bottomPanel.add(type, BorderLayout.WEST);
        bottomPanel.add(actionButton, BorderLayout.EAST);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        actionButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    caller.doTriggerEvent(CallableListener.CONFIRMATION);
                }
            });
        pack();
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    private PlayerData getPlayerData() {
        PlayerData data = detail.getPlayerData();
        data.setPlayerId(TeamPlayerManager.instance().getNewPlayerId());
        data.setPlayerName("Player " + (-data.getPlayerId()));
        return data;
    }

    // ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     */
    private void jbInit() {
        type.addItem("Fixed Date");
        type.addItem("Linked Date");
        panel = Commons.getModel().getGUI().createImagePanel();
        panel.setLayout(new BorderLayout());

        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        c.add(panel, BorderLayout.CENTER);
        pack();
    }
}
