// %393632151:de.hattrickorganizer.gui.matches%
package ho.module.matches;

import ho.core.datatype.CBItem;
import ho.core.db.DBManager;
import ho.core.gui.HOMainFrame;
import ho.core.gui.RefreshManager;
import ho.core.gui.Refreshable;
import ho.core.gui.comp.entry.ColorLabelEntry;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.gui.model.MatchesColumnModel;
import ho.core.gui.theme.HOColorName;
import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.HOVerwaltung;
import ho.core.model.UserParameter;
import ho.core.model.match.IMatchDetails;
import ho.core.model.match.MatchKurzInfo;
import ho.core.model.match.MatchLineupPlayer;
import ho.core.model.match.Matchdetails;
import ho.core.model.player.ISpielerPosition;
import ho.core.module.IModule;
import ho.core.net.OnlineWorker;
import ho.core.prediction.MatchEnginePanel;
import ho.core.prediction.MatchPredictionDialog;
import ho.core.prediction.engine.MatchPredictionManager;
import ho.core.prediction.engine.TeamData;
import ho.core.prediction.engine.TeamRatings;
import ho.core.util.HOLogger;
import ho.core.util.Helper;
import ho.module.lineup.Lineup;
import ho.module.matches.statistics.MatchesHighlightsTable;
import ho.module.matches.statistics.MatchesOverviewCommonPanel;
import ho.module.matches.statistics.MatchesOverviewTable;
import ho.module.teamAnalyzer.ui.RatingUtil;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

