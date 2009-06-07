package de.hattrickorganizer.gui.model;

import de.hattrickorganizer.model.Spieler;

/**
 * editable checkbox in a JTable
 * @author Thorsten Dietz
 *
 */
class BooleanColumn extends UserColumn {

	/**
	 * constructor
	 * @param id
	 * @param name
	 * @param tooltip
	 * @param minWidth
	 */
	protected BooleanColumn(int id,String name, String tooltip,int minWidth){
		super(id,name,tooltip);
		this.minWidth = minWidth;
		preferredWidth = minWidth;
	}
	
	/**
	 * return a Boolean, not a TableEntry like all other Columns!
	 * @param player
	 * @return Boolean
	 */
	public Boolean getValue(Spieler player){
		return Boolean.valueOf(player.isSpielberechtigt());
	}
	
}
