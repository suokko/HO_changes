package de.hattrickorganizer.database;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Vector;

import plugins.IMatchKurzInfo;
import plugins.IMatchLineup;
import plugins.ISpielePanel;
import plugins.ITrainingWeek;
import de.hattrickorganizer.gui.model.ArenaStatistikModel;
import de.hattrickorganizer.gui.model.ArenaStatistikTableModel;
import de.hattrickorganizer.model.HOMiniModel;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.TrainingPerWeek;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.Helper;

public class StatisticQuery {

	public static double[][] getSpielerDaten4Statistik(int spielerId, int anzahlHRF) {

		Vector<ITrainingWeek> trainings = HOMiniModel.instance().getTrainingsManager().getTrainingsVector();
		final int anzahlSpalten = 16;
		final float faktor = gui.UserParameter.instance().faktorGeld;

		double[][] returnWerte = new double[0][0];
		final Vector<double[]> vWerte = new Vector<double[]>();

		ResultSet rs =
			DBZugriff.instance().getAdapter().executeQuery("SELECT * FROM SPIELER WHERE SpielerID=" + spielerId + " AND HRF_ID IN (" + getInClause(anzahlHRF, trainings) + ") ORDER BY Datum DESC");

		if (rs != null) {
			try {
				rs.beforeFirst();

				while (rs.next()) {
					final double[] tempwerte = new double[anzahlSpalten];

					//faktor;
					tempwerte[0] = rs.getDouble("Marktwert");
					tempwerte[1] = rs.getDouble("Gehalt") / faktor;
					tempwerte[2] = rs.getDouble("Fuehrung");
					tempwerte[3] = rs.getDouble("Erfahrung");
					tempwerte[4] = rs.getDouble("Form");
					tempwerte[5] = rs.getDouble("Kondition");
					tempwerte[6] = rs.getDouble("Torwart") + rs.getDouble("SubTorwart");
					tempwerte[7] = rs.getDouble("Verteidigung") + rs.getDouble("SubVerteidigung");
					tempwerte[8] = rs.getDouble("Spielaufbau") + rs.getDouble("SubSpielaufbau");
					tempwerte[9] = rs.getDouble("Passpiel") + rs.getDouble("SubPasspiel");
					tempwerte[10] = rs.getDouble("Fluegel") + rs.getDouble("SubFluegel");
					tempwerte[11] = rs.getDouble("Torschuss") + rs.getDouble("SubTorschuss");
					tempwerte[12] = rs.getDouble("Standards") + rs.getDouble("SubStandards");
					tempwerte[13] = rs.getDouble("Bewertung") / 2d;
					tempwerte[14] = rs.getDouble("Loyalty");
					tempwerte[15] = rs.getTimestamp("Datum").getTime();
					
					//TSI, alle Marktwerte / 1000 teilen
					if (rs.getTimestamp("Datum").before(DBZugriff.TSIDATE)) {
						tempwerte[0] /= 1000d;
					}

					vWerte.add(tempwerte);
				}

				returnWerte = new double[anzahlSpalten][vWerte.size()];

				for (int i = 0; i < vWerte.size(); i++) {
					final double[] werte = (double[]) vWerte.get(i);

					//Alle Ratings, die == 0 sind -> bis zu 6 Tage vorher nach Rating suchen
					if (werte[13] == 0) {
						final Timestamp hrftime = new Timestamp((long) werte[15]);

						//6 Tage vorher
						final Timestamp beforetime = new Timestamp((long) werte[15] - 518400000);
						rs =
							DBZugriff.instance().getAdapter().executeQuery(
								"SELECT Bewertung FROM SPIELER WHERE Bewertung>0 AND Datum>='"
									+ beforetime.toString()
									+ "' AND Datum<='"
									+ hrftime.toString()
									+ "' AND SpielerID="
									+ spielerId
									+ " ORDER BY Datum");

						//Wert gefunden
						if (rs.first()) {
							werte[13] = rs.getDouble("Bewertung") / 2d;
						}
					}

					for (int j = 0; j < werte.length; j++) {
						returnWerte[j][i] = werte[j];
					}
				}
			} catch (Exception e) {
				HOLogger.instance().log(StatisticQuery.class, "DatenbankZugriff.getSpielerDaten4Statistik " + e);
			}
		}

		return returnWerte;
	}

