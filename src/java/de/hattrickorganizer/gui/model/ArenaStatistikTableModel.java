// %534638504:de.hattrickorganizer.gui.model%
package de.hattrickorganizer.gui.model;

import java.awt.Color;
import java.text.DateFormat;

import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;

import plugins.IMatchKurzInfo;
import plugins.IMatchLineup;
import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.gui.templates.ProgressbarTableEntry;
import de.hattrickorganizer.model.Finanzen;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.Helper;


/**
 * DOCUMENT ME!
 *
 * @author Volker Fischer
 * @version 0.2a    31.10.2001
 */
public class ArenaStatistikTableModel extends AbstractTableModel {
    //~ Static fields/initializers -----------------------------------------------------------------

	private static final long serialVersionUID = 7187251269604772672L;

    public static final java.awt.Color FG_EIGENESTEAM = new java.awt.Color(50, 50, 150);

    //	League match.
    public static final Color LIGASPIEL = new Color(255, 255, 200);
    //	Qualification match.
    public static final Color QUALISPIEL = new Color(255, 200, 200);
    //	Cup match (standard league cup).
    public static final Color POKALSPIEL = new Color(200, 255, 200);
    //	Friendly (normal rules).
    public static final Color TESTSPIEL = Color.white;
    //	Friendly (cup rules).
    public static final Color TESTPOKALSPIEL = Color.white;
    //	Not currently in use, but reserved for international competition matches with normal rules (may or may not be implemented at some future point).
    public static final Color INTSPIEL = Color.lightGray;
    //	Not currently in use, but reserved for international competition matches with cup rules (may or may not be implemented at some future point).
    public static final Color INTCUPSPIEL = Color.lightGray;
    //	International friendly (normal rules).
    public static final Color INT_TESTSPIEL = Color.white;
    //	International friendly (cup rules).
    public static final Color INT_TESTCUPSPIEL = Color.white;
    //	National teams competition match (normal rules).
    public static final Color LAENDERSPIEL = new Color(220, 220, 255);
    //	National teams competition match (cup rules).
    public static final Color LAENDERCUPSPIEL = new Color(220, 220, 255);
    //	National teams friendly.
    public static final Color TESTLAENDERSPIEL = new Color(220, 220, 255);

    //~ Instance fields ----------------------------------------------------------------------------

    public String[] m_sToolTipStrings = {
			HOVerwaltung.instance().getLanguageString("Datum"),
			// Spielart
			HOVerwaltung.instance().getLanguageString("Spielart"),
			// Gast
			HOVerwaltung.instance().getLanguageString("Gast"),
			// Ergebnis
			HOVerwaltung.instance().getLanguageString("Ergebnis"),
			// Wetter
			"",
			// MatchId
			HOVerwaltung.instance().getLanguageString("ID"),
			// Stadiongroesse
			HOVerwaltung.instance().getLanguageString("Aktuell"),
			// Zuschauer
			HOVerwaltung.instance().getLanguageString("Zuschauer"),
			// Auslastung
			"%",
			// Fananzahl
			HOVerwaltung.instance().getLanguageString("Fans"),
			HOVerwaltung.instance().getLanguageString("Fans") + " / "
					+ HOVerwaltung.instance().getLanguageString("Wochen"),
			HOVerwaltung.instance().getLanguageString("Zuschauer") + " / "
					+ HOVerwaltung.instance().getLanguageString("Fans"),
			// Stimmung
			HOVerwaltung.instance().getLanguageString("Fans"),
			// LigaPlatz
			HOVerwaltung.instance().getLanguageString("Platzierung") };

    protected Object[][] m_clData;

