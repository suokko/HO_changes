// %1126721330135:hoplugins.transfers.ui%
package hoplugins.transfers.ui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


/**
 * Cell reneder to show an icon for the type of transfer (in or out).
 *
 * @author <a href=mailto:nethyperon@users.sourceforge.net>Boy van der Werf</a>
 */
public class ColorCellRenderer extends DefaultTableCellRenderer {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public static final Color GREEN = new Color(220, 255, 220);

    /** TODO Missing Parameter Documentation */
    public static final Color YELLOW = new Color(255, 255, 200);

    //~ Instance fields ----------------------------------------------------------------------------

    private Color color;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates an instance of ColorcellRenderer.
     * 
     * @param color Color tto use
     */
    public ColorCellRenderer(Color color) {
        super();
        this.color = color;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /** {@inheritDoc} */
    public final Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (!isSelected) {
            this.setBackground(this.color);
        }

        final int intVal = ((Integer) value).intValue();

        if (intVal == 0) {
            setText("---");
        }

        return this;
    }
}
