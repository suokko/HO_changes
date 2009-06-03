// %1884453469:de.hattrickorganizer.gui.templates%
package de.hattrickorganizer.gui.templates;

import javax.swing.SwingConstants;

/**
 * Ein Panel mit zwei Labels, um zwei Werte in einer Spalte anzuzeigen ( Wert, Verbesserung )
 */
public class DoppelLabelEntry extends TableEntry {
    //~ Instance fields ----------------------------------------------------------------------------

    private DoppelLabel m_clComponent = new DoppelLabel();
    private TableEntry m_clLinks;
    private TableEntry m_clRechts;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new DoppelLabelEntry object.
     */
    public DoppelLabelEntry() {
    }

    /**
     * Creates a new DoppelLabelEntry object.
     *
     * @param color TODO Missing Constructuor Parameter Documentation
     */
    public DoppelLabelEntry(java.awt.Color color) {
        super();
        m_clLinks = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD, color,
                                        SwingConstants.RIGHT);
        m_clRechts = new ColorLabelEntry("", ColorLabelEntry.FG_VERLETZT, color,
                                         SwingConstants.CENTER);
        createComponent();
    }

    /**
     * Creates a new DoppelLabelEntry object.
     *
     * @param links TODO Missing Constructuor Parameter Documentation
     * @param rechts TODO Missing Constructuor Parameter Documentation
     */
    public DoppelLabelEntry(TableEntry links, TableEntry rechts) {
        m_clLinks = links;
        m_clRechts = rechts;
        createComponent();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param isSelected TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final javax.swing.JComponent getComponent(boolean isSelected) {
        m_clComponent.removeAll();
        m_clComponent.setOpaque(false);

        final javax.swing.JComponent links = m_clLinks.getComponent(isSelected);
        final javax.swing.JComponent rechts = m_clRechts.getComponent(isSelected);

        m_clComponent.add(links);
        m_clComponent.add(rechts);

        return m_clComponent;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param links TODO Missing Method Parameter Documentation
     * @param rechts TODO Missing Method Parameter Documentation
     */
    public final void setLabels(TableEntry links, TableEntry rechts) {
        m_clLinks = links;
        m_clRechts = rechts;
        updateComponent();
    }

    /**
     * Nur benutzen, wenn es auch ein ColorLabelEntry ist!
     *
     * @return TODO Missing Return Method Documentation
     */
    public final ColorLabelEntry getLinks() {
        return (ColorLabelEntry) m_clLinks;
    }

    /**
     * Nur benutzen, wenn es auch ein ColorLabelEntry ist!
     *
     * @return TODO Missing Return Method Documentation
     */
    public final ColorLabelEntry getRechts() {
        return (ColorLabelEntry) m_clRechts;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final TableEntry getTableEntryLinks() {
        return m_clLinks;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final TableEntry getTableRechts() {
        return m_clRechts;
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void clear() {
        m_clLinks.clear();
        m_clRechts.clear();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param obj TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int compareTo(Object obj) {
        if (obj instanceof DoppelLabelEntry) {
            final DoppelLabelEntry entry = (DoppelLabelEntry) obj;
            return getTableEntryLinks().compareTo(entry.getTableEntryLinks());
        }

        return 0;
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void createComponent() {
        DoppelLabel panel = new DoppelLabel();

        m_clComponent = panel;
    }

    /**
     * TODO Missing Method Documentation
     */
    public void updateComponent() {
        m_clLinks.updateComponent();
        m_clRechts.updateComponent();
    }
}
