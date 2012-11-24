package ho.core.gui.comp.entry;

import javax.swing.JComponent;
import javax.swing.SwingConstants;



public class HomegrownEntry implements IHOTableEntry{

	private ColorLabelEntry icon = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
			ColorLabelEntry.BG_STANDARD,
			SwingConstants.CENTER);
	private ho.core.model.player.Spieler spieler;

	//~ Constructors -------------------------------------------------------------------------------

	/**
	 * Creates a new Homegrown Entry.
	 */
	public HomegrownEntry() {
		super();
	}

	public final void setSpieler(ho.core.model.player.Spieler spieler) {
		this.spieler = spieler;
		updateComponent();
	}

	public final ho.core.model.player.Spieler getSpieler() {
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
				icon.setIcon(ho.core.gui.theme.ThemeManager.getIcon(ho.core.gui.theme.HOIconName.HOMEGROWN));
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

