package ho.core.gui.model;

import ho.core.gui.comp.entry.IHOTableEntry;
import ho.core.gui.comp.table.UserColumn;
import ho.core.model.match.MatchKurzInfo;

/**
 * column shows values from a matchKurzInfo
 * @author Thorsten Dietz
 * @since 1.36
 */
class MatchKurzInfoColumn extends UserColumn {

	/**
	 * constructor
	 * @param id
	 * @param name
	 * @param minWidth
	 */
	protected MatchKurzInfoColumn(int id,String name,int minWidth){
		this(id,name,name,minWidth);
		
	}
	
	/**
	 * constructor
	 * @param id
	 * @param name
	 * @param tooltip
	 * @param minWidth
	 */
	protected MatchKurzInfoColumn(int id,String name, String tooltip,int minWidth){
		super(id,name,tooltip);
		this.minWidth = minWidth;
		preferredWidth = minWidth;
	}
	
	/**
	 * overwritten by created column
	 * @param match
	 * @return
	 */
	public IHOTableEntry getTableEntry(MatchKurzInfo match){
		return null;
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
