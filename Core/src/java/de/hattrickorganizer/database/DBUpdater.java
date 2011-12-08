package de.hattrickorganizer.database;

import gui.UserParameter;
import de.hattrickorganizer.gui.HOMainFrame;
import de.hattrickorganizer.model.HOParameter;
import de.hattrickorganizer.tools.HOLogger;

final class DBUpdater {
	JDBCAdapter m_clJDBCAdapter;
	DBZugriff dbZugriff;
	
	void setDbZugriff(DBZugriff dbZugriff) {
		this.dbZugriff = dbZugriff;
	}

	void updateDB(int DBVersion) {
		// I introduce some new db versioning system.
		// Just add new version cases in the switch..case part
		// and leave the old ones active, so also users which
		// have skipped a version get their database updated.
		// jailbird.
		int version = 0;
		this.m_clJDBCAdapter = dbZugriff.getAdapter();
		
		version = 	((UserConfigurationTable) dbZugriff.getTable(UserConfigurationTable.TABLENAME)).getDBVersion();

		// If development always force to rebuild latest DB
		if (version == DBVersion && HOMainFrame.isDevelopment()) {
			version = DBVersion - 1;
		}

		// We may now update depending on the version identifier!
		if (version != DBVersion) {
			try {
				HOLogger.instance().log(getClass(), "Updating DB to version " + DBVersion + "...");

				switch (version) { // hint: fall though (no breaks) is intended here
					case 0 :
						updateDBv1();
					case 1 :
						updateDBv2();
					case 2 :
						updateDBv3();
					case 3 :
						updateDBv4();
					case 4 :
						updateDBv5();
					case 5 :
						updateDBv6();
					case 6 :
						updateDBv7();
					case 7 :
						updateDBv8();
					case 8 :
						updateDBv9();
					case 9 : 
						updateDBv10();
					case 10: 
						updateDBv11();
				}

				HOLogger.instance().log(getClass(), "done.");
			} catch (Exception e) {
				HOLogger.instance().log(getClass(), "failed.");
			}
		} else {
			HOLogger.instance().log(getClass(), "No DB update necessary.");
		}
	}

	/**
	  * Update database to version 1.
	  */
	private void updateDBv1() throws Exception {
		// Add TimeZone and DBVersion field
		m_clJDBCAdapter.executeUpdate("ALTER TABLE UserParameter ADD COLUMN DBVersion INTEGER");
		m_clJDBCAdapter.executeUpdate(
			"ALTER TABLE UserParameter ADD COLUMN TimeZoneDifference INTEGER");
		m_clJDBCAdapter.executeUpdate("UPDATE UserParameter SET TimeZoneDifference = 0");

		// Add fields to Scout and set default values for existing entries
		m_clJDBCAdapter.executeUpdate("ALTER TABLE SCOUT ADD COLUMN PlayerID INTEGER");
		m_clJDBCAdapter.executeUpdate("UPDATE SCOUT SET PlayerID = 0");
		m_clJDBCAdapter.executeUpdate("ALTER TABLE SCOUT ADD COLUMN Speciality INTEGER");
		m_clJDBCAdapter.executeUpdate("UPDATE SCOUT SET Speciality = 0");
		m_clJDBCAdapter.executeUpdate("ALTER TABLE SCOUT ADD COLUMN Price INTEGER");
		m_clJDBCAdapter.executeUpdate("UPDATE SCOUT SET Price = 0");

		m_clJDBCAdapter.executeUpdate("ALTER TABLE UserParameter ADD COLUMN HOUsers INTEGER");
		m_clJDBCAdapter.executeUpdate("UPDATE UserParameter SET HOUsers = 0");
		m_clJDBCAdapter.executeUpdate("ALTER TABLE UserParameter ADD COLUMN HOUsersTot INTEGER");
		m_clJDBCAdapter.executeUpdate("UPDATE UserParameter SET HOUsersTot = 0");
		m_clJDBCAdapter.executeUpdate(
			"ALTER TABLE UserParameter ADD COLUMN AAP_ExcludeLastLineup BIT");
		m_clJDBCAdapter.executeUpdate("UPDATE UserParameter SET AAP_ExcludeLastLineup = false");

		// Always set field DBVersion to the new value as last action.
		// Do not use DBVersion but the value, as update packs might
		// do version checking again before applying!
		m_clJDBCAdapter.executeUpdate("UPDATE UserParameter SET DBVersion = 1");
	}

