// %2675300316:de.hattrickorganizer.gui.matches%
package de.hattrickorganizer.gui.matches;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.JLabel;

import plugins.IMatchLineupPlayer;
import plugins.ISpielerPosition;

import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.gui.templates.RasenPanel;
import de.hattrickorganizer.model.matches.MatchLineup;
import de.hattrickorganizer.model.matches.MatchLineupPlayer;
import de.hattrickorganizer.model.matches.MatchLineupTeam;
import de.hattrickorganizer.tools.HOLogger;


/**
 * Zeigt die Stärken und Aufstellung einer Mannschaft an
 */
public class AufstellungsSternePanel extends RasenPanel {
	
	private static final long serialVersionUID = 7568934875331878466L;
	
    //~ Instance fields ----------------------------------------------------------------------------

	private JLabel m_jlTeamName;
    private SpielerSternePanel m_clAusgewechselt1;
    private SpielerSternePanel m_clAusgewechselt2;
    private SpielerSternePanel m_clAusgewechselt3;
    private SpielerSternePanel m_clLeftBack;
    private SpielerSternePanel m_clLeftWinger;
    private SpielerSternePanel m_clLeftCentralDefender;
    private SpielerSternePanel m_clLeftInnerMidfielder;
    private SpielerSternePanel m_clLeftForward;
    private SpielerSternePanel m_clRightBack;
    private SpielerSternePanel m_clRightWinger;
    private SpielerSternePanel m_clRightCentralDefender;
    private SpielerSternePanel m_clRightInnerMidfielder;
    private SpielerSternePanel m_clRightForward;
    private SpielerSternePanel m_clMiddleCentralDefender;
    private SpielerSternePanel m_clCentralInnerMidfielder;
    private SpielerSternePanel m_clCentralForward;
    private SpielerSternePanel m_clReserveWinger;
    private SpielerSternePanel m_clReserveMidfielder;
    private SpielerSternePanel m_clReserveForward;
    private SpielerSternePanel m_clReserveKeeper;
    private SpielerSternePanel m_clReserveDefender;
    private SpielerSternePanel m_clCaptain;
    private SpielerSternePanel m_clSetPieces;
    private SpielerSternePanel m_clKeeper;
    private boolean m_bHeim;
    private boolean m_bPrint;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new AufstellungsSternePanel object.
     *
     * @param heim TODO Missing Constructuor Parameter Documentation
     */
    public AufstellungsSternePanel(boolean heim) {
        this(heim, false);
    }

