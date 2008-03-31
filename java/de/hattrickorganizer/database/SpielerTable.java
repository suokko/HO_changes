package de.hattrickorganizer.database;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Vector;

import plugins.ISpieler;
import de.hattrickorganizer.model.Spieler;
import de.hattrickorganizer.tools.HOLogger;

public final class SpielerTable extends AbstractTable {

	/** tablename **/
	public final static String TABLENAME = "SPIELER";

	protected SpielerTable(JDBCAdapter adapter) {
		super(TABLENAME, adapter);
	}

	protected void initColumns() {
		columns = new ColumnDescriptor[57];
		columns[0] = new ColumnDescriptor("HRF_ID", Types.INTEGER, false);
		columns[1] = new ColumnDescriptor("Datum", Types.TIMESTAMP, false);
		columns[2] = new ColumnDescriptor("GelbeKarten", Types.INTEGER, false);
		columns[3] = new ColumnDescriptor("SpielerID", Types.INTEGER, false);
		columns[4] = new ColumnDescriptor("Name", Types.VARCHAR, true, 127);
		columns[5] = new ColumnDescriptor("Age", Types.INTEGER, false);
		columns[6] = new ColumnDescriptor("Kondition", Types.INTEGER, false);
		columns[7] = new ColumnDescriptor("Form", Types.INTEGER, false);
		columns[8] = new ColumnDescriptor("Torwart", Types.INTEGER, false);
		columns[9] = new ColumnDescriptor("Verteidigung", Types.INTEGER, false);
		columns[10] = new ColumnDescriptor("Spielaufbau", Types.INTEGER, false);
		columns[11] = new ColumnDescriptor("Fluegel", Types.INTEGER, false);
		columns[12] = new ColumnDescriptor("Torschuss", Types.INTEGER, false);
		columns[13] = new ColumnDescriptor("Passpiel", Types.INTEGER, false);
		columns[14] = new ColumnDescriptor("Standards", Types.INTEGER, false);
		columns[15] = new ColumnDescriptor("SubTorwart", Types.REAL, false);
		columns[16] = new ColumnDescriptor("SubVerteidigung", Types.REAL, false);
		columns[17] = new ColumnDescriptor("SubSpielaufbau", Types.REAL, false);
		columns[18] = new ColumnDescriptor("SubFluegel", Types.REAL, false);
		columns[19] = new ColumnDescriptor("SubTorschuss", Types.REAL, false);
		columns[20] = new ColumnDescriptor("SubPasspiel", Types.REAL, false);
		columns[21] = new ColumnDescriptor("SubStandards", Types.REAL, false);
		columns[22] = new ColumnDescriptor("OffsetTorwart", Types.REAL, false);
		columns[23] = new ColumnDescriptor("OffsetVerteidigung", Types.REAL, false);
		columns[24] = new ColumnDescriptor("OffsetSpielaufbau", Types.REAL, false);
		columns[25] = new ColumnDescriptor("OffsetFluegel", Types.REAL, false);
		columns[26] = new ColumnDescriptor("OffsetTorschuss", Types.REAL, false);
		columns[27] = new ColumnDescriptor("OffsetPasspiel", Types.REAL, false);
		columns[28] = new ColumnDescriptor("OffsetStandards", Types.REAL, false);
		columns[29] = new ColumnDescriptor("iSpezialitaet", Types.INTEGER, false);
		columns[30] = new ColumnDescriptor("sSpezialitaet", Types.VARCHAR, false, 127);
		columns[31] = new ColumnDescriptor("iCharakter", Types.INTEGER, false);
		columns[32] = new ColumnDescriptor("sCharakter", Types.VARCHAR, false, 127);
		columns[33] = new ColumnDescriptor("iAnsehen", Types.INTEGER, false);
		columns[34] = new ColumnDescriptor("sAnsehen", Types.VARCHAR, false, 127);
		columns[35] = new ColumnDescriptor("iAgressivitaet", Types.INTEGER, false);
		columns[36] = new ColumnDescriptor("sAgressivitaet", Types.VARCHAR, false, 127);
		columns[37] = new ColumnDescriptor("Fuehrung", Types.INTEGER, false);
		columns[38] = new ColumnDescriptor("Erfahrung", Types.INTEGER, false);
		columns[39] = new ColumnDescriptor("Gehalt", Types.INTEGER, false);
		columns[40] = new ColumnDescriptor("Bonus", Types.INTEGER, false);
		columns[41] = new ColumnDescriptor("Land", Types.INTEGER, false);
		columns[42] = new ColumnDescriptor("Marktwert", Types.INTEGER, false);
		columns[43] = new ColumnDescriptor("Verletzt", Types.INTEGER, false);
		columns[44] = new ColumnDescriptor("ToreFreund", Types.INTEGER, false);
		columns[45] = new ColumnDescriptor("ToreLiga", Types.INTEGER, false);
		columns[46] = new ColumnDescriptor("TorePokal", Types.INTEGER, false);
		columns[47] = new ColumnDescriptor("ToreGesamt", Types.INTEGER, false);
		columns[48] = new ColumnDescriptor("Hattrick", Types.INTEGER, false);
		columns[49] = new ColumnDescriptor("Bewertung", Types.INTEGER, false);
		columns[50] = new ColumnDescriptor("TrainerTyp", Types.INTEGER, false);
		columns[51] = new ColumnDescriptor("Trainer", Types.INTEGER, false);
		columns[52] = new ColumnDescriptor("PlayerNumber", Types.INTEGER, false);
		columns[53] = new ColumnDescriptor("TransferListed", Types.INTEGER, false);
		columns[54] = new ColumnDescriptor("Caps", Types.INTEGER, false);
		columns[55] = new ColumnDescriptor("CapsU20", Types.INTEGER, false);
		columns[56] = new ColumnDescriptor("AgeDays", Types.INTEGER, false);

	}

