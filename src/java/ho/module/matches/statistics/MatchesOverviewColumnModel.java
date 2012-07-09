package ho.module.matches.statistics;

import ho.core.gui.comp.table.HOTableModel;
import ho.core.model.match.MatchesOverviewRow;
import ho.core.util.StringUtils;


public final class MatchesOverviewColumnModel extends HOTableModel {
		
	private static final long serialVersionUID = 1L;
	private MatchesOverviewRow[] rows;
	public MatchesOverviewColumnModel(int id){
		super(id,"MatchesStatistics");
		initialize();
	}
	
	
	private void initialize() {
		columns = createMatchesStatisticsArray();
	
	}
	
	private MatchesOverviewColumn[] createMatchesStatisticsArray(){
		MatchesOverviewColumn[] columns = new MatchesOverviewColumn[6];
		columns[0] = new MatchesOverviewColumn(701, " "," ",50);
		columns[1] = new MatchesOverviewColumn(702, "Spiele","Spiele",100);
		columns[2] = new MatchesOverviewColumn(703, "SerieAuswaertsSieg","SerieAuswaertsSieg",50);
		columns[3] = new MatchesOverviewColumn(704, "SerieAuswaertsUnendschieden","SerieAuswaertsUnendschieden",50);
		columns[4] = new MatchesOverviewColumn(706, "SerieAuswaertsNiederlage","SerieAuswaertsNiederlage",50);
		columns[5] = new MatchesOverviewColumn(707, "Tore","Tore",50);
		return columns;
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
			m_clData[i][5] = title?"":String.valueOf(StringUtils.getResultString(rows[i].getHomeGoals(),rows[i].getAwayGoals()));
		}
										
	}

    public final void setValues(MatchesOverviewRow[] rows) {
    	this.rows = rows;
        initData();
    }
}
