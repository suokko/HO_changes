// %1968012293:de.hattrickorganizer.gui.model%
package de.hattrickorganizer.gui.model;

import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

import plugins.IMatchDetails;
import plugins.ISpieler;
import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.gui.theme.ThemeManager;
import de.hattrickorganizer.tools.Helper;


/**
 * Renderer für eine Combobox mit SpielerCBItems
 */
public class WetterRenderer implements ListCellRenderer {
    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public ColorLabelEntry m_clEntry = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
    												ThemeManager.getColor("tableEntry.background"),
                                                           SwingConstants.LEFT);

    public javax.swing.JLabel m_jlLeer = new javax.swing.JLabel(" ");

    public final java.awt.Component getListCellRendererComponent(javax.swing.JList jList,
                                                                 Object obj, int index,
                                                                 boolean isSelected,
                                                                 boolean cellHasFocus) {
        if (obj instanceof CBItem && (obj != null)) {
            switch (((CBItem) obj).getId()) {
                case ISpieler.SONNIG:
                    m_clEntry.setIcon(Helper.getImageIcon4Wetter(IMatchDetails.WETTER_SONNE));
                    break;

                case ISpieler.LEICHTBEWOELKT:
                    m_clEntry.setIcon(Helper.getImageIcon4Wetter(IMatchDetails.WETTER_WOLKIG));
                    break;

                case ISpieler.BEWOELKT:
                    m_clEntry.setIcon(Helper.getImageIcon4Wetter(IMatchDetails.WETTER_BEWOELKT));
                    break;

                case ISpieler.REGEN:
                    m_clEntry.setIcon(Helper.getImageIcon4Wetter(IMatchDetails.WETTER_REGEN));
                    break;
            }

            return m_clEntry.getComponent(isSelected);
        } else {
            m_jlLeer.setOpaque(true);

            if (isSelected) {
                m_jlLeer.setBackground(de.hattrickorganizer.gui.model.SpielerTableRenderer.SELECTION_BG);
            } else {
                m_jlLeer.setBackground(ThemeManager.getColor("tableEntry.background"));
            }

            return m_jlLeer;
        }
    }
}
