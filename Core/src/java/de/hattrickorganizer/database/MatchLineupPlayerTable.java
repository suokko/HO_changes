package de.hattrickorganizer.database;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.Vector;

import plugins.IMatchLineupPlayer;
import plugins.ISpielerPosition;

import de.hattrickorganizer.model.matches.MatchLineupPlayer;
import de.hattrickorganizer.tools.HOLogger;

public final class MatchLineupPlayerTable extends AbstractTable {

	/** tablename **/
	public final static String TABLENAME = "MATCHLINEUPPLAYER";											
	
	protected MatchLineupPlayerTable(JDBCAdapter  adapter){
		super(TABLENAME,adapter);
	}

	@Override
	protected void initColumns() {
		columns = new ColumnDescriptor[13];
		columns[0]= new ColumnDescriptor("MatchID",Types.INTEGER,false);
		columns[1]= new ColumnDescriptor("TeamID",Types.INTEGER,false);
		columns[2]= new ColumnDescriptor("SpielerID",Types.INTEGER,false);
		columns[3]= new ColumnDescriptor("RoleID",Types.INTEGER,false);
		columns[4]= new ColumnDescriptor("Taktik",Types.INTEGER,false);
		columns[5]= new ColumnDescriptor("PositionCode",Types.INTEGER,false);
		columns[6]= new ColumnDescriptor("VName",Types.VARCHAR,false,255);
		columns[7]= new ColumnDescriptor("NickName",Types.VARCHAR,false,255);
		columns[8]= new ColumnDescriptor("Name",Types.VARCHAR,false,255);
		columns[9]= new ColumnDescriptor("Rating",Types.REAL,false);
		columns[10]= new ColumnDescriptor("HoPosCode",Types.INTEGER,false);
		columns[11]= new ColumnDescriptor("STATUS",Types.INTEGER,false);
		columns[12]= new ColumnDescriptor("FIELDPOS",Types.INTEGER,false);

	}

	@Override
	protected String[] getCreateIndizeStatements(){
		return new String[]{
			"CREATE INDEX iMATCHLINEUPPLAYER_1 ON "+getTableName()+"("+columns[2].getColumnName()+")",
			"CREATE INDEX iMATCHLINEUPPLAYER_2 ON "+getTableName()+"("+columns[0].getColumnName()+","+columns[1].getColumnName()+")"
		};
	}
	
