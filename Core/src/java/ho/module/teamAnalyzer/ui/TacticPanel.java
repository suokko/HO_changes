// %1654063142:hoplugins.teamAnalyzer.ui%
/*
 * Created on Sep 20, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package ho.module.teamAnalyzer.ui;

import ho.core.gui.model.BaseTableModel;
import ho.core.model.player.SpielerPosition;
import ho.module.teamAnalyzer.report.TacticReport;

import java.awt.BorderLayout;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;



/**
 * DOCUMENT ME!
 *
 * @author mamato To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TacticPanel extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -5271524746410453832L;
	private DefaultTableModel tableModel;
    private JTable table;
    private NumberFormat f = new DecimalFormat("#.#");

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TacticPanel object.
     */
    public TacticPanel() {
        jbInit();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param number TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String format(double number) {
        return f.format(number);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param list TODO Missing Method Parameter Documentation
     */
    public void reload(List<TacticReport> list) {
        tableModel = new BaseTableModel(new Vector<Object>(),
                                        new Vector<String>(Arrays.asList(new String[]{
                                                                     "COL_A", "COL_B", "COL_C"
                                                                 })));
        table.setModel(tableModel);

        Vector<Object> rowData;
        int row = 0;

        for (Iterator<TacticReport> iter = list.iterator(); iter.hasNext();) {
            TacticReport report = iter.next();

            rowData = new Vector<Object>();
            rowData.add(SpielerPosition.getNameForPosition((byte)report.getTacticCode()));
            rowData.add("" + report.getAppearance());
            rowData.add(format(report.getRating()));
            tableModel.addRow(rowData);
            row++;

            if (row == 3) {
                break;
            }
        }

        if (row > 0) {
            this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

            for (; row < 3; row++) {
                tableModel.addRow(emptyLine());
            }
        } else {
            this.setBorder(null);
        }

        table.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(30);
        table.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(30);
    }

    /**
     *
     */
    private Vector<Object> emptyLine() {
        Vector<Object> v = new Vector<Object>();

        return v;
    }

    /**
     * TODO Missing Method Documentation
     */
    private void jbInit() {
        Vector<Object> data = new Vector<Object>();

        tableModel = new BaseTableModel(data,
                                        new Vector<String>(Arrays.asList(new String[]{
                                                                     "COL_A", "COL_B", "COL_C"
                                                                 })));
        table = new JTable(tableModel);
        table.setRowSelectionAllowed(false);
        table.setColumnSelectionAllowed(false);
        this.setLayout(new BorderLayout());
        this.add(table, BorderLayout.CENTER);
        table.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(30);
        table.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(30);
    }
}
