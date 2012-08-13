package ho.core.db;

import ho.core.constants.player.PlayerSkill;
import ho.core.model.player.Spieler;
import ho.core.util.HOLogger;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Vector;

final class SpielerTable extends AbstractTable {

	/** tablename **/
	final static String TABLENAME = "SPIELER";

	SpielerTable(JDBCAdapter adapter) {
		super(TABLENAME, adapter);
	}

	@Override
	protected void initColumns() {
		columns = new ColumnDescriptor[56];
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
		columns[30] = new ColumnDescriptor("iCharakter", Types.INTEGER, false);
		columns[31] = new ColumnDescriptor("iAnsehen", Types.INTEGER, false);
		columns[32] = new ColumnDescriptor("iAgressivitaet", Types.INTEGER, false);
		columns[33] = new ColumnDescriptor("Fuehrung", Types.INTEGER, false);
		columns[34] = new ColumnDescriptor("Erfahrung", Types.INTEGER, false);
		columns[35] = new ColumnDescriptor("Gehalt", Types.INTEGER, false);
		columns[36] = new ColumnDescriptor("Bonus", Types.INTEGER, false);
		columns[37] = new ColumnDescriptor("Land", Types.INTEGER, false);
		columns[38] = new ColumnDescriptor("Marktwert", Types.INTEGER, false);
		columns[39] = new ColumnDescriptor("Verletzt", Types.INTEGER, false);
		columns[40] = new ColumnDescriptor("ToreFreund", Types.INTEGER, false);
		columns[41] = new ColumnDescriptor("ToreLiga", Types.INTEGER, false);
		columns[42] = new ColumnDescriptor("TorePokal", Types.INTEGER, false);
		columns[43] = new ColumnDescriptor("ToreGesamt", Types.INTEGER, false);
		columns[44] = new ColumnDescriptor("Hattrick", Types.INTEGER, false);
		columns[45] = new ColumnDescriptor("Bewertung", Types.INTEGER, false);
		columns[46] = new ColumnDescriptor("TrainerTyp", Types.INTEGER, false);
		columns[47] = new ColumnDescriptor("Trainer", Types.INTEGER, false);
		columns[48] = new ColumnDescriptor("PlayerNumber", Types.INTEGER, false);
		columns[49] = new ColumnDescriptor("TransferListed", Types.INTEGER, false);
		columns[50] = new ColumnDescriptor("Caps", Types.INTEGER, false);
		columns[51] = new ColumnDescriptor("CapsU20", Types.INTEGER, false);
		columns[52] = new ColumnDescriptor("AgeDays", Types.INTEGER, false);
		columns[53] = new ColumnDescriptor("TrainingBlock", Types.BOOLEAN, false);
		columns[54] = new ColumnDescriptor("Loyalty", Types.INTEGER, false);
		columns[55] = new ColumnDescriptor("HomeGrown", Types.BOOLEAN, false);
	}

	@Override
	protected String[] getCreateIndizeStatements() {
		return new String[] {
			"CREATE INDEX iSpieler_1 ON " + getTableName() + "(" + columns[3].getColumnName() + "," + columns[1].getColumnName() + ")",
			"CREATE INDEX iSpieler_2 ON " + getTableName() + "(" + columns[0].getColumnName() + ")" };
	}

	/**
	 * saves one player to the DB
	 *
	 * @param hrfId		hrf id
	 * @param player	the player to be saved
	 */

