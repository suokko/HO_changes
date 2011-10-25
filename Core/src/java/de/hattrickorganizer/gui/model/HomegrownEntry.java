package de.hattrickorganizer.gui.model;

import javax.swing.JComponent;
import javax.swing.SwingConstants;

import plugins.IHOTableEntry;

import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.gui.templates.TableEntry;

public class HomegrownEntry extends TableEntry{

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

	@Override
	public int compareTo(IHOTableEntry obj) {
		if (obj instanceof HomegrownEntry) {
			final HomegrownEntry entry = (HomegrownEntry) obj;

			if ((entry.getSpieler() != null) && (getSpieler() != null)) {

				if (entry.getSpieler().isHomeGrown() != getSpieler().isHomeGrown()) {
					if (getSpieler().isHomeGrown() == true) {
						return -1;
					} else {
						return 1;
					}
				}
			}
		}
		return 0;
	}

	
	@Override
	public final void updateComponent() {
		if (spieler != null) {
			if (spieler.isHomeGrown()) {
				icon.setIcon(de.hattrickorganizer.gui.theme.ThemeManager.getIcon(gui.HOIconName.HOMEGROWN));
			} else {
				icon.clear();
			}

		} else {
			icon.clear();
		}
	}

	@Override
	public JComponent getComponent(boolean isSelected) {
		return icon.getComponent(isSelected);
	}

	@Override
	public void clear() {
		spieler = null;
		updateComponent();
		
	}

	@Override
	public void createComponent() {
		icon = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
				ColorLabelEntry.BG_STANDARD,
				SwingConstants.CENTER);
	}
}

