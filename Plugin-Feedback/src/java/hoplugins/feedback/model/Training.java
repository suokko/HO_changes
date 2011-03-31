package hoplugins.feedback.model;

import hoplugins.Commons;
import hoplugins.feedback.constants.FeedbackConstants;
import hoplugins.feedback.model.training.SkillUp;
import hoplugins.feedback.model.training.SkillUpKey;
import hoplugins.feedback.model.training.TrainingsWeek;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import plugins.IHOMiniModel;
import plugins.IJDBCAdapter;
import plugins.ISpieler;
import plugins.ISpielerPosition;
import plugins.ITeam;
import plugins.ITrainingPerPlayer;
import plugins.ITrainingPoint;
import plugins.ITrainingsManager;

public class Training extends FeedbackObject {
	private static String baseUrl = "http://hofeedback.flattermann.net/addtraining.php?";
	// Start of season 34, 2007-11-05 (new Matchengine, new low level training)
//	private static Date startDate = new GregorianCalendar(2007, 10, 5).getTime();
	// Start of season 35, 2008-02-25 (minute based training)
	private static Date startDate = new GregorianCalendar(2008, 1, 25).getTime();
	/**
	 * Up to which date was the upload completed,
	 * i.e. ignore data sets before this date
	 */
	private static Timestamp completedDate = new Timestamp(0);
	private static long SKILL_MULTI = 100000000000l;
	private static long VALUE_MULTI = 1000000000l;
	/**
	 * HashMap of all Trainings
	 * Key = Training Date
	 * Val = TrainingsWeek
	 */
	private static Map<Timestamp,TrainingsWeek> trainingsMap;
	/**
	 * HashMap of all Skillups
	 * Key = SkillUpKey (playerId, skill, value)
	 * Value = SkillUp
	 */
	private static Map<SkillUpKey,SkillUp> skillupMap;
	/**
	 * HashMap of all Skillups by Date
	 * Key = TrainingDate (timestamp)
	 * Value = SkillUp
	 */
	private static Map<Timestamp,List<SkillUp>> skillupMapByDate;

	private SkillUp skillUp;

	public Training (SkillUp skillUp) {
		super (FeedbackConstants.TYPE_TRAINING, 
				skillUp.getSkill()*SKILL_MULTI + 
				skillUp.getValue()*VALUE_MULTI +
				skillUp.getPlayerId());
		this.skillUp = skillUp;
	}

	public static List<FeedbackObject> rebuildList(Timestamp completedDate) {
		List<FeedbackObject> newList = new Vector<FeedbackObject>();
		try {
			Training.completedDate = completedDate;
			trainingsMap = new HashMap<Timestamp,TrainingsWeek>();
			skillupMap = new HashMap<SkillUpKey, SkillUp>();
			skillupMapByDate = new HashMap<Timestamp, List<SkillUp>>();

			Timestamp nextTrainingDate = Commons.getModel().getXtraDaten().getTrainingDate();
			Timestamp firstHrfDate = getFirstHrfDate();
			Calendar curTrainCal = new GregorianCalendar();
		
			for (curTrainCal.setTimeInMillis(nextTrainingDate.getTime());
					curTrainCal.getTimeInMillis() > firstHrfDate.getTime() &&
					curTrainCal.getTimeInMillis() > startDate.getTime() &&
					curTrainCal.getTimeInMillis() > completedDate.getTime();
					curTrainCal.add(Calendar.WEEK_OF_YEAR, -1)) {
				List<SkillUp> skillUpsThisWeek = getSkillUpsForWeek(new Timestamp (curTrainCal.getTimeInMillis()));
				Iterator<SkillUp> iter = skillUpsThisWeek.iterator();
				while (iter.hasNext()) {
					SkillUp curSkillUp = iter.next();
					SkillUp lastSkillUp = getSkillUp(curSkillUp.getPlayerId(), curSkillUp.getSkill(), curSkillUp.getValue()-1);
					if (lastSkillUp != null) {
						curSkillUp.setLastSkillup(lastSkillUp.getTimestamp());
						calcLength (curSkillUp);
						if (curSkillUp.getLength() >= 0)
							newList.add (new Training(curSkillUp));
//						System.out.println ("RebuildList: "+curSkillUp);
					}
				}
			}
		} catch (Exception e) {
			System.out.println ("Feedback.Training: Exception catched:");
			e.printStackTrace();
		}
		return newList;
	}

