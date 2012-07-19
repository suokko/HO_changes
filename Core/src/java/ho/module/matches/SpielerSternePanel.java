// %2591273278:de.hattrickorganizer.gui.matches%
package ho.module.matches;

import ho.core.gui.comp.entry.ColorLabelEntry;
import ho.core.gui.comp.entry.RatingTableEntry;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ImageUtilities;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.HOVerwaltung;
import ho.core.model.match.MatchLineup;
import ho.core.model.match.MatchLineupPlayer;
import ho.core.model.player.ISpielerPosition;
import ho.core.util.Helper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;



/**
 * Zeigt den Spieler an der Position an und dessen Sterne
 */
final class SpielerSternePanel extends ImagePanel implements ActionListener {

	private static final long serialVersionUID = 744463551751056443L;

    //~ Instance fields ----------------------------------------------------------------------------


	private int PANEL_WIDTH = Helper.calcCellWidth(120);
	private int PANEL_HEIGHT = Helper.calcCellWidth(59);
	private int PANEL_HEIGHT_REDUCED = Helper.calcCellWidth(44);

	/** TODO Missing Parameter Documentation */
    protected int m_iPositionsID = -1;
    public final JButton m_jbSpieler = new JButton();
    private final JLabel m_jlPosition = new JLabel();
    private final JLabel m_jlSpecial = new JLabel();
    private MatchLineup m_clMatchLineup;
    private MatchLineupPlayer m_clMatchPlayer;
    private RatingTableEntry m_jpSterne = new RatingTableEntry();
    private Box m_jpDummy = new Box(BoxLayout.X_AXIS);

    private JPanel m_jpParent;
    private boolean m_bOnScreen = false;
    GridBagConstraints m_gbcConstraints = new GridBagConstraints();


    protected SpielerSternePanel(int positionsID, JPanel parent, int x, int y) {
        this(positionsID, false, parent, x, y);
    }


    protected SpielerSternePanel(int positionsID, boolean print, JPanel parent, int x, int y) {
        super(print);

        m_iPositionsID = positionsID;
        m_jpParent = parent;

        initComponents();
        initLabel(positionsID, (byte)0); // Tactic does not matter anymore...


        // This one size fits all will be bad whenever it is decided to
        // remove subs/captain/setpiece-taker panels when empty
        m_jpDummy.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        // Init the constraints object, and add this panel to the parent
        m_gbcConstraints.anchor = GridBagConstraints.NORTH;
        m_gbcConstraints.fill = GridBagConstraints.NONE;
        m_gbcConstraints.weightx = 0.0;
        m_gbcConstraints.weighty = 0.0;
        m_gbcConstraints.insets = new Insets(2, 2, 2, 2);
        m_gbcConstraints.gridx = x;
        m_gbcConstraints.gridy = y;
        m_gbcConstraints.gridwidth = 1;

    	addPanel();
    }

    public final void actionPerformed(ActionEvent e) {
        new SpielerDetailDialog(ho.core.gui.HOMainFrame.instance(), m_clMatchPlayer,
                                m_clMatchLineup);
    }


    protected final void clear() {

    	// We want empty frames in the on field positions hidden when empty
    	if ((m_bOnScreen == true) &&
    			( (m_iPositionsID >= ISpielerPosition.startLineup ) &&
    					(m_iPositionsID < ISpielerPosition.startReserves))) {
    		removePanel();
    	}

    	// lets leave the position text, right? - Blaghaid
    	// m_jlPosition.setText("");
        m_jbSpieler.setText("");
        m_jbSpieler.setIcon(null);
        m_jbSpieler.setEnabled(false);
        m_jpSterne.clear();
        m_jlSpecial.setIcon(null);
        repaint();
    }

    /**
     * Erzeugt die Komponenten
     */
    private final void initComponents() {
        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 0;
        constraints.insets = new Insets(1, 1, 1, 1);

        setLayout(layout);

        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        constraints.gridx = 0;
        constraints.gridy = 0;
        m_jlPosition.setPreferredSize(new Dimension(100, 10));
        layout.setConstraints(m_jlPosition, constraints);
        add(m_jlPosition);

        final JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.setBackground(ColorLabelEntry.BG_STANDARD);
        panel.setOpaque(true);

        m_jbSpieler.setToolTipText(ho.core.model.HOVerwaltung.instance().getLanguageString("tt_Spiel_Spielerdetails"));
        m_jbSpieler.setHorizontalAlignment(SwingConstants.LEFT);
        m_jbSpieler.setMargin(new Insets(0, 1, 0, 1));
        m_jbSpieler.setPreferredSize(new Dimension(125, 15));
        m_jbSpieler.setFocusPainted(false);
        m_jbSpieler.setEnabled(false);
        m_jbSpieler.addActionListener(this);
        m_jbSpieler.setBackground(ColorLabelEntry.BG_STANDARD);
        m_jbSpieler.setOpaque(true);
        m_jbSpieler.setBorder(BorderFactory.createEmptyBorder());
        panel.add(m_jbSpieler, BorderLayout.CENTER);

        panel.add(m_jlSpecial, BorderLayout.EAST);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weighty = 0.5; // Give extra vertical space to the player button
        constraints.gridwidth = 1;
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
                constraints.weighty = 0; // No vertical stretch for this one

                final JComponent component = m_jpSterne.getComponent(false);
                layout.setConstraints(component, constraints);
                add(component);

                setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
                break;
            }

