package de.hattrickorganizer.tools.updater;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hattrickorganizer.tools.HOLogger;

/**
 * Simple data class to store version related information.
 */
public class VersionInfo {

	private double version;
	private int build;
	private Date released;
	private boolean beta;
	final static private DecimalFormat DECF = new DecimalFormat("0.000##");
	final static private DateFormat DATF = new SimpleDateFormat("dd.MM.yyyy");
	final static private DateFormat DATFILE = new SimpleDateFormat("yyyyMMdd");
	
	static {
		DecimalFormatSymbols ds = new DecimalFormatSymbols();
		ds.setDecimalSeparator('.');
		DECF.setDecimalFormatSymbols(ds);
		DECF.setGroupingUsed(false);
	}

	/**
	 * Get a human readable version info string.
	 */
	public String getVersionString() {
		return DECF.format(version) + (beta ? " DEV" : "") + (build > 0 ? (" (Build " + build + ")") : "");
	}
	
	/**
	 * Get the proper file name for the version, e.g. HO_1429_BETA_r866.zip
	 */
	public String getZipFileName() {
		String fn = "HO_";
		fn += (DECF.format(version).replace(".", ""));
		if (beta) {
			fn += "_BETA_trunk";
		}
		if (build > 0) {
			fn += ("_r" + build);
		}
		if (getFileNameDate().length() > 0) {
			fn += ("_" + getFileNameDate());
		}
		fn += ".zip";
		return fn;
	}

	public double getVersion() {
		return version;
	}

	public void setVersion(double version) {
		this.version = version;
	}

	public int getBuild() {
		return build;
	}

	public void setBuild(int build) {
		this.build = build;
	}

	public Date getReleased() {
		return released;
	}

	public String getReleaseDate() {
		return released != null ? DATF.format(released) : "";
	}

	public String getFileNameDate() {
		return released != null ? DATFILE.format(released) : "";
	}
	
	public void setReleased(Date released) {
		this.released = released;
	}

	public boolean isBeta() {
		return beta;
	}

	public void setBeta(boolean beta) {
		this.beta = beta;
	}

	// generic setter, example:
	// version=1.429
	// build=866
	// beta=true
	// released=31.05.2011
	public void setValue(final String key, final String val) {
		try {
			if ("version".equals(key)) {
				setVersion(Double.parseDouble(val));
			} else if ("build".equals(key)) {
				setBuild(Integer.parseInt(val));
			} else if ("beta".equals(key)) {
				setBeta(Boolean.parseBoolean(val));
			} else if ("released".equals(key)) {
				setReleased(DATF.parse(val));
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(), "Error parsing " + key + " / " + val + " : " + e);
		}
	}

	public boolean isValid() {
		return (version > 0d);
	}

	@Override
	public String toString() {
		return "VersionInfo [version=" + version + ", build=" + build + ", released=" + released + ", beta=" + beta + "]";
	}
}
