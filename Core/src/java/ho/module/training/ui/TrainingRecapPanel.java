// %776182880:hoplugins.trainingExperience.ui%
package ho.module.training.ui;

import ho.core.gui.IRefreshable;
import ho.core.gui.RefreshManager;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.gui.comp.panel.LazyPanel;
import ho.core.gui.model.BaseTableModel;
import ho.core.model.HOVerwaltung;
import ho.core.model.UserParameter;
import ho.core.model.player.ISkillup;
import ho.core.model.player.Spieler;
import ho.core.training.FutureTrainingManager;
import ho.module.training.ui.model.ModelChange;
import ho.module.training.ui.model.ModelChangeListener;
import ho.module.training.ui.model.TrainingModel;
import ho.module.training.ui.renderer.TrainingRecapRenderer;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

/**
 * Recap Panel when future preview of skillups is shown ("Prediction" tab,
 * "Training Recap" table").
 * 
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class TrainingRecapPanel extends LazyPanel {

	private static final long serialVersionUID = 7240288702397251461L;
	private static final int fixedColumns = 3;
	private BaseTableModel tableModel;
	private TrainingRecapTable recapTable;
	private final TrainingModel model;
	private boolean initialized = false;
	private boolean needsRefresh = false;

	/**
	 * Creates a new TrainingRecapPanel object.
	 */
	public TrainingRecapPanel(TrainingModel model) {
		this.model = model;
	}

	@Override
	protected void initialize() {
		initComponents();
		addListeners();
		setNeedsRefresh(true);
	}

	@Override
	protected void update() {
		reload();
	}

	/**
	 * Reload the panel
	 */
	private void reload() {
		// empty the table
		List<String> columns = getColumns();
		List<Spieler> list = HOVerwaltung.instance().getModel().getAllSpieler();

		List<Vector<String>> players = new ArrayList<Vector<String>>();

		for (Spieler player : list) {
			FutureTrainingManager ftm = new FutureTrainingManager(player,
					this.model.getFutureTrainings(), this.model.getNumberOfCoTrainers(),
					this.model.getTrainerLevel());
			List<ISkillup> su = ftm.getFutureSkillups();

			// Skip player!
			if (su.size() == 0) {
				continue;
			}

			HashMap<String, ISkillup> maps = new HashMap<String, ISkillup>();

			for (Iterator<ISkillup> iterator = su.iterator(); iterator.hasNext();) {
				ISkillup skillup = iterator.next();
				maps.put(skillup.getHtSeason() + " " + skillup.getHtWeek(), skillup);
			}

			Vector<String> row = new Vector<String>();
			row.add(player.getName());
			row.add(player.getAlterWithAgeDaysAsString());
			row.add(Integer.toString(ftm.getTrainingSpeed()));

			for (int i = 0; i < UserParameter.instance().futureWeeks; i++) {
				ISkillup s = (ISkillup) maps.get(columns.get(i + fixedColumns));

				if (s == null) {
					row.add("");
				} else {
					row.add(s.getType() + " " + s.getValue());
				}
			}

			row.add(Integer.toString(player.getSpielerID()));
			players.add(row);
		}

		// Sort the players
		Collections.sort(players, new TrainingComparator(2, fixedColumns));

		// and add them to the model
		for (Vector<String> row : players) {
			tableModel.addRow(row);
		}
	}

	private void addListeners() {
		RefreshManager.instance().registerRefreshable(new IRefreshable() {

			@Override
			public void refresh() {
				if (isShowing()) {
					reload();
				} else {
					needsRefresh = true;
				}
			}
		});

		this.model.addModelChangeListener(new ModelChangeListener() {

			@Override
			public void modelChanged(ModelChange change) {
				if (change == ModelChange.ACTIVE_PLAYER) {
					selectPlayerFromModel();
				}
			}
		});
	}

	/**
	 * Get Columns name
	 * 
	 * @return List of string
	 */
	private Vector<String> getColumns() {
		Vector<String> columns = new Vector<String>();

		columns.add(HOVerwaltung.instance().getLanguageString("Spieler")); //$NON-NLS-1$
		columns.add(HOVerwaltung.instance().getLanguageString("ls.player.age")); //$NON-NLS-1$
		columns.add("Speed"); //$NON-NLS-1$

		int actualSeason = HOVerwaltung.instance().getModel().getBasics().getSeason();
		int actualWeek = HOVerwaltung.instance().getModel().getBasics().getSpieltag();

		// We are in the middle where season has not been updated!
		try {
			if (HOVerwaltung.instance().getModel().getXtraDaten().getTrainingDate()
					.after(HOVerwaltung.instance().getModel().getXtraDaten().getSeriesMatchDate())) {
				actualWeek++;

				if (actualWeek == 17) {
					actualWeek = 1;
					actualSeason++;
				}
			}
		} catch (Exception e1) {
			// Null when first time HO is launched
		}

		for (int i = 0; i < UserParameter.instance().futureWeeks; i++) {
			// calculate the week and season of the future training
			int week = (actualWeek + i) - 1;
			int season = actualSeason + (week / 16);

			week = (week % 16) + 1;

			columns.add(season + " " + week); //$NON-NLS-1$
		}

		columns.add(HOVerwaltung.instance().getLanguageString("ls.player.id")); //$NON-NLS-1$

		return columns;
	}

	/**
	 * Initialize the GUI
	 */
	private void initComponents() {
		setOpaque(false);
		setLayout(new BorderLayout());

		JPanel panel = new ImagePanel();
		panel.setOpaque(false);
		panel.setLayout(new BorderLayout());

		JLabel title = new JLabel(HOVerwaltung.instance().getLanguageString("Recap"),
				SwingConstants.CENTER);

		title.setOpaque(false);
		panel.add(title, BorderLayout.NORTH);

		Vector<String> columns = getColumns();
		tableModel = new BaseTableModel(new Vector<Object>(), columns);

		JTable table = new JTable(tableModel);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		recapTable = new TrainingRecapTable(table, fixedColumns);
		recapTable.getScrollTable().setDefaultRenderer(Object.class, new TrainingRecapRenderer());

		JTable scrollTable = recapTable.getScrollTable();

		// Hide the last column
		int lastSTCol = scrollTable.getColumnCount() - 1;

		scrollTable.getTableHeader().getColumnModel().getColumn(lastSTCol).setPreferredWidth(0);
		scrollTable.getTableHeader().getColumnModel().getColumn(lastSTCol).setMinWidth(0);
		scrollTable.getTableHeader().getColumnModel().getColumn(lastSTCol).setMaxWidth(0);

		JTable lockedTable = recapTable.getLockedTable();
		lockedTable.getSelectionModel().addListSelectionListener(
				new PlayerSelectionListener(this.model, scrollTable, lastSTCol));
		panel.add(recapTable, BorderLayout.CENTER);
		recapTable.getScrollTable().getTableHeader().setReorderingAllowed(false);

		// Add legend panel.
		panel.add(new TrainingLegendPanel(), BorderLayout.SOUTH);
		add(panel, BorderLayout.CENTER);
	}

	private void selectPlayerFromModel() {
		this.recapTable.getLockedTable().clearSelection();
		Spieler player = this.model.getActivePlayer();
		if (player != null) {
			for (int i = 0; i < this.tableModel.getRowCount(); i++) {
				String name = (String) this.tableModel.getValueAt(i, 0);
				if (player.getName().equals(name)) {
					int viewIndex = this.recapTable.getLockedTable().convertRowIndexToView(i);
					this.recapTable.getLockedTable().setRowSelectionInterval(viewIndex, viewIndex);
					break;
				}
			}
		}
	}
}
