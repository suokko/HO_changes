// %3467106003:hoplugins.teamAnalyzer.ui%
package hoplugins.teamAnalyzer.ui;

import hoplugins.Commons;

import hoplugins.commons.utils.PluginProperty;

import hoplugins.teamAnalyzer.SystemManager;
import hoplugins.teamAnalyzer.ht.HattrickManager;
import hoplugins.teamAnalyzer.manager.TeamManager;
import hoplugins.teamAnalyzer.ui.component.JokePanel;
import hoplugins.teamAnalyzer.vo.Team;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.util.Iterator;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class FilterPanel extends JPanel implements ActionListener {
    //~ Static fields/initializers -----------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -2795086705715618810L;

	/** TODO Missing Parameter Documentation */
    private static final String CARD_AUTOMATIC = "AUTOMATIC CARD";

    /** TODO Missing Parameter Documentation */
    private static final String CARD_MANUAL = "MANUAL CARD";
    private static boolean teamComboUpdating = false;

    //~ Instance fields ----------------------------------------------------------------------------

    private AutoFilterPanel autoPanel;
    private JButton downloadButton = new JButton(PluginProperty.getString("Update"));
    private JComboBox teamCombo = new JComboBox();
    private JPanel cards = new JPanel(new CardLayout());
    private JRadioButton radioAutomatic;
    private JRadioButton radioManual;
    private ManualFilterPanel manualPanel;
    private boolean fullUpdate = false;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new FilterPanel object.
     */
    public FilterPanel() {
        jbInit();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public Team getSelectedTeam() {
        return (Team) teamCombo.getSelectedItem();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param ae TODO Missing Method Parameter Documentation
     */
    public void actionPerformed(ActionEvent ae) {
        Object compo = ae.getSource();
        CardLayout cLayout = (CardLayout) (cards.getLayout());

        if (compo == radioAutomatic) {
            SystemManager.getFilter().setAutomatic(true);
            autoPanel.reload();
            cLayout.show(cards, CARD_AUTOMATIC);

            return;
        } else if (compo == radioManual) {
            cLayout.show(cards, CARD_MANUAL);
            SystemManager.getFilter().setAutomatic(false);
            manualPanel.reload();

            return;
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    public void reload() {
        if (TeamManager.isUpdated()) {
            fillTeamCombo();
        }

        boolean isNextOpponent = ((SystemManager.getActiveTeamId() == SystemManager
                                                                      .getCupOpponentId())
                                 || (SystemManager.getActiveTeamId() == SystemManager
                                                                        .getLeagueOpponentId()));

        if (isNextOpponent) {
            fullUpdate = true;
            downloadButton.setEnabled(true);
            downloadButton.setText(PluginProperty.getString("Update"));
        } else {
            fullUpdate = false;
            downloadButton.setText(PluginProperty.getString("FilterPanel.RosterUpdate"));
        }

        CardLayout cLayout = (CardLayout) (cards.getLayout());

        if (SystemManager.getFilter().isAutomatic()) {
            radioAutomatic.setSelected(true);
            cLayout.show(cards, CARD_AUTOMATIC);
            autoPanel.reload();

            return;
        }

        if (!SystemManager.getFilter().isAutomatic()) {
            radioManual.setSelected(true);
            cLayout.show(cards, CARD_MANUAL);
            manualPanel.reload();

            return;
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    private void fillTeamCombo() {
        teamComboUpdating = true;
        teamCombo.removeAllItems();

        int i = 0;

        for (Iterator iter = TeamManager.getTeams().iterator(); iter.hasNext();) {
            Team element = (Team) iter.next();

            teamCombo.addItem(element);

            if (SystemManager.getActiveTeamId() == element.getTeamId()) {
                teamCombo.setSelectedItem(element);
            }

            i++;
        }

        teamCombo.setMaximumRowCount(i);
        teamComboUpdating = false;
    }

    /**
     * TODO Missing Method Documentation
     */
    private void jbInit() {
        JPanel main = Commons.getModel().getGUI().createImagePanel();

        main.setLayout(new BorderLayout());
        setLayout(new BorderLayout());
        setOpaque(false);

        fillTeamCombo();
        teamCombo.setRenderer(new ComboBoxRenderer());
        teamCombo.setOpaque(false);
        teamCombo.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    if (!teamComboUpdating) {
                        SystemManager.setActiveTeam((Team) teamCombo.getSelectedItem());
                        SystemManager.refresh();
                    }
                }
            });

        JButton analyzeButton = new JButton(PluginProperty.getString("AutoFilterPanel.Analyze")); //$NON-NLS-1$

        analyzeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if ((SystemManager.getActiveTeamId() == 240416)
                        && (Commons.getModel().getBasics().getTeamId() != 240416)) {
                        JOptionPane.showMessageDialog(SystemManager.getPlugin().getPluginPanel(),
                                                      new JokePanel(),
                                                      PluginProperty.getString("Menu.About"),
                                                      JOptionPane.PLAIN_MESSAGE);

                        return;
                    }

                    if (radioManual.isSelected()) {
                        manualPanel.setFilter();
                    } else {
                        autoPanel.setFilter();
                    }

                    SystemManager.updateReport();
                }
            });

        downloadButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    HattrickManager.downloadPlayers(SystemManager.getActiveTeamId());

                    if (fullUpdate) {
                        HattrickManager.downloadMatches(SystemManager.getActiveTeamId());
                    }

                    SystemManager.refresh();
                }
            });

        JPanel teamPanel = Commons.getModel().getGUI().createImagePanel();

        teamPanel.setLayout(new BorderLayout());
        teamPanel.add(downloadButton, BorderLayout.NORTH);
        teamPanel.add(teamCombo, BorderLayout.SOUTH);
        teamPanel.setOpaque(false);

        JPanel topPanel = Commons.getModel().getGUI().createImagePanel();

        topPanel.setLayout(new BorderLayout());
        radioAutomatic = new JRadioButton(PluginProperty.getString("FilterPanel.Automatic")); //$NON-NLS-1$
        radioAutomatic.setSelected(true);
        radioAutomatic.addActionListener(this);
        radioAutomatic.setOpaque(false);
        radioManual = new JRadioButton(PluginProperty.getString("FilterPanel.Manual")); //$NON-NLS-1$
        radioManual.addActionListener(this);
        radioManual.setOpaque(false);

        ButtonGroup groupRadio = new ButtonGroup();
        JPanel buttonPanel = Commons.getModel().getGUI().createImagePanel();

        buttonPanel.setLayout(new BorderLayout());
        groupRadio.add(radioAutomatic);
        groupRadio.add(radioManual);
        buttonPanel.add(radioAutomatic, BorderLayout.WEST);
        buttonPanel.add(radioManual, BorderLayout.EAST);
        topPanel.add(teamPanel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        main.add(topPanel, BorderLayout.NORTH);
        add(main, BorderLayout.NORTH);
        autoPanel = new AutoFilterPanel();
        manualPanel = new ManualFilterPanel();
        cards.add(autoPanel, CARD_AUTOMATIC);
        cards.add(manualPanel, CARD_MANUAL);
        add(cards, BorderLayout.CENTER);
        add(analyzeButton, BorderLayout.SOUTH);
    }
}
