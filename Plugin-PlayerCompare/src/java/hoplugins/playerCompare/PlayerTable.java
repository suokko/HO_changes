package hoplugins.playerCompare;

import gui.HOColorName;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import plugins.IHOMiniModel;

/**
 * @author KickMuck
 */
public class PlayerTable extends JTable{

	private static final long serialVersionUID = 1453037819569111763L;
	private int anzCols1;
	
	public PlayerTable(TableSorter tm, final IHOMiniModel iHoMiniMod)
	{
		super(tm); 
		anzCols1 = tm.getColumnCount();
	    TableColumn col;
	    setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value,
					boolean isSelected, boolean hasFocus, int row, int column) {

				setText(value.toString());
				setHorizontalAlignment(CENTER);
				if(isSelected){
					setBackground(table.getSelectionBackground());
					setForeground(table.getSelectionForeground());
				} else {
					setBackground(column==1?table.getBackground():iHoMiniMod.getGUI().getColor(HOColorName.PLAYER_SUBPOS_BG));
					setForeground(table.getForeground());
				}
				return this;
			}
		});
	    setBackground(iHoMiniMod.getGUI().getColor(HOColorName.PLAYER_POS_BG));
	    setForeground(iHoMiniMod.getGUI().getColor(HOColorName.TABLEENTRY_FG));
	    setSelectionBackground(iHoMiniMod.getGUI().getColor(HOColorName.TABLE_SELECTION_BG));
	    setSelectionForeground(iHoMiniMod.getGUI().getColor(HOColorName.TABLE_SELECTION_FG));
	    for(int qq = 0; qq < anzCols1; qq++)
	    {
	    	int width = 65;
	    	col = this.getColumnModel().getColumn(qq);
	    	//col.setCellRenderer(new MyTableCellRenderer(iHoMiniMod));
	    	if(qq ==1)
	    		width = 90;
	    	col.setPreferredWidth(width);
	    }
	    this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
	
	
	public PlayerTable(TableSorter tm, IHOMiniModel iHoMiniMod, PlayerTableModel ptm){
		
		super(tm); 
		anzCols1 = tm.getColumnCount();
	    TableColumn col;
	    setBackground(iHoMiniMod.getGUI().getColor(HOColorName.TABLEENTRY_BG));
	    setForeground(iHoMiniMod.getGUI().getColor(HOColorName.TABLEENTRY_FG));
	    setSelectionBackground(iHoMiniMod.getGUI().getColor(HOColorName.TABLE_SELECTION_BG));
	    setSelectionForeground(iHoMiniMod.getGUI().getColor(HOColorName.TABLE_SELECTION_FG));
	    for(int pp = 0; pp < anzCols1; pp++)
	    {
	    	int width = 0;
	    	String columnName = ptm.getColumnName(pp);
	    	col = this.getColumnModel().getColumn(pp);
	    	if(pp > 0)
	    	{
	    		col.setCellRenderer(new MyTableCellRenderer(iHoMiniMod));
	    	}
	    	if(columnName.equals(iHoMiniMod.getLanguageString("TOR"))
	    			|| columnName.equals(iHoMiniMod.getLanguageString("IV"))
					|| columnName.equals(iHoMiniMod.getLanguageString("IVA"))
					|| columnName.equals(iHoMiniMod.getLanguageString("IVO"))
					|| columnName.equals(iHoMiniMod.getLanguageString("AV"))
					|| columnName.equals(iHoMiniMod.getLanguageString("AVI"))
					|| columnName.equals(iHoMiniMod.getLanguageString("AVO"))
					|| columnName.equals(iHoMiniMod.getLanguageString("AVD"))
					|| columnName.equals(iHoMiniMod.getLanguageString("MIT"))
					|| columnName.equals(iHoMiniMod.getLanguageString("MITD"))
					|| columnName.equals(iHoMiniMod.getLanguageString("MITA"))
					|| columnName.equals(iHoMiniMod.getLanguageString("MITO"))
					|| columnName.equals(iHoMiniMod.getLanguageString("FLG"))
					|| columnName.equals(iHoMiniMod.getLanguageString("FLGI"))
					|| columnName.equals(iHoMiniMod.getLanguageString("FLGO"))
					|| columnName.equals(iHoMiniMod.getLanguageString("FLGD"))
					|| columnName.equals(iHoMiniMod.getLanguageString("STU"))
					|| columnName.equals(iHoMiniMod.getLanguageString("STUD"))
					|| columnName.equals(iHoMiniMod.getLanguageString("STUA"))
					|| columnName.equals(iHoMiniMod.getLanguageString("Gruppe"))
	    	)
	    	{
	    		width = 60;
	    	}
	    	else if(columnName.equals(iHoMiniMod.getLanguageString("Name"))
	    			|| columnName.equals(iHoMiniMod.getLanguageString("BestePosition"))
	    			)
	    	{
	    		width = 175;
	    	}
	    	else if(columnName.equals(iHoMiniMod.getLanguageString("FUE"))
	    			|| columnName.equals(iHoMiniMod.getLanguageString("ER"))
					|| columnName.equals(iHoMiniMod.getLanguageString("FO"))
					|| columnName.equals(iHoMiniMod.getLanguageString("KO"))
					|| columnName.equals(iHoMiniMod.getLanguageString("TW"))
					|| columnName.equals(iHoMiniMod.getLanguageString("VE"))
					|| columnName.equals(iHoMiniMod.getLanguageString("SA"))
					|| columnName.equals(iHoMiniMod.getLanguageString("PS"))
					|| columnName.equals(iHoMiniMod.getLanguageString("FL"))
					|| columnName.equals(iHoMiniMod.getLanguageString("TS"))
					|| columnName.equals(iHoMiniMod.getLanguageString("ST"))
					|| columnName.equals(iHoMiniMod.getLanguageString("LOY"))
					|| columnName.equals(iHoMiniMod.getLanguageString("MC"))
					)
	    	{
	    		width = 40;
	    	}
	    	else if(columnName.equals(iHoMiniMod.getLanguageString("Gehalt"))
	    			|| columnName.equals(iHoMiniMod.getLanguageString("ID"))
					|| columnName.equals("TSI")
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
