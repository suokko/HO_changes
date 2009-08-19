// %950346978:hoplugins.teamplanner.ui.renderer%
package hoplugins.teamplanner.ui.renderer;

import hoplugins.teamplanner.ui.model.OperationCell;

import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;


/**
 * HTML cell renderer. Renders HTML source into a table cell.
 *
 * @author <a href="mailto:kenmooda@users.sourceforge.net">Tommi Rautava</a>
 */
public class OperationDataCellRenderer extends JTextArea implements TableCellRenderer {
    //~ Constructors -------------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 4377350654433371118L;

	/**
     * Constructor
     */
    public OperationDataCellRenderer() {
        super();

        //this.setLineWrap(true);
        //this.setWrapStyleWord(true);
        this.setAlignmentY(Component.CENTER_ALIGNMENT);
        this.setMargin(new Insets(0, 1, 0, 1));
    }

    //~ Methods ------------------------------------------------------------------------------------

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable,
     *      java.lang.Object, boolean, boolean, int, int)
     */
    public Component getTableCellRendererComponent(JTable table, Object object, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        // Is the object OperationCell (should be always)
        if (object instanceof OperationCell) {
            OperationCell oc = (OperationCell) object;

            // Is the data valid?
            if (oc.isValid()) {
                // Data is valid, how is balance?
                if (oc.getBalance() >= 0) {
                    setForeground(Color.BLACK);
                } else {
                    setForeground(Color.RED);
                }
            } else {
                // Invalid data.
                setForeground(Color.BLUE);
            }
        }

        // Set cell text.
        try {
            this.setText(object.toString());
        } catch (Exception e) {
            this.setText(e.toString());
        }

        // Increase row height, if this is the tallest cell.
        int height_wanted = (int) this.getPreferredSize().getHeight();

        if (height_wanted > table.getRowHeight(row)) {
            table.setRowHeight(row, height_wanted);
        }

        // Set background color.
        if (isSelected) {
            this.setBackground(table.getSelectionBackground());
        } else {
            this.setBackground(table.getBackground());
        }

        return this;
    }
}
