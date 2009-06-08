// %3459649550:de.hattrickorganizer.gui.statistic%
package de.hattrickorganizer.gui.statistic;

import gui.UserParameter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import plugins.ISpielePanel;

import de.hattrickorganizer.gui.matches.SpielePanel;
import de.hattrickorganizer.gui.model.CBItem;
import de.hattrickorganizer.gui.templates.ImagePanel;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class ArenaStatistikPanel extends ImagePanel implements MouseListener, KeyListener,
                                                               ItemListener
{
	private static final long serialVersionUID = 2679088584924124183L;
	
    //~ Instance fields ----------------------------------------------------------------------------

	private ArenaStatistikTable m_jtArenaStatistikTable;
    private JComboBox m_jcbSpieleFilter;
    private CBItem[] SPIELEFILTER = {
                                        new CBItem(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("NurEigeneSpiele"),
                                                   ISpielePanel.NUR_EIGENE_SPIELE),
                                        new CBItem(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("NurEigenePflichtspiele"),
                                                   ISpielePanel.NUR_EIGENE_PFLICHTSPIELE),
                                        new CBItem(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("NurEigenePokalspiele"),
                                                   ISpielePanel.NUR_EIGENE_POKALSPIELE),
                                        new CBItem(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("NurEigeneLigaspiele"),
                                                   ISpielePanel.NUR_EIGENE_LIGASPIELE),
                                        new CBItem(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("NurEigeneFreundschaftsspiele"),
                                                   ISpielePanel.NUR_EIGENE_FREUNDSCHAFTSSPIELE)
                                    };
    private boolean m_bInitialisiert;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new ArenaStatistikPanel object.
     */
    public ArenaStatistikPanel() {
        setLayout(new BorderLayout());

        final ImagePanel panel = new ImagePanel(null);
        m_jcbSpieleFilter = new JComboBox(SPIELEFILTER);

        //Nur Pflichtspiele ist default
        m_jcbSpieleFilter.setSelectedIndex(1);

        //tools.Helper.markierenComboBox( m_jcbSpieleFilter, UserParameter.instance().spieleFilter );
        m_jcbSpieleFilter.addItemListener(this);
        m_jcbSpieleFilter.setFont(m_jcbSpieleFilter.getFont().deriveFont(Font.BOLD));
        m_jcbSpieleFilter.setSize(200, 25);
        m_jcbSpieleFilter.setLocation(10, 5);
        panel.setPreferredSize(new Dimension(240, 35));
        panel.add(m_jcbSpieleFilter);
        add(panel, BorderLayout.NORTH);

        m_jtArenaStatistikTable = new ArenaStatistikTable(UserParameter.instance().spieleFilter);
        m_jtArenaStatistikTable.addMouseListener(this);
        m_jtArenaStatistikTable.addKeyListener(this);

        final JScrollPane scrollpane = new JScrollPane(m_jtArenaStatistikTable);
        scrollpane.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);

        add(scrollpane, BorderLayout.CENTER);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param init TODO Missing Method Parameter Documentation
     */
    public final void setInitialisiert(boolean init) {
        m_bInitialisiert = init;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean isInitialisiert() {
        return m_bInitialisiert;
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void doInitialisieren() {
        initStatistik();
        m_bInitialisiert = true;
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void initStatistik() {
        m_jtArenaStatistikTable.refresh(((CBItem) m_jcbSpieleFilter.getSelectedItem()).getId());
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param e TODO Missing Method Parameter Documentation
     */
    public final void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            //Änderung der Tabelle -> Anderer Filter!
            reInit();
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param keyEvent TODO Missing Method Parameter Documentation
     */
    public final void keyPressed(java.awt.event.KeyEvent keyEvent) {
        if (keyEvent.getSource().equals(m_jtArenaStatistikTable)) {
            manageSelectionRow();
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param keyEvent TODO Missing Method Parameter Documentation
     */
    public final void keyReleased(java.awt.event.KeyEvent keyEvent) {
        if (keyEvent.getSource().equals(m_jtArenaStatistikTable)) {
            manageSelectionRow();
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param keyEvent TODO Missing Method Parameter Documentation
     */
    public void keyTyped(java.awt.event.KeyEvent keyEvent) {
    }

    //----------------------Listener    
    public final void mouseClicked(java.awt.event.MouseEvent mouseEvent) {
        if (mouseEvent.getSource().equals(m_jtArenaStatistikTable)) {
            manageSelectionRow();
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param mouseEvent TODO Missing Method Parameter Documentation
     */
    public void mouseEntered(java.awt.event.MouseEvent mouseEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param mouseEvent TODO Missing Method Parameter Documentation
     */
    public void mouseExited(java.awt.event.MouseEvent mouseEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param mouseEvent TODO Missing Method Parameter Documentation
     */
    public void mousePressed(java.awt.event.MouseEvent mouseEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param mouseEvent TODO Missing Method Parameter Documentation
     */
    public final void mouseReleased(java.awt.event.MouseEvent mouseEvent) {
        if (mouseEvent.getSource().equals(m_jtArenaStatistikTable)) {
            manageSelectionRow();
        }
    }

    /**
     * ReInit
     */
    public final void reInit() {
        if (m_jcbSpieleFilter.getSelectedIndex() > -1) {
            //Tabelle updaten
            m_jtArenaStatistikTable.refresh(((CBItem) m_jcbSpieleFilter.getSelectedItem()).getId());

            //UserParameter.instance().spieleFilter   =   ( (CBItem)m_jcbSpieleFilter.getSelectedItem () ).getId ();
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    private void manageSelectionRow() {
        final int row = m_jtArenaStatistikTable.getSelectedRow();

        if (row > -1) {
            m_jtArenaStatistikTable.setRowSelectionInterval(row, row);
        } else {
            m_jtArenaStatistikTable.clearSelection();
        }
    }
}
