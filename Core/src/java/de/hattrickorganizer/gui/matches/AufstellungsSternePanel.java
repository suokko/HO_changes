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

import de.hattrickorganizer.gui.templates.RasenPanel;
import de.hattrickorganizer.model.matches.MatchLineupPlayer;
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
    private SpielerSternePanel m_clLinkeAussenVerteidiger;
    private SpielerSternePanel m_clLinkeFluegel;
    private SpielerSternePanel m_clLinkeInnenVerteidiger;
    private SpielerSternePanel m_clLinkeMittelfeld;
    private SpielerSternePanel m_clLinkerSturm;
    private SpielerSternePanel m_clRechteAussenVerteidiger;
    private SpielerSternePanel m_clRechteFluegel;
    private SpielerSternePanel m_clRechteInnenVerteidiger;
    private SpielerSternePanel m_clRechteMittelfeld;
    private SpielerSternePanel m_clRechterSturm;
    private SpielerSternePanel m_clReserveFluegel;
    private SpielerSternePanel m_clReserveMittelfeld;
    private SpielerSternePanel m_clReserveSturm;
    private SpielerSternePanel m_clReserveTorwart;
    private SpielerSternePanel m_clReserveVerteidiger;
    private SpielerSternePanel m_clSpielfuehrer;
    private SpielerSternePanel m_clStandard;
    private SpielerSternePanel m_clTorwart;
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
        m_clTorwart.clear();
        m_clLinkeAussenVerteidiger.clear();
        m_clLinkeInnenVerteidiger.clear();
        m_clRechteInnenVerteidiger.clear();
        m_clRechteAussenVerteidiger.clear();
        m_clLinkeFluegel.clear();
        m_clLinkeMittelfeld.clear();
        m_clRechteMittelfeld.clear();
        m_clRechteFluegel.clear();
        m_clLinkerSturm.clear();
        m_clRechterSturm.clear();

        m_clReserveTorwart.clear();
        m_clReserveVerteidiger.clear();
        m_clReserveMittelfeld.clear();
        m_clReserveFluegel.clear();
        m_clReserveSturm.clear();

        m_clSpielfuehrer.clear();
        m_clStandard.clear();

        m_clAusgewechselt1.clear();
        m_clAusgewechselt2.clear();
        m_clAusgewechselt3.clear();
    }

    /**
     * Holt das Lineup für das Match und Team und aktualisiert die SpielerSternePanels
     *
     * @param matchid TODO Missing Constructuor Parameter Documentation
     * @param teamid TODO Missing Constructuor Parameter Documentation
     */
    public final void refresh(int matchid, int teamid) {
        final de.hattrickorganizer.model.matches.MatchLineup lineup = de.hattrickorganizer.database.DBZugriff.instance()
                                                                                                             .getMatchLineup(matchid);
        de.hattrickorganizer.model.matches.MatchLineupTeam lineupteam = null;

        if (lineup.getHeimId() == teamid) {
            lineupteam = (de.hattrickorganizer.model.matches.MatchLineupTeam) lineup.getHeim();
        } else {
            lineupteam = (de.hattrickorganizer.model.matches.MatchLineupTeam) lineup.getGast();
        }

        clearAll();

        if (lineupteam != null) {
            m_jlTeamName.setText(lineupteam.getTeamName() + " (" + lineupteam.getTeamID() + ")");

            final Vector<IMatchLineupPlayer> aufstellung = lineupteam.getAufstellung();

            for (int i = 0; i < aufstellung.size(); i++) {
                final MatchLineupPlayer player = (MatchLineupPlayer) aufstellung.get(i);

                switch (player.getId()) {
                    case ISpielerPosition.keeper: {
                        m_clTorwart.refresh(lineup, player);
                        break;
                    }

                    case ISpielerPosition.rightBack: {
                        m_clRechteAussenVerteidiger.refresh(lineup, player);
                        break;
                    }

                    case ISpielerPosition.leftBack: {
                        m_clLinkeAussenVerteidiger.refresh(lineup, player);
                        break;
                    }

                    case ISpielerPosition.insideBack1: {
                        m_clRechteInnenVerteidiger.refresh(lineup, player);
                        break;
                    }

                    case ISpielerPosition.insideBack2: {
                        m_clLinkeInnenVerteidiger.refresh(lineup, player);
                        break;
                    }

                    case ISpielerPosition.insideMid1: {
                        m_clRechteMittelfeld.refresh(lineup, player);
                        break;
                    }

                    case ISpielerPosition.insideMid2: {
                        m_clLinkeMittelfeld.refresh(lineup, player);
                        break;
                    }

                    case ISpielerPosition.leftWinger: {
                        m_clLinkeFluegel.refresh(lineup, player);
                        break;
                    }

                    case ISpielerPosition.rightWinger: {
                        m_clRechteFluegel.refresh(lineup, player);
                        break;
                    }

                    case ISpielerPosition.forward1: {
                        m_clLinkerSturm.refresh(lineup, player);
                        break;
                    }

                    case ISpielerPosition.forward2: {
                        m_clRechterSturm.refresh(lineup, player);
                        break;
                    }

                    case ISpielerPosition.substBack: {
                        m_clReserveVerteidiger.refresh(lineup, player);
                        break;
                    }

                    case ISpielerPosition.substForward: {
                        m_clReserveSturm.refresh(lineup, player);
                        break;
                    }

                    case ISpielerPosition.substInsideMid: {
                        m_clReserveMittelfeld.refresh(lineup, player);
                        break;
                    }

                    case ISpielerPosition.substKeeper: {
                        m_clReserveTorwart.refresh(lineup, player);
                        break;
                    }

                    case ISpielerPosition.substWinger: {
                        m_clReserveFluegel.refresh(lineup, player);
                        break;
                    }

                    case ISpielerPosition.spielfuehrer: {
                        m_clSpielfuehrer.refresh(lineup, player);
                        break;
                    }

                    case ISpielerPosition.standard: {
                        m_clStandard.refresh(lineup, player);
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
                        HOLogger.instance().log(getClass(),"Umbekannte Position: " + player.getPositionCode());

                    //Ausgewechselte Spieler fehlen noch!
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
            constraints.gridwidth = 4;
        } else {
            constraints.gridx = 0;
            constraints.gridy = 8;
            constraints.gridwidth = 4;
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
            constraints.gridwidth = 4;
        } else {
            constraints.gridx = 0;
            constraints.gridy = 4;
            constraints.gridwidth = 4;
        }

        m_clTorwart = new SpielerSternePanel(ISpielerPosition.keeper, m_bPrint);
        layout.setConstraints(m_clTorwart, constraints);
        centerPanel.add(m_clTorwart);

        if (m_bHeim) {
            constraints.gridx = 0;
            constraints.gridy = 5;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 3;
            constraints.gridy = 3;
            constraints.gridwidth = 1;
        }

        m_clRechteAussenVerteidiger = new SpielerSternePanel(ISpielerPosition.rightBack, m_bPrint);
        layout.setConstraints(m_clRechteAussenVerteidiger, constraints);
        centerPanel.add(m_clRechteAussenVerteidiger);

        if (m_bHeim) {
            constraints.gridx = 1;
            constraints.gridy = 5;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 2;
            constraints.gridy = 3;
            constraints.gridwidth = 1;
        }

        m_clRechteInnenVerteidiger = new SpielerSternePanel(ISpielerPosition.insideBack1, m_bPrint);
        layout.setConstraints(m_clRechteInnenVerteidiger, constraints);
        centerPanel.add(m_clRechteInnenVerteidiger);

        if (m_bHeim) {
            constraints.gridx = 2;
            constraints.gridy = 5;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 1;
            constraints.gridy = 3;
            constraints.gridwidth = 1;
        }

        m_clLinkeInnenVerteidiger = new SpielerSternePanel(ISpielerPosition.insideBack2, m_bPrint);
        layout.setConstraints(m_clLinkeInnenVerteidiger, constraints);
        centerPanel.add(m_clLinkeInnenVerteidiger);

        if (m_bHeim) {
            constraints.gridx = 3;
            constraints.gridy = 5;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 0;
            constraints.gridy = 3;
            constraints.gridwidth = 1;
        }

        m_clLinkeAussenVerteidiger = new SpielerSternePanel(ISpielerPosition.leftBack, m_bPrint);
        layout.setConstraints(m_clLinkeAussenVerteidiger, constraints);
        centerPanel.add(m_clLinkeAussenVerteidiger);

        if (m_bHeim) {
            constraints.gridx = 0;
            constraints.gridy = 6;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 3;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
        }

        m_clRechteFluegel = new SpielerSternePanel(ISpielerPosition.rightWinger, m_bPrint);
        layout.setConstraints(m_clRechteFluegel, constraints);
        centerPanel.add(m_clRechteFluegel);

        if (m_bHeim) {
            constraints.gridx = 1;
            constraints.gridy = 6;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 2;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
        }

        m_clRechteMittelfeld = new SpielerSternePanel(ISpielerPosition.insideMid1, m_bPrint);
        layout.setConstraints(m_clRechteMittelfeld, constraints);
        centerPanel.add(m_clRechteMittelfeld);

        if (m_bHeim) {
            constraints.gridx = 2;
            constraints.gridy = 6;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 1;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
        }

        m_clLinkeMittelfeld = new SpielerSternePanel(ISpielerPosition.insideMid2, m_bPrint);
        layout.setConstraints(m_clLinkeMittelfeld, constraints);
        centerPanel.add(m_clLinkeMittelfeld);

        if (m_bHeim) {
            constraints.gridx = 3;
            constraints.gridy = 6;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 0;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
        }

        m_clLinkeFluegel = new SpielerSternePanel(ISpielerPosition.leftWinger, m_bPrint);
        layout.setConstraints(m_clLinkeFluegel, constraints);
        centerPanel.add(m_clLinkeFluegel);

        if (m_bHeim) {
            constraints.gridx = 1;
            constraints.gridy = 7;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 2;
            constraints.gridy = 1;
            constraints.gridwidth = 1;
        }

        m_clLinkerSturm = new SpielerSternePanel(ISpielerPosition.forward1, m_bPrint);
        layout.setConstraints(m_clLinkerSturm, constraints);
        centerPanel.add(m_clLinkerSturm);

        if (m_bHeim) {
            constraints.gridx = 2;
            constraints.gridy = 7;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 1;
            constraints.gridy = 1;
            constraints.gridwidth = 1;
        }

        m_clRechterSturm = new SpielerSternePanel(ISpielerPosition.forward2, m_bPrint);
        layout.setConstraints(m_clRechterSturm, constraints);
        centerPanel.add(m_clRechterSturm);

        if (m_bHeim) {
            constraints.gridx = 0;
            constraints.gridy = 3;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 0;
            constraints.gridy = 5;
            constraints.gridwidth = 1;
        }

        m_clStandard = new SpielerSternePanel(ISpielerPosition.standard, m_bPrint);
        layout.setConstraints(m_clStandard, constraints);
        centerPanel.add(m_clStandard);

        if (m_bHeim) {
            constraints.gridx = 1;
            constraints.gridy = 3;
            constraints.gridwidth = 2;
        } else {
            constraints.gridx = 1;
            constraints.gridy = 5;
            constraints.gridwidth = 2;
        }

        m_clReserveTorwart = new SpielerSternePanel(ISpielerPosition.substKeeper, m_bPrint);
        layout.setConstraints(m_clReserveTorwart, constraints);
        centerPanel.add(m_clReserveTorwart);

        if (m_bHeim) {
            constraints.gridx = 3;
            constraints.gridy = 3;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 3;
            constraints.gridy = 5;
            constraints.gridwidth = 1;
        }

        m_clSpielfuehrer = new SpielerSternePanel(ISpielerPosition.spielfuehrer, m_bPrint);
        layout.setConstraints(m_clSpielfuehrer, constraints);
        centerPanel.add(m_clSpielfuehrer);

        if (m_bHeim) {
            constraints.gridx = 0;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 0;
            constraints.gridy = 6;
            constraints.gridwidth = 1;
        }

        m_clReserveVerteidiger = new SpielerSternePanel(ISpielerPosition.substBack, m_bPrint);
        layout.setConstraints(m_clReserveVerteidiger, constraints);
        centerPanel.add(m_clReserveVerteidiger);

        if (m_bHeim) {
            constraints.gridx = 1;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 1;
            constraints.gridy = 6;
            constraints.gridwidth = 1;
        }

        m_clReserveMittelfeld = new SpielerSternePanel(ISpielerPosition.substInsideMid, m_bPrint);
        layout.setConstraints(m_clReserveMittelfeld, constraints);
        centerPanel.add(m_clReserveMittelfeld);

        if (m_bHeim) {
            constraints.gridx = 2;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 2;
            constraints.gridy = 6;
            constraints.gridwidth = 1;
        }

        m_clReserveSturm = new SpielerSternePanel(ISpielerPosition.substForward, m_bPrint);
        layout.setConstraints(m_clReserveSturm, constraints);
        centerPanel.add(m_clReserveSturm);

        if (m_bHeim) {
            constraints.gridx = 3;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
        } else {
            constraints.gridx = 3;
            constraints.gridy = 6;
            constraints.gridwidth = 1;
        }

        m_clReserveFluegel = new SpielerSternePanel(ISpielerPosition.substWinger, m_bPrint);
        layout.setConstraints(m_clReserveFluegel, constraints);
        centerPanel.add(m_clReserveFluegel);

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
