package ho.tool.arenasizer;

import ho.core.db.DBManager;
import ho.core.gui.comp.entry.ColorLabelEntry;
import ho.core.gui.comp.entry.DoppelLabelEntry;
import ho.core.gui.comp.renderer.HODefaultTableCellRenderer;
import ho.core.gui.model.ArenaStatistikModel;
import ho.core.gui.theme.HOColorName;
import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.HOVerwaltung;
import ho.core.util.Helper;
import ho.tool.updater.TableModel;

import java.awt.BorderLayout;
import java.math.BigDecimal;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumnModel;

import plugins.IHOTableEntry;
import plugins.ISpielePanel;

class DistributionStatisticsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public DistributionStatisticsPanel() {
		initialize();
	}

	private void initialize() {
		setLayout(new BorderLayout());
		add(createTable(), BorderLayout.CENTER);
		
	}

	protected JScrollPane createTable() {
		JTable table = new JTable(getModel());
		//table.setDefaultRenderer(Object.class, new UpdaterCellRenderer());
		table.setDefaultRenderer(java.lang.Object.class, new HODefaultTableCellRenderer());
		table.getTableHeader().setReorderingAllowed(false);
		
		final TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setMinWidth(Helper.calcCellWidth(50));
        columnModel.getColumn(1).setMinWidth(Helper.calcCellWidth(50));
		
		JScrollPane scroll = new JScrollPane(table);
		return scroll;
	}
	
	protected TableModel getModel() {
		HOVerwaltung hoV = HOVerwaltung.instance();
		// DATUM SpielerRenderer
		String[] columnNames = {hoV.getLanguageString("ID"),hoV.getLanguageString("Wetter"),hoV.getLanguageString("Zuschauer"),hoV.getLanguageString("Stehplaetze")+" ( %)",
					hoV.getLanguageString("Sitzplaetze")+" ( %)",hoV.getLanguageString("Ueberdachteplaetze")+" ( %)",hoV.getLanguageString("Logen")+" ( %)",
					hoV.getLanguageString("Fans")+" ( )"};

		ArenaStatistikModel[] matches=  DBManager.instance().getArenaStatistikModel(ISpielePanel.NUR_EIGENE_LIGASPIELE).getMatches();
		IHOTableEntry[][] value = new IHOTableEntry[matches.length][columnNames.length];
        for (int i = 0; i < matches.length; i++) {
        	 value[i][0] = new ColorLabelEntry(matches[i].getMatchID()+"",
                     ColorLabelEntry.FG_STANDARD,  ColorLabelEntry.BG_STANDARD, SwingConstants.RIGHT);
           
            value[i][1] = new ColorLabelEntry(ThemeManager.getIcon(HOIconName.WEATHER[matches[i].getWetter()]),0,ColorLabelEntry.FG_STANDARD,  ColorLabelEntry.BG_STANDARD, SwingConstants.CENTER);
            value[i][2] = new ColorLabelEntry(matches[i].getZuschaueranzahl()+"",
                    ColorLabelEntry.FG_STANDARD,  ColorLabelEntry.BG_STANDARD, SwingConstants.RIGHT);
            BigDecimal tmp = new BigDecimal(matches[i].getZuschaueranzahl()).setScale(1);
           
            value[i][3] = createDoppelLabelEntry(matches[i].getSoldTerraces(), new BigDecimal(matches[i].getSoldTerraces()*100).setScale(1).divide(tmp,BigDecimal.ROUND_HALF_DOWN).toString());
            value[i][4] = createDoppelLabelEntry(matches[i].getSoldBasics(),new BigDecimal(matches[i].getSoldBasics()*100).setScale(1).divide(tmp,BigDecimal.ROUND_HALF_DOWN).toString());
            value[i][5] = createDoppelLabelEntry(matches[i].getSoldRoof(),new BigDecimal(matches[i].getSoldRoof()*100).setScale(1).divide(tmp,BigDecimal.ROUND_HALF_DOWN).toString());
            value[i][6] = createDoppelLabelEntry(matches[i].getSoldVip(),new BigDecimal(matches[i].getSoldVip()*100).setScale(1).divide(tmp,BigDecimal.ROUND_HALF_DOWN).toString());
            value[i][7] = createFansDoppelLabelEntry(matches[i].getFans(),tmp.divide(new BigDecimal(matches[i].getFans()),BigDecimal.ROUND_HALF_DOWN).setScale(1).toString());
        }

        TableModel model = new TableModel(value, columnNames);
        
        return model;
    }
	
	private DoppelLabelEntry createDoppelLabelEntry(int leftValue, String rightValue){
    	return new DoppelLabelEntry(new ColorLabelEntry(leftValue+"",
                						ColorLabelEntry.FG_STANDARD,
                						ColorLabelEntry.BG_STANDARD, SwingConstants.RIGHT),
                	               new ColorLabelEntry(rightValue+" %",
                	            		   ThemeManager.getColor(HOColorName.PLAYER_OLD_FG),
                	            		   ColorLabelEntry.BG_STANDARD, SwingConstants.RIGHT));
    }
	
	private DoppelLabelEntry createFansDoppelLabelEntry(int leftValue, String rightValue){
    	return new DoppelLabelEntry(new ColorLabelEntry(leftValue+"",
                						ColorLabelEntry.FG_STANDARD,
                						ColorLabelEntry.BG_STANDARD, SwingConstants.RIGHT),
                	               new ColorLabelEntry(rightValue+"",
                	            		   ThemeManager.getColor(HOColorName.PLAYER_OLD_FG),
                	            		   ColorLabelEntry.BG_STANDARD, SwingConstants.RIGHT));
    }
	
}
