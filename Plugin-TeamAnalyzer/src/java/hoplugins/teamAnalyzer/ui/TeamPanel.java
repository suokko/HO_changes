// %924224980:hoplugins.teamAnalyzer.ui%
package hoplugins.teamAnalyzer.ui;

import hoplugins.Commons;

import hoplugins.commons.utils.PluginProperty;

import hoplugins.teamAnalyzer.SystemManager;
import hoplugins.teamAnalyzer.ui.lineup.FormationPanel;
import hoplugins.teamAnalyzer.vo.TeamLineup;
import hoplugins.teamAnalyzer.vo.UserTeamSpotLineup;

import plugins.ILineUp;
import plugins.ISpieler;

import java.awt.BorderLayout;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class TeamPanel extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    private FormationPanel lineupPanel = new FormationPanel();
    private PlayerPanel keeper = new PlayerPanel();
    private PlayerPanel leftAttacker = new PlayerPanel();
    private PlayerPanel leftBack = new PlayerPanel();
    private PlayerPanel leftCentral = new PlayerPanel();
    private PlayerPanel leftMidfielder = new PlayerPanel();
    private PlayerPanel leftWinger = new PlayerPanel();
    private PlayerPanel rightAttacker = new PlayerPanel();
    private PlayerPanel rightBack = new PlayerPanel();
    private PlayerPanel rightCentral = new PlayerPanel();
    private PlayerPanel rightMidfielder = new PlayerPanel();
    private PlayerPanel rightWinger = new PlayerPanel();

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TeamPanel object.
     */
    public TeamPanel() {
        jbInit();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public TeamLineupData getMyTeamLineupPanel() {
        return lineupPanel.getMyTeam();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public TeamLineupData getOpponentTeamLineupPanel() {
        return lineupPanel.getOpponentTeam();
    }

    /**
     * TODO Missing Method Documentation
     */
    public void jbInit() {
        JPanel grassPanel = Commons.getModel().getGUI().createGrassPanel();

        grassPanel.setLayout(new BorderLayout());
        grassPanel.add(lineupPanel, BorderLayout.CENTER);
        setMyTeam();
        setLayout(new BorderLayout());
        fillPanel(lineupPanel.getOpponentTeam().getKeeperPanel(), keeper);
        fillPanel(lineupPanel.getOpponentTeam().getLeftWingbackPanel(), leftBack);
        fillPanel(lineupPanel.getOpponentTeam().getLeftCentralDefenderPanel(), leftCentral);
        fillPanel(lineupPanel.getOpponentTeam().getRightCentralDefenderPanel(), rightCentral);
        fillPanel(lineupPanel.getOpponentTeam().getRightWingbackPanel(), rightBack);
        fillPanel(lineupPanel.getOpponentTeam().getLeftWingPanel(), leftWinger);
        fillPanel(lineupPanel.getOpponentTeam().getLeftMidfieldPanel(), leftMidfielder);
        fillPanel(lineupPanel.getOpponentTeam().getRightMidfieldPanel(), rightMidfielder);
        fillPanel(lineupPanel.getOpponentTeam().getRightWingPanel(), rightWinger);
        fillPanel(lineupPanel.getOpponentTeam().getLeftForwardPanel(), leftAttacker);
        fillPanel(lineupPanel.getOpponentTeam().getRightForwardPanel(), rightAttacker);

        JScrollPane scrollPane = new JScrollPane(grassPanel);

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * DOCUMENT ME!
     *
     * @param lineup
     * @param week TODO Missing Constructuor Parameter Documentation
     * @param season TODO Missing Constructuor Parameter Documentation
     */
    public void reload(TeamLineup lineup, int week, int season) {
        if (lineup != null) {
            lineupPanel.getOpponentTeam().setTeamName(SystemManager.getActiveTeamName() + " ("
                                                      + SystemManager.getActiveTeamId() + ")");
            keeper.reload(lineup.getSpotLineup(1), week, season);
            leftBack.reload(lineup.getSpotLineup(5), week, season);
            leftCentral.reload(lineup.getSpotLineup(4), week, season);
            rightCentral.reload(lineup.getSpotLineup(3), week, season);
            rightBack.reload(lineup.getSpotLineup(2), week, season);
            leftWinger.reload(lineup.getSpotLineup(9), week, season);
            leftMidfielder.reload(lineup.getSpotLineup(8), week, season);
            rightMidfielder.reload(lineup.getSpotLineup(7), week, season);
            rightWinger.reload(lineup.getSpotLineup(6), week, season);
            leftAttacker.reload(lineup.getSpotLineup(10), week, season);
            rightAttacker.reload(lineup.getSpotLineup(11), week, season);
            lineupPanel.getOpponentTeam().setLeftAttack(lineup.getRating().getLeftAttack());
            lineupPanel.getOpponentTeam().setLeftDefence(lineup.getRating().getLeftDefense());
            lineupPanel.getOpponentTeam().setRightAttack(lineup.getRating().getRightAttack());
            lineupPanel.getOpponentTeam().setRightDefence(lineup.getRating().getRightDefense());
            lineupPanel.getOpponentTeam().setMiddleAttack(lineup.getRating().getCentralAttack());
            lineupPanel.getOpponentTeam().setMiddleDefence(lineup.getRating().getCentralDefense());
            lineupPanel.getOpponentTeam().setMidfield(lineup.getRating().getMidfield());

            setMyTeam();
        } else {
            lineupPanel.getOpponentTeam().setTeamName(PluginProperty.getString("TeamPanel.TeamMessage")); //$NON-NLS-1$
            keeper.reload(null, 0, 0);
            leftBack.reload(null, 0, 0);
            leftCentral.reload(null, 0, 0);
            rightCentral.reload(null, 0, 0);
            rightBack.reload(null, 0, 0);
            leftWinger.reload(null, 0, 0);
            leftMidfielder.reload(null, 0, 0);
            rightMidfielder.reload(null, 0, 0);
            rightWinger.reload(null, 0, 0);
            leftAttacker.reload(null, 0, 0);
            rightAttacker.reload(null, 0, 0);
            lineupPanel.getOpponentTeam().setLeftAttack(0);
            lineupPanel.getOpponentTeam().setLeftDefence(0);
            lineupPanel.getOpponentTeam().setRightAttack(0);
            lineupPanel.getOpponentTeam().setRightDefence(0);
            lineupPanel.getOpponentTeam().setMiddleAttack(0);
            lineupPanel.getOpponentTeam().setMiddleDefence(0);
            lineupPanel.getOpponentTeam().setMidfield(0);
        }

        lineupPanel.reload(SystemManager.getConfig().isLineup(),
                           SystemManager.getConfig().isMixedLineup());
    }

    /**
     * TODO Missing Method Documentation
     */
    private void setMyTeam() {
        List list = new ArrayList();
        ILineUp lineup = Commons.getModel().getLineUP();

        for (int spot = 1; spot < 12; spot++) {
            ISpieler spieler = lineup.getPlayerByPositionID(spot);
            UserTeamPlayerPanel pp = new UserTeamPlayerPanel();

            if (spieler != null) {
                UserTeamSpotLineup spotLineup = new UserTeamSpotLineup();

                spotLineup.setAppearance(0);
                spotLineup.setName(spieler.getName());
                spotLineup.setPlayerId(spieler.getSpielerID());
                spotLineup.setSpecialEvent(spieler.getSpezialitaet());
                spotLineup.setTacticCode(lineup.getTactic4PositionID(spot));
                spotLineup.setPosition(lineup.getEffectivePos4PositionID(spot));
                spotLineup.setRating(spieler.calcPosValue(lineup.getEffectivePos4PositionID(spot),
                                                          true));
                spotLineup.setSpot(spot);
                spotLineup.setTactics(new ArrayList());
                pp.reload(spotLineup);
            } else {
                pp.reload(null);
            }

            list.add(pp);
        }

        lineupPanel.getMyTeam().setTeamName(Commons.getModel().getBasics().getTeamName() + " ("
                                            + Commons.getModel().getBasics().getTeamId() + ")");
        fillPanel(lineupPanel.getMyTeam().getKeeperPanel(), (PlayerPanel) list.get(0));
        fillPanel(lineupPanel.getMyTeam().getLeftWingbackPanel(), (PlayerPanel) list.get(4));
        fillPanel(lineupPanel.getMyTeam().getLeftCentralDefenderPanel(), (PlayerPanel) list.get(3));
        fillPanel(lineupPanel.getMyTeam().getRightCentralDefenderPanel(), (PlayerPanel) list.get(2));
        fillPanel(lineupPanel.getMyTeam().getRightWingbackPanel(), (PlayerPanel) list.get(1));
        fillPanel(lineupPanel.getMyTeam().getLeftWingPanel(), (PlayerPanel) list.get(8));
        fillPanel(lineupPanel.getMyTeam().getLeftMidfieldPanel(), (PlayerPanel) list.get(7));
        fillPanel(lineupPanel.getMyTeam().getRightMidfieldPanel(), (PlayerPanel) list.get(6));
        fillPanel(lineupPanel.getMyTeam().getRightWingPanel(), (PlayerPanel) list.get(5));
        fillPanel(lineupPanel.getMyTeam().getLeftForwardPanel(), (PlayerPanel) list.get(9));
        fillPanel(lineupPanel.getMyTeam().getRightForwardPanel(), (PlayerPanel) list.get(10));
        lineupPanel.getMyTeam().setLeftAttack(convertRating(lineup.getLeftAttackRating()));
        lineupPanel.getMyTeam().setLeftDefence(convertRating(lineup.getLeftDefenseRating()));
        lineupPanel.getMyTeam().setRightAttack(convertRating(lineup.getRightAttackRating()));
        lineupPanel.getMyTeam().setRightDefence(convertRating(lineup.getRightDefenseRating()));
        lineupPanel.getMyTeam().setMiddleAttack(convertRating(lineup.getCentralAttackRating()));
        lineupPanel.getMyTeam().setMiddleDefence(convertRating(lineup.getCentralDefenseRating()));
        lineupPanel.getMyTeam().setMidfield(convertRating(lineup.getMidfieldRating()));
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param rating TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private int convertRating(double rating) {
        return Commons.getModel().getLineUP().getIntValue4Rating(rating);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param panel TODO Missing Method Parameter Documentation
     * @param playerPanel TODO Missing Method Parameter Documentation
     */
    private void fillPanel(JPanel panel, JPanel playerPanel) {
        panel.removeAll();
        panel.add(playerPanel);
    }
}