public final class SpielePanel extends ImagePanel implements MouseListener, KeyListener,
		Refreshable, ActionListener {
	private static final long serialVersionUID = -6337569355347545083L;
	private AufstellungsSternePanel m_jpAufstellungGastPanel;
	private AufstellungsSternePanel m_jpAufstellungHeimPanel;
	private JButton m_jbAufstellungUebernehmen = new JButton(
			ThemeManager.getIcon(HOIconName.GETLINEUP));
	private JButton printButton = new JButton(ThemeManager.getIcon(HOIconName.PRINTER));
	private JButton deleteButton = new JButton(ThemeManager.getIcon(HOIconName.REMOVE));
	private JButton m_jbReloadMatch = new JButton(ThemeManager.getIcon(HOIconName.RELOAD));
	private JButton m_jbSimMatch = new JButton(ThemeManager.getIcon(HOIconName.SIMULATEMATCH));
	private JComboBox m_jcbSpieleFilter;
	private JPanel linupPanel;
	private JSplitPane horizontalLeftSplitPane;
	private JSplitPane verticalSplitPane;
	private JTabbedPane matchDetailsTabbedPane;
	private ManschaftsBewertungsPanel m_jpManschaftsBewertungsPanel;
	private ManschaftsBewertungs2Panel m_jpManschaftsBewertungs2Panel;
	private MatchKurzInfo matchShortInfo;
	private MatchberichtPanel matchReportPanel;
	private SpielHighlightPanel matchHighlightPanel;
	private MatchesTable matchesTable;
	private MatchesOverviewTable matchesOverviewTable;
	private MatchesOverviewCommonPanel matchesOverviewCommonPanel;
	private MatchesHighlightsTable matchesHighlightsTable;
	private StaerkenvergleichPanel teamsComparePanel;

	private CBItem[] SPIELEFILTER = {
			new CBItem(HOVerwaltung.instance().getLanguageString("AlleSpiele"),
					SpielePanel.ALLE_SPIELE),
			new CBItem(HOVerwaltung.instance().getLanguageString("NurEigeneSpiele"),
					SpielePanel.NUR_EIGENE_SPIELE),
			new CBItem(HOVerwaltung.instance().getLanguageString("NurEigenePflichtspiele"),
					SpielePanel.NUR_EIGENE_PFLICHTSPIELE),
			new CBItem(HOVerwaltung.instance().getLanguageString("NurEigenePokalspiele"),
					SpielePanel.NUR_EIGENE_POKALSPIELE),
			new CBItem(HOVerwaltung.instance().getLanguageString("NurEigeneLigaspiele"),
					SpielePanel.NUR_EIGENE_LIGASPIELE),
			new CBItem(HOVerwaltung.instance().getLanguageString("NurEigeneFreundschaftsspiele"),
					SpielePanel.NUR_EIGENE_FREUNDSCHAFTSSPIELE),
			new CBItem(HOVerwaltung.instance().getLanguageString("NurEigeneTournamentsspiele"),
					SpielePanel.NUR_EIGENE_TOURNAMENTSPIELE),
			new CBItem(HOVerwaltung.instance().getLanguageString("NurFremdeSpiele"),
					SpielePanel.NUR_FREMDE_SPIELE) };

	/** Only played Matches of suplied team (unsupported for now) */
	public static final int NUR_GESPIELTEN_SPIELE = 10;

	/** Only tournament matches of supplied team */
	public static final int NUR_EIGENE_TOURNAMENTSPIELE = 7;

	/** Only Matches without suplied team */
	public static final int NUR_FREMDE_SPIELE = 6;

	/** Only friendly Matches of suplied team */
	public static final int NUR_EIGENE_FREUNDSCHAFTSSPIELE = 5;

	/** Only league Matches of suplied team */
	public static final int NUR_EIGENE_LIGASPIELE = 4;

	/** Only cup Matches of suplied team */
	public static final int NUR_EIGENE_POKALSPIELE = 3;

	/** Only cup +league + quali Matches of suplied team */
	public static final int NUR_EIGENE_PFLICHTSPIELE = 2;

	/** Only Matches of suplied team */
	public static final int NUR_EIGENE_SPIELE = 1;

	/** TODO Missing Parameter Documentation */
	public static final int ALLE_SPIELE = 0;

	public SpielePanel() {
		RefreshManager.instance().registerRefreshable(this);

		initComponents();
	}

	/**
	 * Gibt die aktuellen DividerLocations zurück, damit sie gespeichert werden
	 * können
	 */
	public int[] getDividerLocations() {
		final int[] locations = new int[2];
		locations[0] = horizontalLeftSplitPane.getDividerLocation();
		locations[1] = verticalSplitPane.getDividerLocation();
		return locations;
	}

	public void saveColumnOrder() {
		matchesTable.saveColumnOrder();
		matchesOverviewTable.saveColumnOrder();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == m_jbReloadMatch) {
			final int matchid = matchShortInfo.getMatchID();
			OnlineWorker.downloadMatchData(matchShortInfo.getMatchID(),
					matchShortInfo.getMatchTyp(), true);
			RefreshManager.instance().doReInit();
			showMatch(matchid);
		} else if (e.getSource() == deleteButton) {
			final int[] rows = matchesTable.getSelectedRows();
			final MatchKurzInfo[] infos = new MatchKurzInfo[rows.length];

			for (int i = 0; i < rows.length; i++) {
				infos[i] = ((MatchesColumnModel) matchesTable.getSorter().getModel())
						.getMatch((int) ((ColorLabelEntry) matchesTable.getSorter().getValueAt(
								rows[i], 5)).getZahl());
			}

			final StringBuilder text = new StringBuilder(100);
			text.append(HOVerwaltung.instance().getLanguageString("ls.button.delete"));
			if (infos.length > 1) {
				text.append(" (" + infos.length + " ");
				text.append(HOVerwaltung.instance().getLanguageString("Spiele"));
				text.append(")");
			}
			text.append(":");

			for (int i = 0; (i < infos.length) && (i < 11); i++) {
				text.append("\n" + infos[i].getHeimName() + " - " + infos[i].getGastName());
				if (i == 10) {
					text.append("\n ... ");
				}
			}

			final int value = JOptionPane.showConfirmDialog(this, text, HOVerwaltung.instance()
					.getLanguageString("ls.button.delete"), JOptionPane.YES_NO_OPTION);

			if (value == JOptionPane.YES_OPTION) {
				for (int i = 0; i < infos.length; i++) {
					DBManager.instance().deleteMatch(infos[i].getMatchID());
				}
				RefreshManager.instance().doReInit();
			}
		} else if (e.getSource() == printButton) {
			if (matchShortInfo != null) {
				final SpielePrintDialog printDialog = new SpielePrintDialog(matchShortInfo);
				printDialog.doPrint(matchShortInfo.getHeimName() + " : "
						+ matchShortInfo.getGastName() + " - " + matchShortInfo.getMatchDate());
				printDialog.setVisible(false);
				printDialog.dispose();
			}
		} else if (e.getSource() == m_jbAufstellungUebernehmen) {
			if ((matchShortInfo != null)
					&& (matchShortInfo.getMatchStatus() == MatchKurzInfo.FINISHED)) {
				final int teamid = HOVerwaltung.instance().getModel().getBasics().getTeamId();
				final Vector<MatchLineupPlayer> vteamspieler = DBManager.instance()
						.getMatchLineupPlayers(matchShortInfo.getMatchID(), teamid);
				final Lineup aufstellung = HOVerwaltung.instance().getModel().getAufstellung();

				aufstellung.clearLineup(); // To make sure the old one is gone.

				for (int i = 0; (vteamspieler != null) && (i < vteamspieler.size()); i++) {
					final MatchLineupPlayer player = (MatchLineupPlayer) vteamspieler.get(i);

					if (player.getId() == ISpielerPosition.setPieces) {
						aufstellung.setKicker(player.getSpielerId());
					} else if (player.getId() == ISpielerPosition.captain) {
						aufstellung.setKapitaen(player.getSpielerId());
					} else {
						aufstellung.setSpielerAtPosition(player.getId(), player.getSpielerId(),
								player.getTaktik());
					}
				}

				// Alles Updaten
				HOMainFrame.instance().getAufstellungsPanel().update();

				// Aufstellung zeigen
				HOMainFrame.instance().showTab(IModule.LINEUP);
			}
		} else if (e.getSource() == m_jbSimMatch) {
			if (matchShortInfo != null) {
				final Matchdetails details = DBManager.instance().getMatchDetails(
						matchShortInfo.getMatchID());
				final MatchPredictionManager manager = MatchPredictionManager.instance();
				final int teamId = HOVerwaltung.instance().getModel().getBasics().getTeamId();
				boolean homeMatch = false;
				if (teamId == matchShortInfo.getHeimID()) {
					homeMatch = true;
				}

				TeamRatings homeTeamRatings = manager.generateTeamRatings(
						details != null ? getRatingValue(details.getHomeMidfield()) : 1,
						details != null ? getRatingValue(details.getHomeLeftDef()) : 1,
						details != null ? getRatingValue(details.getHomeMidDef()) : 1,
						details != null ? getRatingValue(details.getHomeRightDef()) : 1,
						details != null ? getRatingValue(details.getHomeLeftAtt()) : 1,
						details != null ? getRatingValue(details.getHomeMidAtt()) : 1,
						details != null ? getRatingValue(details.getHomeRightAtt()) : 1);

				final TeamData homeTeamValues;
				if (homeMatch && !ratingsAreKnown(homeTeamRatings)) {
					homeTeamValues = getOwnLineupRatings(manager);
				} else {
					homeTeamValues = manager.generateTeamData(matchShortInfo.getHeimName(),
							homeTeamRatings, details != null ? details.getHomeTacticType()
									: IMatchDetails.TAKTIK_NORMAL,
							details != null ? getRatingValue(details.getHomeTacticSkill() - 1) : 1);
				}

				TeamRatings awayTeamRatings = manager.generateTeamRatings(
						details != null ? getRatingValue(details.getGuestMidfield()) : 1,
						details != null ? getRatingValue(details.getGuestLeftDef()) : 1,
						details != null ? getRatingValue(details.getGuestMidDef()) : 1,
						details != null ? getRatingValue(details.getGuestRightDef()) : 1,
						details != null ? getRatingValue(details.getGuestLeftAtt()) : 1,
						details != null ? getRatingValue(details.getGuestMidAtt()) : 1,
						details != null ? getRatingValue(details.getGuestRightAtt()) : 1);

				final TeamData awayTeamValues;
				if (!homeMatch && !ratingsAreKnown(awayTeamRatings)) {
					awayTeamValues = getOwnLineupRatings(manager);
				} else {
					awayTeamValues = manager
							.generateTeamData(
									matchShortInfo.getGastName(),
									awayTeamRatings,
									details != null ? details.getGuestTacticType()
											: IMatchDetails.TAKTIK_NORMAL,
									details != null ? getRatingValue(details.getGuestTacticSkill() - 1)
											: 1);
				}

				String match = matchShortInfo.getHeimName() + " - " + matchShortInfo.getGastName();
				MatchEnginePanel matchPredictionPanel = new MatchEnginePanel(homeTeamValues,
						awayTeamValues);

				MatchPredictionDialog d = new MatchPredictionDialog(matchPredictionPanel, match);
			}
		}
	}

	/**
	 * Helper to get at least the minium rating value.
	 */
	private int getRatingValue(int in) {
		if (in > 0) {
			return in;
		}
		return 1;
	}

	/**
	 * Check, if the ratings are ok/known or if all are at the default.
	 */
	private boolean ratingsAreKnown(TeamRatings ratings) {
		return (ratings != null && ratings.getMidfield() > 1d && ratings.getLeftDef() > 1d
				&& ratings.getMiddleDef() > 1d && ratings.getRightDef() > 1d
				&& ratings.getLeftAttack() > 1d && ratings.getMiddleAttack() > 1d && ratings
				.getRightAttack() > 1d);
	}

	/**
	 * Get the team data for the own team (current linep).
	 */
	private TeamData getOwnLineupRatings(MatchPredictionManager manager) {
		Lineup lineup = HOVerwaltung.instance().getModel().getAufstellung();
		TeamRatings teamRatings = manager.generateTeamRatings(
				getRatingValue(RatingUtil.getIntValue4Rating(lineup.getMidfieldRating())),
				getRatingValue(RatingUtil.getIntValue4Rating(lineup.getLeftDefenseRating())),
				getRatingValue(RatingUtil.getIntValue4Rating(lineup.getCentralDefenseRating())),
				getRatingValue(RatingUtil.getIntValue4Rating(lineup.getRightDefenseRating())),
				getRatingValue(RatingUtil.getIntValue4Rating(lineup.getLeftAttackRating())),
				getRatingValue(RatingUtil.getIntValue4Rating(lineup.getCentralAttackRating())),
				getRatingValue(RatingUtil.getIntValue4Rating(lineup.getRightAttackRating())));

		int tactic = lineup.getTacticType();
		return manager.generateTeamData(HOVerwaltung.instance().getModel().getBasics()
				.getTeamName(), teamRatings, tactic, getTacticStrength(lineup, tactic));
	}

	/**
	 * Get the tactic strength of the given lineup.
	 */
	private int getTacticStrength(Lineup lineup, int tacticType) {
		double tacticLevel = 1d;
		switch (tacticType) {
		case IMatchDetails.TAKTIK_KONTER:
			tacticLevel = lineup.getTacticLevelCounter();
			break;
		case IMatchDetails.TAKTIK_MIDDLE:
			tacticLevel = lineup.getTacticLevelAimAow();
			break;
		case IMatchDetails.TAKTIK_PRESSING:
			tacticLevel = lineup.getTacticLevelPressing();
			break;
		case IMatchDetails.TAKTIK_WINGS:
			tacticLevel = lineup.getTacticLevelAimAow();
			break;
		case IMatchDetails.TAKTIK_LONGSHOTS:
			tacticLevel = lineup.getTacticLevelLongShots();
			break;
		}
		tacticLevel -= 1;
		return (int) Math.max(tacticLevel, 0);
	}

	/**
	 * React on key pressed events.
	 */
	@Override
	public void keyPressed(java.awt.event.KeyEvent keyEvent) {
		if (keyEvent.getSource().equals(matchesTable)) {
			// manageSelectionRow ( );
			newSelectionInform();
		}
	}

	/**
	 * React on key released events.
	 */
	@Override
	public void keyReleased(java.awt.event.KeyEvent keyEvent) {
		if (keyEvent.getSource().equals(matchesTable)) {
			// manageSelectionRow ( );
			newSelectionInform();
		}
	}

	/**
	 * React on key typed events.
	 */
	@Override
	public void keyTyped(java.awt.event.KeyEvent keyEvent) {
	}

	/**
	 * React on mouse klicked events.
	 */
	@Override
	public void mouseClicked(java.awt.event.MouseEvent mouseEvent) {
		if (mouseEvent.getSource().equals(matchesTable)) {
			// manageSelectionRow ( );
			newSelectionInform();
		}
	}

	/**
	 * React on mouse entered events.
	 */
	@Override
	public void mouseEntered(java.awt.event.MouseEvent mouseEvent) {
	}

	/**
	 * React on mouse exited events.
	 */
	@Override
	public void mouseExited(java.awt.event.MouseEvent mouseEvent) {
	}

	/**
	 * React on mouse pressed events.
	 */
	@Override
	public void mousePressed(java.awt.event.MouseEvent mouseEvent) {
	}

	/**
	 * React on mouse released events.
	 */
	@Override
	public void mouseReleased(java.awt.event.MouseEvent mouseEvent) {
		if (mouseEvent.getSource().equals(matchesTable)) {
			// manageSelectionRow ( );
			newSelectionInform();
		}
	}

	/**
	 * ReInit
	 */
	@Override
	public void reInit() {
		if (m_jcbSpieleFilter.getSelectedIndex() > -1) {
			// Tabelle updaten
			int id = ((CBItem) m_jcbSpieleFilter.getSelectedItem()).getId();
			matchesTable.refresh(id);
			matchesOverviewTable.refresh(id);
			matchesOverviewCommonPanel.refresh(id);
			matchesHighlightsTable.refresh(id);
			UserParameter.instance().spieleFilter = id;

			// Dann alle anderen Panels
			newSelectionInform();
		}
	}

	/**
	 * Refresh
	 */
	@Override
	public void refresh() {
		// nix
	}

	/**
	 * Zeigt das Match mit der ID an.
	 */
	public void showMatch(int matchid) {
		matchesTable.markiereMatch(matchid);

		// Wenn kein Match in Tabelle gefunden
		if (matchesTable.getSelectedRow() < 0) {
			// Alle Spiele auswählen, damit die Markierung funktioniert
			m_jcbSpieleFilter.setSelectedIndex(0);
			UserParameter.instance().spieleFilter = 0;
			matchesTable.markiereMatch(matchid);
		}

		newSelectionInform();
	}

	/**
	 * Disable all buttons.
	 */
	private void clear() {
		m_jbReloadMatch.setEnabled(false);
		deleteButton.setEnabled(false);
		printButton.setEnabled(false);
		m_jbAufstellungUebernehmen.setEnabled(false);
		m_jbSimMatch.setEnabled(false);
	}

	/**
	 * Initialise and get the visitor lineup panel.
	 */
	private Component initAufstellungGast() {
		m_jpAufstellungGastPanel = new AufstellungsSternePanel(false);
		return m_jpAufstellungGastPanel;
	}

	/**
	 * Initialise and get the home teams lineup panel.
	 */
	private Component initAufstellungHeim() {
		m_jpAufstellungHeimPanel = new AufstellungsSternePanel(true);
		return m_jpAufstellungHeimPanel;
	}

	private void initComponents() {
		setLayout(new BorderLayout());

		horizontalLeftSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false,
				initSpieleTabelle(), initSpieldetails());

		linupPanel = new JPanel(new GridLayout(2, 1));
		linupPanel.add(initAufstellungHeim());
		linupPanel.add(initAufstellungGast());
		verticalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false,
				horizontalLeftSplitPane, new JScrollPane(linupPanel));

		horizontalLeftSplitPane
				.setDividerLocation(ho.core.model.UserParameter.instance().spielePanel_horizontalLeftSplitPane);

		verticalSplitPane
				.setDividerLocation(ho.core.model.UserParameter.instance().spielePanel_verticalSplitPane);

		add(verticalSplitPane, BorderLayout.CENTER);

		deleteButton.setBackground(ThemeManager.getColor(HOColorName.BUTTON_BG));
	}

	/**
	 * Initialise player details GUI components.
	 */
	private Component initSpieldetails() {
		final JPanel mainpanel = new ImagePanel(new BorderLayout());
		matchDetailsTabbedPane = new JTabbedPane();

		// Allgemein
		teamsComparePanel = new StaerkenvergleichPanel();
		matchDetailsTabbedPane.addTab(HOVerwaltung.instance().getLanguageString("Allgemein"),
				new JScrollPane(teamsComparePanel));

		// Bewertung
		m_jpManschaftsBewertungsPanel = new ManschaftsBewertungsPanel();
		matchDetailsTabbedPane.addTab(HOVerwaltung.instance().getLanguageString("Rating"),
				new JScrollPane(m_jpManschaftsBewertungsPanel));
		// //Bewertung2
		m_jpManschaftsBewertungs2Panel = new ManschaftsBewertungs2Panel();
		matchDetailsTabbedPane.addTab(HOVerwaltung.instance().getLanguageString("Rating") + " 2",
				new JScrollPane(m_jpManschaftsBewertungs2Panel));

		// Highlights
		matchHighlightPanel = new SpielHighlightPanel();
		matchDetailsTabbedPane.addTab(HOVerwaltung.instance().getLanguageString("Highlights"),
				new JScrollPane(matchHighlightPanel));

		// Matchbericht
		matchReportPanel = new MatchberichtPanel(true);
		matchDetailsTabbedPane.addTab(HOVerwaltung.instance().getLanguageString("Matchbericht"),
				matchReportPanel);

		mainpanel.add(matchDetailsTabbedPane, BorderLayout.CENTER);

		final JPanel buttonPanel = new ImagePanel(new FlowLayout(FlowLayout.LEFT));

		// Reloadbutton
		m_jbReloadMatch
				.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_Spiel_reload"));
		m_jbReloadMatch.addActionListener(this);
		m_jbReloadMatch.setPreferredSize(new Dimension(24, 24));
		m_jbReloadMatch.setEnabled(false);
		buttonPanel.add(m_jbReloadMatch);

		deleteButton.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_Spiel_loeschen"));
		deleteButton.addActionListener(this);
		deleteButton.setPreferredSize(new Dimension(24, 24));
		deleteButton.setEnabled(false);
		buttonPanel.add(deleteButton);

		printButton.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_Spiel_drucken"));
		printButton.addActionListener(this);
		printButton.setPreferredSize(new Dimension(24, 24));
		printButton.setEnabled(false);
		buttonPanel.add(printButton);

		m_jbAufstellungUebernehmen.setToolTipText(HOVerwaltung.instance().getLanguageString(
				"tt_Spiel_aufstellunguebernehmen"));
		m_jbAufstellungUebernehmen.addActionListener(this);
		m_jbAufstellungUebernehmen.setPreferredSize(new Dimension(24, 24));
		m_jbAufstellungUebernehmen.setEnabled(false);
		buttonPanel.add(m_jbAufstellungUebernehmen);

		m_jbSimMatch.setToolTipText(HOVerwaltung.instance().getLanguageString("Simulate"));
		m_jbSimMatch.addActionListener(this);
		m_jbSimMatch.setPreferredSize(new Dimension(24, 24));
		m_jbSimMatch.setEnabled(false);
		buttonPanel.add(m_jbSimMatch);

		mainpanel.add(buttonPanel, BorderLayout.SOUTH);
		return mainpanel;
	}

	/**
	 * Initialise matches panel.
	 */
	private Component initSpieleTabelle() {
		final ImagePanel panel = new ImagePanel(new BorderLayout());

		m_jcbSpieleFilter = new JComboBox(SPIELEFILTER);
		Helper.markierenComboBox(m_jcbSpieleFilter, UserParameter.instance().spieleFilter);
		m_jcbSpieleFilter.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
					reInit();
				}
			}
		});
		m_jcbSpieleFilter.setFont(m_jcbSpieleFilter.getFont().deriveFont(Font.BOLD));
		panel.add(m_jcbSpieleFilter, BorderLayout.NORTH);

		matchesTable = new MatchesTable(UserParameter.instance().spieleFilter);
		matchesTable.addMouseListener(this);
		matchesTable.addKeyListener(this);

		final JScrollPane scrollpane = new JScrollPane(matchesTable);

		matchesOverviewTable = new MatchesOverviewTable(UserParameter.instance().spieleFilter);
		final JScrollPane scrollpane1 = new JScrollPane(matchesOverviewTable);

		matchesOverviewCommonPanel = new MatchesOverviewCommonPanel(
				UserParameter.instance().spieleFilter);
		final JScrollPane scrollpane2 = new JScrollPane(matchesOverviewCommonPanel);

		matchesHighlightsTable = new MatchesHighlightsTable(UserParameter.instance().spieleFilter);
		final JScrollPane scrollpane3 = new JScrollPane(matchesHighlightsTable);

		JTabbedPane pane = new JTabbedPane();
		HOVerwaltung hov = HOVerwaltung.instance();
		pane.addTab(hov.getLanguageString("Spiele"), scrollpane);
		pane.addTab(
				hov.getLanguageString("Statistik") + " ("
						+ hov.getLanguageString("SerieAuswaertsSieg") + "-"
						+ hov.getLanguageString("SerieAuswaertsUnendschieden") + "-"
						+ hov.getLanguageString("SerieAuswaertsNiederlage") + ")", scrollpane1);
		pane.addTab(hov.getLanguageString("Statistik") + " (" + hov.getLanguageString("Allgemein")
				+ ")", scrollpane2);
		pane.addTab(hov.getLanguageString("Statistik") + " (" + hov.getLanguageString("Highlights")
				+ ")", scrollpane3);
		panel.add(pane, BorderLayout.CENTER);

		return panel;
	}

	private void newSelectionInform() {
		final int row = matchesTable.getSelectedRow();

		if (row > -1) {
			// Selektiertes Spiel des Models holen und alle 3 Panel informieren
			try {
				final MatchKurzInfo info = ((MatchesColumnModel) matchesTable.getSorter()
						.getModel()).getMatch((int) ((ColorLabelEntry) matchesTable.getSorter()
						.getValueAt(row, 5)).getZahl());
				refresh(info);
				final Matchdetails details = DBManager.instance()
						.getMatchDetails(info.getMatchID());
				if (details != null && details.getMatchID() > 0) {
					teamsComparePanel.refresh(info, details);
					m_jpManschaftsBewertungsPanel.refresh(info, details);
					m_jpManschaftsBewertungs2Panel.refresh(info, details);
					matchHighlightPanel.refresh(info, details);
					matchReportPanel.refresh(info, details);
				} else {
					teamsComparePanel.clear();
					m_jpManschaftsBewertungsPanel.clear();
					m_jpManschaftsBewertungs2Panel.clear();
					matchHighlightPanel.clear();
					matchReportPanel.clear();
					m_jpAufstellungHeimPanel.clearAll();
					m_jpAufstellungGastPanel.clearAll();
				}
				if (info.getMatchStatus() == MatchKurzInfo.FINISHED) {
					m_jpAufstellungHeimPanel.refresh(info.getMatchID(), info.getHeimID());
					m_jpAufstellungGastPanel.refresh(info.getMatchID(), info.getGastID());
				} else {
					m_jpAufstellungHeimPanel.clearAll();
					m_jpAufstellungGastPanel.clearAll();
				}
			} catch (Exception e) {
				clear();
				teamsComparePanel.clear();
				m_jpManschaftsBewertungsPanel.clear();
				m_jpManschaftsBewertungs2Panel.clear();
				matchHighlightPanel.clear();
				matchReportPanel.clear();
				m_jpAufstellungHeimPanel.clearAll();
				m_jpAufstellungGastPanel.clearAll();
				HOLogger.instance().log(getClass(),
						"SpielePanel.newSelectionInform: No match for found for table entry! " + e);
			}
		} else {
			// Alle Panels zurücksetzen
			clear();
			teamsComparePanel.clear();
			m_jpManschaftsBewertungsPanel.clear();
			m_jpManschaftsBewertungs2Panel.clear();
			matchHighlightPanel.clear();
			matchReportPanel.clear();
			m_jpAufstellungHeimPanel.clearAll();
			m_jpAufstellungGastPanel.clearAll();
		}
	}

	/**
	 * Refresh button states.
	 */
	private void refresh(MatchKurzInfo info) {
		matchShortInfo = info;
		deleteButton.setEnabled(true);
		m_jbSimMatch.setEnabled(true);
		if (info.getMatchStatus() == MatchKurzInfo.FINISHED) {
			m_jbReloadMatch.setEnabled(true);
			final int teamid = HOVerwaltung.instance().getModel().getBasics().getTeamId();
			if ((info.getHeimID() == teamid) || (info.getGastID() == teamid)) {
				m_jbAufstellungUebernehmen.setEnabled(true);
			} else {
				m_jbAufstellungUebernehmen.setEnabled(false);
			}
			printButton.setEnabled(true);
		} else {
			m_jbReloadMatch.setEnabled(false);
			m_jbAufstellungUebernehmen.setEnabled(false);
			printButton.setEnabled(false);
		}
	}
}
