// %1968012293:de.hattrickorganizer.gui.model%
package ho.core.gui.comp.renderer;

import ho.core.constants.player.PlayerSpeciality;
import ho.core.datatype.CBItem;
import ho.core.gui.comp.entry.ColorLabelEntry;
import ho.core.gui.theme.HOColorName;
import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.match.IMatchDetails;
import ho.core.model.match.Weather;

import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;


/**
 * Renderer für eine Combobox mit SpielerCBItems
 */
public class WeatherListCellRenderer implements ListCellRenderer {
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
        	Weather weather = Weather.RAINY;
            switch (((CBItem) obj).getId()) {
                case PlayerSpeciality.SUN:
                	weather = Weather.SUNNY;
                    break;
                case PlayerSpeciality.PARTIALLY_CLOUDY:
                	weather = Weather.PARTIALLY_CLOUDY;
                    break;

                case PlayerSpeciality.OVERCAST:
                	weather = Weather.OVERCAST;
                    break;

                case PlayerSpeciality.RAIN:
                	weather = Weather.RAINY;
                    break;
            }
            m_clEntry.setIcon(ThemeManager.getIcon(HOIconName.WEATHER[weather.getId()]));
            return m_clEntry.getComponent(isSelected);
        } 
        m_jlLeer.setOpaque(true);

       if (isSelected) {
              m_jlLeer.setBackground(ThemeManager.getColor(HOColorName.TABLE_SELECTION_BG));
       } else {
              m_jlLeer.setBackground(ThemeManager.getColor(HOColorName.TABLEENTRY_BG));
       }
       return m_jlLeer;
    }
}
