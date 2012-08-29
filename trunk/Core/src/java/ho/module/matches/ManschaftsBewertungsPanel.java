// %2517784300:de.hattrickorganizer.gui.matches%
package ho.module.matches;

import ho.core.constants.player.PlayerAbility;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.gui.theme.HOColorName;
import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ImageUtilities;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.HOVerwaltung;
import ho.core.model.match.MatchKurzInfo;
import ho.core.model.match.Matchdetails;
import ho.core.util.Helper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;



/**
 * Zeigt die Stärken eines Matches an
 */
class ManschaftsBewertungsPanel extends ImagePanel /*implements ActionListener*/ {
    //~ Static fields/initializers -----------------------------------------------------------------

	private static final long serialVersionUID = 1835093736247065469L;

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

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new ManschaftsBewertungsPanel object.
     */
    ManschaftsBewertungsPanel() {
        this(false);
    }

    ManschaftsBewertungsPanel(boolean print) {
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

        //Platzhalter
        JLabel label = new JLabel("  ");
        constraints.weightx = 0.0;
        constraints.gridheight = 20;
        constraints.gridwidth = 1;
        add(panel,label,layout,constraints,3,1);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Heim"));
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

        //Platzhalter
        label = new JLabel(" ");
        add(panel,label,layout,constraints,0,5);

        //Bewertungen
        //Mittelfeld
        label = new JLabel(HOVerwaltung.instance().getLanguageString("Gesamtstaerke"));
        add(panel,label,layout,constraints,0,6);
        add(panel,m_clHeimGesamt,layout,constraints,1,6);
        add(panel,m_clGastGesamt,layout,constraints,4,6);

        //Platzhalter
        label = new JLabel(" ");
        add(panel,label,layout,constraints,0,7);

        //Mittelfeld
        label = new JLabel(HOVerwaltung.instance().getLanguageString("MatchMittelfeld"));
        add(panel,label,layout,constraints,0,8);
        add(panel,m_clHeimMidfield,layout,constraints,1,8);
        add(panel,m_clGastMidfield,layout,constraints,4,8);

        //rechte Abwehrseite
        label = new JLabel(HOVerwaltung.instance().getLanguageString("rechteAbwehrseite"));
        add(panel,label,layout,constraints,0,9);
        add(panel,m_clHeimRightDef,layout,constraints,1,9);
        add(panel,m_clGastRightDef,layout,constraints,4,9);

        //Abwehrzentrum
        label = new JLabel(HOVerwaltung.instance().getLanguageString("Abwehrzentrum"));
        add(panel,label,layout,constraints,0,10);
        add(panel,m_clHeimCenterDef,layout,constraints,1,10);
        add(panel,m_clGastCenterDef,layout,constraints,4,10);

        //Linke Abwehrseite
        label = new JLabel(HOVerwaltung.instance().getLanguageString("linkeAbwehrseite"));
        add(panel,label,layout,constraints,0,11);
        add(panel,m_clHeimLeftDef,layout,constraints,1,11);
        add(panel,m_clGastLeftDef,layout,constraints,4,11);

        //Rechte Angriffsseite
        label = new JLabel(HOVerwaltung.instance().getLanguageString("rechteAngriffsseite"));
        add(panel,label,layout,constraints,0,12);
        add(panel,m_clHeimRightAtt,layout,constraints,1,12);
        add(panel,m_clGastRightAtt,layout,constraints,4,12);

        //Angriffszentrum
        label = new JLabel(HOVerwaltung.instance().getLanguageString("Angriffszentrum"));
        add(panel,label,layout,constraints,0,13);
        add(panel,m_clHeimCenterAtt,layout,constraints,1,13);
        add(panel,m_clGastCenterAtt,layout,constraints,4,13);

        //Linke Angriffsseite
        label = new JLabel(HOVerwaltung.instance().getLanguageString("linkeAngriffsseite"));
        add(panel,label,layout,constraints,0,14);
        add(panel,m_clHeimLeftAtt,layout,constraints,1,14);
        add(panel,m_clGastLeftAtt,layout,constraints,4,14);

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
    //~ Methods ------------------------------------------------------------------------------------


    final void clear() {
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

    final void refresh(MatchKurzInfo info,Matchdetails details) {

        //Teams
        final int teamid = HOVerwaltung.instance().getModel().getBasics().getTeamId();

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

            String temp;
            temp = PlayerAbility.getNameForSkill(details.getHomeGesamtstaerke(false), false, true);

            if (ho.core.model.UserParameter.instance().zahlenFuerSkill) {
                temp += (" ("
                + Helper.round((((details.getHomeGesamtstaerke(false)- 1) / 4) + 1), 2) + ")");
            }

            m_clHeimGesamt.setText(temp);
            temp = PlayerAbility.getNameForSkill(details.getGuestGesamtstaerke(false), false, true);

            if (ho.core.model.UserParameter.instance().zahlenFuerSkill) {
                temp += (" ("
                + Helper.round((((details.getGuestGesamtstaerke(false) - 1) / 4) + 1), 2) + ")");
            }

            m_clGastGesamt.setText(temp);
            m_clHeimMidfield.setText(PlayerAbility.getNameForSkill(true, details.getHomeMidfield()));
            m_clGastMidfield.setText(PlayerAbility.getNameForSkill(true, details.getGuestMidfield()));
            m_clHeimRightDef.setText(PlayerAbility.getNameForSkill(true, details.getHomeRightDef()));
            m_clGastRightDef.setText(PlayerAbility.getNameForSkill(true, details.getGuestRightDef()));
            m_clHeimCenterDef.setText(PlayerAbility.getNameForSkill(true, details.getHomeMidDef()));
            m_clGastCenterDef.setText(PlayerAbility.getNameForSkill(true, details.getGuestMidDef()));
            m_clHeimLeftDef.setText(PlayerAbility.getNameForSkill(true, details.getHomeLeftDef()));
            m_clGastLeftDef.setText(PlayerAbility.getNameForSkill(true, details.getGuestLeftDef()));
            m_clHeimRightAtt.setText(PlayerAbility.getNameForSkill(true, details.getHomeRightAtt()));
            m_clGastRightAtt.setText(PlayerAbility.getNameForSkill(true, details.getGuestRightAtt()));
            m_clHeimCenterAtt.setText(PlayerAbility.getNameForSkill(true, details.getHomeMidAtt()));
            m_clGastCenterAtt.setText(PlayerAbility.getNameForSkill(true, details.getGuestMidAtt()));
            m_clHeimLeftAtt.setText(PlayerAbility.getNameForSkill(true, details.getHomeLeftAtt()));
            m_clGastLeftAtt.setText(PlayerAbility.getNameForSkill(true, details.getGuestLeftAtt()));

            m_clHeimGesamt.setIcon(ImageUtilities.getImageIcon4Veraenderung((int) (details.getHomeGesamtstaerke(false)
                                                                                               - details.getGuestGesamtstaerke(false)),true));
            m_clGastGesamt.setIcon(ImageUtilities.getImageIcon4Veraenderung((int) (details.getGuestGesamtstaerke(false)
                                                                                               - details.getHomeGesamtstaerke(false)),true));
            m_clHeimMidfield.setIcon(ImageUtilities.getImageIcon4Veraenderung(details.getHomeMidfield()- details.getGuestMidfield(),true));
            m_clGastMidfield.setIcon(ImageUtilities.getImageIcon4Veraenderung(details.getGuestMidfield()- details.getHomeMidfield(),true));
            m_clHeimRightDef.setIcon(ImageUtilities.getImageIcon4Veraenderung(details.getHomeRightDef()- details.getGuestLeftAtt(),true));
            m_clGastRightDef.setIcon(ImageUtilities.getImageIcon4Veraenderung(details.getGuestRightDef()- details.getHomeLeftAtt(),true));
            m_clHeimCenterDef.setIcon(ImageUtilities.getImageIcon4Veraenderung(details.getHomeMidDef()- details.getGuestMidAtt(),true));
            m_clGastCenterDef.setIcon(ImageUtilities.getImageIcon4Veraenderung(details.getGuestMidDef()- details.getHomeMidAtt(),true));
            m_clHeimLeftDef.setIcon(ImageUtilities.getImageIcon4Veraenderung(details.getHomeLeftDef()- details.getGuestRightAtt(),true));
            m_clGastLeftDef.setIcon(ImageUtilities.getImageIcon4Veraenderung(details.getGuestLeftDef()- details.getHomeRightAtt(),true));
            m_clHeimRightAtt.setIcon(ImageUtilities.getImageIcon4Veraenderung(details.getHomeRightAtt()- details.getGuestLeftDef(),true));
            m_clGastRightAtt.setIcon(ImageUtilities.getImageIcon4Veraenderung(details.getGuestRightAtt()- details.getHomeLeftDef(),true));
            m_clHeimCenterAtt.setIcon(ImageUtilities.getImageIcon4Veraenderung(details.getHomeMidAtt()- details.getGuestMidDef(),true));
            m_clGastCenterAtt.setIcon(ImageUtilities.getImageIcon4Veraenderung(details.getGuestMidAtt()- details.getHomeMidDef(),true));
            m_clHeimLeftAtt.setIcon(ImageUtilities.getImageIcon4Veraenderung(details.getHomeLeftAtt() - details.getGuestRightDef(),true));
            m_clGastLeftAtt.setIcon(ImageUtilities.getImageIcon4Veraenderung(details.getGuestLeftAtt()- details.getHomeRightDef(),true));
        } //Ende Finished

        //Spiel noch nicht gespielt
        else {
        	clear();
        }

        repaint();
    }
}
