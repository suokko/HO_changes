// %3954565201:hoplugins.teamplanner.util%
package hoplugins.teamplanner.util;

import java.awt.Component;
import java.awt.Font;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;


/**
 * Missing Class Documentation
 *
 * @author Draghetto
 */
public class RowHeaderRenderer extends JLabel implements ListCellRenderer {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 8984290622169408462L;
	private Border normalBorder;
    private Border selectedBorder;
    private Font normalFont;
    private Font selectedFont;
    private JTable table;
    private List<String> headerList;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new RowHeaderRenderer object.
     *
     * @param table Missing Constructuor Parameter Documentation
     * @param headerList Missing Constructuor Parameter Documentation
     */
    RowHeaderRenderer(JTable table, List<String> headerList) {
        this.table = table;
        this.headerList = headerList;
        normalBorder = UIManager.getBorder("TableHeader.cellBorder");
        selectedBorder = BorderFactory.createRaisedBevelBorder();

        final JTableHeader header = table.getTableHeader();

        normalFont = header.getFont();
        selectedFont = normalFont.deriveFont(normalFont.getStyle() | Font.BOLD);
        setForeground(header.getForeground());
        setBackground(header.getBackground());
        setOpaque(true);
        setHorizontalAlignment(CENTER);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     *
     * @param list Missing Method Parameter Documentation
     * @param value Missing Method Parameter Documentation
     * @param index Missing Method Parameter Documentation
     * @param isSelected Missing Method Parameter Documentation
     * @param cellHasFocus Missing Method Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    public Component getListCellRendererComponent(JList list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        if (table.getSelectionModel().isSelectedIndex(index)) {
            setFont(selectedFont);
            setBorder(selectedBorder);
        } else {
            setFont(normalFont);
            setBorder(normalBorder);
        }

        //String label = String.valueOf(index + 1);
        String label = String.valueOf(headerList.get(index));

        setText(label);

        return this;
    }
}
