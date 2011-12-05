package de.hattrickorganizer.gui.model;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

import de.hattrickorganizer.gui.templates.ColorLabelEntry;

public class BooleanTableCellrenderer extends JCheckBox implements TableCellRenderer {

	private static final long serialVersionUID = 1232313L;

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		boolean b = ((Boolean)value).booleanValue();
		setSelected(b);
		setOpaque(true);
		setBackground(isSelected?SpielerTableRenderer.SELECTION_BG:ColorLabelEntry.BG_STANDARD);
		setHorizontalAlignment(SwingConstants.CENTER);
		return this;
	}

}
