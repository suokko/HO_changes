package de.hattrickorganizer;

import de.hattrickorganizer.database.JDBCAdapter;

/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class DBTest {
    //~ Constructors -------------------------------------------------------------------------------

    //private JDBCAdapter m_clJDBCAdapter                         = new JDBCAdapter();

    /**
     * Creates a new instance of dbTest
     */
    public DBTest() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * main führt übergebenes SQL statement aus
     *
     * @param args TODO Missing Constructuor Parameter Documentation
     */
    public static void main(String[] args) {
        final JDBCAdapter m_clJDBCAdapter = new JDBCAdapter();

        //Connecten //erzeugt in f:\apps\forte4j\bin (weil akutelles Verzeichniss) unterverzecihniss db, mit file database
        try {
			m_clJDBCAdapter.connect("jdbc:hsqldb:file:db/database", "sa", "", "org.hsqldb.jdbcDriver");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        //parameter ausführen nur create update insert
        try {
            for (int i = 0; (args != null) && (i < args.length); i++) {
                m_clJDBCAdapter.executeQuery(args[i]);
            }
        } catch (Exception e) {
        }

        try {
            for (int i = 0; (args != null) && (i < args.length); i++) {
                m_clJDBCAdapter.executeUpdate(args[i]);
            }
        } catch (Exception e) {
        }

        /*
           //Tabelle erzeugen
           m_clJDBCAdapter.executeQuery ( "CREATE TABLE sample_table ( id INTEGER IDENTITY, str_col VARCHAR(256), num_col INTEGER)" );
           m_clJDBCAdapter.executeUpdate ( "INSERT INTO sample_table(str_col,num_col) VALUES('Ford', 100)" );
           rs  =   m_clJDBCAdapter.executeQuery ( "SELECT * FROM sample_table WHERE num_col < 250" );
           try
           {
               rs.first ();
           }
           catch (Exception e ) {}
         */
        m_clJDBCAdapter.disconnect();
    }
}
