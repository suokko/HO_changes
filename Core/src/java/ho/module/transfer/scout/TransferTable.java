// %1101944701:de.hattrickorganizer.gui.transferscout%
package ho.module.transfer.scout;

import ho.core.db.DBManager;
import ho.core.gui.RefreshManager;
import ho.core.gui.Refreshable;
import ho.core.gui.comp.renderer.HODefaultTableCellRenderer;
import ho.core.gui.comp.table.TableSorter;
import ho.core.gui.comp.table.ToolTipHeader;
import ho.core.util.Helper;

import javax.swing.JTable;



/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class TransferTable extends JTable implements Refreshable {
	
	private static final long serialVersionUID = 5687881326217263261L;
	
    //~ Instance fields ----------------------------------------------------------------------------
	private TableSorter m_clTableSorter;
    private TransferTableModel m_clTableModel;

    //~ Constructors -------------------------------------------------------------------------------

    TransferTable() {
        super();
        m_clTableModel = new TransferTableModel(DBManager.instance().getScoutList());
        initModel();
        setDefaultRenderer(java.lang.Object.class, new HODefaultTableCellRenderer());
        RefreshManager.instance().registerRefreshable(this);
    }

    //~ Methods ------------------------------------------------------------------------------------

    final TableSorter getSorter() {
        return m_clTableSorter;
    }

    public final TransferTableModel getTransferTableModel() {
        return ((TransferTableModel) ((TableSorter) this.getModel()).getModel());
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void reInit() {
        initModel();
        repaint();
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void refresh() {
        initModel();
        repaint();
    }

    private void setMinWidth(int index, int width) {
        getColumnModel().getColumn(getColumnModel().getColumnIndex(Integer.valueOf(index))).setMinWidth(Helper.calcCellWidth(width));
    }

    private void setPreferredWidth(int index, int width) {
        getColumnModel().getColumn(getColumnModel().getColumnIndex(Integer.valueOf(index)))
            .setPreferredWidth(Helper.calcCellWidth(width));
    }

    /**
     * Initializes the model
     */
    private void initModel() {
        setOpaque(false);

        if (m_clTableSorter == null) {
            m_clTableSorter = new TableSorter(m_clTableModel, 0, 1);

            final ToolTipHeader header = new ToolTipHeader(getColumnModel());
            header.setToolTipStrings(m_clTableModel.m_sToolTipStrings);
            header.setToolTipText("");
            setTableHeader(header);

            setModel(m_clTableSorter);

            for (int i = 0; i <= 35; i++) {
                getColumnModel().getColumn(i).setIdentifier(Integer.valueOf(i));
            }

            m_clTableSorter.addMouseListenerToHeaderInTable(this);
        } else {
            m_clTableSorter.setModel(m_clTableModel);

            setModel(m_clTableSorter);
        }

        // Set column sizes
        setAutoResizeMode(AUTO_RESIZE_OFF);
        setMinWidth(0, 80);
        setMinWidth(1, 120);
        setMinWidth(2, 80);
        setMinWidth(3, 135);
        setMinWidth(4, 135);
        setMinWidth(5, 40);
        setPreferredWidth(5, 40);
        setMinWidth(6, 80);

        for (int i = 7; i <= 16; i++) {
            setMinWidth(i, 25);
            setPreferredWidth(i, 25);
        }

        for (int i = 17; i <= 33; i++) {
            setMinWidth(i, 33);
            setPreferredWidth(i, 33);
        }

        setMinWidth(34, 35);
        setPreferredWidth(34, 35);
        setMinWidth(35, 100);
        setSelectionMode(0);
        setRowSelectionAllowed(true);

        m_clTableSorter.initsort();
    }
}
