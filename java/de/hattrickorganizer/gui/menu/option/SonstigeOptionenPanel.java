// %1942107811:de.hattrickorganizer.gui.menu.option%
package de.hattrickorganizer.gui.menu.option;

import de.hattrickorganizer.gui.model.GeldFaktorCBItem;
import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.model.OptionManager;
import de.hattrickorganizer.tools.LanguageFiles;

import java.awt.GridLayout;

import javax.swing.JCheckBox;


/**
 * Alle weiteren Optionen, die Keine Formeln sind
 */
public final class SonstigeOptionenPanel extends ImagePanel
    implements javax.swing.event.ChangeListener, java.awt.event.ItemListener
{
    //~ Static fields/initializers -----------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public static de.hattrickorganizer.gui.model.GeldFaktorCBItem[] WAEHRUNGEN = {
                                                                                     new GeldFaktorCBItem("Sverige kr",1f,1),
                                                                                     new GeldFaktorCBItem("Great Britain £",15f,2),
                                                                                     new GeldFaktorCBItem("Euro €",10f,3),
                                                                                     new GeldFaktorCBItem("México Pesos",1f,4),
                                                                                     new GeldFaktorCBItem("Argentina Pesos",10f, 5),
                                                                                     new GeldFaktorCBItem("USA $",10f, 6),
                                                                                     new GeldFaktorCBItem("Norge Kroner", 1f, 7),
                                                                                     new GeldFaktorCBItem("Oceania AU$",5f, 8),
                                                                                     new GeldFaktorCBItem("Canada C$", 5f, 9),
                                                                                     new GeldFaktorCBItem("Colombia Dollar", 1f, 10),
                                                                                     new GeldFaktorCBItem("South Africa Rand", 1.25f, 11),
                                                                                     new GeldFaktorCBItem("Venezuela VZ$", 10f, 12),
                                                                                     new GeldFaktorCBItem("Prathet Thai Baht", 0.25f, 13),
                                                                                     new GeldFaktorCBItem("Egypt E£", 2.5f, 14),
                                                                                     new GeldFaktorCBItem("Rossiya Roubel", 0.25f, 15),
                                                                                     new GeldFaktorCBItem("România Lei", 0.5f, 16),
                                                                                     new GeldFaktorCBItem("Island Ikr", 0.1f, 17),
                                                                                     new GeldFaktorCBItem("Schweiz CHF", 5f, 18),
                                                                                     new GeldFaktorCBItem("Magyarország hFt", 5f, 19),
                                                                                     new GeldFaktorCBItem("Latvija Lats", 20f,20),
                                                                                     new GeldFaktorCBItem("Indonesia RP", 1f, 21),
                                                                                     new GeldFaktorCBItem("Pilipinas Pesos", 0.25f, 22),
                                                                                     new GeldFaktorCBItem("Eesti Krooni",0.5f,23),
                                                                                     new GeldFaktorCBItem("Srbija i Crna Gora New Dinar", 1f, 24),
                                                                                     new GeldFaktorCBItem("Hrvatska Kuna",1f, 25),
                                                                                     new GeldFaktorCBItem("Hong Kong HKD",1f, 26),
                                                                                     new GeldFaktorCBItem("Taiwan $",10f, 27),
                                                                                     new GeldFaktorCBItem("Bulgaria Lev", 5f, 28),
                                                                                     new GeldFaktorCBItem("Israel NIS",2f, 29),
                                                                                     new GeldFaktorCBItem("Danmark Kroner", 1f,30),
                                                                                     new GeldFaktorCBItem("Brasil Real",5f, 31),
                                                                                     new GeldFaktorCBItem("Chile Pesos", 0.05f,32),
                                                                                     new GeldFaktorCBItem("India Rupees", 0.25f, 33),
                                                                                     new GeldFaktorCBItem("Nippon Yen",0.1f, 34),
                                                                                     new GeldFaktorCBItem("Polska Zloty", 2.5f, 35),
                                                                                     new GeldFaktorCBItem("Uruguay Pesos", 1f, 36),
                                                                                     new GeldFaktorCBItem("South Korea Won", 0.01f, 37),
                                                                                     new GeldFaktorCBItem("Türkiye Lira",10f,38),
                                                                                     new GeldFaktorCBItem("China Yuan", 1f, 39),
                                                                                     new GeldFaktorCBItem("Malaysia MYR", 2.5f, 40),
                                                                                     new GeldFaktorCBItem("Singapore SG$", 5f,41),
                                                                                     new GeldFaktorCBItem("Ceska republika Koruny",0.25f, 42)
                                                                                 };

    /** TODO Missing Parameter Documentation */
    public static de.hattrickorganizer.gui.model.CBItem[] NACHKOMMASTELLEN = {
                                                                                 new de.hattrickorganizer.gui.model.CBItem("1 (1.2)",
                                                                                                                           1),
                                                                                 new de.hattrickorganizer.gui.model.CBItem("2 (1.23)",
                                                                                                                           2)
                                                                             };

    /** TODO Missing Parameter Documentation */
    public static de.hattrickorganizer.gui.model.CBItem[] TIMEZONES = {
                                                                          new de.hattrickorganizer.gui.model.CBItem("-24:00",
                                                                                                                    -24),
                                                                          new de.hattrickorganizer.gui.model.CBItem("-23:00",
                                                                                                                    -23),
                                                                          new de.hattrickorganizer.gui.model.CBItem("-22:00",
                                                                                                                    -22),
                                                                          new de.hattrickorganizer.gui.model.CBItem("-21:00",
                                                                                                                    -21),
                                                                          new de.hattrickorganizer.gui.model.CBItem("-20:00",
                                                                                                                    -20),
                                                                          new de.hattrickorganizer.gui.model.CBItem("-19:00",
                                                                                                                    -19),
                                                                          new de.hattrickorganizer.gui.model.CBItem("-18:00",
                                                                                                                    -18),
                                                                          new de.hattrickorganizer.gui.model.CBItem("-17:00",
                                                                                                                    -17),
                                                                          new de.hattrickorganizer.gui.model.CBItem("-16:00",
                                                                                                                    -16),
                                                                          new de.hattrickorganizer.gui.model.CBItem("-15:00",
                                                                                                                    -15),
                                                                          new de.hattrickorganizer.gui.model.CBItem("-14:00",
                                                                                                                    -14),
                                                                          new de.hattrickorganizer.gui.model.CBItem("-13:00",
                                                                                                                    -13),
                                                                          new de.hattrickorganizer.gui.model.CBItem("-12:00",
                                                                                                                    -12),
                                                                          new de.hattrickorganizer.gui.model.CBItem("-11:00",
                                                                                                                    -11),
                                                                          new de.hattrickorganizer.gui.model.CBItem("-10:00",
                                                                                                                    -10),
                                                                          new de.hattrickorganizer.gui.model.CBItem("-09:00",
                                                                                                                    -9),
                                                                          new de.hattrickorganizer.gui.model.CBItem("-08:00",
                                                                                                                    -8),
                                                                          new de.hattrickorganizer.gui.model.CBItem("-07:00",
                                                                                                                    -7),
                                                                          new de.hattrickorganizer.gui.model.CBItem("-06:00",
                                                                                                                    -6),
                                                                          new de.hattrickorganizer.gui.model.CBItem("-05:00",
                                                                                                                    -5),
                                                                          new de.hattrickorganizer.gui.model.CBItem("-04:00",
                                                                                                                    -4),
                                                                          new de.hattrickorganizer.gui.model.CBItem("-03:00",
                                                                                                                    -3),
                                                                          new de.hattrickorganizer.gui.model.CBItem("-02:00",
                                                                                                                    -2),
                                                                          new de.hattrickorganizer.gui.model.CBItem("-01:00",
                                                                                                                    -1),
                                                                          new de.hattrickorganizer.gui.model.CBItem("00:00",
                                                                                                                    0),
                                                                          new de.hattrickorganizer.gui.model.CBItem("+01:00",
                                                                                                                    1),
                                                                          new de.hattrickorganizer.gui.model.CBItem("+02:00",
                                                                                                                    2),
                                                                          new de.hattrickorganizer.gui.model.CBItem("+03:00",
                                                                                                                    3),
                                                                          new de.hattrickorganizer.gui.model.CBItem("+04:00",
                                                                                                                    4),
                                                                          new de.hattrickorganizer.gui.model.CBItem("+05:00",
                                                                                                                    5),
                                                                          new de.hattrickorganizer.gui.model.CBItem("+06:00",
                                                                                                                    6),
                                                                          new de.hattrickorganizer.gui.model.CBItem("+07:00",
                                                                                                                    7),
                                                                          new de.hattrickorganizer.gui.model.CBItem("+08:00",
                                                                                                                    8),
                                                                          new de.hattrickorganizer.gui.model.CBItem("+09:00",
                                                                                                                    9),
                                                                          new de.hattrickorganizer.gui.model.CBItem("+10:00",
                                                                                                                    10),
                                                                          new de.hattrickorganizer.gui.model.CBItem("+11:00",
                                                                                                                    11),
                                                                          new de.hattrickorganizer.gui.model.CBItem("+12:00",
                                                                                                                    12),
                                                                          new de.hattrickorganizer.gui.model.CBItem("+13:00",
                                                                                                                    13),
                                                                          new de.hattrickorganizer.gui.model.CBItem("+14:00",
                                                                                                                    14),
                                                                          new de.hattrickorganizer.gui.model.CBItem("+15:00",
                                                                                                                    15),
                                                                          new de.hattrickorganizer.gui.model.CBItem("+16:00",
                                                                                                                    16),
                                                                          new de.hattrickorganizer.gui.model.CBItem("+17:00",
                                                                                                                    17),
                                                                          new de.hattrickorganizer.gui.model.CBItem("+18:00",
                                                                                                                    18),
                                                                          new de.hattrickorganizer.gui.model.CBItem("+19:00",
                                                                                                                    19),
                                                                          new de.hattrickorganizer.gui.model.CBItem("+20:00",
                                                                                                                    20),
                                                                          new de.hattrickorganizer.gui.model.CBItem("+21:00",
                                                                                                                    21),
                                                                          new de.hattrickorganizer.gui.model.CBItem("+22:00",
                                                                                                                    22),
                                                                          new de.hattrickorganizer.gui.model.CBItem("+23:00",
                                                                                                                    23),
                                                                          new de.hattrickorganizer.gui.model.CBItem("+24:00",
                                                                                                                    24),
                                                                      };

    //~ Instance fields ----------------------------------------------------------------------------

    //Nicht Statik, da es sonst zu früh initialisiert wird

    /** TODO Missing Parameter Documentation */
    public de.hattrickorganizer.gui.model.CBItem[] SORTIERUNG = {
                                                                    new de.hattrickorganizer.gui.model.CBItem(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                                                                     .getResource()
                                                                                                                                                     .getProperty("Name"),
                                                                                                              gui.UserParameter.SORT_NAME),
                                                                    new de.hattrickorganizer.gui.model.CBItem(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                                                                     .getResource()
                                                                                                                                                     .getProperty("BestePosition"),
                                                                                                              gui.UserParameter.SORT_BESTPOS),
                                                                    new de.hattrickorganizer.gui.model.CBItem(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                                                                     .getResource()
                                                                                                                                                     .getProperty("Aufgestellt"),
                                                                                                              gui.UserParameter.SORT_AUFGESTELLT),
                                                                    new de.hattrickorganizer.gui.model.CBItem(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                                                                     .getResource()
                                                                                                                                                     .getProperty("Gruppe"),
                                                                                                              gui.UserParameter.SORT_GRUPPE),
                                                                    new de.hattrickorganizer.gui.model.CBItem(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                                                                     .getResource()
                                                                                                                                                     .getProperty("Bewertung"),
                                                                                                              gui.UserParameter.SORT_BEWERTUNG),
                                                                };
    private ComboBoxPanel m_jcbNachkomma;
    private ComboBoxPanel m_jcbSortierung;
    private ComboBoxPanel m_jcbSprachdatei;
    private ComboBoxPanel m_jcbTimeZoneDifference;
    private ComboBoxPanel m_jcbWaehrung;
