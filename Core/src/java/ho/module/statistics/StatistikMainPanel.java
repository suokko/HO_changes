// %1280579671:de.hattrickorganizer.gui.statistic%
package ho.module.statistics;

import ho.core.gui.CursorToolkit;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.model.HOVerwaltung;

import java.awt.BorderLayout;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;

import javax.swing.JTabbedPane;

/**
 * TabbedPane mit Statistiken
 */
public class StatistikMainPanel extends ImagePanel {

	private static final long serialVersionUID = -4248329201381491432L;
	private AlleSpielerStatistikPanel alleSpielerStatistikPanel;
	private ArenaStatistikPanel arenaStatistikPanel;
	private FinanzStatistikPanel finanzStatistikPanel;
	private JTabbedPane tabbedPane;
	private SpieleStatistikPanel spieleStatistikPanel;
	private SpielerStatistikPanel spielerStatistikPanel;
	private boolean initialized = false;

	/**
	 * Creates a new StatistikMainPanel object.
	 */
	public StatistikMainPanel() {
		addHierarchyListener(new HierarchyListener() {

			@Override
			public void hierarchyChanged(HierarchyEvent e) {
				if ((HierarchyEvent.SHOWING_CHANGED == (e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) && isShowing())) {
					if (!initialized) {
						initialize();
					}
				}

			}
		});
	}

	private void initialize() {
		CursorToolkit.startWaitCursor(this);
		try {
			initComponents();
			this.initialized = true;
		} finally {
			CursorToolkit.stopWaitCursor(this);
		}

	}

	private void initComponents() {
		setLayout(new BorderLayout());
		tabbedPane = new JTabbedPane();

		// Spielerstatistik
		spielerStatistikPanel = new SpielerStatistikPanel();
		tabbedPane.addTab(HOVerwaltung.instance().getLanguageString("Spieler"),
				spielerStatistikPanel);
		// SpieleStatistik
		spieleStatistikPanel = new SpieleStatistikPanel();
		tabbedPane
				.addTab(HOVerwaltung.instance().getLanguageString("Spiele"), spieleStatistikPanel);
		// DurchschnittlicheSpielerstatistik
		alleSpielerStatistikPanel = new AlleSpielerStatistikPanel();
		tabbedPane.addTab(HOVerwaltung.instance().getLanguageString("Verein"),
				alleSpielerStatistikPanel);
		// Finanzstatistik
		finanzStatistikPanel = new FinanzStatistikPanel();
		tabbedPane.addTab(HOVerwaltung.instance().getLanguageString("Finanzen"),
				finanzStatistikPanel);
		// Arenastatistik
		arenaStatistikPanel = new ArenaStatistikPanel();
		tabbedPane
				.addTab(HOVerwaltung.instance().getLanguageString("Stadion"), arenaStatistikPanel);
		add(tabbedPane, java.awt.BorderLayout.CENTER);
	}

	public final void setShowSpieler(int spielerid) {
		spielerStatistikPanel.setAktuelleSpieler(spielerid);
		tabbedPane.setSelectedIndex(0);
	}

}
