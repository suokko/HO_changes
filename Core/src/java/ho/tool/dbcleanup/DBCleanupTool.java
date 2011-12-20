package ho.tool.dbcleanup;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JFrame;

import plugins.IMatchLineup;
import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.gui.RefreshManager;
import de.hattrickorganizer.model.HOMiniModel;
import de.hattrickorganizer.model.HRF;
import de.hattrickorganizer.model.matches.MatchKurzInfo;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.HelperWrapper;

/**
 * HO Database Cleanup Tool
 * 
 * Removes old HRFs and old matches from the DB to speedup HO
 * 
 * @author flattermann <HO@flattermann.net>
 */
public class DBCleanupTool {
	public static int REMOVE_NONE = -1;
	public static int REMOVE_ALL = 0;

	public DBCleanupTool() {
		
	}
	
	public void showDialog(JFrame owner) {
		new DBCleanupDialog(owner, this);
	}

	/**
	 * Remove old HRFs
	 * 
	 * @param removeDate remove HRFs older than x weeks (-1=keep All, 0=remove All)
	 * @param autoRemove if true, automatically remove all HRFs except the first per training week  
	 */
	public void cleanupHRFs (int keepWeeks, boolean autoRemove) {
		Timestamp removeDate = null;
		if (keepWeeks >= 0) {
			Calendar cal = new GregorianCalendar();
			cal.add(Calendar.WEEK_OF_YEAR, -keepWeeks);
			removeDate = new Timestamp(cal.getTimeInMillis());
		}
		if (removeDate != null || autoRemove) {
			cleanupHRFs(removeDate, autoRemove);
		}
	}
	
	/**
	 * Remove old HRFs
	 * 
	 * @param removeDate remove HRFs older than x (null=keep all)
	 * @param autoRemove if true, automatically remove all HRFs except the first per training week  
	 */
	public void cleanupHRFs (Timestamp removeDate, boolean autoRemove) {
		HOLogger.instance().debug(getClass(), 
				"Removing old HRFs: removeDate=" + removeDate + ", autoRemove="+autoRemove);
		HRF[] allHrfs = DBZugriff.instance().getAllHRFs(-1, -1, true);
		int latestHrf = DBZugriff.instance().getLatestHrfId();
		int lastSeason = -1;
		int lastWeek = -1;
		int counter = 0;
		for (HRF curHrf : allHrfs) {
			int curId = curHrf.getHrfId();
//			String curName = curHrf.getName();
			Timestamp curDate = curHrf.getDatum();
			int curHtSeasonTraining = HelperWrapper.instance().getHTSeason(curDate, true);
			int curHtWeekTraining = HelperWrapper.instance().getHTWeek(curDate, true);
			boolean remove = false;
			if (removeDate != null && removeDate.after(curDate)) {
				remove = true;
			} else if (autoRemove) {
				if (lastSeason == curHtSeasonTraining && lastWeek == curHtWeekTraining) {
					remove = true;
				} else {
					lastSeason = curHtSeasonTraining;
					lastWeek = curHtWeekTraining;
				}
			}
			// Do not remove the latest HRF
			if (remove && curId != latestHrf) {
				HOLogger.instance().debug(getClass(),
						"Removing Hrf: " + curId + " @ " + curDate + " (" + curHtSeasonTraining + "/" + curHtWeekTraining + ")");
				DBZugriff.instance().deleteHRF(curId);
				counter++;
			} else {
//				HOLogger.instance().debug(getClass(),
//						"Keeping Hrf: " + curId + " @ " + curDate + " (" + curHtSeasonTraining + "/" + curHtWeekTraining + ")");				
			}
		}
		HOLogger.instance().debug(getClass(), "Removed " + counter + "/" + allHrfs.length + " HRFs from DB!");
		if (counter > 0) {
			reInitHO();
		}
	}
	
