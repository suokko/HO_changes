// %515069496:hoplugins.evilCard.ui.model%
/*
 * Created on 13.04.2004
 *
 * Mag. Bernhard Hödl
 *
 * AH - Solutions
 * Augsten & Hödl OEG
 * Neubachgasse 12
 * A - 2325 Himberg
 */
package hoplugins.evilCard.ui.model;

import hoplugins.Commons;

import hoplugins.commons.ui.DebugWindow;
import hoplugins.commons.utils.PluginProperty;

import plugins.IMatchHighlight;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Arrays;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;


/**
 * Player details panel
 *
 * @author Mag. Bernhard Hödl  AH - Solutions Augsten & Hödl OEG Neubachgasse 12 A - 2325 Himberg
 *         Tabellenmodel und Daten für die dargestellte Tabelle für das HO Plungin
 */
public class DetailsTableModel extends AbstractTableModel {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public static final int cols = 10;

    /** TODO Missing Parameter Documentation */
    public static final int COL_DIRECT_RED_CARDS = 0;

    /** TODO Missing Parameter Documentation */
    public static final int COL_WARNINGS_TYPE1 = 1;

    /** TODO Missing Parameter Documentation */
    public static final int COL_WARNINGS_TYPE2 = 2;

    /** TODO Missing Parameter Documentation */
    public static final int COL_WARNINGS_TYPE3 = 3;

    /** TODO Missing Parameter Documentation */
    public static final int COL_WARNINGS_TYPE4 = 4;

    /** TODO Missing Parameter Documentation */
    public static final int COL_MATCH_ID = 5;

    /** TODO Missing Parameter Documentation */
    public static final int COL_MATCH_HOME = 6;

    /** TODO Missing Parameter Documentation */
    public static final int COL_MATCH_GUEST = 7;

    /** TODO Missing Parameter Documentation */
    public static final int COL_MATCH_RESULT = 8;

    /** TODO Missing Parameter Documentation */
    public static final int COL_EVENT = 9;

    /** TODO Missing Parameter Documentation */
    public static final String CHECKED = "X";

    /** TODO Missing Parameter Documentation */
    public static final String UNDEFINED = "";

    //~ Instance fields ----------------------------------------------------------------------------

    private Vector vColumnNames = null;
    private Object[][] data = null;
    private int playerId = 0;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new DetailTableModel object.
     *
     * @param playerId TODO Missing Constructuor Parameter Documentation
     */
    public DetailsTableModel(int playerId) {
        this.playerId = playerId;

        data = new Object[0][cols];

        vColumnNames = new Vector(Arrays.asList(new String[cols]));

        //Riempimento valori
        vColumnNames.set(COL_DIRECT_RED_CARDS, PluginProperty.getString("column.RedCards"));
        vColumnNames.set(COL_WARNINGS_TYPE1, PluginProperty.getString("column.WarningType1"));
        vColumnNames.set(COL_WARNINGS_TYPE2, PluginProperty.getString("column.WarningType2"));
        vColumnNames.set(COL_WARNINGS_TYPE3, PluginProperty.getString("column.WarningType3"));
        vColumnNames.set(COL_WARNINGS_TYPE4, PluginProperty.getString("column.WarningType4"));
        vColumnNames.set(COL_MATCH_ID, PluginProperty.getString("ID"));
        vColumnNames.set(COL_MATCH_HOME, PluginProperty.getString("Heim"));
        vColumnNames.set(COL_MATCH_GUEST, PluginProperty.getString("Gast"));
        vColumnNames.set(COL_MATCH_RESULT, PluginProperty.getString("Ergebnis"));
        vColumnNames.set(COL_EVENT, PluginProperty.getString("column.Event"));

        // dimensione campi
        generateData();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param c TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public Class getColumnClass(int c) {
        //return getValueAt(0, c).getClass();
        return Object.class;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getColumnCount() {
        return vColumnNames.size();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param c TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getColumnName(int c) {
        return (String) vColumnNames.get(c);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getRowCount() {
        return data.length;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param rowIndex TODO Missing Method Parameter Documentation
     * @param columnIndex TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param playerId TODO Missing Method Parameter Documentation
     */
    public void refresh(int playerId) {
        this.playerId = playerId;
        generateData();
        this.fireTableDataChanged();
    }

    /**
     * TODO Missing Method Documentation
     */
    private void generateData() {
        // Inserimento valori iniziali
        if (playerId > 0) {
            String sql = "SELECT * FROM MATCHHIGHLIGHTS WHERE TYP = "
                         + IMatchHighlight.HIGHLIGHT_KARTEN + " AND SPIELERID = " + playerId;

            //TYP = " + IMatchHighlight.HIGHLIGHT_KARTEN + " AND
            ResultSet res = Commons.getModel().getAdapter().executeQuery(sql);

            try {
                int i = 0;
                res.last();

                int rows = res.getRow();

                if (rows <= 0) {
                    data = new Object[0][cols];
                    return;
                }

                //inizializazione
                data = new Object[rows][cols];

                res.first();

                while (true) {
                    data[i][COL_MATCH_ID] = new Integer(res.getInt("MATCHID"));

                    data[i][COL_EVENT] = new String("<html>" + res.getString("EVENTTEXT"));

                    // controllo ammonizioni
                    switch (res.getInt("SUBTYP")) {
                        case IMatchHighlight.HIGHLIGHT_SUB_GELB_HARTER_EINSATZ:
                            data[i][COL_WARNINGS_TYPE1] = CHECKED;
                            break;

                        case IMatchHighlight.HIGHLIGHT_SUB_GELB_ROT_HARTER_EINSATZ:
                            data[i][COL_WARNINGS_TYPE2] = CHECKED;
                            break;

                        case IMatchHighlight.HIGHLIGHT_SUB_GELB_UNFAIR:
                            data[i][COL_WARNINGS_TYPE3] = CHECKED;
                            break;

                        case IMatchHighlight.HIGHLIGHT_SUB_GELB_ROT_UNFAIR:
                            data[i][COL_WARNINGS_TYPE4] = CHECKED;
                            break;

                        case IMatchHighlight.HIGHLIGHT_SUB_ROT:
                            data[i][COL_DIRECT_RED_CARDS] = CHECKED;
                            break;
                    }

                    data[i][COL_MATCH_HOME] = UNDEFINED;
                    data[i][COL_MATCH_HOME] = UNDEFINED;
                    data[i][COL_MATCH_RESULT] = UNDEFINED;
                    i++;

                    if (!res.next()) {
                        break;
                    }
                }

                // MATCH
                for (i = 0; i < rows; i++) {
                    sql = "SELECT * FROM MATCHESKURZINFO WHERE MATCHID = "
                          + ((Integer) data[i][COL_MATCH_ID]).intValue();

                    ResultSet resMatch = Commons.getModel().getAdapter().executeQuery(sql);

                    if (resMatch.first()) {
                        //if (resMatch.next())
                        data[i][COL_MATCH_HOME] = resMatch.getString("HEIMNAME");
                        data[i][COL_MATCH_GUEST] = resMatch.getString("GASTNAME");
                        data[i][COL_MATCH_RESULT] = resMatch.getInt("HEIMTORE") + " - "
                                                    + resMatch.getInt("GASTTORE");
                    }

                    //	else
                    //		data[i][COL_MATCH] = new String("X");
                }
            } catch (SQLException e) {
                DebugWindow.debug(e);
            }
        }
    }
}
