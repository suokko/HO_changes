package ho.module.specialEvents;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

class SpecialEventsTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 8499826497766216534L;

	SpecialEventsTableModel(Vector<Vector<Object>> data, Vector<String> columns) {
		super(data, columns);
	}

	@Override
	public Class<?> getColumnClass(int col) {
		if (col == SpecialEventsTable.HOMEEVENTCOLUMN
				|| col == SpecialEventsTable.AWAYEVENTCOLUMN
				|| col == SpecialEventsTable.CHANCECOLUMN
				|| col == SpecialEventsTable.EVENTTYPCOLUMN) {
			return javax.swing.ImageIcon.class;
		}
		return super.getColumnClass(col);
	}
}
