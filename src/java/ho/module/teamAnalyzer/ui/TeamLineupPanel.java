// %2482368348:hoplugins.teamAnalyzer.ui%
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
     *
     * @return TODO Missing Return Method Documentation
     */
    public JPanel getKeeperPanel() {
        return m_clTorwart;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     */
    public void setLeftAttack(double i) {
        leftAttack = i;
    }

    //	public JPanel getM_clLinkeAussenVerteidiger() {
    //		return m_clLinkeAussenVerteidiger;
    //	}
    //
    //	public JPanel getM_clLinkeFluegel() {
    //		return m_clLinkeFluegel;
    //	}
    //
    //	public JPanel getM_clLinkeInnenVerteidiger() {
    //		return m_clLinkeInnenVerteidiger;
    //	}
    //
    //	public JPanel getM_clLinkeMittelfeld() {
    //		return m_clLinkeMittelfeld;
    //	}
    //
    //	public JPanel getM_clLinkerSturm() {
    //		return m_clLinkerSturm;
    //	}
    //
    //	public JPanel getM_clRechteAussenVerteidiger() {
    //		return m_clRechteAussenVerteidiger;
    //	}
    //
    //	public JPanel getM_clRechteFluegel() {
    //		return m_clRechteFluegel;
    //	}
    //
    //	public JPanel getM_clRechteInnenVerteidiger() {
    //		return m_clRechteInnenVerteidiger;
    //	}
    //
    //	public JPanel getM_clRechteMittelfeld() {
    //		return m_clRechteMittelfeld;
    //	}
    //
    //	public JPanel getM_clRechterSturm() {
    //		return m_clRechterSturm;
    //	}
    //
    //	public JPanel getM_clTorwart() {
    //		return m_clTorwart;
    //	}
    //
    //	public JLabel getM_jlTeamName() {
    //		return m_jlTeamName;
    //	}
    //
    //
    //	public void setM_clLinkeAussenVerteidiger(JPanel panel) {
    //		m_clLinkeAussenVerteidiger = panel;
    //	}
    //
    //	public void setM_clLinkeFluegel(JPanel panel) {
    //		m_clLinkeFluegel = panel;
    //	}
    //
    //	public void setM_clLinkeInnenVerteidiger(JPanel panel) {
    //		m_clLinkeInnenVerteidiger = panel;
    //	}
    //
    //	public void setM_clLinkeMittelfeld(JPanel panel) {
    //		m_clLinkeMittelfeld = panel;
    //	}
    //
    //	public void setM_clLinkerSturm(JPanel panel) {
    //		m_clLinkerSturm = panel;
    //	}
    //
    //	public void setM_clRechteAussenVerteidiger(JPanel panel) {
    //		m_clRechteAussenVerteidiger = panel;
    //	}
    //
    //	public void setM_clRechteFluegel(JPanel panel) {
    //		m_clRechteFluegel = panel;
    //	}
    //
    //	public void setM_clRechteInnenVerteidiger(JPanel panel) {
    //		m_clRechteInnenVerteidiger = panel;
    //	}
    //
    //	public void setM_clRechteMittelfeld(JPanel panel) {
    //		m_clRechteMittelfeld = panel;
    //	}
    //
    //	public void setM_clRechterSturm(JPanel panel) {
    //		m_clRechterSturm = panel;
    //	}
    //
    //	public void setM_clTorwart(JPanel panel) {
    //		m_clTorwart = panel;
    //	}
    //
    //	public void setM_jlTeamName(JLabel label) {
    //		m_jlTeamName = label;
    //	}
    public double getLeftAttack() {
        return leftAttack;
    }

    /**
     * Get the Panel Left Central Defender
     *
     * @return TODO Missing Return Method Documentation
     */
    public JPanel getLeftCentralDefenderPanel() {
        return m_clLinkeInnenVerteidiger;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     */
    public void setLeftDefence(double i) {
        leftDefence = i;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public double getLeftDefence() {
        return leftDefence;
    }

    /**
     * Get the Panel Left Forward
     *
     * @return TODO Missing Return Method Documentation
     */
    public JPanel getLeftForwardPanel() {
        return m_clLinkerSturm;
    }

    /**
     * Get the Panel Left Midfield
     *
     * @return TODO Missing Return Method Documentation
     */
    public JPanel getLeftMidfieldPanel() {
        return m_clLinkeMittelfeld;
    }

    /**
     * Get the Panel Left Wing
     *
     * @return TODO Missing Return Method Documentation
     */
    public JPanel getLeftWingPanel() {
        return m_clLinkeFluegel;
    }

    /**
     * Get the Panel Left Wingback
     *
     * @return TODO Missing Return Method Documentation
     */
    public JPanel getLeftWingbackPanel() {
        return m_clLinkeAussenVerteidiger;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     */
    public void setMiddleAttack(double i) {
        middleAttack = i;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public double getMiddleAttack() {
        return middleAttack;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     */
    public void setMiddleDefence(double i) {
        middleDefence = i;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public double getMiddleDefence() {
        return middleDefence;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     */
    public void setMidfield(double i) {
        midfield = i;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public double getMidfield() {
        return midfield;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     */
    public void setRightAttack(double i) {
        rightAttack = i;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public double getRightAttack() {
        return rightAttack;
    }

    /**
     * Get the Panel Right Centraldefender
     *
     * @return TODO Missing Return Method Documentation
     */
    public JPanel getRightCentralDefenderPanel() {
        return m_clRechteInnenVerteidiger;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     */
    public void setRightDefence(double i) {
        rightDefence = i;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public double getRightDefence() {
        return rightDefence;
    }

    /**
     * Get the Panel Right Forward
     *
     * @return TODO Missing Return Method Documentation
     */
    public JPanel getRightForwardPanel() {
        return m_clRechterSturm;
    }

    /**
     * Get the Panel Right Midfield
     *
     * @return TODO Missing Return Method Documentation
     */
    public JPanel getRightMidfieldPanel() {
        return m_clRechteMittelfeld;
    }

    /**
     * Get the Panel Right Wing
     *
     * @return TODO Missing Return Method Documentation
     */
    public JPanel getRightWingPanel() {
        return m_clRechteFluegel;
    }

    /**
     * Get the Panel Right Wingback
     *
     * @return TODO Missing Return Method Documentation
     */
    public JPanel getRightWingbackPanel() {
        return m_clRechteAussenVerteidiger;
    }

    //-- Getter/Setter ----------------------------------------------------------

    /**
     * Set the Name of the Team
     *
     * @param teamname TODO Missing Constructuor Parameter Documentation
     */
    public void setTeamName(String teamname) {
        m_jlTeamName.setText(teamname);
    }

    /**
     * Get the Name of the Team
     *
     * @return TODO Missing Return Method Documentation
     */
    public JLabel getTeamPanel() {
        return m_jlTeamName;
    }
}
