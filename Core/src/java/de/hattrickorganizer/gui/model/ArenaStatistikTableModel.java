// %534638504:de.hattrickorganizer.gui.model%
package de.hattrickorganizer.gui.model;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

import plugins.IMatchLineup;
import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.gui.templates.ProgressbarTableEntry;
import de.hattrickorganizer.model.matches.MatchKurzInfo;


/**
 * DOCUMENT ME!
 *
 * @author Volker Fischer
 * @version 0.2a    31.10.2001
 */
public class ArenaStatistikTableModel extends AbstractTableModel {
    //~ Static fields/initializers -----------------------------------------------------------------

	private static final long serialVersionUID = 7187251269604772672L;

	/** TODO Missing Parameter Documentation */
    public static final java.awt.Color FG_EIGENESTEAM = new java.awt.Color(50, 50, 150);

    /** TODO Missing Parameter Documentation */

    //	League match.	
    public static final Color LIGASPIEL = new Color(255, 255, 200);

    /** TODO Missing Parameter Documentation */

    //	Qualification match.	
    public static final Color QUALISPIEL = new Color(255, 200, 200);

    /** TODO Missing Parameter Documentation */

    //	Cup match (standard league cup).	
    public static final Color POKALSPIEL = new Color(200, 255, 200);

    /** TODO Missing Parameter Documentation */

    //	Friendly (normal rules).	
    public static final Color TESTSPIEL = Color.white;

    /** TODO Missing Parameter Documentation */

    //	Friendly (cup rules).	
    public static final Color TESTPOKALSPIEL = Color.white;

    /** TODO Missing Parameter Documentation */

    //	Not currently in use, but reserved for international competition matches with normal rules (may or may not be implemented at some future point).	
    public static final Color INTSPIEL = Color.lightGray;

    /** TODO Missing Parameter Documentation */

    //	Not currently in use, but reserved for international competition matches with cup rules (may or may not be implemented at some future point).	
    public static final Color INTCUPSPIEL = Color.lightGray;

    /** TODO Missing Parameter Documentation */

    //	International friendly (normal rules).	
    public static final Color INT_TESTSPIEL = Color.white;

    /** TODO Missing Parameter Documentation */

    //	International friendly (cup rules).	
    public static final Color INT_TESTCUPSPIEL = Color.white;

    /** TODO Missing Parameter Documentation */

    //	National teams competition match (normal rules).	
    public static final Color LAENDERSPIEL = new Color(220, 220, 255);

    /** TODO Missing Parameter Documentation */

    //	National teams competition match (cup rules).	
    public static final Color LAENDERCUPSPIEL = new Color(220, 220, 255);

    /** TODO Missing Parameter Documentation */

    //	National teams friendly.
    public static final Color TESTLAENDERSPIEL = new Color(220, 220, 255);

    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public String[] m_sToolTipStrings = {
                                            de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Datum"),
                                            

    //Spielart
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Spielart"),
                                            

    //Gast
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Gast"),
                                            

    //Ergebnis
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Ergebnis"), 
    //Wetter
    "", 
    //MatchId
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("ID"),
                                            

    //Stadiongroesse
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Aktuell"),
                                            

    //Zuschauer
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Zuschauer"), 
    //Auslastung
    "%", 
    //Fananzahl
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Fans"),
                                            
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Fans") + " / "
                                            + de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Wochen"),
                                            
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Zuschauer")
                                            + " / "
                                            + de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Fans"),
                                            

    //Stimmung
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Fans"),
                                            

    //LigaPlatz
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Platzierung")
                                        };

    /** TODO Missing Parameter Documentation */
    protected Object[][] m_clData;

    /** TODO Missing Parameter Documentation */
    protected String[] m_sColumnNames = {
                                            de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Datum"),
                                            

    //Spielart
    "", 
    //Gast
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Gast"),
                                            

    //Ergebnis
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Ergebnis"), 
    //Wetter
    "", 
    //MatchId
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("ID"),
                                            

    //Stadiongroesse
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Aktuell"),
                                            

    //Zuschauer
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Zuschauer"), 
    //Auslastung
    "%", 
    //Fananzahl
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Fans"),
                                            
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Fans") + " / "
                                            + de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Wochen"),
                                            
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Zuschauer")
                                            + " / "
                                            + de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Fans"),
                                            

    //Stimmung
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Fans"),
                                            

    //LigaPlatz
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Platzierung")
                                        };
    private ArenaStatistikModel[] m_clMatches;
    private int m_iMaxArenaGroesse;
    private int m_iMaxFananzahl;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new ArenaStatistikTableModel object.
     */
    public ArenaStatistikTableModel() {
        //Nix
    }

