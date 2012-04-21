// %3280892954:de.hattrickorganizer.gui.lineup%
package ho.module.lineup2;

import ho.core.constants.TeamConfidence;
import ho.core.constants.TeamSpirit;
import ho.core.constants.player.PlayerAbility;
import ho.core.datatype.CBItem;
import ho.core.gui.Refreshable;
import ho.core.gui.comp.entry.ColorLabelEntry;
import ho.core.gui.comp.entry.RatingTableEntry;
import ho.core.gui.model.AufstellungCBItem;
import ho.core.model.HOModel;
import ho.core.model.HOVerwaltung;
import ho.core.model.match.IMatchDetails;
import ho.core.model.match.Matchdetails;
import ho.core.model.player.Spieler;
import ho.core.rating.RatingPredictionConfig;
import ho.core.util.Helper;
import ho.module.lineup.AufstellungsVergleichHistoryPanel;
import ho.module.lineup.Lineup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Create the lineup detail panel.
 */
final class AufstellungsDetailPanel extends JPanel implements Refreshable, ItemListener {
	// ~ Instance fields
	// ----------------------------------------------------------------------------

	private static final long serialVersionUID = -2077901764599789950L;

	private ColorLabelEntry m_jpAktuellesSystem = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
			ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE, SwingConstants.LEFT);
	private ColorLabelEntry m_jpDurchschnittErfahrung = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
			ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE, SwingConstants.LEFT);

	private ColorLabelEntry m_jpErfahrungAktuellesSystem = new ColorLabelEntry("",
			ColorLabelEntry.FG_STANDARD, ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE, SwingConstants.CENTER);
	private ColorLabelEntry m_jpGesamtStaerkeText = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
			ColorLabelEntry.BG_STANDARD, SwingConstants.RIGHT);
	private ColorLabelEntry m_jpHatstat = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
			ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE, SwingConstants.CENTER);
	private ColorLabelEntry m_jpLoddarstat = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
			ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE, SwingConstants.CENTER);
	private ColorLabelEntry m_jpTaktikStaerke = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
			ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE, SwingConstants.LEFT);
	private RatingTableEntry m_jpGesamtStaerke = new RatingTableEntry();
	private CBItem[] EINSTELLUNG = {
			new CBItem(HOVerwaltung.instance().getLanguageString("PIC"), IMatchDetails.EINSTELLUNG_PIC),
			new CBItem(HOVerwaltung.instance().getLanguageString("Normal"), IMatchDetails.EINSTELLUNG_NORMAL),
			new CBItem(HOVerwaltung.instance().getLanguageString("MOTS"), IMatchDetails.EINSTELLUNG_MOTS) };
	private JComboBox m_jcbEinstellung = new JComboBox(EINSTELLUNG);
	private JComboBox m_jcbSelbstvertrauen = new JComboBox(TeamConfidence.ITEMS);

	private CBItem[] PREDICTIONTYPE = getPredictionItems();
	private JComboBox m_jcbPredictionType = new JComboBox(PREDICTIONTYPE);

	private JComboBox m_jcbMainStimmung = new JComboBox(TeamSpirit.ITEMS);
	private CBItem[] SUBSTIMM = { new CBItem(HOVerwaltung.instance().getLanguageString("verylow"), 0),
			new CBItem(HOVerwaltung.instance().getLanguageString("low"), 1),
			new CBItem(HOVerwaltung.instance().getLanguageString("Durchschnitt"), 2),
			new CBItem(HOVerwaltung.instance().getLanguageString("high"), 3),
			new CBItem(HOVerwaltung.instance().getLanguageString("veryhigh"), 4) };
	private JComboBox m_jcbSubStimmung = new JComboBox(SUBSTIMM);
	private CBItem[] TAKTIK = {
			new CBItem(Matchdetails.getNameForTaktik(IMatchDetails.TAKTIK_NORMAL),
					IMatchDetails.TAKTIK_NORMAL),
			new CBItem(Matchdetails.getNameForTaktik(IMatchDetails.TAKTIK_PRESSING),
					IMatchDetails.TAKTIK_PRESSING),
			new CBItem(Matchdetails.getNameForTaktik(IMatchDetails.TAKTIK_KONTER),
					IMatchDetails.TAKTIK_KONTER),
			new CBItem(Matchdetails.getNameForTaktik(IMatchDetails.TAKTIK_MIDDLE),
					IMatchDetails.TAKTIK_MIDDLE),
			new CBItem(Matchdetails.getNameForTaktik(IMatchDetails.TAKTIK_WINGS), IMatchDetails.TAKTIK_WINGS),
			new CBItem(Matchdetails.getNameForTaktik(IMatchDetails.TAKTIK_CREATIVE),
					IMatchDetails.TAKTIK_CREATIVE),
			new CBItem(Matchdetails.getNameForTaktik(IMatchDetails.TAKTIK_LONGSHOTS),
					IMatchDetails.TAKTIK_LONGSHOTS) };
	private JComboBox m_jcbTaktik = new JComboBox(TAKTIK);

	// Pull back minute
	private CBItem[] PULLBACK_MINUTE = {
			new CBItem(HOVerwaltung.instance().getLanguageString("PullBack.None"), 90), new CBItem("85", 85),
			new CBItem("80", 80), new CBItem("75", 75), new CBItem("70", 70), new CBItem("65", 65),
			new CBItem("60", 60), new CBItem("55", 55), new CBItem("50", 50), new CBItem("45", 45),
			new CBItem("40", 40), new CBItem("35", 35), new CBItem("30", 30), new CBItem("25", 25),
			new CBItem("20", 20), new CBItem("15", 15), new CBItem("10", 10), new CBItem("5", 5),
			new CBItem(HOVerwaltung.instance().getLanguageString("PullBack.WholeGame"), 0) };

	private JComboBox m_jcbPullBackMinute = new JComboBox(PULLBACK_MINUTE);

	private JCheckBox m_jchPullBackOverride = new JCheckBox(HOVerwaltung.instance().getLanguageString(
			"PullBack.Override"), false);

	// ~ Constructors
	// -------------------------------------------------------------------------------

	/**
	 * Creates a new AufstellungsDetailPanel object.
	 */
	public AufstellungsDetailPanel() {
		initComponents();
		ho.core.gui.RefreshManager.instance().registerRefreshable(this);
	}

	// ~ Methods
	// ------------------------------------------------------------------------------------

	/**
	 * Set the constant for Pic/Mots/Normal.
	 * 
	 * @param einstellung
	 *            the constant for Pic/Mots/Normal
	 */
	public void setEinstellung(int einstellung) {
		Helper.markierenComboBox(m_jcbEinstellung, einstellung);
	}

	/**
	 * Get the constant for Pic/Mots/Normal.
	 * 
	 * @return the constant for Pic/Mots/Normal
	 */
	public int getEinstellung() {
		return ((CBItem) m_jcbEinstellung.getSelectedItem()).getId();
	}

	private void setLabels() {
		if (HOVerwaltung.instance().getModel().getTeam() != null) {
			final HOModel homodel = HOVerwaltung.instance().getModel();
			final Vector<Spieler> allSpieler = homodel.getAllSpieler();
			final Lineup aufstellung = homodel.getAufstellung();

			// AufstellungCBItem avergleich =
			// AufstellungsVergleichHistoryPanel.getVergleichsAufstellung ();
			// HRF-Vergleich gefordert
			if (AufstellungsVergleichHistoryPanel.isVergleichgefordert()) {
				// Erst die Werte auf die der geladenen Aufstellung setzen
				final AufstellungCBItem vergleichsaufstellungcbitem = AufstellungsVergleichHistoryPanel
						.getVergleichsAufstellung();

				if (vergleichsaufstellungcbitem != null) {
					final Lineup vergleichsaufstellung = vergleichsaufstellungcbitem.getAufstellung();

					if (vergleichsaufstellung != null) {
						// Wegen der Berechnung zuerst die Aufstellung kurz in
						// Model packen, da immer die aktuelle Aufstellung
						// genommen wird
						// vergleichsaufstellung.updateRatingPredictionConfig();
						homodel.setAufstellung(vergleichsaufstellung);
						// m_jpRating.setTopRight(vergleichsaufstellung.getLeftDefenseRating());
						// m_jpRating.setTopCenter(vergleichsaufstellung.getCentralDefenseRating());
						// m_jpRating.setTopLeft(vergleichsaufstellung.getRightDefenseRating());
						// m_jpRating.setMiddle(vergleichsaufstellung.getMidfieldRating());
						// m_jpRating.setBottomRight(vergleichsaufstellung.getLeftAttackRating());
						// m_jpRating.setBottomCenter(vergleichsaufstellung.getCentralAttackRating());
						// m_jpRating.setBottomLeft(vergleichsaufstellung.getRightAttackRating());

						// Wieder die richtige Aufstellung setzen
						homodel.setAufstellung(aufstellung);
					}
				}
			}

			// Erst mal leeren
			// aufstellung.updateRatingPredictionConfig();
			// m_jpRating.clear();
			// m_jpRating.setTopRightText(PlayerHelper.getNameForSkill(
			// (aufstellung.getIntValue4Rating(aufstellung.getLeftDefenseRating())),
			// false, true));
			// m_jpRating.setTopCenterText(PlayerHelper.getNameForSkill(
			// (aufstellung.getIntValue4Rating(aufstellung.getCentralDefenseRating())),
			// false, true));
			// m_jpRating.setTopLeftText(PlayerHelper.getNameForSkill(
			// (aufstellung.getIntValue4Rating(aufstellung.getRightDefenseRating())),
			// false, true));
			// m_jpRating.setMiddleText(PlayerHelper.getNameForSkill(
			// (aufstellung.getIntValue4Rating(aufstellung.getMidfieldRating())),
			// false, true));
			// m_jpRating.setBottomRightText(PlayerHelper.getNameForSkill(
			// (aufstellung.getIntValue4Rating(aufstellung.getLeftAttackRating())),
			// false, true));
			// m_jpRating.setBottomCenterText(PlayerHelper.getNameForSkill(
			// (aufstellung.getIntValue4Rating(aufstellung.getCentralAttackRating())),
			// false, true));
			// m_jpRating.setBottomLeftText(PlayerHelper.getNameForSkill(
			// (aufstellung.getIntValue4Rating(aufstellung.getRightAttackRating())),
			// false, true));
			// m_jpRating.setTopRight(aufstellung.getLeftDefenseRating());
			// m_jpRating.setTopCenter(aufstellung.getCentralDefenseRating());
			// m_jpRating.setTopLeft(aufstellung.getRightDefenseRating());
			// m_jpRating.setMiddle(aufstellung.getMidfieldRating());
			// m_jpRating.setBottomRight(aufstellung.getLeftAttackRating());
			// m_jpRating.setBottomCenter(aufstellung.getCentralAttackRating());
			// m_jpRating.setBottomLeft(aufstellung.getRightAttackRating());

			// Farben neu berechnen
			// m_jpRating.calcColorBorders();

			final double gesamtstaerke = aufstellung.getGesamtStaerke(allSpieler, true);

			// *2 wegen halben Sternen
			m_jpGesamtStaerke.setRating((int) (gesamtstaerke * 2));
			m_jpGesamtStaerkeText.setText(Helper.DEFAULTDEZIMALFORMAT.format(gesamtstaerke));
			m_jpLoddarstat.setText(Helper.round(aufstellung.getLoddarStats(), 2) + "");
			m_jpHatstat.setText(aufstellung.getHATStats() + "");

			setStimmung(homodel.getTeam().getStimmungAsInt(), homodel.getTeam().getSubStimmung());
			setSelbstvertrauen(homodel.getTeam().getSelbstvertrauenAsInt());
			setPredictionType(RatingPredictionConfig.getInstancePredictionType());

			setTaktik(aufstellung.getTacticType());
			m_jpTaktikStaerke.setText(getTaktikString());

			setEinstellung(aufstellung.getAttitude());
			setPullBackMinute(aufstellung.getPullBackMinute());
			m_jcbPullBackMinute.setEnabled(!aufstellung.isPullBackOverride());
			setPullBackOverride(aufstellung.isPullBackOverride());

			// m_jpPredictionType.setText(RatingPredictionConfig.getInstancePredictionName());

			float avXp = homodel.getAufstellung().getAverageExperience();
			m_jpDurchschnittErfahrung.setText(PlayerAbility.getNameForSkill(avXp));
			m_jpDurchschnittErfahrung.setToolTipText((avXp < 0 ? "Need to set team captain!"
					: "TeamXP formula by kopsterkespits")); // TODO L10N
			m_jpAktuellesSystem.setText(Lineup.getNameForSystem(aufstellung.ermittelSystem()));
			// m_jpAktuellesSystem.setText(Aufstellung.getNameForSystem(aufstellung.ermittelSystem()));
			m_jpErfahrungAktuellesSystem.setText(homodel.getAufstellung().getTeamErfahrung4AktuellesSystem()
					+ "");

			m_jpErfahrungAktuellesSystem.setFGColor(new Color(
					Math.min(Math.max(
							((8 - homodel.getAufstellung().getTeamErfahrung4AktuellesSystem()) * 32) - 1, 0),
							255), 0, 0));
		}
	}

	/**
	 * Set the team confidence.
	 * 
	 * @param selbstvertrauen
	 *            the confidence value
	 */
	public void setSelbstvertrauen(int selbstvertrauen) {
		Helper.markierenComboBox(m_jcbSelbstvertrauen, selbstvertrauen);
	}

	/**
	 * Set the team spirit values.
	 * 
	 * @param stimmung
	 *            team spirit
	 * @param subStimmung
	 *            subskill of the team spirit
	 */
	public void setStimmung(int stimmung, int subStimmung) {
		Helper.markierenComboBox(m_jcbMainStimmung, stimmung);
		Helper.markierenComboBox(m_jcbSubStimmung, subStimmung);
	}

	/**
	 * Set the prediction type.
	 */
	public void setPredictionType(int newPredictionType) {
		ho.core.util.Helper.markierenComboBox(m_jcbPredictionType, newPredictionType);
	}

	/**
	 * Set the tactic using it's constant.
	 * 
	 * @param taktik
	 *            the tactic constant
	 */
	public void setTaktik(int taktik) {
		Helper.markierenComboBox(m_jcbTaktik, taktik);
	}

	/**
	 * Get the tactic constant.
	 * 
	 * @return get the tactic constant
	 */
	public int getTaktik() {
		return ((CBItem) m_jcbTaktik.getSelectedItem()).getId();
	}

	/**
	 * Set the pullback minute
	 * 
	 * @param minute
	 */
	public void setPullBackMinute(int minute) {
		Helper.markierenComboBox(m_jcbPullBackMinute, minute);
	}

	/**
	 * Get the pullback minute
	 * 
	 * @return get the pullback minute
	 */
	public int getPullBackMinute() {
		return ((CBItem) m_jcbPullBackMinute.getSelectedItem()).getId();
	}

	/**
	 * Sets the pullback override flag.
	 * 
	 * @param pullBackOverride
	 */
	private void setPullBackOverride(boolean pullBackOverride) {
		m_jchPullBackOverride.setSelected(pullBackOverride);
	}

	/**
	 * React on state changed events
	 * 
	 * @param event
	 *            the event
	 */
	public void itemStateChanged(ItemEvent event) {

		if (event.getStateChange() == ItemEvent.DESELECTED) {
			if (event.getSource().equals(m_jchPullBackOverride)) {
				HOVerwaltung.instance().getModel().getAufstellung().setPullBackOverride(false);
				m_jcbPullBackMinute.setEnabled(true);
				refresh();
			}

		} else if (event.getStateChange() == ItemEvent.SELECTED) {
			if (event.getSource().equals(m_jchPullBackOverride)) {
				HOVerwaltung.instance().getModel().getAufstellung().setPullBackOverride(true);
				m_jcbPullBackMinute.setEnabled(false);
			} else if (event.getSource().equals(m_jcbPullBackMinute)) {
				// Pull Back minute changed
				HOVerwaltung.instance().getModel().getAufstellung()
						.setPullBackMinute(((CBItem) m_jcbPullBackMinute.getSelectedItem()).getId());
			} else if (event.getSource().equals(m_jcbTaktik)) {
				// Tactic changed
				HOVerwaltung.instance().getModel().getAufstellung()
						.setTacticType(((CBItem) m_jcbTaktik.getSelectedItem()).getId());
			} else if (event.getSource().equals(m_jcbEinstellung)) {
				// Attitude changed
				HOVerwaltung.instance().getModel().getAufstellung()
						.setAttitude(((CBItem) m_jcbEinstellung.getSelectedItem()).getId());
			} else if (event.getSource().equals(m_jcbMainStimmung)) {
				// team spirit changed
				HOVerwaltung.instance().getModel().getTeam()
						.setStimmungAsInt(((CBItem) m_jcbMainStimmung.getSelectedItem()).getId());
			} else if (event.getSource().equals(m_jcbSubStimmung)) {
				// team spirit (sub) changed
				HOVerwaltung.instance().getModel().getTeam()
						.setSubStimmung(((CBItem) m_jcbSubStimmung.getSelectedItem()).getId());
			} else if (event.getSource().equals(m_jcbSelbstvertrauen)) {
				// team confidence changed
				HOVerwaltung.instance().getModel().getTeam()
						.setSelbstvertrauenAsInt(((CBItem) m_jcbSelbstvertrauen.getSelectedItem()).getId());
			} else if (event.getSource().equals(m_jcbPredictionType)) {
				// prediction type changed
				RatingPredictionConfig.setInstancePredictionType(((CBItem) m_jcbPredictionType
						.getSelectedItem()).getId());
			}
			refresh();
		}
	}

	/**
	 * Reinit the GUI:
	 */
	public void reInit() {
		setLabels();
	}

	/**
	 * Refresh the GUI.
	 */
	public void refresh() {
		removeItemListeners();
		setLabels();
		addItemListeners();
	}

	/**
	 * Get the i18n'ed name of the tactic together with it's strength.
	 * 
	 * @return the name of the tactic incl. strength
	 */
	private String getTaktikString() {
		final Lineup aufstellung = HOVerwaltung.instance().getModel().getAufstellung();

		// getTaktik liefert Taktik aus ComboBox, wir wollen Taktik aus
		// aufstellung!
		// switch (getTaktik()) {
		switch (aufstellung.getTacticType()) {
		case IMatchDetails.TAKTIK_NORMAL:
			// Play creatively has no tactic level
		case IMatchDetails.TAKTIK_CREATIVE:
			return HOVerwaltung.instance().getLanguageString("Normal");

		case IMatchDetails.TAKTIK_PRESSING:
		case IMatchDetails.TAKTIK_KONTER:
		case IMatchDetails.TAKTIK_MIDDLE:
		case IMatchDetails.TAKTIK_WINGS:
		case IMatchDetails.TAKTIK_LONGSHOTS:
			return PlayerAbility.getNameForSkill(aufstellung.getTacticLevel(getTaktik()));

		default:
			return HOVerwaltung.instance().getLanguageString("Unbestimmt");
		}
	}

	/**
	 * Initialize the GUI and layout components.
	 */
	private void initComponents() {
		final GridBagLayout layout = new GridBagLayout();
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;

		setLayout(layout);

		JLabel label;
		JPanel panel;

		int yPos = 1;

		constraints.gridx = 1;
		constraints.gridy = yPos;
		constraints.gridwidth = 2;
		// layout.setConstraints(m_jpRating, constraints);
		// add(m_jpRating, constraints);

		yPos++;
		panel = new JPanel(new BorderLayout());
		panel.setOpaque(true);
		m_jpGesamtStaerke.setToolTipText(HOVerwaltung.instance().getLanguageString("Gesamtstaerke"));
		panel.add(m_jpGesamtStaerke.getComponent(false), BorderLayout.CENTER);
		m_jpGesamtStaerkeText.setFontStyle(Font.BOLD);
		m_jpGesamtStaerkeText.setToolTipText(HOVerwaltung.instance().getLanguageString("Gesamtstaerke"));
		panel.add(m_jpGesamtStaerkeText.getComponent(false), BorderLayout.EAST);
		constraints.gridx = 1;
		constraints.gridy = yPos;
		constraints.gridwidth = 2;
		constraints.weighty = 0.0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		layout.setConstraints(panel, constraints);
		add(panel);

		yPos++;
		constraints.gridx = 1;
		constraints.gridy = yPos;
		constraints.gridwidth = 2;
		m_jcbEinstellung.setToolTipText(HOVerwaltung.instance().getLanguageString(
				"tt_AufstellungsDetails_Einstellung"));
		layout.setConstraints(m_jcbEinstellung, constraints);
		add(m_jcbEinstellung);

		yPos++;
		constraints.gridx = 1;
		constraints.gridy = yPos;
		constraints.gridwidth = 2;
		m_jcbTaktik
				.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_AufstellungsDetails_Taktik"));
		layout.setConstraints(m_jcbTaktik, constraints);
		add(m_jcbTaktik);

		yPos++;
		constraints.gridx = 1;
		constraints.gridy = yPos;
		constraints.gridwidth = 1;

		// model.HOVerwaltung.instance().getLanguageString( "HATStat" ) );
		label = new JLabel("HatStats");
		layout.setConstraints(label, constraints);
		add(label);
		constraints.gridx = 2;
		constraints.gridy = yPos;

		// m_jpHatstat.setToolTipText(
		// model.HOVerwaltung.instance().getLanguageString(
		// "tt_AufstellungsDetails_HatStat" ) );
		layout.setConstraints(m_jpHatstat.getComponent(false), constraints);
		add(m_jpHatstat.getComponent(false));

		yPos++;
		initLabel(constraints, layout, new JLabel("Loddar Stats"), yPos);
		constraints.gridx = 2;
		constraints.gridy = yPos;

		// m_jpHatstat.setToolTipText(
		// model.HOVerwaltung.instance().getLanguageString(
		// "tt_AufstellungsDetails_LoddarStat" ) );
		layout.setConstraints(m_jpLoddarstat.getComponent(false), constraints);
		add(m_jpLoddarstat.getComponent(false));

		yPos++;
		constraints.gridx = 1;
		constraints.gridy = yPos;
		constraints.gridwidth = 1;
		label = new JLabel(HOVerwaltung.instance().getLanguageString("Taktikstaerke"));
		layout.setConstraints(label, constraints);
		add(label);
		constraints.gridx = 2;
		constraints.gridy = yPos;
		constraints.weightx = 1.0;
		layout.setConstraints(m_jpTaktikStaerke.getComponent(false), constraints);
		add(m_jpTaktikStaerke.getComponent(false));

		yPos++;
		initLabel(constraints, layout, new JLabel(HOVerwaltung.instance().getLanguageString("Stimmung")),
				yPos);
		constraints.gridx = 2;
		constraints.gridy = yPos;
		m_jcbMainStimmung.setMaximumRowCount(13);
		layout.setConstraints(m_jcbMainStimmung, constraints);
		add(m_jcbMainStimmung);

		yPos++;
		initLabel(constraints, layout,
				new JLabel("Sub" + HOVerwaltung.instance().getLanguageString("Stimmung")), yPos);
		constraints.gridx = 2;
		constraints.gridy = yPos;
		m_jcbSubStimmung.setMaximumRowCount(5);
		layout.setConstraints(m_jcbSubStimmung, constraints);
		add(m_jcbSubStimmung);

		yPos++;
		initLabel(constraints, layout, new JLabel(HOVerwaltung.instance()
				.getLanguageString("Selbstvertrauen")), yPos);
		constraints.gridx = 2;
		constraints.gridy = yPos;
		m_jcbSelbstvertrauen.setMaximumRowCount(10);
		layout.setConstraints(m_jcbSelbstvertrauen, constraints);
		add(m_jcbSelbstvertrauen);

		yPos++;
		initLabel(constraints, layout,
				new JLabel(HOVerwaltung.instance().getLanguageString("PullBack.PullBackStartMinute")), yPos);
		constraints.gridx = 2;
		constraints.gridy = yPos;
		constraints.gridwidth = 1;
		m_jcbPullBackMinute.setToolTipText(HOVerwaltung.instance().getLanguageString(
				"PullBack.PullBackStartMinute.ToolTip"));
		m_jcbPullBackMinute.setOpaque(false);
		layout.setConstraints(m_jcbPullBackMinute, constraints);
		add(m_jcbPullBackMinute);

		yPos++;
		initLabel(constraints, layout, new JLabel(""), yPos);
		constraints.gridx = 2;
		constraints.gridy = yPos;
		m_jchPullBackOverride.setToolTipText(HOVerwaltung.instance().getLanguageString(
				"PullBack.Override.ToolTip"));
		m_jchPullBackOverride.setOpaque(false);
		layout.setConstraints(m_jchPullBackOverride, constraints);
		add(m_jchPullBackOverride);

		yPos++;
		initLabel(constraints, layout,
				new JLabel(HOVerwaltung.instance().getLanguageString("PredictionType")), yPos);
		constraints.gridx = 2;
		constraints.gridy = yPos;
		// m_jcbPredictionType.setMaximumRowCount(3);
		layout.setConstraints(m_jcbPredictionType, constraints);
		add(m_jcbPredictionType);

		yPos++;
		initLabel(constraints, layout, new JLabel(HOVerwaltung.instance().getLanguageString("Erfahrung")),
				yPos);
		constraints.gridx = 2;
		constraints.gridy = yPos;
		layout.setConstraints(m_jpDurchschnittErfahrung.getComponent(false), constraints);
		add(m_jpDurchschnittErfahrung.getComponent(false));

		yPos++;
		initLabel(constraints, layout, new JLabel(HOVerwaltung.instance()
				.getLanguageString("AktuellesSystem")), yPos);
		constraints.gridx = 2;
		constraints.gridy = yPos;
		layout.setConstraints(m_jpAktuellesSystem.getComponent(false), constraints);
		add(m_jpAktuellesSystem.getComponent(false));

        yPos++;
        initLabel(constraints,layout,new JLabel(HOVerwaltung.instance().getLanguageString("ErfahrungAktuellesSys")), yPos);
        constraints.gridx = 2;
        constraints.gridy = yPos;
        layout.setConstraints(m_jpErfahrungAktuellesSystem.getComponent(false), constraints);
        add(m_jpErfahrungAktuellesSystem.getComponent(false));

		// Add all item listeners
		addItemListeners();

//		JButton b = new JButton("dlg");
//		add(b);
//		b.addActionListener(new ActionListener() {
//
//			public void actionPerformed(ActionEvent e) {
//				FormationExperienceDialog dlg = new FormationExperienceDialog();
//				dlg.setModal(true);
//				dlg.setVisible(true);
//			}
//		});
	}

	/**
	 * Add all item listeners to the combo boxes
	 */
	private void addItemListeners() {
		m_jcbEinstellung.addItemListener(this);
		m_jcbTaktik.addItemListener(this);
		m_jcbMainStimmung.addItemListener(this);
		m_jcbSubStimmung.addItemListener(this);
		m_jcbSelbstvertrauen.addItemListener(this);
		m_jcbPredictionType.addItemListener(this);
		m_jcbPullBackMinute.addItemListener(this);
		m_jchPullBackOverride.addItemListener(this);
	}

	/**
	 * Remove all item listeners from the combo boxes
	 */
	private void removeItemListeners() {
		m_jcbEinstellung.removeItemListener(this);
		m_jcbTaktik.removeItemListener(this);
		m_jcbMainStimmung.removeItemListener(this);
		m_jcbSubStimmung.removeItemListener(this);
		m_jcbSelbstvertrauen.removeItemListener(this);
		m_jcbPredictionType.removeItemListener(this);
		m_jcbPullBackMinute.removeItemListener(this);
		m_jchPullBackOverride.removeItemListener(this);
	}

	private CBItem[] getPredictionItems() {
		final Properties properties = HOVerwaltung.instance().getResource();
		String[] allPredictionNames = RatingPredictionConfig.getAllPredictionNames();
		CBItem[] allItems = new CBItem[allPredictionNames.length];
		for (int i = 0; i < allItems.length; i++) {
			String predictionName = allPredictionNames[i];
			if (properties.containsKey("prediction." + predictionName))
				predictionName = HOVerwaltung.instance().getLanguageString("prediction." + predictionName);
			allItems[i] = new CBItem(predictionName, i);
		}
		return allItems;
	}

	private void initLabel(GridBagConstraints constraints, GridBagLayout layout, JLabel label, int y) {
		constraints.gridx = 1;
		constraints.gridy = y;
		layout.setConstraints(label, constraints);
		add(label);
	}
}