	/**
	 * Update database to version 2.
	 */
	private void updateDBv2() throws Exception {

		// Move Future Weeks from TA into Main HO Database
		//			createFutureTrainingsTabelle();
		dbZugriff.getTable(SpielerSkillupTable.TABLENAME).createTable();
		dbZugriff.getTable(FutureTrainingTable.TABLENAME).createTable();
		dbZugriff.getTable(UserConfigurationTable.TABLENAME).createTable();
		dbZugriff.getTable(UserColumnsTable.TABLENAME).createTable();
		m_clJDBCAdapter.executeUpdate(
			"INSERT INTO FUTURETRAINING select * from TRAININGEXPERIENCE_TRAINWEEK");
		m_clJDBCAdapter.executeUpdate("DROP TABLE TRAININGEXPERIENCE_TRAINWEEK");

		m_clJDBCAdapter.executeUpdate("ALTER TABLE SCOUT ADD COLUMN AGE INTEGER");
		m_clJDBCAdapter.executeUpdate("UPDATE SCOUT SET AGE=ALTER");
		m_clJDBCAdapter.executeUpdate("ALTER TABLE SCOUT DROP COLUMN ALTER");

		m_clJDBCAdapter.executeUpdate("ALTER TABLE SPIELER ADD COLUMN AGE INTEGER");
		m_clJDBCAdapter.executeUpdate("UPDATE SPIELER SET AGE=ALTER");
		m_clJDBCAdapter.executeUpdate("ALTER TABLE SPIELER DROP COLUMN ALTER");

		DBUserParameter userParameter = new DBUserParameter();
		userParameter.loadUserParameter(m_clJDBCAdapter);
		userParameter = null;

		UserConfigurationTable configTable = (UserConfigurationTable) dbZugriff.getTable(UserConfigurationTable.TABLENAME);
		configTable.store(UserParameter.instance());
		configTable.store(HOParameter.instance());
//		configTable.store(FormulaFactors.instance());

		m_clJDBCAdapter.executeUpdate("DROP TABLE UserParameter");
		m_clJDBCAdapter.executeUpdate("DROP TABLE SpaltenReihenfolge");

		m_clJDBCAdapter.executeUpdate(
			"UPDATE MATCHLINEUPPLAYER SET HOPOSCODE = 21 WHERE POSITIONCODE = 11 AND TAKTIK = 4");
		m_clJDBCAdapter.executeUpdate(
			"UPDATE MATCHLINEUPPLAYER SET HOPOSCODE = 21 WHERE POSITIONCODE = 10 AND TAKTIK = 4");

		// Always set field DBVersion to the new value as last action.
		// Do not use DBVersion but the value, as update packs might
		// do version checking again before applying!
		dbZugriff.saveUserParameter("DBVersion", 2);
	}

	/**
	 * Update database to version 3.
	 */
	private void updateDBv3() throws Exception {

		changeColumnType("MATCHDETAILS","Matchreport","Matchreport","VARCHAR(8196)");
		changeColumnType("MatchHighlights","EventText","EventText","VARCHAR(512)");
		changeColumnType("FAKTOREN","HOPosition","HOPosition","INTEGER");
		changeColumnType("POSITIONEN","Taktik","Taktik","INTEGER");
		changeColumnType("SPIELERNOTIZ","userPos","userPos","INTEGER");

		m_clJDBCAdapter.executeUpdate("ALTER TABLE MatchHighLights RENAME TO MATCHHIGHLIGHTS");
		m_clJDBCAdapter.executeUpdate("ALTER TABLE SpielerSkillup RENAME TO SPIELERSKILLUP");
		m_clJDBCAdapter.executeUpdate("ALTER TABLE USERCOLUMNS ADD COLUMN COLUMN_WIDTH INTEGER");
		// Always set field DBVersion to the new value as last action.
		// Do not use DBVersion but the value, as update packs might
		// do version checking again before applying!
		dbZugriff.saveUserParameter("DBVersion", 3);
	}

