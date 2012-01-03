// %2737695228:de.hattrickorganizer.database%
package de.hattrickorganizer.database;


import java.awt.Color;
import java.sql.ResultSet;

import de.hattrickorganizer.model.HOParameter;
import de.hattrickorganizer.tools.HOLogger;

public class DBUserParameter {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * The Class managed loading and saving of UserParameter
     */
    protected DBUserParameter() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Speichert die Spaltenreihenfolge der Tabellen public void saveTabellenSpaltenReihenfolge(
     * int[][] spieleruebersicht, int[][] aufstellung ) {}
     */
    /**
     * Lädt die UserParameter direkt in das UserParameter-SingeltonObjekt
     */
    public final void loadUserParameter(JDBCAdapter m_clJDBCAdapter) {
        ResultSet rs = null;
        String statement = "SELECT * FROM UserParameter";

        try {
            rs = m_clJDBCAdapter.executeQuery(statement);

            while (rs != null && rs.next()) {

                //byte helper = 0;
                //Daten in UserParameter setzten
                final gui.UserParameter parameter = gui.UserParameter.instance();
				final HOParameter hoParameter = HOParameter.instance();

                //Frame
                parameter.hoMainFrame_PositionX = rs.getInt("FramePosX");
                parameter.hoMainFrame_PositionY = rs.getInt("FramePosY");
                parameter.hoMainFrame_width = rs.getInt("FrameWidth");
                parameter.hoMainFrame_height = rs.getInt("FrameHeight");

                //SpielerÜbersichtsPanel
                parameter.spielerUebersichtsPanel_horizontalLeftSplitPane = rs.getInt("SUP_HorLeft");
                parameter.spielerUebersichtsPanel_horizontalRightSplitPane = rs.getInt("SUP_HorRight");
                parameter.spielerUebersichtsPanel_verticalSplitPane = rs.getInt("SUP_Vert");

                //AufstellungsPanel
                parameter.aufstellungsPanel_verticalSplitPaneLow = rs.getInt("AP_VertLow");
                parameter.aufstellungsPanel_horizontalLeftSplitPane = rs.getInt("AP_HorLeft");
                parameter.aufstellungsPanel_horizontalRightSplitPane = rs.getInt("AP_HorRight");
                parameter.aufstellungsPanel_verticalSplitPane = rs.getInt("AP_Vert");

                //SpielePanel
                parameter.spielePanel_horizontalLeftSplitPane = rs.getInt("SP_HorLeft");
                parameter.spielePanel_verticalSplitPane = rs.getInt("SP_Vert");

                //SpielerAnalyse
                parameter.spielerAnalysePanel_horizontalSplitPane = rs.getInt("SPA_Hor");

                //AufstellungsAssistentPanel
                parameter.aufstellungsAssistentPanel_gruppe = rs.getString("AAP_Gruppe");
                parameter.aufstellungsAssistentPanel_reihenfolge = rs.getInt("AAP_Reihenfolge");
                parameter.aufstellungsAssistentPanel_not = rs.getBoolean("AAP_Not");
                parameter.aufstellungsAssistentPanel_cbfilter = rs.getBoolean("AAP_CBFilter");
                parameter.aufstellungsAssistentPanel_idealPosition = rs.getBoolean("AAP_IdealPositionZuerst");
                parameter.aufstellungsAssistentPanel_form = rs.getBoolean("AAP_FormBeruecksichtigen");
                parameter.aufstellungsAssistentPanel_verletzt = rs.getBoolean("AAP_VerletztIgnorieren");
                parameter.aufstellungsAssistentPanel_gesperrt = rs.getBoolean("AAP_GesperrtIgnorieren");
                parameter.aufstellungsAssistentPanel_notLast = rs.getBoolean("AAP_ExcludeLastLineup");

                //TransferScoutPanel
                parameter.transferScoutPanel_horizontalSplitPane = rs.getInt("TSP_Hor");

                //MiniScout
                parameter.miniscout_PositionX = rs.getInt("MS_PosX");
                parameter.miniscout_PositionY = rs.getInt("MS_PosY");

                //HRFPfad
                parameter.hrfImport_HRFPath = rs.getString("HRFImp_hrfPfad");
                parameter.spielPlanImport_Path = rs.getString("SpielplanImp_Pfad");
                parameter.matchLineupImport_Path = rs.getString("MatchLineupImp_Path");
                parameter.deadlineFrist = rs.getInt("ScoutSleep");

                parameter.ProxyAktiv = rs.getBoolean("ProxyAktiv");
                parameter.ProxyHost = rs.getString("ProxyHost");
                parameter.ProxyPort = rs.getString("ProxyPort");
                parameter.ProxyAuthAktiv = rs.getBoolean("ProxyAuthAktiv");
                parameter.ProxyAuthName = rs.getString("ProxyAuthName");
                parameter.ProxyAuthPassword = rs.getString("ProxyAuthPassword");
//                parameter.LoginName = rs.getString("LoginName");
//                parameter.LoginPWD = de.hattrickorganizer.tools.MyHelper.decryptString(rs.getString("LoginPWD"));
                parameter.AccessToken = rs.getString("AccessToken");
              	parameter.TokenSecret = rs.getString("TokenSecret");
                
                parameter.MinIdealPosStk = rs.getFloat("MinIdealPosStk");
                parameter.WetterEffektBonus = rs.getFloat("WetterEffektBonus");

                //Währung, Sprachdatei, Schriftgroesse
                //Die Währungsid ist veraltet, der Faktor wird nachher überschrieben durch die Extradata aus dem HRF, wenn vorhanden
                parameter.waehrungsID = rs.getInt("Waehrung");
                parameter.faktorGeld = de.hattrickorganizer.gui.menu.option.SonstigeOptionenPanel.getFaktorGeld4WaehrungsID(parameter.waehrungsID);
                parameter.sprachDatei = rs.getString("Sprachdatei");
                parameter.schriftGroesse = rs.getInt("Schriftgroesse");

                //Training
                parameter.TRAINING_OFFSET_GOALKEEPING = rs.getInt("Dauer_TW");
                parameter.DAUER_ALLGEMEIN = rs.getInt("Dauer_ALL");
                parameter.TRAINING_OFFSET_STAMINA = rs.getInt("Dauer_Kon");
                parameter.TRAINING_OFFSET_PLAYMAKING = rs.getInt("Dauer_SA");
                parameter.TRAINING_OFFSET_WINGER = rs.getInt("Dauer_FL");
                parameter.TRAINING_OFFSET_SCORING = rs.getInt("Dauer_CV");
                parameter.TRAINING_OFFSET_DEFENDING = rs.getInt("Dauer_VER");
                parameter.TRAINING_OFFSET_PASSING = rs.getInt("Dauer_PAS");
                parameter.TRAINING_OFFSET_SETPIECES = rs.getInt("Dauer_STD");

                //Faktoren
                parameter.TRAINING_OFFSET_AGE = rs.getFloat("Fktr_Alter");
                parameter.TrainerFaktor = rs.getFloat("Fktr_Trainer");
                parameter.TRAINING_OFFSET_ASSISTANTS = rs.getFloat("Fktr_CoTr");
                parameter.TRAINING_OFFSET_INTENSITY = rs.getFloat("Fktr_Training");

                //SpielerStatisik
                parameter.statistikAnzahlHRF = rs.getInt("Stat_AnzahlHRF");
                parameter.statistikHilfslinien = rs.getBoolean("Stat_Hilfslinien");
                parameter.statistikBeschriftung = rs.getBoolean("Stat_Beschriftung");
                parameter.statistikFuehrung = rs.getBoolean("Stat_Fuehrung");
                parameter.statistikErfahrung = rs.getBoolean("Stat_Erfahrung");
                parameter.statistikForm = rs.getBoolean("Stat_Form");
                parameter.statistikKondition = rs.getBoolean("Stat_Kondition");
                parameter.statistikTorwart = rs.getBoolean("Stat_Torwart");
                parameter.statistikVerteidigung = rs.getBoolean("Stat_Verteidigung");
                parameter.statistikSpielaufbau = rs.getBoolean("Stat_Spielaufbau");
                parameter.statistikPasspiel = rs.getBoolean("Stat_Passpiel");
                parameter.statistikFluegel = rs.getBoolean("Stat_Fluegel");
                parameter.statistikTorschuss = rs.getBoolean("Stat_Torschuss");
                parameter.statistikStandards = rs.getBoolean("Stat_Standards");
                parameter.statistikBewertung = rs.getBoolean("Stat_Bewertung");

                //SpielerFinanzStatistik - Benutzt für SpieleStatistik
                parameter.statistikSpielerFinanzenAnzahlHRF = rs.getInt("StatSpielerFinanzen_AnzahlHRF");
                parameter.statistikSpielerFinanzenHilfslinien = rs.getBoolean("StatSpielerFinanzen_Hilfslinien");
                parameter.statistikSpielerFinanzenBeschriftung = rs.getBoolean("StatSpielerFinanzen_Beschriftung");

                //Unused
                parameter.statistikSpielerFinanzenMarktwert = rs.getBoolean("StatSpielerFinanzen_Marktwert");

                //Unused
                parameter.statistikSpielerFinanzenGehalt = rs.getBoolean("StatSpielerFinanzen_Gehalt");
                parameter.statistikSpieleFilter = rs.getInt("Stat_spiele_filter");
                parameter.statistikSpieleBewertung = rs.getBoolean("Stat_spiele_bewertung");
                parameter.statistikSpieleMittelfeld = rs.getBoolean("Stat_spiele_mittelfeld");
                parameter.statistikSpieleRechteAbwehr = rs.getBoolean("Stat_spiele_rechteAbwehr");
                parameter.statistikSpieleAbwehrzentrum = rs.getBoolean("Stat_spiele_Abwehrzentrum");
                parameter.statistikSpieleLinkeAbwehr = rs.getBoolean("Stat_spiele_linkeAbwehr");
                parameter.statistikSpieleRechterAngriff = rs.getBoolean("Stat_spiele_rechterAngriff");
                parameter.statistikSpieleAngriffszentrum = rs.getBoolean("Stat_spiele_Angriffszentrum");
                parameter.statistikSpieleLinkerAngriff = rs.getBoolean("Stat_spiele_linkerAngriff");

                //Spiele
                parameter.spieleFilter = rs.getInt("SpieleFilter");

                //AlleSpielerStatisik
                parameter.statistikAlleAnzahlHRF = rs.getInt("StatAlle_AnzahlHRF");
                parameter.statistikAlleHilfslinien = rs.getBoolean("StatAlle_Hilfslinien");
                parameter.statistikAlleBeschriftung = rs.getBoolean("StatAlle_Beschriftung");
                parameter.statistikAlleFuehrung = rs.getBoolean("StatAlle_Fuehrung");
                parameter.statistikAlleErfahrung = rs.getBoolean("StatAlle_Erfahrung");
                parameter.statistikAlleForm = rs.getBoolean("StatAlle_Form");
                parameter.statistikAlleKondition = rs.getBoolean("StatAlle_Kondition");
                parameter.statistikAlleTorwart = rs.getBoolean("StatAlle_Torwart");
                parameter.statistikAlleVerteidigung = rs.getBoolean("StatAlle_Verteidigung");
                parameter.statistikAlleSpielaufbau = rs.getBoolean("StatAlle_Spielaufbau");
                parameter.statistikAllePasspiel = rs.getBoolean("StatAlle_Passpiel");
                parameter.statistikAlleFluegel = rs.getBoolean("StatAlle_Fluegel");
                parameter.statistikAlleTorschuss = rs.getBoolean("StatAlle_Torschuss");
                parameter.statistikAlleStandards = rs.getBoolean("StatAlle_Standards");

                //FinanzStatistik
                parameter.statistikFinanzenAnzahlHRF = rs.getInt("Stat_Finanzen_AnzahlHRF");
                parameter.statistikFinanzenHilfslinien = rs.getBoolean("Stat_Finanzen_Hilfslinien");
                parameter.statistikFinanzenBeschriftung = rs.getBoolean("Stat_Finanzen_Beschriftung");
                parameter.statistikKontostand = rs.getBoolean("Stat_Kontostand");
                parameter.statistikGewinnVerlust = rs.getBoolean("Stat_GewinnVerlust");
                parameter.statistikGesamtEinnahmen = rs.getBoolean("Stat_GesamtEinnahmen");
                parameter.statistikGesamtAusgaben = rs.getBoolean("Stat_GesamtAusgaben");
                parameter.statistikZuschauer = rs.getBoolean("Stat_Zuschauer");
                parameter.statistikSponsoren = rs.getBoolean("Stat_Sponsoren");
                parameter.statistikZinsertraege = rs.getBoolean("Stat_Zinsertraege");
                parameter.statistikSonstigeEinnahmen = rs.getBoolean("Stat_SonstigeEinnahmen");
                parameter.statistikStadion = rs.getBoolean("Stat_Stadion");
                parameter.statistikSpielergehaelter = rs.getBoolean("Stat_Spielergehaelter");
                parameter.statistikZinsaufwendungen = rs.getBoolean("Stat_Zinsaufwendungen");
                parameter.statistikSonstigeAusgaben = rs.getBoolean("Stat_SonstigeAusgaben");
                parameter.statistikTrainerstab = rs.getBoolean("Stat_Trainerstab");
                parameter.statistikJugend = rs.getBoolean("Stat_Jugend");
                parameter.statistikMarktwert = rs.getBoolean("Stat_Marktwert");
                parameter.statistikFananzahl = rs.getBoolean("Stat_Fananzahl");

                //Faktor für Zeilenbreite in den Tabellen, Wird nicht gespeichert, sondern berechnet
                //11 ist defaultschriftgroesse
                parameter.zellenbreitenFaktor = parameter.schriftGroesse / 11f;

//                parameter.htip = rs.getString("HTIP");

                parameter.updateCheck = rs.getBoolean("UpdateCheck");
                parameter.logoutOnExit = rs.getBoolean("LogoutOnExit");

                parameter.spielerDetails_PositionX = rs.getInt("SpielerDetails_PosX");
                parameter.spielerDetails_PositionY = rs.getInt("SpielerDetails_PosY");

                parameter.bestPostWidth = rs.getInt("BestPosWidth");

                parameter.zahlenFuerSkill = rs.getBoolean("ZahlenFuerSkill");

                parameter.showHRFSaveDialog = rs.getBoolean("ShowHRFSaveDialog");

                parameter.anzahlNachkommastellen = rs.getInt("Nachkommastellen");

                parameter.spieleranalyseVertikal = rs.getBoolean("SpieleranalyseVertikal");

                parameter.FG_STANDARD = new Color(rs.getInt("FarbeSpielerStandard"));
                parameter.FG_ANGESCHLAGEN = new Color(rs.getInt("FarbeSpielerAngeschlagen"));
                parameter.FG_VERLETZT = new Color(rs.getInt("FarbeSpielerVerletzt"));
                parameter.FG_ZWEIKARTEN = new Color(rs.getInt("FarbeSpielerZweiKarten"));
                parameter.FG_GESPERRT = new Color(rs.getInt("FarbeSpielerGesperrt"));
                parameter.FG_TRANSFERMARKT = new Color(rs.getInt("FarbeSpielerTransfermarkt"));

                parameter.statistikSpieleGesamt = rs.getBoolean("Stat_spiele_Gesamt");
                parameter.statistikSpieleStimmung = rs.getBoolean("Stat_spiele_Stimmung");
                parameter.statistikSpieleSelbstvertrauen = rs.getBoolean("Stat_spiele_Selbstvertrauen");
                parameter.standardsortierung = rs.getInt("Standardsortierung");
                parameter.einzelnePositionenAnzeigen = rs.getBoolean("EinzelnePositionenAnzeigen");

                parameter.tempTabSpieleruebersicht = rs.getBoolean("tempTabSpieleruebersicht");
                parameter.tempTabAufstellung = rs.getBoolean("tempTabAufstellung");
                parameter.tempTabLigatabelle = rs.getBoolean("tempTabLigatabelle");
                parameter.tempTabSpiele = rs.getBoolean("tempTabSpiele");
                parameter.tempTabSpieleranalyse = rs.getBoolean("tempTabSpieleranalyse");
                parameter.tempTabStatistik = rs.getBoolean("tempTabStatistik");
                parameter.tempTabTransferscout = rs.getBoolean("tempTabTransferscout");
                parameter.tempTabArenasizer = rs.getBoolean("tempTabArenasizer");
                parameter.tempTabInformation = rs.getBoolean("tempTabInformation");

                hoParameter.DBVersion = rs.getInt("DBVersion");
                parameter.TimeZoneDifference = rs.getInt("TimeZoneDifference");

				hoParameter.HOUsers = rs.getInt("HOUsers");
				hoParameter.HOTotalUsers = rs.getInt("HOUsersTot");

                //Spaltenreihenfolge
                statement = "SELECT * FROM Spaltenreihenfolge";

                rs = m_clJDBCAdapter.executeQuery(statement);

                if (rs != null) {
                    rs.first();

                    //Spaltenreihenfolge
                    int[][] spieleruebersichtreihenfolge = new int[45][2];
                    int[][] aufstellungsreihenfolge = new int[38][2];
                    int[][] spielereihenfolge = new int[6][2];
                    int[][] spieleranalysereihenfolge = new int[50][2];

                    for (int i = 0; i < spieleruebersichtreihenfolge.length; i++) {
                        spieleruebersichtreihenfolge[i][0] = i;
                    }

                    for (int i = 0; i < aufstellungsreihenfolge.length; i++) {
                        aufstellungsreihenfolge[i][0] = i;
                    }

                    for (int i = 0; i < spielereihenfolge.length; i++) {
                        spielereihenfolge[i][0] = i;
                    }

                    for (int i = 0; i < spieleranalysereihenfolge.length; i++) {
                        spieleranalysereihenfolge[i][0] = i;
                    }

                    spieleruebersichtreihenfolge[0][1] = rs.getInt("SU_Name");
                    spieleruebersichtreihenfolge[1][1] = rs.getInt("SU_Trickotnummer");
                    spieleruebersichtreihenfolge[2][1] = rs.getInt("SU_Nationalitaet");
                    spieleruebersichtreihenfolge[3][1] = rs.getInt("SU_Alter");
                    spieleruebersichtreihenfolge[4][1] = rs.getInt("SU_BestPos");
                    spieleruebersichtreihenfolge[5][1] = rs.getInt("SU_Aufgestellt");
                    spieleruebersichtreihenfolge[6][1] = rs.getInt("SU_Gruppe");
                    spieleruebersichtreihenfolge[7][1] = rs.getInt("SU_Status");
                    spieleruebersichtreihenfolge[8][1] = rs.getInt("SU_Fuehrung");
                    spieleruebersichtreihenfolge[9][1] = rs.getInt("SU_Erfahrung");
                    spieleruebersichtreihenfolge[10][1] = rs.getInt("SU_Form");
                    spieleruebersichtreihenfolge[11][1] = rs.getInt("SU_Kondition");
                    spieleruebersichtreihenfolge[12][1] = rs.getInt("SU_Torwart");
                    spieleruebersichtreihenfolge[13][1] = rs.getInt("SU_Verteidigung");
                    spieleruebersichtreihenfolge[14][1] = rs.getInt("SU_Spielaufbau");
                    spieleruebersichtreihenfolge[15][1] = rs.getInt("SU_Passpiel");
                    spieleruebersichtreihenfolge[16][1] = rs.getInt("SU_Fluegel");
                    spieleruebersichtreihenfolge[17][1] = rs.getInt("SU_Torschuss");
                    spieleruebersichtreihenfolge[18][1] = rs.getInt("SU_Standard");
                    spieleruebersichtreihenfolge[19][1] = rs.getInt("SU_HOTorwart");
                    spieleruebersichtreihenfolge[20][1] = rs.getInt("SU_HOInnenverteidiger");
                    spieleruebersichtreihenfolge[21][1] = rs.getInt("SU_HOInnenverteidiger_Aus");
                    spieleruebersichtreihenfolge[22][1] = rs.getInt("SU_HOInnenverteidiger_Off");
                    spieleruebersichtreihenfolge[23][1] = rs.getInt("SU_HOAussenverteidiger");
                    spieleruebersichtreihenfolge[24][1] = rs.getInt("SU_HOAussenverteidiger_In");
                    spieleruebersichtreihenfolge[25][1] = rs.getInt("SU_HOAussenverteidiger_Off");
                    spieleruebersichtreihenfolge[26][1] = rs.getInt("SU_HOAussenverteidiger_Def");
                    spieleruebersichtreihenfolge[27][1] = rs.getInt("SU_HOMittelfeld");
                    spieleruebersichtreihenfolge[28][1] = rs.getInt("SU_HOMittelfeld_Aus");
                    spieleruebersichtreihenfolge[29][1] = rs.getInt("SU_HOMittelfeld_Off");
                    spieleruebersichtreihenfolge[30][1] = rs.getInt("SU_HOMittelfeld_Def");
                    spieleruebersichtreihenfolge[31][1] = rs.getInt("SU_HOFluegelspiel");
                    spieleruebersichtreihenfolge[32][1] = rs.getInt("SU_HOFluegelspiel_In");
                    spieleruebersichtreihenfolge[33][1] = rs.getInt("SU_HOFluegelspiel_Off");
                    spieleruebersichtreihenfolge[34][1] = rs.getInt("SU_HOFluegelspiel_Def");
                    spieleruebersichtreihenfolge[35][1] = rs.getInt("SU_HOSturm");
                    spieleruebersichtreihenfolge[36][1] = rs.getInt("SU_HOSturm_Def");
                    spieleruebersichtreihenfolge[37][1] = rs.getInt("SU_Bewertung");
                    spieleruebersichtreihenfolge[38][1] = rs.getInt("SU__ToreGesamt");
                    spieleruebersichtreihenfolge[39][1] = rs.getInt("SU_ToreFreund");
                    spieleruebersichtreihenfolge[40][1] = rs.getInt("SU_ToreLiga");
                    spieleruebersichtreihenfolge[41][1] = rs.getInt("SU_TorePokal");
                    spieleruebersichtreihenfolge[42][1] = rs.getInt("SU_Gehalt");
                    spieleruebersichtreihenfolge[43][1] = rs.getInt("SU_Marktwert");
                    spieleruebersichtreihenfolge[44][1] = rs.getInt("SU_ID");
                    aufstellungsreihenfolge[0][1] = rs.getInt("AS_Name");
                    aufstellungsreihenfolge[1][1] = rs.getInt("AS_Trickotnummer");
                    aufstellungsreihenfolge[2][1] = rs.getInt("AS_Alter");
                    aufstellungsreihenfolge[3][1] = rs.getInt("AS_BestPos");
                    aufstellungsreihenfolge[4][1] = rs.getInt("AS_Aufgestellt");
                    aufstellungsreihenfolge[5][1] = rs.getInt("AS_Autolineup");
                    aufstellungsreihenfolge[6][1] = rs.getInt("AS_Gruppe");
                    aufstellungsreihenfolge[7][1] = rs.getInt("AS_Status");
                    aufstellungsreihenfolge[8][1] = rs.getInt("AS_Form");
                    aufstellungsreihenfolge[9][1] = rs.getInt("AS_Erfahrung");
                    aufstellungsreihenfolge[10][1] = rs.getInt("AS_Bewertung");
                    aufstellungsreihenfolge[11][1] = rs.getInt("AS_Kondition");
                    aufstellungsreihenfolge[12][1] = rs.getInt("AS_Torwart");
                    aufstellungsreihenfolge[13][1] = rs.getInt("AS_Verteidigung");
                    aufstellungsreihenfolge[14][1] = rs.getInt("AS_Spielaufbau");
                    aufstellungsreihenfolge[15][1] = rs.getInt("AS_Passpiel");
                    aufstellungsreihenfolge[16][1] = rs.getInt("AS_Fluegel");
                    aufstellungsreihenfolge[17][1] = rs.getInt("AS_Torschuss");
                    aufstellungsreihenfolge[18][1] = rs.getInt("AS_Standard");
                    aufstellungsreihenfolge[19][1] = rs.getInt("AS_HOTorwart");
                    aufstellungsreihenfolge[20][1] = rs.getInt("AS_HOInnenverteidiger");
                    aufstellungsreihenfolge[21][1] = rs.getInt("AS_HOInnenverteidiger_Aus");
                    aufstellungsreihenfolge[22][1] = rs.getInt("AS_HOInnenverteidiger_Off");
                    aufstellungsreihenfolge[23][1] = rs.getInt("AS_HOAussenverteidiger");
                    aufstellungsreihenfolge[24][1] = rs.getInt("AS_HOAussenverteidiger_In");
                    aufstellungsreihenfolge[25][1] = rs.getInt("AS_HOAussenverteidiger_Off");
                    aufstellungsreihenfolge[26][1] = rs.getInt("AS_HOAussenverteidiger_Def");
                    aufstellungsreihenfolge[27][1] = rs.getInt("AS_HOMittelfeld");
                    aufstellungsreihenfolge[28][1] = rs.getInt("AS_HOMittelfeld_Aus");
                    aufstellungsreihenfolge[29][1] = rs.getInt("AS_HOMittelfeld_Off");
                    aufstellungsreihenfolge[30][1] = rs.getInt("AS_HOMittelfeld_Def");
                    aufstellungsreihenfolge[31][1] = rs.getInt("AS_HOFluegelspiel");
                    aufstellungsreihenfolge[32][1] = rs.getInt("AS_HOFluegelspiel_In");
                    aufstellungsreihenfolge[33][1] = rs.getInt("AS_HOFluegelspiel_Off");
                    aufstellungsreihenfolge[34][1] = rs.getInt("AS_HOFluegelspiel_Def");
                    aufstellungsreihenfolge[35][1] = rs.getInt("AS_HOSturm");
                    aufstellungsreihenfolge[36][1] = rs.getInt("AS_HOSturm_Def");
                    aufstellungsreihenfolge[37][1] = rs.getInt("AS_ID");
                    spielereihenfolge[0][1] = rs.getInt("SP_Datum");
                    spielereihenfolge[1][1] = rs.getInt("SP_SpieleArt");
                    spielereihenfolge[2][1] = rs.getInt("SP_Heim");
                    spielereihenfolge[3][1] = rs.getInt("SP_Gast");
                    spielereihenfolge[4][1] = rs.getInt("SP_Ergebnis");
                    spielereihenfolge[5][1] = rs.getInt("SP_MatchID");
                    spieleranalysereihenfolge[0][1] = rs.getInt("SPA_Datum");
                    spieleranalysereihenfolge[1][1] = rs.getInt("SPA_Bewertung");
                    spieleranalysereihenfolge[2][1] = rs.getInt("SPA_Position");
                    spieleranalysereihenfolge[3][1] = rs.getInt("SPA_BestPos");
                    spieleranalysereihenfolge[4][1] = rs.getInt("SPA_Spielart");
                    spieleranalysereihenfolge[5][1] = rs.getInt("SPA_Heim");
                    spieleranalysereihenfolge[6][1] = rs.getInt("SPA_Gast");
                    spieleranalysereihenfolge[7][1] = rs.getInt("SPA_Ergebnis");
                    spieleranalysereihenfolge[8][1] = rs.getInt("SPA_Status");
                    spieleranalysereihenfolge[9][1] = rs.getInt("SPA_Wetter");
                    spieleranalysereihenfolge[10][1] = rs.getInt("SPA_Einstellung");
                    spieleranalysereihenfolge[11][1] = rs.getInt("SPA_Taktik");
                    spieleranalysereihenfolge[12][1] = rs.getInt("SPA_Taktikstaerke");
                    spieleranalysereihenfolge[13][1] = rs.getInt("SPA_Stimmung");
                    spieleranalysereihenfolge[14][1] = rs.getInt("SPA_Selbstvertrauen");
                    spieleranalysereihenfolge[15][1] = rs.getInt("SPA_Fuehrung");
                    spieleranalysereihenfolge[16][1] = rs.getInt("SPA_Erfahrung");
                    spieleranalysereihenfolge[17][1] = rs.getInt("SPA_Form");
                    spieleranalysereihenfolge[18][1] = rs.getInt("SPA_Kondition");
                    spieleranalysereihenfolge[19][1] = rs.getInt("SPA_Torwart");
                    spieleranalysereihenfolge[20][1] = rs.getInt("SPA_Verteidigung");
                    spieleranalysereihenfolge[21][1] = rs.getInt("SPA_Spielaufbau");
                    spieleranalysereihenfolge[22][1] = rs.getInt("SPA_Passpiel");
                    spieleranalysereihenfolge[23][1] = rs.getInt("SPA_Fluegel");
                    spieleranalysereihenfolge[24][1] = rs.getInt("SPA_Torschuss");
                    spieleranalysereihenfolge[25][1] = rs.getInt("SPA_Standard");
                    spieleranalysereihenfolge[26][1] = rs.getInt("SPA_HOTorwart");
                    spieleranalysereihenfolge[27][1] = rs.getInt("SPA_HOInnenverteidiger");
                    spieleranalysereihenfolge[28][1] = rs.getInt("SPA_HOInnenverteidiger_Aus");
                    spieleranalysereihenfolge[29][1] = rs.getInt("SPA_HOInnenverteidiger_Off");
                    spieleranalysereihenfolge[30][1] = rs.getInt("SPA_HOAussenverteidiger");
                    spieleranalysereihenfolge[31][1] = rs.getInt("SPA_HOAussenverteidiger_In");
                    spieleranalysereihenfolge[32][1] = rs.getInt("SPA_HOAussenverteidiger_Off");
                    spieleranalysereihenfolge[33][1] = rs.getInt("SPA_HOAussenverteidiger_Def");
                    spieleranalysereihenfolge[34][1] = rs.getInt("SPA_HOMittelfeld");
                    spieleranalysereihenfolge[35][1] = rs.getInt("SPA_HOMittelfeld_Aus");
                    spieleranalysereihenfolge[36][1] = rs.getInt("SPA_HOMittelfeld_Off");
                    spieleranalysereihenfolge[37][1] = rs.getInt("SPA_HOMittelfeld_Def");
                    spieleranalysereihenfolge[38][1] = rs.getInt("SPA_HOFluegelspiel");
                    spieleranalysereihenfolge[39][1] = rs.getInt("SPA_HOFluegelspiel_In");
                    spieleranalysereihenfolge[40][1] = rs.getInt("SPA_HOFluegelspiel_Off");
                    spieleranalysereihenfolge[41][1] = rs.getInt("SPA_HOFluegelspiel_Def");
                    spieleranalysereihenfolge[42][1] = rs.getInt("SPA_HOSturm");
                    spieleranalysereihenfolge[43][1] = rs.getInt("SPA_HOSturm_Def");
                    spieleranalysereihenfolge[44][1] = rs.getInt("SPA__ToreGesamt");
                    spieleranalysereihenfolge[45][1] = rs.getInt("SPA_ToreFreund");
                    spieleranalysereihenfolge[46][1] = rs.getInt("SPA_ToreLiga");
                    spieleranalysereihenfolge[47][1] = rs.getInt("SPA_TorePokal");
                    spieleranalysereihenfolge[48][1] = rs.getInt("SPA_Gehalt");
                    spieleranalysereihenfolge[49][1] = rs.getInt("SPA_Marktwert");

                }
            }
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"DBUserParameter.loadUserParameter: " + e);
            HOLogger.instance().log(getClass(),e);
        }
    }
}
