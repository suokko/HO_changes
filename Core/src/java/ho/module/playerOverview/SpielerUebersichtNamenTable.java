// %733429640:de.hattrickorganizer.gui.playeroverview%
package ho.module.playerOverview;

import ho.core.gui.comp.renderer.HODefaultTableCellRenderer;
import ho.core.gui.comp.table.TableSorter;

import javax.swing.JTable;

import de.hattrickorganizer.gui.RefreshManager;
import de.hattrickorganizer.gui.Refreshable;
import de.hattrickorganizer.gui.model.ReduzedTableModel;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class SpielerUebersichtNamenTable extends JTable implements Refreshable
{

	private static final long serialVersionUID = -7686660400379157142L;
	
	//~ Instance fields ----------------------------------------------------------------------------

    //TableSorter sorter;
    private TableSorter m_clTableSorter;

    /**
     * Nur Namensspalte anzeigen
     *
     */
    public SpielerUebersichtNamenTable(TableSorter model) {
        super();
        m_clTableSorter = model;
        model.addMouseListenerToHeaderInTable(this);
        model.addTableModelListener(this);
        setSelectionMode(0);
        setModel(new ReduzedTableModel(model, 0));
        setDefaultRenderer(java.lang.Object.class, new HODefaultTableCellRenderer());
        RefreshManager.instance().registerRefreshable(this);

        //Kein init hier!
    }

    //~ Methods ------------------------------------------------------------------------------------

    public final TableSorter getSorter() {
        return m_clTableSorter;
    }


    public final void setSpieler(int spielerid) {
        final int index = m_clTableSorter.getRow4Spieler(spielerid);

        if (index >= 0) {
            this.setRowSelectionInterval(index, index);
        }
    }

    public final void reInit() {
        initModelNamen();

        repaint();
    }

    public final void refresh() {
        //Datenanpassung wird vom SpielerUbersichtsTable erledigt
        repaint();
    }

    /**
     * Initialisiert das Model für die Namen
     */
    private void initModelNamen() {
        setAutoResizeMode(AUTO_RESIZE_OFF);
        setSelectionMode(0);
        setRowSelectionAllowed(true);
        getColumnModel().getColumn(0).setMinWidth(167);
    }
}