	void saveSpieler(int hrfId, Spieler player, Timestamp date) {
		StringBuilder statement = new StringBuilder(500);
		final String[] awhereS = { "HRF_ID", "SpielerId" };
		final String[] awhereV = { "" + hrfId, "" + player.getSpielerID()};
		if (player != null) {
			// Delete old values
			delete(awhereS, awhereV);

			//insert vorbereiten
			statement.append("INSERT INTO ").append(getTableName());
			statement.append(" ( GelbeKarten , SpielerID , Name , Age , AgeDays , ");
			statement.append("Kondition , Form , Torwart , Verteidigung , Spielaufbau , Fluegel , ");
			statement.append("Torschuss , Passpiel , Standards , SubTorwart , SubVerteidigung , ");
			statement.append( "SubSpielaufbau , SubFluegel , SubTorschuss , SubPasspiel , SubStandards , ");
			statement.append( "OffsetTorwart , OffsetVerteidigung , OffsetSpielaufbau , OffsetFluegel , ");
			statement.append( "OffsetTorschuss , OffsetPasspiel , OffsetStandards , iSpezialitaet , ");
			statement.append( "iCharakter , iAnsehen , iAgressivitaet , Fuehrung , Erfahrung , Gehalt , ");
			statement.append( "Bonus , Land , Marktwert , Verletzt , ToreFreund , ToreLiga , TorePokal , ");
			statement.append( "ToreGesamt , Hattrick , Bewertung , TrainerTyp, Trainer, HRF_ID, Datum, ");
			statement.append( "PlayerNumber, TransferListed,  Caps, CapsU20, TrainingBlock, Loyalty, HomeGrown ) VALUES(");
			statement.append(player.getGelbeKarten()).append(",");
						
			statement.append(player.getSpielerID()).append(",");
			statement.append("'").append(DBManager.insertEscapeSequences(player.getName())).append("',");
			statement.append(player.getAlter()).append(",");
			statement.append(player.getAgeDays()).append(",");
			statement.append(player.getKondition()).append(",");
			statement.append(player.getForm()).append(",");
			statement.append(player.getTorwart()).append(",");
			statement.append(player.getVerteidigung()).append(",");
			statement.append(player.getSpielaufbau()).append(",");
			statement.append(player.getFluegelspiel()).append(",");
			statement.append(player.getTorschuss()).append(",");
			statement.append(player.getPasspiel()).append(",");
			statement.append(player.getStandards()).append(",");
			statement.append(player.getSubskill4Pos(PlayerSkill.KEEPER)).append(",");
			statement.append(player.getSubskill4Pos(PlayerSkill.DEFENDING)).append(",");
			statement.append(player.getSubskill4Pos(PlayerSkill.PLAYMAKING)).append(",");
			statement.append(player.getSubskill4Pos(PlayerSkill.WINGER)).append(",");
			statement.append(player.getSubskill4Pos(PlayerSkill.SCORING)).append(",");
			statement.append(player.getSubskill4Pos(PlayerSkill.PASSING)).append(",");
			statement.append(player.getSubskill4Pos(PlayerSkill.SET_PIECES)).append(",");
			// Training offsets below
			statement.append("0,");
			statement.append("0,");
			statement.append("0,");
			statement.append("0,");
			statement.append("0,");
			statement.append("0,");
			statement.append("0,");
			statement.append(player.getSpezialitaet()).append(",");
			statement.append(player.getCharakter()).append(",");
			statement.append(player.getAnsehen()).append(",");
			statement.append(player.getAgressivitaet()).append(",");
			statement.append(player.getFuehrung()).append(",");
			statement.append(player.getErfahrung()).append(",");
			statement.append(player.getGehalt()).append(",");
			statement.append(player.getBonus()).append(",");
			statement.append(player.getNationalitaet()).append(",");
			statement.append(player.getSaveMarktwert()).append(",");
			statement.append(player.getVerletzt()).append(",");
			statement.append(player.getToreFreund()).append(",");
			statement.append(player.getToreLiga()).append(",");
			statement.append(player.getTorePokal()).append(",");
			statement.append(player.getToreGesamt()).append(",");
			statement.append(player.getHattrick()).append(",");
			statement.append(player.getBewertung()).append(",");
			statement.append(player.getTrainerTyp()).append(",");
			statement.append(player.getTrainer()).append(",");
			statement.append(hrfId).append(",");
			statement.append("'").append(date.toString()).append("',");
			statement.append(player.getTrikotnummer()).append(",");
			statement.append(player.getTransferlisted()).append(",");
			statement.append(player.getLaenderspiele()).append(",");
			statement.append(player.getU20Laenderspiele()).append(",");
			statement.append(player.hasTrainingBlock()).append(",");
			statement.append(player.getLoyalty()).append(",");
			statement.append(player.isHomeGrown()).append(")");
			adapter.executeUpdate(statement.toString());
			}
	}
	
