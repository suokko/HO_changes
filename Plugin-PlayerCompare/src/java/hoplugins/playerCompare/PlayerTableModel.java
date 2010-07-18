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

import java.util.*;

/**
 * @author KickMuck
 *
 * TODO 
 * Der Aufruf der Klasse muss in PlayerCompare noch angepasst werden
 * auch das erstellen des Vectors mit den Spielern vom Typ Player
 */
//public class PlayerTableModel extends AbstractTableModel{
public class PlayerTableModel extends DefaultTableModel{

	private int m_anzZeilen;
	private PlayerTableModel model;
	private IHOMiniModel m_iHoMiniModel;
	private Player tmpPlayer;
	private String[] columnNames;
	private Object[][] data;
	private Object[][] daten;
	private int[] oldValues;
	private double[] m_d_IdealStaerke;
	
	public PlayerTableModel(IHOMiniModel miniModel, Player m_spieler)
	{
		m_iHoMiniModel = miniModel;
		columnNames = new String[3];
		m_anzZeilen = 18;
		
		
		columnNames[0] = PlayerCompare.getPCProperties("original");
		columnNames[1] = PlayerCompare.getPCProperties("position");
		columnNames[2] = PlayerCompare.getPCProperties("geaendert");
		
		data = new Object[18][3];
		
		data[0][0] = new Float(m_spieler.getPosWertTWOld());
		data[0][1] = new String(m_iHoMiniModel.getResource().getProperty("TORW"));
		data[0][2] = new Float(m_spieler.getPosWertTW());
		data[1][0] = new Float(m_spieler.getPosWertIVOld());
		data[1][1] = new String(m_iHoMiniModel.getResource().getProperty("IV"));
		data[1][2] = new Float(m_spieler.getPosWertIV());
		data[2][0] = new Float(m_spieler.getPosWertIV_AOld());
		data[2][1] = new String(m_iHoMiniModel.getResource().getProperty("IVA"));
		data[2][2] = new Float(m_spieler.getPosWertIV_A());
		data[3][0] = new Float(m_spieler.getPosWertIV_OOld());
		data[3][1] = new String(m_iHoMiniModel.getResource().getProperty("IVO"));
		data[3][2] = new Float(m_spieler.getPosWertIV_O());
		data[4][0] = new Float(m_spieler.getPosWertAVOld());
		data[4][1] = new String(m_iHoMiniModel.getResource().getProperty("AV"));
		data[4][2] = new Float(m_spieler.getPosWertAV());
		data[5][0] = new Float(m_spieler.getPosWertAV_IOld());
		data[5][1] = new String(m_iHoMiniModel.getResource().getProperty("AVI"));
		data[5][2] = new Float(m_spieler.getPosWertAV_I());
		data[6][0] = new Float(m_spieler.getPosWertAV_OOld());
		data[6][1] = new String(m_iHoMiniModel.getResource().getProperty("AVO"));
		data[6][2] = new Float(m_spieler.getPosWertAV_O());
		data[7][0] = new Float(m_spieler.getPosWertAV_DOld());
		data[7][1] = new String(m_iHoMiniModel.getResource().getProperty("AVD"));
		data[7][2] = new Float(m_spieler.getPosWertAV_D());
		data[8][0] = new Float(m_spieler.getPosWertMIOld());
		data[8][1] = new String(m_iHoMiniModel.getResource().getProperty("MIT"));
		data[8][2] = new Float(m_spieler.getPosWertMI());
		data[9][0] = new Float(m_spieler.getPosWertMI_AOld());
		data[9][1] = new String(m_iHoMiniModel.getResource().getProperty("MITA"));
		data[9][2] = new Float(m_spieler.getPosWertMI_A());
		data[10][0] = new Float(m_spieler.getPosWertMI_OOld());
		data[10][1] = new String(m_iHoMiniModel.getResource().getProperty("MITO"));
		data[10][2] = new Float(m_spieler.getPosWertMI_O());
		data[11][0] = new Float(m_spieler.getPosWertMI_DOld());
		data[11][1] = new String(m_iHoMiniModel.getResource().getProperty("MITD"));
		data[11][2] = new Float(m_spieler.getPosWertMI_D());
		data[12][0] = new Float(m_spieler.getPosWertFLOld());
		data[12][1] = new String(m_iHoMiniModel.getResource().getProperty("FLG"));
		data[12][2] = new Float(m_spieler.getPosWertFL());
		data[13][0] = new Float(m_spieler.getPosWertFL_IOld());
		data[13][1] = new String(m_iHoMiniModel.getResource().getProperty("FLGI"));
		data[13][2] = new Float(m_spieler.getPosWertFL_I());
		data[14][0] = new Float(m_spieler.getPosWertFL_OOld());
		data[14][1] = new String(m_iHoMiniModel.getResource().getProperty("FLGO"));
		data[14][2] = new Float(m_spieler.getPosWertFL_O());
		data[15][0] = new Float(m_spieler.getPosWertFL_DOld());
		data[15][1] = new String(m_iHoMiniModel.getResource().getProperty("FLGD"));
		data[15][2] = new Float(m_spieler.getPosWertFL_D());
		data[16][0] = new Float(m_spieler.getPosWertSTOld());
		data[16][1] = new String(m_iHoMiniModel.getResource().getProperty("STU"));
		data[16][2] = new Float(m_spieler.getPosWertST());
		data[17][0] = new Float(m_spieler.getPosWertST_DOld());
		data[17][1] = new String(m_iHoMiniModel.getResource().getProperty("STUD"));
		data[17][2] = new Float(m_spieler.getPosWertST_D());
		PlayerCompare.appendText("PlayerTableModel PosWertSturm: " + m_spieler.getPosWertST());
	}

	
	public PlayerTableModel(IHOMiniModel miniModel, Player[] spieler, int call)
	{
		//*** Hinzugefügt 18.08.04 ***
		columnNames = new String[38];
		
		m_iHoMiniModel = miniModel;
		
		columnNames[0] = "";
		columnNames[1] = m_iHoMiniModel.getResource().getProperty("Name");
		columnNames[2] = "";
		columnNames[3] = "";
		columnNames[4] = m_iHoMiniModel.getResource().getProperty("BestePosition");
		columnNames[5] = m_iHoMiniModel.getResource().getProperty("Gruppe");
		columnNames[6] = m_iHoMiniModel.getResource().getProperty("FUE");
		columnNames[7] = m_iHoMiniModel.getResource().getProperty("ER");
		columnNames[8] = m_iHoMiniModel.getResource().getProperty("FO");
		columnNames[9] = m_iHoMiniModel.getResource().getProperty("KO");
		columnNames[10] = m_iHoMiniModel.getResource().getProperty("TW");
		columnNames[11] = m_iHoMiniModel.getResource().getProperty("VE");
		columnNames[12] = m_iHoMiniModel.getResource().getProperty("SA");
		columnNames[13] = m_iHoMiniModel.getResource().getProperty("PS");
		columnNames[14] = m_iHoMiniModel.getResource().getProperty("FL");
		columnNames[15] = m_iHoMiniModel.getResource().getProperty("TS");
		columnNames[16] = m_iHoMiniModel.getResource().getProperty("ST");
		columnNames[17] = m_iHoMiniModel.getResource().getProperty("TOR");
		columnNames[18] = m_iHoMiniModel.getResource().getProperty("IV");
		columnNames[19] = m_iHoMiniModel.getResource().getProperty("IVA");
		columnNames[20] = m_iHoMiniModel.getResource().getProperty("IVO");
		columnNames[21] = m_iHoMiniModel.getResource().getProperty("AV");
		columnNames[22] = m_iHoMiniModel.getResource().getProperty("AVI");
		columnNames[23] = m_iHoMiniModel.getResource().getProperty("AVO");
		columnNames[24] = m_iHoMiniModel.getResource().getProperty("AVD");
		columnNames[25] = m_iHoMiniModel.getResource().getProperty("MIT");
		columnNames[26] = m_iHoMiniModel.getResource().getProperty("MITA");
		columnNames[27] = m_iHoMiniModel.getResource().getProperty("MITO");
		columnNames[28] = m_iHoMiniModel.getResource().getProperty("MITD");
		columnNames[29] = m_iHoMiniModel.getResource().getProperty("FLG");
		columnNames[30] = m_iHoMiniModel.getResource().getProperty("FLGI");
		columnNames[31] = m_iHoMiniModel.getResource().getProperty("FLGO");
		columnNames[32] = m_iHoMiniModel.getResource().getProperty("FLGD");
		columnNames[33] = m_iHoMiniModel.getResource().getProperty("STU");
		columnNames[34] = m_iHoMiniModel.getResource().getProperty("STUD");
		columnNames[35] = m_iHoMiniModel.getResource().getProperty("Gehalt");
		columnNames[36] = "TSI";
		columnNames[37] = m_iHoMiniModel.getResource().getProperty("ID");
		
		m_anzZeilen = spieler.length;
			
		int counter = 0;
		
		data = new Object[spieler.length][columnNames.length];
		for(int i = 0; i < spieler.length; i++)
		{
			tmpPlayer = spieler[i];
			if(call == 1)
			{
				data[counter][0] = new Boolean(false);
				data[counter][4] = new Float(tmpPlayer.getBestPositionOld() + (tmpPlayer.getBestPosStaerkeOld()) / 100);
			}
			else
			{
				data[counter][0] = "";
				data[counter][4] = new Float(tmpPlayer.getBestPosition() + (tmpPlayer.getBestPosStaerke()) / 100);
			}
			
			data[counter][1] = new String(tmpPlayer.getName()+";"+tmpPlayer.getSpezialitaet());
			data[counter][2] = new Integer(tmpPlayer.getNation());
			data[counter][3] = new Integer(tmpPlayer.getAlter());
			data[counter][5] = new String(tmpPlayer.getGruppe());
			data[counter][6] = new Double(tmpPlayer.getFuehrung() + (tmpPlayer.getFuehrung() * 0.01));
			data[counter][7] = new Double(tmpPlayer.getSkillCompareAsDouble(0));
			data[counter][8] = new Double(tmpPlayer.getSkillCompareAsDouble(1));
			data[counter][9] = new Double(tmpPlayer.getSkillCompareAsDouble(2));
			data[counter][10] = new Double(tmpPlayer.getSkillCompareAsDouble(3));
			data[counter][11] = new Double(tmpPlayer.getSkillCompareAsDouble(4));
			data[counter][12] = new Double(tmpPlayer.getSkillCompareAsDouble(5));
			data[counter][13] = new Double(tmpPlayer.getSkillCompareAsDouble(6));
			data[counter][14] = new Double(tmpPlayer.getSkillCompareAsDouble(7));
			data[counter][15] = new Double(tmpPlayer.getSkillCompareAsDouble(8));
			data[counter][16] = new Double(tmpPlayer.getSkillCompareAsDouble(9));
			data[counter][17] = new String(tmpPlayer.getPositionCompareAsString(ISpielerPosition.TORWART));
			data[counter][18] = new String(tmpPlayer.getPositionCompareAsString(ISpielerPosition.INNENVERTEIDIGER));
			data[counter][19] = new String(tmpPlayer.getPositionCompareAsString(ISpielerPosition.INNENVERTEIDIGER_AUS));
			data[counter][20] = new String(tmpPlayer.getPositionCompareAsString(ISpielerPosition.INNENVERTEIDIGER_OFF));
			data[counter][21] = new String(tmpPlayer.getPositionCompareAsString(ISpielerPosition.AUSSENVERTEIDIGER));
			data[counter][22] = new String(tmpPlayer.getPositionCompareAsString(ISpielerPosition.AUSSENVERTEIDIGER_IN));
			data[counter][23] = new String(tmpPlayer.getPositionCompareAsString(ISpielerPosition.AUSSENVERTEIDIGER_OFF));
			data[counter][24] = new String(tmpPlayer.getPositionCompareAsString(ISpielerPosition.AUSSENVERTEIDIGER_DEF));
			data[counter][25] = new String(tmpPlayer.getPositionCompareAsString(ISpielerPosition.MITTELFELD));
			data[counter][26] = new String(tmpPlayer.getPositionCompareAsString(ISpielerPosition.MITTELFELD_AUS));
			data[counter][27] = new String(tmpPlayer.getPositionCompareAsString(ISpielerPosition.MITTELFELD_OFF));
			data[counter][28] = new String(tmpPlayer.getPositionCompareAsString(ISpielerPosition.MITTELFELD_DEF));
			data[counter][29] = new String(tmpPlayer.getPositionCompareAsString(ISpielerPosition.FLUEGELSPIEL));
			data[counter][30] = new String(tmpPlayer.getPositionCompareAsString(ISpielerPosition.FLUEGELSPIEL_IN));
			data[counter][31] = new String(tmpPlayer.getPositionCompareAsString(ISpielerPosition.FLUEGELSPIEL_OFF));
			data[counter][32] = new String(tmpPlayer.getPositionCompareAsString(ISpielerPosition.FLUEGELSPIEL_DEF));
			data[counter][33] = new String(tmpPlayer.getPositionCompareAsString(ISpielerPosition.STURM));
			data[counter][34] = new String(tmpPlayer.getPositionCompareAsString(ISpielerPosition.STURM_DEF));
			
			data[counter][35] = new Integer((tmpPlayer.getGehalt())/10);
			data[counter][36] = new Integer(tmpPlayer.getTsi());
			data[counter][37] = new Integer(tmpPlayer.getId());
			counter++;
		}
	}

	public boolean isCellEditable(int row, int col)
	{
		/********* Nötiges Einbinden der Spalte 8 für Testweise Einbinden CellEditor
		if(col == 0 || col == 8)
		***************************************************************************/
		if(col == 0 && this.getColumnCount() > 3)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public Class getColumnClass(int columnIndex) 
	{
		Object o = getValueAt(0, columnIndex);
		if (o == null) 
		{
			return Object.class;
		} 
		else 
		{
			return o.getClass();
		}
	}	
	public int getColumnCount(){
		return columnNames.length;
	}

	public int getRowCount(){
		return m_anzZeilen;
	}
	
	public String getColumnName(int col)
	{
		return columnNames[col];
	}
	
	public Object getValueAt(int row, int col)
    {
        return data[row][col];
    }
	
	public void setValueAt(Object value, int row, int col)
	{
		data[row][col] = value;
		fireTableCellUpdated(row,col);
	}
}