	/**
	 * Update database to version 4.
	 */
	private void updateDBv4() throws Exception {

		m_clJDBCAdapter.executeUpdate("ALTER TABLE SCOUT DROP COLUMN Finanzberater");

		// Always set field DBVersion to the new value as last action.
		// Do not use DBVersion but the value, as update packs might
		// do version checking again before applying!
		dbZugriff.saveUserParameter("DBVersion", 4);
	}

	/**
	 * Update database to version 5.
	 */
	private void updateDBv5() throws Exception {

		m_clJDBCAdapter.executeUpdate("ALTER TABLE TEAM ADD COLUMN STAMINATRAININGPART INTEGER");
		m_clJDBCAdapter.executeUpdate("ALTER TABLE TRAINING ADD COLUMN STAMINATRAININGPART INTEGER");
		m_clJDBCAdapter.executeUpdate("ALTER TABLE FUTURETRAINING ADD COLUMN STAMINATRAININGPART INTEGER");
		m_clJDBCAdapter.executeUpdate("UPDATE FUTURETRAINING SET STAMINATRAININGPART=5 WHERE STAMINATRAININGPART IS NULL");

		// Always set field DBVersion to the new value as last action.
		// Do not use DBVersion but the value, as update packs might
		// do version checking again before applying!
		dbZugriff.saveUserParameter("DBVersion", 5);
	}

	/**
	 * Update DB structure to v6
	 */
	private void updateDBv6() throws Exception {

		m_clJDBCAdapter.executeUpdate("ALTER TABLE SPIELER ADD COLUMN AGEDAYS INTEGER");
		m_clJDBCAdapter.executeUpdate("ALTER TABLE SCOUT ADD COLUMN AGEDAYS INTEGER");

		// Always set field DBVersion to the new value as last action.
		// Do not use DBVersion but the value, as update packs might
		// do version checking again before applying!
		dbZugriff.saveUserParameter("DBVersion", 6);
	}

	/**
	 * Update DB structure to v7
	 */
	private void updateDBv7() throws Exception {

		// Adding arena/region ID
		m_clJDBCAdapter.executeUpdate("ALTER TABLE BASICS ADD COLUMN Region INTEGER");
		m_clJDBCAdapter.executeUpdate("ALTER TABLE STADION ADD COLUMN ArenaID INTEGER");
		m_clJDBCAdapter.executeUpdate("ALTER TABLE MATCHDETAILS ADD COLUMN RegionID INTEGER");

		// Drop and recreate the table
		// (i.e. use defaults from defaults.xml)
		AbstractTable faktorenTab = (AbstractTable)dbZugriff.getTable(FaktorenTable.TABLENAME);
		if (faktorenTab != null) {
			faktorenTab.dropTable();
			faktorenTab.createTable();
		}

		// Always set field DBVersion to the new value as last action.
		// Do not use DBVersion but the value, as update packs might
		// do version checking again before applying!
		dbZugriff.saveUserParameter("DBVersion", 7);
	}

	/**
	 * Update DB structure to v8
	 *
	 * @throws Exception
	 */
	private void updateDBv8() throws Exception {
		m_clJDBCAdapter.executeUpdate("ALTER TABLE Spieler ADD COLUMN TrainingBlock BOOLEAN");
		m_clJDBCAdapter.executeUpdate("UPDATE Spieler SET TrainingBlock=false WHERE TrainingBlock IS null");
		// Always set field DBVersion to the new value as last action.
		// Do not use DBVersion but the value, as update packs might
		// do version checking again before applying!
		dbZugriff.saveUserParameter("DBVersion", 8);
	}

