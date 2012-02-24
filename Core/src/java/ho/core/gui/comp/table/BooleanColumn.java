package ho.core.gui.comp.table;

import ho.core.model.Spieler;

/**
 * editable checkbox in a JTable
 * @author Thorsten Dietz
 *
 */
public class BooleanColumn extends UserColumn {

	/**
	 * constructor
	 * @param id
	 * @param name
	 * @param tooltip
	 * @param minWidth
	 */
	public BooleanColumn(int id,String name, String tooltip,int minWidth){
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
