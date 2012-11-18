package ho.module.training;

import ho.core.gui.comp.panel.LazyPanel;
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
import ho.module.training.ui.TrainingPanel;
import ho.module.training.ui.TrainingRecapPanel;
import ho.module.training.ui.comp.DividerListener;
import ho.module.training.ui.model.TrainingModel;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

public class TrainingModulePanel extends LazyPanel {

	private static final long serialVersionUID = -1313192105835561643L;
	private TrainingModel model;
	private boolean initialized = false;
	private boolean needsRefresh = false;

	@Override
	protected void initialize() {
		this.model = new TrainingModel();
		setStaffInTrainingModel(this.model);
		initComponents();
		registerRefreshable(true);
	}

	@Override
	protected void update() {
		Spieler oldPlayer = this.model.getActivePlayer();
		// reset the selected player
		this.model.setActivePlayer(null);
		// reload the staff, could have changed
		setStaffInTrainingModel(this.model);

		if (oldPlayer != null) {
			Spieler player = HOVerwaltung.instance().getModel()
					.getSpieler(oldPlayer.getSpielerID());
			this.model.setActivePlayer(player);
		}
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
		tabbedPane.addTab(getLangStr("Training"), new OutputPanel(this.model));
		tabbedPane.addTab(getLangStr("MainPanel.Prediction"), new TrainingRecapPanel(this.model));
		tabbedPane.addTab(getLangStr("MainPanel.Analyzer"), new AnalyzerPanel(this.model));
		tabbedPane.addTab(getLangStr("MainPanel.Effect"), new EffectPanel());

		JSplitPane splitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tabbedPane, bottomPanel);
		splitPanel.setDividerLocation(UserParameter.instance().training_mainSplitPane);
		splitPanel.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY,
				new DividerListener(DividerListener.training_mainSplitPane));

		JSplitPane mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPanel,
				new TrainingPanel(this.model));
		mainPanel.setDividerLocation(UserParameter.instance().training_rightSplitPane);
		mainPanel.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY,
				new DividerListener(DividerListener.training_rightSplitPane));

		mainPanel.setOpaque(false);
		add(mainPanel, BorderLayout.CENTER);
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

	private String getLangStr(String key) {
		return HOVerwaltung.instance().getLanguageString(key);
	}
}
