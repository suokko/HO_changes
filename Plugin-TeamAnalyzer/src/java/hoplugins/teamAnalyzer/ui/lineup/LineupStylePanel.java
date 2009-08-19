// %4118680904:hoplugins.teamAnalyzer.ui.lineup%
package hoplugins.teamAnalyzer.ui.lineup;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * An abstract Lineup jpanel
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public abstract class LineupStylePanel extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 6857727877436754893L;

	/** The main formation panel */
    protected FormationPanel mainPanel;

    /** GridBag Constraints */
    protected GridBagConstraints constraints = new GridBagConstraints();

    /** GridBag layout */
    protected GridBagLayout layout = new GridBagLayout();

    /** The main panel */
    protected JPanel centerPanel = new javax.swing.JPanel();

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Constructor
     *
     * @param _mainPanel the main formation panel
     */
    public LineupStylePanel(FormationPanel _mainPanel) {
        super();
        mainPanel = _mainPanel;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Sets the layout for 2 teams  displayed
     */
    public abstract void initCompare();

    /**
     * Sets the layout for only 1 team displayed
     */
    public abstract void initSingle();

    /**
     * Sets the rating bar in the proper line on the grid bag grid
     *
     * @param line
     */
    protected void setMidfieldRatingBar(int line) {
        constraints.gridx = 0;
        constraints.gridy = line;
        constraints.gridwidth = 4;
        layout.setConstraints(mainPanel.midfield, constraints);
        centerPanel.add(mainPanel.midfield);
    }

    /**
     * Sets a lineup line in the proper line on the grid bag grid
     *
     * @param line
     */
    protected void setMyAttack(int line) {
        JPanel m_clLinkerSturm;
        JPanel m_clRechterSturm;

        constraints.gridx = 2;
        constraints.gridy = line;
        constraints.gridwidth = 1;
        m_clLinkerSturm = mainPanel.getMyTeam().getLeftForwardPanel();
        m_clLinkerSturm.setOpaque(false);
        layout.setConstraints(m_clLinkerSturm, constraints);
        centerPanel.add(m_clLinkerSturm);

        constraints.gridx = 1;
        constraints.gridy = line;
        constraints.gridwidth = 1;
        m_clRechterSturm = mainPanel.getMyTeam().getRightForwardPanel();
        m_clRechterSturm.setOpaque(false);
        layout.setConstraints(m_clRechterSturm, constraints);
        centerPanel.add(m_clRechterSturm);
    }

    /**
     * Sets a lineup line in the proper line on the grid bag grid
     *
     * @param line
     */
    protected void setMyDefence(int line) {
        constraints.gridx = 3;
        constraints.gridy = line;
        constraints.gridwidth = 1;

        JPanel m_clRechteAussenVerteidiger = mainPanel.getMyTeam().getRightWingbackPanel();

        m_clRechteAussenVerteidiger.setOpaque(false);
        layout.setConstraints(m_clRechteAussenVerteidiger, constraints);
        centerPanel.add(m_clRechteAussenVerteidiger);

        constraints.gridx = 2;
        constraints.gridy = line;
        constraints.gridwidth = 1;

        JPanel m_clRechteInnenVerteidiger = mainPanel.getMyTeam().getRightCentralDefenderPanel();

        m_clRechteInnenVerteidiger.setOpaque(false);
        layout.setConstraints(m_clRechteInnenVerteidiger, constraints);
        centerPanel.add(m_clRechteInnenVerteidiger);

        constraints.gridx = 1;
        constraints.gridy = line;
        constraints.gridwidth = 1;

        JPanel m_clLinkeInnenVerteidiger = mainPanel.getMyTeam().getLeftCentralDefenderPanel();

        m_clLinkeInnenVerteidiger.setOpaque(false);
        layout.setConstraints(m_clLinkeInnenVerteidiger, constraints);
        centerPanel.add(m_clLinkeInnenVerteidiger);

        constraints.gridx = 0;
        constraints.gridy = line;
        constraints.gridwidth = 1;

        JPanel m_clLinkeAussenVerteidiger = mainPanel.getMyTeam().getLeftWingbackPanel();

        m_clLinkeAussenVerteidiger.setOpaque(false);
        layout.setConstraints(m_clLinkeAussenVerteidiger, constraints);
        centerPanel.add(m_clLinkeAussenVerteidiger);
    }

    /**
     * Sets a lineup line in the proper line on the grid bag grid
     *
     * @param line
     */
    protected void setMyKeeper(int line) {
        JPanel m_clTorwart;

        constraints.gridx = 0;
        constraints.gridy = line;
        constraints.gridwidth = 4;
        m_clTorwart = mainPanel.getMyTeam().getKeeperPanel();
        m_clTorwart.setOpaque(false);
        layout.setConstraints(m_clTorwart, constraints);
        centerPanel.add(m_clTorwart);
    }

    /**
     * Sets a lineup line in the proper line on the grid bag grid
     *
     * @param line
     */
    protected void setMyMidfield(int line) {
        JPanel m_clRechteFluegel;
        JPanel m_clRechteMittelfeld;
        JPanel m_clLinkeMittelfeld;
        JPanel m_clLinkeFluegel;

        constraints.gridx = 3;
        constraints.gridy = line;
        constraints.gridwidth = 1;
        m_clRechteFluegel = mainPanel.getMyTeam().getRightWingPanel();
        m_clRechteFluegel.setOpaque(false);
        layout.setConstraints(m_clRechteFluegel, constraints);
        centerPanel.add(m_clRechteFluegel);

        constraints.gridx = 2;
        constraints.gridy = line;
        constraints.gridwidth = 1;
        m_clRechteMittelfeld = mainPanel.getMyTeam().getRightMidfieldPanel();
        m_clRechteMittelfeld.setOpaque(false);
        layout.setConstraints(m_clRechteMittelfeld, constraints);
        centerPanel.add(m_clRechteMittelfeld);

        constraints.gridx = 1;
        constraints.gridy = line;
        constraints.gridwidth = 1;
        m_clLinkeMittelfeld = mainPanel.getMyTeam().getLeftMidfieldPanel();
        m_clLinkeMittelfeld.setOpaque(false);
        layout.setConstraints(m_clLinkeMittelfeld, constraints);
        centerPanel.add(m_clLinkeMittelfeld);

        constraints.gridx = 0;
        constraints.gridy = line;
        constraints.gridwidth = 1;
        m_clLinkeFluegel = mainPanel.getMyTeam().getLeftWingPanel();
        m_clLinkeFluegel.setOpaque(false);
        layout.setConstraints(m_clLinkeFluegel, constraints);
        centerPanel.add(m_clLinkeFluegel);
    }

    /**
     * Sets the user team title panel on the grid
     *
     * @param line
     */
    protected void setMyPanel(int line) {
        JLabel m_jlTeamName;

        constraints.gridx = 0;
        constraints.gridy = line;
        constraints.gridwidth = 4;
        m_jlTeamName = mainPanel.getMyTeam().getTeamPanel();
        m_jlTeamName.setOpaque(false);
        m_jlTeamName.setForeground(Color.white);
        m_jlTeamName.setFont(m_jlTeamName.getFont().deriveFont(Font.BOLD,
                                                               gui.UserParameter.instance().schriftGroesse
                                                               + 3));
        layout.setConstraints(m_jlTeamName, constraints);
        centerPanel.add(m_jlTeamName);
    }

    /**
     * Sets a lineup line in the proper line on the grid bag grid
     *
     * @param line
     */
    protected void setOpponentAttack(int line) {
        constraints.gridx = 1;
        constraints.gridy = line;
        constraints.gridwidth = 1;

        JPanel m_clLinkerSturm = mainPanel.getOpponentTeam().getLeftForwardPanel();

        m_clLinkerSturm.setOpaque(false);
        layout.setConstraints(m_clLinkerSturm, constraints);
        centerPanel.add(m_clLinkerSturm);

        constraints.gridx = 2;
        constraints.gridy = line;
        constraints.gridwidth = 1;

        JPanel m_clRechterSturm = mainPanel.getOpponentTeam().getRightForwardPanel();

        m_clRechterSturm.setOpaque(false);
        layout.setConstraints(m_clRechterSturm, constraints);
        centerPanel.add(m_clRechterSturm);
    }

    /**
     * Sets a lineup line in the proper line on the grid bag grid
     *
     * @param line
     */
    protected void setOpponentDefence(int line) {
        constraints.gridx = 0;
        constraints.gridy = line;
        constraints.gridwidth = 1;

        JPanel m_clRechteAussenVerteidiger = mainPanel.getOpponentTeam().getRightWingbackPanel();

        m_clRechteAussenVerteidiger.setOpaque(false);
        layout.setConstraints(m_clRechteAussenVerteidiger, constraints);
        centerPanel.add(m_clRechteAussenVerteidiger);

        constraints.gridx = 1;
        constraints.gridy = line;
        constraints.gridwidth = 1;

        JPanel m_clRechteInnenVerteidiger = mainPanel.getOpponentTeam()
                                                     .getRightCentralDefenderPanel();

        m_clRechteInnenVerteidiger.setOpaque(false);
        layout.setConstraints(m_clRechteInnenVerteidiger, constraints);
        centerPanel.add(m_clRechteInnenVerteidiger);

        constraints.gridx = 2;
        constraints.gridy = line;
        constraints.gridwidth = 1;

        JPanel m_clLinkeInnenVerteidiger = mainPanel.getOpponentTeam().getLeftCentralDefenderPanel();

        m_clLinkeInnenVerteidiger.setOpaque(false);
        layout.setConstraints(m_clLinkeInnenVerteidiger, constraints);
        centerPanel.add(m_clLinkeInnenVerteidiger);

        constraints.gridx = 3;
        constraints.gridy = line;
        constraints.gridwidth = 1;

        JPanel m_clLinkeAussenVerteidiger = mainPanel.getOpponentTeam().getLeftWingbackPanel();

        m_clLinkeAussenVerteidiger.setOpaque(false);
        layout.setConstraints(m_clLinkeAussenVerteidiger, constraints);
        centerPanel.add(m_clLinkeAussenVerteidiger);
    }

    /**
     * Sets a lineup line in the proper line on the grid bag grid
     *
     * @param line
     */
    protected void setOpponentKeeper(int line) {
        constraints.gridx = 0;
        constraints.gridy = line;
        constraints.gridwidth = 4;

        JPanel m_clTorwart = mainPanel.getOpponentTeam().getKeeperPanel();

        m_clTorwart.setOpaque(false);
        layout.setConstraints(m_clTorwart, constraints);
        centerPanel.add(m_clTorwart);
    }

    /**
     * Sets a lineup line in the proper line on the grid bag grid
     *
     * @param line
     */
    protected void setOpponentMidfield(int line) {
        constraints.gridx = 0;
        constraints.gridy = line;
        constraints.gridwidth = 1;

        JPanel m_clRechteFluegel = mainPanel.getOpponentTeam().getRightWingPanel();

        m_clRechteFluegel.setOpaque(false);
        layout.setConstraints(m_clRechteFluegel, constraints);
        centerPanel.add(m_clRechteFluegel);

        constraints.gridx = 1;
        constraints.gridy = line;
        constraints.gridwidth = 1;

        JPanel m_clRechteMittelfeld = mainPanel.getOpponentTeam().getRightMidfieldPanel();

        m_clRechteMittelfeld.setOpaque(false);
        layout.setConstraints(m_clRechteMittelfeld, constraints);
        centerPanel.add(m_clRechteMittelfeld);

        constraints.gridx = 2;
        constraints.gridy = line;
        constraints.gridwidth = 1;

        JPanel m_clLinkeMittelfeld = mainPanel.getOpponentTeam().getLeftMidfieldPanel();

        m_clLinkeMittelfeld.setOpaque(false);
        layout.setConstraints(m_clLinkeMittelfeld, constraints);
        centerPanel.add(m_clLinkeMittelfeld);

        constraints.gridx = 3;
        constraints.gridy = line;
        constraints.gridwidth = 1;

        JPanel m_clLinkeFluegel = mainPanel.getOpponentTeam().getLeftWingPanel();

        m_clLinkeFluegel.setOpaque(false);
        layout.setConstraints(m_clLinkeFluegel, constraints);
        centerPanel.add(m_clLinkeFluegel);
    }

    /**
     * Sets the opponent team title panel on the grid
     *
     * @param line
     */
    protected void setOpponentPanel(int line) {
        constraints.gridx = 0;
        constraints.gridy = line;
        constraints.gridwidth = 4;

        JLabel m_jlTeamName = mainPanel.getOpponentTeam().getTeamPanel();

        m_jlTeamName.setOpaque(false);
        m_jlTeamName.setForeground(Color.white);
        m_jlTeamName.setFont(m_jlTeamName.getFont().deriveFont(Font.BOLD,
                                                               gui.UserParameter.instance().schriftGroesse
                                                               + 3));
        layout.setConstraints(m_jlTeamName, constraints);
        centerPanel.add(m_jlTeamName);
    }

    /**
     * Sets the rating bar in the proper line on the grid bag grid
     *
     * @param line
     */
    protected void setRatingBar1(int line) {
        constraints.gridy = line;
        constraints.gridwidth = 1;

        constraints.gridx = 3;
        layout.setConstraints(mainPanel.leftDef, constraints);
        centerPanel.add(mainPanel.leftDef);

        constraints.gridx = 0;
        layout.setConstraints(mainPanel.rightDef, constraints);
        centerPanel.add(mainPanel.rightDef);

        constraints.gridx = 1;
        constraints.gridwidth = 2;
        layout.setConstraints(mainPanel.midDef, constraints);
        centerPanel.add(mainPanel.midDef);
    }

    /**
     * Sets the rating bar in the proper line on the grid bag grid
     *
     * @param line
     */
    protected void setRatingBar2(int line) {
        constraints.gridy = line;
        constraints.gridwidth = 1;

        constraints.gridx = 3;
        layout.setConstraints(mainPanel.leftAtt, constraints);
        centerPanel.add(mainPanel.leftAtt);

        constraints.gridx = 0;
        layout.setConstraints(mainPanel.rightAtt, constraints);
        centerPanel.add(mainPanel.rightAtt);

        constraints.gridx = 1;
        constraints.gridwidth = 2;
        layout.setConstraints(mainPanel.midAtt, constraints);
        centerPanel.add(mainPanel.midAtt);
    }
}
