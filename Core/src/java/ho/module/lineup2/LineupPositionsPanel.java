// %2976388207:de.hattrickorganizer.gui.lineup%
package ho.module.lineup2;

import gui.HOIconName;
import ho.core.gui.Updateable;
import ho.core.gui.model.AufstellungCBItem;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.HOVerwaltung;
import ho.core.model.Spieler;
import ho.core.util.HOLogger;
import ho.module.lineup.AufstellungsVergleichHistoryPanel;
import ho.module.lineup.Lineup;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import plugins.ISpieler;
import plugins.ISpielerPosition;

/**
 * Enthält die einzelnen Positionen
 */
public class LineupPositionsPanel extends JPanel implements Updateable, ActionListener {

	private static final long serialVersionUID = -9098199182886069L;
	private JButton printButton = new JButton(ThemeManager.getIcon(HOIconName.PRINTER));
	private JButton flipSideButton = new JButton(ThemeManager.getIcon(HOIconName.RELOAD));
	private JButton midiFrameButton = new JButton(ThemeManager.getIcon(HOIconName.MIDLINEUPFRAME));
	private JButton miniFrameButton = new JButton(ThemeManager.getIcon(HOIconName.MINLINEUPFRAME));
	private PlayerPositionPanel centralForward;
	private PlayerPositionPanel centralInnerMidfielder;
	private PlayerPositionPanel leftBack;
	private PlayerPositionPanel leftWinger;
	private PlayerPositionPanel leftCentralDefender;
	private PlayerPositionPanel leftInnerMidfielder;
	private PlayerPositionPanel leftForward;
	private PlayerPositionPanel middleCentralDefender;
	private PlayerPositionPanel rightBack;
	private PlayerPositionPanel rightWinger;
	private PlayerPositionPanel rightCentralDefender;
	private PlayerPositionPanel rightInnerMidfielder;
	private PlayerPositionPanel rightForward;
	private PlayerPositionPanel substWinger;
	private PlayerPositionPanel substMidfield;
	private PlayerPositionPanel substForward;
	private PlayerPositionPanel substKeeper;
	private PlayerPositionPanel substDefender;
	private PlayerPositionPanel captain;
	private PlayerPositionPanel setpieceTaker;
	private PlayerPositionPanel keeper;
	private javax.swing.JLayeredPane centerPanel;
	private final SwapPositionsManager swapPositionsManager = new SwapPositionsManager(this);
	private LineupSettings lineupSettings;
	private Lineup lineup;

	public LineupPositionsPanel(Lineup lineup, LineupSettings lineupSettings) {
		this.lineup = lineup;
		this.lineupSettings = lineupSettings;
		this.swapPositionsManager.setLineup(this.lineup);
		initComponentes();
	}

	public final void actionPerformed(java.awt.event.ActionEvent actionEvent) {
		if (actionEvent.getSource().equals(this.flipSideButton)) {
			this.lineup.flipSide();
			refresh();
			// HOMainFrame.instance().getAufstellungsPanel().update();
		}
		// if (actionEvent.getSource().equals(m_jbMiniFrame)) {
		// new AufstellungsMiniPositionsFrame(m_clLineupPanel, false, true);
		// } else if (actionEvent.getSource().equals(m_jbMidiFrame)) {
		// new AufstellungsMiniPositionsFrame(m_clLineupPanel, false, false);
		// } else if (actionEvent.getSource().equals(this.flipSideButton)) {
		// HOVerwaltung.instance().getModel().getAufstellung().flipSide();
		// HOMainFrame.instance().getAufstellungsPanel().update();
		// } else {
		// final AufstellungsMiniPositionsFrame frame = new
		// AufstellungsMiniPositionsFrame(m_clLineupPanel,
		// true, false);
		// frame.setVisible(true);
		//
		// try {
		// Thread.sleep(500);
		// } catch (Exception e) {
		// }
		//
		// frame.doPrint();
		// }
	}

