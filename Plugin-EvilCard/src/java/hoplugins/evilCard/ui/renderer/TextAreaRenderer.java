// %536666283:hoplugins.evilCard.ui.renderer%
package hoplugins.evilCard.ui.renderer;

import java.awt.Component;
import java.awt.Insets;

import javax.swing.JEditorPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;


/**
 * TextArea table cell renderer. Allows multiple line table cells.
 *
 * @author <a href="mailto:kenmooda@users.sourceforge.net">Tommi Rautava</a>
 */
public class TextAreaRenderer extends JEditorPane implements TableCellRenderer {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     *
     */
    public TextAreaRenderer() {
        super();
        this.setContentType("text/html");
        this.setAlignmentY(TextAreaRenderer.CENTER_ALIGNMENT);
        this.setMargin(new Insets(0, 1, 0, 1));
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param table TODO Missing Method Parameter Documentation
     * @param object TODO Missing Method Parameter Documentation
     * @param isSelected TODO Missing Method Parameter Documentation
     * @param hasFocus TODO Missing Method Parameter Documentation
     * @param row TODO Missing Method Parameter Documentation
     * @param column TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
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
