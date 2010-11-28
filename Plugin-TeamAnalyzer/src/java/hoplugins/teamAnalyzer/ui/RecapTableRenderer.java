// %3338167864:hoplugins.teamAnalyzer.ui%
package hoplugins.teamAnalyzer.ui;

import hoplugins.Commons;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Renderer for the selected matches in the TA.
 * 
 * @author Draghetto
 */
public class RecapTableRenderer extends DefaultTableCellRenderer {
	// ~ Methods
	// ------------------------------------------------------------------------------------

	/**
	 * 
	 */
	private static final long serialVersionUID = -1496877275674136140L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax
	 * .swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		// Is this 'average' or 'no matches' row?
		if ((row == 0) && ((table.getRowCount() > 1) || (table.getValueAt(0, 1) == null))) {
			Font nFont = new Font(this.getFont().getFontName(), Font.BOLD, this.getFont().getSize());

			this.setFont(nFont);
			this.setBackground(UIColors.SelectedRowColor);
			this.setIcon(null);
		} else {
			int matchType = 0;
			boolean isHomeMatch = true;

			try {
				matchType = ((Integer) table.getValueAt(row, 20)).intValue();
				isHomeMatch = ((Boolean) table.getValueAt(row, 21)).booleanValue();
				this.setBackground(UIColors.getColor4Matchtyp(matchType));
			} catch (Exception e) {
				// Make the exception visible.
				this.setBackground(Color.RED);
				this.setText("!!!"); //$NON-NLS-1$
				this.setToolTipText(e.toString());

				return this;
			}

			switch (column) {
			case 1:

				// Set icon for match type.
				this.setIcon(Commons.getModel().getHelper().getImageIcon4Spieltyp(matchType));
				this.setText(null);

				StringBuffer tipText = new StringBuffer(Commons.getModel().getHelper().getNameForMatchTyp(matchType));

				tipText.append(" - "); //$NON-NLS-1$

				if (isHomeMatch) {
					tipText.append(Commons.getModel().getLanguageString("Heim")); //$NON-NLS-1$
				} else {
					tipText.append(Commons.getModel().getLanguageString("Gast")); //$NON-NLS-1$
				}

				this.setToolTipText(tipText.toString());

				break;

			default:
				this.setToolTipText(null);
				this.setIcon(null);
			}
		}

		if (isSelected) {
			this.setBackground(UIColors.RecapRowColor);
		}

		return this;
	}
}
