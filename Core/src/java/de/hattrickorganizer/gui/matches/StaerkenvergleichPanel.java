// %1751165603:de.hattrickorganizer.gui.matches%
package de.hattrickorganizer.gui.matches;

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

import plugins.IMatchDetails;
import plugins.IMatchKurzInfo;
import plugins.IMatchLineupPlayer;
import plugins.ISpielerPosition;

import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.gui.templates.RatingTableEntry;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.matches.MatchKurzInfo;
import de.hattrickorganizer.model.matches.MatchLineup;
import de.hattrickorganizer.model.matches.MatchLineupPlayer;
import de.hattrickorganizer.model.matches.Matchdetails;
import de.hattrickorganizer.tools.Helper;
import de.hattrickorganizer.tools.PlayerHelper;


/**
 * Zeigt die Stärken eines Matches an
 */
public class StaerkenvergleichPanel extends ImagePanel {
	
	private static final long serialVersionUID = -4203763992583137178L;
	
    //~ Static fields/initializers -----------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public static final java.awt.Color FG_EIGENESTEAM = new java.awt.Color(50, 50, 150);

    //~ Instance fields ----------------------------------------------------------------------------

    private JLabel m_clGastEinstellung = new JLabel();
    private JLabel m_clGastSelbstvertrauen = new JLabel();
    private JLabel m_clGastSterne = new JLabel();
    private JLabel m_clGastStimmung = new JLabel();
    private JLabel m_clGastTaktik = new JLabel();
    private JLabel m_clGastTaktikskill = new JLabel();
    private JLabel m_clGastTeamName = new JLabel();
    private JLabel m_clGastTeamTore = new JLabel();
    private JLabel m_clHeimEinstellung = new JLabel();
    private JLabel m_clHeimSelbstvertrauen = new JLabel();
    private JLabel m_clHeimSterne = new JLabel();
    private JLabel m_clHeimStimmung = new JLabel();
    private JLabel m_clHeimTaktik = new JLabel();
    private JLabel m_clHeimTaktikskill = new JLabel();
    private JLabel m_clHeimTeamName = new JLabel();
    private JLabel m_clHeimTeamTore = new JLabel();
    private JLabel m_clMatchtyp = new JLabel();
    private JLabel m_clWetter = new JLabel();
    private JLabel m_clZuschauer = new JLabel();
    private RatingTableEntry m_clGastTeamRating = new RatingTableEntry();
    private RatingTableEntry m_clHeimTeamRating = new RatingTableEntry();

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new StaerkenvergleichPanel object.
     */
    public StaerkenvergleichPanel() {
        this(false);
    }

