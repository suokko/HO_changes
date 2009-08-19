// %3796493357:hoplugins.teamplanner.ui.model%
package hoplugins.teamplanner.ui.model;

import hoplugins.Commons;

import hoplugins.commons.utils.PluginProperty;

import hoplugins.teamplanner.vo.FinancesOfWeek;
import hoplugins.teamplanner.vo.HTWeek;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;


/**
 * Missing Class Documentation
 *
 * @author Draghetto
 */
public class FinancesTableModel extends AbstractTableModel {
    //~ Instance fields ----------------------------------------------------------------------------

	private static final long serialVersionUID = 7060311989341266730L;
	private Vector<String> colNames;
    private Vector<FinancesOfWeek> values;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new FinancesTableModel object.
     *
     * @param values Missing Constructuor Parameter Documentation
     */
    public FinancesTableModel(Vector<FinancesOfWeek> values) {
        this.values = null;
        colNames = new Vector<String>();
        this.values = values;
        colNames.add(Commons.getModel().getLanguageString("Datum"));
        colNames.add(Commons.getModel().getLanguageString("Season") + " / "
                     + PluginProperty.getString("Week"));
        colNames.add(Commons.getModel().getLanguageString("Kontostand"));
        colNames.add(Commons.getModel().getLanguageString("Zuschauer"));
        colNames.add(Commons.getModel().getLanguageString("Sponsoren"));
        colNames.add(Commons.getModel().getLanguageString("Zinsertraege"));
        colNames.add(Commons.getModel().getLanguageString("Sonstiges"));
        colNames.add(Commons.getModel().getLanguageString("Gesamteinnahmen"));
        colNames.add(Commons.getModel().getLanguageString("Stadion"));
        colNames.add(Commons.getModel().getLanguageString("Spielergehaelter"));
        colNames.add(Commons.getModel().getLanguageString("Zinsaufwendungen"));
        colNames.add(Commons.getModel().getLanguageString("Sonstiges"));
        colNames.add(Commons.getModel().getLanguageString("Trainerstab"));
        colNames.add(Commons.getModel().getLanguageString("Jugend"));
        colNames.add(Commons.getModel().getLanguageString("Gesamtausgaben"));
        colNames.add(Commons.getModel().getLanguageString("ErwarteterGewinnVerlust"));
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     *
     * @param row Missing Method Parameter Documentation
     * @param col Missing Method Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    @Override
	public boolean isCellEditable(int row, int col) {
        return false;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getColumnCount() {
        return colNames.size();
    }

    /**
     * Missing Method Documentation
     *
     * @param col Missing Method Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    @Override
	public String getColumnName(int col) {
        return (String) colNames.get(col);
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getRowCount() {
        return values.size();
    }

    /**
     * Missing Method Documentation
     *
     * @param row Missing Method Parameter Documentation
     * @param col Missing Method Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    public Object getValueAt(int row, int col) {
        FinancesOfWeek rowData = (FinancesOfWeek) values.get(row);

        switch (col) {
            case 0: // '\0'
                return rowData.getTimestamp();

            case 1: // '\001'
            	Integer season = Commons.getModel().getHelper().getHTSeason(rowData.getDate());
            	Integer week = Commons.getModel().getHelper().getHTWeek(rowData.getDate());

                return new HTWeek(season,week);

            case 2: // '\002'
                return new Integer(rowData.getCash());

            case 3: // '\003'
                return new Integer(rowData.getSpectatorsIncome());

            case 4: // '\004'
                return new Integer(rowData.getSponsorsIncome());

            case 5: // '\005'
                return new Integer(rowData.getInterestIncome());

            case 6: // '\006'
                return new Integer(rowData.getTemporaryIncome());

            case 7: // '\007'
                return new Integer(rowData.getTotalIncome());

            case 8: // '\b'
                return new Integer(rowData.getArenaExpenses());

            case 9: // '\t'
                return new Integer(rowData.getSalaries());

            case 10: // '\n'
                return new Integer(rowData.getInterestExpenses());

            case 11: // '\013'
                return new Integer(rowData.getTemporaryExpenses());

            case 12: // '\f'
                return new Integer(rowData.getStaffExpenses());

            case 13: // '\r'
                return new Integer(rowData.getYouthSquadExpenses());

            case 14: // '\016'
                return new Integer(rowData.getTotalExpenses());

            case 15: // '\017'
                return new Integer(rowData.getExpectedProfitOrLoss());
        }

        return "";
    }

    /**
     * Missing Method Documentation
     *
     * @param values Missing Method Parameter Documentation
     */
    public void refresh(Vector<FinancesOfWeek> values) {
        this.values = values;
        fireTableDataChanged();
    }
}
