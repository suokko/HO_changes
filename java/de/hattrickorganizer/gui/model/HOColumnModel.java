package de.hattrickorganizer.gui.model;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

import de.hattrickorganizer.model.HOVerwaltung;

/**
 * Basic ColumnModel for all UserColumnModels
 * @author Thorsten Dietz
 * @since 1.36
 */
public abstract class HOColumnModel extends AbstractTableModel{
	
	/**id from ColumnModell, important for saving columns in db */
	private int id;
	
	/** name of ColumnModell, shows in OptionsPanel **/
	private String name;
	
	/** count of displayed column **/
	private int displayedColumnsCount;
	
	/** all columns from this modell **/
	protected UserColumn [] columns;
	
	/** only displayed columns **/
	protected UserColumn [] displayedColumns;
	
	/** data of table **/
	protected Object[][] m_clData;
	
	/** instance of the same class **/
	protected int instance;
	
	/**
	 * constructor
	 * @param id
	 * @param name
	 */
	protected HOColumnModel(int id, String name){
		this.id = id;
		this.name = name;
	}

	/**
	 *  return all columns of the model
	 * @return UserColumn[]
	 */
	public final UserColumn[] getColumns() {
		return columns;
	}

	/**
	 * 
	 * @return id
	 */
	public final int getId() {
		return id;
	}

	/**
	 * return the language dependent name of this model
	 */
	public String toString(){
		String tmp = HOVerwaltung.instance().getResource().getProperty(name);
		return (instance == 0)?tmp:(tmp+instance);
	}
	
	/**
	 * return all columnNames of displayed columns
	 * @return String[]
	 */
	public String[] getColumnNames(){
		final String[] columnNames = new String[getDisplayedColumnCount()];
		for (int i = 0; i < getDisplayedColumns().length; i++) 
			columnNames[i] = getDisplayedColumns()[i].getColumnName();
		
		return columnNames;
	}
	
	/**
	 * return all tooltips of displayed columns
	 * @return String[]
	 */
	public String[] getTooltips(){
		final String[] tooltips = new String[getDisplayedColumnCount()];
			for (int i = 0; i < getDisplayedColumns().length; i++) 
					tooltips[i] = getDisplayedColumns()[i].getTooltip();
		return tooltips;
	}
	
	/**
	 * return all displayed columns
	 * @return UserColumn[]
	 */
	public UserColumn[] getDisplayedColumns(){
		
		if(displayedColumns == null){
			final int columncount = getDisplayedColumnCount();
			displayedColumns = new UserColumn[columncount];
			int currentIndex = 0;
			for (int i = 0; i < columns.length; i++) {
				
				if(columns[i].isDisplay()){
					displayedColumns[currentIndex] = columns[i];
					
					if(columns[i].getIndex() >= columncount)
						displayedColumns[currentIndex].setIndex(columncount-1);
					currentIndex++;
				} // column is displayed
			} // for
		}
		
		return displayedColumns;
	}
	
	/**
	 * return count of displayed columns
	 * @return int
	 */
	private int getDisplayedColumnCount(){
		if(displayedColumnsCount == 0){
			for (int i = 0; i < columns.length; i++) {
				if(columns[i].isDisplay())
					displayedColumnsCount++;
			}
		}
		return displayedColumnsCount;
	}
	
	/**
	 * return return count of displayed columns
	 * redundant method, but this is overwritten method from AbstractTableModel 
	 */
	public int getColumnCount(){
		return getDisplayedColumnCount();
	}
	
	/**
     * return value
     *
     * @param row 
     * @param column 
     *
     * @return Object
     */
    public final Object getValueAt(int row, int column) {
        if (m_clData != null) {
            return m_clData[row][column];
        }

        return null;
    }
    
    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getRowCount() {
        return (m_clData != null) ? m_clData.length : 0;
    }
    
    /**
     * TODO Missing Method Documentation
     *
     * @param row TODO Missing Method Parameter Documentation
     * @param col TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public boolean isCellEditable(int row, int col) {
        return false;
    }
    
    /**
     * TODO Missing Method Documentation
     *
     * @param columnIndex TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Class getColumnClass(int columnIndex) {
        final Object obj = getValueAt(0, columnIndex);

        if (obj != null) {
            return obj.getClass();
        }

        return "".getClass();
    }
    
    /**
     * TODO Missing Method Documentation
     *
     * @param columnIndex TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final String getColumnName(int columnIndex) {
        if (getDisplayedColumnCount() > columnIndex) {
            return getColumnNames()[columnIndex];
        }

        return null;
    }
    
    /**
     * TODO Missing Method Documentation
     *
     * @param row TODO Missing Method Parameter Documentation
     * @param columnName TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Object getValue(int row, String columnName) {
        if (m_clData != null) {
            int i = 0;

            while ((i < getColumnNames().length) && !getColumnNames()[i].equals(columnName)) {
                i++;
            }

            return m_clData[row][i];
        }

        return null;
    }
    
    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     * @param row TODO Missing Method Parameter Documentation
     * @param column TODO Missing Method Parameter Documentation
     */
    public final void setValueAt(Object value, int row, int column) {
        m_clData[row][column] = value;
    }
    
    /**
     * 
     * @param searchId
     * @return
     */
    protected int getColumnIndexOfDisplayedColumn(int searchId){
    	UserColumn[] tmp = getDisplayedColumns();
    	for (int i = 0; i < tmp.length; i++) {
			if(tmp[i].getId() == searchId)
				return i;
		}
    	return -1;
    }
    
    /**
     * return the order of the column
     * like old method getSpaltenreihenfolge
     * @return
     */
    public int[][] getColumnOrder(){
    	UserColumn[] tmp = getDisplayedColumns();
    	int order[][] = new int[tmp.length][2];
    	for (int i = 0; i < order.length; i++) {
			order[i][0] = i;
			order[i][1] = tmp[i].getIndex();
		}
    	return order;
    }
    
    /**
     * sets size in JTable
     * @param tableColumnModel
     */
    public void setColumnsSize(TableColumnModel tableColumnModel){
    	final UserColumn[] tmpColumns = getDisplayedColumns();
    	for (int i = 0; i < tmpColumns.length; i++) {
			tmpColumns[i].setSize(tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(i))));
		}
    }
 
    protected abstract void initData();
    
    /**
     * return the array index from a Column id
     * @param searchid
     * @return
     */
    public int  getPositionInArray(int searchid){
    	final UserColumn[] tmpColumns = getDisplayedColumns();
    	for (int i = 0; i < tmpColumns.length; i++) {
    		if(tmpColumns[i].getId() == searchid)
    			return i;
    	}
    	return -1;
    }
    
    
    public void setCurrentValueToColumns(UserColumn[] tmpColumns){
    	for (int i = 0; i < tmpColumns.length; i++) {
			for (int j = 0; j < columns.length; j++) {
				if(columns[j].getId() == tmpColumns[i].getId()){
					columns[j].setIndex(tmpColumns[i].getIndex());
					columns[j].setPreferredWidth(tmpColumns[i].getPreferredWidth());
					break;
				}
			}
		}
    }
 }
