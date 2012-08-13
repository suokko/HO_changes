package ho.module.ifa.table;

import java.util.Arrays;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class SortedTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2323510453329739002L;
	DefaultTableModel model;
	int sortColumn;
	private Row[] rows;
	boolean alfa;

	public SortedTableModel(DefaultTableModel m)  {
    	model=m;
    	rows=new Row[model.getRowCount()];
    	for(int i=0; i<rows.length; i++) {
    		rows[i]=new Row();
    		rows[i].index=i;
      	}
   }
	public void setAlfa(boolean a) {
		alfa=a;
	}
	
	public void sort(int c)  
    {
 		sortColumn=c;
 		rows=new Row[model.getRowCount()];//******************
 		for(int i=0; i<rows.length; i++) {//da die Tabelle Anzahl von Zeilen �ndert 
    		rows[i]=new Row();//
    		rows[i].index=i;//
      	}//****************************************
 		Arrays.sort(rows);
 		fireTableDataChanged();
 	}

	@Override
	public Object getValueAt(int r, int c) {
		return model.getValueAt(rows[r].index, c);
	}


	@Override
	public boolean isCellEditable(int r, int c) {
		return false;
    	//return model.isCellEditable(rows[r].index, c);
	}

	@Override
	public void setValueAt(Object aValue, int r, int c) {
		model.setValueAt(aValue, rows[r].index, c);
	}

	@Override
	public int getRowCount() {
		return model.getRowCount();
	}

	@Override
	public int getColumnCount() {
		return model.getColumnCount();
	}

	@Override
	public String getColumnName(int c) {
		return model.getColumnName(c);
	}

	@Override
	public Class<?> getColumnClass(int c){
		return model.getColumnClass(c);
	}
	
	public void removeRow(int row) {
		model.removeRow(rows[row].index);
		sort(sortColumn);// Damit Model aktualisiert wird und repainted
	}
	
	public void addColumn(TableColumn  column) {
	    model.addColumn(column);
		sort(sortColumn);// Damit Model aktualisiert wird und repainted
	}
   
	private class Row implements Comparable<Object>
    {
		public int index;
		@Override
		public int compareTo(Object other) {
			Row otherRow=(Row)other;
			Object a = model.getValueAt(index, sortColumn);
			
			Object b = model.getValueAt(otherRow.index, sortColumn);
			if(alfa)
			    return compareObjects(a, b);
			return  compareObjects(b, a);
		}
	}
	
	/**
	 * Vergleicht zwei Objekten
	 * @param a
	 * @param b
	 * @return int
	 */
	int compareObjects(Object obj1, Object obj2) 
    {
		if(obj1 instanceof Integer){
			return ((Integer)obj1).compareTo((Integer)obj2);
		}
		if(obj1 instanceof Date){
			return ((Date)obj1).compareTo((Date)obj2);
		}
        if(obj1 instanceof JLabel)
        {
            String tmp1 = ((JLabel)obj1).getText();
            String tmp2 = ((JLabel)obj2).getText();
            int i = prepairForCompare(tmp1).compareTo(prepairForCompare(tmp2));   
            return ( 0 != i ) ? i : (((JLabel)obj1).getText()).compareTo(((JLabel)obj2).getText());
        }
			
        return obj1.toString().compareTo(obj2.toString());
	}
	
    private String prepairForCompare( Object o )
    {
        return ((String)o).toLowerCase().replace( '�', 'a' )
                                        .replace( '�', 'o' )
                                        .replace( '�', 'u' )
                                        .replace( '�', 's' )
                                        .replace( '�', 'i' )
                                        .replace( '\u010D', 'c' );//?
        
        
    }
    
	public DefaultTableModel getModel() {
		return model;
	}
	
    public int getSortColumn() {
        return sortColumn;
    }
    public void setModel(DefaultTableModel model) {
        this.model = model;
    }
}
