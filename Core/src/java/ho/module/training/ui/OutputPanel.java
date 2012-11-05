// %3839090226:hoplugins.trainingExperience.ui%
package ho.module.training.ui;

import ho.core.gui.RefreshManager;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.model.HOVerwaltung;
import ho.core.model.match.MatchType;
import ho.core.net.OnlineWorker;
import ho.core.training.TrainingManager;
import ho.core.util.HOLogger;
import ho.core.util.Helper;
import ho.core.util.HelperWrapper;
import ho.module.training.ui.model.OutputTableModel;
import ho.module.training.ui.model.TrainingModel;
import ho.module.training.ui.renderer.OutputTableRenderer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

/**
 * The Panel where the main training table is shown ("Training").
 * 
 * <p>
 * TODO Costomize to show only players that received training?
 * </p>
 * 
 * <p>
 * TODO Maybe i want to test for players that haven't received trainings to
 * preview effect of change of training.
 * </p>
 * 
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class OutputPanel extends ImagePanel {

	private static final long serialVersionUID = 7955126207696897546L;
	private JTable outputTable;
	private OutputTableSorter sorter;
	private final TrainingModel model;

	/**
	 * Creates a new OutputPanel object.
	 */
	public OutputPanel(TrainingModel model) {
		super();
		this.model = model;
		initComponents();
	}

	/**
	 * update the panel with the new value
	 */
	public void reload() {
		OutputTableSorter otm = (OutputTableSorter) outputTable.getModel();
		otm.fillWithData();
	}

	/**
	 * Import a match from Hattrick
	 */
	private void import_matches() {
		String input = JOptionPane.showInputDialog(HOVerwaltung.instance().getLanguageString(
				"GameID"));

		try {
			if (input != null) {
				input = input.trim();
			}
			Integer matchID = new Integer(input);

			if (HelperWrapper.instance().isUserMatch(input, MatchType.LEAGUE)) {
				if (OnlineWorker.downloadMatchData(matchID.intValue(), MatchType.LEAGUE, false)) {
					Helper.showMessage(null,
							HOVerwaltung.instance().getLanguageString("MatchImported"),
							HOVerwaltung.instance().getLanguageString("ImportOK"), 1);
					RefreshManager.instance().doRefresh();
				}
			} else {
				Helper.showMessage(null, HOVerwaltung.instance().getLanguageString("NotUserMatch"),
						HOVerwaltung.instance().getLanguageString("ImportError"), 1);
			}
		} catch (Exception e) {
			Helper.showMessage(null, HOVerwaltung.instance().getLanguageString("MatchNotImported"),
					HOVerwaltung.instance().getLanguageString("ImportError"), 1);
			HOLogger.instance().log(OutputPanel.class, e);
		}
	}

	/**
	 * Initialize the object layout
	 */
	private void initComponents() {
		setLayout(new BorderLayout());
		OutputTableModel outputTableModel = new OutputTableModel(this.model);

		sorter = new OutputTableSorter(outputTableModel);
		outputTable = new OutputTable(sorter);
		outputTable.getTableHeader().setReorderingAllowed(false);
		sorter.setTableHeader(outputTable.getTableHeader());
		outputTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		outputTable.setDefaultRenderer(Object.class, new OutputTableRenderer());
		outputTable.getSelectionModel().addListSelectionListener(
				new PlayerSelectionListener(outputTable, 11));

		for (int i = 0; i < outputTable.getColumnCount(); i++) {
			TableColumn column = outputTable.getColumnModel().getColumn(i);

			switch (i) {
			case 0:
				column.setPreferredWidth(150);
				break;
			case 1:
				column.setPreferredWidth(43);
				break;
			case 2:
				column.setPreferredWidth(100);
				break;
			default:
				column.setPreferredWidth(70);
			}
		}

		// Hide column 11 (playerId)
		TableColumn playerIDCol = outputTable.getTableHeader().getColumnModel().getColumn(11);
		playerIDCol.setPreferredWidth(0);
		playerIDCol.setMinWidth(0);
		playerIDCol.setMaxWidth(0);
		outputTable.setAutoResizeMode(0);
		outputTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
		add(new JScrollPane(outputTable), BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel(new GridBagLayout());

		JButton importButton = new JButton(HOVerwaltung.instance().getLanguageString("ImportMatch"));
		importButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				import_matches();
			}
		});
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(6, 8, 6, 4);
		buttonPanel.add(importButton, gbc);

		JButton calculateButton = new JButton(HOVerwaltung.instance()
				.getLanguageString("Calculate"));
		calculateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				TrainingManager.instance().recalcSubskills(true);
				reload();
				ho.module.training.TrainingPanel.getTabbedPanel().getRecap().reload();
			}
		});

		gbc.gridx = 1;
		gbc.weightx = 1.0;
		gbc.insets = new Insets(6, 4, 6, 8);
		buttonPanel.add(calculateButton, gbc);

		add(buttonPanel, BorderLayout.NORTH);
	}
}
