package de.hattrickorganizer.tools;

public class StringUtilities {

	private StringUtilities() {
	}

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

	public static boolean isEmpty(String str) {
		return (str == null || str.length() == 0);
	}

}
