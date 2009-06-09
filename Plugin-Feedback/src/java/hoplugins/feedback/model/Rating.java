package hoplugins.feedback.model;

import hoplugins.feedback.constants.FeedbackConstants;
import hoplugins.feedback.model.rating.SimpleLineUp;
import hoplugins.feedback.util.FeedbackHelper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import plugins.IDBAdapter;
import plugins.IExportMatchData;
import plugins.IHOMiniModel;
import plugins.IMatchDetails;
import plugins.IMatchHighlight;
import plugins.IMatchLineupPlayer;
import plugins.IMatchLineupTeam;
import plugins.IRatingPredictionManager;
import plugins.ISpieler;
import plugins.ISpielerPosition;
import plugins.ITeam;

public class Rating extends FeedbackObject {
	private String baseUrl = "http://hofeedback.flattermann.net/addrating.php?";

	/**
	 * Up to which date was the upload completed,
	 * i.e. ignore data sets before this date
	 */
	private static Timestamp completedDate = new Timestamp(0);
	private IExportMatchData matchData;

	public Rating (IExportMatchData matchData) {
		super (FeedbackConstants.TYPE_RATING, matchData.getDetails().getMatchID());
		this.matchData = matchData;
	}

	public static List<FeedbackObject> rebuildList(Timestamp completedDate) {
		List<FeedbackObject> newList = new Vector<FeedbackObject>();
		try {
			IHOMiniModel miniModel = getMiniModel();
			Rating.completedDate = completedDate;
			Date startDateLeague = IRatingPredictionManager.LAST_CHANGE;
			Date startDateFriendly = IRatingPredictionManager.LAST_CHANGE_FRIENDLY;
			if (startDateLeague.getTime() < completedDate.getTime())
				startDateLeague = completedDate;
			if (startDateFriendly.getTime() < completedDate.getTime())
				startDateFriendly = completedDate;
			List<IExportMatchData> matches = miniModel.getDataUsefullMatches(startDateLeague, startDateFriendly);
			Iterator<IExportMatchData> iter = matches.iterator();
			while (iter.hasNext()) {
				IExportMatchData matchData = (IExportMatchData) iter.next();
				newList.add(new Rating (matchData));
			}
		} catch (Exception e) {
			System.out.println ("Feedback.Rating: Exception catched:");
			e.printStackTrace();
		}
		return (newList);
	}

