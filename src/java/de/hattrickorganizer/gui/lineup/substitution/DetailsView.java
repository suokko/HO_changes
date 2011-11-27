package de.hattrickorganizer.gui.lineup.substitution;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.MessageFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import plugins.ISpieler;
import plugins.ISubstitution;
import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.model.HOModel;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.tools.Helper;

public class DetailsView extends JPanel {

	private static final Dimension COMPONENTENSIZE = new Dimension(Helper.calcCellWidth(300),
			Helper.calcCellWidth(18));
	private static final long serialVersionUID = -8046083206070885556L;
	private ISubstitution substitution;
	private ColorLabelEntry orderTypeEntry;
	private ColorLabelEntry playerInEntry;
	private ColorLabelEntry playerOutEntry;
	private ColorLabelEntry whenEntry;
	private ColorLabelEntry newPositionEntry;
	private ColorLabelEntry standingEntry;
	private ColorLabelEntry redCardsEntry;
	private JLabel playerLabel;
	private JLabel playerInLabel;
	private JLabel newPositionLabel;

	public DetailsView() {
		initComponents();
	}

	public void setSubstitution(ISubstitution sub) {
		this.substitution = sub;
		updateView();
		updateData();
	}

	private void updateView() {

	}

	private void updateData() {
		byte idOrderType = -1;
		String orderType = "";
		String playerIn = "";
		String playerOut = "";
		String when = "";
		String newPosition = "";
		String standing = "";
		String redCards = "";

		if (this.substitution != null) {
			idOrderType = this.substitution.getOrderType();

			HOModel hoModel = HOVerwaltung.instance().getModel();
			orderType = Lookup.getOrderType(this.substitution.getOrderType());

			ISpieler in = hoModel.getSpieler(this.substitution.getPlayerIn());
			playerIn = (in != null) ? in.getName() : "";
			if (this.substitution.getPlayerOut() != this.substitution.getPlayerIn()) {
				ISpieler out = hoModel.getSpieler(this.substitution.getPlayerOut());
				playerOut = (out != null) ? out.getName() : "";
			}

			if (this.substitution.getMatchMinuteCriteria() > 0) {
				when = MessageFormat.format(HOVerwaltung.instance().getLanguageString("subs.MinuteAfterX"),
						Integer.valueOf(this.substitution.getMatchMinuteCriteria()));
			} else {
				when = HOVerwaltung.instance().getLanguageString("subs.MinuteAnytime");
			}

			if (this.substitution.getPos() != -1) {
				newPosition = Lookup.getPosition(this.substitution.getPos());
			}

			redCards = Lookup.getRedCard(this.substitution.getCard());
			standing = Lookup.getStanding(this.substitution.getStanding());
		}
		this.orderTypeEntry.setText(orderType);
		this.playerInEntry.setText(playerIn);
		this.playerOutEntry.setText(playerOut);
		this.whenEntry.setText(when);
		this.newPositionEntry.setText(newPosition);
		this.redCardsEntry.setText(redCards);
		this.standingEntry.setText(standing);

		switch (idOrderType) {
		case ISubstitution.SUBSTITUTION:
			this.playerLabel.setText(HOVerwaltung.instance().getLanguageString("subs.Out"));
			this.playerInLabel.setText(HOVerwaltung.instance().getLanguageString("subs.In"));
			this.newPositionLabel.setEnabled(true);
			break;
		case ISubstitution.BEHAVIOUR:
			this.playerLabel.setText(HOVerwaltung.instance().getLanguageString("subs.Player"));
			this.playerInLabel.setText(HOVerwaltung.instance().getLanguageString("subs.In"));
			this.newPositionLabel.setEnabled(true);
			break;
		case ISubstitution.POSITION_SWAP:
			this.playerLabel.setText(HOVerwaltung.instance().getLanguageString("subs.Reposition"));
			this.playerInLabel.setText(HOVerwaltung.instance().getLanguageString("subs.RepositionWith"));
			this.newPositionLabel.setEnabled(false);
			break;
		default:
			this.playerLabel.setText(HOVerwaltung.instance().getLanguageString("subs.Out"));
			this.playerInLabel.setText(HOVerwaltung.instance().getLanguageString("subs.In"));
			this.newPositionLabel.setEnabled(true);
		}
	}

