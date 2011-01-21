// %2591273278:de.hattrickorganizer.gui.matches%
package de.hattrickorganizer.gui.matches;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import plugins.ISpielerPosition;
import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.gui.templates.RatingTableEntry;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.matches.MatchLineup;
import de.hattrickorganizer.model.matches.MatchLineupPlayer;
import de.hattrickorganizer.tools.Helper;


/**
 * Zeigt den Spieler an der Position an und dessen Sterne
 */
final class SpielerSternePanel extends ImagePanel implements ActionListener {
	
	private static final long serialVersionUID = 744463551751056443L;
	
    //~ Instance fields ----------------------------------------------------------------------------

	/** TODO Missing Parameter Documentation */
    protected int m_iPositionsID = -1;
    private final JButton m_jbSpieler = new JButton();
    private final JLabel m_jlPosition = new JLabel();
    private final JLabel m_jlSpecial = new JLabel();
    private MatchLineup m_clMatchLineup;
    private MatchLineupPlayer m_clMatchPlayer;
    private RatingTableEntry m_jpSterne = new RatingTableEntry();

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new SpielerSternePanel object.
     *
     * @param positionsID TODO Missing Constructuor Parameter Documentation
     */
    protected SpielerSternePanel(int positionsID) {
        this(positionsID, false);
    }

