// %3815329211:de.hattrickorganizer.gui.league%
package de.hattrickorganizer.gui.league;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import plugins.IMatchKurzInfo;
import plugins.IPaarung;
import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.gui.HOMainFrame;
import de.hattrickorganizer.gui.RefreshManager;
import de.hattrickorganizer.gui.model.SpielerTableRenderer;
import de.hattrickorganizer.gui.theme.ThemeManager;
import de.hattrickorganizer.logik.MatchUpdater;
import de.hattrickorganizer.model.HOMiniModel;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.matches.MatchKurzInfo;
import de.hattrickorganizer.model.matches.MatchLineup;
import de.hattrickorganizer.model.matchlist.Paarung;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.Helper;


/**
 * Display a matchday
 */
final class SpieltagPanel extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 6884532906036202996L;
	
    //~ Static fields/initializers -----------------------------------------------------------------
    public static final int NAECHSTER_SPIELTAG = -2;
    public static final int LETZTER_SPIELTAG = -1;

    //~ Instance fields ----------------------------------------------------------------------------

    private JButton m_jbFirstMatch = new JButton();
    private JButton m_jbFourthMatch = new JButton();
    private JButton m_jbSecondMatch = new JButton();
    private JButton m_jbThirdMatch = new JButton();
    private JLabel m_jlFirstHomeTeam = new JLabel();
    private JLabel m_jlFirstResult = new JLabel();
    private JLabel m_jlFirstVisitorTeam = new JLabel();
    private JLabel m_jlFourthHomeTeam = new JLabel();
    private JLabel m_jlFourthResult = new JLabel();
    private JLabel m_jlFourthVisitorTeam = new JLabel();
    private JLabel m_jlSecondHomeTeam = new JLabel();
    private JLabel m_jlSecondResult = new JLabel();
    private JLabel m_jlSecondVisitorTeam = new JLabel();
    private JLabel m_jlThirdHomeTeam = new JLabel();
    private JLabel m_jlThirdResult = new JLabel();
    private JLabel m_jlThirdVisitorTeam = new JLabel();
    private int m_iSpieltag = -1;

    
    
    protected SpieltagPanel(int spieltag) {
        //Kann codiert sein!
        m_iSpieltag = spieltag;

        initComponents();
    }

    public void actionPerformed(ActionEvent e) {
        //Das Match anzeigen
        int[] matchdata = new int[0];

        try {
            //Matchid, Heimid, Gastid, Heimtore, Gasttore
            matchdata = Helper.generateIntArray(e.getActionCommand());
        } catch (Exception ex) {
            HOLogger.instance().log(getClass(),"SpieltagPanel.actionPerformed: Matchid konnte nicht geparsed werden: "
                               + e.getActionCommand() + " / " + ex);
            return;
        }

        //--Match zeigen ggf runterladen--
        if (matchdata[0] > 0) {
            //Spiel nicht vorhanden, dann erst runterladen!
            if (!DBZugriff.instance().isMatchVorhanden(matchdata[0])) {
                try {
                    //Lineups
                    if (!HOMainFrame.instance().getOnlineWorker().getMatchlineup(matchdata[0],
                                                                             matchdata[1],
                                                                             matchdata[2])) {
                        //Abbruch, wenn das Lineup nicht gezogen wurde
                        return;
                    }

                    if (!HOMainFrame.instance().getOnlineWorker().getMatchDetails(matchdata[0])) {
                        //Abbruch, wenn das Details nicht gezogen wurde
                        return;
                    }

                    //MatchKurzInfo muss hier erstellt werden!!
                    final MatchLineup lineup = DBZugriff.instance().getMatchLineup(matchdata[0]);

                    if (lineup != null) {
                        final MatchKurzInfo info = new MatchKurzInfo();

                        //?
                        info.setAufstellung(true);
                        info.setGastID(lineup.getGastId());
                        info.setGastName(lineup.getGastName());
                        info.setGastTore(matchdata[4]);
                        info.setHeimID(lineup.getHeimId());
                        info.setHeimName(lineup.getHeimName());
                        info.setHeimTore(matchdata[3]);
                        info.setMatchDate(lineup.getStringSpielDate());
                        info.setMatchID(matchdata[0]);
                        info.setMatchStatus(IMatchKurzInfo.FINISHED);
                        info.setMatchTyp(lineup.getMatchTyp());

                        final MatchKurzInfo[] infos = {info};
                        DBZugriff.instance().storeMatchKurzInfos(infos);

                        MatchUpdater.updateMatch(HOMiniModel.instance(), matchdata[0]);
                    }

                    fillLabels();

                    RefreshManager.instance().doReInit();
                } catch (Exception ex) {
                    //Fehler!
                    HOLogger.instance().log(getClass(),"SpieltagPanel.actionPerformed: Fehler beim Download eines Spieles : "
                                       + ex);
                    return;
                }
            }
            else {
                //Match zeigen
                HOMainFrame.instance().showMatch(matchdata[0]);
            }
        }
    }

    protected void changeSaison() {
        fillLabels();
    }

    private void setConstraintsValues(GridBagConstraints constraints, int fillValue,
                                      double weightxValue, int gridxValue, int gridyValue,
                                      int gridWithValue) {
        constraints.fill = fillValue;
        constraints.weightx = weightxValue;
        constraints.gridx = gridxValue;
        constraints.gridy = gridyValue;
        constraints.gridwidth = gridWithValue;
    }

    private void setMatchButton(JButton button, Paarung paarung) {
        button.setPreferredSize(new Dimension(27, 18));

        //Hat das Spiel schon stattgefunden
        if ((paarung != null) && paarung.hatStattgefunden()) {
            //Match schon in der Datenbank
            if (DBZugriff.instance().isMatchVorhanden(paarung
                                                                                    .getMatchId())) {
                button.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_Ligatabelle_SpielAnzeigen"));
                button.setEnabled(true);
                button.setIcon(Helper.SHOWMATCHICON);
                button.setDisabledIcon(Helper.SHOWMATCHICON);
            }
            //Match noch nicht in der Datenbank
            else {
                button.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_Ligatabelle_SpielDownloaden"));
                button.setEnabled(true);
                button.setIcon(Helper.DOWNLOADMATCHICON);
                button.setDisabledIcon(Helper.DOWNLOADMATCHICON);
            }
        }
        //Noch nicht stattgefunden
        else {
            button.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_Ligatabelle_SpielNochnichtgespielt"));
            button.setEnabled(false);
            button.setIcon(Helper.NOMATCHICON);
            button.setDisabledIcon(Helper.NOMATCHICON);
        }
    }

    private String convertResult(String homeGoals, String awayGoals) {
        char[] result = new char[]{' ', ' ', ' ', ' ', ' ', ':', ' ', ' ', ' ', ' '};

        if (homeGoals.length() == 2) {
            result[0] = homeGoals.charAt(0);
        }

        result[1] = homeGoals.charAt(homeGoals.length() - 1);

        if (awayGoals.length() == 2) {
            result[8] = awayGoals.charAt(0);
        }

        result[9] = awayGoals.charAt(awayGoals.length() - 1);
        return new String(result);
    }

    private void fillLabels() {
        int spieltag = m_iSpieltag;
        Vector<IPaarung> paarungen = null;
        final int teamid = HOVerwaltung.instance().getModel().getBasics().getTeamId();

        if (LigaTabellePanel.getAktuellerSpielPlan() == null) {
            //Button aktualisieren
            setMatchButton(m_jbFirstMatch, null);
            setMatchButton(m_jbSecondMatch, null);
            setMatchButton(m_jbThirdMatch, null);
            setMatchButton(m_jbFourthMatch, null);

            return;
        }

        //Letzte Spieltag
        if (spieltag == LETZTER_SPIELTAG) {
            spieltag = HOVerwaltung.instance().getModel().getBasics().getSpieltag() - 1;

            if (spieltag <= 0) {
                spieltag = 1;
            }
        }
        //Nächste Spieltag
        else if (spieltag == NAECHSTER_SPIELTAG) {
            spieltag = HOVerwaltung.instance().getModel().getBasics().getSpieltag();

            if (spieltag > 14) {
                spieltag = 14;
            }

        }

        paarungen = LigaTabellePanel.getAktuellerSpielPlan().getPaarungenBySpieltag(spieltag);

        String bordertext = HOVerwaltung.instance().getLanguageString("Spieltag") + " "
                            + spieltag;

        if (paarungen != null && paarungen.size()>0) {
        	try {
        		bordertext += ("  ( "
        				+ java.text.DateFormat.getDateTimeInstance().format(((Paarung) paarungen.get(0))
        						.getDatum()) + " )");
        	} catch (Exception e) {
        		bordertext += ("  ( " + ((Paarung) paarungen.get(0)).getStringDate() + " )");
        	}
        }

        setBorder(BorderFactory.createTitledBorder(bordertext));

        //Panel aktualisieren, wenn Paarungen gefunden
        if ((paarungen != null) && (paarungen.size() == 4)) {
            resetMarkierung();

            //Erste Paarung------------------------------------------
            Paarung paarung = (Paarung) paarungen.get(0);
            m_jbFirstMatch.setActionCommand(paarung.getMatchId() + "," + paarung.getHeimId() + ","
                                            + paarung.getGastId() + "," + paarung.getToreHeim()
                                            + "," + paarung.getToreGast());
            setMatchButton(m_jbFirstMatch, paarung);

            fillRow(m_jlFirstHomeTeam, m_jlFirstVisitorTeam, m_jlFirstResult, paarung, teamid);

            //Zweite Paarung------------------------------------------
            paarung = (Paarung) paarungen.get(1);
            m_jbSecondMatch.setActionCommand(paarung.getMatchId() + "," + paarung.getHeimId() + ","
                                             + paarung.getGastId() + "," + paarung.getToreHeim()
                                             + "," + paarung.getToreGast());
            setMatchButton(m_jbSecondMatch, paarung);

            fillRow(m_jlSecondHomeTeam, m_jlSecondVisitorTeam, m_jlSecondResult, paarung, teamid);

            //Dritte Paarung------------------------------------------
            paarung = (Paarung) paarungen.get(2);
            m_jbThirdMatch.setActionCommand(paarung.getMatchId() + "," + paarung.getHeimId() + ","
                                            + paarung.getGastId() + "," + paarung.getToreHeim()
                                            + "," + paarung.getToreGast());
            setMatchButton(m_jbThirdMatch, paarung);

            fillRow(m_jlThirdHomeTeam, m_jlThirdVisitorTeam, m_jlThirdResult, paarung, teamid);

            //Vierte Paarung------------------------------------------
            paarung = (Paarung) paarungen.get(3);
            m_jbFourthMatch.setActionCommand(paarung.getMatchId() + "," + paarung.getHeimId() + ","
                                             + paarung.getGastId() + "," + paarung.getToreHeim()
                                             + "," + paarung.getToreGast());
            setMatchButton(m_jbFourthMatch, paarung);

            fillRow(m_jlFourthHomeTeam, m_jlFourthVisitorTeam, m_jlFourthResult, paarung, teamid);
        }
        //Keine Paarungen
        else {
            //Button aktualisieren
            setMatchButton(m_jbFirstMatch, null);
            setMatchButton(m_jbSecondMatch, null);
            setMatchButton(m_jbThirdMatch, null);
            setMatchButton(m_jbFourthMatch, null);
        }
    }

    private void fillRow(JLabel homeTeam, JLabel visitorTeam, JLabel result, Paarung paarung,
                         int teamid) {
    	homeTeam.setText(paarung.getHeimName());
    	visitorTeam.setText(paarung.getGastName());
        if ((paarung.getToreHeim() > -1) && (paarung.getToreGast() > -1)) {
            result.setText(convertResult(paarung.getToreHeim() + "", "" + paarung.getToreGast()));

            //HomeVictory
            if (paarung.getToreHeim() > paarung.getToreGast()) {
                homeTeam.setIcon(Helper.YELLOWSTARIMAGEICON);
                visitorTeam.setIcon(Helper.NOIMAGEICON);
            }
            //VisitorVictory
            else if (paarung.getToreHeim() < paarung.getToreGast()) {
                homeTeam.setIcon(Helper.NOIMAGEICON);
                visitorTeam.setIcon(Helper.YELLOWSTARIMAGEICON);
            }
            //drawn
            else {
                homeTeam.setIcon(Helper.GREYSTARIMAGEICON);
                visitorTeam.setIcon(Helper.GREYSTARIMAGEICON);
            }
        } else {
            result.setText(convertResult("-", "-"));
            homeTeam.setIcon(Helper.NOIMAGEICON);
            visitorTeam.setIcon(Helper.NOIMAGEICON);
        }

        markMyTeam(homeTeam, paarung.getHeimId(), teamid);
        markMyTeam(visitorTeam, paarung.getGastId(), teamid);
        markSelectedTeam(homeTeam, paarung.getHeimName());
        markSelectedTeam(visitorTeam, paarung.getGastName());
    }

    private void initButton(JButton button, GridBagConstraints constraints, GridBagLayout layout,
                            int row) {
        setConstraintsValues(constraints, GridBagConstraints.NONE, 0.0, 6, row, 1);

        button.setBackground(this.getBackground());
        button.addActionListener(this);
        layout.setConstraints(button, constraints);
        add(button);
    }

    private void initColon(GridBagConstraints constraints, GridBagLayout layout, int row) {
        JLabel label = new JLabel(":");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        setConstraintsValues(constraints, GridBagConstraints.NONE, 0.0, 1, row, 1);
        layout.setConstraints(label, constraints);
        add(label);
    }

    private void initComponents() {
        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weighty = 0.0;
        constraints.insets = new Insets(2, 2, 2, 2);

        this.setBackground(ThemeManager.getColor("panel.matchday.background")); 

        JLabel label;

        setLayout(layout);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Heim"));
        label.setFont(label.getFont().deriveFont(Font.BOLD));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        setConstraintsValues(constraints, GridBagConstraints.HORIZONTAL, 1.0, 0, 0, 1);
        layout.setConstraints(label, constraints);
        add(label);

        label = new JLabel();
        label.setHorizontalAlignment(SwingConstants.CENTER);
        setConstraintsValues(constraints, GridBagConstraints.HORIZONTAL, 0.0, 1, 0, 1);
        layout.setConstraints(label, constraints);
        add(label);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Gast"));
        label.setFont(label.getFont().deriveFont(Font.BOLD));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        setConstraintsValues(constraints, GridBagConstraints.HORIZONTAL, 1.0, 2, 0, 1);
        layout.setConstraints(label, constraints);
        add(label);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Ergebnis"));
        label.setFont(label.getFont().deriveFont(Font.BOLD));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        setConstraintsValues(constraints, GridBagConstraints.NONE, 0.5, 3, 0, 3);
        layout.setConstraints(label, constraints);
        add(label);

        label = new JLabel("");

        //label.setFont( label.getFont().deriveFont( Font.BOLD ) );
        label.setHorizontalAlignment(SwingConstants.CENTER);
        setConstraintsValues(constraints, GridBagConstraints.NONE, 0.0, 4, 0, 1);
        layout.setConstraints(label, constraints);
        add(label);

        //First Match
        initTeam(m_jlFirstHomeTeam, constraints, layout, 1, 0);
        initColon(constraints, layout, 1);
        initTeam(m_jlFirstVisitorTeam, constraints, layout, 1, 2);
        initResultLabel(m_jlFirstResult, constraints, layout, 1);
        initButton(m_jbFirstMatch, constraints, layout, 1);

        //Second Match
        initTeam(m_jlSecondHomeTeam, constraints, layout, 2, 0);
        initColon(constraints, layout, 2);
        initTeam(m_jlSecondVisitorTeam, constraints, layout, 2, 2);
        initResultLabel(m_jlSecondResult, constraints, layout, 2);
        initButton(m_jbSecondMatch, constraints, layout, 2);

        //Third Match
        initTeam(m_jlThirdHomeTeam, constraints, layout, 3, 0);
        initColon(constraints, layout, 3);
        initTeam(m_jlThirdVisitorTeam, constraints, layout, 3, 2);
        initResultLabel(m_jlThirdResult, constraints, layout, 3);
        initButton(m_jbThirdMatch, constraints, layout, 3);

        //Fourth Match
        initTeam(m_jlFourthHomeTeam, constraints, layout, 4, 0);
        initColon(constraints, layout, 4);
        initTeam(m_jlFourthVisitorTeam, constraints, layout, 4, 2);
        initResultLabel(m_jlFourthResult, constraints, layout, 4);
        initButton(m_jbFourthMatch, constraints, layout, 4);

        fillLabels();
    }

    private void initResultLabel(JLabel label, GridBagConstraints constraints,
                                 GridBagLayout layout, int row) {
        setConstraintsValues(constraints, GridBagConstraints.NONE, 0.5, 3, row, 3);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        layout.setConstraints(label, constraints);
        add(label);
    }

    private void initTeam(JLabel label, GridBagConstraints constraints, GridBagLayout layout,
                          int row, int column) {
        setConstraintsValues(constraints, GridBagConstraints.HORIZONTAL, 1.0, column, row, 1);
        layout.setConstraints(label, constraints);
        add(label);
    }

    private void markMyTeam(JLabel team, int matchTeamId, int myTeamId) {
        if (matchTeamId == myTeamId) {
            team.setFont(team.getFont().deriveFont(Font.BOLD));
            team.setForeground(ThemeManager.getColor("ho.label.ownTeam.foreground"));
        }
    }

    private void markSelectedTeam(JLabel team, String teamName) {
        if (teamName.equals(LigaTabellePanel.MARKIERTER_VEREIN)) {
            team.setOpaque(true);
            team.setBackground(SpielerTableRenderer.SELECTION_BG);
        }
    }

    private void resetMarkierung() {
        resetMarkup(m_jlFirstHomeTeam);
        resetMarkup(m_jlFirstVisitorTeam);
        resetMarkup(m_jlSecondHomeTeam);
        resetMarkup(m_jlSecondVisitorTeam);
        resetMarkup(m_jlThirdHomeTeam);
        resetMarkup(m_jlThirdVisitorTeam);
        resetMarkup(m_jlFourthHomeTeam);
        resetMarkup(m_jlFourthVisitorTeam);
    }

    private void resetMarkup(JLabel label) {
        label.setFont(label.getFont().deriveFont(Font.PLAIN));
        label.setForeground(Color.black);
        label.setOpaque(false);
    }
}