	/**
	 * Update DB structure to v9
	 */
	private void updateDBv9() throws Exception {
		// Add new columns for spectator distribution
		m_clJDBCAdapter.executeUpdate("ALTER TABLE MatchDetails ADD COLUMN soldTerraces INTEGER");
		m_clJDBCAdapter.executeUpdate("ALTER TABLE MatchDetails ADD COLUMN soldBasic INTEGER");
		m_clJDBCAdapter.executeUpdate("ALTER TABLE MatchDetails ADD COLUMN soldRoof INTEGER");
		m_clJDBCAdapter.executeUpdate("ALTER TABLE MatchDetails ADD COLUMN soldVIP INTEGER");

		m_clJDBCAdapter.executeUpdate("UPDATE MatchDetails SET soldTerraces=-1 WHERE soldTerraces IS null");
		m_clJDBCAdapter.executeUpdate("UPDATE MatchDetails SET soldBasic=-1 WHERE soldBasic IS null");
		m_clJDBCAdapter.executeUpdate("UPDATE MatchDetails SET soldRoof=-1 WHERE soldRoof IS null");
		m_clJDBCAdapter.executeUpdate("UPDATE MatchDetails SET soldVIP=-1 WHERE soldVIP IS null");

		m_clJDBCAdapter.executeUpdate("ALTER TABLE Scout ADD COLUMN Agreeability INTEGER");
		m_clJDBCAdapter.executeUpdate("ALTER TABLE Scout ADD COLUMN baseWage INTEGER");
		m_clJDBCAdapter.executeUpdate("ALTER TABLE Scout ADD COLUMN Nationality INTEGER");
		m_clJDBCAdapter.executeUpdate("ALTER TABLE Scout ADD COLUMN Leadership INTEGER");

		m_clJDBCAdapter.executeUpdate("UPDATE Scout SET  Agreeability=-1 WHERE Agreeability IS null");
		m_clJDBCAdapter.executeUpdate("UPDATE Scout SET  baseWage=-1 WHERE baseWage IS null");
		m_clJDBCAdapter.executeUpdate("UPDATE Scout SET  Nationality=-1 WHERE Nationality IS null");
		m_clJDBCAdapter.executeUpdate("UPDATE Scout SET  Leadership=-1 WHERE Leadership IS null");

		// Always set field DBVersion to the new value as last action.
		// Do not use DBVersion but the value, as update packs might
		// do version checking again before applying!
		dbZugriff.saveUserParameter("DBVersion", 9);
	}
	
	/**
	 * Update database to version 10.
	 */
	private void updateDBv10() throws Exception {
		m_clJDBCAdapter.executeUpdate("ALTER TABLE TEAM ADD COLUMN iErfahrung442 INTEGER");
		m_clJDBCAdapter.executeUpdate("ALTER TABLE TEAM ADD COLUMN iErfahrung523 INTEGER");
		m_clJDBCAdapter.executeUpdate("ALTER TABLE TEAM ADD COLUMN iErfahrung550 INTEGER");
		m_clJDBCAdapter.executeUpdate("ALTER TABLE TEAM ADD COLUMN iErfahrung253 INTEGER");
		
		m_clJDBCAdapter.executeUpdate("UPDATE TEAM SET iErfahrung442=8 WHERE iErfahrung442 IS NULL");
		m_clJDBCAdapter.executeUpdate("UPDATE TEAM SET iErfahrung523=1 WHERE iErfahrung523 IS NULL");
		m_clJDBCAdapter.executeUpdate("UPDATE TEAM SET iErfahrung550=1 WHERE iErfahrung550 IS NULL");
		m_clJDBCAdapter.executeUpdate("UPDATE TEAM SET iErfahrung253=1 WHERE iErfahrung253 IS NULL");

		// Always set field DBVersion to the new value as last action.
		// Do not use DBVersion but the value, as update packs might
		// do version checking again before applying!
		dbZugriff.saveUserParameter("DBVersion", 10);
	}

