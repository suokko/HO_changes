// %3884409028:de.hattrickorganizer.gui.matches%
package de.hattrickorganizer.gui.matches;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.gui.model.MatchesColumnModel;
import de.hattrickorganizer.gui.model.SpielerTableRenderer;
import de.hattrickorganizer.gui.model.UserColumn;
import de.hattrickorganizer.gui.model.UserColumnController;
import de.hattrickorganizer.gui.utils.TableSorter;
import de.hattrickorganizer.model.HOVerwaltung;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
final class MatchesTable extends JTable {
    //~ Instance fields ----------------------------------------------------------------------------

    private MatchesColumnModel m_clTableModel;
    private TableSorter m_clTableSorter;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new MatchesTable object.
     *
     * @param matchtyp TODO Missing Constructuor Parameter Documentation
     */
    protected MatchesTable(int matchtyp) {
        super();
        initModel(matchtyp);
        setDefaultRenderer(java.lang.Object.class,
                           new SpielerTableRenderer());
        setSelectionBackground(SpielerTableRenderer.SELECTION_BG);
        setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return int[spaltenanzahl][2] mit 0=ModelIndex und 1=ViewIndex
     */
    public int[][] getSpaltenreihenfolge() {
        final int[][] reihenfolge = new int[m_clTableModel.getColumnCount()][2];

        for (int i = 0; i < m_clTableModel.getColumnCount(); i++) {
            // Modelindex
            reihenfolge[i][0] = i;

            //ViewIndex
            reihenfolge[i][1] = convertColumnIndexToView(i);
        }

        return reihenfolge;
    }

    public final void saveColumnOrder(){
    	final UserColumn[] columns = m_clTableModel.getDisplayedColumns();
    	final TableColumnModel tableColumnModel = getColumnModel();
    	for (int i = 0; i < columns.length; i++) {
    		columns[i].setIndex(convertColumnIndexToView(i));
    		columns[i].setPreferredWidth(tableColumnModel.getColumn(convertColumnIndexToView(i)).getWidth());
    	}
    	m_clTableModel.setCurrentValueToColumns(columns);
    	DBZugriff.instance().saveHOColumnModel(m_clTableModel);
    }
    
    //----------------Refresh-------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param matchtypen Die Typen der Matches ( Im SpielePanel definiert )
     */
    public void refresh(int matchtypen) {
        initModel(matchtypen);

        repaint();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected TableSorter getSorter() {
        return m_clTableSorter;
    }

    /**
     * Markiert ein Match, wenn es in der Tabelle vorhanden ist, sonst wird die Selektion gelöscht
     *
     * @param matchid TODO Missing Constructuor Parameter Documentation
     */
    protected void markiereMatch(int matchid) {
        final int row = m_clTableSorter.getRow4Match(matchid);

        if (row > -1) {
            setRowSelectionInterval(row, row);
        } else {
            clearSelection();
        }
    }

    /**
     * Initialisiert das Model
     *
     * @param matchtyp Die Typen der Matches ( Im SpielePanel definiert )
     */
    private void initModel(int matchtyp) {
        setOpaque(false);

        if (m_clTableModel == null) {
            m_clTableModel = UserColumnController.instance().getMatchesModel();
            
            m_clTableModel.setValues(DBZugriff.instance().getMatchesKurzInfo(HOVerwaltung.instance().getModel()
                                                                               .getBasics().getTeamId(), matchtyp,false));
            
            m_clTableSorter = new TableSorter(m_clTableModel,  m_clTableModel.getDisplayedColumns().length-1, -1);

            final de.hattrickorganizer.gui.utils.ToolTipHeader header = new de.hattrickorganizer.gui.utils.ToolTipHeader(getColumnModel());
            header.setToolTipStrings(m_clTableModel.getTooltips());
            header.setToolTipText("");
            setTableHeader(header);

            setModel(m_clTableSorter);

            final TableColumnModel tableColumnModel = getColumnModel();

            for (int i = 0; i < m_clTableModel.getColumnCount(); i++) {
                tableColumnModel.getColumn(i).setIdentifier(new Integer(i));
            }

            int[][] targetColumn = m_clTableModel.getColumnOrder();

            //Reihenfolge -> nach [][1] sortieren
            targetColumn = de.hattrickorganizer.tools.Helper.sortintArray(targetColumn, 1);

            if (targetColumn != null) {
                for (int i = 0; i < targetColumn.length; i++) {
                    this.moveColumn(getColumnModel().getColumnIndex(new Integer(targetColumn[i][0])),
                                    targetColumn[i][1]);
                }
            }

            m_clTableSorter.addMouseListenerToHeaderInTable(this);
            m_clTableModel.setColumnsSize(getColumnModel());
        } else {
            //Werte neu setzen
            m_clTableModel.setValues(DBZugriff.instance().getMatchesKurzInfo(HOVerwaltung.instance().getModel()
                                                                                                    .getBasics()
                                                                                                    .getTeamId(),
                                                                                                matchtyp,
                                                                                                false));
            m_clTableSorter.reallocateIndexes();

        }

        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        
        
        setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        setRowSelectionAllowed(true);

        m_clTableSorter.initsort();
    }
}