	/**
	 * Remove old matches from DB (by weeks)
	 * 
	 * @param keepWeeksOwnMatches	keep x weeks of own matches (-1=keep All, 0=remove All)
	 * @param keepWeeksOwnFriendlies keep x weeks of own friendlies (-1=keep All, 0=remove All)
	 * @param keepWeeksOtherMatches keep x weeks of other matches (-1=keep All, 0=remove All)
	 * @param keepWeeksOtherFriendlies keep x weeks of other friendlies (-1=keep All, 0=remove All)
	 */
	public void cleanupMatches (int keepWeeksOwnMatches, int keepWeeksOwnFriendlies, int keepWeeksOtherMatches, int keepWeeksOtherFriendlies) {
		Timestamp removeDateOwnMatches = null;
		Timestamp removeDateOwnFriendlies = null;
		Timestamp removeDateOtherMatches = null;
		Timestamp removeDateOtherFriendlies = null;
		if (keepWeeksOwnMatches >= 0) {
			Calendar cal = new GregorianCalendar();
			cal.add(Calendar.WEEK_OF_YEAR, -keepWeeksOwnMatches);
			removeDateOwnMatches = new Timestamp(cal.getTimeInMillis());
		}
		if (keepWeeksOwnFriendlies >= 0) {
			Calendar cal = new GregorianCalendar();
			cal.add(Calendar.WEEK_OF_YEAR, -keepWeeksOwnFriendlies);
			removeDateOwnFriendlies = new Timestamp(cal.getTimeInMillis());
		}
		if (keepWeeksOtherMatches >= 0) {
			Calendar cal = new GregorianCalendar();
			cal.add(Calendar.WEEK_OF_YEAR, -keepWeeksOtherMatches);
			removeDateOtherMatches = new Timestamp(cal.getTimeInMillis());
		}
		if (keepWeeksOtherFriendlies >= 0) {
			Calendar cal = new GregorianCalendar();
			cal.add(Calendar.WEEK_OF_YEAR, -keepWeeksOtherFriendlies);
			removeDateOtherFriendlies = new Timestamp(cal.getTimeInMillis());
		}
		if (removeDateOwnMatches != null || removeDateOwnFriendlies != null
				|| removeDateOtherMatches != null || removeDateOtherFriendlies != null) {
			cleanupMatches (removeDateOwnMatches, removeDateOwnFriendlies, removeDateOtherMatches, removeDateOtherFriendlies);
		}
	}
	
	/**
	 * Remove old matches from DB (by date)
	 * 
	 * @param removeDateOwnMatches	remove own matches older than x (null=keep All)
	 * @param removeDateOwnFriendlies	remove own friendlies older than x (null=keep All)
	 * @param removeDateOtherMatches remove other matches older than x (null=keep All)
	 * @param removeDateOtherFriendlies remove other friendlies older than x (null=keep All)
	 */
	public void cleanupMatches (Timestamp removeDateOwnMatches, Timestamp removeDateOwnFriendlies, 
			Timestamp removeDateOtherMatches, Timestamp removeDateOtherFriendlies) {
		HOLogger.instance().debug(getClass(),  
				"Removing old matches: "
				+ "removeDateOwnMatches="+removeDateOwnMatches 
				+ ", removeDateOwnFriendlies="+removeDateOwnFriendlies 
				+ ", removeDateOtherMatches="+removeDateOtherMatches
				+ ", removeDateOtherFriendlies="+removeDateOtherFriendlies);
		MatchKurzInfo[] kurzInfos = DBZugriff.instance().getMatchesKurzInfo(-1);
		int counter = 0;
		int myTeamId = HOMiniModel.instance().getBasics().getTeamId();
		for (MatchKurzInfo curKurzInfo : kurzInfos) {
			Timestamp curMatchDate = curKurzInfo.getMatchDateAsTimestamp();
			int curMatchId = curKurzInfo.getMatchID();
			int curMatchType = curKurzInfo.getMatchTyp();
			boolean isMyMatch = (curKurzInfo.getHeimID() == myTeamId || curKurzInfo.getGastID() == myTeamId);
			boolean removeMatch = false;
			if (isMyMatch) {
				if (isFriendlyType (curMatchType)) {
					if (removeDateOwnFriendlies != null && removeDateOwnFriendlies.after(curMatchDate)) {
						// Remove friendly of my team
						removeMatch = true;
					}
				} else if (removeDateOwnMatches != null && removeDateOwnMatches.after(curMatchDate)) {
					// Remove non-friendly of my team
					removeMatch = true;					
				}
			} else {
				if (isFriendlyType (curMatchType)) {
					if (removeDateOtherFriendlies != null && removeDateOtherFriendlies.after(curMatchDate)) {
						// Remove friendly of other team
						removeMatch = true;
					}
				} else if (removeDateOtherMatches != null && removeDateOtherMatches.after(curMatchDate)) {
					// Remove non-friendly of other team
					removeMatch = true;					
				}
				
			}
			if (removeMatch) {
				// Remove match
				HOLogger.instance().debug(getClass(), 
						"Removing match "+curMatchId+" @ "+curMatchDate+ " (myMatch="+isMyMatch+", type="+curMatchType+")");
	            DBZugriff.instance().deleteMatch(curMatchId);
				counter++;
			} 
		}
		HOLogger.instance().debug(getClass(), "Removed " + counter + "/" + kurzInfos.length + " matches from DB!");
		if (counter > 0) {
			reInitHO();
		}
	}
	
	private boolean isFriendlyType (int matchType) {
		switch (matchType) {
		case IMatchLineup.TESTSPIEL:
		case IMatchLineup.TESTPOKALSPIEL:
		case IMatchLineup.INT_TESTSPIEL:
		case IMatchLineup.INT_TESTCUPSPIEL:
			return true;
			
		default:
			return false;
		}
	}
	
	private void reInitHO() {
        RefreshManager.instance().doReInit();
	}
}