	/**
	 * Gibt die MatchDetails zu einem Match zurück
	 */
	public static ArenaStatistikTableModel getArenaStatistikModel(int matchtyp) {
		ArenaStatistikTableModel tablemodel = null;

		ArenaStatistikModel[] arenamodels = new ArenaStatistikModel[0];
		ArenaStatistikModel arenamodel = null;
		String sql = null;
		ResultSet rs = null;
		final Vector<ArenaStatistikModel> liste = new Vector<ArenaStatistikModel>();
		final int teamId = HOVerwaltung.instance().getModel().getBasics().getTeamId();
		int maxFans = 0;
		int maxArenaGroesse = 0;

		try {
			sql = ("SELECT " + MatchesKurzInfoTable.TABLENAME + ".* FROM " + MatchesKurzInfoTable.TABLENAME + " INNER JOIN  " + MatchDetailsTable.TABLENAME + " on " + MatchesKurzInfoTable.TABLENAME + ".matchid = " + MatchDetailsTable.TABLENAME + ".matchid ");
			sql += (" WHERE  Arenaname in (SELECT DISTINCT Stadionname as Arenaname FROM " + StadionTable.TABLENAME + ") AND " + MatchDetailsTable.TABLENAME + ".HeimID = " + teamId + " AND Status=" + IMatchKurzInfo.FINISHED);

			//Matchtypen
			switch (matchtyp) {
				case ISpielePanel.NUR_EIGENE_SPIELE :

					//Nix zu tun, da die teamId die einzige Einschränkung ist
					break;

				case ISpielePanel.NUR_EIGENE_PFLICHTSPIELE :
					sql += (" AND ( MatchTyp=" + IMatchLineup.QUALISPIEL);
					sql += (" OR MatchTyp=" + IMatchLineup.LIGASPIEL);
					sql += (" OR MatchTyp=" + IMatchLineup.POKALSPIEL + " )");
					break;

				case ISpielePanel.NUR_EIGENE_POKALSPIELE :
					sql += (" AND MatchTyp=" + IMatchLineup.POKALSPIEL);
					break;

				case ISpielePanel.NUR_EIGENE_LIGASPIELE :
					sql += (" AND MatchTyp=" + IMatchLineup.LIGASPIEL);
					break;

				case ISpielePanel.NUR_EIGENE_FREUNDSCHAFTSSPIELE :
					sql += (" AND ( MatchTyp=" + IMatchLineup.TESTSPIEL);
					sql += (" OR MatchTyp=" + IMatchLineup.TESTPOKALSPIEL);
					sql += (" OR MatchTyp=" + IMatchLineup.INT_TESTCUPSPIEL);
					sql += (" OR MatchTyp=" + IMatchLineup.INT_TESTSPIEL + " )");
					break;
			}

			sql += " ORDER BY MatchDate DESC";

			rs = DBZugriff.instance().getAdapter().executeQuery(sql);

			rs.beforeFirst();

			while (rs.next()) {
				//Paarung auslesen
				arenamodel = new de.hattrickorganizer.gui.model.ArenaStatistikModel();
				arenamodel.setMatchDate(rs.getString("MatchDate"));
				arenamodel.setGastName(DBZugriff.deleteEscapeSequences(rs.getString("GastName")));
				arenamodel.setHeimName(DBZugriff.deleteEscapeSequences(rs.getString("HeimName")));
				arenamodel.setMatchID(rs.getInt("MatchID"));
				arenamodel.setGastTore(rs.getInt("GastTore"));
				arenamodel.setHeimTore(rs.getInt("HeimTore"));
				arenamodel.setMatchID(rs.getInt("MatchID"));
				arenamodel.setMatchTyp(rs.getInt("MatchTyp"));
				arenamodel.setMatchStatus(rs.getInt("Status"));

				//Adden
				liste.add(arenamodel);
			}
		} catch (Exception e) {
			HOLogger.instance().log(StatisticQuery.class, "DB.getArenaStatistikModel 1 Error" + e);
			//HOLogger.instance().log(MarketQuery.class,e);
		}

		arenamodels = new ArenaStatistikModel[liste.size()];
		Helper.copyVector2Array(liste, arenamodels);

		// Jetzt noch die Arenadate für die Zeit holen
		for (int i = 0; i < arenamodels.length; i++) {
			final int hrfid = DBZugriff.instance().getHRFID4Date(arenamodels[i].getTimestampMatchDate());

			try {
				//Zuschauer
				sql = "SELECT Zuschauer, WetterId FROM " + MatchDetailsTable.TABLENAME + " WHERE MatchID=" + arenamodels[i].getMatchID();
				rs = DBZugriff.instance().getAdapter().executeQuery(sql);

				if (rs.first()) {
					arenamodels[i].setZuschaueranzahl(rs.getInt("Zuschauer"));
					arenamodels[i].setWetter(rs.getInt("WetterId"));
				}
				rs.close();

				//Stadiongrösse
				sql = "SELECT GesamtGr FROM " + StadionTable.TABLENAME + " WHERE HRF_ID=" + hrfid;
				rs = DBZugriff.instance().getAdapter().executeQuery(sql);
				if (rs.first()) {
					arenamodels[i].setArenaGroesse(rs.getInt("GesamtGr"));
					maxArenaGroesse = Math.max(arenamodels[i].getArenaGroesse(), maxArenaGroesse);
				}
				rs.close();

				// fix bug when visitors exceed the stadiumsize
				try {
					if (arenamodels[i].getZuschaueranzahl() > arenamodels[i].getArenaGroesse()) {
						rs = DBZugriff.instance().getAdapter().executeQuery("SELECT GesamtGr FROM " + StadionTable.TABLENAME + " WHERE HRF_ID=" + (hrfid+1));
						if (rs.next()) {
							arenamodels[i].setArenaGroesse(rs.getInt("GesamtGr"));
							maxArenaGroesse = Math.max(arenamodels[i].getArenaGroesse(), maxArenaGroesse);
						}
					}
				} catch (Exception e) {
					HOLogger.instance().log(StatisticQuery.class, "Error(>100% handling): " + e);
				} finally {
					if (rs != null) rs.close();
				}

				//Fananzahl
				sql = "SELECT Fans FROM " + VereinTable.TABLENAME + " WHERE HRF_ID=" + hrfid;
				rs = DBZugriff.instance().getAdapter().executeQuery(sql);
				if (rs.first()) {
					arenamodels[i].setFans(rs.getInt("Fans"));
					maxFans = Math.max(arenamodels[i].getFans(), maxFans);
				}
				rs.close();

				//Fanzufriedenheit
				sql = "SELECT Supporter FROM " + FinanzenTable.TABLENAME + " WHERE HRF_ID=" + hrfid;
				rs = DBZugriff.instance().getAdapter().executeQuery(sql);
				if (rs.first()) {
					arenamodels[i].setFanZufriedenheit(rs.getInt("Supporter"));
				}
				rs.close();

				//Ligaplatz
				sql = "SELECT Platz FROM " + LigaTable.TABLENAME + " WHERE HRF_ID=" + hrfid;
				rs = DBZugriff.instance().getAdapter().executeQuery(sql);
				if (rs.first()) {
					arenamodels[i].setLigaPlatz(rs.getInt("Platz"));
				}
				rs.close();
			} catch (Exception e) {
				HOLogger.instance().warning(StatisticQuery.class, "DB.getArenaStatistikModel 2 Error" + e);
				HOLogger.instance().log(StatisticQuery.class, e);
			}
		}

		tablemodel = new ArenaStatistikTableModel(arenamodels, maxArenaGroesse, maxFans);

		return tablemodel;
	}

