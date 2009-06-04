// %1126721451682:hoplugins.trainingExperience.ui%
/*
 * Created on 05.05.2004
 * by Bernhard
 *
 * Klassen:
 *    Hauptw�rter, ohne Abst�nde, Wortanfangsbuchstaben gro�, eigene Klassen beginnen mit "My"
 *    Bsp: MyFitnessCenter,MyMainPanel
 *
 *
 * Interfaces:
 *    beginnen mit "I", Hauptw�rter, ohne Abst�nde, Wortanfangsbuchstaben gro�
 *    Bsp: IKunden, IPlan
 *
 * Methoden: - F�r Parameter gelten die gleichen Richtlinien wie f�r lokale Variablen
 *    Verben, mit kleinem Anfangsbuchstaben, Folgew�rter mit gro�em Anfangsbuchstaben
 *    Bsp: getKunde, initialize
 *
 *
 * globale Variablen in einer Klasse:
 *    W�rter, die die aufnehmenden Daten oder die Funktion der Variablen beschreiben,
 *    kleiner Anfangsbuchstabe, Folgew�rter gro�,
 *    je nach Sichtbarkeit mit Suffix:
 *        private: p_
 *        protected: pr_
 *        public: pub_
 *    je nach Typ sollten sie mit folgenden Einschub erg�nzt werden:
 *        int: i_
 *        Integer: I_
 *        double: d_
 *        Doulbe: D_
 *        float: f_
 *        Float: F_
 *        String: S_
 *        Vector: V_
 *        Hashtable: Ht_
 *        HashSet: HS_
 *        bzw. je nach Typ ein K�rzel (Bsp: p_IK_Kunde = private InterfaceKunde mit Namen Kunde)
 *    ist die Variable ein Array, dann wird noch ein a vor den Einschub mit der Typbezeichnung gestellt
 *
 * lokale Variable in einer Methode:
 *    sind frei von jedem Suffix, sollen nur m�glichst gut die Funktion beschreiben, zu der sie verwendet werden
 *
 * Konstanten: alles mit Gro�buchstaben
 */
package hoplugins.trainingExperience.ui;

import java.awt.event.MouseEvent;

import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;


/**
 * DOCUMENT ME!
 *
 * @author Bernhard HO
 */
public class OutputTable extends JTable {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     *
     */
    public OutputTable() {
        super();
    }

    /**
     * DOCUMENT ME!
     *
     * @param numRows
     * @param numColumns
     */
    public OutputTable(int numRows, int numColumns) {
        super(numRows, numColumns);
    }

    /**
     * DOCUMENT ME!
     *
     * @param dm
     */
    public OutputTable(TableModel dm) {
        super(dm);
    }

    /**
     * DOCUMENT ME!
     *
     * @param rowData
     * @param columnNames
     */
    public OutputTable(Object[][] rowData, Object[] columnNames) {
        super(rowData, columnNames);
    }

    /**
     * DOCUMENT ME!
     *
     * @param rowData
     * @param columnNames
     */
    public OutputTable(Vector rowData, Vector columnNames) {
        super(rowData, columnNames);
    }

    /**
     * DOCUMENT ME!
     *
     * @param dm
     * @param cm
     */
    public OutputTable(TableModel dm, TableColumnModel cm) {
        super(dm, cm);
    }

    /**
     * DOCUMENT ME!
     *
     * @param dm
     * @param cm
     * @param sm
     */
    public OutputTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
        super(dm, cm, sm);
    }

    //~ Methods ------------------------------------------------------------------------------------

    //Methode f�r ToolTipText des Trainingstable
    public String getToolTipText(MouseEvent e) {
        OutputTableSorter tableModel = (OutputTableSorter) getModel();
        java.awt.Point p = e.getPoint();
        int rowIndex = rowAtPoint(p);
        int colIndex = columnAtPoint(p);
        int realColumnIndex = convertColumnIndexToModel(colIndex);
        int realRowIndex = tableModel.modelIndex(rowIndex);

        if ((realColumnIndex > 2) && (realColumnIndex < 11)) {
            Object obj = tableModel.getToolTipAt(realRowIndex, realColumnIndex);

            return obj.toString();
        }

        return ""; //$NON-NLS-1$
    }
}
