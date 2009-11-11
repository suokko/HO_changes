/*
 * Created on 19.04.2005
 *
 */
package hoplugins.conv;


import java.io.File;
import java.sql.ResultSet;

import javax.swing.JOptionPane;


/**
 * @author Thorsten Dietz
 *  
 */
final class DBToHrf extends HrfMaker {
    private String trainerID, trainerName;
    private int[] position = new int[]{
          0,4,5,6,7,8,9,10,11,12,13,14,18,15,16,17,19  
    };
    private StringBuffer error;
    private boolean exportStatus = true;
    
    protected DBToHrf(){
        type = "DB";
    }
    
    protected void start(File[] selectedFiles,File targetDir) {

        String filter = "";
        error = new StringBuffer();
        try {
            Object[] ids = null;

            ids = getHRF_Ids();

            if (ids.length == 0)
                return;
           
            for (int i = 0; i < ids.length; i++) {
                trainerID = "";
                trainerName = "";
                exportStatus = true;
                
                clearArrays();

                addBasics();
                analyzeBasic((SimpleObject) ids[i]);
                if(exportStatus)
                analyzeEconomy((SimpleObject) ids[i]);
                
                if(exportStatus)
                analyzeClub((SimpleObject) ids[i]);
                
                if(exportStatus)
                analyzeArena((SimpleObject) ids[i]);
                
                if(exportStatus)
                analyzeTraining((SimpleObject) ids[i]);
                
                if(exportStatus)
                analyzeLineup((SimpleObject) ids[i]);
                
                if(exportStatus)
                analyzePlayer((SimpleObject) ids[i]);
                
                if(exportStatus)
                analyzeExtra((SimpleObject) ids[i]);
                
                if(exportStatus)
                analyzeLeague((SimpleObject) ids[i]);

                String tmp = ((SimpleObject) ids[i]).getTxt();
                filter = tmp.substring(0, 4);
                filter += tmp.substring(5, 7);
                filter += tmp.substring(8, 10);

                if(exportStatus)
                writeHrf(filter, targetDir);
                
            }
            if(error.length() > 0){
                debug(error.toString());
            }
            JOptionPane.showMessageDialog(null, RSC.getProperty("finished"));

        } catch (Exception e1) {
            debug(e1);
        }
    }

    private final void analyzeBasic(SimpleObject hrfID) throws Exception {
        
        ResultSet rs = RSC.MINIMODEL.getAdapter().executeQuery(
                "SELECT * FROM BASICS where HRF_ID = " + hrfID.getId());
        if (rs.next()) {
            basicsValue[3][1] = rs.getString("DATUM").substring(0, 19);
            basicsValue[4][1] = rs.getString("SAISON");
            basicsValue[5][1] = rs.getString("SPIELTAG");
            basicsValue[6][1] = rs.getString("TEAMID");
            basicsValue[7][1] = rs.getString("TEAMNAME");
            basicsValue[8][1] = rs.getString("MANAGER");
//          basicsValue[9][1] = rs.getString(""); // ownerEmail
//          basicsValue[10][1] = rs.getString(""); // ownerICQ
//          basicsValue[11][1] = rs.getString(""); // ownerHomepage
            basicsValue[12][1] = rs.getString("LAND");
            basicsValue[13][1] = rs.getString("LIGA");
        } else {
            addError(hrfID,"BASICS");
            exportStatus=false;
        }
        rs.close();
    }
    
    private void addError(SimpleObject hrfID, String table){
        error.append("<p>Error => Table ");
        error.append(table);
        error.append(" ID:");
        error.append(hrfID.getId());
        error.append(" FROM: ");
        error.append(hrfID.getTxt());
        error.append(" no record found</p>");
    }

    private final void analyzeExtra(SimpleObject hrfID) throws Exception {
        ResultSet rs = RSC.MINIMODEL.getAdapter().executeQuery(
                "SELECT * FROM XTRADATA where HRF_ID = " + hrfID.getId());
        if (rs.next()) {
            xtraValue[1][1] = rs.getString("TRAININGDATE").substring(0, 19);
            xtraValue[2][1] = rs.getString("ECONOMYDATE").substring(0, 19);
            xtraValue[3][1] = rs.getString("SERIESMATCHDATE").substring(0, 19);
            xtraValue[4][1] = rs.getString("CURRENCYNAME");
            xtraValue[5][1] = rs.getString("CURRENCYRATE");
            xtraValue[6][1] = rs.getString("LOGOURL");
            xtraValue[7][1] = rs.getString("HASPROMOTED");
            xtraValue[8][1] = trainerID;
            xtraValue[9][1] = trainerName;
 //        xtraValue[10][1] = rs.getString("");//ArrivalDate no Info in DB
            xtraValue[11][1] = rs.getString("LeagueLevelUnitID");
        } else {
            addError(hrfID,"XTRADATA (OLD HRF)");
        }
        rs.close();
    }

