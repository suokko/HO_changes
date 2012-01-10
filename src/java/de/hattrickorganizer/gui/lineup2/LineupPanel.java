// %3969157412:de.hattrickorganizer.gui.lineup%
package de.hattrickorganizer.gui.lineup2;

import gui.HOIconName;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import de.hattrickorganizer.gui.lineup.AufstellungsVergleichHistoryPanel;
import de.hattrickorganizer.gui.theme.ImageUtilities;
import de.hattrickorganizer.gui.theme.ThemeManager;
import de.hattrickorganizer.tools.extension.FileExtensionManager;

/**
 * Panel zum Darstellen aller SpielerPositionen
 */
public class LineupPanel extends de.hattrickorganizer.gui.templates.ImagePanel {

	private static final long serialVersionUID = -8522462525028842L;

	private AufstellungsAssistentPanel aufstellungsAssistentPanel;
	private AufstellungsDetailPanel aufstellungsDetailPanel;
	private LineupPositionsPanel aufstellungsPositionsPanel;

	/**
	 * Creates a new AufstellungsPanel object.
	 */
	public LineupPanel() {
		initComponents();
	}

	/**
	 * Gibt das AufstellungsAssistentPanel zurück
	 * 
	 */
	public final AufstellungsAssistentPanel getAufstellungsAssitentPanel() {
		return this.aufstellungsAssistentPanel;
	}

	/**
	 * Gibt das AufstellungsDetailPanel zurück
	 * 
	 */
	public final AufstellungsDetailPanel getAufstellungsDetailPanel() {
		return this.aufstellungsDetailPanel;
	}

	/**
	 * Gibt das AufstellungsPositionsPanel zurück
	 * 
	 */
	public final LineupPositionsPanel getAufstellungsPositionsPanel() {
		return this.aufstellungsPositionsPanel;
	}

	/**
	 * Setzt die Spieler und Taktiken der einzelnen PositionsPanels neu
	 */
	public final void update() {
		this.aufstellungsPositionsPanel.refresh();
		this.aufstellungsDetailPanel.refresh();

		this.aufstellungsPositionsPanel.exportOldLineup("Actual");
		FileExtensionManager.extractLineup("Actual");
	}

	private void initComponents() {
		setLayout(new BorderLayout());

		JSplitPane verticalSplitPaneLow = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false);

		final JTabbedPane tabbedPane = new JTabbedPane();
		this.aufstellungsAssistentPanel = new AufstellungsAssistentPanel();
		ImageIcon ballIcon = new ImageIcon(ImageUtilities.makeColorTransparent(
				ThemeManager.loadImage("gui/bilder/credits/Ball.png"), Color.red).getScaledInstance(13, 13,
				Image.SCALE_SMOOTH));
		tabbedPane.addTab("", ballIcon, new JScrollPane(this.aufstellungsAssistentPanel));
		tabbedPane.addTab("", ThemeManager.getIcon(HOIconName.DISK), new AufstellungsVergleichHistoryPanel());

		this.aufstellungsDetailPanel = new AufstellungsDetailPanel();
		JSplitPane horizontalRightSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false,
				new JScrollPane(this.aufstellungsDetailPanel), tabbedPane);

		this.aufstellungsPositionsPanel = new LineupPositionsPanel(null, new LineupSettings());
		JSplitPane verticalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false, new JScrollPane(
				this.aufstellungsPositionsPanel), horizontalRightSplitPane);

		verticalSplitPaneLow
				.setDividerLocation(gui.UserParameter.instance().aufstellungsPanel_verticalSplitPaneLow);
		horizontalRightSplitPane
				.setDividerLocation(gui.UserParameter.instance().aufstellungsPanel_horizontalRightSplitPane);
		verticalSplitPane
				.setDividerLocation(gui.UserParameter.instance().aufstellungsPanel_verticalSplitPane);

		add(verticalSplitPane, BorderLayout.CENTER);
	}
}
