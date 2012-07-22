// %3484484869:de.hattrickorganizer.gui.playeroverview%
package ho.module.playerOverview;

import ho.core.constants.player.PlayerAbility;
import ho.core.constants.player.PlayerAggressiveness;
import ho.core.constants.player.PlayerAgreeability;
import ho.core.constants.player.PlayerHonesty;
import ho.core.constants.player.PlayerSkill;
import ho.core.constants.player.PlayerSpeciality;
import ho.core.db.DBManager;
import ho.core.epv.EPVData;
import ho.core.gui.HOMainFrame;
import ho.core.gui.RefreshManager;
import ho.core.gui.Refreshable;
import ho.core.gui.comp.entry.ColorLabelEntry;
import ho.core.gui.comp.entry.DoppelLabelEntry;
import ho.core.gui.comp.entry.RatingTableEntry;
import ho.core.gui.comp.entry.SpielerLabelEntry;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.gui.comp.renderer.SmilieListCellRenderer;
import ho.core.gui.theme.HOColorName;
import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ImageUtilities;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.HOVerwaltung;
import ho.core.model.player.ISpielerPosition;
import ho.core.model.player.Spieler;
import ho.core.model.player.SpielerPosition;
import ho.core.module.IModule;
import ho.core.util.Helper;
import ho.module.lineup.Lineup;
import ho.module.statistics.StatistikMainPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;



/**
 * Shows player details for the selected player
 */
public final class SpielerDetailPanel extends ImagePanel implements Refreshable, FocusListener, ItemListener, ActionListener
{

