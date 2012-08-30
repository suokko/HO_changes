package ho.module.teamAnalyzer.ui.controller;

import ho.core.model.UserParameter;
import ho.core.util.HOLogger;
import ho.core.util.HTCalendarFactory;
import ho.module.teamAnalyzer.SystemManager;
import ho.module.teamAnalyzer.manager.ReportManager;
import ho.module.teamAnalyzer.ui.RecapPanel;
import ho.module.teamAnalyzer.ui.RecapTableSorter;
import ho.module.teamAnalyzer.ui.model.UiRecapTableModel;
import ho.module.teamAnalyzer.vo.TeamLineup;

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

                int week = HTCalendarFactory.getHTWeek(calendar.getTime());
                int season =HTCalendarFactory.getHTSeason(calendar.getTime());
                if (lineup != null)
                HOLogger.instance().log(getClass(), lineup.toString() + " time: " + season + " " + week);
                SystemManager.getPlugin().getMainPanel().reload(lineup, week, season);
                SystemManager.getPlugin().getRatingPanel().reload(lineup);
            } else {
                TeamLineup lineup = ReportManager.getLineup(selectedRow);
                int week = Integer.parseInt("" + tableModel.getValueAt(selectedRow, 3));
                int season = Integer.parseInt("" + tableModel.getValueAt(selectedRow, 4));
                if (lineup != null)
                HOLogger.instance().log(getClass(), lineup.toString() + " time: " + season + " " + week);
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