	protected String[] getCreateIndizeStatements() {
		return new String[] {
			"CREATE INDEX iSpieler_1 ON " + getTableName() + "(" + columns[3].getColumnName() + "," + columns[1].getColumnName() + ")",
			"CREATE INDEX iSpieler_2 ON " + getTableName() + "(" + columns[0].getColumnName() + ")" };
	}

	/**
	 * speichert die Spieler
	 *
	 * @param hrfId TODO Missing Constructuor Parameter Documentation
	 * @param spieler TODO Missing Constructuor Parameter Documentation
	 * @param date TODO Missing Constructuor Parameter Documentation
	 */
	public void saveSpieler(int hrfId, Vector spieler, Timestamp date) {
		String statement = null;
		final String[] awhereS = { "HRF_ID" };
		final String[] awhereV = { "" + hrfId };
		Spieler player = null;

		if (spieler != null) {
			//erst Vorhandene Aufstellung löschen
			delete(awhereS, awhereV);

			for (int i = 0; i < spieler.size(); i++) {
				player = (Spieler) spieler.elementAt(i);

				//insert vorbereiten
				statement =
					"INSERT into "+getTableName()+" ( GelbeKarten , SpielerID , Name , Age , AgeDays , "
						+ "Kondition , Form , Torwart , Verteidigung , Spielaufbau , Fluegel , "
						+ "Torschuss , Passpiel , Standards , SubTorwart , SubVerteidigung , "
						+ "SubSpielaufbau , SubFluegel , SubTorschuss , SubPasspiel , SubStandards , "
						+ "OffsetTorwart , OffsetVerteidigung , OffsetSpielaufbau , OffsetFluegel , "
						+ "OffsetTorschuss , OffsetPasspiel , OffsetStandards , iSpezialitaet , "
						+ "sSpezialitaet , iCharakter , sCharakter , iAnsehen , sAnsehen , "
						+ "iAgressivitaet , sAgressivitaet , Fuehrung , Erfahrung , Gehalt , "
						+ "Bonus , Land , Marktwert , Verletzt , ToreFreund , ToreLiga , TorePokal , "
						+ "ToreGesamt , Hattrick , Bewertung , TrainerTyp, Trainer, HRF_ID, Datum, "
						+ "PlayerNumber, TransferListed,  Caps, CapsU20 ) VALUES(";
				statement
					+= (""
						+ player.getGelbeKarten()
						+ ","
						+ player.getSpielerID()
						+ ",'"
						+ de.hattrickorganizer.database.DBZugriff.insertEscapeSequences(player.getName())
						+ "',"
						+ player.getAlter()
						+ ","
						+ player.getAgeDays()
						+ ","
						+ player.getKondition()
						+ ","
						+ player.getForm()
						+ ","
						+ player.getTorwart()
						+ ","
						+ player.getVerteidigung()
						+ ","
						+ player.getSpielaufbau()
						+ ","
						+ player.getFluegelspiel()
						+ ","
						+ player.getTorschuss()
						+ ","
						+ player.getPasspiel()
						+ ","
						+ player.getStandards()
						+ ","
						+ player.getSubskill4Pos(ISpieler.SKILL_TORWART)
						+ ","
						+ player.getSubskill4Pos(ISpieler.SKILL_VERTEIDIGUNG)
						+ ","
						+ player.getSubskill4Pos(ISpieler.SKILL_SPIELAUFBAU)
						+ ","
						+ player.getSubskill4Pos(ISpieler.SKILL_FLUEGEL)
						+ ","
						+ player.getSubskill4Pos(ISpieler.SKILL_TORSCHUSS)
						+ ","
						+ player.getSubskill4Pos(ISpieler.SKILL_PASSSPIEL)
						+ ","
						+ player.getSubskill4Pos(ISpieler.SKILL_STANDARDS)
						+ ","
						+ player.getTrainingsOffsetTorwart()
						+ ","
						+ player.getTrainingsOffsetVerteidigung()
						+ ","
						+ player.getTrainingsOffsetSpielaufbau()
						+ ","
						+ player.getTrainingsOffsetFluegelspiel()
						+ ","
						+ player.getTrainingsOffsetTorschuss()
						+ ","
						+ player.getTrainingsOffsetPasspiel()
						+ ","
						+ player.getTrainingsOffsetStandards()
						+ ","
						+ player.getSpezialitaet()
						+ ",'"
						+ de.hattrickorganizer.database.DBZugriff.insertEscapeSequences(player.getSpezialitaetString())
						+ "',"
						+ player.getCharakter()
						+ ",'"
						+ de.hattrickorganizer.database.DBZugriff.insertEscapeSequences(player.getCharakterString())
						+ "',"
						+ player.getAnsehen()
						+ ",'"
						+ de.hattrickorganizer.database.DBZugriff.insertEscapeSequences(player.getAnsehenString())
						+ "',"
						+ player.getAgressivitaet()
						+ ",'"
						+ de.hattrickorganizer.database.DBZugriff.insertEscapeSequences(player.getAgressivitaetString())
						+ "',"
						+ player.getFuehrung()
						+ ","
						+ player.getErfahrung()
						+ ","
						+ player.getGehalt()
						+ ","
						+ player.getBonus()
						+ ","
						+ player.getNationalitaet()
						+ ","
						+ player.getSaveMarktwert()
						+ ","
						+ player.getVerletzt()
						+ ","
						+ player.getToreFreund()
						+ ","
						+ player.getToreLiga()
						+ ","
						+ player.getTorePokal()
						+ ","
						+ player.getToreGesamt()
						+ ","
						+ player.getHattrick()
						+ ","
						+ player.getBewertung()
						+ ","
						+ player.getTrainerTyp()
						+ ","
						+ player.getTrainer()
						+ ","
						+ hrfId
						+ ",'"
						+ date.toString()
						+ "',"
						+ player.getTrikotnummer()
						+ ","
						+ player.getTransferlisted()
						+ ","
						+ player.getLaenderspiele()
						+ ","
						+ player.getU20Laenderspiele()
						+ " )");
				adapter.executeUpdate(statement);
			}
		}
	}

