// %3174669501:de.hattrickorganizer.gui.utils%
package de.hattrickorganizer.gui.utils;

import java.awt.event.MouseEvent;

import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class ToolTipHeader extends JTableHeader {
    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    String[] toolTips;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new ToolTipHeader object.
     *
     * @param model TODO Missing Constructuor Parameter Documentation
     */
    public ToolTipHeader(TableColumnModel model) {
        super(model);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param toolTips TODO Missing Method Parameter Documentation
     */
    public final void setToolTipStrings(String[] toolTips) {
        this.toolTips = toolTips;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param e TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final String getToolTipText(MouseEvent e) {
        final int col = columnAtPoint(e.getPoint());
        final int modelCol = getTable().convertColumnIndexToModel(col);
        String retStr;

        try {
            retStr = toolTips[modelCol];
        } catch (NullPointerException ex) {
            retStr = "";
        } catch (ArrayIndexOutOfBoundsException ex) {
            retStr = "";
        }

        if (retStr == null || retStr.length() < 1) {
            retStr = super.getToolTipText(e);
        }

        return retStr;
    }
}
