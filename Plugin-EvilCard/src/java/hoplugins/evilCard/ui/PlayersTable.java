// %1097770271:hoplugins.evilCard.ui%
package hoplugins.evilCard.ui;

import hoplugins.commons.utils.PluginProperty;

import hoplugins.evilCard.ui.model.PlayersTableModel;
import hoplugins.evilCard.ui.renderer.PlayersTableCellRenderer;

import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.JTableHeader;


/**
 * DOCUMENT ME!
 *
 * @author pino  TODO To change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class PlayersTable extends JTable {
    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    protected String[] columnToolTips = null;
    private PlayersTableModel playersTableModel = null;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new MainTable object.
     */
    public PlayersTable() {
        super();

        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        columnToolTips = new String[PlayersTableModel.cols];
        columnToolTips[PlayersTableModel.COL_WARNINGS] = PluginProperty.getString("tooltip.Warnings");
        columnToolTips[PlayersTableModel.COL_WARNINGS_TYPE1] = PluginProperty.getString("tooltip.WarningType1");
        columnToolTips[PlayersTableModel.COL_WARNINGS_TYPE2] = PluginProperty.getString("tooltip.WarningType2");
        columnToolTips[PlayersTableModel.COL_WARNINGS_TYPE3] = PluginProperty.getString("tooltip.WarningType3");
        columnToolTips[PlayersTableModel.COL_WARNINGS_TYPE4] = PluginProperty.getString("tooltip.WarningType4");
        columnToolTips[PlayersTableModel.COL_DIRECT_RED_CARDS] = PluginProperty.getString("tooltip.RedCards");
        columnToolTips[PlayersTableModel.COL_CARDS] = PluginProperty.getString("tooltip.CardsTotal");
        columnToolTips[PlayersTableModel.COL_MATCHES] = PluginProperty.getString("tooltip.MatchCount");
        columnToolTips[PlayersTableModel.COL_RAW_AVERAGE] = PluginProperty.getString("tooltip.RawAverage");
        columnToolTips[PlayersTableModel.COL_WEIGHTED_AVERAGE] = PluginProperty.getString("tooltip.WeightedAverage");

        playersTableModel = new PlayersTableModel(0, 0, 0, PlayersTableModel.TYPE_CURRENT_PLAYERS);

        TableSorter sorter = new TableSorter(playersTableModel);
        this.setModel(sorter);
        sorter.setTableHeader(this.getTableHeader());

        this.getColumnModel().getColumn(PlayersTableModel.COL_ID).setPreferredWidth(70);
        this.getColumnModel().getColumn(PlayersTableModel.COL_NAME).setPreferredWidth(120);
        this.getColumnModel().getColumn(PlayersTableModel.COL_AGGRESSIVITY).setPreferredWidth(120);
        this.getColumnModel().getColumn(PlayersTableModel.COL_HONESTY).setPreferredWidth(120);
        this.getColumnModel().getColumn(PlayersTableModel.COL_AGREEABILITY).setPreferredWidth(120);
        this.getColumnModel().getColumn(PlayersTableModel.COL_DIRECT_RED_CARDS).setPreferredWidth(20);
        this.getColumnModel().getColumn(PlayersTableModel.COL_CARDS).setPreferredWidth(20);
        this.getColumnModel().getColumn(PlayersTableModel.COL_WARNINGS).setPreferredWidth(20);
        this.getColumnModel().getColumn(PlayersTableModel.COL_WARNINGS_TYPE1).setPreferredWidth(20);
        this.getColumnModel().getColumn(PlayersTableModel.COL_WARNINGS_TYPE2).setPreferredWidth(20);
        this.getColumnModel().getColumn(PlayersTableModel.COL_WARNINGS_TYPE3).setPreferredWidth(20);
        this.getColumnModel().getColumn(PlayersTableModel.COL_WARNINGS_TYPE4).setPreferredWidth(20);
        this.getColumnModel().getColumn(PlayersTableModel.COL_RAW_AVERAGE).setPreferredWidth(50);
        this.getColumnModel().getColumn(PlayersTableModel.COL_WEIGHTED_AVERAGE).setPreferredWidth(50);
        this.getColumnModel().getColumn(PlayersTableModel.COL_MATCHES).setPreferredWidth(35);

        this.setDefaultRenderer(String.class, new PlayersTableCellRenderer());
        this.setDefaultRenderer(Integer.class, new PlayersTableCellRenderer());
        this.setDefaultRenderer(Double.class, new PlayersTableCellRenderer());
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param filterMode TODO Missing Method Parameter Documentation
     */
    public void setFilter(int filterMode) {
        playersTableModel.refresh(filterMode);
    }

    /**
     * TODO Missing Method Documentation
     */
    public void prova() {
    }

    //Implement table header tool tips.
    protected JTableHeader createDefaultTableHeader() {
        return new JTableHeader(columnModel) {
                public String getToolTipText(MouseEvent e) {
                    String tip = null;
                    java.awt.Point p = e.getPoint();
                    int index = columnModel.getColumnIndexAtX(p.x);
                    int realIndex = columnModel.getColumn(index).getModelIndex();
                    return columnToolTips[realIndex];
                }
            };
    }
}
