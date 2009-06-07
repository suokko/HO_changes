// %3164997154:hoplugins.teamplanner.ui%
package hoplugins.teamplanner.ui;

import hoplugins.TeamPlanner;

import hoplugins.teamplanner.ui.tabs.players.CBItem;
import hoplugins.teamplanner.vo.YearSetting;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * DOCUMENT ME!
 *
 * @author <a href="mailto:kenmooda@users.sourceforge.net">Tommi Rautava</a>
 */
public class YearSettingPane extends JPanel implements ActionListener {
    //~ Instance fields ----------------------------------------------------------------------------

    private JComboBox cupRound = new JComboBox();
    private JComboBox leaguePosition = new JComboBox();
    private JComboBox seasonEvent = new JComboBox();
    private JComboBox seasonEventType = new JComboBox();
    private JComboBox seasonPoint = new JComboBox();
    private YearSetting setting = null;
    private int season = 0;
    private int serie = 0;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new FutureSettingsPane object.
     *
     * @param season Missing Constructuor Parameter Documentation
     */
    public YearSettingPane(int season) {
        super();
        this.season = season;
        jbInit(season);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     *
     * @param newSerie Missing Method Parameter Documentation
     */
    public void setSerie(int newSerie) {
        serie = newSerie;
        setting = null;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public YearSetting getSetting() {
        if (setting == null) {
            setting = new YearSetting();
            setting.setSeason(season);
            setting.setCupRound(((CBItem) cupRound.getSelectedItem()).getId());
            setting.setSeasonEvent(((CBItem) seasonEvent.getSelectedItem()).getId());
            setting.setSeasonEventType(((CBItem) seasonEventType.getSelectedItem()).getId());
            setting.setSeasonResult(((CBItem) seasonPoint.getSelectedItem()).getId());
            setting.setLeaguePosition(((CBItem) leaguePosition.getSelectedItem()).getId());
            setting.setSerie(serie);
        }

        return setting;
    }

    /**
     * Missing Method Documentation
     *
     * @param e Missing Method Parameter Documentation
     */
    public void actionPerformed(ActionEvent e) {
        setting = null;

        // Remove it		
        removeListeners();

        if (e.getSource() == seasonEvent) {
            setSeasonEventType();
            calculateSeasonResult();
            calculateLeaguePosition();
        }

        if (e.getSource() == seasonEventType) {
            calculateSeasonResult();
            calculateLeaguePosition();
        }

        if (e.getSource() == seasonPoint) {
            calculateLeaguePosition();
        }

        // Add listeners
        initializeListeners();

        onChange();
    }

    /**
     * Missing Method Documentation
     */
    public void loadInputData() {
        //seasonEvent.setSelectedIndex(1);
        //seasonPoint.setSelectedIndex(4);
        //leaguePosition.setSelectedIndex(4);
    }

    /**
     * Missing Method Documentation
     */
    protected void onChange() {
        TeamPlanner.getFuturePane().refreshTable();
        TeamPlanner.getFansPane().refreshTable();
        TeamPlanner.getStadiumPane().refreshTable();
        TeamPlanner.getExtrasPane().refreshTable();
        TeamPlanner.getRecapPane().refreshTable();
    }

    /**
     * Missing Method Documentation
     */
    private void setSeasonEventType() {
        seasonEventType.removeAllItems();

        int se = ((CBItem) seasonEvent.getSelectedItem()).getId();

        switch (se) {
            case 1: {
                seasonEventType.addItem(new CBItem("DIRECT PROMOTION", 2));
                seasonEventType.addItem(new CBItem("PROMOTION QUALIFIER", 1));
                seasonEventType.setSelectedIndex(0);
                break;
            }

            case 0: {
                seasonEventType.addItem(new CBItem("PROMOTION QUALIFIER", 1));
                seasonEventType.addItem(new CBItem("NOTHING", 0));

                if (serie < 6) {
                    seasonEventType.addItem(new CBItem("RELEGATION QUALIFIER", -1));
                }

                seasonEventType.setSelectedIndex(1);
                break;
            }

            case -1: {
                seasonEventType.addItem(new CBItem("DIRECT RELEGATION", -2));

                if (serie < 6) {
                    seasonEventType.addItem(new CBItem("RELEGATION QUALIFIER", -1));
                }

                seasonEventType.setSelectedIndex(0);
                break;
            }
        }
    }

    /**
     * Missing Method Documentation
     */
    private void calculateLeaguePosition() {
        int eventType = ((CBItem) seasonEvent.getSelectedItem()).getId();
        int points = ((CBItem) seasonPoint.getSelectedItem()).getId();

        // Direct Promotion
        if (eventType == 2) {
            leaguePosition.setSelectedIndex(0);
        }

        // Promotion Qualifier
        if (eventType == 1) {
            leaguePosition.setSelectedIndex(0);
        }

        // Normal
        if (eventType == 0) {
            if (points > 5) {
                leaguePosition.setSelectedIndex(1);
            } else if (points > 4) {
                leaguePosition.setSelectedIndex(2);
            } else if (points == 4) {
                leaguePosition.setSelectedIndex(3);
            } else {
                // TODO In VI lower no relegation, remove with next season
                if (serie > 5) {
                    if (points < 3) {
                        leaguePosition.setSelectedIndex(5);
                    } else {
                        leaguePosition.setSelectedIndex(4);
                    }
                } else {
                    leaguePosition.setSelectedIndex(3);
                }
            }
        }

        // Releagation Qualifier
        if (eventType == -1) {
            if (points < 4) {
                leaguePosition.setSelectedIndex(5);
            } else {
                leaguePosition.setSelectedIndex(4);
            }
        }

        // Direct Releagation
        if (eventType == -2) {
            if (points < 2) {
                leaguePosition.setSelectedIndex(7);
            } else {
                leaguePosition.setSelectedIndex(6);
            }
        }
    }

    /**
     * Missing Method Documentation
     */
    private void calculateSeasonResult() {
        int eventType = ((CBItem) seasonEvent.getSelectedItem()).getId();

        // Direct Promotion
        if (eventType == 2) {
            seasonPoint.setSelectedIndex(6);
        }

        // Promotion Qualifier
        if (eventType == 1) {
            seasonPoint.setSelectedIndex(5);
        }

        // Normal
        if (eventType == 0) {
            seasonPoint.setSelectedIndex(4);
        }

        // Releagation Qualifier
        if (eventType == -1) {
            seasonPoint.setSelectedIndex(3);
        }

        // Direct Releagation
        if (eventType == -2) {
            seasonPoint.setSelectedIndex(1);
        }
    }

    /**
     * Missing Method Documentation
     */
    private void fillCupResult() {
        for (int i = 1; i <= 16; i++) {
            cupRound.addItem(new CBItem("ROUND " + i, i));
        }

        cupRound.setSelectedIndex(0);
    }

    /**
     * Missing Method Documentation
     */
    private void fillLeaguePosition() {
        for (int i = 1; i <= 8; i++) {
            leaguePosition.addItem(new CBItem("" + i, i));
        }

        leaguePosition.setSelectedIndex(3);
    }

    /**
     * Missing Method Documentation
     */
    private void fillSeasonEvent() {
        seasonEvent.addItem(new CBItem("RELEGATION", -1));
        seasonEvent.addItem(new CBItem("NORMAL", 0));
        seasonEvent.addItem(new CBItem("PROMOTION", 1));
        seasonEvent.setSelectedIndex(1);
    }

    /**
     * Missing Method Documentation
     */
    private void fillSeasonResult() {
        seasonPoint.addItem(new CBItem("0-6", 1));
        seasonPoint.addItem(new CBItem("7-12", 2));
        seasonPoint.addItem(new CBItem("13-18", 3));
        seasonPoint.addItem(new CBItem("19-24", 4));
        seasonPoint.addItem(new CBItem("25-30", 5));
        seasonPoint.addItem(new CBItem("31-36", 6));
        seasonPoint.addItem(new CBItem("37-42", 7));
        seasonPoint.setSelectedIndex(3);
    }

    /**
     * Missing Method Documentation
     */
    private void initializeListeners() {
        seasonEvent.addActionListener(this);
        seasonEventType.addActionListener(this);
        seasonPoint.addActionListener(this);
        leaguePosition.addActionListener(this);
        cupRound.addActionListener(this);
    }

    /**
     * Missing Method Documentation
     *
     * @param season Missing Constructuor Parameter Documentation
     */
    private void jbInit(int season) {
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(2, 6));
        p.add(new JLabel("Year"));
        p.add(new JLabel(season + ""));
        p.add(new JLabel("Cup Round"));
        p.add(cupRound);
        p.add(new JLabel("League Final Position"));
        p.add(leaguePosition);

        p.add(new JLabel("Season Outcome"));
        p.add(seasonEvent);
        p.add(new JLabel("Draw Type"));
        p.add(seasonEventType);
        p.add(new JLabel("Season Points"));
        p.add(seasonPoint);

        setLayout(new BorderLayout());
        add(p, BorderLayout.CENTER);

        fillSeasonEvent();
        setSeasonEventType();
        fillSeasonResult();
        fillLeaguePosition();
        fillCupResult();

        loadInputData();
        initializeListeners();
    }

    /**
     * Missing Method Documentation
     */
    private void removeListeners() {
        seasonEvent.removeActionListener(this);
        seasonEventType.removeActionListener(this);
        seasonPoint.removeActionListener(this);
        leaguePosition.removeActionListener(this);
        cupRound.removeActionListener(this);
    }
}
