package ho.core.util;

/**
 * Utility class for various String related operations.
 * 
 */
public class StringUtilities {

	/**
	 * Utility class - private constructor enforces noninstantiability.
	 */
	private StringUtilities() {
	}

	/**
	 * Checks if a <code>String</code> contains digits only. This means that for
	 * every character x in the given string <code>Character.isDigit(x)</code>
	 * has to be <code>true</code>.
	 * 
	 * @param str
	 *            the string to check.
	 * @return <code>true</code> if the given string contains digits only,
	 *         <code>false</code> if the given string is <code>null</code>,
	 *         empty or contains at least one character which is not a digit.
	 */
	public static boolean isNumeric(String str) {
		if (isEmpty(str)) {
			return false;
		}

		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if a given <code>String</code> is empty. This method is
	 * <code>null</code>-safe which means that it will return <code>true</code>
	 * if the given parameter is <code>null</code>.
	 * 
	 * @param str
	 *            the string to check.
	 * @return <code>true</code> if the given string is empty or
	 *         <code>null</code>, <code>false</code> otherwise.
	 */
	public static boolean isEmpty(String str) {
		return (str == null || str.length() == 0);
	}

	public static  String getResultString(int homeGoals, int awayGoals) {
		final StringBuffer buffer = new StringBuffer();
		if(homeGoals < 0 || awayGoals < 0)
			return " - : - ";

        if (homeGoals < 10) {
            buffer.append(" ");
        }

        buffer.append(homeGoals);
        buffer.append(" : ");

        if (awayGoals < 10) {
            buffer.append(" ");
        }

        buffer.append(awayGoals);

        return buffer.toString();
    }
}