    protected String[] m_sColumnNames = {
			HOVerwaltung.instance().getLanguageString("Datum"),
			"", // Spielart
			HOVerwaltung.instance().getLanguageString("Gast"), // Gast
			HOVerwaltung.instance().getLanguageString("Ergebnis"), // Ergebnis
			"", // Wetter
			HOVerwaltung.instance().getLanguageString("ID"), // MatchId
			HOVerwaltung.instance().getLanguageString("Aktuell"), // Stadiongroesse
			HOVerwaltung.instance().getLanguageString("Zuschauer"), // Zuschauer
			"%", // Auslastung
			HOVerwaltung.instance().getLanguageString("Fans"), // Fananzahl
			HOVerwaltung.instance().getLanguageString("Fans") + " / "
					+ HOVerwaltung.instance().getLanguageString("Wochen"),
			HOVerwaltung.instance().getLanguageString("Zuschauer") + " / "
					+ HOVerwaltung.instance().getLanguageString("Fans"),
			HOVerwaltung.instance().getLanguageString("Fans"), // Stimmung
			HOVerwaltung.instance().getLanguageString("Platzierung") // LigaPlatz
    };
    private ArenaStatistikModel[] m_clMatches;
    private int m_iMaxArenaGroesse;
    private int m_iMaxFananzahl;

    // ~ Constructors
	// -------------------------------------------------------------------------------

    /**
     * Creates a new ArenaStatistikTableModel object.
     */
    public ArenaStatistikTableModel() {
        //Nix
    }

