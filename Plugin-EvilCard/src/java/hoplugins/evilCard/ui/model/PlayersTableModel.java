// %982614250:hoplugins.evilCard.ui.model%
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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import plugins.IMatchHighlight;
import plugins.ISpieler;


/**
 * DOCUMENT ME!
 *
 * @author Mag. Bernhard Hödl AH - Solutions Augsten & Hödl OEG Neubachgasse 12 A - 2325 Himberg
 *         Tabellenmodel und Daten für die dargestellte Tabelle für das HO Plungin
 */
public class PlayersTableModel extends AbstractTableModel {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public static final int COL_ID = 0;

    /** TODO Missing Parameter Documentation */
    public static final int COL_NAME = 1;

    /** TODO Missing Parameter Documentation */
    public static final int COL_AGREEABILITY = 2;

    /** TODO Missing Parameter Documentation */
    public static final int COL_AGGRESSIVITY = 3;

    /** TODO Missing Parameter Documentation */
    public static final int COL_HONESTY = 4;

    /** TODO Missing Parameter Documentation */
    public static final int COL_CARDS = 5;

    /** TODO Missing Parameter Documentation */
    public static final int COL_DIRECT_RED_CARDS = 6;

    /** TODO Missing Parameter Documentation */
    public static final int COL_WARNINGS = 7;

    /** TODO Missing Parameter Documentation */
    public static final int COL_WARNINGS_TYPE1 = 8;

    /** TODO Missing Parameter Documentation */
    public static final int COL_WARNINGS_TYPE2 = 9;

    /** TODO Missing Parameter Documentation */
    public static final int COL_WARNINGS_TYPE3 = 10;

    /** TODO Missing Parameter Documentation */
    public static final int COL_WARNINGS_TYPE4 = 11;

    /** TODO Missing Parameter Documentation */
    public static final int COL_RAW_AVERAGE = 12;

    /** TODO Missing Parameter Documentation */
    public static final int COL_WEIGHTED_AVERAGE = 13;

    /** TODO Missing Parameter Documentation */
    public static final int COL_MATCHES = 14;

    /** TODO Missing Parameter Documentation */
    public static final int TYPE_CURRENT_PLAYERS = 1;

    /** TODO Missing Parameter Documentation */
    public static final int TYPE_ALL_PLAYERS = 2;

    /** TODO Missing Parameter Documentation */
    public static final int TYPE_ALL_MATCHES = 1;

    /** TODO Missing Parameter Documentation */
    public static final int TYPE_FRIENDLIES = 2;

    /** TODO Missing Parameter Documentation */
    public static final int TYPE_OFFICIAL_MATCHES = 3;

    /** TODO Missing Parameter Documentation */
    public static final int cols = 15;

    /** TODO Missing Parameter Documentation */
    private static final String PERCENT = " %";

    /** TODO Missing Parameter Documentation */
    private static final String NO_AVERAGE = "-";

    //~ Instance fields ----------------------------------------------------------------------------

