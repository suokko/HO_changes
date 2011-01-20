package de.hattrickorganizer.database;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.Vector;

import plugins.ISpielerPosition;
import de.hattrickorganizer.tools.HOLogger;

public final class PositionenTable extends AbstractTable {

	/** tablename **/
	public final static String TABLENAME = "POSITIONEN";
	
	protected PositionenTable(JDBCAdapter  adapter){
		super(TABLENAME,adapter);
	}
	

	@Override
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
	public Vector<ISpielerPosition> getSystemPositionen(int hrfID, String sysName) {
		ResultSet rs = null;
		de.hattrickorganizer.model.SpielerPosition pos = null;
		String sql = null;
		final Vector<ISpielerPosition> ret = new Vector<ISpielerPosition>();

		sql = "SELECT * FROM "+getTableName()+" WHERE HRF_ID = " + hrfID + " AND Aufstellungsname ='" + sysName + "'";
		rs = adapter.executeQuery(sql);

		try {
			if (rs != null) {
				rs.beforeFirst();

				while (rs.next()) {
					
					int roleID = rs.getInt("ID");
					int behavior = rs.getByte("Taktik");
					
					switch (behavior) {
					case ISpielerPosition.OLD_EXTRA_DEFENDER :
						roleID = ISpielerPosition.middleCentralDefender;
						behavior = ISpielerPosition.NORMAL;
						break;
					case ISpielerPosition.OLD_EXTRA_MIDFIELD :
						roleID = ISpielerPosition.centralInnerMidfield;
						behavior = ISpielerPosition.NORMAL;
						break;
					case ISpielerPosition.OLD_EXTRA_FORWARD :
						roleID = ISpielerPosition.centralForward;
						behavior = ISpielerPosition.NORMAL;
						break;
					case ISpielerPosition.OLD_EXTRA_DEFENSIVE_FORWARD :
						roleID = ISpielerPosition.centralForward;
						behavior = ISpielerPosition.DEFENSIVE;
				}
					
					if (roleID < ISpielerPosition.setPieces) {
						roleID = convertOldRoleToNew(roleID);
					}
					pos = new de.hattrickorganizer.model.SpielerPosition(roleID, rs.getInt("SpielerID"), (byte)behavior);
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
	public void saveSystemPositionen(int hrfId, Vector<ISpielerPosition> positionen, String sysName) {
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
	
	// Helper
	private int convertOldRoleToNew(int roleID) {
    	switch (roleID) {
    		case ISpielerPosition.oldKeeper :
    			return ISpielerPosition.keeper;
    		case ISpielerPosition.oldRightBack :
    			return ISpielerPosition.rightBack;
    		case ISpielerPosition.oldLeftCentralDefender :
    			return ISpielerPosition.leftCentralDefender;
    		case ISpielerPosition.oldRightCentralDefender :
    			return ISpielerPosition.rightCentralDefender;
    		case ISpielerPosition.oldLeftBack :
    			return ISpielerPosition.leftBack;
    		case ISpielerPosition.oldRightWinger :
    			return ISpielerPosition.rightWinger;
    		case ISpielerPosition.oldRightInnerMidfielder :
    			return ISpielerPosition.rightInnerMidfield;
    		case ISpielerPosition.oldLeftInnerMidfielder :
    			return ISpielerPosition.leftInnerMidfield;
    		case ISpielerPosition.oldLeftWinger:
    			return ISpielerPosition.leftWinger;
    		case ISpielerPosition.oldRightForward :
    			return ISpielerPosition.rightForward;
    		case ISpielerPosition.oldLeftForward :
    			return ISpielerPosition.leftForward;
    		case ISpielerPosition.oldSubstKeeper :
    			return ISpielerPosition.substKeeper;
    		case ISpielerPosition.oldSubstDefender :
    			return ISpielerPosition.substDefender;
    		case ISpielerPosition.oldSubstMidfielder :
    			return ISpielerPosition.substInnerMidfield;
    		case ISpielerPosition.oldSubstWinger :
    			return ISpielerPosition.substWinger;
    		case ISpielerPosition.oldSubstForward :
    			return ISpielerPosition.substForward;
    		default :
    			return roleID;
    	}
    }

}
