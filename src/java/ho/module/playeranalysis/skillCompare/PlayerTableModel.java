/*
 * Created on 20.06.2004
 */
package ho.module.playeranalysis.skillCompare;

//import hoplugins.PlayerCompare;

//import java.awt.*;
//import javax.swing.*;

import ho.core.model.HOVerwaltung;
import ho.core.model.player.ISpielerPosition;

import javax.swing.table.DefaultTableModel;


class PlayerTableModel extends DefaultTableModel{

	/**
	 *
	 */
	private static final long serialVersionUID = -5133382441969026697L;
	private int m_anzZeilen;
	private Player tmpPlayer;
	private String[] columnNames;
	private Object[][] data;

	PlayerTableModel( Player m_spieler)
	{

		columnNames = new String[3];
		m_anzZeilen = 19;

		columnNames[0] = HOVerwaltung.instance().getLanguageString("original");
		columnNames[1] = HOVerwaltung.instance().getLanguageString("Position");
		columnNames[2] = HOVerwaltung.instance().getLanguageString("geaendert");

		data = new Object[19][3];

		data[0][0] = Float.valueOf(m_spieler.getOldPosVal_GK());
		data[0][1] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.keeper");
		data[0][2] = Float.valueOf(m_spieler.getPosVal_GK());
		data[1][0] = Float.valueOf(m_spieler.getOldPosVal_CD());
		data[1][1] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.centraldefender");
		data[1][2] = Float.valueOf(m_spieler.getPosVal_CD());
		data[2][0] = Float.valueOf(m_spieler.getOldPosVal_CD_TW());
		data[2][1] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.centraldefendertowardswing");
		data[2][2] = Float.valueOf(m_spieler.getPosVal_CD_TW());
		data[3][0] = Float.valueOf(m_spieler.getOldPosVal_CD_O());
		data[3][1] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.centraldefenderoffensive");
		data[3][2] = Float.valueOf(m_spieler.getPosVal_CD_O());
		data[4][0] = Float.valueOf(m_spieler.getOldPosVal_WB());
		data[4][1] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.wingback");
		data[4][2] = Float.valueOf(m_spieler.getPosVAL_WB());
		data[5][0] = Float.valueOf(m_spieler.getOldPosVal_WB_TM());
		data[5][1] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.wingbacktowardsmiddle");
		data[5][2] = Float.valueOf(m_spieler.getPosVal_WB_TM());
		data[6][0] = Float.valueOf(m_spieler.getOldPosVal_WB_O());
		data[6][1] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.wingbackoffensive");
		data[6][2] = Float.valueOf(m_spieler.getPosVal_WB_O());
		data[7][0] = Float.valueOf(m_spieler.getOldPosVAL_WB_D());
		data[7][1] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.wingbackdefensive");
		data[7][2] = Float.valueOf(m_spieler.getPosVal_WB_D());
		data[8][0] = Float.valueOf(m_spieler.getOldPosVal_IM());
		data[8][1] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.innermidfielder");
		data[8][2] = Float.valueOf(m_spieler.getPosVal_IM());
		data[9][0] = Float.valueOf(m_spieler.getOldPosVal_IM_TW());
		data[9][1] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.innermidfieldertowardswing");
		data[9][2] = Float.valueOf(m_spieler.getPosVal_IM_TW());
		data[10][0] = Float.valueOf(m_spieler.getOldPosVal_IM_O());
		data[10][1] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.innermidfielderoffensive");
		data[10][2] = Float.valueOf(m_spieler.getPosVal_IM_O());
		data[11][0] = Float.valueOf(m_spieler.getOldPosVal_IM_D());
		data[11][1] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.innermidfielderdefensive");
		data[11][2] = Float.valueOf(m_spieler.getPosVal_IM_D());
		data[12][0] = Float.valueOf(m_spieler.getOldPosVal_W());
		data[12][1] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.winger");
		data[12][2] = Float.valueOf(m_spieler.getPosVal_W());
		data[13][0] = Float.valueOf(m_spieler.getOldPosVal_W_TM());
		data[13][1] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.wingertowardsmiddle");
		data[13][2] = Float.valueOf(m_spieler.getPosVal_W_TM());
		data[14][0] = Float.valueOf(m_spieler.getOldPosVal_W_O());
		data[14][1] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.wingeroffensive");
		data[14][2] = Float.valueOf(m_spieler.getPosVal_W_O());
		data[15][0] = Float.valueOf(m_spieler.getOldPosVal_W_D());
		data[15][1] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.wingerdefensive");
		data[15][2] = Float.valueOf(m_spieler.getPosVal_W_D());
		data[16][0] = Float.valueOf(m_spieler.getOldPosVal_F());
		data[16][1] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.innermidfieldertowardswing");
		data[16][2] = Float.valueOf(m_spieler.getPosVal_F());
		data[17][0] = Float.valueOf(m_spieler.getOldPosVal_F_D());
		data[17][1] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.forwarddefensive");
		data[17][2] = Float.valueOf(m_spieler.getPosVal_F_D());
		data[18][0] = Float.valueOf(m_spieler.getOldPosVal_F_TW());
		data[18][1] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.forwardtowardswing");
		data[18][2] = Float.valueOf(m_spieler.getPosVal_F_TW());
	}