	@Override
	public String createUrl() {
		try {
			IHOMiniModel miniModel = getMiniModel();
			// Stars ratings are stored in a hash map (key=playerID, val=stars)
			HashMap<Integer,Double> stars = new HashMap<Integer,Double>();
			IMatchDetails det = matchData.getDetails();
			boolean isHomeTeam;
			IMatchLineupTeam lineupTeam = null;
			if (det.getHeimId() == miniModel.getBasics().getTeamId()) {
				lineupTeam = miniModel.getMatchLineup(det.getMatchID()).getHeim();
				isHomeTeam = true;
			} else {
				lineupTeam = miniModel.getMatchLineup(det.getMatchID()).getGast();
				isHomeTeam = false;
			}
			SimpleLineUp lineup = new SimpleLineUp();
			for (int k = 0;(lineupTeam.getAufstellung() != null) && (k < lineupTeam.getAufstellung().size()); k++) {
				IMatchLineupPlayer playerMatch = (IMatchLineupPlayer) lineupTeam.getAufstellung().get(k);
				if (playerMatch != null &&
						playerMatch.getId() >= ISpielerPosition.keeper &&
						playerMatch.getId() <= ISpielerPosition.forward2 &&
						playerMatch.getSpielerId() > 0) {
					stars.put(new Integer(playerMatch.getSpielerId()), new Double (playerMatch.getRating()));
					ISpieler playerData = (ISpieler) matchData.getPlayers().get(new Integer(playerMatch.getSpielerId()));
					lineup.setSpieler(playerMatch.getId(),playerMatch.getTaktik(),playerData);
				}
			}

			if (isHomeTeam) {
				lineup.setAttitude(det.getHomeEinstellung());
				lineup.setHeimspiel((short) 1);
				lineup.setTacticType(det.getHomeTacticType());
			} else {
				lineup.setAttitude(det.getGuestEinstellung());
				lineup.setHeimspiel((short) 0);
				lineup.setTacticType(det.getGuestTacticType());
			}

			IDBAdapter dba = miniModel.getDBAdapter();
			int hrfID = dba.getHrfIDSameTraining(matchData.getInfo().getMatchDateAsTimestamp());
			ITeam team = dba.getTeam(hrfID);
			short trainerType = (short)dba.getTrainerType(hrfID);
			return createUrl (det, team, lineup, trainerType, isHomeTeam, stars);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String createUrl(IMatchDetails det, ITeam team, SimpleLineUp lineup, short trainerType, boolean isHomeTeam, HashMap<Integer,Double> stars) {
		try {
			IHOMiniModel miniModel = getMiniModel();
			StringBuffer url = new StringBuffer();
			url.append(baseUrl);
			url.append("contributor=" + miniModel.getBasics().getTeamId());
	        Calendar c = Calendar.getInstance();
	        c.setTimeInMillis(det.getSpielDatum().getTime());
	        final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	        url.append("&tstamp="+sdf.format(c.getTime()));
			url.append("&matchID=" + det.getMatchID()
					+ "&home=" + miniModel.getHelper().getMatchHelper().getLocation(det.getMatchID())
					+ "&trainer=" + trainerType
					+ "&ts=" + team.getStimmungAsInt()
					+ "&tc=" + team.getSelbstvertrauenAsInt()
					+ "&att=" + lineup.getAttitude()
					+ "&tactic=" + lineup.getTacticType()
					+ "&tacticlvl=" + (isHomeTeam?det.getHomeTacticSkill():det.getGuestTacticSkill())
					+ "&deflead=" + getDefendLeadMinute(det)
			);
			for (int i = 1; i < 12; i++) {
				ISpieler player = lineup.getPlayerByPositionID(i);
				if (player != null)
					url.append("&pl"+i+"fo=" + player.getForm()
								+ "&pl"+i+"xp=" + player.getErfahrung()
								+ "&pl"+i+"st=" + player.getKondition()
								+ "&pl"+i+"gk=" + FeedbackHelper.round2(player
										.getValue4Skill4(ISpieler.SKILL_TORWART) + player
										.getSubskill4SkillWithOffset(ISpieler.SKILL_TORWART))
								+ "&pl"+i+"pm=" + FeedbackHelper.round2(player
										.getValue4Skill4(ISpieler.SKILL_SPIELAUFBAU) + player
										.getSubskill4SkillWithOffset(ISpieler.SKILL_SPIELAUFBAU))
								+ "&pl"+i+"ps=" + FeedbackHelper.round2(player
										.getValue4Skill4(ISpieler.SKILL_PASSSPIEL) + player
										.getSubskill4SkillWithOffset(ISpieler.SKILL_PASSSPIEL))
								+ "&pl"+i+"wi=" + FeedbackHelper.round2(player
										.getValue4Skill4(ISpieler.SKILL_FLUEGEL) + player
										.getSubskill4SkillWithOffset(ISpieler.SKILL_FLUEGEL))
								+ "&pl"+i+"de=" + FeedbackHelper.round2(player
										.getValue4Skill4(ISpieler.SKILL_VERTEIDIGUNG) + player
										.getSubskill4SkillWithOffset(ISpieler.SKILL_VERTEIDIGUNG))
								+ "&pl"+i+"sc=" + FeedbackHelper.round2(player
										.getValue4Skill4(ISpieler.SKILL_TORSCHUSS) + player
										.getSubskill4SkillWithOffset(ISpieler.SKILL_TORSCHUSS))
								+ "&pl"+i+"sp=" + FeedbackHelper.round2(player
										.getValue4Skill4(ISpieler.SKILL_STANDARDS) + player
										.getSubskill4SkillWithOffset(ISpieler.SKILL_STANDARDS))
								+ "&pl"+i+"spec=" + player.getSpezialitaet()
								+ "&pl"+i+"beh=" + lineup.getTactic4PositionID(i)
								+ "&pl"+i+"stars=" + stars.get(new Integer(player.getSpielerID()))
						);
			}
			double mid = 0;
			double sd_r = 0;
			double cd = 0;
			double sd_l = 0;
			double sa_r = 0;
			double ca = 0;
			double sa_l = 0;

			if (isHomeTeam) {
				sd_r = 0.875d + det.getHomeRightDef() / 4.0d;
				cd = 0.875d + det.getHomeMidDef() / 4.0d;
				sd_l = 0.875d + det.getHomeLeftDef() / 4.0d;
				mid = 0.875d + det.getHomeMidfield() / 4.0d;
				sa_r = 0.875d + det.getHomeRightAtt() / 4.0d;
				ca = 0.875d + det.getHomeMidAtt() / 4.0d;
				sa_l = 0.875d + det.getHomeLeftAtt() / 4.0d;
			} else {
				sd_r = 0.875d + det.getGuestRightDef() / 4.0d;
				cd = 0.875d + det.getGuestMidDef() / 4.0d;
				sd_l = 0.875d + det.getGuestLeftDef() / 4.0d;
				mid = 0.875d + det.getGuestMidfield() / 4.0d;
				sa_r = 0.875d + det.getGuestRightAtt() / 4.0d;
				ca = 0.875d + det.getGuestMidAtt() / 4.0d;
				sa_l = 0.875d + det.getGuestLeftAtt() / 4.0d;
			}
			url.append("&mid="+mid + "&sd_r=" + sd_r + "&cd=" + cd + "&sd_l=" + sd_l
					+ "&sa_r=" + sa_r + "&ca=" + ca + "&sa_l=" + sa_l);
	        url.append("&ver=" + getFeedbackVersion()); 
			return (url.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Get the minute with the 'Pull back to defend lead' event
	 * if not existent, return -1
	 * 
	 * @param det	Match details
	 * @return		minute of the DefendLead event
	 */
	private static int getDefendLeadMinute (IMatchDetails det) {
		Vector<IMatchHighlight> highlights = det.getHighlights();
		Iterator<IMatchHighlight> iter = highlights.iterator();
		while (iter.hasNext()) {
			IMatchHighlight curHighlight = (IMatchHighlight) iter.next();
			if (curHighlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_INFORMATION
					&& curHighlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_FUEHRUNG_HALTEN
					&& curHighlight.getTeamID() == getMiniModel().getBasics().getTeamId()) {
				return curHighlight.getMinute();
			}
		}
		return -1;
	}
	
	public static Timestamp getCompletedDate() {
		return completedDate;
	}

	public static void setCompletedDate(Timestamp completedDate) {
		Rating.completedDate = completedDate;
	}
	
}
