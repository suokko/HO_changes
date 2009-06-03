// %3053844359:de.hattrickorganizer.gui.model%
package de.hattrickorganizer.gui.model;

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
            return spielerCBItem.getListCellRendererComponent(jList, obj, index, isSelected,
                                                              cellHasFocus);
        } else {
            m_jlLeer.setOpaque(true);

            if (isSelected) {
                m_jlLeer.setBackground(de.hattrickorganizer.gui.model.SpielerTableRenderer.SELECTION_BG);
            } else {
                m_jlLeer.setBackground(java.awt.Color.white);
            }

            return m_jlLeer;
        }
    }
}
