// %2601556114:hoplugins.trainingExperience.ui%
package ho.module.training.ui;

import ho.core.constants.player.PlayerSkill;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.gui.theme.ImageUtilities;
import ho.core.model.HOVerwaltung;
import ho.core.model.player.ISkillup;
import ho.core.model.player.Spieler;
import ho.core.util.GUIUtils;
import ho.module.training.OldTrainingManager;
import ho.module.training.SkillChange;
import ho.module.training.ui.model.ChangesTableModel;
import ho.module.training.ui.renderer.ChangeTableRenderer;
import ho.module.training.ui.renderer.SkillupTypeTableCellRenderer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
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
import javax.swing.table.TableColumn;

/**
 * Shows a table of skillups, which can be filtered using the checkboxes on the
 * side.
 * 
 * @author NetHyperon
 */
public class AnalyzerPanel extends JPanel implements ActionListener,
		ChangeListener {

	private static final long serialVersionUID = -2152169077412317532L;
	private static final String CMD_SELECT_ALL = "selectAll";
	private static final String CMD_CLEAR_ALL = "clearAll";
	private ButtonModel oldPlayers;
	private JPanel filterPanel = new ImagePanel();
	private JTable changesTable = new JTable();
	private Map<Integer, ButtonModel> buttonModels = new HashMap<Integer, ButtonModel>();
	private Map<Integer, List<SkillChange>> skillups;
	private Map<Integer, List<SkillChange>> skillupsOld;

	/**
	 * Creates a new AnalyzerPanel object.
	 */
	public AnalyzerPanel() {
		super();
		initComponents();
		reload();
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (CMD_SELECT_ALL.equals(e.getActionCommand())) {
			setAllSelected(true);
		} else if (CMD_CLEAR_ALL.equals(e.getActionCommand())) {
			setAllSelected(false);
		}
		updateTableModel();
	}

	/**
	 * Reload the data and redraw the panel
	 */
	public void reload() {
		this.skillups = getSkillups(HOVerwaltung.instance().getModel()
				.getAllSpieler());
		this.skillupsOld = getSkillups(HOVerwaltung.instance().getModel()
				.getAllOldSpieler());
		updateFilterPanel();
		updateTableModel();
		updateUI();
	}

	/**
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		updateFilterPanel();
	}

	/**
	 * Sets the model for skill changes table.
	 */
	public void updateTableModel() {
		List<SkillChange> values = new ArrayList<SkillChange>();

		for (Iterator<Integer> iter = this.buttonModels.keySet().iterator(); iter
				.hasNext();) {
			Integer skillType = iter.next();
			ButtonModel bModel = this.buttonModels.get(skillType);

			if (this.skillups.containsKey(skillType) && bModel.isSelected()) {
				values.addAll(this.skillups.get(skillType));
			}

			if (this.oldPlayers.isSelected() && bModel.isSelected()
					&& this.skillupsOld.containsKey(skillType)) {
				values.addAll(this.skillupsOld.get(skillType));
			}
		}

		Collections.sort(values, new Comparator<SkillChange>() {
			@Override
			public int compare(SkillChange sc1, SkillChange sc2) {
				if (sc1.getSkillup().getHtSeason() > sc2.getSkillup()
						.getHtSeason()) {
					return -1;
				} else if (sc1.getSkillup().getHtSeason() < sc2.getSkillup()
						.getHtSeason()) {
					return 1;
				} else {
					if (sc1.getSkillup().getHtWeek() > sc2.getSkillup()
							.getHtWeek()) {
						return -1;
					} else if (sc1.getSkillup().getHtWeek() < sc2.getSkillup()
							.getHtWeek()) {
						return 1;
					} else {
						if ((sc1.getPlayer().equals(sc2.getPlayer()))
								&& (sc1.getSkillup().getType() == sc2
										.getSkillup().getType())) {
							if (sc1.getSkillup().getValue() > sc2.getSkillup()
									.getValue()) {
								return -1;
							} else {
								return 1;
							}
						} else {
							return sc1.getPlayer().getName()
									.compareTo(sc2.getPlayer().getName());
						}
					}
				}
			}
		});

		changesTable.setModel(new ChangesTableModel(values));
		changesTable
				.setDefaultRenderer(Object.class, new ChangeTableRenderer());
		changesTable.getTableHeader().setReorderingAllowed(false);
		changesTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		changesTable.getColumnModel().getColumn(1).setPreferredWidth(50);
		changesTable.getColumnModel().getColumn(2).setPreferredWidth(150);
		changesTable.getColumnModel().getColumn(3).setPreferredWidth(100);
		changesTable.getColumnModel().getColumn(4).setPreferredWidth(100);

		// Hide column 5
		TableColumn tblColumn = changesTable.getTableHeader().getColumnModel()
				.getColumn(5);
		tblColumn.setPreferredWidth(0);
		tblColumn.setMinWidth(0);
		tblColumn.setMaxWidth(0);

		// Hide column 6
		tblColumn = changesTable.getTableHeader().getColumnModel().getColumn(6);
		tblColumn.setPreferredWidth(0);
		tblColumn.setMinWidth(0);
		tblColumn.setMaxWidth(0);

		// Set own renderer instance for skillup column.
		changesTable.getTableHeader().getColumnModel().getColumn(3)
				.setCellRenderer(new SkillupTypeTableCellRenderer());
	}

	private void setAllSelected(boolean selected) {
		for (Iterator<ButtonModel> iter = this.buttonModels.values().iterator(); iter
				.hasNext();) {
			ButtonModel bModel = iter.next();
			bModel.setSelected(selected);
		}
		this.oldPlayers.setSelected(selected);
	}

	/**
	 * Get map of skillups for a list of players. The map will contain a list of
	 * skillups for each skill, represented as an <code>Integer</code> as key
	 * 
	 * @param players
	 *            List of players to analyze
	 * 
	 * @return Map of skillups
	 */
	private Map<Integer, List<SkillChange>> getSkillups(List<Spieler> players) {
		Map<Integer, List<SkillChange>> skillupsByType = new HashMap<Integer, List<SkillChange>>();

		for (Spieler player : players) {
			OldTrainingManager otm = new OldTrainingManager(player);
			List<ISkillup> skillups = otm.getAllSkillups();

			for (ISkillup skillup : skillups) {
				Integer skillType = new Integer(skillup.getType());
				List<SkillChange> skillChanges = skillupsByType.get(skillType);

				if (skillChanges == null) {
					skillChanges = new ArrayList<SkillChange>();
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
	 * @param skill
	 *            skill type
	 * 
	 * @return a panel
	 */
	private JPanel createSkillSelector(int skill) {
		Integer skillType = new Integer(skill);

		int change = 0;

		if (this.skillups.containsKey(skillType)) {
			change += (this.skillups.get(skillType)).size();
		}

		if (this.oldPlayers.isSelected()
				&& this.skillupsOld.containsKey(skillType)) {
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

		cBox.setText(PlayerSkill.toString(skill));
		cBox.addActionListener(this);

		JPanel panel = new ImagePanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(new JLabel(ImageUtilities.getWideImageIcon4Veraenderung(
				change, true)));
		panel.add(cBox);

		return panel;
	}

	/**
	 * Initialize panel.
	 */
	private void initComponents() {
		setOpaque(false);
		setLayout(new BorderLayout());

		JPanel mainpanel = new ImagePanel(new BorderLayout());
		JPanel skillPanel = new ImagePanel();

		skillPanel.setLayout(new BorderLayout());
		skillPanel.setBorder(BorderFactory.createTitledBorder(HOVerwaltung
				.instance().getLanguageString("TAB_SKILL")));

		// Add selection listener.
		changesTable.getSelectionModel().addListSelectionListener(
				new PlayerSelectionListener(changesTable, 6));

		JScrollPane changesPane = new JScrollPane(changesTable);

		skillPanel.add(changesPane, BorderLayout.CENTER);

		JCheckBox cbOldPlayers = new JCheckBox();

		cbOldPlayers.setOpaque(false);
		cbOldPlayers.setText(HOVerwaltung.instance().getLanguageString(
				"IncludeOld")); //$NON-NLS-1$
		cbOldPlayers.setFocusable(false);
		cbOldPlayers.setSelected(false);
		cbOldPlayers.addChangeListener(this);
		cbOldPlayers.addActionListener(this);
		this.oldPlayers = cbOldPlayers.getModel();
		skillPanel.add(cbOldPlayers, BorderLayout.SOUTH);

		JPanel sidePanel = new ImagePanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();		
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.gridy = 0;
		gbc.insets = new Insets(20, 8, 4, 8);
		sidePanel.add(filterPanel, gbc);

		filterPanel.setLayout(new GridBagLayout());

		JButton btnShowAll = new JButton();
		btnShowAll
				.setText(HOVerwaltung.instance().getLanguageString("ShowAll"));
		btnShowAll.setFocusable(false);
		btnShowAll.addActionListener(this);
		btnShowAll.setActionCommand(CMD_SELECT_ALL);
		gbc.gridy = 1;
		gbc.insets = new Insets(8, 8, 2, 8);
		sidePanel.add(btnShowAll, gbc);

		JButton btnClearAll = new JButton();
		btnClearAll.setText(HOVerwaltung.instance().getLanguageString(
				"ClearAll"));
		btnClearAll.setFocusable(false);
		btnClearAll.addActionListener(this);
		btnClearAll.setActionCommand(CMD_CLEAR_ALL);
		gbc.gridy = 2;
		gbc.insets = new Insets(2, 8, 8, 8);
		gbc.weighty = 1.0;
		sidePanel.add(btnClearAll, gbc);

		GUIUtils.equalizeComponentSizes(btnShowAll, btnClearAll);

		JScrollPane sidePane = new JScrollPane(sidePanel);
		sidePane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sidePane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		sidePane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		mainpanel.add(sidePane, BorderLayout.WEST);
		mainpanel.add(skillPanel, BorderLayout.CENTER);
		add(mainpanel, BorderLayout.CENTER);
	}

	/**
	 * Redraws the panel with a checkbox for each skill and a number of
	 * increases per skill.
	 */
	private void updateFilterPanel() {
		filterPanel.removeAll();

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridy = 0;
		filterPanel.add(createSkillSelector(PlayerSkill.KEEPER), gbc);
		gbc.gridy++;
		filterPanel.add(createSkillSelector(PlayerSkill.PLAYMAKING), gbc);
		gbc.gridy++;
		filterPanel.add(createSkillSelector(PlayerSkill.PASSING), gbc);
		gbc.gridy++;
		filterPanel.add(createSkillSelector(PlayerSkill.WINGER), gbc);
		gbc.gridy++;
		filterPanel.add(createSkillSelector(PlayerSkill.DEFENDING), gbc);
		gbc.gridy++;
		filterPanel.add(createSkillSelector(PlayerSkill.SCORING), gbc);
		gbc.gridy++;
		filterPanel.add(createSkillSelector(PlayerSkill.SET_PIECES), gbc);
		gbc.gridy++;
		filterPanel.add(createSkillSelector(PlayerSkill.STAMINA), gbc);
		gbc.gridy++;
		filterPanel.add(createSkillSelector(PlayerSkill.EXPERIENCE), gbc);

		filterPanel.revalidate();
	}
}
