// %1968012293:de.hattrickorganizer.gui.model%
package de.hattrickorganizer.gui.model;

import javax.swing.SwingConstants;

import plugins.IMatchDetails;
import plugins.ISpieler;
import de.hattrickorganizer.gui.templates.ColorLabelEntry;


/**
 * Renderer für eine Combobox mit SpielerCBItems
 */
public class WetterRenderer implements javax.swing.ListCellRenderer {
    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public ColorLabelEntry m_clEntry = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                           ColorLabelEntry.BG_STANDARD,
                                                           SwingConstants.LEFT);

    /** TODO Missing Parameter Documentation */
    public javax.swing.JLabel m_jlLeer = new javax.swing.JLabel(" ");
    private javax.swing.ImageIcon m_clBewoelkt = de.hattrickorganizer.tools.Helper
                                                 .getImageIcon4Wetter(IMatchDetails.WETTER_BEWOELKT);
    private javax.swing.ImageIcon m_clLeichtBewoelkt = de.hattrickorganizer.tools.Helper
                                                       .getImageIcon4Wetter(IMatchDetails.WETTER_WOLKIG);
    private javax.swing.ImageIcon m_clRegen = de.hattrickorganizer.tools.Helper.getImageIcon4Wetter(IMatchDetails.WETTER_REGEN);
    private javax.swing.ImageIcon m_clSonnig = de.hattrickorganizer.tools.Helper
                                               .getImageIcon4Wetter(IMatchDetails.WETTER_SONNE);

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
        if (obj instanceof CBItem && (obj != null)) {
            switch (((CBItem) obj).getId()) {
                case ISpieler.SONNIG:
                    m_clEntry.setIcon(m_clSonnig);
                    break;

                case ISpieler.LEICHTBEWOELKT:
                    m_clEntry.setIcon(m_clLeichtBewoelkt);
                    break;

                case ISpieler.BEWOELKT:
                    m_clEntry.setIcon(m_clBewoelkt);
                    break;

                case ISpieler.REGEN:
                    m_clEntry.setIcon(m_clRegen);
                    break;
            }

            return m_clEntry.getComponent(isSelected);
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