	/**
	 * lädt die Spieler zum angegeben HRF file ein
	 *
	 * @param hrfID TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	protected Vector getSpieler(int hrfID) {
		ResultSet rs = null;
		Spieler player = null;
		String sql = null;
		final Vector ret = new Vector();

		sql = "SELECT * from "+getTableName()+" WHERE HRF_ID = " + hrfID;
		rs = adapter.executeQuery(sql);

		try {
			if (rs != null) {
				rs.beforeFirst();

				while (rs.next()) {
					player = new Spieler(rs);

					//HOLogger.instance().log(getClass(), player.getSpielerID () );
					ret.add(player);
				}
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DatenbankZugriff.getSpieler: " + e);
		}

		return ret;
	}

	/**
	 * gibt alle Spieler zurück, auch ehemalige
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Vector getAllSpieler() {
		ResultSet rs = null;
		Spieler player = null;
		String sql = null;
		final Vector ret = new Vector();

		sql = "SELECT DISTINCT SpielerID from "+getTableName()+"";
		rs = adapter.executeQuery(sql);

		try {
			if (rs != null) {
				final Vector idVector = new Vector();
				rs.beforeFirst();

				while (rs.next()) {
					idVector.add(new Integer(rs.getInt("SpielerID")));
				}

				for (int i = 0; i < idVector.size(); i++) {
					sql = "SELECT * from "+getTableName()+" WHERE SpielerID=" + ((Integer) idVector.get(i)).toString() + " ORDER BY Datum DESC";
					rs = adapter.executeQuery(sql);

					if (rs.first()) {
						player = new Spieler(rs);

						//HOLogger.instance().log(getClass(), player.getSpielerID () );
						ret.add(player);
					}
				}
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DatenbankZugriff.getSpieler: " + e);
		}

		return ret;
	}


	/**
	 * Gibt die letzte Bewertung für den Spieler zurück // HRF
	 *
	 * @param spielerid TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public int getLetzteBewertung4Spieler(int spielerid) {
		int bewertung = 0;

		try {
			final String sql = "SELECT Bewertung from "+getTableName()+" WHERE SpielerID=" + spielerid + " AND Bewertung>0 ORDER BY Datum DESC";
			final ResultSet rs = adapter.executeQuery(sql);

			if ((rs != null) && rs.first()) {
				bewertung = rs.getInt("Bewertung");
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DatenbankZugriff.getLetzteBewertung4Spieler : " + spielerid + " : " + e);
		}

		return bewertung;
	}

	/**
	 * Gibt einen Spieler zurück mit den Daten kurz vor dem Timestamp
	 *
	 * @param spielerid TODO Missing Constructuor Parameter Documentation
	 * @param time TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Spieler getSpielerAtDate(int spielerid, Timestamp time) {
		ResultSet rs = null;
		Spieler player = null;
		String sql = null;

		//6 Tage   //1209600000  //14 Tage vorher
		final int spanne = 518400000;

		if (time == null) {
			return null;
		}

		//--- Zuerst x Tage vor dem Datum suchen -------------------------------
		//x Tage vorher
		final Timestamp time2 = new Timestamp(time.getTime() - spanne);

		//HOLogger.instance().log(getClass(),"Time : " + time + " : vor 14 Tage : " + time2 );
		sql = "SELECT * from "+getTableName()+" WHERE Datum<='" + time.toString() + "' AND Datum>='" + time2.toString() + "' AND SpielerID=" + spielerid + " ORDER BY Datum DESC";
		rs = adapter.executeQuery(sql);

		try {
			if (rs != null) {
				if (rs.first()) {
					player = new Spieler(rs);

					//HOLogger.instance().log(getClass(), "Spieler " + player.getName () + " vom " + rs.getTimestamp ( "Datum" ) );
				}
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"1. Spieler nicht gefunden für Datum " + time.toString() + " und SpielerID " + spielerid);
		}

		//--- Dann ein HRF später versuchen, Dort muss er dann eigenlich vorhanden sein! ---
		if (player == null) {
			sql = "SELECT * from "+getTableName()+" WHERE Datum>'" + time.toString() + "' AND SpielerID=" + spielerid + " ORDER BY Datum";
			rs = adapter.executeQuery(sql);

			try {
				if (rs != null) {
					if (rs.first()) {
						player = new Spieler(rs);

						//HOLogger.instance().log(getClass(), "Spieler " + player.getName () + " vom " + rs.getTimestamp ( "Datum" ) );
					}
				}
			} catch (Exception e) {
				HOLogger.instance().log(getClass(),"2. Spieler nicht gefunden für Datum " + time.toString() + " und SpielerID " + spielerid);
			}
		}

		//----Dann noch die dopplete Spanne vor der Spanne suchen---------------
		if (player == null) {
			//x Tage vorher
			final Timestamp time3 = new Timestamp(time2.getTime() - (spanne * 2));

			//HOLogger.instance().log(getClass(),"Time : " + time + " : vor 14 Tage : " + time2 );
			sql = "SELECT * from "+getTableName()+" WHERE Datum<='" + time2.toString() + "' AND Datum>='" + time3.toString() + "' AND SpielerID=" + spielerid + " ORDER BY Datum DESC";
			rs = adapter.executeQuery(sql);

			try {
				if (rs != null) {
					if (rs.first()) {
						player = new Spieler(rs);

						//HOLogger.instance().log(getClass(), "Spieler " + player.getName () + " vom " + rs.getTimestamp ( "Datum" ) );
					}
				}
			} catch (Exception e) {
				HOLogger.instance().log(getClass(),"3. Spieler nicht gefunden für Datum " + time.toString() + " und SpielerID " + spielerid);
			}
		}

		return player;
	}

	//------------------------------------------------------------------------------

	public Spieler getSpielerBeforeDate(Timestamp time, int spielerid) {
		ResultSet rs = null;
		Spieler player = null;
		String sql = null;

		if (time == null) {
			return null;
		}

		//HOLogger.instance().log(getClass(),"Time : " + time + " : vor 14 Tage : " + time2 );
		sql = "SELECT * from "+getTableName()+" WHERE Datum<'" + time.toString() + "' AND SpielerID=" + spielerid + " ORDER BY Datum DESC";
		rs = adapter.executeQuery(sql);

		try {
			if (rs != null) {
				if (rs.first()) {
					player = new Spieler(rs);

					//HOLogger.instance().log(getClass(), "Spieler " + player.getName () + " vom " + rs.getTimestamp ( "Datum" ) );
				}
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DBZugriff.getSpielerBeforeDate: Keine HRF mit dem Spieler vor dem Datum gefunden");
		}

		return player;
	}

	/**
	 * Gibt einen Spieler zurück aus dem ersten HRF
	 *
	 * @param spielerid TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Spieler getSpielerFirstHRF(int spielerid) {
		ResultSet rs = null;
		Spieler player = null;
		String sql = null;

		sql = "SELECT * from "+getTableName()+" WHERE SpielerID=" + spielerid + " ORDER BY Datum ASC";
		rs = adapter.executeQuery(sql);

		try {
			if (rs != null) {
				if (rs.first()) {
					player = new Spieler(rs);

					//Info, da der Spieler für den Vergleich in der Spielerübersicht benutzt wird
					player.setOld(true);
					HOLogger.instance().log(getClass(),"Spieler " + player.getName() + " vom " + rs.getTimestamp("Datum"));
				}
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"Nicht gefunden SpielerID " + spielerid);
		}

		return player;
	}

	/**
	 * Gibt das Datum des ersten HRFs zurück, in dem der Spieler aufgetaucht ist
	 *
	 * @param spielerid TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Timestamp getTimestamp4FirstPlayerHRF(int spielerid) {
		Timestamp time = null;

		try {
			final String sql = "SELECT Datum from "+getTableName()+" WHERE SpielerID=" + spielerid + " ORDER BY Datum";
			final ResultSet rs = adapter.executeQuery(sql);

			if ((rs != null) && rs.first()) {
				time = rs.getTimestamp("Datum");
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DatenbankZugriff.getLetzteBewertung4Spieler : " + spielerid + " : " + e);
		}

		return time;
	}

	/**
	 * Gibt einen Spieler zurï¿½ck mit den Daten kurz vor dem Timestamp
	 *
	 * @param hrfID TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public int getTrainerType(int hrfID) {
		ResultSet rs = null;
		String sql = null;

		sql = "SELECT TrainerTyp FROM "+getTableName()+" WHERE HRF_ID=" + hrfID + " AND TrainerTyp >=0 AND Trainer >0 order by Trainer desc";
		rs = adapter.executeQuery(sql);

		try {
			if (rs != null) {
				if (rs.first()) {
					return rs.getInt("TrainerTyp");
				}
			}
		} catch (Exception e) {
		}

		return -99;
	}

}
