// %3702132305:de.hattrickorganizer.gui.model%
package de.hattrickorganizer.gui.model;

import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;

import plugins.ISpielerPosition;

import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.gui.templates.SpielerLabelEntry;
import de.hattrickorganizer.model.HOVerwaltung;
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
	
	private static final long serialVersionUID = -7723286963812074041L;

	//~ Instance fields ----------------------------------------------------------------------------

    /** Array of ToolTip Strings shown in the table header (first row of table) */
    public String[] m_sToolTipStrings = 
    {
    	HOVerwaltung.instance().getLanguageString("ID"),
        //Name
	    HOVerwaltung.instance().getLanguageString("Name"),
	    //Current price
	    HOVerwaltung.instance().getLanguageString("scout_price"),
	    //Ablaufdatum
	    HOVerwaltung.instance().getLanguageString("Ablaufdatum"),
	    //Beste Position
	    HOVerwaltung.instance().getLanguageString("BestePosition"),
	    //Alter
	    HOVerwaltung.instance().getLanguageString("Alter"), 
	    //TSI
	    "TSI", 
	    //Erfahrung
	    HOVerwaltung.instance().getLanguageString("Erfahrung"),
	    //Form
	    HOVerwaltung.instance().getLanguageString("Form"),
	    //Kondition
	    HOVerwaltung.instance().getLanguageString("skill.stamina"),
	    //Torwart
	    HOVerwaltung.instance().getLanguageString("skill.keeper"),
	    //Verteidigung
	    HOVerwaltung.instance().getLanguageString("skill.defending"),
	    //Spielaufbau
	    HOVerwaltung.instance().getLanguageString("skill.playmaking"),
	    //Passpiel
	    HOVerwaltung.instance().getLanguageString("skill.passing"),
	    //Flügelspiel
	    HOVerwaltung.instance().getLanguageString("skill.winger"),
	    //Torschuss
	    HOVerwaltung.instance().getLanguageString("skill.scoring"),
	    //Standards
	    HOVerwaltung.instance().getLanguageString("skill.set_pieces"),
	    //Gesamt Torwart
	    HOVerwaltung.instance().getLanguageString("Torwart"),
	    //Innenverteidiger
	    HOVerwaltung.instance().getLanguageString("Innenverteidiger"),
	    //Innenverteidiger Nach Aussen
	    HOVerwaltung.instance().getLanguageString("Innenverteidiger_Aus"),
	    //Innenverteidiger Offensiv
	    HOVerwaltung.instance().getLanguageString("Innenverteidiger_Off"),
	    //Aussenverteidiger
	    HOVerwaltung.instance().getLanguageString("Aussenverteidiger"),
	    //Aussenverteidiger Nach Innen
	    HOVerwaltung.instance().getLanguageString("Aussenverteidiger_In"),
	    //Aussenverteidiger Offensiv
	    HOVerwaltung.instance().getLanguageString("Aussenverteidiger_Off"),
	    //Aussenverteidiger Defensiv
	    HOVerwaltung.instance().getLanguageString("Aussenverteidiger_Def"),
	    //Mittelfeld
	    HOVerwaltung.instance().getLanguageString("Mittelfeld"),
	    //Mittelfeld Nach Aussen
	    HOVerwaltung.instance().getLanguageString("Mittelfeld_Aus"),
	    //Mittelfeld Offensiv
	    HOVerwaltung.instance().getLanguageString("Mittelfeld_Off"),
	    //Mittelfeld Defensiv
	    HOVerwaltung.instance().getLanguageString("Mittelfeld_Def"),
	    //Flügel
	    HOVerwaltung.instance().getLanguageString("Fluegelspiel"),
	    //Flügel Nach Innen
	    HOVerwaltung.instance().getLanguageString("Fluegelspiel_In"),
	    //Flügel Offensiv
	    HOVerwaltung.instance().getLanguageString("Fluegelspiel_Off"),
	    //Flügel Defensiv
	    HOVerwaltung.instance().getLanguageString("Fluegelspiel_Def"),
	    //Sturm
	    HOVerwaltung.instance().getLanguageString("Sturm"),
	    //Sturm Defensiv
	    HOVerwaltung.instance().getLanguageString("Sturm_Def"),
	    //Notes
	    HOVerwaltung.instance().getLanguageString("Notizen"),
    };

    /** TODO Missing Parameter Documentation */
    protected Object[][] m_clData;

    /** Array of Strings shown in the table header (first row of table) */
    protected String[] m_sColumnNames = 
    {
        HOVerwaltung.instance().getLanguageString("ID"),
        //Name
	    HOVerwaltung.instance().getLanguageString("Name"),
	    //Current price
	    HOVerwaltung.instance().getLanguageString("scout_price"),
	    //Ablaufdatum
	    HOVerwaltung.instance().getLanguageString("Ablaufdatum"),
	    //Beste Position
	    HOVerwaltung.instance().getLanguageString("BestePosition"),
	    //Alter
	    HOVerwaltung.instance().getLanguageString("Alter"), 
	    //TSI
	    "TSI", 
	    //Erfahrung
	    HOVerwaltung.instance().getLanguageString("ER"),
	    //Form
	    HOVerwaltung.instance().getLanguageString("FO"),
	    //Kondition
	    HOVerwaltung.instance().getLanguageString("KO"),
	    //Torwart
	    HOVerwaltung.instance().getLanguageString("TW"),
	    //Verteidigung
	    HOVerwaltung.instance().getLanguageString("VE"),
	    //Spielaufbau
	    HOVerwaltung.instance().getLanguageString("SA"),
	    //Passpiel
	    HOVerwaltung.instance().getLanguageString("PS"),
	    //Flügelspiel
	    HOVerwaltung.instance().getLanguageString("FL"),
	    //Torschuss
	    HOVerwaltung.instance().getLanguageString("TS"),
	    //Standards
	    HOVerwaltung.instance().getLanguageString("ST"),
	    //Gesamt Torwart
	    HOVerwaltung.instance().getLanguageString("TORW"),
	    //Innenverteidiger
	    HOVerwaltung.instance().getLanguageString("IV"),
	    //Innenverteidiger Nach Aussen
	    HOVerwaltung.instance().getLanguageString("IVA"),
	    //Innenverteidiger Offensiv
	    HOVerwaltung.instance().getLanguageString("IVO"),
	    //Aussenverteidiger
	    HOVerwaltung.instance().getLanguageString("AV"),
	    //Aussenverteidiger Zur Mitte
	    HOVerwaltung.instance().getLanguageString("AVI"),
	    //Aussenverteidiger Offensiv
	    HOVerwaltung.instance().getLanguageString("AVO"),
	    //Aussenverteidiger Defensiv
	    HOVerwaltung.instance().getLanguageString("AVD"),
	    //Mittelfeld
	    HOVerwaltung.instance().getLanguageString("MIT"),
	    //Mittelfeld Nach Aussen
	    HOVerwaltung.instance().getLanguageString("MITA"),
	    //Mittelfeld Offensiv
	    HOVerwaltung.instance().getLanguageString("MITO"),
	    //Mittelfeld Defensiv
	    HOVerwaltung.instance().getLanguageString("MITD"),
	    //Flügel
	    HOVerwaltung.instance().getLanguageString("FLG"),
	    //Flügel Nach Innen
	    HOVerwaltung.instance().getLanguageString("FLGI"),
	    //Flügel Offensiv
	    HOVerwaltung.instance().getLanguageString("FLGO"),
	    //Flügel Defensiv
	    HOVerwaltung.instance().getLanguageString("FLGD"),
	    //Sturm
	    HOVerwaltung.instance().getLanguageString("STU"),
	    //Sturm Defensiv
	    HOVerwaltung.instance().getLanguageString("STUD"),
	    //Notes
	    HOVerwaltung.instance().getLanguageString("Notizen"),
    };
    
    private Vector<ScoutEintrag> m_vScoutEintraege;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TransferTableModel object.
     *
     * @param scouteintraege TODO Missing Constructuor Parameter Documentation
     */
    public TransferTableModel(Vector<ScoutEintrag> scouteintraege) {
        m_vScoutEintraege = scouteintraege;
        initData();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     *  Returns false.  This is the default implementation for all cells.
     *
     *  @param  row  the row being queried
     *  @param  col the column being queried
     *  @return false
     */
    @Override
	public final boolean isCellEditable(int row, int col) {
        return false;
    }

    /**
     *  Returns the class of the table entry row=0, column=columnIndex
     *  Returns String.class if there is no such entry
     *
     *  @param columnIndex  the column being queried
     *  @return see above
     */
    @Override
	public final Class<?> getColumnClass(int columnIndex) {
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
     *  Returns the name for the column of columnIndex.
     *  Return null if there is no match.
     *   
     *
     * @param column  the column being queried
     * @return a string containing the name of <code>column</code>
     */
    @Override
	public final String getColumnName(int columnIndex) {
        if (m_sColumnNames.length > columnIndex) {
            return m_sColumnNames[columnIndex];
        } else {
            return null;
        }
    }

    /**
     * Returns the number of data sets (without header).
     *
     * @return m_clData.length
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
            if ((m_vScoutEintraege.get(i)).getPlayerID() == playerID) {
                return (m_vScoutEintraege.get(i)).duplicate();
            }
        }
        return null;
    }

    /**
     * Returns the list of ScoutEntries
     *
     * @return TODO Missing Return Method Documentation
     */
    public final java.util.Vector<ScoutEintrag> getScoutListe() {
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
    @Override
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
    public final void setValues(Vector<ScoutEintrag> scouteintraege) {
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
     * Remove one ScoutEntry from table
     *
     * @param scouteintraege the ScoutEntry which will be removed from the table
     */
    public final void removeScoutEintrag(ScoutEintrag scouteintraege) {
        if (m_vScoutEintraege.remove(scouteintraege)) {
            initData();
        }
    }
    
    /**
     * Remove all players from table
     *
     */
    public final void removeScoutEntries() {
    	if (m_vScoutEintraege != null) {
    		m_vScoutEintraege.removeAllElements();
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
            final ScoutEintrag aktuellerScoutEintrag = m_vScoutEintraege.get(i);
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
            m_clData[i][0] = new ColorLabelEntry(aktuellerScoutEintrag.getPlayerID()+"",
                                                 ColorLabelEntry.FG_STANDARD,
                                                 ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);

            //Name
            m_clData[i][1] = new SpielerLabelEntry(aktuellerSpieler, null, 0f, false, false);

            //Price
            m_clData[i][2] = new ColorLabelEntry(aktuellerScoutEintrag.getPrice()+"",
                                                 ColorLabelEntry.FG_STANDARD,
                                                 ColorLabelEntry.BG_STANDARD, SwingConstants.RIGHT);

            //Ablaufdatum
            m_clData[i][3] = new ColorLabelEntry(aktuellerScoutEintrag.getDeadline().getTime(),
                                                 java.text.DateFormat.getDateTimeInstance().format(aktuellerScoutEintrag
                                                                                                   .getDeadline()),
                                                 ColorLabelEntry.FG_STANDARD,
                                                 ColorLabelEntry.BG_STANDARD, SwingConstants.RIGHT);

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
                                                 ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);

            //Alter
            m_clData[i][5] = new ColorLabelEntry(aktuellerScoutEintrag.getAlterWithAgeDays(), aktuellerScoutEintrag.getAlterWithAgeDaysAsString(),
                                                 ColorLabelEntry.FG_STANDARD,
                                                 ColorLabelEntry.BG_STANDARD, SwingConstants.CENTER);

            //Marktwert
            m_clData[i][6] = new ColorLabelEntry(aktuellerScoutEintrag.getTSI()+"",
                                                 ColorLabelEntry.FG_STANDARD,
                                                 ColorLabelEntry.BG_STANDARD, SwingConstants.RIGHT);

            //Erfahrung
            m_clData[i][7] = new ColorLabelEntry(aktuellerSpieler.getErfahrung()+"",
                                                 ColorLabelEntry.FG_STANDARD,
                                                 ColorLabelEntry.BG_SPIELERSONDERWERTE, SwingConstants.RIGHT);

            //Form
            m_clData[i][8] = new ColorLabelEntry(aktuellerSpieler.getForm()+"",
                                                 ColorLabelEntry.FG_STANDARD,
                                                 ColorLabelEntry.BG_SPIELERSONDERWERTE, SwingConstants.RIGHT);

            //Kondition
            m_clData[i][9] = new ColorLabelEntry(aktuellerSpieler.getKondition()+"",
                                                 ColorLabelEntry.FG_STANDARD,
                                                 ColorLabelEntry.BG_SPIELEREINZELWERTE, SwingConstants.RIGHT);

            //Torwart
            m_clData[i][10] = new ColorLabelEntry(aktuellerSpieler.getTorwart()+"",
                                                  ColorLabelEntry.FG_STANDARD,
                                                  ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                  SwingConstants.RIGHT);

            //Verteidigung
            m_clData[i][11] = new ColorLabelEntry(aktuellerSpieler.getVerteidigung()+"",
                                                  ColorLabelEntry.FG_STANDARD,
                                                  ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                  SwingConstants.RIGHT);

            //Spielaufbau
            m_clData[i][12] = new ColorLabelEntry(aktuellerSpieler.getSpielaufbau()+"",
                                                  ColorLabelEntry.FG_STANDARD,
                                                  ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                  SwingConstants.RIGHT);

            //Passpiel
            m_clData[i][13] = new ColorLabelEntry(aktuellerSpieler.getPasspiel()+"",
                                                  ColorLabelEntry.FG_STANDARD,
                                                  ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                  SwingConstants.RIGHT);

            //Flügelspiel
            m_clData[i][14] = new ColorLabelEntry(aktuellerSpieler.getFluegelspiel()+"",
                                                  ColorLabelEntry.FG_STANDARD,
                                                  ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                  SwingConstants.RIGHT);

            //Torschuss
            m_clData[i][15] = new ColorLabelEntry(aktuellerSpieler.getTorschuss()+"",
                                                  ColorLabelEntry.FG_STANDARD,
                                                  ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                  SwingConstants.RIGHT);

            //Standards
            m_clData[i][16] = new ColorLabelEntry(aktuellerSpieler.getStandards()+"",
                                                  ColorLabelEntry.FG_STANDARD,
                                                  ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                  SwingConstants.RIGHT);

            //Wert Torwart
            m_clData[i][17] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(ISpielerPosition.KEEPER,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERPOSITONSWERTE, false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Innnenverteidiger
            m_clData[i][18] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(ISpielerPosition.CENTRAL_DEFENDER,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERPOSITONSWERTE, false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Innnenverteidiger Nach Aussen
            m_clData[i][19] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.CENTRAL_DEFENDER_TOWING,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Innnenverteidiger Offensiv
            m_clData[i][20] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.CENTRAL_DEFENDER_OFF,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Aussenverteidiger
            m_clData[i][21] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.BACK,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERPOSITONSWERTE, false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Aussenverteidiger
            m_clData[i][22] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.BACK_TOMID,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Aussenverteidiger
            m_clData[i][23] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.BACK_OFF,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Aussenverteidiger
            m_clData[i][24] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.BACK_DEF,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Mittelfeld
            m_clData[i][25] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.MIDFIELDER,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERPOSITONSWERTE, false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Mittelfeld
            m_clData[i][26] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.MIDFIELDER_TOWING,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Mittelfeld
            m_clData[i][27] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.MIDFIELDER_OFF,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Mittelfeld
            m_clData[i][28] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.MIDFIELDER_DEF,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Flügel
            m_clData[i][29] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.WINGER,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERPOSITONSWERTE, false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Flügel
            m_clData[i][30] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.WINGER_TOMID,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Flügel
            m_clData[i][31] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.WINGER_OFF,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Flügel
            m_clData[i][32] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.WINGER_DEF,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Sturm
            m_clData[i][33] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.FORWARD,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERPOSITONSWERTE, false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Sturm
            m_clData[i][34] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.FORWARD_DEF,
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