	private void initComponents() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		// Order type
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(10, 10, 2, 2);
		gbc.anchor = GridBagConstraints.WEST;
		add(new JLabel(HOVerwaltung.instance().getLanguageString("subs.Order")), gbc);

		this.orderTypeEntry = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
				ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);
		gbc.gridx = 1;
		gbc.insets = new Insets(10, 2, 2, 10);
		Component component = this.orderTypeEntry.getComponent(false);
		component.setPreferredSize(COMPONENTENSIZE);
		add(component, gbc);

		// Player (Out/Reposition)
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.insets = new Insets(2, 10, 2, 2);
		this.playerLabel = new JLabel(HOVerwaltung.instance().getLanguageString("subs.Out"));
		add(this.playerLabel, gbc);

		this.playerOutEntry = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
				ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);
		gbc.gridx = 1;
		gbc.insets = new Insets(2, 2, 2, 10);
		component = this.playerOutEntry.getComponent(false);
		component.setPreferredSize(COMPONENTENSIZE);
		add(component, gbc);

		// Player (In/With)
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.insets = new Insets(2, 10, 2, 2);
		this.playerInLabel = new JLabel(HOVerwaltung.instance().getLanguageString("subs.In"));
		add(this.playerInLabel, gbc);

		this.playerInEntry = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
				ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);
		gbc.gridx = 1;
		gbc.insets = new Insets(2, 2, 2, 10);
		component = this.playerInEntry.getComponent(false);
		component.setPreferredSize(COMPONENTENSIZE);
		add(component, gbc);

		// When
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.insets = new Insets(2, 10, 2, 2);
		add(new JLabel(HOVerwaltung.instance().getLanguageString("subs.When")), gbc);

		this.whenEntry = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD, ColorLabelEntry.BG_STANDARD,
				SwingConstants.LEFT);
		gbc.gridx = 1;
		gbc.insets = new Insets(2, 2, 2, 10);
		component = this.whenEntry.getComponent(false);
		component.setPreferredSize(COMPONENTENSIZE);
		add(component, gbc);

		// New position
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.insets = new Insets(2, 10, 2, 2);
		this.newPositionLabel = new JLabel(HOVerwaltung.instance().getLanguageString("subs.Position"));
		add(this.newPositionLabel, gbc);

		this.newPositionEntry = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
				ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);
		gbc.gridx = 1;
		gbc.insets = new Insets(2, 2, 2, 10);
		component = this.newPositionEntry.getComponent(false);
		component.setPreferredSize(COMPONENTENSIZE);
		add(component, gbc);

		// Red Cards
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.insets = new Insets(2, 10, 2, 2);
		add(new JLabel(HOVerwaltung.instance().getLanguageString("subs.RedCard")), gbc);

		this.redCardsEntry = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
				ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);
		gbc.gridx = 1;
		gbc.insets = new Insets(2, 2, 2, 10);
		component = this.redCardsEntry.getComponent(false);
		component.setPreferredSize(COMPONENTENSIZE);
		add(component, gbc);

		// Standing
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.insets = new Insets(2, 10, 2, 2);
		add(new JLabel(HOVerwaltung.instance().getLanguageString("subs.Standing")), gbc);

		this.standingEntry = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
				ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);
		gbc.gridx = 1;
		gbc.insets = new Insets(2, 2, 2, 10);
		component = this.standingEntry.getComponent(false);
		component.setPreferredSize(COMPONENTENSIZE);
		add(component, gbc);

		// dummy label to consume all extra space
		JLabel dummy = new JLabel("");
		gbc.gridx = 2;
		gbc.gridy++;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		add(dummy, gbc);
	}
}
