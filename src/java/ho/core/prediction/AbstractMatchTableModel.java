// %1590051216:de.hattrickorganizer.gui.model%
/*
 * MatchPredictionSpieleTableModel.java
 *
 * Created on 4. Januar 2005, 13:19
 */
package ho.core.prediction;

import ho.core.prediction.engine.MatchResult;

import javax.swing.JProgressBar;
import javax.swing.table.AbstractTableModel;

/**
 * DOCUMENT ME!
 *
 * @author Pirania
 */
public abstract class AbstractMatchTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -7810048787047274663L;
	
	//~ Instance fields ----------------------------------------------------------------------------


	/** TODO Missing Parameter Documentation */
	protected Object[][] m_clData;

	protected MatchResult matchResult;

	public abstract String[] getColumnNames();
	protected abstract void initData();	
	private boolean isHomeMatch = true;
	//~ Constructors -------------------------------------------------------------------------------

	/**
	 * Creates a new MatchPredictionSpieleTableModel object.
	 *
	 * @param vErgebnisse TODO Missing Constructuor Parameter Documentation
	 */
	public AbstractMatchTableModel(MatchResult matchresults, boolean ishome) {
		this.matchResult = matchresults;
		isHomeMatch = ishome;
		initData();
	}

	//~ Methods ------------------------------------------------------------------------------------

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param row TODO Missing Method Parameter Documentation
	 * @param col TODO Missing Method Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	@Override
	public final boolean isCellEditable(int row, int col) {
		return false;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param columnIndex TODO Missing Method Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	@Override
	public final Class<?> getColumnClass(int columnIndex) {
		final Object obj = getValueAt(0, columnIndex);

		if (obj != null) {
			return obj.getClass();
		} 
		
		return "".getClass();
		
	}

	//-----Zugriffsmethoden----------------------------------------        
	public final int getColumnCount() {
		return getColumnNames().length;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param columnIndex TODO Missing Method Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	@Override
	public final String getColumnName(int columnIndex) {
		if ((getColumnNames() != null) && (getColumnNames().length > columnIndex)) {
			return getColumnNames()[columnIndex];
		} 
		
		return null;
		
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public final int getRowCount() {
		if (m_clData != null) {
			return m_clData.length;
		} 
		
		return 0;
		
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
		if ((getColumnNames() != null) && (m_clData != null)) {
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
	@Override
	public final void setValueAt(Object value, int row, int column) {
		m_clData[row][column] = value;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param row TODO Missing Method Parameter Documentation
	 * @param column TODO Missing Method Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public final Object getValueAt(int row, int column) {
		if (m_clData != null) {
			return m_clData[row][column];
		}

		return null;
	}

	/**
	 * Matches neu setzen
	 *
	 * @param vErgebnisse TODO Missing Constructuor Parameter Documentation
	 */
	public final void setValues(MatchResult matchresults) {
		this.matchResult = matchresults;
		initData();
	}

	//-----initialisierung-----------------------------------------

	protected JProgressBar getProgressBar(double value) {
		JProgressBar bar = new JProgressBar(0, 100);
		bar.setStringPainted(true);
		bar.setValue((int) (value * 100));
		return bar;
	}
	public boolean isHomeMatch() {
		return isHomeMatch;
	}

}
