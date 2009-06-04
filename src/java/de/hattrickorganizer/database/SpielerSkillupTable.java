package de.hattrickorganizer.database;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import plugins.ISpieler;
import de.hattrickorganizer.model.HOModel;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.tools.HOLogger;

public final class SpielerSkillupTable extends AbstractTable {

	/** tablename **/						
	public final static String TABLENAME = "SPIELERSKILLUP";
	private static Map<String,Vector<Object[]>> playerSkillup = null;

	protected SpielerSkillupTable(JDBCAdapter adapter) {
		super(TABLENAME, adapter);					
	}

	protected void initColumns() {
		columns = new ColumnDescriptor[5];
		columns[0] = new ColumnDescriptor("HRF_ID", Types.INTEGER, false);
		columns[1] = new ColumnDescriptor("Datum", Types.TIMESTAMP, false);
		columns[2] = new ColumnDescriptor("SpielerID", Types.INTEGER, false);
		columns[3] = new ColumnDescriptor("Skill", Types.INTEGER, false);
		columns[4] = new ColumnDescriptor("Value", Types.INTEGER, false);

	}

	protected String[] getCreateIndizeStatements() {
		return new String[] {
			"CREATE INDEX iSkillup_1 ON " + getTableName() + "(" + columns[2].getColumnName() + ")",
			"CREATE INDEX iSkillup_2 ON " + getTableName() + "(" + columns[2].getColumnName() + "," + columns[3].getColumnName() + ")"};
	}

	/**
	 * speichert die Spieler
	 *
	 * @param hrfId TODO Missing Constructuor Parameter Documentation
	 * @param spieler TODO Missing Constructuor Parameter Documentation
	 * @param date TODO Missing Constructuor Parameter Documentation
	 */
	public void saveSkillup(int hrfId, int spielerId, Timestamp date, int skillValue, int skillCode) {
		storeSkillup(hrfId,spielerId,date,skillValue,skillCode,true);
	}

	private void storeSkillup(int hrfId, int spielerId, Timestamp date, int skillValue, int skillCode, boolean reload) {
		String statement = null;

		//erst Vorhandene Aufstellung löschen
		final String[] awhereS = { "HRF_ID", "SpielerID", "Skill" };
		final String[] awhereV = { "" + hrfId, "" + spielerId, "" +skillCode};

		delete(awhereS, awhereV);

		//insert vorbereiten
		statement =
			"INSERT INTO "+getTableName()+" ( HRF_ID , Datum , SpielerID , Skill , Value ) VALUES(";
		statement
			+= (""
				+ hrfId
				+ ",'"
				+ date
				+ "', "
				+ spielerId
				+ ","
				+ skillCode
				+ ","
				+ skillValue
				+ " )");
		adapter.executeUpdate(statement);
		if (reload) {
			Vector<Object[]> data = getSpielerSkillUp(spielerId);
			data.clear();
			data.addAll(loadSpieler(spielerId));						
		}				
	}

	public Object[] getLastLevelUp(int skillCode, int spielerId) {
		Vector<Object[]> data = getSpielerSkillUp(spielerId);
		for (Iterator<Object[]> iter = data.iterator(); iter.hasNext();) {
			Object[] element = (Object[]) iter.next();
			int code = ((Integer) element[4]).intValue();			
			if (code==skillCode) {
				return new Object[] { element[2], new Boolean(true)};									
			}
		}
		return new Object[] { new Timestamp(System.currentTimeMillis()), new Boolean(false)};						
	}

	public Vector<Object[]> getAllLevelUp(int skillCode, int spielerId) {		
		Vector<Object[]> data = getSpielerSkillUp(spielerId);
		Vector<Object[]> v = new Vector<Object[]>();
		for (Iterator<Object[]> iter = data.iterator(); iter.hasNext();) {
			Object[] element = (Object[]) iter.next();
			int code = ((Integer) element[4]).intValue();			
			if (code==skillCode) {
				v.add(new Object[] { element[2], new Boolean(true)});						
			}
		}
		return v;
	}

