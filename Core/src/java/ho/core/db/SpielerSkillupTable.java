package ho.core.db;

import ho.core.constants.player.PlayerSkill;
import ho.core.model.HOModel;
import ho.core.model.HOVerwaltung;
import ho.core.model.Spieler;
import ho.core.util.HOLogger;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

final class SpielerSkillupTable extends AbstractTable {

	/** tablename **/						
	final static String TABLENAME = "SPIELERSKILLUP";
	private static Map<String,Vector<Object[]>> playerSkillup = null;

	SpielerSkillupTable(JDBCAdapter adapter) {
		super(TABLENAME, adapter);					
	}

	@Override
	protected void initColumns() {
		columns = new ColumnDescriptor[5];
		columns[0] = new ColumnDescriptor("HRF_ID", Types.INTEGER, false);
		columns[1] = new ColumnDescriptor("Datum", Types.TIMESTAMP, false);
		columns[2] = new ColumnDescriptor("SpielerID", Types.INTEGER, false);
		columns[3] = new ColumnDescriptor("Skill", Types.INTEGER, false);
		columns[4] = new ColumnDescriptor("Value", Types.INTEGER, false);

	}

	@Override
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
	void saveSkillup(int hrfId, int spielerId, Timestamp date, int skillValue, int skillCode) {
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

	Object[] getLastLevelUp(int skillCode, int spielerId) {
		Vector<Object[]> data = getSpielerSkillUp(spielerId);
		for (Iterator<Object[]> iter = data.iterator(); iter.hasNext();) {
			Object[] element = iter.next();
			int code = ((Integer) element[4]).intValue();			
			if (code==skillCode) {
				return new Object[] { element[2], Boolean.TRUE};									
			}
		}
		return new Object[] { new Timestamp(System.currentTimeMillis()), Boolean.FALSE};						
	}

	Vector<Object[]> getAllLevelUp(int skillCode, int spielerId) {		
		Vector<Object[]> data = getSpielerSkillUp(spielerId);
		Vector<Object[]> v = new Vector<Object[]>();
		for (Iterator<Object[]> iter = data.iterator(); iter.hasNext();) {
			Object[] element = iter.next();
			int code = ((Integer) element[4]).intValue();			
			if (code==skillCode) {
				v.add(new Object[] { element[2], Boolean.TRUE});						
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
			Integer element = iter.next();
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
					idVector.add(Integer.valueOf(rs.getInt("SpielerID")));
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
				v.add(new Object[] {Integer.valueOf(rs.getInt("HRF_ID")),Integer.valueOf(spielerId),rs.getTimestamp("datum"),Integer.valueOf(rs.getInt("Value")),Integer.valueOf(rs.getInt("Skill"))});															
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),e);
		}
		return v;
	}

	// -------------------------------- Importing PArt ----------------------------------------------

	void importNewSkillup(HOModel homodel) {
		Vector<Spieler> players = homodel.getAllSpieler();
		for (Iterator<Spieler> iter = players.iterator(); iter.hasNext();) {
			Spieler nPlayer = iter.next();
			Spieler oPlayer = HOVerwaltung.instance().getModel().getSpieler(nPlayer.getSpielerID());
			if (oPlayer!=null) {
				checkNewSkillup(nPlayer,nPlayer.getTorwart(),oPlayer.getTorwart(),PlayerSkill.KEEPER,homodel.getID());	
				checkNewSkillup(nPlayer,nPlayer.getSpielaufbau(),oPlayer.getSpielaufbau(),PlayerSkill.PLAYMAKING,homodel.getID());
				checkNewSkillup(nPlayer,nPlayer.getPasspiel(),oPlayer.getPasspiel(),PlayerSkill.PASSING,homodel.getID());
				checkNewSkillup(nPlayer,nPlayer.getFluegelspiel(),oPlayer.getFluegelspiel(),PlayerSkill.WINGER,homodel.getID());
				checkNewSkillup(nPlayer,nPlayer.getVerteidigung(),oPlayer.getVerteidigung(),PlayerSkill.DEFENDING,homodel.getID());
				checkNewSkillup(nPlayer,nPlayer.getTorschuss(),oPlayer.getTorschuss(),PlayerSkill.SCORING,homodel.getID());
				checkNewSkillup(nPlayer,nPlayer.getStandards(),oPlayer.getStandards(),PlayerSkill.SET_PIECES,homodel.getID());
				checkNewSkillup(nPlayer,nPlayer.getKondition(),oPlayer.getKondition(),PlayerSkill.STAMINA,homodel.getID());
				checkNewSkillup(nPlayer,nPlayer.getErfahrung(),oPlayer.getErfahrung(),PlayerSkill.EXPERIENCE,homodel.getID());				
				
			}
			
		}
	}
		
