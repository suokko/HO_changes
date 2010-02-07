// %2976388207:de.hattrickorganizer.gui.lineup%
package de.hattrickorganizer.gui.lineup;

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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import plugins.ISpieler;
import plugins.ISpielerPosition;
import de.hattrickorganizer.gui.HOMainFrame;
import de.hattrickorganizer.gui.RefreshManager;
import de.hattrickorganizer.gui.model.AufstellungCBItem;
import de.hattrickorganizer.model.Aufstellung;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.Spieler;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.Helper;


/**
 * Enthält die einzelnen Positionen
 */
public class AufstellungsPositionsPanel extends de.hattrickorganizer.gui.templates.RasenPanel
    implements de.hattrickorganizer.gui.Refreshable, de.hattrickorganizer.gui.Updateable,
               ActionListener
{
	
	private static final long serialVersionUID = -9098199182886069003L;
	
    //~ Instance fields ----------------------------------------------------------------------------
	private AufstellungsPanel m_clAufstellungsPanel;
    private JButton m_jbDrucken = new JButton(new ImageIcon(Helper.loadImage("gui/bilder/Drucken.png")));
    private JButton m_jbFlipSide = new JButton(new ImageIcon(Helper.loadImage("gui/bilder/Reload.png")));
	private JButton m_jbMidiFrame = new JButton(new ImageIcon(Helper.loadImage("gui/bilder/MidiAufstellung.png")));
	private JButton m_jbMiniFrame = new JButton(new ImageIcon(Helper.loadImage("gui/bilder/MiniAufstellung.png")));
    private SpielerPositionsPanel m_clLinkeAussenVerteidiger;
    private SpielerPositionsPanel m_clLinkeFluegel;
    private SpielerPositionsPanel m_clLinkeInnenVerteidiger;
    private SpielerPositionsPanel m_clLinkeMittelfeld;
    private SpielerPositionsPanel m_clLinkerSturm;
    private SpielerPositionsPanel m_clRechteAussenVerteidiger;
    private SpielerPositionsPanel m_clRechteFluegel;
    private SpielerPositionsPanel m_clRechteInnenVerteidiger;
    private SpielerPositionsPanel m_clRechteMittelfeld;
    private SpielerPositionsPanel m_clRechterSturm;
    private SpielerPositionsPanel m_clReserveFluegel;
    private SpielerPositionsPanel m_clReserveMittelfeld;
    private SpielerPositionsPanel m_clReserveSturm;
    private SpielerPositionsPanel m_clReserveTorwart;
    private SpielerPositionsPanel m_clReserveVerteidiger;
    private SpielerPositionsPanel m_clSpielfuehrer;
    private SpielerPositionsPanel m_clStandard;
    private SpielerPositionsPanel m_clTorwart;
    
    private final SwapPositionsManager swapPositionsManager = new SwapPositionsManager(
			this);

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new AufstellungsPositionsPanel object.
     *
     * @param panel TODO Missing Constructuor Parameter Documentation
     */
    public AufstellungsPositionsPanel(AufstellungsPanel panel) {
        m_clAufstellungsPanel = panel;

        initComponentes();

        RefreshManager.instance().registerRefreshable(this);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param actionEvent TODO Missing Method Parameter Documentation
     */
    public final void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(m_jbMiniFrame)) {
            new AufstellungsMiniPositionsFrame(m_clAufstellungsPanel, false, true);
        } else if (actionEvent.getSource().equals(m_jbMidiFrame)) {
            new AufstellungsMiniPositionsFrame(m_clAufstellungsPanel, false, false);
        } else if (actionEvent.getSource().equals(m_jbFlipSide)) {
            HOVerwaltung.instance().getModel().getAufstellung().flipSide();
            HOMainFrame.instance().getAufstellungsPanel().update();
        } else {
            final AufstellungsMiniPositionsFrame frame = new AufstellungsMiniPositionsFrame(m_clAufstellungsPanel,
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
        final boolean gruppenfilter = m_clAufstellungsPanel.getAufstellungsAssitentPanel()
                                                           .isGruppenFilter();
        final String gruppe = m_clAufstellungsPanel.getAufstellungsAssitentPanel().getGruppe();
        final boolean gruppenegieren = m_clAufstellungsPanel.getAufstellungsAssitentPanel()
                                                            .isNotGruppe();

        final boolean exludeLast = m_clAufstellungsPanel.getAufstellungsAssitentPanel()
                                                        .isExcludeLastMatch();

        //Alle SpielerPositionen Informieren
        //erste 11
        final Vector<ISpieler> aufgestellteSpieler = new Vector<ISpieler>();

        final Vector<ISpieler> alleSpieler = HOVerwaltung.instance().getModel().getAllSpieler();
        final Vector<ISpieler> gefilterteSpieler = new Vector<ISpieler>();
        final Aufstellung aufstellung = HOVerwaltung.instance().getModel().getAufstellung();

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
        m_clTorwart.refresh(gefilterteSpieler);

        //position = aufstellung.getPositionById(m_clLinkeAussenVerteidiger.getPositionsID());
        m_clLinkeAussenVerteidiger.refresh(gefilterteSpieler);

        //position = aufstellung.getPositionById(m_clLinkeInnenVerteidiger.getPositionsID());
        m_clLinkeInnenVerteidiger.refresh(gefilterteSpieler);

        //position = aufstellung.getPositionById(m_clRechteInnenVerteidiger.getPositionsID());
        m_clRechteInnenVerteidiger.refresh(gefilterteSpieler);

        //position = aufstellung.getPositionById(m_clRechteAussenVerteidiger.getPositionsID());
        m_clRechteAussenVerteidiger.refresh(gefilterteSpieler);

        //position = aufstellung.getPositionById(m_clLinkeFluegel.getPositionsID());
        m_clLinkeFluegel.refresh(gefilterteSpieler);

        //position = aufstellung.getPositionById(m_clLinkeMittelfeld.getPositionsID());
        m_clLinkeMittelfeld.refresh(gefilterteSpieler);

        //position = aufstellung.getPositionById(m_clRechteMittelfeld.getPositionsID());
        m_clRechteMittelfeld.refresh(gefilterteSpieler);

        //position = aufstellung.getPositionById(m_clRechteFluegel.getPositionsID());
        m_clRechteFluegel.refresh(gefilterteSpieler);

        //position = aufstellung.getPositionById(m_clLinkerSturm.getPositionsID());
        m_clLinkerSturm.refresh(gefilterteSpieler);

        //position = aufstellung.getPositionById(m_clRechterSturm.getPositionsID());
        m_clRechterSturm.refresh(gefilterteSpieler);

        //position = aufstellung.getPositionById(m_clReserveTorwart.getPositionsID());
        m_clReserveTorwart.refresh(gefilterteSpieler);

        //position = aufstellung.getPositionById(m_clReserveVerteidiger.getPositionsID());
        m_clReserveVerteidiger.refresh(gefilterteSpieler);

        //position = aufstellung.getPositionById(m_clReserveMittelfeld.getPositionsID());
        m_clReserveMittelfeld.refresh(gefilterteSpieler);

        //position = aufstellung.getPositionById(m_clReserveFluegel.getPositionsID());
        m_clReserveFluegel.refresh(gefilterteSpieler);

        //position = aufstellung.getPositionById(m_clReserveSturm.getPositionsID());
        m_clReserveSturm.refresh(gefilterteSpieler);

        //position = aufstellung.getPositionById(m_clStandard.getPositionsID());
        m_clStandard.refresh(aufgestellteSpieler);

        //position = aufstellung.getPositionById(m_clSpielfuehrer.getPositionsID());
        m_clSpielfuehrer.refresh(aufgestellteSpieler);

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
			saveDetail(bw,m_clTorwart);
			saveDetail(bw,m_clRechteAussenVerteidiger);
			saveDetail(bw,m_clRechteInnenVerteidiger);
			saveDetail(bw,m_clLinkeInnenVerteidiger);
			saveDetail(bw,m_clLinkeAussenVerteidiger);						
			saveDetail(bw,m_clRechteFluegel);
			saveDetail(bw,m_clRechteMittelfeld);
			saveDetail(bw,m_clLinkeMittelfeld);
			saveDetail(bw,m_clLinkeFluegel);						
			saveDetail(bw,m_clRechterSturm);
			saveDetail(bw,m_clLinkerSturm);
			saveDetail(bw,m_clStandard);
			saveDetail(bw,m_clReserveTorwart);			
			saveDetail(bw,m_clSpielfuehrer);	
			saveDetail(bw,m_clReserveVerteidiger);
			saveDetail(bw,m_clReserveMittelfeld);
			saveDetail(bw,m_clReserveFluegel);
			saveDetail(bw,m_clReserveSturm);
			bw.write("<tacticType>"+m_clAufstellungsPanel.getAufstellungsDetailPanel().getTaktik()+"</tacticType>");
			bw.newLine();			
			bw.write("<matchType>"+m_clAufstellungsPanel.getAufstellungsDetailPanel().getEinstellung()+"</matchType>");
			bw.newLine();
			bw.write("</lineup>");
			bw.newLine();									
			bw.flush();
			bw.close();
		} catch (IOException e) {
			HOLogger.instance().log(getClass(),e);
		}
	}
    
    private void saveDetail(BufferedWriter bw, SpielerPositionsPanel positionPanel) throws IOException {
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
        m_clAufstellungsPanel.update();
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

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 4;
        m_clTorwart = new SpielerPositionsPanel(this, ISpielerPosition.keeper);
        layout.setConstraints(m_clTorwart, constraints);
        centerPanel.add(m_clTorwart);
        swapPositionsManager.addSwapCapabilityTo(m_clTorwart);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        m_clRechteAussenVerteidiger = new SpielerPositionsPanel(this, ISpielerPosition.rightBack);
        layout.setConstraints(m_clRechteAussenVerteidiger, constraints);
        centerPanel.add(m_clRechteAussenVerteidiger);
        swapPositionsManager.addSwapCapabilityTo(m_clRechteAussenVerteidiger);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        m_clRechteInnenVerteidiger = new SpielerPositionsPanel(this, ISpielerPosition.insideBack1);
        layout.setConstraints(m_clRechteInnenVerteidiger, constraints);
        centerPanel.add(m_clRechteInnenVerteidiger);
        swapPositionsManager.addSwapCapabilityTo(m_clRechteInnenVerteidiger);

        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        m_clLinkeInnenVerteidiger = new SpielerPositionsPanel(this, ISpielerPosition.insideBack2);
        layout.setConstraints(m_clLinkeInnenVerteidiger, constraints);
        centerPanel.add(m_clLinkeInnenVerteidiger);
        swapPositionsManager.addSwapCapabilityTo(m_clLinkeInnenVerteidiger);

        constraints.gridx = 3;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        m_clLinkeAussenVerteidiger = new SpielerPositionsPanel(this, ISpielerPosition.leftBack);
        layout.setConstraints(m_clLinkeAussenVerteidiger, constraints);
        centerPanel.add(m_clLinkeAussenVerteidiger);
        swapPositionsManager.addSwapCapabilityTo(m_clLinkeAussenVerteidiger);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        m_clRechteFluegel = new SpielerPositionsPanel(this, ISpielerPosition.rightWinger);
        layout.setConstraints(m_clRechteFluegel, constraints);
        centerPanel.add(m_clRechteFluegel);
        swapPositionsManager.addSwapCapabilityTo(m_clRechteFluegel);

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        m_clRechteMittelfeld = new SpielerPositionsPanel(this, ISpielerPosition.insideMid1);
        layout.setConstraints(m_clRechteMittelfeld, constraints);
        centerPanel.add(m_clRechteMittelfeld);
        swapPositionsManager.addSwapCapabilityTo(m_clRechteMittelfeld);

        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        m_clLinkeMittelfeld = new SpielerPositionsPanel(this, ISpielerPosition.insideMid2);
        layout.setConstraints(m_clLinkeMittelfeld, constraints);
        centerPanel.add(m_clLinkeMittelfeld);
        swapPositionsManager.addSwapCapabilityTo(m_clLinkeMittelfeld);

        constraints.gridx = 3;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        m_clLinkeFluegel = new SpielerPositionsPanel(this, ISpielerPosition.leftWinger);
        layout.setConstraints(m_clLinkeFluegel, constraints);
        centerPanel.add(m_clLinkeFluegel);
        swapPositionsManager.addSwapCapabilityTo(m_clLinkeFluegel);

        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        m_clLinkerSturm = new SpielerPositionsPanel(this, ISpielerPosition.forward1);
        layout.setConstraints(m_clLinkerSturm, constraints);
        centerPanel.add(m_clLinkerSturm);
        swapPositionsManager.addSwapCapabilityTo(m_clLinkerSturm);

        constraints.gridx = 2;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        m_clRechterSturm = new SpielerPositionsPanel(this, ISpielerPosition.forward2);
        layout.setConstraints(m_clRechterSturm, constraints);
        centerPanel.add(m_clRechterSturm);
        swapPositionsManager.addSwapCapabilityTo(m_clRechterSturm);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        m_clStandard = new SpielerPositionsPanel(this, SpielerPositionsPanel.STANDARD);
        layout.setConstraints(m_clStandard, constraints);
        centerPanel.add(m_clStandard);

        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        m_clReserveTorwart = new SpielerPositionsPanel(this, ISpielerPosition.substKeeper);
        layout.setConstraints(m_clReserveTorwart, constraints);
        centerPanel.add(m_clReserveTorwart);

        constraints.gridx = 3;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        m_clSpielfuehrer = new SpielerPositionsPanel(this, SpielerPositionsPanel.SPIELFUEHRER);
        layout.setConstraints(m_clSpielfuehrer, constraints);
        centerPanel.add(m_clSpielfuehrer);

        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        m_clReserveVerteidiger = new SpielerPositionsPanel(this, ISpielerPosition.substBack);
        layout.setConstraints(m_clReserveVerteidiger, constraints);
        centerPanel.add(m_clReserveVerteidiger);

        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        m_clReserveMittelfeld = new SpielerPositionsPanel(this, ISpielerPosition.substInsideMid);
        layout.setConstraints(m_clReserveMittelfeld, constraints);
        centerPanel.add(m_clReserveMittelfeld);

        constraints.gridx = 2;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        m_clReserveSturm = new SpielerPositionsPanel(this, ISpielerPosition.substForward);
        layout.setConstraints(m_clReserveSturm, constraints);
        centerPanel.add(m_clReserveSturm);

        constraints.gridx = 3;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        m_clReserveFluegel = new SpielerPositionsPanel(this, ISpielerPosition.substWinger);
        layout.setConstraints(m_clReserveFluegel, constraints);
        centerPanel.add(m_clReserveFluegel);

        add(centerPanel, BorderLayout.CENTER);

        //Gruppenzuordnung des aufgestellten 
        add(new AufstellungsGruppenPanel(), BorderLayout.EAST);

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
        buttonPanel.add(panel, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