	private static final long serialVersionUID = 3466993172643378958L;

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
    // Top Row, column 1
    private final ColorLabelEntry m_jpName = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
    		ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);
    private final ColorLabelEntry m_jpAge = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
    		ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);
    private final ColorLabelEntry m_jpNationality = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
    		ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);
    private final ColorLabelEntry m_jpPositioned = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
    		ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);
    private RatingTableEntry m_jpRating = new RatingTableEntry();
    private final ColorLabelEntry m_jpBestPosition = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
    		ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);
    // Top Row, column 2
    private final JComboBox m_jcbSquad = new JComboBox(HOIconName.TEAMSMILIES);
    private final JComboBox m_jcbInformation = new JComboBox(HOIconName.MANUELLSMILIES);
    private SpielerStatusLabelEntry m_jpStatus = new SpielerStatusLabelEntry();
    private final DoppelLabelEntry m_jpSalary = new DoppelLabelEntry(new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
            ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT), new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
            ColorLabelEntry.BG_STANDARD, SwingConstants.RIGHT));
    private final DoppelLabelEntry m_jpTSI = new DoppelLabelEntry(new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
    		ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT), new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
    		ColorLabelEntry.BG_STANDARD, SwingConstants.RIGHT));
    private final JComboBox m_jcbUserBestPosition = new JComboBox(SpielerPosition.POSITIONEN);
    // Top Row, column 3
    private final ColorLabelEntry m_jpLeadership = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
    		ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);
    private final ColorLabelEntry m_jpSpeciality = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
            ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);
    private final ColorLabelEntry m_jpAggressivity = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
    		ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);
    private final ColorLabelEntry m_jpAgreeability = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
    		ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);
    private final ColorLabelEntry m_jpHonesty = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
    		ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);
    private final ColorLabelEntry m_jpInTeamSince = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
    		ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);
    // Second Row, Column 1
    private final ColorLabelEntry m_jpExperience = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
    		ColorLabelEntry.BG_SPIELERSONDERWERTE, SwingConstants.LEFT);
    private final ColorLabelEntry m_jpExperienceChange = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
    		ColorLabelEntry.BG_SPIELERSONDERWERTE, SwingConstants.CENTER);
    private final ColorLabelEntry m_jpStamina = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
    		ColorLabelEntry.BG_SPIELEREINZELWERTE, SwingConstants.LEFT);
    private final ColorLabelEntry m_jpStaminaChange = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
    		ColorLabelEntry.BG_SPIELEREINZELWERTE, SwingConstants.CENTER);
    private final ColorLabelEntry m_jpPlaymaking = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
            ColorLabelEntry.BG_SPIELEREINZELWERTE, SwingConstants.LEFT);
    private final ColorLabelEntry m_jpPlaymakingChange = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
             ColorLabelEntry.BG_SPIELEREINZELWERTE, SwingConstants.CENTER);
	private final ColorLabelEntry m_jpWinger = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
    		ColorLabelEntry.BG_SPIELEREINZELWERTE, SwingConstants.LEFT);
    private final ColorLabelEntry m_jpWingerChange = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
    		ColorLabelEntry.BG_SPIELEREINZELWERTE, SwingConstants.CENTER);
    private final ColorLabelEntry m_jpScoring = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
            ColorLabelEntry.BG_SPIELEREINZELWERTE, SwingConstants.LEFT);
    private final ColorLabelEntry m_jpScoringChange = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
             ColorLabelEntry.BG_SPIELEREINZELWERTE, SwingConstants.CENTER);
    private final ColorLabelEntry m_jpLoyalty = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
			ColorLabelEntry.BG_SPIELEREINZELWERTE, SwingConstants.LEFT);
    private final ColorLabelEntry m_jpLoyaltyChange = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
			ColorLabelEntry.BG_SPIELEREINZELWERTE, SwingConstants.CENTER);
    // Second Row, Column 2
    private final ColorLabelEntry m_jpForm = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
    		ColorLabelEntry.BG_SPIELERSONDERWERTE, SwingConstants.LEFT);
    private final ColorLabelEntry m_jpFormChange = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
    		ColorLabelEntry.BG_SPIELERSONDERWERTE, SwingConstants.CENTER);
    private final ColorLabelEntry m_jpKeeper = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
            ColorLabelEntry.BG_SPIELEREINZELWERTE, SwingConstants.LEFT);
    private final ColorLabelEntry m_jpKeeperChange = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
             ColorLabelEntry.BG_SPIELEREINZELWERTE, SwingConstants.CENTER);
    private final ColorLabelEntry m_jpPassing = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
    		ColorLabelEntry.BG_SPIELEREINZELWERTE, SwingConstants.LEFT);
    private final ColorLabelEntry m_jpPassingChange = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
    		ColorLabelEntry.BG_SPIELEREINZELWERTE, SwingConstants.CENTER);
    private final ColorLabelEntry m_jpDefending = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
            ColorLabelEntry.BG_SPIELEREINZELWERTE, SwingConstants.LEFT);
    private final ColorLabelEntry m_jpDefendingChange = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
    		ColorLabelEntry.BG_SPIELEREINZELWERTE, SwingConstants.CENTER);
    private final ColorLabelEntry m_jpSetPieces = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
            ColorLabelEntry.BG_SPIELEREINZELWERTE, SwingConstants.LEFT);
    private final ColorLabelEntry m_jpSetPiecesChange = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
             ColorLabelEntry.BG_SPIELEREINZELWERTE, SwingConstants.CENTER);
    private final ColorLabelEntry m_jpMotherClub = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
			ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);
    // Second Row, Column 3
    private final ColorLabelEntry m_jpGoalsFriendly = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
            ColorLabelEntry.BG_STANDARD, SwingConstants.CENTER);
    private final ColorLabelEntry m_jpGoalsLeague = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
            ColorLabelEntry.BG_STANDARD, SwingConstants.CENTER);
    private final ColorLabelEntry m_jpGoalsCup = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
            ColorLabelEntry.BG_STANDARD, SwingConstants.CENTER);
    private final ColorLabelEntry m_jpGoalsTotal = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
            ColorLabelEntry.BG_STANDARD, SwingConstants.CENTER);
    private final ColorLabelEntry m_jpHattricks = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
    		ColorLabelEntry.BG_STANDARD, SwingConstants.CENTER);
    private final ColorLabelEntry m_jpMarketValue = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
            ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);
    // Third Row, Columns 1 & 2
    private final JTextArea m_jtaNotes = new JTextArea(5, 12);
    // Third Row, Column 3
    private final JButton m_jbStatistics = new JButton(ThemeManager.getIcon(HOIconName.GOTOSTATISTIK));
    private final JButton m_jbAnalysisTop = new JButton(ThemeManager.getIcon(HOIconName.GOTOANALYSETOP));
    private final JButton m_jbAnalysisBottom = new JButton(ThemeManager.getIcon(HOIconName.GOTOANALYSEBOTTOM));
    private final JButton m_jbOffsets = new JButton(ThemeManager.getIcon(HOIconName.OFFSET));
    private final JButton m_jbTrainingBlock = new JButton(ThemeManager.getIcon(HOIconName.TRAININGBLOCK));
    // Ratings Column
    private final DoppelLabelEntry m_jpRatingKeeper = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERPOSITONSWERTE);
    private final DoppelLabelEntry m_jpRatingCentralDefender = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERPOSITONSWERTE);
    private final DoppelLabelEntry m_jpRatingCentralDefenderTowardsWing = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private final DoppelLabelEntry m_jpRatingCentralDefenderOffensive = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private final DoppelLabelEntry m_jpRatingWingback = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERPOSITONSWERTE);
    private final DoppelLabelEntry m_jpRatingWingbackDefensive = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private final DoppelLabelEntry m_jpRatingWingbackTowardsMiddle = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private final DoppelLabelEntry m_jpRatingWingbackOffensive = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private final DoppelLabelEntry m_jpRatingeMidfielder = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERPOSITONSWERTE);
    private final DoppelLabelEntry m_jpRatingeMidfielderTowardsWing = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private final DoppelLabelEntry m_jpRatingeMidfielderDefensive = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private final DoppelLabelEntry m_jpRatingeMidfielderOffensive = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private final DoppelLabelEntry m_jpRatingWinger = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERPOSITONSWERTE);
    private final DoppelLabelEntry m_jpRatingWingerDefensive = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private final DoppelLabelEntry m_jpRatingWingerTowardsMiddle = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private final DoppelLabelEntry m_jpRatingWingerOffensive = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private final DoppelLabelEntry m_jpRatingForward = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERPOSITONSWERTE);
    private final DoppelLabelEntry m_jpRatingForwardTowardsWing = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private final DoppelLabelEntry m_jpRatingForwardDefensive = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    // Players
    private Spieler m_clPlayer;
    private Spieler m_clComparisonPlayer;

    private final DoppelLabelEntry[] playerPositionValues= new DoppelLabelEntry[]{
    		m_jpRatingKeeper,
            m_jpRatingCentralDefender,
            m_jpRatingCentralDefenderTowardsWing,
            m_jpRatingCentralDefenderOffensive,
            m_jpRatingWingback,
            m_jpRatingWingbackTowardsMiddle,
            m_jpRatingWingbackOffensive,
            m_jpRatingWingbackDefensive,
            m_jpRatingeMidfielder,
            m_jpRatingeMidfielderTowardsWing,
            m_jpRatingeMidfielderOffensive,
            m_jpRatingeMidfielderDefensive,
            m_jpRatingWinger,
            m_jpRatingWingerTowardsMiddle,
            m_jpRatingWingerOffensive,
            m_jpRatingWingerDefensive,
            m_jpRatingForward,
            m_jpRatingForwardTowardsWing,
            m_jpRatingForwardDefensive
    };

    private final byte[] playerPosition = new byte[]{
    		ISpielerPosition.KEEPER,
            ISpielerPosition.CENTRAL_DEFENDER,
            ISpielerPosition.CENTRAL_DEFENDER_TOWING,
            ISpielerPosition.CENTRAL_DEFENDER_OFF,
            ISpielerPosition.BACK,
            ISpielerPosition.BACK_TOMID,
            ISpielerPosition.BACK_OFF,
            ISpielerPosition.BACK_DEF,
            ISpielerPosition.MIDFIELDER,
            ISpielerPosition.MIDFIELDER_TOWING,
            ISpielerPosition.MIDFIELDER_OFF,
            ISpielerPosition.MIDFIELDER_DEF,
            ISpielerPosition.WINGER,
            ISpielerPosition.WINGER_TOMID,
            ISpielerPosition.WINGER_OFF,
            ISpielerPosition.WINGER_DEF,
            ISpielerPosition.FORWARD,
            ISpielerPosition.FORWARD_TOWING,
            ISpielerPosition.FORWARD_DEF

    };

    //~ Constructors -------------------------------------------------------------------------------
    /**
     * Creates a new SpielerDetailPanel object.
     */
    protected SpielerDetailPanel() {
        initComponents();
        RefreshManager.instance().registerRefreshable(this);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Set the player to be shown
     *
     * @param spieler TODO Missing Method Parameter Documentation
     */
    public final void setSpieler(Spieler player) {
        m_clPlayer = player;
        if (m_clPlayer != null) {
        	findComparisonPlayer();
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
    @Override
	public final void actionPerformed(java.awt.event.ActionEvent actionevent) {
    	if (actionevent.getSource().equals(m_jbStatistics)) {
    		HOMainFrame.instance().showTab(IModule.STATISTICS);
    		((StatistikMainPanel)HOMainFrame.instance().getTabbedPane().getModulePanel(IModule.STATISTICS)).setShowSpieler(m_clPlayer.getSpielerID());
        } else if (actionevent.getSource().equals(m_jbAnalysisTop)) {
        	HOMainFrame.instance().showTab(IModule.PLAYERANALYSIS);
            HOMainFrame.instance().getSpielerAnalyseMainPanel().setSpieler4Top(m_clPlayer.getSpielerID());
        } else if (actionevent.getSource().equals(m_jbAnalysisBottom)) {
            HOMainFrame.instance().showTab(IModule.PLAYERANALYSIS);
        	HOMainFrame.instance().getSpielerAnalyseMainPanel().setSpieler4Bottom(m_clPlayer.getSpielerID());
        } else if (actionevent.getSource().equals(m_jbOffsets)) {
            new SpielerOffsetDialog(HOMainFrame.instance(), m_clPlayer).setVisible(true);
        } else if (actionevent.getSource().equals(m_jbTrainingBlock)) {
        	new SpielerTrainingBlockDialog(HOMainFrame.instance(), m_clPlayer).setVisible(true);
        }
    }

    /**
     * action on focus gained => do nothing
     *
     * @param event
     */
    @Override
	public void focusGained(java.awt.event.FocusEvent event) {
    }

    /**
     * action on focus lost => save player note
     *
     * @param event
     */
    @Override
	public final void focusLost(java.awt.event.FocusEvent event) {
        if (m_clPlayer != null) {
            DBManager.instance().saveSpielerNotiz(m_clPlayer.getSpielerID(), m_jtaNotes.getText());
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param itemEvent TODO Missing Method Parameter Documentation
     */
    @Override
	public final void itemStateChanged(java.awt.event.ItemEvent itemEvent) {
        if (itemEvent.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            if (m_clPlayer != null) {
                if (itemEvent.getSource().equals(m_jcbSquad)) {
                    m_clPlayer.setTeamInfoSmilie(m_jcbSquad.getSelectedItem().toString());
                } else if (itemEvent.getSource().equals(m_jcbInformation)) {
                    m_clPlayer.setManuellerSmilie(m_jcbInformation.getSelectedItem().toString());
                } else if (itemEvent.getSource().equals(m_jcbUserBestPosition)) {
                    m_clPlayer.setUserPosFlag((byte) ((ho.core.datatype.CBItem) m_jcbUserBestPosition
                                                       .getSelectedItem()).getId());
                }
                HOMainFrame.instance().getSpielerUebersichtPanel().update();
            }
        }
    }

    /**
     * set the player to compare and refresh the display
     */
    @Override
	public final void reInit() {
        if (m_clPlayer != null) {
            findComparisonPlayer();
        }
        setSpieler(null);
    }

    /**
     * refresh the display
     */
    @Override
	public final void refresh() {
        setSpieler(m_clPlayer);
    }

    /**
     * set values of the player to fields
     */
    private void setLabels() {
        m_jpName.setText(m_clPlayer.getName());
        m_jpName.setFGColor(SpielerLabelEntry.getForegroundForSpieler(m_clPlayer));
        m_jpAge.setText(m_clPlayer.getAgeStringFull());
        m_jpNationality.setIcon(ImageUtilities.getFlagIcon(m_clPlayer.getNationalitaet()));
        if (m_clPlayer.isHomeGrown())
        	m_jpMotherClub.setIcon( ThemeManager.getIcon(HOIconName.HOMEGROWN));
        else
        	m_jpMotherClub.clear();
        Lineup lineup = HOVerwaltung.instance().getModel().getAufstellung();
        if (lineup.isSpielerAufgestellt(m_clPlayer.getSpielerID())
            && (lineup.getPositionBySpielerId(m_clPlayer.getSpielerID()) != null)) {
            m_jpPositioned.setIcon(ImageUtilities.getImage4Position(lineup.getPositionBySpielerId(m_clPlayer.getSpielerID()),
            		m_clPlayer.getTrikotnummer()));
            m_jpPositioned.setText(SpielerPosition.getNameForPosition(lineup.getPositionBySpielerId(m_clPlayer.getSpielerID())
            		.getPosition()));
        } else {
            m_jpPositioned.setIcon(ImageUtilities.getImage4Position(null, m_clPlayer.getTrikotnummer()));
            m_jpPositioned.setText("");
        }
        //Rating
        if (m_clPlayer.getBewertung() > 0) {
            m_jpRating.setYellowStar(true);
            m_jpRating.setRating(m_clPlayer.getBewertung());
        } else {
            m_jpRating.setYellowStar(false);
            m_jpRating.setRating(m_clPlayer.getLetzteBewertung());
        }
        m_jcbSquad.removeItemListener(this);
        m_jcbSquad.setSelectedItem(m_clPlayer.getTeamInfoSmilie());
        m_jcbSquad.addItemListener(this);
        m_jcbInformation.removeItemListener(this);
        m_jcbInformation.setSelectedItem(m_clPlayer.getManuellerSmilie());
        m_jcbInformation.addItemListener(this);
        m_jpStatus.setSpieler(m_clPlayer);
        m_jcbUserBestPosition.removeItemListener(this);
        Helper.markierenComboBox(m_jcbUserBestPosition, m_clPlayer.getUserPosFlag());
        m_jcbUserBestPosition.addItemListener(this);
        final int salary = (int)(m_clPlayer.getGehalt() / ho.core.model.UserParameter.instance().faktorGeld);
		final String salarytext = Helper.getNumberFormat(true, 0).format(salary);
		final String tsitext = Helper.getNumberFormat(false, 0).format(m_clPlayer.getTSI());
        if (m_clComparisonPlayer == null) {
            String bonus = "";
            if (m_clPlayer.getBonus() > 0) {
                bonus = " (" + m_clPlayer.getBonus() + "% "
                        + HOVerwaltung.instance().getLanguageString("Bonus") + ")";
            }
            m_jpSalary.getLinks().setText(salarytext + bonus);
            m_jpSalary.getRechts().clear();
            m_jpTSI.getLinks().setText(tsitext);
            m_jpTSI.getRechts().clear();
            m_jpForm.setText(PlayerAbility.getNameForSkill(m_clPlayer.getForm()) + "");
            m_jpFormChange.clear();
            m_jpStamina.setText(PlayerAbility.getNameForSkill(m_clPlayer.getKondition()) + "");
            m_jpStaminaChange.clear();
            m_jpKeeper.setText(PlayerAbility.getNameForSkill(m_clPlayer.getTorwart()
            		+ m_clPlayer.getSubskill4Pos(PlayerSkill.KEEPER)) + "");
            m_jpKeeperChange.clear();
            m_jpDefending.setText(PlayerAbility.getNameForSkill(m_clPlayer.getVerteidigung()
            		+ m_clPlayer.getSubskill4Pos(PlayerSkill.DEFENDING)) + "");
            m_jpDefendingChange.clear();
            m_jpPlaymaking.setText(PlayerAbility.getNameForSkill(m_clPlayer.getSpielaufbau()
            		+ m_clPlayer.getSubskill4Pos(PlayerSkill.PLAYMAKING)) + "");
            m_jpPlaymakingChange.clear();
            m_jpPassing.setText(PlayerAbility.getNameForSkill(m_clPlayer.getPasspiel()
            		+ m_clPlayer.getSubskill4Pos(PlayerSkill.PASSING)) + "");
            m_jpPassingChange.clear();
            m_jpWinger.setText(PlayerAbility.getNameForSkill(m_clPlayer.getFluegelspiel()
            		+ m_clPlayer.getSubskill4Pos(PlayerSkill.WINGER)) + "");
            m_jpWingerChange.clear();
            m_jpSetPieces.setText(PlayerAbility.getNameForSkill(m_clPlayer.getStandards()
            		+ m_clPlayer.getSubskill4Pos(PlayerSkill.SET_PIECES)) + "");
            m_jpSetPiecesChange.clear();
            m_jpScoring.setText(PlayerAbility.getNameForSkill(m_clPlayer.getTorschuss()
            		+ m_clPlayer.getSubskill4Pos(PlayerSkill.SCORING)) + "");
            m_jpScoringChange.clear();
            m_jpExperience.setText(PlayerAbility.getNameForSkill(m_clPlayer.getErfahrung()) + "");
            m_jpExperienceChange.clear();
            m_jpLeadership.setText(PlayerAbility.getNameForSkill(m_clPlayer.getFuehrung()) + "");
            m_jpLoyalty.setText(PlayerAbility.getNameForSkill(m_clPlayer.getLoyalty()) + "");
            m_jpLoyaltyChange.clear();
            m_jpBestPosition.setText(SpielerPosition.getNameForPosition(m_clPlayer.getIdealPosition())
                                + " ("
                                + Helper.getNumberFormat(false, ho.core.model.UserParameter.instance().anzahlNachkommastellen).format(
                                		m_clPlayer.calcPosValue(m_clPlayer.getIdealPosition(), true))
                                + ")");
            for (int i = 0; i < playerPositionValues.length; i++) {
            	showNormal(playerPositionValues[i],playerPosition[i]);
			}

        } else {
            String bonus = "";
            final int gehalt2 = (int)(m_clComparisonPlayer.getGehalt() / ho.core.model.UserParameter.instance().faktorGeld);
            if (m_clPlayer.getBonus() > 0) {
                bonus = " (" + m_clPlayer.getBonus() + "% "
                        + HOVerwaltung.instance().getLanguageString("Bonus") + ")";
            }
            m_jpSalary.getLinks().setText(salarytext + bonus);
            m_jpSalary.getRechts().setSpezialNumber(salary - gehalt2, true);
            m_jpTSI.getLinks().setText(tsitext);
            m_jpTSI.getRechts().setSpezialNumber(m_clPlayer.getTSI() - m_clComparisonPlayer.getTSI(), false);
            m_jpForm.setText(PlayerAbility.getNameForSkill(m_clPlayer.getForm()) + "");
            m_jpFormChange.setGrafischeVeraenderungswert(m_clPlayer.getForm()
            		- m_clComparisonPlayer.getForm(), !m_clComparisonPlayer.isOld(), true);
            m_jpStamina.setText(PlayerAbility.getNameForSkill(m_clPlayer.getKondition()) + "");
            m_jpStaminaChange.setGrafischeVeraenderungswert(m_clPlayer.getKondition()
            		- m_clComparisonPlayer.getKondition(), !m_clComparisonPlayer.isOld(), true);
            m_jpKeeper.setText(PlayerAbility.getNameForSkill(m_clPlayer.getTorwart()
            		+ m_clPlayer.getSubskill4Pos(PlayerSkill.KEEPER)) + "");
            m_jpKeeperChange.setGrafischeVeraenderungswert(m_clPlayer.getTorwart()
            		- m_clComparisonPlayer.getTorwart(),
            		m_clPlayer.getSubskill4Pos(PlayerSkill.KEEPER)
            		- m_clComparisonPlayer.getSubskill4Pos(PlayerSkill.KEEPER),
            		!m_clComparisonPlayer.isOld(), true);
            m_jpDefending.setText(PlayerAbility.getNameForSkill(m_clPlayer.getVerteidigung()
            		+ m_clPlayer.getSubskill4Pos(PlayerSkill.DEFENDING)) + "");
            m_jpDefendingChange.setGrafischeVeraenderungswert(m_clPlayer.getVerteidigung()
            		- m_clComparisonPlayer.getVerteidigung(),
            		m_clPlayer.getSubskill4Pos(PlayerSkill.DEFENDING)
                    - m_clComparisonPlayer.getSubskill4Pos(PlayerSkill.DEFENDING),
                    !m_clComparisonPlayer.isOld(), true);
            m_jpPlaymaking.setText(PlayerAbility.getNameForSkill(m_clPlayer.getSpielaufbau()
            		+ m_clPlayer.getSubskill4Pos(PlayerSkill.PLAYMAKING)) + "");
            m_jpPlaymakingChange.setGrafischeVeraenderungswert(m_clPlayer.getSpielaufbau()
            		- m_clComparisonPlayer.getSpielaufbau(),
            		m_clPlayer.getSubskill4Pos(PlayerSkill.PLAYMAKING)
            		- m_clComparisonPlayer.getSubskill4Pos(PlayerSkill.PLAYMAKING),
            		!m_clComparisonPlayer.isOld(), true);
            m_jpPassing.setText(PlayerAbility.getNameForSkill(m_clPlayer.getPasspiel()
            		+ m_clPlayer.getSubskill4Pos(PlayerSkill.PASSING)) + "");
            m_jpPassingChange.setGrafischeVeraenderungswert(m_clPlayer.getPasspiel()
            		- m_clComparisonPlayer.getPasspiel(),
            		m_clPlayer.getSubskill4Pos(PlayerSkill.PASSING)
            		- m_clComparisonPlayer.getSubskill4Pos(PlayerSkill.PASSING),
            		!m_clComparisonPlayer.isOld(), true);
            m_jpWinger.setText(PlayerAbility.getNameForSkill(m_clPlayer.getFluegelspiel()
            		+ m_clPlayer.getSubskill4Pos(PlayerSkill.WINGER)) + "");
            m_jpWingerChange.setGrafischeVeraenderungswert(m_clPlayer.getFluegelspiel()
            		- m_clComparisonPlayer.getFluegelspiel(),
            		m_clPlayer.getSubskill4Pos(PlayerSkill.WINGER)
                    - m_clComparisonPlayer.getSubskill4Pos(PlayerSkill.WINGER),
                    !m_clComparisonPlayer.isOld(), true);
            m_jpSetPieces.setText(PlayerAbility.getNameForSkill(m_clPlayer.getStandards()
            		+ m_clPlayer.getSubskill4Pos(PlayerSkill.SET_PIECES)) + "");
            m_jpSetPiecesChange.setGrafischeVeraenderungswert(m_clPlayer.getStandards()
            		- m_clComparisonPlayer.getStandards(),
            		m_clPlayer.getSubskill4Pos(PlayerSkill.SET_PIECES)
            		- m_clComparisonPlayer.getSubskill4Pos(PlayerSkill.SET_PIECES),
            		!m_clComparisonPlayer.isOld(), true);
            m_jpScoring.setText(PlayerAbility.getNameForSkill(m_clPlayer.getTorschuss()
            		+ m_clPlayer.getSubskill4Pos(PlayerSkill.SCORING)) + "");
            m_jpScoringChange.setGrafischeVeraenderungswert(m_clPlayer.getTorschuss()
            		- m_clComparisonPlayer.getTorschuss(),
            		m_clPlayer.getSubskill4Pos(PlayerSkill.SCORING)
            		- m_clComparisonPlayer.getSubskill4Pos(PlayerSkill.SCORING),
            		!m_clComparisonPlayer.isOld(), true);
            m_jpExperience.setText(PlayerAbility.getNameForSkill(m_clPlayer.getErfahrung()) + "");
            m_jpExperienceChange.setGrafischeVeraenderungswert(m_clPlayer.getErfahrung()
            		- m_clComparisonPlayer.getErfahrung(), !m_clComparisonPlayer.isOld(), true);
            m_jpLeadership.setText(PlayerAbility.getNameForSkill(m_clPlayer.getFuehrung()) + "");
            m_jpLoyalty.setText(PlayerAbility.getNameForSkill(m_clPlayer.getLoyalty()) + "");
            m_jpLoyaltyChange.setGrafischeVeraenderungswert(m_clPlayer.getLoyalty()
            		- m_clComparisonPlayer.getLoyalty(),
            		!m_clComparisonPlayer.isOld(), true);
            m_jpBestPosition.setText(SpielerPosition.getNameForPosition(m_clPlayer.getIdealPosition())
                                + " ("
                                + m_clPlayer.calcPosValue(m_clPlayer.getIdealPosition(), true)
                                + ")");
            for (int i = 0; i < playerPositionValues.length; i++) {
            	showWithCompare(playerPositionValues[i],playerPosition[i]);
			}
        }
        m_jpGoalsFriendly.setText(m_clPlayer.getToreFreund() + "");
        m_jpGoalsLeague.setText(m_clPlayer.getToreLiga() + "");
        m_jpGoalsCup.setText(m_clPlayer.getTorePokal() + "");
        m_jpGoalsTotal.setText(m_clPlayer.getToreGesamt() + "");
        m_jpHattricks.setText(m_clPlayer.getHattrick() + "");
        m_jpSpeciality.setText(PlayerSpeciality.toString(m_clPlayer.getSpezialitaet()));
        m_jpSpeciality.setIcon( ThemeManager.getIcon(HOIconName.SPECIAL[m_clPlayer.getSpezialitaet()]));
        m_jpAggressivity.setText(PlayerAggressiveness.toString(m_clPlayer.getAgressivitaet()));
        m_jpHonesty.setText(PlayerAgreeability.toString(m_clPlayer.getCharakter()));
        m_jpAgreeability.setText(PlayerHonesty.toString(m_clPlayer.getAnsehen()));

        String temp = "";
        final java.sql.Timestamp time = m_clPlayer.getTimestamp4FirstPlayerHRF();
        if (time != null) {
            temp += java.text.DateFormat.getDateInstance().format(time);
            final long timemillis = System.currentTimeMillis() - time.getTime();
            temp += (" (" + (int) (timemillis / 604800000) + " "
            + HOVerwaltung.instance().getLanguageString("Wochen")
            + ")");
        }
        m_jpInTeamSince.setText(temp);
        m_jtaNotes.setEditable(true);
        m_jtaNotes.setText(DBManager.instance().getSpielerNotiz(m_clPlayer.getSpielerID()));
        EPVData data = new EPVData(m_clPlayer);
        double price = HOVerwaltung.instance().getModel().getEPV().getPrice(data);
        final String epvtext = Helper.getNumberFormat(true, 0).format(price);
        m_jpMarketValue.setText( epvtext );
        m_jbStatistics.setEnabled(true);
        m_jbAnalysisTop.setEnabled(true);
        m_jbAnalysisBottom.setEnabled(true);
        m_jbOffsets.setEnabled(true);
        m_jbTrainingBlock.setEnabled(true);
    }

    private void showNormal(DoppelLabelEntry labelEntry,byte playerPosition){
    	labelEntry.getLinks().setText(Helper.getNumberFormat(false, ho.core.model.UserParameter.instance()
    			.anzahlNachkommastellen).format(m_clPlayer.calcPosValue(playerPosition, true)));
    	labelEntry.getRechts().clear();
    }

    private void showWithCompare(DoppelLabelEntry labelEntry,byte playerPosition){
    	labelEntry.getLinks().setText(Helper.getNumberFormat(false, ho.core.model.UserParameter.instance()
    			.anzahlNachkommastellen).format(m_clPlayer.calcPosValue(playerPosition,true)));
    	labelEntry.getRechts().setSpezialNumber(m_clPlayer.calcPosValue(playerPosition,true)
    			- m_clComparisonPlayer.calcPosValue(playerPosition,true),false);
    }
    /**
     * return first player who is find in db
     *
     * @param player template
     *
     * @return player
     */
    private Spieler getComparisonPlayerFirstHRF(Spieler vorlage) {
        return ho.core.db.DBManager.instance()
        	.getSpielerFirstHRF(vorlage.getSpielerID());
    }

    /**
     * search player to compare
     */
    private void findComparisonPlayer() {
        final int id = m_clPlayer.getSpielerID();
        for (int i = 0;
             (SpielerTrainingsVergleichsPanel.getVergleichsSpieler() != null)
             && (i < SpielerTrainingsVergleichsPanel.getVergleichsSpieler().size()); i++) {
            Spieler comparisonPlayer = (Spieler)SpielerTrainingsVergleichsPanel
            	.getVergleichsSpieler().get(i);
            if (comparisonPlayer.getSpielerID() == id) {
                // Found it
            	m_clComparisonPlayer = comparisonPlayer;
                return;
            }
        }
        if (SpielerTrainingsVergleichsPanel.isVergleichsMarkierung()) {
            m_clComparisonPlayer = getComparisonPlayerFirstHRF(m_clPlayer);
            return;
        }
        //Not found
        m_clComparisonPlayer = null;
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
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.0;
        constraints.weighty = 1.0;
        constraints.insets = new Insets(1, 2, 1, 1);
        panel.setLayout(layout);

        JLabel label;

        // Empty row
        label = new JLabel("  ");
        setPosition(constraints,3,0);
        constraints.weightx = 0.0;
        constraints.gridheight = 11;
        layout.setConstraints(label, constraints);
        panel.add(label);
        constraints.gridheight = 1;

        // ***** Block 1
        label = new JLabel(HOVerwaltung.instance().getLanguageString("Name"));
        initNormalLabel(0,0,constraints,layout,panel,label);
        initNormalField(1,0,constraints,layout,panel,m_jpName.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Alter"));
        initNormalLabel(0,1,constraints,layout,panel,label);
        initNormalField(1,1,constraints,layout,panel,m_jpAge.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Nationalitaet"));
        initNormalLabel(0,2,constraints,layout,panel,label);
        initNormalField(1,2,constraints,layout,panel,m_jpNationality.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Aufgestellt"));
        initNormalLabel(0,3,constraints,layout,panel,label);
        initNormalField(1,3,constraints,layout,panel,m_jpPositioned.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Bewertung"));
        initNormalLabel(0,4,constraints,layout,panel,label);
        initNormalField(1,4,constraints,layout,panel,m_jpRating.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("BestePosition"));
        initNormalLabel(0,5,constraints,layout,panel,label);
        initNormalField(1,5,constraints,layout,panel,m_jpBestPosition.getComponent(false));

        // ***** Block 2
        label = new JLabel(HOVerwaltung.instance().getLanguageString("Gruppe"));
        initNormalLabel(4,0,constraints,layout,panel,label);
        m_jcbSquad.setPreferredSize(COMPONENTENSIZECB);
        m_jcbSquad.setBackground(ThemeManager.getColor(HOColorName.TABLEENTRY_BG));
        m_jcbSquad.setRenderer(new SmilieListCellRenderer());
        m_jcbSquad.addItemListener(this);
        setPosition(constraints,5,0);
        constraints.weightx = 1.0;
        constraints.gridwidth = 2;
        layout.setConstraints(m_jcbSquad, constraints);
        panel.add(m_jcbSquad);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Info"));
        initNormalLabel(4,1,constraints,layout,panel,label);

        m_jcbInformation.setMaximumRowCount(10);
        m_jcbInformation.setPreferredSize(COMPONENTENSIZECB);
        m_jcbInformation.setBackground(m_jcbSquad.getBackground());
        m_jcbInformation.setRenderer(new SmilieListCellRenderer());
        m_jcbInformation.addItemListener(this);
        setPosition(constraints,5,1);
        constraints.weightx = 1.0;
        constraints.gridwidth = 2;

        layout.setConstraints(m_jcbInformation, constraints);
        panel.add(m_jcbInformation);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Status"));
        initNormalLabel(4,2,constraints,layout,panel,label);
        initNormalField(5,2,constraints,layout,panel,m_jpStatus.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Gehalt"));
        initNormalLabel(4,3,constraints,layout,panel,label);
        initNormalField(5,3,constraints,layout,panel,m_jpSalary.getComponent(false));

        label = new JLabel("TSI");
        initNormalLabel(4,4,constraints,layout,panel,label);
        initNormalField(5,4,constraints,layout,panel,m_jpTSI.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("BestePosition"));
        initNormalLabel(4,5,constraints,layout,panel,label);

        m_jcbUserBestPosition.setMaximumRowCount(20);
        m_jcbUserBestPosition.setPreferredSize(COMPONENTENSIZECB);
        m_jcbUserBestPosition.setBackground(m_jcbSquad.getBackground());
        m_jcbUserBestPosition.addItemListener(this);
        setPosition(constraints,5,5);
        constraints.weightx = 1.0;
        constraints.gridwidth = 2;
        layout.setConstraints(m_jcbUserBestPosition, constraints);
        panel.add(m_jcbUserBestPosition);

        //empty row
        label = new JLabel();
        setPosition(constraints,0,6);
        constraints.weightx = 0.0;
        constraints.gridwidth = 4;
        layout.setConstraints(label, constraints);
        panel.add(label);

        constraints.gridwidth = 1;
        label = new JLabel(HOVerwaltung.instance().getLanguageString("Erfahrung"));
        initNormalLabel(0,7,constraints,layout,panel,label);
        initYellowMainField(1,7,constraints,layout,panel,m_jpExperience.getComponent(false));
        initYellowChangesField(2,7,constraints,layout,panel,m_jpExperienceChange.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Form"));
        initNormalLabel(4,7,constraints,layout,panel,label);
        initYellowMainField(5,7,constraints,layout,panel,m_jpForm.getComponent(false));
        initYellowChangesField(6,7,constraints,layout,panel,m_jpFormChange.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("ls.player.skill.stamina"));
        initNormalLabel(0,8,constraints,layout,panel,label);
        initYellowMainField(1,8,constraints,layout,panel,m_jpStamina.getComponent(false));
        initYellowChangesField(2,8,constraints,layout,panel,m_jpStaminaChange.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("ls.player.skill.keeper"));
        initNormalLabel(4,8,constraints,layout,panel,label);
        initYellowMainField(5,8,constraints,layout,panel,m_jpKeeper.getComponent(false));
        initYellowChangesField(6,8,constraints,layout,panel,m_jpKeeperChange.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("ls.player.skill.playmaking"));
        initNormalLabel(0,9,constraints,layout,panel,label);
        initYellowMainField(1,9,constraints,layout,panel,m_jpPlaymaking.getComponent(false));
        initYellowChangesField(2,9,constraints,layout,panel,m_jpPlaymakingChange.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("ls.player.skill.passing"));
        initNormalLabel(4,9,constraints,layout,panel,label);
        initYellowMainField(5,9,constraints,layout,panel,m_jpPassing.getComponent(false));
        initYellowChangesField(6,9,constraints,layout,panel,m_jpPassingChange.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("ls.player.skill.winger"));
        initNormalLabel(0,10,constraints,layout,panel,label);
        initYellowMainField(1,10,constraints,layout,panel,m_jpWinger.getComponent(false));
        initYellowChangesField(2,10,constraints,layout,panel,m_jpWingerChange.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("ls.player.skill.defending"));
        initNormalLabel(4,10,constraints,layout,panel,label);
        initYellowMainField(5,10,constraints,layout,panel,m_jpDefending.getComponent(false));
        initYellowChangesField(6,10,constraints,layout,panel,m_jpDefendingChange.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("ls.player.skill.scoring"));
        initNormalLabel(0,11,constraints,layout,panel,label);
        initYellowMainField(1,11,constraints,layout,panel,m_jpScoring.getComponent(false));
        initYellowChangesField(2,11,constraints,layout,panel,m_jpScoringChange.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("ls.player.skill.setpieces"));
        initNormalLabel(4,11,constraints,layout,panel,label);
        initYellowMainField(5,11,constraints,layout,panel,m_jpSetPieces.getComponent(false));
        initYellowChangesField(6,11,constraints,layout,panel,m_jpSetPiecesChange.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Loyalty"));
        initNormalLabel(0,12,constraints,layout,panel,label);
        initYellowMainField(1,12,constraints,layout,panel,m_jpLoyalty.getComponent(false));
        initYellowChangesField(2,12,constraints,layout,panel,m_jpLoyaltyChange.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Motherclub"));
        initNormalLabel(4,12,constraints,layout,panel,label);
        initNormalField(5,12,constraints,layout,panel,m_jpMotherClub.getComponent(false));

        m_jtaNotes.addFocusListener(this);
        m_jtaNotes.setEditable(false);
        m_jtaNotes.setBackground(ColorLabelEntry.BG_STANDARD);

        final JPanel panel2 = new ImagePanel();
        panel2.setLayout(new BorderLayout());
        panel2.setBorder(javax.swing.BorderFactory.createTitledBorder(HOVerwaltung.instance().getLanguageString("Notizen")));
        panel2.add(new JScrollPane(m_jtaNotes), BorderLayout.CENTER);
        constraints.gridx = 0;
        constraints.weightx = 0.0;
        constraints.gridy = 13;
        constraints.gridheight = 7;
        constraints.gridwidth = 7;
        layout.setConstraints(panel2, constraints);
        panel.add(panel2);
        constraints.gridheight = 1;
        constraints.gridwidth = 1;

        //empty row
        label = new JLabel("  ");
        setPosition(constraints,7,0);
        constraints.weightx = 0.0;
        constraints.gridheight = 11;
        layout.setConstraints(label, constraints);
        panel.add(label);
        constraints.gridheight = 1;

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Fuehrung"));
        initNormalLabel(8,0,constraints,layout,panel,label);
        setPosition(constraints,9,0);
        constraints.weightx = 1.0;
        component = m_jpLeadership.getComponent(false);
        component.setPreferredSize(COMPONENTENSIZE3);
        layout.setConstraints(component, constraints);
        panel.add(component);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Spezialitaet"));
        initNormalLabel(8,1,constraints,layout,panel,label);
        initNormalField(9,1,constraints,layout,panel,m_jpSpeciality.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Aggressivitaet"));
        initNormalLabel(8,2,constraints,layout,panel,label);
        initNormalField(9,2,constraints,layout,panel,m_jpAggressivity.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Ansehen"));
        initNormalLabel(8,3,constraints,layout,panel,label);
        initNormalField(9,3,constraints,layout,panel,m_jpHonesty.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Charakter"));
        initNormalLabel(8,4,constraints,layout,panel,label);
        initNormalField(9,4,constraints,layout,panel,m_jpAgreeability.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("ImTeamSeit"));
        initNormalLabel(8,5,constraints,layout,panel,label);
        initNormalField(9,5,constraints,layout,panel,m_jpInTeamSince.getComponent(false));

        label = new JLabel();
        setPosition(constraints,11,6);
        constraints.weightx = 0.0;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;
        layout.setConstraints(label, constraints);
        panel.add(label);
        constraints.gridwidth = 1;
        constraints.gridheight = 1;

        label = new JLabel(HOVerwaltung.instance().getLanguageString("ToreFreund"));
        initNormalLabel(8,7,constraints,layout,panel,label);
        initNormalField(9,7,constraints,layout,panel,m_jpGoalsFriendly.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("ToreLiga"));
        initNormalLabel(8,8,constraints,layout,panel,label);
        initNormalField(9,8,constraints,layout,panel,m_jpGoalsLeague.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("TorePokal"));
        initNormalLabel(8,9,constraints,layout,panel,label);
        initNormalField(9,9,constraints,layout,panel,m_jpGoalsCup.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("ToreGesamt"));
        initNormalLabel(8,10,constraints,layout,panel,label);
        initNormalField(9,10,constraints,layout,panel,m_jpGoalsTotal.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Hattricks"));
        initNormalLabel(8,11,constraints,layout,panel,label);
        initNormalField(9,11,constraints,layout,panel,m_jpHattricks.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Marktwert"));
        initNormalLabel(8,12,constraints,layout,panel,label);
        initNormalField(9,12,constraints,layout,panel,m_jpMarketValue.getComponent(false));

        //Buttons
        final JPanel buttonpanel = new JPanel();
        buttonpanel.setOpaque(false);
        initButton(m_jbStatistics,HOVerwaltung.instance().getLanguageString("tt_Spieler_statistik"),buttonpanel);
        initButton(m_jbAnalysisTop,HOVerwaltung.instance().getLanguageString("tt_Spieler_analyse1"),buttonpanel);
        initButton(m_jbAnalysisBottom,HOVerwaltung.instance().getLanguageString("tt_Spieler_analyse2"),buttonpanel);
        initButton(m_jbOffsets,HOVerwaltung.instance().getLanguageString("tt_Spieler_offset"),buttonpanel);
        initButton(m_jbTrainingBlock,HOVerwaltung.instance().getLanguageString("TrainingBlock"),buttonpanel);

        setPosition(constraints,8,15);
        constraints.weightx = 1.0;
        constraints.gridheight = 3;
        constraints.gridwidth = 4;
        layout.setConstraints(buttonpanel, constraints);
        panel.add(buttonpanel);
        constraints.gridheight = 1;
        constraints.gridwidth = 1;

        // Empty row
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
        m_jpAge.clear();
        m_jpNationality.clear();
        m_jpPositioned.clear();
        m_jpStatus.clear();
        m_jcbSquad.setSelectedItem("");
        m_jcbInformation.setSelectedItem("");
        m_jpRating.clear();
        m_jpSalary.clear();
        m_jpTSI.clear();
        m_jpForm.clear();
        m_jpStamina.clear();
        m_jpKeeper.clear();
        m_jpDefending.clear();
        m_jpPlaymaking.clear();
        m_jpPassing.clear();
        m_jpWinger.clear();
        m_jpSetPieces.clear();
        m_jpScoring.clear();
        m_jpExperience.clear();
        m_jpLeadership.clear();
        m_jpFormChange.clear();
        m_jpStaminaChange.clear();
        m_jpKeeperChange.clear();
        m_jpDefendingChange.clear();
        m_jpPlaymakingChange.clear();
        m_jpPassingChange.clear();
        m_jpWingerChange.clear();
        m_jpSetPiecesChange.clear();
        m_jpScoringChange.clear();
        m_jpExperienceChange.clear();
        m_jpBestPosition.clear();
        m_jcbUserBestPosition.setSelectedItem("");
        m_jpLoyalty.clear();
        m_jpLoyaltyChange.clear();
        m_jpMotherClub.clear();
        for (int i = 0; i < playerPositionValues.length; i++) {
        	playerPositionValues[i].clear();
        }
        m_jpGoalsFriendly.clear();
        m_jpGoalsLeague.clear();
        m_jpGoalsCup.clear();
        m_jpGoalsTotal.clear();
        m_jpHattricks.clear();
        m_jpSpeciality.clear();
        m_jpAggressivity.clear();
        m_jpHonesty.clear();
        m_jpAgreeability.clear();
        m_jpInTeamSince.clear();
        m_jtaNotes.setText("");
        m_jtaNotes.setEditable(false);
        m_jbStatistics.setEnabled(false);
        m_jbAnalysisTop.setEnabled(false);
        m_jbAnalysisBottom.setEnabled(false);
        m_jbOffsets.setEnabled(false);
        m_jbTrainingBlock.setEnabled(false);
    }
}
