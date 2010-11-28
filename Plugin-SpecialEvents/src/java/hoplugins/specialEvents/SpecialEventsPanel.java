package hoplugins.specialEvents;

import hoplugins.commons.utils.PluginProperty;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import plugins.IDebugWindow;
import plugins.IHOMiniModel;

public class SpecialEventsPanel extends JTable {

	private static final long serialVersionUID = 8656004206333977669L;

	public static final int MATCHDATECOLUMN = 0;
	public static final int MATCHIDCOLUMN = 1;
	public static final int HOMETACTICCOLUMN = 2;
	public static final int HOMEEVENTCOLUMN = 3;
	public static final int HOMETEAMCOLUMN = 4;
	public static final int RESULTCOLUMN = 5;
	public static final int AWAYTEAMCOLUMN = 6;
	public static final int AWAYEVENTCOLUMN = 7;
	public static final int AWAYTACTICCOLUMN = 8;
	public static final int MINUTECOLUMN = 9;
	public static final int CHANCECOLUMN = 10;
	public static final int EVENTTYPCOLUMN = 11;
	public static final int SETEXTCOLUMN = 12;
	public static final int NAMECOLUMN = 13;
	public static final int HIDDENCOLUMN = 14;
	public static final int NUMCOLUMNS = 15;

	// private Properties props;
	private IHOMiniModel miniModel;
	private String columnNames[];
	private Vector<String> highlightTexte;

	public SpecialEventsPanel(IHOMiniModel miniModel) {
		columnNames = new String[NUMCOLUMNS];
		this.miniModel = miniModel;
		setColumnHeaders();
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // AUTO_RESIZE_ALL_COLUMNS
		getTableHeader().setReorderingAllowed(false);
		try {
			setDefaultRenderer(Object.class, new SpecialEventsTableRenderer());
			TableModel table = getSEModel(miniModel);
			setModel(table);
		} catch (RuntimeException e) {
			showDebug(e);
		}
	}

	private void setColumnHeaders() {
		columnNames[MATCHDATECOLUMN] = PluginProperty.getString("Datum");
		columnNames[MATCHIDCOLUMN] = PluginProperty.getString("MatchId");
		columnNames[HOMETACTICCOLUMN] = PluginProperty.getString("Taktik");
		columnNames[HOMEEVENTCOLUMN] = "";
		columnNames[HOMETEAMCOLUMN] = PluginProperty.getString("Heim");
		columnNames[RESULTCOLUMN] = "";
		columnNames[AWAYTEAMCOLUMN] = PluginProperty.getString("Gast");
		columnNames[AWAYEVENTCOLUMN] = "";
		columnNames[AWAYTACTICCOLUMN] = PluginProperty.getString("Taktik");
		columnNames[MINUTECOLUMN] = PluginProperty.getString("Min");
		columnNames[CHANCECOLUMN] = "";
		columnNames[EVENTTYPCOLUMN] = "";
		columnNames[SETEXTCOLUMN] = PluginProperty.getString("Event");
		columnNames[NAMECOLUMN] = PluginProperty.getString("Spieler");
		columnNames[HIDDENCOLUMN] = "";
	}

	@Override
	protected JTableHeader createDefaultTableHeader() {
		return new JTableHeader(columnModel) {

			private static final long serialVersionUID = 203261496086729638L;

			@Override
			public String getToolTipText(MouseEvent e) {
				String tip = null;
				Point p = e.getPoint();
				int index = columnModel.getColumnIndexAtX(p.x);
				int realIndex = columnModel.getColumn(index).getModelIndex();
				if (realIndex == HOMEEVENTCOLUMN || realIndex == AWAYEVENTCOLUMN) {
					tip = PluginProperty.getString("Tip4");
				} else {
					tip = (columnNames[realIndex] != null && columnNames[realIndex].length() > 0) ? columnNames[realIndex] : null;
					
				}
				return tip;
			}

		};
	}

	public TableModel getSEModel(IHOMiniModel miniModel) {
		SpecialEventsDM specialEventsDM = new SpecialEventsDM(miniModel);
		Vector matches = specialEventsDM.holeInfos(FilterPanel.getGameTypAll().isSelected(), //
				FilterPanel.getSaisonTyp(), FilterPanel.showFriendlies());
		highlightTexte = specialEventsDM.getHighlightText();
		TableModel tableModel = new SpecialEventsTableModel(matches, new Vector<String>(Arrays.asList(columnNames)));
		return tableModel;
	}

	@Override
	public String getToolTipText(MouseEvent e) {
		String tip = null;
		Point p = e.getPoint();
		int rowIndex = rowAtPoint(p);
		int colIndex = columnAtPoint(p);
		int realColumnIndex = convertColumnIndexToModel(colIndex);
		if (realColumnIndex == HOMEEVENTCOLUMN || realColumnIndex == AWAYEVENTCOLUMN) {
			tip = PluginProperty.getString("Tip4");
		}
		if (realColumnIndex == NAMECOLUMN) {
			tip = PluginProperty.getString("TipName");
		}
		if (realColumnIndex == EVENTTYPCOLUMN) {
			String highlightText = "<table width='300'><tr><td>" + (String) highlightTexte.elementAt(rowIndex) + "</td></tr></table>";
			String text = "<html>" + highlightText + "</html>";
			tip = text;
		}
		return tip;
	}

	public void setTableModel(TableModel tm) {
		setModel(tm);
		setAutoscrolls(true);
		setTableSize();
	}

	private void setTableSize() {
		columnWidth(MATCHDATECOLUMN, 64, 34);
		columnWidth(MATCHIDCOLUMN, 64, 34);
		columnWidth(HOMETACTICCOLUMN, 34, 22);
		columnWidth(HOMEEVENTCOLUMN, 20, 20);
		columnWidth(HOMETEAMCOLUMN, 150, 100);
		columnWidth(RESULTCOLUMN, 40, 20);
		columnWidth(AWAYTEAMCOLUMN, 150, 100);
		columnWidth(AWAYEVENTCOLUMN, 20, 20);
		columnWidth(AWAYTACTICCOLUMN, 34, 22);
		columnWidth(MINUTECOLUMN, 27, 27);
		columnWidth(CHANCECOLUMN, 20, 20);
		columnWidth(EVENTTYPCOLUMN, 20, 20);
		columnWidth(SETEXTCOLUMN, 270, 140);
		columnWidth(NAMECOLUMN, 200, 200);
		columnWidth(HIDDENCOLUMN, 0, 0);
		setRowHeight(20);
	}

	private void columnWidth(int col, int width, int minWidth) {
		getColumnModel().getColumn(col).setMaxWidth(width);
		getColumnModel().getColumn(col).setMinWidth(minWidth);
		getColumnModel().getColumn(col).setWidth(width);
		getColumnModel().getColumn(col).setPreferredWidth(width);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int mColIndex) {
		return false;
	}

	private void showDebug(Exception exr) {
		// exr.printStackTrace();
		IDebugWindow debugWindow = miniModel.getGUI().createDebugWindow(new Point(100, 200), new Dimension(700, 400));
		debugWindow.setVisible(true);
		debugWindow.append("Error initializing SpecialEventsPanel:\n");
		debugWindow.append(exr);
	}
}
