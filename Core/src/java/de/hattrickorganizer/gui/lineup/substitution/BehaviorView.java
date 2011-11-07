package de.hattrickorganizer.gui.lineup.substitution;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.hattrickorganizer.gui.lineup.substitution.PositionSelectionEvent.Change;
import de.hattrickorganizer.gui.model.CBItem;
import de.hattrickorganizer.model.HOVerwaltung;

public class BehaviorView extends JPanel {

	private static final long serialVersionUID = 6041242290064429972L;
	private CBItem[] standingValues = {
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.GoalAny"), -1),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.GoalTied"), 0),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.GoalLead"), 1),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.GoalDown"), 2),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.GoalLeadMT1"), 3),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.GoalDownMT1"), 4),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.GoalNotDown"), 5),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.GoalNotLead"), 6),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.GoalLeadMT2"), 7),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.GoalDownMT2"), 8) };

	private CBItem[] redcardValues = {
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedIgnore"), -1),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedMy"), 1),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedOpp"), 2),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedMyCD"), 11),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedMyMF"), 12),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedMyFW"), 13),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedMyWB"), 14),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedMyWI"), 15),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedOppCD"), 21),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedOppMF"), 22),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedOppFW"), 23),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedOppWB"), 24),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedOppWi"), 25), };

	private CBItem[] behaviourValues = {
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.BehNoChange"), -1),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.BehNormal"), 0),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.BehOffensive"), 1),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.BehDefensive"), 2),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.BehToMid"), 3),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.BehToWi"), 4) };
	private JComboBox playerComboBox;
	private JComboBox positionComboBox;
	private PositionChooser positionChooser;

	public BehaviorView() {
		initComponents();

		HashMap<Integer, PlayerPositionItem> lineupPositions = PositionDataProvider.getLineupPositions();
		Collection<PlayerPositionItem> players = lineupPositions.values();

		this.playerComboBox.setModel(new DefaultComboBoxModel(players.toArray()));
		this.playerComboBox.setSelectedItem(null);

		this.positionComboBox.setModel(new DefaultComboBoxModel(players.toArray()));
		this.positionComboBox.setSelectedItem(null);
		this.positionComboBox.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				PlayerPositionItem item = (PlayerPositionItem) positionComboBox.getSelectedItem();
				if (item != null) {
					positionChooser.select(Integer.valueOf(item.getPosition()));
				} else {
					positionChooser.select(null);
				}
			}
		});

		this.positionChooser.init(lineupPositions);
		this.positionChooser.addPositionSelectionListener(new PositionSelectionListener() {

			public void selectionChanged(PositionSelectionEvent event) {
				if (event.getChange() == Change.SELECTED) {
					for (int i = 0; i < positionComboBox.getModel().getSize(); i++) {
						PlayerPositionItem item = (PlayerPositionItem) positionComboBox.getModel()
								.getElementAt(i);
						if (event.getPosition().equals(item.getPosition())) {
							if (item != positionComboBox.getSelectedItem()) {
								positionComboBox.setSelectedItem(item);
							}
							break;
						}
					}
				} else {
					if (positionComboBox.getSelectedItem() != null) {
						positionComboBox.setSelectedItem(null);
					}
				}
			}
		});
	}

	private void initComponents() {
		setLayout(new GridBagLayout());

		JLabel playerLabel = new JLabel("Player:");
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 10, 4, 2);
		add(playerLabel, gbc);

		this.playerComboBox = new JComboBox();
		Dimension comboBoxSize = new Dimension(200, playerComboBox.getPreferredSize().height);
		this.playerComboBox.setMinimumSize(comboBoxSize);
		this.playerComboBox.setPreferredSize(comboBoxSize);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(10, 2, 4, 10);
		add(this.playerComboBox, gbc);

		JLabel behaviorLabel = new JLabel("New behavior:");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(4, 10, 4, 2);
		add(behaviorLabel, gbc);

		JComboBox behaviorComboBox = new JComboBox(this.behaviourValues);
		behaviorComboBox.setMinimumSize(comboBoxSize);
		behaviorComboBox.setPreferredSize(comboBoxSize);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(4, 2, 4, 10);
		add(behaviorComboBox, gbc);

		JLabel whenLabel = new JLabel("When:");
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(4, 10, 4, 2);
		add(whenLabel, gbc);

		final WhenTextField whenTextField = new WhenTextField();
		Dimension textFieldSize = new Dimension(200, whenTextField.getPreferredSize().height);
		whenTextField.setMinimumSize(textFieldSize);
		whenTextField.setPreferredSize(textFieldSize);
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.insets = new Insets(4, 2, 4, 10);
		add(whenTextField, gbc);

		final JSlider whenSlider = new JSlider(0, 119, 0);
		whenSlider.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				whenTextField.setValue(Integer.valueOf(whenSlider.getModel().getValue()));
			}
		});
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.insets = new Insets(0, 2, 8, 10);
		add(whenSlider, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(8, 4, 8, 4);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		add(new Divider("Advanced (optional)"), gbc);

		JLabel positionLabel = new JLabel("New position:");
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(4, 10, 4, 2);
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.0;
		add(positionLabel, gbc);

		this.positionComboBox = new JComboBox();
		this.positionComboBox.setMinimumSize(comboBoxSize);
		this.positionComboBox.setPreferredSize(comboBoxSize);
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.insets = new Insets(4, 2, 4, 10);
		add(this.positionComboBox, gbc);

		this.positionChooser = new PositionChooser();
		gbc.gridy = 6;
		gbc.insets = new Insets(2, 10, 8, 10);
		add(this.positionChooser, gbc);

		JLabel redCardsLabel = new JLabel("Red cards:");
		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(4, 10, 4, 2);
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.0;
		add(redCardsLabel, gbc);

		JComboBox redCardsComboBox = new JComboBox(this.redcardValues);
		redCardsComboBox.setMinimumSize(comboBoxSize);
		redCardsComboBox.setPreferredSize(comboBoxSize);
		gbc.gridx = 1;
		gbc.gridy = 7;
		gbc.insets = new Insets(4, 2, 4, 10);
		add(redCardsComboBox, gbc);

		JLabel standingLabel = new JLabel("Standing:");
		gbc.gridx = 0;
		gbc.gridy = 8;
		gbc.insets = new Insets(4, 10, 4, 2);
		add(standingLabel, gbc);

		JComboBox stadingComboBox = new JComboBox(this.standingValues);
		stadingComboBox.setMinimumSize(comboBoxSize);
		stadingComboBox.setPreferredSize(comboBoxSize);
		gbc.gridx = 1;
		gbc.gridy = 8;
		gbc.insets = new Insets(4, 2, 4, 10);
		add(stadingComboBox, gbc);

		whenTextField.addPropertyChangeListener("value", new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent evt) {
				Integer value = (Integer) whenTextField.getValue();
				if (value != null) {
					whenSlider.setValue(value.intValue());
				} else {
					whenSlider.setValue(0);
				}
			}
		});
	}

}