	/**
	 * Update database to version 11.
	 */
	private void updateDBv11() throws Exception {
		m_clJDBCAdapter.executeUpdate("ALTER TABLE MATCHLINEUPPLAYER ADD COLUMN RatingStarsEndOfMatch REAL");
		m_clJDBCAdapter.executeUpdate("UPDATE MATCHLINEUPPLAYER SET RatingStarsEndOfMatch = -1 WHERE RatingStarsEndOfMatch IS NULL");
		
		m_clJDBCAdapter.executeUpdate("ALTER TABLE BASICS ADD COLUMN HasSupporter BOOLEAN");
		m_clJDBCAdapter.executeUpdate("UPDATE BASICS SET HasSupporter = 'false' WHERE HasSupporter IS NULL");
		
		m_clJDBCAdapter.executeUpdate("ALTER TABLE SPIELER ADD COLUMN Loyalty INTEGER");
		m_clJDBCAdapter.executeUpdate("UPDATE SPIELER SET Loyalty = 0 WHERE Loyalty IS NULL");
		
		m_clJDBCAdapter.executeUpdate("ALTER TABLE SPIELER ADD COLUMN HomeGrown BOOLEAN");
		m_clJDBCAdapter.executeUpdate("UPDATE SPIELER SET HomeGrown = 'false' WHERE HomeGrown IS NULL");
		
		m_clJDBCAdapter.executeUpdate("ALTER TABLE SCOUT ADD COLUMN Loyalty INTEGER");
		m_clJDBCAdapter.executeUpdate("UPDATE SCOUT SET Loyalty = 0 WHERE Loyalty IS NULL");
		
		m_clJDBCAdapter.executeUpdate("ALTER TABLE SCOUT ADD COLUMN MotherClub BOOLEAN");
		m_clJDBCAdapter.executeUpdate("UPDATE SCOUT SET MotherClub = 'false' WHERE MotherClub IS NULL");
		
		m_clJDBCAdapter.executeUpdate("ALTER TABLE MATCHLINEUPPLAYER ADD COLUMN StartPosition INTEGER");
		m_clJDBCAdapter.executeUpdate("UPDATE MATCHLINEUPPLAYER SET StartPosition = -1 WHERE StartPosition IS NULL");
		
		m_clJDBCAdapter.executeUpdate("ALTER TABLE MATCHLINEUPPLAYER ADD COLUMN StartBehaviour INTEGER");
		m_clJDBCAdapter.executeUpdate("UPDATE MATCHLINEUPPLAYER SET StartBehaviour = -1 WHERE StartBehaviour IS NULL");
		
		dbZugriff.getTable(MatchSubstitutionTable.TABLENAME).createTable();
		m_clJDBCAdapter.executeUpdate("ALTER TABLE MATCHSUBSTITUTION ADD COLUMN LineupName VARCHAR");
		m_clJDBCAdapter.executeUpdate("UPDATE MATCHSUBSTITUTION SET LineupName = 'D' WHERE LineupName IS NULL");
		
		
		// Always set field DBVersion to the new value as last action.
		// Do not use DBVersion but the value, as update packs might
		// do version checking again before applying!
		dbZugriff.saveUserParameter("DBVersion", 11);
	}
	
	private void changeColumnType(String table,String oldName, String newName, String type) {
		m_clJDBCAdapter.executeUpdate("ALTER TABLE "+table+" ADD COLUMN TEMPCOLUMN "+ type);
		m_clJDBCAdapter.executeUpdate("UPDATE "+table+" SET TEMPCOLUMN="+oldName);
		m_clJDBCAdapter.executeUpdate("ALTER TABLE "+table+" DROP COLUMN "+oldName);
		m_clJDBCAdapter.executeUpdate("ALTER TABLE "+table+" ADD COLUMN "+newName+" "+ type);
		m_clJDBCAdapter.executeUpdate("UPDATE "+table+" SET "+newName+"=TEMPCOLUMN");
		m_clJDBCAdapter.executeUpdate("ALTER TABLE "+table+" DROP COLUMN TEMPCOLUMN");
}
	
