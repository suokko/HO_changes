// %3053844359:de.hattrickorganizer.gui.model%
package de.hattrickorganizer.gui.model;

import java.awt.Color;

import de.hattrickorganizer.gui.templates.ColorLabelEntry;

/**
 * Renderer für eine Combobox mit SpielerCBItems
 */
public class SpielerCBItemRenderer implements javax.swing.ListCellRenderer {
    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public javax.swing.JLabel m_jlLeer = new javax.swing.JLabel(" ");

    //~ Methods ------------------------------------------------------------------------------------

    // public SpielerLabelEntry      m_clEntry  = new SpielerLabelEntry( null, null, 0f, true, true );
    public final java.awt.Component getListCellRendererComponent(javax.swing.JList jList,
                                                                 Object obj, int index,
                                                                 boolean isSelected,
                                                                 boolean cellHasFocus) {
        if (obj instanceof SpielerCBItem) {
            final SpielerCBItem spielerCBItem = ((SpielerCBItem) obj);
            return spielerCBItem.getListCellRendererComponent(jList, index, isSelected);
        } else {
            m_jlLeer.setOpaque(true);

            if (isSelected) {
                m_jlLeer.setBackground(SpielerTableRenderer.SELECTION_BG);
            } else {
                m_jlLeer.setBackground(ColorLabelEntry.BG_STANDARD);
            }

            return m_jlLeer;
        }
    }
}