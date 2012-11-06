// %1126721451963:hoplugins.trainingExperience.ui%
package ho.module.training.ui;

import ho.core.model.HOVerwaltung;
import ho.core.training.TrainingManager;
import ho.module.training.ui.comp.FutureSettingPanel;
import ho.module.training.ui.model.FutureTrainingsTableModel;
import ho.module.training.ui.model.PastTrainingsTableModel;
import ho.module.training.ui.model.TrainingModel;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.table.TableCellEditor;

/**
 * Panel where past and future training are shown
 */
public class TrainingPanel extends JPanel {

	private static final long serialVersionUID = 5456485854278251422L;
	/** Future trainings table model */
	private FutureTrainingsTableModel futureTrainingsTableModel;
	/** Past trainings table model */
	private PastTrainingsTableModel pastTrainingsTableModel;
	private JTable futureTrainingsTable;
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
		pastTrainingsTableModel.populate(TrainingManager.instance().getTrainingWeekList());
		futureTrainingsTableModel.populate(this.model.getFutureTrainings());
	}

	private void addListeners() {
		this.setAllButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				TableCellEditor editor = futureTrainingsTable.getCellEditor();

				if (editor != null) {
					editor.stopCellEditing();
				}

				JOptionPane.showMessageDialog((JFrame) getTopLevelAncestor(),
						new FutureSettingPanel(model, futureTrainingsTableModel), HOVerwaltung
								.instance().getLanguageString("SetAll"), JOptionPane.PLAIN_MESSAGE);
			}
		});
	}

	/**
	 * Initialize the object layout
	 */
	private void initComponents() {
		JPanel pastTrainingsPanel = new JPanel(new GridBagLayout());
		GridBagConstraints uGbc = new GridBagConstraints();
		uGbc.anchor = GridBagConstraints.WEST;
		uGbc.insets = new Insets(3, 3, 3, 3);

		JLabel pastTrainingsLabel = new JLabel();
		pastTrainingsLabel.setText(HOVerwaltung.instance().getLanguageString("PastTrainings"));
		uGbc.gridx = 0;
		uGbc.gridy = 0;
		pastTrainingsPanel.add(pastTrainingsLabel, uGbc);

		this.pastTrainingsTableModel = new PastTrainingsTableModel();
		JTable pastTrainingsTable = new TrainingTable(this.pastTrainingsTableModel);
		JScrollPane upperScrollPane = new JScrollPane(pastTrainingsTable);
		upperScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		uGbc.gridy = 1;
		uGbc.weightx = 1.0;
		uGbc.weighty = 1.0;
		uGbc.fill = GridBagConstraints.BOTH;
		pastTrainingsPanel.add(upperScrollPane, uGbc);

		JPanel futureTrainingsPanel = new JPanel(new GridBagLayout());
		GridBagConstraints lGbc = new GridBagConstraints();
		lGbc.anchor = GridBagConstraints.WEST;
		lGbc.insets = new Insets(3, 3, 3, 3);

		JLabel futureTrainingLabel = new JLabel();
		futureTrainingLabel.setText(HOVerwaltung.instance().getLanguageString("FutureTrainings"));
		lGbc.gridx = 0;
		lGbc.gridy = 0;
		futureTrainingsPanel.add(futureTrainingLabel, lGbc);

		this.setAllButton = new JButton(HOVerwaltung.instance().getLanguageString("SetAll"));
		lGbc.gridx = 1;
		lGbc.anchor = GridBagConstraints.EAST;
		futureTrainingsPanel.add(this.setAllButton, lGbc);

		this.futureTrainingsTableModel = new FutureTrainingsTableModel(this.model);
		this.futureTrainingsTable = new TrainingTable(this.futureTrainingsTableModel);
		JScrollPane lowerScrollPane = new JScrollPane(this.futureTrainingsTable);
		lowerScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		lGbc.gridx = 0;
		lGbc.gridy = 1;
		lGbc.gridwidth = 2;
		lGbc.weightx = 1.0;
		lGbc.weighty = 1.0;
		lGbc.fill = GridBagConstraints.BOTH;
		futureTrainingsPanel.add(lowerScrollPane, lGbc);

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, pastTrainingsPanel,
				futureTrainingsPanel);
		setLayout(new BorderLayout());
		add(splitPane, BorderLayout.CENTER);
	}
}
