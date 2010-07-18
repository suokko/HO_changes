/*
 * Created on 17.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package hoplugins.hrfExplorer;

import java.sql.*;
import java.util.Vector;

import plugins.IHOMiniModel;
import plugins.IJDBCAdapter;
import hoplugins.HrfExplorer;
/**
 * @author KickMuck
 */
public class HrfDBResult
{
	private plugins.IJDBCAdapter m_jdbcAdapter = null;
	private ResultSet m_ResultSet = null;
	private Vector m_ResultAsVector = null;
	private int m_anzChangedRows = 0;
    private int m_anzRows = 0;
    private int m_anzCols = 0;
	
    public HrfDBResult(IHOMiniModel model)
    {
    	m_jdbcAdapter = model.getAdapter();
    }
	/**
	 * Führt ein beliebiges SELECT gegen die Datenbank aus
	 * @param selectQuery
	 */
	public void doSelect(String selectQuery)
	{
		m_ResultSet = null;
		m_ResultAsVector = null;
		m_anzChangedRows = 0;
	    m_anzRows = 0;
	    m_anzCols = 0;
		m_ResultSet = m_jdbcAdapter.executeQuery(selectQuery);
	}
	
	/**
	 * Führt ein beliebiges UPDATE, DELETE oderCREATE gegen die Datenbank aus
	 * @param updateQuery
	 */
	public void doUpdate(String updateQuery)
	{
		m_anzChangedRows = m_jdbcAdapter.executeUpdate(updateQuery);
	}
	
	/**
	 * Generiert aus einem beliebigen ResultSet einen Vector
	 * @param rs
	 */
	public void createResultVector()
	{
		try
		{
			m_anzCols = m_ResultSet.getMetaData().getColumnCount();
			
			while(m_ResultSet.next())
			{
				m_anzRows++;
				Vector tmp = new Vector();
				for(int ii = 0; ii < m_anzCols; ii++)
				{
					tmp.add(m_ResultSet.getObject(ii));
				}
				m_ResultAsVector.add(tmp);;
			}
		}
		catch(SQLException sexc)
		{
			HrfExplorer.appendText("" + sexc);
		}
	}
	
	/**
	 * Liefert ein Object des ResultSet an angegebener Stelle
	 * @param row
	 * @param col
	 * @return
	 */
	public Object getOneObject(int row, int col)
	{
		Object obj1 = new Object();
		try
		{
			while(m_ResultSet.getRow() < row)
			{
				m_ResultSet.next();
			}
			obj1 = m_ResultSet.getObject(col);
		}
		catch(SQLException obj)
		{
			
		}
		
		return obj1;
	}
	
	/**
	 * Getter für den erzeugten Vector
	 * @return
	 */
	public Vector getResultAsVector()
	{
		return m_ResultAsVector;
	}
	
	/**
	 * Getter für die Anzahl der Zeilen
	 * @return
	 */
	public int getRowCount()
	{
		return m_anzRows;
	}
	
	/**
	 * Getter für die Anzahl der Spalten
	 * @return
	 */
	public int getColCount()
	{
		return m_anzCols;
	}
	
	/**
	 * Getter für die Anzahl der geänderten Zeilen
	 * @return
	 */
	public int getChangedRows()
	{
		return m_anzChangedRows;
	}
}
