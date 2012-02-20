// %4201738339:de.hattrickorganizer.gui.transferscout%
package ho.module.transfer.scout;

import gui.HOIconName;
import ho.core.db.DBManager;
import ho.core.gui.comp.table.TableSorter;
import ho.core.gui.print.ComponentPrintObject;
import ho.core.gui.print.PrintController;
import ho.core.gui.theme.ThemeManager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;

import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.tools.HOLogger;
/**
 * The TransferScout main Panel
 */
public class TransferScoutPanel extends ImagePanel implements MouseListener, KeyListener, ActionListener {

	private static final long serialVersionUID = 1L;
	
	//~ Instance fields ----------------------------------------------------------------------------
    private JSplitPane verticalSplitPane;
    private ScoutThread m_clScoutThread;
    private TransferEingabePanel m_jpTransferEingabePanel;
    private TransferTable m_jtTransferTable;
    private JPanel toolbar;
    private JButton jbPrint = new JButton(ThemeManager.getIcon(HOIconName.PRINTER));

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TransferScoutPanel object.
     */
    public TransferScoutPanel() {
        initComponents();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getDividerLocation() {
        return verticalSplitPane.getDividerLocation();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final TransferTable getTransferTable() {
        return m_jtTransferTable;
    }

    //------------------------------------------------------------------------
    public final void addScoutEintrag(ScoutEintrag scouteintrag) {
        //Wird nur durchgeführt, wenn der Eintrag schon vorhanden ist
        removeScoutEintrag(scouteintrag);
        m_jtTransferTable.getTransferTableModel().addScoutEintrag(scouteintrag);

        //Thread aktualisieren
        if (m_clScoutThread != null) {
            //m_clScoutThread.addEintrag ( scouteintrag );
            m_clScoutThread.setVector(m_jtTransferTable.getTransferTableModel().getScoutListe());

            //neuer Thread
        } else {
            ScoutThread.start(m_jtTransferTable.getTransferTableModel().getScoutListe());
        }

        m_jtTransferTable.refresh();
    }

    /**
     * Drucken der Transferscouttabelle
     */
    public final void drucken() {
        try {
            //Damit nur bestimmte Spalten gedruckt werden ist eine spezielle Tabelle notwendig.
            //Das Scrollpane benötigt man, damit die Spaltenbeschriftung auch angezeigt wird.
            final TransferTable table = new TransferTable();
            final JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(table.getPreferredSize().width + 10,
                                                      table.getPreferredSize().height + 70));
            scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
            scrollPane.getViewport().setBackground(Color.white);

            final PrintController printController = PrintController.getInstance();
            final java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            final String title = HOVerwaltung.instance().getLanguageString("TransferScout")
                                 + " - "
                                 + HOVerwaltung.instance().getModel().getBasics().getTeamName()
                                 + " - "
                                 + java.text.DateFormat.getDateTimeInstance().format(calendar.getTime());
            printController.add(new ComponentPrintObject(printController.getPf(), title,
            		scrollPane, ComponentPrintObject.NICHTSICHTBAR));
            printController.print();
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param keyEvent TODO Missing Method Parameter Documentation
     */
    public final void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getSource().equals(m_jtTransferTable)) {
            newSelectionInform();
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param keyEvent TODO Missing Method Parameter Documentation
     */
    public final void keyReleased(KeyEvent keyEvent) {
        if (keyEvent.getSource().equals(m_jtTransferTable)) {
            newSelectionInform();
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param keyEvent TODO Missing Method Parameter Documentation
     */
    public void keyTyped(KeyEvent keyEvent) {
    }

    //------------Listener---------------------------------------------------
    public void mouseClicked(MouseEvent mouseEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param mouseEvent TODO Missing Method Parameter Documentation
     */
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param mouseEvent TODO Missing Method Parameter Documentation
     */
    public void mouseExited(MouseEvent mouseEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param mouseEvent TODO Missing Method Parameter Documentation
     */
    public void mousePressed(MouseEvent mouseEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param mouseEvent TODO Missing Method Parameter Documentation
     */
    public final void mouseReleased(MouseEvent mouseEvent) {
        if (mouseEvent.getSource().equals(m_jtTransferTable)) {
            newSelectionInform();
        }
    }

    //------------------------------------------------------
    public final void newSelectionInform() {
        final int row = m_jtTransferTable.getSelectedRow();
        if (row > -1) {
            final TableSorter model = (TableSorter)m_jtTransferTable.getModel();
            m_jpTransferEingabePanel.setScoutEintrag(model.getScoutEintrag(row).duplicate());
        } else {
            //m_jpTransferEingabePanel.setSpieler( null );
        }
    }

    /**
     * Removes one entry from transfer table
     *
     * @param scouteintrag the scout entry which should be removed
     */
    public final void removeScoutEintrag(ScoutEintrag scouteintrag) {
        m_jtTransferTable.getTransferTableModel().removeScoutEintrag(scouteintrag);

        //Thread aktualisieren
        //m_clScoutThread.removeEintrag ( scouteintrag );
        m_clScoutThread.setVector(m_jtTransferTable.getTransferTableModel().getScoutListe());
        m_jtTransferTable.refresh();
    }
    
    /**
     * Removes all entries from transfer table and scout thread
     */
    public final void removeScoutEntries() {
        m_jtTransferTable.getTransferTableModel().removeScoutEntries();

        //Thread aktualisieren
        //m_clScoutThread.removeEintrag ( scouteintrag );
        m_clScoutThread.setVector(null);
        m_jtTransferTable.refresh();
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void saveScoutListe() {
        DBManager.instance().saveScoutList(m_jtTransferTable.getTransferTableModel().getScoutListe());
    }

    /**
     * TODO Missing Method Documentation
     */
    private void initComponents() {
        setLayout(new BorderLayout());

        add(getToolBar(),BorderLayout.NORTH);
        verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false, initTransferTable(),
                                           initTransferEingabePanel());

        add(verticalSplitPane, BorderLayout.CENTER);

        verticalSplitPane.setDividerLocation(gui.UserParameter.instance().transferScoutPanel_horizontalSplitPane);

        //Thread mit Wecker starten
        m_clScoutThread = ScoutThread.start(DBManager.instance().getScoutList());
    }

    
    private JPanel getToolBar(){
    	if(toolbar == null){
    		toolbar = new ImagePanel(new FlowLayout(FlowLayout.LEADING));
    		jbPrint.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_Transferscout_drucken"));
            jbPrint.addActionListener(this);
            toolbar.add(jbPrint);
    	}
    	return toolbar;
    }
    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private Component initTransferEingabePanel() {
        m_jpTransferEingabePanel = new TransferEingabePanel(this);
        return new JScrollPane(m_jpTransferEingabePanel);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private Component initTransferTable() {
        m_jtTransferTable = new TransferTable();
        m_jtTransferTable.addMouseListener(this);
        m_jtTransferTable.addKeyListener(this);

        final JScrollPane scrollpane = new JScrollPane(m_jtTransferTable);
        scrollpane.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
        return scrollpane;
    }

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(jbPrint)) {
            drucken();
        }
		
	}
}
