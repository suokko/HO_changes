package hoplugins.pluginIFA;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateHelper {
	private static SimpleDateFormat dateFormatter = new SimpleDateFormat();

	public static Date getDate(String input) {
		Date date = null;
		if (input.length() == 6) {
			int day = Integer.parseInt(input.substring(0, 2));
			int month = Integer.parseInt(input.substring(2, 4));
			int year = Integer.parseInt(input.substring(4, 6));

			Calendar calendar = Calendar.getInstance();
			year = year > calendar.get(1) - 2000 ? year + 1900 : year + 2000;

			calendar.set(1, year);
			calendar.set(5, day);
			calendar.set(2, month - 1);

			date = calendar.getTime();
		} else {
			int day = Integer.parseInt(input.substring(8, 10));
			int month = Integer.parseInt(input.substring(5, 7));
			int year = Integer.parseInt(input.substring(0, 4));

			Calendar calendar = Calendar.getInstance();

			calendar.set(1, year);
			calendar.set(5, day);
			calendar.set(2, month - 1);

			date = calendar.getTime();
		}
		return date;
	}

	public static String getDateString(Date date) {
		return convert(date, "2003-01-01", "yyyy-MM-dd");
	}

	public static String displayDateString(Date date) {
		return convert(date, "01.01.2003", "dd.MM.yyyy");
	}

	public static String getTimestampString(Date date) {
		return convert(date, "2003-01-01 00:00:00", "yyyy-MM-dd HH:mm:ss");
	}

	public static String getTimeString(Date date) {
		return convert(date, "00:00", "HH:mm");
	}

	private static String convert(Date date, String defaultValue, String pattern) {
		if (date == null)
			return defaultValue;
		synchronized (dateFormatter) {
			dateFormatter.applyPattern(pattern);
			return dateFormatter.format(date);
		}
	}

	public static String getTodayDateString() {
		Calendar calendar = Calendar.getInstance();
		return getDateString(calendar.getTime());
	}
}
