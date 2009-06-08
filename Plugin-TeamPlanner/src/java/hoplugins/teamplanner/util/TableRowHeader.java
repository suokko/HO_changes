// %2603171993:hoplugins.teamplanner.util%
package hoplugins.teamplanner.util;

import java.awt.Rectangle;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.JTableHeader;


/**
 * Missing Class Documentation
 *
 * @author Draghetto
 */
public class TableRowHeader extends JList {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 850256662372653780L;
	private JTable table;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TableRowHeader object.
     *
     * @param table Missing Constructuor Parameter Documentation
     * @param header Missing Constructuor Parameter Documentation
     */
    public TableRowHeader(JTable table, List header) {
        super(new TableRowHeaderModel(table));
        this.table = table;
        setFixedCellHeight(table.getRowHeight());
        setFixedCellWidth(preferredHeaderWidth());
        setCellRenderer(new RowHeaderRenderer(table, header));
        setSelectionModel(table.getSelectionModel());
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Returns the bounds of the specified range of items in JList   coordinates. Returns null if
     * index isn't valid.
     *
     * @param index0 the index of the first JList cell in the range
     * @param index1 the index of the last JList cell in the range
     *
     * @return the bounds of the indexed cells in pixels
     */
    @Override
	public Rectangle getCellBounds(int index0, int index1) {
        Rectangle rect0 = table.getCellRect(index0, 0, true);
        Rectangle rect1 = table.getCellRect(index1, 0, true);
        int y;
        int height;

        if (rect0.y < rect1.y) {
            y = rect0.y;
            height = (rect1.y + rect1.height) - y;
        } else {
            y = rect1.y;
            height = (rect0.y + rect0.height) - y;
        }

        return new Rectangle(0, y, getFixedCellWidth(), height);
    }

    // assume that row header width should be big enough to display row number Integer.MAX_VALUE completely 
    private int preferredHeaderWidth() {
        JLabel longestRowLabel = new JLabel("6535653453462527");
        JTableHeader header = table.getTableHeader();

        longestRowLabel.setBorder(header.getBorder());

        //UIManager.getBorder("TableHeader.cellBorder")); 
        longestRowLabel.setHorizontalAlignment(SwingConstants.CENTER);
        longestRowLabel.setFont(header.getFont());

        return longestRowLabel.getPreferredSize().width;
    }
}
