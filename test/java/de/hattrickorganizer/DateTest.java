// %1912333325:de.hattrickorganizer.credits%
package de.hattrickorganizer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.hattrickorganizer.tools.HelperWrapper;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
class DateTest  {
    //~ Instance fields ----------------------------------------------------------------------------
	
	public static void main(String[] args) {

		try {
			System.out.println(getHattrickDate("2005-11-14 11:59:23.122"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Date getHattrickDate(String string) throws ParseException {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.UK);
		final Date d = sdf.parse(string);
		final String hh = string.substring(11, 13);

		if (hh.equalsIgnoreCase("12")) {
			final Calendar c = Calendar.getInstance(Locale.UK);
			c.setFirstDayOfWeek(Calendar.SUNDAY);
			c.setTime(d);
			c.add(Calendar.HOUR_OF_DAY, 12);
			return c.getTime();
		}

		return d;
	}
	
}
