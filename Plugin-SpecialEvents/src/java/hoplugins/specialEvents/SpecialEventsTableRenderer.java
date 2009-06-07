package hoplugins.specialEvents;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class SpecialEventsTableRenderer extends DefaultTableCellRenderer
{

	private static final long serialVersionUID = 6621498528069679319L;
	public static Color Color1 = new Color(220, 220, 255);
    public static Color Color2 = new Color(255, 255, 255);

    public SpecialEventsTableRenderer()
    {
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(column == SpecialEventsPanel.RESULTCOLUMN)
        {
            setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        } else
        {
            setHorizontalAlignment(DefaultTableCellRenderer.LEFT);
        }
        setForeground(Color.black);
        if(column == SpecialEventsPanel.NAMECOLUMN)
        {
            String name = (String)value;
            if(!name.equals(""))
            {
                String rName = name.substring(0, name.length() - 2);
                String rType = name.substring(name.length() - 1);
                setText(rName);
                if(rType.equals("-")) {
                    setForeground(Color.red);
                } else if (rType.equals("#")) {
                    setForeground(Color.LIGHT_GRAY);                	
                }
            }
        }
        int type = 0;
        try
        {
            type = Integer.parseInt((String)table.getValueAt(row, SpecialEventsPanel.HIDDENCOLUMN));
        }
        catch(NumberFormatException numberformatexception) { }
        cell.setBackground(Color1);
        if(type == -1)
        {
            cell.setBackground(Color1);
        }
        if(type == 1)
        {
            cell.setBackground(Color2);
        }
        return cell;
    }

}
