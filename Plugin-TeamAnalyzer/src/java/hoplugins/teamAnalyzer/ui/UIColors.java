// %1856096278:hoplugins.teamAnalyzer.ui%
package hoplugins.teamAnalyzer.ui;

import java.awt.Color;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class UIColors {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public static Color SelectedRowColor = new Color(235, 235, 235);

    /** TODO Missing Parameter Documentation */
    public static Color RecapRowColor = new Color(220, 220, 255);

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param type TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static Color getColor4Matchtyp(int type) {
        if (type == 1) {
            return new Color(255, 255, 200);
        } else if (type == 2) {
            return new Color(255, 200, 200);
        } else if (type == 3) {
            return new Color(200, 255, 200);
        }

        return Color.white;
    }
}
