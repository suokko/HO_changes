package de.hattrickorganizer.gui.model;

import plugins.IHOTableEntry;

/**
 * show a value from a SpielerMatchCBItem
 * @author Thorsten Dietz
 * @since 1.36
 *
 */
class PlayerCBItem extends UserColumn {

	/**
	 * constructor
	 * @param id
	 * @param name
	 */
	protected PlayerCBItem(int id,String name){
		super(id,name,name);
		this.minWidth =20;
		preferredWidth = 80;
	}
	
	/**
	 * overwritten by created column
	 * @param spielerCBItem
	 * @return
	 */
	public IHOTableEntry getTableEntry(SpielerMatchCBItem spielerCBItem){
		return null;
	}
}
