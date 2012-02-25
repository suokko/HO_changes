package ho.module.ifa;

import ho.core.db.DBManager;
import ho.core.model.HOVerwaltung;
import ho.core.model.WorldDetailsManager;
import ho.core.util.HelperWrapper;
import ho.core.util.StringUtilities;

import java.util.HashMap;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class StatisticScrollPanel extends JScrollPane {
	private static final long serialVersionUID = 6444247701541392477L;
	private JTable table;
	private SortedTableModel model;
	private String[] columnNames;
	private boolean home;

	public StatisticScrollPanel(boolean home) {
		this.home = home;
		initialize();
	}

	public void initialize() {
		this.columnNames = new String[] { HOVerwaltung.instance().getLanguageString("Country"), HOVerwaltung.instance().getLanguageString("tooltip.MatchCount"), HOVerwaltung.instance().getLanguageString("Gewonnen"),
		HOVerwaltung.instance().getLanguageString("Unendschieden"), HOVerwaltung.instance().getLanguageString("Verloren"),HOVerwaltung.instance().getLanguageString("Tore"),HOVerwaltung.instance().getLanguageString("Coolness"), HOVerwaltung.instance().getLanguageString("LastMatch") };
		try {
			this.model = new SortedTableModel(new DefaultTableModel(
					getTableData(), this.columnNames));
			this.model.setAlfa(true);
			this.model.sort(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.table = new JTable(this.model);
		this.table.setDefaultRenderer(Object.class,
				new StatisticTableCellRenderer());
		for (int i = 0; i < this.columnNames.length; i++) {
			this.table.getColumn(this.columnNames[i]).setHeaderRenderer(
					new RendererDecorator());
		}
		this.table.getTableHeader().addMouseListener(
				new RendererSorter(this.table, this.model, 0));
		setViewportView(this.table);
	}

	private Object[][] getTableData() {
		IfaMatch[] matches = DBManager.instance().getIfaMatches( home);
		if (matches.length == 0) 
			return new Object[0][0];

		HashMap<Integer,FriendlyStatistik> map = new HashMap<Integer,FriendlyStatistik>();
		
		for (int i = 0; i < matches.length; i++) {
			Integer leagueId = Integer.valueOf(home?matches[i].getAwayLeagueId():matches[i].getHomeLeagueId());
			FriendlyStatistik tmpStat = map.get(leagueId);
			
			if(tmpStat == null) {
				tmpStat = new FriendlyStatistik( WorldDetailsManager.instance().getWorldDetailLeagueByLeagueId(leagueId));
				map.put(leagueId, tmpStat);
			}
			tmpStat.add(matches[i]);
		}
		
		Object[][] objects = new Object[map.size()][8];
		Set<Integer> keys = map.keySet();
		int counter = 0;
		for (Integer key : keys) {
			FriendlyStatistik stat = map.get(key);
			JLabel label = new JLabel(stat.getLeague().getCountryName());
			label.setIcon(HelperWrapper.instance().getImageIcon4Country(stat.getLeague().getCountryId()));
			objects[counter][0] = 	stat.getLeague();
			objects[counter][1] =	Integer.valueOf(stat.getTotal());
			objects[counter][2] =	Integer.valueOf(home?stat.getHomeWon():stat.getHomeLost());
			objects[counter][3] = 	Integer.valueOf(stat.getDraw());
			objects[counter][4] = 	Integer.valueOf(home?stat.getHomeLost():stat.getHomeWon());
			objects[counter][5] =  StringUtilities.getResultString(stat.getHomeGoals(),stat.getAwayGoals());
			objects[counter][6] =  Integer.valueOf(stat.getLeague().getActiveUsers());
			objects[counter][7] =  stat.getLastPlayedDate();
			counter++;
		}

		return objects;
	}

	public void refresh() {
		try {
			this.model = new SortedTableModel(new DefaultTableModel(
					getTableData(), this.columnNames));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.table = new JTable(this.model);
		this.table.setDefaultRenderer(Object.class,
				new StatisticTableCellRenderer());
		for (int i = 0; i < this.columnNames.length; i++) {
			this.table.getColumn(this.columnNames[i]).setHeaderRenderer(
					new RendererDecorator());
		}
		this.table.getTableHeader().addMouseListener(
				new RendererSorter(this.table, this.model, 0));
		setViewportView(this.table);
	}
}