	/**
	 * speichert die Spieler
	 *
	 * @param hrfId TODO Missing Constructuor Parameter Documentation
	 * @param spieler TODO Missing Constructuor Parameter Documentation
	 * @param date TODO Missing Constructuor Parameter Documentation
	 */
	void saveSpieler(int hrfId, Vector<Spieler> spieler, Timestamp date) {
//		String statement = null;
		final String[] awhereS = { "HRF_ID" };
		final String[] awhereV = { "" + hrfId };
		Spieler player = null;

		if (spieler != null) {
			// Delete old values
			delete(awhereS, awhereV);

			for (int i = 0; i < spieler.size(); i++) {
				player = (Spieler) spieler.elementAt(i);
				
				saveSpieler (hrfId, player, date);
			}
		}
	}

	/**
	 * get a player from a specific HRF
	 *
	 * @param hrfID hrd id
	 * @param playerId player id
	 * 
	 *
	 * @return player
	 */
	Spieler getSpielerFromHrf(int hrfID, int playerId) {
		ResultSet rs = null;
		Spieler player = null;
		String sql = null;

		sql = "SELECT * from "+getTableName()+" WHERE HRF_ID = " + hrfID + " AND SpielerId="+playerId;
		rs = adapter.executeQuery(sql);

		try {
			if (rs != null) {
				rs.beforeFirst();

				if (rs.next()) {
					player = createObject(rs);
					return player;
				}
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DatenbankZugriff.getSpielerFromHrf: " + e);
		}

		return null;		
	}
	
	/**
	 * lädt die Spieler zum angegeben HRF file ein
	 *
	 * @param hrfID TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	Vector<Spieler> getSpieler(int hrfID) {
		ResultSet rs = null;
		Spieler player = null;
		String sql = null;
		final Vector<Spieler> ret = new Vector<Spieler>();

		sql = "SELECT * from "+getTableName()+" WHERE HRF_ID = " + hrfID;
		rs = adapter.executeQuery(sql);

		try {
			if (rs != null) {
				rs.beforeFirst();

				while (rs.next()) {
					player = createObject(rs);

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
	Vector<Spieler> getAllSpieler() {
		ResultSet rs = null;
		Spieler player = null;
		String sql = null;
		final Vector<Spieler> ret = new Vector<Spieler>();

		sql = "SELECT DISTINCT SpielerID from "+getTableName()+"";
		rs = adapter.executeQuery(sql);

		try {
			if (rs != null) {
				final Vector<Integer> idVector = new Vector<Integer>();
				rs.beforeFirst();

				while (rs.next()) {
					idVector.add(Integer.valueOf(rs.getInt("SpielerID")));
				}

				for (int i = 0; i < idVector.size(); i++) {
					sql = "SELECT * from "+getTableName()+" WHERE SpielerID=" + idVector.get(i) + " ORDER BY Datum DESC";
					rs = adapter.executeQuery(sql);

					if (rs.first()) {
						player = createObject(rs);

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
	int getLetzteBewertung4Spieler(int spielerid) {
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
	Spieler getSpielerAtDate(int spielerid, Timestamp time) {
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
					player = createObject(rs);

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
						player = createObject(rs);

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
						player = createObject(rs);

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

	Spieler getSpielerBeforeDate(Timestamp time, int spielerid) {
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
					player = createObject(rs);

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
	Spieler getSpielerFirstHRF(int spielerid) {
		ResultSet rs = null;
		Spieler player = null;
		String sql = null;

		sql = "SELECT * from "+getTableName()+" WHERE SpielerID=" + spielerid + " ORDER BY Datum ASC";
		rs = adapter.executeQuery(sql);

		try {
			if (rs != null) {
				if (rs.first()) {
					player = createObject(rs);

					//Info, da der Spieler für den Vergleich in der Spielerübersicht benutzt wird
					player.setOld(true);
//					HOLogger.instance().log(getClass(),"Spieler " + player.getName() + " vom " + rs.getTimestamp("Datum"));
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
	Timestamp getTimestamp4FirstPlayerHRF(int spielerid) {
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
	int getTrainerType(int hrfID) {
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

	   /**
     * Erstellt einen Spieler aus der Datenbank
     *
     * @param rs TODO Missing Constructuor Parameter Documentation
     */
    private Spieler createObject(ResultSet rs) {
    	Spieler player = new Spieler();
        try {
        	player.setSpielerID(rs.getInt("SpielerID"));
            player.setName(DBManager.deleteEscapeSequences(rs.getString("Name")));
            player.setAlter(rs.getInt("Age"));
            player.setAgeDays(rs.getInt("AgeDays"));
            player.setKondition(rs.getInt("Kondition"));
            player.setForm(rs.getInt("Form"));
            player.setTorwart(rs.getInt("Torwart"));
            player.setVerteidigung(rs.getInt("Verteidigung"));
            player.setSpielaufbau(rs.getInt("Spielaufbau"));
            player.setPasspiel(rs.getInt("Passpiel"));
            player.setFluegelspiel(rs.getInt("Fluegel"));
            player.setTorschuss(rs.getInt("Torschuss"));
            player.setStandards(rs.getInt("Standards"));
            player.setSpezialitaet(rs.getInt("iSpezialitaet"));
            player.setCharakter(rs.getInt("iCharakter"));
            player.setAnsehen(rs.getInt("iAnsehen"));
            player.setAgressivitaet(rs.getInt("iAgressivitaet"));
            player.setErfahrung(rs.getInt("Erfahrung"));
            player.setLoyalty(rs.getInt("Loyalty"));
            player.setHomeGrown(rs.getBoolean("HomeGrown"));
            player.setFuehrung(rs.getInt("Fuehrung"));
            player.setGehalt(rs.getInt("Gehalt"));
            player.setNationalitaet(rs.getInt("Land"));
            player.setTSI(rs.getInt("Marktwert"));

            //TSI, alles vorher durch 1000 teilen
            player.setHrfDate(rs.getTimestamp("Datum"));

            if (player.getHrfDate().before(DBManager.TSIDATE)) {
                player.setTSI(player.getTSI()/1000);
            }

            //Subskills
            player.setSubskill4Pos(PlayerSkill.KEEPER,rs.getFloat("SubTorwart"));
            player.setSubskill4Pos(PlayerSkill.DEFENDING,rs.getFloat("SubVerteidigung"));
            player.setSubskill4Pos(PlayerSkill.PLAYMAKING,rs.getFloat("SubSpielaufbau"));
            player.setSubskill4Pos(PlayerSkill.PASSING,rs.getFloat("SubPasspiel"));
            player.setSubskill4Pos(PlayerSkill.WINGER,rs.getFloat("SubFluegel"));
            player.setSubskill4Pos(PlayerSkill.SCORING,rs.getFloat("SubTorschuss"));
            player.setSubskill4Pos(PlayerSkill.SET_PIECES,rs.getFloat("SubStandards"));
           
            player.setGelbeKarten(rs.getInt("GelbeKarten"));
            player.setVerletzt(rs.getInt("Verletzt"));
            player.setToreFreund(rs.getInt("ToreFreund"));
            player.setToreLiga(rs.getInt("ToreLiga"));
            player.setTorePokal(rs.getInt("TorePokal"));
            player.setToreGesamt(rs.getInt("ToreGesamt"));
            player.setHattrick(rs.getInt("Hattrick"));
            player.setBewertung(rs.getInt("Bewertung"));
            player.setTrainerTyp(rs.getInt("TrainerTyp"));
            player.setTrainer(rs.getInt("Trainer"));

            player.setTrikotnummer(rs.getInt("PlayerNumber"));
            player.setTransferlisted(rs.getInt("TransferListed"));
            player.setLaenderspiele(rs.getInt("Caps"));
            player.setU20Laenderspiele(rs.getInt("CapsU20"));

            // Training block
            player.setTrainingBlock(rs.getBoolean("TrainingBlock"));
            
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }
        return player;
    }
}
