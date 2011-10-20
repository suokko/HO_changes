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
//	private PlayerTableModel model;
	private IHOMiniModel m_iHoMiniModel;
	private Player tmpPlayer;
	private String[] columnNames;
	private Object[][] data;
//	private Object[][] daten;
	//private int[] oldValues;
	//private double[] m_d_IdealStaerke;
	
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
		data[0][1] = new String(m_iHoMiniModel.getLanguageString("TORW"));
		data[0][2] = new Float(m_spieler.getPosWertTW());
		data[1][0] = new Float(m_spieler.getPosWertIVOld());
		data[1][1] = new String(m_iHoMiniModel.getLanguageString("IV"));
		data[1][2] = new Float(m_spieler.getPosWertIV());
		data[2][0] = new Float(m_spieler.getPosWertIV_AOld());
		data[2][1] = new String(m_iHoMiniModel.getLanguageString("IVA"));
		data[2][2] = new Float(m_spieler.getPosWertIV_A());
		data[3][0] = new Float(m_spieler.getPosWertIV_OOld());
		data[3][1] = new String(m_iHoMiniModel.getLanguageString("IVO"));
		data[3][2] = new Float(m_spieler.getPosWertIV_O());
		data[4][0] = new Float(m_spieler.getPosWertAVOld());
		data[4][1] = new String(m_iHoMiniModel.getLanguageString("AV"));
		data[4][2] = new Float(m_spieler.getPosWertAV());
		data[5][0] = new Float(m_spieler.getPosWertAV_IOld());
		data[5][1] = new String(m_iHoMiniModel.getLanguageString("AVI"));
		data[5][2] = new Float(m_spieler.getPosWertAV_I());
		data[6][0] = new Float(m_spieler.getPosWertAV_OOld());
		data[6][1] = new String(m_iHoMiniModel.getLanguageString("AVO"));
		data[6][2] = new Float(m_spieler.getPosWertAV_O());
		data[7][0] = new Float(m_spieler.getPosWertAV_DOld());
		data[7][1] = new String(m_iHoMiniModel.getLanguageString("AVD"));
		data[7][2] = new Float(m_spieler.getPosWertAV_D());
		data[8][0] = new Float(m_spieler.getPosWertMIOld());
		data[8][1] = new String(m_iHoMiniModel.getLanguageString("MIT"));
		data[8][2] = new Float(m_spieler.getPosWertMI());
		data[9][0] = new Float(m_spieler.getPosWertMI_AOld());
		data[9][1] = new String(m_iHoMiniModel.getLanguageString("MITA"));
		data[9][2] = new Float(m_spieler.getPosWertMI_A());
		data[10][0] = new Float(m_spieler.getPosWertMI_OOld());
		data[10][1] = new String(m_iHoMiniModel.getLanguageString("MITO"));
		data[10][2] = new Float(m_spieler.getPosWertMI_O());
		data[11][0] = new Float(m_spieler.getPosWertMI_DOld());
		data[11][1] = new String(m_iHoMiniModel.getLanguageString("MITD"));
		data[11][2] = new Float(m_spieler.getPosWertMI_D());
		data[12][0] = new Float(m_spieler.getPosWertFLOld());
		data[12][1] = new String(m_iHoMiniModel.getLanguageString("FLG"));
		data[12][2] = new Float(m_spieler.getPosWertFL());
		data[13][0] = new Float(m_spieler.getPosWertFL_IOld());
		data[13][1] = new String(m_iHoMiniModel.getLanguageString("FLGI"));
		data[13][2] = new Float(m_spieler.getPosWertFL_I());
		data[14][0] = new Float(m_spieler.getPosWertFL_OOld());
		data[14][1] = new String(m_iHoMiniModel.getLanguageString("FLGO"));
		data[14][2] = new Float(m_spieler.getPosWertFL_O());
		data[15][0] = new Float(m_spieler.getPosWertFL_DOld());
		data[15][1] = new String(m_iHoMiniModel.getLanguageString("FLGD"));
		data[15][2] = new Float(m_spieler.getPosWertFL_D());
		data[16][0] = new Float(m_spieler.getPosWertSTOld());
		data[16][1] = new String(m_iHoMiniModel.getLanguageString("STU"));
		data[16][2] = new Float(m_spieler.getPosWertST());
		data[17][0] = new Float(m_spieler.getPosWertST_DOld());
		data[17][1] = new String(m_iHoMiniModel.getLanguageString("STUD"));
		data[17][2] = new Float(m_spieler.getPosWertST_D());
		PlayerCompare.appendText("PlayerTableModel PosWertSturm: " + m_spieler.getPosWertST());
	}

	
	public PlayerTableModel(IHOMiniModel miniModel, Player[] spieler, int call)
	{
		//*** Hinzugef�gt 18.08.04 ***
		columnNames = new String[38];
		
		m_iHoMiniModel = miniModel;
		
		columnNames[0] = "";
		columnNames[1] = m_iHoMiniModel.getLanguageString("Name");
		columnNames[2] = "";
		columnNames[3] = "";
		columnNames[4] = m_iHoMiniModel.getLanguageString("BestePosition");
		columnNames[5] = m_iHoMiniModel.getLanguageString("Gruppe");
		columnNames[6] = m_iHoMiniModel.getLanguageString("FUE");
		columnNames[7] = m_iHoMiniModel.getLanguageString("ER");
		columnNames[8] = m_iHoMiniModel.getLanguageString("FO");
		columnNames[9] = m_iHoMiniModel.getLanguageString("KO");
		columnNames[10] = m_iHoMiniModel.getLanguageString("TW");
		columnNames[11] = m_iHoMiniModel.getLanguageString("VE");
		columnNames[12] = m_iHoMiniModel.getLanguageString("SA");
		columnNames[13] = m_iHoMiniModel.getLanguageString("PS");
		columnNames[14] = m_iHoMiniModel.getLanguageString("FL");
		columnNames[15] = m_iHoMiniModel.getLanguageString("TS");
		columnNames[16] = m_iHoMiniModel.getLanguageString("ST");
		columnNames[17] = m_iHoMiniModel.getLanguageString("TOR");
		columnNames[18] = m_iHoMiniModel.getLanguageString("IV");
		columnNames[19] = m_iHoMiniModel.getLanguageString("IVA");
		columnNames[20] = m_iHoMiniModel.getLanguageString("IVO");
		columnNames[21] = m_iHoMiniModel.getLanguageString("AV");
		columnNames[22] = m_iHoMiniModel.getLanguageString("AVI");
		columnNames[23] = m_iHoMiniModel.getLanguageString("AVO");
		columnNames[24] = m_iHoMiniModel.getLanguageString("AVD");
		columnNames[25] = m_iHoMiniModel.getLanguageString("MIT");
		columnNames[26] = m_iHoMiniModel.getLanguageString("MITA");
		columnNames[27] = m_iHoMiniModel.getLanguageString("MITO");
		columnNames[28] = m_iHoMiniModel.getLanguageString("MITD");
		columnNames[29] = m_iHoMiniModel.getLanguageString("FLG");
		columnNames[30] = m_iHoMiniModel.getLanguageString("FLGI");
		columnNames[31] = m_iHoMiniModel.getLanguageString("FLGO");
		columnNames[32] = m_iHoMiniModel.getLanguageString("FLGD");
		columnNames[33] = m_iHoMiniModel.getLanguageString("STU");
		columnNames[34] = m_iHoMiniModel.getLanguageString("STUD");
		columnNames[35] = m_iHoMiniModel.getLanguageString("Gehalt");
		columnNames[36] = "TSI";
		columnNames[37] = m_iHoMiniModel.getLanguageString("ID");
		
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
			data[counter][17] = new String(tmpPlayer.getPositionCompareAsString(ISpielerPosition.KEEPER));
			data[counter][18] = new String(tmpPlayer.getPositionCompareAsString(ISpielerPosition.CENTRAL_DEFENDER));
			data[counter][19] = new String(tmpPlayer.getPositionCompareAsString(ISpielerPosition.CENTRAL_DEFENDER_TOWING));
			data[counter][20] = new String(tmpPlayer.getPositionCompareAsString(ISpielerPosition.CENTRAL_DEFENDER_OFF));
			data[counter][21] = new String(tmpPlayer.getPositionCompareAsString(ISpielerPosition.BACK));
			data[counter][22] = new String(tmpPlayer.getPositionCompareAsString(ISpielerPosition.BACK_TOMID));
			data[counter][23] = new String(tmpPlayer.getPositionCompareAsString(ISpielerPosition.BACK_OFF));
			data[counter][24] = new String(tmpPlayer.getPositionCompareAsString(ISpielerPosition.BACK_DEF));
			data[counter][25] = new String(tmpPlayer.getPositionCompareAsString(ISpielerPosition.MIDFIELDER));
			data[counter][26] = new String(tmpPlayer.getPositionCompareAsString(ISpielerPosition.MIDFIELDER_TOWING));
			data[counter][27] = new String(tmpPlayer.getPositionCompareAsString(ISpielerPosition.MIDFIELDER_OFF));
			data[counter][28] = new String(tmpPlayer.getPositionCompareAsString(ISpielerPosition.MIDFIELDER_DEF));
			data[counter][29] = new String(tmpPlayer.getPositionCompareAsString(ISpielerPosition.WINGER));
			data[counter][30] = new String(tmpPlayer.getPositionCompareAsString(ISpielerPosition.WINGER_TOMID));
			data[counter][31] = new String(tmpPlayer.getPositionCompareAsString(ISpielerPosition.WINGER_OFF));
			data[counter][32] = new String(tmpPlayer.getPositionCompareAsString(ISpielerPosition.WINGER_DEF));
			data[counter][33] = new String(tmpPlayer.getPositionCompareAsString(ISpielerPosition.FORWARD));
			data[counter][34] = new String(tmpPlayer.getPositionCompareAsString(ISpielerPosition.FORWARD_DEF));
			
			data[counter][35] = new Integer((tmpPlayer.getGehalt())/10);
			data[counter][36] = new Integer(tmpPlayer.getTsi());
			data[counter][37] = new Integer(tmpPlayer.getId());
			counter++;
		}
	}

	public boolean isCellEditable(int row, int col)
	{
		/********* N�tiges Einbinden der Spalte 8 f�r Testweise Einbinden CellEditor
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
	
	public Class<?> getColumnClass(int columnIndex) 
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