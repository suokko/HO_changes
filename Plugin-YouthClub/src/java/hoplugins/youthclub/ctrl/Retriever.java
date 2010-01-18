package hoplugins.youthclub.ctrl;

import static hoplugins.youthclub.Constants.*;  
import hoplugins.commons.utils.Debug;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;

public class Retriever {

	// https://www.hattrick-youthclub.org/_data_provider/?action=package1&application_id=1&application_code=[YOUR CODE]&htID=[HT_USER_ID]&password=[HATTRICK YOUTHCLUB SECURITY CODE]
	private final static String pack1 = "https://www.hattrick-youthclub.org/_data_provider/?action=package1&application_id="
		+ YOUTHCLUB_APP_ID + "&application_code=" + YOUTHCLUB_APP_CODE + "&htID=";
	
	/**
	 * Get the URL for the main YC package.
	 * @param htLogin the users HT login name
	 * @param ycCode the YC secutity code
	 */
	public static URL getPackage1Url(String htLogin, String ycCode) throws MalformedURLException {
		return new URL(pack1 + htLogin + "&password=" + getMD5(ycCode));
	}
	
	public static String getMD5(String in) {
		if (in == null) {
			return null;
		}
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(in.getBytes());
			byte[] hash = digest.digest();

			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < hash.length; i++) {
				hexString.append(Integer.toHexString(0xFF & hash[i]));
			}

			String hashCodeString = hexString.toString();

			if (hashCodeString.length() < 32) {
				int differenz = 32 - hashCodeString.length();
				for (int i = 0; i < differenz; i++) {
					hashCodeString = "0" + hashCodeString;
				}
			}
			return hashCodeString;
		} catch (Exception e) {
			Debug.logException(e);
		}
		return null;
	}
}
