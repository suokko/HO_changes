// %80307481:de.hattrickorganizer.gui.lineup%
package de.hattrickorganizer.gui.lineup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import de.hattrickorganizer.gui.HOMainFrame;
import de.hattrickorganizer.gui.Refreshable;
import de.hattrickorganizer.gui.Updateable;
import de.hattrickorganizer.gui.model.SpielerCBItem;
import de.hattrickorganizer.gui.templates.RasenPanel;
import de.hattrickorganizer.model.Aufstellung;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.Spieler;
import de.hattrickorganizer.model.SpielerPosition;


/**
 * Darstellung der Aufstellung in einem kleinen Frame
 */
public class AufstellungsMiniPositionsFrame extends JFrame implements WindowListener, Refreshable,
                                                                      Updateable, ActionListener
{
    //~ Instance fields ----------------------------------------------------------------------------

    private AufstellungsPanel m_clAufstellungsPanel;
    private JButton m_jbMaxFrame = new JButton(new ImageIcon(de.hattrickorganizer.tools.Helper
                                                             .loadImage("gui/bilder/MaxAufstellung.png")));
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
    private boolean m_bMinimize;
    private boolean m_bPrint;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new AufstellungsMiniPositionsFrame object.
     *
     * @param aufstellungsPanel TODO Missing Constructuor Parameter Documentation
     * @param print TODO Missing Constructuor Parameter Documentation
     * @param minimized TODO Missing Constructuor Parameter Documentation
     */
    public AufstellungsMiniPositionsFrame(AufstellungsPanel aufstellungsPanel, boolean print,
                                          boolean minimized) {
        super("Mini"
              + de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Aufstellung"));

        m_bPrint = print;
        m_bMinimize = minimized;

        m_clAufstellungsPanel = aufstellungsPanel;

        de.hattrickorganizer.gui.RefreshManager.instance().registerRefreshable(this);

        initComponentes();

        this.setIconImage(de.hattrickorganizer.tools.Helper.loadImage("gui/bilder/Logo-16px.png"));
        this.addWindowListener(this);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Position des MiniScouts speichern
     *
     * @param sichtbar TODO Missing Constructuor Parameter Documentation
     */
    public final void setVisible(boolean sichtbar) {
        super.setVisible(sichtbar);

        if (!sichtbar && !m_bPrint) {
            gui.UserParameter.instance().miniscout_PositionX = this.getLocation().x;
            gui.UserParameter.instance().miniscout_PositionY = this.getLocation().y;
            de.hattrickorganizer.gui.RefreshManager.instance().unregisterRefreshable(this);
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param actionEvent TODO Missing Method Parameter Documentation
     */
    public final void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        setVisible(false);
        de.hattrickorganizer.gui.HOMainFrame.instance().setVisible(true);
        dispose();
    }

    /**
     * Drucken der Aufstellung
     */
    public final void doPrint() {
        try {
            final de.hattrickorganizer.gui.print.PrintController printController = de.hattrickorganizer.gui.print.PrintController
                                                                                   .getInstance();

            final java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());

            final String titel = de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Aufstellung")
                                 + " - "
                                 + de.hattrickorganizer.model.HOVerwaltung.instance().getModel()
                                                                          .getBasics().getTeamName()
                                 + " - "
                                 + java.text.DateFormat.getDateTimeInstance().format(calendar
                                                                                     .getTime());
            printController.add(new de.hattrickorganizer.gui.print.ComponentPrintObject(printController
                                                                                        .getPf(),
                                                                                        titel,
                                                                                        getContentPane(),
                                                                                        de.hattrickorganizer.gui.print.ComponentPrintObject.SICHTBAR));

            printController.print();
        } catch (Exception e) {
            //TODO
        }

        setVisible(false);
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

        //Alle SpielerPositionen Informieren
        //erste 11
        final java.util.Vector aufgestellteSpieler = new java.util.Vector();

        final java.util.Vector alleSpieler = HOVerwaltung.instance().getModel().getAllSpieler();
        final java.util.Vector gefilterteSpieler = new java.util.Vector();
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
                gefilterteSpieler.add(spieler);
            }
        }

        //SpielerPositionsPanels aktualisieren
        SpielerPosition position;

        position = aufstellung.getPositionById(m_clTorwart.getPositionsID());
        m_clTorwart.refresh(gefilterteSpieler);

        position = aufstellung.getPositionById(m_clLinkeAussenVerteidiger.getPositionsID());
        m_clLinkeAussenVerteidiger.refresh(gefilterteSpieler);

        position = aufstellung.getPositionById(m_clLinkeInnenVerteidiger.getPositionsID());
        m_clLinkeInnenVerteidiger.refresh(gefilterteSpieler);

        position = aufstellung.getPositionById(m_clRechteInnenVerteidiger.getPositionsID());
        m_clRechteInnenVerteidiger.refresh(gefilterteSpieler);

        position = aufstellung.getPositionById(m_clRechteAussenVerteidiger.getPositionsID());
        m_clRechteAussenVerteidiger.refresh(gefilterteSpieler);

        position = aufstellung.getPositionById(m_clLinkeFluegel.getPositionsID());
        m_clLinkeFluegel.refresh(gefilterteSpieler);

        position = aufstellung.getPositionById(m_clLinkeMittelfeld.getPositionsID());
        m_clLinkeMittelfeld.refresh(gefilterteSpieler);

        position = aufstellung.getPositionById(m_clRechteMittelfeld.getPositionsID());
        m_clRechteMittelfeld.refresh(gefilterteSpieler);

        position = aufstellung.getPositionById(m_clRechteFluegel.getPositionsID());
        m_clRechteFluegel.refresh(gefilterteSpieler);

        position = aufstellung.getPositionById(m_clLinkerSturm.getPositionsID());
        m_clLinkerSturm.refresh(gefilterteSpieler);

        position = aufstellung.getPositionById(m_clRechterSturm.getPositionsID());
        m_clRechterSturm.refresh(gefilterteSpieler);

        position = aufstellung.getPositionById(m_clReserveTorwart.getPositionsID());
        m_clReserveTorwart.refresh(gefilterteSpieler);

        position = aufstellung.getPositionById(m_clReserveVerteidiger.getPositionsID());
        m_clReserveVerteidiger.refresh(gefilterteSpieler);

        position = aufstellung.getPositionById(m_clReserveMittelfeld.getPositionsID());
        m_clReserveMittelfeld.refresh(gefilterteSpieler);

        position = aufstellung.getPositionById(m_clReserveFluegel.getPositionsID());
        m_clReserveFluegel.refresh(gefilterteSpieler);

        position = aufstellung.getPositionById(m_clReserveSturm.getPositionsID());
        m_clReserveSturm.refresh(gefilterteSpieler);

        position = aufstellung.getPositionById(m_clStandard.getPositionsID());
        m_clStandard.refresh(aufgestellteSpieler);

        position = aufstellung.getPositionById(m_clSpielfuehrer.getPositionsID());
        m_clSpielfuehrer.refresh(aufgestellteSpieler);

        //Check
        aufstellung.checkAufgestellteSpieler();
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void update() {
        m_clAufstellungsPanel.update();
        refresh();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param windowEvent TODO Missing Method Parameter Documentation
     */
    public void windowActivated(java.awt.event.WindowEvent windowEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param windowEvent TODO Missing Method Parameter Documentation
     */
    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param windowEvent TODO Missing Method Parameter Documentation
     */
    public final void windowClosing(java.awt.event.WindowEvent windowEvent) {
        setVisible(false);
        de.hattrickorganizer.gui.HOMainFrame.instance().setVisible(true);
        dispose();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param windowEvent TODO Missing Method Parameter Documentation
     */
    public void windowDeactivated(java.awt.event.WindowEvent windowEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param windowEvent TODO Missing Method Parameter Documentation
     */
    public void windowDeiconified(java.awt.event.WindowEvent windowEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param windowEvent TODO Missing Method Parameter Documentation
     */
    public void windowIconified(java.awt.event.WindowEvent windowEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param windowEvent TODO Missing Method Parameter Documentation
     */
    public void windowOpened(java.awt.event.WindowEvent windowEvent) {
    }

    /**
     * Erzeugt ein Label für den Spieler
     *
     * @param spielerID TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private SpielerCBItem createSpielerLabel(int spielerID) {
        final de.hattrickorganizer.model.Spieler spieler = de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                  .getModel()
                                                                                                  .getSpieler(spielerID);

        if (spieler != null) {
            return new SpielerCBItem(spieler.getName(), 0f, spieler);
        } else {
            return new SpielerCBItem("", 0f, null);
        }
    }

    /**
     * Erstellt die Komponenten
     */
    private void initComponentes() {
        setContentPane(new RasenPanel(new BorderLayout(), m_bPrint));

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
        m_clTorwart = new SpielerPositionsPanel(this, SpielerPosition.keeper, m_bPrint, m_bMinimize);
        layout.setConstraints(m_clTorwart, constraints);
        centerPanel.add(m_clTorwart);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        m_clRechteAussenVerteidiger = new SpielerPositionsPanel(this, SpielerPosition.rightBack,
                                                                m_bPrint, m_bMinimize);
        layout.setConstraints(m_clRechteAussenVerteidiger, constraints);
        centerPanel.add(m_clRechteAussenVerteidiger);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        m_clRechteInnenVerteidiger = new SpielerPositionsPanel(this, SpielerPosition.insideBack1,
                                                               m_bPrint, m_bMinimize);
        layout.setConstraints(m_clRechteInnenVerteidiger, constraints);
        centerPanel.add(m_clRechteInnenVerteidiger);

        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        m_clLinkeInnenVerteidiger = new SpielerPositionsPanel(this, SpielerPosition.insideBack2,
                                                              m_bPrint, m_bMinimize);
        layout.setConstraints(m_clLinkeInnenVerteidiger, constraints);
        centerPanel.add(m_clLinkeInnenVerteidiger);

        constraints.gridx = 3;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        m_clLinkeAussenVerteidiger = new SpielerPositionsPanel(this, SpielerPosition.leftBack,
                                                               m_bPrint, m_bMinimize);
        layout.setConstraints(m_clLinkeAussenVerteidiger, constraints);
        centerPanel.add(m_clLinkeAussenVerteidiger);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        m_clRechteFluegel = new SpielerPositionsPanel(this, SpielerPosition.rightWinger, m_bPrint,
                                                      m_bMinimize);
        layout.setConstraints(m_clRechteFluegel, constraints);
        centerPanel.add(m_clRechteFluegel);

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        m_clRechteMittelfeld = new SpielerPositionsPanel(this, SpielerPosition.insideMid1,
                                                         m_bPrint, m_bMinimize);
        layout.setConstraints(m_clRechteMittelfeld, constraints);
        centerPanel.add(m_clRechteMittelfeld);

        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        m_clLinkeMittelfeld = new SpielerPositionsPanel(this, SpielerPosition.insideMid2, m_bPrint,
                                                        m_bMinimize);
        layout.setConstraints(m_clLinkeMittelfeld, constraints);
        centerPanel.add(m_clLinkeMittelfeld);

        constraints.gridx = 3;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        m_clLinkeFluegel = new SpielerPositionsPanel(this, SpielerPosition.leftWinger, m_bPrint,
                                                     m_bMinimize);
        layout.setConstraints(m_clLinkeFluegel, constraints);
        centerPanel.add(m_clLinkeFluegel);

        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        m_clLinkerSturm = new SpielerPositionsPanel(this, SpielerPosition.forward1, m_bPrint,
                                                    m_bMinimize);
        layout.setConstraints(m_clLinkerSturm, constraints);
        centerPanel.add(m_clLinkerSturm);

        constraints.gridx = 2;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        m_clRechterSturm = new SpielerPositionsPanel(this, SpielerPosition.forward2, m_bPrint,
                                                     m_bMinimize);
        layout.setConstraints(m_clRechterSturm, constraints);
        centerPanel.add(m_clRechterSturm);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        m_clStandard = new SpielerPositionsPanel(this, SpielerPositionsPanel.STANDARD, m_bPrint,
                                                 m_bMinimize);
        layout.setConstraints(m_clStandard, constraints);
        centerPanel.add(m_clStandard);

        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        m_clReserveTorwart = new SpielerPositionsPanel(this, SpielerPosition.substKeeper, m_bPrint,
                                                       m_bMinimize);
        layout.setConstraints(m_clReserveTorwart, constraints);
        centerPanel.add(m_clReserveTorwart);

        constraints.gridx = 3;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        m_clSpielfuehrer = new SpielerPositionsPanel(this, SpielerPositionsPanel.SPIELFUEHRER,
                                                     m_bPrint, m_bMinimize);
        layout.setConstraints(m_clSpielfuehrer, constraints);
        centerPanel.add(m_clSpielfuehrer);

        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        m_clReserveVerteidiger = new SpielerPositionsPanel(this, SpielerPosition.substBack,
                                                           m_bPrint, m_bMinimize);
        layout.setConstraints(m_clReserveVerteidiger, constraints);
        centerPanel.add(m_clReserveVerteidiger);

        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        m_clReserveMittelfeld = new SpielerPositionsPanel(this, SpielerPosition.substInsideMid,
                                                          m_bPrint, m_bMinimize);
        layout.setConstraints(m_clReserveMittelfeld, constraints);
        centerPanel.add(m_clReserveMittelfeld);

        constraints.gridx = 2;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        m_clReserveSturm = new SpielerPositionsPanel(this, SpielerPosition.substForward, m_bPrint,
                                                     m_bMinimize);
        layout.setConstraints(m_clReserveSturm, constraints);
        centerPanel.add(m_clReserveSturm);

        constraints.gridx = 3;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        m_clReserveFluegel = new SpielerPositionsPanel(this, SpielerPosition.substWinger, m_bPrint,
                                                       m_bMinimize);
        layout.setConstraints(m_clReserveFluegel, constraints);
        centerPanel.add(m_clReserveFluegel);

        getContentPane().add(centerPanel, BorderLayout.CENTER);

        if (!m_bPrint) {
            //MiniLineup
            final JPanel panel = new JPanel(new BorderLayout());
            panel.setOpaque(false);
            m_jbMaxFrame.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("tt_AufstellungsMiniPosFrame_zurueck"));
            m_jbMaxFrame.setPreferredSize(new Dimension(25, 25));
            m_jbMaxFrame.addActionListener(this);
            panel.add(m_jbMaxFrame, BorderLayout.EAST);
            getContentPane().add(panel, BorderLayout.SOUTH);
        } else {
            //Aufstellungsratingspanel
            final AufstellungsDetailPanel detailpanel = new AufstellungsDetailPanel();
            detailpanel.refresh();
            detailpanel.setPreferredSize(new Dimension(HOMainFrame.instance().getAufstellungsPanel()
                                                                  .getAufstellungsDetailPanel()
                                                                  .getWidth(), 100));
            getContentPane().add(detailpanel, BorderLayout.WEST);
        }

        //Elfmeterschützen
        final JPanel sidePanel = new JPanel(new BorderLayout());
        sidePanel.setOpaque(false);

        final JLabel label = new JLabel(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Elfmeterschuetzen"));
        label.setFont(label.getFont().deriveFont(Font.BOLD));

        if (!m_bPrint) {
            label.setForeground(Color.WHITE);
        } else {
            label.setForeground(Color.BLACK);
        }

        sidePanel.add(label, BorderLayout.NORTH);

        final int[] elfmeterIDs = de.hattrickorganizer.model.HOVerwaltung.instance().getModel()
                                                                         .getAufstellung()
                                                                         .getBestElferKicker();

        final JList liste = new JList();
        liste.setOpaque(false);
        liste.setCellRenderer(new de.hattrickorganizer.gui.model.SpielerCBItemRenderer());
        liste.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        final DefaultListModel listmodel = new DefaultListModel();

        for (int i = 0; i < elfmeterIDs.length; i++) {
            listmodel.addElement(createSpielerLabel(elfmeterIDs[i]));
        }

        liste.setModel(listmodel);

        sidePanel.add(liste, BorderLayout.CENTER);

        getContentPane().add(sidePanel, BorderLayout.EAST);

        refresh();

        pack();
        setSize(getSize().width + 20, getSize().height + 30);

        if (!m_bPrint) {
            setLocation(gui.UserParameter.instance().miniscout_PositionX,
                        gui.UserParameter.instance().miniscout_PositionY);
            de.hattrickorganizer.gui.HOMainFrame.instance().setVisible(false);
        } else {
            try {
                final Toolkit kit = Toolkit.getDefaultToolkit();
                setLocation(kit.getScreenSize().width, kit.getScreenSize().height);
            } catch (Exception e) {
                //NIX
            }
        }

        setVisible(true);
    }
}
