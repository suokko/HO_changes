// %635214327:hoplugins.evilCard.ui%
/*
 * Created on 25-feb-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package hoplugins.evilCard.ui;

import hoplugins.commons.utils.PluginProperty;

import hoplugins.evilCard.ui.model.DetailsTableModel;
import hoplugins.evilCard.ui.renderer.DetailsTableCellRenderer;
import hoplugins.evilCard.ui.renderer.TextAreaRenderer;

import java.awt.Dimension;
import java.awt.event.MouseEvent;

import javax.swing.JEditorPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;


/**
 * DOCUMENT ME!
 *
 * @author pino  TODO To change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DetailsTable extends JTable {
    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    protected String[] columnToolTips = null;

    /** TODO Missing Parameter Documentation */
    DetailsTableModel detailsTableModel = null;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new DetailTable object.
     */
    public DetailsTable() {
        super();

        //this.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0)));
        // TIPS
        columnToolTips = new String[DetailsTableModel.cols];
        columnToolTips[DetailsTableModel.COL_MATCH_ID] = PluginProperty.getString("ID");
        columnToolTips[DetailsTableModel.COL_MATCH_HOME] = PluginProperty.getString("Heim");
        columnToolTips[DetailsTableModel.COL_MATCH_GUEST] = PluginProperty.getString("Gast");
        columnToolTips[DetailsTableModel.COL_MATCH_RESULT] = PluginProperty.getString("Ergebnis");
        columnToolTips[DetailsTableModel.COL_EVENT] = PluginProperty.getString("tooltip.Event");
        columnToolTips[DetailsTableModel.COL_WARNINGS_TYPE1] = PluginProperty.getString("tooltip.WarningType1");
        columnToolTips[DetailsTableModel.COL_WARNINGS_TYPE2] = PluginProperty.getString("tooltip.WarningType2");
        columnToolTips[DetailsTableModel.COL_WARNINGS_TYPE3] = PluginProperty.getString("tooltip.WarningType3");
        columnToolTips[DetailsTableModel.COL_WARNINGS_TYPE4] = PluginProperty.getString("tooltip.WarningType4");
        columnToolTips[DetailsTableModel.COL_DIRECT_RED_CARDS] = PluginProperty.getString("tooltip.RedCards");

        detailsTableModel = new DetailsTableModel(0);
        this.setModel(new TableSorter(detailsTableModel));

        //      m_jtDettagli.getColumnModel().getColumn(5).setMaxWidth(5);
        getColumnModel().getColumn(DetailsTableModel.COL_WARNINGS_TYPE1).setPreferredWidth(20);
        getColumnModel().getColumn(DetailsTableModel.COL_WARNINGS_TYPE2).setPreferredWidth(20);
        getColumnModel().getColumn(DetailsTableModel.COL_WARNINGS_TYPE3).setPreferredWidth(20);
        getColumnModel().getColumn(DetailsTableModel.COL_WARNINGS_TYPE4).setPreferredWidth(20);
        getColumnModel().getColumn(DetailsTableModel.COL_DIRECT_RED_CARDS).setPreferredWidth(20);

        getColumnModel().getColumn(DetailsTableModel.COL_MATCH_ID).setPreferredWidth(70);
        getColumnModel().getColumn(DetailsTableModel.COL_MATCH_HOME).setPreferredWidth(120);
        getColumnModel().getColumn(DetailsTableModel.COL_MATCH_GUEST).setPreferredWidth(120);
        getColumnModel().getColumn(DetailsTableModel.COL_MATCH_RESULT).setPreferredWidth(50);
        getColumnModel().getColumn(DetailsTableModel.COL_EVENT).setPreferredWidth(500);

        this.setDefaultRenderer(Object.class, new DetailsTableCellRenderer());
        this.getColumnModel().getColumn(DetailsTableModel.COL_EVENT).setCellRenderer(new TextAreaRenderer());
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param e TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getToolTipText(MouseEvent e) {
        String tip = null;
        java.awt.Point p = e.getPoint();
        int rowIndex = rowAtPoint(p);
        int colIndex = columnAtPoint(p);
        int realColumnIndex = convertColumnIndexToModel(colIndex);

        if (realColumnIndex == DetailsTableModel.COL_EVENT) { //HIGHLIGHT

            // column
            tip = adattaLunghezza(getValueAt(rowIndex, colIndex).toString());
        } else { //another column
            tip = super.getToolTipText(e);
        }

        return tip;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param playerId TODO Missing Method Parameter Documentation
     */
    public void refresh(int playerId) {
        this.detailsTableModel.refresh(playerId);

        int maxHeight = 1; //this.getRowHeight();

        for (int row = this.getRowCount() - 1; row >= 0; row--) {
            TableCellRenderer renderer = this.getCellRenderer(row, DetailsTableModel.COL_EVENT);
            JEditorPane comp = (JEditorPane) this.prepareRenderer(renderer, row,
                                                                  DetailsTableModel.COL_EVENT);
            Dimension dim = comp.getPreferredSize();
            int rowCount = ((int) dim.getWidth() / this.getColumnModel()
                                                       .getColumn(DetailsTableModel.COL_EVENT)
                                                       .getWidth()) + 1;
            int rowHeight = (rowCount * (int) dim.getHeight()) + (this.getRowMargin() * 2);
            maxHeight = Math.max(maxHeight, rowHeight);
        }

        this.setRowHeight(maxHeight);
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

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private String aCapo() {
        return new String("<br>");
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param testo TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private String adattaLunghezza(String testo) {
        String risultato = "";

        for (int i = 0, troncamento = 0; i < (testo.length() - 1); i++, troncamento++) {
            risultato += testo.charAt(i);

            if (troncamento >= 40) {
                // andare a capo se c'è uno spazio dopo
                if (testo.charAt(i + 1) == ' ') {
                    risultato += aCapo();
                    troncamento = 0;
                }
            }
        }

        return risultato;
    }
}
