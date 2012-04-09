// %4061149036:de.hattrickorganizer.gui.playeranalysis%
package ho.module.playeranalysis;

import ho.core.gui.RefreshManager;
import ho.core.gui.Refreshable;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.gui.model.SpielerCBItem;
import ho.core.gui.model.SpielerCBItemRenderer;
import ho.core.gui.theme.HOColorName;
import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.HOVerwaltung;
import ho.core.model.player.Spieler;
import ho.core.util.HOLogger;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;


/**
 * Bietet Übersicht über alle Spieler
 */
public class SpielerAnalysePanel extends ImagePanel implements Refreshable, ItemListener,
                                                               ActionListener
{
	private static final long serialVersionUID = 7705544952029589545L;
	
    //~ Instance fields ----------------------------------------------------------------------------

    private final JButton m_jbDrucken = new JButton(ThemeManager.getIcon(HOIconName.PRINTER));
    private JComboBox m_jcbSpieler;
    private JSplitPane horizontalSplitPane;
    private SpielerMatchesTable m_jtSpielerMatchesTable;
    private SpielerPositionTable m_jtSpielerPositionTable;
    private int columnModelInstance;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new SpielerAnalysePanel object.
     */
    public SpielerAnalysePanel(int instance) {
        RefreshManager.instance().registerRefreshable(this);
        columnModelInstance = instance;
        initComponents();
    }

    //~ Methods ------------------------------------------------------------------------------------

    //----------------------------------------------------    

    /**
     * Vielleicht mal, wenn das Match zu dem Tabelleneintrag angezeigt werden soll ... private void
     * chooseSelectionInform() { int row  = m_jtSpieleTable.getSelectedRow(); if ( row > -1 ) {
     * //Selektiertes Spiel des Models holen und alle 3 Panel informieren  try {
     * model.machtes.MatchKurzInfo info = ( (gui.model.SpieleTableModel)m_jtSpieleTable.getSorter
     * ().getModel () ).getMatch ( (int)( (ColorLabelEntry)m_jtSpieleTable.getValueAt ( row, 5 )
     * ).getZahl () ); m_jpStaerkenvergleichsPanel.refresh( info ); if ( info.getMatchStatus () ==
     * model.machtes.MatchKurzInfo.FINISHED ) { m_jpAufstellungHeimPanel.refresh( info.getMatchID
     * (), info.getHeimID () ); m_jpAufstellungGastPanel.refresh( info.getMatchID (),
     * info.getGastID () ); } else { m_jpAufstellungHeimPanel.clearAll ();
     * m_jpAufstellungGastPanel.clearAll (); }} catch ( Exception e ) {
     * m_jpStaerkenvergleichsPanel.clear (); m_jpAufstellungHeimPanel.clearAll ();
     * m_jpAufstellungGastPanel.clearAll (); HOLogger.instance().log(getClass(), "SpielePanel.newSelectionInform:
     * Keine Match zum Eintrag in der Tabelle gefunden! "+e ); }} else { //Alle Panels
     * zurücksetzen m_jpStaerkenvergleichsPanel.clear (); m_jpAufstellungHeimPanel.clearAll ();
     * m_jpAufstellungGastPanel.clearAll (); }}
     *
     * @param spielerid TODO Missing Constructuor Parameter Documentation
     */
    public final void setAktuelleSpieler(int spielerid) {
        final ComboBoxModel model = m_jcbSpieler.getModel();

        for (int i = 0; i < model.getSize(); ++i) {
            if (model.getElementAt(i) instanceof ho.core.gui.model.SpielerCBItem) {
                if (((ho.core.gui.model.SpielerCBItem) model.getElementAt(i)).getSpieler()
                     .getSpielerID() == spielerid) {
                    //Spieler gefunden -> Auswählen und fertig
                    m_jcbSpieler.setSelectedIndex(i);
                    return;
                }
            }
        }
    }

    /**
     * Gibt die aktuellen DividerLocations zurück, damit sie gespeichert werden können
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getDividerLocations() {
        return horizontalSplitPane.getDividerLocation();
    }

    public void saveColumnOrder(){
    	m_jtSpielerMatchesTable.saveColumnOrder();
    }
    
    /**
     * TODO Missing Method Documentation
     *
     * @param actionEvent TODO Missing Method Parameter Documentation
     */
    public final void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        drucken();
    }

  
    public final void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            //Änderung der Tabelle -> Anderer Filter!
            showSelectedPlayer();
        }
    }

    /**
     * ReInit
     */
    public final void reInit() {
        fillSpielerCB();

        showSelectedPlayer();
    }

    //----------------------Refresh--

    /**
     * Refresh
     */
    public void refresh() {
        //nix
    }

    /**
     * Drucken der SpielerAnalyse
     */
    private void drucken() {
        try {
            final JPanel panel = new JPanel(new BorderLayout());
            panel.setBackground(Color.WHITE);

            //Damit nur bestimmte Spalten gedruckt werden ist eine spezielle Tabelle notwendig.
            //Das Scrollpane benötigt man, damit die Spaltenbeschriftung auch angezeigt wird.
            final SpielerMatchesTable table = new SpielerMatchesTable(((SpielerCBItem) m_jcbSpieler
                                                                       .getSelectedItem()).getSpieler()
                                                                       .getSpielerID(),columnModelInstance);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(table.getPreferredSize().width + 10,
                                                      table.getPreferredSize().height + 70));
            scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
            scrollPane.getViewport().setBackground(Color.WHITE);

            panel.add(scrollPane, BorderLayout.NORTH);

            final SpielerPositionTable table2 = new SpielerPositionTable(((SpielerCBItem) m_jcbSpieler
                                                                          .getSelectedItem()).getSpieler()
                                                                          .getSpielerID());
            scrollPane = new JScrollPane(table2);
            scrollPane.setPreferredSize(new Dimension(table2.getPreferredSize().width + 10,
                                                      table2.getPreferredSize().height + 70));
            scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
            scrollPane.getViewport().setBackground(Color.WHITE);

            panel.add(scrollPane, BorderLayout.SOUTH);

            final ho.core.gui.print.PrintController printController = ho.core.gui.print.PrintController
                                                                                   .getInstance();

            final java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());

            final String titel = HOVerwaltung.instance().getLanguageString("SpielerAnalyse")
                                 + " - "
                                 + HOVerwaltung.instance().getModel().getBasics().getTeamName()
                                 + " - "
                                 + java.text.DateFormat.getDateTimeInstance().format(calendar
                                                                                     .getTime());
            printController.add(new ho.core.gui.print.ComponentPrintObject(printController
                                                                                        .getPf(),
                                                                                        titel,
                                                                                        panel,
                                                                                        ho.core.gui.print.ComponentPrintObject.NICHTSICHTBAR));

            printController.print();
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    private void fillSpielerCB() {
        final Vector<Spieler> spieler = HOVerwaltung.instance().getModel().getAllSpieler();
        final SpielerCBItem[] spielerCBItems = new SpielerCBItem[spieler.size()];

        for (int i = 0; i < spieler.size(); i++) {
            spielerCBItems[i] = new SpielerCBItem((spieler.get(i)).getName(),0f,spieler.get(i));
        }

        java.util.Arrays.sort(spielerCBItems);

        //Alte Spieler
        final Vector<Spieler> allSpieler = HOVerwaltung.instance().getModel().getAllOldSpieler();
        final SpielerCBItem[] spielerAllCBItems = new SpielerCBItem[allSpieler
                                                                                                                                  .size()];

        for (int i = 0; i < allSpieler.size(); i++) {
            spielerAllCBItems[i] = new SpielerCBItem((allSpieler.get(i)).getName(), 0f,allSpieler.get(i));
        }

        java.util.Arrays.sort(spielerAllCBItems);

        //Zusammenfügen
        final SpielerCBItem[] cbItems = new SpielerCBItem[spielerCBItems.length
                                                                       + spielerAllCBItems.length
                                                                       + 1];
        int i = 0;

        for (; i < spielerCBItems.length; i++) {
            cbItems[i] = spielerCBItems[i];
        }

        //Fur die Leerzeile;
        i++;

        for (int j = 0; j < spielerAllCBItems.length; j++) {
            cbItems[i + j] = spielerAllCBItems[j];
        }

        final javax.swing.DefaultComboBoxModel cbModel = new javax.swing.DefaultComboBoxModel(cbItems);

        m_jcbSpieler.setModel(cbModel);
        m_jcbSpieler.removeItemListener(this);

        //Kein Spieler selektiert
        m_jcbSpieler.setSelectedItem(null);
        m_jcbSpieler.addItemListener(this);
    }

    //----------init-----------------------------------------------
    private void initComponents() {
        setLayout(new BorderLayout());

        add(initSpielerCB(), BorderLayout.NORTH);

        int spielerid = -1;

        if (m_jcbSpieler.getSelectedItem() != null) {
            spielerid = ((SpielerCBItem) m_jcbSpieler.getSelectedItem()).getSpieler().getSpielerID();
        }

        horizontalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false,
                                             initSpielerMatchesTabelle(spielerid),
                                             initSpielerPositionTabelle(spielerid));

        //horizontalSplitPane.setDividerLocation( gui.UserParameter.instance ().spielerAnalysePanel_horizontalSplitPane );
        //1/4 Höhe des Frames
        horizontalSplitPane.setDividerLocation((ho.core.model.UserParameter.instance().hoMainFrame_height * 1) / 3);

        add(horizontalSplitPane, BorderLayout.CENTER);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private Component initSpielerCB() {
        final ImagePanel panel = new ImagePanel(null);

        m_jcbSpieler = new JComboBox();
        m_jcbSpieler.setRenderer(new SpielerCBItemRenderer());
        m_jcbSpieler.setMaximumRowCount(25);
        m_jcbSpieler.addItemListener(this);
        m_jcbSpieler.setMaximumSize(new Dimension(200, 25));
        m_jcbSpieler.setSize(200, 25);
        m_jcbSpieler.setLocation(10, 5);
        m_jcbSpieler.setBackground(ThemeManager.getColor(HOColorName.TABLEENTRY_BG));

        panel.add(m_jcbSpieler);

        m_jbDrucken.setSize(25, 25);
        m_jbDrucken.setLocation(220, 5);
        m_jbDrucken.addActionListener(this);

        panel.add(m_jbDrucken);

        panel.setPreferredSize(new Dimension(220, 35));

        fillSpielerCB();

        return panel;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param spielerid TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private Component initSpielerMatchesTabelle(int spielerid) {
        m_jtSpielerMatchesTable = new SpielerMatchesTable(spielerid,columnModelInstance);

        //m_jtSpielerMatchesTable.addMouseListener( this );
        //m_jtSpielerMatchesTable.addKeyListener( this );
        final JScrollPane scrollpane = new JScrollPane(m_jtSpielerMatchesTable);
        scrollpane.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);

        return scrollpane;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param spielerid TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private Component initSpielerPositionTabelle(int spielerid) {
        m_jtSpielerPositionTable = new SpielerPositionTable(spielerid);

        //m_jtSpielerPositionsTable.addMouseListener( this );
        //m_jtSpielerPositionsTable.addKeyListener( this );
        final JScrollPane scrollpane = new JScrollPane(m_jtSpielerPositionTable);
        scrollpane.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);

        return scrollpane;
    }

    /**
     * Aktualisiert die beiden Tabellen mit den Werten des ausgewählten Spielers
     */
    private void showSelectedPlayer() {
        if (m_jcbSpieler.getSelectedIndex() > -1) {
            //Tabelle updaten
            m_jtSpielerMatchesTable.refresh(((SpielerCBItem) m_jcbSpieler.getSelectedItem()).getSpieler()
                                             .getSpielerID());
            m_jtSpielerPositionTable.refresh(((SpielerCBItem) m_jcbSpieler.getSelectedItem()).getSpieler()
                                              .getSpielerID());
        } else {
            //Tabelle leeren
            m_jtSpielerMatchesTable.refresh(-1);
            m_jtSpielerPositionTable.refresh(-1);
        }
    }
}
