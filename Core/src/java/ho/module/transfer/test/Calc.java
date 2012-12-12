package ho.module.transfer.test;

import ho.core.db.DBManager;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Calc {

	public static void main(String[] args) {
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:s");
		try {
			// 26.05. 02.06. 09.06. 16.03. 23.06. 30.06 07.07. 14.07. 21.07.
			// 28.07. 04.08. 11.08. 18.08.

			Date buyingDate = df.parse("25.05.2012 17:00:12");
			Date sellingDate = df.parse("09.06.2012 10:30:45");
			WeekDayTime time = new WeekDayTime(Calendar.SATURDAY, 0, 0, 1);

			Date lastEconomyDateInPeriod = lastUpdateBefore(Calendar.SATURDAY, time, sellingDate);
			System.out.println("buyingDate: " + buyingDate);
			System.out.println("sellingDate: " + sellingDate);
			System.out.println("lastEconomyDateInPeriod: " + lastEconomyDateInPeriod);
			System.out.println("updates: " + getUpdates(buyingDate, lastEconomyDateInPeriod));

			// 18 Jahre und 28 Tage, nächster Geburtstag: 23.10.2012

			int age = 18 * 112 + 28;
			getWages(age, buyingDate, sellingDate, time);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
	}

	public static List<Birthday> getBirthdays(int playerId, int from, int to) {
		List<Birthday> list = new ArrayList<Birthday>();

		Date birthday17 = get17thBirthday(playerId);
		
		if (from < 17) {
			from = 17;
		}
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(birthday17);
		int offset = to-17;
		for (int i=from; i<=to; i++) {
			cal.add(GregorianCalendar.DAY_OF_MONTH, offset*112);
			list.add(new Birthday(i, cal.getTime()));
			offset++;
		}
		
		return list;
	}

	public static Date get17thBirthday(int playerId) {
		String query = "SELECT LIMIT 0 1 AGE, AGEDAYS, DATUM FROM spieler WHERE spielerid="
				+ playerId;
		ResultSet rs = DBManager.instance().getAdapter().executeQuery(query);
		try {
			if (rs.next()) {
				Date rsDate = new Date(rs.getTimestamp("DATUM").getTime());
				int yearsDiffTo17 = rs.getInt("AGE") - 17;
				int daysSince17 = yearsDiffTo17 * 112 + rs.getInt("AGEDAYS");

				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(rsDate);
				cal.add(GregorianCalendar.DAY_OF_MONTH, -daysSince17);
				return cal.getTime();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 
	 * @param age
	 *            total age in days (note that a HT-year has 112 days)
	 * @param buyingDate
	 * @param sellingDate
	 * @param economyUpdate
	 */
	private static Map<Integer, List<Date>> getWages(int age, Date buyingDate, Date sellingDate,
			WeekDayTime economyUpdate) {
		Map<Integer, List<Date>> wages = new HashMap<Integer, List<Date>>();
		int totalDaysInPeriod = getDaysBetween(sellingDate, buyingDate);
		System.out.println("totalDaysInPeriod: " + totalDaysInPeriod);

		int years = age / 112;
		int days = age % 112;
		System.out.println(years + ", " + days);

		int daysToNextBirthday = 112 - days;
		System.out.println("daysToNextBirthday " + daysToNextBirthday);

		Date firstDate = nextUpdateAfter(economyUpdate, buyingDate);
		if (totalDaysInPeriod < daysToNextBirthday) {
			System.out.println("nextUpdateAfter " + buyingDate + " - "
					+ nextUpdateAfter(economyUpdate, buyingDate));
		}

		return wages;
	}

	private static int getUpdates(Date buyingDate, Date lastEconomyDateInPeriod) {
		long millis = lastEconomyDateInPeriod.getTime() - buyingDate.getTime();
		long updates = millis / 1000 / 60 / 60 / 24 / 7;
		updates += 1; // add one for lastEconomyDateInPeriod itself
		return (int) updates;
	}

	private static Date nextUpdateAfter(WeekDayTime time, Date date) {
		// Bsp.: Sa. 01:00
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);

		// set the weekday to the day when the updates happens
		cal.set(Calendar.DAY_OF_WEEK, time.getDay());
		cal.set(Calendar.HOUR_OF_DAY, time.getHour());
		cal.set(Calendar.MINUTE, time.getMinute());
		cal.set(Calendar.SECOND, time.getSecond());
		cal.set(Calendar.MILLISECOND, 0);

		// if the day where the update happens is before the given date,
		// take the update next week
		if (date.getTime() > cal.getTimeInMillis()) {
			cal.add(Calendar.DAY_OF_WEEK, 7);
		}

		return cal.getTime();
	}

	private static Date lastUpdateBefore(int dayOfWeek, WeekDayTime time, Date date) {
		// Bsp.: Sa. 01:00
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);

		// set the weekday to the day when the updates happens
		cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
		cal.set(Calendar.HOUR_OF_DAY, time.getHour());
		cal.set(Calendar.MINUTE, time.getMinute());
		cal.set(Calendar.SECOND, time.getSecond());
		cal.set(Calendar.MILLISECOND, 0);

		// if the day where the update happens is past the given date,
		// take the update before (one week back)
		if (date.getTime() < cal.getTimeInMillis()) {
			cal.add(Calendar.DAY_OF_WEEK, -7);
		}

		return cal.getTime();
	}

	/**
	 * Gets the difference between <code>date1</code> and <code>date2</code> in
	 * days (date1 - date2).
	 * <p/>
	 * Note that the the time is not taken into account, so this method will
	 * return <code>3</code> for <code>date1</code> <i>May 21 00:00:00 2009</i>
	 * and <code>date2</code> <i>May 18 23:59:59 2009</i> although there are 48
	 * hours and 1 second between the two dates.
	 * <p/>
	 * If <code>date1</code> is before </code>date2</code> a negative number of
	 * days will be returned.
	 * 
	 * @param date1
	 *            the first date.
	 * @param date2
	 *            the second date.
	 * @return the difference between date1 and date2 in days (date1 - date2).
	 * @throws NullPointerException
	 *             if one (or both) of the given dates is <code>null</code>.
	 */
	public static int getDaysBetween(Date date1, Date date2) {
		// comparing the timestamp is fast and avoids unnecessary object
		// creation
		// if both dates are the same
		if (date1.getTime() == date2.getTime()) {
			return 0;
		}

		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date1);
		setMinTime(cal);
		BigDecimal date1Millis = new BigDecimal(cal.getTimeInMillis());

		cal.setTime(date2);
		setMinTime(cal);
		BigDecimal date2Millis = new BigDecimal(cal.getTimeInMillis());

		return date1Millis.subtract(date2Millis)
				.divide(new BigDecimal(86400000), BigDecimal.ROUND_HALF_EVEN).intValue();
	}

	/**
	 * Sets the calendar's time values to their minimum. This will be the very
	 * first millisecond of a day (00:00:00.000).
	 * 
	 * @param cal
	 *            The calendar to zero the time.
	 */
	private static void setMinTime(Calendar cal) {
		cal.set(GregorianCalendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
		cal.set(GregorianCalendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
		cal.set(GregorianCalendar.SECOND, cal.getMinimum(Calendar.SECOND));
		cal.set(GregorianCalendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
	}

	public static int getAgeAt(Date date, int playerId) {
		String query = "SELECT LIMIT 0 1 AGE, AGEDAYS, DATUM FROM spieler WHERE spielerid="
				+ playerId;
		ResultSet rs = DBManager.instance().getAdapter().executeQuery(query);
		try {
			if (rs.next()) {
				int age = rs.getInt("AGE") * 112 + rs.getInt("AGEDAYS");
				Date rsDate = new Date(rs.getTimestamp("DATUM").getTime());
				return Calc.getDaysBetween(date, rsDate) + age;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}
}
