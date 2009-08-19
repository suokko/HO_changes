// %11693975:hoplugins.commons.ui.renderer%
package hoplugins.commons.ui.renderer;

import java.awt.Component;
import java.awt.Insets;

import javax.swing.JEditorPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;


/**
 * HTML cell renderer. Renders HTML source into a table cell.
 *
 * @author <a href="mailto:kenmooda@users.sourceforge.net">Tommi Rautava</a>
 */
public class HtmlCellRenderer extends JEditorPane implements TableCellRenderer {
    //~ Constructors -------------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -2237371473033852394L;

	/**
     * Constructor
     */
    public HtmlCellRenderer() {
        super();
        this.setContentType("text/html");
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
        // Set text/html.
        this.setText((String) object);

        // Set background color.
        if (isSelected) {
            this.setBackground(table.getSelectionBackground());
        } else {
            this.setBackground(table.getBackground());
        }

        return this;
    }
}