    /**
     * Creates a new AufstellungsSternePanel object.
     *
     * @param heim TODO Missing Constructuor Parameter Documentation
     * @param print TODO Missing Constructuor Parameter Documentation
     */
    public AufstellungsSternePanel(boolean heim, boolean print) {
        super(print);

        m_bPrint = print;
        m_bHeim = heim;

        initComponentes();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     */
    public final void clearAll() {
        m_jlTeamName.setText(" ");
        m_clKeeper.clear();
        m_clLeftBack.clear();
        m_clLeftCentralDefender.clear();
        m_clMiddleCentralDefender.clear();
        m_clRightCentralDefender.clear();
        m_clRightBack.clear();
        m_clLeftWinger.clear();
        m_clLeftInnerMidfielder.clear();
        m_clCentralInnerMidfielder.clear();
        m_clRightInnerMidfielder.clear();
        m_clRightWinger.clear();
        m_clLeftForward.clear();
        m_clCentralForward.clear();
        m_clRightForward.clear();

        m_clReserveKeeper.clear();
        m_clReserveDefender.clear();
        m_clReserveMidfielder.clear();
        m_clReserveWinger.clear();
        m_clReserveForward.clear();

        m_clCaptain.clear();
        m_clSetPieces.clear();

        m_clAusgewechselt1.clear();
        m_clAusgewechselt2.clear();
        m_clAusgewechselt3.clear();
    }

    /**
     * Get match lineup and refresh this SpielerSternePanels.
     */
	public final void refresh(int matchid, int teamid) {
		final MatchLineup lineup = DBZugriff.instance().getMatchLineup(matchid);
		MatchLineupTeam lineupteam = null;

		if (lineup.getHeimId() == teamid) {
			lineupteam = (MatchLineupTeam) lineup.getHeim();
		} else {
			lineupteam = (MatchLineupTeam) lineup.getGast();
		}

		clearAll();

		if (lineupteam != null) {
			m_jlTeamName.setText(lineupteam.getTeamName() + " (" + lineupteam.getTeamID() + ")");

			final Vector<IMatchLineupPlayer> aufstellung = lineupteam.getAufstellung();

			for (int i = 0; i < aufstellung.size(); i++) {
				final MatchLineupPlayer player = (MatchLineupPlayer) aufstellung.get(i);

				switch (player.getId()) {
				case ISpielerPosition.keeper: {
					m_clKeeper.refresh(lineup, player);
					break;
				}

				case ISpielerPosition.rightBack: {
					m_clRightBack.refresh(lineup, player);
					break;
				}

				case ISpielerPosition.leftBack: {
					m_clLeftBack.refresh(lineup, player);
					break;
				}

				case ISpielerPosition.rightCentralDefender: {
					m_clRightCentralDefender.refresh(lineup, player);
					break;
				}
				
				case ISpielerPosition.middleCentralDefender: {
					m_clMiddleCentralDefender.refresh(lineup, player);
					break;
				}

				case ISpielerPosition.leftCentralDefender: {
					m_clLeftCentralDefender.refresh(lineup, player);
					break;
				}

				case ISpielerPosition.rightInnerMidfield: {
					m_clRightInnerMidfielder.refresh(lineup, player);
					break;
				}
				
				case ISpielerPosition.centralInnerMidfield: {
					m_clCentralInnerMidfielder.refresh(lineup, player);
					break;
				}

				case ISpielerPosition.leftInnerMidfield: {
					m_clLeftInnerMidfielder.refresh(lineup, player);
					break;
				}

				case ISpielerPosition.leftWinger: {
					m_clLeftWinger.refresh(lineup, player);
					break;
				}

				case ISpielerPosition.rightWinger: {
					m_clRightWinger.refresh(lineup, player);
					break;
				}

				case ISpielerPosition.rightForward: {
					m_clLeftForward.refresh(lineup, player);
					break;
				}
				
				case ISpielerPosition.centralForward: {
					m_clCentralForward.refresh(lineup, player);
					break;
				}

				case ISpielerPosition.leftForward: {
					m_clRightForward.refresh(lineup, player);
					break;
				}

				case ISpielerPosition.substDefender: {
					m_clReserveDefender.refresh(lineup, player);
					break;
				}

				case ISpielerPosition.substForward: {
					m_clReserveForward.refresh(lineup, player);
					break;
				}

				case ISpielerPosition.substInnerMidfield: {
					m_clReserveMidfielder.refresh(lineup, player);
					break;
				}

				case ISpielerPosition.substKeeper: {
					m_clReserveKeeper.refresh(lineup, player);
					break;
				}

				case ISpielerPosition.substWinger: {
					m_clReserveWinger.refresh(lineup, player);
					break;
				}

				case ISpielerPosition.captain: {
					m_clCaptain.refresh(lineup, player);
					break;
				}

				case ISpielerPosition.setPieces: {
					m_clSetPieces.refresh(lineup, player);
					break;
				}

				case ISpielerPosition.ausgewechselt: {
					m_clAusgewechselt1.refresh(lineup, player);
					break;
				}

				case ISpielerPosition.ausgewechselt + 1: {
					m_clAusgewechselt2.refresh(lineup, player);
					break;
				}

				case ISpielerPosition.ausgewechselt + 2: {
					m_clAusgewechselt3.refresh(lineup, player);
					break;
				}

				default:
					HOLogger.instance().log(getClass(), getClass().getName() + ": Unknown player position: " + player.getPositionCode());
				}
			}
		}
	}

    /**
     * Erstellt die Komponenten
     */
    private void initComponentes() {
        setLayout(new BorderLayout());

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

        if (m_bHeim) {
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.gridwidth = 5;
        } else {
            constraints.gridx = 0;
            constraints.gridy = 8;
            constraints.gridwidth = 5;
        }

        m_jlTeamName = new JLabel(" ");
        m_jlTeamName.setOpaque(false);
        m_jlTeamName.setForeground(Color.white);
        m_jlTeamName.setFont(m_jlTeamName.getFont().deriveFont(Font.BOLD,
                                                               gui.UserParameter.instance().schriftGroesse
                                                               + 3));
        layout.setConstraints(m_jlTeamName, constraints);
        centerPanel.add(m_jlTeamName);

        if (m_bHeim) {
            constraints.gridx = 0;
            constraints.gridy = 4;
            constraints.gridwidth = 5;
        } else {
            constraints.gridx = 0;
            constraints.gridy = 4;
            constraints.gridwidth = 5;
        }

        m_clKeeper = new SpielerSternePanel(ISpielerPosition.keeper, m_bPrint);
        layout.setConstraints(m_clKeeper, constraints);
        centerPanel.add(m_clKeeper);

        if (m_bHeim) {
            constraints.gridx = 0;
            constraints.gridy = 5;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 4;
            constraints.gridy = 3;
            constraints.gridwidth = 1;
        }

        m_clRightBack = new SpielerSternePanel(ISpielerPosition.rightBack, m_bPrint);
        layout.setConstraints(m_clRightBack, constraints);
        centerPanel.add(m_clRightBack);

        if (m_bHeim) {
            constraints.gridx = 1;
            constraints.gridy = 5;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 3;
            constraints.gridy = 3;
            constraints.gridwidth = 1;
        }

        m_clRightCentralDefender = new SpielerSternePanel(ISpielerPosition.rightCentralDefender, m_bPrint);
        layout.setConstraints(m_clRightCentralDefender, constraints);
        centerPanel.add(m_clRightCentralDefender);

        if (m_bHeim) {
            constraints.gridx = 2;
            constraints.gridy = 5;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 2;
            constraints.gridy = 3;
            constraints.gridwidth = 1;
        }

        m_clMiddleCentralDefender = new SpielerSternePanel(ISpielerPosition.middleCentralDefender, m_bPrint);
        layout.setConstraints(m_clMiddleCentralDefender, constraints);
        centerPanel.add(m_clMiddleCentralDefender);
        
        if (m_bHeim) {
            constraints.gridx = 3;
            constraints.gridy = 5;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 1;
            constraints.gridy = 3;
            constraints.gridwidth = 1;
        }

        m_clLeftCentralDefender = new SpielerSternePanel(ISpielerPosition.leftCentralDefender, m_bPrint);
        layout.setConstraints(m_clLeftCentralDefender, constraints);
        centerPanel.add(m_clLeftCentralDefender);

        if (m_bHeim) {
            constraints.gridx = 4;
            constraints.gridy = 5;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 0;
            constraints.gridy = 3;
            constraints.gridwidth = 1;
        }

        m_clLeftBack = new SpielerSternePanel(ISpielerPosition.leftBack, m_bPrint);
        layout.setConstraints(m_clLeftBack, constraints);
        centerPanel.add(m_clLeftBack);

        if (m_bHeim) {
            constraints.gridx = 0;
            constraints.gridy = 6;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 4;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
        }

        m_clRightWinger = new SpielerSternePanel(ISpielerPosition.rightWinger, m_bPrint);
        layout.setConstraints(m_clRightWinger, constraints);
        centerPanel.add(m_clRightWinger);

        if (m_bHeim) {
            constraints.gridx = 1;
            constraints.gridy = 6;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 3;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
        }

        m_clRightInnerMidfielder = new SpielerSternePanel(ISpielerPosition.rightInnerMidfield, m_bPrint);
        layout.setConstraints(m_clRightInnerMidfielder, constraints);
        centerPanel.add(m_clRightInnerMidfielder);

        if (m_bHeim) {
            constraints.gridx = 2;
            constraints.gridy = 6;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 2;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
        }
        
        m_clCentralInnerMidfielder = new SpielerSternePanel(ISpielerPosition.centralInnerMidfield, m_bPrint);
        layout.setConstraints(m_clCentralInnerMidfielder, constraints);
        centerPanel.add(m_clCentralInnerMidfielder);

        if (m_bHeim) {
            constraints.gridx = 3;
            constraints.gridy = 6;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 1;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
        }

        m_clLeftInnerMidfielder = new SpielerSternePanel(ISpielerPosition.leftInnerMidfield, m_bPrint);
        layout.setConstraints(m_clLeftInnerMidfielder, constraints);
        centerPanel.add(m_clLeftInnerMidfielder);

        if (m_bHeim) {
            constraints.gridx = 4;
            constraints.gridy = 6;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 0;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
        }

        m_clLeftWinger = new SpielerSternePanel(ISpielerPosition.leftWinger, m_bPrint);
        layout.setConstraints(m_clLeftWinger, constraints);
        centerPanel.add(m_clLeftWinger);

        if (m_bHeim) {
            constraints.gridx = 1;
            constraints.gridy = 7;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 3;
            constraints.gridy = 1;
            constraints.gridwidth = 1;
        }

        m_clLeftForward = new SpielerSternePanel(ISpielerPosition.rightForward, m_bPrint);
        layout.setConstraints(m_clLeftForward, constraints);
        centerPanel.add(m_clLeftForward);
        
        if (m_bHeim) {
            constraints.gridx = 2;
            constraints.gridy = 7;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 2;
            constraints.gridy = 1;
            constraints.gridwidth = 1;
        }

        m_clCentralForward = new SpielerSternePanel(ISpielerPosition.centralForward, m_bPrint);
        layout.setConstraints(m_clCentralForward, constraints);
        centerPanel.add(m_clCentralForward);

        if (m_bHeim) {
            constraints.gridx = 3;
            constraints.gridy = 7;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 1;
            constraints.gridy = 1;
            constraints.gridwidth = 1;
        }

        m_clRightForward = new SpielerSternePanel(ISpielerPosition.leftForward, m_bPrint);
        layout.setConstraints(m_clRightForward, constraints);
        centerPanel.add(m_clRightForward);

        if (m_bHeim) {
            constraints.gridx = 0;
            constraints.gridy = 3;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 0;
            constraints.gridy = 5;
            constraints.gridwidth = 1;
        }

        m_clSetPieces = new SpielerSternePanel(ISpielerPosition.setPieces, m_bPrint);
        layout.setConstraints(m_clSetPieces, constraints);
        centerPanel.add(m_clSetPieces);

        if (m_bHeim) {
            constraints.gridx = 2;
            constraints.gridy = 3;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 2;
            constraints.gridy = 5;
            constraints.gridwidth = 1;
        }

        m_clReserveKeeper = new SpielerSternePanel(ISpielerPosition.substKeeper, m_bPrint);
        layout.setConstraints(m_clReserveKeeper, constraints);
        centerPanel.add(m_clReserveKeeper);

        if (m_bHeim) {
            constraints.gridx = 4;
            constraints.gridy = 3;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 4;
            constraints.gridy = 5;
            constraints.gridwidth = 1;
        }

        m_clCaptain = new SpielerSternePanel(ISpielerPosition.captain, m_bPrint);
        layout.setConstraints(m_clCaptain, constraints);
        centerPanel.add(m_clCaptain);

        if (m_bHeim) {
            constraints.gridx = 0;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 0;
            constraints.gridy = 6;
            constraints.gridwidth = 1;
        }

        m_clReserveDefender = new SpielerSternePanel(ISpielerPosition.substDefender, m_bPrint);
        layout.setConstraints(m_clReserveDefender, constraints);
        centerPanel.add(m_clReserveDefender);

        if (m_bHeim) {
            constraints.gridx = 1;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 1;
            constraints.gridy = 6;
            constraints.gridwidth = 1;
        }

        m_clReserveMidfielder = new SpielerSternePanel(ISpielerPosition.substInnerMidfield, m_bPrint);
        layout.setConstraints(m_clReserveMidfielder, constraints);
        centerPanel.add(m_clReserveMidfielder);

        if (m_bHeim) {
            constraints.gridx = 2;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 2;
            constraints.gridy = 6;
            constraints.gridwidth = 1;
        }

        m_clReserveForward = new SpielerSternePanel(ISpielerPosition.substForward, m_bPrint);
        layout.setConstraints(m_clReserveForward, constraints);
        centerPanel.add(m_clReserveForward);

        if (m_bHeim) {
            constraints.gridx = 3;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 3;
            constraints.gridy = 6;
            constraints.gridwidth = 1;
        }

        m_clReserveWinger = new SpielerSternePanel(ISpielerPosition.substWinger, m_bPrint);
        layout.setConstraints(m_clReserveWinger, constraints);
        centerPanel.add(m_clReserveWinger);

        if (m_bHeim) {
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

        if (m_bHeim) {
            constraints.gridx = 1;
            constraints.gridy = 1;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 1;
            constraints.gridy = 7;
            constraints.gridwidth = 1;
        }

        m_clAusgewechselt1 = new SpielerSternePanel(ISpielerPosition.ausgewechselt, m_bPrint);
        layout.setConstraints(m_clAusgewechselt1, constraints);
        centerPanel.add(m_clAusgewechselt1);

        if (m_bHeim) {
            constraints.gridx = 2;
            constraints.gridy = 1;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 2;
            constraints.gridy = 7;
            constraints.gridwidth = 1;
        }

        m_clAusgewechselt2 = new SpielerSternePanel(ISpielerPosition.ausgewechselt, m_bPrint);
        layout.setConstraints(m_clAusgewechselt2, constraints);
        centerPanel.add(m_clAusgewechselt2);

        if (m_bHeim) {
            constraints.gridx = 3;
            constraints.gridy = 1;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 3;
            constraints.gridy = 7;
            constraints.gridwidth = 1;
        }

        m_clAusgewechselt3 = new SpielerSternePanel(ISpielerPosition.ausgewechselt, m_bPrint);
        layout.setConstraints(m_clAusgewechselt3, constraints);
        centerPanel.add(m_clAusgewechselt3);

        add(centerPanel, BorderLayout.CENTER);
    }
}
