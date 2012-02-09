// %2976388207:de.hattrickorganizer.gui.lineup%
package de.hattrickorganizer.gui.lineup;

import gui.HOIconName;
import ho.core.gui.theme.ThemeManager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import plugins.ISpieler;
import plugins.ISpielerPosition;
import de.hattrickorganizer.gui.HOMainFrame;
import de.hattrickorganizer.gui.RefreshManager;
import de.hattrickorganizer.gui.model.AufstellungCBItem;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.Lineup;
import de.hattrickorganizer.model.Spieler;
import de.hattrickorganizer.tools.HOLogger;


/**
 * Enthält die einzelnen Positionen
 */
public class LineupPositionsPanel extends de.hattrickorganizer.gui.templates.RasenPanel
    implements de.hattrickorganizer.gui.Refreshable, de.hattrickorganizer.gui.Updateable,
               ActionListener
{
	
	private static final long serialVersionUID = -9098199182886069003L;
	
    //~ Instance fields ----------------------------------------------------------------------------
	private LineupPanel m_clLineupPanel;
    private JButton m_jbDrucken = new JButton(ThemeManager.getIcon(HOIconName.PRINTER));
    private JButton m_jbFlipSide = new JButton(ThemeManager.getIcon(HOIconName.RELOAD));
	private JButton m_jbMidiFrame = new JButton(ThemeManager.getIcon(HOIconName.MIDLINEUPFRAME));
	private JButton m_jbMiniFrame = new JButton(ThemeManager.getIcon(HOIconName.MINLINEUPFRAME));

	private PlayerPositionPanel m_clCentralForward;
	private PlayerPositionPanel m_clCentralInnerMidfielder;
	private PlayerPositionPanel m_clLeftBack;
    private PlayerPositionPanel m_clLeftWinger;
    private PlayerPositionPanel m_clLeftCentralDefender;
    private PlayerPositionPanel m_clLeftInnerMidfielder;
    private PlayerPositionPanel m_clLeftForward;
    private PlayerPositionPanel m_clMiddleCentralDefender;
    private PlayerPositionPanel m_clRightBack;
    private PlayerPositionPanel m_clRightWinger;
    private PlayerPositionPanel m_clRightCentralDefender;
    private PlayerPositionPanel m_clRightInnerMidfielder;
    private PlayerPositionPanel m_clRightForward;
    private PlayerPositionPanel m_clSubstWinger;
    private PlayerPositionPanel m_clSubstMidfield;
    private PlayerPositionPanel m_clSubstForward;
    private PlayerPositionPanel m_clSubstKeeper;
    private PlayerPositionPanel m_clSubstDefender;
    private PlayerPositionPanel m_clCaptain;
    private PlayerPositionPanel m_clSetPieceTaker;
    private PlayerPositionPanel m_clKeeper;
    
    private javax.swing.JLayeredPane centerPanel;
    private final SwapPositionsManager swapPositionsManager = new SwapPositionsManager(
			this);
    private final AufstellungsAssistentPanel assistantPanel;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new LineupPositionsPanel object.
     *
     * @param panel TODO Missing Constructuor Parameter Documentation
     */
    public LineupPositionsPanel(LineupPanel panel) {
        m_clLineupPanel = panel;
        assistantPanel = panel.getAufstellungsAssitentPanel();
        initComponentes();

        RefreshManager.instance().registerRefreshable(this);
    }

    //~ Methods ------------------------------------------------------------------------------------

    public javax.swing.JLayeredPane getCenterPanel() {
    	return centerPanel;
    }
    
    /**
     * TODO Missing Method Documentation
     *
     * @param actionEvent TODO Missing Method Parameter Documentation
     */
    public final void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(m_jbMiniFrame)) {
            new AufstellungsMiniPositionsFrame(m_clLineupPanel, false, true);
        } else if (actionEvent.getSource().equals(m_jbMidiFrame)) {
            new AufstellungsMiniPositionsFrame(m_clLineupPanel, false, false);
        } else if (actionEvent.getSource().equals(m_jbFlipSide)) {
            HOVerwaltung.instance().getModel().getAufstellung().flipSide();
            HOMainFrame.instance().getAufstellungsPanel().update();
        } else {
            final AufstellungsMiniPositionsFrame frame = new AufstellungsMiniPositionsFrame(m_clLineupPanel,
                                                                                            true,
                                                                                            false);
            frame.setVisible(true);

            try {
                Thread.sleep(500);
            } catch (Exception e) {
            }

            frame.doPrint();
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void reInit() {
        refresh();
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void refresh() {
        final boolean gruppenfilter = m_clLineupPanel.getAufstellungsAssitentPanel()
                                                           .isGruppenFilter();
        final String gruppe = m_clLineupPanel.getAufstellungsAssitentPanel().getGruppe();
        final boolean gruppenegieren = m_clLineupPanel.getAufstellungsAssitentPanel()
                                                            .isNotGruppe();

        final boolean exludeLast = m_clLineupPanel.getAufstellungsAssitentPanel()
                                                        .isExcludeLastMatch();

        //Alle SpielerPositionen Informieren
        //erste 11
        final Vector<ISpieler> aufgestellteSpieler = new Vector<ISpieler>();

        final Vector<ISpieler> alleSpieler = HOVerwaltung.instance().getModel().getAllSpieler();
        final Vector<ISpieler> gefilterteSpieler = new Vector<ISpieler>();
        final Lineup aufstellung = HOVerwaltung.instance().getModel().getAufstellung();

        for (int i = 0; i < alleSpieler.size(); i++) {
            final Spieler spieler = (Spieler) alleSpieler.get(i);

            //ein erste 11
            if (aufstellung.isSpielerInAnfangsElf(spieler.getSpielerID())) {
                aufgestellteSpieler.add(spieler);
            }
        }

        //Den Gruppenfilter anwenden
        for (int i = 0; i < alleSpieler.size(); i++) {
            final Spieler spieler = (Spieler) alleSpieler.get(i);

            //Kein Filter
            if (!gruppenfilter
                || (gruppe.equals(spieler.getTeamInfoSmilie()) && !gruppenegieren)
                || (!gruppe.equals(spieler.getTeamInfoSmilie()) && gruppenegieren)) {
                boolean include = true;
                final AufstellungCBItem lastLineup = AufstellungsVergleichHistoryPanel
                                                     .getLastLineup();

                if (exludeLast
                    && (lastLineup != null)
                    && lastLineup.getAufstellung().isSpielerInAnfangsElf(spieler.getSpielerID())) {
                    include = false;
                    HOLogger.instance().log(getClass(),"Exclude: " + spieler.getName());
                }

                if (include) {
                    gefilterteSpieler.add(spieler);
                }
            }
        }

        //SpielerPositionsPanels aktualisieren
        // comment Thorsten Dietz 25.09.05 
        // I see no reason or effect for setting position
        //SpielerPosition position;
        //position = aufstellung.getPositionById(m_clTorwart.getPositionsID());
        m_clKeeper.refresh(gefilterteSpieler);

        //position = aufstellung.getPositionById(m_clLinkeAussenVerteidiger.getPositionsID());
        m_clLeftBack.refresh(gefilterteSpieler);

        //position = aufstellung.getPositionById(m_clLinkeInnenVerteidiger.getPositionsID());
        m_clLeftCentralDefender.refresh(gefilterteSpieler);

        m_clMiddleCentralDefender.refresh(gefilterteSpieler);

        //position = aufstellung.getPositionById(m_clRechteInnenVerteidiger.getPositionsID());
        m_clRightCentralDefender.refresh(gefilterteSpieler);

        //position = aufstellung.getPositionById(m_clRechteAussenVerteidiger.getPositionsID());
        m_clRightBack.refresh(gefilterteSpieler);

        //position = aufstellung.getPositionById(m_clLinkeFluegel.getPositionsID());
        m_clLeftWinger.refresh(gefilterteSpieler);

        //position = aufstellung.getPositionById(m_clLinkeMittelfeld.getPositionsID());
        m_clLeftInnerMidfielder.refresh(gefilterteSpieler);

        m_clCentralInnerMidfielder.refresh(gefilterteSpieler);
        
        //position = aufstellung.getPositionById(m_clRechteMittelfeld.getPositionsID());
        m_clRightInnerMidfielder.refresh(gefilterteSpieler);

        //position = aufstellung.getPositionById(m_clRechteFluegel.getPositionsID());
        m_clRightWinger.refresh(gefilterteSpieler);

        //position = aufstellung.getPositionById(m_clLinkerSturm.getPositionsID());
        m_clLeftForward.refresh(gefilterteSpieler);
        
        m_clCentralForward.refresh(gefilterteSpieler);

        //position = aufstellung.getPositionById(m_clRechterSturm.getPositionsID());
        m_clRightForward.refresh(gefilterteSpieler);

        //position = aufstellung.getPositionById(m_clReserveTorwart.getPositionsID());
        m_clSubstKeeper.refresh(gefilterteSpieler);

        //position = aufstellung.getPositionById(m_clReserveVerteidiger.getPositionsID());
        m_clSubstDefender.refresh(gefilterteSpieler);

        //position = aufstellung.getPositionById(m_clReserveMittelfeld.getPositionsID());
        m_clSubstMidfield.refresh(gefilterteSpieler);

        //position = aufstellung.getPositionById(m_clReserveFluegel.getPositionsID());
        m_clSubstWinger.refresh(gefilterteSpieler);

        //position = aufstellung.getPositionById(m_clReserveSturm.getPositionsID());
        m_clSubstForward.refresh(gefilterteSpieler);

        //position = aufstellung.getPositionById(m_clStandard.getPositionsID());
        m_clSetPieceTaker.refresh(aufgestellteSpieler);

        //position = aufstellung.getPositionById(m_clSpielfuehrer.getPositionsID());
        m_clCaptain.refresh(aufgestellteSpieler);

        //Check
        aufstellung.checkAufgestellteSpieler();
        
    }

	public void exportOldLineup(String name) {
		File dir = new File("Lineups/"+ HOVerwaltung.instance().getModel().getBasics().getManager());               
		if (!dir.exists()) {
			dir.mkdirs();
		}
			
		try {
			File f = new File(dir,name+".dat");
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			bw.write("<lineup>");
			bw.newLine();
			saveDetail(bw,m_clKeeper);
			saveDetail(bw,m_clRightBack);
			saveDetail(bw,m_clRightCentralDefender);
			saveDetail(bw,m_clMiddleCentralDefender);
			saveDetail(bw,m_clLeftCentralDefender);
			saveDetail(bw,m_clLeftBack);						
			saveDetail(bw,m_clRightWinger);
			saveDetail(bw,m_clRightInnerMidfielder);
			saveDetail(bw,m_clCentralInnerMidfielder);
			saveDetail(bw,m_clLeftInnerMidfielder);
			saveDetail(bw,m_clLeftWinger);						
			saveDetail(bw,m_clRightForward);
			saveDetail(bw,m_clCentralForward);
			saveDetail(bw,m_clLeftForward);
			saveDetail(bw,m_clSetPieceTaker);
			saveDetail(bw,m_clSubstKeeper);			
			saveDetail(bw,m_clCaptain);	
			saveDetail(bw,m_clSubstDefender);
			saveDetail(bw,m_clSubstMidfield);
			saveDetail(bw,m_clSubstWinger);
			saveDetail(bw,m_clSubstForward);
			bw.write("<tacticType>"+m_clLineupPanel.getAufstellungsDetailPanel().getTaktik()+"</tacticType>");
			bw.newLine();			
			bw.write("<matchType>"+m_clLineupPanel.getAufstellungsDetailPanel().getEinstellung()+"</matchType>");
			bw.newLine();
			bw.write("</lineup>");
			bw.newLine();									
			bw.flush();
			bw.close();
		} catch (IOException e) {
			HOLogger.instance().log(getClass(),e);
		}
	}
    
    private void saveDetail(BufferedWriter bw, PlayerPositionPanel positionPanel) throws IOException {
    	bw.write("<position>");
		bw.newLine();    	
		bw.write("<code>"+positionPanel.getPositionsID()+"</code>");
		bw.newLine();		
		bw.write("<player>"+positionPanel.getPlayerId()+"</player>");
		bw.newLine();		
		bw.write("<tactic>"+positionPanel.getTacticOrder()+"</tactic>");
		bw.newLine();		
		bw.write("</position>");
		bw.newLine();
	}


    /**
     * TODO Missing Method Documentation
     */
    public final void update() {
        m_clLineupPanel.update();
    }

    /**
     * Erstellt die Komponenten
     */
    private void initComponentes() {
        setLayout(new BorderLayout());

        centerPanel = new javax.swing.JLayeredPane();
        centerPanel.setOpaque(false);

        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.insets = new Insets(2, 2, 2, 2);

        centerPanel.setLayout(layout);

        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        m_clKeeper = new PlayerPositionPanel(this, ISpielerPosition.keeper);
        layout.setConstraints(m_clKeeper, constraints);
        centerPanel.add(m_clKeeper);
        swapPositionsManager.addSwapCapabilityTo(m_clKeeper);
//      assistantPanel.addToAssistant(m_clKeeper);

        
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        m_clRightBack = new PlayerPositionPanel(this, ISpielerPosition.rightBack);
        layout.setConstraints(m_clRightBack, constraints);
        centerPanel.add(m_clRightBack);
        swapPositionsManager.addSwapCapabilityTo(m_clRightBack);
        assistantPanel.addToAssistant(m_clRightBack);

        // Defense line
        
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        m_clRightCentralDefender = new PlayerPositionPanel(this, ISpielerPosition.rightCentralDefender);
        layout.setConstraints(m_clRightCentralDefender, constraints);
        centerPanel.add(m_clRightCentralDefender);
        swapPositionsManager.addSwapCapabilityTo(m_clRightCentralDefender);
        assistantPanel.addToAssistant(m_clRightCentralDefender);
        
        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        m_clMiddleCentralDefender = new PlayerPositionPanel(this, ISpielerPosition.middleCentralDefender);
        layout.setConstraints(m_clMiddleCentralDefender, constraints);
        centerPanel.add(m_clMiddleCentralDefender);
        swapPositionsManager.addSwapCapabilityTo(m_clMiddleCentralDefender);
        assistantPanel.addToAssistant(m_clMiddleCentralDefender);
        
        constraints.gridx = 3;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        m_clLeftCentralDefender = new PlayerPositionPanel(this, ISpielerPosition.leftCentralDefender);
        layout.setConstraints(m_clLeftCentralDefender, constraints);
        centerPanel.add(m_clLeftCentralDefender);
        swapPositionsManager.addSwapCapabilityTo(m_clLeftCentralDefender);
        assistantPanel.addToAssistant(m_clLeftCentralDefender);
        
        constraints.gridx = 4;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        m_clLeftBack = new PlayerPositionPanel(this, ISpielerPosition.leftBack);
        layout.setConstraints(m_clLeftBack, constraints);
        centerPanel.add(m_clLeftBack);
        swapPositionsManager.addSwapCapabilityTo(m_clLeftBack);
        assistantPanel.addToAssistant(m_clLeftBack);

        // Midfield Line

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        m_clRightWinger = new PlayerPositionPanel(this, ISpielerPosition.rightWinger);
        layout.setConstraints(m_clRightWinger, constraints);
        centerPanel.add(m_clRightWinger);
        swapPositionsManager.addSwapCapabilityTo(m_clRightWinger);
        assistantPanel.addToAssistant(m_clRightWinger);
        
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        m_clRightInnerMidfielder = new PlayerPositionPanel(this, ISpielerPosition.rightInnerMidfield);
        layout.setConstraints(m_clRightInnerMidfielder, constraints);
        centerPanel.add(m_clRightInnerMidfielder);
        swapPositionsManager.addSwapCapabilityTo(m_clRightInnerMidfielder);
        assistantPanel.addToAssistant(m_clRightInnerMidfielder);
        
        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        m_clCentralInnerMidfielder = new PlayerPositionPanel(this, ISpielerPosition.centralInnerMidfield);
        layout.setConstraints(m_clCentralInnerMidfielder, constraints);
        centerPanel.add(m_clCentralInnerMidfielder);
        swapPositionsManager.addSwapCapabilityTo(m_clCentralInnerMidfielder);
        assistantPanel.addToAssistant(m_clCentralInnerMidfielder);
        
        constraints.gridx = 3;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        m_clLeftInnerMidfielder = new PlayerPositionPanel(this, ISpielerPosition.leftInnerMidfield);
        layout.setConstraints(m_clLeftInnerMidfielder, constraints);
        centerPanel.add(m_clLeftInnerMidfielder);
        swapPositionsManager.addSwapCapabilityTo(m_clLeftInnerMidfielder);
        assistantPanel.addToAssistant(m_clLeftInnerMidfielder);
        
        constraints.gridx = 4;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        m_clLeftWinger = new PlayerPositionPanel(this, ISpielerPosition.leftWinger);
        layout.setConstraints(m_clLeftWinger, constraints);
        centerPanel.add(m_clLeftWinger);
        swapPositionsManager.addSwapCapabilityTo(m_clLeftWinger);
        assistantPanel.addToAssistant(m_clLeftWinger);

        // Forward line
        
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        m_clRightForward = new PlayerPositionPanel(this, ISpielerPosition.rightForward);
        layout.setConstraints(m_clRightForward, constraints);
        centerPanel.add(m_clRightForward);
        swapPositionsManager.addSwapCapabilityTo(m_clRightForward);
        assistantPanel.addToAssistant(m_clRightForward);

        constraints.gridx = 2;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        m_clCentralForward = new PlayerPositionPanel(this, ISpielerPosition.centralForward);
        layout.setConstraints(m_clCentralForward, constraints);
        centerPanel.add(m_clCentralForward);
        swapPositionsManager.addSwapCapabilityTo(m_clCentralForward);
        assistantPanel.addToAssistant(m_clCentralForward);

        constraints.gridx = 3;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        m_clLeftForward = new PlayerPositionPanel(this, ISpielerPosition.leftForward);
        layout.setConstraints(m_clLeftForward, constraints);
        centerPanel.add(m_clLeftForward);
        swapPositionsManager.addSwapCapabilityTo(m_clLeftForward);
        assistantPanel.addToAssistant(m_clLeftForward);
        
        // A spacer between forwards and reserves.
        
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 5;
        Box box = new Box(BoxLayout.Y_AXIS);
        box.add(Box.createRigidArea(new Dimension(10, 6)));
        layout.setConstraints(box, constraints);
        centerPanel.add(box);
        
        // The reserves
        
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        m_clSubstKeeper = new PlayerPositionPanel(this, ISpielerPosition.substKeeper);
        layout.setConstraints(m_clSubstKeeper, constraints);
        centerPanel.add(m_clSubstKeeper);
        swapPositionsManager.addSwapCapabilityTo(m_clSubstKeeper);
        
        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        m_clSubstDefender = new PlayerPositionPanel(this, ISpielerPosition.substDefender);
        layout.setConstraints(m_clSubstDefender, constraints);
        centerPanel.add(m_clSubstDefender);
        swapPositionsManager.addSwapCapabilityTo(m_clSubstDefender);

        constraints.gridx = 2;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        m_clSubstMidfield = new PlayerPositionPanel(this, ISpielerPosition.substInnerMidfield);
        layout.setConstraints(m_clSubstMidfield, constraints);
        centerPanel.add(m_clSubstMidfield);
        swapPositionsManager.addSwapCapabilityTo(m_clSubstMidfield);

        constraints.gridx = 3;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        m_clSubstForward = new PlayerPositionPanel(this, ISpielerPosition.substForward);
        layout.setConstraints(m_clSubstForward, constraints);
        centerPanel.add(m_clSubstForward);
        swapPositionsManager.addSwapCapabilityTo(m_clSubstForward);

        constraints.gridx = 4;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        m_clSubstWinger = new PlayerPositionPanel(this, ISpielerPosition.substWinger);
        layout.setConstraints(m_clSubstWinger, constraints);
        centerPanel.add(m_clSubstWinger);
        swapPositionsManager.addSwapCapabilityTo(m_clSubstWinger);

        // Captain and setpieces
       
        
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 1;
        m_clCaptain = new PlayerPositionPanel(this, ISpielerPosition.captain);
        layout.setConstraints(m_clCaptain, constraints);
        centerPanel.add(m_clCaptain);
        
        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.gridwidth = 1;
        m_clSetPieceTaker = new PlayerPositionPanel(this, ISpielerPosition.setPieces);
        layout.setConstraints(m_clSetPieceTaker, constraints);
        centerPanel.add(m_clSetPieceTaker);
        

        //Gruppenzuordnung des aufgestellten 
        
        constraints.gridx = 3;
        constraints.gridy = 6;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        centerPanel.add(new AufstellungsGruppenPanel(), constraints);

        //MiniLineup
        final JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setOpaque(false);

        final JPanel panel = new JPanel();
        panel.setOpaque(false);
        m_jbDrucken.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("tt_AufstellungsPosition_Drucken"));
        m_jbDrucken.addActionListener(this);
        m_jbDrucken.setPreferredSize(new Dimension(25, 25));
        panel.add(m_jbDrucken);
        m_jbMiniFrame.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("tt_AufstellungsPosition_MiniFrame"));
        m_jbMiniFrame.addActionListener(this);
        m_jbMiniFrame.setPreferredSize(new Dimension(25, 25));
        panel.add(m_jbMiniFrame);
        m_jbMidiFrame.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("tt_AufstellungsPosition_MidiFrame"));
        m_jbMidiFrame.addActionListener(this);
        m_jbMidiFrame.setPreferredSize(new Dimension(25, 25));
        panel.add(m_jbMidiFrame);
        m_jbFlipSide.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("tt_AufstellungsPosition_FlipSide"));
        m_jbFlipSide.addActionListener(this);
        m_jbFlipSide.setPreferredSize(new Dimension(25, 25));
        panel.add(m_jbFlipSide);
        m_jbDrucken.setPreferredSize(new Dimension(m_jbDrucken.getPreferredSize().width, 25));
        buttonPanel.add(panel, BorderLayout.NORTH);
        
        constraints.gridx = 4;
        constraints.gridy = 6;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        layout.setConstraints(panel, constraints);
        centerPanel.add(panel);
        
        add(centerPanel, BorderLayout.CENTER);

        //add(buttonPanel, BorderLayout.SOUTH);
    }
}
