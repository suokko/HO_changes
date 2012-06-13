// %3394711869:de.hattrickorganizer.gui.keepertool%
package ho.tool.keepertool;


import ho.core.constants.player.PlayerSkill;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.model.HOVerwaltung;
import ho.core.model.player.Spieler;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;


/**
 * Panel where results are shown
 *
 * @author draghetto
 */
class ResultPanel extends JPanel {
	
	private static final long serialVersionUID = 272383166131665396L;
	
    //~ Instance fields ----------------------------------------------------------------------------

	private DecimalFormat df = new DecimalFormat("#.00");
    private JButton set = new JButton();
    private JDialog parent;
    private JTextArea result = new JTextArea();
    private double average;
    private int id;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new ResultPanel object.
     *
     * @param dialog the main KeeperTool dialog
     */
    ResultPanel(KeeperToolDialog dialog) {
        parent = dialog;
        init();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Reset the panel to default data
     */
    public final void reset() {
        id = 0;
        average = 0;
        set.setEnabled(false);
        result.setText("Skill");
        set.setText(ho.core.model.HOVerwaltung.instance().getLanguageString("Disabled"));
    }

    /**
     * Methods that calculates the player's keeper subskill. show them and if a roster player
     * enable the button to update it
     *
     * @param form keeper form
     * @param tsi keeper tsi
     * @param playerId keeper id, 0 if not real
     * @param name player name if scout or roster, empty if manual
     */
    protected final void setPlayer(int form, int tsi, int playerId, String name) {
        if (tsi == 0) {
            reset();
            return;
        }

        final KeeperTool kt = new KeeperTool(form, tsi);
        id = playerId;
        result.setText("Skill: " + df.format(kt.getMin()) + "-" + df.format(kt.getMax()));
        average = kt.getAvg();

        if (playerId > 0) {
            set.setText(ho.core.model.HOVerwaltung.instance().getLanguageString("OffsetTitle") + " "
                        + name);
            set.setEnabled(true);
        } else {
            set.setText(ho.core.model.HOVerwaltung.instance().getLanguageString("Disabled"));
            set.setEnabled(false);
        }
    }

    /**
     * Initialize the GUI components
     */
    private void init() {
        setOpaque(false);
        setLayout(new BorderLayout());

        final JPanel panel = new ImagePanel();
        add(panel, BorderLayout.CENTER);
        result.setOpaque(false);
        panel.setLayout(new GridLayout(2, 1));
        panel.add(result, BorderLayout.NORTH);
        panel.add(set, BorderLayout.SOUTH);

        set.addActionListener(new ActionListener() {
                @Override
				public void actionPerformed(ActionEvent arg0) {
                    final Spieler sp = HOVerwaltung.instance().getModel().getSpieler(id);
                    double decimals = average - sp.getTorwart()
                                      - sp.getSubskill4Pos(PlayerSkill.KEEPER);

                    if (decimals > 1) {
                        decimals = 0.99;
                    }

                    if (decimals < 0) {
                        decimals = 0;
                    }

                    sp.setSubskill4Pos(PlayerSkill.KEEPER, (float)decimals);
                    ho.core.db.DBManager.instance().saveSpieler(ho.core.model.HOVerwaltung.instance()
                                                                                                                          .getModel()
                                                                                                                          .getID(),
                                                                                   ho.core.model.HOVerwaltung.instance()
                                                                                                                          .getModel()
                                                                                                                          .getAllSpieler(),
                                                                                   ho.core.model.HOVerwaltung.instance()
                                                                                                                          .getModel()
                                                                                                                          .getBasics()
                                                                                                                          .getDatum());
                    ho.core.gui.RefreshManager.instance().doReInit();
                    parent.setVisible(false);
                    parent.dispose();
                }
            });
        reset();
    }
}
