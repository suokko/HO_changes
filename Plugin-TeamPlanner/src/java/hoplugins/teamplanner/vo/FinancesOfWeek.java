// %2434855926:hoplugins.teamplanner.vo%
package hoplugins.teamplanner.vo;

import hoplugins.Commons;

import hoplugins.commons.utils.HTCalendar;
import hoplugins.commons.utils.HTCalendarFactory;

import java.sql.Timestamp;

import java.util.Date;


/**
 * Missing Class Documentation
 *
 * @author Draghetto
 */
public class FinancesOfWeek implements Comparable {
    //~ Instance fields ----------------------------------------------------------------------------

    /** Missing Parameter Documentation */
    HTCalendar calendar;

    /** Missing Parameter Documentation */
    String currencyName;

    /** Missing Parameter Documentation */
    Timestamp date;

    /** Missing Parameter Documentation */
    float currencyRate;

    /** Missing Parameter Documentation */
    int arenaExpenses;

    /** Missing Parameter Documentation */
    int cash;

    /** Missing Parameter Documentation */
    int expectedProfitOrLoss;

    /** Missing Parameter Documentation */
    int interestExpenses;

    /** Missing Parameter Documentation */
    int interestIncome;

    /** Missing Parameter Documentation */
    int salaries;

    /** Missing Parameter Documentation */
    int spectatorsIncome;

    /** Missing Parameter Documentation */
    int sponsorsIncome;

    /** Missing Parameter Documentation */
    int staffExpenses;

    /** Missing Parameter Documentation */
    int temporaryExpenses;

    /** Missing Parameter Documentation */
    int temporaryIncome;

    /** Missing Parameter Documentation */
    int totalExpenses;

    /** Missing Parameter Documentation */
    int totalIncome;

    /** Missing Parameter Documentation */
    int youthSquadExpenses;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new FinancesOfWeek object.
     */
    public FinancesOfWeek() {
        calendar = null;
        date = null;
        currencyName = Commons.getModel().getXtraDaten().getCurrencyName();
        currencyRate = (new Float(Commons.getModel().getXtraDaten().getCurrencyRate())).floatValue();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     *
     * @param arenaExpenses Missing Method Parameter Documentation
     */
    public void setArenaExpenses(int arenaExpenses) {
        this.arenaExpenses = arenaExpenses;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getArenaExpenses() {
        return Math.round((float) arenaExpenses / currencyRate);
    }

    /**
     * Missing Method Documentation
     *
     * @param cash Missing Method Parameter Documentation
     */
    public void setCash(int cash) {
        this.cash = cash;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getCash() {
        return Math.round((float) cash / currencyRate);
    }

    /**
     * Missing Method Documentation
     *
     * @param date Missing Method Parameter Documentation
     */
    public void setDate(Date date) {
        this.date = new Timestamp(date.getTime());
        calendar = HTCalendarFactory.createEconomyCalendar(Commons.getModel(), date);
    }

    /**
     * Missing Method Documentation
     *
     * @param date Missing Method Parameter Documentation
     */
    public void setDate(Timestamp date) {
        this.date = date;
        calendar = HTCalendarFactory.createEconomyCalendar(Commons.getModel(), date);
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public Date getDate() {
        return calendar.getTime();
    }

    /**
     * Missing Method Documentation
     *
     * @param expectedProfitOrLoss Missing Method Parameter Documentation
     */
    public void setExpectedProfitOrLoss(int expectedProfitOrLoss) {
        this.expectedProfitOrLoss = expectedProfitOrLoss;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getExpectedProfitOrLoss() {
        return Math.round((float) expectedProfitOrLoss / currencyRate);
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getHTSeason() {
        return calendar.getHTSeason();
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getHTWeek() {
        return calendar.getHTWeek();
    }

    /**
     * Missing Method Documentation
     *
     * @param interestExpenses Missing Method Parameter Documentation
     */
    public void setInterestExpenses(int interestExpenses) {
        this.interestExpenses = interestExpenses;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getInterestExpenses() {
        return Math.round((float) interestExpenses / currencyRate);
    }

    /**
     * Missing Method Documentation
     *
     * @param interestIncome Missing Method Parameter Documentation
     */
    public void setInterestIncome(int interestIncome) {
        this.interestIncome = interestIncome;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getInterestIncome() {
        return Math.round((float) interestIncome / currencyRate);
    }

    /**
     * Missing Method Documentation
     *
     * @param salaries Missing Method Parameter Documentation
     */
    public void setSalaries(int salaries) {
        this.salaries = salaries;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getSalaries() {
        return Math.round((float) salaries / currencyRate);
    }

    /**
     * Missing Method Documentation
     *
     * @param spectatorsIncome Missing Method Parameter Documentation
     */
    public void setSpectatorsIncome(int spectatorsIncome) {
        this.spectatorsIncome = spectatorsIncome;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getSpectatorsIncome() {
        return Math.round((float) spectatorsIncome / currencyRate);
    }

    /**
     * Missing Method Documentation
     *
     * @param sponsorsIncome Missing Method Parameter Documentation
     */
    public void setSponsorsIncome(int sponsorsIncome) {
        this.sponsorsIncome = sponsorsIncome;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getSponsorsIncome() {
        return Math.round((float) sponsorsIncome / currencyRate);
    }

    /**
     * Missing Method Documentation
     *
     * @param staffExpenses Missing Method Parameter Documentation
     */
    public void setStaffExpenses(int staffExpenses) {
        this.staffExpenses = staffExpenses;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getStaffExpenses() {
        return Math.round((float) staffExpenses / currencyRate);
    }

    /**
     * Missing Method Documentation
     *
     * @param temporaryExpenses Missing Method Parameter Documentation
     */
    public void setTemporaryExpenses(int temporaryExpenses) {
        this.temporaryExpenses = temporaryExpenses;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getTemporaryExpenses() {
        return Math.round((float) temporaryExpenses / currencyRate);
    }

    /**
     * Missing Method Documentation
     *
     * @param temporaryIncome Missing Method Parameter Documentation
     */
    public void setTemporaryIncome(int temporaryIncome) {
        this.temporaryIncome = temporaryIncome;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getTemporaryIncome() {
        return Math.round((float) temporaryIncome / currencyRate);
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public Timestamp getTimestamp() {
        return date;
    }

    /**
     * Missing Method Documentation
     *
     * @param totalExpenses Missing Method Parameter Documentation
     */
    public void setTotalExpenses(int totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getTotalExpenses() {
        return Math.round((float) totalExpenses / currencyRate);
    }

    /**
     * Missing Method Documentation
     *
     * @param totalIncome Missing Method Parameter Documentation
     */
    public void setTotalIncome(int totalIncome) {
        this.totalIncome = totalIncome;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getTotalIncome() {
        return Math.round((float) totalIncome / currencyRate);
    }

    /**
     * Missing Method Documentation
     *
     * @param youthSquadExpenses Missing Method Parameter Documentation
     */
    public void setYouthSquadExpenses(int youthSquadExpenses) {
        this.youthSquadExpenses = youthSquadExpenses;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getYouthSquadExpenses() {
        return Math.round((float) youthSquadExpenses / currencyRate);
    }

    /**
     * Missing Method Documentation
     *
     * @param obj Missing Method Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int compareTo(Object obj) {
        Date day = ((FinancesOfWeek) obj).calendar.getTime();

        return day.compareTo(calendar.getTime());
    }
}
