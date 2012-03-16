// %487704913:hoplugins.toTW%
package ho.module.teamOfTheWeek;

import ho.core.db.DBManager;
import ho.core.db.JDBCAdapter;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.model.HOVerwaltung;
import ho.core.util.HelperWrapper;
import ho.module.series.Spielplan;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import plugins.IPaarung;
import plugins.ISpielerPosition;
import plugins.LineupPanel;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class TeamOfTheWeekPanel extends JPanel implements ChangeListener,ActionListener {

	private static final long serialVersionUID = 7990572479100871307L;

    //~ Instance fields ----------------------------------------------------------------------------

	private MyLineupPanel bestOfWeek = new MyLineupPanel(LineupPanel.LINEUP_NORMAL_SEQUENCE);
    private MyLineupPanel bestOfYear = new MyLineupPanel(LineupPanel.LINEUP_NORMAL_SEQUENCE);
    private MyLineupPanel worstOfWeek = new MyLineupPanel(LineupPanel.LINEUP_NORMAL_SEQUENCE);
    private MyLineupPanel worstOfYear = new MyLineupPanel(LineupPanel.LINEUP_NORMAL_SEQUENCE);

    private JComboBox seasonCombo = new JComboBox();
    JSpinner weekSpinner = new JSpinner();
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new PotwUI object.
     */
    public TeamOfTheWeekPanel() {
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
    	 int week = ((Number)weekSpinner.getValue()).intValue();
        MatchLineupPlayer[] sl = calcBestLineup(week, true);
        fillLineup(bestOfWeek, sl, false);
        sl = calcBestLineup(week,  false);
        fillLineup(worstOfWeek, sl, false);

        if (isSeason) {
            sl = calcBestLineup(-1,  true);
            fillLineup(bestOfYear, sl, false);
            sl = calcBestLineup(-1,  false);
            fillLineup(worstOfYear, sl, false);
        }
    }

    private MatchLineupPlayer[] calcBestLineup(int week, boolean best) {
    	 Spielplan plan = (Spielplan)seasonCombo.getSelectedItem();
        Map<String, MatchLineupPlayer> spieler = getPlayers(week,plan, best);
        MatchLineupPlayer[] mlp = new MatchLineupPlayer[11];

        for (int i = 0; i < 11; i++) {
            mlp[i] = spieler.get("" + (i + 1));
        }

        return mlp;
    }

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

        String posi = HelperWrapper.instance().getNameForPosition((byte) mlp.getPositionCode());

        panel.removeAll();

        JLabel spielername = createLabel(mlp.getNname(), Color.black, 1);
        JLabel teamname = createLabel(mlp.getTeamName(), Color.black, 1);
        JLabel position = createLabel(posi, Color.black, 0);
        position.setOpaque(false);

        JPanel spielerdetails = new JPanel();
        spielerdetails.setBorder(BorderFactory.createEtchedBorder());
        spielerdetails.setBackground(Color.white);
        spielerdetails.setLayout(new BorderLayout());
        spielerdetails.add(spielername, BorderLayout.NORTH);
        spielerdetails.add(teamname, BorderLayout.SOUTH);
        JPanel sternzahl = (JPanel) new ho.core.gui.comp.entry.RatingTableEntry(toInt(mlp.getRating()), true).getComponent(false);
        sternzahl.setOpaque(false);
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



        JPanel mainPanel = new ImagePanel();
        mainPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        mainPanel.add(leftPanel, BorderLayout.CENTER);

        panel.add(mainPanel);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param seasonCombo TODO Missing Method Parameter Documentation
     */
    private void fillSeasonCombo(JComboBox seasonCombo) {
    	 final Spielplan[] spielplaene = DBManager.instance().getAllSpielplaene(true);
        for (int i = 0; i < spielplaene.length; i++) {
        	seasonCombo.addItem(spielplaene[i]);
		}
    }

    /**
     * TODO Missing Method Documentation
     */
    private void jbInit() {
       

        JPanel m_jpPanel = new ImagePanel();
        m_jpPanel.setOpaque(false);
        fillSeasonCombo(seasonCombo);
        seasonCombo.addActionListener(this);
       
        int week = 14;//TotW.getWeek();
        SpinnerNumberModel cSNM = new SpinnerNumberModel(week, 1, week, 1);
        weekSpinner.setModel(cSNM);
        weekSpinner.addChangeListener(this);
        weekSpinner.setPreferredSize(new Dimension(60, 22));
        weekSpinner.setFocusable(false);

        
        

        JLabel jl = new JLabel(HOVerwaltung.instance().getLanguageString("Spieltag"));
        //JLabel sl = new JLabel(HOVerwaltung.instance().getLanguageString("Season"));
        JPanel north = new ImagePanel();
        jl.setForeground(Color.BLACK);
        jl.setLabelFor(weekSpinner);
        //sl.setForeground(Color.BLACK);
        //sl.setLabelFor(seasonCombo);
        reloadData(true);
        //north.setPreferredSize(new Dimension(120, 30));
        north.add(jl);
        north.add(weekSpinner);
        //north.add(sl);
        north.add(seasonCombo);
        north.setOpaque(true);
        m_jpPanel.setLayout(new BorderLayout());
        m_jpPanel.add(north, BorderLayout.NORTH);

        JTabbedPane tab = new JTabbedPane();
        tab.addTab(HOVerwaltung.instance().getLanguageString("bestOfWeek"), bestOfWeek);
        tab.addTab(HOVerwaltung.instance().getLanguageString("worstOfWeek"), worstOfWeek);
        tab.addTab(HOVerwaltung.instance().getLanguageString("bestOfSeason"), bestOfYear);
        tab.addTab(HOVerwaltung.instance().getLanguageString("worstOfSeason"), worstOfYear);
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

	public void stateChanged(ChangeEvent e) {
		reloadData(false);
        updateUI();
	}

	public void actionPerformed(ActionEvent e) {
		 Spielplan plan = (Spielplan)seasonCombo.getSelectedItem();
        int week = HOVerwaltung.instance().getModel().getBasics().getSpieltag() - 1;

        if (week < 1) {
            week = 1;
        }
        if (HOVerwaltung.instance().getModel().getBasics().getSeason() != plan.getSaison()) 
        	week = 14;
        

        weekSpinner.setModel(new SpinnerNumberModel(1, 1, week, 1));
        reloadData(true);
        updateUI();
		
	}
	
    /**
     * Missing Method Documentation
     *
     * @param week Missing Constructuor Parameter Documentation
     * @param season Missing Constructuor Parameter Documentation
     * @param isBest Missing Constructuor Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    private Map<String,MatchLineupPlayer> getPlayers(int week, Spielplan plan, boolean isBest) {
        JDBCAdapter db = DBManager.instance().getAdapter();
        Vector<IPaarung> matchIDs =plan.getPaarungenBySpieltag(week);
        // TODO For match of year attention of doubles
        Map<String,MatchLineupPlayer> spieler = new HashMap<String,MatchLineupPlayer>();
        List<MatchLineupPlayer> players = getPlayetAt(db, matchIDs, ISpielerPosition.KEEPER, 1, isBest);
        spieler.put("1", players.get(0));
        players = getPlayetAt(db, matchIDs, ISpielerPosition.BACK, 2, isBest);
        spieler.put("2", players.get(0));
        spieler.put("5", players.get(1));
        players = getPlayetAt(db, matchIDs, ISpielerPosition.CENTRAL_DEFENDER, 2, isBest);
        spieler.put("3", players.get(0));
        spieler.put("4", players.get(1));
        players = getPlayetAt(db, matchIDs, ISpielerPosition.WINGER, 2, isBest);
        spieler.put("6", players.get(0));
        spieler.put("9", players.get(1));
        players = getPlayetAt(db, matchIDs, ISpielerPosition.MIDFIELDER, 2, isBest);
        spieler.put("7", players.get(0));
        spieler.put("8", players.get(1));
        players = getPlayetAt(db, matchIDs, ISpielerPosition.FORWARD, 2, isBest);
        spieler.put("10", players.get(0));
        spieler.put("11", players.get(1));
        return spieler;
    }

    /**
     * Missing Method Documentation
     *
     * @param db Missing Method Parameter Documentation
     * @param matchIDs Missing Method Parameter Documentation
     * @param position Missing Method Parameter Documentation
     * @param number Missing Method Parameter Documentation
     * @param isBest Missing Constructuor Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    private List<MatchLineupPlayer> getPlayetAt(JDBCAdapter db, Vector<IPaarung> matchIDs, int position, int number,
                                    boolean isBest) {
        ResultSet rs;
        String posClase = "";

		switch (position) {
			case ISpielerPosition.KEEPER: {
				posClase += " FIELDPOS=" + ISpielerPosition.keeper + " ";
				break;
			}

			case ISpielerPosition.CENTRAL_DEFENDER: {
				posClase += " (FIELDPOS=" + ISpielerPosition.leftCentralDefender + " OR FIELDPOS=" + 
						ISpielerPosition.middleCentralDefender + " OR FIELDPOS=" + ISpielerPosition.rightCentralDefender + ") ";
				break;
			}

			case ISpielerPosition.BACK: {
				posClase += " (FIELDPOS=" + ISpielerPosition.leftBack + " OR FIELDPOS=" + ISpielerPosition.rightBack + ") ";
				break;
			}

			case ISpielerPosition.WINGER: {
				posClase += " (FIELDPOS=" + ISpielerPosition.leftWinger + " OR FIELDPOS=" + ISpielerPosition.rightWinger + ") ";
				break;
			}

			case ISpielerPosition.MIDFIELDER: {
				posClase += " (FIELDPOS=" + ISpielerPosition.leftInnerMidfield + " OR FIELDPOS=" + 
						ISpielerPosition.centralInnerMidfield + " OR FIELDPOS=" + ISpielerPosition.rightInnerMidfield + ") ";
				break;
			}

			case ISpielerPosition.FORWARD: {
				posClase += " (FIELDPOS=" + ISpielerPosition.leftForward + " OR FIELDPOS=" + 
						ISpielerPosition.centralForward + " OR FIELDPOS=" + ISpielerPosition.rightForward + ") ";
				break;
			}
		}

		String matchClause = "";
        for (int i = 0; i < matchIDs.size(); i++) {
            if (matchClause.length() > 1) {
                matchClause += " OR ";
            }
			matchClause += " MATCHID=" + matchIDs.get(i).getMatchId();
        }

		String sql = "SELECT DISTINCT SPIELERID, NAME, RATING, HOPOSCODE, TEAMID FROM MATCHLINEUPPLAYER WHERE "+posClase;
		if (matchClause.length()>1) {
			sql += " AND (" + matchClause + ") ";
		}
        sql += "ORDER BY RATING ";

        if (isBest) {
            sql += " DESC";
        } else {
            sql += " ASC";
        }

        rs = db.executeQuery(sql);

        List<MatchLineupPlayer> ret = new ArrayList<MatchLineupPlayer>();

        for (int i = 0; i < number; i++) {
            ret.add(new MatchLineupPlayer(rs, matchIDs));
        }

        return ret;
    }
}
