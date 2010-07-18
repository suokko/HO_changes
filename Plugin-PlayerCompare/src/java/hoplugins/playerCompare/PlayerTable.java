package hoplugins.playerCompare;

import plugins.*;
import hoplugins.PlayerCompare;

//import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
//import java.awt.*;

//import plugins.*;
/**
 * @author KickMuck
 */
public class PlayerTable extends JTable{

	private IHOMiniModel m_HOModel;
	private PlayerTableModel plTableModel;
	private int anzCols;
	private IHOMiniModel m_HOModel1;
	private PlayerTableModel plTableModel1;
	private int anzCols1;
	//public PlayerTable(PlayerTableModel tm){
	public PlayerTable(TableSorter tm, IHOMiniModel iHoMiniMod, PlayerTableModel ptm,boolean small)
	{
		super(tm); 
		m_HOModel1 = iHoMiniMod;
		plTableModel1 = ptm;
		PlayerCompare.appendText("Name in PlayerTable: " + ptm.getValueAt(0,1));
		anzCols1 = tm.getColumnCount();
		int vColIndex = 0;
	    TableColumn col;
	    for(int qq = 0; qq < anzCols1; qq++)
	    {
	    	int width = 0;
	    	col = this.getColumnModel().getColumn(qq);
	    	col.setCellRenderer(new MyTableCellRenderer(m_HOModel1,plTableModel1));
	    	PlayerCompare.appendText("Werte in Schleife PlayerTable: " + ptm.getValueAt(0,qq));
	    	if(qq == 0)
	    	{
	    		width = 65;
	    	}
	    	else if(qq ==1)
	    	{
	    		width = 90;
	    	}
	    	else if(qq == 2)
	    	{
	    		width = 65;
	    	}
	    	col.setPreferredWidth(width);
	    }
	    this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
	public PlayerTable(TableSorter tm, IHOMiniModel iHoMiniMod, PlayerTableModel ptm){
		
		super(tm); 
		m_HOModel = iHoMiniMod;
		plTableModel = ptm;
		anzCols = tm.getColumnCount();
		int vColIndex = 0;
	    TableColumn col;
	
	    for(int pp = 0; pp < anzCols; pp++)
	    {
	    	int width = 0;
	    	col = this.getColumnModel().getColumn(pp);
	    	if(pp > 0)
	    	{
	    		col.setCellRenderer(new MyTableCellRenderer(m_HOModel,plTableModel));
	    		PlayerCompare.appendText("Werte in Schleife PlayerTable: " + ptm.getValueAt(0,pp));
	    	}
	    	if((ptm.getColumnName(pp)).equals(m_HOModel.getResource().getProperty("TOR"))
	    			|| (ptm.getColumnName(pp)).equals(m_HOModel.getResource().getProperty("IV"))
					|| (ptm.getColumnName(pp)).equals(m_HOModel.getResource().getProperty("IVA"))
					|| (ptm.getColumnName(pp)).equals(m_HOModel.getResource().getProperty("IVO"))
					|| (ptm.getColumnName(pp)).equals(m_HOModel.getResource().getProperty("AV"))
					|| (ptm.getColumnName(pp)).equals(m_HOModel.getResource().getProperty("AVI"))
					|| (ptm.getColumnName(pp)).equals(m_HOModel.getResource().getProperty("AVO"))
					|| (ptm.getColumnName(pp)).equals(m_HOModel.getResource().getProperty("AVD"))
					|| (ptm.getColumnName(pp)).equals(m_HOModel.getResource().getProperty("MIT"))
					|| (ptm.getColumnName(pp)).equals(m_HOModel.getResource().getProperty("MITD"))
					|| (ptm.getColumnName(pp)).equals(m_HOModel.getResource().getProperty("MITA"))
					|| (ptm.getColumnName(pp)).equals(m_HOModel.getResource().getProperty("MITO"))
					|| (ptm.getColumnName(pp)).equals(m_HOModel.getResource().getProperty("FLG"))
					|| (ptm.getColumnName(pp)).equals(m_HOModel.getResource().getProperty("FLGI"))
					|| (ptm.getColumnName(pp)).equals(m_HOModel.getResource().getProperty("FLGO"))
					|| (ptm.getColumnName(pp)).equals(m_HOModel.getResource().getProperty("FLGD"))
					|| (ptm.getColumnName(pp)).equals(m_HOModel.getResource().getProperty("STU"))
					|| (ptm.getColumnName(pp)).equals(m_HOModel.getResource().getProperty("STUD"))
					|| (ptm.getColumnName(pp)).equals(m_HOModel.getResource().getProperty("Gruppe"))
	    	)
	    	{
	    		width = 60;
	    	}
	    	else if((ptm.getColumnName(pp)).equals(m_HOModel.getResource().getProperty("Name"))
	    			|| (ptm.getColumnName(pp)).equals(m_HOModel.getResource().getProperty("BestePosition"))
	    			)
	    	{
	    		width = 175;
	    	}
	    	else if((ptm.getColumnName(pp)).equals(m_HOModel.getResource().getProperty("FUE"))
	    			|| (ptm.getColumnName(pp)).equals(m_HOModel.getResource().getProperty("ER"))
					|| (ptm.getColumnName(pp)).equals(m_HOModel.getResource().getProperty("FO"))
					|| (ptm.getColumnName(pp)).equals(m_HOModel.getResource().getProperty("KO"))
					|| (ptm.getColumnName(pp)).equals(m_HOModel.getResource().getProperty("TW"))
					|| (ptm.getColumnName(pp)).equals(m_HOModel.getResource().getProperty("VE"))
					|| (ptm.getColumnName(pp)).equals(m_HOModel.getResource().getProperty("SA"))
					|| (ptm.getColumnName(pp)).equals(m_HOModel.getResource().getProperty("PS"))
					|| (ptm.getColumnName(pp)).equals(m_HOModel.getResource().getProperty("FL"))
					|| (ptm.getColumnName(pp)).equals(m_HOModel.getResource().getProperty("TS"))
					|| (ptm.getColumnName(pp)).equals(m_HOModel.getResource().getProperty("ST"))
					)
	    	{
	    		width = 40;
	    	}
	    	else if((ptm.getColumnName(pp)).equals(m_HOModel.getResource().getProperty("Gehalt"))
	    			|| (ptm.getColumnName(pp)).equals(m_HOModel.getResource().getProperty("ID"))
					|| (ptm.getColumnName(pp)).equals("TSI")
					)
	    	{
	    		width = 80;
	    	}
	    	else
	    	{
	    		width = 30;
	    	}
	    	col.setPreferredWidth(width);
	    }
	    
	    this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
}
