package ho.module.specialEvents;

import ho.core.gui.IRefreshable;
import ho.core.gui.RefreshManager;
import ho.core.gui.comp.panel.ImagePanel;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class SpecialEventsPanel extends ImagePanel implements IRefreshable {

	private static final long serialVersionUID = 1L;
	private static SpecialEventsTable specialEventsTable;

	SpecialEventsPanel() {
		initialize();
	}

	private void initialize() {
		setLayout(new BorderLayout());
		Filter filter = new Filter();
		// TODO initial values for testing (settings should be save/restored
		// from DB later)
		filter.setShowMatchesWithSEOnly(false);
		filter.setSeasonFilterValue(SeasonFilterValue.LAST_TWO_SEASONS);
		
		filter.addFilterChangeListener(new FilterChangeListener() {

			@Override
			public void filterChanged(FilterChangeEvent evt) {
				SpecialEventsPanel.newTableModel();
			}
		});

		JPanel filterPanel = new FilterPanelNew(filter);
		specialEventsTable = new SpecialEventsTable(filter);
		specialEventsTable.setTableModel(specialEventsTable.getSEModel());
		JScrollPane matchArea = new JScrollPane(specialEventsTable);
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, filterPanel, matchArea);
		splitPane.setDividerSize(5);
		splitPane.setContinuousLayout(true);
		add(splitPane, BorderLayout.CENTER);
		RefreshManager.instance().registerRefreshable(this);
	}

	public void refresh() {
		newTableModel();
	}

	public static void newTableModel() {
		specialEventsTable.removeAll();
		specialEventsTable.setTableModel(specialEventsTable.getSEModel());
	}

}
