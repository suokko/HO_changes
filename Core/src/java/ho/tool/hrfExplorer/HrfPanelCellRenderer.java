/*
 * Created on 04.06.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ho.tool.hrfExplorer;

import javax.swing.table.*;
import javax.swing.*;
import java.awt.*;

class HrfPanelCellRenderer extends JPanel implements TableCellRenderer{

	private static final long serialVersionUID = -2474494701054971719L;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
	removeAll();
	add((JComponent)value);
	return this;
	}
}
