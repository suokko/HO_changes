// %2601556114:hoplugins.trainingExperience.ui%
package ho.module.training.ui;

import ho.module.training.OldTrainingManager;
import ho.module.training.SkillChange;
import ho.module.training.ui.model.ChangesTableModel;
import ho.module.training.ui.renderer.ChangeTableRenderer;
import ho.module.training.ui.renderer.SkillupTypeTableCellRenderer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import plugins.ISkillup;
import plugins.ISpieler;
import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.tools.HelperWrapper;
import de.hattrickorganizer.tools.extension.PlayerCreator;


/**
 * Shows a table of skillups, which can be filtered using the checkboxes on the side.
 *
 * @author NetHyperon
 */
public class AnalyzerPanel extends JPanel implements ActionListener, ChangeListener {
    //~ Static fields/initializers -----------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -2152169077412317532L;

	/** TODO Missing Parameter Documentation */
    private static final String CMD_SELECT_ALL = "selectAll"; //$NON-NLS-1$

    //~ Instance fields ----------------------------------------------------------------------------

    private ButtonModel oldPlayers;
    private JButton btAll = new JButton();
    private JPanel filterPanel = new ImagePanel();
    private JTable changesTable = new JTable();
    private Map<Integer,ButtonModel> buttonModels = new HashMap<Integer,ButtonModel>();
    private Map<Integer,List<SkillChange>> skillups;
    private Map<Integer,List<SkillChange>> skillupsOld;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new AnalyzerPanel object.
     */
    public AnalyzerPanel() {
        super();
        jbInit();
        reload();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        if (CMD_SELECT_ALL.equals(e.getActionCommand())) {
            for (Iterator<ButtonModel> iter = this.buttonModels.values().iterator(); iter.hasNext();) {
                ButtonModel bModel = iter.next();
                bModel.setSelected(true);
            }
            this.oldPlayers.setSelected(true);
        }
        updateTableModel();
    }

    /**
     * Reload the data and redraw the panel
     */
    public void reload() {
        this.skillups = getSkillups(HOVerwaltung.instance().getModel().getAllSpieler());
        this.skillupsOld = getSkillups(HOVerwaltung.instance().getModel().getAllOldSpieler());
        updateFilterPanel();
        updateTableModel();
        updateUI();
    }

    /**
     * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     */
    public void stateChanged(ChangeEvent e) {
        updateFilterPanel();
    }

    /**
     * Sets the model for skill changes table.
     */
    public void updateTableModel() {
    	List<SkillChange> values = new ArrayList<SkillChange>();

        for (Iterator<Integer> iter = this.buttonModels.keySet().iterator(); iter.hasNext();) {
            Integer skillType = iter.next();
            ButtonModel bModel = this.buttonModels.get(skillType);

            if (this.skillups.containsKey(skillType) && bModel.isSelected()) {
                values.addAll(this.skillups.get(skillType));
            }

            if (this.oldPlayers.isSelected()
                && bModel.isSelected()
                && this.skillupsOld.containsKey(skillType)) {
                values.addAll(this.skillupsOld.get(skillType));
            }
        }

        Collections.sort(values,
                         new Comparator<SkillChange>() {
                public int compare(SkillChange o1, SkillChange o2) {
                    SkillChange sc1 = (SkillChange) o1;
                    SkillChange sc2 = (SkillChange) o2;

                    if (sc1.getSkillup().getHtSeason() > sc2.getSkillup().getHtSeason()) {
                        return -1;
                    } else if (sc1.getSkillup().getHtSeason() < sc2.getSkillup().getHtSeason()) {
                        return 1;
                    } else {
                        if (sc1.getSkillup().getHtWeek() > sc2.getSkillup().getHtWeek()) {
                            return -1;
                        } else if (sc1.getSkillup().getHtWeek() < sc2.getSkillup().getHtWeek()) {
                            return 1;
                        } else {
                            if ((sc1.getPlayer().equals(sc2.getPlayer()))
                                && (sc1.getSkillup().getType() == sc2.getSkillup().getType())) {
                                if (sc1.getSkillup().getValue() > sc2.getSkillup().getValue()) {
                                    return -1;
                                } else {
                                    return 1;
                                }
                            } else {
                                return sc1.getPlayer().getName().compareTo(sc2.getPlayer().getName());
                            }
                        }
                    }
                }
            });

        changesTable.setModel(new ChangesTableModel(values));
        changesTable.setDefaultRenderer(Object.class, new ChangeTableRenderer());
        changesTable.getTableHeader().setReorderingAllowed(false);
        changesTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        changesTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        changesTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        changesTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        changesTable.getColumnModel().getColumn(4).setPreferredWidth(100);

        // Hide column 5
        changesTable.getTableHeader().getColumnModel().getColumn(5).setPreferredWidth(0);
        changesTable.getTableHeader().getColumnModel().getColumn(5).setMinWidth(0);
        changesTable.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(0);

        // Hide column 6
        changesTable.getTableHeader().getColumnModel().getColumn(6).setPreferredWidth(0);
        changesTable.getTableHeader().getColumnModel().getColumn(6).setMinWidth(0);
        changesTable.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(0);

        // Set own renderer instance for skillup column.
        changesTable.getTableHeader().getColumnModel().getColumn(3).setCellRenderer(new SkillupTypeTableCellRenderer());
    }

