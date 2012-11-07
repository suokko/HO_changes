package ho.module.teamAnalyzer.ui;

import ho.core.gui.CursorToolkit;
import ho.core.gui.IRefreshable;
import ho.core.gui.RefreshManager;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.model.HOVerwaltung;
import ho.core.model.UserParameter;
import ho.core.module.config.ModuleConfig;
import ho.module.teamAnalyzer.SystemManager;
import ho.module.teamAnalyzer.manager.ReportManager;
import ho.module.teamAnalyzer.ui.controller.SimButtonListener;
import ho.module.teamAnalyzer.vo.Filter;
import ho.module.teamAnalyzer.vo.TeamLineup;
import ho.module.training.ui.comp.DividerListener;

import java.awt.BorderLayout;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class TeamAnalyzerPanel extends JPanel {

	/** The filters */
	public static Filter filter = new Filter();
	private static final long serialVersionUID = 1L;
	private JButton simButton;
	private RecapPanel recapPanel;
	private MainPanel mainPanel;
	private FilterPanel filterPanel;
	private RatingPanel ratingPanel;
	private boolean initialized = false;
	private boolean needsRefresh = false;

	public TeamAnalyzerPanel() {
		addHierarchyListener(new HierarchyListener() {

			@Override
			public void hierarchyChanged(HierarchyEvent e) {
				if ((HierarchyEvent.SHOWING_CHANGED == (e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) && isShowing())) {
					if (!initialized) {
						initialize();
					}
					if (needsRefresh) {
						refresh();
					}
				}
			}
		});
	}

	private void initialize() {
		CursorToolkit.startWaitCursor(this);
		try {
			SystemManager.initialize(this);
			initComponents();
			addListeners();
			SystemManager.refreshData();
			this.initialized = true;
		} finally {
			CursorToolkit.stopWaitCursor(this);
		}
	}

	private void addListeners() {
		simButton.addActionListener(new SimButtonListener(mainPanel.getMyTeamLineupPanel(),
				mainPanel.getOpponentTeamLineupPanel(), recapPanel));

		RefreshManager.instance().registerRefreshable(new IRefreshable() {

			@Override
			public void refresh() {
				if (isShowing()) {
					refresh();
				} else {
					needsRefresh = true;
				}
			}
		});
	}

	private void refresh() {
		CursorToolkit.startWaitCursor(this);
		try {
			SystemManager.refreshData();
			this.needsRefresh = false;
		} finally {
			CursorToolkit.stopWaitCursor(this);
		}
	}

	private void initComponents() {
		filterPanel = new FilterPanel();
		recapPanel = new RecapPanel();
		mainPanel = new MainPanel();
		ratingPanel = new RatingPanel();
		setLayout(new BorderLayout());

		JPanel buttonPanel = new ImagePanel(new BorderLayout());
		simButton = new JButton(HOVerwaltung.instance().getLanguageString("Simulate"));
		buttonPanel.add(simButton, BorderLayout.CENTER);

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, ratingPanel, buttonPanel);
		splitPane.setDividerSize(1);
		splitPane.setResizeWeight(1);
		splitPane.setDividerLocation(UserParameter.instance().teamAnalyzer_LowerLefSplitPane);
		splitPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY,
				new DividerListener(DividerListener.teamAnalyzer_LowerLefSplitPane));

		JSplitPane splitPaneLeft = new JSplitPane(JSplitPane.VERTICAL_SPLIT, filterPanel, splitPane);
		splitPaneLeft.setDividerLocation(UserParameter.instance().teamAnalyzer_UpperLeftSplitPane);
		splitPaneLeft.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY,
				new DividerListener(DividerListener.teamAnalyzer_UpperLeftSplitPane));

		JSplitPane splitPaneUpper = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPaneLeft,
				mainPanel);
		splitPaneUpper.setDividerLocation(UserParameter.instance().teamAnalyzer_MainSplitPane);
		splitPaneUpper.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY,
				new DividerListener(DividerListener.teamAnalyzer_MainSplitPane));

		JSplitPane splitPaneMain = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPaneUpper,
				recapPanel);
		splitPaneMain.setDividerLocation(UserParameter.instance().teamAnalyzer_BottomSplitPane);
		splitPaneMain.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY,
				new DividerListener(DividerListener.teamAnalyzer_BottomSplitPane));
		add(splitPaneMain, BorderLayout.CENTER);
	}

	public MainPanel getMainPanel() {
		return mainPanel;
	}

	/**
	 * Returns the Filter Panel
	 * 
	 * @return
	 */
	public FilterPanel getFilterPanel() {
		return filterPanel;
	}

	/**
	 * Returns the rating panel
	 * 
	 * @return
	 */
	public RatingPanel getRatingPanel() {
		return ratingPanel;
	}

	/**
	 * Returns the recap panel
	 * 
	 * @return
	 */
	RecapPanel getRecapPanel() {
		return recapPanel;
	}

	public void reload() {
		TeamLineup lineup = ReportManager.getLineup();

		getFilterPanel().reload();

		getMainPanel().reload(lineup, 0, 0);
		getRecapPanel().reload(lineup);
		getRatingPanel().reload(lineup);

		if (ModuleConfig.instance().getBoolean(SystemManager.ISLINEUP)) {
			this.simButton.setVisible(true);
		} else {
			this.simButton.setVisible(false);
		}
	}

}
