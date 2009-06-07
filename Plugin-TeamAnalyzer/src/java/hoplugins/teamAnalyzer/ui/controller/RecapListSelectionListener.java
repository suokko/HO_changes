package hoplugins.teamAnalyzer.ui.controller;

import gui.UserParameter;
import hoplugins.Commons;
import hoplugins.commons.utils.HTCalendar;
import hoplugins.commons.utils.HTCalendarFactory;
import hoplugins.teamAnalyzer.SystemManager;
import hoplugins.teamAnalyzer.manager.ReportManager;
import hoplugins.teamAnalyzer.ui.RecapPanel;
import hoplugins.teamAnalyzer.ui.RecapTableSorter;
import hoplugins.teamAnalyzer.ui.model.UiRecapTableModel;
import hoplugins.teamAnalyzer.vo.TeamLineup;

import java.util.Calendar;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Listener for the recap panel.
 *
 * @author draghetto, aik
 */
public class RecapListSelectionListener implements ListSelectionListener {

	private String selectedTacticType = RecapPanel.VALUE_NA;
    private String selectedTacticSkill = RecapPanel.VALUE_NA;
    private RecapTableSorter sorter = null;
    private UiRecapTableModel tableModel = null;

    /**
     * Consructor.
     */
    public RecapListSelectionListener(RecapTableSorter sorter, UiRecapTableModel tableModel) {
    	this.sorter = sorter;
    	this.tableModel = tableModel;
    }

    /**
     * Handle value changed events.
     */
	public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }

        ListSelectionModel lsm = (ListSelectionModel) e.getSource();

        if (!lsm.isSelectionEmpty()) {
            int selectedRow = sorter.modelIndex(lsm.getMinSelectionIndex());
            selectedTacticType = String.valueOf(tableModel.getValueAt(selectedRow, 17));
            selectedTacticSkill = String.valueOf(tableModel.getValueAt(selectedRow, 18));

            if (selectedRow == 0) {
                TeamLineup lineup = ReportManager.getLineup();
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.HOUR, UserParameter.instance().TimeZoneDifference);

                HTCalendar cal = HTCalendarFactory.createTrainingCalendar(Commons.getModel(),
                                                                          calendar.getTime());
                SystemManager.getPlugin().getMainPanel().reload(lineup,
                                                                cal.getHTWeek(),
                                                                cal.getHTSeason());
                SystemManager.getPlugin().getRatingPanel().reload(lineup);
            } else {
                TeamLineup lineup = ReportManager.getLineup(selectedRow);
                int week = Integer.parseInt("" + tableModel.getValueAt(selectedRow, 3));
                int season = Integer.parseInt("" + tableModel.getValueAt(selectedRow, 4));

                SystemManager.getPlugin().getMainPanel().reload(lineup, week, season);
                SystemManager.getPlugin().getRatingPanel().reload(lineup);
            }
        }
    }

	/**
	 * Get the currently selected tactic type as i18ned string.
	 */
	public String getSelectedTacticType() {
		return selectedTacticType;
	}

	/**
	 * Get the skill of the currently selected tactic as i18ned string.
	 */
	public String getSelectedTacticSkill() {
		return selectedTacticSkill;
	}

}
