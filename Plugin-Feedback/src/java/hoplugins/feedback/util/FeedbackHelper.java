package hoplugins.feedback.util;

public class FeedbackHelper {

	public static double round2 (double value) {
		return round(value, 2);
	}

	public static double round (double value, int digits) {
		return ((int)(value*Math.pow(10, digits)))/Math.pow(10, digits);
	}
}
