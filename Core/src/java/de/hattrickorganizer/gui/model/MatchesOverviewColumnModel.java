package de.hattrickorganizer.gui.model;

import de.hattrickorganizer.model.matches.MatchesOverviewRow;
import de.hattrickorganizer.tools.StringUtilities;


public final class MatchesOverviewColumnModel extends HOColumnModel {
		
	private static final long serialVersionUID = 1L;
	private MatchesOverviewRow[] rows;
	protected MatchesOverviewColumnModel(int id){
		super(id,"MatchesStatistics");
		initialize();
	}
	
	
	private void initialize() {
		columns =  UserColumnFactory.createMatchesStatisticsArray();
	
	}
	
	@Override
	protected void initData() {
//		UserColumn [] tmpDisplayedColumns = getDisplayedColumns();
		m_clData = new Object[rows.length][columns.length];
		for (int i = 0; i < rows.length; i++) {
			boolean title = rows[i].getType() == -1;
			m_clData[i][0] = rows[i];
			m_clData[i][1] = title?"":Integer.valueOf(rows[i].getCount());
			m_clData[i][2] = title?"":Integer.valueOf(rows[i].getWin());
			m_clData[i][3] = title?"":Integer.valueOf(rows[i].getDraw());
			m_clData[i][4] = title?"":Integer.valueOf(rows[i].getLoss());
			m_clData[i][5] = title?"":String.valueOf(StringUtilities.getResultString(rows[i].getHomeGoals(),rows[i].getAwayGoals()));
		}
										
	}

    public final void setValues(MatchesOverviewRow[] rows) {
    	this.rows = rows;
        initData();
    }
}
