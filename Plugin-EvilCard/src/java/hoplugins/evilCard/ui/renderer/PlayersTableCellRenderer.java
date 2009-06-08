// %3399341330:hoplugins.evilCard.ui.renderer%
/*
 * Created on 24-feb-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package hoplugins.evilCard.ui.renderer;


/**
 */
import hoplugins.evilCard.ui.model.PlayersTableModel;

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
public class PlayersTableCellRenderer extends DefaultTableCellRenderer {
    //~ Static fields/initializers -----------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -8182314658725941668L;

	/** TODO Missing Parameter Documentation */
    public static final Color WARNINGS_COLOR = new Color(255, 255, 200);

    /** TODO Missing Parameter Documentation */
    public static final Color WARNINGS_TYPE1_COLOR = WARNINGS_COLOR;

    /** TODO Missing Parameter Documentation */
    public static final Color WARNINGS_TYPE2_COLOR = new Color(255, 227, 200);

    /** TODO Missing Parameter Documentation */
    public static final Color DIRECT_RED_COLOR = new Color(255, 200, 200);

    /** TODO Missing Parameter Documentation */
    public static final Color NUMBER_COLOR = new Color(220, 255, 220);

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
            case PlayersTableModel.COL_CARDS:
            case PlayersTableModel.COL_MATCHES:
            case PlayersTableModel.COL_RAW_AVERAGE:
            case PlayersTableModel.COL_WEIGHTED_AVERAGE:
                this.setBackground(NUMBER_COLOR);
                this.setHorizontalAlignment(SwingConstants.CENTER);
                break;

            case PlayersTableModel.COL_WARNINGS:
                this.setBackground(WARNINGS_COLOR);
                this.setHorizontalAlignment(SwingConstants.CENTER);
                break;

            case PlayersTableModel.COL_WARNINGS_TYPE1:
            case PlayersTableModel.COL_WARNINGS_TYPE3:
                this.setBackground(WARNINGS_TYPE1_COLOR);
                this.setHorizontalAlignment(SwingConstants.CENTER);
                break;

            case PlayersTableModel.COL_WARNINGS_TYPE2:
            case PlayersTableModel.COL_WARNINGS_TYPE4:
                this.setBackground(WARNINGS_TYPE2_COLOR);
                this.setHorizontalAlignment(SwingConstants.CENTER);
                break;

            case PlayersTableModel.COL_DIRECT_RED_CARDS:
                this.setBackground(DIRECT_RED_COLOR);
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