    private final void analyzeLeague(SimpleObject hrfID) throws Exception {
        ResultSet rs = RSC.MINIMODEL.getAdapter().executeQuery(
                "SELECT * FROM LIGA where HRF_ID = " + hrfID.getId());
        if (rs.next()) {
            leagueValue[1][1] = rs.getString("LIGANAME");
            leagueValue[5][1] = rs.getString("PUNKTE");
            leagueValue[3][1] = rs.getString("TOREFUER");
            leagueValue[4][1] = rs.getString("TOREGEGEN");
            leagueValue[6][1] = rs.getString("PLATZ");
            leagueValue[2][1] = rs.getString("SPIELTAG");
        } else {
            addError(hrfID,"LIGA");
            exportStatus=false;
        }
        rs.close();
    }

    private final void analyzeLineup(SimpleObject hrfID) throws Exception {
        int index = 0;
        String tableName = "POSITION";
        if (RSC.MINIMODEL.getHOVersion() >= 1.2972)
            tableName = "POSITIONEN";

        // HO!
        ResultSet rs = RSC.MINIMODEL.getAdapter().executeQuery(
                "SELECT * FROM " + tableName + " where HRF_ID = "
                        + hrfID.getId() + " AND AUFSTELLUNGSNAME = 'HO!'");
        while (rs.next()) {
            String playerID = rs.getString("SPIELERID");
            index = position[Integer.parseInt(rs.getString("ID"))];
            linupValue[index][1] = playerID;
        }
        rs.close();

        // HO! LastLineup
        rs = RSC.MINIMODEL.getAdapter().executeQuery(
                "SELECT * FROM " + tableName + " where HRF_ID = "
                        + hrfID.getId()
                        + " AND AUFSTELLUNGSNAME = 'HO!LastLineup'");
        while (rs.next()) {
            String playerID = rs.getString("SPIELERID");
            index = position[Integer.parseInt(rs.getString("ID"))];
            lastlinupValue[index][1] = playerID;
        }
        rs.close();

    }