	public static double[][] getDurchschnittlicheSpielerDaten4Statistik(int anzahlHRF, String gruppe) {
		Vector<ITrainingWeek> trainings = HOMiniModel.instance().getTrainingsManager().getTrainingsVector();
		final int anzahlSpalten = 15;
		final float faktor = gui.UserParameter.instance().faktorGeld;
		double[][] returnWerte = new double[0][0];
		final Vector<double[]> vWerte = new Vector<double[]>();

		String statement = "SELECT * FROM SPIELER";

		//Eine Gruppe gewählt
		if (!gruppe.equals("")) {
			statement += (" , SPIELERNOTIZ WHERE SPIELERNOTIZ.TeamInfoSmilie='" + gruppe + "' AND SPIELERNOTIZ.SpielerID=SPIELER.SpielerID AND");
		} else {
			statement += " WHERE ";
		}

		statement += (" Trainer=0 AND SPIELER.HRF_ID IN (" + getInClause(anzahlHRF, trainings) + ") ORDER BY Datum DESC");

		final ResultSet rs = DBZugriff.instance().getAdapter().executeQuery(statement);

		if (rs != null) {
			try {
				rs.beforeFirst();

				int letzteHRFID = -1;
				int spielerProHRFID = 0;
				double[] summewerte = new double[anzahlSpalten];

				while (rs.next()) {
					final double[] tempwerte = new double[anzahlSpalten - 1];
					tempwerte[0] = rs.getDouble("Fuehrung");
					tempwerte[1] = rs.getDouble("Erfahrung");
					tempwerte[2] = rs.getDouble("Form");
					tempwerte[3] = rs.getDouble("Kondition");
					tempwerte[4] = rs.getDouble("Torwart") + rs.getDouble("SubTorwart");
					tempwerte[5] = rs.getDouble("Verteidigung") + rs.getDouble("SubVerteidigung");
					tempwerte[6] = rs.getDouble("Spielaufbau") + rs.getDouble("SubSpielaufbau");
					tempwerte[7] = rs.getDouble("Passpiel") + rs.getDouble("SubPasspiel");
					tempwerte[8] = rs.getDouble("Fluegel") + rs.getDouble("SubFluegel");
					tempwerte[9] = rs.getDouble("Torschuss") + rs.getDouble("SubTorschuss");
					tempwerte[10] = rs.getDouble("Standards") + rs.getDouble("SubStandards");
					tempwerte[11] = rs.getDouble("Loyalty");
					tempwerte[12] = rs.getDouble("Marktwert");
					if (rs.getTimestamp("Datum").before(DBZugriff.TSIDATE)) {
						tempwerte[12] /= 1000d;
					}
					tempwerte[13] = rs.getDouble("Gehalt") / faktor;
					//Initialisierung
					if (letzteHRFID == -1) {
						//HRFID neu setzen, notwendig, wenn letzteid -1 war
						letzteHRFID = rs.getInt("HRF_ID");

						//summenwerte initialisieren
						for (int i = 0; i < summewerte.length; i++) {
							summewerte[i] = 0.0d;
						}
					}

					//Neues HRF beginnt
					if (letzteHRFID != rs.getInt("HRF_ID")) {
						//summenwerte durch anzahl Spieler pro HRF teilen
						for (int i = 0; i < (summewerte.length - 1); i++) {
							summewerte[i] = summewerte[i] / spielerProHRFID;
						}

						//summenwerte speichern
						vWerte.add(summewerte);

						//---Für neue HRF vorbereiten----
						letzteHRFID = rs.getInt("HRF_ID");

						summewerte = new double[anzahlSpalten];

						//summenwerte initialisieren
						for (int i = 0; i < summewerte.length; i++) {
							summewerte[i] = 0.0d;
						}

						spielerProHRFID = 0;
					}

					//Alle tempwerte zu den summenwerten addieren
					for (int i = 0; i < (summewerte.length - 1); i++) {
						summewerte[i] += tempwerte[i];
					}

					//Datum
					summewerte[14] = rs.getTimestamp("Datum").getTime();

					//Spieleranzahl pro HRF erhöhen
					spielerProHRFID++;
				}

				//Die letzen werte noch übernehmen
				//summenwerte durch anzahl Spieler pro HRF teilen
				for (int i = 0; i < (summewerte.length - 1); i++) {
					summewerte[i] = summewerte[i] / spielerProHRFID;
				}

				//summenwerte speichern
				vWerte.add(summewerte);

				returnWerte = new double[anzahlSpalten][vWerte.size()];

				for (int i = 0; i < vWerte.size(); i++) {
					final double[] werte = (double[]) vWerte.get(i);

					for (int j = 0; j < werte.length; j++) {
						returnWerte[j][i] = werte[j];
					}
				}
			} catch (Exception e) {
				HOLogger.instance().log(StatisticQuery.class, "DatenbankZugriff.getSpielerDaten4Statistik " + e);
			}
		}

		return returnWerte;
	}

