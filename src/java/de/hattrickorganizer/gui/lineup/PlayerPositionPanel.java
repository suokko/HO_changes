// %2927626437:de.hattrickorganizer.gui.lineup%
package de.hattrickorganizer.gui.lineup;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import plugins.ISpieler;
import plugins.ISpielerPosition;
import de.hattrickorganizer.gui.Updateable;
import de.hattrickorganizer.gui.model.CBItem;
import de.hattrickorganizer.gui.model.SpielerCBItem;
import de.hattrickorganizer.gui.model.SpielerCBItemRenderer;
import de.hattrickorganizer.model.Lineup;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.Spieler;
import de.hattrickorganizer.model.SpielerPosition;
import de.hattrickorganizer.tools.Helper;


/**
 * Panel, in dem die Spielerposition dargestellt wird und geändert werden kann
 */
final class PlayerPositionPanel extends de.hattrickorganizer.gui.templates.ImagePanel
    implements ItemListener, FocusListener
{
    //~ Static fields/initializers -----------------------------------------------------------------
	private static final long serialVersionUID = 3121389904504282953L;

	/** TODO Missing Parameter Documentation */
 //   protected static final int CAPTAIN = 200;

    /** TODO Missing Parameter Documentation */
 //   protected static final int SET_PIECE = 201;
    private static SpielerCBItem m_clNullSpieler = new SpielerCBItem("", 0f, null);

    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    private final JComboBox m_jcbPlayer = new JComboBox();
    private final JComboBox m_jcbTactic = new JComboBox();

    /** TODO Missing Parameter Documentation */
    private final JLabel m_jlPosition = new JLabel();

    /** TODO Missing Parameter Documentation */

    //Für Minimized
    private final JLabel m_jlPlayer = new JLabel();
    private final SpielerCBItem m_clSelectedPlayer = new SpielerCBItem("", 0f, null);

    /** TODO Missing Parameter Documentation */
    private Updateable m_clUpdater;
    private SpielerCBItem[] m_clCBItems = new SpielerCBItem[0];

    /** TODO Missing Parameter Documentation */
    private boolean m_bMinimize;

    /** TODO Missing Parameter Documentation */
    private int m_iPositionID = -1;

	private int playerId = -1;
	private int tacticOrder = -1;
	
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new SpielerPositionsPanel object.
     *
     * @param updater TODO Missing Constructuor Parameter Documentation
     * @param positionsID TODO Missing Constructuor Parameter Documentation
     */
    protected PlayerPositionPanel(Updateable updater, int positionsID) {
        this(updater, positionsID, false, false);
    }

    /**
     * Creates a new SpielerPositionsPanel object.
     *
     * @param updater TODO Missing Constructuor Parameter Documentation
     * @param positionsID TODO Missing Constructuor Parameter Documentation
     * @param print TODO Missing Constructuor Parameter Documentation
     * @param minimize TODO Missing Constructuor Parameter Documentation
     */
    protected PlayerPositionPanel(Updateable updater, int positionsID, boolean print,
                                 boolean minimize) {
        super(print || minimize);

        m_clUpdater = updater;
        m_iPositionID = positionsID;
        m_bMinimize = minimize;

        setOpaque(true);

        initTaktik(null);
        initLabel();
        initComponents(true);
    }

    //~ Methods ------------------------------------------------------------------------------------

    //--------------------------------------------------------    

    /**
     * Gibt die PositionsID zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    protected int getPositionsID() {
        return m_iPositionID;
    }

//    /**
//     * Setzt ein neues Label aus Beschriftung
//     *
//     * @param text TODO Missing Constructuor Parameter Documentation
//     */
//    public void setPositionsLabel(String text) {
//        m_jlPosition.setText(text);
//
//        repaint();
//    }

    /**
     * Gibt den aktuellen Spieler auf dieser Position zurück, oder null, wenn keiner gewählt wurde
     *
     * @return TODO Missing Return Method Documentation
     */
    private plugins.ISpieler getSelectedPlayer() {
        final Object obj = m_jcbPlayer.getSelectedItem();

        if ((obj != null) && obj instanceof SpielerCBItem) {
            return ((SpielerCBItem) obj).getSpieler();
        }

        return null;
    }

    /**
     * Gibt die Taktik an
     *
     * @return TODO Missing Return Method Documentation
     */
    private byte getTactic() {
        return (byte) ((CBItem) m_jcbTactic.getSelectedItem()).getId();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param event TODO Missing Method Parameter Documentation
     */
    public void focusGained(FocusEvent event) {
        if (getSelectedPlayer() != null) {
            de.hattrickorganizer.gui.HOMainFrame.instance().setActualSpieler(getSelectedPlayer()
                                                                                 .getSpielerID());
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param event TODO Missing Method Parameter Documentation
     */
    public void focusLost(FocusEvent event) {
        //nix
    }

    /**
     * Erzeugt die Komponenten, Die CB für die Spieler und den Listener nicht vergessen!
     *
     * @param aenderbar TODO Missing Constructuor Parameter Documentation
     */
    private void initComponents(boolean aenderbar) {
        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.insets = new Insets(2, 2, 2, 2);

        setLayout(layout);

        //Minimiert
        if (m_bMinimize) {
            setBorder(javax.swing.BorderFactory.createLineBorder(Color.lightGray));
            setBackground(Color.WHITE);

            constraints.gridx = 0;
            constraints.gridy = 0;
            layout.setConstraints(m_jlPosition, constraints);
            add(m_jlPosition);

            constraints.gridx = 0;
            constraints.gridy = 1;
            layout.setConstraints(m_jlPlayer, constraints);
            add(m_jlPlayer);

            setPreferredSize(new Dimension(Helper.calcCellWidth(120),Helper.calcCellWidth(32)));
        }
        //Normal
        else {
            setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.gridwidth = 1;
            layout.setConstraints(m_jlPosition, constraints);
            add(m_jlPosition);

            constraints.gridx = 0;
            constraints.gridy = 1;
            constraints.gridwidth = 5;
            m_jcbPlayer.addFocusListener(this);
            m_jcbPlayer.setMaximumRowCount(15);
            m_jcbPlayer.setRenderer(new SpielerCBItemRenderer());
            layout.setConstraints(m_jcbPlayer, constraints);
            add(m_jcbPlayer);

            if (!aenderbar) {
                m_jcbPlayer.setEnabled(false);
            }

            m_jcbPlayer.setBackground(Color.white);

            //Nur anzeigen, wenn mehr als eine Taktik möglich ist
            if (m_jcbTactic.getItemCount() > 1) {
                constraints.gridx = 1;
                constraints.gridy = 0;
                constraints.gridwidth = 3;
                if (!aenderbar) {
                    m_jcbTactic.setEnabled(false);
                }

                m_jcbTactic.setBackground(Color.white);
                layout.setConstraints(m_jcbTactic, constraints);
                add(m_jcbTactic);

                setPreferredSize(new Dimension(Helper.calcCellWidth(200),Helper.calcCellWidth(55)));// W 80
            } else {
                setPreferredSize(new Dimension(Helper.calcCellWidth(200),Helper.calcCellWidth(55)));// W 50
            }
        }
    }

    //-------------Listener------------------------------------------------
    public void itemStateChanged(java.awt.event.ItemEvent itemEvent) {
        if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
            final Lineup aufstellung = de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                   .getModel()
                                                                                   .getAufstellung();

            final plugins.ISpieler spieler = getSelectedPlayer();

            //Spieler setzen
            if (itemEvent.getSource().equals(m_jcbPlayer)) {
                //Standart
                if (m_iPositionID == ISpielerPosition.setPieces) {
                    if (spieler != null) {
                        aufstellung.setKicker(spieler.getSpielerID());
                    } else {
                        aufstellung.setKicker(0);
                    }
                }
                //Spielführer
                else if (m_iPositionID == ISpielerPosition.captain) {
                    if (spieler != null) {
                        aufstellung.setKapitaen(spieler.getSpielerID());
                    } else {
                        aufstellung.setKapitaen(0);
                    }
                }
                //Andere
                else {
                    if (spieler != null) {
                        aufstellung.setSpielerAtPosition(m_iPositionID, spieler.getSpielerID());
                    } else {
                        aufstellung.setSpielerAtPosition(m_iPositionID, 0);
                    }
                }

                //CBFarben anpassen
                if (spieler != null) {
                    m_jcbPlayer.setForeground(de.hattrickorganizer.gui.templates.ColorLabelEntry
                                               .getForegroundForSpieler(spieler));
                }

                //Taktikwerte anpassen
                setTaktik(getTactic(), spieler);
            } else if (itemEvent.getSource().equals(m_jcbTactic)) {
                aufstellung.getPositionById(m_iPositionID).setTaktik(getTactic());
            }

            //Aktualisierung der Tabellen
            if (spieler != null) {
                de.hattrickorganizer.gui.HOMainFrame.instance().setActualSpieler(spieler
                                                                                 .getSpielerID());
            }

            //Alle anderen Position aktualisieren
            m_clUpdater.update();
        }
    }

    /**
     * Erneuert die Daten in den Komponenten
     *
     * @param spieler TODO Missing Constructuor Parameter Documentation
     */
    public void refresh(Vector<ISpieler> spieler) {
        Spieler aktuellerSpieler = null;
		playerId = -1;
        if (m_iPositionID == ISpielerPosition.setPieces) {
            aktuellerSpieler = HOVerwaltung.instance().getModel().getSpieler(HOVerwaltung.instance()
                                                                                         .getModel()
                                                                                         .getAufstellung()
                                                                                         .getKicker());
			if (aktuellerSpieler!=null) {
				playerId = aktuellerSpieler.getSpielerID();
			}	
			tacticOrder=-1;                                                                                         
        } else if (m_iPositionID == ISpielerPosition.captain) {
            aktuellerSpieler = HOVerwaltung.instance().getModel().getSpieler(HOVerwaltung.instance()
                                                                                         .getModel()
                                                                                         .getAufstellung()
                                                                                         .getKapitaen());
			if (aktuellerSpieler!=null) {
				playerId = aktuellerSpieler.getSpielerID();
			}	
			tacticOrder=-1;                                                                                         
        } else {
            //Aktuell aufgestellten Spieler holen
            final SpielerPosition position = HOVerwaltung.instance().getModel().getAufstellung()
                                                         .getPositionById(m_iPositionID);

            if (position != null) {
                aktuellerSpieler = HOVerwaltung.instance().getModel().getSpieler(position
                                                                                 .getSpielerId());
                                                                                 
				if (aktuellerSpieler!=null) {
					playerId = aktuellerSpieler.getSpielerID();
				}					
				tacticOrder = position.getTaktik();
                setTaktik(position.getTaktik(), aktuellerSpieler);				
            }
        }

        setSpielerListe(spieler, aktuellerSpieler);

        initLabel();

        repaint();
    }

    /**
     * Setzt die Liste der möglichen Spieler für diese Position und den aktuell gewählten Spieler
     *
     * @param spielerListe TODO Missing Constructuor Parameter Documentation
     * @param aktuellerSpieler TODO Missing Constructuor Parameter Documentation
     */
    protected void setSpielerListe(Vector<ISpieler> spielerListe, Spieler aktuellerSpieler) {
        //Listener entfernen
        m_jcbPlayer.removeItemListener(this);

        final DefaultComboBoxModel cbmodel = ((DefaultComboBoxModel) m_jcbPlayer.getModel());

        //Alle Items entfernen
        cbmodel.removeAllElements();

        //Genug CBItems für alle Spieler
        if (m_clCBItems.length != spielerListe.size()) {
            SpielerCBItem[] tempCB = new SpielerCBItem[spielerListe.size()];

            //Mit SpielerCBItems füllen: Vorzugsweise alte wiederverwenden
            for (int i = 0; i < tempCB.length; i++) {
                //Wiederverwenden
                if ((m_clCBItems.length > i) && (m_clCBItems[i] != null)) {
                    tempCB[i] = m_clCBItems[i];

                    //HOLogger.instance().log(getClass(), "Use old SpielerCBItem " + this.m_iPositionsID );
                }
                //Neu erzeugen
                else {
                    tempCB[i] = new SpielerCBItem("", 0f, null);

                    //HOLogger.instance().log(getClass(), "Create new SpielerCBItem " + this.m_iPositionsID );
                }
            }

            //Referenz leeren und neu setzen
            m_clCBItems = null;
            m_clCBItems = tempCB;
        }

        //Aktueller Spieler
        cbmodel.addElement(createSpielerCBItem(m_clSelectedPlayer, aktuellerSpieler));

        //Kein Spieler
        cbmodel.addElement(m_clNullSpieler);

        //Spielerliste sortieren
        SpielerCBItem[] cbItems = new SpielerCBItem[spielerListe.size()];

        for (int i = 0; i < spielerListe.size(); i++) {
            cbItems[i] = createSpielerCBItem(m_clCBItems[i], ((Spieler) spielerListe.get(i)));
        }

        java.util.Arrays.sort(cbItems);

        for (int i = 0; i < cbItems.length; i++) {
            //Alle anderen Spieler
            cbmodel.addElement(cbItems[i]);
        }

        //CBFarben anpassen
        if (aktuellerSpieler != null) {
            m_jcbPlayer.setForeground(de.hattrickorganizer.gui.templates.ColorLabelEntry
                                       .getForegroundForSpieler(aktuellerSpieler));
        }

        //Speicher freigeben
        cbItems = null;

        //Listener wieder hinzu
        m_jcbPlayer.addItemListener(this);

        //Minimized 
        if ((m_clSelectedPlayer != null) && (m_clSelectedPlayer.getSpieler() != null)) {
            m_jlPlayer.setText(m_clSelectedPlayer.getSpieler().getName());
            m_jlPlayer.setIcon(Helper.getImage4Position(HOVerwaltung.instance().getModel().getAufstellung().getPositionBySpielerId(m_clSelectedPlayer.getSpieler()
                                                                                                                                                                       .getSpielerID()),
                                                                                    m_clSelectedPlayer.getSpieler()
                                                                                                        .getTrikotnummer()));
        } else {
            m_jlPlayer.setText("");
            m_jlPlayer.setIcon(null);
        }

        setTaktik(getTactic(), aktuellerSpieler);
    }

    /**
     * Setzt die aktuelle Taktik
     *
     * @param taktik TODO Missing Constructuor Parameter Documentation
     * @param aktuellerSpieler TODO Missing Constructuor Parameter Documentation
     */
    private void setTaktik(byte taktik, plugins.ISpieler aktuellerSpieler) {
        //Listener entfernen
        m_jcbTactic.removeItemListener(this);

        //Taktik neu füllen!
        initTaktik(aktuellerSpieler);

        //Suche nach der Taktik
        Helper.markierenComboBox(m_jcbTactic, taktik);

        //Listener hinzu
        m_jcbTactic.addItemListener(this);
    }

    /**
     * Setzt das Label
     */
    private void initLabel() {
    	final Lineup lineup = HOVerwaltung.instance().getModel().getAufstellung();
    	
        if (m_iPositionID == ISpielerPosition.setPieces) {
            m_jlPosition.setText(HOVerwaltung.instance().getLanguageString("Standards"));
        } else if (m_iPositionID == ISpielerPosition.captain) {
            m_jlPosition.setText(HOVerwaltung.instance().getLanguageString("Spielfuehrer"));
        } else {
            final SpielerPosition position = lineup.getPositionById(m_iPositionID);

            if (position != null) {
                //Reserve
            	final String nameForPosition = (m_bMinimize)?SpielerPosition.getKurzNameForPosition(position
                        .getPosition()):SpielerPosition.getKurzNameForPosition(position.getPosition());
                if ((m_jcbTactic.getItemCount() == 1) && (position.getId() != ISpielerPosition.keeper)) {
                	//special naming for reserve defender
                	if (!m_bMinimize && position.getId() == ISpielerPosition.substDefender) {
                		m_jlPosition.setText(HOVerwaltung.instance().getLanguageString("Reserve")
                                + " " + HOVerwaltung.instance().getLanguageString("defender"));
                	} else {
                		m_jlPosition.setText(HOVerwaltung.instance().getLanguageString("Reserve")
                                         + " " + nameForPosition);
                	}
                } else {
                    m_jlPosition.setText(nameForPosition);
                }
            }
        }
        
        //Minimized 
        if ((m_clSelectedPlayer != null) && (m_clSelectedPlayer.getSpieler() != null)) {
            m_jlPlayer.setText(m_clSelectedPlayer.getSpieler().getName());
            m_jlPlayer.setIcon(Helper.getImage4Position(lineup.getPositionBySpielerId(m_clSelectedPlayer.getSpieler().getSpielerID()),
                                                                                    m_clSelectedPlayer.getSpieler()
                                                                                                        .getTrikotnummer()));
        } else {
            m_jlPlayer.setText("");
            m_jlPlayer.setIcon(null);
        }
    }

    /**
     * Setzt die Taktik je nach SpielerPosition
     *
     * @param aktuellerSpieler TODO Missing Constructuor Parameter Documentation
     */
    private void initTaktik(plugins.ISpieler aktuellerSpieler) {
        m_jcbTactic.removeAllItems();

        switch (m_iPositionID) {
            case ISpielerPosition.keeper: {
                m_jcbTactic.addItem(new CBItem(HOVerwaltung.instance().getLanguageString("Normal"),
                                               ISpielerPosition.NORMAL));
                break;
            }

            case ISpielerPosition.rightBack:
            case ISpielerPosition.leftBack: {
            	addTactic(aktuellerSpieler,HOVerwaltung.instance().getLanguageString("Normal"),ISpielerPosition.NORMAL);
            	addTactic(aktuellerSpieler,HOVerwaltung.instance().getLanguageString("Offensiv"),ISpielerPosition.OFFENSIVE);
            	addTactic(aktuellerSpieler,HOVerwaltung.instance().getLanguageString("Defensiv"),ISpielerPosition.DEFENSIVE);
            	addTactic(aktuellerSpieler,HOVerwaltung.instance().getLanguageString("zurMitte"),ISpielerPosition.TOWARDS_MIDDLE);
                break;
            }

            case ISpielerPosition.rightCentralDefender:
            case ISpielerPosition.leftCentralDefender: {
            	addTactic(aktuellerSpieler,HOVerwaltung.instance().getLanguageString("Normal"),ISpielerPosition.NORMAL);
            	addTactic(aktuellerSpieler,HOVerwaltung.instance().getLanguageString("Offensiv"),ISpielerPosition.OFFENSIVE);
            	addTactic(aktuellerSpieler,HOVerwaltung.instance().getLanguageString("nachAussen"),ISpielerPosition.TOWARDS_WING);
                break;
            }
            
            case ISpielerPosition.middleCentralDefender: {
            	addTactic(aktuellerSpieler,HOVerwaltung.instance().getLanguageString("Normal"),ISpielerPosition.NORMAL);
            	addTactic(aktuellerSpieler,HOVerwaltung.instance().getLanguageString("Offensiv"),ISpielerPosition.OFFENSIVE);
            	break;
            }

            case ISpielerPosition.rightInnerMidfield:
            case ISpielerPosition.leftInnerMidfield: {
            	addTactic(aktuellerSpieler,HOVerwaltung.instance().getLanguageString("Normal"),ISpielerPosition.NORMAL);
            	addTactic(aktuellerSpieler,HOVerwaltung.instance().getLanguageString("Offensiv"),ISpielerPosition.OFFENSIVE);
            	addTactic(aktuellerSpieler,HOVerwaltung.instance().getLanguageString("Defensiv"),ISpielerPosition.DEFENSIVE);
            	addTactic(aktuellerSpieler,HOVerwaltung.instance().getLanguageString("nachAussen"),ISpielerPosition.TOWARDS_WING);
            	break;
            }

            case ISpielerPosition.centralInnerMidfield: {
            	addTactic(aktuellerSpieler,HOVerwaltung.instance().getLanguageString("Normal"),ISpielerPosition.NORMAL);
            	addTactic(aktuellerSpieler,HOVerwaltung.instance().getLanguageString("Offensiv"),ISpielerPosition.OFFENSIVE);
            	addTactic(aktuellerSpieler,HOVerwaltung.instance().getLanguageString("Defensiv"),ISpielerPosition.DEFENSIVE);
            	break;
            }
            
            case ISpielerPosition.leftWinger:
            case ISpielerPosition.rightWinger: {
            	addTactic(aktuellerSpieler,HOVerwaltung.instance().getLanguageString("Normal"),ISpielerPosition.NORMAL);
            	addTactic(aktuellerSpieler,HOVerwaltung.instance().getLanguageString("Offensiv"),ISpielerPosition.OFFENSIVE);
            	addTactic(aktuellerSpieler,HOVerwaltung.instance().getLanguageString("Defensiv"),ISpielerPosition.DEFENSIVE);
            	addTactic(aktuellerSpieler,HOVerwaltung.instance().getLanguageString("zurMitte"),ISpielerPosition.TOWARDS_MIDDLE);
                break;
            }

            case ISpielerPosition.rightForward:
            case ISpielerPosition.leftForward: {
            	addTactic(aktuellerSpieler,HOVerwaltung.instance().getLanguageString("Normal"),ISpielerPosition.NORMAL);
            	addTactic(aktuellerSpieler,HOVerwaltung.instance().getLanguageString("Defensiv"),ISpielerPosition.DEFENSIVE);
            	addTactic(aktuellerSpieler,HOVerwaltung.instance().getLanguageString("nachAussen"),ISpielerPosition.TOWARDS_WING);
                break;
            }

            case ISpielerPosition.centralForward: {
            	addTactic(aktuellerSpieler,HOVerwaltung.instance().getLanguageString("Normal"),ISpielerPosition.NORMAL);
            	addTactic(aktuellerSpieler,HOVerwaltung.instance().getLanguageString("Defensiv"),ISpielerPosition.DEFENSIVE);
            	break;
            }

            
            case ISpielerPosition.substDefender:
            case ISpielerPosition.substForward:
            case ISpielerPosition.substInnerMidfield:
            case ISpielerPosition.substKeeper:
            case ISpielerPosition.substWinger: {
                m_jcbTactic.addItem(new CBItem(HOVerwaltung.instance().getLanguageString("Normal"),
                                               ISpielerPosition.NORMAL));
                break;
            }

            default:
                m_jcbTactic.addItem(new CBItem(HOVerwaltung.instance().getLanguageString("Normal"),
                                               ISpielerPosition.NORMAL));
        }
    }

    
    private void addTactic(ISpieler currentPlayer, String text, byte playerPosition){
    	if (currentPlayer != null) {
            text += " ("
                    + currentPlayer.calcPosValue(SpielerPosition.getPosition(m_iPositionID,
                    		playerPosition),
                                                    true) + ")";
        }

        m_jcbTactic.addItem(new CBItem(text, playerPosition));
    }
    //-------------private-------------------------------------------------

    /**
     * Generiert ein SpielerCBItem für einen Spieler
     *
     * @param item TODO Missing Constructuor Parameter Documentation
     * @param spieler TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private SpielerCBItem createSpielerCBItem(SpielerCBItem item, Spieler spieler) {
        if (spieler != null) {
            if (m_iPositionID == ISpielerPosition.setPieces) {
                item.setValues(spieler.getName(),
                               spieler.getStandards()
                               + spieler.getSubskill4SkillWithOffset(ISpieler.SKILL_STANDARDS),
                               spieler);
                return item;
            } else if (m_iPositionID == ISpielerPosition.captain) {
                item.setValues(spieler.getName(),
                               Helper.round(
                            		   HOVerwaltung.instance().getModel().getAufstellung().getAverageExperience(spieler.getSpielerID()), 
                            		   gui.UserParameter.instance().anzahlNachkommastellen),
                               spieler);
                return item;
            } else {
                final SpielerPosition position = HOVerwaltung.instance().getModel().getAufstellung()
                                                                                        .getPositionById(m_iPositionID);

                if (position != null) {                	                
                    item.setValues(spieler.getName(),
                                   spieler.calcPosValue(position.getPosition(), true), spieler);					
                    return item;
                } 
                
                return m_clNullSpieler;
                
            }
        }
        //Kein Spieler
        return m_clNullSpieler;
        
    }
	public int getPlayerId() {
		return playerId;
	}

	public int getTacticOrder() {
		return tacticOrder;
	}

	/**
	 * Exposes the player combo box to reset the swap button if needed.
	 * 
	 * @return the player {@link JComboBox}.
	 */
	protected JComboBox getPlayerComboBox() {
		return m_jcbPlayer;
	}

}
