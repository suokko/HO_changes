// %534638504:de.hattrickorganizer.gui.model%
package ho.core.gui.model;

import ho.core.gui.comp.entry.ColorLabelEntry;
import ho.core.gui.comp.entry.ProgressbarTableEntry;
import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ImageUtilities;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.Finanzen;
import ho.core.model.HOVerwaltung;
import ho.core.util.HOLogger;
import ho.core.util.Helper;
import ho.core.util.StringUtilities;

import java.awt.Color;
import java.text.DateFormat;

import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;

import plugins.IMatchKurzInfo;


/**
 * DOCUMENT ME!
 *
 * @author Volker Fischer
 * @version 0.2a    31.10.2001
 */
public class ArenaStatistikTableModel extends AbstractTableModel {
    //~ Static fields/initializers -----------------------------------------------------------------

	private static final long serialVersionUID = 7187251269604772672L;

 
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
			HOVerwaltung.instance().getLanguageString("Stehplaetze"),
			HOVerwaltung.instance().getLanguageString("Sitzplaetze"),
			HOVerwaltung.instance().getLanguageString("Ueberdachteplaetze"),
			HOVerwaltung.instance().getLanguageString("Logen"),
			// Fananzahl
			HOVerwaltung.instance().getLanguageString("Fans"),
			HOVerwaltung.instance().getLanguageString("Fans") + " / "
					+ HOVerwaltung.instance().getLanguageString("Wochen"),
			HOVerwaltung.instance().getLanguageString("Zuschauer") + " / "
					+ HOVerwaltung.instance().getLanguageString("Fans"),
			// Stimmung
			HOVerwaltung.instance().getLanguageString("Fans"),
			// LigaPlatz
			HOVerwaltung.instance().getLanguageString("Platzierung")

			};

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
			HOVerwaltung.instance().getLanguageString("Stehplaetze"),
			HOVerwaltung.instance().getLanguageString("Sitzplaetze"),
			HOVerwaltung.instance().getLanguageString("Ueberdachteplaetze"),
			HOVerwaltung.instance().getLanguageString("Logen"),
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



 

    //-----initialisierung-----------------------------------------

    /**
     * Erzeugt einen Data[][] aus dem Spielervector
     */
    private void initData() {
    	try {
			m_clData = new Object[m_clMatches.length][m_sColumnNames.length];

			for (int i = 0; i < m_clMatches.length; i++) {
			    final ArenaStatistikModel match = m_clMatches[i];
			    final Color background = MatchesColumnModel.getColor4Matchtyp(match.getMatchTyp());
			    //Datum
			    m_clData[i][0] = new ColorLabelEntry(match.getMatchDateAsTimestamp().getTime(),
			    		DateFormat.getDateTimeInstance().format(match.getMatchDateAsTimestamp()),
			    		ColorLabelEntry.FG_STANDARD, background, SwingConstants.LEFT);
			    //Spielart
			    m_clData[i][1] = new ColorLabelEntry(ThemeManager.getIcon(HOIconName.MATCHTYPES[match.getMatchTyp()]),
			    		match.getMatchTyp(), ColorLabelEntry.FG_STANDARD, background, SwingConstants.CENTER);
			    //Gast
			    m_clData[i][2] = new ColorLabelEntry(match.getGastName(), ColorLabelEntry.FG_STANDARD,
			    		background, SwingConstants.LEFT);
			    //Ergebnis
			    m_clData[i][3] = new ColorLabelEntry(StringUtilities.getResultString(match.getHeimTore(), match.getGastTore()),
			    		ColorLabelEntry.FG_STANDARD, background, SwingConstants.CENTER);

			    //Sterne für Sieger!
			    if (match.getMatchStatus() != IMatchKurzInfo.FINISHED) {
			        ((ColorLabelEntry) m_clData[i][3]).setIcon(ImageUtilities.NOIMAGEICON);
			    } else if (match.getHeimTore() > match.getGastTore()) {
			        ((ColorLabelEntry) m_clData[i][3]).setIcon(ThemeManager.getTransparentIcon(HOIconName.STAR, Color.WHITE));
			    } else if (match.getHeimTore() < match.getGastTore()) {
			        ((ColorLabelEntry) m_clData[i][3]).setIcon(ImageUtilities.NOIMAGEICON);
			    } else {
			        ((ColorLabelEntry) m_clData[i][3]).setIcon(ThemeManager.getTransparentIcon(HOIconName.STAR_GRAY, Color.WHITE));
			    }

			    //Wetter
			    m_clData[i][4] = new ColorLabelEntry(ThemeManager.getIcon(HOIconName.WEATHER[match.getWetter()]),
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

				  //Ligaplatz
			    m_clData[i][9] = new ColorLabelEntry(match.getSoldTerraces()+"",
			                                          ColorLabelEntry.FG_STANDARD, background,
			                                          SwingConstants.CENTER);
			    
			  //Ligaplatz
			    m_clData[i][10] = new ColorLabelEntry(match.getSoldBasics()+"",
			                                          ColorLabelEntry.FG_STANDARD, background,
			                                          SwingConstants.CENTER);
			    
			  //Ligaplatz
			    m_clData[i][11] = new ColorLabelEntry(match.getSoldRoof()+"",
			                                          ColorLabelEntry.FG_STANDARD, background,
			                                          SwingConstants.CENTER);
			    
			  //Ligaplatz
			    m_clData[i][12] = new ColorLabelEntry(match.getSoldVip()+"",
			                                          ColorLabelEntry.FG_STANDARD, background,
			                                          SwingConstants.CENTER);
			    
			    //Fananzahl
			    m_clData[i][13] = new ProgressbarTableEntry(match.getFans(), 0, m_iMaxFananzahl, 0, 1, background, new Color(80, 80, 80), "");

			    //Fanzuwachs pro Woche
			    float fanzuwachs = 0;

			    if ((i + 1) < m_clMatches.length) {
			        fanzuwachs = ((match.getFans() - m_clMatches[i + 1].getFans()) * 604800000f) / (match.getMatchDateAsTimestamp().getTime()
			                     - m_clMatches[i + 1].getMatchDateAsTimestamp().getTime());
			    }

			    m_clData[i][14] = new ColorLabelEntry(fanzuwachs, background, false,false,0);

			    //Quotione  Zuschauer/Fans
			    m_clData[i][15] = new ColorLabelEntry(Helper.round((float) match.getZuschaueranzahl()
						/ (float) match.getFans(), 2) + "", ColorLabelEntry.FG_STANDARD, background,
						SwingConstants.RIGHT);

			    // Fanstimmung
			    m_clData[i][16] = new ColorLabelEntry(Finanzen.getNameForLevelFans(match.getFanZufriedenheit(), match.getMatchDateAsTimestamp()),
			                                          ColorLabelEntry.FG_STANDARD, background,
			                                          SwingConstants.LEFT);

			    //Ligaplatz
			    m_clData[i][17] = new ColorLabelEntry(match.getLigaPlatz() + ".",
			                                          ColorLabelEntry.FG_STANDARD, background,
			                                          SwingConstants.CENTER);
			    
			}
		} catch (Exception e) {
			HOLogger.instance().error(getClass(), e);
		}
    }


	public ArenaStatistikModel[] getMatches() {
		return m_clMatches;
	}

}
