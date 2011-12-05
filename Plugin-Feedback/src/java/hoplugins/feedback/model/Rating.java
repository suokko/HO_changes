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
	private String baseUrl = "http://hofeedback.flattermann.net/addmatch.php?";

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
			List<IExportMatchData> matches = miniModel.getDataUsefullMatches(startDateLeague, startDateFriendly, false);
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
						playerMatch.getId() < ISpielerPosition.startReserves &&
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
			int physios = -1;
			try {
				physios = dba.getVerein(hrfID).getMasseure();
			} catch (Exception e) {/** Nothing **/}
			return createUrl (det, team, lineup, trainerType, physios, isHomeTeam, stars);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String createUrl(IMatchDetails det, ITeam team, SimpleLineUp lineup, short trainerType, int physios, boolean isHomeTeam, HashMap<Integer,Double> stars) {
		try {
			IHOMiniModel miniModel = getMiniModel();
			StringBuffer url = new StringBuffer();
			url.append(baseUrl);
			int contributor = miniModel.getBasics().getTeamId();
			url.append("contributor=" + contributor);
	        Calendar c = Calendar.getInstance();
	        c.setTimeInMillis(det.getSpielDatum().getTime());
	        final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	        url.append("&tstamp="+sdf.format(c.getTime()));
			url.append("&matchId=" + det.getMatchID()
					+ "&loc=" + miniModel.getHelper().getMatchHelper().getLocation(det.getMatchID())
					+ "&trainer=" + trainerType
					+ "&ts=" + team.getStimmungAsInt()
					+ "&tc=" + team.getSelbstvertrauenAsInt()
					+ "&ti=" + team.getTrainingslevel()
					+ "&phy=" + physios
			);
			int plNum = 0;
			for (int i = ISpielerPosition.startLineup; i < ISpielerPosition.startReserves; i++) {
				ISpieler player = lineup.getPlayerByPositionID(i);
				if (player != null) {
					url.append("&pl["+plNum+"][id]=" + player.getSpielerID()
								+ "&pl["+plNum+"][pos]=" + i // Pos
								+ "&pl["+plNum+"][fo]=" + player.getForm()
								+ "&pl["+plNum+"][xp]=" + player.getErfahrung()
								+ "&pl["+plNum+"][st]=" + player.getKondition()
								+ "&pl["+plNum+"][gk]=" + FeedbackHelper.round2(player
										.getValue4Skill4(ISpieler.SKILL_TORWART) + player
										.getSubskill4SkillWithOffset(ISpieler.SKILL_TORWART))
								+ "&pl["+plNum+"][pm]=" + FeedbackHelper.round2(player
										.getValue4Skill4(ISpieler.SKILL_SPIELAUFBAU) + player
										.getSubskill4SkillWithOffset(ISpieler.SKILL_SPIELAUFBAU))
								+ "&pl["+plNum+"][ps]=" + FeedbackHelper.round2(player
										.getValue4Skill4(ISpieler.SKILL_PASSSPIEL) + player
										.getSubskill4SkillWithOffset(ISpieler.SKILL_PASSSPIEL))
								+ "&pl["+plNum+"][wi]=" + FeedbackHelper.round2(player
										.getValue4Skill4(ISpieler.SKILL_FLUEGEL) + player
										.getSubskill4SkillWithOffset(ISpieler.SKILL_FLUEGEL))
								+ "&pl["+plNum+"][de]=" + FeedbackHelper.round2(player
										.getValue4Skill4(ISpieler.SKILL_VERTEIDIGUNG) + player
										.getSubskill4SkillWithOffset(ISpieler.SKILL_VERTEIDIGUNG))
								+ "&pl["+plNum+"][sc]=" + FeedbackHelper.round2(player
										.getValue4Skill4(ISpieler.SKILL_TORSCHUSS) + player
										.getSubskill4SkillWithOffset(ISpieler.SKILL_TORSCHUSS))
								+ "&pl["+plNum+"][sp]=" + FeedbackHelper.round2(player
										.getValue4Skill4(ISpieler.SKILL_STANDARDS) + player
										.getSubskill4SkillWithOffset(ISpieler.SKILL_STANDARDS))
								+ "&pl["+plNum+"][spec]=" + player.getSpezialitaet()
								+ "&pl["+plNum+"][inj]=" + player.getVerletzt()
								+ "&pl["+plNum+"][beh]=" + lineup.getTactic4PositionID(i)
								+ "&pl["+plNum+"][stars]=" + stars.get(new Integer(player.getSpielerID()))
						);
					plNum++;
				}
			}

			Vector<IMatchHighlight> highlights = det.getHighlights();

			url.append("&isHome="+(isHomeTeam?"1":"0"));
			url.append("&pb="+getPullBackMinute(det));
			url.append("&oc="+(miniModel.getHelper().getMatchHelper().hasOverConfidence(highlights, contributor)?"1":"0"));
			url.append("&tacProp="+(miniModel.getHelper().getMatchHelper().hasTacticalProblems(highlights, contributor)?"1":"0"));
			url.append("&redCard="+(miniModel.getHelper().getMatchHelper().hasRedCard(highlights, contributor)?"1":"0"));
			url.append("&injury="+(miniModel.getHelper().getMatchHelper().hasInjury(highlights, contributor)?"1":"0"));
			url.append("&weatherSE="+(miniModel.getHelper().getMatchHelper().hasWeatherSE(highlights, contributor)?"1":"0"));
			url.append("&sub="+(miniModel.getHelper().getMatchHelper().hasManualSubstitution(highlights, contributor)?"1":"0"));

			url.append("&aId=" + det.getArenaID());
			url.append("&rId=" + det.getRegionId());

			url.append("&hId=" + det.getHeimId());
			url.append("&hGoals=" + det.getHomeGoals());
			url.append("&hAtt=" + det.getHomeEinstellung());
			url.append("&hTac=" + det.getHomeTacticType());
			url.append("&hTacLvl=" + det.getHomeTacticSkill());
			url.append("&hr[rd]=" + det.getHomeRightDef());
			url.append("&hr[md]=" + det.getHomeMidDef());
			url.append("&hr[ld]=" + det.getHomeLeftDef());
			url.append("&hr[mid]=" + det.getHomeMidfield());
			url.append("&hr[ra]=" + det.getHomeRightAtt());
			url.append("&hr[ma]=" + det.getHomeMidAtt());
			url.append("&hr[la]=" + det.getHomeLeftAtt());
			url.append("&hr[ifkd]=" + getIfkRating(highlights, det.getHeimId(), true));
			url.append("&hr[ifka]=" + getIfkRating(highlights, det.getHeimId(), false));

			url.append("&gId=" + det.getGastId());
			url.append("&gGoals=" + det.getGuestGoals());
			url.append("&gAtt=" + det.getGuestEinstellung());
			url.append("&gTac=" + det.getGuestTacticType());
			url.append("&gTacLvl=" + det.getGuestTacticSkill());
			url.append("&gr[rd]=" + det.getGuestRightDef());
			url.append("&gr[md]=" + det.getGuestMidDef());
			url.append("&gr[ld]=" + det.getGuestLeftDef());
			url.append("&gr[mid]=" + det.getGuestMidfield());
			url.append("&gr[ra]=" + det.getGuestRightAtt());
			url.append("&gr[ma]=" + det.getGuestMidAtt());
			url.append("&gr[la]=" + det.getGuestLeftAtt());
			url.append("&gr[ifkd]=" + getIfkRating(highlights, det.getGastId(), true));
			url.append("&gr[ifka]=" + getIfkRating(highlights, det.getGastId(), false));

			url.append("&weather=" + det.getWetterId());
			url.append("&spect=" + det.getZuschauer());
			url.append("&spectT=" + det.getSoldTerraces());
			url.append("&spectB=" + det.getSoldBasic());
			url.append("&spectR=" + det.getSoldRoof());
			url.append("&spectV=" + det.getSoldVIP());


			Iterator<IMatchHighlight> iter = highlights.iterator();
			int hlNum = 0;
			while (iter.hasNext()) {
				IMatchHighlight curHighlight = (IMatchHighlight) iter.next();
				url.append("&hl["+hlNum+"][min]="+curHighlight.getMinute());
				url.append("&hl["+hlNum+"][type]="+curHighlight.getHighlightTyp());
				url.append("&hl["+hlNum+"][sub]="+curHighlight.getHighlightSubTyp());
				url.append("&hl["+hlNum+"][tId]="+curHighlight.getTeamID());
				url.append("&hl["+hlNum+"][sId]="+curHighlight.getSpielerID());
				url.append("&hl["+hlNum+"][sHome]="+(curHighlight.getSpielerHeim()?"1":"0"));
				url.append("&hl["+hlNum+"][oId]="+curHighlight.getGehilfeID());
				url.append("&hl["+hlNum+"][oHome]="+(curHighlight.getGehilfeHeim()?"1":"0"));
				hlNum++;
			}

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
	private static int getPullBackMinute (IMatchDetails det) {
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

	private static int getIfkRating (Vector<IMatchHighlight> highlights, int teamId, boolean def) {
		Iterator<IMatchHighlight> iter = highlights.iterator();
		while (iter.hasNext()) {
			IMatchHighlight curHighlight = (IMatchHighlight) iter.next();
			if (curHighlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_KARTEN
					&& curHighlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_IFK_RATINGS
					&& curHighlight.getTeamID() == teamId) {
				if (def) {
					// Def IFK rating is in ObjectPlayerId
					return curHighlight.getGehilfeID();
				} else {
					// Off IFK rating is in SubjectPlayerId
					return curHighlight.getSpielerID();
				}
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
