// %807458674:de.hattrickorganizer.gui.model%
package de.hattrickorganizer.gui.model;

import java.awt.Color;
import java.util.Vector;

import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;

import plugins.ISpieler;
import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.gui.templates.DoppelLabelEntry;
import de.hattrickorganizer.gui.templates.SpielerLabelEntry;
import de.hattrickorganizer.model.Spieler;
import de.hattrickorganizer.model.SpielerPosition;


/**
 * DOCUMENT ME!
 *
 * @author Volker Fischer
 */
public class SkillAenderungsTableModel extends AbstractTableModel {
    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public String[] m_sToolTipStrings = {
                                            de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Name"),
                                            

    //Alter
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Alter"),
                                            

    //Beste Position
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("BestePosition"),
                                            

    //Status
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Status"),
                                            

    //Torwart
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Torwart"),
                                            

    //Verteidigung
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Verteidigung"),
                                            

    //Spielaufbau
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Spielaufbau"),
                                            

    //Passpiel
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Passpiel"),
                                            

    //Flügelspiel
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Fluegelspiel"),
                                            

    //Torschuss
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Torschuss"),
                                            

    //Standards
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Standards"),
                                            

    //ID
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("ID"),
                                        };

    /** TODO Missing Parameter Documentation */
    protected Object[][] m_clData;

