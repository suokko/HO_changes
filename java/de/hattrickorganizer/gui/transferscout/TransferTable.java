// %1101944701:de.hattrickorganizer.gui.transferscout%
package de.hattrickorganizer.gui.transferscout;

import javax.swing.JTable;

import de.hattrickorganizer.gui.model.TransferTableModel;
import de.hattrickorganizer.gui.utils.TableSorter;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class TransferTable extends JTable implements de.hattrickorganizer.gui.Refreshable {
    //~ Instance fields ----------------------------------------------------------------------------

    private TableSorter m_clTableSorter;
    private TransferTableModel m_clTableModel;

    //~ Constructors -------------------------------------------------------------------------------

    //private DragSource                  m_clDragsource  =   null;
    public TransferTable() {
        super();
        m_clTableModel = new TransferTableModel(de.hattrickorganizer.database.DBZugriff.instance()
                                                                                       .getScoutList());
        initModel();
        setDefaultRenderer(java.lang.Object.class,
                           new de.hattrickorganizer.gui.model.SpielerTableRenderer());
        de.hattrickorganizer.gui.RefreshManager.instance().registerRefreshable(this);
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

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
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

    /**
     * TODO Missing Method Documentation
     *
     * @param index TODO Missing Method Parameter Documentation
     * @param width TODO Missing Method Parameter Documentation
     */
    private void setMinWidth(int index, int width) {
        getColumnModel().getColumn(getColumnModel().getColumnIndex(new Integer(index))).setMinWidth(de.hattrickorganizer.tools.Helper
                                                                                                    .calcCellWidth(width));
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param index TODO Missing Method Parameter Documentation
     * @param width TODO Missing Method Parameter Documentation
     */
    private void setPreferredWidth(int index, int width) {
        getColumnModel().getColumn(getColumnModel().getColumnIndex(new Integer(index)))
            .setPreferredWidth(de.hattrickorganizer.tools.Helper.calcCellWidth(width));
    }

    /**
     * Initializes the model
     */
    private void initModel() {
        setOpaque(false);

        if (m_clTableSorter == null) {
            m_clTableSorter = new TableSorter(m_clTableModel, 0, 1);

            final de.hattrickorganizer.gui.utils.ToolTipHeader header = new de.hattrickorganizer.gui.utils.ToolTipHeader(getColumnModel());
            header.setToolTipStrings(m_clTableModel.m_sToolTipStrings);
            header.setToolTipText("");
            setTableHeader(header);

            setModel(m_clTableSorter);

            for (int i = 0; i <= 35; i++) {
                getColumnModel().getColumn(i).setIdentifier(new Integer(i));
            }

            m_clTableSorter.addMouseListenerToHeaderInTable(this);
        } else {
            m_clTableSorter.setModel(m_clTableModel);

            setModel(m_clTableSorter);
        }

        // Set column sizes
        setAutoResizeMode(this.AUTO_RESIZE_OFF);
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

        //setGridColor(new Color(220, 220, 220));
        //getTableHeader().setReorderingAllowed( false );
        //m_clDragsource = new DragSource();
        //m_clDragsource.createDefaultDragGestureRecognizer( this, 1, this );
    }

    //----------------Listener-------------------------------------------

    /*
       public void dragEnter(DragSourceDragEvent dragsourcedragevent)
       {
       }
    
       public void dragOver(DragSourceDragEvent dragsourcedragevent)
       {
       }
    
       public void dropActionChanged(DragSourceDragEvent dragsourcedragevent)
       {
       }
    
       public void dragExit(DragSourceEvent dragsourceevent)
       {
       }
    
       public void dragDropEnd(DragSourceDropEvent dragsourcedropevent)
       {
       }
    
       public void dragGestureRecognized(DragGestureEvent draggestureevent)
       {
           int row = getSelectedRow();
           if ( row >= 0 )
           {
               Spieler spieler = m_clTableModel.getSpieler( row );
               if( spieler != null )
               {
                   SpielerTransferable spielertransferable = new SpielerTransferable( spieler );
                   m_clDragsource.startDrag(draggestureevent, DragSource.DefaultCopyNoDrop, spielertransferable, this);
               }
           }
       }
     */
}
