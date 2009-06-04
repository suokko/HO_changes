// %2328098351:hoplugins.teamAnalyzer.ui.model%
package hoplugins.teamAnalyzer.ui.model;

import hoplugins.commons.ui.BaseTableModel;

import java.util.Vector;

import javax.swing.ImageIcon;


/**
 * Custom RatingTable model
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class UiRecapTableModel extends BaseTableModel {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new UiRecapTableModel object.
     *
     * @param vector Vector of table data
     * @param vector2 Vector of column names
     */
    public UiRecapTableModel(Vector vector, Vector vector2) {
        super(vector, vector2);
    }

    /**
     * Creates a new instance of UiFilterTableModel
     */
    public UiRecapTableModel() {
        super();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Returns the column class type
     *
     * @param column
     *
     * @return
     */
    public Class getColumnClass(int column) {
        if (column == 1) {
            return ImageIcon.class;
        }

        return super.getColumnClass(column);
    }
}
