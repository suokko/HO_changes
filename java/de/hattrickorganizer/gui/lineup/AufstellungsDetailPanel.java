// %3280892954:de.hattrickorganizer.gui.lineup%
package de.hattrickorganizer.gui.lineup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Properties;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import plugins.IMatchDetails;
import plugins.ISpieler;
import plugins.ITeam;
import de.hattrickorganizer.gui.HOMainFrame;
import de.hattrickorganizer.gui.model.AufstellungCBItem;
import de.hattrickorganizer.gui.model.CBItem;
import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.gui.templates.RatingTableEntry;
import de.hattrickorganizer.model.Aufstellung;
import de.hattrickorganizer.model.HOMiniModel;
import de.hattrickorganizer.model.HOModel;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.tools.PlayerHelper;
import de.hattrickorganizer.tools.extension.FileExtensionManager;


/**
 * Gibt die Gesamtstärken aus
 */
final class AufstellungsDetailPanel extends ImagePanel
    implements de.hattrickorganizer.gui.Refreshable, java.awt.event.ItemListener
{
    //~ Instance fields ----------------------------------------------------------------------------

    private AufstellungsRatingPanel m_jpRating = new AufstellungsRatingPanel();
	    
    private ColorLabelEntry m_jpAktuellesSystem = new ColorLabelEntry("",
                                                                      ColorLabelEntry.FG_STANDARD,
                                                                      ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                                      SwingConstants.LEFT);
    private ColorLabelEntry m_jpDurchschnittErfahrung = new ColorLabelEntry("",
                                                                            ColorLabelEntry.FG_STANDARD,
                                                                            ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                                            SwingConstants.LEFT);
    private ColorLabelEntry m_jpErfahrung343 = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                   ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                                   SwingConstants.CENTER);
    private ColorLabelEntry m_jpErfahrung352 = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                   ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                                   SwingConstants.CENTER);
    private ColorLabelEntry m_jpErfahrung433 = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                   ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                                   SwingConstants.CENTER);
    private ColorLabelEntry m_jpErfahrung442 = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                   ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                                   SwingConstants.CENTER);
    private ColorLabelEntry m_jpErfahrung451 = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                   ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                                   SwingConstants.CENTER);
    private ColorLabelEntry m_jpErfahrung532 = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                   ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                                   SwingConstants.CENTER);
    private ColorLabelEntry m_jpErfahrung541 = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                   ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                                   SwingConstants.CENTER);
    private ColorLabelEntry m_jpErfahrungAktuellesSystem = new ColorLabelEntry("",
                                                                               ColorLabelEntry.FG_STANDARD,
                                                                               ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                                               SwingConstants.CENTER);
    private ColorLabelEntry m_jpGesamtStaerkeText = new ColorLabelEntry("",
                                                                        ColorLabelEntry.FG_STANDARD,
                                                                        Color.WHITE,
                                                                        SwingConstants.RIGHT);
    private ColorLabelEntry m_jpHatstat = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                              ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                              SwingConstants.CENTER);
    private ColorLabelEntry m_jpLoddarstat = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                 ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                                 SwingConstants.CENTER);
    private ColorLabelEntry m_jpTaktikStaerke = new ColorLabelEntry("",
                                                                    ColorLabelEntry.FG_STANDARD,
                                                                    ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE,
                                                                    SwingConstants.LEFT);
    private JCheckBox m_jchSpielort = new JCheckBox(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                           .getResource()
                                                                                           .getProperty("Heimspiel"));
    private RatingTableEntry m_jpGesamtStaerke = new RatingTableEntry();
    private CBItem[] EINSTELLUNG = {
                                       new CBItem(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                         .getResource()
                                                                                         .getProperty("PIC"),
                                                  IMatchDetails.EINSTELLUNG_PIC),
                                       new CBItem(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                         .getResource()
                                                                                         .getProperty("Normal"),
                                                  IMatchDetails.EINSTELLUNG_NORMAL),
                                       new CBItem(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                         .getResource()
                                                                                         .getProperty("MOTS"),
                                                  IMatchDetails.EINSTELLUNG_MOTS)
                                   };
    private JComboBox m_jcbEinstellung = new JComboBox(EINSTELLUNG);
    private CBItem[] SELBSTVERTRAUEN = {
                                           new CBItem(de.hattrickorganizer.model.Team
                                                      .getNameForSelbstvertrauen(ITeam.SV_nichtVorhanden),
                                                      ITeam.SV_nichtVorhanden),
                                           new CBItem(de.hattrickorganizer.model.Team
                                                      .getNameForSelbstvertrauen(ITeam.SV_katastrophal),
                                                      ITeam.SV_katastrophal),
                                           new CBItem(de.hattrickorganizer.model.Team
                                                      .getNameForSelbstvertrauen(ITeam.SV_armselig),
                                                      ITeam.SV_armselig),
                                           new CBItem(de.hattrickorganizer.model.Team
                                                      .getNameForSelbstvertrauen(ITeam.SV_gering),
                                                      ITeam.SV_gering),
                                           new CBItem(de.hattrickorganizer.model.Team
                                                      .getNameForSelbstvertrauen(ITeam.SV_bescheiden),
                                                      ITeam.SV_bescheiden),
                                           new CBItem(de.hattrickorganizer.model.Team
                                                      .getNameForSelbstvertrauen(ITeam.SV_stark),
                                                      ITeam.SV_stark),
                                           new CBItem(de.hattrickorganizer.model.Team
                                                      .getNameForSelbstvertrauen(ITeam.SV_sehr_gross),
                                                      ITeam.SV_sehr_gross),
                                           new CBItem(de.hattrickorganizer.model.Team
                                                      .getNameForSelbstvertrauen(ITeam.SV_etwas_ueberheblich),
                                                      ITeam.SV_etwas_ueberheblich),
                                           new CBItem(de.hattrickorganizer.model.Team
                                                      .getNameForSelbstvertrauen(ITeam.SV_voellig_uebertrieben),
                                                      ITeam.SV_voellig_uebertrieben),
                                           new CBItem(de.hattrickorganizer.model.Team
                                                      .getNameForSelbstvertrauen(ITeam.SV_voellig_abgehoben),
                                                      ITeam.SV_voellig_abgehoben)
                                       };
    private JComboBox m_jcbSelbstvertrauen = new JComboBox(SELBSTVERTRAUEN);
	private CBItem[] TRAINERTYPE = {
			new CBItem(HOMiniModel.instance().getResource().getProperty("Defensiv"),0),
			new CBItem(HOMiniModel.instance().getResource().getProperty("balanced"),2),
			new CBItem(HOMiniModel.instance().getResource().getProperty("Offensiv"),1),
		};    
	private JComboBox m_jcbTrainerType= new JComboBox(TRAINERTYPE);
    private CBItem[] STIMMUNG = {
                                    new CBItem(de.hattrickorganizer.model.Team.getNameForStimmung(ITeam.TS_wie_im_kalten_Krieg),
                                               ITeam.TS_wie_im_kalten_Krieg),
                                    new CBItem(de.hattrickorganizer.model.Team.getNameForStimmung(ITeam.TS_blutruenstig),
                                               ITeam.TS_blutruenstig),
                                    new CBItem(de.hattrickorganizer.model.Team.getNameForStimmung(ITeam.TS_wuetend),
                                               ITeam.TS_wuetend),
                                    new CBItem(de.hattrickorganizer.model.Team.getNameForStimmung(ITeam.TS_irritiert),
                                               ITeam.TS_irritiert),
                                    new CBItem(de.hattrickorganizer.model.Team.getNameForStimmung(ITeam.TS_ruhig),
                                               ITeam.TS_ruhig),
                                    new CBItem(de.hattrickorganizer.model.Team.getNameForStimmung(ITeam.TS_zufrieden),
                                               ITeam.TS_zufrieden),
                                    new CBItem(de.hattrickorganizer.model.Team.getNameForStimmung(ITeam.TS_gut),
                                               ITeam.TS_gut),
                                    new CBItem(de.hattrickorganizer.model.Team.getNameForStimmung(ITeam.TS_ausgezeichnet),
                                               ITeam.TS_ausgezeichnet),
                                    new CBItem(de.hattrickorganizer.model.Team.getNameForStimmung(ITeam.TS_euphorisch),
                                               ITeam.TS_euphorisch),
                                    new CBItem(de.hattrickorganizer.model.Team.getNameForStimmung(ITeam.TS_auf_Wolke_sieben),
                                               ITeam.TS_auf_Wolke_sieben),
                                    new CBItem(de.hattrickorganizer.model.Team.getNameForStimmung(ITeam.TS_paradiesisch),
                                               ITeam.TS_paradiesisch)
                                };
    private JComboBox m_jcbMainStimmung = new JComboBox(STIMMUNG);
	private CBItem[] SUBSTIMM = {
									new CBItem(HOMiniModel.instance().getResource().getProperty("verylow"),0),
									new CBItem(HOMiniModel.instance().getResource().getProperty("low"),1),
									new CBItem(HOMiniModel.instance().getResource().getProperty("Durchschnitt"),2),
									new CBItem(HOMiniModel.instance().getResource().getProperty("high"),3),
									new CBItem(HOMiniModel.instance().getResource().getProperty("veryhigh"),4)
								};    
	private JComboBox m_jcbSubStimmung = new JComboBox(SUBSTIMM);
    private CBItem[] TAKTIK = {
                                  new CBItem(de.hattrickorganizer.model.matches.Matchdetails
                                             .getNameForTaktik(IMatchDetails.TAKTIK_NORMAL),
                                             IMatchDetails.TAKTIK_NORMAL),
                                  new CBItem(de.hattrickorganizer.model.matches.Matchdetails
                                             .getNameForTaktik(IMatchDetails.TAKTIK_PRESSING),
                                             IMatchDetails.TAKTIK_PRESSING),
                                  new CBItem(de.hattrickorganizer.model.matches.Matchdetails
                                             .getNameForTaktik(IMatchDetails.TAKTIK_KONTER),
                                             IMatchDetails.TAKTIK_KONTER),
                                  new CBItem(de.hattrickorganizer.model.matches.Matchdetails
                                             .getNameForTaktik(IMatchDetails.TAKTIK_MIDDLE),
                                             IMatchDetails.TAKTIK_MIDDLE),
                                  new CBItem(de.hattrickorganizer.model.matches.Matchdetails
                                             .getNameForTaktik(IMatchDetails.TAKTIK_WINGS),
                                             IMatchDetails.TAKTIK_WINGS),
                                  new CBItem(de.hattrickorganizer.model.matches.Matchdetails
                                             .getNameForTaktik(IMatchDetails.TAKTIK_CREATIVE),
                                             IMatchDetails.TAKTIK_CREATIVE)
                              };
    private JComboBox m_jcbTaktik = new JComboBox(TAKTIK);

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new AufstellungsDetailPanel object.
     */
    public AufstellungsDetailPanel() {
        initComponents();			
        de.hattrickorganizer.gui.RefreshManager.instance().registerRefreshable(this);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param einstellung TODO Missing Method Parameter Documentation
     */
    public void setEinstellung(int einstellung) {
        de.hattrickorganizer.tools.Helper.markierenComboBox(m_jcbEinstellung, einstellung);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getEinstellung() {
        return ((CBItem) m_jcbEinstellung.getSelectedItem()).getId();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param heimspiel TODO Missing Method Parameter Documentation
     */
    public void setHeimspiel(int heimspiel) {
        m_jchSpielort.removeItemListener(this);

        if (heimspiel >= 1) {
            m_jchSpielort.setSelected(true);
        } else {
            m_jchSpielort.setSelected(false);
        }

        m_jchSpielort.addItemListener(this);
    }

    //	 unused code 
    //	Fooczy	2005-09-12    
    //    private void resetLabels()
    //    {
    //          m_jpRating.clear();
    //          setTaktik( model.machtes.Matchdetails.TAKTIK_NORMAL );
    //          setEinstellung( model.machtes.Matchdetails.EINSTELLUNG_NORMAL );
    //          m_jpGesamtStaerke.clear ();
    //          m_jpGesamtStaerkeText.clear();
    //          //m_jpTaktikStaerke.clear ();
    //          setStimmung( model.Team.TS_ruhig );
    //          setSelbstvertrauen( model.Team.SV_stark );
    //          m_jpDurchschnittErfahrung.clear();
    //          m_jpAktuellesSystem.clear();   
    //          m_jpErfahrungAktuellesSystem.clear();   
    //          m_jpErfahrung433.clear();   
    //          m_jpErfahrung442.clear();   
    //          m_jpErfahrung532.clear();   
    //          m_jpErfahrung541.clear();   
    //          m_jpErfahrung352.clear();   
    //          m_jpErfahrung343.clear();   
    //          m_jpErfahrung451.clear();       
    //    }
    //    
    private void setLabels() {
        if (HOVerwaltung.instance().getModel().getTeam() != null) {
            final HOModel homodel = HOVerwaltung.instance().getModel();
            final java.util.Vector allSpieler = homodel.getAllSpieler();
            final Aufstellung aufstellung = homodel.getAufstellung();

            //AufstellungCBItem   avergleich  = AufstellungsVergleichHistoryPanel.getVergleichsAufstellung ();
            //HRF-Vergleich gefordert
            if (AufstellungsVergleichHistoryPanel.isVergleichgefordert()) {
                //Erst die Werte auf die der geladenen Aufstellung setzen
                final AufstellungCBItem vergleichsaufstellungcbitem = AufstellungsVergleichHistoryPanel
                                                                      .getVergleichsAufstellung();

                if (vergleichsaufstellungcbitem != null) {
                    final Aufstellung vergleichsaufstellung = vergleichsaufstellungcbitem
                                                              .getAufstellung();

                    if (vergleichsaufstellung != null) {
                        //Wegen der Berechnung zuerst die Aufstellung kurz in Model packen, da immer die aktuelle Aufstellung genommen wird
                        homodel.setAufstellung(vergleichsaufstellung);
                        m_jpRating.setTopRight(vergleichsaufstellung.getLeftDefenseRating());
                        m_jpRating.setTopCenter(vergleichsaufstellung.getCentralDefenseRating());
                        m_jpRating.setTopLeft(vergleichsaufstellung.getRightDefenseRating());
                        m_jpRating.setMiddle(vergleichsaufstellung.getMidfieldRating());
                        m_jpRating.setBottomRight(vergleichsaufstellung.getLeftAttackRating());
                        m_jpRating.setBottomCenter(vergleichsaufstellung.getCentralAttackRating());
                        m_jpRating.setBottomLeft(vergleichsaufstellung.getRightAttackRating());

                        //Wieder die richtige Aufstellung setzen
                        homodel.setAufstellung(aufstellung);
                    }
                }
            }

            //Erst mal leeren
            m_jpRating.clear();

            m_jpRating.setTopRightText(PlayerHelper.getNameForSkill((aufstellung.getIntValue4Rating(aufstellung
                                                                                                    .getLeftDefenseRating())),
                                                                    false, true));
            m_jpRating.setTopCenterText(PlayerHelper.getNameForSkill((aufstellung
                                                                      .getIntValue4Rating(aufstellung
                                                                                          .getCentralDefenseRating())),
                                                                     false, true));
            m_jpRating.setTopLeftText(PlayerHelper.getNameForSkill((aufstellung.getIntValue4Rating(aufstellung
                                                                                                   .getRightDefenseRating())),
                                                                   false, true));
            m_jpRating.setMiddleText(PlayerHelper.getNameForSkill((aufstellung.getIntValue4Rating(aufstellung
                                                                                                  .getMidfieldRating())),
                                                                  false, true));
            m_jpRating.setBottomRightText(PlayerHelper.getNameForSkill((aufstellung
                                                                        .getIntValue4Rating(aufstellung
                                                                                            .getLeftAttackRating())),
                                                                       false, true));
            m_jpRating.setBottomCenterText(PlayerHelper.getNameForSkill((aufstellung
                                                                         .getIntValue4Rating(aufstellung
                                                                                             .getCentralAttackRating())),
                                                                        false, true));
            m_jpRating.setBottomLeftText(PlayerHelper.getNameForSkill((aufstellung
                                                                       .getIntValue4Rating(aufstellung
                                                                                           .getRightAttackRating())),
                                                                      false, true));
            m_jpRating.setTopRight(aufstellung.getLeftDefenseRating());
            m_jpRating.setTopCenter(aufstellung.getCentralDefenseRating());
            m_jpRating.setTopLeft(aufstellung.getRightDefenseRating());
            m_jpRating.setMiddle(aufstellung.getMidfieldRating());
            m_jpRating.setBottomRight(aufstellung.getLeftAttackRating());
            m_jpRating.setBottomCenter(aufstellung.getCentralAttackRating());
            m_jpRating.setBottomLeft(aufstellung.getRightAttackRating());

            //Farben neu berechnen
            m_jpRating.calcColorBorders();

            m_jcbTaktik.removeItemListener(this);
            m_jcbEinstellung.removeItemListener(this);
            de.hattrickorganizer.tools.Helper.markierenComboBox(m_jcbTaktik,
                                                                aufstellung.getTacticType());
            de.hattrickorganizer.tools.Helper.markierenComboBox(m_jcbEinstellung,
                                                                aufstellung.getAttitude());
            m_jcbTaktik.addItemListener(this);
            m_jcbEinstellung.addItemListener(this);
            setHeimspiel(aufstellung.getHeimspiel());

            final double gesamtstaerke = aufstellung.getGesamtStaerke(allSpieler, true);

            //*2 wegen halben Sternen
            m_jpGesamtStaerke.setRating((int) (gesamtstaerke * 2));
            m_jpGesamtStaerkeText.setText(de.hattrickorganizer.tools.Helper.DEFAULTDEZIMALFORMAT
                                          .format(gesamtstaerke));
            m_jpLoddarstat.setText(de.hattrickorganizer.tools.Helper.round(aufstellung
                                                                           .getLoddarStats(), 2)
                                   + "");
            m_jpHatstat.setText(aufstellung.getHATStats() + "");
            m_jpTaktikStaerke.setText(getTaktikString());
            setStimmung(homodel.getTeam().getStimmungAsInt(),homodel.getTeam().getSubStimmung());
            setSelbstvertrauen(homodel.getTeam().getSelbstvertrauenAsInt());
            setTrainerType(homodel.getTrainer().getTrainerTyp());
            m_jpDurchschnittErfahrung.setText(PlayerHelper.getNameForSkill(homodel.getAufstellung()
                                                                                  .getAvgExpierence()));
            m_jpAktuellesSystem.setText(Aufstellung.getNameForSystem(aufstellung.ermittelSystem()));
            m_jpAktuellesSystem.setText(Aufstellung.getNameForSystem(aufstellung.ermittelSystem()));
            m_jpErfahrungAktuellesSystem.setText(homodel.getAufstellung()
                                                        .getTeamErfahrung4AktuellesSystem() + "");
            m_jpErfahrung433.setText(homodel.getTeam().getErfahrung433() + "");
            m_jpErfahrung442.setText(ISpieler.sehr_gut + "");
            m_jpErfahrung532.setText(homodel.getTeam().getErfahrung532() + "");
            m_jpErfahrung541.setText(homodel.getTeam().getErfahrung541() + "");
            m_jpErfahrung352.setText(homodel.getTeam().getErfahrung352() + "");
            m_jpErfahrung343.setText(homodel.getTeam().getErfahrung343() + "");
            m_jpErfahrung451.setText(homodel.getTeam().getErfahrung451() + "");
            m_jpErfahrungAktuellesSystem.setFGColor(new java.awt.Color(Math.min(Math.max(((8
                                                                                         - homodel.getAufstellung()
                                                                                                  .getTeamErfahrung4AktuellesSystem()) * 32)
                                                                                         - 1, 0),
                                                                                255), 0, 0));
            m_jpErfahrung433.setFGColor(new java.awt.Color(Math.min(Math.max(((8
                                                                             - homodel.getTeam()
                                                                                      .getErfahrung433()) * 32)
                                                                             - 1, 0), 255), 0, 0));
            m_jpErfahrung442.setText(ISpieler.sehr_gut + "");
            m_jpErfahrung532.setFGColor(new java.awt.Color(Math.min(Math.max(((8
                                                                             - homodel.getTeam()
                                                                                      .getErfahrung532()) * 32)
                                                                             - 1, 0), 255), 0, 0));
            m_jpErfahrung541.setFGColor(new java.awt.Color(Math.min(Math.max(((8
                                                                             - homodel.getTeam()
                                                                                      .getErfahrung541()) * 32)
                                                                             - 1, 0), 255), 0, 0));
            m_jpErfahrung352.setFGColor(new java.awt.Color(Math.min(Math.max(((8
                                                                             - homodel.getTeam()
                                                                                      .getErfahrung352()) * 32)
                                                                             - 1, 0), 255), 0, 0));
            m_jpErfahrung343.setFGColor(new java.awt.Color(Math.min(Math.max(((8
                                                                             - homodel.getTeam()
                                                                                      .getErfahrung343()) * 32)
                                                                             - 1, 0), 255), 0, 0));
            m_jpErfahrung451.setFGColor(new java.awt.Color(Math.min(Math.max(((8
                                                                             - homodel.getTeam()
                                                                                      .getErfahrung451()) * 32)
                                                                             - 1, 0), 255), 0, 0));
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param selbstvertrauen TODO Missing Method Parameter Documentation
     */
    public void setSelbstvertrauen(int selbstvertrauen) {
        de.hattrickorganizer.tools.Helper.markierenComboBox(m_jcbSelbstvertrauen, selbstvertrauen);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param stimmung TODO Missing Method Parameter Documentation
     */
    public void setStimmung(int stimmung,int subStimmung) {
        de.hattrickorganizer.tools.Helper.markierenComboBox(m_jcbMainStimmung, stimmung);
		de.hattrickorganizer.tools.Helper.markierenComboBox(m_jcbSubStimmung, subStimmung);
    }

    public void setTrainerType (int newTrainerType) {
    	de.hattrickorganizer.tools.Helper.markierenComboBox(m_jcbTrainerType, newTrainerType);
    }
    
    /**
     * TODO Missing Method Documentation
     *
     * @param taktik TODO Missing Method Parameter Documentation
     */
    public void setTaktik(int taktik) {
        de.hattrickorganizer.tools.Helper.markierenComboBox(m_jcbTaktik, taktik);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getTaktik() {
        return ((CBItem) m_jcbTaktik.getSelectedItem()).getId();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param event TODO Missing Method Parameter Documentation
     */
    public void itemStateChanged(java.awt.event.ItemEvent event) {
        if (event.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            //Taktik geändert
            if (event.getSource().equals(m_jcbTaktik)) {
                // Fooczy playing creatively 21-09-2005
                //int selectedTacticType = ((CBItem) m_jcbTaktik.getSelectedItem()).getId();
                //PlayerHelper.currentTacticType = selectedTacticType;
                //           		
                //            	Bewertung neu berechnen
                HOVerwaltung.instance().getModel().getAufstellung().setTacticType(((CBItem) m_jcbTaktik
                                                                                   .getSelectedItem())
                                                                                  .getId());                                                                                  
                refresh();
				HOMainFrame.instance().getAufstellungsPanel().getAufstellungsPositionsPanel().exportOldLineup("Actual");
				FileExtensionManager.extractLineup("Actual");
                // Fooczy playing creatively 21-09-2005
                // de.hattrickorganizer.gui.HOMainFrame.instance().getAufstellungsPanel().update();
            } else if (event.getSource().equals(m_jcbEinstellung)) {
                //Bewertung neu berechnen
                HOVerwaltung.instance().getModel().getAufstellung().setAttitude(((CBItem) m_jcbEinstellung
                                                                                 .getSelectedItem())
                                                                                .getId());
                refresh();
				HOMainFrame.instance().getAufstellungsPanel().getAufstellungsPositionsPanel().exportOldLineup("Actual");
				FileExtensionManager.extractLineup("Actual");                
            } else if (event.getSource().equals(m_jcbMainStimmung)) {
				HOVerwaltung.instance().getModel().getTeam().setStimmungAsInt(((CBItem) m_jcbMainStimmung.getSelectedItem()).getId());
				HOVerwaltung.instance().getModel().getTeam().setSubStimmung(((CBItem) m_jcbSubStimmung.getSelectedItem()).getId());
            	
                HOVerwaltung.instance().getModel().getTeam().setStimmung(((CBItem) m_jcbMainStimmung
                                                                          .getSelectedItem())
                                                                         .getText());
                refresh();
			} else if (event.getSource().equals(m_jcbSubStimmung)) {
				HOVerwaltung.instance().getModel().getTeam().setStimmungAsInt(((CBItem) m_jcbMainStimmung.getSelectedItem()).getId());
				HOVerwaltung.instance().getModel().getTeam().setSubStimmung(((CBItem) m_jcbSubStimmung.getSelectedItem()).getId());

				HOVerwaltung.instance().getModel().getTeam().setStimmung(((CBItem) m_jcbMainStimmung
																		  .getSelectedItem())
																		 .getText());
				refresh();                
            } else if (event.getSource().equals(m_jcbSelbstvertrauen)) {
                HOVerwaltung.instance().getModel().getTeam().setSelbstvertrauenAsInt(((CBItem) m_jcbSelbstvertrauen
                                                                                      .getSelectedItem())
                                                                                     .getId());
                HOVerwaltung.instance().getModel().getTeam().setSelbstvertrauen(((CBItem) m_jcbSelbstvertrauen
                                                                                 .getSelectedItem())
                                                                                .getText());
                refresh();
            } else if (event.getSource().equals(m_jcbTrainerType)) {
            	HOVerwaltung.instance().getModel().getTrainer().
            			setTrainerTyp(((CBItem)m_jcbTrainerType.getSelectedItem()).getId());
            	refresh();
            }
        }

        //Auch deselected notwendig!
        if (event.getSource().equals(m_jchSpielort)) {
            HOVerwaltung.instance().getModel().getAufstellung().setHeimspiel(m_jchSpielort
                                                                             .isSelected()
                                                                             ? (short) 1 : (short) 0);
            refresh();
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    public void reInit() {
        //resetLabels();
        setLabels();

        //        invalidate();
        //        validate();
        //        repaint();        
    }

    /**
     * TODO Missing Method Documentation
     */
    public void refresh() {
        //resetLabels();		       
        setLabels();		
		
        //        invalidate();
        //        validate();
        //        repaint();        
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private String getTaktikString() {
        final Aufstellung aufstellung = HOVerwaltung.instance().getModel().getAufstellung();

        switch (getTaktik()) {
            case IMatchDetails.TAKTIK_NORMAL:
                return HOVerwaltung.instance().getResource().getProperty("Normal");

            case IMatchDetails.TAKTIK_PRESSING:
                return PlayerHelper.getNameForSkill(aufstellung.getPressingSTK(HOVerwaltung.instance()
                                                                                           .getModel()
                                                                                           .getAllSpieler())
                                                    + 0.5f);

            case IMatchDetails.TAKTIK_KONTER:
                return PlayerHelper.getNameForSkill(aufstellung.getKonterSTK(HOVerwaltung.instance()
                                                                                         .getModel()
                                                                                         .getAllSpieler())
                                                    + 0.5f);

            case IMatchDetails.TAKTIK_MIDDLE:
                return PlayerHelper.getNameForSkill(aufstellung.getAttackSTK(HOVerwaltung.instance()
                                                                                         .getModel()
                                                                                         .getAllSpieler())
                                                    + 0.5f);

            case IMatchDetails.TAKTIK_WINGS:
                return PlayerHelper.getNameForSkill(aufstellung.getAttackSTK(HOVerwaltung.instance()
                                                                                         .getModel()
                                                                                         .getAllSpieler())
                                                    + 0.5f);

            case IMatchDetails.TAKTIK_CREATIVE:

                //return "HO "+aufstellung.getCreativeSTK(HOVerwaltung.instance().getModel().getAllSpieler());
                return HOVerwaltung.instance().getResource().getProperty("katastrophal");

            default:
                return HOVerwaltung.instance().getResource().getProperty("Unbestimmt");
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    private void initComponents() {
        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;

        setLayout(layout);

        JLabel label;
        JPanel panel;

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        layout.setConstraints(m_jpRating, constraints);
        add(m_jpRating, constraints);

        panel = new JPanel(new BorderLayout());
        panel.setOpaque(true);
        m_jpGesamtStaerke.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                .getResource()
                                                                                .getProperty("Gesamtstaerke"));
        panel.add(m_jpGesamtStaerke.getComponent(false), BorderLayout.CENTER);
        m_jpGesamtStaerkeText.setFontStyle(Font.BOLD);
        m_jpGesamtStaerkeText.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                    .getResource()
                                                                                    .getProperty("Gesamtstaerke"));
        panel.add(m_jpGesamtStaerkeText.getComponent(false), BorderLayout.EAST);
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.weighty = 0.0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        layout.setConstraints(panel, constraints);
        add(panel);

        final Properties properties = de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                             .getResource();

        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        m_jcbEinstellung.addItemListener(this);
        m_jcbEinstellung.setToolTipText(properties.getProperty("tt_AufstellungsDetails_Einstellung"));
        layout.setConstraints(m_jcbEinstellung, constraints);
        add(m_jcbEinstellung);

        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        m_jcbTaktik.addItemListener(this);
        m_jcbTaktik.setToolTipText(properties.getProperty("tt_AufstellungsDetails_Taktik"));
        layout.setConstraints(m_jcbTaktik, constraints);
        add(m_jcbTaktik);

        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        m_jchSpielort.addItemListener(this);
        m_jchSpielort.setToolTipText(properties.getProperty("tt_AufstellungsDetails_Spielort"));
        m_jchSpielort.setOpaque(false);
        layout.setConstraints(m_jchSpielort, constraints);
        add(m_jchSpielort);

        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.gridwidth = 1;

        //model.HOVerwaltung.instance ().getResource ().getProperty( "HATStat" ) );
        label = new JLabel("HatStats");
        layout.setConstraints(label, constraints);
        add(label);
        constraints.gridx = 2;
        constraints.gridy = 6;

        //m_jpHatstat.setToolTipText( model.HOVerwaltung.instance ().getResource ().getProperty( "tt_AufstellungsDetails_HatStat" ) );
        layout.setConstraints(m_jpHatstat.getComponent(false), constraints);
        add(m_jpHatstat.getComponent(false));

        initLabel(constraints,layout,new JLabel("Loddar Stats"),7);
        constraints.gridx = 2;
        constraints.gridy = 7;

        //m_jpHatstat.setToolTipText( model.HOVerwaltung.instance ().getResource ().getProperty( "tt_AufstellungsDetails_LoddarStat" ) );
        layout.setConstraints(m_jpLoddarstat.getComponent(false), constraints);
        add(m_jpLoddarstat.getComponent(false));

        constraints.gridx = 1;
        constraints.gridy = 8;
        constraints.gridwidth = 1;
        label = new JLabel(properties.getProperty("Taktikstaerke"));
        layout.setConstraints(label, constraints);
        add(label);
        constraints.gridx = 2;
        constraints.gridy = 8;
        constraints.weightx = 1.0;
        layout.setConstraints(m_jpTaktikStaerke.getComponent(false), constraints);
        add(m_jpTaktikStaerke.getComponent(false));

        initLabel(constraints,layout,new JLabel(properties.getProperty("Stimmung")),9);
        constraints.gridx = 2;
        constraints.gridy = 9;
        m_jcbMainStimmung.addItemListener(this);
		m_jcbMainStimmung.setPreferredSize(new Dimension(50,
                                                     de.hattrickorganizer.tools.Helper
                                                     .calcCellWidth(20)));
		m_jcbMainStimmung.setMaximumRowCount(13);
        layout.setConstraints(m_jcbMainStimmung, constraints);
        add(m_jcbMainStimmung);

		initLabel(constraints,layout,new JLabel("Sub"+properties.getProperty("Stimmung")),10);
		constraints.gridx = 2;
		constraints.gridy = 10;
		m_jcbSubStimmung.addItemListener(this);
		m_jcbSubStimmung.setPreferredSize(new Dimension(50,
													 de.hattrickorganizer.tools.Helper
													 .calcCellWidth(20)));
		m_jcbSubStimmung.setMaximumRowCount(5);
		layout.setConstraints(m_jcbSubStimmung, constraints);
		add(m_jcbSubStimmung);
		
        initLabel(constraints,layout,new JLabel(properties.getProperty("Selbstvertrauen")),11);
        constraints.gridx = 2;
        constraints.gridy = 11;
        m_jcbSelbstvertrauen.addItemListener(this);
        m_jcbSelbstvertrauen.setPreferredSize(new Dimension(50,
                                                            de.hattrickorganizer.tools.Helper
                                                            .calcCellWidth(20)));
        m_jcbSelbstvertrauen.setMaximumRowCount(10);
        layout.setConstraints(m_jcbSelbstvertrauen, constraints);
        add(m_jcbSelbstvertrauen);

        initLabel(constraints,layout,new JLabel(properties.getProperty("Trainer")),12);
        constraints.gridx = 2;
        constraints.gridy = 12;
        m_jcbTrainerType.addItemListener(this);
		m_jcbTrainerType.setPreferredSize(new Dimension(50,
                                                     de.hattrickorganizer.tools.Helper
                                                     .calcCellWidth(20)));
		m_jcbTrainerType.setMaximumRowCount(3);
        layout.setConstraints(m_jcbTrainerType, constraints);
        add(m_jcbTrainerType);

        initLabel(constraints,layout,new JLabel(properties.getProperty("Erfahrung")),13);
        constraints.gridx = 2;
        constraints.gridy = 13;
        layout.setConstraints(m_jpDurchschnittErfahrung.getComponent(false), constraints);
        add(m_jpDurchschnittErfahrung.getComponent(false));

        initLabel(constraints,layout,new JLabel(properties.getProperty("AktuellesSystem")),14);
        constraints.gridx = 2;
        constraints.gridy = 14;
        layout.setConstraints(m_jpAktuellesSystem.getComponent(false), constraints);
        add(m_jpAktuellesSystem.getComponent(false));

        initLabel(constraints,layout,new JLabel(properties.getProperty("ErfahrungAktuellesSys")),15);
        constraints.gridx = 2;
        constraints.gridy = 15;
        layout.setConstraints(m_jpErfahrungAktuellesSystem.getComponent(false), constraints);
        add(m_jpErfahrungAktuellesSystem.getComponent(false));

        initLabel(constraints,layout,new JLabel(properties.getProperty("Erfahrung433")),16);
        constraints.gridx = 2;
        constraints.gridy = 16;
        layout.setConstraints(m_jpErfahrung433.getComponent(false), constraints);
        add(m_jpErfahrung433.getComponent(false));

        initLabel(constraints,layout,new JLabel(properties.getProperty("Erfahrung442")),17);
        constraints.gridx = 2;
        constraints.gridy = 17;
        layout.setConstraints(m_jpErfahrung442.getComponent(false), constraints);
        add(m_jpErfahrung442.getComponent(false));

        initLabel(constraints,layout,new JLabel(properties.getProperty("Erfahrung532")),18);
        constraints.gridx = 2;
        constraints.gridy = 18;
        layout.setConstraints(m_jpErfahrung532.getComponent(false), constraints);
        add(m_jpErfahrung532.getComponent(false));

        initLabel(constraints,layout,new JLabel(properties.getProperty("Erfahrung541")),19);
        constraints.gridx = 2;
        constraints.gridy = 19;
        layout.setConstraints(m_jpErfahrung541.getComponent(false), constraints);
        add(m_jpErfahrung541.getComponent(false));

        initLabel(constraints,layout,new JLabel(properties.getProperty("Erfahrung352")),20);
        constraints.gridx = 2;
        constraints.gridy = 20;
        layout.setConstraints(m_jpErfahrung352.getComponent(false), constraints);
        add(m_jpErfahrung352.getComponent(false));

        initLabel(constraints,layout,new JLabel(properties.getProperty("Erfahrung343")),21);
        constraints.gridx = 2;
        constraints.gridy = 21;
        layout.setConstraints(m_jpErfahrung343.getComponent(false), constraints);
        add(m_jpErfahrung343.getComponent(false));


        initLabel(constraints,layout,new JLabel(properties.getProperty("Erfahrung451")),22);
        constraints.gridx = 2;
        constraints.gridy = 22;
        layout.setConstraints(m_jpErfahrung451.getComponent(false), constraints);
        add(m_jpErfahrung451.getComponent(false));
    }
    
    private void initLabel(GridBagConstraints constraints,GridBagLayout layout, JLabel label, int y){
    	constraints.gridx = 1;
        constraints.gridy = y;
        layout.setConstraints(label, constraints);
        add(label);
    }
}
