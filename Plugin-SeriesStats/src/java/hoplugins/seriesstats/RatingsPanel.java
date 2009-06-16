// %1117737442328:hoplugins.seriesstats%
/*
 * Created on 30.11.2004
 */
package hoplugins.seriesstats;

import plugins.IDebugWindow;
import plugins.IHOMiniModel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * DOCUMENT ME!
 *
 * @author Stefan Cyris
 */
public class RatingsPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    private IDebugWindow IDB;
    private IHOMiniModel hOMiniModel;
    private JLabel Team1AttackCenter;
    private JLabel Team1AttackLeft;
    private JLabel Team1AttackRight;
    private JLabel Team1Attitude;
    private JLabel Team1DefenseCenter;
    private JLabel Team1DefenseLeft;
    private JLabel Team1DefenseRight;
    private JLabel Team1HomeAway;
    private JLabel Team1Midfield;
    private JLabel Team1System;
    private JLabel Team1Tactic;
    private JLabel Team1Tacticskill;
    private JLabel Team1Teamspirit;
    private JLabel Team2AttackCenter;
    private JLabel Team2AttackLeft;
    private JLabel Team2AttackRight;
    private JLabel Team2Attitude;
    private JLabel Team2DefenseCenter;
    private JLabel Team2DefenseLeft;
    private JLabel Team2DefenseRight;
    private JLabel Team2HomeAway;
    private JLabel Team2Midfield;
    private JLabel Team2System;
    private JLabel Team2Tactic;
    private JLabel Team2Tacticskill;
    private JLabel Team2Teamspirit;
    private JLabel TeamDif11;
    private JLabel TeamDif12;
    private JLabel TeamDif13;
    private JLabel TeamDif14;
    private JLabel TeamDif15;
    private JLabel TeamDif16;
    private JLabel TeamDif17;
    private JLabel TeamDif21;
    private JLabel TeamDif22;
    private JLabel TeamDif23;
    private JLabel TeamDif24;
    private JLabel TeamDif25;
    private JLabel TeamDif26;
    private JLabel TeamDif27;
    private boolean doDebug = false;
    /*private double Team1AttackCenterValue = 0;
    private double Team1AttackLeftValue = 0;
    private double Team1AttackRightValue = 0;
    private double Team1DefenseCenterValue = 0;
    private double Team1DefenseLeftValue = 0;
    private double Team1DefenseRightValue = 0;
    private double Team1MidfieldValue = 0;
    private double Team2AttackCenterValue = 0;
    private double Team2AttackLeftValue = 0;
    private double Team2AttackRightValue = 0;
    private double Team2DefenseCenterValue = 0;
    private double Team2DefenseLeftValue = 0;
    private double Team2DefenseRightValue = 0;
    private double Team2MidfieldValue = 0;*/

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new RatingsPanel object.
     *
     * @param myhOMiniModel TODO Missing Constructuor Parameter Documentation
     */
    public RatingsPanel(IHOMiniModel myhOMiniModel) {
        this(myhOMiniModel, null);
    }

    /**
     * Creates a new RatingsPanel object.
     *
     * @param myhOMiniModel TODO Missing Constructuor Parameter Documentation
     * @param myIDB TODO Missing Constructuor Parameter Documentation
     */
    public RatingsPanel(IHOMiniModel myhOMiniModel, IDebugWindow myIDB) {
        hOMiniModel = myhOMiniModel;

        if (myIDB != null) {
            this.doDebug = true;
            this.IDB = myIDB;
        }
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public JPanel getPanel() {
        JPanel p3b = new JPanel();

        //p3b.setOpaque(false);
        p3b.setBorder(BorderFactory.createLineBorder(Colors.Black));
        p3b.setBackground(Color.WHITE);

        //LegendePanel
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(2, 5, 5, 2);
        p3b.setLayout(gbl);

        // System
        gbc.gridx = 0;
        gbc.gridy = 6;

        JLabel jlabel = new JLabel(hOMiniModel.getLanguageString("AktuellesSystem"));
        jlabel.setFont(jlabel.getFont().deriveFont(Font.BOLD));
        p3b.add(jlabel, gbc);

        gbc.gridx = 5;
        gbc.gridy = 6;
        jlabel = new JLabel(hOMiniModel.getLanguageString("AktuellesSystem"));
        jlabel.setFont(jlabel.getFont().deriveFont(Font.BOLD));
        p3b.add(jlabel, gbc);

        // Heimsystem
        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 6;
        this.Team1System = new JLabel("0-0-0");
        p3b.add(Team1System, gbc);

        //Gastsystem
        gbc.gridx = 4;
        gbc.gridy = 6;
        this.Team2System = new JLabel("0-0-0");
        p3b.add(Team2System, gbc);

        int zeile = 7;

        //Mittelfeld
        gbc.gridx = 1;
        gbc.gridy = zeile;
        jlabel = this.Team1Midfield = new JLabel("");
        p3b.add(jlabel, gbc);

        gbc.gridx = 4;
        gbc.gridy = zeile;
        jlabel = this.Team2Midfield = new JLabel("");
        p3b.add(jlabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = zeile;
        jlabel = TeamDif11 = new JLabel();
        p3b.add(jlabel, gbc);

        gbc.gridx = 3;
        gbc.gridy = zeile;
        jlabel = TeamDif21 = new JLabel();
        p3b.add(jlabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = zeile;
        jlabel = new JLabel(hOMiniModel.getLanguageString("MatchMittelfeld"));
        jlabel.setFont(jlabel.getFont().deriveFont(Font.BOLD));
        p3b.add(jlabel, gbc);

        gbc.gridx = 5;
        gbc.gridy = zeile++;
        jlabel = new JLabel(hOMiniModel.getLanguageString("MatchMittelfeld"));
        jlabel.setFont(jlabel.getFont().deriveFont(Font.BOLD));
        p3b.add(jlabel, gbc);

        //rechteAbwehrseite
        gbc.gridx = 1;
        gbc.gridy = zeile;
        jlabel = this.Team1DefenseRight = new JLabel("");
        p3b.add(jlabel, gbc);

        gbc.gridx = 4;
        gbc.gridy = zeile;
        jlabel = this.Team2AttackLeft = new JLabel("");
        p3b.add(jlabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = zeile;
        jlabel = TeamDif12 = new JLabel();
        p3b.add(jlabel, gbc);

        gbc.gridx = 3;
        gbc.gridy = zeile;
        jlabel = TeamDif22 = new JLabel();
        p3b.add(jlabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = zeile;
        jlabel = new JLabel(hOMiniModel.getLanguageString("rechteAbwehrseite"));
        jlabel.setFont(jlabel.getFont().deriveFont(Font.BOLD));
        p3b.add(jlabel, gbc);

        gbc.gridx = 5;
        gbc.gridy = zeile++;
        jlabel = new JLabel(hOMiniModel.getLanguageString("linkeAngriffsseite"));
        jlabel.setFont(jlabel.getFont().deriveFont(Font.BOLD));
        p3b.add(jlabel, gbc);

        //Abwehrzentrum
        gbc.gridx = 1;
        gbc.gridy = zeile;
        jlabel = this.Team1DefenseCenter = new JLabel("");
        p3b.add(jlabel, gbc);

        gbc.gridx = 4;
        gbc.gridy = zeile;
        jlabel = this.Team2AttackCenter = new JLabel("");
        p3b.add(jlabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = zeile;
        jlabel = TeamDif13 = new JLabel();
        p3b.add(jlabel, gbc);

        gbc.gridx = 3;
        gbc.gridy = zeile;
        jlabel = TeamDif23 = new JLabel();
        p3b.add(jlabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = zeile;
        jlabel = new JLabel(hOMiniModel.getLanguageString("Abwehrzentrum"));
        jlabel.setFont(jlabel.getFont().deriveFont(Font.BOLD));
        p3b.add(jlabel, gbc);

        gbc.gridx = 5;
        gbc.gridy = zeile++;
        jlabel = new JLabel(hOMiniModel.getLanguageString("Angriffszentrum"));
        jlabel.setFont(jlabel.getFont().deriveFont(Font.BOLD));
        p3b.add(jlabel, gbc);

        //linkeAbwehrseite
        gbc.gridx = 1;
        gbc.gridy = zeile;
        jlabel = this.Team1DefenseLeft = new JLabel("");
        p3b.add(jlabel, gbc);

        gbc.gridx = 4;
        gbc.gridy = zeile;
        jlabel = this.Team2AttackRight = new JLabel("");
        p3b.add(jlabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = zeile;
        jlabel = TeamDif14 = new JLabel();
        p3b.add(jlabel, gbc);

        gbc.gridx = 3;
        gbc.gridy = zeile;
        jlabel = TeamDif24 = new JLabel();
        p3b.add(jlabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = zeile;
        jlabel = new JLabel(hOMiniModel.getLanguageString("linkeAbwehrseite"));
        jlabel.setFont(jlabel.getFont().deriveFont(Font.BOLD));
        p3b.add(jlabel, gbc);

        gbc.gridx = 5;
        gbc.gridy = zeile++;
        jlabel = new JLabel(hOMiniModel.getLanguageString("rechteAngriffsseite"));
        jlabel.setFont(jlabel.getFont().deriveFont(Font.BOLD));
        p3b.add(jlabel, gbc);

        //linkeAngriffsseite
        gbc.gridx = 1;
        gbc.gridy = zeile;
        jlabel = this.Team1AttackRight = new JLabel("");
        p3b.add(jlabel, gbc);

        gbc.gridx = 4;
        gbc.gridy = zeile;
        jlabel = this.Team2DefenseLeft = new JLabel("");
        p3b.add(jlabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = zeile;
        jlabel = TeamDif15 = new JLabel();
        p3b.add(jlabel, gbc);

        gbc.gridx = 3;
        gbc.gridy = zeile;
        jlabel = TeamDif25 = new JLabel();
        p3b.add(jlabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = zeile;
        jlabel = new JLabel(hOMiniModel.getLanguageString("rechteAngriffsseite"));
        jlabel.setFont(jlabel.getFont().deriveFont(Font.BOLD));
        p3b.add(jlabel, gbc);

        gbc.gridx = 5;
        gbc.gridy = zeile++;
        jlabel = new JLabel(hOMiniModel.getLanguageString("linkeAbwehrseite"));
        jlabel.setFont(jlabel.getFont().deriveFont(Font.BOLD));
        p3b.add(jlabel, gbc);

        //Angriffszentrum
        gbc.gridx = 1;
        gbc.gridy = zeile;
        jlabel = this.Team1AttackCenter = new JLabel("");
        p3b.add(jlabel, gbc);

        gbc.gridx = 4;
        gbc.gridy = zeile;
        jlabel = this.Team2DefenseCenter = new JLabel("");
        p3b.add(jlabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = zeile;
        jlabel = TeamDif16 = new JLabel();
        p3b.add(jlabel, gbc);

        gbc.gridx = 3;
        gbc.gridy = zeile;
        jlabel = TeamDif26 = new JLabel();
        p3b.add(jlabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = zeile;
        jlabel = new JLabel(hOMiniModel.getLanguageString("Angriffszentrum"));
        jlabel.setFont(jlabel.getFont().deriveFont(Font.BOLD));
        p3b.add(jlabel, gbc);

        gbc.gridx = 5;
        gbc.gridy = zeile++;
        jlabel = new JLabel(hOMiniModel.getLanguageString("Abwehrzentrum"));
        jlabel.setFont(jlabel.getFont().deriveFont(Font.BOLD));
        p3b.add(jlabel, gbc);

        // rechteAngriffsseite
        gbc.gridx = 1;
        gbc.gridy = zeile;
        jlabel = this.Team1AttackLeft = new JLabel("");
        p3b.add(jlabel, gbc);

        gbc.gridx = 4;
        gbc.gridy = zeile;
        jlabel = this.Team2DefenseRight = new JLabel("");
        p3b.add(jlabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = zeile;
        jlabel = TeamDif17 = new JLabel();
        p3b.add(jlabel, gbc);

        gbc.gridx = 3;
        gbc.gridy = zeile;
        jlabel = TeamDif27 = new JLabel();
        p3b.add(jlabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = zeile;
        jlabel = new JLabel(hOMiniModel.getLanguageString("linkeAngriffsseite"));
        jlabel.setFont(jlabel.getFont().deriveFont(Font.BOLD));
        p3b.add(jlabel, gbc);

        gbc.gridx = 5;
        gbc.gridy = zeile++;
        jlabel = new JLabel(hOMiniModel.getLanguageString("rechteAbwehrseite"));
        jlabel.setFont(jlabel.getFont().deriveFont(Font.BOLD));
        p3b.add(jlabel, gbc);

        // Einstellung
        gbc.gridx = 1;
        gbc.gridy = zeile;
        jlabel = this.Team1Attitude = new JLabel("");
        p3b.add(jlabel, gbc);

        gbc.gridx = 4;
        gbc.gridy = zeile;
        jlabel = this.Team2Attitude = new JLabel("");
        p3b.add(jlabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = zeile;
        jlabel = new JLabel(hOMiniModel.getLanguageString("Einstellung"));
        jlabel.setFont(jlabel.getFont().deriveFont(Font.BOLD));
        p3b.add(jlabel, gbc);

        gbc.gridx = 5;
        gbc.gridy = zeile++;
        jlabel = new JLabel(hOMiniModel.getLanguageString("Einstellung"));
        jlabel.setFont(jlabel.getFont().deriveFont(Font.BOLD));
        p3b.add(jlabel, gbc);

        // Taktik
        gbc.gridx = 1;
        gbc.gridy = zeile;
        jlabel = this.Team1Tactic = new JLabel("");
        p3b.add(jlabel, gbc);

        gbc.gridx = 4;
        gbc.gridy = zeile;
        jlabel = this.Team2Tactic = new JLabel("");
        p3b.add(jlabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = zeile;
        jlabel = new JLabel(hOMiniModel.getLanguageString("Taktik"));
        jlabel.setFont(jlabel.getFont().deriveFont(Font.BOLD));
        p3b.add(jlabel, gbc);

        gbc.gridx = 5;
        gbc.gridy = zeile++;
        jlabel = new JLabel(hOMiniModel.getLanguageString("Taktik"));
        jlabel.setFont(jlabel.getFont().deriveFont(Font.BOLD));
        p3b.add(jlabel, gbc);

        // Taktikstärke
        gbc.gridx = 1;
        gbc.gridy = zeile;
        jlabel = this.Team1Tacticskill = new JLabel("");
        p3b.add(jlabel, gbc);

        gbc.gridx = 4;
        gbc.gridy = zeile;
        jlabel = this.Team2Tacticskill = new JLabel("");
        p3b.add(jlabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = zeile;
        jlabel = new JLabel(hOMiniModel.getLanguageString("Taktikstaerke"));
        jlabel.setFont(jlabel.getFont().deriveFont(Font.BOLD));
        p3b.add(jlabel, gbc);

        gbc.gridx = 5;
        gbc.gridy = zeile++;
        jlabel = new JLabel(hOMiniModel.getLanguageString("Taktikstaerke"));
        jlabel.setFont(jlabel.getFont().deriveFont(Font.BOLD));
        p3b.add(jlabel, gbc);

        // Heim/Auswärts
        gbc.gridx = 1;
        gbc.gridy = zeile;
        jlabel = this.Team1HomeAway = new JLabel("");
        p3b.add(jlabel, gbc);

        gbc.gridx = 4;
        gbc.gridy = zeile;
        jlabel = this.Team2HomeAway = new JLabel("");
        p3b.add(jlabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = zeile;
        jlabel = new JLabel(hOMiniModel.getLanguageString("Heim") + "/"
                            + hOMiniModel.getLanguageString("Gast"));
        jlabel.setFont(jlabel.getFont().deriveFont(Font.BOLD));
        p3b.add(jlabel, gbc);

        gbc.gridx = 5;
        gbc.gridy = zeile++;
        jlabel = new JLabel(hOMiniModel.getLanguageString("Heim") + "/"
                            + hOMiniModel.getLanguageString("Gast"));
        jlabel.setFont(jlabel.getFont().deriveFont(Font.BOLD));
        p3b.add(jlabel, gbc);

        // Teamstimmung
        gbc.gridx = 1;
        gbc.gridy = zeile;
        jlabel = this.Team1Teamspirit = new JLabel("");
        p3b.add(jlabel, gbc);

        gbc.gridx = 4;
        gbc.gridy = zeile;
        jlabel = this.Team2Teamspirit = new JLabel("");
        p3b.add(jlabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = zeile;
        jlabel = new JLabel(hOMiniModel.getLanguageString("Stimmung"));
        jlabel.setFont(jlabel.getFont().deriveFont(Font.BOLD));
        p3b.add(jlabel, gbc);

        gbc.gridx = 5;
        gbc.gridy = zeile++;
        jlabel = new JLabel(hOMiniModel.getLanguageString("Stimmung"));
        jlabel.setFont(jlabel.getFont().deriveFont(Font.BOLD));
        p3b.add(jlabel, gbc);

        return p3b;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    public void setTeam1AttackCenter(double value) {
        this.Team1AttackCenter.setText(this.generateText(value));
        //this.Team1AttackCenterValue = value;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    public void setTeam1AttackLeft(double value) {
        this.Team1AttackLeft.setText(this.generateText(value));
        //this.Team1AttackLeftValue = value;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    public void setTeam1AttackRight(double value) {
        this.Team1AttackRight.setText(this.generateText(value));
        //this.Team1AttackRightValue = value;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    public void setTeam1Attitude(double value) {
        this.Team1Attitude.setText(this.generateText(value));
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    public void setTeam1DefenseCenter(double value) {
        this.Team1DefenseCenter.setText(this.generateText(value));
        //this.Team1DefenseCenterValue = value;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    public void setTeam1DefenseLeft(double value) {
        this.Team1DefenseLeft.setText(this.generateText(value));
        //this.Team1DefenseLeftValue = value;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    public void setTeam1DefenseRight(double value) {
        this.Team1DefenseRight.setText(this.generateText(value));
        //this.Team1DefenseRightValue = value;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    public void setTeam1HomeAway(double value) {
        this.Team1HomeAway.setText(this.generateText(value));
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    public void setTeam1Midfield(double value) {
        this.Team1Midfield.setText(this.generateText(value));
        //this.Team1MidfieldValue = value;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    public void setTeam1System(double value) {
        this.Team1System.setText(this.generateText(value));
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    public void setTeam1Tactic(double value) {
        this.Team1Tactic.setText(this.generateText(value));
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    public void setTeam1Tacticskill(double value) {
        this.Team1Tacticskill.setText(this.generateText(value));
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    public void setTeam1Teamspirit(double value) {
        this.Team1Teamspirit.setText(this.generateText(value));
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    public void setTeam2AttackCenter(double value) {
        this.Team2AttackCenter.setText(this.generateText(value));
        //this.Team2AttackCenterValue = value;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    public void setTeam2AttackLeft(double value) {
        this.Team2AttackLeft.setText(this.generateText(value));
        //this.Team2AttackLeftValue = value;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    public void setTeam2AttackRight(double value) {
        this.Team2AttackRight.setText(this.generateText(value));
        //this.Team2AttackRightValue = value;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    public void setTeam2Attitude(double value) {
        this.Team2Attitude.setText(this.generateText(value));
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    public void setTeam2DefenseCenter(double value) {
        this.Team2DefenseCenter.setText(this.generateText(value));
        //this.Team2DefenseCenterValue = value;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    public void setTeam2DefenseLeft(double value) {
        this.Team2DefenseLeft.setText(this.generateText(value));
        //this.Team2DefenseLeftValue = value;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    public void setTeam2DefenseRight(double value) {
        this.Team2DefenseRight.setText(this.generateText(value));
        //this.Team2DefenseRightValue = value;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    public void setTeam2HomeAway(double value) {
        this.Team2HomeAway.setText(this.generateText(value));
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    public void setTeam2Midfield(double value) {
        this.Team2Midfield.setText(this.generateText(value));
        //this.Team2MidfieldValue = value;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    public void setTeam2System(double value) {
        this.Team2System.setText(this.generateText(value));
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    public void setTeam2Tactic(double value) {
        this.Team2Tactic.setText(this.generateText(value));
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    public void setTeam2Tacticskill(double value) {
        this.Team2Tacticskill.setText(this.generateText(value));
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    public void setTeam2Teamspirit(double value) {
        this.Team2Teamspirit.setText(this.generateText(value));
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public boolean updateDifferences() {
        return true;
    }

    /**
     * DOCUMENT ME!
     *
     * @param value The value generating label string for.
     *
     * @return TODO Missing Return Method Documentation
     */
    private String generateText(double value) {
        String text = "";

        try {
            text = hOMiniModel.getHelper().getNameForBewertung((int) hOMiniModel.getHelper().round(value,
                                                                                                   1),
                                                               true, true);
        } catch (Exception e) {
            if (doDebug) {
                IDB.append("---ooo---");
                IDB.append(e);
            }
        }

        return text;
    }
}
