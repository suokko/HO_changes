package ho.module.training;

import ho.core.gui.IRefreshable;
import ho.core.gui.RefreshManager;
import ho.core.model.HOModel;
import ho.core.model.HOVerwaltung;
import ho.core.model.UserParameter;
import ho.core.model.player.Spieler;
import ho.module.training.ui.AnalyzerPanel;
import ho.module.training.ui.EffectPanel;
import ho.module.training.ui.OutputPanel;
import ho.module.training.ui.PlayerDetailPanel;
import ho.module.training.ui.SkillupPanel;
import ho.module.training.ui.StaffPanel;
import ho.module.training.ui.TrainingRecapPanel;
import ho.module.training.ui.comp.DividerListener;
import ho.module.training.ui.model.TrainingModel;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

public class TrainingPanel extends JPanel {

	private static final long serialVersionUID = -1313192105835561643L;
	private static TrainingModel model;

	public TrainingPanel() {
		initialize();
	}

	private void initialize() {
		model = new TrainingModel();
		setStaffInTrainingModel(model);
		initComponents();
		addListeners();
	}

	private void addListeners() {
		RefreshManager.instance().registerRefreshable(new IRefreshable() {

			@Override
			public void refresh() {
				update();
			}
		});
	}

	private void initComponents() {
		setLayout(new BorderLayout());

		JSplitPane leftPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				new SkillupPanel(this.model), new StaffPanel(this.model));
		leftPane.setResizeWeight(1);
		leftPane.setDividerLocation(UserParameter.instance().training_lowerLeftSplitPane);
		leftPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY,
				new DividerListener(DividerListener.training_lowerLeftSplitPane));

		JSplitPane bottomPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPane,
				new JScrollPane(new PlayerDetailPanel(this.model)));

		bottomPanel.setDividerLocation(UserParameter.instance().training_bottomSplitPane);
		bottomPanel.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY,
				new DividerListener(DividerListener.training_bottomSplitPane));

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab(getLangStr("Training"), new OutputPanel(model));
		tabbedPane.addTab(getLangStr("MainPanel.Prediction"), new TrainingRecapPanel(model));
		tabbedPane.addTab(getLangStr("MainPanel.Analyzer"), new AnalyzerPanel(model));
		tabbedPane.addTab(getLangStr("MainPanel.Effect"), new EffectPanel());
		JSplitPane splitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tabbedPane, bottomPanel);

		splitPanel.setDividerLocation(UserParameter.instance().training_mainSplitPane);
		splitPanel.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY,
				new DividerListener(DividerListener.training_mainSplitPane));

		JSplitPane mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPanel,
				new ho.module.training.ui.TrainingPanel(this.model));

		mainPanel.setDividerLocation(UserParameter.instance().training_rightSplitPane);
		mainPanel.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY,
				new DividerListener(DividerListener.training_rightSplitPane));

		mainPanel.setOpaque(false);
		add(mainPanel, BorderLayout.CENTER);
	}

	/**
	 * When called by HO reload everything!
	 */
	private void update() {
		// reset the selected player
		this.model.setActivePlayer(null);

		// reload the staff, could have changed
		setStaffInTrainingModel(this.model);
	}

	private void setStaffInTrainingModel(TrainingModel trainingModel) {
		HOModel hoModel = HOVerwaltung.instance().getModel();
		if (hoModel.getVerein() != null) {
			trainingModel.setNumberOfCoTrainers(hoModel.getVerein().getCoTrainer());
		}
		if (hoModel.getTrainer() != null) {
			trainingModel.setTrainerLevel(hoModel.getTrainer().getTrainer());
		} else {
			trainingModel.setTrainerLevel(4);
		}
	}

	/**
	 * Sets the new active player and recalculate everything
	 * 
	 * @param player
	 *            the new selected player
	 */
	public static void selectPlayer(Spieler player) {
		model.setActivePlayer(player);
	}

	private String getLangStr(String key) {
		return HOVerwaltung.instance().getLanguageString(key);
	}
}