    /**
     * Creates a new SpielerSternePanel object.
     *
     * @param positionsID TODO Missing Constructuor Parameter Documentation
     * @param print TODO Missing Constructuor Parameter Documentation
     */
    protected SpielerSternePanel(int positionsID, boolean print) {
        super(print);

        m_iPositionsID = positionsID;

        initComponents();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param e TODO Missing Method Parameter Documentation
     */
    public final void actionPerformed(ActionEvent e) {
        new SpielerDetailDialog(de.hattrickorganizer.gui.HOMainFrame.instance(), m_clMatchPlayer,
                                m_clMatchLineup);
    }

    /**
     * TODO Missing Method Documentation
     */
    protected final void clear() {
        m_jlPosition.setText("");
        m_jbSpieler.setText("");
        m_jbSpieler.setIcon(null);
        m_jbSpieler.setEnabled(false);
        m_jpSterne.clear();
        m_jlSpecial.setIcon(null);
    }

    /**
     * Erzeugt die Komponenten
     */
    private final void initComponents() {
        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.insets = new Insets(1, 1, 1, 1);

        setLayout(layout);

        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        constraints.gridx = 0;
        constraints.gridy = 0;
        m_jlPosition.setPreferredSize(new Dimension(100, 15));
        layout.setConstraints(m_jlPosition, constraints);
        add(m_jlPosition);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;

        final JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.setBackground(Color.WHITE);
        panel.setOpaque(true);

        m_jbSpieler.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("tt_Spiel_Spielerdetails"));
        m_jbSpieler.setHorizontalAlignment(SwingConstants.LEFT);
        m_jbSpieler.setMargin(new Insets(0, 1, 0, 1));
        m_jbSpieler.setPreferredSize(new Dimension(145, 16));
        m_jbSpieler.setFocusPainted(false);
        m_jbSpieler.setEnabled(false);
        m_jbSpieler.addActionListener(this);
        m_jbSpieler.setBackground(Color.WHITE);
        m_jbSpieler.setOpaque(true);
        m_jbSpieler.setBorder(BorderFactory.createEmptyBorder());
        panel.add(m_jbSpieler, BorderLayout.CENTER);

        panel.add(m_jlSpecial, BorderLayout.EAST);

        constraints.gridx = 0;
        constraints.gridy = 1;
        layout.setConstraints(panel, constraints);
        add(panel);

        switch (m_iPositionsID) {
            case ISpielerPosition.keeper:
            case ISpielerPosition.rightBack:
            case ISpielerPosition.leftBack:
            case ISpielerPosition.rightCentralDefender:
            case ISpielerPosition.middleCentralDefender:
            case ISpielerPosition.leftCentralDefender:
            case ISpielerPosition.rightInnerMidfield:
            case ISpielerPosition.centralInnerMidfield:
            case ISpielerPosition.leftInnerMidfield:
            case ISpielerPosition.leftWinger:
            case ISpielerPosition.rightWinger:
            case ISpielerPosition.rightForward:
            case ISpielerPosition.centralForward:
            case ISpielerPosition.leftForward:
            case ISpielerPosition.ausgewechselt: {
                constraints.gridx = 0;
                constraints.gridy = 2;

                final JComponent component = m_jpSterne.getComponent(false);
                layout.setConstraints(component, constraints);
                add(component);

                setPreferredSize(new Dimension(Helper.calcCellWidth(150),
                                               Helper.calcCellWidth(62)));
                break;
            }

            default:
                setPreferredSize(new Dimension(Helper.calcCellWidth(150),
                                               Helper.calcCellWidth(36)));
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param lineup TODO Missing Method Parameter Documentation
     * @param player TODO Missing Method Parameter Documentation
     */
    public final void refresh(MatchLineup lineup, MatchLineupPlayer player) {
        m_clMatchLineup = lineup;
        m_clMatchPlayer = player;

        if (player != null) {
            m_jbSpieler.setText(player.getSpielerName());

            //SpielerPosition pos = new SpielerPosition( player.getId (), player.getSpielerId (), player.getTaktik () );
            int trickotnummer = 0;
            final de.hattrickorganizer.model.Spieler spieler = de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                      .getModel()
                                                                                                      .getSpieler(player
                                                                                                                  .getSpielerId());

            if (spieler != null) {
                trickotnummer = spieler.getTrikotnummer();
                m_jlSpecial.setIcon(Helper.getImageIcon4Spezialitaet(spieler
                                                                                                .getSpezialitaet()));
            } else {
                m_jlSpecial.setIcon(null);
            }

            m_jbSpieler.setIcon(Helper.getImage4Position(player.getId(),
                                                                                    player
                                                                                    .getTaktik(),
                                                                                    trickotnummer));
            m_jbSpieler.setEnabled(player.getSpielerId() > 0);

            //m_jlPosition.setText ( SpielerPosition.getNameForPosition ( pos.getPosition () ) );
            m_jpSterne.setRating((float) player.getRating() * 2f, true);

            initLabel(player.getId(), player.getTaktik());
        } else {
            clear();
        }
    }

    /**
     * Setzt das Label
     *
     * @param posid TODO Missing Constructuor Parameter Documentation
     * @param taktik TODO Missing Constructuor Parameter Documentation
     */
    protected final void initLabel(int posid, byte taktik) {
        if (m_iPositionsID == ISpielerPosition.ausgewechselt) {
            m_jlPosition.setText(HOVerwaltung.instance().getLanguageString("Ausgewechselt"));
        } else if (m_iPositionsID == ISpielerPosition.setPieces) {
            m_jlPosition.setText(HOVerwaltung.instance().getLanguageString("Standards"));
        } else if (m_iPositionsID == ISpielerPosition.captain) {
            m_jlPosition.setText(HOVerwaltung.instance().getLanguageString("Spielfuehrer"));
        } else if (m_iPositionsID == ISpielerPosition.substDefender) {
            m_jlPosition.setText(HOVerwaltung.instance().getLanguageString("Reserve")
                                 + " "
                                 + HOVerwaltung.instance().getLanguageString("defender"));
        } else if (m_iPositionsID == ISpielerPosition.substForward) {
            m_jlPosition.setText(HOVerwaltung.instance().getLanguageString("Reserve")
                                 + " "
                                 + HOVerwaltung.instance().getLanguageString("Sturm"));
        } else if (m_iPositionsID == ISpielerPosition.substWinger) {
            m_jlPosition.setText(HOVerwaltung.instance().getLanguageString("Reserve")
                                 + " "
                                 + HOVerwaltung.instance().getLanguageString("Fluegelspiel"));
        } else if (m_iPositionsID == ISpielerPosition.substInnerMidfield) {
            m_jlPosition.setText(HOVerwaltung.instance().getLanguageString("Reserve")
                                 + " "
                                 + HOVerwaltung.instance().getLanguageString("Mittelfeld"));
        } else if (m_iPositionsID == ISpielerPosition.substKeeper) {
            m_jlPosition.setText(HOVerwaltung.instance().getLanguageString("Reserve")
                                 + " "
                                 + HOVerwaltung.instance().getLanguageString("Torwart"));
        } else {
            if (posid >= 0) {
                //Reserve
                //Bei Reserve ist die posId immer -1! Fehler!
                if (posid >= ISpielerPosition.startReserves) {
                    m_jlPosition.setText(HOVerwaltung.instance().getLanguageString("Reserve")
                                         + " "
                                         + de.hattrickorganizer.model.SpielerPosition
                                           .getNameForPosition(de.hattrickorganizer.model.SpielerPosition
                                                               .getPosition(posid, taktik)));
                } else {
                    m_jlPosition.setText(de.hattrickorganizer.model.SpielerPosition
                                         .getNameForPosition(de.hattrickorganizer.model.SpielerPosition
                                                             .getPosition(posid, taktik)));
                }

                //NIX
            } else {
                m_jlPosition.setText("");
            }
        }
    }
}
