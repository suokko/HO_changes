package de.hattrickorganizer.database;

import java.sql.ResultSet;
import java.sql.Types;

import plugins.IVerein;
import de.hattrickorganizer.model.Verein;
import de.hattrickorganizer.tools.HOLogger;

public final class VereinTable extends AbstractTable {

	/** tablename **/
	public final static String TABLENAME = "VEREIN";

	protected VereinTable(JDBCAdapter  adapter){
		super( TABLENAME, adapter );
	}

	@Override
	protected void initColumns() {
		columns 	= new ColumnDescriptor[12];
		columns[0]	= new ColumnDescriptor( "HRF_ID",		Types.INTEGER,false, true );
		columns[1]	= new ColumnDescriptor( "TWTrainer",	Types.INTEGER,false );
		columns[2]	= new ColumnDescriptor( "COTrainer",	Types.INTEGER,false );
		columns[3]	= new ColumnDescriptor( "Physiologen",	Types.INTEGER,false );
		columns[4]	= new ColumnDescriptor( "Pschyologen",	Types.INTEGER,false );
		columns[5]	= new ColumnDescriptor( "Finanzberater",Types.INTEGER,false );
		columns[6]	= new ColumnDescriptor( "PRManager",	Types.INTEGER,false );
		columns[7]	= new ColumnDescriptor( "Aerzte",		Types.INTEGER,false );
		columns[8]	= new ColumnDescriptor( "Jugend",		Types.INTEGER,false );
		columns[9]	= new ColumnDescriptor( "Siege",		Types.INTEGER,false );
		columns[10]	= new ColumnDescriptor( "Ungeschlagen",	Types.INTEGER,false );
		columns[11]	= new ColumnDescriptor( "Fans",			Types.INTEGER,false );
	}

	@Override
	protected String[] getCreateIndizeStatements() {
		return new String[] {
			"CREATE INDEX IVEREIN_1 ON " + getTableName() + "(" + columns[0].getColumnName() + ")"};
	}
	/**
	 * speichert das Verein
	 *
	 * @param hrfId TODO Missing Constructuor Parameter Documentation
	 * @param verein TODO Missing Constructuor Parameter Documentation
	 */
	public void saveVerein(int hrfId, IVerein verein) {
		String statement = null;
		final String[] awhereS = { "HRF_ID" };
		final String[] awhereV = { "" + hrfId };

		if (verein != null) {
			//erst Vorhandene Aufstellung löschen
			delete( awhereS, awhereV );

			//insert vorbereiten
			statement = "INSERT INTO "+getTableName()+" ( TWTrainer , COTrainer , Physiologen , Pschyologen , Finanzberater , PRManager , Aerzte , Jugend , Siege , Ungeschlagen , Fans , HRF_ID ) VALUES(";
			statement
				+= (""
					+ 0
					+ ","
					+ verein.getCoTrainer()
					+ ","
					+ verein.getMasseure()
					+ ","
					+ verein.getPsychologen()
					+ ","
					+ 0
					+ ","
					+ verein.getPRManager()
					+ ","
					+ verein.getAerzte()
					+ ","
					+ verein.getJugend()
					+ ","
					+ verein.getSiege()
					+ ","
					+ verein.getUngeschlagen()
					+ ","
					+ verein.getFans()
					+ ","
					+ hrfId
					+ " )");
			adapter.executeUpdate(statement);
		}
	}

	/**
	 * lädt die Basics zum angegeben HRF file ein
	 *
	 * @param hrfID TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Verein getVerein(int hrfID) {
		ResultSet rs = null;
		Verein verein = null;
		String sql = null;

		sql = "SELECT * FROM "+getTableName()+" WHERE HRF_ID = " + hrfID;
		rs = adapter.executeQuery(sql);

		try {
			if (rs != null) {
				rs.first();
				verein = new Verein(rs);
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DatenbankZugriff.getTeam: " + e);
		}

		return verein;
	}

}
