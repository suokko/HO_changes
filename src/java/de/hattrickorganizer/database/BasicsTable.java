package de.hattrickorganizer.database;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Vector;

import de.hattrickorganizer.gui.model.CBItem;
import de.hattrickorganizer.model.Basics;
import de.hattrickorganizer.tools.HOLogger;

public final class BasicsTable extends AbstractTable {

	/** tablename **/
	public final static String TABLENAME = "BASICS";
	
	protected BasicsTable(JDBCAdapter  adapter){
		super(TABLENAME,adapter);
	}

	@Override
	protected void initColumns() {
        columns = new ColumnDescriptor[10];
		columns[0]= new ColumnDescriptor("HRF_ID",Types.INTEGER,false,true);
		columns[1]= new ColumnDescriptor("Manager",Types.VARCHAR,false,127);
		columns[2]= new ColumnDescriptor("TeamID",Types.INTEGER,false);
		columns[3]= new ColumnDescriptor("TeamName",Types.VARCHAR,false,127);
		columns[4]= new ColumnDescriptor("Land",Types.INTEGER,false);
		columns[5]= new ColumnDescriptor("Liga",Types.INTEGER,false);
		columns[6]= new ColumnDescriptor("Saison",Types.INTEGER,false);
		columns[7]= new ColumnDescriptor("Spieltag",Types.INTEGER,false);
		columns[8]= new ColumnDescriptor("Datum",Types.TIMESTAMP,false);
		columns[9]= new ColumnDescriptor("Region",Types.INTEGER,false);
	}

	@Override
	protected String[] getCreateIndizeStatements() {
		return new String[] {
			"CREATE INDEX IBASICS_1 ON " + getTableName() + "(" + columns[0].getColumnName() + ")",
			"CREATE INDEX IBASICS_2 ON " + getTableName() + "(" + columns[8].getColumnName() + ")" };
	}
	
	/**
	 * speichert die Basdics
	 *
	 * @param hrfId TODO Missing Constructuor Parameter Documentation
	 * @param basics TODO Missing Constructuor Parameter Documentation
	 */
	protected void saveBasics(int hrfId, de.hattrickorganizer.model.Basics basics) {
		String statement = null;
		final String[] awhereS = { "HRF_ID" };
		final String[] awhereV = { "" + hrfId };

		if (basics != null) {
			//erst Vorhandene Aufstellung löschen
			delete( awhereS, awhereV );

			//insert vorbereiten
			statement = "INSERT INTO "+getTableName()+" ( TeamID , Manager , TeamName , Land , Liga , Saison , Spieltag , HRF_ID, Datum, Region ) VALUES(";
			statement
				+= (""
					+ basics.getTeamId()
					+ ",'"
					+ de.hattrickorganizer.database.DBZugriff.insertEscapeSequences(basics.getManager())
					+ "','"
					+ de.hattrickorganizer.database.DBZugriff.insertEscapeSequences(basics.getTeamName())
					+ "',"
					+ basics.getLand()
					+ ","
					+ basics.getLiga()
					+ ","
					+ basics.getSeason()
					+ ","
					+ basics.getSpieltag()
					+ ","
					+ hrfId
					+ ",'"
					+ basics.getDatum().toString()
					+ "',"
					+ basics.getRegionId()
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
	public Basics getBasics(int hrfID) {
		ResultSet rs = null;
		Basics basics = null;

		rs = getSelectByHrfID(hrfID);

		try {
			if (rs != null) {
				rs.first();
				basics = new Basics(rs);
				rs.close();
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DatenbankZugriff.getBasic: " + e);
		}

		return basics;
	}

	/**
	 * Gibt eine Vector mit HRF-CBItems zurück
	 *
	 * @param datum from which hrf has to be returned, used to load a subset of hrf
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Vector<CBItem> getCBItemHRFListe(Timestamp datum) {
		ResultSet rs = null;
		final String statement = "SELECT * FROM "+getTableName()+" WHERE Datum >='" + datum.toString() + "' ORDER BY Datum DESC";
		final Vector<CBItem> hrfs = new Vector<CBItem>();

		try {
			rs = adapter.executeQuery(statement);

			if (rs != null) {
				rs.beforeFirst();

				while (rs.next()) {
					hrfs.add(
						new de.hattrickorganizer.gui.model.CBItem(
							java.text.DateFormat.getDateTimeInstance().format(rs.getTimestamp("Datum"))
								+ " ( "
								+ de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Season")
								+ " "
								+ rs.getInt("Saison")
								+ "  "
								+ de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Spieltag")
								+ " "
								+ rs.getInt("Spieltag")
								+ " )",
							rs.getInt("HRF_ID")));
				}
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DatenbankZugriff.getCBItemHRFListe " + e);
		}

		return hrfs;
	}
	
	/**
	 * Gibt die HRFId vor dem Datum zurï¿½ck, wenn mï¿½glich
	 *
	 * @param time TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public int getHrfIDSameTraining(Timestamp time) {
		ResultSet rs = null;
		String sql = null;
		int hrfID = -1;
		Timestamp mintime = new Timestamp(time.getTime() - 259200000); //72 Std
		Timestamp hrfDate = null;

		//Die passende HRF-ID besorgen
		sql = "SELECT HRF_ID, Datum FROM "+getTableName()+" WHERE Datum<='" + time.toString() + "' AND Datum>='" + mintime.toString() + "' ORDER BY Datum DESC";
		rs = adapter.executeQuery(sql);

		try {
			if (rs != null) {
				//HRF vorher vorhanden?
				if (rs.first()) {
					hrfID = rs.getInt("HRF_ID");
					hrfDate = rs.getTimestamp("Datum");
				}
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(), "XMLExporter.getHRFID4Time: " + e);
		}

		if (hrfID != -1) {
			//todo sicherstellen das kein Trainingsdatum zwischen matchdate und hrfdate liegt

			Timestamp training4Hrf = DBZugriff.instance().getXtraDaten(hrfID).getTrainingDate();

			if ((training4Hrf.after(hrfDate)) && (training4Hrf.before(time))) //wenn hrfDate vor TrainingsDate und Matchdate nach Trainigsdate ->Abbruch!
				{
				hrfID = -1;
			}
		}

		return hrfID;
	}
		
}
