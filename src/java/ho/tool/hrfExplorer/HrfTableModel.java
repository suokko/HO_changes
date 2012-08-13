/*
 * Created on 09.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ho.tool.hrfExplorer;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

/**
 * @author KickMuck
 */
public class HrfTableModel extends DefaultTableModel {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4726662462776212169L;

	public HrfTableModel(Vector columns, Vector rows) {
		dataVector = rows;
		columnIdentifiers = columns;

	}

	@Override
	public boolean isCellEditable(int row, int col) {
		if (getValueAt(row, col).equals(Boolean.TRUE)
				|| getValueAt(row, col).equals(Boolean.FALSE)) {
			this.fireTableCellUpdated(row, col);
			return true;
		}
			return false;

	}

	@Override
	public Class getColumnClass(int columnIndex) {
		Object o = getValueAt(0, columnIndex);
		Vector v = (Vector) dataVector.elementAt(0);
		if (o == null) {
			return Object.class;
		}
		return v.elementAt(columnIndex).getClass();
	}

	public void removeAllRows() {
		while (dataVector.size() > 0) {
			this.removeRow(0);
		}
	}

	@Override
	public void addRow(Vector myRow) {
		try {
			dataVector.addElement(myRow);
		} catch (Exception e) {
			HrfExplorer.appendText("FEHLER iN addrow");
		}

	}
}
