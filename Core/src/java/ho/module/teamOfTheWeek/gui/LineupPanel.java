// %2956927164:plugins%
package ho.module.teamOfTheWeek.gui;

import ho.core.gui.theme.HOColorName;
import ho.core.gui.theme.ThemeManager;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;



/**
 * This is an empty panel to display a lineup
 */
class LineupPanel extends JPanel {
    //~ Static fields/initializers -----------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -1373544624896628833L;

	/** Shows the Lineup with Keeper on top */
    public static boolean LINEUP_NORMAL_SEQUENCE = true;

    /** TODO Missing Parameter Documentation */
    public static boolean LINEUP_REVERSE_SEQUENCE;

    //~ Instance fields ----------------------------------------------------------------------------

    /** The Panels and Label */
    private JLabel m_jlTeamName;
    private JPanel m_clSubstituted1;
    private JPanel m_clSubstituted2;
    private JPanel m_clSubstituted3;
    private JPanel m_clLeftBack;
    private JPanel m_clLeftWinger;
    private JPanel m_clLeftCentralDefender;
    private JPanel m_clLeftMidfield;
    private JPanel m_clLeftForward;
    private JPanel m_clRightBack;
    private JPanel m_clRightWinger;
    private JPanel m_clRightCentralDefender;
    private JPanel m_clRightMidfield;
    private JPanel m_clRightForward;
    private JPanel m_clReserveWinger;
    private JPanel m_clReserveMidfield;
    private JPanel m_clReserveForward;
    private JPanel m_clReserveKeeper;
    private JPanel m_clReserveDefender;
    private JPanel m_clCaptain;
    private JPanel m_clSetPieceTaker;
    private JPanel m_clKeeper;

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
        return m_clSubstituted1;
    }

    /**
     * Get the Panel Injured2
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getInjured2Panel() {
        return m_clSubstituted2;
    }

    /**
     * Get the Panel Injured3
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getInjured3Panel() {
        return m_clSubstituted3;
    }

    /**
     * Get the Panel Keeper
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getKeeperPanel() {
        return m_clKeeper;
    }

    /**
     * Get the Panel Left Central Defender
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getLeftCentralDefenderPanel() {
        return m_clLeftCentralDefender;
    }

    /**
     * Get the Panel Left Forward
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getLeftForwardPanel() {
        return m_clLeftForward;
    }

    /**
     * Get the Panel Left Midfield
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getLeftMidfieldPanel() {
        return m_clLeftMidfield;
    }

    /**
     * Get the Panel Left Wing
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getLeftWingPanel() {
        return m_clLeftWinger;
    }

    /**
     * Get the Panel Left Wingback
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getLeftWingbackPanel() {
        return m_clLeftBack;
    }

    /**
     * Get the Panel Reserve Defender
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getReserveDefenderPanel() {
        return m_clReserveDefender;
    }

    /**
     * Get the Panel Reserve Forward
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getReserveForwardPanel() {
        return m_clReserveForward;
    }

    /**
     * Get the Panel Reserve Keeper
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getReserveKeeperPanel() {
        return m_clReserveKeeper;
    }

    /**
     * Get the Panel Reserve Midfield
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getReserveMidfieldPanel() {
        return m_clReserveMidfield;
    }

    /**
     * Get the Panel Reserve Wing
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getReserveWingPanel() {
        return m_clReserveWinger;
    }

    /**
     * Get the Panel Right Centraldefender
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getRightCentralDefenderPanel() {
        return m_clRightCentralDefender;
    }

    /**
     * Get the Panel Right Forward
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getRightForwardPanel() {
        return m_clRightForward;
    }

    /**
     * Get the Panel Right Midfield
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getRightMidfieldPanel() {
        return m_clRightMidfield;
    }

    /**
     * Get the Panel Right Wing
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getRightWingPanel() {
        return m_clRightWinger;
    }

    /**
     * Get the Panel Right Wingback
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getRightWingbackPanel() {
        return m_clRightBack;
    }

    /**
     * Get the Panel Set Pieces
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getSetPiecesPanel() {
        return m_clSetPieceTaker;
    }

    /**
     * Get the Panel Teamleader
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JPanel getTeamLeaderPanel() {
        return m_clCaptain;
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
        m_jlTeamName.setForeground(ThemeManager.getColor(HOColorName.LABEL_ONGREEN_FG));
        m_jlTeamName.setFont(m_jlTeamName.getFont().deriveFont(Font.BOLD,
                                                               ho.core.model.UserParameter.instance().schriftGroesse
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

        m_clKeeper = new JPanel();
        m_clKeeper.setOpaque(false);
        layout.setConstraints(m_clKeeper, constraints);
        centerPanel.add(m_clKeeper);

        if (flags) {
            constraints.gridx = 0;
            constraints.gridy = 5;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 3;
            constraints.gridy = 3;
            constraints.gridwidth = 1;
        }

        m_clRightBack = new JPanel();
        m_clRightBack.setOpaque(false);
        layout.setConstraints(m_clRightBack, constraints);
        centerPanel.add(m_clRightBack);

        if (flags) {
            constraints.gridx = 1;
            constraints.gridy = 5;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 2;
            constraints.gridy = 3;
            constraints.gridwidth = 1;
        }

        m_clRightCentralDefender = new JPanel();
        m_clRightCentralDefender.setOpaque(false);
        layout.setConstraints(m_clRightCentralDefender, constraints);
        centerPanel.add(m_clRightCentralDefender);

        if (flags) {
            constraints.gridx = 2;
            constraints.gridy = 5;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 1;
            constraints.gridy = 3;
            constraints.gridwidth = 1;
        }

        m_clLeftCentralDefender = new JPanel();
        m_clLeftCentralDefender.setOpaque(false);
        layout.setConstraints(m_clLeftCentralDefender, constraints);
        centerPanel.add(m_clLeftCentralDefender);

        if (flags) {
            constraints.gridx = 3;
            constraints.gridy = 5;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 0;
            constraints.gridy = 3;
            constraints.gridwidth = 1;
        }

        m_clLeftBack = new JPanel();
        m_clLeftBack.setOpaque(false);
        layout.setConstraints(m_clLeftBack, constraints);
        centerPanel.add(m_clLeftBack);

        if (flags) {
            constraints.gridx = 0;
            constraints.gridy = 6;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 3;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
        }

        m_clRightWinger = new JPanel();
        m_clRightWinger.setOpaque(false);
        layout.setConstraints(m_clRightWinger, constraints);
        centerPanel.add(m_clRightWinger);

        if (flags) {
            constraints.gridx = 1;
            constraints.gridy = 6;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 2;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
        }

        m_clRightMidfield = new JPanel();
        m_clRightMidfield.setOpaque(false);
        layout.setConstraints(m_clRightMidfield, constraints);
        centerPanel.add(m_clRightMidfield);

        if (flags) {
            constraints.gridx = 2;
            constraints.gridy = 6;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 1;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
        }

        m_clLeftMidfield = new JPanel();
        m_clLeftMidfield.setOpaque(false);
        layout.setConstraints(m_clLeftMidfield, constraints);
        centerPanel.add(m_clLeftMidfield);

        if (flags) {
            constraints.gridx = 3;
            constraints.gridy = 6;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 0;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
        }

        m_clLeftWinger = new JPanel();
        m_clLeftWinger.setOpaque(false);
        layout.setConstraints(m_clLeftWinger, constraints);
        centerPanel.add(m_clLeftWinger);

        if (flags) {
            constraints.gridx = 1;
            constraints.gridy = 7;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 2;
            constraints.gridy = 1;
            constraints.gridwidth = 1;
        }

        m_clLeftForward = new JPanel();
        m_clLeftForward.setOpaque(false);
        layout.setConstraints(m_clLeftForward, constraints);
        centerPanel.add(m_clLeftForward);

        if (flags) {
            constraints.gridx = 2;
            constraints.gridy = 7;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 1;
            constraints.gridy = 1;
            constraints.gridwidth = 1;
        }

        m_clRightForward = new JPanel();
        m_clRightForward.setOpaque(false);
        layout.setConstraints(m_clRightForward, constraints);
        centerPanel.add(m_clRightForward);

        if (flags) {
            constraints.gridx = 0;
            constraints.gridy = 3;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 0;
            constraints.gridy = 5;
            constraints.gridwidth = 1;
        }

        m_clSetPieceTaker = new JPanel();
        m_clSetPieceTaker.setOpaque(false);
        layout.setConstraints(m_clSetPieceTaker, constraints);
        centerPanel.add(m_clSetPieceTaker);

        if (flags) {
            constraints.gridx = 1;
            constraints.gridy = 3;
            constraints.gridwidth = 2;
        } else {
            constraints.gridx = 1;
            constraints.gridy = 5;
            constraints.gridwidth = 2;
        }

        m_clReserveKeeper = new JPanel();
        m_clReserveKeeper.setOpaque(false);
        layout.setConstraints(m_clReserveKeeper, constraints);
        centerPanel.add(m_clReserveKeeper);

        if (flags) {
            constraints.gridx = 3;
            constraints.gridy = 3;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 3;
            constraints.gridy = 5;
            constraints.gridwidth = 1;
        }

        m_clCaptain = new JPanel();
        m_clCaptain.setOpaque(false);
        layout.setConstraints(m_clCaptain, constraints);
        centerPanel.add(m_clCaptain);

        if (flags) {
            constraints.gridx = 0;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 0;
            constraints.gridy = 6;
            constraints.gridwidth = 1;
        }

        m_clReserveDefender = new JPanel();
        m_clReserveDefender.setOpaque(false);
        layout.setConstraints(m_clReserveDefender, constraints);
        centerPanel.add(m_clReserveDefender);

        if (flags) {
            constraints.gridx = 1;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 1;
            constraints.gridy = 6;
            constraints.gridwidth = 1;
        }

        m_clReserveMidfield = new JPanel();
        m_clReserveMidfield.setOpaque(false);
        layout.setConstraints(m_clReserveMidfield, constraints);
        centerPanel.add(m_clReserveMidfield);

        if (flags) {
            constraints.gridx = 2;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 2;
            constraints.gridy = 6;
            constraints.gridwidth = 1;
        }

        m_clReserveForward = new JPanel();
        m_clReserveForward.setOpaque(false);
        layout.setConstraints(m_clReserveForward, constraints);
        centerPanel.add(m_clReserveForward);

        if (flags) {
            constraints.gridx = 3;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 3;
            constraints.gridy = 6;
            constraints.gridwidth = 1;
        }

        m_clReserveWinger = new JPanel();
        m_clReserveWinger.setOpaque(false);
        layout.setConstraints(m_clReserveWinger, constraints);
        centerPanel.add(m_clReserveWinger);

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

        m_clSubstituted1 = new JPanel();
        m_clSubstituted1.setOpaque(false);
        layout.setConstraints(m_clSubstituted1, constraints);
        centerPanel.add(m_clSubstituted1);

        if (flags) {
            constraints.gridx = 2;
            constraints.gridy = 1;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 2;
            constraints.gridy = 7;
            constraints.gridwidth = 1;
        }

        m_clSubstituted2 = new JPanel();
        m_clSubstituted2.setOpaque(false);
        layout.setConstraints(m_clSubstituted2, constraints);
        centerPanel.add(m_clSubstituted2);

        if (flags) {
            constraints.gridx = 3;
            constraints.gridy = 1;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 3;
            constraints.gridy = 7;
            constraints.gridwidth = 1;
        }

        m_clSubstituted3 = new JPanel();
        m_clSubstituted3.setOpaque(false);
        layout.setConstraints(m_clSubstituted3, constraints);
        centerPanel.add(m_clSubstituted3);

        add(centerPanel, BorderLayout.CENTER);
    }
}
