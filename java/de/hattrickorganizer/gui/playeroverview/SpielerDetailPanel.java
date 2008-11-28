// %3484484869:de.hattrickorganizer.gui.playeroverview%
package de.hattrickorganizer.gui.playeroverview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import plugins.ISpieler;
import plugins.ISpielerPosition;
import de.hattrickorganizer.gui.HOMainFrame;
import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.gui.templates.DoppelLabelEntry;
import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.gui.templates.RatingTableEntry;
import de.hattrickorganizer.model.EPVData;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.Spieler;
import de.hattrickorganizer.model.SpielerPosition;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.Helper;
import de.hattrickorganizer.tools.PlayerHelper;


/**
 * Gibt Details zum Markierten Spieler aus
 */
public final class SpielerDetailPanel extends ImagePanel implements de.hattrickorganizer.gui.Refreshable,
                                                              java.awt.event.FocusListener,
                                                              java.awt.event.ItemListener,
                                                              java.awt.event.ActionListener
{
    //~ Static fields/initializers -----------------------------------------------------------------

    public static final Dimension COMPONENTENSIZE = new Dimension(Helper.calcCellWidth(150),
                                                             Helper.calcCellWidth(18));
    public static final Dimension COMPONENTENSIZE2 = new Dimension(Helper.calcCellWidth(65),
                                                              Helper.calcCellWidth(18));
    private static final Dimension COMPONENTENSIZE3 = new Dimension(Helper.calcCellWidth(100),
                                                              Helper.calcCellWidth(18));
    private static final Dimension COMPONENTENSIZE4 = new Dimension(Helper.calcCellWidth(50),
                                                              Helper.calcCellWidth(18));
    private static final Dimension COMPONENTENSIZECB = new Dimension(Helper.calcCellWidth(150), 16);

    //~ Instance fields ----------------------------------------------------------------------------

    private final ColorLabelEntry m_jpAggressivitaet = new ColorLabelEntry("",
                                                                     ColorLabelEntry.FG_STANDARD,
                                                                     ColorLabelEntry.BG_STANDARD,
                                                                     SwingConstants.LEFT);
    private final ColorLabelEntry m_jpAlter = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                            ColorLabelEntry.BG_STANDARD,
                                                            SwingConstants.CENTER);
    private final ColorLabelEntry m_jpAnsehen = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                              ColorLabelEntry.BG_STANDARD,
                                                              SwingConstants.LEFT);
    private final ColorLabelEntry m_jpAufgestellt = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                  ColorLabelEntry.BG_STANDARD,
                                                                  SwingConstants.CENTER);
    private final ColorLabelEntry m_jpBestPos = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                              ColorLabelEntry.BG_STANDARD,
                                                              SwingConstants.LEFT);
    private final ColorLabelEntry m_jpCharakter = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                ColorLabelEntry.BG_STANDARD,
                                                                SwingConstants.LEFT);
    private final ColorLabelEntry m_jpErfahrung = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                ColorLabelEntry.BG_SPIELERSONDERWERTE,
                                                                SwingConstants.LEFT);
    private final ColorLabelEntry m_jpErfahrung2 = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                 ColorLabelEntry.BG_SPIELERSONDERWERTE,
                                                                 SwingConstants.CENTER);
    private final ColorLabelEntry m_jpFluegelspiel = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                   ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                                   SwingConstants.LEFT);
    private final ColorLabelEntry m_jpFluegelspiel2 = new ColorLabelEntry("",
                                                                    ColorLabelEntry.FG_STANDARD,
                                                                    ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                                    SwingConstants.CENTER);
    private final ColorLabelEntry m_jpForm = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                           ColorLabelEntry.BG_SPIELERSONDERWERTE,
                                                           SwingConstants.LEFT);
    private final ColorLabelEntry m_jpForm2 = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                            ColorLabelEntry.BG_SPIELERSONDERWERTE,
                                                            SwingConstants.CENTER);
    private final ColorLabelEntry m_jpFuehrung = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                               ColorLabelEntry.BG_SPIELERSONDERWERTE,
                                                               SwingConstants.LEFT);
    private final ColorLabelEntry m_jpFuehrung2 = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                ColorLabelEntry.BG_SPIELERSONDERWERTE,
                                                                SwingConstants.CENTER);
    private final ColorLabelEntry m_jpHattriks = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                               ColorLabelEntry.BG_STANDARD,
                                                               SwingConstants.CENTER);
    private final ColorLabelEntry m_jpImTeamSeit = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                 ColorLabelEntry.BG_STANDARD,
                                                                 SwingConstants.LEFT);
    private final ColorLabelEntry m_jpKondition = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                                SwingConstants.LEFT);
    private final ColorLabelEntry m_jpKondition2 = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                 ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                                 SwingConstants.CENTER);
    private final ColorLabelEntry m_jpName = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                           ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);
    private final ColorLabelEntry m_jpNationalitaet = new ColorLabelEntry("",
                                                                    ColorLabelEntry.FG_STANDARD,
                                                                    ColorLabelEntry.BG_FLAGGEN,
                                                                    SwingConstants.CENTER);
    private final ColorLabelEntry m_jpPasspiel = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                               ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                               SwingConstants.LEFT);
    private final ColorLabelEntry m_jpPasspiel2 = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                                SwingConstants.CENTER);
    private final ColorLabelEntry m_jpSpezialitaet = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                   ColorLabelEntry.BG_STANDARD,
                                                                   SwingConstants.LEFT);
    private final ColorLabelEntry m_jpSpielaufbau = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                  ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                                  SwingConstants.LEFT);
    private final ColorLabelEntry m_jpSpielaufbau2 = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                   ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                                   SwingConstants.CENTER);
    private final ColorLabelEntry m_jpStandards = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                                SwingConstants.LEFT);
    private final ColorLabelEntry m_jpStandards2 = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                 ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                                 SwingConstants.CENTER);
    private final ColorLabelEntry m_jpToreFreund = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                 ColorLabelEntry.BG_STANDARD,
                                                                 SwingConstants.CENTER);
    private final ColorLabelEntry m_jpToreGesamt = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                 ColorLabelEntry.BG_STANDARD,
                                                                 SwingConstants.CENTER);
    private final ColorLabelEntry m_jpToreLiga = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                               ColorLabelEntry.BG_STANDARD,
                                                               SwingConstants.CENTER);
    private final ColorLabelEntry m_jpTorePokal = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                ColorLabelEntry.BG_STANDARD,
                                                                SwingConstants.CENTER);
    private final ColorLabelEntry m_jpTorschuss = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                                SwingConstants.LEFT);
    private final ColorLabelEntry m_jpTorschuss2 = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                 ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                                 SwingConstants.CENTER);
    private final ColorLabelEntry m_jpTorwart = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                              ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                              SwingConstants.LEFT);
    private final ColorLabelEntry m_jpTorwart2 = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                               ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                               SwingConstants.CENTER);
    private final ColorLabelEntry m_jpVerteidigung = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                   ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                                   SwingConstants.LEFT);
    private final ColorLabelEntry m_jpVerteidigung2 = new ColorLabelEntry("",
                                                                    ColorLabelEntry.FG_STANDARD,
                                                                    ColorLabelEntry.BG_SPIELEREINZELWERTE,
                                                                    SwingConstants.CENTER);
    private final ColorLabelEntry m_jpEPV = new ColorLabelEntry("",
														            ColorLabelEntry.FG_STANDARD,
														            ColorLabelEntry.BG_STANDARD,
														            SwingConstants.RIGHT);
    private final DoppelLabelEntry m_jpGehalt = new DoppelLabelEntry(ColorLabelEntry.BG_STANDARD);
    private final DoppelLabelEntry m_jpMartwert = new DoppelLabelEntry(ColorLabelEntry.BG_STANDARD);
    private final DoppelLabelEntry m_jpWertAussenVert = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERPOSITONSWERTE);
    private final DoppelLabelEntry m_jpWertAussenVertDef = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private final DoppelLabelEntry m_jpWertAussenVertIn = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private final DoppelLabelEntry m_jpWertAussenVertOff = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private final DoppelLabelEntry m_jpWertFluegel = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERPOSITONSWERTE);
    private final DoppelLabelEntry m_jpWertFluegelDef = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private final DoppelLabelEntry m_jpWertFluegelIn = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private final DoppelLabelEntry m_jpWertFluegelOff = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private final DoppelLabelEntry m_jpWertInnenVert = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERPOSITONSWERTE);
    private final DoppelLabelEntry m_jpWertInnenVertAus = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private final DoppelLabelEntry m_jpWertInnenVertOff = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private final DoppelLabelEntry m_jpWertMittelfeld = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERPOSITONSWERTE);
    private final DoppelLabelEntry m_jpWertMittelfeldAus = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private final DoppelLabelEntry m_jpWertMittelfeldDef = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private final DoppelLabelEntry m_jpWertMittelfeldOff = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private final DoppelLabelEntry m_jpWertSturm = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERPOSITONSWERTE);
    private final DoppelLabelEntry m_jpWertSturmAus = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private final DoppelLabelEntry m_jpWertSturmDef = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private final DoppelLabelEntry m_jpWertTor = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERPOSITONSWERTE);
    private final JButton m_jbAnalyse1 = new JButton(new ImageIcon(Helper.loadImage("gui/bilder/gotoAnalyseTop.png")));
    private final JButton m_jbAnalyse2 = new JButton(new ImageIcon(Helper.loadImage("gui/bilder/gotoAnalyseBottom.png")));
    private final JButton m_jbOffsets = new JButton(new ImageIcon(Helper.loadImage("gui/bilder/offset.png")));
    private final JButton m_jbStatistik = new JButton(new ImageIcon(Helper.loadImage("gui/bilder/gotoStatistik.png")));
    private final JButton m_jbTransvergleich = new JButton(new ImageIcon(Helper.makeColorTransparent(Helper
                                                                                         .loadImage("gui/bilder/smilies/8dollar.png"),
                                                                                         new java.awt.Color(209,
                                                                                                            41,
                                                                                                            144))));
    private final JComboBox m_jcbManuellerSmilie = new JComboBox(Helper.MANUELLSMILIES);
    private final JComboBox m_jcbTeamInfoSmilie = new JComboBox(Helper.TEAMSMILIES);
    private final JComboBox m_jcbUserPos = new JComboBox(de.hattrickorganizer.model.SpielerPosition.POSITIONEN);
    private final JTextArea m_jtaNotizen = new JTextArea(5, 12);
    private RatingTableEntry m_jpRating = new RatingTableEntry();
    private Spieler m_clSpieler;
    private Spieler m_clVergleichsSpieler;
    private SpielerStatusLabelEntry m_jpStatus = new SpielerStatusLabelEntry();

    private final DoppelLabelEntry[] playerPositionValues= new DoppelLabelEntry[]{
    		m_jpWertTor,
            m_jpWertInnenVert,
            m_jpWertInnenVertAus,
            m_jpWertInnenVertOff,
            m_jpWertAussenVert,
            m_jpWertAussenVertIn,
            m_jpWertAussenVertOff,
            m_jpWertAussenVertDef,
            m_jpWertMittelfeld,
            m_jpWertMittelfeldAus,
            m_jpWertMittelfeldOff,
            m_jpWertMittelfeldDef,
            m_jpWertFluegel,
            m_jpWertFluegelIn,
            m_jpWertFluegelOff,
            m_jpWertFluegelDef,
            m_jpWertSturm,
            m_jpWertSturmAus,
            m_jpWertSturmDef
    };

    private final byte[] playerPosition = new byte[]{
    		ISpielerPosition.TORWART,
            ISpielerPosition.INNENVERTEIDIGER,
            ISpielerPosition.INNENVERTEIDIGER_AUS,
            ISpielerPosition.INNENVERTEIDIGER_OFF,
            ISpielerPosition.AUSSENVERTEIDIGER,
            ISpielerPosition.AUSSENVERTEIDIGER_IN,
            ISpielerPosition.AUSSENVERTEIDIGER_OFF,
            ISpielerPosition.AUSSENVERTEIDIGER_DEF,
            ISpielerPosition.MITTELFELD,
            ISpielerPosition.MITTELFELD_AUS,
            ISpielerPosition.MITTELFELD_OFF,
            ISpielerPosition.MITTELFELD_DEF,
            ISpielerPosition.FLUEGELSPIEL,
            ISpielerPosition.FLUEGELSPIEL_IN,
            ISpielerPosition.FLUEGELSPIEL_OFF,
            ISpielerPosition.FLUEGELSPIEL_DEF,
            ISpielerPosition.STURM,
            ISpielerPosition.STURM_AUS,
            ISpielerPosition.STURM_DEF

    };


    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new SpielerDetailPanel object.
     */
    protected SpielerDetailPanel() {
        initComponents();

        de.hattrickorganizer.gui.RefreshManager.instance().registerRefreshable(this);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param spieler TODO Missing Method Parameter Documentation
     */
    public final void setSpieler(Spieler spieler) {
        m_clSpieler = spieler;

        if (m_clSpieler != null) {
            findVergleichsSpieler();
            setLabels();
        } else {
            resetLabels();
        }

        invalidate();
        validate();
        repaint();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param actionevent TODO Missing Method Parameter Documentation
     */
    public final void actionPerformed(java.awt.event.ActionEvent actionevent) {
        if (actionevent.getSource().equals(m_jbTransvergleich)) {
            transvergleich();
        } else if (actionevent.getSource().equals(m_jbStatistik)) {
            HOMainFrame.instance().getStatistikMainPanel().setShowSpieler(m_clSpieler.getSpielerID());
            de.hattrickorganizer.gui.HOMainFrame.instance().showTab(HOMainFrame.STATISTIK);
        } else if (actionevent.getSource().equals(m_jbAnalyse1)) {
            HOMainFrame.instance().getSpielerAnalyseMainPanel()
                                                .setSpieler4Top(m_clSpieler.getSpielerID());
            HOMainFrame.instance().showTab(HOMainFrame.SPIELERANALYSE);

        } else if (actionevent.getSource().equals(m_jbAnalyse2)) {
            HOMainFrame.instance().getSpielerAnalyseMainPanel()
                                                .setSpieler4Bottom(m_clSpieler.getSpielerID());
            HOMainFrame.instance().showTab(HOMainFrame.SPIELERANALYSE);

        } else if (actionevent.getSource().equals(m_jbOffsets)) {
            new SpielerOffsetDialog(HOMainFrame.instance(), m_clSpieler)
            .setVisible(true);
        }
    }

    /**
     * action on focus gained => do nothing
     *
     * @param event
     */
    public void focusGained(java.awt.event.FocusEvent event) {
    }

    /**
     * action on focus lost => save player note
     *
     * @param event
     */
    public final void focusLost(java.awt.event.FocusEvent event) {
        if (m_clSpieler != null) {
            de.hattrickorganizer.database.DBZugriff.instance().saveSpielerNotiz(m_clSpieler
                                                                                .getSpielerID(),
                                                                                m_jtaNotizen
                                                                                .getText());
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param itemEvent TODO Missing Method Parameter Documentation
     */
    public final void itemStateChanged(java.awt.event.ItemEvent itemEvent) {
        if (itemEvent.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            if (m_clSpieler != null) {
                if (itemEvent.getSource().equals(m_jcbTeamInfoSmilie)) {
                    m_clSpieler.setTeamInfoSmilie(m_jcbTeamInfoSmilie.getSelectedItem().toString());
                } else if (itemEvent.getSource().equals(m_jcbManuellerSmilie)) {
                    m_clSpieler.setManuellerSmilie(m_jcbManuellerSmilie.getSelectedItem().toString());
                } else if (itemEvent.getSource().equals(m_jcbUserPos)) {
                    m_clSpieler.setUserPosFlag((byte) ((de.hattrickorganizer.gui.model.CBItem) m_jcbUserPos
                                                       .getSelectedItem()).getId());
                }

                de.hattrickorganizer.gui.HOMainFrame.instance().getSpielerUebersichtPanel().update();
            }
        }
    }

    /**
     * set the player to compare and refresh the display
     */
    public final void reInit() {
        if (m_clSpieler != null) {
            findVergleichsSpieler();
        }

        setSpieler(null);
    }

    /**
     * refresh the display
     */
    public final void refresh() {
        setSpieler(m_clSpieler);
    }

    /**
     * set values of the player to fields
     */
    private void setLabels() {
        m_jpName.setText(m_clSpieler.getName());
        m_jpName.setFGColor(ColorLabelEntry.getForegroundForSpieler(m_clSpieler));
        String tmpAge = m_clSpieler.getAgeStringFull();
        m_jpAlter.setText(tmpAge);
        m_jpAlter.setToolTipText(tmpAge);
        m_jpNationalitaet.setIcon(Helper.getImageIcon4Country(m_clSpieler
                                                                                         .getNationalitaet()));

        if (de.hattrickorganizer.model.HOVerwaltung.instance().getModel().getAufstellung()
                                                   .isSpielerAufgestellt(m_clSpieler.getSpielerID())
            && (de.hattrickorganizer.model.HOVerwaltung.instance().getModel().getAufstellung()
                                                       .getPositionBySpielerId(m_clSpieler
                                                                               .getSpielerID()) != null)) {
            m_jpAufgestellt.setIcon(Helper.getImage4Position(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                                               .getModel()
                                                                                                                               .getAufstellung()
                                                                                                                               .getPositionBySpielerId(m_clSpieler
                                                                                                                                                       .getSpielerID()),
                                                                                        m_clSpieler
                                                                                        .getTrikotnummer()));
            m_jpAufgestellt.setText(de.hattrickorganizer.model.SpielerPosition.getNameForPosition(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                                                         .getModel()
                                                                                                                                         .getAufstellung()
                                                                                                                                         .getPositionBySpielerId(m_clSpieler
                                                                                                                                                                 .getSpielerID())
                                                                                                                                         .getPosition()));
        } else {
            m_jpAufgestellt.setIcon(Helper.getImage4Position(null,
                                                                                        m_clSpieler
                                                                                        .getTrikotnummer()));
            m_jpAufgestellt.setText("");
        }

        m_jcbTeamInfoSmilie.removeItemListener(this);
        m_jcbTeamInfoSmilie.setSelectedItem(m_clSpieler.getTeamInfoSmilie());
        m_jcbTeamInfoSmilie.addItemListener(this);

        m_jcbManuellerSmilie.removeItemListener(this);
        m_jcbManuellerSmilie.setSelectedItem(m_clSpieler.getManuellerSmilie());
        m_jcbManuellerSmilie.addItemListener(this);

        m_jcbUserPos.removeItemListener(this);
        Helper.markierenComboBox(m_jcbUserPos,
                                                            m_clSpieler.getUserPosFlag());
        m_jcbUserPos.addItemListener(this);

        m_jpStatus.setSpieler(m_clSpieler);

        //Bewertung
        if (m_clSpieler.getBewertung() > 0) {
            m_jpRating.setYellowStar(true);
            m_jpRating.setRating(m_clSpieler.getBewertung());
        } else {
            m_jpRating.setYellowStar(false);
            m_jpRating.setRating(m_clSpieler.getLetzteBewertung());
        }

        final int gehalt = (int) (m_clSpieler.getGehalt() / gui.UserParameter.instance().faktorGeld);
		final String gehalttext = Helper.getNumberFormat(true, 0).format(gehalt);
		final String tsitext = Helper.getNumberFormat(false, 0).format(m_clSpieler.getTSI());
        if (m_clVergleichsSpieler == null) {
            String bonus = "";

            if (m_clSpieler.getBonus() > 0) {
                bonus = " (" + m_clSpieler.getBonus() + "% "
                        + de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                 .getProperty("Bonus") + ")";
            }

            m_jpGehalt.getLinks().setText(gehalttext + bonus);
            m_jpGehalt.getRechts().clear();
            m_jpMartwert.getLinks().setText(tsitext);
            m_jpMartwert.getRechts().clear();
            m_jpForm.setText(PlayerHelper.getNameForSkill(m_clSpieler.getForm()) + "");
            m_jpForm2.clear();
            m_jpKondition.setText(PlayerHelper.getNameForSkill(m_clSpieler.getKondition()) + "");
            m_jpKondition2.clear();
            m_jpTorwart.setText(PlayerHelper.getNameForSkill(m_clSpieler.getTorwart()
                                                             + m_clSpieler
                                                               .getSubskill4SkillWithOffset(ISpieler.SKILL_TORWART))
                                + "");
            m_jpTorwart2.clear();
            m_jpVerteidigung.setText(PlayerHelper.getNameForSkill(m_clSpieler.getVerteidigung()
                                                                  + m_clSpieler
                                                                    .getSubskill4SkillWithOffset(ISpieler.SKILL_VERTEIDIGUNG))
                                     + "");
            m_jpVerteidigung2.clear();
            m_jpSpielaufbau.setText(PlayerHelper.getNameForSkill(m_clSpieler.getSpielaufbau()
                                                                 + m_clSpieler
                                                                   .getSubskill4SkillWithOffset(ISpieler.SKILL_SPIELAUFBAU))
                                    + "");
            m_jpSpielaufbau2.clear();
            m_jpPasspiel.setText(PlayerHelper.getNameForSkill(m_clSpieler.getPasspiel()
                                                              + m_clSpieler
                                                                .getSubskill4SkillWithOffset(ISpieler.SKILL_PASSSPIEL))
                                 + "");
            m_jpPasspiel2.clear();
            m_jpFluegelspiel.setText(PlayerHelper.getNameForSkill(m_clSpieler.getFluegelspiel()
                                                                  + m_clSpieler
                                                                    .getSubskill4SkillWithOffset(ISpieler.SKILL_FLUEGEL))
                                     + "");
            m_jpFluegelspiel2.clear();
            m_jpStandards.setText(PlayerHelper.getNameForSkill(m_clSpieler.getStandards()
                                                               + m_clSpieler
                                                                 .getSubskill4SkillWithOffset(ISpieler.SKILL_STANDARDS))
                                  + "");
            m_jpStandards2.clear();
            m_jpTorschuss.setText(PlayerHelper.getNameForSkill(m_clSpieler.getTorschuss()
                                                               + m_clSpieler
                                                                 .getSubskill4SkillWithOffset(ISpieler.SKILL_TORSCHUSS))
                                  + "");
            m_jpTorschuss2.clear();
            m_jpErfahrung.setText(PlayerHelper.getNameForSkill(m_clSpieler.getErfahrung()) + "");
            m_jpErfahrung2.clear();
            m_jpFuehrung.setText(PlayerHelper.getNameForSkill(m_clSpieler.getFuehrung()) + "");
            m_jpFuehrung2.clear();
            m_jpBestPos.setText(de.hattrickorganizer.model.SpielerPosition.getNameForPosition(m_clSpieler
                                                                                              .getIdealPosition())
                                + " ("
                                + m_clSpieler.calcPosValue(m_clSpieler.getIdealPosition(), true)
                                + ")");

            for (int i = 0; i < playerPositionValues.length; i++) {
            	showNormal(playerPositionValues[i],playerPosition[i]);
			}

        } else {
            String bonus = "";
            final int gehalt2 = (int) (m_clVergleichsSpieler.getGehalt() / gui.UserParameter
                                                                           .instance().faktorGeld);
            if (m_clSpieler.getBonus() > 0) {
                bonus = " (" + m_clSpieler.getBonus() + "% "
                        + de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                 .getProperty("Bonus") + ")";
            }

            m_jpGehalt.getLinks().setText(gehalttext + bonus);
            m_jpGehalt.getRechts().setSpezialNumber(gehalt - gehalt2, true);
            m_jpMartwert.getLinks().setText(tsitext);
            m_jpMartwert.getRechts().setSpezialNumber(m_clSpieler.getTSI()
                                                      - m_clVergleichsSpieler.getTSI(), false);
            m_jpForm.setText(PlayerHelper.getNameForSkill(m_clSpieler.getForm()) + "");
            m_jpForm2.setGrafischeVeraenderungswert(m_clSpieler.getForm()
                                                    - m_clVergleichsSpieler.getForm(),
                                                    !m_clVergleichsSpieler.isOld(), true);
            m_jpKondition.setText(PlayerHelper.getNameForSkill(m_clSpieler.getKondition()) + "");
            m_jpKondition2.setGrafischeVeraenderungswert(m_clSpieler.getKondition()
                                                         - m_clVergleichsSpieler.getKondition(),
                                                         !m_clVergleichsSpieler.isOld(), true);
            m_jpTorwart.setText(PlayerHelper.getNameForSkill(m_clSpieler.getTorwart()
                                                             + m_clSpieler
                                                               .getSubskill4SkillWithOffset(ISpieler.SKILL_TORWART))
                                + "");
            m_jpTorwart2.setGrafischeVeraenderungswert(m_clSpieler.getTorwart()
                                                       - m_clVergleichsSpieler.getTorwart(),
                                                       m_clSpieler.getSubskill4SkillWithOffset(ISpieler.SKILL_TORWART)
                                                       - m_clVergleichsSpieler
                                                         .getSubskill4SkillWithOffset(ISpieler.SKILL_TORWART),
                                                       !m_clVergleichsSpieler.isOld(), true);
            m_jpVerteidigung.setText(PlayerHelper.getNameForSkill(m_clSpieler.getVerteidigung()
                                                                  + m_clSpieler
                                                                    .getSubskill4SkillWithOffset(ISpieler.SKILL_VERTEIDIGUNG))
                                     + "");
            m_jpVerteidigung2.setGrafischeVeraenderungswert(m_clSpieler.getVerteidigung()
                                                            - m_clVergleichsSpieler.getVerteidigung(),
                                                            m_clSpieler.getSubskill4SkillWithOffset(ISpieler.SKILL_VERTEIDIGUNG)
                                                            - m_clVergleichsSpieler
                                                              .getSubskill4SkillWithOffset(ISpieler.SKILL_VERTEIDIGUNG),
                                                            !m_clVergleichsSpieler.isOld(), true);
            m_jpSpielaufbau.setText(PlayerHelper.getNameForSkill(m_clSpieler.getSpielaufbau()
                                                                 + m_clSpieler
                                                                   .getSubskill4SkillWithOffset(ISpieler.SKILL_SPIELAUFBAU))
                                    + "");
            m_jpSpielaufbau2.setGrafischeVeraenderungswert(m_clSpieler.getSpielaufbau()
                                                           - m_clVergleichsSpieler.getSpielaufbau(),
                                                           m_clSpieler.getSubskill4SkillWithOffset(ISpieler.SKILL_SPIELAUFBAU)
                                                           - m_clVergleichsSpieler
                                                             .getSubskill4SkillWithOffset(ISpieler.SKILL_SPIELAUFBAU),
                                                           !m_clVergleichsSpieler.isOld(), true);
            m_jpPasspiel.setText(PlayerHelper.getNameForSkill(m_clSpieler.getPasspiel()
                                                              + m_clSpieler
                                                                .getSubskill4SkillWithOffset(ISpieler.SKILL_PASSSPIEL))
                                 + "");
            m_jpPasspiel2.setGrafischeVeraenderungswert(m_clSpieler.getPasspiel()
                                                        - m_clVergleichsSpieler.getPasspiel(),
                                                        m_clSpieler.getSubskill4SkillWithOffset(ISpieler.SKILL_PASSSPIEL)
                                                        - m_clVergleichsSpieler
                                                          .getSubskill4SkillWithOffset(ISpieler.SKILL_PASSSPIEL),
                                                        !m_clVergleichsSpieler.isOld(), true);
            m_jpFluegelspiel.setText(PlayerHelper.getNameForSkill(m_clSpieler.getFluegelspiel()
                                                                  + m_clSpieler
                                                                    .getSubskill4SkillWithOffset(ISpieler.SKILL_FLUEGEL))
                                     + "");
            m_jpFluegelspiel2.setGrafischeVeraenderungswert(m_clSpieler.getFluegelspiel()
                                                            - m_clVergleichsSpieler.getFluegelspiel(),
                                                            m_clSpieler.getSubskill4SkillWithOffset(ISpieler.SKILL_FLUEGEL)
                                                            - m_clVergleichsSpieler
                                                              .getSubskill4SkillWithOffset(ISpieler.SKILL_FLUEGEL),
                                                            !m_clVergleichsSpieler.isOld(), true);
            m_jpStandards.setText(PlayerHelper.getNameForSkill(m_clSpieler.getStandards()
                                                               + m_clSpieler
                                                                 .getSubskill4SkillWithOffset(ISpieler.SKILL_STANDARDS))
                                  + "");
            m_jpStandards2.setGrafischeVeraenderungswert(m_clSpieler.getStandards()
                                                         - m_clVergleichsSpieler.getStandards(),
                                                         m_clSpieler.getSubskill4SkillWithOffset(ISpieler.SKILL_STANDARDS)
                                                         - m_clVergleichsSpieler
                                                           .getSubskill4SkillWithOffset(ISpieler.SKILL_STANDARDS),
                                                         !m_clVergleichsSpieler.isOld(), true);
            m_jpTorschuss.setText(PlayerHelper.getNameForSkill(m_clSpieler.getTorschuss()
                                                               + m_clSpieler
                                                                 .getSubskill4SkillWithOffset(ISpieler.SKILL_TORSCHUSS))
                                  + "");
            m_jpTorschuss2.setGrafischeVeraenderungswert(m_clSpieler.getTorschuss()
                                                         - m_clVergleichsSpieler.getTorschuss(),
                                                         m_clSpieler.getSubskill4SkillWithOffset(ISpieler.SKILL_TORSCHUSS)
                                                         - m_clVergleichsSpieler
                                                           .getSubskill4SkillWithOffset(ISpieler.SKILL_TORSCHUSS),
                                                         !m_clVergleichsSpieler.isOld(), true);
            m_jpErfahrung.setText(PlayerHelper.getNameForSkill(m_clSpieler.getErfahrung()) + "");
            m_jpErfahrung2.setGrafischeVeraenderungswert(m_clSpieler.getErfahrung()
                                                         - m_clVergleichsSpieler.getErfahrung(),
                                                         !m_clVergleichsSpieler.isOld(), true);
            m_jpFuehrung.setText(PlayerHelper.getNameForSkill(m_clSpieler.getFuehrung()) + "");
            m_jpFuehrung2.setGrafischeVeraenderungswert(m_clSpieler.getFuehrung()
                                                        - m_clVergleichsSpieler.getFuehrung(),
                                                        !m_clVergleichsSpieler.isOld(), true);
            m_jpBestPos.setText(de.hattrickorganizer.model.SpielerPosition.getNameForPosition(m_clSpieler
                                                                                              .getIdealPosition())
                                + " ("
                                + m_clSpieler.calcPosValue(m_clSpieler.getIdealPosition(), true)
                                + ")");

            for (int i = 0; i < playerPositionValues.length; i++) {
            	showWithCompare(playerPositionValues[i],playerPosition[i]);
			}

        }

        m_jpToreFreund.setText(m_clSpieler.getToreFreund() + "");
        m_jpToreLiga.setText(m_clSpieler.getToreLiga() + "");
        m_jpTorePokal.setText(m_clSpieler.getTorePokal() + "");
        m_jpToreGesamt.setText(m_clSpieler.getToreGesamt() + "");
        m_jpHattriks.setText(m_clSpieler.getHattrick() + "");
        m_jpSpezialitaet.setText(PlayerHelper.getNameForSpeciality(m_clSpieler.getSpezialitaet()));
        m_jpSpezialitaet.setIcon(Helper.getImageIcon4Spezialitaet(m_clSpieler
                                                                                             .getSpezialitaet()));
        m_jpAggressivitaet.setText(PlayerHelper.getNameForAggressivness(m_clSpieler
                                                                        .getAgressivitaet()));

        //Dreher
        m_jpAnsehen.setText(PlayerHelper.getNameForGentleness(m_clSpieler.getCharakter()));
        m_jpCharakter.setText(PlayerHelper.getNameForCharacter(m_clSpieler.getAnsehen()));

        String temp = "";
        final java.sql.Timestamp time = m_clSpieler.getTimestamp4FirstPlayerHRF();

        if (time != null) {
            temp += java.text.DateFormat.getDateInstance().format(time);

            final long timemillis = System.currentTimeMillis() - time.getTime();
            temp += (" (" + (int) (timemillis / 604800000) + " "
            + de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Wochen")
            + ")");
        }

        m_jpImTeamSeit.setText(temp);
        m_jtaNotizen.setEditable(true);
        m_jtaNotizen.setText(de.hattrickorganizer.database.DBZugriff.instance().getSpielerNotiz(m_clSpieler
                                                                                                .getSpielerID()));

        EPVData data = new EPVData(m_clSpieler);

        double price = HOVerwaltung.instance().getModel().getEPV().getPrice(data);
        final String epvtext = Helper.getNumberFormat(true, 0).format(price);
        m_jpEPV.setText( epvtext );


        m_jbTransvergleich.setEnabled(true);
        m_jbStatistik.setEnabled(true);
        m_jbAnalyse1.setEnabled(true);
        m_jbAnalyse2.setEnabled(true);
        m_jbOffsets.setEnabled(true);
    }

    private void showNormal(DoppelLabelEntry labelEntry,byte playerPosition){
    	labelEntry.getLinks().setText(Helper.round(m_clSpieler.calcPosValue(playerPosition,true),
                gui.UserParameter.instance().anzahlNachkommastellen)+ "");
    	labelEntry.getRechts().clear();
    }


    private void showWithCompare(DoppelLabelEntry labelEntry,byte playerPosition){
    	labelEntry.getLinks().setText(Helper.round(m_clSpieler.calcPosValue(playerPosition,true),
                gui.UserParameter.instance().anzahlNachkommastellen)+ "");

    	labelEntry.getRechts().setSpezialNumber(m_clSpieler.calcPosValue(playerPosition,true)
    					- m_clVergleichsSpieler.calcPosValue(playerPosition,true),false);
    }
    /**
     * return first player who is find in db
     *
     * @param player template
     *
     * @return player
     */
    private Spieler getVergleichsSpielerFirstHRF(Spieler vorlage) {
        return de.hattrickorganizer.database.DBZugriff.instance().getSpielerFirstHRF(vorlage
                                                                                     .getSpielerID());
    }

    /**
     * search player to compare
     */
    private void findVergleichsSpieler() {
        final int id = m_clSpieler.getSpielerID();

        for (int i = 0;
             (SpielerTrainingsVergleichsPanel.getVergleichsSpieler() != null)
             && (i < SpielerTrainingsVergleichsPanel.getVergleichsSpieler().size()); i++) {
            Spieler vergleichsSpieler = (Spieler) SpielerTrainingsVergleichsPanel.getVergleichsSpieler()
                                                                                 .get(i);

            if (vergleichsSpieler.getSpielerID() == id) {
                //Treffer
                m_clVergleichsSpieler = vergleichsSpieler;
                return;
            }
        }

        if (SpielerTrainingsVergleichsPanel.isVergleichsMarkierung()) {
            m_clVergleichsSpieler = getVergleichsSpielerFirstHRF(m_clSpieler);
            return;
        }

        //nix gefunden
        m_clVergleichsSpieler = null;
    }

    /**
     * initialize all fields
     */
    private void initComponents() {
        JComponent component = null;

        setLayout(new BorderLayout());

        final JPanel panel = new ImagePanel();
        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints constraints = new GridBagConstraints();
        final Properties properties = HOVerwaltung.instance().getResource();
        //constraints.anchor              =   GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.0;
        constraints.weighty = 1.0;
        constraints.insets = new Insets(1, 2, 1, 1);
        panel.setLayout(layout);

        JLabel label;

        //Leerzeile
        label = new JLabel("  ");
        setPosition(constraints,3,0);
        constraints.weightx = 0.0;
        constraints.gridheight = 11;
        layout.setConstraints(label, constraints);
        panel.add(label);
        constraints.gridheight = 1;

// ***** Block 1
        label = new JLabel(properties.getProperty("Name"));
        initNormalLabel(0,0,constraints,layout,panel,label);
        initNormalField(1,0,constraints,layout,panel,m_jpName.getComponent(false));

        label = new JLabel(properties.getProperty("Alter"));
        initNormalLabel(0,1,constraints,layout,panel,label);
        initNormalField(1,1,constraints,layout,panel,m_jpAlter.getComponent(false));

        label = new JLabel(properties.getProperty("Nationalitaet"));
        initNormalLabel(0,2,constraints,layout,panel,label);
        initNormalField(1,2,constraints,layout,panel,m_jpNationalitaet.getComponent(false));

        label = new JLabel(properties.getProperty("Aufgestellt"));
        initNormalLabel(0,3,constraints,layout,panel,label);
        initNormalField(1,3,constraints,layout,panel,m_jpAufgestellt.getComponent(false));

        label = new JLabel(properties.getProperty("Bewertung"));
        initNormalLabel(0,4,constraints,layout,panel,label);
        initNormalField(1,4,constraints,layout,panel,m_jpRating.getComponent(false));

        label = new JLabel(properties.getProperty("BestePosition"));
        initNormalLabel(0,5,constraints,layout,panel,label);
        initNormalField(1,5,constraints,layout,panel,m_jpBestPos.getComponent(false));

// ***** Block 2

        label = new JLabel(properties.getProperty("Gruppe"));
        initNormalLabel(4,0,constraints,layout,panel,label);
        //m_jcbTeamInfoSmilie.setPreferredSize ( new Dimension( 40, 16 ) );
        m_jcbTeamInfoSmilie.setPreferredSize(COMPONENTENSIZECB);
        m_jcbTeamInfoSmilie.setBackground(Color.white);
        m_jcbTeamInfoSmilie.setRenderer(new de.hattrickorganizer.gui.model.SmilieRenderer());
        m_jcbTeamInfoSmilie.addItemListener(this);
        setPosition(constraints,5,0);
        constraints.weightx = 1.0;
        constraints.gridwidth = 2;

        //m_jcbTeamInfoSmilie.setPreferredSize( COMPONENTENSIZE );
        layout.setConstraints(m_jcbTeamInfoSmilie, constraints);
        panel.add(m_jcbTeamInfoSmilie);

        label = new JLabel(properties.getProperty("Info"));
        initNormalLabel(4,1,constraints,layout,panel,label);

        m_jcbManuellerSmilie.setMaximumRowCount(10);
        //m_jcbManuellerSmilie.setPreferredSize( new Dimension( 40, 16 ) );
        m_jcbManuellerSmilie.setPreferredSize(COMPONENTENSIZECB);
        m_jcbManuellerSmilie.setBackground(Color.white);
        m_jcbManuellerSmilie.setRenderer(new de.hattrickorganizer.gui.model.SmilieRenderer());
        m_jcbManuellerSmilie.addItemListener(this);
        setPosition(constraints,5,1);
        constraints.weightx = 1.0;
        constraints.gridwidth = 2;

        //m_jcbManuellerSmilie.setPreferredSize( COMPONENTENSIZE );
        layout.setConstraints(m_jcbManuellerSmilie, constraints);
        panel.add(m_jcbManuellerSmilie);

        label = new JLabel(properties.getProperty("Status"));
        initNormalLabel(4,2,constraints,layout,panel,label);
        initNormalField(5,2,constraints,layout,panel,m_jpStatus.getComponent(false));

        label = new JLabel(properties.getProperty("Gehalt"));
        initNormalLabel(4,3,constraints,layout,panel,label);
        initNormalField(5,3,constraints,layout,panel,m_jpGehalt.getComponent(false));

        label = new JLabel("TSI");
        initNormalLabel(4,4,constraints,layout,panel,label);
        initNormalField(5,4,constraints,layout,panel,m_jpMartwert.getComponent(false));

        label = new JLabel(properties.getProperty("BestePosition"));
        initNormalLabel(4,5,constraints,layout,panel,label);

        m_jcbUserPos.setMaximumRowCount(20);
        //m_jcbUserPos.setPreferredSize( new Dimension( 40, 16 ) );
        m_jcbUserPos.setPreferredSize(COMPONENTENSIZECB);
        m_jcbUserPos.setBackground(Color.white);
        m_jcbUserPos.addItemListener(this);
        setPosition(constraints,5,5);
        constraints.weightx = 1.0;
        constraints.gridwidth = 2;
        //m_jcbManuellerSmilie.setPreferredSize( COMPONENTENSIZE );
        layout.setConstraints(m_jcbUserPos, constraints);
        panel.add(m_jcbUserPos);

        ///////////////////////////////////////////////////////////////////////////////////////
        //empty row
        label = new JLabel();
        setPosition(constraints,0,6);
        constraints.weightx = 0.0;
        constraints.gridwidth = 4;
        layout.setConstraints(label, constraints);
        panel.add(label);


        constraints.gridwidth = 1;
        label = new JLabel(properties.getProperty("Erfahrung"));
        initNormalLabel(0,7,constraints,layout,panel,label);
        initYellowMainField(1,7,constraints,layout,panel,m_jpErfahrung.getComponent(false));
        initYellowChangesField(2,7,constraints,layout,panel,m_jpErfahrung2.getComponent(false));

        label = new JLabel(properties.getProperty("Form"));
        initNormalLabel(4,7,constraints,layout,panel,label);
        initYellowMainField(5,7,constraints,layout,panel,m_jpForm.getComponent(false));
        initYellowChangesField(6,7,constraints,layout,panel,m_jpForm2.getComponent(false));

        label = new JLabel(properties.getProperty("Kondition"));
        initNormalLabel(0,8,constraints,layout,panel,label);
        initYellowMainField(1,8,constraints,layout,panel,m_jpKondition.getComponent(false));
        initYellowChangesField(2,8,constraints,layout,panel,m_jpKondition2.getComponent(false));

        label = new JLabel(properties.getProperty("Torwart"));
        initNormalLabel(4,8,constraints,layout,panel,label);
        initYellowMainField(5,8,constraints,layout,panel,m_jpTorwart.getComponent(false));
        initYellowChangesField(6,8,constraints,layout,panel,m_jpTorwart2.getComponent(false));

        label = new JLabel(properties.getProperty("Spielaufbau"));
        initNormalLabel(0,9,constraints,layout,panel,label);
        initYellowMainField(1,9,constraints,layout,panel,m_jpSpielaufbau.getComponent(false));
        initYellowChangesField(2,9,constraints,layout,panel,m_jpSpielaufbau2.getComponent(false));

        label = new JLabel(properties.getProperty("Passpiel"));
        initNormalLabel(4,9,constraints,layout,panel,label);
        initYellowMainField(5,9,constraints,layout,panel,m_jpPasspiel.getComponent(false));
        initYellowChangesField(6,9,constraints,layout,panel,m_jpPasspiel2.getComponent(false));

        label = new JLabel(properties.getProperty("Fluegelspiel"));
        initNormalLabel(0,10,constraints,layout,panel,label);
        initYellowMainField(1,10,constraints,layout,panel,m_jpFluegelspiel.getComponent(false));
        initYellowChangesField(2,10,constraints,layout,panel,m_jpFluegelspiel2.getComponent(false));

        label = new JLabel(properties.getProperty("Verteidigung"));
        initNormalLabel(4,10,constraints,layout,panel,label);
        initYellowMainField(5,10,constraints,layout,panel,m_jpVerteidigung.getComponent(false));
        initYellowChangesField(6,10,constraints,layout,panel,m_jpVerteidigung2.getComponent(false));

        label = new JLabel(properties.getProperty("Torschuss"));
        initNormalLabel(0,11,constraints,layout,panel,label);
        initYellowMainField(1,11,constraints,layout,panel,m_jpTorschuss.getComponent(false));
        initYellowChangesField(2,11,constraints,layout,panel,m_jpTorschuss2.getComponent(false));

        label = new JLabel(properties.getProperty("Standards"));
        initNormalLabel(4,11,constraints,layout,panel,label);
        initYellowMainField(5,11,constraints,layout,panel,m_jpStandards.getComponent(false));
        initYellowChangesField(6,11,constraints,layout,panel,m_jpStandards2.getComponent(false));

        m_jtaNotizen.addFocusListener(this);
        m_jtaNotizen.setEditable(false);

        final JPanel panel2 = new ImagePanel();
        panel2.setLayout(new BorderLayout());
        panel2.setBorder(javax.swing.BorderFactory.createTitledBorder(properties.getProperty("Notizen")));
        panel2.add(new JScrollPane(m_jtaNotizen), BorderLayout.CENTER);
        constraints.gridx = 0;
        constraints.weightx = 0.0;
        constraints.gridy = 12;
        constraints.gridheight = 7;
        constraints.gridwidth = 7;
        layout.setConstraints(panel2, constraints);
        panel.add(panel2);
        constraints.gridheight = 1;
        constraints.gridwidth = 1;

        ////////////////////////////////////////////////////////////////////////
        //empty row
        label = new JLabel("  ");
        setPosition(constraints,7,0);
        constraints.weightx = 0.0;
        constraints.gridheight = 11;
        layout.setConstraints(label, constraints);
        panel.add(label);
        constraints.gridheight = 1;

        label = new JLabel(properties.getProperty("Fuehrung"));
        initNormalLabel(8,0,constraints,layout,panel,label);
        setPosition(constraints,9,0);
        constraints.weightx = 1.0;
        component = m_jpFuehrung.getComponent(false);
        component.setPreferredSize(COMPONENTENSIZE3);
        layout.setConstraints(component, constraints);
        panel.add(component);
        setPosition(constraints,10,0);
        constraints.weightx = 1.0;
        component = m_jpFuehrung2.getComponent(false);
        component.setPreferredSize(COMPONENTENSIZE4);
        layout.setConstraints(component, constraints);
        panel.add(component);

        label = new JLabel(properties.getProperty("Spezialitaet"));
        initNormalLabel(8,1,constraints,layout,panel,label);
        initNormalField(9,1,constraints,layout,panel,m_jpSpezialitaet.getComponent(false));

        label = new JLabel(properties.getProperty("Aggressivitaet"));
        initNormalLabel(8,2,constraints,layout,panel,label);
        initNormalField(9,2,constraints,layout,panel,m_jpAggressivitaet.getComponent(false));

        label = new JLabel(properties.getProperty("Ansehen"));
        initNormalLabel(8,3,constraints,layout,panel,label);
        initNormalField(9,3,constraints,layout,panel,m_jpAnsehen.getComponent(false));

        label = new JLabel(properties.getProperty("Charakter"));
        initNormalLabel(8,4,constraints,layout,panel,label);
        initNormalField(9,4,constraints,layout,panel,m_jpCharakter.getComponent(false));

        label = new JLabel(properties.getProperty("ImTeamSeit"));
        initNormalLabel(8,5,constraints,layout,panel,label);
        initNormalField(9,5,constraints,layout,panel,m_jpImTeamSeit.getComponent(false));

        //1 Leerzeile
        label = new JLabel();
        setPosition(constraints,11,6);
        constraints.weightx = 0.0;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;
        layout.setConstraints(label, constraints);
        panel.add(label);
        constraints.gridwidth = 1;
        constraints.gridheight = 1;

        label = new JLabel(properties.getProperty("ToreFreund"));
        initNormalLabel(8,7,constraints,layout,panel,label);
        initNormalField(9,7,constraints,layout,panel,m_jpToreFreund.getComponent(false));

        label = new JLabel(properties.getProperty("ToreLiga"));
        initNormalLabel(8,8,constraints,layout,panel,label);
        initNormalField(9,8,constraints,layout,panel,m_jpToreLiga.getComponent(false));

        label = new JLabel(properties.getProperty("TorePokal"));
        initNormalLabel(8,9,constraints,layout,panel,label);
        initNormalField(9,9,constraints,layout,panel,m_jpTorePokal.getComponent(false));

        label = new JLabel(properties.getProperty("ToreGesamt"));
        initNormalLabel(8,10,constraints,layout,panel,label);
        initNormalField(9,10,constraints,layout,panel,m_jpToreGesamt.getComponent(false));

        label = new JLabel(properties.getProperty("Hattricks"));
        initNormalLabel(8,11,constraints,layout,panel,label);
        initNormalField(9,11,constraints,layout,panel,m_jpHattriks.getComponent(false));

        label = new JLabel(properties.getProperty("Marktwert"));
        initNormalLabel(8,13,constraints,layout,panel,label);
        initNormalField(9,13,constraints,layout,panel,m_jpEPV.getComponent(false));


        //Button
        final JPanel buttonpanel = new JPanel();

        //buttonpanel.setLayout( new BoxLayout( buttonpanel, BoxLayout.X_AXIS ) );
        buttonpanel.setOpaque(false);


        m_jbTransvergleich.setToolTipText(properties.getProperty("tt_Spieler_transfervergleich"));
        m_jbTransvergleich.setPreferredSize(new Dimension(28, 28));
        m_jbTransvergleich.setEnabled(false);
        m_jbTransvergleich.addActionListener(this);

        //buttonpanel.add( m_jbTransvergleich );// Geht mit readonlylogin nicht :(
        initButton(m_jbStatistik,properties.getProperty("tt_Spieler_statistik"),buttonpanel);
        initButton(m_jbAnalyse1,properties.getProperty("tt_Spieler_analyse1"),buttonpanel);
        initButton(m_jbAnalyse2,properties.getProperty("tt_Spieler_analyse2"),buttonpanel);
        initButton(m_jbOffsets,properties.getProperty("tt_Spieler_offset"),buttonpanel);

        setPosition(constraints,8,15);
        constraints.weightx = 1.0;
        constraints.gridheight = 3;
        constraints.gridwidth = 4;
        layout.setConstraints(buttonpanel, constraints);
        panel.add(buttonpanel);
        constraints.gridheight = 1;
        constraints.gridwidth = 1;

        ////////////////////////////////////////////////////////////////////////
        //Leerzeile
        label = new JLabel("  ");
        setPosition(constraints,11,0);
        constraints.weightx = 0.0;
        constraints.gridheight = 18;
        layout.setConstraints(label, constraints);
        panel.add(label);

        constraints.gridheight = 1;

        for (int i = 0; i < playerPositionValues.length; i++) {
        	label = new JLabel(SpielerPosition.getKurzNameForPosition(playerPosition[i]));
            label.setToolTipText(SpielerPosition.getNameForPosition(playerPosition[i]));
            initBlueLabel(i,constraints,layout,panel,label);
            initBlueField(i,constraints,layout,panel,playerPositionValues[i].getComponent(false));
		}

        add(panel, BorderLayout.CENTER);
    }

 	/**
	 * init a label
	 * @param x
	 * @param y
	 * @param constraints
	 * @param layout
	 * @param panel
	 * @param label
	 */
    private void initNormalLabel(int x,int y,GridBagConstraints constraints,GridBagLayout layout, JPanel panel, JLabel label){
    	constraints.gridwidth = 1;
    	setPosition(constraints,x,y);
    	constraints.weightx = 0.0;
    	layout.setConstraints(label, constraints);
    	panel.add(label);
    }

    /**
     * init a value field
     * @param x
     * @param y
     * @param constraints
     * @param layout
     * @param panel
     * @param component
     */
    private void initNormalField(int x,int y,GridBagConstraints constraints,GridBagLayout layout, JPanel panel, JComponent component){
        setPosition(constraints,x,y);
        constraints.weightx = 1.0;
        constraints.gridwidth = 2;
        component.setPreferredSize(COMPONENTENSIZE);
        layout.setConstraints(component, constraints);
        panel.add(component);
    }

    /**
     * init a label
     * @param y
     * @param constraints
     * @param layout
     * @param panel
     * @param label
     */
    private void initBlueLabel(int y,GridBagConstraints constraints,GridBagLayout layout, JPanel panel, JLabel label){
    	setPosition(constraints,12,y);
        constraints.weightx = 0.0;
        layout.setConstraints(label, constraints);
        panel.add(label);
    }

    /**
     * init a value field
     * @param y
     * @param constraints
     * @param layout
     * @param panel
     * @param component
     */
    private void initBlueField(int y,GridBagConstraints constraints,GridBagLayout layout, JPanel panel, JComponent component){
        setPosition(constraints,13,y);
        constraints.weightx = 1.0;
        component.setPreferredSize(COMPONENTENSIZE2);
        layout.setConstraints(component, constraints);
        panel.add(component);
    }

    /**
     * init a value field
     * @param y
     * @param constraints
     * @param layout
     * @param panel
     * @param component
     */
    private void initYellowMainField(int x,int y,GridBagConstraints constraints,GridBagLayout layout, JPanel panel, JComponent component){
        setPosition(constraints,x,y);
        constraints.weightx = 1.0;
        component.setPreferredSize(COMPONENTENSIZE3);
        layout.setConstraints(component, constraints);
        panel.add(component);
    }
    /**
     * init a value field
     * @param y
     * @param constraints
     * @param layout
     * @param panel
     * @param component
     */
    private void initYellowChangesField(int x,int y,GridBagConstraints constraints,GridBagLayout layout, JPanel panel, JComponent component){
        setPosition(constraints,x,y);
        constraints.weightx = 1.0;
        component.setPreferredSize(COMPONENTENSIZE4);
        layout.setConstraints(component, constraints);
        panel.add(component);
    }
    /**
     * set position in gridBag
     * @param constraints
     * @param x
     * @param y
     */
    private void setPosition(GridBagConstraints c, int x, int y){
    	c.gridx = x;
        c.gridy = y;
    }

    private void initButton(JButton button, String tooltipText, JPanel buttonpanel){
    	button.setToolTipText(tooltipText);
    	button.setPreferredSize(new Dimension(28, 28));
    	button.setEnabled(false);
    	button.addActionListener(this);
        buttonpanel.add(button);
    }
    /**
     * clears all labels
     */
    private void resetLabels() {
        m_jpName.clear();
        m_jpAlter.clear();
        m_jpNationalitaet.clear();
        m_jpAufgestellt.clear();
        m_jpStatus.clear();
        m_jcbTeamInfoSmilie.setSelectedItem("");
        m_jcbManuellerSmilie.setSelectedItem("");
        m_jpRating.clear();
        m_jpGehalt.clear();
        m_jpMartwert.clear();
        m_jpForm.clear();
        m_jpKondition.clear();
        m_jpTorwart.clear();
        m_jpVerteidigung.clear();
        m_jpSpielaufbau.clear();
        m_jpPasspiel.clear();
        m_jpFluegelspiel.clear();
        m_jpStandards.clear();
        m_jpTorschuss.clear();
        m_jpErfahrung.clear();
        m_jpFuehrung.clear();
        m_jpForm2.clear();
        m_jpKondition2.clear();
        m_jpTorwart2.clear();
        m_jpVerteidigung2.clear();
        m_jpSpielaufbau2.clear();
        m_jpPasspiel2.clear();
        m_jpFluegelspiel2.clear();
        m_jpStandards2.clear();
        m_jpTorschuss2.clear();
        m_jpErfahrung2.clear();
        m_jpFuehrung2.clear();
        m_jpBestPos.clear();
        m_jcbUserPos.setSelectedItem("");

        for (int i = 0; i < playerPositionValues.length; i++) {
        	playerPositionValues[i].clear();
        }

        m_jpToreFreund.clear();
        m_jpToreLiga.clear();
        m_jpTorePokal.clear();
        m_jpToreGesamt.clear();
        m_jpHattriks.clear();
        m_jpSpezialitaet.clear();
        m_jpAggressivitaet.clear();
        m_jpAnsehen.clear();
        m_jpCharakter.clear();
        m_jpImTeamSeit.clear();
        m_jtaNotizen.setText("");
        m_jtaNotizen.setEditable(false);

        m_jbTransvergleich.setEnabled(false);
        m_jbStatistik.setEnabled(false);
        m_jbAnalyse1.setEnabled(false);
        m_jbAnalyse2.setEnabled(false);
        m_jbOffsets.setEnabled(false);
    }

    /**
     * Transfervergleich
     */
    private void transvergleich() {
        //Aktuellen Spieler holen
        final de.hattrickorganizer.model.Spieler spieler = de.hattrickorganizer.gui.HOMainFrame.instance()
                                                                                               .getSpielerUebersichtPanel()
                                                                                               .getAktuellenSpieler();

        if (spieler != null) {
            try {
                final String text = de.hattrickorganizer.net.MyConnector.instance()
                                                                        .getTransferCompare(spieler
                                                                                            .getSpielerID());
                String showntext = "<html><body>";

                //Dialog mit Matchbericht erzeugen
                final String titel = spieler.getName();
                final JDialog matchdialog = new JDialog(de.hattrickorganizer.gui.HOMainFrame
                                                        .instance(), titel);
                matchdialog.getContentPane().setLayout(new BorderLayout());

                final de.hattrickorganizer.gui.matches.MatchberichtEditorPanel berichtpanel = new de.hattrickorganizer.gui.matches.MatchberichtEditorPanel();
                matchdialog.getContentPane().add(berichtpanel, BorderLayout.CENTER);

                matchdialog.setLocation(50, 50);
                matchdialog.setSize(400, 400);
                matchdialog.setVisible(true);

                //4. Table als start
                final int startindex = text.indexOf("<TABLE",
                                                    text.indexOf("<TABLE",
                                                                 text.indexOf("<TABLE",
                                                                              text.indexOf("<TABLE",
                                                                                           text
                                                                                           .indexOf("<TABLE")
                                                                                           + 1) + 1)
                                                                 + 1) + 1);

                //6. Tabellenenden als ende
                final int endindex = text.indexOf("</TABLE",
                                                  text.indexOf("</TABLE",
                                                               text.indexOf("</TABLE",
                                                                            text.indexOf("</TABLE")
                                                                            + 1) + 1) + 1);

                showntext += text.substring(startindex, endindex);
                showntext += "</table></body></html>";

                berichtpanel.setText(showntext);
            } catch (Exception e) {
                HOLogger.instance().log(getClass(),e);
            }
        }
    }
}
