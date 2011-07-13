// %1885149141:de.hattrickorganizer.gui.model%
package de.hattrickorganizer.gui.model;

import gui.HOColorName;

import javax.swing.SwingConstants;

import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.gui.theme.ThemeManager;


/**
 * Renderer für eine Combobox mit SpielerCBItems
 */
public final class SmilieRenderer implements javax.swing.ListCellRenderer {
    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    private ColorLabelEntry m_clEntry = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
    														ThemeManager.getColor(HOColorName.TABLEENTRY_BG),
                                                            SwingConstants.LEFT);

    /** TODO Missing Parameter Documentation */
    private javax.swing.JLabel m_jlLeer = new javax.swing.JLabel(" ");

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param jList TODO Missing Method Parameter Documentation
     * @param obj TODO Missing Method Parameter Documentation
     * @param index TODO Missing Method Parameter Documentation
     * @param isSelected TODO Missing Method Parameter Documentation
     * @param cellHasFocus TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final java.awt.Component getListCellRendererComponent(javax.swing.JList jList,
                                                                 Object obj, int index,
                                                                 boolean isSelected,
                                                                 boolean cellHasFocus) {
        if (obj instanceof String && (obj != null) && !obj.equals("")) {
            m_clEntry.setIcon(ThemeManager.getIcon(obj.toString()));
            return m_clEntry.getComponent(isSelected);
        }

        m_jlLeer.setOpaque(true);

        if (isSelected) {
            m_jlLeer.setBackground(SpielerTableRenderer.SELECTION_BG);
        } else {
            m_jlLeer.setBackground(ThemeManager.getColor(HOColorName.TABLEENTRY_BG));
        }

        return m_jlLeer;
    }
}