	PlayerTableModel( Player[] spieler, int call)
	{
		columnNames = new String[41];


		columnNames[0] = "";
		columnNames[1] = HOVerwaltung.instance().getLanguageString("Name");
		columnNames[2] = "";
		columnNames[3] = "";
		columnNames[4] = HOVerwaltung.instance().getLanguageString("BestePosition");
		columnNames[5] = HOVerwaltung.instance().getLanguageString("Gruppe");
		columnNames[6] = HOVerwaltung.instance().getLanguageString("MC");
		columnNames[7] = HOVerwaltung.instance().getLanguageString("FUE");
		columnNames[8] = HOVerwaltung.instance().getLanguageString("ER");
		columnNames[9] = HOVerwaltung.instance().getLanguageString("FO");
		columnNames[10] = HOVerwaltung.instance().getLanguageString("KO");
		columnNames[11] = HOVerwaltung.instance().getLanguageString("LOY");
		columnNames[12] = HOVerwaltung.instance().getLanguageString("TW");
		columnNames[13] = HOVerwaltung.instance().getLanguageString("VE");
		columnNames[14] = HOVerwaltung.instance().getLanguageString("SA");
		columnNames[15] = HOVerwaltung.instance().getLanguageString("PS");
		columnNames[16] = HOVerwaltung.instance().getLanguageString("FL");
		columnNames[17] = HOVerwaltung.instance().getLanguageString("TS");
		columnNames[18] = HOVerwaltung.instance().getLanguageString("ST");
		columnNames[19] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.keeper");
		columnNames[20] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.centraldefender");
		columnNames[21] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.centraldefendertowardswing");
		columnNames[22] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.centraldefenderoffensive");
		columnNames[23] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.wingback");
		columnNames[24] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.wingbacktowardsmiddle");
		columnNames[25] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.wingbackoffensive");
		columnNames[26] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.wingbackdefensive");
		columnNames[27] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.innermidfielder");
		columnNames[28] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.innermidfieldertowardswing");
		columnNames[29] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.innermidfielderoffensive");
		columnNames[30] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.innermidfielderdefensive");
		columnNames[31] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.winger");
		columnNames[32] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.wingertowardsmiddle");
		columnNames[33] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.wingeroffensive");
		columnNames[34] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.wingerdefensive");
		columnNames[35] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.innermidfieldertowardswing");
		columnNames[36] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.forwarddefensive");
		columnNames[37] = HOVerwaltung.instance().getLanguageString("ls.player.position_short.forwardtowardswing");
		columnNames[38] = HOVerwaltung.instance().getLanguageString("Gehalt");
		columnNames[39] = "TSI";
		columnNames[40] = HOVerwaltung.instance().getLanguageString("ID");

		m_anzZeilen = spieler.length;

		int counter = 0;

		data = new Object[spieler.length][columnNames.length];
		for(int i = 0; i < spieler.length; i++) {
			tmpPlayer = spieler[i];
			if(call == 1) {
				data[counter][0] = new Boolean(false);
				data[counter][4] = Float.valueOf(tmpPlayer.getOldBestPosition() + (tmpPlayer.getOldBestPositionRating()) / 100);
			} else {
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

	@Override
	public boolean isCellEditable(int row, int col)	{
		if(col == 0 && this.getColumnCount() > 3)
			return true;
		return false;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex)	{
		Object o = getValueAt(0, columnIndex);
		return o == null?Object.class:o.getClass();
	}

	@Override
	public int getColumnCount(){
		return columnNames.length;
	}

	@Override
	public int getRowCount(){
		return m_anzZeilen;
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int row, int col) {
        return data[row][col];
    }

	@Override
	public void setValueAt(Object value, int row, int col) {
		data[row][col] = value;
		fireTableCellUpdated(row,col);
	}
}