	public void refresh() {

		// Alle SpielerPositionen Informieren
		// erste 11
		Vector<ISpieler> aufgestellteSpieler = new Vector<ISpieler>();
		List<ISpieler> alleSpieler = HOVerwaltung.instance().getModel().getAllSpieler();
		Vector<ISpieler> gefilterteSpieler = new Vector<ISpieler>();

		for (int i = 0; i < alleSpieler.size(); i++) {
			Spieler spieler = (Spieler) alleSpieler.get(i);

			// ein erste 11
			if (this.lineup.isSpielerInAnfangsElf(spieler.getSpielerID())) {
				aufgestellteSpieler.add(spieler);
			}
		}

		// Den Gruppenfilter anwenden
		for (int i = 0; i < alleSpieler.size(); i++) {
			Spieler spieler = (Spieler) alleSpieler.get(i);

			// Kein Filter
			if (!this.lineupSettings.isGroupFilter()
					|| (this.lineupSettings.getGroup().equals(spieler.getTeamInfoSmilie()) && !this.lineupSettings
							.isNotGroup())
					|| (!this.lineupSettings.getGroup().equals(spieler.getTeamInfoSmilie()) && this.lineupSettings
							.isNotGroup())) {
				boolean include = true;
				AufstellungCBItem lastLineup = AufstellungsVergleichHistoryPanel.getLastLineup();

				if (this.lineupSettings.isExcludeLastMatch() && (lastLineup != null)
						&& lastLineup.getAufstellung().isSpielerInAnfangsElf(spieler.getSpielerID())) {
					include = false;
					HOLogger.instance().log(getClass(), "Exclude: " + spieler.getName());
				}

				if (include) {
					gefilterteSpieler.add(spieler);
				}
			}
		}

		this.keeper.refresh(gefilterteSpieler);
		this.leftBack.refresh(gefilterteSpieler);
		this.leftCentralDefender.refresh(gefilterteSpieler);
		this.middleCentralDefender.refresh(gefilterteSpieler);
		this.rightCentralDefender.refresh(gefilterteSpieler);
		this.rightBack.refresh(gefilterteSpieler);
		this.leftWinger.refresh(gefilterteSpieler);
		this.leftInnerMidfielder.refresh(gefilterteSpieler);
		this.centralInnerMidfielder.refresh(gefilterteSpieler);
		this.rightInnerMidfielder.refresh(gefilterteSpieler);
		this.rightWinger.refresh(gefilterteSpieler);
		this.leftForward.refresh(gefilterteSpieler);
		this.centralForward.refresh(gefilterteSpieler);
		this.rightForward.refresh(gefilterteSpieler);
		this.substKeeper.refresh(gefilterteSpieler);
		this.substDefender.refresh(gefilterteSpieler);
		this.substMidfield.refresh(gefilterteSpieler);
		this.substWinger.refresh(gefilterteSpieler);
		this.substForward.refresh(gefilterteSpieler);
		this.setpieceTaker.refresh(aufgestellteSpieler);
		this.captain.refresh(aufgestellteSpieler);
		this.lineup.checkAufgestellteSpieler();

	}

	public void exportOldLineup(String name) {
		File dir = new File("Lineups/" + HOVerwaltung.instance().getModel().getBasics().getManager());
		if (!dir.exists()) {
			dir.mkdirs();
		}

		try {
			File f = new File(dir, name + ".dat");
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			bw.write("<lineup>");
			bw.newLine();
			saveDetail(bw, this.keeper);
			saveDetail(bw, this.rightBack);
			saveDetail(bw, this.rightCentralDefender);
			saveDetail(bw, this.middleCentralDefender);
			saveDetail(bw, this.leftCentralDefender);
			saveDetail(bw, this.leftBack);
			saveDetail(bw, this.rightWinger);
			saveDetail(bw, this.rightInnerMidfielder);
			saveDetail(bw, this.centralInnerMidfielder);
			saveDetail(bw, this.leftInnerMidfielder);
			saveDetail(bw, this.leftWinger);
			saveDetail(bw, this.rightForward);
			saveDetail(bw, this.centralForward);
			saveDetail(bw, this.leftForward);
			saveDetail(bw, this.setpieceTaker);
			saveDetail(bw, this.substKeeper);
			saveDetail(bw, this.captain);
			saveDetail(bw, this.substDefender);
			saveDetail(bw, this.substMidfield);
			saveDetail(bw, this.substWinger);
			saveDetail(bw, this.substForward);
			bw.write("<tacticType>" + this.lineupSettings.getTactic() + "</tacticType>");
			bw.newLine();
			bw.write("<matchType>" + this.lineupSettings.getTeamAttitude() + "</matchType>");
			bw.newLine();
			bw.write("</lineup>");
			bw.newLine();
			bw.flush();
			bw.close();
		} catch (IOException e) {
			HOLogger.instance().log(getClass(), e);
		}
	}

