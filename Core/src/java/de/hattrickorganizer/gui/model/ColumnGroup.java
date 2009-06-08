// %1183459409:de.hattrickorganizer.gui.model%
package de.hattrickorganizer.gui.model;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;


/**
 * ColumnGroup
 *
 * @author Nobuo Tamemasa
 * @version 1.0 10/20/98
 */
public class ColumnGroup {
    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    protected String text;

    /** TODO Missing Parameter Documentation */
    protected TableCellRenderer renderer;

    /** TODO Missing Parameter Documentation */
    protected Vector v;

    /** TODO Missing Parameter Documentation */
    protected int margin;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new ColumnGroup object.
     *
     * @param text TODO Missing Constructuor Parameter Documentation
     */
    public ColumnGroup(String text) {
        this(null, text);
    }

    /**
     * Creates a new ColumnGroup object.
     *
     * @param renderer TODO Missing Constructuor Parameter Documentation
     * @param text TODO Missing Constructuor Parameter Documentation
     */
    public ColumnGroup(TableCellRenderer renderer, String text) {
        if (renderer == null) {
            this.renderer = new DefaultTableCellRenderer() {
                    @Override
					public Component getTableCellRendererComponent(JTable table, Object value,
                                                                   boolean isSelected,
                                                                   boolean hasFocus, int row,
                                                                   int column) {
                        final JTableHeader header = table.getTableHeader();

                        if (header != null) {
                            setForeground(header.getForeground());
                            setBackground(header.getBackground());
                            setFont(header.getFont());
                        }

                        setHorizontalAlignment(SwingConstants.CENTER);
                        setText((value == null) ? "" : value.toString());
                        setBorder(UIManager.getBorder("TableHeader.cellBorder"));
                        return this;
                    }
                };
        } else {
            this.renderer = renderer;
        }

        this.text = text;
        v = new Vector();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param c TableColumn
     * @param g ColumnGroups
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Vector getColumnGroups(TableColumn c, Vector g) {
        g.addElement(this);

        if (v.contains(c)) {
            return g;
        }

        final Enumeration enumi = v.elements();

        while (enumi.hasMoreElements()) {
            final Object obj = enumi.nextElement();

            if (obj instanceof ColumnGroup) {
                final Vector groups = ((ColumnGroup) obj).getColumnGroups(c,
                                                                                   (Vector) g.clone());

                if (groups != null) {
                    return groups;
                }
            }
        }

        return null;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param margin TODO Missing Method Parameter Documentation
     */
    public final void setColumnMargin(int margin) {
        this.margin = margin;

        final Enumeration enumi = v.elements();

        while (enumi.hasMoreElements()) {
            final Object obj = enumi.nextElement();

            if (obj instanceof ColumnGroup) {
                ((ColumnGroup) obj).setColumnMargin(margin);
            }
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param renderer TODO Missing Method Parameter Documentation
     */
    public final void setHeaderRenderer(TableCellRenderer renderer) {
        if (renderer != null) {
            this.renderer = renderer;
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final TableCellRenderer getHeaderRenderer() {
        return renderer;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Object getHeaderValue() {
        return text;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param table TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Dimension getSize(JTable table) {
        final Component comp = renderer.getTableCellRendererComponent(table, getHeaderValue(),
                                                                      false, false, -1, -1);
        final int height = comp.getPreferredSize().height;
        int width = 0;
        final Enumeration enumi = v.elements();

        while (enumi.hasMoreElements()) {
            final Object obj = enumi.nextElement();

            if (obj instanceof TableColumn) {
                final TableColumn aColumn = (TableColumn) obj;
                width += aColumn.getWidth();
                width += margin;
            } else {
                width += ((ColumnGroup) obj).getSize(table).width;
            }
        }

        return new Dimension(width, height);
    }

    /**
     * DOCUMENT ME!
     *
     * @param obj TableColumn or ColumnGroup
     */
    public final void add(Object obj) {
        if (obj == null) {
            return;
        }

        v.addElement(obj);
    }
}
