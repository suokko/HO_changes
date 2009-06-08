// %2562534051:hoplugins.evilCard.ui.renderer%
/*
 * Created on 24-feb-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package hoplugins.evilCard.ui.renderer;


/**
 */
import hoplugins.evilCard.ui.model.DetailsTableModel;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class DetailsTableCellRenderer extends DefaultTableCellRenderer {
    //~ Static fields/initializers -----------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -640090984809074407L;

	/** TODO Missing Parameter Documentation */
    public static final Color WARNINGS_TYPE1_COLOR = new Color(255, 255, 200);

    /** TODO Missing Parameter Documentation */
    public static final Color WARNINGS_TYPE2_COLOR = new Color(255, 227, 200);

    /** TODO Missing Parameter Documentation */
    public static final Color DIRECT_RED_COLOR = new Color(255, 200, 200);

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Constructor.
     */
    public DetailsTableCellRenderer() {
        super();
        this.setVerticalAlignment(SwingConstants.CENTER);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable,
     *      java.lang.Object, boolean, boolean, int, int)
     */
    @Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        switch (column) {
            case DetailsTableModel.COL_DIRECT_RED_CARDS:
                this.setBackground(DIRECT_RED_COLOR);
                this.setHorizontalAlignment(SwingConstants.CENTER);
                break;

            case DetailsTableModel.COL_WARNINGS_TYPE1:
            case DetailsTableModel.COL_WARNINGS_TYPE3:
                this.setBackground(WARNINGS_TYPE1_COLOR);
                this.setHorizontalAlignment(SwingConstants.CENTER);
                break;

            case DetailsTableModel.COL_WARNINGS_TYPE2:
            case DetailsTableModel.COL_WARNINGS_TYPE4:
                this.setBackground(WARNINGS_TYPE1_COLOR);
                this.setHorizontalAlignment(SwingConstants.CENTER);
                break;

            case DetailsTableModel.COL_MATCH_RESULT:
                this.setHorizontalAlignment(SwingConstants.CENTER);
                break;

            default:
                this.setBackground(table.getBackground());
                this.setHorizontalAlignment(SwingConstants.LEFT);
                break;
        }

        if (isSelected) {
            this.setBackground(table.getSelectionBackground());
        }

        return this;
    }
}
