package ho.module.specialEvents;

import ho.core.datatype.CBItem;
import ho.core.model.HOVerwaltung;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class FilterPanelNew extends JPanel {

	private static final long serialVersionUID = 6299290138063653349L;
	// matches
	private JCheckBox onlySEMatchesCheckBox;
	private JComboBox seasonComboBox;
	// match types
	private JCheckBox friendliesCheckBox;
	private JCheckBox leagueCheckBox;
	private JCheckBox tournamentCheckBox;
	private JCheckBox cupCheckBox;
	private JCheckBox mastersCheckBox;
	// SE types
	private JCheckBox specialitySECheckBox;
	private JCheckBox weatherSECheckBox;
	private JCheckBox counterAttackSECheckBox;
	private JCheckBox freeKickSECheckBox;
	private JCheckBox freeKickIndirectSECheckBox;
	private JCheckBox penaltySECheckBox;
	private JCheckBox longshotSECheckBox;
	private final Filter filter;

	public FilterPanelNew(Filter filter) {
		this.filter = filter;
		initComponents();
		initFromFilter();
		addListeners();
	}

	private void initFromFilter() {		
		SeasonFilterValue period = this.filter.getSeasonFilterValue();
		CBItem itemToSelect = null;
		if (period != null) {
			ComboBoxModel comboModel = this.seasonComboBox.getModel();
			for (int i = 0; i < comboModel.getSize(); i++) {
				CBItem item = (CBItem) comboModel.getElementAt(i);
				if (item.getId() == period.getId()) {
					itemToSelect = item;
					break;
				}
			}
		}
		this.seasonComboBox.setSelectedItem(itemToSelect);
		
		this.onlySEMatchesCheckBox.setSelected(this.filter.isShowMatchesWithSEOnly());
		this.friendliesCheckBox.setSelected(this.filter.isShowFriendlies());
		this.leagueCheckBox.setSelected(this.filter.isShowLeague());
		this.tournamentCheckBox.setSelected(this.filter.isShowTournament());
		this.cupCheckBox.setSelected(this.filter.isShowCup());
		this.mastersCheckBox.setSelected(this.filter.isShowMasters());
		this.specialitySECheckBox.setSelected(this.filter.isShowSpecialitySE());
		this.weatherSECheckBox.setSelected(this.filter.isShowWeatherSE());
		this.counterAttackSECheckBox.setSelected(this.filter.isShowCounterAttack());
		this.freeKickSECheckBox.setSelected(this.filter.isShowFreeKick());
		this.freeKickIndirectSECheckBox.setSelected(this.filter.isShowFreeKickIndirect());
		this.penaltySECheckBox.setSelected(this.filter.isShowPenalty());
		this.longshotSECheckBox.setSelected(this.filter.isShowLongShot());
	}

	private void initComponents() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.fill = GridBagConstraints.VERTICAL;

		add(createMatchFilterPanel(), gbc);
		gbc.gridx = 1;
		add(createMatchTypeFilterPanel(), gbc);
		gbc.gridx = 2;
		gbc.weightx = 1.0;
		add(createSEFilterPanel(), gbc);

	}

	private void addListeners() {

		this.seasonComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CBItem item = (CBItem) seasonComboBox.getSelectedItem();
				if (item == null) {
					filter.setSeasonFilterValue(null);
				} else {
					filter.setSeasonFilterValue(SeasonFilterValue.getById(item.getId()));
				}
			}
		});

		ItemListener checkBoxListener = new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				Object source = e.getSource();
				boolean selected = e.getStateChange() == ItemEvent.SELECTED;

				if (source == onlySEMatchesCheckBox) {
					filter.setShowMatchesWithSEOnly(selected);
				} else if (source == friendliesCheckBox) {
					filter.setShowFriendlies(selected);
				} else if (source == leagueCheckBox) {
					filter.setShowLeague(selected);
				} else if (source == tournamentCheckBox) {
					filter.setShowTournament(selected);
				} else if (source == cupCheckBox) {
					filter.setShowCup(selected);
				} else if (source == mastersCheckBox) {
					filter.setShowMasters(selected);
				} else if (source == specialitySECheckBox) {
					filter.setShowSpecialitySE(selected);
				} else if (source == weatherSECheckBox) {
					filter.setShowWeatherSE(selected);
				} else if (source == counterAttackSECheckBox) {
					filter.setShowCounterAttack(selected);
				} else if (source == freeKickSECheckBox) {
					filter.setShowFreeKick(selected);
				} else if (source == freeKickIndirectSECheckBox) {
					filter.setShowFreeKickIndirect(selected);
				} else if (source == penaltySECheckBox) {
					filter.setShowPenalty(selected);
				} else if (source == longshotSECheckBox) {
					filter.setShowLongShot(selected);
				}
			}
		};

		this.onlySEMatchesCheckBox.addItemListener(checkBoxListener);
		this.friendliesCheckBox.addItemListener(checkBoxListener);
		this.leagueCheckBox.addItemListener(checkBoxListener);
		this.tournamentCheckBox.addItemListener(checkBoxListener);
		this.cupCheckBox.addItemListener(checkBoxListener);
		this.mastersCheckBox.addItemListener(checkBoxListener);
		this.specialitySECheckBox.addItemListener(checkBoxListener);
		this.weatherSECheckBox.addItemListener(checkBoxListener);
		this.counterAttackSECheckBox.addItemListener(checkBoxListener);
		this.freeKickSECheckBox.addItemListener(checkBoxListener);
		this.freeKickIndirectSECheckBox.addItemListener(checkBoxListener);
		this.penaltySECheckBox.addItemListener(checkBoxListener);
		this.longshotSECheckBox.addItemListener(checkBoxListener);
	}

	private JPanel createSEFilterPanel() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory
				.createTitledBorder(getLangStr("specialEvents.filter.se.title")));

		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(4, 4, 4, 4);

		this.specialitySECheckBox = new JCheckBox();
		this.specialitySECheckBox.setText(getLangStr("SPECIALTYSE"));
		panel.add(this.specialitySECheckBox, gbc);

		this.weatherSECheckBox = new JCheckBox();
		this.weatherSECheckBox.setText(getLangStr("WEATHERSE"));
		gbc.gridy = 1;
		panel.add(this.weatherSECheckBox, gbc);

		this.counterAttackSECheckBox = new JCheckBox();
		this.counterAttackSECheckBox.setText(getLangStr("ls.match.event.counter-attack"));
		gbc.gridy = 2;
		panel.add(this.counterAttackSECheckBox, gbc);

		this.freeKickSECheckBox = new JCheckBox();
		this.freeKickSECheckBox.setText(getLangStr("highlight_freekick"));
		gbc.gridx = 1;
		gbc.gridy = 0;
		panel.add(this.freeKickSECheckBox, gbc);

		this.freeKickIndirectSECheckBox = new JCheckBox();
		this.freeKickIndirectSECheckBox.setText(getLangStr("highlight_freekick")
				+ getLangStr("indirect"));
		gbc.gridy = 1;
		panel.add(this.freeKickIndirectSECheckBox, gbc);

		this.penaltySECheckBox = new JCheckBox();
		this.penaltySECheckBox.setText(getLangStr("highlight_penalty"));
		gbc.gridy = 2;
		panel.add(this.penaltySECheckBox, gbc);

		this.longshotSECheckBox = new JCheckBox();
		this.longshotSECheckBox.setText(getLangStr("ls.match.event.longshot"));
		gbc.gridx = 2;
		gbc.gridy = 0;
		panel.add(this.longshotSECheckBox, gbc);

		return panel;
	}

	private JPanel createMatchFilterPanel() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory
				.createTitledBorder(getLangStr("specialEvents.filter.matches.title")));

		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(4, 4, 4, 4);

		this.onlySEMatchesCheckBox = new JCheckBox();
		this.onlySEMatchesCheckBox.setText(getLangStr("SpieleMitSEs"));
		panel.add(this.onlySEMatchesCheckBox, gbc);

		CBItem[] comboItems = new CBItem[3];
		comboItems[0] = new CBItem(getLangStr("AktSaison"),
				SeasonFilterValue.CURRENT_SEASON.getId());
		comboItems[1] = new CBItem(getLangStr("2Saison"),
				SeasonFilterValue.LAST_TWO_SEASONS.getId());
		comboItems[2] = new CBItem(getLangStr("AllSeasons"), SeasonFilterValue.ALL_SEASONS.getId());
		this.seasonComboBox = new JComboBox(comboItems);
		gbc.gridy = 1;
		panel.add(this.seasonComboBox, gbc);

		return panel;
	}

	private JPanel createMatchTypeFilterPanel() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory
				.createTitledBorder(getLangStr("specialEvents.filter.matchTypes.title")));

		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(4, 4, 4, 4);

		this.friendliesCheckBox = new JCheckBox();
		this.friendliesCheckBox.setText(getLangStr("specialEvents.filter.matchTypes.friendly"));
		panel.add(this.friendliesCheckBox, gbc);

		this.leagueCheckBox = new JCheckBox();
		this.leagueCheckBox.setText(getLangStr("specialEvents.filter.matchTypes.league"));
		gbc.gridy = 1;
		panel.add(this.leagueCheckBox, gbc);

		this.cupCheckBox = new JCheckBox();
		this.cupCheckBox.setText(getLangStr("specialEvents.filter.matchTypes.cup"));
		gbc.gridy = 2;
		panel.add(this.cupCheckBox, gbc);

		this.tournamentCheckBox = new JCheckBox();
		this.tournamentCheckBox.setText(getLangStr("specialEvents.filter.matchTypes.tournament"));
		gbc.gridy = 0;
		gbc.gridx = 1;
		panel.add(this.tournamentCheckBox, gbc);

		this.mastersCheckBox = new JCheckBox();
		this.mastersCheckBox.setText(getLangStr("specialEvents.filter.matchTypes.masters"));
		gbc.gridy = 1;
		panel.add(this.mastersCheckBox, gbc);

		return panel;
	}

	/**
	 * Convenience method.
	 * 
	 * @param key
	 * @return
	 */
	private String getLangStr(String key) {
		return HOVerwaltung.instance().getLanguageString(key);
	}
}
