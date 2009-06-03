// %733429640:de.hattrickorganizer.gui.playeroverview%
package de.hattrickorganizer.gui.playeroverview;

import javax.swing.JTable;

import de.hattrickorganizer.gui.utils.TableSorter;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class SpielerUebersichtNamenTable extends JTable
    implements de.hattrickorganizer.gui.Refreshable
{
    //~ Instance fields ----------------------------------------------------------------------------

    //TableSorter sorter;
    private TableSorter m_clTableSorter;

    //~ Constructors -------------------------------------------------------------------------------

    //private DragSource                  m_clDragsource  =   null;

    /**
     * Nur Namensspalte anzeigen
     *
     * @param model TODO Missing Constructuor Parameter Documentation
     */
    public SpielerUebersichtNamenTable(TableSorter model) {
        super();
        m_clTableSorter = model;
        model.addMouseListenerToHeaderInTable(this);
        model.addTableModelListener(this);
        setSelectionMode(0);
        setModel(new de.hattrickorganizer.gui.model.ReduzedTableModel(model, 0));
        setDefaultRenderer(java.lang.Object.class,
                           new de.hattrickorganizer.gui.model.SpielerTableRenderer());
        de.hattrickorganizer.gui.RefreshManager.instance().registerRefreshable(this);

        //Kein init hier!
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final TableSorter getSorter() {
        return m_clTableSorter;
    }


    public final void setSpieler(int spielerid) {
        final int index = m_clTableSorter.getRow4Spieler(spielerid);

        if (index >= 0) {
            this.setRowSelectionInterval(index, index);
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void reInit() {
        initModelNamen();

        repaint();
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void refresh() {
        //Datenanpassung wird vom SpielerUbersichtsTable erledigt
        repaint();
    }

    /**
     * Initialisiert das Model für die Namen
     */
    private void initModelNamen() {
        setAutoResizeMode(this.AUTO_RESIZE_OFF);
        setSelectionMode(0);
        setRowSelectionAllowed(true);
        getColumnModel().getColumn(0).setMinWidth(167);
    }
}
