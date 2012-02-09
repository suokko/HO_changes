package de.hattrickorganizer.gui.templates;

import javax.swing.JComponent;
import javax.swing.SwingConstants;

import plugins.IHOTableEntry;


public class HomegrownEntry implements TableEntry{

	private ColorLabelEntry icon = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
			ColorLabelEntry.BG_STANDARD,
			SwingConstants.CENTER);
	private de.hattrickorganizer.model.Spieler spieler;

	//~ Constructors -------------------------------------------------------------------------------

	/**
	 * Creates a new Homegrown Entry.
	 */
	public HomegrownEntry() {
		super();
	}

	public final void setSpieler(de.hattrickorganizer.model.Spieler spieler) {
		this.spieler = spieler;
		updateComponent();
	}

	public final de.hattrickorganizer.model.Spieler getSpieler() {
		return spieler;
	}


	public int compareTo(IHOTableEntry obj) {
		if (obj instanceof HomegrownEntry) {
			final HomegrownEntry entry = (HomegrownEntry) obj;

			if ((entry.getSpieler() != null) && (getSpieler() != null)) {

				if (entry.getSpieler().isHomeGrown() != getSpieler().isHomeGrown()) {
					if (getSpieler().isHomeGrown() == true) {
						return 1;
					} else {
						return -1;
					}
				}
			}
		}
		return 0;
	}

	
	public final void updateComponent() {
		if (spieler != null) {
			if (spieler.isHomeGrown()) {
				icon.setIcon(ho.core.gui.theme.ThemeManager.getIcon(gui.HOIconName.HOMEGROWN));
			} else {
				icon.clear();
			}

		} else {
			icon.clear();
		}
	}


	public JComponent getComponent(boolean isSelected) {
		return icon.getComponent(isSelected);
	}


	public void clear() {
		spieler = null;
		updateComponent();
		
	}


	public void createComponent() {
		icon = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
				ColorLabelEntry.BG_STANDARD,
				SwingConstants.CENTER);
	}
}

