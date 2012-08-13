package ho.module.ifa.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;

public class RendererDecorator implements TableCellRenderer {
	private JPanel panel;
	private JLabel iconLabel = new JLabel();
	private Border panelBorder;
	private Color panelBackground;

	public RendererDecorator(Border panelBorder, Color panelBackground) {
		this.panelBorder = panelBorder;
		this.panelBackground = panelBackground;
	}

	public RendererDecorator() {
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int col) {
		JLabel c = new JLabel(value + "");
		embellishComponent(c);
		return this.panel;
	}

	private void embellishComponent(Component c) {
		if (this.panel == null) {
			this.iconLabel.setPreferredSize(new Dimension(9, 9));
			this.panel = new JPanel();
			this.panel.setLayout(new BorderLayout());
			((JLabel) c).setHorizontalAlignment(0);
			this.panel.add(c);
			this.panel.add(this.iconLabel, "East");
			c.setForeground(UIManager.getColor("TableHeader.foreground"));
			this.panel
					.setBackground(this.panelBackground != null ? this.panelBackground
							: UIManager.getColor("TableHeader.background"));
			this.panel.setForeground(UIManager
					.getColor("TableHeader.foreground"));
			this.panel.setBorder(this.panelBorder != null ? this.panelBorder
					: UIManager.getBorder("TableHeader.cellBorder"));
		}
	}

	public void setLabelIcon(Icon icon) {
		this.iconLabel.setIcon(icon);
	}

	public Icon getLabelIcon() {
		return this.iconLabel.getIcon();
	}
}