    private final void analyzePlayer(SimpleObject hrfID) throws Exception {
        ResultSet rs = RSC.MINIMODEL.getAdapter().executeQuery(
                "SELECT Count(*) FROM SPIELER where HRF_ID = " + hrfID.getId());
        rs.next();
        initPlayerArray(rs.getInt(1));
        rs.close();
        String tmpTrainerType = "";
        String playerID = "";
        rs = RSC.MINIMODEL.getAdapter().executeQuery(
                "SELECT * FROM SPIELER where HRF_ID = " + hrfID.getId());
        for (int i = 1; rs.next(); i++) {
            playerID = rs.getString("SPIELERID");
            playerValue[0][i] = playerID + "]";
            playerValue[1][i] = rs.getString("NAME");
            playerValue[2][i] = rs.getString("ALTER");
            playerValue[3][i] = rs.getString("VERLETZT");
            playerValue[4][i] = rs.getString("FORM");
            playerValue[5][i] = rs.getString("KONDITION");
            playerValue[6][i] = rs.getString("SPIELAUFBAU");
            playerValue[7][i] = rs.getString("TORSCHUSS");
            playerValue[8][i] = rs.getString("PASSPIEL");
            playerValue[9][i] = rs.getString("FLUEGEL");
            playerValue[10][i] = rs.getString("STANDARDS");
            playerValue[11][i] = rs.getString("VERTEIDIGUNG");
            playerValue[12][i] = rs.getString("TORWART");
            playerValue[13][i] = rs.getString("ERFAHRUNG");
            playerValue[14][i] = rs.getString("FUEHRUNG");
            playerValue[15][i] = rs.getString("GEHALT");
            playerValue[16][i] = rs.getString("MARKTWERT");
            playerValue[17][i] = rs.getString("TOREGESAMT");
            playerValue[18][i] = rs.getString("TORELIGA");
            playerValue[19][i] = rs.getString("TOREPOKAL");
            playerValue[20][i] = rs.getString("TOREFREUND");
            playerValue[21][i] = rs.getString("HATTRICK");
            playerValue[22][i] = rs.getString("LAND");
            playerValue[23][i] = rs.getString("GELBEKARTEN");
            playerValue[24][i] = rs.getString("ISPEZIALITAET");
            playerValue[25][i] = rs.getString("SSPEZIALITAET");
            playerValue[28][i] = rs.getString("IANSEHEN");
            playerValue[29][i] = rs.getString("SANSEHEN");
            playerValue[26][i] = rs.getString("ICHARAKTER");
            playerValue[27][i] = rs.getString("SCHARAKTER");
            playerValue[30][i] = rs.getString("IAGRESSIVITAET");
            playerValue[31][i] = rs.getString("SAGRESSIVITAET");
            tmpTrainerType = rs.getString("TRAINERTYP");
            if (tmpTrainerType != null && Integer.parseInt(tmpTrainerType) > -1) {
                playerValue[32][i] = tmpTrainerType;
                playerValue[33][i] = rs.getString("TRAINER");
                trainerID = playerID;
                trainerName = playerValue[1][i];
                //trainerSkill=playerValue[33][i];
                linupValue[1][1] = trainerID;
                lastlinupValue[1][1] = trainerID;
            }

            playerValue[34][i] = rs.getString("BEWERTUNG");
            playerValue[35][i] = rs.getString("PLAYERNUMBER");
            playerValue[36][i] = rs.getString("TRANSFERLISTED");
            //    playerValue[37][i] = rs.getString(""); // NationalTeamID no Info in DB
            playerValue[38][i] = rs.getString("Caps");
            playerValue[39][i] = rs.getString("CapsU20");

        } // for
    }

    

    private final void analyzeEconomy(SimpleObject hrfID) throws Exception {
        ResultSet rs = RSC.MINIMODEL.getAdapter().executeQuery(
                "SELECT * FROM FINANZEN where HRF_ID = " + hrfID.getId());
        if (rs.next()) {
            economyValue[1][1] = rs.getString("SUPPORTER");
            economyValue[2][1] = rs.getString("SPONSOREN");
            economyValue[3][1] = rs.getString("FINANZEN"); 
            economyValue[5][1] = rs.getString("EINZUSCHAUER");
            economyValue[4][1] = rs.getString("EINSPONSOREN");
            economyValue[6][1] = rs.getString("EINZINSEN");
            economyValue[7][1] = rs.getString("EINSONSTIGES");
            economyValue[8][1] = rs.getString("EINGESAMT");
            economyValue[9][1] = rs.getString("KOSTSPIELER");
            economyValue[10][1] = rs.getString("KOSTTRAINER");
            economyValue[11][1] = rs.getString("KOSTSTADION");
            economyValue[12][1] = rs.getString("KOSTJUGEND");
            economyValue[13][1] = rs.getString("KOSTZINSEN");
            economyValue[14][1] = rs.getString("KOSTSONSTIGES");
            economyValue[15][1] = rs.getString("KOSTGESAMT");
            economyValue[16][1] = rs.getString("GEWINNVERLUST");
            economyValue[17][1] = rs.getString("LETZTEEINSPONSOREN");
            economyValue[18][1] = rs.getString("LETZTEEINZUSCHAUER");
            economyValue[19][1] = rs.getString("LETZTEEINZINSEN");
            economyValue[20][1] = rs.getString("LETZTEEINSONSTIGES");
            economyValue[21][1] = rs.getString("LETZTEEINGESAMT");
            economyValue[22][1] = rs.getString("LETZTEKOSTSPIELER");
            economyValue[23][1] = rs.getString("LETZTEKOSTTRAINER");
            economyValue[24][1] = rs.getString("LETZTEKOSTSTADION");
            economyValue[25][1] = rs.getString("LETZTEKOSTJUGEND");
            economyValue[26][1] = rs.getString("LETZTEKOSTZINSEN");
            economyValue[27][1] = rs.getString("LETZTEKOSTSONSTIGES");
            economyValue[28][1] = rs.getString("LETZTEKOSTGESAMT");
            economyValue[29][1] = rs.getString("LETZTEGEWINNVERLUST");
        } else {
            addError(hrfID,"FINANZEN");
            exportStatus=false;
        }
        rs.close();
    }

