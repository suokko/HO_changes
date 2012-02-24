package ho.module.ifa;

import ho.core.db.DBManager;
import ho.core.model.WorldDetailLeague;
import ho.core.model.WorldDetailsManager;
import ho.core.util.HelperWrapper;

import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class StatisticScrollPanel extends JScrollPane {
	private JTable table;
	private SortedTableModel model;
	private String[] columnNames;
	private boolean homeAway;

	public StatisticScrollPanel(boolean homeAway) {
		this.homeAway = homeAway;
		initialize();
	}

	public void initialize() {
		this.columnNames = new String[] { "Country", "Matches played", "Won",
				"Draw", "Lost", "Last Match" };
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
		Vector vector = DBManager.instance().getIfaMatches(! homeAway);

		if (vector.size() < 1) {
			return new Object[0][0];
		}
		Vector newVector = new Vector();
		Object[] obj1 = (Object[]) null;
		int counter = 0;
		int won = 0;
		int draw = 0;
		int lost = 0;

		for (int i = 0; i < vector.size(); i++) {
			Object[] obj2 = (Object[]) vector.get(i);
			if ((obj1 != null)
					&& (!obj1[0].toString().equals(obj2[0].toString()))) {
				Object[] obj = new Object[6];
				obj[0] = obj1[0].toString();
				obj[1] = counter;
				obj[2] = won;
				obj[3] = draw;
				obj[4] = lost;
				obj[5] = DateHelper.getDateString(DateHelper.getDate(obj1[1]
						.toString()));
				newVector.add(obj);
				counter = 0;
				won = 0;
				draw = 0;
				lost = 0;
			}
			counter++;
			int homegoals = Integer.parseInt(obj2[2].toString());
			int awaygoals = Integer.parseInt(obj2[3].toString());
			if (homegoals > awaygoals) {
				if (this.homeAway)
					lost++;
				else
					won++;
			} else if (homegoals == awaygoals)
				draw++;
			else if (homegoals < awaygoals)
				if (this.homeAway)
					won++;
				else
					lost++;
			obj1 = obj2;
		}
		Object[] obj = new Object[6];
		obj[0] = obj1[0].toString();
		obj[1] = counter;
		obj[2] = won;
		obj[3] = draw;
		obj[4] = lost;
		obj[5] = DateHelper
				.getDateString(DateHelper.getDate(obj1[1].toString()));
		newVector.add(obj);

		Object[][] objects = new Object[newVector.size()][6];
		for (int i = 0; i < objects.length; i++) {
			Object[] vecObj = (Object[]) newVector.get(i);
			int leagueID = Integer.parseInt(vecObj[0].toString());
			WorldDetailLeague league = WorldDetailsManager.instance().getWorldDetailLeagueByLeagueId(leagueID);
			JLabel label = new JLabel(league.getCountryName());
			label.setIcon(HelperWrapper.instance().getImageIcon4Country(league.getCountryId()));
			objects[i][0] = label;
			objects[i][1] = vecObj[1];
			objects[i][2] = vecObj[2];
			objects[i][3] = vecObj[3];
			objects[i][4] = vecObj[4];
			objects[i][5] = vecObj[5];
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
