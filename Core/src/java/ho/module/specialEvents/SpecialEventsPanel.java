package ho.module.specialEvents;

import static ho.module.specialEvents.SpecialEventsTableModel.AWAYEVENTCOLUMN;
import static ho.module.specialEvents.SpecialEventsTableModel.AWAYTACTICCOLUMN;
import static ho.module.specialEvents.SpecialEventsTableModel.AWAYTEAMCOLUMN;
import static ho.module.specialEvents.SpecialEventsTableModel.CHANCECOLUMN;
import static ho.module.specialEvents.SpecialEventsTableModel.EVENTTYPCOLUMN;
import static ho.module.specialEvents.SpecialEventsTableModel.HOMEEVENTCOLUMN;
import static ho.module.specialEvents.SpecialEventsTableModel.HOMETACTICCOLUMN;
import static ho.module.specialEvents.SpecialEventsTableModel.HOMETEAMCOLUMN;
import static ho.module.specialEvents.SpecialEventsTableModel.MATCHDATECOLUMN;
import static ho.module.specialEvents.SpecialEventsTableModel.MATCHIDCOLUMN;
import static ho.module.specialEvents.SpecialEventsTableModel.MATCHTYPECOLUMN;
import static ho.module.specialEvents.SpecialEventsTableModel.MINUTECOLUMN;
import static ho.module.specialEvents.SpecialEventsTableModel.NAMECOLUMN;
import static ho.module.specialEvents.SpecialEventsTableModel.SETEXTCOLUMN;
import ho.core.gui.ApplicationClosingListener;
import ho.core.gui.HOMainFrame;
import ho.core.gui.comp.panel.LazyImagePanel;
import ho.module.specialEvents.filter.Filter;
import ho.module.specialEvents.filter.FilterChangeEvent;
import ho.module.specialEvents.filter.FilterChangeListener;
import ho.module.specialEvents.filter.FilterHelper;
import ho.module.specialEvents.table.ChanceTableCellRenderer;
import ho.module.specialEvents.table.DateTableCellRenderer;
import ho.module.specialEvents.table.DefaultSETableCellRenderer;
import ho.module.specialEvents.table.EventTypeTableCellRenderer;
import ho.module.specialEvents.table.MatchTypeTableCellRenderer;
import ho.module.specialEvents.table.PlayerNameTableCellRenderer;
import ho.module.specialEvents.table.SETypeTableCellRenderer;
import ho.module.specialEvents.table.TacticsTableCellRenderer;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class SpecialEventsPanel extends LazyImagePanel {

	private static final long serialVersionUID = 1L;
	private static SpecialEventsTable specialEventsTable;
	private Filter filter;

	@Override
	protected void initialize() {
		initComponents();
		registerRefreshable(true);
		setNeedsRefresh(true);
	}

	@Override
	protected void update() {
		setTableData();
	}

	private void initComponents() {
		this.filter = new Filter();
		FilterHelper.loadSettings(this.filter);
		setLayout(new BorderLayout());

		this.filter.addFilterChangeListener(new FilterChangeListener() {

			@Override
			public void filterChanged(FilterChangeEvent evt) {
				setTableData();
			}
		});

		HOMainFrame.instance().addApplicationClosingListener(new ApplicationClosingListener() {

			@Override
			public void applicationClosing() {
				FilterHelper.saveSettings(filter);
			}
		});

		JPanel filterPanel = new FilterPanel(filter);
		specialEventsTable = new SpecialEventsTable();
		specialEventsTable.getTableHeader().setReorderingAllowed(false);
		SpecialEventsTableModel tblModel = new SpecialEventsTableModel();

		specialEventsTable.setModel(tblModel);
		TableColumnModel columnModel = specialEventsTable.getColumnModel();
		specialEventsTable.setDefaultRenderer(Object.class, new DefaultSETableCellRenderer());
		TacticsTableCellRenderer tacticsTableCellRenderer = new TacticsTableCellRenderer();

		TableColumn matchDateColumn = columnModel.getColumn(MATCHDATECOLUMN);
		matchDateColumn.setPreferredWidth(75);
		matchDateColumn.setCellRenderer(new DateTableCellRenderer());

		columnModel.getColumn(MATCHIDCOLUMN).setPreferredWidth(66);

		TableColumn matchTypeColumn = columnModel.getColumn(MATCHTYPECOLUMN);
		matchTypeColumn.setMaxWidth(25);
		matchTypeColumn.setPreferredWidth(25);
		matchTypeColumn.setCellRenderer(new MatchTypeTableCellRenderer());

		columnModel.getColumn(HOMETACTICCOLUMN).setPreferredWidth(37);

		TableColumn homeTacticColumn = columnModel.getColumn(HOMETACTICCOLUMN);
		homeTacticColumn.setCellRenderer(tacticsTableCellRenderer);

		TableColumn homeEventColumn = columnModel.getColumn(HOMEEVENTCOLUMN);
		homeEventColumn.setMaxWidth(20);
		homeEventColumn.setPreferredWidth(20);
		homeEventColumn.setCellRenderer(new SETypeTableCellRenderer(false));

		TableColumn homeTeamColumn = columnModel.getColumn(HOMETEAMCOLUMN);
		homeTeamColumn.setPreferredWidth(150);

		TableColumn resultColumn = columnModel.getColumn(SpecialEventsTableModel.RESULTCOLUMN);
		resultColumn.setPreferredWidth(40);

		TableColumn awayTeamColumn = columnModel.getColumn(AWAYTEAMCOLUMN);
		awayTeamColumn.setPreferredWidth(150);

		TableColumn awayEventColumn = columnModel.getColumn(AWAYEVENTCOLUMN);
		awayEventColumn.setMaxWidth(20);
		awayEventColumn.setPreferredWidth(20);
		awayEventColumn.setCellRenderer(new SETypeTableCellRenderer(true));

		TableColumn awayTacticColumn = columnModel.getColumn(AWAYTACTICCOLUMN);
		awayTacticColumn.setPreferredWidth(37);
		awayTacticColumn.setCellRenderer(tacticsTableCellRenderer);

		TableColumn minuteColumn = columnModel.getColumn(MINUTECOLUMN);
		minuteColumn.setPreferredWidth(27);

		TableColumn chanceColumn = columnModel.getColumn(CHANCECOLUMN);
		chanceColumn.setMaxWidth(23);
		chanceColumn.setPreferredWidth(23);
		chanceColumn.setCellRenderer(new ChanceTableCellRenderer());

		TableColumn eventTypeColumn = columnModel.getColumn(EVENTTYPCOLUMN);
		eventTypeColumn.setMaxWidth(23);
		eventTypeColumn.setPreferredWidth(23);
		eventTypeColumn.setCellRenderer(new EventTypeTableCellRenderer());

		TableColumn settExtColumn = columnModel.getColumn(SETEXTCOLUMN);
		settExtColumn.setPreferredWidth(270);

		TableColumn nameColumn = columnModel.getColumn(NAMECOLUMN);
		nameColumn.setPreferredWidth(200);
		nameColumn.setCellRenderer(new PlayerNameTableCellRenderer());
		specialEventsTable.setRowHeight(20);

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, filterPanel,
				new JScrollPane(specialEventsTable));
		splitPane.setDividerSize(5);
		splitPane.setContinuousLayout(true);
		add(splitPane, BorderLayout.CENTER);
	}

	private void setTableData() {
		SpecialEventsDM specialEventsDM = new SpecialEventsDM();
		((SpecialEventsTableModel) specialEventsTable.getModel()).setData(specialEventsDM
				.getRows(this.filter));
	}
}
