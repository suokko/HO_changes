package ho.module.teamAnalyzer.ui;

import ho.module.teamAnalyzer.vo.TeamLineup;
import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class MainPanel extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -6374854816698657464L;
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
       // JTabbedPane pane = new JTabbedPane();

        //pane.setOpaque(false);
        setOpaque(false);
        setLayout(new BorderLayout());
        //pane.add(HOVerwaltung.instance().getLanguageString("Aufstellung"), teamPanel);
//        pane.add(PluginProperty.getString("Players"), rosterPanel);
        add(teamPanel, BorderLayout.CENTER);
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