	private void saveDetail(BufferedWriter bw, PlayerPositionPanel positionPanel) throws IOException {
		bw.write("<position>");
		bw.newLine();
		bw.write("<code>" + positionPanel.getPositionsID() + "</code>");
		bw.newLine();
		bw.write("<player>" + positionPanel.getPlayerId() + "</player>");
		bw.newLine();
		bw.write("<tactic>" + positionPanel.getTacticOrder() + "</tactic>");
		bw.newLine();
		bw.write("</position>");
		bw.newLine();
	}

	/**
	 * Erstellt die Komponenten
	 */
	private void initComponentes() {
		setLayout(new BorderLayout());

		centerPanel = new javax.swing.JLayeredPane();
		centerPanel.setOpaque(false);

		final GridBagLayout layout = new GridBagLayout();
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.fill = GridBagConstraints.NONE;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.insets = new Insets(2, 2, 2, 2);

		centerPanel.setLayout(layout);

		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		this.keeper = new PlayerPositionPanel(this, this.lineup, ISpielerPosition.keeper);
		layout.setConstraints(this.keeper, constraints);
		centerPanel.add(this.keeper);
		swapPositionsManager.addSwapCapabilityTo(this.keeper);
		// assistantPanel.addToAssistant(this.keeper);

		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		this.rightBack = new PlayerPositionPanel(this, this.lineup, ISpielerPosition.rightBack);
		layout.setConstraints(this.rightBack, constraints);
		centerPanel.add(this.rightBack);
		swapPositionsManager.addSwapCapabilityTo(this.rightBack);
		// assistantPanel.addToAssistant(this.rightBack);

		// Defense line
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		this.rightCentralDefender = new PlayerPositionPanel(this, this.lineup,
				ISpielerPosition.rightCentralDefender);
		layout.setConstraints(this.rightCentralDefender, constraints);
		centerPanel.add(this.rightCentralDefender);
		swapPositionsManager.addSwapCapabilityTo(this.rightCentralDefender);
		// assistantPanel.addToAssistant(this.rightCentralDefender);

		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		this.middleCentralDefender = new PlayerPositionPanel(this, this.lineup,
				ISpielerPosition.middleCentralDefender);
		layout.setConstraints(this.middleCentralDefender, constraints);
		centerPanel.add(this.middleCentralDefender);
		swapPositionsManager.addSwapCapabilityTo(this.middleCentralDefender);
		// assistantPanel.addToAssistant(this.middleCentralDefender);

		constraints.gridx = 3;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		this.leftCentralDefender = new PlayerPositionPanel(this, this.lineup,
				ISpielerPosition.leftCentralDefender);
		layout.setConstraints(this.leftCentralDefender, constraints);
		centerPanel.add(this.leftCentralDefender);
		swapPositionsManager.addSwapCapabilityTo(this.leftCentralDefender);
		// assistantPanel.addToAssistant(this.leftCentralDefender);

		constraints.gridx = 4;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		this.leftBack = new PlayerPositionPanel(this, this.lineup, ISpielerPosition.leftBack);
		layout.setConstraints(this.leftBack, constraints);
		centerPanel.add(this.leftBack);
		swapPositionsManager.addSwapCapabilityTo(this.leftBack);
		// assistantPanel.addToAssistant(this.leftBack);

		// Midfield Line
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		this.rightWinger = new PlayerPositionPanel(this, this.lineup, ISpielerPosition.rightWinger);
		layout.setConstraints(this.rightWinger, constraints);
		centerPanel.add(this.rightWinger);
		swapPositionsManager.addSwapCapabilityTo(this.rightWinger);
		// assistantPanel.addToAssistant(this.rightWinger);

		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		this.rightInnerMidfielder = new PlayerPositionPanel(this, this.lineup,
				ISpielerPosition.rightInnerMidfield);
		layout.setConstraints(this.rightInnerMidfielder, constraints);
		centerPanel.add(this.rightInnerMidfielder);
		swapPositionsManager.addSwapCapabilityTo(this.rightInnerMidfielder);
		// assistantPanel.addToAssistant(this.rightInnerMidfielder);

		constraints.gridx = 2;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		this.centralInnerMidfielder = new PlayerPositionPanel(this, this.lineup,
				ISpielerPosition.centralInnerMidfield);
		layout.setConstraints(this.centralInnerMidfielder, constraints);
		centerPanel.add(this.centralInnerMidfielder);
		swapPositionsManager.addSwapCapabilityTo(this.centralInnerMidfielder);
		// assistantPanel.addToAssistant(m_clCentralInnerMidfielder);

		constraints.gridx = 3;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		this.leftInnerMidfielder = new PlayerPositionPanel(this, this.lineup,
				ISpielerPosition.leftInnerMidfield);
		layout.setConstraints(this.leftInnerMidfielder, constraints);
		centerPanel.add(this.leftInnerMidfielder);
		swapPositionsManager.addSwapCapabilityTo(this.leftInnerMidfielder);
		// assistantPanel.addToAssistant(this.leftInnerMidfielder);

		constraints.gridx = 4;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		this.leftWinger = new PlayerPositionPanel(this, this.lineup, ISpielerPosition.leftWinger);
		layout.setConstraints(this.leftWinger, constraints);
		centerPanel.add(this.leftWinger);
		swapPositionsManager.addSwapCapabilityTo(this.leftWinger);
		// assistantPanel.addToAssistant(this.leftWinger);

		// Forward line
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		this.rightForward = new PlayerPositionPanel(this, this.lineup, ISpielerPosition.rightForward);
		layout.setConstraints(this.rightForward, constraints);
		centerPanel.add(this.rightForward);
		swapPositionsManager.addSwapCapabilityTo(this.rightForward);
		// assistantPanel.addToAssistant(this.rightForward);

		constraints.gridx = 2;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		this.centralForward = new PlayerPositionPanel(this, this.lineup, ISpielerPosition.centralForward);
		layout.setConstraints(this.centralForward, constraints);
		centerPanel.add(this.centralForward);
		swapPositionsManager.addSwapCapabilityTo(this.centralForward);
		// assistantPanel.addToAssistant(this.centralForward);

		constraints.gridx = 3;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		this.leftForward = new PlayerPositionPanel(this, this.lineup, ISpielerPosition.leftForward);
		layout.setConstraints(this.leftForward, constraints);
		centerPanel.add(this.leftForward);
		swapPositionsManager.addSwapCapabilityTo(this.leftForward);
		// assistantPanel.addToAssistant(this.leftForward);

		// A spacer between forwards and reserves.
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.gridwidth = 5;
		Box box = new Box(BoxLayout.Y_AXIS);
		box.add(Box.createRigidArea(new Dimension(10, 6)));
		layout.setConstraints(box, constraints);
		centerPanel.add(box);

		// The reserves
		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.gridwidth = 1;
		this.substKeeper = new PlayerPositionPanel(this, this.lineup, ISpielerPosition.substKeeper);
		layout.setConstraints(this.substKeeper, constraints);
		centerPanel.add(this.substKeeper);
		swapPositionsManager.addSwapCapabilityTo(this.substKeeper);

		constraints.gridx = 1;
		constraints.gridy = 5;
		constraints.gridwidth = 1;
		this.substDefender = new PlayerPositionPanel(this, this.lineup, ISpielerPosition.substDefender);
		layout.setConstraints(this.substDefender, constraints);
		centerPanel.add(this.substDefender);
		swapPositionsManager.addSwapCapabilityTo(this.substDefender);

		constraints.gridx = 2;
		constraints.gridy = 5;
		constraints.gridwidth = 1;
		this.substMidfield = new PlayerPositionPanel(this, this.lineup, ISpielerPosition.substInnerMidfield);
		layout.setConstraints(this.substMidfield, constraints);
		centerPanel.add(this.substMidfield);
		swapPositionsManager.addSwapCapabilityTo(this.substMidfield);

		constraints.gridx = 3;
		constraints.gridy = 5;
		constraints.gridwidth = 1;
		this.substForward = new PlayerPositionPanel(this, this.lineup, ISpielerPosition.substForward);
		layout.setConstraints(this.substForward, constraints);
		centerPanel.add(this.substForward);
		swapPositionsManager.addSwapCapabilityTo(this.substForward);

		constraints.gridx = 4;
		constraints.gridy = 5;
		constraints.gridwidth = 1;
		this.substWinger = new PlayerPositionPanel(this, this.lineup, ISpielerPosition.substWinger);
		layout.setConstraints(this.substWinger, constraints);
		centerPanel.add(this.substWinger);
		swapPositionsManager.addSwapCapabilityTo(this.substWinger);

		// Captain and setpieces
		constraints.gridx = 0;
		constraints.gridy = 6;
		constraints.gridwidth = 1;
		this.captain = new PlayerPositionPanel(this, this.lineup, ISpielerPosition.captain);
		layout.setConstraints(this.captain, constraints);
		centerPanel.add(this.captain);

		constraints.gridx = 1;
		constraints.gridy = 6;
		constraints.gridwidth = 1;
		this.setpieceTaker = new PlayerPositionPanel(this, this.lineup, ISpielerPosition.setPieces);
		layout.setConstraints(this.setpieceTaker, constraints);
		centerPanel.add(this.setpieceTaker);

		// Gruppenzuordnung des aufgestellten
		constraints.gridx = 3;
		constraints.gridy = 6;
		constraints.gridwidth = 1;
		constraints.anchor = GridBagConstraints.CENTER;
		centerPanel.add(new AufstellungsGruppenPanel(), constraints);

		// MiniLineup
		JPanel buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.setOpaque(false);

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		Dimension preferredButtonSize = new Dimension(25, 25);
		this.printButton.setToolTipText(getLanguageString("tt_AufstellungsPosition_Drucken"));
		this.printButton.setPreferredSize(preferredButtonSize);
		panel.add(this.printButton);
		this.miniFrameButton.setToolTipText(getLanguageString("tt_AufstellungsPosition_MiniFrame"));
		this.miniFrameButton.setPreferredSize(preferredButtonSize);
		panel.add(this.miniFrameButton);
		this.midiFrameButton.setToolTipText(getLanguageString("tt_AufstellungsPosition_MidiFrame"));
		this.midiFrameButton.setPreferredSize(preferredButtonSize);
		panel.add(this.midiFrameButton);
		this.flipSideButton.setToolTipText(getLanguageString("tt_AufstellungsPosition_FlipSide"));
		this.flipSideButton.setPreferredSize(preferredButtonSize);
		this.flipSideButton.addActionListener(this);
		panel.add(this.flipSideButton);
		buttonPanel.add(panel, BorderLayout.NORTH);

		constraints.gridx = 4;
		constraints.gridy = 6;
		constraints.gridwidth = 1;
		constraints.anchor = GridBagConstraints.CENTER;
		layout.setConstraints(panel, constraints);
		centerPanel.add(panel);

		add(centerPanel, BorderLayout.CENTER);
	}

	public void update() {
		refresh();
	}

	/**
	 * convenience method
	 * 
	 * @param key
	 * @return
	 */
	private String getLanguageString(String key) {
		return HOVerwaltung.instance().getLanguageString(key);
	}
}
