// %515857825:de.hattrickorganizer.gui.playeranalysis%
package de.hattrickorganizer.gui.playeranalysis;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.gui.model.PlayerAnalysisModel;
import de.hattrickorganizer.gui.model.UserColumn;
import de.hattrickorganizer.gui.model.UserColumnController;
import de.hattrickorganizer.gui.utils.TableSorter;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
final class SpielerMatchesTable extends JTable {
	
	private static final long serialVersionUID = 5959815846371146851L;
	
    //~ Instance fields ----------------------------------------------------------------------------

	//TableSorter sorter;
    private PlayerAnalysisModel m_clTableModel;
    private TableSorter m_clTableSorter;

    //private DragSource                  m_clDragsource  =   null;
    private int m_iSpielerId = -1;
    private int instance;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new SpielerMatchesTable object.
     *
     * @param spielerid TODO Missing Constructuor Parameter Documentation
     */
    protected SpielerMatchesTable(int spielerid, int instance) {
        super();
        this.instance = instance;
        m_iSpielerId = spielerid;

        initModel();
        setDefaultRenderer(java.lang.Object.class,
                           new de.hattrickorganizer.gui.model.SpielerTableRenderer());
        setSelectionBackground(de.hattrickorganizer.gui.model.SpielerTableRenderer.SELECTION_BG);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public TableSorter getSorter() {
        return m_clTableSorter;
    }

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
    
    //----------------Listener-------------------------------------------
    public void refresh(int spielerid) {
        m_iSpielerId = spielerid;

        initModel();

        repaint();
    }

    /**
     * Initialisiert das Model
     */
    private void initModel() {
        setOpaque(false);

        if (m_clTableModel == null) {
            m_clTableModel = (instance== 1)?UserColumnController.instance().getAnalysis1Model():UserColumnController.instance().getAnalysis2Model();
            m_clTableModel.setValues(DBZugriff.instance().getSpieler4Matches(m_iSpielerId));

            m_clTableSorter = new TableSorter(m_clTableModel, -1, -1);

            final de.hattrickorganizer.gui.utils.ToolTipHeader header = new de.hattrickorganizer.gui.utils.ToolTipHeader(getColumnModel());
            header.setToolTipStrings(m_clTableModel.getTooltips());
            header.setToolTipText("");
            setTableHeader(header);

            setModel(m_clTableSorter);

            final TableColumnModel columModel = getColumnModel();

            for (int i = 0; i < m_clTableModel.getColumnCount(); i++) {
                columModel.getColumn(i).setIdentifier(new Integer(i));
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
            m_clTableModel.setValues(DBZugriff.instance().getSpieler4Matches(m_iSpielerId));
            m_clTableSorter.reallocateIndexes();
        }

 
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        setSelectionMode(0);
        setRowSelectionAllowed(true);

        m_clTableSorter.initsort();

        //setGridColor(new Color(220, 220, 220));
        //getTableHeader().setReorderingAllowed( false );
        //m_clDragsource = new DragSource();
        //m_clDragsource.createDefaultDragGestureRecognizer( this, 1, this );
    }

}
