package ho.module.lineup.substitution;

import ho.core.gui.comp.entry.ColorLabelEntry;
import ho.core.gui.theme.HOColorName;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.HOModel;
import ho.core.model.HOVerwaltung;
import ho.core.model.player.Spieler;
import ho.core.util.Helper;
import ho.module.lineup.substitution.model.GoalDiffCriteria;
import ho.module.lineup.substitution.model.MatchOrderType;
import ho.module.lineup.substitution.model.RedCardCriteria;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.MessageFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class DetailsView extends JPanel {

	private static final Dimension COMPONENTENSIZE = new Dimension(Helper.calcCellWidth(300),
			Helper.calcCellWidth(18));
	private static final long serialVersionUID = -8046083206070885556L;
	private Substitution substitution;
	private ColorLabelEntry orderTypeEntry;
	private ColorLabelEntry firstPlayerEntry;
	private ColorLabelEntry secondPlayerEntry;
	private ColorLabelEntry whenEntry;
	private ColorLabelEntry newBehaviourEntry;
	private ColorLabelEntry newPositionEntry;
	private ColorLabelEntry standingEntry;
	private ColorLabelEntry redCardsEntry;
	private JLabel playerLabel;
	private JLabel playerInLabel;

	public DetailsView() {
		initComponents();
	}

	public void setSubstitution(Substitution sub) {
		this.substitution = sub;
		refresh();
	}

	public void refresh() {
		updateData();
		updateView();
	}

	private void updateView() {
		if (this.substitution != null) {
			String color = (this.substitution.getPlayerOut() != -1) ? HOColorName.SUBST_CHANGED_VALUE_BG
					: HOColorName.TABLEENTRY_BG;
			this.firstPlayerEntry.setBGColor(ThemeManager.getColor(color));

			color = (this.substitution.getPlayerIn() != -1 && this.substitution.getOrderType() != MatchOrderType.NEW_BEHAVIOUR) ? HOColorName.SUBST_CHANGED_VALUE_BG
					: HOColorName.TABLEENTRY_BG;
			this.secondPlayerEntry.setBGColor(ThemeManager.getColor(color));

			color = (this.substitution.getMatchMinuteCriteria() > 0) ? HOColorName.SUBST_CHANGED_VALUE_BG
					: HOColorName.TABLEENTRY_BG;
			this.whenEntry.setBGColor(ThemeManager.getColor(color));

			color = (this.substitution.getPos() != -1) ? HOColorName.SUBST_CHANGED_VALUE_BG
					: HOColorName.TABLEENTRY_BG;
			this.newPositionEntry.setBGColor(ThemeManager.getColor(color));

			color = (this.substitution.getBehaviour() != -1) ? HOColorName.SUBST_CHANGED_VALUE_BG
					: HOColorName.TABLEENTRY_BG;
			this.newBehaviourEntry.setBGColor(ThemeManager.getColor(color));

			color = (this.substitution.getRedCardCriteria() != RedCardCriteria.IGNORE) ? HOColorName.SUBST_CHANGED_VALUE_BG
					: HOColorName.TABLEENTRY_BG;
			this.redCardsEntry.setBGColor(ThemeManager.getColor(color));

			color = (this.substitution.getStanding() != GoalDiffCriteria.ANY_STANDING) ? HOColorName.SUBST_CHANGED_VALUE_BG
					: HOColorName.TABLEENTRY_BG;
			this.standingEntry.setBGColor(ThemeManager.getColor(color));

			switch (this.substitution.getOrderType()) {
			case SUBSTITUTION:
				this.playerLabel.setText(HOVerwaltung.instance().getLanguageString("subs.Out"));
				this.playerInLabel.setText(HOVerwaltung.instance().getLanguageString("subs.In"));
				break;
			case NEW_BEHAVIOUR:
				this.playerLabel.setText(HOVerwaltung.instance().getLanguageString("subs.Player"));
				this.playerInLabel.setText("");
				break;
			case POSITION_SWAP:
				this.playerLabel.setText(HOVerwaltung.instance().getLanguageString(
						"subs.Reposition"));
				this.playerInLabel.setText(HOVerwaltung.instance().getLanguageString(
						"subs.RepositionWith"));
				break;
			}
		} else {
			this.playerLabel.setText(HOVerwaltung.instance().getLanguageString("subs.Out"));
			this.playerInLabel.setText(HOVerwaltung.instance().getLanguageString("subs.In"));

			Color color = ThemeManager.getColor(HOColorName.TABLEENTRY_BG);
			this.firstPlayerEntry.setBGColor(color);
			this.secondPlayerEntry.setBGColor(color);
			this.whenEntry.setBGColor(color);
			this.newPositionEntry.setBGColor(color);
			this.newBehaviourEntry.setBGColor(color);
			this.redCardsEntry.setBGColor(color);
			this.standingEntry.setBGColor(color);
		}
	}

	private void updateData() {
		String orderType = "";
		String playerIn = "";
		String playerOut = "";
		String when = "";
		String newBehaviour = "";
		String newPosition = "";
		String standing = "";
		String redCards = "";

		if (this.substitution != null) {
			HOModel hoModel = HOVerwaltung.instance().getModel();
			orderType = Lookup.getOrderType(this.substitution.getOrderType());

			Spieler out = hoModel.getSpieler(this.substitution.getPlayerOut());
			playerOut = (out != null) ? out.getName() : "";
			if (this.substitution.getPlayerOut() != this.substitution.getPlayerIn()) {
				Spieler in = hoModel.getSpieler(this.substitution.getPlayerIn());
				playerIn = (in != null) ? in.getName() : "";
			}

			if (this.substitution.getMatchMinuteCriteria() > 0) {
				when = MessageFormat.format(
						HOVerwaltung.instance().getLanguageString("subs.MinuteAfterX"),
						Integer.valueOf(this.substitution.getMatchMinuteCriteria()));
			} else {
				when = HOVerwaltung.instance().getLanguageString("subs.MinuteAnytime");
			}

			if (this.substitution.getPos() != -1) {
				newPosition = Lookup.getPosition(this.substitution.getPos());
			}

			newBehaviour = Lookup.getBehaviour(this.substitution.getBehaviour());
			redCards = Lookup.getRedCard(this.substitution.getRedCardCriteria());
			standing = Lookup.getStanding(this.substitution.getStanding());
		}
		this.orderTypeEntry.setText(orderType);
		this.firstPlayerEntry.setText(playerOut);
		this.secondPlayerEntry.setText(playerIn);
		this.whenEntry.setText(when);
		this.newBehaviourEntry.setText(newBehaviour);
		this.newPositionEntry.setText(newPosition);
		this.redCardsEntry.setText(redCards);
		this.standingEntry.setText(standing);
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
		this.orderTypeEntry.setBold(true);
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

		this.firstPlayerEntry = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
				ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);
		gbc.gridx = 1;
		gbc.insets = new Insets(2, 2, 2, 10);
		component = this.firstPlayerEntry.getComponent(false);
		component.setPreferredSize(COMPONENTENSIZE);
		add(component, gbc);

		// Player (In/With)
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.insets = new Insets(2, 10, 2, 2);
		this.playerInLabel = new JLabel(HOVerwaltung.instance().getLanguageString("subs.In"));
		add(this.playerInLabel, gbc);

		this.secondPlayerEntry = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
				ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);
		gbc.gridx = 1;
		gbc.insets = new Insets(2, 2, 2, 10);
		component = this.secondPlayerEntry.getComponent(false);
		component.setPreferredSize(COMPONENTENSIZE);
		add(component, gbc);

		// When
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.insets = new Insets(2, 10, 2, 2);
		add(new JLabel(HOVerwaltung.instance().getLanguageString("subs.When")), gbc);

		this.whenEntry = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
				ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);
		gbc.gridx = 1;
		gbc.insets = new Insets(2, 2, 2, 10);
		component = this.whenEntry.getComponent(false);
		component.setPreferredSize(COMPONENTENSIZE);
		add(component, gbc);

		// New behaviour
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.insets = new Insets(2, 10, 2, 2);
		add(new JLabel(HOVerwaltung.instance().getLanguageString("subs.Behavior")), gbc);

		this.newBehaviourEntry = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
				ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);
		gbc.gridx = 1;
		gbc.insets = new Insets(2, 2, 2, 10);
		component = this.newBehaviourEntry.getComponent(false);
		component.setPreferredSize(COMPONENTENSIZE);
		add(component, gbc);

		// New position
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.insets = new Insets(2, 10, 2, 2);
		add(new JLabel(HOVerwaltung.instance().getLanguageString("subs.Position")), gbc);

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