	private static void calcLength (SkillUp skillUp) {
		// Length already calculated
		if (skillUp.getLength() > 0) {
//			System.out.println ("Length already calculated for "+skillUp);
			return;
		}
		IHOMiniModel miniModel = getMiniModel();
		Calendar thisSkillUpCal = new GregorianCalendar();
		thisSkillUpCal.setTimeInMillis(skillUp.getTimestamp().getTime());
		Calendar lastSkillUpCal = new GregorianCalendar();
		lastSkillUpCal.setTimeInMillis(skillUp.getLastSkillup().getTime());

		ISpieler player = skillUp.getPlayer();
		int playerId = skillUp.getPlayerId();

		Calendar curTrainCal = new GregorianCalendar();
		for (curTrainCal.setTimeInMillis(thisSkillUpCal.getTimeInMillis());
				curTrainCal.getTimeInMillis() >= lastSkillUpCal.getTimeInMillis();
				curTrainCal.add(Calendar.WEEK_OF_YEAR, -1)) {
			TrainingsWeek tw = getTrainingForWeek(new Timestamp(curTrainCal.getTimeInMillis()));
//			System.out.println ("Feedback: "+skillUp.getPlayerId()+", TW="+tw.toString());
			List<Integer> matches = getMatchesForTraining(curTrainCal);
			if (tw == null || tw.getHrfId() == -1 || 
					matches == null || matches.size() == 0) {
				// Missing training/no matches for this week
				// We skip this skillup to avoid false datasets
				skillUp.setLength(-1);
				System.out.println ("Feedback.Training: Skipping skillup ("
						+ "id=" + skillUp.getPlayerId() + ", skill="+skillUp.getSkill()
						+ ", val="+skillUp.getValue() + ") because of missing trainings");
				return;
			}
			if (tw.getStaminaTrainingPart() < 5) {
				// Wrong training data (downloaded with too old HO Version, where STP did not exist yet)
				// StaminaTrainingPart need to be at least 5%
				// We skip this skillup to avoid false datasets
				skillUp.setLength(-1);
				System.out.println ("Feedback.Training: Skipping skillup ("
						+ "id=" + skillUp.getPlayerId() + ", skill="+skillUp.getSkill()
						+ ", val="+skillUp.getValue() + ") because of StaminaTrainingPart = " + tw.getStaminaTrainingPart() + " < 5");
				return;
			}
			ITrainingPoint curTrainingPoint = 
				miniModel.getTrainingsManager().getTrainingPoint(
						tw.getYear(), tw.getWeek(), tw.getTrainingType(), 
						tw.getTrainingIntensity(), tw.getStaminaTrainingPart());
			ITrainingPerPlayer trainPerPlayer = miniModel.getTrainingsManager().getTrainingPerPlayer(player);
			boolean osmosisTraining = false;
			for (int i=0; i<matches.size(); i++) {
				int curMatchId = ((Integer)matches.get(i)).intValue();
				int curMinutes = getMinutesPlayed(curMatchId, playerId);
				int curPos = getMatchPosition(curMatchId, playerId);
				curTrainingPoint.addTrainingMatch(curMinutes, curPos);
//				System.out.println ("Feedback: "+skillUp.getPlayerId()+", mID="+curMatchId+", pos="+curPos+", mins="+curMinutes);
				if (curPos == ITrainingsManager.PLAYERSTATUS_RED_CARD) {
					// The player got a red card in the current match
					// and we can't know on which position he played before he left the field
					// We skip this skillup to avoid false datasets
					// See de.hologik.TrainingsManager for more informations
					skillUp.setLength(-1);
					System.out.println ("Feedback.Training: Skipping skillup ("
							+ "id=" + skillUp.getPlayerId() + ", skill="+skillUp.getSkill()
							+ ", val="+skillUp.getValue() + ") because of red card in match "+curMatchId+", "+curMinutes+"th minute");
					return;					
				} else if (curPos == ITrainingsManager.PLAYERSTATUS_NO_MATCHDATA) {
					skillUp.setLength(-1);
					System.out.println ("Feedback.Training: Skipping skillup ("
							+ "id=" + skillUp.getPlayerId() + ", skill="+skillUp.getSkill()
							+ ", val="+skillUp.getValue() + ") because of missing match data for match "+curMatchId);
					return;										
				} else if (curPos == ITrainingsManager.PLAYERSTATUS_NO_MATCHDETAILS) {
					skillUp.setLength(-1);
					System.out.println ("Feedback.Training: Skipping skillup ("
							+ "id=" + skillUp.getPlayerId() + ", skill="+skillUp.getSkill()
							+ ", val="+skillUp.getValue() + ") because of missing match details for match "+curMatchId);
					return;															
				} else if (curPos == ITrainingsManager.PLAYERSTATUS_SUBSTITUTED_IN) {
					skillUp.setLength(-1);
					System.out.println ("Feedback.Training: Skipping skillup ("
							+ "id=" + skillUp.getPlayerId() + ", skill="+skillUp.getSkill()
							+ ", val="+skillUp.getValue() + ") because of manual sub (in) in match "+curMatchId);
					return;															
				} else if (curPos == ITrainingsManager.PLAYERSTATUS_SUBSTITUTED_OUT) {
					skillUp.setLength(-1);
					System.out.println ("Feedback.Training: Skipping skillup ("
							+ "id=" + skillUp.getPlayerId() + ", skill="+skillUp.getSkill()
							+ ", val="+skillUp.getValue() + ") because of manual sub (out) in match "+curMatchId);
					return;															
				} else if (curPos == ITrainingsManager.PLAYERSTATUS_TACTIC_CHANGE) {
					skillUp.setLength(-1);
					System.out.println ("Feedback.Training: Skipping skillup ("
							+ "id=" + skillUp.getPlayerId() + ", skill="+skillUp.getSkill()
							+ ", val="+skillUp.getValue() + ") because of tactic change in match "+curMatchId);
					return;															
				}
				// If player has played (no matter on which position) he will get osmosis training at least
				// Keeper included, they also get osmosis training... - Blaghaid
				if (curPos >= ISpielerPosition.keeper && curMinutes > 0 && isSkillTrained(skillUp.getSkill(), tw.getTrainingType()))
					osmosisTraining = true;
			}
			trainPerPlayer.setTrainPoint(curTrainingPoint);
			double lengthThisWeek = 
				trainPerPlayer.getSkillValue(skillUp.getSkill()) * tw.getTrainingIntensity()/100d 
					* (1-tw.getStaminaTrainingPart()/100d);
//			System.out.println ("Feedback: "+skillUp.getPlayerId()+", skillFromTPP="+trainPerPlayer.getSkillValue(skillUp.getSkill()));
			skillUp.addHrf (tw.getHrfId());
			skillUp.addLength(lengthThisWeek);
			// Add training type to log
			if (lengthThisWeek > 0)
				skillUp.addTraining(tw.getTrainingType());
			else if (osmosisTraining)
				skillUp.addOsmosis(tw.getTrainingType());
//			System.out.println ("Feedback: "+skillUp.getPlayerId()+", Adding length "+lengthThisWeek+" for week "+tw.getHtSeason()+"/"+tw.getHtWeek()+" -> "+skillUp.getLength() + ", " + skillUp.getTrainings());
		}
//		System.out.println ("Feedback: "+skillUp.getPlayerId()+" COMPLETE: "+skillUp.getLength()+", "+skillUp.getTrainings()+"\n");
	}
	
