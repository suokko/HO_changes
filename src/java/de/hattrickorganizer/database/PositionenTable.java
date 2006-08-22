package de.hattrickorganizer.database;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.Vector;

import de.hattrickorganizer.tools.HOLogger;

public final class PositionenTable extends AbstractTable {

	/** tablename **/
	public final static String TABLENAME = "POSITIONEN";
	
	protected PositionenTable(JDBCAdapter  adapter){
		super(TABLENAME,adapter);
	}
	

	protected void initColumns() {
		columns = new ColumnDescriptor[5];
		columns[0]= new ColumnDescriptor("HRF_ID",Types.INTEGER,false);
		columns[1]= new ColumnDescriptor("ID",Types.INTEGER,false);
		columns[2]= new ColumnDescriptor("Aufstellungsname",Types.VARCHAR,false,256);
		columns[3]= new ColumnDescriptor("SpielerID",Types.INTEGER,false);
		columns[4]= new ColumnDescriptor("Taktik",Types.INTEGER,false);
	}
	
	/**
	 * lädt System Positionen
	 *
	 * @param hrfID TODO Missing Constructuor Parameter Documentation
	 * @param sysName TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Vector getSystemPositionen(int hrfID, String sysName) {
		ResultSet rs = null;
		de.hattrickorganizer.model.SpielerPosition pos = null;
		String sql = null;
		final Vector ret = new Vector();

		sql = "SELECT * FROM "+getTableName()+" WHERE HRF_ID = " + hrfID + " AND Aufstellungsname ='" + sysName + "'";
		rs = adapter.executeQuery(sql);

		try {
			if (rs != null) {
				rs.beforeFirst();

				while (rs.next()) {
					pos = new de.hattrickorganizer.model.SpielerPosition(rs.getInt("ID"),

					/*rs.getByte ( "Position" ), */
					rs.getInt("SpielerID"), rs.getByte("Taktik"));
					ret.add(pos);
				}
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DatenbankZugriff.getSystemPositionen: " + e);
		}

		return ret;
	}

	/**
	 * speichert System Positionen
	 *
	 * @param hrfId TODO Missing Constructuor Parameter Documentation
	 * @param positionen TODO Missing Constructuor Parameter Documentation
	 * @param sysName TODO Missing Constructuor Parameter Documentation
	 */
	public void saveSystemPositionen(int hrfId, Vector positionen, String sysName) {
		String statement = null;
		de.hattrickorganizer.model.SpielerPosition pos = null;

		//bereits vorhandenen Eintrag entdernen
		DBZugriff.instance().deleteSystem(hrfId, sysName);

		//speichern vorbereiten
		for (int i = 0;(positionen != null) && (sysName != null) && (i < positionen.size()); i++) {
			pos = (de.hattrickorganizer.model.SpielerPosition) positionen.elementAt(i);
			statement = "INSERT INTO "+getTableName()+" ( HRF_ID, ID, Aufstellungsname, SpielerID, Taktik ) VALUES(";
			statement += ("" + hrfId + "," + pos.getId() + ",'" + sysName + "'," + pos.getSpielerId() + "," + pos.getTaktik() + " )");

			adapter.executeUpdate(statement);
		}
	}	

}
