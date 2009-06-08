// %2440820945:de.hattrickorganizer.gui.skillupdate%
package de.hattrickorganizer.gui.skillupdate;

import de.hattrickorganizer.gui.model.SkillAenderungsTableModel;
import de.hattrickorganizer.gui.utils.TableSorter;

/**
 * Tabelle mit Spielerdaten
 */
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import plugins.ISpieler;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class SkillAenderungsTable extends JTable implements de.hattrickorganizer.gui.Refreshable {
	
	private static final long serialVersionUID = 3202436484641379405L;
	
    //~ Instance fields ----------------------------------------------------------------------------

	private SkillAenderungsTableModel m_clTableModel;
    private TableSorter m_clTableSorter;
    private boolean m_bInitModel;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new SkillAenderungsTable object.
     */
    public SkillAenderungsTable() {
        super();

        //Erst durch den Button
        initModel();
        setDefaultRenderer(java.lang.Object.class,
                           new de.hattrickorganizer.gui.model.SpielerTableRenderer());
        setSelectionBackground(de.hattrickorganizer.gui.model.SpielerTableRenderer.SELECTION_BG);
        de.hattrickorganizer.gui.RefreshManager.instance().registerRefreshable(this);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /*
     * @return int[spaltenanzahl][2] mit 0=ModelIndex und 1=ViewIndex
     */

    //public int[][] getSpaltenreihenfolge()
    //{
    //    int[][] reihenfolge = new int[m_clTableModel.getColumnCount()][2];
    //    
    //    for ( int i = 0; i < m_clTableModel.getColumnCount(); i++ )
    //    {
    //        reihenfolge[i][0] = i; // Modelindex
    //        reihenfolge[i][1] = convertColumnIndexToView(i);//ViewIndex
    //    }
    //    
    //    return reihenfolge;
    //}
    public final TableSorter getSorter() {
        return m_clTableSorter;
    }

    //Per Button
    public final void init() {
        m_bInitModel = true;

        initModel();

        repaint();
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void reInit() {
        m_bInitModel = false;

        initModel();

        repaint();
    }

    //----------------Refresh-------------------------------------------
    public void refresh() {
    }

    /**
     * Initialisiert das Model
     */
    private void initModel() {
        setOpaque(false);

        if (m_clTableModel == null) {
            /*model.HOVerwaltung.instance().getModel().getAllSpieler()*/
            m_clTableModel = new SkillAenderungsTableModel(new Vector<ISpieler>());
            m_clTableSorter = new TableSorter(m_clTableModel, 11, 2);

            final de.hattrickorganizer.gui.utils.ToolTipHeader header = new de.hattrickorganizer.gui.utils.ToolTipHeader(getColumnModel());
            header.setToolTipStrings(m_clTableModel.m_sToolTipStrings);
            header.setToolTipText("");
            setTableHeader(header);

            setModel(m_clTableSorter);

            final TableColumnModel tableColumnModel = getColumnModel();

            for (int i = 0; i < 12; i++) {
                tableColumnModel.getColumn(i).setIdentifier(new Integer(i));
            }

            //            int[][] targetColumn = gui.UserParameter.instance ().spielespaltenreihenfolge;
            //            
            //            //Reihenfolge -> nach [][1] sortieren
            //            targetColumn = tools.Helper.sortintArray( targetColumn, 1 );
            //            
            //            if ( targetColumn != null )
            //            {
            //                for ( int i = 0; i < targetColumn.length; i++ )
            //                {
            //                    this.moveColumn ( getColumnModel().getColumnIndex ( new Integer( targetColumn[i][0] ) ), targetColumn[i][1] );
            //                }
            //            }
            m_clTableSorter.addMouseListenerToHeaderInTable(this);
        } else {
            if (m_bInitModel) {
                //Werte neu setzen
                m_clTableModel.setValues(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                .getModel()
                                                                                .getAllSpieler());
                m_clTableSorter.reallocateIndexes();
            } else {
                //Werte wieder leeren
                m_clTableModel.setValues(new Vector<ISpieler>());
                m_clTableSorter.reallocateIndexes();
            }
        }

        setAutoResizeMode(AUTO_RESIZE_OFF);

        final TableColumnModel tableColumnModel = getColumnModel();
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(0)))
                        .setPreferredWidth(de.hattrickorganizer.tools.Helper.calcCellWidth(167));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(0))).setMinWidth(de.hattrickorganizer.tools.Helper
                                                                                                .calcCellWidth(167));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(1)))
                        .setPreferredWidth(de.hattrickorganizer.tools.Helper.calcCellWidth(20));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(1))).setMinWidth(de.hattrickorganizer.tools.Helper
                                                                                                .calcCellWidth(20));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(2)))
                        .setPreferredWidth(gui.UserParameter.instance().bestPostWidth);
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(3))).setMinWidth(de.hattrickorganizer.tools.Helper
                                                                                                .calcCellWidth(50));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(3)))
                        .setPreferredWidth(de.hattrickorganizer.tools.Helper.calcCellWidth(50));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(4))).setMinWidth(de.hattrickorganizer.tools.Helper
                                                                                                .calcCellWidth(100));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(4)))
                        .setPreferredWidth(de.hattrickorganizer.tools.Helper.calcCellWidth(120));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(5))).setMinWidth(de.hattrickorganizer.tools.Helper
                                                                                                .calcCellWidth(100));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(5)))
                        .setPreferredWidth(de.hattrickorganizer.tools.Helper.calcCellWidth(120));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(6))).setMinWidth(de.hattrickorganizer.tools.Helper
                                                                                                .calcCellWidth(100));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(6)))
                        .setPreferredWidth(de.hattrickorganizer.tools.Helper.calcCellWidth(120));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(7))).setMinWidth(de.hattrickorganizer.tools.Helper
                                                                                                .calcCellWidth(100));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(7)))
                        .setPreferredWidth(de.hattrickorganizer.tools.Helper.calcCellWidth(120));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(8))).setMinWidth(de.hattrickorganizer.tools.Helper
                                                                                                .calcCellWidth(100));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(8)))
                        .setPreferredWidth(de.hattrickorganizer.tools.Helper.calcCellWidth(120));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(9))).setMinWidth(de.hattrickorganizer.tools.Helper
                                                                                                .calcCellWidth(100));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(9)))
                        .setPreferredWidth(de.hattrickorganizer.tools.Helper.calcCellWidth(120));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(10))).setMinWidth(de.hattrickorganizer.tools.Helper
                                                                                                 .calcCellWidth(100));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(10)))
                        .setPreferredWidth(de.hattrickorganizer.tools.Helper.calcCellWidth(120));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(11)))
                        .setPreferredWidth(de.hattrickorganizer.tools.Helper.calcCellWidth(55));
        setSelectionMode(0);
        setRowSelectionAllowed(true);

        m_clTableSorter.initsort();
    }
}
