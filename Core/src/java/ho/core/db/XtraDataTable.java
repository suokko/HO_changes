package ho.core.db;

import ho.core.model.XtraData;
import ho.core.util.HOLogger;

import java.sql.ResultSet;
import java.sql.Types;


final class XtraDataTable extends AbstractTable {
	final static String TABLENAME = "XTRADATA";
	
	protected XtraDataTable(JDBCAdapter  adapter){
		super(TABLENAME,adapter);
	}
	
	@Override
	protected void initColumns() {
		columns = new ColumnDescriptor[9];
		columns[0]= new ColumnDescriptor( "HRF_ID",				Types.INTEGER,	false, true );
		columns[1]= new ColumnDescriptor( "CurrencyRate",		Types.REAL,		false );
		columns[2]= new ColumnDescriptor( "HasPromoted",		Types.BOOLEAN,	false );
		columns[3]= new ColumnDescriptor( "CurrencyName",		Types.VARCHAR,	false, 127);
		columns[4]= new ColumnDescriptor( "LogoURL",			Types.VARCHAR,	false, 127);
		columns[5]= new ColumnDescriptor( "SeriesMatchDate",	Types.TIMESTAMP,false );
		columns[6]= new ColumnDescriptor( "TrainingDate",		Types.TIMESTAMP,false );
		columns[7]= new ColumnDescriptor( "EconomyDate",		Types.TIMESTAMP,false );
		columns[8]= new ColumnDescriptor( "LeagueLevelUnitID",	Types.INTEGER,	false );
	}
	
	@Override
	protected String[] getCreateIndizeStatements() {
		return new String[] {
			"CREATE INDEX IXTRADATA_1 ON " + getTableName() + "(" + columns[0].getColumnName() + ")"};
	}	
	
	/**
	 * lädt die Basics zum angegeben HRF file ein
	 *
	 * @param hrfID TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	XtraData getXtraDaten(int hrfID) {
		ResultSet rs = null;
		XtraData xtra = null;
		String sql = null;

		sql = "SELECT * FROM "+getTableName()+" WHERE HRF_ID = " + hrfID;
		rs = adapter.executeQuery(sql);

		try {
			if (rs != null) {
				rs.first();
				xtra = new XtraData(rs);
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DatenbankZugriff.XtraData: " + e);
		}

		return xtra;
	}

	/**
	 * speichert das Team
	 *
	 * @param hrfId TODO Missing Constructuor Parameter Documentation
	 * @param xtra TODO Missing Constructuor Parameter Documentation
	 */
	void saveXtraDaten(int hrfId, XtraData xtra) {
		String statement = null;

		if (xtra != null) {
			int hasProm = 0;

			if (xtra.isHasPromoted()) {
				hasProm = 1;
			}

			//erst Vorhandene Aufstellung löschen
			deleteXtraDaten(hrfId);

			//insert vorbereiten
			statement = "INSERT INTO "+getTableName()+" ( HRF_ID , CurrencyRate, HasPromoted , CurrencyName , LogoURL , SeriesMatchDate ,TrainingDate, EconomyDate, LeagueLevelUnitID ) VALUES(";
			statement
				+= (""
					+ hrfId
					+ ","
					+ xtra.getCurrencyRate()
					+ ","
					+ hasProm
					+ ",'"
					+ ho.core.db.DBManager.insertEscapeSequences(xtra.getCurrencyName())
					+ "','"
					+ ho.core.db.DBManager.insertEscapeSequences(xtra.getLogoURL())
					+ "', '"
					+ xtra.getSeriesMatchDate()
					+ "', '"
					+ xtra.getTrainingDate()
					+ "', '"
					+ xtra.getEconomyDate()
					+ "', "
					+ xtra.getLeagueLevelUnitID()
					+ " )");
			adapter.executeUpdate(statement);
		}
	}
	
	private void deleteXtraDaten(int hrfID) {
		final String[] where = { "HRF_ID" };
		final String[] value = { hrfID + "" };
		delete( where,value );
	}

}
