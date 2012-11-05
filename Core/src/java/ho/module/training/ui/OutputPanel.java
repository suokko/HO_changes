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
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

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
	private JButton importButton;
	private JButton calculateButton;
	private boolean initialized = false;
	private final TrainingModel model;

	/**
	 * Creates a new OutputPanel object.
	 */
	public OutputPanel(TrainingModel model) {
		super();
		this.model = model;

		addHierarchyListener(new HierarchyListener() {

			@Override
			public void hierarchyChanged(HierarchyEvent e) {
				if ((HierarchyEvent.SHOWING_CHANGED == (e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) && isShowing())) {
					if (!initialized) {
						initialize();
					}
				}
			}
		});
	}

	/**
	 * update the panel with the new value
	 */
	public void reload() {
		if (this.initialized) {
			((OutputTableModel) outputTable.getModel()).fillWithData();
		}
	}

	private void initialize() {
		initComponents();
		addListeners();
		this.initialized = true;
		reload();		
	}

	/**
	 * Import a match from Hattrick
	 */
	private void importMatches() {
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

	private void addListeners() {
		this.importButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				importMatches();
			}
		});

		this.calculateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				TrainingManager.instance().recalcSubskills(true);
				reload();
				ho.module.training.TrainingPanel.getTabbedPanel().getRecap().reload();
			}
		});
	}

	/**
	 * Initialize the object layout
	 */
	private void initComponents() {
		setLayout(new BorderLayout());

		outputTable = new OutputTable(new OutputTableModel(this.model));
		outputTable.getTableHeader().setReorderingAllowed(false);
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
		outputTable.setAutoCreateRowSorter(true);

		add(new JScrollPane(outputTable), BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel(new GridBagLayout());

		this.importButton = new JButton(HOVerwaltung.instance().getLanguageString("ImportMatch"));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(6, 8, 6, 4);
		buttonPanel.add(this.importButton, gbc);

		this.calculateButton = new JButton(HOVerwaltung.instance().getLanguageString("Calculate"));
		gbc.gridx = 1;
		gbc.weightx = 1.0;
		gbc.insets = new Insets(6, 4, 6, 8);
		buttonPanel.add(this.calculateButton, gbc);

		add(buttonPanel, BorderLayout.NORTH);
	}

	private class OutputTable extends JTable {
		private static final long serialVersionUID = 1089805262735794338L;

		public OutputTable(TableModel dm) {
			super(dm);
		}

		@Override
		public String getToolTipText(MouseEvent e) {
			OutputTableModel tableModel = (OutputTableModel) getModel();
			Point p = e.getPoint();
			int realColumnIndex = convertColumnIndexToModel(columnAtPoint(p));
			int realRowIndex = convertRowIndexToModel(rowAtPoint(p));

			if ((realColumnIndex > 2) && (realColumnIndex < 11)) {
				Object obj = tableModel.getToolTipAt(realRowIndex, realColumnIndex);

				return obj.toString();
			}

			return "";
		}
	}
}
