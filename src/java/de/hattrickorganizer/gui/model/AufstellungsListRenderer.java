// %2449251406:de.hattrickorganizer.gui.model%
package de.hattrickorganizer.gui.model;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * Für 2 Markierungen
 */
public class AufstellungsListRenderer extends JLabel implements ListCellRenderer {

	private static final long serialVersionUID = 7059514707568786835L;
	public static Color bgColor = new Color(220, 220, 255);
	public static Color angezeigtColor = new Color(0, 0, 150);

	public final java.awt.Component getListCellRendererComponent(final JList jList, final Object value, final int row,
			final boolean isSelected, final boolean hasFocus) {
		if (value instanceof AufstellungCBItem) {
			setText(value.toString());
			setOpaque(true);

			if (isSelected) {
				setOpaque(true);
				setBackground(bgColor);
			} else {
				setOpaque(false);
				setBackground(Color.lightGray);
			}

			if (((AufstellungCBItem) value).isAngezeigt()) {
				setForeground(angezeigtColor);
			} else {
				setForeground(Color.black);
			}
		} else {
			setText(value.toString());
			setOpaque(true);

			if (isSelected) {
				setOpaque(true);
				setBackground(bgColor);
			} else {
				setOpaque(false);
				setBackground(Color.lightGray);
			}
		}

		return this;
	}
}
