// %602244001:de.hattrickorganizer.gui.model%
package de.hattrickorganizer.gui.model;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class GroupableTableHeaderUI extends BasicTableHeaderUI {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param c TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Dimension getPreferredSize(JComponent c) {
        long width = 0;
        final Enumeration enumeration = header.getColumnModel().getColumns();

        while (enumeration.hasMoreElements()) {
            final TableColumn aColumn = (TableColumn) enumeration.nextElement();
            width = width + aColumn.getPreferredWidth();
        }

        return createHeaderSize(width);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param g TODO Missing Method Parameter Documentation
     * @param c TODO Missing Method Parameter Documentation
     */
    public final void paint(Graphics g, JComponent c) {
        final Rectangle clipBounds = g.getClipBounds();

        if (header.getColumnModel() == null) {
            return;
        }

        ((GroupableTableHeader) header).setColumnMargin();

        int column = 0;
        final Dimension size = header.getSize();
        final Rectangle cellRect = new Rectangle(0, 0, size.width, size.height);
        final Hashtable h = new Hashtable();
        final int columnMargin = header.getColumnModel().getColumnMargin();

        final Enumeration enumeration = header.getColumnModel().getColumns();

        while (enumeration.hasMoreElements()) {
            cellRect.height = size.height;
            cellRect.y = 0;

            final TableColumn aColumn = (TableColumn) enumeration.nextElement();
            final Enumeration cGroups = ((GroupableTableHeader) header).getColumnGroups(aColumn);

            if (cGroups != null) {
                int groupHeight = 0;

                while (cGroups.hasMoreElements()) {
                    final ColumnGroup cGroup = (ColumnGroup) cGroups.nextElement();
                    Rectangle groupRect = (Rectangle) h.get(cGroup);

                    if (groupRect == null) {
                        groupRect = new Rectangle(cellRect);

                        final Dimension d = cGroup.getSize(header.getTable());
                        groupRect.width = d.width;
                        groupRect.height = d.height;
                        h.put(cGroup, groupRect);
                    }

                    paintCell(g, groupRect, cGroup);
                    groupHeight += groupRect.height;
                    cellRect.height = size.height - groupHeight;
                    cellRect.y = groupHeight;
                }
            }

            cellRect.width = aColumn.getWidth() + columnMargin;

            if (cellRect.intersects(clipBounds)) {
                paintCell(g, cellRect, column);
            }

            cellRect.x += cellRect.width;
            column++;
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private int getHeaderHeight() {
        int height = 32;
        final TableColumnModel columnModel = header.getColumnModel();

        for (int column = 0; column < columnModel.getColumnCount(); column++) {
            final TableColumn aColumn = columnModel.getColumn(column);
            final TableCellRenderer renderer = aColumn.getHeaderRenderer();
            final Component comp = renderer.getTableCellRendererComponent(header.getTable(),
                                                                          aColumn.getHeaderValue(),
                                                                          false, false, -1, column);
            int cHeight = comp.getPreferredSize().height;
            final Enumeration enumi = ((GroupableTableHeader) header).getColumnGroups(aColumn);

            if (enumi != null) {
                while (enumi.hasMoreElements()) {
                    final ColumnGroup cGroup = (ColumnGroup) enumi.nextElement();
                    cHeight += cGroup.getSize(header.getTable()).height;
                }
            }

            height = Math.max(height, cHeight);
        }

        return height;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param width TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private Dimension createHeaderSize(long width) {
        final TableColumnModel columnModel = header.getColumnModel();
        width += (columnModel.getColumnMargin() * columnModel.getColumnCount());

        if (width > Integer.MAX_VALUE) {
            width = Integer.MAX_VALUE;
        }

        return new Dimension((int) width, getHeaderHeight());
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param g TODO Missing Method Parameter Documentation
     * @param cellRect TODO Missing Method Parameter Documentation
     * @param columnIndex TODO Missing Method Parameter Documentation
     */
    private void paintCell(Graphics g, Rectangle cellRect, int columnIndex) {
        final TableColumn aColumn = header.getColumnModel().getColumn(columnIndex);
        final TableCellRenderer renderer = aColumn.getHeaderRenderer();
        final Component component = renderer.getTableCellRendererComponent(header.getTable(),
                                                                           aColumn.getHeaderValue(),
                                                                           false, false, -1,
                                                                           columnIndex);
        rendererPane.add(component);
        rendererPane.paintComponent(g, component, header, cellRect.x, cellRect.y, cellRect.width,
                                    cellRect.height, true);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param g TODO Missing Method Parameter Documentation
     * @param cellRect TODO Missing Method Parameter Documentation
     * @param cGroup TODO Missing Method Parameter Documentation
     */
    private void paintCell(Graphics g, Rectangle cellRect, ColumnGroup cGroup) {
        final TableCellRenderer renderer = cGroup.getHeaderRenderer();
        final Component component = renderer.getTableCellRendererComponent(header.getTable(),
                                                                           cGroup.getHeaderValue(),
                                                                           false, false, -1, -1);
        rendererPane.add(component);
        rendererPane.paintComponent(g, component, header, cellRect.x, cellRect.y, cellRect.width,
                                    cellRect.height, true);
    }
}
