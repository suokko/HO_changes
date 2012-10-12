package ho.module.specialEvents;

import ho.core.gui.IRefreshable;
import ho.core.gui.RefreshManager;
import ho.core.gui.comp.panel.ImagePanel;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class SpecialEventsPanel extends ImagePanel implements IRefreshable {

	private static final long serialVersionUID = 1L;
	private static SpecialEventsTable specialEventsTable;
	private Filter filter = new Filter();

	SpecialEventsPanel() {
		initialize();
	}

	private void initialize() {
		setLayout(new BorderLayout());
		// TODO initial values for testing (settings should be save/restored
		// from DB later)
		filter.setShowMatchesWithSEOnly(false);
		filter.setSeasonFilterValue(SeasonFilterValue.LAST_TWO_SEASONS);

		filter.addFilterChangeListener(new FilterChangeListener() {

			@Override
			public void filterChanged(FilterChangeEvent evt) {
				setTableData();
			}
		});

		JPanel filterPanel = new FilterPanelNew(filter);
		specialEventsTable = new SpecialEventsTable(filter);
		specialEventsTable.getTableHeader().setReorderingAllowed(false);
		specialEventsTable.setDefaultRenderer(Object.class, new SpecialEventsTableRenderer());
		specialEventsTable.setModel(new SpecialEventsTableModel());
		specialEventsTable.getColumnModel().getColumn(SpecialEventsTableModel.MATCHDATECOLUMN)
				.setPreferredWidth(66);
		specialEventsTable.getColumnModel().getColumn(SpecialEventsTableModel.MATCHIDCOLUMN)
				.setPreferredWidth(66);
		specialEventsTable.getColumnModel().getColumn(SpecialEventsTableModel.HOMETACTICCOLUMN)
				.setPreferredWidth(37);
		specialEventsTable.getColumnModel().getColumn(SpecialEventsTableModel.HOMEEVENTCOLUMN)
				.setPreferredWidth(20);
		specialEventsTable.getColumnModel().getColumn(SpecialEventsTableModel.HOMETEAMCOLUMN)
				.setPreferredWidth(150);
		specialEventsTable.getColumnModel().getColumn(SpecialEventsTableModel.RESULTCOLUMN)
				.setPreferredWidth(40);
		specialEventsTable.getColumnModel().getColumn(SpecialEventsTableModel.AWAYTEAMCOLUMN)
				.setPreferredWidth(150);
		specialEventsTable.getColumnModel().getColumn(SpecialEventsTableModel.AWAYEVENTCOLUMN)
				.setPreferredWidth(20);
		specialEventsTable.getColumnModel().getColumn(SpecialEventsTableModel.AWAYTACTICCOLUMN)
				.setPreferredWidth(37);
		specialEventsTable.getColumnModel().getColumn(SpecialEventsTableModel.MINUTECOLUMN)
				.setPreferredWidth(27);
		specialEventsTable.getColumnModel().getColumn(SpecialEventsTableModel.CHANCECOLUMN)
				.setPreferredWidth(22);
		specialEventsTable.getColumnModel().getColumn(SpecialEventsTableModel.EVENTTYPCOLUMN)
				.setPreferredWidth(23);
		specialEventsTable.getColumnModel().getColumn(SpecialEventsTableModel.SETEXTCOLUMN)
				.setPreferredWidth(270);
		specialEventsTable.getColumnModel().getColumn(SpecialEventsTableModel.NAMECOLUMN)
				.setPreferredWidth(200);
		specialEventsTable.getColumnModel().getColumn(SpecialEventsTableModel.HIDDENCOLUMN)
				.setPreferredWidth(0);
		specialEventsTable.setRowHeight(20);
		setTableData();
		
		JScrollPane matchArea = new JScrollPane(specialEventsTable);
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, filterPanel, matchArea);
		splitPane.setDividerSize(5);
		splitPane.setContinuousLayout(true);
		add(splitPane, BorderLayout.CENTER);
		RefreshManager.instance().registerRefreshable(this);
	}

	@Override
	public void refresh() {
		setTableData();
	}

	private void setTableData() {
		SpecialEventsDM specialEventsDM = new SpecialEventsDM(this.filter);
		List<List<Object>> matches = specialEventsDM.holeInfos(
				!this.filter.isShowMatchesWithSEOnly(), this.filter.getSeasonFilterValue(), true);
		specialEventsTable.setHighlightTexte(specialEventsDM.getHighlightText());
		((SpecialEventsTableModel) specialEventsTable.getModel()).setData(matches);
	}
}
