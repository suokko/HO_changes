// %3702132305:de.hattrickorganizer.gui.model%
package de.hattrickorganizer.gui.model;

import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.gui.templates.SpielerLabelEntry;
import de.hattrickorganizer.model.ScoutEintrag;
import de.hattrickorganizer.model.Spieler;
import de.hattrickorganizer.model.SpielerPosition;


/**
 * DOCUMENT ME!
 *
 * @author Volker Fischer
 * @version 0.2a    31.10.2001
 */
public class TransferTableModel extends AbstractTableModel {
    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public String[] m_sToolTipStrings = {
                                            de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                   .getResource()
                                                                                   .getProperty("ID"),
                                            

    //Name
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Name"),
                                            

    //Current price
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("scout_price"),
                                            

    //Ablaufdatum
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Ablaufdatum"),
                                            

    //Beste Position
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("BestePosition"),
                                            

    //Alter
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Alter"), 
    //TSI
    "TSI", 
    //Erfahrung
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Erfahrung"),
                                            

    //Form
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Form"),
                                            

    //Kondition
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Kondition"),
                                            

    //Torwart
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Torwart"),
                                            

    //Verteidigung
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Verteidigung"),
                                            

    //Spielaufbau
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Spielaufbau"),
                                            

    //Passpiel
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Passpiel"),
                                            

    //Flügelspiel
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Fluegelspiel"),
                                            

    //Torschuss
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Torschuss"),
                                            

    //Standards
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Standards"),
                                            

    //Gesamt Torwart
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Torwart"),
                                            

    //Innenverteidiger
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Innenverteidiger"),
                                            

    //Innenverteidiger Nach Aussen
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Innenverteidiger_Aus"),
                                            

    //Innenverteidiger Offensiv
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Innenverteidiger_Off"),
                                            

    //Aussenverteidiger
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Aussenverteidiger"),
                                            

    //Aussenverteidiger Nach Innen
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Aussenverteidiger_In"),
                                            

    //Aussenverteidiger Offensiv
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Aussenverteidiger_Off"),
                                            

    //Aussenverteidiger Defensiv
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Aussenverteidiger_Def"),
                                            

    //Mittelfeld
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Mittelfeld"),
                                            

    //Mittelfeld Nach Aussen
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Mittelfeld_Aus"),
                                            

    //Mittelfeld Offensiv
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Mittelfeld_Off"),
                                            

    //Mittelfeld Defensiv
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Mittelfeld_Def"),
                                            

    //Flügel
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Fluegelspiel"),
                                            

    //Flügel Nach Innen
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Fluegelspiel_In"),
                                            

    //Flügel Offensiv
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Fluegelspiel_Off"),
                                            

    //Flügel Defensiv
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Fluegelspiel_Def"),
                                            

    //Sturm
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Sturm"),
                                            

    //Sturm Defensiv
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Sturm_Def"),
                                            

    //Notes
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Notizen"),
                                        };

    /** TODO Missing Parameter Documentation */
    protected Object[][] m_clData;