    private final void analyzeClub(SimpleObject hrfID) throws Exception {
        ResultSet rs = RSC.MINIMODEL.getAdapter().executeQuery(
                "SELECT * FROM VEREIN where HRF_ID = " + hrfID.getId());
        if (rs.next()) {
            clubValue[1][1] = rs.getString("TWTRAINER");

            clubValue[2][1] = rs.getString("COTRAINER");
            clubValue[3][1] = rs.getString("PSCHYOLOGEN");
            clubValue[4][1] = rs.getString("PRMANAGER");
            clubValue[5][1] = rs.getString("FINANZBERATER");
            clubValue[6][1] = rs.getString("PHYSIOLOGEN");
            clubValue[7][1] = rs.getString("AERZTE");
            clubValue[8][1] = rs.getString("JUGEND");
            clubValue[9][1] = rs.getString("UNGESCHLAGEN");
            clubValue[10][1] = rs.getString("SIEGE");
            clubValue[11][1] = rs.getString("FANS");
        } else {
            addError(hrfID,"VEREIN");
            exportStatus=false;
        }
        rs.close();
    }

    private final void analyzeArena(SimpleObject hrfID) throws Exception {
        ResultSet rs = RSC.MINIMODEL.getAdapter().executeQuery(
                "SELECT * FROM STADION where HRF_ID = " + hrfID.getId());
        if (rs.next()) {
            arenaValue[1][1] = rs.getString("STADIONNAME");
            arenaValue[2][1] = rs.getString("ANZSTEH");
            arenaValue[3][1] = rs.getString("ANZSITZ");
            arenaValue[4][1] = rs.getString("ANZDACH");
            arenaValue[5][1] = rs.getString("ANZLOGEN");
            arenaValue[6][1] = rs.getString("GESAMTGR");
        } else {
            addError(hrfID,"STADION");
            exportStatus=false;
        }
        rs.close();
    }

    private final void analyzeTraining(SimpleObject hrfID) throws Exception {
        ResultSet rs = RSC.MINIMODEL.getAdapter().executeQuery(
                "SELECT * FROM TEAM where HRF_ID = " + hrfID.getId());
        if (rs.next()) {
            teamValue[1][1] = rs.getString("TRAININGSINTENSITAET");
            teamValue[2][1] = rs.getString("TRAININGSART");
            teamValue[3][1] = rs.getString("STRAININGSART");
            teamValue[4][1] = rs.getString("ISTIMMUNG");
            teamValue[5][1] = rs.getString("SSTIMMUNG");
            teamValue[6][1] = rs.getString("ISELBSTVERTRAUEN");
            teamValue[7][1] = rs.getString("SSELBSTVERTRAUEN");
            teamValue[8][1] = rs.getString("IERFAHRUNG433");
            teamValue[9][1] = rs.getString("IERFAHRUNG451");
            teamValue[10][1] = rs.getString("IERFAHRUNG352");
            teamValue[11][1] = rs.getString("IERFAHRUNG532");
            teamValue[12][1] = rs.getString("IERFAHRUNG343");
            teamValue[13][1] = rs.getString("IERFAHRUNG541");
        } else {
            addError(hrfID,"TEAM");
            exportStatus=false;
        }
        rs.close();
    }

    private final Object[] getHRF_Ids() throws Exception {
        SimpleObject[] hrfs = null;
        ResultSet rs = RSC.MINIMODEL.getAdapter().executeQuery(
                "SELECT * FROM HRF ORDER BY HRF_ID");
        if(rs.next()){
            rs.last();
            int row = rs.getRow();
            rs.beforeFirst();
            hrfs = new SimpleObject[row];
            for (int i = 0; rs.next(); i++) {
                hrfs[i] = new SimpleObject(rs.getInt(1), rs.getTimestamp("Datum")
                    .toString());
            } // FOR
        }// if
        rs.close();

        SelectObjectDialog dialog = new SelectObjectDialog(RSC.MINIMODEL.getGUI()
                .getOwner4Dialog(), 240, 320, hrfs);
        dialog.setVisible(true);
        if (dialog.isSaved()) {
            return dialog.getSelectedObjects();
        }

        return new SimpleObject[0];
    }

    
}