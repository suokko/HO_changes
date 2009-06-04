// %1126721330385:hoplugins.transfers.ui%
package hoplugins.transfers.ui;

import hoplugins.transfers.vo.PlayerTransfer;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


/**
 * Cell reneder to show an icon for the type of transfer (in or out).
 *
 * @author <a href=mailto:nethyperon@users.sourceforge.net>Boy van der Werf</a>
 */
public class IconCellRenderer extends DefaultTableCellRenderer {
    //~ Methods ------------------------------------------------------------------------------------

    /** {@inheritDoc} */
    public final Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, null, isSelected, hasFocus, row, column);

        final int type = ((Integer) value).intValue();

        if (type == PlayerTransfer.BUY) {
            this.setIcon(Icon.IN); //return new JLabel(Icon.IN);
        } else {
            this.setIcon(Icon.OUT); //return new JLabel(Icon.OUT);
        }

        return this;
    }
}