    /**
     * Creates a new StaerkenvergleichPanel object.
     *
     * @param print TODO Missing Constructuor Parameter Documentation
     */
    public StaerkenvergleichPanel(boolean print) {
        super(print);

        setBackground(Color.WHITE);

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
        panel.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        panel.setBackground(Color.white);

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
        label.setHorizontalAlignment(SwingConstants.CENTER);
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
        label.setHorizontalAlignment(SwingConstants.CENTER);
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

        //Einstellung
        label = new JLabel(HOVerwaltung.instance().getLanguageString("Einstellung"));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        panel.add(label);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.gridwidth = 2;
        layout.setConstraints(m_clHeimEinstellung, constraints);
        panel.add(m_clHeimEinstellung);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.gridx = 4;
        constraints.gridy = 6;
        constraints.gridwidth = 2;
        layout.setConstraints(m_clGastEinstellung, constraints);
        panel.add(m_clGastEinstellung);

        //Taktiktyp
        label = new JLabel(HOVerwaltung.instance().getLanguageString("Taktik"));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        panel.add(label);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.gridx = 1;
        constraints.gridy = 7;
        constraints.gridwidth = 2;
        layout.setConstraints(m_clHeimTaktik, constraints);
        panel.add(m_clHeimTaktik);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.gridx = 4;
        constraints.gridy = 7;
        constraints.gridwidth = 2;
        layout.setConstraints(m_clGastTaktik, constraints);
        panel.add(m_clGastTaktik);

        //Taktikskill
        label = new JLabel(HOVerwaltung.instance().getLanguageString("Taktikstaerke"));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridx = 0;
        constraints.gridy = 8;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        panel.add(label);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.gridx = 1;
        constraints.gridy = 8;
        constraints.gridwidth = 2;
        layout.setConstraints(m_clHeimTaktikskill, constraints);
        panel.add(m_clHeimTaktikskill);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.gridx = 4;
        constraints.gridy = 8;
        constraints.gridwidth = 2;
        layout.setConstraints(m_clGastTaktikskill, constraints);
        panel.add(m_clGastTaktikskill);

        //Stimmung
        label = new JLabel(HOVerwaltung.instance().getLanguageString("Stimmung"));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridx = 0;
        constraints.gridy = 9;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        panel.add(label);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.gridx = 1;
        constraints.gridy = 9;
        constraints.gridwidth = 2;
        layout.setConstraints(m_clHeimStimmung, constraints);
        panel.add(m_clHeimStimmung);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.gridx = 4;
        constraints.gridy = 9;
        constraints.gridwidth = 2;
        layout.setConstraints(m_clGastStimmung, constraints);
        panel.add(m_clGastStimmung);

        //Selbstvertrauen
        label = new JLabel(HOVerwaltung.instance().getLanguageString("Selbstvertrauen"));
        label.setPreferredSize(new Dimension(label.getPreferredSize().width + 10,
                                             label.getPreferredSize().height));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridx = 0;
        constraints.gridy = 10;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        panel.add(label);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.gridx = 1;
        constraints.gridy = 10;
        constraints.gridwidth = 2;
        layout.setConstraints(m_clHeimSelbstvertrauen, constraints);
        panel.add(m_clHeimSelbstvertrauen);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.gridx = 4;
        constraints.gridy = 10;
        constraints.gridwidth = 2;
        layout.setConstraints(m_clGastSelbstvertrauen, constraints);
        panel.add(m_clGastSelbstvertrauen);

        mainconstraints.gridx = 0;
        mainconstraints.gridy = 0;
        mainlayout.setConstraints(panel, mainconstraints);
        add(panel);

        clear();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     */
    public final void clear() {
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

    /**
     * TODO Missing Method Documentation
     *
     * @param info TODO Missing Method Parameter Documentation
     */
    public final void refresh(MatchKurzInfo info) {
        final Matchdetails details = DBZugriff.instance()
                                                                            .getMatchDetails(info
                                                                                             .getMatchID());

        m_clZuschauer.setText(details.getZuschauer() + "");
        m_clWetter.setIcon(de.hattrickorganizer.tools.Helper.getImageIcon4Wetter(details
                                                                                 .getWetterId()));

        m_clMatchtyp.setIcon(de.hattrickorganizer.tools.Helper.getImageIcon4Spieltyp(info
                                                                                     .getMatchTyp()));

        String name4matchtyp = MatchLineup.getName4MatchTyp(info.getMatchTyp());

        if ((details.getZuschauer() <= 0) && (info.getMatchStatus() == IMatchKurzInfo.FINISHED)) {
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

        m_clHeimTeamTore.setText(info.getHeimTore() + " ");
        m_clGastTeamTore.setText(info.getGastTore() + " ");

        if (info.getHeimID() == teamid) {
            m_clHeimTeamName.setForeground(FG_EIGENESTEAM);
        } else {
            m_clHeimTeamName.setForeground(java.awt.Color.black);
        }

        if (info.getGastID() == teamid) {
            m_clGastTeamName.setForeground(FG_EIGENESTEAM);
        } else {
            m_clGastTeamName.setForeground(java.awt.Color.black);
        }

        if (info.getMatchStatus() == IMatchKurzInfo.FINISHED) {
            final Vector<IMatchLineupPlayer> heimteam = DBZugriff.instance().getMatchLineupPlayers(info.getMatchID(),
                                                                               info.getHeimID());
            final Vector<IMatchLineupPlayer> gastteam = DBZugriff.instance().getMatchLineupPlayers(info.getMatchID(),
                                                                               info.getGastID());

            float heimSterne = 0;
            float gastSterne = 0;

            //Heim
            for (int i = 0; i < heimteam.size(); i++) {
                final MatchLineupPlayer player = (MatchLineupPlayer) heimteam.get(i);

                if (player.getId() < ISpielerPosition.beginnReservere) {
                    float rating = (float) player.getRating();

                    if (rating > 0) {
                        heimSterne += rating;
                    }
                }
            }

            //Gast
            for (int i = 0; i < gastteam.size(); i++) {
                final MatchLineupPlayer player = (MatchLineupPlayer) gastteam.get(i);

                if (player.getId() < ISpielerPosition.beginnReservere) {
                    float rating = (float) player.getRating();

                    if (rating > 0) {
                        gastSterne += rating;
                    }
                }
            }

            //--updaten--
            //Sterne für Sieger!
            if (info.getMatchStatus() != IMatchKurzInfo.FINISHED) {
                m_clHeimTeamName.setIcon(null);
                m_clGastTeamName.setIcon(null);
            } else if (info.getHeimTore() > info.getGastTore()) {
                m_clHeimTeamName.setIcon(Helper.YELLOWSTARIMAGEICON);
                m_clGastTeamName.setIcon(null);
            } else if (info.getHeimTore() < info.getGastTore()) {
                m_clHeimTeamName.setIcon(null);
                m_clGastTeamName.setIcon(Helper.YELLOWSTARIMAGEICON);
            } else {
                m_clHeimTeamName.setIcon(Helper.GREYSTARIMAGEICON);
                m_clGastTeamName.setIcon(Helper.GREYSTARIMAGEICON);
            }

            //Sterneanzahl
            m_clHeimSterne.setText(Helper.round(heimSterne, 2) + " ");
            m_clGastSterne.setText(Helper.round(gastSterne, 2) + " ");

            m_clHeimTeamRating.setRating(heimSterne * 2);
            m_clGastTeamRating.setRating(gastSterne * 2);

            //Einstellung
            String heimEinstellung = "";
            String gastEinstellung = "";

            switch (details.getHomeEinstellung()) {
                case IMatchDetails.EINSTELLUNG_NORMAL:
                    heimEinstellung = HOVerwaltung.instance().getLanguageString("Normal");
                    break;

                case IMatchDetails.EINSTELLUNG_PIC:
                    heimEinstellung = HOVerwaltung.instance().getLanguageString("PIC");
                    break;

                case IMatchDetails.EINSTELLUNG_MOTS:
                    heimEinstellung = HOVerwaltung.instance().getLanguageString("MOTS");
                    break;

                default:
                    heimEinstellung = "";
            }

            switch (details.getGuestEinstellung()) {
                case IMatchDetails.EINSTELLUNG_NORMAL:
                    gastEinstellung = HOVerwaltung.instance().getLanguageString("Normal");
                    break;

                case IMatchDetails.EINSTELLUNG_PIC:
                    gastEinstellung = HOVerwaltung.instance().getLanguageString("PIC");
                    break;

                case IMatchDetails.EINSTELLUNG_MOTS:
                    gastEinstellung = HOVerwaltung.instance().getLanguageString("MOTS");
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
                m_clHeimTaktikskill.setText(PlayerHelper.getNameForSkill(details.getHomeTacticSkill()));
            } else {
                m_clHeimTaktikskill.setText("");
            }

            if (details.getGuestTacticType() != 0) {
                m_clGastTaktikskill.setText(PlayerHelper.getNameForSkill(details
                                                                         .getGuestTacticSkill()));
            } else {
                m_clGastTaktikskill.setText("");
            }

            //Stimmung und Selbstvertrauen
            final int hrfid = DBZugriff.instance().getHRFID4Date(info.getMatchDateAsTimestamp());
            
            final String[] stimmungSelbstvertrauen = DBZugriff.instance().getStimmmungSelbstvertrauen(hrfid);

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

            //m_jbReloadMatch.setEnabled ( false ); NO!
        }

        repaint();
    }
}