    private static boolean isSkillTrained(int skill, int trainingstype) {
        boolean isTrained = false;

        switch (trainingstype) {
        case ITeam.TA_TORWART:
            isTrained = skill == ISpieler.SKILL_TORWART;
            break;
        case ITeam.TA_SPIELAUFBAU:
            isTrained = skill == ISpieler.SKILL_SPIELAUFBAU;
            break;
        case ITeam.TA_PASSSPIEL:
        case ITeam.TA_STEILPAESSE:
                isTrained = skill == ISpieler.SKILL_PASSSPIEL;
                break;
        case ITeam.TA_FLANKEN:
        case ITeam.TA_EXTERNALATTACK:
                isTrained = skill == ISpieler.SKILL_FLUEGEL;
                break;
        case ITeam.TA_VERTEIDIGUNG:
        case ITeam.TA_ABWEHRVERHALTEN:
                isTrained = skill == ISpieler.SKILL_VERTEIDIGUNG;
                break;
        case ITeam.TA_CHANCEN:
        case ITeam.TA_SCHUSSTRAINING:
                isTrained = skill == ISpieler.SKILL_TORSCHUSS;
                break;
        case ITeam.TA_STANDARD:
                isTrained = skill == ISpieler.SKILL_STANDARDS;
                break;
        }

        return isTrained;
    }

