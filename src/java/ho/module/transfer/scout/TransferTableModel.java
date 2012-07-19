// %3702132305:de.hattrickorganizer.gui.model%
package ho.module.transfer.scout;


import ho.core.constants.player.PlayerSkill;
import ho.core.gui.comp.entry.ColorLabelEntry;
import ho.core.gui.comp.entry.HomegrownEntry;
import ho.core.gui.comp.entry.SpielerLabelEntry;
import ho.core.model.HOVerwaltung;
import ho.core.model.player.ISpielerPosition;
import ho.core.model.player.Spieler;
import ho.core.model.player.SpielerPosition;

import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;



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
	    // Homegrown
	    HOVerwaltung.instance().getLanguageString("Motherclub"),
	    //Erfahrung
	    HOVerwaltung.instance().getLanguageString("Erfahrung"),
	    //Form
	    HOVerwaltung.instance().getLanguageString("Form"),
	    //Kondition
	    HOVerwaltung.instance().getLanguageString("skill.stamina"),
	    //Loyalty
	    HOVerwaltung.instance().getLanguageString("Loyalty"),
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
	    //Torwart
	    HOVerwaltung.instance().getLanguageString("ls.player.position.keeper"),
	    //Innenverteidiger
	    HOVerwaltung.instance().getLanguageString("ls.player.position.centraldefender"),
	    //Innenverteidiger Nach Aussen
	    HOVerwaltung.instance().getLanguageString("ls.player.position.centraldefendertowardswing"),
	    //Innenverteidiger Offensiv
	    HOVerwaltung.instance().getLanguageString("ls.player.position.centraldefenderoffensive"),
	    //Aussenverteidiger
	    HOVerwaltung.instance().getLanguageString("ls.player.position.wingback"),
	    //Aussenverteidiger Nach Innen
	    HOVerwaltung.instance().getLanguageString("ls.player.position.wingbacktowardsmiddle"),
	    //Aussenverteidiger Offensiv
	    HOVerwaltung.instance().getLanguageString("ls.player.position.wingbackoffensive"),
	    //Aussenverteidiger Defensiv
	    HOVerwaltung.instance().getLanguageString("ls.player.position.wingbackdefensive"),
	    //Mittelfeld
	    HOVerwaltung.instance().getLanguageString("ls.player.position.innermidfielder"),
	    //Mittelfeld Nach Aussen
	    HOVerwaltung.instance().getLanguageString("ls.player.position.innermidfieldertowardswing"),
	    //Mittelfeld Offensiv
	    HOVerwaltung.instance().getLanguageString("ls.player.position.innermidfielderoffensive"),
	    //Mittelfeld Defensiv
	    HOVerwaltung.instance().getLanguageString("ls.player.position.innermidfielderdefensive"),
	    //Flügel
	    HOVerwaltung.instance().getLanguageString("ls.player.position.winger"),
	    //Flügel Nach Innen
	    HOVerwaltung.instance().getLanguageString("ls.player.position.wingertowardsmiddle"),
	    //Flügel Offensiv
	    HOVerwaltung.instance().getLanguageString("ls.player.position.wingeroffensive"),
	    //Flügel Defensiv
	    HOVerwaltung.instance().getLanguageString("ls.player.position.wingerdefensive"),
	    //Sturm
	    HOVerwaltung.instance().getLanguageString("ls.player.position.forward"),
	    //Sturm Defensiv
	    HOVerwaltung.instance().getLanguageString("ls.player.position.forwarddefensive"),
		//Sturm Nach Aussen
	    HOVerwaltung.instance().getLanguageString("ls.player.position.forwardtowardswing"),
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
	    // Homegrown
	    HOVerwaltung.instance().getLanguageString("MC"),
	    //Erfahrung
	    HOVerwaltung.instance().getLanguageString("ER"),
	    //Form
	    HOVerwaltung.instance().getLanguageString("FO"),
	    //Kondition
	    HOVerwaltung.instance().getLanguageString("KO"),
	    // Loyalty
	    HOVerwaltung.instance().getLanguageString("LOY"),
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
	    //Torwart
	    HOVerwaltung.instance().getLanguageString("ls.player.position_short.keeper"),
	    //Innenverteidiger
	    HOVerwaltung.instance().getLanguageString("ls.player.position_short.centraldefender"),
	    //Innenverteidiger Nach Aussen
	    HOVerwaltung.instance().getLanguageString("ls.player.position_short.centraldefendertowardswing"),
	    //Innenverteidiger Offensiv
	    HOVerwaltung.instance().getLanguageString("ls.player.position_short.centraldefenderoffensive"),
	    //Aussenverteidiger
	    HOVerwaltung.instance().getLanguageString("ls.player.position_short.wingback"),
	    //Aussenverteidiger Zur Mitte
	    HOVerwaltung.instance().getLanguageString("ls.player.position_short.wingbacktowardsmiddle"),
	    //Aussenverteidiger Offensiv
	    HOVerwaltung.instance().getLanguageString("ls.player.position_short.wingbackoffensive"),
	    //Aussenverteidiger Defensiv
	    HOVerwaltung.instance().getLanguageString("ls.player.position_short.wingbackdefensive"),
	    //Mittelfeld
	    HOVerwaltung.instance().getLanguageString("ls.player.position_short.innermidfielder"),
	    //Mittelfeld Nach Aussen
	    HOVerwaltung.instance().getLanguageString("ls.player.position_short.innermidfieldertowardswing"),
	    //Mittelfeld Offensiv
	    HOVerwaltung.instance().getLanguageString("ls.player.position_short.innermidfielderoffensive"),
	    //Mittelfeld Defensiv
	    HOVerwaltung.instance().getLanguageString("ls.player.position_short.innermidfielderdefensive"),
	    //Flügel
	    HOVerwaltung.instance().getLanguageString("ls.player.position_short.winger"),
	    //Flügel Nach Innen
	    HOVerwaltung.instance().getLanguageString("ls.player.position_short.wingertowardsmiddle"),
	    //Flügel Offensiv
	    HOVerwaltung.instance().getLanguageString("ls.player.position_short.wingeroffensive"),
	    //Flügel Defensiv
	    HOVerwaltung.instance().getLanguageString("ls.player.position_short.wingerdefensive"),
	    //Sturm
	    HOVerwaltung.instance().getLanguageString("ls.player.position_short.forward"),
	    //Sturm Defensiv
	    HOVerwaltung.instance().getLanguageString("ls.player.position_short.forwarddefensive"),
	  //Sturm Nach Aussen
	    HOVerwaltung.instance().getLanguageString("ls.player.position_short.forwardtowardswing"),
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
            aktuellerSpieler.setLoyalty(aktuellerScoutEintrag.getLoyalty());
            aktuellerSpieler.setHomeGrown(aktuellerScoutEintrag.isHomegrown());
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
                                                 java.text.DateFormat.getDateTimeInstance()
                                                 .format(aktuellerScoutEintrag.getDeadline()),
                                                 ColorLabelEntry.FG_STANDARD,
                                                 ColorLabelEntry.BG_STANDARD, SwingConstants.RIGHT);
            //Beste Position
            m_clData[i][4] = new ColorLabelEntry(SpielerPosition
            		.getSortId(aktuellerSpieler.getIdealPosition(), false)
            		- (aktuellerSpieler.getIdealPosStaerke(true) / 100.0f),
            		SpielerPosition.getNameForPosition(aktuellerSpieler.getIdealPosition())
            		+ " ("
            		+ aktuellerSpieler.calcPosValue(aktuellerSpieler.getIdealPosition(), true) + ")",
            		ColorLabelEntry.FG_STANDARD, ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);
            //Alter
            m_clData[i][5] = new ColorLabelEntry(aktuellerScoutEintrag.getAlterWithAgeDays(),
            		aktuellerScoutEintrag.getAlterWithAgeDaysAsString(),
            		ColorLabelEntry.FG_STANDARD, ColorLabelEntry.BG_STANDARD, SwingConstants.CENTER);
            //TSI
            m_clData[i][6] = new ColorLabelEntry(aktuellerScoutEintrag.getTSI()+"",
            		ColorLabelEntry.FG_STANDARD, ColorLabelEntry.BG_STANDARD, SwingConstants.RIGHT);
            // Homegrown
            HomegrownEntry home = new HomegrownEntry();
            home.setSpieler(aktuellerSpieler);
            m_clData[i][7] = home;
            //Erfahrung
            m_clData[i][8] = new ColorLabelEntry(aktuellerSpieler.getErfahrung()+"",
            		ColorLabelEntry.FG_STANDARD, ColorLabelEntry.BG_SPIELERSONDERWERTE, SwingConstants.RIGHT);
            //Form
            m_clData[i][9] = new ColorLabelEntry(aktuellerSpieler.getForm()+"",
            		ColorLabelEntry.FG_STANDARD, ColorLabelEntry.BG_SPIELERSONDERWERTE, SwingConstants.RIGHT);
            //Kondition
            m_clData[i][10] = new ColorLabelEntry(aktuellerSpieler.getKondition()+"",
            		ColorLabelEntry.FG_STANDARD, ColorLabelEntry.BG_SPIELEREINZELWERTE, SwingConstants.RIGHT);
            // Loyalty
            m_clData[i][11] = new ColorLabelEntry(aktuellerSpieler.getLoyalty()+"",
            		ColorLabelEntry.FG_STANDARD, ColorLabelEntry.BG_SPIELEREINZELWERTE,
                    SwingConstants.RIGHT);

            //Torwart
            m_clData[i][12] = new ColorLabelEntry(aktuellerSpieler.getTorwart()+"",
                                                  ColorLabelEntry.FG_STANDARD,
                                                  ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                  SwingConstants.RIGHT);

            //Verteidigung
            m_clData[i][13] = new ColorLabelEntry(aktuellerSpieler.getVerteidigung()+"",
                                                  ColorLabelEntry.FG_STANDARD,
                                                  ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                  SwingConstants.RIGHT);

            //Spielaufbau
            m_clData[i][14] = new ColorLabelEntry(aktuellerSpieler.getSpielaufbau()+"",
                                                  ColorLabelEntry.FG_STANDARD,
                                                  ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                  SwingConstants.RIGHT);

            //Passpiel
            m_clData[i][15] = new ColorLabelEntry(aktuellerSpieler.getPasspiel()+"",
                                                  ColorLabelEntry.FG_STANDARD,
                                                  ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                  SwingConstants.RIGHT);

            //Flügelspiel
            m_clData[i][16] = new ColorLabelEntry(aktuellerSpieler.getFluegelspiel()+"",
                                                  ColorLabelEntry.FG_STANDARD,
                                                  ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                  SwingConstants.RIGHT);

            //Torschuss
            m_clData[i][17] = new ColorLabelEntry(aktuellerSpieler.getTorschuss()+"",
                                                  ColorLabelEntry.FG_STANDARD,
                                                  ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                  SwingConstants.RIGHT);

            //Standards
            m_clData[i][18] = new ColorLabelEntry(aktuellerSpieler.getStandards()+"",
                                                  ColorLabelEntry.FG_STANDARD,
                                                  ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                  SwingConstants.RIGHT);

            //Wert Torwart
            m_clData[i][19] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(ISpielerPosition.KEEPER,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERPOSITONSWERTE, false,
                                                  ho.core.model.UserParameter.instance().anzahlNachkommastellen);

            //Wert Innnenverteidiger
            m_clData[i][20] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(ISpielerPosition.CENTRAL_DEFENDER,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERPOSITONSWERTE, false,
                                                  ho.core.model.UserParameter.instance().anzahlNachkommastellen);

            //Wert Innnenverteidiger Nach Aussen
            m_clData[i][21] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.CENTRAL_DEFENDER_TOWING,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  ho.core.model.UserParameter.instance().anzahlNachkommastellen);

            //Wert Innnenverteidiger Offensiv
            m_clData[i][22] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.CENTRAL_DEFENDER_OFF,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  ho.core.model.UserParameter.instance().anzahlNachkommastellen);

            //Wert Aussenverteidiger
            m_clData[i][23] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.BACK,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERPOSITONSWERTE, false,
                                                  ho.core.model.UserParameter.instance().anzahlNachkommastellen);

            //Wert Aussenverteidiger Nach Innen
            m_clData[i][24] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.BACK_TOMID,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  ho.core.model.UserParameter.instance().anzahlNachkommastellen);

            //Wert Aussenverteidiger Offensiv
            m_clData[i][25] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.BACK_OFF,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  ho.core.model.UserParameter.instance().anzahlNachkommastellen);

            //Wert Aussenverteidiger Defensiv
            m_clData[i][26] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.BACK_DEF,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  ho.core.model.UserParameter.instance().anzahlNachkommastellen);

            //Wert Mittelfeld
            m_clData[i][27] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.MIDFIELDER,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERPOSITONSWERTE, false,
                                                  ho.core.model.UserParameter.instance().anzahlNachkommastellen);

            //Wert Mittelfeld Nach Aussen
            m_clData[i][28] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.MIDFIELDER_TOWING,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  ho.core.model.UserParameter.instance().anzahlNachkommastellen);

            //Wert Mittelfeld Offensiv
            m_clData[i][29] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.MIDFIELDER_OFF,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  ho.core.model.UserParameter.instance().anzahlNachkommastellen);

            //Wert Mittelfeld Defensiv
            m_clData[i][30] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.MIDFIELDER_DEF,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  ho.core.model.UserParameter.instance().anzahlNachkommastellen);

            //Wert Flügel
            m_clData[i][31] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.WINGER,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERPOSITONSWERTE, false,
                                                  ho.core.model.UserParameter.instance().anzahlNachkommastellen);

            //Wert Flügel Nach Innen
            m_clData[i][32] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.WINGER_TOMID,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  ho.core.model.UserParameter.instance().anzahlNachkommastellen);

            //Wert Flügel Offensiv
            m_clData[i][33] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.WINGER_OFF,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  ho.core.model.UserParameter.instance().anzahlNachkommastellen);

            //Wert Flügel Defensiv
            m_clData[i][34] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.WINGER_DEF,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  ho.core.model.UserParameter.instance().anzahlNachkommastellen);

            //Wert Sturm
            m_clData[i][35] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.FORWARD,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERPOSITONSWERTE, false,
                                                  ho.core.model.UserParameter.instance().anzahlNachkommastellen);

            //Wert Sturm Defensiv
            m_clData[i][36] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.FORWARD_DEF,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  ho.core.model.UserParameter.instance().anzahlNachkommastellen);

          //Wert Sturm Nach Aussen
            m_clData[i][37] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(SpielerPosition.FORWARD_TOWING,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  ho.core.model.UserParameter.instance().anzahlNachkommastellen);

            //Notiz
            m_clData[i][38] = new ColorLabelEntry(aktuellerScoutEintrag.getInfo(),
                                                  ColorLabelEntry.FG_STANDARD,
                                                  ColorLabelEntry.BG_STANDARD, JLabel.LEFT);

        }
    }
}
