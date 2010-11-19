// %92455273:de.hattrickorganizer.gui.model%
package de.hattrickorganizer.gui.model;

import java.util.Vector;

import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;

import plugins.ISpieler;
import plugins.ISpielerPosition;
import de.hattrickorganizer.gui.playeroverview.SpielerStatusLabelEntry;
import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.gui.templates.RatingTableEntry;
import de.hattrickorganizer.gui.templates.SkillEntry;
import de.hattrickorganizer.gui.templates.SpielerLabelEntry;
import de.hattrickorganizer.model.Lineup;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.Spieler;
import de.hattrickorganizer.model.SpielerPosition;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.Helper;


/**
 * DOCUMENT ME!
 *
 * @author Volker Fischer
 * @version 0.2a    31.10.2001
 */
public class AufstellungSpielerTableModel extends AbstractTableModel {
	
	private static final long serialVersionUID = -8616275278600943349L;
	
    //~ Instance fields ----------------------------------------------------------------------------

	/** TODO Missing Parameter Documentation */
    public final String[] m_sToolTipStrings = {
                                            HOVerwaltung.instance().getLanguageString("Name"),
 
    //Trickotnummer
    " ", 
    //Alter
    HOVerwaltung.instance().getLanguageString("Alter"),

    //Beste Position
    HOVerwaltung.instance().getLanguageString("BestePosition"),
 
    //Aufgestellt
    HOVerwaltung.instance().getLanguageString("Aufgestellt"),
 
    //Autolineup
    HOVerwaltung.instance().getLanguageString("AutoAufstellung"),

    //Gruppe
    HOVerwaltung.instance().getLanguageString("Gruppe"),
 
    //Status
    HOVerwaltung.instance().getLanguageString("Status"),
 
    //Form       
    HOVerwaltung.instance().getLanguageString("Form"),

    //Erfahrung
    HOVerwaltung.instance().getLanguageString("Erfahrung"),

    //Bewertung
    HOVerwaltung.instance().getLanguageString("Bewertung"),

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

    //Sturm nach Aussen
    HOVerwaltung.instance().getLanguageString("Sturm_Aus"),
    
    //Sturm Defensiv
    HOVerwaltung.instance().getLanguageString("Sturm_Def"),                                        

    //ID
    HOVerwaltung.instance().getLanguageString("ID")
    };

    /** TODO Missing Parameter Documentation */
    private Object[][] m_clData;

    /** TODO Missing Parameter Documentation */
    private final String[] m_sColumnNames = {
                                            HOVerwaltung.instance().getLanguageString("Name"),
                                            
    //Trickotnummer
    " ", 
    //Alter
    " ",
 
    //Beste Position
    HOVerwaltung.instance().getLanguageString("BestePosition"),

    //Aufgestellt
    " ",

    //Autolineup
    HOVerwaltung.instance().getLanguageString("AutoAufstellung"),

    //Gruppe
    HOVerwaltung.instance().getLanguageString("Gruppe"),

    //Status
    HOVerwaltung.instance().getLanguageString("Status"),
 
    //Form     
    HOVerwaltung.instance().getLanguageString("FO"),
 
    //Erfahrung
    HOVerwaltung.instance().getLanguageString("ER"),

    //Bewertung
    HOVerwaltung.instance().getLanguageString("Bewertung"),

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

    //Aussenverteidiger
    HOVerwaltung.instance().getLanguageString("AVI"),
 
    //Aussenverteidiger
    HOVerwaltung.instance().getLanguageString("AVO"),

    //Aussenverteidiger
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

    //Sturm
    HOVerwaltung.instance().getLanguageString("STUA"),
    
    //Sturm Defensiv
    HOVerwaltung.instance().getLanguageString("STUD"),

    //ID
    HOVerwaltung.instance().getLanguageString("ID")
    };
    
