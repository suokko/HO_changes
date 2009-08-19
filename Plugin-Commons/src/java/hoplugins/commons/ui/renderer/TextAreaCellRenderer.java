// %2652773612:hoplugins.commons.ui.renderer%
package hoplugins.commons.ui.renderer;

import java.awt.Component;
import java.awt.Insets;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;


/**
 * TextArea table cell renderer. Allows multiple line table cells.
 *
 * @author <a href="mailto:kenmooda@users.sourceforge.net">Tommi Rautava</a>
 */
public class TextAreaCellRenderer extends JTextArea implements TableCellRenderer {
    //~ Constructors -------------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 7087240948541117550L;

	/**
     * Constructor
     */
    public TextAreaCellRenderer() {
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
        // Set text.
        try {
            this.setText(object.toString());
        } catch (Exception e) {
            this.setText(e.toString());
        }

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