    /**
     * Creates a new ArenaStatistikTableModel object.
     *
     * @param matches TODO Missing Constructuor Parameter Documentation
     * @param maxAreanGroesse TODO Missing Constructuor Parameter Documentation
     * @param maxFananzahl TODO Missing Constructuor Parameter Documentation
     */
    public ArenaStatistikTableModel(ArenaStatistikModel[] matches, int maxAreanGroesse,
                                    int maxFananzahl) {
        m_clMatches = matches;
        m_iMaxArenaGroesse = maxAreanGroesse;
        m_iMaxFananzahl = maxFananzahl;
        initData();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param row TODO Missing Method Parameter Documentation
     * @param col TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean isCellEditable(int row, int col) {
        return false;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param columnIndex TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Class getColumnClass(int columnIndex) {
        final Object obj = getValueAt(0, columnIndex);

        if (obj != null) {
            return obj.getClass();
        }

        return "".getClass();
    }

    //-----Zugriffsmethoden----------------------------------------        
    public final int getColumnCount() {
        return m_sColumnNames.length;

        /*
           if ( m_clData!=null && m_clData.length > 0 && m_clData[0] != null )
               return m_clData[0].length;
           else
               return 0;
         */
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param columnIndex TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final String getColumnName(int columnIndex) {
        if ((m_sColumnNames != null) && (m_sColumnNames.length > columnIndex)) {
            return m_sColumnNames[columnIndex];
        } else {
            return null;
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param id TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final ArenaStatistikModel getMatch(int id) {
        if (id > 0) {
            for (int i = 0; i < m_clMatches.length; i++) {
                if (m_clMatches[i].getMatchID() == id) {
                    return m_clMatches[i];
                }
            }
        }

        return null;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getRowCount() {
        return (m_clData != null) ? m_clData.length : 0;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param row TODO Missing Method Parameter Documentation
     * @param columnName TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Object getValue(int row, String columnName) {
        if ((m_sColumnNames != null) && (m_clData != null)) {
            int i = 0;

            while ((i < m_sColumnNames.length) && !m_sColumnNames[i].equals(columnName)) {
                i++;
            }

            return m_clData[row][i];
        } else {
            return null;
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     * @param row TODO Missing Method Parameter Documentation
     * @param column TODO Missing Method Parameter Documentation
     */
    public final void setValueAt(Object value, int row, int column) {
        m_clData[row][column] = value;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param row TODO Missing Method Parameter Documentation
     * @param column TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Object getValueAt(int row, int column) {
        if (m_clData != null) {
            return m_clData[row][column];
        }

        return null;
    }

    /**
     * Matches neu setzen
     *
     * @param matches TODO Missing Constructuor Parameter Documentation
     * @param maxAreanGroesse TODO Missing Constructuor Parameter Documentation
     * @param maxFananzahl TODO Missing Constructuor Parameter Documentation
     */
    public final void setValues(ArenaStatistikModel[] matches, int maxAreanGroesse, int maxFananzahl) {
        m_clMatches = matches;
        m_iMaxArenaGroesse = maxAreanGroesse;
        m_iMaxFananzahl = maxFananzahl;
        initData();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param typ TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private Color getColor4Matchtyp(int typ) {
        switch (typ) {
            case IMatchLineup.LIGASPIEL:
                return LIGASPIEL;

            case IMatchLineup.POKALSPIEL:
                return POKALSPIEL;

            case IMatchLineup.QUALISPIEL:
                return QUALISPIEL;

            case IMatchLineup.LAENDERCUPSPIEL:
                return LAENDERCUPSPIEL;

            case IMatchLineup.INTCUPSPIEL:
                return INTCUPSPIEL;

            case IMatchLineup.LAENDERSPIEL:
                return LAENDERSPIEL;

            case IMatchLineup.INTSPIEL:
                return INTSPIEL;

            case IMatchLineup.INT_TESTCUPSPIEL:
                return INT_TESTCUPSPIEL;

            case IMatchLineup.INT_TESTSPIEL:
                return INT_TESTSPIEL;

            case IMatchLineup.TESTLAENDERSPIEL:
                return TESTLAENDERSPIEL;

            case IMatchLineup.TESTPOKALSPIEL:
                return TESTPOKALSPIEL;

            case IMatchLineup.TESTSPIEL:
                return TESTSPIEL;

            //Fehler?
            default:
                return Color.white;
        }
    }

    /**
     * Gibt eine String zurück, der die Tore darstellt
     *
     * @param heim TODO Missing Constructuor Parameter Documentation
     * @param gast TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private String createTorString(int heim, int gast) {
        final StringBuffer buffer = new StringBuffer();

        if ((heim < 0) || (gast < 0)) {
            return "-";
        }

        if (heim < 10) {
            buffer.append(" ");
        }

        if (heim >= 0) {
            buffer.append(heim);
        }

        buffer.append(" : ");

        if (gast < 10) {
            buffer.append(" ");
        }

        if (gast >= 0) {
            buffer.append(gast);
        }

        return buffer.toString();
    }

    //-----initialisierung-----------------------------------------

    /**
     * Erzeugt einen Data[][] aus dem Spielervector
     */
    private void initData() {
        m_clData = new Object[m_clMatches.length][m_sColumnNames.length];

        for (int i = 0; i < m_clMatches.length; i++) {
            final ArenaStatistikModel match = m_clMatches[i];

            final Color background = getColor4Matchtyp(match.getMatchTyp());

            //Datum
            m_clData[i][0] = new ColorLabelEntry(match.getMatchDateAsTimestamp().getTime(),
                                                 java.text.DateFormat.getDateTimeInstance().format(match
                                                                                                   .getMatchDateAsTimestamp()),
                                                 ColorLabelEntry.FG_STANDARD, background,
                                                 JLabel.LEFT);

            //Spielart
            m_clData[i][1] = new ColorLabelEntry(de.hattrickorganizer.tools.Helper
                                                 .getImageIcon4Spieltyp(match.getMatchTyp()),
                                                 match.getMatchTyp(), ColorLabelEntry.FG_STANDARD,
                                                 background, JLabel.CENTER);

            //Gast
            m_clData[i][2] = new ColorLabelEntry(match.getGastName(), ColorLabelEntry.FG_STANDARD,
                                                 background, JLabel.LEFT);

            //Ergebnis
            m_clData[i][3] = new ColorLabelEntry(createTorString(match.getHeimTore(),
                                                                 match.getGastTore()),
                                                 ColorLabelEntry.FG_STANDARD, background,
                                                 JLabel.CENTER);

            //Sterne für Sieger!
            if (match.getMatchStatus() != MatchKurzInfo.FINISHED) {
                ((ColorLabelEntry) m_clData[i][3]).setIcon(de.hattrickorganizer.tools.Helper.NOIMAGEICON);
            } else if (match.getHeimTore() > match.getGastTore()) {
                ((ColorLabelEntry) m_clData[i][3]).setIcon(de.hattrickorganizer.tools.Helper.YELLOWSTARIMAGEICON);
            } else if (match.getHeimTore() < match.getGastTore()) {
                ((ColorLabelEntry) m_clData[i][3]).setIcon(de.hattrickorganizer.tools.Helper.NOIMAGEICON);
            } else {
                ((ColorLabelEntry) m_clData[i][3]).setIcon(de.hattrickorganizer.tools.Helper.GREYSTARIMAGEICON);
            }

            //Wetter
            m_clData[i][4] = new ColorLabelEntry(de.hattrickorganizer.tools.Helper
                                                 .getImageIcon4Wetter(match.getWetter()),
                                                 match.getWetter(), ColorLabelEntry.FG_STANDARD,
                                                 background, JLabel.RIGHT);

            //Matchid
            m_clData[i][5] = new ColorLabelEntry(match.getMatchID(), match.getMatchID() + "",
                                                 ColorLabelEntry.FG_STANDARD, background,
                                                 JLabel.RIGHT);

            //Stadiongroesse
            m_clData[i][6] = new ProgressbarTableEntry(match.getArenaGroesse(), 0,
                                                       m_iMaxArenaGroesse, 0, 1, background,
                                                       new Color(0, 0, 120), "");

            //Zuschauer
            m_clData[i][7] = new ProgressbarTableEntry(match.getZuschaueranzahl(), 0,
                                                       m_iMaxArenaGroesse, 0, 1, background,
                                                       new Color(0, 120, 0), "");

            //Verhältnis Auslastung
            m_clData[i][8] = new ProgressbarTableEntry((int) ((float) match.getZuschaueranzahl() / (float) match
                                                                                                   .getArenaGroesse() * 1000),
                                                       0, 1000, 1, 0.1, background,
                                                       new Color(0, 120, 120), " %");

            //Fananzahl
            m_clData[i][9] = new ProgressbarTableEntry(match.getFans(), 0, m_iMaxFananzahl, 0, 1,
                                                       background, new Color(80, 80, 80), "");

            //Fanzuwachs pro Woche
            float fanzuwachs = 0;

            if ((i + 1) < m_clMatches.length) {
                fanzuwachs = ((float) (match.getFans() - m_clMatches[i + 1].getFans()) * 604800000f) / (match.getMatchDateAsTimestamp()
                                                                                                             .getTime()
                             - m_clMatches[i + 1].getMatchDateAsTimestamp().getTime());
            }

            m_clData[i][10] = new ColorLabelEntry(fanzuwachs, background, false);

            //Quotione  Zuschauer/Fans
            m_clData[i][11] = new ColorLabelEntry(de.hattrickorganizer.tools.Helper.round((float) match
                                                                                          .getZuschaueranzahl() / (float) match
                                                                                                                  .getFans(),
                                                                                          2) + "",
                                                  ColorLabelEntry.FG_STANDARD, background,
                                                  JLabel.RIGHT);

            //Fanstimmung
            m_clData[i][12] = new ColorLabelEntry(de.hattrickorganizer.model.Finanzen
                                                  .getNameForLevelFans(match.getFanZufriedenheit(), match.getMatchDateAsTimestamp()),
                                                  ColorLabelEntry.FG_STANDARD, background,
                                                  JLabel.LEFT);

            //Ligaplatz
            m_clData[i][13] = new ColorLabelEntry(match.getLigaPlatz() + ".",
                                                  ColorLabelEntry.FG_STANDARD, background,
                                                  JLabel.CENTER);
        }
    }
    
    
	public ArenaStatistikModel[] getMatches() {
		return m_clMatches;
	}

}