    private Vector<ISpieler> m_vSpieler;
//    private Vector m_vVergleichsSpieler = new Vector();
    private int m_iIDSpalte = 38;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new AufstellungSpielerTableModel object.
     *
     * @param spieler TODO Missing Constructuor Parameter Documentation
     */
    public AufstellungSpielerTableModel(Vector<ISpieler> spieler) {
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
    @Override
	public final boolean isCellEditable(int row, int col) {
        if (getValueAt(row, col) instanceof Boolean) {
            return true;
        } 
        
        return false;
        
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param columnIndex TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	public final Class<?> getColumnClass(int columnIndex) {
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
    @Override
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
        return (m_clData != null)?m_clData.length:0;
    }

    //----------Listener------------------------------        

    /**
     * Listener für die Checkboxen zum Autoaufstellen
     *
     * @param zeile TODO Missing Constructuor Parameter Documentation
     */
    public final void setSpielberechtigung(int zeile) {
        try {
            for (int i = 0; i < this.getRowCount(); i++) {
                final int id = Integer.parseInt(((de.hattrickorganizer.gui.templates.ColorLabelEntry) getValueAt(i,
                                                                                                                 m_iIDSpalte))
                                                .getText());
                final Spieler spieler = getSpieler(id);

                if ((spieler != null)
                    && (spieler.isSpielberechtigt() != ((Boolean) getValueAt(i, 5)).booleanValue())) {
                    spieler.setSpielberechtigt(((Boolean) getValueAt(i, 5)).booleanValue());
                }

                //HOLogger.instance().log(getClass(), spieler.getName () + " " + spieler.isSpielberechtigt () );
            }
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"AufstellungsSpielerTableModel.setSpielberechtigung: " + e);
        }
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
     * Spieler neu setzen
     *
     * @param spieler TODO Missing Constructuor Parameter Documentation
     */
    public final void setValues(Vector<ISpieler> spieler) {
        m_vSpieler = spieler;
        initData();
    }

//    /**
//     * Vergleichsspieler eines alten HRFs
//     *
//     * @param vergleichsSpieler TODO Missing Constructuor Parameter Documentation
//     */
//    public final void setVergleichsValues(Vector vergleichsSpieler) {
//        m_vVergleichsSpieler = vergleichsSpieler;
//        initData();
//    }

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

    /**
     * Passt nur die Aufstellung an
     */
    public final void reInitData() {
    	final Lineup lineup = HOVerwaltung.instance().getModel().getAufstellung(); 
        for (int i = 0; i < m_vSpieler.size(); i++) {
            final Spieler aktuellerSpieler = (Spieler) m_vSpieler.get(i);

            //Spielername
            m_clData[i][0] = new SpielerLabelEntry(aktuellerSpieler,lineup
                                                               .getPositionBySpielerId(aktuellerSpieler
                                                                                       .getSpielerID()),
                                                   0f, false, true);

            //Beste Position
            m_clData[i][3] = new ColorLabelEntry(-SpielerPosition.getSortId(aktuellerSpieler
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
                ((ColorLabelEntry) m_clData[i][3]).setIcon(Helper.ZAHNRAD);
            } else {
                ((ColorLabelEntry) m_clData[i][3]).setIcon(Helper.MANUELL);
            }

            //Aufgestellt 
            if (lineup.isSpielerAufgestellt(aktuellerSpieler.getSpielerID())
                && (lineup.getPositionBySpielerId(aktuellerSpieler.getSpielerID()) != null)) {
                m_clData[i][4] = new ColorLabelEntry(Helper
                                                     .getImage4Position(lineup.getPositionBySpielerId(aktuellerSpieler
                                                                                                            .getSpielerID()),
                                                                        aktuellerSpieler
                                                                        .getTrikotnummer()),
                                                     -lineup.getPositionBySpielerId(aktuellerSpieler
                                                                                          .getSpielerID())
                                                                  .getSortId(),
                                                     ColorLabelEntry.FG_STANDARD,
                                                     
                //TODO Grafik
                ColorLabelEntry.BG_STANDARD, SwingConstants.CENTER);
            } else {
                m_clData[i][4] = new ColorLabelEntry(Helper.getImage4Position(null,
                                                                        aktuellerSpieler
                                                                        .getTrikotnummer()),
                                                     -aktuellerSpieler.getTrikotnummer() - 1000,
                                                     ColorLabelEntry.FG_STANDARD,
                                                     ColorLabelEntry.BG_STANDARD, SwingConstants.CENTER);
            }

            //Gruppe
            ((SmilieEntry) m_clData[i][6]).setSpieler(aktuellerSpieler);
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

//    /**
//     * Gibt den Spieler mit der gleichen ID, wie die übergebene, zurück, oder null
//     *
//     * @param vorlage TODO Missing Constructuor Parameter Documentation
//     *
//     * @return TODO Missing Return Method Documentation
//     */
//    private Spieler getVergleichsSpieler(Spieler vorlage) {
//        final int id = vorlage.getSpielerID();
//
//        for (int i = 0; i < m_vVergleichsSpieler.size(); i++) {
//            final Spieler vergleichsSpieler = (Spieler) m_vVergleichsSpieler.get(i);
//
//            if (vergleichsSpieler.getSpielerID() == id) {
//                //Treffer
//                return vergleichsSpieler;
//            }
//        }
//
//        //nix gefunden
//        return null;
//    }

    //-----initialisierung-----------------------------------------

    /**
     * Erzeugt einen Data[][] aus dem Spielervector
     */
    private void initData() {
        m_clData = new Object[m_vSpieler.size()][m_sColumnNames.length];
        final Lineup lineup = HOVerwaltung.instance().getModel().getAufstellung();
        for (int i = 0; i < m_vSpieler.size(); i++) {
            final Spieler aktuellerSpieler = (Spieler) m_vSpieler.get(i);

            //Name
            m_clData[i][0] = new SpielerLabelEntry(aktuellerSpieler,lineup
                                                               .getPositionBySpielerId(aktuellerSpieler
                                                                                       .getSpielerID()),
                                                   0f, false, true);

            //Trickotnummer
            int sort = aktuellerSpieler.getTrikotnummer();

            if (sort <= 0) {
                //Damit die Spieler ohne Trickot nach den andern kommen
                sort = 10000;
            }

            m_clData[i][1] = new ColorLabelEntry(Helper.getImageIcon4Trickotnummer(aktuellerSpieler
                                                                             .getTrikotnummer()),
                                                 sort, java.awt.Color.black, java.awt.Color.white,
                                                 SwingConstants.LEFT);

            //Alter
            m_clData[i][2] = new ColorLabelEntry(aktuellerSpieler.getAlterWithAgeDays(), aktuellerSpieler.getAlterWithAgeDaysAsString(),
                                                 ColorLabelEntry.FG_STANDARD,
                                                 ColorLabelEntry.BG_STANDARD, SwingConstants.CENTER);

            //Beste Position
            m_clData[i][3] = new ColorLabelEntry(-SpielerPosition.getSortId(aktuellerSpieler
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
                ((ColorLabelEntry) m_clData[i][3]).setIcon(Helper.ZAHNRAD);
            } else {
                ((ColorLabelEntry) m_clData[i][3]).setIcon(Helper.MANUELL);
            }

            //Aufgestellt 
            if (lineup.isSpielerAufgestellt(aktuellerSpieler.getSpielerID())
                && (lineup.getPositionBySpielerId(aktuellerSpieler
                                                                                               .getSpielerID()) != null)) {
                m_clData[i][4] = new ColorLabelEntry(Helper.getImage4Position(lineup.getPositionBySpielerId(aktuellerSpieler
                                                                                                            .getSpielerID()),
                                                                        aktuellerSpieler
                                                                        .getTrikotnummer()),
                                                     -lineup.getPositionBySpielerId(aktuellerSpieler
                                                                                          .getSpielerID())
                                                                  .getSortId(),
                                                     ColorLabelEntry.FG_STANDARD,
                                                     
                //TODO Grafik
                ColorLabelEntry.BG_STANDARD, SwingConstants.CENTER);
            } else {
                m_clData[i][4] = new ColorLabelEntry(Helper.getImage4Position(null,
                                                                        aktuellerSpieler
                                                                        .getTrikotnummer()),
                                                     -aktuellerSpieler.getTrikotnummer() - 1000,
                                                     ColorLabelEntry.FG_STANDARD,
                                                     ColorLabelEntry.BG_STANDARD, SwingConstants.CENTER);
            }

            //Spielberechtigt
            m_clData[i][5] = Boolean.valueOf(aktuellerSpieler.isSpielberechtigt());

            //Gruppe
            m_clData[i][6] = new SmilieEntry();
            ((SmilieEntry) m_clData[i][6]).setSpieler(aktuellerSpieler);

            //Status
            m_clData[i][7] = new SpielerStatusLabelEntry();
            ((SpielerStatusLabelEntry) m_clData[i][7]).setSpieler(aktuellerSpieler);

            //Form
            m_clData[i][8] = new ColorLabelEntry(aktuellerSpieler.getForm(),
                                                 ColorLabelEntry.BG_SPIELERSONDERWERTE, false, 0);

            //Erfahrung
            m_clData[i][9] = new ColorLabelEntry(aktuellerSpieler.getErfahrung(),
                                                 ColorLabelEntry.BG_SPIELERSONDERWERTE, false, 0);

            //Bewertung
            if (aktuellerSpieler.getBewertung() > 0) {
                //Hat im letzen Spiel gespielt
                m_clData[i][10] = new RatingTableEntry(aktuellerSpieler.getBewertung(), true);
            } else {
                m_clData[i][10] = new RatingTableEntry(aktuellerSpieler.getLetzteBewertung(), false);
            }

            //Kondition
            m_clData[i][11] = new ColorLabelEntry(aktuellerSpieler.getKondition(),
                                                  ColorLabelEntry.BG_SPIELEREINZELWERTE, false, 0);

            //Torwart
            m_clData[i][12] = new SkillEntry(aktuellerSpieler.getTorwart()
                                             + aktuellerSpieler.getSubskill4SkillWithOffset(ISpieler.SKILL_TORWART),
                                             ColorLabelEntry.FG_STANDARD, ColorLabelEntry.FG_GRAU,
                                             ColorLabelEntry.BG_SPIELEREINZELWERTE);

            //Verteidigung
            m_clData[i][13] = new SkillEntry(aktuellerSpieler.getVerteidigung()
                                             + aktuellerSpieler.getSubskill4SkillWithOffset(ISpieler.SKILL_VERTEIDIGUNG),
                                             ColorLabelEntry.FG_STANDARD, ColorLabelEntry.FG_GRAU,
                                             ColorLabelEntry.BG_SPIELEREINZELWERTE);

            //Spielaufbau
            m_clData[i][14] = new SkillEntry(aktuellerSpieler.getSpielaufbau()
                                             + aktuellerSpieler.getSubskill4SkillWithOffset(ISpieler.SKILL_SPIELAUFBAU),
                                             ColorLabelEntry.FG_STANDARD, ColorLabelEntry.FG_GRAU,
                                             ColorLabelEntry.BG_SPIELEREINZELWERTE);

            //Passpiel
            m_clData[i][15] = new SkillEntry(aktuellerSpieler.getPasspiel()
                                             + aktuellerSpieler.getSubskill4SkillWithOffset(ISpieler.SKILL_PASSSPIEL),
                                             ColorLabelEntry.FG_STANDARD, ColorLabelEntry.FG_GRAU,
                                             ColorLabelEntry.BG_SPIELEREINZELWERTE);

            //Flügelspiel
            m_clData[i][16] = new SkillEntry(aktuellerSpieler.getFluegelspiel()
                                             + aktuellerSpieler.getSubskill4SkillWithOffset(ISpieler.SKILL_FLUEGEL),
                                             ColorLabelEntry.FG_STANDARD, ColorLabelEntry.FG_GRAU,
                                             ColorLabelEntry.BG_SPIELEREINZELWERTE);

            //Torschuss
            m_clData[i][17] = new SkillEntry(aktuellerSpieler.getTorschuss()
                                             + aktuellerSpieler.getSubskill4SkillWithOffset(ISpieler.SKILL_TORSCHUSS),
                                             ColorLabelEntry.FG_STANDARD, ColorLabelEntry.FG_GRAU,
                                             ColorLabelEntry.BG_SPIELEREINZELWERTE);

            //Standards
            m_clData[i][18] = new SkillEntry(aktuellerSpieler.getStandards()
                                             + aktuellerSpieler.getSubskill4SkillWithOffset(ISpieler.SKILL_STANDARDS),
                                             ColorLabelEntry.FG_STANDARD, ColorLabelEntry.FG_GRAU,
                                             ColorLabelEntry.BG_SPIELEREINZELWERTE);

            //Wert Torwart
            m_clData[i][19] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(ISpielerPosition.KEEPER,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERPOSITONSWERTE, false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Innnenverteidiger
            m_clData[i][20] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(ISpielerPosition.CENTRAL_DEFENDER,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERPOSITONSWERTE, false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Innnenverteidiger Nach Aussen
            m_clData[i][21] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(ISpielerPosition.CENTRAL_DEFENDER_TOWING,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Innnenverteidiger Offensiv
            m_clData[i][22] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(ISpielerPosition.CENTRAL_DEFENDER_OFF,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Aussenverteidiger
            m_clData[i][23] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(ISpielerPosition.BACK,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERPOSITONSWERTE, false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Aussenverteidiger
            m_clData[i][24] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(ISpielerPosition.BACK_TOMID,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Aussenverteidiger
            m_clData[i][25] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(ISpielerPosition.BACK_OFF,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Aussenverteidiger
            m_clData[i][26] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(ISpielerPosition.BACK_DEF,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Mittelfeld
            m_clData[i][27] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(ISpielerPosition.MIDFIELDER,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERPOSITONSWERTE, false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Mittelfeld
            m_clData[i][28] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(ISpielerPosition.MIDFIELDER_TOWING,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Mittelfeld
            m_clData[i][29] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(ISpielerPosition.MIDFIELDER_OFF,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Mittelfeld
            m_clData[i][30] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(ISpielerPosition.MIDFIELDER_DEF,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Flügel
            m_clData[i][31] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(ISpielerPosition.WINGER,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERPOSITONSWERTE, false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Flügel
            m_clData[i][32] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(ISpielerPosition.WINGER_TOMID,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Flügel
            m_clData[i][33] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(ISpielerPosition.WINGER_OFF,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Flügel
            m_clData[i][34] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(ISpielerPosition.WINGER_DEF,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Sturm
            m_clData[i][35] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(ISpielerPosition.FORWARD,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERPOSITONSWERTE, false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);
            
            // Wert Sturm
            m_clData[i][36] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(ISpielerPosition.FORWARD_TOWING,
                    															true),
                                                  ColorLabelEntry.BG_SPIELERPOSITONSWERTE, false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //Wert Sturm
            m_clData[i][37] = new ColorLabelEntry(aktuellerSpieler.calcPosValue(ISpielerPosition.FORWARD_DEF,
                                                                                true),
                                                  ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                  false,
                                                  gui.UserParameter.instance().anzahlNachkommastellen);

            //ID
            m_clData[i][m_iIDSpalte] = new ColorLabelEntry(aktuellerSpieler.getSpielerID(),
                                                           aktuellerSpieler.getSpielerID() + "",
                                                           ColorLabelEntry.FG_STANDARD,
                                                           ColorLabelEntry.BG_STANDARD, SwingConstants.RIGHT);
        }
    }
}
