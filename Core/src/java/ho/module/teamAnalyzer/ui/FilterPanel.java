// %3467106003:hoplugins.teamAnalyzer.ui%
package ho.module.teamAnalyzer.ui;

import ho.core.gui.comp.panel.ImagePanel;
import ho.core.model.HOVerwaltung;
import ho.core.util.HOLogger;
import ho.module.teamAnalyzer.SystemManager;
import ho.module.teamAnalyzer.ht.HattrickManager;
import ho.module.teamAnalyzer.manager.TeamManager;
import ho.module.teamAnalyzer.vo.Team;

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
import javax.swing.JPanel;
import javax.swing.JRadioButton;



/**
 * Panel to filter opponents matches.
 */
public class FilterPanel extends JPanel implements ActionListener {
    //~ Static fields/initializers -----------------------------------------------------------------

	private static final long serialVersionUID = -2795086705715618810L;
	
    private static final String CARD_AUTOMATIC = "AUTOMATIC CARD";
    private static final String CARD_MANUAL = "MANUAL CARD";
    private static boolean teamComboUpdating = false;

    //~ Instance fields ----------------------------------------------------------------------------

    private AutoFilterPanel autoPanel;
    private JButton downloadButton = new JButton(HOVerwaltung.instance().getLanguageString("update"));
    private JComboBox teamCombo = new JComboBox();
    private JPanel cards = new JPanel(new CardLayout());
    private JRadioButton radioAutomatic;
    private JRadioButton radioManual;
    private ManualFilterPanel manualPanel;
   
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new FilterPanel object.
     */
    public FilterPanel() {
        jbInit();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Get the selected team from the list box with opponents
     */
    public Team getSelectedTeam() {
        return (Team) teamCombo.getSelectedItem();
    }

    /**
     * Handle action events.
     */
    @Override
	public void actionPerformed(ActionEvent ae) {
        Object compo = ae != null ? ae.getSource() : null;
        CardLayout cLayout = (CardLayout) (cards.getLayout());

        if (radioAutomatic.equals(compo)) {
        	TeamAnalyzerPanel.filter.setAutomatic(true);
            autoPanel.reload();
            cLayout.show(cards, CARD_AUTOMATIC);
            return;
        } else if (radioManual.equals(compo)) {
            cLayout.show(cards, CARD_MANUAL);
            TeamAnalyzerPanel.filter.setAutomatic(false);
            manualPanel.reload();
            return;
        }
    }
    
    /**
     * Check, if the selected opponent is among the next 2 teams (allows full update according to CHPP rules).
     */
	private boolean isNextOpponent() {
		return ((SystemManager.getActiveTeamId() == SystemManager.getCupOpponentId()) //
		|| (SystemManager.getActiveTeamId() == SystemManager.getLeagueOpponentId()));
	}

    /**
     * Update GUI elements.
     */
    public void reload() {
        if (TeamManager.isUpdated()) {
            fillTeamCombo();
        }

        downloadButton.setEnabled(true);
        downloadButton.setText(HOVerwaltung.instance().getLanguageString("update"));

        CardLayout cLayout = (CardLayout) (cards.getLayout());

        if (TeamAnalyzerPanel.filter.isAutomatic()) {
            radioAutomatic.setSelected(true);
            cLayout.show(cards, CARD_AUTOMATIC);
            autoPanel.reload();
            return;
        }

        if (!TeamAnalyzerPanel.filter.isAutomatic()) {
            radioManual.setSelected(true);
            cLayout.show(cards, CARD_MANUAL);
            manualPanel.reload();
            return;
        }
    }

    /**
     * Fill the combo box with teams.
     */
    private void fillTeamCombo() {
        teamComboUpdating = true;
        teamCombo.removeAllItems();
        int i = 0;
		for (Iterator<Team> iter = TeamManager.getTeams().iterator(); iter.hasNext(); ) {
			Team element = iter.next();
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
     * Init GUI.
     */
    private void jbInit() {
        JPanel main = new ImagePanel();

        main.setLayout(new BorderLayout());
        setLayout(new BorderLayout());
        setOpaque(false);

        fillTeamCombo();
        teamCombo.setRenderer(new ComboBoxRenderer());
        teamCombo.setOpaque(false);
        teamCombo.addItemListener(new ItemListener() {
                @Override
				public void itemStateChanged(ItemEvent e) {
                    if (!teamComboUpdating) {
                        SystemManager.setActiveTeam((Team) teamCombo.getSelectedItem());
                        SystemManager.refresh();
                    }
                }
            });

        JButton analyzeButton = new JButton(HOVerwaltung.instance().getLanguageString("AutoFilterPanel.Analyze"));

		analyzeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				sorry Draghetto
//				if ((SystemManager.getActiveTeamId() == 240416) && (Commons.getModel().getBasics().getTeamId() != 240416)) {
//					JOptionPane.showMessageDialog(SystemManager.getPlugin().getPluginPanel(), new JokePanel(),
//							PluginProperty.getString("Menu.About"), JOptionPane.PLAIN_MESSAGE);
//					return;
//				}
				if (radioManual.isSelected()) {
					manualPanel.setFilter();
				} else {
					autoPanel.setFilter();
				}
				SystemManager.updateReport();
			}
		});

		downloadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				HOLogger.instance().log(getClass(), "UPDATE for Team " + SystemManager.getActiveTeamId());
				HattrickManager.downloadPlayers(SystemManager.getActiveTeamId());
					HattrickManager.downloadMatches(SystemManager.getActiveTeamId(), TeamAnalyzerPanel.filter);
				SystemManager.refresh();
			}
		});

        JPanel teamPanel =new ImagePanel();

        teamPanel.setLayout(new BorderLayout());
        teamPanel.add(downloadButton, BorderLayout.NORTH);
        teamPanel.add(teamCombo, BorderLayout.SOUTH);
        teamPanel.setOpaque(false);

        JPanel topPanel = new ImagePanel();

        topPanel.setLayout(new BorderLayout());
        radioAutomatic = new JRadioButton(HOVerwaltung.instance().getLanguageString("Option.Auto")); //$NON-NLS-1$
        radioAutomatic.setSelected(true);
        radioAutomatic.addActionListener(this);
        radioAutomatic.setOpaque(false);
        radioManual = new JRadioButton(HOVerwaltung.instance().getLanguageString("Manual")); //$NON-NLS-1$
        radioManual.addActionListener(this);
        radioManual.setOpaque(false);

        ButtonGroup groupRadio = new ButtonGroup();
        JPanel buttonPanel = new ImagePanel();

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
