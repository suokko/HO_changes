package ho.module.specialEvents;

import ho.core.model.HOVerwaltung;
import ho.core.util.HOLogger;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.JTableHeader;

public class SpecialEventsTable extends JTable {

	private static final long serialVersionUID = 8656004206333977669L;
	private final Filter filter;
	private List<String> highlightTexte;

	SpecialEventsTable(Filter filter) {
		this.filter = filter;
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
				int modelIndex = convertColumnIndexToModel(columnModel.getColumnIndexAtX(p.x));
				if (modelIndex == SpecialEventsTableModel.HOMEEVENTCOLUMN
						|| modelIndex == SpecialEventsTableModel.AWAYEVENTCOLUMN) {
					tip = HOVerwaltung.instance().getLanguageString("Tip4");
				} else {
					tip = getModel().getColumnName(modelIndex);

				}
				return tip;
			}

		};
	}

	public void setHighlightTexte(List<String> highlightTexte) {
		this.highlightTexte = highlightTexte;
	}

	@Override
	public String getToolTipText(MouseEvent e) {
		String tip = null;
		Point p = e.getPoint();
		int rowIndex = rowAtPoint(p);
		int colIndex = columnAtPoint(p);
		int modelColumnIndex = convertColumnIndexToModel(colIndex);
		if (modelColumnIndex == SpecialEventsTableModel.HOMEEVENTCOLUMN
				|| modelColumnIndex == SpecialEventsTableModel.AWAYEVENTCOLUMN) {
			tip = HOVerwaltung.instance().getLanguageString("Tip4");
		}
		if (modelColumnIndex == SpecialEventsTableModel.NAMECOLUMN) {
			tip = HOVerwaltung.instance().getLanguageString("TipName");
		}
		if (modelColumnIndex == SpecialEventsTableModel.EVENTTYPCOLUMN) {
			String highlightText = "<table width='300'><tr><td>"
					+ highlightTexte.get(rowIndex) + "</td></tr></table>";
			String text = "<html>" + highlightText + "</html>";
			tip = text;
		}
		return tip;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int mColIndex) {
		return false;
	}

	private void showDebug(Exception exr) {
		HOLogger.instance().error(this.getClass(), exr);
	}
}
