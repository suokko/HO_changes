package de.hattrickorganizer.gui.lineup;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import de.hattrickorganizer.gui.HOMainFrame;
import de.hattrickorganizer.gui.lineup.substitution.SubstitutionOverview;
import de.hattrickorganizer.model.HOVerwaltung;

public class LineupMasterPanel extends JPanel {

	private static final long serialVersionUID = -3711183280801885465L;
	private LineupPanel lineupPanel;

	public LineupMasterPanel() {
		if (!HOMainFrame.isDeveloperMode()) {
			initComponents();
		} else {
			initComponentsDEVEL();
		}

	}

	public LineupPanel getLineupPanel() {
		return this.lineupPanel;
	}

	private void initComponents() {
		setLayout(new BorderLayout());
		this.lineupPanel = new LineupPanel();
		add(this.lineupPanel, BorderLayout.CENTER);
	}

	private void initComponentsDEVEL() {
		setLayout(new BorderLayout());
		this.lineupPanel = new LineupPanel();
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab(HOVerwaltung.instance().getLanguageString("Aufstellung"), this.lineupPanel);
		tabbedPane.addTab("Subs/Orders", new SubstitutionOverview());
		add(tabbedPane, BorderLayout.CENTER);
	}
}
