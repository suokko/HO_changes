// %1751165603:de.hattrickorganizer.gui.matches%
package ho.module.matches;

import ho.core.constants.player.PlayerAbility;
import ho.core.db.DBManager;
import ho.core.gui.comp.entry.RatingTableEntry;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.gui.theme.HOColorName;
import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.HOVerwaltung;
import ho.core.model.match.IMatchDetails;
import ho.core.model.match.MatchKurzInfo;
import ho.core.model.match.MatchLineupPlayer;
import ho.core.model.match.MatchType;
import ho.core.model.match.Matchdetails;
import ho.core.model.player.ISpielerPosition;
import ho.core.util.Helper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;



/**
 * Zeigt die Stärken eines Matches an
 */
class StaerkenvergleichPanel extends ImagePanel {

	private static final long serialVersionUID = -4203763992583137178L;

    private JLabel m_clGastEinstellung = new JLabel();
    private JLabel m_clGastSelbstvertrauen = new JLabel();
    private JLabel m_clGastSterne = new JLabel();
    private JLabel m_clGastStimmung = new JLabel();
    private JLabel m_clGastTaktik = new JLabel();
    private JLabel m_clGastTaktikskill = new JLabel();
    private JLabel m_clGastTeamName = new JLabel();
    private JLabel m_clGastTeamTore = new JLabel();
    private JLabel m_clGastTeamHatstats = new JLabel();
    private JLabel m_clGastTeamLoddar = new JLabel();
    private JLabel m_clHeimEinstellung = new JLabel();
    private JLabel m_clHeimSelbstvertrauen = new JLabel();
    private JLabel m_clHeimSterne = new JLabel();
    private JLabel m_clHeimStimmung = new JLabel();
    private JLabel m_clHeimTaktik = new JLabel();
    private JLabel m_clHeimTaktikskill = new JLabel();
    private JLabel m_clHeimTeamName = new JLabel();
    private JLabel m_clHeimTeamTore = new JLabel();
    private JLabel m_clHeimTeamHatstats = new JLabel();
    private JLabel m_clHeimTeamLoddar = new JLabel();
    private JLabel m_clMatchtyp = new JLabel();
    private JLabel m_clWetter = new JLabel();
    private JLabel m_clZuschauer = new JLabel();
    private RatingTableEntry m_clGastTeamRating = new RatingTableEntry();
    private RatingTableEntry m_clHeimTeamRating = new RatingTableEntry();

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new StaerkenvergleichPanel object.
     */
     StaerkenvergleichPanel() {
        this(false);
    }

