package hoplugins.teamAnalyzer.ui.renderer;

import hoplugins.commons.utils.PluginProperty;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Renderer for the rating table cells
 * 
 * @see hoplugins.teamAnalyzer.ui.RatingPanel
 * 
 * @author flattermann <HO@flattermann.net>
 *
 */
public class RatingTableCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = -8095772313633000705L;

	/*
	   * @see TableCellRenderer#getTableCellRendererComponent(JTable, Object, boolean, boolean, int, int)
	   */
	  public Component getTableCellRendererComponent(JTable table, Object value,
	                                                 boolean isSelected, boolean hasFocus, 
	                                                 int row, int column) {
		  // Column 3 is the relative rating diff in %
		  // right align and adjust font color
		  if (column == 3) {
			  String curText = value.toString();
			  this.setIcon(null);
			  this.setHorizontalAlignment(SwingConstants.TRAILING);
			  this.setToolTipText(PluginProperty.getString("TeamAnalyzer.RatingPanel.Relative.Tooltip"));
			  if (curText != null) {
				  if (curText.startsWith("+")) {
					  this.setForeground(Color.GREEN.darker().darker());
					  // Remove +
					  curText = curText.substring(1);
				  } else if (curText.startsWith("-")) {
					  this.setForeground(Color.RED.darker());
					  // Remove -
					  curText = curText.substring(1);
				  } else {
					  this.setForeground(Color.BLACK);
				  }
			  }
			  this.setText(curText);
			  return this;
		  } else {
			  this.setIcon(null);
			  this.setText(null);
			  this.setForeground(Color.BLACK);
			  this.setToolTipText(null);
			  if (column == 0)
				  // area is left aligned
				  this.setHorizontalAlignment(SwingConstants.LEADING);
			  else if (column == 1)
				  // rating (as number) is right aligned
				  this.setHorizontalAlignment(SwingConstants.TRAILING);
			  else if (column == 2)
				  // icon is centered
				  this.setHorizontalAlignment(SwingConstants.CENTER);
			  else
				  // Everything (what?) else is left aligned
				  this.setHorizontalAlignment(SwingConstants.LEADING);

			  return super.getTableCellRendererComponent (table, value, isSelected, hasFocus, row, column);
		  }
	  }
}