    /**
     * Get map of skillups for a list of players. The map will contain a list of skillups for each
     * skill, represented as an <code>Integer</code> as key
     *
     * @param players List of players to analyze
     *
     * @return Map of skillups
     */
    private Map<Integer,List<SkillChange>> getSkillups(List<ISpieler> players) {
        Map<Integer,List<SkillChange>> skillupsByType = new HashMap<Integer,List<SkillChange>>();

        for (Iterator<ISpieler> iter = players.iterator(); iter.hasNext();) {
            ISpieler player = (ISpieler) iter.next();

            OldTrainingManager otm = new OldTrainingManager(player);

            List<ISkillup> skillups = otm.getAllSkillups();

            for (Iterator<ISkillup>  iterator = skillups.iterator(); iterator.hasNext();) {
                ISkillup skillup = iterator.next();
                Integer skillType = new Integer(skillup.getType());

                List<SkillChange> skillChanges = skillupsByType.get(skillType);

                if (skillChanges == null) {
                    skillChanges = new Vector<SkillChange>();
                    skillupsByType.put(skillType, skillChanges);
                }

                skillChanges.add(new SkillChange(player, skillup));
            }
        }

        return skillupsByType;
    }

    /**
     * Creates a panel with a skill increases number and a checkbox.
     *
     * @param skill skill type
     *
     * @return a panel
     */
    private JPanel createSkillSelector(int skill) {
        Integer skillType = new Integer(skill);

        int change = 0;

        if (this.skillups.containsKey(skillType)) {
            change += (this.skillups.get(skillType)).size();
        }

        if (this.oldPlayers.isSelected() && this.skillupsOld.containsKey(skillType)) {
            change += (this.skillupsOld.get(skillType)).size();
        }

        JCheckBox cBox = new JCheckBox();

        cBox.setOpaque(false);
        cBox.setFocusable(false);

        if (this.buttonModels.containsKey(skillType)) {
            cBox.setModel((ButtonModel) this.buttonModels.get(skillType));
        } else {
            this.buttonModels.put(skillType, cBox.getModel());

            if (change > 0) {
                cBox.setSelected(true);
            } else {
                cBox.setSelected(false);
            }
        }

        cBox.setText(PlayerCreator.getSkillDescription(skill));
        cBox.addActionListener(this);

        JPanel panel = new ImagePanel();

        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        panel.add(new JLabel(HelperWrapper.instance().getWideImageIcon4Veraenderung(change)));
        panel.add(cBox);

        return panel;
    }

    /**
     * Initialize panel.
     */
    private void jbInit() {
        setOpaque(false);
        setLayout(new BorderLayout());

        JPanel mainpanel = new ImagePanel();

        mainpanel.setLayout(new BorderLayout());

        JPanel skillPanel = new ImagePanel();

        skillPanel.setLayout(new BorderLayout());
        skillPanel.setBorder(BorderFactory.createTitledBorder(HOVerwaltung.instance().getLanguageString("TAB_SKILL"))); //$NON-NLS-1$

        // Add selection listener.
        changesTable.getSelectionModel().addListSelectionListener(new PlayerSelectionListener(changesTable,
                                                                                              6));

        JScrollPane changesPane = new JScrollPane(changesTable);

        skillPanel.add(changesPane, BorderLayout.CENTER);

        JCheckBox cbOldPlayers = new JCheckBox();

        cbOldPlayers.setOpaque(false);
        cbOldPlayers.setText(HOVerwaltung.instance().getLanguageString("IncludeOld")); //$NON-NLS-1$
        cbOldPlayers.setFocusable(false);
        cbOldPlayers.setSelected(false);
        cbOldPlayers.addChangeListener(this);
        cbOldPlayers.addActionListener(this);
        this.oldPlayers = cbOldPlayers.getModel();
        skillPanel.add(cbOldPlayers, BorderLayout.SOUTH);

        JPanel sidePanel = new ImagePanel();

        sidePanel.setLayout(new BorderLayout());
        sidePanel.add(filterPanel, BorderLayout.CENTER);

        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));

        btAll.setText(HOVerwaltung.instance().getLanguageString("ShowAll")); //$NON-NLS-1$
        btAll.setFocusable(false);
        btAll.addActionListener(this);
        btAll.setActionCommand(CMD_SELECT_ALL);
        sidePanel.add(btAll, BorderLayout.SOUTH);

        JScrollPane sidePane = new JScrollPane(sidePanel);

        sidePane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sidePane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        sidePane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        mainpanel.add(sidePane, BorderLayout.WEST);
        mainpanel.add(skillPanel, BorderLayout.CENTER);

        // Add legend panel. 
        //mainpanel.add(new TrainingLegendPanel(), BorderLayout.SOUTH);
        add(mainpanel, BorderLayout.CENTER);
    }

    /**
     * Redraws the panel with a checkbox for each skill and a number of increases per skill.
     */
    private void updateFilterPanel() {
        filterPanel.removeAll();

        filterPanel.add(Box.createVerticalStrut(20));

        filterPanel.add(createSkillSelector(ISpieler.SKILL_TORWART));
        filterPanel.add(createSkillSelector(ISpieler.SKILL_SPIELAUFBAU));
        filterPanel.add(createSkillSelector(ISpieler.SKILL_PASSSPIEL));
        filterPanel.add(createSkillSelector(ISpieler.SKILL_FLUEGEL));
        filterPanel.add(createSkillSelector(ISpieler.SKILL_VERTEIDIGUNG));
        filterPanel.add(createSkillSelector(ISpieler.SKILL_TORSCHUSS));
        filterPanel.add(createSkillSelector(ISpieler.SKILL_STANDARDS));
        filterPanel.add(createSkillSelector(ISpieler.SKILL_KONDITION));
        filterPanel.add(createSkillSelector(ISpieler.SKILL_EXPIERIENCE));

        filterPanel.add(Box.createVerticalStrut(20));

        filterPanel.revalidate();
    }
}
