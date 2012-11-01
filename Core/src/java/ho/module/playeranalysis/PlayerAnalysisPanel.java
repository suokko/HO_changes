package ho.module.playeranalysis;

import ho.core.gui.CursorToolkit;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.model.HOVerwaltung;
import ho.core.module.config.ModuleConfig;
import ho.module.playeranalysis.experience.ExperienceViewer;
import ho.module.playeranalysis.skillCompare.PlayerComparePanel;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JTabbedPane;

public class PlayerAnalysisPanel extends ImagePanel {

	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;
	private SpielerAnalyseMainPanel spielerAnalyseMainPanel;
	private PlayerComparePanel playerComparePanel;
	private ExperienceViewer experienceViewer;
	private boolean initialized = false;

	public PlayerAnalysisPanel() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				if (isShowing()) {
					if (!initialized) {
						CursorToolkit.startWaitCursor(PlayerAnalysisPanel.this);
						try {
							initialize();
						} finally {
							CursorToolkit.stopWaitCursor(PlayerAnalysisPanel.this);
						}
					}
				}
			}
		});
	}

	private void initialize() {
		setLayout(new BorderLayout());
		add(getTabbedPane(), BorderLayout.CENTER);
		this.initialized = true;
	}

	private JTabbedPane getTabbedPane() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane();
			tabbedPane.add(getSpielerAnalyseMainPanel(),
					HOVerwaltung.instance().getLanguageString("Spiele"));
			if (ModuleConfig.instance().getBoolean(PlayerAnalysisModule.SHOW_PLAYERCOMPARE))
				tabbedPane.add(getPlayerComparePanel(),
						HOVerwaltung.instance().getLanguageString("PlayerCompare"));
			if (ModuleConfig.instance().getBoolean(PlayerAnalysisModule.SHOW_EXPERIENCE))
				tabbedPane.add(getExperienceViewer(),
						HOVerwaltung.instance().getLanguageString("ExperienceViewer"));
		}
		return tabbedPane;
	}

	private SpielerAnalyseMainPanel getSpielerAnalyseMainPanel() {
		if (spielerAnalyseMainPanel == null)
			spielerAnalyseMainPanel = new SpielerAnalyseMainPanel();
		return spielerAnalyseMainPanel;
	}

	private PlayerComparePanel getPlayerComparePanel() {
		if (playerComparePanel == null)
			playerComparePanel = new PlayerComparePanel();
		return playerComparePanel;
	}

	private ExperienceViewer getExperienceViewer() {
		if (experienceViewer == null)
			experienceViewer = new ExperienceViewer();
		return experienceViewer;
	}

	public final void setSpieler4Bottom(int spielerid) {
		getSpielerAnalyseMainPanel().setSpieler4Bottom(spielerid);
	}

	public final void setSpieler4Top(int spielerid) {
		getSpielerAnalyseMainPanel().setSpieler4Top(spielerid);
	}
}
