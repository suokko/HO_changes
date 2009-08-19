// %487704913:hoplugins.toTW%
package hoplugins.toTW;

import hoplugins.Commons;
import hoplugins.TotW;

import hoplugins.toTW.dao.TeamColorDAO;
import hoplugins.toTW.listener.LigaActionListener;
import hoplugins.toTW.listener.WeekChangeListener;
import hoplugins.toTW.vo.LigaItem;
import hoplugins.toTW.vo.MatchLineupPlayer;
import hoplugins.toTW.vo.TeamDetail;

import plugins.LineupPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class PotwUI extends JPanel {

	private static final long serialVersionUID = 7990572479100871307L;

    //~ Instance fields ----------------------------------------------------------------------------

	private MyLineupPanel bestOfWeek = new MyLineupPanel(LineupPanel.LINEUP_NORMAL_SEQUENCE);
    private MyLineupPanel bestOfYear = new MyLineupPanel(LineupPanel.LINEUP_NORMAL_SEQUENCE);
    private MyLineupPanel worstOfWeek = new MyLineupPanel(LineupPanel.LINEUP_NORMAL_SEQUENCE);
    private MyLineupPanel worstOfYear = new MyLineupPanel(LineupPanel.LINEUP_NORMAL_SEQUENCE);

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new PotwUI object.
     */
    public PotwUI() {
        super();
        jbInit();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param isSeason TODO Missing Constructuor Parameter Documentation
     */
    public void reloadData(boolean isSeason) {
        MatchLineupPlayer[] sl = LineupCalculator.calcBestLineup(TotW.getWeek(), TotW.getSeason(),
                                                                 true);
        fillLineup(bestOfWeek, sl, false);
        sl = LineupCalculator.calcBestLineup(TotW.getWeek(), TotW.getSeason(), false);
        fillLineup(worstOfWeek, sl, false);

        if (isSeason) {
            sl = LineupCalculator.calcBestLineup(-1, TotW.getSeason(), true);
            fillLineup(bestOfYear, sl, false);
            sl = LineupCalculator.calcBestLineup(-1, TotW.getSeason(), false);
            fillLineup(worstOfYear, sl, false);
        }
    }

    //    /**
    //     * TODO Missing Method Documentation
    //     *
    //     * @param m TODO Missing Method Parameter Documentation
    //     *
    //     * @return TODO Missing Return Method Documentation
    //     */
    //    private String getPositionDesc(int m) {
    //        String posi;
    //
    //        if (m == 0) {
    //            posi = "Keeper";
    //        } else if (m == 1) {
    //            posi = "Central Defender";
    //        } else if (m == 2) {
    //            posi = "Central Defender - Offensive";
    //        } else if (m == 3) {
    //            posi = "Central Defender - To Wing";
    //        } else if (m == 4) {
    //            posi = "Wingback";
    //        } else if (m == 5) {
    //            posi = "Wingback - Offensive";
    //        } else if (m == 6) {
    //            posi = "Wingback - To Mid";
    //        } else if (m == 7) {
    //            posi = "Wingback - Defensive";
    //        } else if (m == 8) {
    //            posi = "Inner Midfield";
    //        } else if (m == 9) {
    //            posi = "Inner Midfield - Offensive";
    //        } else if (m == 10) {
    //            posi = "Inner Midfield - Defensive";
    //        } else if (m == 11) {
    //            posi = "Inner Midfield - To Wing";
    //        } else if (m == 12) {
    //            posi = "Wing";
    //        } else if (m == 13) {
    //            posi = "Wing - Offensive";
    //        } else if (m == 14) {
    //            posi = "Wing - Defensive";
    //        } else if (m == 15) {
    //            posi = "Wing - To Mid";
    //        } else if (m == 16) {
    //            posi = "Striker";
    //        } else if (m == 17) {
    //            posi = "Striker - Defensive";
    //        } else {
    //            posi = "Unknown";
    //        }
    //
    //        return posi;
    //    }

    /**
     * TODO Missing Method Documentation
     *
     * @param text TODO Missing Method Parameter Documentation
     * @param farbe TODO Missing Method Parameter Documentation
     * @param Bordertype TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private JLabel createLabel(String text, Color farbe, int Bordertype) {
        JLabel bla = new JLabel(text);
        bla.setHorizontalAlignment(0);
        bla.setForeground(farbe);
        bla.setBorder(BorderFactory.createEtchedBorder(Bordertype));
        return bla;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param lineupPanel TODO Missing Constructuor Parameter Documentation
     * @param sl TODO Missing Method Parameter Documentation
     * @param noStars TODO Missing Method Parameter Documentation
     */
    private void fillLineup(MyLineupPanel lineupPanel, MatchLineupPlayer[] sl, boolean noStars) {
        if (!noStars) {
            fillPanel(lineupPanel.getLineup().getKeeperPanel(), sl[0]);
            fillPanel(lineupPanel.getLineup().getLeftWingbackPanel(), sl[1]);
            fillPanel(lineupPanel.getLineup().getLeftCentralDefenderPanel(), sl[2]);
            fillPanel(lineupPanel.getLineup().getRightCentralDefenderPanel(), sl[3]);
            fillPanel(lineupPanel.getLineup().getRightWingbackPanel(), sl[4]);
            fillPanel(lineupPanel.getLineup().getLeftWingPanel(), sl[5]);
            fillPanel(lineupPanel.getLineup().getLeftMidfieldPanel(), sl[6]);
            fillPanel(lineupPanel.getLineup().getRightMidfieldPanel(), sl[7]);
            fillPanel(lineupPanel.getLineup().getRightWingPanel(), sl[8]);
            fillPanel(lineupPanel.getLineup().getLeftForwardPanel(), sl[9]);
            fillPanel(lineupPanel.getLineup().getRightForwardPanel(), sl[10]);
        } else {
            fillPanel(lineupPanel.getLineup().getKeeperPanel(), sl[0], true);
            fillPanel(lineupPanel.getLineup().getLeftWingbackPanel(), sl[1], true);
            fillPanel(lineupPanel.getLineup().getLeftCentralDefenderPanel(), sl[2], true);
            fillPanel(lineupPanel.getLineup().getRightCentralDefenderPanel(), sl[3], true);
            fillPanel(lineupPanel.getLineup().getRightWingbackPanel(), sl[4], true);
            fillPanel(lineupPanel.getLineup().getLeftWingPanel(), sl[5], true);
            fillPanel(lineupPanel.getLineup().getLeftMidfieldPanel(), sl[6], true);
            fillPanel(lineupPanel.getLineup().getRightMidfieldPanel(), sl[7], true);
            fillPanel(lineupPanel.getLineup().getRightWingPanel(), sl[8], true);
            fillPanel(lineupPanel.getLineup().getLeftForwardPanel(), sl[9], true);
            fillPanel(lineupPanel.getLineup().getRightForwardPanel(), sl[10], true);
        }

        lineupPanel.getLineup().setTeamName("");
        lineupPanel.getLineup().updateUI();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param panel TODO Missing Method Parameter Documentation
     * @param mlp TODO Missing Method Parameter Documentation
     */
    private void fillPanel(JPanel panel, MatchLineupPlayer mlp) {
        fillPanel(panel, mlp, false);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param panel TODO Missing Method Parameter Documentation
     * @param mlp TODO Missing Method Parameter Documentation
     * @param noStars TODO Missing Method Parameter Documentation
     */
    private void fillPanel(JPanel panel, MatchLineupPlayer mlp, boolean noStars) {
        panel.setOpaque(false);

        String posi = Commons.getModel().getHelper().getNameForPosition((byte) mlp.getPositionCode());

        panel.removeAll();

        TeamDetail teamDetail = TeamColorDAO.load(mlp.getTeamID());
        JLabel spielername = createLabel(mlp.getNname(), Color.black, 1);
        JLabel teamname = createLabel(teamDetail.getName(), Color.black, 1);
        JLabel position = createLabel(posi, Color.black, 0);
        position.setOpaque(false);

        JPanel spielerdetails = new JPanel();
        spielerdetails.setBorder(BorderFactory.createEtchedBorder());
        spielerdetails.setBackground(Color.white);
        spielerdetails.setLayout(new BorderLayout());
        spielerdetails.add(spielername, BorderLayout.NORTH);
        spielerdetails.add(teamname, BorderLayout.SOUTH);

        JPanel sternzahl = TotW.getModel().getGUI().createStarPanel(toInt(mlp.getRating()), true);
        sternzahl.setOpaque(true);
        sternzahl.setBorder(BorderFactory.createEtchedBorder());

        JPanel leftPanel = new JPanel();
        leftPanel.setOpaque(false);
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        leftPanel.setPreferredSize(new Dimension(180, 80));
        leftPanel.add(position, BorderLayout.NORTH);
        leftPanel.add(spielerdetails, BorderLayout.CENTER);

        if (!noStars) {
            leftPanel.add(sternzahl, BorderLayout.SOUTH);
        }

        String code = "_03";

        if (teamDetail.getShirt().charAt(0) == 'H') {
            code = "_02";
        }

        if (teamDetail.getShirt().charAt(0) == 'I') {
            code = "_06";
        }

        if (teamDetail.getShirt().charAt(0) == 'J') {
            code = "_16";
        }

        if (teamDetail.getShirt().charAt(0) == 'K') {
            code = "_19";
        }

        if (teamDetail.getShirt().charAt(0) == 'H') {
            code = "_02";
        }

        String shirt = "Shirt_" + teamDetail.getShirt().charAt(0) + code
                       + teamDetail.getShirt().charAt(1) + teamDetail.getShirt().charAt(2);

        if (teamDetail.getShirt().startsWith("N")) {
            shirt = "dressShirt" + teamDetail.getShirt().charAt(1)
                    + teamDetail.getShirt().charAt(2);
        }

        JPanel jp = new JPanel(new BorderLayout());

        jp.add(new JLabel(new ImageIcon("hoplugins/toTW/resources/" + shirt + ".gif")),
               BorderLayout.NORTH);
        jp.add(new JLabel(new ImageIcon("hoplugins/toTW/resources/dressPants"
                                        + teamDetail.getPants() + ".gif")), BorderLayout.WEST);
        jp.add(new JLabel(new ImageIcon("hoplugins/toTW/resources/dressSocks"
                                        + teamDetail.getSocks() + ".gif")), BorderLayout.SOUTH);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(jp, BorderLayout.NORTH);

        JPanel mainPanel = TotW.getModel().getGUI().createImagePanel();
        mainPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        mainPanel.add(rightPanel, BorderLayout.WEST);
        mainPanel.add(leftPanel, BorderLayout.CENTER);

        panel.add(mainPanel);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param seasonCombo TODO Missing Method Parameter Documentation
     */
    private void fillSeasonCombo(JComboBox seasonCombo) {
        Iterator<LigaItem> it = DBManager.getLeagues().iterator();

        while (it.hasNext()) {
            seasonCombo.addItem(it.next());
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    private void jbInit() {
        int week = TotW.getWeek();

        JPanel m_jpPanel = TotW.getModel().getGUI().createImagePanel();
        m_jpPanel.setOpaque(false);

        JSpinner weekSpinner = new JSpinner();
        SpinnerNumberModel cSNM = new SpinnerNumberModel(week, 1, week, 1);
        weekSpinner.setModel(cSNM);
        weekSpinner.addChangeListener(new WeekChangeListener());
        weekSpinner.setPreferredSize(new Dimension(40, 20));
        weekSpinner.setFocusable(false);

        JComboBox seasonCombo = new JComboBox();
        fillSeasonCombo(seasonCombo);
        seasonCombo.addActionListener(new LigaActionListener(weekSpinner));

        JLabel jl = new JLabel("Matchweek: ");
        JLabel sl = new JLabel("Season: ");
        JPanel north = TotW.getModel().getGUI().createImagePanel();
        jl.setForeground(Color.BLACK);
        jl.setLabelFor(weekSpinner);
        sl.setForeground(Color.BLACK);
        sl.setLabelFor(seasonCombo);
        reloadData(true);
        north.setPreferredSize(new Dimension(120, 30));
        north.add(jl);
        north.add(weekSpinner);
        north.add(sl);
        north.add(seasonCombo);
        north.setOpaque(true);
        m_jpPanel.setLayout(new BorderLayout());
        m_jpPanel.add(north, BorderLayout.NORTH);

        JTabbedPane tab = new JTabbedPane();
        tab.addTab("Best of the Week", bestOfWeek);
        tab.addTab("Worst of the Week", worstOfWeek);
        tab.addTab("Best of the Year", bestOfYear);
        tab.addTab("Worst of the Year", worstOfYear);
        m_jpPanel.add(tab, BorderLayout.CENTER);
        setLayout(new BorderLayout());
        add(m_jpPanel, BorderLayout.CENTER);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private int toInt(float i) {
        return (new Float(i * 2.0F)).intValue();
    }
}