	public static double[][] getFinanzen4Statistik(int anzahlHRF) {
		Vector<ITrainingWeek> trainings = HOMiniModel.instance().getTrainingsManager().getTrainingsVector();
		final int anzahlSpalten = 17;

		final double[][] marktwerte = getMarktwert4Statistik(anzahlHRF);
		final float faktor = gui.UserParameter.instance().faktorGeld;

		double[][] returnWerte = new double[0][0];
		Vector<double[]> vWerte = new Vector<double[]>();

		try {
			//aktuelle Werte hinzufügen
			ResultSet rs =
				DBZugriff.instance().getAdapter().executeQuery(
					"SELECT FINANZEN.*, VEREIN.Fans FROM FINANZEN, VEREIN where FINANZEN.HRF_ID="
						+ de.hattrickorganizer.model.HOVerwaltung.instance().getModel().getID()
						+ " AND VEREIN.HRF_ID="
						+ de.hattrickorganizer.model.HOVerwaltung.instance().getModel().getID());

			if (rs.first()) {
				final double[] tempwerte = new double[anzahlSpalten];
				tempwerte[0] = (rs.getDouble("Finanzen") / faktor) + (rs.getDouble("GewinnVerlust") / faktor);
				tempwerte[1] = rs.getDouble("GewinnVerlust") / faktor;
				tempwerte[2] = rs.getDouble("EinGesamt") / faktor;
				tempwerte[3] = rs.getDouble("KostGesamt") / faktor;
				tempwerte[4] = rs.getDouble("EinZuschauer") / faktor;
				tempwerte[5] = rs.getDouble("EinSponsoren") / faktor;
				tempwerte[6] = rs.getDouble("EinZinsen") / faktor;
				tempwerte[7] = rs.getDouble("EinSonstiges") / faktor;
				tempwerte[8] = rs.getDouble("KostStadion") / faktor;
				tempwerte[9] = rs.getDouble("KostSpieler") / faktor;
				tempwerte[10] = rs.getDouble("KostZinsen") / faktor;
				tempwerte[11] = rs.getDouble("KostSonstiges") / faktor;
				tempwerte[12] = rs.getDouble("KostTrainer") / faktor;
				tempwerte[13] = rs.getDouble("KostJugend") / faktor;
				tempwerte[14] = rs.getDouble("Fans");
				tempwerte[16] = rs.getTimestamp("Datum").getTime();

				vWerte.add(tempwerte);
			}

			rs =
				DBZugriff.instance().getAdapter().executeQuery(
					"SELECT FINANZEN.*, VEREIN.Fans FROM FINANZEN, VEREIN WHERE FINANZEN.HRF_ID=VEREIN.HRF_ID AND VEREIN.HRF_ID IN (" + getInClause(anzahlHRF, trainings) + ") ORDER BY Datum DESC");

			rs.beforeFirst();

			double[] tempwerte = null;

			while (rs.next()) {
				tempwerte = new double[anzahlSpalten];
				tempwerte[0] = rs.getDouble("Finanzen") / faktor;
				tempwerte[1] = rs.getDouble("LetzteGewinnVerlust") / faktor;
				tempwerte[2] = rs.getDouble("LetzteEinGesamt") / faktor;
				tempwerte[3] = rs.getDouble("LetzteKostGesamt") / faktor;
				tempwerte[4] = rs.getDouble("LetzteEinZuschauer") / faktor;
				tempwerte[5] = rs.getDouble("LetzteEinSponsoren") / faktor;
				tempwerte[6] = rs.getDouble("LetzteEinZinsen") / faktor;
				tempwerte[7] = rs.getDouble("LetzteEinSonstiges") / faktor;
				tempwerte[8] = rs.getDouble("LetzteKostStadion") / faktor;
				tempwerte[9] = rs.getDouble("LetzteKostSpieler") / faktor;
				tempwerte[10] = rs.getDouble("LetzteKostZinsen") / faktor;
				tempwerte[11] = rs.getDouble("LetzteKostSonstiges") / faktor;
				tempwerte[12] = rs.getDouble("LetzteKostTrainer") / faktor;
				tempwerte[13] = rs.getDouble("LetzteKostJugend") / faktor;
				tempwerte[14] = rs.getDouble("Fans");
				tempwerte[16] = rs.getTimestamp("Datum").getTime();

				//summenwerte speichern
				vWerte.add(tempwerte);
			}

			Vector<double[]> temp = new Vector<double[]>();

			for (int i = 0; i < vWerte.size(); i++) {
				final double[] werte = (double[]) vWerte.get(i);

				try {
					if (i == 0) {
						//Letzten Marktwert reinkopieren
						werte[15] = marktwerte[0][0];
					} else {
						//Marktwert reinkopieren
						werte[15] = marktwerte[0][i - 1];
					}

					temp.add(werte);
				} catch (Exception ex) {
					//Warum ist unklar
					HOLogger.instance().log(StatisticQuery.class, "DBZugriff.getFinanzen4Statistik : " + ex);
				}
			}

			//Zurückreferenzieren
			vWerte = temp;

			returnWerte = new double[anzahlSpalten][vWerte.size()];

			for (int i = 0; i < vWerte.size(); i++) {
				final double[] werte = (double[]) vWerte.get(i);

				for (int j = 0; j < werte.length; j++) {
					returnWerte[j][i] = werte[j];
				}
			}
		} catch (Exception e) {
			HOLogger.instance().log(StatisticQuery.class, "DatenbankZugriff.getFinanzen4Statistik " + e);
			HOLogger.instance().log(StatisticQuery.class, e);
		}

		return returnWerte;
	}

