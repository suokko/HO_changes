package de.hattrickorganizer.database;

import java.sql.ResultSet;
import java.sql.Types;

import de.hattrickorganizer.model.Stadium;
import de.hattrickorganizer.tools.HOLogger;

public final class StadionTable extends AbstractTable {

	/** tablename **/
	public final static String TABLENAME = "STADION";
	
	protected StadionTable(JDBCAdapter  adapter){
		super(TABLENAME, adapter);
	}

	@Override
	protected void initColumns() {
		columns = new ColumnDescriptor[18];
		columns[0]= new ColumnDescriptor("HRF_ID",Types.INTEGER,false,true);
		columns[1]= new ColumnDescriptor("StadionName",Types.VARCHAR,false,127);
		columns[2]= new ColumnDescriptor("GesamtGr",Types.INTEGER,false);
		columns[3]= new ColumnDescriptor("AnzSteh",Types.INTEGER,false);
		columns[4]= new ColumnDescriptor("AnzSitz",Types.INTEGER,false);
		columns[5]= new ColumnDescriptor("AnzDach",Types.INTEGER,false);
		columns[6]= new ColumnDescriptor("AnzLogen",Types.INTEGER,false);
		columns[7]= new ColumnDescriptor("AusbauSteh",Types.INTEGER,false);
		columns[8]= new ColumnDescriptor("AusbauSitz",Types.INTEGER,false);
		columns[9]= new ColumnDescriptor("AusbauDach",Types.INTEGER,false);
		columns[10]= new ColumnDescriptor("AusbauLogen",Types.INTEGER,false);
		columns[11]= new ColumnDescriptor("Ausbau",Types.INTEGER,false);
		columns[12]= new ColumnDescriptor("VerkaufteSteh",Types.INTEGER,false);
		columns[13]= new ColumnDescriptor("VerkaufteSitz",Types.INTEGER,false);
		columns[14]= new ColumnDescriptor("VerkaufteDach",Types.INTEGER,false);
		columns[15]= new ColumnDescriptor("VerkaufteLogen",Types.INTEGER,false);
		columns[16]= new ColumnDescriptor("AusbauKosten",Types.INTEGER,false);
		columns[17]= new ColumnDescriptor("ArenaID",Types.INTEGER,false);
	}
	
	@Override
	protected String[] getCreateIndizeStatements() {
		return new String[] {
			"CREATE INDEX ISTADION_1 ON " + getTableName() + "(" + columns[0].getColumnName() + ")"};
	}	

	/**
	 * speichert die Finanzen
	 *
	 * @param hrfId TODO Missing Constructuor Parameter Documentation
	 * @param stadion TODO Missing Constructuor Parameter Documentation
	 */
	public void saveStadion(int hrfId, de.hattrickorganizer.model.Stadium stadion) {
		String statement = null;
		final String[] awhereS = { "HRF_ID" };
		final String[] awhereV = { "" + hrfId };

		if (stadion != null) {
			//erst Vorhandene Aufstellung löschen
			delete( awhereS, awhereV );
			//insert vorbereiten
			statement =
				"INSERT INTO "+getTableName()+" ( HRF_ID, StadionName, GesamtGr, AnzSteh, AnzSitz , AnzDach , AnzLogen , AusbauSteh , AusbauSitz , AusbauDach , AusbauLogen , Ausbau , VerkaufteSteh , VerkaufteSitz , VerkaufteDach , VerkaufteLogen , AusbauKosten , ArenaID ) VALUES(";
			statement
				+= (""
					+ hrfId
					+ ",'"
					+ de.hattrickorganizer.database.DBZugriff.insertEscapeSequences(stadion.getStadienname())
					+ "',"
					+ stadion.getGesamtgroesse()
					+ ","
					+ stadion.getStehplaetze()
					+ ","
					+ stadion.getSitzplaetze()
					+ ","
					+ stadion.getUeberdachteSitzplaetze()
					+ ","
					+ stadion.getLogen()
					+ ","
					+ stadion.getAusbauStehplaetze()
					+ ","
					+ stadion.getAusbauSitzplaetze()
					+ ","
					+ stadion.getAusbauUeberdachteSitzplaetze()
					+ ","
					+ stadion.getAusbauLogen()
					+ ","
					+ stadion.getAusbau()
					+ ","
					+ "0"
					+ ","
					+ "0"
					+ ","
					+ "0"
					+ ","
					+ "0"
					+ ","
					+ stadion.getAusbauKosten()
					+ ","
					+ stadion.getArenaId()
					+ " )");
			adapter.executeUpdate(statement);
		}
	}
	
	/**
	 * lädt die Finanzen zum angegeben HRF file ein
	 *
	 * @param hrfID TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Stadium getStadion(int hrfID) {
		ResultSet rs = null;
		Stadium stadion = null;
		String sql = null;

		sql = "SELECT * FROM "+getTableName()+" WHERE HRF_ID = " + hrfID;
		rs = adapter.executeQuery(sql);

		try {
			if (rs != null) {
				rs.first();
				stadion = new Stadium(rs);
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DatenbankZugriff.getStadion: " + e);
		}

		return stadion;
	}
	
}