	private Vector<Object[]> getSpielerSkillUp(int spielerId) {
		if (playerSkillup==null) {
			populate();
		}
		Vector<Object[]> v = playerSkillup.get(""+spielerId);
		if (v==null) {
			v = new Vector<Object[]>();
			playerSkillup.put(""+spielerId,v);	
		}
		return v;
	}
	
	private void populate() {

		Vector<Integer> idVector = getPlayerList();
		if (idVector.size()==0) {
			importFromSpieler();
			idVector = getPlayerList();			
		}  
		playerSkillup = new HashMap<String,Vector<Object[]>>();				
		for (Iterator<Integer> iter = idVector.iterator(); iter.hasNext();) {
			Integer element = (Integer) iter.next();
			playerSkillup.put(""+element.intValue(),loadSpieler(element.intValue()));
		}
		
	}

	private Vector<Integer> getPlayerList() {
		Vector<Integer> idVector = new Vector<Integer>();
		ResultSet rs;
		String sql = "SELECT DISTINCT SpielerID FROM "+getTableName();
		try {
			rs = adapter.executeQuery(sql);			
			if (rs != null) {
				rs.beforeFirst();
				while (rs.next()) {
					idVector.add(new Integer(rs.getInt("SpielerID")));
				}
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),e);
			HOLogger.instance().log(getClass(),"DatenbankZugriff.getSpieler: " + e);
		}
		return idVector;
	}

	private Vector<Object[]> loadSpieler(int spielerId) {		
		Vector<Object[]> v = new Vector<Object[]>();				
		try {
			String sql = "SELECT * FROM "+getTableName()+" WHERE SpielerID=" + spielerId + " Order By Datum DESC";
			ResultSet rs = adapter.executeQuery(sql);
			rs.beforeFirst();
			while (rs.next()) {
				v.add(new Object[] {new Integer(rs.getInt("HRF_ID")),new Integer(spielerId),rs.getTimestamp("datum"),new Integer(rs.getInt("Value")),new Integer(rs.getInt("Skill"))});															
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),e);
		}
		return v;
	}

	// -------------------------------- Importing PArt ----------------------------------------------

	public void importNewSkillup(HOModel homodel) {
		Vector<ISpieler> players = homodel.getAllSpieler();
		for (Iterator<ISpieler> iter = players.iterator(); iter.hasNext();) {
			ISpieler nPlayer = (ISpieler) iter.next();
			ISpieler oPlayer = HOVerwaltung.instance().getModel().getSpieler(nPlayer.getSpielerID());
			if (oPlayer!=null) {
				checkNewSkillup(nPlayer,nPlayer.getTorwart(),oPlayer.getTorwart(),ISpieler.SKILL_TORWART,homodel.getID());	
				checkNewSkillup(nPlayer,nPlayer.getSpielaufbau(),oPlayer.getSpielaufbau(),ISpieler.SKILL_SPIELAUFBAU,homodel.getID());
				checkNewSkillup(nPlayer,nPlayer.getPasspiel(),oPlayer.getPasspiel(),ISpieler.SKILL_PASSSPIEL,homodel.getID());
				checkNewSkillup(nPlayer,nPlayer.getFluegelspiel(),oPlayer.getFluegelspiel(),ISpieler.SKILL_FLUEGEL,homodel.getID());
				checkNewSkillup(nPlayer,nPlayer.getVerteidigung(),oPlayer.getVerteidigung(),ISpieler.SKILL_VERTEIDIGUNG,homodel.getID());
				checkNewSkillup(nPlayer,nPlayer.getTorschuss(),oPlayer.getTorschuss(),ISpieler.SKILL_TORSCHUSS,homodel.getID());
				checkNewSkillup(nPlayer,nPlayer.getStandards(),oPlayer.getStandards(),ISpieler.SKILL_STANDARDS,homodel.getID());
				checkNewSkillup(nPlayer,nPlayer.getKondition(),oPlayer.getKondition(),ISpieler.SKILL_KONDITION,homodel.getID());
				checkNewSkillup(nPlayer,nPlayer.getErfahrung(),oPlayer.getErfahrung(),ISpieler.SKILL_EXPIERIENCE,homodel.getID());				
				
			}
			
		}
	}
		
	private void checkNewSkillup(ISpieler nPlayer, int newValue, int oldValue, int skill, int hrf) {
		if (newValue>oldValue) {
			storeSkillup(hrf,nPlayer.getSpielerID(),nPlayer.getHrfDate(),newValue,skill,true);
		}
		
	}

	public void importFromSpieler() {
		playerSkillup = null;
		ResultSet rs = null;
		String sql = "SELECT DISTINCT SpielerID FROM SPIELER";
		final Vector<Integer> idVector = new Vector<Integer>();
		try {
			rs = adapter.executeQuery(sql);			
			if (rs != null) {
				rs.beforeFirst();
				while (rs.next()) {
					idVector.add(new Integer(rs.getInt("SpielerID")));
				}
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),e);
			HOLogger.instance().log(getClass(),"DatenbankZugriff.getSpieler: " + e);
		}	
		adapter.executeUpdate("DELETE FROM "+getTableName());
		for (Iterator<Integer> iter = idVector.iterator(); iter.hasNext();) {
			Integer element = (Integer) iter.next();
			importSpieler(element.intValue());
		}
	}

	private void importSpieler(int spielerId) {	
		importSkillUp(ISpieler.SKILL_SPIELAUFBAU,spielerId);
		importSkillUp(ISpieler.SKILL_KONDITION,spielerId);
		importSkillUp(ISpieler.SKILL_VERTEIDIGUNG,spielerId);
		importSkillUp(ISpieler.SKILL_TORWART,spielerId);
		importSkillUp(ISpieler.SKILL_FLUEGEL,spielerId);
		importSkillUp(ISpieler.SKILL_TORSCHUSS,spielerId);
		importSkillUp(ISpieler.SKILL_PASSSPIEL,spielerId);
		importSkillUp(ISpieler.SKILL_STANDARDS,spielerId);
		importSkillUp(ISpieler.SKILL_EXPIERIENCE,spielerId);
	}
	
	private void importSkillUp(int skillCode, int spielerId) {
		try {	
			String key = getKey(skillCode);				
			String sql = "SELECT HRF_ID, datum, " + key + " FROM SPIELER WHERE SpielerID=" + spielerId + " Order By Datum ASC";
			ResultSet rs = adapter.executeQuery(sql);
			rs.beforeFirst();
			int lastValue = -1;
			if (rs.next()) {
				lastValue = rs.getInt(key);
			}
			Vector<Object[]> v = new Vector<Object[]>();					
			while (rs.next()) {
				int value = rs.getInt(key);
				if (value > lastValue) {
					v.add(new Object[] {new Integer(rs.getInt("HRF_ID")),new Integer(spielerId),rs.getTimestamp("datum"),new Integer(value),new Integer(skillCode)});															
				}
				lastValue = value;
			}
			for (Iterator<Object[]> iter = v.iterator(); iter.hasNext();) {
				Object[] element = (Object[]) iter.next();
				storeSkillup(((Integer)element[0]).intValue(),((Integer)element[1]).intValue(),((Timestamp)element[2]),((Integer)element[3]).intValue(),((Integer)element[4]).intValue(),false);
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),e);
		}
	}

	private String getKey(int code) {
		String key = "Spielaufbau";

		switch (code) {
			case ISpieler.SKILL_STANDARDS:
				key = "Standards";
				break;

			case ISpieler.SKILL_PASSSPIEL:
				key = "Passpiel";
				break;

			case ISpieler.SKILL_TORSCHUSS:
				key = "Torschuss";
				break;

			case ISpieler.SKILL_SPIELAUFBAU:
				key = "Spielaufbau";
				break;

			case ISpieler.SKILL_FLUEGEL:
				key = "Fluegel";
				break;

			case ISpieler.SKILL_TORWART:
				key = "Torwart";
				break;

			case ISpieler.SKILL_VERTEIDIGUNG:
				key = "Verteidigung";
				break;

			case ISpieler.SKILL_KONDITION:
				key = "Kondition";
				break;

			case ISpieler.SKILL_EXPIERIENCE:
				key = "Erfahrung";
				break;
		}
		return key;
 
	}

}