	private static List<Integer> getMatchesForTraining (Calendar trainingDate) {
		return getMiniModel().getTrainingsManager().getMatchesForTraining(trainingDate);
	}
				
	private static int getMinutesPlayed (int matchId, int playerId) {
		return getMiniModel().getTrainingsManager().getMinutesPlayed(matchId, playerId);
	}

    private static int getMatchPosition (int matchId, int playerId) {
		return getMiniModel().getTrainingsManager().getMatchPosition(matchId, playerId);
    }

	private static TrainingsWeek getTrainingForWeek (Timestamp thisTrainingDate) {
		// If the training is in the cache, use it
		if (trainingsMap != null && trainingsMap.containsKey(thisTrainingDate))
			return (TrainingsWeek) trainingsMap.get(thisTrainingDate);
		// Training is not in cache, get from db and put to cache for further usage
		Calendar oldTrainCal = new GregorianCalendar();
		oldTrainCal.setTimeInMillis(thisTrainingDate.getTime());
		oldTrainCal.add(Calendar.WEEK_OF_YEAR, -1);
		IJDBCAdapter adapter = getMiniModel().getAdapter();
//		System.out.println ("getTraining for: last="+lastTrainingDate.toLocaleString()+", this="+thisTrainingDate.toLocaleString());
		String myQuery = "select HRF_ID, DATUM, TRAININGSINTENSITAET,  STAMINATRAININGPART, TRAININGSART "
			+"from HRF,TEAM "
			+"where HRF.HRF_ID=TEAM.HRF_ID and DATUM>'"+new Timestamp(oldTrainCal.getTimeInMillis()).toString()+"' and DATUM<'"+thisTrainingDate.toString() + "' "
			+"order by DATUM desc limit 1";
//		System.out.println (myQuery);
		ResultSet rs = adapter.executeQuery(myQuery);
		
		Timestamp date = thisTrainingDate;

		// Initialize with dummy TrainingsWeek
		TrainingsWeek tw = new TrainingsWeek(thisTrainingDate, -1, -1, -1, -1);
		
		if (rs != null) {
			try {
				if (rs.next()) {
					int hrfId = rs.getInt("HRF_ID");
					int trainingIntensity = rs.getInt("TRAININGSINTENSITAET");
					int staminaTrainingPart = rs.getInt("STAMINATRAININGPART");
					int trainingType = rs.getInt("TRAININGSART");
					tw = new TrainingsWeek(date, hrfId,
												trainingType, trainingIntensity, staminaTrainingPart);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
//		System.out.println ("Adding to trainingMap: "+tw);
		trainingsMap.put(thisTrainingDate, tw);
		// Return TrainingsWeek
		return tw;
	}

	private static SkillUp getSkillUp (int playerId, int skill, int value) {
		SkillUpKey skillUpKey = new SkillUpKey(playerId, skill, value);
		// If the SkillUp is in the cache, use it
		if (skillupMap != null && skillupMap.containsKey(skillUpKey))
			return (SkillUp) skillupMap.get(skillUpKey);
		// SkillUp is not in cache, get from db and put to cache for further usage
		IJDBCAdapter adapter = getMiniModel().getAdapter();
//		System.out.println ("getSkillUp for: playerId="+playerId+", skill="+skill+", value="+value);
		String myQuery = "select * from SPIELERSKILLUP "
			+"where SPIELERID="+playerId+" AND SKILL="+skill+" AND VALUE="+value
			+" AND DATUM>='"+new Timestamp(startDate.getTime()).toString()+"'";
//		System.out.println (myQuery);
		ResultSet rs = adapter.executeQuery(myQuery);
		
//		Timestamp date = thisTrainingDate;

		SkillUp skillUp = null;

		if (rs != null) {
			try {
				if (rs.next()) {
					// date is the END of the current training week
					Timestamp date = getTrainingDateForTimestamp(rs.getTimestamp("DATUM"));
					int hrfId = rs.getInt("HRF_ID");
					if (rs.next()) {
						// We have another skillup for this player with the same value
						// That means that the player had a skill drop in between
						// Therefore, we skip the skillup completely
						System.out.println ("Duplicate skillup ("+playerId+", "+skill+", "+value+") because of Skill Drop -> SKIPPING SKILLUP");
						rs.close();
					} else {
						rs.close();
						/*
						 * Check if we have a HRF from the week before this skillup,
						 * if not -> ignore this skillup (missing HRFs)

						 * If we do not check this here, we have a problem when a gap in the HRFs exist,
						 * because HO thinks that all skillups in the gap occure in the first HRF after the gap
						 * 
						 * All following skillup lengths would be calculated to short!
						 */
					
						Calendar prevTrainWeekCal = new GregorianCalendar();
						prevTrainWeekCal.setTime(date);
						prevTrainWeekCal.add(Calendar.WEEK_OF_YEAR, -1);
						// prevTrainWeekCal is now at the END of the previous training week
						Timestamp prevTrainWeekEnd = new Timestamp(prevTrainWeekCal.getTimeInMillis());
						prevTrainWeekCal.add(Calendar.WEEK_OF_YEAR, -1);
						// prevTrainWeekCal is now at the START of the previous training week
						Timestamp prevTrainWeekStart = new Timestamp(prevTrainWeekCal.getTimeInMillis());
						String checkQuery = "select * from HRF where DATUM>='"+prevTrainWeekStart.toString()+"' and DATUM<='"+prevTrainWeekEnd.toString()+"'";
						rs = adapter.executeQuery(checkQuery);
					
						// We have a HRF of the previous training week, so this skillup is valid!
						if (rs != null && rs.next()) {
							skillUp = new SkillUp(hrfId, date, playerId, skill, value);						
						} else {
							System.out.println ("No HRFs for the previous training week, between "+prevTrainWeekStart.toString() +" and "+prevTrainWeekEnd.toString()
									+ " -> SKIPPING SKILLUP ("+playerId+", "+skill+", "+value+")");						
						}
					}
				}
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
//		if (skillUp != null)
//			System.out.println (skillUp);
//		else
//			System.out.println ("No matching Skillup found");
		
		skillupMap.put(skillUpKey, skillUp);
		return skillUp;		
	}
	
	private static Timestamp getTrainingDateForTimestamp (Timestamp timestamp) {
		Timestamp endOfTraining = Commons.getModel().getXtraDaten().getTrainingDate();
		Timestamp firstHrfDate = getFirstHrfDate();
		Calendar endOfTrainingCal = new GregorianCalendar();
//		System.out.println ("getTrDateForTS: ts="+timestamp.toLocaleString());
		for (endOfTrainingCal.setTimeInMillis(endOfTraining.getTime());
				endOfTrainingCal.getTimeInMillis() > firstHrfDate.getTime();
				endOfTrainingCal.add(Calendar.WEEK_OF_YEAR, -1)) {
			Calendar beginOfTrainingCal = new GregorianCalendar();
			beginOfTrainingCal.setTime(endOfTrainingCal.getTime());
			beginOfTrainingCal.add (Calendar.WEEK_OF_YEAR, -1);
			
//			System.out.println ("getTrDateForTS: checking b="+beginOfTrainingCal.getTime().toLocaleString()+", e="+endOfTrainingCal.getTime().toLocaleString());
			if (timestamp.getTime() > endOfTrainingCal.getTimeInMillis())
				return null;
			else if (timestamp.getTime() > beginOfTrainingCal.getTimeInMillis()) {
				return new Timestamp(endOfTrainingCal.getTimeInMillis());
			}
		}
		return null;
	}
	
	private static List<SkillUp> getSkillUpsForWeek (Timestamp thisTrainingDate) {
//		// If the skillup is in the cache, use it
		if (skillupMapByDate != null && skillupMapByDate.containsKey(thisTrainingDate))
			return skillupMapByDate.get(thisTrainingDate);
		// skillup list for this date is not in cache, get from db and put to cache for further usage
		List<SkillUp> newList = new Vector<SkillUp>();
		IJDBCAdapter adapter = getMiniModel().getAdapter();
		Calendar nextTrainingDate = new GregorianCalendar();
		nextTrainingDate.setTimeInMillis(thisTrainingDate.getTime());
		nextTrainingDate.add(Calendar.WEEK_OF_YEAR, 1);
//		System.out.println ("getSkillUps for: this="+thisTrainingDate.toLocaleString()+", next="+nextTrainingDate.getTime().toLocaleString());
		String myQuery = "select * from SPIELERSKILLUP "
			+ " where DATUM>'"+thisTrainingDate.toString()+"' and DATUM<'"+new Timestamp(nextTrainingDate.getTimeInMillis()).toString() + "'"
			+ " and not (SKILL="+ISpieler.SKILL_EXPIERIENCE+" or SKILL="+ISpieler.SKILL_FORM
					+ " or SKILL="+ISpieler.SKILL_KONDITION+")"
			+ " order by DATUM";
//		System.out.println (myQuery);
		ResultSet rs = adapter.executeQuery(myQuery);
		
		Timestamp date = thisTrainingDate;

		if (rs != null) {
			try {
				while (rs.next()) {
//					Timestamp date = rs.getTimestamp("DATUM");
					int hrfId = rs.getInt("HRF_ID");
					int playerId = rs.getInt("SPIELERID");
					int skill = rs.getInt("SKILL");
					int value = rs.getInt("VALUE");
					SkillUp skillUp = new SkillUp(hrfId, date, playerId, skill, value);
					newList.add (skillUp);
//					System.out.println (skillUp);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (newList.size() > 0)
			skillupMapByDate.put(date, newList);
		return newList;
	}

	private static Timestamp getFirstHrfDate () {
		IJDBCAdapter adapter = getMiniModel().getAdapter();
		String myQuery = "select HRF_ID, DATUM "
			+"from HRF "
			+"order by DATUM limit 1";
//		System.out.println (myQuery);
		ResultSet rs = adapter.executeQuery(myQuery);
		
		if (rs != null) {
			try {
				if (rs.next()) {
					Timestamp date = rs.getTimestamp("DATUM");
					return date;
//					return new Timestamp (Math.max(date.getTime(), startDate.getTime()));
				}
			} catch (Exception e) {
				
			}
		}
		return null;
	}
	
	@Override
	public String createUrl() {
		return baseUrl + skillUp.toUrl();
	}

	public static Timestamp getCompletedDate() {
		return completedDate;
	}

	public static void setCompletedDate(Timestamp completedDate) {
		Training.completedDate = completedDate;
	}

}
