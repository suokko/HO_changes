// %2863750181:de.hattrickorganizer.gui.lineup%
package de.hattrickorganizer.gui.lineup;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.gui.RefreshManager;
import de.hattrickorganizer.gui.model.BooleanTableCellrenderer;
import de.hattrickorganizer.gui.model.LineupColumnModel;
import de.hattrickorganizer.gui.model.SpielerTableRenderer;
import de.hattrickorganizer.gui.model.UserColumn;
import de.hattrickorganizer.gui.model.UserColumnController;
import de.hattrickorganizer.gui.model.UserColumnFactory;
import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.gui.utils.TableSorter;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.tools.Helper;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public final class AustellungSpielerTable extends JTable implements java.awt.event.MouseListener,
                                                                    java.awt.event.KeyListener,
                                                                    de.hattrickorganizer.gui.Refreshable
{
	
	private static final long serialVersionUID = -8295456454328467793L;
	
    //~ Instance fields ----------------------------------------------------------------------------

	
	//TableSorter sorter;
    private LineupColumnModel m_clTableModel;
    private TableSorter m_clTableSorter;

    //~ Constructors -------------------------------------------------------------------------------

    //private DragSource                  m_clDragsource  =   null;
    protected AustellungSpielerTable() {
        super();

        initModel();
        setDefaultRenderer(Boolean.class,new BooleanTableCellrenderer());
        setDefaultRenderer(Object.class,new SpielerTableRenderer());
        setSelectionBackground(SpielerTableRenderer.SELECTION_BG);
        setBackground(ColorLabelEntry.BG_STANDARD);
        addMouseListener(this);
        RefreshManager.instance().registerRefreshable(this);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param spielerid TODO Missing Method Parameter Documentation
     */
    public void setSpieler(int spielerid) {
        final int index = m_clTableSorter.getRow4Spieler(spielerid);

        if (index >= 0) {
            this.setRowSelectionInterval(index, index);
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param keyEvent TODO Missing Method Parameter Documentation
     */
    public void keyPressed(java.awt.event.KeyEvent keyEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param keyEvent TODO Missing Method Parameter Documentation
     */
    public void keyReleased(java.awt.event.KeyEvent keyEvent) {
        if (this.getSelectedRow() > -1) {
            m_clTableModel.setSpielberechtigung();
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param keyEvent TODO Missing Method Parameter Documentation
     */
    public void keyTyped(java.awt.event.KeyEvent keyEvent) {
    }

    //---------------Listener--------------------------------------    
    public void mouseClicked(java.awt.event.MouseEvent mouseEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param mouseEvent TODO Missing Method Parameter Documentation
     */
    public void mouseEntered(java.awt.event.MouseEvent mouseEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param mouseEvent TODO Missing Method Parameter Documentation
     */
    public void mouseExited(java.awt.event.MouseEvent mouseEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param mouseEvent TODO Missing Method Parameter Documentation
     */
    public void mousePressed(java.awt.event.MouseEvent mouseEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param mouseEvent TODO Missing Method Parameter Documentation
     */
    public void mouseReleased(java.awt.event.MouseEvent mouseEvent) {
        if (this.getSelectedRow() > -1) {
            m_clTableModel.setSpielberechtigung();
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    public void reInit() {
        initModel();

        repaint();
    }

    /**
     * TODO Missing Method Documentation
     */
    public void reInitModel() {
        ((LineupColumnModel) (this.getSorter()).getModel()).reInitData();
    }

    /**
     * TODO Missing Method Documentation
     */
    public void refresh() {
        reInitModel();

        repaint();
    }

    /**
     * Breite der BestPosSpalte zurückgeben
     *
     * @return TODO Missing Return Method Documentation
     */
    protected int getBestPosWidth() {
        return getColumnModel().getColumn(getColumnModel().getColumnIndex(new Integer(3))).getWidth();
    }

    /**
     * Gibt die Spalte für die Sortierung zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    protected int getSortSpalte() {
    	switch (gui.UserParameter.instance().standardsortierung) {
        case gui.UserParameter.SORT_NAME:
            return m_clTableModel.getPositionInArray( UserColumnFactory.NAME );

        case gui.UserParameter.SORT_BESTPOS:
            return m_clTableModel.getPositionInArray( UserColumnFactory.BEST_POSITION );

        case gui.UserParameter.SORT_AUFGESTELLT:
            return m_clTableModel.getPositionInArray( UserColumnFactory.LINUP );

        case gui.UserParameter.SORT_GRUPPE:
            return m_clTableModel.getPositionInArray( UserColumnFactory.GROUP );

        case gui.UserParameter.SORT_BEWERTUNG:
            return m_clTableModel.getPositionInArray( UserColumnFactory.RATING );

        default:
            return m_clTableModel.getPositionInArray( UserColumnFactory.BEST_POSITION );

        }
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
     * DOCUMENT ME!
     *
     * @return int[spaltenanzahl][2] mit 0=ModelIndex und 1=ViewIndex
     */
    protected int[][] getSpaltenreihenfolge() {
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
    
    /**
     * Initialisiert das Model
     */
    private void initModel() {
        setOpaque(false);

        if (m_clTableModel == null) {
            m_clTableModel = UserColumnController.instance().getLineupModel();//();
            
            m_clTableModel.setValues(HOVerwaltung.instance().getModel().getAllSpieler());                                                                                      
            m_clTableSorter = new TableSorter(m_clTableModel, m_clTableModel.getPositionInArray(UserColumnFactory.ID), getSortSpalte());

            final de.hattrickorganizer.gui.utils.ToolTipHeader header = new de.hattrickorganizer.gui.utils.ToolTipHeader(getColumnModel());
            header.setToolTipStrings(m_clTableModel.getTooltips());
            header.setToolTipText("");
            setTableHeader(header);

            setModel(m_clTableSorter);

            final TableColumnModel columnModel = getColumnModel();

            for (int i = 0; i < m_clTableModel.getColumnCount(); i++) {
                columnModel.getColumn(i).setIdentifier(new Integer(i));
            }

            int[][] targetColumn = m_clTableModel.getColumnOrder();//gui.UserParameter.instance().aufstellungsspaltenreihenfolge;

            //Reihenfolge -> nach [][1] sortieren
            targetColumn = Helper.sortintArray(targetColumn, 1);

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
            m_clTableModel.setValues(HOVerwaltung.instance().getModel().getAllSpieler());
            m_clTableSorter.reallocateIndexes();
        }

        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
       
       setSelectionMode(0);
        setRowSelectionAllowed(true);

        m_clTableSorter.initsort();

    }
    
}
