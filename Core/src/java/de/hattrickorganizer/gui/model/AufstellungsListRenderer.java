// %2449251406:de.hattrickorganizer.gui.model%
package de.hattrickorganizer.gui.model;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import de.hattrickorganizer.gui.theme.ThemeManager;

/**
 * Für 2 Markierungen
 */
public class AufstellungsListRenderer extends JLabel implements ListCellRenderer {

	private static final long serialVersionUID = 7059514707568786835L;
	private static final Color bgColor = ThemeManager.getColor("ho.list.selection.background");//new Color(220, 220, 255);
	private static Color angezeigtColor = ThemeManager.getColor("ho.list.current.foreground");//new Color(0, 0, 150);

	public final Component getListCellRendererComponent(final JList jList, final Object value, final int row,
			final boolean isSelected, final boolean hasFocus) {
		setText(value.toString());
		setOpaque(true);
		setForeground(ThemeManager.getColor("ho.list.foreground"));
		if (isSelected) {
			setBackground(bgColor);
		} else {
			setOpaque(false);
			setBackground(jList.getBackground());
		}
		
		if (value instanceof AufstellungCBItem) {
			if (((AufstellungCBItem) value).isAngezeigt()) {
				setForeground(angezeigtColor);
			} 
		} 

		return this;
	}
}