	/**
	 * Gibt eine Liste an Ratings zurück, auf denen der Spieler gespielt hat: 0 = Max 1 = Min 2 =
	 * Durchschnitt 3 = posid
	 *
	 * @param spielerid TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Vector<float[]> getAlleBewertungen(int spielerid) {
		final Vector<float[]> bewertung = new Vector<float[]>();

		//Alle Möglichen Kombos durchlaufen
		for (byte i = 0; i <= ISpielerPosition.STURM_AUS; i++) {
			final float[] temp = getBewertungen4PlayerUndPosition(spielerid, i);

			//Min ein Wert für die Pos gefunden -> Max > 0
			if (temp[0] > 0) {
				//Erste Wert statt aktuellen wert mit der Posid füllen
				temp[3] = i;

				bewertung.add(temp);
			}
		}

		return bewertung;
	}
	
	/**
	 * Gibt die beste, schlechteste und durchschnittliche Bewertung für den Spieler, sowie die
	 * Anzahl der Bewertungen zurück // Match
	 *
	 * @param spielerid TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public float[] getBewertungen4Player(int spielerid) {
		//Max, Min, Durchschnitt
		final float[] bewertungen = { 0f, 0f, 0f, 0f };

		try {
			final String sql = "SELECT MatchID, Rating FROM "+getTableName()+" WHERE SpielerID=" + spielerid;
			final ResultSet rs = adapter.executeQuery(sql);

			rs.beforeFirst();

			int i = 0;

			while (rs.next()) {
				float rating = rs.getFloat("Rating");

				if (rating > -1) {
					bewertungen[0] = Math.max(bewertungen[0], rating);

					if (bewertungen[1] == 0) {
						bewertungen[1] = rating;
					}

					bewertungen[1] = Math.min(bewertungen[1], rating);
					bewertungen[2] += rating;

					//HOLogger.instance().log(getClass(),rs.getInt("MatchID") + " : " + rating);

					i++;
				}
			}

			if (i > 0) {
				bewertungen[2] = (bewertungen[2] / i);
			}

			bewertungen[3] = i;

			//HOLogger.instance().log(getClass(),"Ratings     : " + i + " - " + bewertungen[0] + " / " + bewertungen[1] + " / " + bewertungen[2] + " / / " + bewertungen[3]);
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DatenbankZugriff.getBewertungen4Player : " + e);
		}

		return bewertungen;
	}
	
	/**
	 * Gibt die beste, schlechteste und durchschnittliche Bewertung für den Spieler, sowie die
	 * Anzahl der Bewertungen zurück // Match
	 *
	 * @param spielerid Spielerid
	 * @param position Usere positionscodierung mit taktik
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public float[] getBewertungen4PlayerUndPosition(int spielerid, byte position) {
		//Max, Min, Durchschnitt
		final float[] bewertungen = { 0f, 0f, 0f, 0f };

		try {
			final String sql = "SELECT MatchID, Rating FROM "+getTableName()+" WHERE SpielerID=" + spielerid + " AND HoPosCode=" + position;
			final ResultSet rs = adapter.executeQuery(sql);

			rs.beforeFirst();

			int i = 0;

			while (rs.next()) {
				float rating = rs.getFloat("Rating");

				if (rating > -1) {
					bewertungen[0] = Math.max(bewertungen[0], rating);

					if (bewertungen[1] == 0) {
						bewertungen[1] = rating;
					}

					bewertungen[1] = Math.min(bewertungen[1], rating);
					bewertungen[2] += rating;

					//HOLogger.instance().log(getClass(),rs.getInt("MatchID") + " : " + rating);

					i++;
				}
			}

			if (i > 0) {
				bewertungen[2] = (bewertungen[2] / i);
			}

			bewertungen[3] = i;

			//HOLogger.instance().log(getClass(),"Ratings Pos : " + i + " - " + bewertungen[0] + " / " + bewertungen[1] + " / " + bewertungen[2] + " / / " + bewertungen[3]);
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DatenbankZugriff.getBewertungen4PlayerUndPosition : " + e);
		}

		return bewertungen;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param player TODO Missing Method Parameter Documentation
	 * @param matchID TODO Missing Method Parameter Documentation
	 * @param teamID TODO Missing Method Parameter Documentation
	 */
	protected void storeMatchLineupPlayer(MatchLineupPlayer player, int matchID, int teamID) {
		if (player != null) {
			final String[] where = { "MatchID" , "TeamID", "RoleID"};
			final String[] werte = { "" + matchID, "" + teamID, "" + player.getId()};			
			delete(where, werte);			
			String sql = null;

			//saven
			try {
				//insert vorbereiten
				sql = "INSERT INTO "+getTableName()+" ( MatchID, TeamID, SpielerID, RoleID, Taktik, PositionCode, VName, NickName, Name, Rating, HoPosCode, STATUS, FIELDPOS ) VALUES(";
				sql
					+= (matchID
						+ ","
						+ teamID
						+ ","
						+ player.getSpielerId()
						+ ","
						+ player.getId()
						+ ", "
						+ player.getTaktik()
						+ ", "
						+ player.getPositionCode()
						+ ", '"
						+ DBZugriff.insertEscapeSequences(player.getSpielerVName())
						+ "', '"
						+ DBZugriff.insertEscapeSequences(player.getNickName())
						+ "', '"
						+ DBZugriff.insertEscapeSequences(player.getSpielerName())
						+ "',"
						+ player.getRating()
						+ ", "
						+ player.getPosition()
						+ ", 0"
						+ ","
						+ player.getPositionCode()
						+ " )");
				adapter.executeUpdate(sql);
			} catch (Exception e) {
				HOLogger.instance().log(getClass(),"DB.storeMatchLineupPlayer Error" + e);
				HOLogger.instance().log(getClass(),e);
			}
		}
	}	

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param matchID TODO Missing Method Parameter Documentation
	 * @param teamID TODO Missing Method Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Vector<IMatchLineupPlayer> getMatchLineupPlayers(int matchID, int teamID) {
		MatchLineupPlayer player = null;
		final Vector<IMatchLineupPlayer> vec = new Vector<IMatchLineupPlayer>();
		String sql = null;
		ResultSet rs = null;
		int roleID;
		int behaivior;
		int spielerID;
		double rating;
		String vname;
		String name;
		String nickName;
		int positionsCode;

		try {
			sql = "SELECT * FROM "+getTableName()+" WHERE MatchID = " + matchID + " AND TeamID = " + teamID;
			rs = adapter.executeQuery(sql);

			rs.beforeFirst();

			while (rs.next()) {
				roleID = rs.getInt("RoleID");
				behaivior = rs.getInt("Taktik");
				spielerID = rs.getInt("SpielerID");
				rating = rs.getDouble("Rating");
				vname = new String(DBZugriff.deleteEscapeSequences(rs.getString("VName")));
				nickName = new String(DBZugriff.deleteEscapeSequences(rs.getString("NickName")));
				name = new String(DBZugriff.deleteEscapeSequences(rs.getString("Name")));
				positionsCode = rs.getInt("PositionCode");

				player = new MatchLineupPlayer(roleID, behaivior, spielerID, rating, vname, nickName, name, positionsCode, rs.getInt("STATUS"), rs.getInt("FIELDPOS"));
				vec.add(player);
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DB.getMatchLineupTeam Error" + e);
		}

		return vec;
	}

}
