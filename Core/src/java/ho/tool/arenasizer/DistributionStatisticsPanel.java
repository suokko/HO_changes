package ho.tool.arenasizer;

import gui.HOColorName;
import gui.HOIconName;

import java.awt.BorderLayout;
import java.math.BigDecimal;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumnModel;

import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.gui.model.SpielerTableRenderer;
import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.gui.templates.DoppelLabelEntry;
import de.hattrickorganizer.gui.templates.TableEntry;
import de.hattrickorganizer.gui.theme.ThemeManager;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.matches.Matchdetails;
import de.hattrickorganizer.tools.Helper;
import de.hattrickorganizer.tools.updater.TableModel;

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
		table.setDefaultRenderer(java.lang.Object.class, new SpielerTableRenderer());
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
					hoV.getLanguageString("Sitzplaetze")+" ( %)",hoV.getLanguageString("Ueberdachteplaetze")+" ( %)",hoV.getLanguageString("Logen")+" ( %)"};

		int arenaId = HOVerwaltung.instance().getModel().getStadium().getArenaId();
		Matchdetails[] details = DBZugriff.instance().getMatchDetailsFromArenaId(arenaId);
		TableEntry[][] value = new TableEntry[details.length][columnNames.length];
        for (int i = 0; i < details.length; i++) {
        	 value[i][0] = new ColorLabelEntry(details[i].getMatchID()+"",
                     ColorLabelEntry.FG_STANDARD,  ColorLabelEntry.BG_STANDARD, SwingConstants.RIGHT);
           
            value[i][1] = new ColorLabelEntry(ThemeManager.getIcon(HOIconName.WEATHER[details[i].getWetterId()]),0,ColorLabelEntry.FG_STANDARD,  ColorLabelEntry.BG_STANDARD, SwingConstants.CENTER);
            value[i][2] = new ColorLabelEntry(details[i].getZuschauer()+"",
                    ColorLabelEntry.FG_STANDARD,  ColorLabelEntry.BG_STANDARD, SwingConstants.RIGHT);
            BigDecimal tmp = new BigDecimal(details[i].getZuschauer()).setScale(1);
           
            value[i][3] = createDoppelLabelEntry(details[i].getSoldTerraces(), new BigDecimal(details[i].getSoldTerraces()*100).setScale(1).divide(tmp,BigDecimal.ROUND_HALF_DOWN).toString());
            value[i][4] = createDoppelLabelEntry(details[i].getSoldBasic(),new BigDecimal(details[i].getSoldBasic()*100).setScale(1).divide(tmp,BigDecimal.ROUND_HALF_DOWN).toString());
            value[i][5] = createDoppelLabelEntry(details[i].getSoldRoof(),new BigDecimal(details[i].getSoldRoof()*100).setScale(1).divide(tmp,BigDecimal.ROUND_HALF_DOWN).toString());
            value[i][6] = createDoppelLabelEntry(details[i].getSoldVIP(),new BigDecimal(details[i].getSoldVIP()*100).setScale(1).divide(tmp,BigDecimal.ROUND_HALF_DOWN).toString());
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
	
	private int getPercent(int spectators, int part){
		return part*100/spectators;
		
	}
}