    StaerkenvergleichPanel(boolean print) {
        super(print);

        setBackground(ThemeManager.getColor(HOColorName.PANEL_BG));

        final GridBagLayout mainlayout = new GridBagLayout();
        final GridBagConstraints mainconstraints = new GridBagConstraints();
        mainconstraints.anchor = GridBagConstraints.NORTH;
        mainconstraints.fill = GridBagConstraints.HORIZONTAL;
        mainconstraints.weighty = 0.1;
        mainconstraints.weightx = 1.0;
        mainconstraints.insets = new Insets(4, 6, 4, 6);

        setLayout(mainlayout);

        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.weighty = 0.0;
        constraints.weightx = 1.0;
        constraints.insets = new Insets(5, 3, 2, 2);

        final JPanel panel = new JPanel(layout);
        panel.setBorder(BorderFactory.createLineBorder(ThemeManager.getColor(HOColorName.PANEL_BORDER)));
        panel.setBackground(getBackground());

        //Match
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 5;
        m_clMatchtyp.setFont(m_clMatchtyp.getFont().deriveFont(Font.BOLD));
        layout.setConstraints(m_clMatchtyp, constraints);
        panel.add(m_clMatchtyp);

        //Platzhalter
        JLabel label = new JLabel("  ");
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridx = 3;
        constraints.gridy = 1;
        constraints.gridheight = 20;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        panel.add(label);

        label = new JLabel(" ");
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridheight = 1;
        layout.setConstraints(label, constraints);
        panel.add(label);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Zuschauer"));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        panel.add(label);

        m_clZuschauer.setFont(m_clZuschauer.getFont().deriveFont(Font.BOLD));
        constraints.anchor = GridBagConstraints.EAST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.2;
        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        layout.setConstraints(m_clZuschauer, constraints);
        panel.add(m_clZuschauer);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Wetter"));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridx = 4;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        panel.add(label);

        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridx = 5;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        m_clWetter.setPreferredSize(new Dimension(28, 28));
        layout.setConstraints(m_clWetter, constraints);
        panel.add(m_clWetter);

        //Platzhalter
        label = new JLabel(" ");
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridheight = 1;
        constraints.gridwidth = 6;
        layout.setConstraints(label, constraints);
        panel.add(label);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Heim"));
        label.setFont(label.getFont().deriveFont(Font.BOLD, label.getFont().getSize() + 1));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        layout.setConstraints(label, constraints);
        panel.add(label);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Gast"));
        label.setFont(label.getFont().deriveFont(Font.BOLD, label.getFont().getSize() + 1));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridx = 4;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        panel.add(label);

        //Teams mit Ergebnis
        label = new JLabel(HOVerwaltung.instance().getLanguageString("Ergebnis"));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridx = 0;
        constraints.gridy = 4;
        layout.setConstraints(label, constraints);
        panel.add(label);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.gridx = 1;
        constraints.gridy = 4;
        m_clHeimTeamName.setPreferredSize(new Dimension(140, 14));
        m_clHeimTeamName.setFont(m_clHeimTeamName.getFont().deriveFont(Font.BOLD));
        layout.setConstraints(m_clHeimTeamName, constraints);
        panel.add(m_clHeimTeamName);

        constraints.anchor = GridBagConstraints.EAST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridx = 2;
        constraints.gridy = 4;
        m_clHeimTeamTore.setFont(m_clHeimTeamTore.getFont().deriveFont(Font.BOLD));
        layout.setConstraints(m_clHeimTeamTore, constraints);
        panel.add(m_clHeimTeamTore);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.gridx = 4;
        constraints.gridy = 4;
        m_clGastTeamName.setPreferredSize(new Dimension(140, 14));
        m_clGastTeamName.setFont(m_clGastTeamName.getFont().deriveFont(Font.BOLD));
        layout.setConstraints(m_clGastTeamName, constraints);
        panel.add(m_clGastTeamName);

        constraints.anchor = GridBagConstraints.EAST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridx = 5;
        constraints.gridy = 4;
        m_clGastTeamTore.setFont(m_clGastTeamTore.getFont().deriveFont(Font.BOLD));
        layout.setConstraints(m_clGastTeamTore, constraints);
        panel.add(m_clGastTeamTore);

        //Sterne
        label = new JLabel(HOVerwaltung.instance().getLanguageString("Bewertung"));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        panel.add(label);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.gridx = 1;
        constraints.gridy = 5;
        m_clHeimTeamRating.setOpaque(false);
        layout.setConstraints(m_clHeimTeamRating.getComponent(false), constraints);
        panel.add(m_clHeimTeamRating.getComponent(false));

        constraints.anchor = GridBagConstraints.EAST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridx = 2;
        constraints.gridy = 5;
        m_clHeimSterne.setFont(m_clHeimSterne.getFont().deriveFont(Font.BOLD));
        layout.setConstraints(m_clHeimSterne, constraints);
        panel.add(m_clHeimSterne);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.gridx = 4;
        constraints.gridy = 5;
        m_clGastTeamRating.setOpaque(false);
        layout.setConstraints(m_clGastTeamRating.getComponent(false), constraints);
        panel.add(m_clGastTeamRating.getComponent(false));

        constraints.anchor = GridBagConstraints.EAST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridx = 5;
        constraints.gridy = 5;
        m_clGastSterne.setFont(m_clGastSterne.getFont().deriveFont(Font.BOLD));
        layout.setConstraints(m_clGastSterne, constraints);
        panel.add(m_clGastSterne);

        // Hatstats
        label = new JLabel(HOVerwaltung.instance().getLanguageString("Hatstats"));
        add(panel,label,layout,constraints,0,6);
        add(panel,m_clHeimTeamHatstats,layout,constraints,1,6);
        add(panel,m_clGastTeamHatstats,layout,constraints,4,6);

     // Loddar Stats
        label = new JLabel(HOVerwaltung.instance().getLanguageString("LoddarStats"));
        add(panel,label,layout,constraints,0,7);
        add(panel,m_clHeimTeamLoddar,layout,constraints,1,7);
        add(panel,m_clGastTeamLoddar,layout,constraints,4,7);

        //Einstellung
        label = new JLabel(HOVerwaltung.instance().getLanguageString("Einstellung"));
        add(panel,label,layout,constraints,0,8);
        add(panel,m_clHeimEinstellung,layout,constraints,1,8);
        add(panel,m_clGastEinstellung,layout,constraints,4,8);

        //Taktiktyp
        label = new JLabel(HOVerwaltung.instance().getLanguageString("Taktik"));
        add(panel,label,layout,constraints,0,9);
        add(panel,m_clHeimTaktik,layout,constraints,1,9);
        add(panel,m_clGastTaktik,layout,constraints,4,9);

        //Taktikskill
        label = new JLabel(HOVerwaltung.instance().getLanguageString("Taktikstaerke"));
        add(panel,label,layout,constraints,0,10);
        add(panel,m_clHeimTaktikskill,layout,constraints,1,10);
        add(panel,m_clGastTaktikskill,layout,constraints,4,10);

        //Stimmung
        label = new JLabel(HOVerwaltung.instance().getLanguageString("Stimmung"));
        add(panel,label,layout,constraints,0,11);
        add(panel,m_clHeimStimmung,layout,constraints,1,11);
        add(panel,m_clGastStimmung,layout,constraints,4,11);

        //Selbstvertrauen
        label = new JLabel(HOVerwaltung.instance().getLanguageString("Selbstvertrauen"));
        label.setPreferredSize(new Dimension(label.getPreferredSize().width + 10,
                                             label.getPreferredSize().height));
        add(panel,label,layout,constraints,0,12);
        add(panel,m_clHeimSelbstvertrauen,layout,constraints,1,12);
        add(panel,m_clGastSelbstvertrauen,layout,constraints,4,12);

        mainconstraints.gridx = 0;
        mainconstraints.gridy = 0;
        mainlayout.setConstraints(panel, mainconstraints);
        add(panel);

        clear();
    }

