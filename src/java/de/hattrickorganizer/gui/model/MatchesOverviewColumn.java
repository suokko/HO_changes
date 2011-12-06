package de.hattrickorganizer.gui.model;

public class MatchesOverviewColumn extends UserColumn {

	protected MatchesOverviewColumn(int id, String name) {
		super(id, name);
		setDisplay(true);
	}

	protected MatchesOverviewColumn(int id,String name, String tooltip,int minWidth){
		super(id,name,tooltip);
		this.minWidth = minWidth;
		preferredWidth = minWidth;
	}
	
	
}