    /** TODO Missing Parameter Documentation */
    protected String[] m_sColumnNames = {
                                            de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                   .getResource()
                                                                                   .getProperty("ID"),
                                            

    //Name
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Name"),
                                            

    //Current price
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("scout_price"),
                                            

    //Ablaufdatum
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Ablaufdatum"),
                                            

    //Beste Position
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("BestePosition"),
                                            

    //Alter
    "", 
    //TSI
    "TSI", 
    //Erfahrung
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("ER"),
                                            

    //Form
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("FO"),
                                            

    //Kondition
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("KO"),
                                            

    //Torwart
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("TW"),
                                            

    //Verteidigung
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("VE"),
                                            

    //Spielaufbau
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("SA"),
                                            

    //Passpiel
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("PS"),
                                            

    //Flügelspiel
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("FL"),
                                            

    //Torschuss
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("TS"),
                                            

    //Standards
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("ST"),
                                            

    //Gesamt Torwart
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("TORW"),
                                            

    //Innenverteidiger
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("IV"),
                                            

    //Innenverteidiger Nach Aussen
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("IVA"),
                                            

    //Innenverteidiger Offensiv
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("IVO"),
                                            

    //Aussenverteidiger
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("AV"),
                                            

    //Aussenverteidiger Zur Mitte
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("AVI"),
                                            

    //Aussenverteidiger Offensiv
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("AVO"),
                                            

    //Aussenverteidiger Defensiv
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("AVD"),
                                            

    //Mittelfeld
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("MIT"),
                                            

    //Mittelfeld Nach Aussen
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("MITA"),
                                            

    //Mittelfeld Offensiv
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("MITO"),
                                            

    //Mittelfeld Defensiv
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("MITD"),
                                            

    //Flügel
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("FLG"),
                                            

    //Flügel Nach Innen
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("FLGI"),
                                            

    //Flügel Offensiv
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("FLGO"),
                                            

    //Flügel Defensiv
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("FLGD"),
                                            

    //Sturm
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("STU"),
                                            

    //Sturm Defensiv
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("STUD"),
                                            

    //Notes
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Notizen"),
                                        };
    private Vector m_vScoutEintraege;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TransferTableModel object.
     *
     * @param scouteintraege TODO Missing Constructuor Parameter Documentation
     */
    public TransferTableModel(Vector scouteintraege) {
        m_vScoutEintraege = scouteintraege;
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
        } else {
            return "".getClass();
        }
    }

    //-----Access methods----------------------------------------
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
     * @return TODO Missing Return Method Documentation
     */
    public final int getRowCount() {
        if (m_clData != null) {
            return m_clData.length;
        } else {
            return 0;
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param playerID TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final ScoutEintrag getScoutEintrag(int playerID) {
        for (int i = 0; i < m_vScoutEintraege.size(); i++) {
            if (((ScoutEintrag) m_vScoutEintraege.get(i)).getPlayerID() == playerID) {
                return ((ScoutEintrag) m_vScoutEintraege.get(i)).duplicate();
            }
        }

        return null;
    }

    /**
     * Returns the list of ScoutEntries
     *
     * @return TODO Missing Return Method Documentation
     */
    public final java.util.Vector getScoutListe() {
        return m_vScoutEintraege;
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
     * Set player again
     *
     * @param scouteintraege TODO Missing Constructuor Parameter Documentation
     */
    public final void setValues(Vector scouteintraege) {
        m_vScoutEintraege = scouteintraege;
        initData();
    }

    /**
     * Add player to the table
     *
     * @param scouteintraege TODO Missing Constructuor Parameter Documentation
     */
    public final void addScoutEintrag(ScoutEintrag scouteintraege) {
        m_vScoutEintraege.add(scouteintraege.duplicate());
        initData();
    }

    /**
     * Remove player from table
     *
     * @param scouteintraege TODO Missing Constructuor Parameter Documentation
     */
    public final void removeScoutEintrag(ScoutEintrag scouteintraege) {
        if (m_vScoutEintraege.remove(scouteintraege)) {
            initData();
        }
    }

    //-----initialization-----------------------------------------

    /**
     * Return a Data[][] from the player vector
     */
    private void initData() {
        m_clData = new Object[m_vScoutEintraege.size()][m_sColumnNames.length];

        for (int i = 0; i < m_vScoutEintraege.size(); i++) {
            final ScoutEintrag aktuellerScoutEintrag = (ScoutEintrag) m_vScoutEintraege.get(i);
            final Spieler aktuellerSpieler = new Spieler();
            aktuellerSpieler.setName(aktuellerScoutEintrag.getName());
            aktuellerSpieler.setSpezialitaet(aktuellerScoutEintrag.getSpeciality());
            aktuellerSpieler.setErfahrung(aktuellerScoutEintrag.getErfahrung());
            aktuellerSpieler.setForm(aktuellerScoutEintrag.getForm());
            aktuellerSpieler.setKondition(aktuellerScoutEintrag.getKondition());
            aktuellerSpieler.setVerteidigung(aktuellerScoutEintrag.getVerteidigung());
            aktuellerSpieler.setTorschuss(aktuellerScoutEintrag.getTorschuss());
            aktuellerSpieler.setTorwart(aktuellerScoutEintrag.getTorwart());
            aktuellerSpieler.setFluegelspiel(aktuellerScoutEintrag.getFluegelspiel());
            aktuellerSpieler.setPasspiel(aktuellerScoutEintrag.getPasspiel());
            aktuellerSpieler.setStandards(aktuellerScoutEintrag.getStandards());
            aktuellerSpieler.setSpielaufbau(aktuellerScoutEintrag.getSpielaufbau());

            //ID
            m_clData[i][0] = new ColorLabelEntry(aktuellerScoutEintrag.getPlayerID(),
                                                 ColorLabelEntry.FG_STANDARD,
                                                 ColorLabelEntry.BG_STANDARD, JLabel.LEFT);

            //Name
            m_clData[i][1] = new SpielerLabelEntry(aktuellerSpieler, null, 0f, false, false);

            //Price
            m_clData[i][2] = new ColorLabelEntry(aktuellerScoutEintrag.getPrice(),
                                                 ColorLabelEntry.FG_STANDARD,
                                                 ColorLabelEntry.BG_STANDARD, JLabel.RIGHT);

            //Ablaufdatum
            m_clData[i][3] = new ColorLabelEntry(aktuellerScoutEintrag.getDeadline().getTime(),
                                                 java.text.DateFormat.getDateTimeInstance().format(aktuellerScoutEintrag
                                                                                                   .getDeadline()),
                                                 ColorLabelEntry.FG_STANDARD,
                                                 ColorLabelEntry.BG_STANDARD, JLabel.RIGHT);

            //Beste Position
            m_clData[i][4] = new ColorLabelEntry(SpielerPosition.getSortId(aktuellerSpieler
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
                                                 ColorLabelEntry.BG_STANDARD, JLabel.LEFT);

            //Alter
            m_clData[i][5] = new ColorLabelEntry(aktuellerScoutEintrag.getAlterWithAgeDays(), aktuellerScoutEintrag.getAlterWithAgeDaysAsString(),
                                                 ColorLabelEntry.FG_STANDARD,
                                                 ColorLabelEntry.BG_STANDARD, JLabel.CENTER);

            //Marktwert
            m_clData[i][6] = new ColorLabelEntry(aktuellerScoutEintrag.getTSI(),
                                                 ColorLabelEntry.FG_STANDARD,
                                                 ColorLabelEntry.BG_STANDARD, JLabel.RIGHT);

            //Erfahrung
            m_clData[i][7] = new ColorLabelEntry(aktuellerSpieler.getErfahrung(),
                                                 ColorLabelEntry.FG_STANDARD,
                                                 ColorLabelEntry.BG_SPIELERSONDERWERTE, JLabel.RIGHT);

            //Form
            m_clData[i][8] = new ColorLabelEntry(aktuellerSpieler.getForm(),
                                                 ColorLabelEntry.FG_STANDARD,
                                                 ColorLabelEntry.BG_SPIELERSONDERWERTE, JLabel.RIGHT);

            //Kondition
            m_clData[i][9] = new ColorLabelEntry(aktuellerSpieler.getKondition(),
                                                 ColorLabelEntry.FG_STANDARD,
                                                 ColorLabelEntry.BG_SPIELEREINZELWERTE, JLabel.RIGHT);

            //Torwart
            m_clData[i][10] = new ColorLabelEntry(aktuellerSpieler.getTorwart(),
                                                  ColorLabelEntry.FG_STANDARD,
                                                  ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                  JLabel.RIGHT);

            //Verteidigung
            m_clData[i][11] = new ColorLabelEntry(aktuellerSpieler.getVerteidigung(),
                                                  ColorLabelEntry.FG_STANDARD,
                                                  ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                  JLabel.RIGHT);

            //Spielaufbau
            m_clData[i][12] = new ColorLabelEntry(aktuellerSpieler.getSpielaufbau(),
                                                  ColorLabelEntry.FG_STANDARD,
                                                  ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                  JLabel.RIGHT);

            //Passpiel
            m_clData[i][13] = new ColorLabelEntry(aktuellerSpieler.getPasspiel(),
                                                  ColorLabelEntry.FG_STANDARD,
                                                  ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                  JLabel.RIGHT);

            //Flügelspiel
            m_clData[i][14] = new ColorLabelEntry(aktuellerSpieler.getFluegelspiel(),
                                                  ColorLabelEntry.FG_STANDARD,
                                                  ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                  JLabel.RIGHT);

            //Torschuss
            m_clData[i][15] = new ColorLabelEntry(aktuellerSpieler.getTorschuss(),
                                                  ColorLabelEntry.FG_STANDARD,
                                                  ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                  JLabel.RIGHT);

            //Standards
            m_clData[i][16] = new ColorLabelEntry(aktuellerSpieler.getStandards(),
                                                  ColorLabelEntry.FG_STANDARD,
                                                  ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                  JLabel.RIGHT);

            //Wert Torwart
            m_clData[i][17] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.TORWART,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERPOSITONSWERTE, false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Innnenverteidiger
            m_clData[i][18] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.INNENVERTEIDIGER,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERPOSITONSWERTE, false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Innnenverteidiger Nach Aussen
            m_clData[i][19] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.INNENVERTEIDIGER_AUS,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Innnenverteidiger Offensiv
            m_clData[i][20] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.INNENVERTEIDIGER_OFF,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Aussenverteidiger
            m_clData[i][21] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.AUSSENVERTEIDIGER,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERPOSITONSWERTE, false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Aussenverteidiger
            m_clData[i][22] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.AUSSENVERTEIDIGER_IN,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Aussenverteidiger
            m_clData[i][23] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.AUSSENVERTEIDIGER_OFF,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Aussenverteidiger
            m_clData[i][24] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.AUSSENVERTEIDIGER_DEF,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Mittelfeld
            m_clData[i][25] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.MITTELFELD,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERPOSITONSWERTE, false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Mittelfeld
            m_clData[i][26] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.MITTELFELD_AUS,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Mittelfeld
            m_clData[i][27] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.MITTELFELD_OFF,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Mittelfeld
            m_clData[i][28] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.MITTELFELD_DEF,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Flügel
            m_clData[i][29] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.FLUEGELSPIEL,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERPOSITONSWERTE, false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Flügel
            m_clData[i][30] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.FLUEGELSPIEL_IN,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Flügel
            m_clData[i][31] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.FLUEGELSPIEL_OFF,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Flügel
            m_clData[i][32] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.FLUEGELSPIEL_DEF,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Sturm
            m_clData[i][33] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.STURM,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERPOSITONSWERTE, false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Sturm
            m_clData[i][34] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.STURM_DEF,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Notiz
            m_clData[i][35] = new ColorLabelEntry(aktuellerScoutEintrag.getInfo(),
                                                  ColorLabelEntry.FG_STANDARD,
                                                  ColorLabelEntry.BG_STANDARD, JLabel.LEFT);
        }
    }
}