    private void add(JPanel panel,JLabel label,GridBagLayout layout,GridBagConstraints constraints, int x, int y){
    	if(x == 0){
            constraints.weightx = 0.0;
            constraints.gridwidth = 1;
    	} else {
            constraints.weightx = 1.0;
            constraints.gridwidth = 2;
    	}

    	constraints.gridx = x;
        constraints.gridy = y;
    	constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
    	layout.setConstraints(label, constraints);
    	panel.add(label);
    }

    final void clear() {
        m_clZuschauer.setText(" ");
        m_clWetter.setIcon(null);
        m_clHeimTeamName.setText(" ");
        m_clGastTeamName.setText(" ");
        m_clMatchtyp.setIcon(null);
        m_clMatchtyp.setText(" ");
        m_clHeimTeamRating.setRating(0, true);
        m_clGastTeamRating.setRating(0, true);
        m_clHeimTeamTore.setText(" ");
        m_clGastTeamTore.setText(" ");
        m_clHeimTeamName.setIcon(null);
        m_clGastTeamName.setIcon(null);
        m_clHeimSterne.setText(" ");
        m_clGastSterne.setText(" ");
        m_clHeimTeamHatstats.setText(" ");
        m_clGastTeamHatstats.setText(" ");
        m_clHeimTeamLoddar.setText(" ");
        m_clGastTeamLoddar.setText(" ");
        m_clHeimEinstellung.setText("");
        m_clGastEinstellung.setText("");
        m_clHeimTaktik.setText("");
        m_clGastTaktik.setText("");
        m_clHeimTaktikskill.setText("");
        m_clGastTaktikskill.setText("");
        m_clHeimStimmung.setText("");
        m_clGastStimmung.setText("");
        m_clHeimSelbstvertrauen.setText("");
        m_clGastSelbstvertrauen.setText("");
    }


