// %1968012293:de.hattrickorganizer.gui.model%
package de.hattrickorganizer.gui.model;

import gui.HOColorName;
import gui.HOIconName;

import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

import plugins.IMatchDetails;
import plugins.ISpieler;
import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.gui.theme.ThemeManager;


/**
 * Renderer für eine Combobox mit SpielerCBItems
 */
public class WetterRenderer implements ListCellRenderer {
    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public ColorLabelEntry m_clEntry = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
    												ThemeManager.getColor(HOColorName.TABLEENTRY_BG),
                                                           SwingConstants.LEFT);

    public javax.swing.JLabel m_jlLeer = new javax.swing.JLabel(" ");

    public final java.awt.Component getListCellRendererComponent(javax.swing.JList jList,
                                                                 Object obj, int index,
                                                                 boolean isSelected,
                                                                 boolean cellHasFocus) {
        if (obj instanceof CBItem && (obj != null)) {
        	int wert = 0;
            switch (((CBItem) obj).getId()) {
                case ISpieler.SONNIG:
                    wert = IMatchDetails.WETTER_SONNE;
                    break;
                case ISpieler.LEICHTBEWOELKT:
                	wert = IMatchDetails.WETTER_WOLKIG;
                    break;

                case ISpieler.BEWOELKT:
                	wert = IMatchDetails.WETTER_BEWOELKT;
                    break;

                case ISpieler.REGEN:
                	wert = IMatchDetails.WETTER_REGEN;
                    break;
            }
            m_clEntry.setIcon(ThemeManager.getIcon(HOIconName.WEATHER[wert]));
            return m_clEntry.getComponent(isSelected);
        } else {
            m_jlLeer.setOpaque(true);

            if (isSelected) {
                m_jlLeer.setBackground(de.hattrickorganizer.gui.model.SpielerTableRenderer.SELECTION_BG);
            } else {
                m_jlLeer.setBackground(ThemeManager.getColor(HOColorName.TABLEENTRY_BG));
            }

            return m_jlLeer;
        }
    }
}
