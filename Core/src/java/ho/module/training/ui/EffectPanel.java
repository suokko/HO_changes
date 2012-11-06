// %3525181034:hoplugins.trainingExperience.ui%
package ho.module.training.ui;

import ho.core.gui.CursorToolkit;
import ho.core.gui.IRefreshable;
import ho.core.gui.RefreshManager;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.model.HOVerwaltung;
import ho.module.training.EffectDAO;
import ho.module.training.TrainWeekEffect;
import ho.module.training.ui.model.EffectTableModel;
import ho.module.training.ui.renderer.SkillupsTableCellRenderer;

import java.awt.BorderLayout;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

/**
 * Shows a table with training effet.
 * 
 * @author NetHyperon
 */
public class EffectPanel extends JPanel {

	private static final long serialVersionUID = 6139712209582341384L;
	private JTable effectTable;
	private boolean initialized = false;
	private boolean needsRefresh = false;

	/**
	 * Creates a new AnalyzerPanel object.
	 */
	public EffectPanel() {
		super();
		addHierarchyListener(new HierarchyListener() {

			@Override
			public void hierarchyChanged(HierarchyEvent e) {
				if ((HierarchyEvent.SHOWING_CHANGED == (e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) && isShowing())) {
					if (!initialized) {
						initialize();
					}
					if (needsRefresh) {
						reload();
					}
				}
			}
		});
	}

	/**
	 * Reload data and redraw panel.
	 */
	private void reload() {
		CursorToolkit.startWaitCursor(this);
		try {
			EffectDAO.reload();
			setEffectModel(EffectDAO.getTrainEffect());
			this.needsRefresh = false;
		} finally {
			CursorToolkit.stopWaitCursor(this);
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
	}

	private void initialize() {
		CursorToolkit.startWaitCursor(this);
		try {
			initComponents();
			addListeners();
			reload();
			this.initialized = true;
		} finally {
			CursorToolkit.stopWaitCursor(this);
		}
	}

	/**
	 * Sets the model for effect table.
	 * 
	 * @param values
	 *            List of values
	 */
	private void setEffectModel(List<TrainWeekEffect> values) {
		effectTable.setModel(new EffectTableModel(values));
		effectTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		effectTable.getColumnModel().getColumn(1).setPreferredWidth(50);
		effectTable.getColumnModel().getColumn(4).setPreferredWidth(100);
		effectTable.getColumnModel().getColumn(6).setPreferredWidth(100);
		effectTable.getColumnModel().getColumn(7).setPreferredWidth(25);
		effectTable.getColumnModel().getColumn(7).setCellRenderer(new SkillupsTableCellRenderer());

		// Hide column 8
		effectTable.getTableHeader().getColumnModel().getColumn(8).setPreferredWidth(0);
		effectTable.getTableHeader().getColumnModel().getColumn(8).setMinWidth(0);
		effectTable.getTableHeader().getColumnModel().getColumn(8).setMaxWidth(0);
	}

	/**
	 * Initialize panel.
	 */
	private void initComponents() {
		setOpaque(false);
		setLayout(new BorderLayout());

		JPanel p = new ImagePanel();
		p.setOpaque(false);
		p.setLayout(new BorderLayout());

		JLabel label = new JLabel(
				HOVerwaltung.instance().getLanguageString("TAB_EFFECT"), SwingConstants.CENTER);

		label.setOpaque(false);
		p.add(label, BorderLayout.NORTH);

		JPanel mainpanel = new ImagePanel();
		mainpanel.setLayout(new BorderLayout());

		this.effectTable = new JTable();
		JScrollPane effectPane = new JScrollPane(this.effectTable);
		effectPane.setOpaque(false);
		mainpanel.add(effectPane, BorderLayout.CENTER);

		p.add(mainpanel, BorderLayout.CENTER);
		add(p, BorderLayout.CENTER);

		// Add legend panel.
		p.add(new TrainingLegendPanel(), BorderLayout.SOUTH);
		add(p, BorderLayout.CENTER);
	}
}