    private String[] columnNames = null;
    private Object[][] data = null;
    private int m_beginSeason;
    private int m_endSeason;
    private int m_matchType;
    private int m_typePlayer;
    private int playersNumber;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new MainTableModel object.
     *
     * @param matchType TODO Missing Constructuor Parameter Documentation
     * @param beginSeason TODO Missing Constructuor Parameter Documentation
     * @param endSeason TODO Missing Constructuor Parameter Documentation
     * @param typePlayer TODO Missing Constructuor Parameter Documentation
     */
    public PlayersTableModel(int matchType, int beginSeason, int endSeason, int typePlayer) {
        m_matchType = matchType;
        m_beginSeason = beginSeason;
        m_endSeason = endSeason;

        columnNames = new String[cols];

        // Riempimento valori
        columnNames[COL_ID] = Commons.getModel().getResource().getProperty("ID");
        columnNames[COL_NAME] = Commons.getModel().getResource().getProperty("Spieler");
        columnNames[COL_AGREEABILITY] = Commons.getModel().getResource().getProperty("Ansehen");
        columnNames[COL_AGGRESSIVITY] = Commons.getModel().getResource().getProperty("Aggressivitaet");
        columnNames[COL_HONESTY] = Commons.getModel().getResource().getProperty("Charakter");
        columnNames[COL_CARDS] = PluginProperty.getString("column.CardsTotal");
        columnNames[COL_DIRECT_RED_CARDS] = PluginProperty.getString("column.RedCards");
        columnNames[COL_WARNINGS] = PluginProperty.getString("column.Warnings");
        columnNames[COL_WARNINGS_TYPE1] = PluginProperty.getString("column.WarningType1");
        columnNames[COL_WARNINGS_TYPE2] = PluginProperty.getString("column.WarningType2");
        columnNames[COL_WARNINGS_TYPE3] = PluginProperty.getString("column.WarningType3");
        columnNames[COL_WARNINGS_TYPE4] = PluginProperty.getString("column.WarningType4");
        columnNames[COL_RAW_AVERAGE] = PluginProperty.getString("column.RawAverage");
        columnNames[COL_WEIGHTED_AVERAGE] = PluginProperty.getString("column.WeightedAverage");
        columnNames[COL_MATCHES] = PluginProperty.getString("column.MatchCount");

        refresh(typePlayer);
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
        return getValueAt(0, c).getClass();

        // return Object.class;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param c TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getColumnName(int c) {
        return columnNames[c];
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
     * @param filterMode TODO Missing Method Parameter Documentation
     */
    public void refresh(int filterMode) {
        this.m_typePlayer = filterMode;

        this.generateData2();
        this.fireTableDataChanged();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param row TODO Missing Method Parameter Documentation
     */
    private void aggiornaMedie(int row) {
        int matches = ((Integer) data[row][COL_MATCHES]).intValue();

        if (matches > 0) {
            int warnings = ((Integer) data[row][COL_WARNINGS]).intValue();
            int direct_reds = ((Integer) data[row][COL_DIRECT_RED_CARDS]).intValue();
            int total = ((Integer) data[row][COL_CARDS]).intValue();

            //data[row][COL_RAW_AVERAGE] = new Double(approssima((total * 100.0) / matches)) + PERCENT;
            //data[row][COL_WEIGHTED_AVERAGE] = new Double(approssima(((warnings + (direct_reds * 2)) * 100.0) / (matches))) + PERCENT;
            String fill = "";
            double val = (approssima((total * 100.0) / matches));
            if (val < 10) {
            	fill = "  ";
            } else if (val <100) {
            	fill = " ";
            }

            data[row][COL_RAW_AVERAGE] = fill + new Double(val) + PERCENT;

            val = approssima(((warnings + (direct_reds * 2)) * 100.0) / (matches));
            fill = "";
            if (val < 10) {
            	fill = "  ";
            } else if (val <100) {
            	fill = " ";
            }
            data[row][COL_WEIGHTED_AVERAGE] = fill + new Double(val) + PERCENT;
        } else {
            data[row][COL_RAW_AVERAGE] = NO_AVERAGE;
            data[row][COL_WEIGHTED_AVERAGE] = NO_AVERAGE;
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param valore TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private double approssima(double valore) {
        long approx = (new Double(valore * 10.0)).longValue();
        return approx / 10.0;
    }

    /**
     * TODO Missing Method Documentation
     */
    private void generateData2() {
        // Get current players.
        Vector players = new Vector();
        players.addAll(Commons.getModel().getAllSpieler());

        // Add old players, when requested.
        if (m_typePlayer == TYPE_ALL_PLAYERS) {
            players.addAll(Commons.getModel().getAllOldSpieler());
        }

        playersNumber = players.size();
        //DebugWindow.setInfoText("players=" + playersNumber);

        // Reset table data.
        data = new Object[playersNumber][cols];

        for (int row = 0; row < playersNumber; row++) {
            // giocatore
            ISpieler player = (ISpieler) players.get(row);

            int id = player.getSpielerID();

            data[row][COL_NAME] = player.getName();
            data[row][COL_ID] = new Integer(id);
            data[row][COL_AGGRESSIVITY] = "(" + player.getAgressivitaet() + ") "
                                          + player.getAgressivitaetString();
            data[row][COL_HONESTY] = "(" + player.getAnsehen() + ") " + player.getAnsehenString();
            data[row][COL_AGREEABILITY] = "(" + player.getCharakter() + ") "
                                          + player.getCharakterString();

            for (int col = 5; col < cols; col++) {
                data[row][col] = new Integer(0);
            }

            // GESTIONE CARTELLINI
            String sql = "SELECT TYP, SUBTYP, MATCHID FROM MATCHHIGHLIGHTS WHERE TYP = "
                         + IMatchHighlight.HIGHLIGHT_KARTEN + " AND SPIELERID = " + id;

            // String filter = " AND MATCHID IN (SELECT MATCHID FROM PAARUNG
            // WHERE SAISON = 25)";
            // sql += filter;
            ResultSet res = Commons.getModel().getAdapter().executeQuery(sql);

            try {
                int matchid = 0;
                int cartellino = 0;

                while (res.next()) {
                    int typeHighlight = res.getInt("TYP");
                    int subtypeHighlight = res.getInt("SUBTYP");
                    int matchidlast = res.getInt("MATCHID");

                    if (matchid != matchidlast) {
                        cartellino = 0;
                        matchid = matchidlast;
                    }

                    incrementaValoreColonna(row, COL_CARDS);

                    switch (subtypeHighlight) {
                        // prima ammonizione : fallaccio
                        case IMatchHighlight.HIGHLIGHT_SUB_GELB_HARTER_EINSATZ:
                            incrementaValoreColonna(row, COL_WARNINGS_TYPE1);
                            incrementaValoreColonna(row, COL_WARNINGS);
                            cartellino++;
                            break;

                        // seconda ammonizione : fallaccio
                        case IMatchHighlight.HIGHLIGHT_SUB_GELB_ROT_HARTER_EINSATZ:
                            incrementaValoreColonna(row, COL_WARNINGS_TYPE2);
                            incrementaValoreColonna(row, COL_WARNINGS);
                            incrementaValoreColonna(row, COL_MATCHES);
                            cartellino++;
                            break;

                        // prima ammonizione : scorretto
                        case IMatchHighlight.HIGHLIGHT_SUB_GELB_UNFAIR:
                            incrementaValoreColonna(row, COL_WARNINGS_TYPE3);
                            incrementaValoreColonna(row, COL_WARNINGS);
                            cartellino++;
                            break;

                        // seconda ammonizione : scorretto
                        case IMatchHighlight.HIGHLIGHT_SUB_GELB_ROT_UNFAIR:
                            incrementaValoreColonna(row, COL_WARNINGS_TYPE4);
                            incrementaValoreColonna(row, COL_WARNINGS);
                            incrementaValoreColonna(row, COL_MATCHES);
                            cartellino++;
                            break;

                        // esplusione diretta
                        case IMatchHighlight.HIGHLIGHT_SUB_ROT:
                            incrementaValoreColonna(row, COL_DIRECT_RED_CARDS);
                            incrementaValoreColonna(row, COL_MATCHES);
                            break;

                        // infortunio
                        case IMatchHighlight.HIGHLIGHT_SUB_VERLETZT_SCHWER:
                            incrementaValoreColonna(row, COL_MATCHES);
                            break;
                    }
                }
            } // try

            catch (SQLException e) {
                DebugWindow.debug(e);
            }

            // GESTIONE PARTITE GIOCATE
            // quelli che vengono espulsi e quelli che vengono sostituiti non
            // risultano
            // aver giocato ! quindi bisogna aggiunstare in altro modo
            sql = "SELECT POSITIONCODE FROM MATCHLINEUPPLAYER WHERE SPIELERID = " + id;

            res = Commons.getModel().getAdapter().executeQuery(sql);

            try {
                while (res.next()) {
                    int codpos = res.getInt("POSITIONCODE");

                    if (codpos != -1) {
                        data[row][COL_MATCHES] = new Integer(((Integer) data[row][COL_MATCHES])
                                                             .intValue() + 1);
                    }
                }
            } catch (SQLException e1) {
                DebugWindow.debug(e1);
            }

            // GESITONE MEDIE
            aggiornaMedie(row);
        }

        // for (per tutti i giocatori)
    }

    // generate

    /**
     * TODO Missing Method Documentation
     *
     * @param rowFind TODO Missing Method Parameter Documentation
     * @param colonna TODO Missing Method Parameter Documentation
     */
    private void incrementaValoreColonna(int rowFind, int colonna) {
        // solo se presenti
        if (rowFind != -1) {
            data[rowFind][colonna] = new Integer(((Integer) data[rowFind][colonna]).intValue() + 1);
        }
    }
}
