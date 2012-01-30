package ho.module.playerCompare;

import gui.HOColorName;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import de.hattrickorganizer.gui.theme.ThemeManager;
import de.hattrickorganizer.model.HOVerwaltung;

/**
 * @author KickMuck
 */
class PlayerTable extends JTable{

	private static final long serialVersionUID = 1453037819569111763L;
	private int anzCols1;
	
	PlayerTable(TableSorter tm)
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
					
					setBackground(column==1?table.getBackground():ThemeManager.getColor(HOColorName.PLAYER_SUBPOS_BG));
					setForeground(table.getForeground());
				}
				return this;
			}
		});
	    setBackground(ThemeManager.getColor(HOColorName.PLAYER_POS_BG));
	    setForeground(ThemeManager.getColor(HOColorName.TABLEENTRY_FG));
	    setSelectionBackground(ThemeManager.getColor(HOColorName.TABLE_SELECTION_BG));
	    setSelectionForeground(ThemeManager.getColor(HOColorName.TABLE_SELECTION_FG));
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
	
	
	PlayerTable(TableSorter tm, PlayerTableModel ptm){
		
		super(tm); 
		anzCols1 = tm.getColumnCount();
	    TableColumn col;
	    setBackground(ThemeManager.getColor(HOColorName.TABLEENTRY_BG));
	    setForeground(ThemeManager.getColor(HOColorName.TABLEENTRY_FG));
	    setSelectionBackground(ThemeManager.getColor(HOColorName.TABLE_SELECTION_BG));
	    setSelectionForeground(ThemeManager.getColor(HOColorName.TABLE_SELECTION_FG));
	    for(int pp = 0; pp < anzCols1; pp++)
	    {
	    	int width = 0;
	    	String columnName = ptm.getColumnName(pp);
	    	col = this.getColumnModel().getColumn(pp);
	    	if(pp > 0)
	    	{
	    		col.setCellRenderer(new MyTableCellRenderer());
	    	}
	    	if(columnName.equals(HOVerwaltung.instance().getLanguageString("TOR"))
	    			|| columnName.equals(HOVerwaltung.instance().getLanguageString("IV"))
					|| columnName.equals(HOVerwaltung.instance().getLanguageString("IVA"))
					|| columnName.equals(HOVerwaltung.instance().getLanguageString("IVO"))
					|| columnName.equals(HOVerwaltung.instance().getLanguageString("AV"))
					|| columnName.equals(HOVerwaltung.instance().getLanguageString("AVI"))
					|| columnName.equals(HOVerwaltung.instance().getLanguageString("AVO"))
					|| columnName.equals(HOVerwaltung.instance().getLanguageString("AVD"))
					|| columnName.equals(HOVerwaltung.instance().getLanguageString("MIT"))
					|| columnName.equals(HOVerwaltung.instance().getLanguageString("MITD"))
					|| columnName.equals(HOVerwaltung.instance().getLanguageString("MITA"))
					|| columnName.equals(HOVerwaltung.instance().getLanguageString("MITO"))
					|| columnName.equals(HOVerwaltung.instance().getLanguageString("FLG"))
					|| columnName.equals(HOVerwaltung.instance().getLanguageString("FLGI"))
					|| columnName.equals(HOVerwaltung.instance().getLanguageString("FLGO"))
					|| columnName.equals(HOVerwaltung.instance().getLanguageString("FLGD"))
					|| columnName.equals(HOVerwaltung.instance().getLanguageString("STU"))
					|| columnName.equals(HOVerwaltung.instance().getLanguageString("STUD"))
					|| columnName.equals(HOVerwaltung.instance().getLanguageString("STUA"))
					|| columnName.equals(HOVerwaltung.instance().getLanguageString("Gruppe"))
	    	)
	    	{
	    		width = 60;
	    	}
	    	else if(columnName.equals(HOVerwaltung.instance().getLanguageString("Name"))
	    			|| columnName.equals(HOVerwaltung.instance().getLanguageString("BestePosition"))
	    			)
	    	{
	    		width = 175;
	    	}
	    	else if(columnName.equals(HOVerwaltung.instance().getLanguageString("FUE"))
	    			|| columnName.equals(HOVerwaltung.instance().getLanguageString("ER"))
					|| columnName.equals(HOVerwaltung.instance().getLanguageString("FO"))
					|| columnName.equals(HOVerwaltung.instance().getLanguageString("KO"))
					|| columnName.equals(HOVerwaltung.instance().getLanguageString("TW"))
					|| columnName.equals(HOVerwaltung.instance().getLanguageString("VE"))
					|| columnName.equals(HOVerwaltung.instance().getLanguageString("SA"))
					|| columnName.equals(HOVerwaltung.instance().getLanguageString("PS"))
					|| columnName.equals(HOVerwaltung.instance().getLanguageString("FL"))
					|| columnName.equals(HOVerwaltung.instance().getLanguageString("TS"))
					|| columnName.equals(HOVerwaltung.instance().getLanguageString("ST"))
					|| columnName.equals(HOVerwaltung.instance().getLanguageString("LOY"))
					|| columnName.equals(HOVerwaltung.instance().getLanguageString("MC"))
					)
	    	{
	    		width = 40;
	    	}
	    	else if(columnName.equals(HOVerwaltung.instance().getLanguageString("Gehalt"))
	    			|| columnName.equals(HOVerwaltung.instance().getLanguageString("ID"))
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
