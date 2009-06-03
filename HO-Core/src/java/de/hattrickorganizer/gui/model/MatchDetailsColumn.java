package de.hattrickorganizer.gui.model;

import de.hattrickorganizer.gui.templates.TableEntry;
import de.hattrickorganizer.model.matches.Matchdetails;

/**
 * column shows values from a matchdetail
 * @author Thorsten Dietz
 * @since V1.36
 *
 */
class MatchDetailsColumn extends UserColumn {


	/**
	 * constructor
	 * @param id
	 * @param name
	 */
	protected MatchDetailsColumn(int id,String name){
		super(id,name,name);
		this.minWidth = 20;
		preferredWidth = 90;
	}
	
	/**
	 * constructor
	 * @param id
	 * @param name
	 * @param minWidth = preferredWidth
	 */
	protected MatchDetailsColumn(int id,String name, int minWidth){
		super(id,name,name);
		this.minWidth = minWidth;
		preferredWidth = minWidth;
	}
	/**
	 * overwritten by created Columns
	 * @param matchdetails
	 * @return TableEntry
	 */
	public TableEntry getTableEntry(Matchdetails match){
		return null;
	}
}
