// %1126721451229:hoplugins.trainingExperience.ui.component%
package hoplugins.trainingExperience.ui.component;

import javax.swing.JComboBox;


/**
 * ComboBox for editing the Training intensity
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class IntensityComboBox extends JComboBox {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new IntensityComboBox object.
     */
    public IntensityComboBox() {
        super();

        for (int i = 0; i <= 100; i++) {
            addItem(new Integer(i));
        }
    }
}