	/**
	 * Automatic update of User Configuration parameters
	 *
	 * This method is similar to the updateDB() method above
	 * The main difference is that it is based on the
	 * HO release version instead of the DB version
	 *
	 * In development mode, we execute the current update steps
	 * again (just like in updateBD()).
	 *
	 * @author flattermann <flattermannHO@gmail.com>
	 */
	void updateConfig () {
		if(m_clJDBCAdapter== null)
			m_clJDBCAdapter = dbZugriff.getAdapter();
		
		double lastConfigUpdate = ((UserConfigurationTable) dbZugriff.getTable(UserConfigurationTable.TABLENAME)).getLastConfUpdate();
		/**
		 * We have to use separate 'if-then' clauses for each conf version (ascending order)
		 * because a user might have skipped some HO releases
		 *
		 * DO NOT use 'if-then-else' here, as this would ignores some updates!
		 */
		if (lastConfigUpdate < 1.4101 || (HOMainFrame.isDevelopment() && lastConfigUpdate == 1.4101)) {
			HOLogger.instance().log(getClass(), "Updating configuration to version 1.410-1...");
			updateConfigTo1410_1(HOMainFrame.isDevelopment() && lastConfigUpdate == 1.4101);
		}

		if (lastConfigUpdate < 1.420 || (HOMainFrame.isDevelopment() && lastConfigUpdate == 1.420)) {
			HOLogger.instance().log(getClass(), "Updating configuration to version 1.420...");
			updateConfigTo1420(HOMainFrame.isDevelopment() && lastConfigUpdate == 1.420);
		}

		if (lastConfigUpdate < 1.424 || (HOMainFrame.isDevelopment() && lastConfigUpdate == 1.424)) {
			HOLogger.instance().log(getClass(), "Updating configuration to version 1.424...");
			updateConfigTo1424(HOMainFrame.isDevelopment() && lastConfigUpdate == 1.424);
		}

		if (lastConfigUpdate < 1.425 || (HOMainFrame.isDevelopment() && lastConfigUpdate == 1.425)) {
			HOLogger.instance().log(getClass(), "Updating configuration to version 1.425...");
			updateConfigTo1425(HOMainFrame.isDevelopment() && lastConfigUpdate == 1.425);
		}
		
		if (lastConfigUpdate < 1.429 || (HOMainFrame.isDevelopment() && lastConfigUpdate == 1.429)) {

			// Lets not reset poor user's custom training setting each time they start...
			HOLogger.instance().log(getClass(), "Updating configuration to version 1.429...");
			updateConfigTo1429(HOMainFrame.isDevelopment() && lastConfigUpdate == 1.429);
		}
		
		if (lastConfigUpdate < 1.431 || (HOMainFrame.isDevelopment() && lastConfigUpdate == 1.431)) {

			HOLogger.instance().log(getClass(), "Updating configuration to version 1.431...");
			updateConfigTo1431(HOMainFrame.isDevelopment() && lastConfigUpdate == 1.431);
		}
		
	}

	private void updateConfigTo1410_1 (boolean alreadyApplied) {
		resetTrainingParameters();
		resetPredictionOffsets();

		// Drop the feedback tables to force new feedback upload for beta testers
		m_clJDBCAdapter.executeUpdate("DROP TABLE IF EXISTS FEEDBACK_SETTINGS");
		m_clJDBCAdapter.executeUpdate("DROP TABLE IF EXISTS FEEDBACK_UPLOAD");

		// always set the LastConfUpdate as last step
		dbZugriff.saveUserParameter("LastConfUpdate", 1.4101);
	}

	private void updateConfigTo1420 (boolean alreadyApplied) {
		resetTrainingParameters();
		resetPredictionOffsets();
		dbZugriff.saveUserParameter("anzahlNachkommastellen", 2);

		// always set the LastConfUpdate as last step
		dbZugriff.saveUserParameter("LastConfUpdate", 1.420);
	}

	private void updateConfigTo1424 (boolean alreadyApplied) {
		resetTrainingParameters (); // Reset training parameters (just to be sure)

		dbZugriff.saveUserParameter("updateCheck", "true");
		dbZugriff.saveUserParameter("newsCheck", "true");

		// always set the LastConfUpdate as last step
		dbZugriff.saveUserParameter("LastConfUpdate", 1.424);
	}

