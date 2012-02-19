// %649934645:de.hattrickorganizer.gui.utils%
package de.hattrickorganizer.gui.utils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import plugins.IHOTableEntry;
import de.hattrickorganizer.gui.model.LineupColumnModel;
import de.hattrickorganizer.gui.model.PlayerOverviewModel;
import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.tools.HOLogger;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class TableSorter extends TableMap {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 1132334126127788944L;
	private Vector<Integer> sortingColumns;
    private int[] indexes;
    private boolean ascending;
    private int compares;
    private int currentColumn;
    private int idSpalte;
    private int m_iInitSortColumnIndex = -1;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TableSorter object.
     */
    public TableSorter() {
        sortingColumns = new Vector<Integer>();
        ascending = false;
        currentColumn = -1;
        indexes = new int[0];
    }

    /**
     * Creates a new TableSorter object.
     *
     * @param tablemodel TODO Missing Constructuor Parameter Documentation
     * @param idSpalte TODO Missing Constructuor Parameter Documentation
     * @param initsortcolumnindex TODO Missing Constructuor Parameter Documentation
     */
    public TableSorter(TableModel tablemodel, int idSpalte, int initsortcolumnindex) {
        this.idSpalte = idSpalte;
        this.m_iInitSortColumnIndex = initsortcolumnindex;
        sortingColumns = new Vector<Integer>();
        ascending = false;
        currentColumn = -1;
        setModel(tablemodel);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param tablemodel TODO Missing Method Parameter Documentation
     */
    @Override
	public final void setModel(TableModel tablemodel) {
        super.setModel(tablemodel);
        reallocateIndexes();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param matchid TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getRow4Match(int matchid) {
        if (matchid > 0) {
            for (int i = 0; i < getRowCount(); i++) {
                try {
                    if (matchid == (int) ((ColorLabelEntry) getValueAt(i,idSpalte))
                                   .getZahl()) {
                        //Die Zeile zurückgeben, muss vorher gemapped werden
                        return indexes[i];
                    }
                } catch (Exception e) {
                    HOLogger.instance().log(getClass(),"TableSorter.getRow4Match: " + e);
                }
            }
        }

        return -1;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param spielerid TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getRow4Spieler(int spielerid) {
        //Kann < 0 für Tempspieler sein if ( spielerid > 0 )
        if (spielerid != 0) {
            for (int i = 0; i < getRowCount(); i++) {
                try {
                    if (spielerid == Integer.parseInt(((de.hattrickorganizer.gui.templates.ColorLabelEntry) getValueAt(i,
                                                                                                                       idSpalte))
                                                      .getText())) {
                        //Die Zeile zurückgeben, muss vorher gemapped werden
                        // indexes[i];
                        return i;
                    }
                } catch (Exception e) {
                    HOLogger.instance().log(getClass(),"TableSorter.getRow4Spieler: " + e);
                }
            }
        }

        return -1;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param row TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final ho.module.transfer.scout.ScoutEintrag getScoutEintrag(int row) {
        if (row > -1) {
            try {
                return ((ho.module.transfer.scout.TransferTableModel) getModel())
                       .getScoutEintrag(Integer.parseInt(((ColorLabelEntry) getValueAt(row, idSpalte)).getText()));
            } catch (Exception e) {
                HOLogger.instance().log(getClass(),"TableSorter.getScoutEintrag: " + e);
                return null;
            }
        }

        return null;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param row TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final de.hattrickorganizer.model.Spieler getSpieler(int row) {
        if (row > -1) {
            try {
            	final int id = Integer.parseInt(((ColorLabelEntry) getValueAt(row,idSpalte)).getText());

                
                if (getModel() instanceof PlayerOverviewModel) {
                    return ((PlayerOverviewModel) getModel()).getSpieler(id);
                } else if (getModel() instanceof LineupColumnModel) {
                    return ((LineupColumnModel) getModel()).getSpieler(id);
                } else {
                    throw new Exception("Tablemodel umbekannt!");
                }
            } catch (Exception e) {
                HOLogger.instance().log(getClass(),e);
                return null;
            }
        }

        return null;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param obj TODO Missing Method Parameter Documentation
     * @param i TODO Missing Method Parameter Documentation
     * @param j TODO Missing Method Parameter Documentation
     */
    @Override
	public final void setValueAt(Object obj, int i, int j) {
        checkModel();
        model.setValueAt(obj, indexes[i], j);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     * @param j TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	public final Object getValueAt(int i, int j) {
        checkModel();

        if ((i < 0) || (j < 0)) {
            return null;
        } 
        
        return model.getValueAt(indexes[i], j);
       
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param jtable TODO Missing Method Parameter Documentation
     */
    public final void addMouseListenerToHeaderInTable(JTable jtable) {
        final TableSorter sorter = this;
        final JTable tableView = jtable;

        final JTableHeader jtableheader = tableView.getTableHeader();

        //Listener schon vorhanden
        if (jtableheader.getComponentListeners().length > 0) {
            return;
        }

        tableView.setColumnSelectionAllowed(false);

        final MouseAdapter mouseadapter = new MouseAdapter() {
            @Override
			public void mouseClicked(MouseEvent mouseevent) {
                final TableColumnModel tablecolumnmodel = tableView.getColumnModel();
                final int i = tablecolumnmodel.getColumnIndexAtX(mouseevent.getX());
                final int j = tableView.convertColumnIndexToModel(i);

                if ((mouseevent.getClickCount() == 1) && (j != -1)) {
                    boolean flag = ascending;

                    if (currentColumn == j) {
                        flag = !flag;
                    }

                    sorter.sortByColumn(j, flag);
                }
            }
        };

        jtableheader.addMouseListener(mouseadapter);
    }

    /**
     * TODO Missing Method Documentation
     */
    private void checkModel() {
        //        if(indexes.length != model.getRowCount())
        //        {
        //            //Problem###
        //        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     * @param j TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int compare(int i, int j) {
        compares++;

        for (int k = 0; k < sortingColumns.size(); k++) {
            final Integer integer = (Integer) sortingColumns.elementAt(k);
            final int l = compareRowsByColumn(i, j, integer.intValue());

            if (l != 0) {
                return ascending ? l : (-l);
            }
        }

        return 0;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     * @param j TODO Missing Method Parameter Documentation
     * @param k TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private final int compareRowsByColumn(int i, int j, int k) {
        final Object obj = model.getValueAt(i, k);
        final Object obj1 = model.getValueAt(j, k);

        if ((obj == null) && (obj1 == null)) {
            return 0;
        }

        if (obj == null) {
            return -1;
        }

        if (obj1 == null) {
            return 1;
        }

        if (obj instanceof IHOTableEntry
            && obj1 instanceof IHOTableEntry) {
            final IHOTableEntry colorLabelentry1 = (IHOTableEntry) model.getValueAt(i, k);
            final IHOTableEntry colorLabelentry2 = (IHOTableEntry) model.getValueAt(j, k);
            return colorLabelentry1.compareTo(colorLabelentry2);
        } 
        
        final Object obj2 = model.getValueAt(i, k);
        final String s2 = obj2.toString();
        final Object obj3 = model.getValueAt(j, k);
        final String s3 = obj3.toString();
        final int i2 = s2.compareTo(s3);

        if (i2 < 0) {
            return -1;
        }

        return (i2 <= 0) ? 0 : 1;
        
    }

    /**
     * Sortierung am Anfang beim Erstellen der Tabelle und bei Modeländerungen
     */
    public final void initsort() {
        //InitSortColumnIndex
        if (m_iInitSortColumnIndex > 0) {
            final int j = m_iInitSortColumnIndex;
            final boolean flag = ascending;
            sortByColumn(j, flag);
        }
    }

//    /**
//     * TODO Missing Method Documentation
//     */
//    public final void n2sort() {
//        for (int i = 0; i < getRowCount(); i++) {
//            for (int j = i + 1; j < getRowCount(); j++) {
//                if (compare(indexes[i], indexes[j]) == -1) {
//                    swap(i, j);
//                }
//            }
//        }
//    }

    /**
     * TODO Missing Method Documentation
     */
    public final void reallocateIndexes() {
        final int i = model.getRowCount();
        indexes = new int[i];

        for (int j = 0; j < i; j++) {
            indexes[j] = j;
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param ai TODO Missing Method Parameter Documentation
     * @param ai1 TODO Missing Method Parameter Documentation
     * @param i TODO Missing Method Parameter Documentation
     * @param j TODO Missing Method Parameter Documentation
     */
    public final void shuttlesort(int[] ai, int[] ai1, int i, int j) {
        if ((j - i) < 2) {
            return;
        }

        final int k = (i + j) / 2;
        shuttlesort(ai1, ai, i, k);
        shuttlesort(ai1, ai, k, j);

        int l = i;
        int i1 = k;

        if (((j - i) >= 4) && (compare(ai[k - 1], ai[k]) <= 0)) {
            for (int j1 = i; j1 < j; j1++) {
                ai1[j1] = ai[j1];
            }

            return;
        }

        for (int k1 = i; k1 < j; k1++) {
            if ((i1 >= j) || ((l < k) && (compare(ai[l], ai[i1]) <= 0))) {
                ai1[k1] = ai[l++];
            } else {
                ai1[k1] = ai[i1++];
            }
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param obj TODO Missing Method Parameter Documentation
     */
    private final void sort(Object obj) {
        checkModel();
        compares = 0;
        shuttlesort(indexes.clone(), indexes, 0, indexes.length);
    }

//    /**
//     * TODO Missing Method Documentation
//     *
//     * @param i TODO Missing Method Parameter Documentation
//     */
//    public final void sortByColumn(int i) {
//        sortByColumn(i, true);
//    }

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     * @param flag TODO Missing Method Parameter Documentation
     */
    private final void sortByColumn(int i, boolean flag) {
        ascending = flag;
        currentColumn = i;
        sortingColumns.removeAllElements();
        sortingColumns.addElement(Integer.valueOf(i));
        sort(this);
        super.tableChanged(new TableModelEvent(this));
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     * @param j TODO Missing Method Parameter Documentation
     */
/*    private final void swap(int i, int j) {
        int k = indexes[i];
        indexes[i] = indexes[j];
        indexes[j] = k;
    }*/

    /**
     * TODO Missing Method Documentation
     *
     * @param tablemodelevent TODO Missing Method Parameter Documentation
     */
    @Override
	public final void tableChanged(TableModelEvent tablemodelevent) {
        reallocateIndexes();
        super.tableChanged(tablemodelevent);
    }
}
