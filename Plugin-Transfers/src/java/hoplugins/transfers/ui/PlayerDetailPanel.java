// %1126721330604:hoplugins.transfers.ui%
package hoplugins.transfers.ui;

import hoplugins.Commons;
import hoplugins.commons.ui.DefaultTableSorter;
import hoplugins.commons.ui.info.clearthought.layout.TableLayout;
import hoplugins.commons.utils.PluginProperty;
import hoplugins.transfers.dao.TransfersDAO;
import hoplugins.transfers.ui.model.PlayerTransferTableModel;
import hoplugins.transfers.utils.PlayerRetriever;
import hoplugins.transfers.vo.PlayerTransfer;

import plugins.IHelper;
import plugins.ISpieler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableModel;


/**
 * Panel for showing detailed information on a player.
 *
 * @author <a href=mailto:nethyperon@users.sourceforge.net>Boy van der Werf</a>
 */
public class PlayerDetailPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = -6855218725568752692L;
	//~ Instance fields ----------------------------------------------------------------------------
    private static final String SKILL_KEEPER = Commons.getModel().getLanguageString("skill.keeper");
    private static final String SKILL_PLAYMAKING = Commons.getModel().getLanguageString("skill.playmaking");
    private static final String SKILL_PASSING = Commons.getModel().getLanguageString("skill.passing");
    private static final String SKILL_WING = Commons.getModel().getLanguageString("skill.winger");
    private static final String SKILL_DEFENSE = Commons.getModel().getLanguageString("skill.defending");
    private static final String SKILL_SCORING = Commons.getModel().getLanguageString("skill.scoring");
    private static final String SKILL_SETPIECES = Commons.getModel().getLanguageString("skill.set_pieces");
    private static final String SKILL_STAMINA = Commons.getModel().getLanguageString("skill.stamina");
    private static final String SKILL_EXPERIENCE = Commons.getModel().getLanguageString("skill.experience");

    private ISpieler player;
    private JButton updBtn = new JButton();
    private JLabel age = new JLabel("", SwingConstants.LEFT); //$NON-NLS-1$
    private JLabel currTSI = new JLabel(PluginProperty.getString("PlayerDetail.NotAvail"),
                                        SwingConstants.LEFT); //$NON-NLS-1$
    private JLabel income = new JLabel("", SwingConstants.LEFT); //$NON-NLS-1$
    private JLabel name = new JLabel("", SwingConstants.LEFT); //$NON-NLS-1$
    
    private JLabel skill_defense = new JLabel("", SwingConstants.LEFT);
    private JLabel skill_experience = new JLabel("", SwingConstants.LEFT);
    private JLabel skill_keeper = new JLabel("", SwingConstants.LEFT);
    private JLabel skill_passing = new JLabel("", SwingConstants.LEFT);
    private JLabel skill_playmaking = new JLabel("", SwingConstants.LEFT);
    private JLabel skill_scoring = new JLabel("", SwingConstants.LEFT);
    private JLabel skill_setpieces = new JLabel("", SwingConstants.LEFT);
    private JLabel skill_stamina = new JLabel("", SwingConstants.LEFT);
    private JLabel skill_wing = new JLabel("", SwingConstants.LEFT);
    
    private JLabel arrow_defense = new JLabel((Icon) null, SwingConstants.CENTER);
    private JLabel arrow_experience = new JLabel((Icon) null, SwingConstants.CENTER);
    private JLabel arrow_keeper = new JLabel((Icon) null, SwingConstants.CENTER);
    private JLabel arrow_passing = new JLabel((Icon) null, SwingConstants.CENTER);
    private JLabel arrow_playmaking = new JLabel((Icon) null, SwingConstants.CENTER);
    private JLabel arrow_scoring = new JLabel((Icon) null, SwingConstants.CENTER);
    private JLabel arrow_setpieces = new JLabel((Icon) null, SwingConstants.CENTER);
    private JLabel arrow_stamina = new JLabel((Icon) null, SwingConstants.CENTER);
    private JLabel arrow_wing = new JLabel((Icon) null, SwingConstants.CENTER);
    private JTable playerTable;
    private String playerName;
    private int playerId;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a PlayerDetailPanel.
     */
    public PlayerDetailPanel() {
        super(new BorderLayout());

        final TableModel model = new PlayerTransferTableModel(new ArrayList());
        final TeamTransferSorter sorter = new TeamTransferSorter(model);
        playerTable = new JTable(sorter);
        sorter.setTableHeader(playerTable.getTableHeader());

        final JScrollPane playerPane = new JScrollPane(playerTable);
        playerPane.setOpaque(false);
        add(playerPane, BorderLayout.CENTER);

        final double[][] sizes = {
                               {
                                   10, 50, 150, 20, 75, 50, TableLayout.FILL, 30, 100, 30, 100, 30,
                                   100, 50, 100, 10
                               },
                               {20, 20, 20}
                           };

        final JPanel detailPanel = Commons.getModel().getGUI().createImagePanel();
        detailPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY),
                                                               PluginProperty.getString("PlayerDetail"))); //$NON-NLS-1$
        detailPanel.setOpaque(false);

        final TableLayout layout = new TableLayout(sizes);
        detailPanel.setLayout(layout);

        detailPanel.add(new JLabel(Commons.getModel().getLanguageString("Name"),
                                   SwingConstants.LEFT), "1, 0"); //$NON-NLS-1$ //$NON-NLS-2$
        detailPanel.add(name, "2, 0"); //$NON-NLS-1$
        detailPanel.add(new JLabel(Commons.getModel().getLanguageString("Alter"),
                                   SwingConstants.LEFT), "1, 1"); //$NON-NLS-1$ //$NON-NLS-2$
        detailPanel.add(age, "2, 1"); //$NON-NLS-1$
        detailPanel.add(new JLabel(PluginProperty.getString("Income"), SwingConstants.LEFT), "1, 2"); //$NON-NLS-1$ //$NON-NLS-2$
        detailPanel.add(income, "2, 2"); //$NON-NLS-1$

        detailPanel.add(new JLabel(PluginProperty.getString("PlayerDetail.CurrentTSI")), "4, 0");
        detailPanel.add(currTSI, "5, 0");

        detailPanel.add(arrow_scoring, "7, 0"); //$NON-NLS-1$
        detailPanel.add(skill_scoring, "8, 0"); //$NON-NLS-1$
        detailPanel.add(arrow_playmaking, "7, 1"); //$NON-NLS-1$
        detailPanel.add(skill_playmaking, "8, 1"); //$NON-NLS-1$
        detailPanel.add(arrow_defense, "7, 2"); //$NON-NLS-1$
        detailPanel.add(skill_defense, "8, 2"); //$NON-NLS-1$

        detailPanel.add(arrow_wing, "9, 0"); //$NON-NLS-1$
        detailPanel.add(skill_wing, "10, 0"); //$NON-NLS-1$
        detailPanel.add(arrow_passing, "9, 1"); //$NON-NLS-1$
        detailPanel.add(skill_passing, "10, 1"); //$NON-NLS-1$
        detailPanel.add(arrow_stamina, "9, 2"); //$NON-NLS-1$
        detailPanel.add(skill_stamina, "10, 2"); //$NON-NLS-1$

        detailPanel.add(arrow_keeper, "11, 0"); //$NON-NLS-1$
        detailPanel.add(skill_keeper, "12, 0"); //$NON-NLS-1$
        detailPanel.add(arrow_setpieces, "11, 1"); //$NON-NLS-1$
        detailPanel.add(skill_setpieces, "12, 1"); //$NON-NLS-1$
        detailPanel.add(arrow_experience, "11, 2"); //$NON-NLS-1$
        detailPanel.add(skill_experience, "12, 2"); //$NON-NLS-1$

        updBtn.setEnabled(false);
        updBtn.setText(PluginProperty.getString("Update"));
        updBtn.setToolTipText(PluginProperty.getString("UpdTooltip"));
        updBtn.addActionListener(this);
        updBtn.setFocusable(false);

        detailPanel.add(updBtn, "14, 0, 14, 1");
        add(new JScrollPane(detailPanel), BorderLayout.NORTH);
        setOpaque(false);

        clearPanel();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Sets the player to display information for.
     *
     * @param playerid Player id
     * @param playerName Player Name
     */
    public final void setPlayer(int playerid, String playerName) {
        this.player = PlayerRetriever.getPlayer(playerid);

        if (this.player != null) {
            this.playerId = player.getSpielerID();
            this.playerName = player.getName();
        } else {
            this.playerId = playerid;
            this.playerName = playerName;
        }

        clearPanel();
        updatePanel();
    }

    /** {@inheritDoc} */
    public final void actionPerformed(ActionEvent e) {
        if (this.playerId > 0) {
            TransfersDAO.updatePlayerTransfers(this.playerId);
            updatePanel();
        }
    }

    /**
     * Clears all information on the panel.
     */
    public final void clearPanel() {
        updBtn.setEnabled(false);
        name.setText(""); //$NON-NLS-1$
        age.setText(""); //$NON-NLS-1$
        income.setText("");
        currTSI.setText(PluginProperty.getString("PlayerDetail.NotAvail"));
        
        skill_keeper.setText(SKILL_KEEPER);
        skill_playmaking.setText(SKILL_PLAYMAKING);
        skill_passing.setText(SKILL_PASSING);
        skill_wing.setText(SKILL_WING);
        skill_defense.setText(SKILL_DEFENSE);
        skill_scoring.setText(SKILL_SCORING);
        skill_setpieces.setText(SKILL_SETPIECES);
        skill_stamina.setText(SKILL_STAMINA);
        skill_experience.setText(SKILL_EXPERIENCE);
        
        arrow_keeper.setIcon(null);
        arrow_playmaking.setIcon(null);
        arrow_passing.setIcon(null);
        arrow_wing.setIcon(null);
        arrow_defense.setIcon(null);
        arrow_scoring.setIcon(null);
        arrow_setpieces.setIcon(null);
        arrow_stamina.setIcon(null);
        arrow_experience.setIcon(null);
        refreshPlayerTable(new Vector());
    }

    /**
     * Refreshes the table with player transfer information.
     *
     * @param values List of player transfers to display.
     */
    private void refreshPlayerTable(List values) {
        final DefaultTableSorter sorter = (DefaultTableSorter) playerTable.getModel();
        sorter.setTableModel(new PlayerTransferTableModel(values));
        playerTable.getColumnModel().getColumn(3).setPreferredWidth(150);
        playerTable.getColumnModel().getColumn(4).setCellRenderer(new IconCellRenderer());
        playerTable.getColumnModel().getColumn(4).setMaxWidth(20);
        playerTable.getColumnModel().getColumn(4).setPreferredWidth(150);
    }

    /**
     * Update the detail panel.
     */
    private void updatePanel() {
        if (playerId > 0) {
            updBtn.setEnabled(true);
            name.setText(this.playerName);

            if (player != null) {
                age.setText(Integer.toString(this.player.getAlter()));

                if (!player.isOld()) {
                    currTSI.setText(Integer.toString(this.player.getTSI()));
                }

                skill_keeper.setText(SKILL_KEEPER + " (" + player.getTorwart() + ")");
                skill_playmaking.setText(SKILL_PLAYMAKING + " (" + player.getSpielaufbau() + ")");
                skill_passing.setText(SKILL_PASSING + " (" + player.getPasspiel() + ")");
                skill_wing.setText(SKILL_WING + " (" + player.getFluegelspiel() + ")");
                skill_defense.setText(SKILL_DEFENSE + " (" + player.getVerteidigung() + ")");
                skill_scoring.setText(SKILL_SCORING + " (" + player.getTorschuss() + ")");
                skill_setpieces.setText(SKILL_SETPIECES + " (" + player.getStandards() + ")");
                skill_stamina.setText(SKILL_STAMINA + " (" + player.getKondition() + ")");
                skill_experience.setText(SKILL_EXPERIENCE + " (" + player.getErfahrung() + ")");

                final IHelper helper = Commons.getModel().getHelper();
                arrow_keeper.setIcon(helper.getImageIcon4Veraenderung(player.getAllLevelUp(ISpieler.SKILL_TORWART)
                                                                            .size()));
                arrow_playmaking.setIcon(helper.getImageIcon4Veraenderung(player.getAllLevelUp(ISpieler.SKILL_SPIELAUFBAU)
                                                                                .size()));
                arrow_passing.setIcon(helper.getImageIcon4Veraenderung(player.getAllLevelUp(ISpieler.SKILL_PASSSPIEL)
                                                                             .size()));
                arrow_wing.setIcon(helper.getImageIcon4Veraenderung(player.getAllLevelUp(ISpieler.SKILL_FLUEGEL)
                                                                          .size()));
                arrow_defense.setIcon(helper.getImageIcon4Veraenderung(player.getAllLevelUp(ISpieler.SKILL_VERTEIDIGUNG)
                                                                             .size()));
                arrow_scoring.setIcon(helper.getImageIcon4Veraenderung(player.getAllLevelUp(ISpieler.SKILL_TORSCHUSS)
                                                                             .size()));
                arrow_setpieces.setIcon(helper.getImageIcon4Veraenderung(player.getAllLevelUp(ISpieler.SKILL_STANDARDS)
                                                                               .size()));
                arrow_stamina.setIcon(helper.getImageIcon4Veraenderung(player.getAllLevelUp(ISpieler.SKILL_KONDITION)
                                                                             .size()));
                arrow_experience.setIcon(helper.getImageIcon4Veraenderung(player.getAllLevelUp(ISpieler.SKILL_EXPIERIENCE)
                                                                                .size()));
            }

            final List transfers = TransfersDAO.getTransfers(this.playerId, true);
            int valIncome = 0;
            final int teamid = Commons.getModel().getBasics().getTeamId();

            for (final Iterator iter = transfers.iterator(); iter.hasNext();) {
                final PlayerTransfer transfer = (PlayerTransfer) iter.next();

                if (transfer.getBuyerid() == teamid) {
                    valIncome -= transfer.getPrice();
                }

                if (transfer.getSellerid() == teamid) {
                    valIncome += transfer.getPrice();
                }
            }

            income.setText(Commons.getModel().getXtraDaten().getCurrencyName() + " " + valIncome);
            refreshPlayerTable(transfers);
        }
    }
}
