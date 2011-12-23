// %1126721330385:hoplugins.transfers.ui%
package ho.modul.transfer.history;



import gui.HOIconName;
import ho.modul.transfer.PlayerTransfer;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import de.hattrickorganizer.gui.theme.ThemeManager;


/**
 * Cell reneder to show an icon for the type of transfer (in or out).
 *
 * @author <a href=mailto:nethyperon@users.sourceforge.net>Boy van der Werf</a>
 */
class IconCellRenderer extends DefaultTableCellRenderer {
    //~ Methods ------------------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -57196039372860581L;

	/** {@inheritDoc} */
    @Override
	public final Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, null, isSelected, hasFocus, row, column);

        final int type = ((Integer) value).intValue();

        this.setIcon(ThemeManager.getIcon(type == PlayerTransfer.BUY?HOIconName.TRANSFER_IN:HOIconName.TRANSFER_OUT));
        return this;
    }
}
