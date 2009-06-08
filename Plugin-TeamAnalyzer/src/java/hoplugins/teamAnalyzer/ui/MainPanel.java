// %3876182235:hoplugins.teamAnalyzer.ui%
package hoplugins.teamAnalyzer.ui;

import hoplugins.Commons;
import hoplugins.teamAnalyzer.vo.TeamLineup;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class MainPanel extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    private RosterPanel rosterPanel = new RosterPanel();
    private TeamPanel teamPanel = new TeamPanel();

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TeamPanel object.
     */
    public MainPanel() {
        jbInit();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public TeamLineupData getMyTeamLineupPanel() {
        return teamPanel.getMyTeamLineupPanel();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public TeamLineupData getOpponentTeamLineupPanel() {
        return teamPanel.getOpponentTeamLineupPanel();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public RosterPanel getRosterPanel() {
        return rosterPanel;
    }

    /**
     * TODO Missing Method Documentation
     */
    public void jbInit() {
        JTabbedPane pane = new JTabbedPane();

        pane.setOpaque(false);
        setOpaque(false);
        setLayout(new BorderLayout());
        pane.add(Commons.getModel().getLanguageString("Aufstellung"), teamPanel);
//        pane.add(PluginProperty.getString("Players"), rosterPanel);
        add(pane, BorderLayout.CENTER);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param lineup TODO Missing Method Parameter Documentation
     * @param week TODO Missing Constructuor Parameter Documentation
     * @param season TODO Missing Constructuor Parameter Documentation
     */
    public void reload(TeamLineup lineup, int week, int season) {
//        rosterPanel.reload();
        teamPanel.reload(lineup, week, season);
    }
}
