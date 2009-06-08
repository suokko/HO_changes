package hoplugins.specialEvents;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class SpecialEventsTableModel extends DefaultTableModel
{

	private static final long serialVersionUID = 8499826497766216534L;

	public SpecialEventsTableModel(Vector data, Vector columns)
    {
        super(data, columns);
    }

    @Override
	public Class getColumnClass(int col)
    {
        if(col == SpecialEventsPanel.HOMEEVENTCOLUMN ||
        		col == SpecialEventsPanel.AWAYEVENTCOLUMN || 
        		col == SpecialEventsPanel.CHANCECOLUMN || 
        		col == SpecialEventsPanel.EVENTTYPCOLUMN)
        {
            return javax.swing.ImageIcon.class;
        } else
        {
            return super.getColumnClass(col);
        }
    }
}