	private void checkNewSkillup(Spieler nPlayer, int newValue, int oldValue, int skill, int hrf) {
		if (newValue>oldValue) {
			storeSkillup(hrf,nPlayer.getSpielerID(),nPlayer.getHrfDate(),newValue,skill,true);
		}
		
	}

	void importFromSpieler() {
		playerSkillup = null;
		ResultSet rs = null;
		String sql = "SELECT DISTINCT SpielerID FROM SPIELER";
		final Vector<Integer> idVector = new Vector<Integer>();
		try {
			rs = adapter.executeQuery(sql);			
			if (rs != null) {
				rs.beforeFirst();
				while (rs.next()) {
					idVector.add(Integer.valueOf(rs.getInt("SpielerID")));
				}
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),e);
			HOLogger.instance().log(getClass(),"DatenbankZugriff.getSpieler: " + e);
		}	
		adapter.executeUpdate("DELETE FROM "+getTableName());
		for (Iterator<Integer> iter = idVector.iterator(); iter.hasNext();) {
			Integer element = iter.next();
			importSpieler(element.intValue());
		}
	}

	private void importSpieler(int spielerId) {	
		importSkillUp(PlayerSkill.PLAYMAKING,spielerId);
		importSkillUp(PlayerSkill.STAMINA,spielerId);
		importSkillUp(PlayerSkill.DEFENDING,spielerId);
		importSkillUp(PlayerSkill.KEEPER,spielerId);
		importSkillUp(PlayerSkill.WINGER,spielerId);
		importSkillUp(PlayerSkill.SCORING,spielerId);
		importSkillUp(PlayerSkill.PASSING,spielerId);
		importSkillUp(PlayerSkill.SET_PIECES,spielerId);
		importSkillUp(PlayerSkill.EXPERIENCE,spielerId);
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
					v.add(new Object[] {Integer.valueOf(rs.getInt("HRF_ID")),Integer.valueOf(spielerId),rs.getTimestamp("datum"),Integer.valueOf(value),Integer.valueOf(skillCode)});															
				}
				lastValue = value;
			}
			for (Iterator<Object[]> iter = v.iterator(); iter.hasNext();) {
				Object[] element = iter.next();
				storeSkillup(((Integer)element[0]).intValue(),((Integer)element[1]).intValue(),((Timestamp)element[2]),((Integer)element[3]).intValue(),((Integer)element[4]).intValue(),false);
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),e);
		}
	}

	private String getKey(int code) {
		String key = "Spielaufbau";

		switch (code) {
			case PlayerSkill.SET_PIECES:
				key = "Standards";
				break;

			case PlayerSkill.PASSING:
				key = "Passpiel";
				break;

			case PlayerSkill.SCORING:
				key = "Torschuss";
				break;

			case PlayerSkill.PLAYMAKING:
				key = "Spielaufbau";
				break;

			case PlayerSkill.WINGER:
				key = "Fluegel";
				break;

			case PlayerSkill.KEEPER:
				key = "Torwart";
				break;

			case PlayerSkill.DEFENDING:
				key = "Verteidigung";
				break;

			case PlayerSkill.STAMINA:
				key = "Kondition";
				break;

			case PlayerSkill.EXPERIENCE:
				key = "Erfahrung";
				break;
		}
		return key;
 
	}

}
