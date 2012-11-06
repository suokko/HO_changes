// %1126721451963:hoplugins.trainingExperience.ui%
package ho.module.training.ui;

import ho.core.gui.comp.panel.ImagePanel;
import ho.core.model.HOVerwaltung;
import ho.core.model.UserParameter;
import ho.core.training.TrainingManager;
import ho.module.training.ui.comp.DividerListener;
import ho.module.training.ui.comp.FutureSettingPanel;
import ho.module.training.ui.model.FutureTrainingsTableModel;
import ho.module.training.ui.model.PastTrainingsTableModel;
import ho.module.training.ui.model.TrainingModel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellEditor;

/**
 * Panel where past and future training are shown
 */
public class TrainingPanel extends JPanel {

	private static final long serialVersionUID = 5456485854278251422L;
	/** Future trainings table model */
	private FutureTrainingsTableModel futureModel;
	/** Past trainings table model */
	private PastTrainingsTableModel oldTableModel;
	private JTable futureTable;
	private JButton setAllButton;
	private final TrainingModel model;

	/**
	 * Creates a new TrainingPanel object.
	 */
	public TrainingPanel(TrainingModel model) {
		super();
		this.model = model;
		initComponents();
		addListeners();
	}

	/**
	 * Populate the table is called everytime a refresh command is issued
	 */
	public void reload() {
		oldTableModel.populate(TrainingManager.instance().getTrainingWeekList());
		futureModel.populate(this.model.getFutureTrainings());
		updateUI();
	}

	private void addListeners() {
		this.setAllButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				TableCellEditor editor = futureTable.getCellEditor();

				if (editor != null) {
					editor.stopCellEditing();
				}

				JOptionPane.showMessageDialog((JFrame) getTopLevelAncestor(),
						new FutureSettingPanel(model, futureModel), HOVerwaltung.instance()
								.getLanguageString("SetAll"),
						JOptionPane.PLAIN_MESSAGE);
			}
		});
	}
	
	/**
	 * Initialize the object layout
	 */
	private void initComponents() {

		// create and populate the old training table
		oldTableModel = new PastTrainingsTableModel();
		JTable oldTable = new TrainingTable(oldTableModel);
		oldTableModel.populate(TrainingManager.instance().getTrainingWeekList());

		JScrollPane scrollPane = new JScrollPane(oldTable);

		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		// create and populate the future training table
		futureModel = new FutureTrainingsTableModel(this.model);
		futureTable = new TrainingTable(futureModel);
		futureModel.populate(this.model.getFutureTrainings());

		JScrollPane scrollPane2 = new JScrollPane(futureTable);

		scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane2
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		// Organize the panel layout
		JPanel panel2 = new ImagePanel();
		panel2.setLayout(new BorderLayout());
		panel2.setOpaque(false);

		JLabel l = new JLabel(
				HOVerwaltung.instance().getLanguageString("FutureTrainings"), SwingConstants.CENTER);
		l.setOpaque(false);

		this.setAllButton = new JButton(HOVerwaltung.instance().getLanguageString("SetAll"));
		panel2.add(this.setAllButton, BorderLayout.EAST);
		panel2.add(l, BorderLayout.CENTER);

		JSplitPane pane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panel2, scrollPane2);
		pane2.setDividerLocation(25);
		pane2.setDividerSize(0);

		JSplitPane pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane, pane2);

		pane.setDividerLocation(UserParameter.instance().training_splitPane);
		pane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, new DividerListener(
				DividerListener.training_splitPane));
		pane.setDividerSize(1);
		setLayout(new BorderLayout());
		setOpaque(false);
		add(pane, BorderLayout.CENTER);
	}
}