	public static double[][] getSpielerFinanzDaten4Statistik(int spielerId, int anzahlHRF) {
		Vector<ITrainingWeek> trainings = HOMiniModel.instance().getTrainingsManager().getTrainingsVector();
		final int anzahlSpalten = 3;
		final float faktor = gui.UserParameter.instance().faktorGeld;

		double[][] returnWerte = new double[0][0];
		final Vector<double[]> vWerte = new Vector<double[]>();

		ResultSet rs =
			DBZugriff.instance().getAdapter().executeQuery("SELECT * FROM SPIELER WHERE SpielerID=" + spielerId + " AND HRF_ID IN (" + getInClause(anzahlHRF, trainings) + ") ORDER BY Datum DESC");

		if (rs != null) {
			try {
				rs.beforeFirst();

				while (rs.next()) {
					final double[] tempwerte = new double[anzahlSpalten];

					//faktor;
					tempwerte[0] = rs.getDouble("Marktwert");
					tempwerte[1] = rs.getDouble("Gehalt") / faktor;
					tempwerte[2] = rs.getTimestamp("Datum").getTime();

					//TSI, alle Marktwerte / 1000 teilen
					if (rs.getTimestamp("Datum").before(DBZugriff.TSIDATE)) {
						tempwerte[0] /= 1000d;
					}

					vWerte.add(tempwerte);
				}

				returnWerte = new double[anzahlSpalten][vWerte.size()];

				for (int i = 0; i < vWerte.size(); i++) {
					final double[] werte = (double[]) vWerte.get(i);

					for (int j = 0; j < werte.length; j++) {
						returnWerte[j][i] = werte[j];
					}
				}
			} catch (Exception e) {
				HOLogger.instance().log(StatisticQuery.class, "DatenbankZugriff.getSpielerFinanzDaten4Statistik " + e);
			}
		}

		return returnWerte;
	}

