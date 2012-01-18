// %1126721451682:hoplugins.trainingExperience.ui%
/*
 * Created on 05.05.2004
 * by Bernhard
 *
 * Klassen:
 *    Hauptwörter, ohne Abstände, Wortanfangsbuchstaben groß, eigene Klassen beginnen mit "My"
 *    Bsp: MyFitnessCenter,MyMainPanel
 *
 *
 * Interfaces:
 *    beginnen mit "I", Hauptwörter, ohne Abstände, Wortanfangsbuchstaben groß
 *    Bsp: IKunden, IPlan
 *
 * Methoden: - Für Parameter gelten die gleichen Richtlinien wie für lokale Variablen
 *    Verben, mit kleinem Anfangsbuchstaben, Folgewörter mit großem Anfangsbuchstaben
 *    Bsp: getKunde, initialize
 *
 *
 * globale Variablen in einer Klasse:
 *    Wörter, die die aufnehmenden Daten oder die Funktion der Variablen beschreiben,
 *    kleiner Anfangsbuchstabe, Folgewörter groß,
 *    je nach Sichtbarkeit mit Suffix:
 *        private: p_
 *        protected: pr_
 *        public: pub_
 *    je nach Typ sollten sie mit folgenden Einschub ergänzt werden:
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
 *        bzw. je nach Typ ein Kürzel (Bsp: p_IK_Kunde = private InterfaceKunde mit Namen Kunde)
 *    ist die Variable ein Array, dann wird noch ein a vor den Einschub mit der Typbezeichnung gestellt
 *
 * lokale Variable in einer Methode:
 *    sind frei von jedem Suffix, sollen nur möglichst gut die Funktion beschreiben, zu der sie verwendet werden
 *
 * Konstanten: alles mit Großbuchstaben
 */
package ho.module.training.ui;

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
	private static final long serialVersionUID = 1089805262735794338L;

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
    public OutputTable(Vector<Object> rowData, Vector<String> columnNames) {
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

    //Methode für ToolTipText des Trainingstable
    @Override
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