    /**
     * Creates a new ArenaStatistikTableModel object.
     */
    public ArenaStatistikTableModel(ArenaStatistikModel[] matches,
    		int maxAreanGroesse, int maxFananzahl) {
        m_clMatches = matches;
        m_iMaxArenaGroesse = maxAreanGroesse;
        m_iMaxFananzahl = maxFananzahl;
        initData();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Check, if cell is editable.
     */
    @Override
	public final boolean isCellEditable(int row, int col) {
        return false;
    }

    /**
     * Get the class for a column.
     */
    @Override
	public final Class<?> getColumnClass(int columnIndex) {
        final Object obj = getValueAt(0, columnIndex);

        if (obj != null) {
            return obj.getClass();
        }

        return "".getClass();
    }

    /**
     * Get amount of columns.
     */
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
     * Get the column name.
     */
    @Override
	public final String getColumnName(int columnIndex) {
        if ((m_sColumnNames != null) && (m_sColumnNames.length > columnIndex)) {
            return m_sColumnNames[columnIndex];
        } else {
            return null;
        }
    }

    /**
     * Get the ArenaStatistikModel for a certain match.
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
     * Get amount of rows.
     */
    public final int getRowCount() {
        return (m_clData != null) ? m_clData.length : 0;
    }

    /**
     * Get the value at a certain row and column.
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
     * Set the value at a certain row and column.
     */
    @Override
	public final void setValueAt(Object value, int row, int column) {
        m_clData[row][column] = value;
    }

    /**
     * Get the value at a certain row and column.
     */
    public final Object getValueAt(int row, int column) {
        if (m_clData != null) {
            return m_clData[row][column];
        }
        return null;
    }

    /**
     * Matches neu setzen
     */
    public final void setValues(ArenaStatistikModel[] matches, int maxAreanGroesse, int maxFananzahl) {
        m_clMatches = matches;
        m_iMaxArenaGroesse = maxAreanGroesse;
        m_iMaxFananzahl = maxFananzahl;
        initData();
    }

    /**
     * Get the color for a certain match type.
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
    	try {
			m_clData = new Object[m_clMatches.length][m_sColumnNames.length];

			for (int i = 0; i < m_clMatches.length; i++) {
			    final ArenaStatistikModel match = m_clMatches[i];
			    final Color background = getColor4Matchtyp(match.getMatchTyp());
			    //Datum
			    m_clData[i][0] = new ColorLabelEntry(match.getMatchDateAsTimestamp().getTime(),
			    		DateFormat.getDateTimeInstance().format(match.getMatchDateAsTimestamp()),
			    		ColorLabelEntry.FG_STANDARD, background, SwingConstants.LEFT);
			    //Spielart
			    m_clData[i][1] = new ColorLabelEntry(Helper.getImageIcon4Spieltyp(match.getMatchTyp()),
			    		match.getMatchTyp(), ColorLabelEntry.FG_STANDARD, background, SwingConstants.CENTER);
			    //Gast
			    m_clData[i][2] = new ColorLabelEntry(match.getGastName(), ColorLabelEntry.FG_STANDARD,
			    		background, SwingConstants.LEFT);
			    //Ergebnis
			    m_clData[i][3] = new ColorLabelEntry(createTorString(match.getHeimTore(), match.getGastTore()),
			    		ColorLabelEntry.FG_STANDARD, background, SwingConstants.CENTER);

			    //Sterne für Sieger!
			    if (match.getMatchStatus() != IMatchKurzInfo.FINISHED) {
			        ((ColorLabelEntry) m_clData[i][3]).setIcon(Helper.NOIMAGEICON);
			    } else if (match.getHeimTore() > match.getGastTore()) {
			        ((ColorLabelEntry) m_clData[i][3]).setIcon(Helper.YELLOWSTARIMAGEICON);
			    } else if (match.getHeimTore() < match.getGastTore()) {
			        ((ColorLabelEntry) m_clData[i][3]).setIcon(Helper.NOIMAGEICON);
			    } else {
			        ((ColorLabelEntry) m_clData[i][3]).setIcon(Helper.GREYSTARIMAGEICON);
			    }

			    //Wetter
			    m_clData[i][4] = new ColorLabelEntry(Helper.getImageIcon4Wetter(match.getWetter()),
			                                         match.getWetter(), ColorLabelEntry.FG_STANDARD,
			                                         background, SwingConstants.RIGHT);

			    //Matchid
			    m_clData[i][5] = new ColorLabelEntry(match.getMatchID(), match.getMatchID() + "",
			                                         ColorLabelEntry.FG_STANDARD, background,
			                                         SwingConstants.RIGHT);

			    //Stadiongroesse
			    m_clData[i][6] = new ProgressbarTableEntry(match.getArenaGroesse(), 0,
			    		m_iMaxArenaGroesse, 0, 1, background, new Color(0, 0, 120), "");

			    //Zuschauer
			    m_clData[i][7] = new ProgressbarTableEntry(match.getZuschaueranzahl(), 0,
			    		m_iMaxArenaGroesse, 0, 1, background, new Color(0, 120, 0), "");

			    //Verhältnis Auslastung
			    m_clData[i][8] = new ProgressbarTableEntry(
			    		(int) ((float) match.getZuschaueranzahl() / (float) match.getArenaGroesse() * 1000),
			    		0, 1000, 1, 0.1, background, new Color(0, 120, 120), " %");

			    //Fananzahl
			    m_clData[i][9] = new ProgressbarTableEntry(match.getFans(), 0, m_iMaxFananzahl, 0, 1, background, new Color(80, 80, 80), "");

			    //Fanzuwachs pro Woche
			    float fanzuwachs = 0;

			    if ((i + 1) < m_clMatches.length) {
			        fanzuwachs = ((match.getFans() - m_clMatches[i + 1].getFans()) * 604800000f) / (match.getMatchDateAsTimestamp().getTime()
			                     - m_clMatches[i + 1].getMatchDateAsTimestamp().getTime());
			    }

			    m_clData[i][10] = new ColorLabelEntry(fanzuwachs, background, false);

			    //Quotione  Zuschauer/Fans
			    m_clData[i][11] = new ColorLabelEntry(Helper.round((float) match.getZuschaueranzahl()
						/ (float) match.getFans(), 2) + "", ColorLabelEntry.FG_STANDARD, background,
						SwingConstants.RIGHT);

			    // Fanstimmung
			    m_clData[i][12] = new ColorLabelEntry(Finanzen.getNameForLevelFans(match.getFanZufriedenheit(), match.getMatchDateAsTimestamp()),
			                                          ColorLabelEntry.FG_STANDARD, background,
			                                          SwingConstants.LEFT);

			    //Ligaplatz
			    m_clData[i][13] = new ColorLabelEntry(match.getLigaPlatz() + ".",
			                                          ColorLabelEntry.FG_STANDARD, background,
			                                          SwingConstants.CENTER);
			}
		} catch (Exception e) {
			HOLogger.instance().error(getClass(), "Error initialising arena stats: " + e);
			HOLogger.instance().error(getClass(), e);
		}
    }


	public ArenaStatistikModel[] getMatches() {
		return m_clMatches;
	}

}
