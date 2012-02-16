/*
 * Created on 09.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ho.tool.hrfExplorer;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import plugins.IHOMiniModel;


/**
 * @author KickMuck
 */
public class HrfTableModel extends DefaultTableModel
{
	private Object[][] daten;
	private int m_anzSpalten;
	private int m_anzZeilen;
	private String m_columnNames[];
	
	
	public HrfTableModel(IHOMiniModel miniModel,String[] header, String[][] werte)
	{
		m_columnNames = header;
		m_anzSpalten = header.length;
		m_anzZeilen = werte.length;
		
		daten = new Object[m_anzZeilen][m_anzSpalten];
		
		for(int ii = 0; ii < m_anzZeilen; ii++)
		{
			for(int jj = 0; jj < m_anzSpalten; jj++)
			{
				daten[ii][jj] = werte[ii][jj];
			}
		}
	}
	
	public HrfTableModel(Vector columns,Vector rows)
	{
		dataVector = rows;
		columnIdentifiers = columns;
		m_anzSpalten = columns.size();
		m_anzZeilen = rows.size();
	}
	
	public boolean isCellEditable(int row, int col)
	{
		if(getValueAt(row,col).equals(new Boolean(true)) || getValueAt(row,col).equals(new Boolean(false)))
		{
			this.fireTableCellUpdated(row,col);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public Class getColumnClass(int columnIndex) 
	{
		Object o = getValueAt(0, columnIndex);
		Vector v = (Vector)dataVector.elementAt(0);
		if (o == null) 
		{
			return Object.class;
		} 
		else 
		{
			//return o.getClass();
			return v.elementAt(columnIndex).getClass();
		}
	}
	
	public void removeAllRows()
	{
		while(dataVector.size() > 0)
		{
			this.removeRow(0);
		}
	}
	
	public void addRow(Vector myRow)
	{
		try
		{
			dataVector.addElement(myRow);
		}
		catch(Exception e)
		{
			HrfExplorer.appendText("FEHLER iN addrow");
		}
		
	}
}

