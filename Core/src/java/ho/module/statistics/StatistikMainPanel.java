// %1280579671:de.hattrickorganizer.gui.statistic%
package ho.module.statistics;

import ho.core.gui.RefreshManager;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.model.HOVerwaltung;

import java.awt.BorderLayout;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;


/**
 * TabbedPane mit Statistiken
 */
public class StatistikMainPanel extends ImagePanel implements
		javax.swing.event.ChangeListener, ho.core.gui.Refreshable {

	private static final long serialVersionUID = -4248329201381491432L;

	// ~ Instance fields
	// ----------------------------------------------------------------------------

	private AlleSpielerStatistikPanel m_clAlleSpielerStatistikPanel = new AlleSpielerStatistikPanel();
	private ArenaStatistikPanel m_clArenaStatistikPanel = new ArenaStatistikPanel();
	private FinanzStatistikPanel m_clFinanzStatistikPanel = new FinanzStatistikPanel();
	private JTabbedPane m_clTabbedPane;
	private SpieleStatistikPanel m_clSpieleStatistikPanel = new SpieleStatistikPanel();
	private SpielerStatistikPanel m_clSpielerStatistikPanel = new SpielerStatistikPanel();

	// ~ Constructors
	// -------------------------------------------------------------------------------

	/**
	 * Creates a new StatistikMainPanel object.
	 */
	public StatistikMainPanel() {
		setLayout(new BorderLayout());

		m_clTabbedPane = new JTabbedPane();

		// Spielerstatistik
		m_clTabbedPane.addTab(HOVerwaltung.instance().getLanguageString(
				"Spieler"), m_clSpielerStatistikPanel);

		// SpielerFinanzstatistik
		// m_clTabbedPane.addTab (
		// model.HOVerwaltung.instance().getLanguageString( "Spieler" ) +
		// model.HOVerwaltung.instance().getLanguageString( "Finanzen" ), new
		// SpielerFinanzenStatistikPanel() );
		// SpieleStatistik
		m_clTabbedPane.addTab(HOVerwaltung.instance().getLanguageString(
				"Spiele"), m_clSpieleStatistikPanel);

		// DurchschnittlicheSpielerstatistik
		m_clTabbedPane.addTab(HOVerwaltung.instance().getLanguageString(
				"Verein"), m_clAlleSpielerStatistikPanel);

		// Finanzstatistik
		m_clTabbedPane.addTab(HOVerwaltung.instance().getLanguageString(
				"Finanzen"), m_clFinanzStatistikPanel);

		// Arenastatistik
		m_clTabbedPane.addTab(HOVerwaltung.instance().getLanguageString(
				"Stadion"), m_clArenaStatistikPanel);

		add(m_clTabbedPane, java.awt.BorderLayout.CENTER);
		m_clTabbedPane.addChangeListener(this);
		RefreshManager.instance().registerRefreshable(this);
	}

	// ~ Methods
	// ------------------------------------------------------------------------------------

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param spielerid
	 *            TODO Missing Method Parameter Documentation
	 */
	public final void setShowSpieler(int spielerid) {
		m_clSpielerStatistikPanel.setAktuelleSpieler(spielerid);
		m_clTabbedPane.setSelectedIndex(0);
	}

	/**
	 * TODO Missing Method Documentation
	 */
	public final void reInit() {
		// Alle Panel auf veraltet setzen!
		m_clSpielerStatistikPanel.setInitialisiert(false);
		m_clSpieleStatistikPanel.setInitialisiert(false);
		m_clAlleSpielerStatistikPanel.setInitialisiert(false);
		m_clFinanzStatistikPanel.setInitialisiert(false);
		m_clArenaStatistikPanel.setInitialisiert(false);

		init();
	}

	/**
	 * TODO Missing Method Documentation
	 */
	public void refresh() {
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param changeEvent
	 *            TODO Missing Method Parameter Documentation
	 */
	public final void stateChanged(ChangeEvent changeEvent) {
		init();
	}

	/**
	 * TODO Missing Method Documentation
	 */
	private void init() {
		// Initialisieren des aktuellen Panels, sonst nix
		if (m_clTabbedPane.getSelectedComponent().equals(
				m_clSpielerStatistikPanel)) {
			if (!m_clSpielerStatistikPanel.isInitialisiert()) {
				m_clSpielerStatistikPanel.doInitialisieren();
			}
		} else if (m_clTabbedPane.getSelectedComponent().equals(
				m_clSpieleStatistikPanel)) {
			if (!m_clSpieleStatistikPanel.isInitialisiert()) {
				m_clSpieleStatistikPanel.doInitialisieren();
			}
		} else if (m_clTabbedPane.getSelectedComponent().equals(
				m_clAlleSpielerStatistikPanel)) {
			if (!m_clAlleSpielerStatistikPanel.isInitialisiert()) {
				m_clAlleSpielerStatistikPanel.doInitialisieren();
			}
		} else if (m_clTabbedPane.getSelectedComponent().equals(
				m_clFinanzStatistikPanel)) {
			if (!m_clFinanzStatistikPanel.isInitialisiert()) {
				m_clFinanzStatistikPanel.doInitialisieren();
			}
		} else if (m_clTabbedPane.getSelectedComponent().equals(
				m_clArenaStatistikPanel)) {
			if (!m_clArenaStatistikPanel.isInitialisiert()) {
				m_clArenaStatistikPanel.doInitialisieren();
			}
		}
	}
}