//    private JCheckBox m_jchEinzelnePositionen;
//    private JCheckBox m_jchLogout;
//    private JCheckBox m_jchShowSaveDialog;
//    private JCheckBox m_jchUpdateCheck;
    private JCheckBox m_jchZahlenBewertung;

    //private ComboBoxPanel       m_jcbHTIP           = null;
    private SliderPanel m_jslDeadline;
    private SliderPanel m_jslMinStaerke;
    private SliderPanel m_jslSchriftgroesse;
    private SliderPanel m_jslWetterEffekt;
	private SliderPanel m_jslFutureWeeks;
    private String m_sAlteSprachdatei = gui.UserParameter.temp().sprachDatei;
    private boolean m_bZahleFuerSkill = gui.UserParameter.temp().zahlenFuerSkill;
    private int m_iAlteSchriftgroesse = gui.UserParameter.temp().schriftGroesse;
	private int m_iFutureWeeks = gui.UserParameter.temp().futureWeeks;
    private int m_iOldTimeZone = gui.UserParameter.temp().TimeZoneDifference;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new SonstigeOptionenPanel object.
     */
    public SonstigeOptionenPanel() {
        initComponents();
    }

    //~ Methods ------------------------------------------------------------------------------------

    //-------
    public static float getFaktorGeld4WaehrungsID(int id) {
        for (int i = 0; i < SonstigeOptionenPanel.WAEHRUNGEN.length; i++) {
            if (id == SonstigeOptionenPanel.WAEHRUNGEN[i].getId()) {
                return SonstigeOptionenPanel.WAEHRUNGEN[i].getFaktor();
            }
        }

        //nix gefunden
        return 1.0f;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param itemEvent TODO Missing Method Parameter Documentation
     */
    public final void itemStateChanged(java.awt.event.ItemEvent itemEvent) {
        //Kein Selected Event!
        gui.UserParameter.temp().zahlenFuerSkill = m_jchZahlenBewertung.isSelected();
 //       gui.UserParameter.temp().einzelnePositionenAnzeigen = m_jchEinzelnePositionen
 //                                                                 .isSelected();

        if (itemEvent.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            //gui.UserParameter.temp ().htip          = ( (String) m_jcbHTIP.getSelectedItem () );
            gui.UserParameter.temp().faktorGeld = ((de.hattrickorganizer.gui.model.GeldFaktorCBItem) m_jcbWaehrung
                                                       .getSelectedItem()).getFaktor();

            //gui.UserParameter.temp ().waehrungsID   = ( (gui.model.GeldFaktorCBItem)m_jcbWaehrung.getSelectedItem () ).getId ();
            gui.UserParameter.temp().TimeZoneDifference = ((de.hattrickorganizer.gui.model.CBItem) m_jcbTimeZoneDifference
                                                               .getSelectedItem()).getId();
            gui.UserParameter.temp().anzahlNachkommastellen = ((de.hattrickorganizer.gui.model.CBItem) m_jcbNachkomma
                                                                   .getSelectedItem()).getId();
            gui.UserParameter.temp().sprachDatei = ((String) m_jcbSprachdatei.getSelectedItem());
            gui.UserParameter.temp().standardsortierung = ((de.hattrickorganizer.gui.model.CBItem) m_jcbSortierung
                                                               .getSelectedItem()).getId();            
        }
        if ((!gui.UserParameter.temp().sprachDatei.equals(gui.UserParameter.instance().sprachDatei) )
            || (gui.UserParameter.temp().TimeZoneDifference != gui.UserParameter.instance().TimeZoneDifference))
        	  OptionManager.instance().setRestartNeeded();
        if (gui.UserParameter.temp().zahlenFuerSkill != gui.UserParameter.instance().zahlenFuerSkill)
        	OptionManager.instance().setReInitNeeded();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param changeEvent TODO Missing Method Parameter Documentation
     */
    public final void stateChanged(javax.swing.event.ChangeEvent changeEvent) {
        gui.UserParameter.temp().deadlineFrist = (int) m_jslDeadline.getValue();
        gui.UserParameter.temp().MinIdealPosStk = m_jslMinStaerke.getValue();
        gui.UserParameter.temp().WetterEffektBonus = m_jslWetterEffekt.getValue();
		gui.UserParameter.temp().futureWeeks = (int) m_jslFutureWeeks.getValue();		
        gui.UserParameter.temp().schriftGroesse = (int) m_jslSchriftgroesse.getValue();
        
        if ((gui.UserParameter.temp().schriftGroesse != gui.UserParameter.instance().schriftGroesse)
			|| (gui.UserParameter.temp().futureWeeks != gui.UserParameter.instance().futureWeeks)) {
        	  OptionManager.instance().setRestartNeeded();
        }
            
        
    }

    /**
     * TODO Missing Method Documentation
     */
    private void initComponents() {
        setLayout(new GridLayout(10, 1, 4, 4));

        //        m_jcbHTIP= new ComboBoxPanel( model.HOVerwaltung.instance ().getResource ().getProperty( "Hattrick" ), HT_IP_ADRESSEN, 120 );
        //        m_jcbHTIP.setSelectedItem ( gui.UserParameter.temp ().htip );
        //        m_jcbHTIP.addItemListener ( this );
        //        add( m_jcbHTIP );
        m_jslDeadline = new SliderPanel(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                               .getResource()
                                                                               .getProperty("TransferWecker"),
                                        60, 0, 1f / 60000f, 1.0f, 120);
        m_jslDeadline.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                            .getProperty("tt_Optionen_TransferWecker"));
        m_jslDeadline.setValue((float) gui.UserParameter.temp().deadlineFrist);
        m_jslDeadline.addChangeListener(this);
        add(m_jslDeadline);

        m_jslMinStaerke = new SliderPanel(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                 .getResource()
                                                                                 .getProperty("MinStaerkeIdealPos"),
                                          100, 0, 10, 0.1f, 120);
        m_jslMinStaerke.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                              .getResource()
                                                                              .getProperty("tt_Optionen_MinStaerkeIdealPos"));
        m_jslMinStaerke.setValue(gui.UserParameter.temp().MinIdealPosStk);
        m_jslMinStaerke.addChangeListener(this);
        add(m_jslMinStaerke);

        m_jslWetterEffekt = new SliderPanel(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                   .getResource()
                                                                                   .getProperty("Wettereffekt"),
                                            100, 0, 100, 1f, 120);
        m_jslWetterEffekt.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                .getResource()
                                                                                .getProperty("tt_Optionen_Wettereffekt"));
        m_jslWetterEffekt.setValue(gui.UserParameter.temp().WetterEffektBonus);
        m_jslWetterEffekt.addChangeListener(this);
        add(m_jslWetterEffekt);

		m_jslFutureWeeks = new SliderPanel(de.hattrickorganizer.model.HOVerwaltung.instance()
																				   .getResource()
																				   .getProperty("futureWeeks"),
											80, 0, 1, 1f, 120);
		m_jslFutureWeeks.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
																				.getResource()
																				.getProperty("tt_Optionen_futureWeeks"));
		m_jslFutureWeeks.setValue(gui.UserParameter.temp().futureWeeks);
		m_jslFutureWeeks.addChangeListener(this);
		add(m_jslFutureWeeks);

        m_jslSchriftgroesse = new SliderPanel(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                     .getResource()
                                                                                     .getProperty("Schriftgroesse"),
                                              12, 8, 1, 1.0f, 120);
        m_jslSchriftgroesse.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                  .getResource()
                                                                                  .getProperty("tt_Optionen_Schriftgroesse"));
        m_jslSchriftgroesse.setValue(gui.UserParameter.temp().schriftGroesse);
        m_jslSchriftgroesse.addChangeListener(this);
        add(m_jslSchriftgroesse);

        final String[] sprachdateien = LanguageFiles.getSprachDateien();

        try {
            java.util.Arrays.sort(sprachdateien);
        } catch (Exception e) {
        }

        m_jcbSprachdatei = new ComboBoxPanel(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                    .getResource()
                                                                                    .getProperty("Sprachdatei"),
                                             sprachdateien, 120);
        m_jcbSprachdatei.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                               .getResource()
                                                                               .getProperty("tt_Optionen_Sprachdatei"));
        m_jcbSprachdatei.setSelectedItem(gui.UserParameter.temp().sprachDatei);
        m_jcbSprachdatei.addItemListener(this);
        add(m_jcbSprachdatei);

        m_jcbTimeZoneDifference = new ComboBoxPanel(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                           .getResource()
                                                                                           .getProperty("options_TimeZone"),
                                                    TIMEZONES, 120);
        m_jcbTimeZoneDifference.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                      .getResource()
                                                                                      .getProperty("tt_Options_TimeZone"));
        m_jcbTimeZoneDifference.setSelectedId(gui.UserParameter.temp().TimeZoneDifference);
        m_jcbTimeZoneDifference.addItemListener(this);
        add(m_jcbTimeZoneDifference);

        java.util.Arrays.sort(WAEHRUNGEN);
        m_jcbWaehrung = new ComboBoxPanel(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                 .getResource()
                                                                                 .getProperty("Waehrungsfaktor"),
                                          WAEHRUNGEN, 120);
        m_jcbWaehrung.setSelectedId(gui.UserParameter.temp().waehrungsID);
        m_jcbWaehrung.addItemListener(this);

        //        add( m_jcbWaehrung );
        m_jcbSortierung = new ComboBoxPanel(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                   .getResource()
                                                                                   .getProperty("Defaultsortierung"),
                                            SORTIERUNG, 120);
        m_jcbSortierung.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                              .getResource()
                                                                              .getProperty("tt_Optionen_Defaultsortierung"));
        m_jcbSortierung.setSelectedId(gui.UserParameter.temp().standardsortierung);
        m_jcbSortierung.addItemListener(this);
        add(m_jcbSortierung);

        m_jcbNachkomma = new ComboBoxPanel(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                  .getResource()
                                                                                  .getProperty("Nachkommastellen"),
                                           NACHKOMMASTELLEN, 120);
        m_jcbNachkomma.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                             .getResource()
                                                                             .getProperty("tt_Optionen_Nachkommastellen"));
        m_jcbNachkomma.setSelectedId(gui.UserParameter.temp().anzahlNachkommastellen);
        m_jcbNachkomma.addItemListener(this);
        add(m_jcbNachkomma);

        /*
        m_jchEinzelnePositionen = new JCheckBox(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                       .getResource()
                                                                                       .getProperty("EinzelneTabellenPositionen"));
        m_jchEinzelnePositionen.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                      .getResource()
                                                                                      .getProperty("tt_Optionen_EinzelneTabellenPositionen"));
        m_jchEinzelnePositionen.setOpaque(false);
        m_jchEinzelnePositionen.setSelected(gui.UserParameter.temp().einzelnePositionenAnzeigen);
        m_jchEinzelnePositionen.addItemListener(this);
        add(m_jchEinzelnePositionen);
*/
        m_jchZahlenBewertung = new JCheckBox(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                    .getResource()
                                                                                    .getProperty("SkillZahlen")
                                             + " : "
                                             + de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                      .getResource()
                                                                                      .getProperty("passabel")
                                             + " (6)");
        m_jchZahlenBewertung.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                   .getResource()
                                                                                   .getProperty("tt_Optionen_SkillZahlen"));
        m_jchZahlenBewertung.setOpaque(false);
        m_jchZahlenBewertung.setSelected(gui.UserParameter.temp().zahlenFuerSkill);
        m_jchZahlenBewertung.addItemListener(this);
        add(m_jchZahlenBewertung);

    }
}