            default:
                setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT_REDUCED));
        }
    }


    public final void refresh(MatchLineup lineup, MatchLineupPlayer player) {
        m_clMatchLineup = lineup;
        m_clMatchPlayer = player;

        if (player != null) {

        	// Make sure this is on screen, we got a player to display
        	if (m_bOnScreen == false) {
        		addPanel();
        	}
            m_jbSpieler.setText(player.getSpielerName().substring(0, 1) + "." + player.getSpielerName()
            							.substring(player.getSpielerName().indexOf(" ")+1));


            //SpielerPosition pos = new SpielerPosition( player.getId (), player.getSpielerId (), player.getTaktik () );
            int trickotnummer = 0;
            final ho.core.model.player.Spieler spieler = ho.core.model.HOVerwaltung.instance()
                                                                                                      .getModel()
                                                                                                      .getSpieler(player
                                                                                                                  .getSpielerId());

            if (spieler != null) {
                trickotnummer = spieler.getTrikotnummer();
                m_jlSpecial.setIcon(ThemeManager.getIcon(HOIconName.SPECIAL[spieler.getSpezialitaet()]));
            } else {
                m_jlSpecial.setIcon(null);
            }

            m_jbSpieler.setIcon(ImageUtilities.getImage4Position(player.getId(),player.getTaktik(),trickotnummer));
            m_jbSpieler.setEnabled(player.getSpielerId() > 0);

            //m_jlPosition.setText ( SpielerPosition.getNameForPosition ( pos.getPosition () ) );
            m_jpSterne.setRating((float) player.getRating() * 2f, true);

            initLabel(player.getId(), player.getTaktik());

        } else {
            clear();
        }
        repaint();
    }

    private void removePanel() {
    	m_jpParent.remove(this);
    	m_jpParent.add(m_jpDummy, m_gbcConstraints);
    	m_bOnScreen = false;

    	//m_jpParent.revalidate();
    	m_jpParent.repaint();
    }

    private void addPanel() {
    	m_jpParent.remove(m_jpDummy);
    	m_jpParent.add(this, m_gbcConstraints);
    	m_bOnScreen = true;

    	//m_jpParent.revalidate();
    	m_jpParent.repaint();
    }


    protected final void initLabel(int posid, byte taktik) {

    	switch (posid) {

    		case ISpielerPosition.setPieces : {
    			m_jlPosition.setText(HOVerwaltung.instance().getLanguageString("Standards"));
    			break;
    		}
    		case ISpielerPosition.captain : {
    			m_jlPosition.setText(HOVerwaltung.instance().getLanguageString("Spielfuehrer"));
    			break;
    		}
    		case ISpielerPosition.substDefender: {
    			m_jlPosition.setText(HOVerwaltung.instance().getLanguageString("Reserve")
    					+ " "
    					+ HOVerwaltung.instance().getLanguageString("defender"));
    			break;
    		}
    		case ISpielerPosition.substForward: {
    			m_jlPosition.setText(HOVerwaltung.instance().getLanguageString("Reserve")
    					+ " "
    					+ HOVerwaltung.instance().getLanguageString("ls.player.position.forward"));
    			break;
    		}
    		case ISpielerPosition.substWinger: {
    			m_jlPosition.setText(HOVerwaltung.instance().getLanguageString("Reserve")
    					+ " "
    					+ HOVerwaltung.instance().getLanguageString("ls.player.position.winger"));
    			break;
    		}
    		case ISpielerPosition.substInnerMidfield: {
    			m_jlPosition.setText(HOVerwaltung.instance().getLanguageString("Reserve")
    					+ " "
    					+ HOVerwaltung.instance().getLanguageString("ls.player.position.innermidfielder"));
    			break;
    		}
    		case ISpielerPosition.substKeeper: {
    			m_jlPosition.setText(HOVerwaltung.instance().getLanguageString("Reserve")
    					+ " "
    					+ HOVerwaltung.instance().getLanguageString("ls.player.position.keeper"));
    			break;
    		}
    		default:
    		{
    			// Special check here for the replaced players, we got a range of at least 3...
    			if ((posid >= ISpielerPosition.ausgewechselt) && (posid <= ISpielerPosition.ausgewechseltEnd)) {
    				m_jlPosition.setText(HOVerwaltung.instance().getLanguageString("Ausgewechselt"));
    			break;
    			} else {

    				m_jlPosition.setText(ho.core.model.player.SpielerPosition
    						.getNameForPosition(ho.core.model.player.SpielerPosition.getPosition(posid, taktik)));
    			}
    		}
        }
    }
}
