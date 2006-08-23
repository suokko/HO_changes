// %2517784300:de.hattrickorganizer.gui.matches%
package de.hattrickorganizer.gui.matches;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.model.matches.MatchKurzInfo;
import de.hattrickorganizer.model.matches.Matchdetails;
import de.hattrickorganizer.tools.PlayerHelper;


/**
 * Zeigt die Stärken eines Matches an
 */
public class ManschaftsBewertungsPanel extends ImagePanel implements ActionListener {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public static final java.awt.Color FG_EIGENESTEAM = new java.awt.Color(50, 50, 150);

    //~ Instance fields ----------------------------------------------------------------------------

    private JLabel m_clGastCenterAtt = new JLabel("", SwingConstants.LEFT);
    private JLabel m_clGastCenterDef = new JLabel("", SwingConstants.LEFT);
    private JLabel m_clGastGesamt = new JLabel("", SwingConstants.LEFT);
    private JLabel m_clGastLeftAtt = new JLabel("", SwingConstants.LEFT);
    private JLabel m_clGastLeftDef = new JLabel("", SwingConstants.LEFT);
    private JLabel m_clGastMidfield = new JLabel("", SwingConstants.LEFT);
    private JLabel m_clGastRightAtt = new JLabel("", SwingConstants.LEFT);
    private JLabel m_clGastRightDef = new JLabel("", SwingConstants.LEFT);
    private JLabel m_clGastTeamName = new JLabel();
    private JLabel m_clGastTeamTore = new JLabel();
    private JLabel m_clHeimCenterAtt = new JLabel("", SwingConstants.LEFT);
    private JLabel m_clHeimCenterDef = new JLabel("", SwingConstants.LEFT);
    private JLabel m_clHeimGesamt = new JLabel("", SwingConstants.LEFT);
    private JLabel m_clHeimLeftAtt = new JLabel("", SwingConstants.LEFT);
    private JLabel m_clHeimLeftDef = new JLabel("", SwingConstants.LEFT);
    private JLabel m_clHeimMidfield = new JLabel("", SwingConstants.LEFT);
    private JLabel m_clHeimRightAtt = new JLabel("", SwingConstants.LEFT);
    private JLabel m_clHeimRightDef = new JLabel("", SwingConstants.LEFT);
    private JLabel m_clHeimTeamName = new JLabel();
    private JLabel m_clHeimTeamTore = new JLabel();
    private MatchKurzInfo m_clMatchKurzInfo;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new ManschaftsBewertungsPanel object.
     */
    public ManschaftsBewertungsPanel() {
        this(false);
    }

