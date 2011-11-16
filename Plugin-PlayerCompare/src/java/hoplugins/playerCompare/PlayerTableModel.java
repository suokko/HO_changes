/*
 * Created on 20.06.2004
 */
package hoplugins.playerCompare;

//import hoplugins.PlayerCompare;

//import java.awt.*;
//import javax.swing.*;

import hoplugins.PlayerCompare;

import javax.swing.table.*;

import plugins.*;

/**
 * @author KickMuck
 *
 * TODO 
 * Der Aufruf der Klasse muss in PlayerCompare noch angepasst werden
 * auch das erstellen des Vectors mit den Spielern vom Typ Player
 */
//public class PlayerTableModel extends AbstractTableModel{
public class PlayerTableModel extends DefaultTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5133382441969026697L;
	private int m_anzZeilen;
	private IHOMiniModel m_iHoMiniModel;
	private Player tmpPlayer;
	private String[] columnNames;
	private Object[][] data;

	public PlayerTableModel(IHOMiniModel miniModel, Player m_spieler)
	{
		m_iHoMiniModel = miniModel;
		columnNames = new String[3];
		m_anzZeilen = 19;
		
		columnNames[0] = PlayerCompare.getPCProperties("original");
		columnNames[1] = PlayerCompare.getPCProperties("position");
		columnNames[2] = PlayerCompare.getPCProperties("geaendert");
		
		data = new Object[19][3];
		
		data[0][0] = Float.valueOf(m_spieler.getOldPosVal_GK());
		data[0][1] = m_iHoMiniModel.getLanguageString("TORW");
		data[0][2] = Float.valueOf(m_spieler.getPosVal_GK());
		data[1][0] = Float.valueOf(m_spieler.getOldPosVal_CD());
		data[1][1] = m_iHoMiniModel.getLanguageString("IV");
		data[1][2] = Float.valueOf(m_spieler.getPosVal_CD());
		data[2][0] = Float.valueOf(m_spieler.getOldPosVal_CD_TW());
		data[2][1] = m_iHoMiniModel.getLanguageString("IVA");
		data[2][2] = Float.valueOf(m_spieler.getPosVal_CD_TW());
		data[3][0] = Float.valueOf(m_spieler.getOldPosVal_CD_O());
		data[3][1] = m_iHoMiniModel.getLanguageString("IVO");
		data[3][2] = Float.valueOf(m_spieler.getPosVal_CD_O());
		data[4][0] = Float.valueOf(m_spieler.getOldPosVal_WB());
		data[4][1] = m_iHoMiniModel.getLanguageString("AV");
		data[4][2] = Float.valueOf(m_spieler.getPosVAL_WB());
		data[5][0] = Float.valueOf(m_spieler.getOldPosVal_WB_TM());
		data[5][1] = m_iHoMiniModel.getLanguageString("AVI");
		data[5][2] = Float.valueOf(m_spieler.getPosVal_WB_TM());
		data[6][0] = Float.valueOf(m_spieler.getOldPosVal_WB_O());
		data[6][1] = m_iHoMiniModel.getLanguageString("AVO");
		data[6][2] = Float.valueOf(m_spieler.getPosVal_WB_O());
		data[7][0] = Float.valueOf(m_spieler.getOldPosVAL_WB_D());
		data[7][1] = m_iHoMiniModel.getLanguageString("AVD");
		data[7][2] = Float.valueOf(m_spieler.getPosVal_WB_D());
		data[8][0] = Float.valueOf(m_spieler.getOldPosVal_IM());
		data[8][1] = m_iHoMiniModel.getLanguageString("MIT");
		data[8][2] = Float.valueOf(m_spieler.getPosVal_IM());
		data[9][0] = Float.valueOf(m_spieler.getOldPosVal_IM_TW());
		data[9][1] = m_iHoMiniModel.getLanguageString("MITA");
		data[9][2] = Float.valueOf(m_spieler.getPosVal_IM_TW());
		data[10][0] = Float.valueOf(m_spieler.getOldPosVal_IM_O());
		data[10][1] = m_iHoMiniModel.getLanguageString("MITO");
		data[10][2] = Float.valueOf(m_spieler.getPosVal_IM_O());
		data[11][0] = Float.valueOf(m_spieler.getOldPosVal_IM_D());
		data[11][1] = m_iHoMiniModel.getLanguageString("MITD");
		data[11][2] = Float.valueOf(m_spieler.getPosVal_IM_D());
		data[12][0] = Float.valueOf(m_spieler.getOldPosVal_W());
		data[12][1] = m_iHoMiniModel.getLanguageString("FLG");
		data[12][2] = Float.valueOf(m_spieler.getPosVal_W());
		data[13][0] = Float.valueOf(m_spieler.getOldPosVal_W_TM());
		data[13][1] = m_iHoMiniModel.getLanguageString("FLGI");
		data[13][2] = Float.valueOf(m_spieler.getPosVal_W_TM());
		data[14][0] = Float.valueOf(m_spieler.getOldPosVal_W_O());
		data[14][1] = m_iHoMiniModel.getLanguageString("FLGO");
		data[14][2] = Float.valueOf(m_spieler.getPosVal_W_O());
		data[15][0] = Float.valueOf(m_spieler.getOldPosVal_W_D());
		data[15][1] = m_iHoMiniModel.getLanguageString("FLGD");
		data[15][2] = Float.valueOf(m_spieler.getPosVal_W_D());
		data[16][0] = Float.valueOf(m_spieler.getOldPosVal_F());
		data[16][1] = m_iHoMiniModel.getLanguageString("STU");
		data[16][2] = Float.valueOf(m_spieler.getPosVal_F());
		data[17][0] = Float.valueOf(m_spieler.getOldPosVal_F_D());
		data[17][1] = m_iHoMiniModel.getLanguageString("STUD");
		data[17][2] = Float.valueOf(m_spieler.getPosVal_F_D());
		data[18][0] = Float.valueOf(m_spieler.getOldPosVal_F_TW());
		data[18][1] = m_iHoMiniModel.getLanguageString("STUA");
		data[18][2] = Float.valueOf(m_spieler.getPosVal_F_TW());
	}
	
	public PlayerTableModel(IHOMiniModel miniModel, Player[] spieler, int call)
	{
		columnNames = new String[41];
		
		m_iHoMiniModel = miniModel;
		
		columnNames[0] = "";
		columnNames[1] = m_iHoMiniModel.getLanguageString("Name");
		columnNames[2] = "";
		columnNames[3] = "";
		columnNames[4] = m_iHoMiniModel.getLanguageString("BestePosition");
		columnNames[5] = m_iHoMiniModel.getLanguageString("Gruppe");
		columnNames[6] = m_iHoMiniModel.getLanguageString("MC");
		columnNames[7] = m_iHoMiniModel.getLanguageString("FUE");
		columnNames[8] = m_iHoMiniModel.getLanguageString("ER");
		columnNames[9] = m_iHoMiniModel.getLanguageString("FO");
		columnNames[10] = m_iHoMiniModel.getLanguageString("KO");
		columnNames[11] = m_iHoMiniModel.getLanguageString("LOY");
		columnNames[12] = m_iHoMiniModel.getLanguageString("TW");
		columnNames[13] = m_iHoMiniModel.getLanguageString("VE");
		columnNames[14] = m_iHoMiniModel.getLanguageString("SA");
		columnNames[15] = m_iHoMiniModel.getLanguageString("PS");
		columnNames[16] = m_iHoMiniModel.getLanguageString("FL");
		columnNames[17] = m_iHoMiniModel.getLanguageString("TS");
		columnNames[18] = m_iHoMiniModel.getLanguageString("ST");
		columnNames[19] = m_iHoMiniModel.getLanguageString("TOR");
		columnNames[20] = m_iHoMiniModel.getLanguageString("IV");
		columnNames[21] = m_iHoMiniModel.getLanguageString("IVA");
		columnNames[22] = m_iHoMiniModel.getLanguageString("IVO");
		columnNames[23] = m_iHoMiniModel.getLanguageString("AV");
		columnNames[24] = m_iHoMiniModel.getLanguageString("AVI");
		columnNames[25] = m_iHoMiniModel.getLanguageString("AVO");
		columnNames[26] = m_iHoMiniModel.getLanguageString("AVD");
		columnNames[27] = m_iHoMiniModel.getLanguageString("MIT");
		columnNames[28] = m_iHoMiniModel.getLanguageString("MITA");
		columnNames[29] = m_iHoMiniModel.getLanguageString("MITO");
		columnNames[30] = m_iHoMiniModel.getLanguageString("MITD");
		columnNames[31] = m_iHoMiniModel.getLanguageString("FLG");
		columnNames[32] = m_iHoMiniModel.getLanguageString("FLGI");
		columnNames[33] = m_iHoMiniModel.getLanguageString("FLGO");
		columnNames[34] = m_iHoMiniModel.getLanguageString("FLGD");
		columnNames[35] = m_iHoMiniModel.getLanguageString("STU");
		columnNames[36] = m_iHoMiniModel.getLanguageString("STUD");
		columnNames[37] = m_iHoMiniModel.getLanguageString("STUA");
		columnNames[38] = m_iHoMiniModel.getLanguageString("Gehalt");
		columnNames[39] = "TSI";
		columnNames[40] = m_iHoMiniModel.getLanguageString("ID");
		
		m_anzZeilen = spieler.length;
			
		int counter = 0;
		
		data = new Object[spieler.length][columnNames.length];
		for(int i = 0; i < spieler.length; i++)
		{
			tmpPlayer = spieler[i];
			if(call == 1)
			{
				data[counter][0] = new Boolean(false);
				data[counter][4] = Float.valueOf(tmpPlayer.getOldBestPosition() + (tmpPlayer.getOldBestPositionRating()) / 100);
			}
			else
			{
				data[counter][0] = "";
				data[counter][4] = Float.valueOf(tmpPlayer.getBestPosition() + (tmpPlayer.getBestPositionRating()) / 100);
			}
			
			data[counter][1] = tmpPlayer.getName()+";"+tmpPlayer.getSpeciality();
			data[counter][2] = Integer.valueOf(tmpPlayer.getNationality());
			data[counter][3] = Integer.valueOf(tmpPlayer.getAge());
			data[counter][5] = tmpPlayer.getGroup();
			data[counter][6] = Double.valueOf(tmpPlayer.getSkillCompareAsDouble(11));
			data[counter][7] = Double.valueOf(tmpPlayer.getLeadership() + (tmpPlayer.getLeadership() * 0.01));
			data[counter][8] = Double.valueOf(tmpPlayer.getSkillCompareAsDouble(0));
			data[counter][9] = Double.valueOf(tmpPlayer.getSkillCompareAsDouble(1));
			data[counter][10] = Double.valueOf(tmpPlayer.getSkillCompareAsDouble(2));
			data[counter][11] = Double.valueOf(tmpPlayer.getSkillCompareAsDouble(10));
			data[counter][12] = Double.valueOf(tmpPlayer.getSkillCompareAsDouble(3));
			data[counter][13] = Double.valueOf(tmpPlayer.getSkillCompareAsDouble(4));
			data[counter][14] = Double.valueOf(tmpPlayer.getSkillCompareAsDouble(5));
			data[counter][15] = Double.valueOf(tmpPlayer.getSkillCompareAsDouble(6));
			data[counter][16] = Double.valueOf(tmpPlayer.getSkillCompareAsDouble(7));
			data[counter][17] = Double.valueOf(tmpPlayer.getSkillCompareAsDouble(8));
			data[counter][18] = Double.valueOf(tmpPlayer.getSkillCompareAsDouble(9));
			data[counter][19] = tmpPlayer.getPositionCompareAsString(ISpielerPosition.KEEPER);
			data[counter][20] = tmpPlayer.getPositionCompareAsString(ISpielerPosition.CENTRAL_DEFENDER);
			data[counter][21] = tmpPlayer.getPositionCompareAsString(ISpielerPosition.CENTRAL_DEFENDER_TOWING);
			data[counter][22] = tmpPlayer.getPositionCompareAsString(ISpielerPosition.CENTRAL_DEFENDER_OFF);
			data[counter][23] = tmpPlayer.getPositionCompareAsString(ISpielerPosition.BACK);
			data[counter][24] = tmpPlayer.getPositionCompareAsString(ISpielerPosition.BACK_TOMID);
			data[counter][25] = tmpPlayer.getPositionCompareAsString(ISpielerPosition.BACK_OFF);
			data[counter][26] = tmpPlayer.getPositionCompareAsString(ISpielerPosition.BACK_DEF);
			data[counter][27] = tmpPlayer.getPositionCompareAsString(ISpielerPosition.MIDFIELDER);
			data[counter][28] = tmpPlayer.getPositionCompareAsString(ISpielerPosition.MIDFIELDER_TOWING);
			data[counter][29] = tmpPlayer.getPositionCompareAsString(ISpielerPosition.MIDFIELDER_OFF);
			data[counter][30] = tmpPlayer.getPositionCompareAsString(ISpielerPosition.MIDFIELDER_DEF);
			data[counter][31] = tmpPlayer.getPositionCompareAsString(ISpielerPosition.WINGER);
			data[counter][32] = tmpPlayer.getPositionCompareAsString(ISpielerPosition.WINGER_TOMID);
			data[counter][33] = tmpPlayer.getPositionCompareAsString(ISpielerPosition.WINGER_OFF);
			data[counter][34] = tmpPlayer.getPositionCompareAsString(ISpielerPosition.WINGER_DEF);
			data[counter][35] = tmpPlayer.getPositionCompareAsString(ISpielerPosition.FORWARD);
			data[counter][36] = tmpPlayer.getPositionCompareAsString(ISpielerPosition.FORWARD_DEF);
			data[counter][37] = tmpPlayer.getPositionCompareAsString(ISpielerPosition.FORWARD_TOWING);			
			data[counter][38] = Integer.valueOf((tmpPlayer.getWages())/10);
			data[counter][39] = Integer.valueOf(tmpPlayer.getTSI());
			data[counter][40] = Integer.valueOf(tmpPlayer.getId());
			counter++;
		}
	}

	public boolean isCellEditable(int row, int col)	{
		if(col == 0 && this.getColumnCount() > 3)
			return true;
		return false;
	}
	
	public Class<?> getColumnClass(int columnIndex)	{
		Object o = getValueAt(0, columnIndex);
		return o == null?Object.class:o.getClass();
	}
	
	public int getColumnCount(){
		return columnNames.length;
	}

	public int getRowCount(){
		return m_anzZeilen;
	}
	
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	public Object getValueAt(int row, int col) {
        return data[row][col];
    }
	
	public void setValueAt(Object value, int row, int col) {
		data[row][col] = value;
		fireTableCellUpdated(row,col);
	}
}