	private static double[][] getMarktwert4Statistik(int anzahlHRF) {

		Vector<ITrainingWeek> trainings = HOMiniModel.instance().getTrainingsManager().getTrainingsVector();
		final int anzahlSpalten = 2;

		double[][] returnWerte = new double[0][0];
		final Vector<double[]> vWerte = new Vector<double[]>();

		final ResultSet rs =
			DBZugriff.instance().getAdapter().executeQuery(
				"SELECT SUM(Marktwert) as TSI, Max(Datum) as Datum FROM SPIELER WHERE Trainer=0 AND HRF_ID IN (" + getInClause(anzahlHRF, trainings) + ") group by HRF_ID ORDER BY Datum DESC");

		if (rs != null) {
			try {
				rs.beforeFirst();

				//double[] summewerte = new double[anzahlSpalten];

				while (rs.next()) {
					final double[] tempwerte = new double[anzahlSpalten];

					// / gui.UserParameter.instance ().faktorGeld;
					tempwerte[0] = rs.getDouble("TSI");
					tempwerte[1] = rs.getTimestamp("Datum").getTime();

					//TSI, alles vorher durch 1000 teilen
					if (rs.getTimestamp("Datum").before(DBZugriff.TSIDATE)) {
						tempwerte[0] /= 1000d;
					}
					vWerte.add(tempwerte);
				}

				returnWerte = new double[anzahlSpalten][vWerte.size()];

				for (int i = 0; i < vWerte.size(); i++) {
					final double[] werte = (double[]) vWerte.get(i);

					for (int j = 0; j < werte.length; j++) {
						returnWerte[j][i] = werte[j];
					}
				}
			} catch (Exception e) {
				HOLogger.instance().log(StatisticQuery.class, "DatenbankZugriff.getMarktwert4Statistik " + e);
			}
		}

		return returnWerte;
	}

	private static String getInClause(int anzahlHRF, Vector<ITrainingWeek> trainings) {
		StringBuffer inClause = new StringBuffer();
		int start = trainings.size()-anzahlHRF;
		if (start<0) {
			start=0;
		}
		inClause.append(DBZugriff.instance().getLatestHrfId());
		for (int index = start; index < trainings.size(); index++) {
			TrainingPerWeek tpw = (TrainingPerWeek) trainings.get(index);
			inClause.append(" , ");
			inClause.append("" + tpw.getPreviousHrfId());
		}
		return inClause.toString();
	}

}
