package ho.module.training;

import ho.core.gui.IRefreshable;
import ho.core.gui.RefreshManager;
import ho.core.model.HOModel;
import ho.core.model.HOVerwaltung;
import ho.core.model.UserParameter;
import ho.core.model.player.Spieler;
import ho.module.training.ui.MainPanel;
import ho.module.training.ui.PlayerDetailPanel;
import ho.module.training.ui.SkillupPanel;
import ho.module.training.ui.StaffPanel;
import ho.module.training.ui.comp.DividerListener;
import ho.module.training.ui.model.TrainingModel;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class TrainingPanel extends JPanel {

	private static final long serialVersionUID = -1313192105835561643L;
	/** Main table panel */
	private static MainPanel tabbedPanel;
	/** Prevision table */
	private static PlayerDetailPanel playerDetailPanel;
	/** Table of past skillups */
	private static SkillupPanel skillupPanel;
	/** Table of old past and future trainings */
	private static ho.module.training.ui.TrainingPanel trainPanel;
	/** Staff panel */
	private static StaffPanel staffPanel;
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
		skillupPanel = new SkillupPanel(this.model);
		playerDetailPanel = new PlayerDetailPanel(this.model);
		trainPanel = new ho.module.training.ui.TrainingPanel();
		staffPanel = new StaffPanel(this.model);
		tabbedPanel = new MainPanel(this.model);

		JSplitPane leftPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, skillupPanel, staffPanel);

		leftPane.setResizeWeight(1);
		leftPane.setDividerLocation(UserParameter.instance().training_lowerLeftSplitPane); //$NON-NLS-1$
		leftPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY,
				new DividerListener(DividerListener.training_lowerLeftSplitPane)); //$NON-NLS-1$

		JSplitPane bottomPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPane,
				new JScrollPane(playerDetailPanel));

		bottomPanel.setDividerLocation(UserParameter.instance().training_bottomSplitPane); //$NON-NLS-1$
		bottomPanel.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY,
				new DividerListener(DividerListener.training_bottomSplitPane)); //$NON-NLS-1$

		JSplitPane splitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tabbedPanel, bottomPanel);

		splitPanel.setDividerLocation(UserParameter.instance().training_mainSplitPane); //$NON-NLS-1$
		splitPanel.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY,
				new DividerListener(DividerListener.training_mainSplitPane)); //$NON-NLS-1$

		JSplitPane mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPanel, trainPanel);

		mainPanel.setDividerLocation(UserParameter.instance().training_rightSplitPane); //$NON-NLS-1$
		mainPanel.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY,
				new DividerListener(DividerListener.training_rightSplitPane)); //$NON-NLS-1$

		mainPanel.setOpaque(false);
		add(mainPanel, BorderLayout.CENTER);
	}

	/**
	 * Returns the Training Panel where the past and future training are shown
	 * 
	 * @return
	 */
	public static ho.module.training.ui.TrainingPanel getTrainPanel() {
		return trainPanel;
	}

	/**
	 * When called by HO reload everything!
	 */
	private void update() {
		// reset the selected player
		this.model.setActivePlayer(null);

		// reload the trainingPanel
		trainPanel.reload();

		// reload the staff, could have changed
		setStaffInTrainingModel(this.model);

		// recalculate and update the main panel
		tabbedPanel.reload();

		// and finally recalculate the player previsions
		refreshPlayerDetail();
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
	 * Refresh all the previsions, this is used when we haven't downloaded
	 * anything from HT but the user has changed something in the staff or in
	 * the future training
	 */
	public static void refreshPlayerDetail() {
		// reload the past skillups
		skillupPanel.reload(model.getActivePlayer());

		// recalculate and show the previsions
		playerDetailPanel.reload(model.getActivePlayer());

		// update upper table!
		tabbedPanel.getOutput().reload();
		tabbedPanel.getRecap().reload();
	}

	/**
	 * Sets the new active player and recalculate everything
	 * 
	 * @param player
	 *            the new selected player
	 */
	public static void selectPlayer(Spieler player) {
		model.setActivePlayer(player);

		// recalculate and show the previsions
		playerDetailPanel.reload(model.getActivePlayer());
	}

	/**
	 * Return the main tab panel
	 * 
	 * @return MainPanel
	 */
	public static MainPanel getTabbedPanel() {
		return tabbedPanel;
	}
}
