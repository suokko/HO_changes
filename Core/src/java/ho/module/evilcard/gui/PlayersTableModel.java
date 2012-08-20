package ho.module.evilcard.gui;

import ho.core.constants.player.PlayerAggressiveness;
import ho.core.constants.player.PlayerAgreeability;
import ho.core.constants.player.PlayerHonesty;
import ho.core.db.DBManager;
import ho.core.gui.model.SpielerMatchCBItem;
import ho.core.model.HOVerwaltung;
import ho.core.model.match.IMatchHighlight;
import ho.core.model.match.MatchHighlight;
import ho.core.model.player.Spieler;

import java.util.Iterator;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;


class PlayersTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 146702588079846156L;


    static final int COL_ID = 0;
    static final int COL_NAME = 1;
    static final int COL_AGREEABILITY = 2;
    static final int COL_AGGRESSIVITY = 3;
    static final int COL_HONESTY = 4;
    static final int COL_CARDS = 5;
    static final int COL_DIRECT_RED_CARDS = 6;
    static final int COL_WARNINGS = 7;
    static final int COL_WARNINGS_TYPE1 = 8;
    static final int COL_WARNINGS_TYPE2 = 9;
    static final int COL_WARNINGS_TYPE3 = 10;
    static final int COL_WARNINGS_TYPE4 = 11;
    static final int COL_RAW_AVERAGE = 12;
    static final int COL_WEIGHTED_AVERAGE = 13;
    static final int COL_MATCHES = 14;
    static final int TYPE_CURRENT_PLAYERS = 1;
    static final int TYPE_ALL_PLAYERS = 2;
    static final int TYPE_ALL_MATCHES = 1;
    static final int TYPE_FRIENDLIES = 2;
    static final int TYPE_OFFICIAL_MATCHES = 3;
    static final int cols = 15;
    static final String PERCENT = " %";
    static final String NO_AVERAGE = "-";

    private String[] columnNames = null;
    private Object[][] data = null;
    private int m_typePlayer;
    private int playersNumber;

    PlayersTableModel(int matchType, int beginSeason, int endSeason, int typePlayer) {
        columnNames = new String[cols];
        
        // Riempimento valori
        columnNames[COL_ID] = HOVerwaltung.instance().getLanguageString("ID");
        columnNames[COL_NAME] = HOVerwaltung.instance().getLanguageString("Spieler");
        columnNames[COL_AGREEABILITY] = HOVerwaltung.instance().getLanguageString("Ansehen");
        columnNames[COL_AGGRESSIVITY] = HOVerwaltung.instance().getLanguageString("Aggressivitaet");
        columnNames[COL_HONESTY] = HOVerwaltung.instance().getLanguageString("Charakter");
        columnNames[COL_CARDS] = HOVerwaltung.instance().getLanguageString("Gesamt");
        columnNames[COL_DIRECT_RED_CARDS] = HOVerwaltung.instance().getLanguageString("column.RedCards");
        columnNames[COL_WARNINGS] = HOVerwaltung.instance().getLanguageString("GelbeKarten");
        columnNames[COL_WARNINGS_TYPE1] = HOVerwaltung.instance().getLanguageString("column.WarningType1");
        columnNames[COL_WARNINGS_TYPE2] = HOVerwaltung.instance().getLanguageString("column.WarningType2");
        columnNames[COL_WARNINGS_TYPE3] = HOVerwaltung.instance().getLanguageString("column.WarningType3");
        columnNames[COL_WARNINGS_TYPE4] = HOVerwaltung.instance().getLanguageString("column.WarningType4");
        columnNames[COL_RAW_AVERAGE] = HOVerwaltung.instance().getLanguageString("column.RawAverage");
        columnNames[COL_WEIGHTED_AVERAGE] = HOVerwaltung.instance().getLanguageString("column.WeightedAverage");
        columnNames[COL_MATCHES] = HOVerwaltung.instance().getLanguageString("Spiele_kurz");

        refresh(typePlayer);
    }

    @Override
	public Class<?> getColumnClass(int c) {
        return getValueAt(0, c).getClass();

        // return Object.class;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
	public String getColumnName(int c) {
        return columnNames[c];
    }

    public int getRowCount() {
        return data.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    public void refresh(int filterMode) {
        this.m_typePlayer = filterMode;

        this.generateData2();
        this.fireTableDataChanged();
    }

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

    private double approssima(double valore) {
        long approx = (new Double(valore * 10.0)).longValue();
        return approx / 10.0;
    }

    /**
     * Helper class to sort and show aggressive information
     *
     */
    private class Aggressive implements Comparable<Aggressive> {
        private Spieler _player;
        Aggressive(Spieler player) {
            _player = player;
        }
 
        @Override
        public String toString() {
            return PlayerAggressiveness.toString(_player.getAgressivitaet())+" (" + _player.getAgressivitaet() + ")";
        }

        @Override
        public int compareTo(Aggressive o2) {
            Spieler p1 = _player;
            Spieler p2 = o2._player;

            if (p1.getAgressivitaet() == p2.getAgressivitaet())
                return 0;
            return p1.getAgressivitaet() < p2.getAgressivitaet() ? -1 : 1;
        }
    }

    /**
     * Helper class to sort and show agreeability information
     *
     */
    private class Agreeability implements Comparable<Agreeability> {
        private Spieler _player;
        Agreeability(Spieler player) {
            _player = player;
        }
 
        @Override
        public String toString() {
            return PlayerAgreeability.toString(_player.getAnsehen())+" (" + _player.getAnsehen() + ")";
        }

        @Override
        public int compareTo(Agreeability o2) {
            Spieler p1 = _player;
            Spieler p2 = o2._player;

            if (p1.getAnsehen() == p2.getAnsehen())
                return 0;
            return p1.getAnsehen() < p2.getAnsehen() ? -1 : 1;
        }
    }

    /**
     * Helper class to sort and show honesty information
     *
     */
    private class Honesty implements Comparable<Honesty> {
        private Spieler _player;
        Honesty(Spieler player) {
            _player = player;
        }
 
        @Override
        public String toString() {
            return PlayerHonesty.toString(_player.getCharakter())+" (" + _player.getCharakter() + ")";
        }

        @Override
        public int compareTo(Honesty o2) {
            Spieler p1 = _player;
            Spieler p2 = o2._player;

            if (p1.getCharakter() == p2.getCharakter())
                return 0;
            return p1.getCharakter() < p2.getCharakter() ? -1 : 1;
        }
    }

    private void generateData2() {
        // Get current players.
        Vector<Spieler> players = new Vector<Spieler>();
        		players.addAll(HOVerwaltung.instance().getModel().getAllSpieler());

        // Add old players, when requested.
        if (m_typePlayer == TYPE_ALL_PLAYERS) {
            players.addAll(HOVerwaltung.instance().getModel().getAllOldSpieler());
        }

        playersNumber = players.size();

        // Reset table data.
        data = new Object[playersNumber][cols];

        for (int row = 0; row < playersNumber; row++) {
            // giocatore
            Spieler player = (Spieler) players.get(row);

            int id = player.getSpielerID();

            data[row][COL_NAME] = player.getName();
            data[row][COL_ID] = new Integer(id);
            data[row][COL_AGGRESSIVITY] = new Aggressive(player);
            data[row][COL_HONESTY] = new Honesty(player);
            data[row][COL_AGREEABILITY] = new Agreeability(player);

            for (int col = 5; col < cols; col++) {
                data[row][col] = new Integer(0);
            }

            // GESTIONE CARTELLINI
            //String sql = "SELECT TYP, SUBTYP, MATCHID FROM MATCHHIGHLIGHTS WHERE TYP = "                      + IMatchHighlight.HIGHLIGHT_KARTEN + " AND SPIELERID = " + id;
            Vector<MatchHighlight> highlights = DBManager.instance().getMatchHighlightsByTypIdAndPlayerId(IMatchHighlight.HIGHLIGHT_KARTEN, id);
            // String filter = " AND MATCHID IN (SELECT MATCHID FROM PAARUNG
            // WHERE SAISON = 25)";
            // sql += filter;
            //ResultSet res = DBManager.instance().getAdapter().executeQuery(sql);

             int matchid = 0;
             int cartellino = 0;

             for (Iterator<MatchHighlight> iterator = highlights.iterator(); iterator.hasNext();) {
					MatchHighlight matchHighlight = iterator.next();
                    //int typeHighlight = res.getInt("TYP");
                    int subtypeHighlight = matchHighlight.getHighlightSubTyp();//res.getInt("SUBTYP");
                    int matchidlast = matchHighlight.getMatchId();//res.getInt("MATCHID");

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
            
             Vector<SpielerMatchCBItem> matches = DBManager.instance().getSpieler4Matches(id);
             data[row][COL_MATCHES] = matches.size();

            // GESITONE MEDIE
            aggiornaMedie(row);
        }

        // for (per tutti i giocatori)
    }

    private void incrementaValoreColonna(int rowFind, int colonna) {
        // solo se presenti
        if (rowFind != -1) {
            data[rowFind][colonna] = new Integer(((Integer) data[rowFind][colonna]).intValue() + 1);
        }
    }
}
