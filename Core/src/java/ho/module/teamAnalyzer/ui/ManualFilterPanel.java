// %2316364359:hoplugins.teamAnalyzer.ui%
/*
 * ManualFilterPanel.java
 *
 * Created on 20 settembre 2004, 16.13
 */
package ho.module.teamAnalyzer.ui;

import ho.core.db.DBManager;
import ho.core.gui.HOMainFrame;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.HOVerwaltung;
import ho.core.model.match.MatchType;
import ho.module.teamAnalyzer.ht.HattrickManager;
import ho.module.teamAnalyzer.manager.MatchManager;
import ho.module.teamAnalyzer.ui.model.UiFilterTableModel;
import ho.module.teamAnalyzer.vo.Match;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;


/**
 * 
 * @author Draghetto
 */
public class ManualFilterPanel extends JPanel {

	private static final long serialVersionUID = -9029680013941604647L;

	private static final Vector<String> COLUMN_NAMES = new Vector<String>(Arrays.asList(new String[] { "",
			HOVerwaltung.instance().getLanguageString("RecapPanel.Game"), //
			HOVerwaltung.instance().getLanguageString("Type"), //
			HOVerwaltung.instance().getLanguageString("Ergebnis"), //
			HOVerwaltung.instance().getLanguageString("Week"), // 
			HOVerwaltung.instance().getLanguageString("Season"), "", "" }));

	List<Match> availableMatches = new ArrayList<Match>();
	private DefaultTableModel tableModel;
	private JTable table;

	/**
	 * Creates a new instance of ManualFilterPanel
	 */
	public ManualFilterPanel() {
		jbInit();
	}

	/**
	 * Re-init the UI components.
	 */
	public void reload() {
		tableModel = new UiFilterTableModel(new Vector<Object>(), COLUMN_NAMES);
		table.setModel(tableModel);
		availableMatches = MatchManager.getAllMatches();

		Vector<Object> rowData;

		for (Iterator<Match> iter = availableMatches.iterator(); iter.hasNext();) {
			List<Object> matchIds = new ArrayList<Object>();
			Match element = iter.next();

			rowData = new Vector<Object>();

			boolean isAvailable = DBManager.instance().isMatchVorhanden(element.getMatchId());
			boolean isSelected = TeamAnalyzerPanel.filter.getMatches().contains("" + element.getMatchId());

			rowData.add(Boolean.valueOf(isSelected));

			if (element.isHome()) {
				rowData.add(element.getAwayTeam());
				rowData.add(ThemeManager.getIcon(HOIconName.MATCHTYPES[element.getMatchType().getIconArrayIndex()]));
				rowData.add(element.getHomeGoals() + " - " + element.getAwayGoals());
			} else {
				rowData.add("*" + element.getHomeTeam());
				rowData.add(ThemeManager.getIcon(HOIconName.MATCHTYPES[element.getMatchType().getIconArrayIndex()]));
				rowData.add(element.getAwayGoals() + " - " + element.getHomeGoals());
			}

			rowData.add(element.getWeek() + "");
			rowData.add(element.getSeason() + "");

			if ((HattrickManager.isDownloadAllowed(element)) || isAvailable) {
				rowData.add("true");
				matchIds.add("");
			} else {
				rowData.add("false");
				matchIds.add(element);
			}

			rowData.add("" + element.getMatchType().getId());
			tableModel.addRow(rowData);
		}

		addTableListener();

		table.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(20);
		table.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getTableHeader().getColumnModel().getColumn(1).setPreferredWidth(90);
		table.getTableHeader().getColumnModel().getColumn(2).setPreferredWidth(20);
		table.getTableHeader().getColumnModel().getColumn(3).setPreferredWidth(40);
		table.getTableHeader().getColumnModel().getColumn(4).setPreferredWidth(40);
		table.getTableHeader().getColumnModel().getColumn(5).setPreferredWidth(40);
		table.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(0);
		table.getTableHeader().getColumnModel().getColumn(6).setMinWidth(0);
		table.getTableHeader().getColumnModel().getColumn(6).setPreferredWidth(0);
		table.getTableHeader().getColumnModel().getColumn(6).setWidth(0);
		table.getTableHeader().getColumnModel().getColumn(7).setMaxWidth(0);
		table.getTableHeader().getColumnModel().getColumn(7).setMinWidth(0);
		table.getTableHeader().getColumnModel().getColumn(7).setPreferredWidth(0);
		table.getTableHeader().getColumnModel().getColumn(7).setWidth(0);
	}

	/**
	 * Set a match filter.
	 */
	protected void setFilter() {
		List<String> list = new ArrayList<String>();
		int i = 0;

		for (Iterator<Match> iter = availableMatches.iterator(); iter.hasNext();) {
			Match element = iter.next();
			boolean isSelected = ((Boolean) tableModel.getValueAt(i, 0)).booleanValue();
			boolean isAvailable = Boolean.valueOf((String) tableModel.getValueAt(i, 6)).booleanValue();

			if (isSelected && isAvailable) {
				list.add("" + element.getMatchId());
			}

			i++;
		}

		TeamAnalyzerPanel.filter.setMatches(list);
	}

	/**
	 * TODO Missing Method Documentation
	 */
	private void addTableListener() {
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getClickCount() == 2) {
					boolean downloadDone = false;
					int row = table.getSelectedRow();
					String status = (String) tableModel.getValueAt(row, 6);

					if (!status.equalsIgnoreCase("true")) {
						int id = availableMatches.get(row).getMatchId();
						MatchType type = availableMatches.get(row).getMatchType();
						downloadDone = HOMainFrame.instance().getOnlineWorker().downloadMatchData(id, type, false);
					}

					e.consume();

					if (downloadDone) {
						tableModel.setValueAt("true", row, 6);
					}

					updateUI();
				}
			}
		});
	}

	/**
	 * TODO Missing Method Documentation
	 */
	private void jbInit() {
		JPanel main = new ImagePanel();

		main.setLayout(new BorderLayout());
		setLayout(new BorderLayout());
		setOpaque(false);

		Vector<Object> data = new Vector<Object>();

		tableModel = new UiFilterTableModel(data, COLUMN_NAMES);
		table = new JTable(tableModel);
		table.setDefaultRenderer(Object.class, new ManualFilterTableRenderer());

		JScrollPane pane = new JScrollPane(table);

		main.add(pane, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane(main);

		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		add(scrollPane);
	}
}
