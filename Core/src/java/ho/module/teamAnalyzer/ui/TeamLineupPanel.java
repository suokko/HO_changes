package ho.module.teamAnalyzer.ui;

import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * This is an empty panel to display a lineup
 */
public class TeamLineupPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    /** The Panels and Label */
    private JLabel m_jlTeamName = new JLabel();
    private JPanel m_clLinkeAussenVerteidiger = new JPanel();
    private JPanel m_clLinkeFluegel = new JPanel();
    private JPanel m_clLinkeInnenVerteidiger = new JPanel();
    private JPanel m_clLinkeMittelfeld = new JPanel();
    private JPanel m_clLinkerSturm = new JPanel();
    private JPanel m_clRechteAussenVerteidiger = new JPanel();
    private JPanel m_clRechteFluegel = new JPanel();
    private JPanel m_clRechteInnenVerteidiger = new JPanel();
    private JPanel m_clRechteMittelfeld = new JPanel();
    private JPanel m_clRechterSturm = new JPanel();
    private JPanel m_clTorwart = new JPanel();
    private double leftAttack;
    private double leftDefence;
    private double middleAttack;
    private double middleDefence;
    private double midfield;
    private double rightAttack;
    private double rightDefence;

    //~ Methods ------------------------------------------------------------------------------------
    /**
     * Get the Panel Keeper
     */
    public JPanel getKeeperPanel() {
        return m_clTorwart;
    }

    public void setLeftAttack(double i) {
        leftAttack = i;
    }

    public double getLeftAttack() {
        return leftAttack;
    }

    /**
     * Get the Panel Left Central Defender
     */
    public JPanel getLeftCentralDefenderPanel() {
        return m_clLinkeInnenVerteidiger;
    }

    public void setLeftDefence(double i) {
        leftDefence = i;
    }

    public double getLeftDefence() {
        return leftDefence;
    }

    /**
     * Get the Panel Left Forward
     */
    public JPanel getLeftForwardPanel() {
        return m_clLinkerSturm;
    }

    /**
     * Get the Panel Left Midfield
     */
    public JPanel getLeftMidfieldPanel() {
        return m_clLinkeMittelfeld;
    }

    /**
     * Get the Panel Left Wing
     */
    public JPanel getLeftWingPanel() {
        return m_clLinkeFluegel;
    }

    /**
     * Get the Panel Left Wingback
     */
    public JPanel getLeftWingbackPanel() {
        return m_clLinkeAussenVerteidiger;
    }

    public void setMiddleAttack(double i) {
        middleAttack = i;
    }

    public double getMiddleAttack() {
        return middleAttack;
    }

    public void setMiddleDefence(double i) {
        middleDefence = i;
    }

    public double getMiddleDefence() {
        return middleDefence;
    }

    public void setMidfield(double i) {
        midfield = i;
    }

    public double getMidfield() {
        return midfield;
    }

    public void setRightAttack(double i) {
        rightAttack = i;
    }

    public double getRightAttack() {
        return rightAttack;
    }

    /**
     * Get the Panel Right Centraldefender
     */
    public JPanel getRightCentralDefenderPanel() {
        return m_clRechteInnenVerteidiger;
    }

    public void setRightDefence(double i) {
        rightDefence = i;
    }

    public double getRightDefence() {
        return rightDefence;
    }

    /**
     * Get the Panel Right Forward
     */
    public JPanel getRightForwardPanel() {
        return m_clRechterSturm;
    }

    /**
     * Get the Panel Right Midfield
     */
    public JPanel getRightMidfieldPanel() {
        return m_clRechteMittelfeld;
    }

    /**
     * Get the Panel Right Wing
     */
    public JPanel getRightWingPanel() {
        return m_clRechteFluegel;
    }

    /**
     * Get the Panel Right Wingback
     */
    public JPanel getRightWingbackPanel() {
        return m_clRechteAussenVerteidiger;
    }

    //-- Getter/Setter ----------------------------------------------------------

    /**
     * Set the Name of the Team
     */
    public void setTeamName(String teamname) {
        m_jlTeamName.setText(teamname);
    }

    /**
     * Get the Name of the Team
     */
    public JLabel getTeamPanel() {
        return m_jlTeamName;
    }
}
