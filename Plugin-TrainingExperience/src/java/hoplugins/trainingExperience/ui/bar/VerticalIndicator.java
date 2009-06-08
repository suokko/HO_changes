// %3339242963:hoplugins.trainingExperience.ui.bar%
package hoplugins.trainingExperience.ui.bar;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;


/**
 * VerticalIndicator that show percentage of training
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class VerticalIndicator extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    private double actual;
    private double total;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new VerticalIndicator object.
     *
     * @param _actual actual points
     * @param _total skillup points
     */
    public VerticalIndicator(double _actual, double _total) {
        super();
        setLayout(new BorderLayout());
        setOpaque(false);
        this.actual = _actual;
        this.total = _total;

        if (actual > 0) {
            int pct = (int) (actual / total * 100);

            if (pct > 100) {
                pct = 100;
            }

            StateBar bar = new StateBar(pct, 100, Color.GREEN, Color.RED);

            bar.setOpaque(false);
            bar.setBackground(Color.YELLOW);
            add(bar, BorderLayout.CENTER);
        }
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Get Training Percentage
     *
     * @return
     */
    public double getPercentage() {
        return actual / total * 100d;
    }

    /**
     * Get Text describing training situation
     *
     * @return
     */
    public String getText() {
        return actual + "(" + total + ")"; //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Return toolTip text
     *
     * @return
     */
    public String getToolTipText() {
        return actual + "(" + total + ")"; //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Get number of training points for skillup
     *
     * @return
     */
    public double getTotal() {
        return total;
    }
}
