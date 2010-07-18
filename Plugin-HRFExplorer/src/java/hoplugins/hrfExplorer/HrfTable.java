/*
 * Created on 09.05.2005
 */
package hoplugins.hrfExplorer;

import hoplugins.HrfExplorer;
import plugins.*;

import javax.swing.JTable;
import javax.swing.table.*;

/**
 * @author KickMuck
 */

public class HrfTable extends JTable
{
	public HrfTable(IHOMiniModel iHoMiniMod, HrfTableModel htm, String name)
	{
		super(htm);
		setName(name);
		TableColumn col;
		int anzColumns = htm.getColumnCount();
		for(int ii = 0; ii < anzColumns; ii++)
		{
			col = this.getColumnModel().getColumn(ii);
	    	col.setCellRenderer(new HrfTableCellRenderer());
		}
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
	
	public HrfTable(IHOMiniModel iHoMiniMod, HrfTableModel htm, int[] colWidth, String name)
	{
		super(htm);
		setName(name);
		TableColumn col;
		int anzColumns = htm.getColumnCount();
		for(int ii = 0; ii < anzColumns; ii++)
		{
			col = this.getColumnModel().getColumn(ii);
			if(super.getName().equals("calendar"))
			{
				col.setCellRenderer(new HrfTableCellRenderer());
			}
			else if(ii > 0)
			{
				col.setCellRenderer(new HrfTableCellRenderer());
			}
			col.setPreferredWidth(colWidth[ii]);
		}
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
	
}
