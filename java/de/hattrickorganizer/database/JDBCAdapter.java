// %4089797104:de.hattrickorganizer.database%
package de.hattrickorganizer.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import de.hattrickorganizer.model.User;
import de.hattrickorganizer.tools.HOLogger;


/**
 * stellt die Verbindungsfunktionen zur Datenbank her
 */
public class JDBCAdapter implements plugins.IJDBCAdapter {
    //~ Instance fields ----------------------------------------------------------------------------

    private Connection m_clConnection;
    private Statement m_clStatement;
    private DBInfo m_clDBInfo;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates new JDBCApapter
     */
    public JDBCAdapter() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * trennt die Verbindung
     */
    public final void disconnect() {
        try {
        	if (User.getCurrentUser().isHSQLDB()) {
				executeQuery("SHUTDOWN COMPACT");
        	}            
            m_clConnection.close();
            m_clConnection = null;
        } catch (Exception e) {
            //vapEngine.Verwaltung.instance ().writeLog ( "JDBCAdapter.disconnect : " + e.getMessage() );
        	HOLogger.instance().error(getClass(),"JDBCAdapter.disconnect : " + e );
            m_clConnection = null;
        }
    }

    /**
     * Führt einen SQL select - befehl aus
     *
     * @param Sql Sql query
     *
     * @return ResultSet of the query
     */
    public final ResultSet executeQuery(String Sql) {
        ResultSet resultat = null;

        try {
            if (m_clConnection.isClosed()) {
                return null;
            }

            //HOLogger.instance().log(getClass(), Sql );
            resultat = m_clStatement.executeQuery(Sql);

            //statement.close();
            return resultat;
        } catch (Exception e) {
            HOLogger.instance().error(getClass(),"JDBCAdapter.executeQuery : " + e + "\nStatement: " + Sql);
            return null;
        }
    }

    /**
     * Executes an SQL INSERT, UPDATE or DELETE statement. In addition, SQL statements that return
     * nothing, such as SQL DDL statements, can be executed.
     *
     * @param Sql INSERT, UPDATE or DELETE statement
     *
     * @return either the row count for SQL Data Manipulation Language (DML) statements 
     * or 0 for SQL statements that return nothing
     * 
     */
    public final int executeUpdate(String Sql) {
        int ret = 0;

        try {
            if (m_clConnection.isClosed()) {
                return 0;
            }

            //HOLogger.instance().log(getClass(), Sql );
            ret = m_clStatement.executeUpdate(Sql);

            //statement.close();
            return ret;
        } catch (Exception e) {
            HOLogger.instance().error(getClass(),"JDBCAdapter.executeUpdate : " + e + "\nStatement: " + Sql);
            return 0;
        }
    }

    /**
     * verbindet unter angabe der Parameter
     *
     * @param URL bezeichnet den Pfad zum Server
     * @param User der Username
     * @param PWD Password
     * @param Treiber der zu verwendende Treiber
     * 
     */
    public final void connect(String URL, String User, String PWD, String Treiber) throws Exception{
        try {
            //            Class.forName(Treiber);
            Class.forName(Treiber);
            			
            m_clConnection = DriverManager.getConnection(URL, User, PWD);
            m_clStatement = m_clConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                           ResultSet.CONCUR_READ_ONLY);
            
        } catch (Exception e) {
            //vapEngine.Verwaltung.instance ().writeLog ( "JDBCAdapter.connect : " + e.getMessage() ); 
            if (m_clConnection != null) {
                try {
                    m_clConnection.close();
                } catch (Exception ex) {
                    HOLogger.instance().error(getClass(),"JDBCAdapter.connect : " + ex.getMessage());
                }
            }
            HOLogger.instance().error(getClass(),"JDBCAdapter.connect : " + e.getMessage());

            //System.exit( 1 );            
            throw e;
        }

    }
    
    /**
     * 
     * @return DBInfo
     * @throws Exception
     */
    public DBInfo getDBInfo() throws Exception{
    	if(m_clDBInfo== null)
    		m_clDBInfo = new DBInfo(m_clConnection.getMetaData());
    	return m_clDBInfo;
    }
    
    public Object[] getAllTableNames(){
    	try {
    		return getDBInfo().getAllTablesNames();
		} catch (Exception e) {
			HOLogger.instance().error(getClass(),"JDBCAdapter.getAllTableNames : " + e);
			String[] fuck = new String[1];
			fuck[0] = e.getMessage();
			return fuck;
		}
    	
    }
}
