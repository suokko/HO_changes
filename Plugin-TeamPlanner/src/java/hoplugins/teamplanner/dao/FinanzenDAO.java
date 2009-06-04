// %3897700372:hoplugins.teamplanner.dao%
package hoplugins.teamplanner.dao;

import hoplugins.Commons;

import hoplugins.commons.ui.DebugWindow;
import hoplugins.commons.utils.HTCalendar;
import hoplugins.commons.utils.HTCalendarFactory;

import hoplugins.teamplanner.vo.FinancesOfWeek;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;


/**
 * Missing Class Documentation
 *
 * @author Draghetto
 */
public class FinanzenDAO {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new FinanzenDAO object.
     */
    private FinanzenDAO() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     *
     * @param weeks Missing Method Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    public static Vector getFinancesHistory(int weeks) {
        Date firstDay;
        HashMap weekMap;
        ResultSet rs;
        //Date lastDay = new Date(Commons.getModel().getBasics().getDatum().getTime());
        long diff = 0x240c8400L * (long) weeks;

        firstDay = new Date(Commons.getModel().getXtraDaten().getEconomyDate().getTime() - diff);
        weekMap = new HashMap();

        String query = "select * from FINANZEN";

        rs = Commons.getModel().getAdapter().executeQuery(query);

        try {
            do {
                if (rs.isLast()) {
                    break;
                }

                rs.next();

                FinancesOfWeek rowData = new FinancesOfWeek();

                rowData.setDate(rs.getTimestamp("DATUM"));

                if (!rowData.getDate().after(firstDay)) {
                    continue;
                }

                HTCalendar cal = HTCalendarFactory.createEconomyCalendar(Commons.getModel(),
                                                                         rowData.getDate());
                int season = cal.getHTSeason();
                int week = cal.getHTWeek() - 1;

                if (week == 0) {
                    season--;
                    week = 16;
                }

                String key = season + "." + week;

                if (weekMap.containsKey(key)) {
                    FinancesOfWeek existingData = (FinancesOfWeek) weekMap.get(key);

                    if (rowData.getDate().after(existingData.getDate())) {
                        continue;
                    }
                }

                rowData.setCash(rs.getInt("FINANZEN"));
                rowData.setSpectatorsIncome(rs.getInt("LETZTEEINZUSCHAUER"));
                rowData.setSponsorsIncome(rs.getInt("LETZTEEINSPONSOREN"));
                rowData.setInterestIncome(rs.getInt("LETZTEEINZINSEN"));
                rowData.setTemporaryIncome(rs.getInt("LETZTEEINSONSTIGES"));
                rowData.setTotalIncome(rs.getInt("LETZTEEINGESAMT"));
                rowData.setArenaExpenses(rs.getInt("LETZTEKOSTSTADION"));
                rowData.setSalaries(rs.getInt("LETZTEKOSTSPIELER"));
                rowData.setInterestExpenses(rs.getInt("LETZTEKOSTZINSEN"));
                rowData.setTemporaryExpenses(rs.getInt("LETZTEKOSTSONSTIGES"));
                rowData.setStaffExpenses(rs.getInt("LETZTEKOSTTRAINER"));
                rowData.setYouthSquadExpenses(rs.getInt("LETZTEKOSTJUGEND"));
                rowData.setTotalExpenses(rs.getInt("LETZTEKOSTGESAMT"));
                rowData.setExpectedProfitOrLoss(rs.getInt("LETZTEGEWINNVERLUST"));
                weekMap.put(key, rowData);
            } while (true);
        } catch (SQLException e) {
            DebugWindow.debug(e);
        }

        try {
            rs.close();
        } catch (Exception e) {
            // Ignore.
        }

        Vector values = new Vector(weekMap.values());

        Collections.sort(values);

        return values;
    }
}