	private void updateConfigTo1425 (boolean alreadyApplied) {
		// Argentina.properties is outdated and got replaced by Spanish_sudamericano.properties
		m_clJDBCAdapter.executeUpdate("UPDATE USERCONFIGURATION SET CONFIG_VALUE='Spanish_sudamericano' where CONFIG_KEY='sprachDatei' and CONFIG_VALUE='Argentina'");

		// Apply only once
		if (!alreadyApplied) {
			dbZugriff.saveUserParameter("updateCheck", "true");
			dbZugriff.saveUserParameter("newsCheck", "true");

			// Drop the feedback tables to force new feedback upload
			m_clJDBCAdapter.executeUpdate("DROP TABLE IF EXISTS FEEDBACK_SETTINGS");
			m_clJDBCAdapter.executeUpdate("DROP TABLE IF EXISTS FEEDBACK_UPLOAD");
		}

		// always set the LastConfUpdate as last step
		dbZugriff.saveUserParameter("LastConfUpdate", 1.425);
	}
	
	private void updateConfigTo1429 (boolean alreadyApplied) {
		
		if (!alreadyApplied){
			resetTrainingParameters ();
		}
		// always set the LastConfUpdate as last step
		dbZugriff.saveUserParameter("LastConfUpdate", 1.429);
	}
	
	private void updateConfigTo1431 (boolean alreadyApplied) {
		
		if (!alreadyApplied){
			try {
				HOLogger.instance().debug(getClass(), "Reseting player overview rows.");
				String sql = "DELETE FROM USERCOLUMNS WHERE COLUMN_ID BETWEEN 2000 AND 3000";
				m_clJDBCAdapter.executeQuery(sql);
	
				HOLogger.instance().debug(getClass(), "Reseting lineup overview rows.");
				sql = "DELETE FROM USERCOLUMNS WHERE COLUMN_ID BETWEEN 3000 AND 4000";
				m_clJDBCAdapter.executeQuery(sql);
			} catch (Exception e) {
				HOLogger.instance().debug(getClass(), "Error updating to config 1431: " + e.getMessage());
			}
		}
		// always set the LastConfUpdate as last step
		dbZugriff.saveUserParameter("LastConfUpdate", 1.431);
	}
	
	private void resetTrainingParameters () {
		// Reset Training Speed Parameters for New Training
		// 1.429 training speed in db is now an offset
		HOLogger.instance().info(this.getClass(), "Resetting training parameters to default values");
		dbZugriff.saveUserParameter("DAUER_TORWART", 0.0);
		dbZugriff.saveUserParameter("DAUER_VERTEIDIGUNG", 0.0);
		dbZugriff.saveUserParameter("DAUER_SPIELAUFBAU", 0.0);
		dbZugriff.saveUserParameter("DAUER_PASSPIEL", 0.0);
		dbZugriff.saveUserParameter("DAUER_FLUEGELSPIEL", 0.0);
		dbZugriff.saveUserParameter("DAUER_CHANCENVERWERTUNG", 0.0);
		dbZugriff.saveUserParameter("DAUER_STANDARDS", 0.0);

		dbZugriff.saveUserParameter("AlterFaktor", 0.0);
		dbZugriff.saveUserParameter("TrainerFaktor", 0.0);
		dbZugriff.saveUserParameter("CoTrainerFaktor", 0.0);
		dbZugriff.saveUserParameter("IntensitaetFaktor", 0.0);
	}

	private void resetPredictionOffsets () {
		// Reset Rating offsets for Rating Prediction
		// because of changes in the prediction files
		HOLogger.instance().info(this.getClass(), "Resetting rating prediction offsets");
		dbZugriff.saveUserParameter("leftDefenceOffset", 0.0);
		dbZugriff.saveUserParameter("middleDefenceOffset", 0.0);
		dbZugriff.saveUserParameter("rightDefenceOffset", 0.0);
		dbZugriff.saveUserParameter("midfieldOffset", 0.0);
		dbZugriff.saveUserParameter("leftAttackOffset", 0.0);
		dbZugriff.saveUserParameter("middleAttackOffset", 0.0);
		dbZugriff.saveUserParameter("rightAttackOffset", 0.0);
	}
}
