// %2956927164:plugins%
package plugins;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * This is an empty panel to display a lineup
 */
public class LineupPanel extends JPanel {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** Shows the Lineup with Keeper on top */
    public static boolean LINEUP_NORMAL_SEQUENCE = true;

    /** TODO Missing Parameter Documentation */
    public static boolean LINEUP_REVERSE_SEQUENCE;

    //~ Instance fields ----------------------------------------------------------------------------

    /** The Panels and Label */
    private JLabel m_jlTeamName;
    private JPanel m_clAusgewechselt1;
    private JPanel m_clAusgewechselt2;
    private JPanel m_clAusgewechselt3;
    private JPanel m_clLinkeAussenVerteidiger;
    private JPanel m_clLinkeFluegel;
    private JPanel m_clLinkeInnenVerteidiger;
    private JPanel m_clLinkeMittelfeld;
    private JPanel m_clLinkerSturm;
    private JPanel m_clRechteAussenVerteidiger;
    private JPanel m_clRechteFluegel;
    private JPanel m_clRechteInnenVerteidiger;
    private JPanel m_clRechteMittelfeld;
    private JPanel m_clRechterSturm;
    private JPanel m_clReserveFluegel;
    private JPanel m_clReserveMittelfeld;
    private JPanel m_clReserveSturm;
    private JPanel m_clReserveTorwart;
    private JPanel m_clReserveVerteidiger;
    private JPanel m_clSpielfuehrer;
    private JPanel m_clStandard;
    private JPanel m_clTorwart;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Constructor
     *
     * @param flags Sequence
     */
    public LineupPanel(boolean flags) {
        super();

        initComponents(flags);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Get the Panel Injured1
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getInjured1Panel() {
        return m_clAusgewechselt1;
    }

    /**
     * Get the Panel Injured2
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getInjured2Panel() {
        return m_clAusgewechselt2;
    }

    /**
     * Get the Panel Injured3
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getInjured3Panel() {
        return m_clAusgewechselt3;
    }

    /**
     * Get the Panel Keeper
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getKeeperPanel() {
        return m_clTorwart;
    }

    /**
     * Get the Panel Left Central Defender
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getLeftCentralDefenderPanel() {
        return m_clLinkeInnenVerteidiger;
    }

    /**
     * Get the Panel Left Forward
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getLeftForwardPanel() {
        return m_clLinkerSturm;
    }

    /**
     * Get the Panel Left Midfield
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getLeftMidfieldPanel() {
        return m_clLinkeMittelfeld;
    }

    /**
     * Get the Panel Left Wing
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getLeftWingPanel() {
        return m_clLinkeFluegel;
    }

    /**
     * Get the Panel Left Wingback
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getLeftWingbackPanel() {
        return m_clLinkeAussenVerteidiger;
    }

    /**
     * Get the Panel Reserve Defender
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getReserveDefenderPanel() {
        return m_clReserveVerteidiger;
    }

    /**
     * Get the Panel Reserve Forward
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getReserveForwardPanel() {
        return m_clReserveSturm;
    }

    /**
     * Get the Panel Reserve Keeper
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getReserveKeeperPanel() {
        return m_clReserveTorwart;
    }

    /**
     * Get the Panel Reserve Midfield
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getReserveMidfieldPanel() {
        return m_clReserveMittelfeld;
    }

    /**
     * Get the Panel Reserve Wing
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getReserveWingPanel() {
        return m_clReserveFluegel;
    }

    /**
     * Get the Panel Right Centraldefender
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getRightCentralDefenderPanel() {
        return m_clRechteInnenVerteidiger;
    }

    /**
     * Get the Panel Right Forward
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getRightForwardPanel() {
        return m_clRechterSturm;
    }

    /**
     * Get the Panel Right Midfield
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getRightMidfieldPanel() {
        return m_clRechteMittelfeld;
    }

    /**
     * Get the Panel Right Wing
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getRightWingPanel() {
        return m_clRechteFluegel;
    }

    /**
     * Get the Panel Right Wingback
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getRightWingbackPanel() {
        return m_clRechteAussenVerteidiger;
    }

    /**
     * Get the Panel Set Pieces
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getSetPiecesPanel() {
        return m_clStandard;
    }

    /**
     * Get the Panel Teamleader
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getTeamLeaderPanel() {
        return m_clSpielfuehrer;
    }

    //-- Getter/Setter ----------------------------------------------------------

    /**
     * Set the Name of the Team
     *
     * @param teamname TODO Missing Constructuor Parameter Documentation
     */
    public final void setTeamName(String teamname) {
        m_jlTeamName.setText(teamname);
    }

    /**
     * Get the Name of the Team
     *
     * @return TODO Missing Return Method Documentation
     */
    public final String getTeamName() {
        return m_jlTeamName.getText();
    }

    //--------------------------------------------------------------------------

    /**
     * Setup the layout
     *
     * @param flags TODO Missing Constructuor Parameter Documentation
     */
    private void initComponents(boolean flags) {
        setLayout(new BorderLayout());
        setOpaque(false);

        final javax.swing.JPanel centerPanel = new javax.swing.JPanel();
        centerPanel.setOpaque(false);

        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.insets = new Insets(2, 2, 2, 2);

        centerPanel.setLayout(layout);

        if (flags) {
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.gridwidth = 4;
        } else {
            constraints.gridx = 0;
            constraints.gridy = 8;
            constraints.gridwidth = 4;
        }

        m_jlTeamName = new JLabel();
        m_jlTeamName.setOpaque(false);
        m_jlTeamName.setForeground(Color.white);
        m_jlTeamName.setFont(m_jlTeamName.getFont().deriveFont(Font.BOLD,
                                                               gui.UserParameter.instance().schriftGroesse
                                                               + 3));
        layout.setConstraints(m_jlTeamName, constraints);
        centerPanel.add(m_jlTeamName);

        if (flags) {
            constraints.gridx = 0;
            constraints.gridy = 4;
            constraints.gridwidth = 4;
        } else {
            constraints.gridx = 0;
            constraints.gridy = 4;
            constraints.gridwidth = 4;
        }

        m_clTorwart = new JPanel();
        m_clTorwart.setOpaque(false);
        layout.setConstraints(m_clTorwart, constraints);
        centerPanel.add(m_clTorwart);

        if (flags) {
            constraints.gridx = 0;
            constraints.gridy = 5;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 3;
            constraints.gridy = 3;
            constraints.gridwidth = 1;
        }

        m_clRechteAussenVerteidiger = new JPanel();
        m_clRechteAussenVerteidiger.setOpaque(false);
        layout.setConstraints(m_clRechteAussenVerteidiger, constraints);
        centerPanel.add(m_clRechteAussenVerteidiger);

        if (flags) {
            constraints.gridx = 1;
            constraints.gridy = 5;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 2;
            constraints.gridy = 3;
            constraints.gridwidth = 1;
        }

        m_clRechteInnenVerteidiger = new JPanel();
        m_clRechteInnenVerteidiger.setOpaque(false);
        layout.setConstraints(m_clRechteInnenVerteidiger, constraints);
        centerPanel.add(m_clRechteInnenVerteidiger);

        if (flags) {
            constraints.gridx = 2;
            constraints.gridy = 5;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 1;
            constraints.gridy = 3;
            constraints.gridwidth = 1;
        }

        m_clLinkeInnenVerteidiger = new JPanel();
        m_clLinkeInnenVerteidiger.setOpaque(false);
        layout.setConstraints(m_clLinkeInnenVerteidiger, constraints);
        centerPanel.add(m_clLinkeInnenVerteidiger);

        if (flags) {
            constraints.gridx = 3;
            constraints.gridy = 5;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 0;
            constraints.gridy = 3;
            constraints.gridwidth = 1;
        }

        m_clLinkeAussenVerteidiger = new JPanel();
        m_clLinkeAussenVerteidiger.setOpaque(false);
        layout.setConstraints(m_clLinkeAussenVerteidiger, constraints);
        centerPanel.add(m_clLinkeAussenVerteidiger);

        if (flags) {
            constraints.gridx = 0;
            constraints.gridy = 6;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 3;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
        }

        m_clRechteFluegel = new JPanel();
        m_clRechteFluegel.setOpaque(false);
        layout.setConstraints(m_clRechteFluegel, constraints);
        centerPanel.add(m_clRechteFluegel);

        if (flags) {
            constraints.gridx = 1;
            constraints.gridy = 6;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 2;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
        }

        m_clRechteMittelfeld = new JPanel();
        m_clRechteMittelfeld.setOpaque(false);
        layout.setConstraints(m_clRechteMittelfeld, constraints);
        centerPanel.add(m_clRechteMittelfeld);

        if (flags) {
            constraints.gridx = 2;
            constraints.gridy = 6;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 1;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
        }

        m_clLinkeMittelfeld = new JPanel();
        m_clLinkeMittelfeld.setOpaque(false);
        layout.setConstraints(m_clLinkeMittelfeld, constraints);
        centerPanel.add(m_clLinkeMittelfeld);

        if (flags) {
            constraints.gridx = 3;
            constraints.gridy = 6;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 0;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
        }

        m_clLinkeFluegel = new JPanel();
        m_clLinkeFluegel.setOpaque(false);
        layout.setConstraints(m_clLinkeFluegel, constraints);
        centerPanel.add(m_clLinkeFluegel);

        if (flags) {
            constraints.gridx = 1;
            constraints.gridy = 7;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 2;
            constraints.gridy = 1;
            constraints.gridwidth = 1;
        }

        m_clLinkerSturm = new JPanel();
        m_clLinkerSturm.setOpaque(false);
        layout.setConstraints(m_clLinkerSturm, constraints);
        centerPanel.add(m_clLinkerSturm);

        if (flags) {
            constraints.gridx = 2;
            constraints.gridy = 7;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 1;
            constraints.gridy = 1;
            constraints.gridwidth = 1;
        }

        m_clRechterSturm = new JPanel();
        m_clRechterSturm.setOpaque(false);
        layout.setConstraints(m_clRechterSturm, constraints);
        centerPanel.add(m_clRechterSturm);

        if (flags) {
            constraints.gridx = 0;
            constraints.gridy = 3;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 0;
            constraints.gridy = 5;
            constraints.gridwidth = 1;
        }

        m_clStandard = new JPanel();
        m_clStandard.setOpaque(false);
        layout.setConstraints(m_clStandard, constraints);
        centerPanel.add(m_clStandard);

        if (flags) {
            constraints.gridx = 1;
            constraints.gridy = 3;
            constraints.gridwidth = 2;
        } else {
            constraints.gridx = 1;
            constraints.gridy = 5;
            constraints.gridwidth = 2;
        }

        m_clReserveTorwart = new JPanel();
        m_clReserveTorwart.setOpaque(false);
        layout.setConstraints(m_clReserveTorwart, constraints);
        centerPanel.add(m_clReserveTorwart);

        if (flags) {
            constraints.gridx = 3;
            constraints.gridy = 3;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 3;
            constraints.gridy = 5;
            constraints.gridwidth = 1;
        }

        m_clSpielfuehrer = new JPanel();
        m_clSpielfuehrer.setOpaque(false);
        layout.setConstraints(m_clSpielfuehrer, constraints);
        centerPanel.add(m_clSpielfuehrer);

        if (flags) {
            constraints.gridx = 0;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 0;
            constraints.gridy = 6;
            constraints.gridwidth = 1;
        }

        m_clReserveVerteidiger = new JPanel();
        m_clReserveVerteidiger.setOpaque(false);
        layout.setConstraints(m_clReserveVerteidiger, constraints);
        centerPanel.add(m_clReserveVerteidiger);

        if (flags) {
            constraints.gridx = 1;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 1;
            constraints.gridy = 6;
            constraints.gridwidth = 1;
        }

        m_clReserveMittelfeld = new JPanel();
        m_clReserveMittelfeld.setOpaque(false);
        layout.setConstraints(m_clReserveMittelfeld, constraints);
        centerPanel.add(m_clReserveMittelfeld);

        if (flags) {
            constraints.gridx = 2;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 2;
            constraints.gridy = 6;
            constraints.gridwidth = 1;
        }

        m_clReserveSturm = new JPanel();
        m_clReserveSturm.setOpaque(false);
        layout.setConstraints(m_clReserveSturm, constraints);
        centerPanel.add(m_clReserveSturm);

        if (flags) {
            constraints.gridx = 3;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 3;
            constraints.gridy = 6;
            constraints.gridwidth = 1;
        }

        m_clReserveFluegel = new JPanel();
        m_clReserveFluegel.setOpaque(false);
        layout.setConstraints(m_clReserveFluegel, constraints);
        centerPanel.add(m_clReserveFluegel);

        if (flags) {
            constraints.gridx = 0;
            constraints.gridy = 1;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 0;
            constraints.gridy = 7;
            constraints.gridwidth = 1;
        }

        final JLabel label = new JLabel();
        label.setOpaque(true);
        layout.setConstraints(label, constraints);
        centerPanel.add(label);

        if (flags) {
            constraints.gridx = 1;
            constraints.gridy = 1;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 1;
            constraints.gridy = 7;
            constraints.gridwidth = 1;
        }

        m_clAusgewechselt1 = new JPanel();
        m_clAusgewechselt1.setOpaque(false);
        layout.setConstraints(m_clAusgewechselt1, constraints);
        centerPanel.add(m_clAusgewechselt1);

        if (flags) {
            constraints.gridx = 2;
            constraints.gridy = 1;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 2;
            constraints.gridy = 7;
            constraints.gridwidth = 1;
        }

        m_clAusgewechselt2 = new JPanel();
        m_clAusgewechselt2.setOpaque(false);
        layout.setConstraints(m_clAusgewechselt2, constraints);
        centerPanel.add(m_clAusgewechselt2);

        if (flags) {
            constraints.gridx = 3;
            constraints.gridy = 1;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 3;
            constraints.gridy = 7;
            constraints.gridwidth = 1;
        }

        m_clAusgewechselt3 = new JPanel();
        m_clAusgewechselt3.setOpaque(false);
        layout.setConstraints(m_clAusgewechselt3, constraints);
        centerPanel.add(m_clAusgewechselt3);

        add(centerPanel, BorderLayout.CENTER);
    }
}
