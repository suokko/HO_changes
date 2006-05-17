package de.hattrickorganizer.database;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.Vector;

import de.hattrickorganizer.model.Liga;
import de.hattrickorganizer.tools.HOLogger;

public final class LigaTable extends AbstractTable {

	/** tablename **/
	public final static String TABLENAME = "LIGA";
	
	protected LigaTable(JDBCAdapter  adapter){
		super(TABLENAME,adapter);
	}
	
	protected void initColumns() {
		columns = new ColumnDescriptor[7];
		columns[0]= new ColumnDescriptor("HRF_ID",Types.INTEGER,false,true);
		columns[1]= new ColumnDescriptor("LigaName",Types.VARCHAR,false,127);
		columns[2]= new ColumnDescriptor("Punkte",Types.INTEGER,false);
		columns[3]= new ColumnDescriptor("ToreFuer",Types.INTEGER,false);
		columns[4]= new ColumnDescriptor("ToreGegen",Types.INTEGER,false);
		columns[5]= new ColumnDescriptor("Platz",Types.INTEGER,false);
		columns[6]= new ColumnDescriptor("Spieltag",Types.INTEGER,false);
	}

	protected String[] getCreateIndizeStatements() {
		return new String[] {
			"CREATE INDEX ILIGA_1 ON " + getTableName() + "(" + columns[0].getColumnName() + ")"};
	}
	
	/**
	 * speichert die Basdics
	 *
	 * @param hrfId TODO Missing Constructuor Parameter Documentation
	 * @param liga TODO Missing Constructuor Parameter Documentation
	 */
	protected void saveLiga(int hrfId, Liga liga) {
		String statement = null;
		final String[] awhereS = { columns[0].getColumnName() };
		final String[] awhereV = { "" + hrfId };

		if (liga != null) {
			//erst Vorhandene Aufstellung l�schen
			delete( awhereS, awhereV );
			//insert vorbereiten
			statement = "INSERT INTO "+getTableName()+" ( LigaName , Punkte , ToreFuer , ToreGegen , Platz , Spieltag , HRF_ID ) VALUES(";
			statement
				+= ("'" + liga.getLiga() + "'," + liga.getPunkte() + "," + liga.getToreFuer() + "," + liga.getToreGegen() + "," + liga.getPlatzierung() + "," + liga.getSpieltag() + "," + hrfId + " )");
			adapter.executeUpdate(statement);
		}
	}
	
	/**
	 * Gibt alle bekannten Ligaids zur�ck
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Integer[] getAllLigaIDs() {
		final Vector vligaids = new Vector();
		Integer[] ligaids = null;

		try {
			final String sql = "SELECT DISTINCT LigaID FROM SPIELPLAN";
			final ResultSet rs = adapter.executeQuery(sql);

			rs.beforeFirst();

			while (rs.next()) {
				vligaids.add(new Integer(rs.getInt("LigaID")));
			}

			//Umkopieren
			ligaids = new Integer[vligaids.size()];

			for (int i = 0; i < vligaids.size(); i++) {
				ligaids[i] = (Integer) vligaids.get(i);
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DatenbankZugriff.getAllLigaIDs : " + e);
		}

		return ligaids;
	}

	/**
	 * l�dt die Basics zum angegeben HRF file ein
	 *
	 * @param hrfID TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Liga getLiga(int hrfID) {
		ResultSet rs = null;
		Liga liga = null;

		rs = getSelectByHrfID(hrfID);

		try {
			if (rs != null) {
				rs.first();
				liga = new Liga(rs);
				rs.close();
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DatenbankZugriff.getLiga: " + e);
		}

		return liga;
	}
	
}