    final void refresh(MatchKurzInfo info,Matchdetails details) {
        m_clZuschauer.setText(details.getZuschauer() + "");
        if(info.getMatchStatus() == MatchKurzInfo.FINISHED)
        	m_clWetter.setIcon(ThemeManager.getIcon(HOIconName.WEATHER[details.getWetterId()]));
        else
        	m_clWetter.setIcon(null);
        m_clMatchtyp.setIcon(ThemeManager.getIcon(HOIconName.MATCHTYPES[info.getMatchTyp().getIconArrayIndex()]));
        String name4matchtyp = info.getMatchTyp().getName();

        if ((details.getZuschauer() <= 0) && (info.getMatchStatus() == MatchKurzInfo.FINISHED)
        		&& (info.getMatchTyp() != MatchType.TOURNAMENTGROUP)
        		&& (info.getMatchTyp() != MatchType.TOURNAMENTPLAYOFF)) {
            name4matchtyp += (" ( "
            + HOVerwaltung.instance().getLanguageString("Reload_Match")
            + " )");
        }

        m_clMatchtyp.setText(name4matchtyp);

        //Teams
        final int teamid = HOVerwaltung.instance().getModel().getBasics()
                                                                  .getTeamId();

        m_clHeimTeamName.setText(info.getHeimName());
        m_clGastTeamName.setText(info.getGastName());

        m_clHeimTeamTore.setText(info.getHeimTore() + " ("+details.getHomeHalfTimeGoals()+") ");
        m_clGastTeamTore.setText(info.getGastTore() + " ("+details.getGuestHalfTimeGoals()+") ");

        if (info.getHeimID() == teamid) {
            m_clHeimTeamName.setForeground(ThemeManager.getColor(HOColorName.TEAM_FG));
        } else {
            m_clHeimTeamName.setForeground(ThemeManager.getColor(HOColorName.LABEL_FG));
        }

        if (info.getGastID() == teamid) {
            m_clGastTeamName.setForeground(ThemeManager.getColor(HOColorName.TEAM_FG));
        } else {
            m_clGastTeamName.setForeground(ThemeManager.getColor(HOColorName.LABEL_FG));
        }

        if (info.getMatchStatus() == MatchKurzInfo.FINISHED) {
            final Vector<MatchLineupPlayer> heimteam = DBManager.instance().getMatchLineupPlayers(info.getMatchID(),
                                                                               info.getHeimID());
            final Vector<MatchLineupPlayer> gastteam = DBManager.instance().getMatchLineupPlayers(info.getMatchID(),
                                                                               info.getGastID());

            float heimSterne = 0;
            float gastSterne = 0;

            //Heim
            for (int i = 0; i < heimteam.size(); i++) {
                final MatchLineupPlayer player = (MatchLineupPlayer) heimteam.get(i);

                if ((player.getId() < ISpielerPosition.startReserves) && (player.getId() >= ISpielerPosition.startLineup)) {
                    float rating = (float) player.getRating();

                    if (rating > 0) {
                        heimSterne += rating;
                    }
                }
            }

            //Gast
            for (int i = 0; i < gastteam.size(); i++) {
                final MatchLineupPlayer player = (MatchLineupPlayer) gastteam.get(i);

                if ((player.getId() < ISpielerPosition.startReserves) && (player.getId() >= ISpielerPosition.startLineup)) {
                    float rating = (float) player.getRating();

                    if (rating > 0) {
                        gastSterne += rating;
                    }
                }
            }

            //--updaten--
            //Sterne für Sieger!
            if (info.getMatchStatus() != MatchKurzInfo.FINISHED) {
                m_clHeimTeamName.setIcon(null);
                m_clGastTeamName.setIcon(null);
            } else if (info.getHeimTore() > info.getGastTore()) {
                m_clHeimTeamName.setIcon(ThemeManager.getTransparentIcon(HOIconName.STAR, Color.WHITE));
                m_clGastTeamName.setIcon(null);
            } else if (info.getHeimTore() < info.getGastTore()) {
                m_clHeimTeamName.setIcon(null);
                m_clGastTeamName.setIcon(ThemeManager.getTransparentIcon(HOIconName.STAR, Color.WHITE));
            } else {
                m_clHeimTeamName.setIcon(ThemeManager.getTransparentIcon(HOIconName.STAR_GRAY, Color.WHITE));
                m_clGastTeamName.setIcon(ThemeManager.getTransparentIcon(HOIconName.STAR_GRAY, Color.WHITE));
            }

            //Sterneanzahl
            m_clHeimSterne.setText(Helper.round(heimSterne, 2) + " ");
            m_clGastSterne.setText(Helper.round(gastSterne, 2) + " ");

            m_clHeimTeamRating.setRating(heimSterne * 2);
            m_clGastTeamRating.setRating(gastSterne * 2);

            m_clHeimTeamHatstats.setText(details.getHomeHatStats() + "");
            m_clGastTeamHatstats.setText(details.getAwayHatStats() + "");

            m_clHeimTeamLoddar.setText(Helper.round(details.getHomeLoddarStats(), 2) + "");
            m_clGastTeamLoddar.setText(Helper.round(details.getAwayLoddarStats(), 2) + "");

            //Einstellung
            String heimEinstellung = "";
            String gastEinstellung = "";

            switch (details.getHomeEinstellung()) {
                case IMatchDetails.EINSTELLUNG_NORMAL:
                    heimEinstellung = HOVerwaltung.instance().getLanguageString("ls.team.attitude.normal");
                    break;

                case IMatchDetails.EINSTELLUNG_PIC:
                    heimEinstellung = HOVerwaltung.instance().getLanguageString("ls.team.attitude.playitcool");
                    break;

                case IMatchDetails.EINSTELLUNG_MOTS:
                    heimEinstellung = HOVerwaltung.instance().getLanguageString("ls.team.attitude.matchoftheseason");
                    break;

                default:
                    heimEinstellung = "";
            }

            switch (details.getGuestEinstellung()) {
                case IMatchDetails.EINSTELLUNG_NORMAL:
                    gastEinstellung = HOVerwaltung.instance().getLanguageString("ls.team.attitude.normal");
                    break;

                case IMatchDetails.EINSTELLUNG_PIC:
                    gastEinstellung = HOVerwaltung.instance().getLanguageString("ls.team.attitude.playitcool");
                    break;

                case IMatchDetails.EINSTELLUNG_MOTS:
                    gastEinstellung = HOVerwaltung.instance().getLanguageString("ls.team.attitude.matchoftheseason");
                    break;

                default:
                    gastEinstellung = "";
            }

            m_clHeimEinstellung.setText(heimEinstellung);
            m_clGastEinstellung.setText(gastEinstellung);

            //Taktik
            m_clHeimTaktik.setText(Matchdetails.getNameForTaktik(details.getHomeTacticType()));
            m_clGastTaktik.setText(Matchdetails.getNameForTaktik(details.getGuestTacticType()));

            //Skill
            if (details.getHomeTacticType() != 0) {
                m_clHeimTaktikskill.setText(PlayerAbility.getNameForSkill(details.getHomeTacticSkill()));
            } else {
                m_clHeimTaktikskill.setText("");
            }

            if (details.getGuestTacticType() != 0) {
                m_clGastTaktikskill.setText(PlayerAbility.getNameForSkill(details.getGuestTacticSkill()));
            } else {
                m_clGastTaktikskill.setText("");
            }

            //Stimmung und Selbstvertrauen
            final int hrfid = DBManager.instance().getHRFID4Date(info.getMatchDateAsTimestamp());

            final String[] stimmungSelbstvertrauen = DBManager.instance().getStimmmungSelbstvertrauen(hrfid);

            if (info.getHeimID() == teamid) {
                m_clHeimStimmung.setText(stimmungSelbstvertrauen[0]);
                m_clGastStimmung.setText("");
                m_clHeimSelbstvertrauen.setText(stimmungSelbstvertrauen[1]);
                m_clGastSelbstvertrauen.setText("");
            } else if (info.getGastID() == teamid) {
                m_clHeimStimmung.setText("");
                m_clGastStimmung.setText(stimmungSelbstvertrauen[0]);
                m_clHeimSelbstvertrauen.setText("");
                m_clGastSelbstvertrauen.setText(stimmungSelbstvertrauen[1]);
            } else {
                m_clHeimStimmung.setText("");
                m_clGastStimmung.setText("");
                m_clHeimSelbstvertrauen.setText("");
                m_clGastSelbstvertrauen.setText("");
            }
        } //Ende Finished

        //Spiel noch nicht gespielt, bzw keine Daten vorhanden
        else {
            m_clHeimTeamRating.setRating(0);
            m_clGastTeamRating.setRating(0);
            m_clHeimTeamTore.setText(" ");
            m_clGastTeamTore.setText(" ");
            m_clHeimTeamName.setIcon(null);
            m_clGastTeamName.setIcon(null);
            m_clHeimSterne.setText(" ");
            m_clGastSterne.setText(" ");
            m_clHeimTeamHatstats.setText(" ");
            m_clGastTeamHatstats.setText(" ");
            m_clHeimTeamLoddar.setText(" ");
            m_clGastTeamLoddar.setText(" ");
            m_clHeimEinstellung.setText("");
            m_clGastEinstellung.setText("");
            m_clHeimTaktik.setText("");
            m_clGastTaktik.setText("");
            m_clHeimTaktikskill.setText("");
            m_clGastTaktikskill.setText("");
            m_clHeimStimmung.setText("");
            m_clGastStimmung.setText("");
            m_clHeimSelbstvertrauen.setText("");
            m_clGastSelbstvertrauen.setText("");
        }
        repaint();
    }
}