    /** TODO Missing Parameter Documentation */
    protected String[] m_sColumnNames = {
                                            de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Name"),
                                            

    //Alter
    " ",
                                            

    //Beste Position
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("BestePosition"),
                                            

    //Status
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Status"),
                                            

    //Torwart
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Torwart"),
                                            

    //Verteidigung
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Verteidigung"),
                                            

    //Spielaufbau
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Spielaufbau"),
                                            

    //Passpiel
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Passpiel"),
                                            

    //Flügelspiel
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Fluegelspiel"),
                                            

    //Torschuss
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Torschuss"),
                                            

    //Standards
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Standards"),
                                            

    //ID
    de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("ID"),
                                        };
    private Vector m_vSpieler;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new SkillAenderungsTableModel object.
     *
     * @param spieler TODO Missing Constructuor Parameter Documentation
     */
    public SkillAenderungsTableModel(Vector spieler) {
        m_vSpieler = spieler;
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
     * @param id TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Spieler getSpieler(int id) {
        if (id > 0) {
            for (int i = 0; i < m_vSpieler.size(); i++) {
                if (((Spieler) m_vSpieler.get(i)).getSpielerID() == id) {
                    return (Spieler) m_vSpieler.get(i);
                }
            }
        }

        return null;
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
        }

        return null;
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
     * Spieler neu setzen
     *
     * @param spieler TODO Missing Constructuor Parameter Documentation
     */
    public final void setValues(Vector spieler) {
        m_vSpieler = spieler;
        initData();
    }

    /**
     * Fügt der Tabelle einen Spieler hinzu
     *
     * @param spieler TODO Missing Constructuor Parameter Documentation
     * @param index TODO Missing Constructuor Parameter Documentation
     */
    public final void addSpieler(Spieler spieler, int index) {
        m_vSpieler.add(index, spieler);
        initData();
    }

    //-----initialisierung-----------------------------------------

    /**
     * Erzeugt einen Data[][] aus dem Spielervector
     */
    public final void initData() {
        m_clData = new Object[m_vSpieler.size()][m_sColumnNames.length];

        for (int i = 0; i < m_vSpieler.size(); i++) {
            final Spieler aktuellerSpieler = (Spieler) m_vSpieler.get(i);

            //Name
            m_clData[i][0] = new SpielerLabelEntry(aktuellerSpieler, null, 0f, false, false);

            //Alter
            m_clData[i][1] = new ColorLabelEntry(aktuellerSpieler.getAlterWithAgeDays(), aktuellerSpieler.getAlterWithAgeDaysAsString(),
                                                 ColorLabelEntry.FG_STANDARD,
                                                 ColorLabelEntry.BG_STANDARD, SwingConstants.CENTER);

            //Beste Position
            m_clData[i][2] = new ColorLabelEntry(-SpielerPosition.getSortId(aktuellerSpieler
                                                                            .getIdealPosition(),
                                                                            false)
                                                 - (aktuellerSpieler.getIdealPosStaerke(true) / 100.0f),
                                                 SpielerPosition.getNameForPosition(aktuellerSpieler
                                                                                    .getIdealPosition())
                                                 + " ("
                                                 + aktuellerSpieler.calcPosValue(aktuellerSpieler
                                                                                 .getIdealPosition(),
                                                                                 true) + ")",
                                                 ColorLabelEntry.FG_STANDARD,
                                                 ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);

            if (aktuellerSpieler.getUserPosFlag() < 0) {
                ((ColorLabelEntry) m_clData[i][2]).setIcon(de.hattrickorganizer.tools.Helper.ZAHNRAD);
            } else {
                ((ColorLabelEntry) m_clData[i][2]).setIcon(de.hattrickorganizer.tools.Helper.MANUELL);
            }

            //Status
            m_clData[i][3] = new de.hattrickorganizer.gui.playeroverview.SpielerStatusLabelEntry();
            ((de.hattrickorganizer.gui.playeroverview.SpielerStatusLabelEntry) m_clData[i][3])
            .setSpieler(aktuellerSpieler);

            Object[] object = null;
            java.sql.Timestamp time = null;
            Color foreground = Color.black;

            //Torwart
            object = aktuellerSpieler.getLastLevelUp(ISpieler.SKILL_TORWART);
            time = (java.sql.Timestamp) object[0];
            foreground = getForeground(((Boolean) object[1]).booleanValue());
            m_clData[i][4] = new DoppelLabelEntry(new ColorLabelEntry(time.getTime(),
                                                                      java.text.DateFormat.getDateInstance()
                                                                                          .format(time),
                                                                      foreground,
                                                                      ColorLabelEntry.BG_STANDARD,
                                                                      SwingConstants.LEFT),
                                                  new ColorLabelEntry((int) (aktuellerSpieler
                                                                             .getLastLevelUpInTage(Spieler.SKILL_TORWART) / 7)
                                                                      + " "
                                                                      + de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Wochen"),
                                                                      foreground,
                                                                      ColorLabelEntry.BG_STANDARD,
                                                                      SwingConstants.RIGHT));

            //Verteidigung
            object = aktuellerSpieler.getLastLevelUp(ISpieler.SKILL_VERTEIDIGUNG);
            time = (java.sql.Timestamp) object[0];
            foreground = getForeground(((Boolean) object[1]).booleanValue());
            m_clData[i][5] = new DoppelLabelEntry(new ColorLabelEntry(time.getTime(),
                                                                      java.text.DateFormat.getDateInstance()
                                                                                          .format(time),
                                                                      foreground,
                                                                      ColorLabelEntry.BG_STANDARD,
                                                                      SwingConstants.LEFT),
                                                  new ColorLabelEntry((int) (aktuellerSpieler
                                                                             .getLastLevelUpInTage(Spieler.SKILL_VERTEIDIGUNG) / 7)
                                                                      + " "
                                                                      + de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Wochen"),
                                                                      foreground,
                                                                      ColorLabelEntry.BG_STANDARD,
                                                                      SwingConstants.RIGHT));

            //Spielaufbau
            object = aktuellerSpieler.getLastLevelUp(ISpieler.SKILL_SPIELAUFBAU);
            time = (java.sql.Timestamp) object[0];
            foreground = getForeground(((Boolean) object[1]).booleanValue());
            m_clData[i][6] = new DoppelLabelEntry(new ColorLabelEntry(time.getTime(),
                                                                      java.text.DateFormat.getDateInstance()
                                                                                          .format(time),
                                                                      foreground,
                                                                      ColorLabelEntry.BG_STANDARD,
                                                                      SwingConstants.LEFT),
                                                  new ColorLabelEntry((int) (aktuellerSpieler
                                                                             .getLastLevelUpInTage(Spieler.SKILL_SPIELAUFBAU) / 7)
                                                                      + " "
                                                                      + de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Wochen"),
                                                                      foreground,
                                                                      ColorLabelEntry.BG_STANDARD,
                                                                      SwingConstants.RIGHT));

            //Passpiel
            object = aktuellerSpieler.getLastLevelUp(ISpieler.SKILL_PASSSPIEL);
            time = (java.sql.Timestamp) object[0];
            foreground = getForeground(((Boolean) object[1]).booleanValue());
            m_clData[i][7] = new DoppelLabelEntry(new ColorLabelEntry(time.getTime(),
                                                                      java.text.DateFormat.getDateInstance()
                                                                                          .format(time),
                                                                      foreground,
                                                                      ColorLabelEntry.BG_STANDARD,
                                                                      SwingConstants.LEFT),
                                                  new ColorLabelEntry((int) (aktuellerSpieler
                                                                             .getLastLevelUpInTage(Spieler.SKILL_PASSSPIEL) / 7)
                                                                      + " "
                                                                      + de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Wochen"),
                                                                      foreground,
                                                                      ColorLabelEntry.BG_STANDARD,
                                                                      SwingConstants.RIGHT));

            //Flügelspiel
            object = aktuellerSpieler.getLastLevelUp(ISpieler.SKILL_FLUEGEL);
            time = (java.sql.Timestamp) object[0];
            foreground = getForeground(((Boolean) object[1]).booleanValue());
            m_clData[i][8] = new DoppelLabelEntry(new ColorLabelEntry(time.getTime(),
                                                                      java.text.DateFormat.getDateInstance()
                                                                                          .format(time),
                                                                      foreground,
                                                                      ColorLabelEntry.BG_STANDARD,
                                                                      SwingConstants.LEFT),
                                                  new ColorLabelEntry((int) (aktuellerSpieler
                                                                             .getLastLevelUpInTage(Spieler.SKILL_FLUEGEL) / 7)
                                                                      + " "
                                                                      + de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Wochen"),
                                                                      foreground,
                                                                      ColorLabelEntry.BG_STANDARD,
                                                                      SwingConstants.RIGHT));

            //Torschuss
            object = aktuellerSpieler.getLastLevelUp(ISpieler.SKILL_TORSCHUSS);
            time = (java.sql.Timestamp) object[0];
            foreground = getForeground(((Boolean) object[1]).booleanValue());
            m_clData[i][9] = new DoppelLabelEntry(new ColorLabelEntry(time.getTime(),
                                                                      java.text.DateFormat.getDateInstance()
                                                                                          .format(time),
                                                                      foreground,
                                                                      ColorLabelEntry.BG_STANDARD,
                                                                      SwingConstants.LEFT),
                                                  new ColorLabelEntry((int) (aktuellerSpieler
                                                                             .getLastLevelUpInTage(Spieler.SKILL_TORSCHUSS) / 7)
                                                                      + " "
                                                                      + de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Wochen"),
                                                                      foreground,
                                                                      ColorLabelEntry.BG_STANDARD,
                                                                      SwingConstants.RIGHT));

            //Standards
            object = aktuellerSpieler.getLastLevelUp(ISpieler.SKILL_STANDARDS);
            time = (java.sql.Timestamp) object[0];
            foreground = getForeground(((Boolean) object[1]).booleanValue());
            m_clData[i][10] = new DoppelLabelEntry(new ColorLabelEntry(time.getTime(),
                                                                       java.text.DateFormat.getDateInstance()
                                                                                           .format(time),
                                                                       foreground,
                                                                       ColorLabelEntry.BG_STANDARD,
                                                                       SwingConstants.LEFT),
                                                   new ColorLabelEntry((int) (aktuellerSpieler
                                                                              .getLastLevelUpInTage(Spieler.SKILL_STANDARDS) / 7)
                                                                       + " "
                                                                       + de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Wochen"),
                                                                       foreground,
                                                                       ColorLabelEntry.BG_STANDARD,
                                                                       SwingConstants.RIGHT));

            //ID
            m_clData[i][11] = new ColorLabelEntry(aktuellerSpieler.getSpielerID() + "",
                                                  ColorLabelEntry.FG_STANDARD,
                                                  ColorLabelEntry.BG_STANDARD, SwingConstants.RIGHT);
        }
    }

    /**
     * Entfernt den Spieler aus der Tabelle
     *
     * @param spieler TODO Missing Constructuor Parameter Documentation
     */
    public final void removeSpieler(Spieler spieler) {
        m_vSpieler.remove(spieler);
        initData();
    }

    ////////////////////////////////////
    private Color getForeground(boolean trainiert) {
        return (trainiert) ? Color.black : Color.gray;
    }
}