    /**
     * Creates a new ManschaftsBewertungsPanel object.
     *
     * @param print TODO Missing Constructuor Parameter Documentation
     */
    public ManschaftsBewertungsPanel(boolean print) {
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

        label = new JLabel(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                  .getProperty("Heim"));
        label.setFont(label.getFont().deriveFont(Font.BOLD, label.getFont().getSize() + 1));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        layout.setConstraints(label, constraints);
        panel.add(label);

        label = new JLabel(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                  .getProperty("Gast"));
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
        label = new JLabel(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                  .getProperty("Ergebnis"));
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

        //Platzhalter
        label = new JLabel(" ");
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        panel.add(label);

        //Bewertungen
        //Mittelfeld
        label = new JLabel(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                  .getProperty("Gesamtstaerke"));
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
        layout.setConstraints(m_clHeimGesamt, constraints);
        panel.add(m_clHeimGesamt);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.gridx = 4;
        constraints.gridy = 6;
        constraints.gridwidth = 2;
        layout.setConstraints(m_clGastGesamt, constraints);
        panel.add(m_clGastGesamt);

        //Platzhalter
        label = new JLabel(" ");
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        panel.add(label);

        //Mittelfeld
        label = new JLabel(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                  .getProperty("MatchMittelfeld"));
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
        layout.setConstraints(m_clHeimMidfield, constraints);
        panel.add(m_clHeimMidfield);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.gridx = 4;
        constraints.gridy = 8;
        constraints.gridwidth = 2;
        layout.setConstraints(m_clGastMidfield, constraints);
        panel.add(m_clGastMidfield);

        //rechte Abwehrseite
        label = new JLabel(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                  .getProperty("rechteAbwehrseite"));
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
        layout.setConstraints(m_clHeimRightDef, constraints);
        panel.add(m_clHeimRightDef);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.gridx = 4;
        constraints.gridy = 9;
        constraints.gridwidth = 2;
        layout.setConstraints(m_clGastRightDef, constraints);
        panel.add(m_clGastRightDef);

        //Abwehrzentrum
        label = new JLabel(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                  .getProperty("Abwehrzentrum"));
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
        layout.setConstraints(m_clHeimCenterDef, constraints);
        panel.add(m_clHeimCenterDef);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.gridx = 4;
        constraints.gridy = 10;
        constraints.gridwidth = 2;
        layout.setConstraints(m_clGastCenterDef, constraints);
        panel.add(m_clGastCenterDef);

        //Linke Abwehrseite
        label = new JLabel(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                  .getProperty("linkeAbwehrseite"));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridx = 0;
        constraints.gridy = 11;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        panel.add(label);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.gridx = 1;
        constraints.gridy = 11;
        constraints.gridwidth = 2;
        layout.setConstraints(m_clHeimLeftDef, constraints);
        panel.add(m_clHeimLeftDef);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.gridx = 4;
        constraints.gridy = 11;
        constraints.gridwidth = 2;
        layout.setConstraints(m_clGastLeftDef, constraints);
        panel.add(m_clGastLeftDef);

        //Rechte Angriffsseite
        label = new JLabel(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                  .getProperty("rechteAngriffsseite"));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridx = 0;
        constraints.gridy = 12;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        panel.add(label);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.gridx = 1;
        constraints.gridy = 12;
        constraints.gridwidth = 2;
        layout.setConstraints(m_clHeimRightAtt, constraints);
        panel.add(m_clHeimRightAtt);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.gridx = 4;
        constraints.gridy = 12;
        constraints.gridwidth = 2;
        layout.setConstraints(m_clGastRightAtt, constraints);
        panel.add(m_clGastRightAtt);

        //Angriffszentrum
        label = new JLabel(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                  .getProperty("Angriffszentrum"));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridx = 0;
        constraints.gridy = 13;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        panel.add(label);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.gridx = 1;
        constraints.gridy = 13;
        constraints.gridwidth = 2;
        layout.setConstraints(m_clHeimCenterAtt, constraints);
        panel.add(m_clHeimCenterAtt);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.gridx = 4;
        constraints.gridy = 13;
        constraints.gridwidth = 2;
        layout.setConstraints(m_clGastCenterAtt, constraints);
        panel.add(m_clGastCenterAtt);

        //Linke Angriffsseite
        label = new JLabel(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                  .getProperty("linkeAngriffsseite"));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridx = 0;
        constraints.gridy = 14;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        panel.add(label);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.gridx = 1;
        constraints.gridy = 14;
        constraints.gridwidth = 2;
        layout.setConstraints(m_clHeimLeftAtt, constraints);
        panel.add(m_clHeimLeftAtt);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.gridx = 4;
        constraints.gridy = 14;
        constraints.gridwidth = 2;
        layout.setConstraints(m_clGastLeftAtt, constraints);
        panel.add(m_clGastLeftAtt);

        mainconstraints.gridx = 0;
        mainconstraints.gridy = 0;
        mainlayout.setConstraints(panel, mainconstraints);
        add(panel);

        clear();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param e TODO Missing Method Parameter Documentation
     */
    public final void actionPerformed(ActionEvent e) {
        final int matchid = m_clMatchKurzInfo.getMatchID();
        de.hattrickorganizer.gui.HOMainFrame.instance().getOnlineWorker().getMatchlineup(m_clMatchKurzInfo
                                                                                         .getMatchID(),
                                                                                         m_clMatchKurzInfo
                                                                                         .getHeimID(),
                                                                                         m_clMatchKurzInfo
                                                                                         .getGastID());
        de.hattrickorganizer.gui.HOMainFrame.instance().getOnlineWorker().getMatchDetails(m_clMatchKurzInfo
                                                                                          .getMatchID());
        de.hattrickorganizer.gui.RefreshManager.instance().doReInit();
        de.hattrickorganizer.gui.HOMainFrame.instance().showMatch(matchid);
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void clear() {
        m_clHeimTeamName.setText(" ");
        m_clGastTeamName.setText(" ");
        m_clHeimTeamTore.setText(" ");
        m_clGastTeamTore.setText(" ");
        m_clHeimTeamName.setIcon(null);
        m_clGastTeamName.setIcon(null);

        m_clHeimGesamt.setText("");
        m_clGastGesamt.setText("");
        m_clHeimMidfield.setText("");
        m_clGastMidfield.setText("");
        m_clHeimRightDef.setText("");
        m_clGastRightDef.setText("");
        m_clHeimCenterDef.setText("");
        m_clGastCenterDef.setText("");
        m_clHeimLeftDef.setText("");
        m_clGastLeftDef.setText("");
        m_clHeimRightAtt.setText("");
        m_clGastRightAtt.setText("");
        m_clHeimCenterAtt.setText("");
        m_clGastCenterAtt.setText("");
        m_clHeimLeftAtt.setText("");
        m_clGastLeftAtt.setText("");

        m_clHeimGesamt.setIcon(null);
        m_clGastGesamt.setIcon(null);
        m_clHeimMidfield.setIcon(null);
        m_clGastMidfield.setIcon(null);
        m_clHeimRightDef.setIcon(null);
        m_clGastRightDef.setIcon(null);
        m_clHeimCenterDef.setIcon(null);
        m_clGastCenterDef.setIcon(null);
        m_clHeimLeftDef.setIcon(null);
        m_clGastLeftDef.setIcon(null);
        m_clHeimRightAtt.setIcon(null);
        m_clGastRightAtt.setIcon(null);
        m_clHeimCenterAtt.setIcon(null);
        m_clGastCenterAtt.setIcon(null);
        m_clHeimLeftAtt.setIcon(null);
        m_clGastLeftAtt.setIcon(null);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param info TODO Missing Method Parameter Documentation
     */
    public final void refresh(MatchKurzInfo info) {
        m_clMatchKurzInfo = info;

        final Matchdetails details = de.hattrickorganizer.database.DBZugriff.instance()
                                                                            .getMatchDetails(info
                                                                                             .getMatchID());

        //Teams
        final int teamid = de.hattrickorganizer.model.HOVerwaltung.instance().getModel().getBasics()
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

        if (info.getMatchStatus() == MatchKurzInfo.FINISHED) {
            //Sterne für Sieger!
            if (info.getMatchStatus() != MatchKurzInfo.FINISHED) {
                m_clHeimTeamName.setIcon(null);
                m_clGastTeamName.setIcon(null);
            } else if (info.getHeimTore() > info.getGastTore()) {
                m_clHeimTeamName.setIcon(de.hattrickorganizer.tools.Helper.YELLOWSTARIMAGEICON);
                m_clGastTeamName.setIcon(null);
            } else if (info.getHeimTore() < info.getGastTore()) {
                m_clHeimTeamName.setIcon(null);
                m_clGastTeamName.setIcon(de.hattrickorganizer.tools.Helper.YELLOWSTARIMAGEICON);
            } else {
                m_clHeimTeamName.setIcon(de.hattrickorganizer.tools.Helper.GREYSTARIMAGEICON);
                m_clGastTeamName.setIcon(de.hattrickorganizer.tools.Helper.GREYSTARIMAGEICON);
            }

            String temp;
            temp = PlayerHelper.getNameForSkill(details.getHomeGesamtstaerke(false), false, true);

            if (gui.UserParameter.instance().zahlenFuerSkill) {
                temp += (" ("
                + de.hattrickorganizer.tools.Helper.round((((details.getHomeGesamtstaerke(false)
                                                          - 1) / 4) + 1), 2) + ")");
            }

            m_clHeimGesamt.setText(temp);
            temp = PlayerHelper.getNameForSkill(details.getGuestGesamtstaerke(false), false, true);

            if (gui.UserParameter.instance().zahlenFuerSkill) {
                temp += (" ("
                + de.hattrickorganizer.tools.Helper.round((((details.getGuestGesamtstaerke(false)
                                                          - 1) / 4) + 1), 2) + ")");
            }

            m_clGastGesamt.setText(temp);
            m_clHeimMidfield.setText(PlayerHelper.getNameForSkill(true, details.getHomeMidfield()));
            m_clGastMidfield.setText(PlayerHelper.getNameForSkill(true, details.getGuestMidfield()));
            m_clHeimRightDef.setText(PlayerHelper.getNameForSkill(true, details.getHomeRightDef()));
            m_clGastRightDef.setText(PlayerHelper.getNameForSkill(true, details.getGuestRightDef()));
            m_clHeimCenterDef.setText(PlayerHelper.getNameForSkill(true, details.getHomeMidDef()));
            m_clGastCenterDef.setText(PlayerHelper.getNameForSkill(true, details.getGuestMidDef()));
            m_clHeimLeftDef.setText(PlayerHelper.getNameForSkill(true, details.getHomeLeftDef()));
            m_clGastLeftDef.setText(PlayerHelper.getNameForSkill(true, details.getGuestLeftDef()));
            m_clHeimRightAtt.setText(PlayerHelper.getNameForSkill(true, details.getHomeRightAtt()));
            m_clGastRightAtt.setText(PlayerHelper.getNameForSkill(true, details.getGuestRightAtt()));
            m_clHeimCenterAtt.setText(PlayerHelper.getNameForSkill(true, details.getHomeMidAtt()));
            m_clGastCenterAtt.setText(PlayerHelper.getNameForSkill(true, details.getGuestMidAtt()));
            m_clHeimLeftAtt.setText(PlayerHelper.getNameForSkill(true, details.getHomeLeftAtt()));
            m_clGastLeftAtt.setText(PlayerHelper.getNameForSkill(true, details.getGuestLeftAtt()));

            //            m_clHeimMidfield.setIcon ( tools.Helper.getImageIcon4Veraenderung ( details.getHomeMidfield () - details.getGuestMidfield () ) );
            //            m_clGastMidfield.setIcon ( tools.Helper.getImageIcon4Veraenderung( details.getGuestMidfield () - details.getHomeMidfield () ) );
            //            m_clHeimRightDef.setIcon ( tools.Helper.getImageIcon4Veraenderung( details.getHomeRightDef () - details.getGuestRightDef () ) );
            //            m_clGastRightDef.setIcon ( tools.Helper.getImageIcon4Veraenderung( details.getGuestRightDef () - details.getHomeRightDef () ) );
            //            m_clHeimCenterDef.setIcon ( tools.Helper.getImageIcon4Veraenderung( details.getHomeMidDef () - details.getGuestMidDef () ) );
            //            m_clGastCenterDef.setIcon ( tools.Helper.getImageIcon4Veraenderung( details.getGuestMidDef () - details.getHomeMidDef () ) );
            //            m_clHeimLeftDef.setIcon ( tools.Helper.getImageIcon4Veraenderung( details.getHomeLeftDef () - details.getGuestLeftDef () ) );
            //            m_clGastLeftDef.setIcon ( tools.Helper.getImageIcon4Veraenderung( details.getGuestLeftDef () - details.getHomeLeftDef () ) );
            //            m_clHeimRightAtt.setIcon ( tools.Helper.getImageIcon4Veraenderung( details.getHomeRightAtt () - details.getGuestRightAtt () ) );
            //            m_clGastRightAtt.setIcon ( tools.Helper.getImageIcon4Veraenderung( details.getGuestRightAtt () - details.getHomeRightAtt () ) );
            //            m_clHeimCenterAtt.setIcon ( tools.Helper.getImageIcon4Veraenderung( details.getHomeMidAtt () - details.getGuestMidAtt () ) );
            //            m_clGastCenterAtt.setIcon ( tools.Helper.getImageIcon4Veraenderung( details.getGuestMidAtt () - details.getHomeMidAtt () ) );
            //            m_clHeimLeftAtt.setIcon ( tools.Helper.getImageIcon4Veraenderung( details.getHomeLeftAtt () - details.getGuestLeftAtt () ) );
            //            m_clGastLeftAtt.setIcon ( tools.Helper.getImageIcon4Veraenderung( details.getGuestLeftAtt () - details.getHomeLeftAtt () ) );
            m_clHeimGesamt.setIcon(de.hattrickorganizer.tools.Helper.getImageIcon4Veraenderung((int) (details
                                                                                                      .getHomeGesamtstaerke(false)
                                                                                               - details
                                                                                                 .getGuestGesamtstaerke(false))));
            m_clGastGesamt.setIcon(de.hattrickorganizer.tools.Helper.getImageIcon4Veraenderung((int) (details
                                                                                                      .getGuestGesamtstaerke(false)
                                                                                               - details
                                                                                                 .getHomeGesamtstaerke(false))));
            m_clHeimMidfield.setIcon(de.hattrickorganizer.tools.Helper.getImageIcon4Veraenderung(details
                                                                                                 .getHomeMidfield()
                                                                                                 - details
                                                                                                   .getGuestMidfield()));
            m_clGastMidfield.setIcon(de.hattrickorganizer.tools.Helper.getImageIcon4Veraenderung(details
                                                                                                 .getGuestMidfield()
                                                                                                 - details
                                                                                                   .getHomeMidfield()));
            m_clHeimRightDef.setIcon(de.hattrickorganizer.tools.Helper.getImageIcon4Veraenderung(details
                                                                                                 .getHomeRightDef()
                                                                                                 - details
                                                                                                   .getGuestLeftAtt()));
            m_clGastRightDef.setIcon(de.hattrickorganizer.tools.Helper.getImageIcon4Veraenderung(details
                                                                                                 .getGuestRightDef()
                                                                                                 - details
                                                                                                   .getHomeLeftAtt()));
            m_clHeimCenterDef.setIcon(de.hattrickorganizer.tools.Helper.getImageIcon4Veraenderung(details
                                                                                                  .getHomeMidDef()
                                                                                                  - details
                                                                                                    .getGuestMidAtt()));
            m_clGastCenterDef.setIcon(de.hattrickorganizer.tools.Helper.getImageIcon4Veraenderung(details
                                                                                                  .getGuestMidDef()
                                                                                                  - details
                                                                                                    .getHomeMidAtt()));
            m_clHeimLeftDef.setIcon(de.hattrickorganizer.tools.Helper.getImageIcon4Veraenderung(details
                                                                                                .getHomeLeftDef()
                                                                                                - details
                                                                                                  .getGuestRightAtt()));
            m_clGastLeftDef.setIcon(de.hattrickorganizer.tools.Helper.getImageIcon4Veraenderung(details
                                                                                                .getGuestLeftDef()
                                                                                                - details
                                                                                                  .getHomeRightAtt()));
            m_clHeimRightAtt.setIcon(de.hattrickorganizer.tools.Helper.getImageIcon4Veraenderung(details
                                                                                                 .getHomeRightAtt()
                                                                                                 - details
                                                                                                   .getGuestLeftDef()));
            m_clGastRightAtt.setIcon(de.hattrickorganizer.tools.Helper.getImageIcon4Veraenderung(details
                                                                                                 .getGuestRightAtt()
                                                                                                 - details
                                                                                                   .getHomeLeftDef()));
            m_clHeimCenterAtt.setIcon(de.hattrickorganizer.tools.Helper.getImageIcon4Veraenderung(details
                                                                                                  .getHomeMidAtt()
                                                                                                  - details
                                                                                                    .getGuestMidDef()));
            m_clGastCenterAtt.setIcon(de.hattrickorganizer.tools.Helper.getImageIcon4Veraenderung(details
                                                                                                  .getGuestMidAtt()
                                                                                                  - details
                                                                                                    .getHomeMidDef()));
            m_clHeimLeftAtt.setIcon(de.hattrickorganizer.tools.Helper.getImageIcon4Veraenderung(details
                                                                                                .getHomeLeftAtt()
                                                                                                - details
                                                                                                  .getGuestRightDef()));
            m_clGastLeftAtt.setIcon(de.hattrickorganizer.tools.Helper.getImageIcon4Veraenderung(details
                                                                                                .getGuestLeftAtt()
                                                                                                - details
                                                                                                  .getHomeRightDef()));
        } //Ende Finished

        //Spiel noch nicht gespielt
        else {
            m_clHeimTeamName.setText(" ");
            m_clGastTeamName.setText(" ");
            m_clHeimTeamTore.setText(" ");
            m_clGastTeamTore.setText(" ");
            m_clHeimTeamName.setIcon(null);
            m_clGastTeamName.setIcon(null);

            m_clHeimGesamt.setText("");
            m_clGastGesamt.setText("");
            m_clHeimMidfield.setText("");
            m_clGastMidfield.setText("");
            m_clHeimRightDef.setText("");
            m_clGastRightDef.setText("");
            m_clHeimCenterDef.setText("");
            m_clGastCenterDef.setText("");
            m_clHeimLeftDef.setText("");
            m_clGastLeftDef.setText("");
            m_clHeimRightAtt.setText("");
            m_clGastRightAtt.setText("");
            m_clHeimCenterAtt.setText("");
            m_clGastCenterAtt.setText("");
            m_clHeimLeftAtt.setText("");
            m_clGastLeftAtt.setText("");

            m_clHeimGesamt.setIcon(null);
            m_clGastGesamt.setIcon(null);
            m_clHeimRightDef.setIcon(null);
            m_clGastRightDef.setIcon(null);
            m_clHeimCenterDef.setIcon(null);
            m_clGastCenterDef.setIcon(null);
            m_clHeimLeftDef.setIcon(null);
            m_clGastLeftDef.setIcon(null);
            m_clHeimRightAtt.setIcon(null);
            m_clGastRightAtt.setIcon(null);
            m_clHeimCenterAtt.setIcon(null);
            m_clGastCenterAtt.setIcon(null);
            m_clHeimLeftAtt.setIcon(null);
            m_clGastLeftAtt.setIcon(null);
        }

        repaint();
    }

    ///////////////////////////////////////////////////////////////////////////////
    private int getDifferenz(int home, String homesub, int guest, String guestsub) {
        int realhome = 3 * home;

        if ((homesub == null) || homesub.trim().equals("")) {
            realhome--;
        } else if (homesub.equals("-")) {
            realhome--;
            realhome--;
        }

        int realguest = 3 * guest;

        if ((guestsub == null) || guestsub.trim().equals("")) {
            realguest--;
        } else if (guestsub.equals("-")) {
            realguest--;
            realguest--;
        }

        return (realhome - realguest);
    }
}
