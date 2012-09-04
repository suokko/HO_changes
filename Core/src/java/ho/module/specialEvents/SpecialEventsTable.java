package ho.module.specialEvents;

import ho.core.model.HOVerwaltung;
import ho.core.util.HOLogger;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;


public class SpecialEventsTable extends JTable {

	private static final long serialVersionUID = 8656004206333977669L;

	static final int MATCHDATECOLUMN = 0;
	static final int MATCHIDCOLUMN = 1;
	static final int HOMETACTICCOLUMN = 2;
	static final int HOMEEVENTCOLUMN = 3;
	static final int HOMETEAMCOLUMN = 4;
	static final int RESULTCOLUMN = 5;
	static final int AWAYTEAMCOLUMN = 6;
	static final int AWAYEVENTCOLUMN = 7;
	static final int AWAYTACTICCOLUMN = 8;
	static final int MINUTECOLUMN = 9;
	static final int CHANCECOLUMN = 10;
	static final int EVENTTYPCOLUMN = 11;
	static final int SETEXTCOLUMN = 12;
	static final int NAMECOLUMN = 13;
	static final int HIDDENCOLUMN = 14;
	static final int NUMCOLUMNS = 15;

	private String columnNames[];
	private Vector<String> highlightTexte;

	SpecialEventsTable() {
		columnNames = new String[NUMCOLUMNS];
		setColumnHeaders();
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // AUTO_RESIZE_ALL_COLUMNS
		getTableHeader().setReorderingAllowed(false);
		try {
			setDefaultRenderer(Object.class, new SpecialEventsTableRenderer());
			TableModel table = getSEModel();
			setModel(table);
		} catch (RuntimeException e) {
			showDebug(e);
		}
	}

	private void setColumnHeaders() {
		columnNames[MATCHDATECOLUMN] = HOVerwaltung.instance().getLanguageString("Datum");
		columnNames[MATCHIDCOLUMN] = HOVerwaltung.instance().getLanguageString("GameID");
		columnNames[HOMETACTICCOLUMN] = HOVerwaltung.instance().getLanguageString("Taktik");
		columnNames[HOMEEVENTCOLUMN] = " ";
		columnNames[HOMETEAMCOLUMN] = HOVerwaltung.instance().getLanguageString("Heim");
		columnNames[RESULTCOLUMN] = " ";
		columnNames[AWAYTEAMCOLUMN] = HOVerwaltung.instance().getLanguageString("Gast");
		columnNames[AWAYEVENTCOLUMN] = " ";
		columnNames[AWAYTACTICCOLUMN] = HOVerwaltung.instance().getLanguageString("Taktik");
		columnNames[MINUTECOLUMN] = HOVerwaltung.instance().getLanguageString("Min");
		columnNames[CHANCECOLUMN] = " ";
		columnNames[EVENTTYPCOLUMN] = " ";
		columnNames[SETEXTCOLUMN] = HOVerwaltung.instance().getLanguageString("Event");
		columnNames[NAMECOLUMN] = HOVerwaltung.instance().getLanguageString("Spieler");
		columnNames[HIDDENCOLUMN] = " ";
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
					tip = HOVerwaltung.instance().getLanguageString("Tip4");
				} else {
					tip = (columnNames[realIndex] != null && columnNames[realIndex].length() > 0) ? columnNames[realIndex] : null;
					
				}
				return tip;
			}

		};
	}

	public void updateData() {
		SpecialEventsDM specialEventsDM = new SpecialEventsDM();
		SpecialEventsTableModel model = (SpecialEventsTableModel) getModel();
		model.setDataVector(specialEventsDM.holeInfos(FilterPanel.getGameTypAll().isSelected(), //
				FilterPanel.getSaisonTyp(), FilterPanel.showFriendlies()),
				new Vector<String>(Arrays.asList(columnNames)));
		model.newDataAvailable(null);
	}

	public TableModel getSEModel() {
		SpecialEventsDM specialEventsDM = new SpecialEventsDM();
		Vector<Vector<Object>> matches = specialEventsDM.holeInfos(FilterPanel.getGameTypAll().isSelected(), //
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
			tip = HOVerwaltung.instance().getLanguageString("Tip4");
		}
		if (realColumnIndex == NAMECOLUMN) {
			tip = HOVerwaltung.instance().getLanguageString("TipName");
		}
		if (realColumnIndex == EVENTTYPCOLUMN) {
			String highlightText = "<table width='300'><tr><td>" + highlightTexte.elementAt(rowIndex) + "</td></tr></table>";
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
		columnWidth(MATCHDATECOLUMN, 66, 34);
		columnWidth(MATCHIDCOLUMN, 66, 34);
		columnWidth(HOMETACTICCOLUMN, 37, 22);
		columnWidth(HOMEEVENTCOLUMN, 20, 20);
		columnWidth(HOMETEAMCOLUMN, 150, 100);
		columnWidth(RESULTCOLUMN, 40, 20);
		columnWidth(AWAYTEAMCOLUMN, 150, 100);
		columnWidth(AWAYEVENTCOLUMN, 20, 20);
		columnWidth(AWAYTACTICCOLUMN, 37, 22);
		columnWidth(MINUTECOLUMN, 27, 27);
		columnWidth(CHANCECOLUMN, 22, 20);
		columnWidth(EVENTTYPCOLUMN, 23, 20);
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
		HOLogger.instance().error(this.getClass(), exr);
	}
}
