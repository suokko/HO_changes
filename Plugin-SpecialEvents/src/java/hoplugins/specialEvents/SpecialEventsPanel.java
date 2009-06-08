package hoplugins.specialEvents;

import hoplugins.commons.utils.PluginProperty;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import plugins.IDebugWindow;
import plugins.IHOMiniModel;

public class SpecialEventsPanel extends JTable
{

	private static final long serialVersionUID = 8656004206333977669L;
	
	public static final int MATCHDATECOLUMN  = 0;
    public static final int MATCHIDCOLUMN    = 1;
    public static final int HOMETACTICCOLUMN = 2;
    public static final int HOMEEVENTCOLUMN  = 3;
    public static final int HOMETEAMCOLUMN   = 4;
    public static final int RESULTCOLUMN     = 5;
    public static final int AWAYTEAMCOLUMN   = 6;
    public static final int AWAYEVENTCOLUMN = 7;
    public static final int AWAYTACTICCOLUMN = 8;
    public static final int MINUTECOLUMN     = 9;
    public static final int CHANCECOLUMN     = 10;
    public static final int EVENTTYPCOLUMN   = 11;
    public static final int SETEXTCOLUMN     = 12;
    public static final int NAMECOLUMN       = 13;
    public static final int HIDDENCOLUMN     = 14;
    public static final int NUMCOLUMNS       = 15;
    
//    private Properties props;
    private IHOMiniModel miniModel;
    private String columnNames[];
    private Vector highlightTexte;

    public SpecialEventsPanel(IHOMiniModel miniModel)
    {
        columnNames = new String[NUMCOLUMNS];
//        this.props = props;
        this.miniModel = miniModel;
        setColumnHeaders();
        setAutoResizeMode(0);
        getTableHeader().setReorderingAllowed(false);
        try
        {
            setDefaultRenderer(java.lang.Object.class, new SpecialEventsTableRenderer());
            TableModel table = getSEModel(miniModel);
            setModel(table);
        }
        catch(RuntimeException e)
        {
            showDebug(e);
        }
    }

    private void setColumnHeaders()
    {
        columnNames[MATCHDATECOLUMN] = PluginProperty.getString("Datum");
        columnNames[MATCHIDCOLUMN] = PluginProperty.getString("MatchId");
        columnNames[HOMETACTICCOLUMN] = PluginProperty.getString("Taktik");
        columnNames[HOMEEVENTCOLUMN] = "";
        columnNames[HOMETEAMCOLUMN] = PluginProperty.getString("Heim");
        columnNames[RESULTCOLUMN] = "";
        columnNames[AWAYTEAMCOLUMN] = PluginProperty.getString("Gast");
        columnNames[AWAYEVENTCOLUMN] = "";
        columnNames[AWAYTACTICCOLUMN] = PluginProperty.getString("Taktik");
        columnNames[MINUTECOLUMN] = PluginProperty.getString("Min");
        columnNames[CHANCECOLUMN] = "";
        columnNames[EVENTTYPCOLUMN] = "";
        columnNames[SETEXTCOLUMN] = PluginProperty.getString("Event");
        columnNames[NAMECOLUMN] = PluginProperty.getString("Spieler");
        columnNames[HIDDENCOLUMN] = "";
    }

    protected JTableHeader createDefaultTableHeader()
    {
        return new JTableHeader(columnModel) {

            public String getToolTipText(MouseEvent e)
            {
                String tip = null;
                Point p = e.getPoint();
                int index = columnModel.getColumnIndexAtX(p.x);
                int realIndex = columnModel.getColumn(index).getModelIndex();
                if(realIndex == HOMEEVENTCOLUMN || realIndex == AWAYEVENTCOLUMN)
                {
                    tip = PluginProperty.getString("Tip4");
                }
                return tip;
            }

        };
    }

    public TableModel getSEModel(IHOMiniModel miniModel)
    {
        SpecialEventsDM specialEventsDM = new SpecialEventsDM(miniModel);
        Vector matches = specialEventsDM.holeInfos(FilterPanel.getGameTypAll().isSelected(), FilterPanel.getSaisonTyp(), FilterPanel.showFriendlies());
        highlightTexte = specialEventsDM.getHighlightText();
        TableModel tableModel = new SpecialEventsTableModel(matches, new Vector(Arrays.asList(columnNames)));
        return tableModel;
    }

    public String getToolTipText(MouseEvent e)
    {
        String tip = null;
        Point p = e.getPoint();
        int rowIndex = rowAtPoint(p);
        int colIndex = columnAtPoint(p);
        int realColumnIndex = convertColumnIndexToModel(colIndex);
        if(realColumnIndex == HOMEEVENTCOLUMN || realColumnIndex == AWAYEVENTCOLUMN)
        {
            tip = PluginProperty.getString("Tip4");
        }
        if(realColumnIndex == NAMECOLUMN)
        {
            tip = PluginProperty.getString("TipName");
        }
        if(realColumnIndex == EVENTTYPCOLUMN)
        {
            String highlightText = "<table width='300'><tr><td>" + (String)highlightTexte.elementAt(rowIndex) + "</td></tr></table>";
            String text = "<html>" + highlightText + "</html>";
            tip = text;
        }
        return tip;
    }

    public void setTableModel(TableModel tm)
    {
        setModel(tm);
        setAutoscrolls(true);
        setTableSize();
    }

    private void setTableSize()
    {
        colomnWidth(MATCHDATECOLUMN, 64, 34);
        colomnWidth(MATCHIDCOLUMN, 64, 34);
        colomnWidth(HOMETACTICCOLUMN, 32, 22);
        colomnWidth(HOMEEVENTCOLUMN, 20, 20);
        colomnWidth(HOMETEAMCOLUMN, 150, 100);
        colomnWidth(RESULTCOLUMN, 40, 20);
        colomnWidth(AWAYTEAMCOLUMN, 150, 100);
        colomnWidth(AWAYEVENTCOLUMN, 20, 20);
        colomnWidth(AWAYTACTICCOLUMN, 32, 22);
        colomnWidth(MINUTECOLUMN, 25, 25);
        colomnWidth(CHANCECOLUMN, 20, 20);
        colomnWidth(EVENTTYPCOLUMN, 20, 20);
        colomnWidth(SETEXTCOLUMN, 270, 140);
        colomnWidth(NAMECOLUMN, 200, 200);
        colomnWidth(HIDDENCOLUMN, 0, 0);
        setRowHeight(20);
    }

    private void colomnWidth(int col, int width, int minWidth)
    {
        getColumnModel().getColumn(col).setMaxWidth(width);
        getColumnModel().getColumn(col).setMinWidth(minWidth);
        getColumnModel().getColumn(col).setWidth(width);
        getColumnModel().getColumn(col).setPreferredWidth(width);
    }

    public boolean isCellEditable(int rowIndex, int mColIndex)
    {
        return false;
    }

    private void showDebug(Exception exr)
    {
//    	exr.printStackTrace();
        IDebugWindow debugWindow = miniModel.getGUI().createDebugWindow(new Point(100, 200), new Dimension(700, 400));
        debugWindow.setVisible(true);
        debugWindow.append(exr);
    }

    private void showDebugString(String s)
    {
        IDebugWindow debugWindow = miniModel.getGUI().createDebugWindow(new Point(100, 200), new Dimension(700, 400));
        debugWindow.setVisible(true);
        debugWindow.append(s);
    }

}
