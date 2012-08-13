
/*
 * Created on Jan 6, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package ho.core.rating;

import ho.core.db.DBManager;
import ho.core.file.xml.ExportMatchData;
import ho.core.file.xml.MatchExporter;
import ho.core.model.HOVerwaltung;
import ho.core.model.Team;
import ho.core.model.match.MatchLineupPlayer;
import ho.core.model.match.MatchLineupTeam;
import ho.core.model.match.Matchdetails;
import ho.core.model.player.ISpielerPosition;
import ho.core.model.player.Spieler;
import ho.core.util.HOLogger;
import ho.module.lineup.Lineup;

import java.text.NumberFormat;
import java.util.List;

/**
 * @author mamato
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class RatingOptimizer {

	/**
	 * 
	 */
	public static double[][] optimize(int maxNumber) {
		double[] linear = new double[7];
		double[] quadratic = new double[7];
		
		List<ExportMatchData> matches = MatchExporter.getDataUsefullMatches(RatingPredictionManager.LAST_CHANGE, RatingPredictionManager.LAST_CHANGE_FRIENDLY, true, true);

		int i = 0;
		int count = 0;
		while ((count < maxNumber) && (i < matches.size())) {
			ExportMatchData matchData =  matches.get(i);
			i++;
			double[] diff = getOffset(matchData);
//			System.out.print ("Diffs for Match "+matchData.getInfo().getMatchID()+": ");
//			for (int d=0; d<diff.length; d++)
//				System.out.print (diff[d] + " ");
//			System.out.println ("");
			// Secure match data not found
			if (diff[0] == -2) {
				continue;
			}
			for (int j = 0; j < 7; j++) {
				linear[j] += diff[j];
				quadratic[j] += diff[j] * diff[j];
			}
			count++;
		}
		if (count == 0) {
			return new double[7][2];
		}

		double[][] r = new double[8][2];
		for (int j = 0; j < 7; j++) {
			r[j][0] = linear[j] / (count);
			if (count > 1) {
                                // variance 
				double var = Math.abs(quadratic[j] - count * r[j][0] * r[j][0]) / ((count-1));
                                // (sample) standard deviation
                                double std = Math.sqrt(var);
				// print error of average? 
                                r[j][1] = std / Math.sqrt(count);
                                // or better the significance?
 				//r[j][1] = Math.abs(r[j][0]) / std * Math.sqrt((double) count);
                                // or try to calculate the probability for that offset?
                                // how?
			}
		}
		r[7][0] = count;
		r[7][1] = count;
		return r;
	}

	private static double[] getOffset(ExportMatchData matchData) {


		// Check for season reset lost		
		//if (prevBasics.getSeason() != followingBasics.getSeason()) {
		//	offset[0] = -2;
		//	return offset;
		//}
					
		MatchLineupTeam lineupTeam = null;
		Matchdetails details = matchData.getDetails();
		if (details.getHeimId() == HOVerwaltung.instance().getModel().getBasics().getTeamId()) {
			lineupTeam = DBManager.instance().getMatchLineup(details.getMatchID()).getHeim();
		} else {
			lineupTeam = DBManager.instance().getMatchLineup(details.getMatchID()).getGast();
		}
		
		// Both teams have WO values, no diff if home/away match
		if (details.getHomeRightDef()==1 && details.getHomeMidfield()==1) {			
			double[] offset = new double[7];
			offset[0] = -2;
			return offset;			
		}
		
		// Switched from use of AufstellungOld to the use of Lineup. Bye, bye, hack. (blaghaid)
		final Lineup lineup = new Lineup();
		for (int k = 0;(lineupTeam.getAufstellung() != null) && (k < lineupTeam.getAufstellung().size()); k++) {					
			MatchLineupPlayer playerMatch = (MatchLineupPlayer) lineupTeam.getAufstellung().get(k);
			Spieler playerData = (Spieler) matchData.getPlayers().get(Integer.valueOf(playerMatch.getSpielerId()));
			
			if (playerMatch.getId() == ISpielerPosition.setPieces) {
				lineup.setKicker(playerMatch.getSpielerId());
			} else if (playerMatch.getId() == ISpielerPosition.captain) {
				lineup.setKapitaen(playerMatch.getSpielerId());
			} else {
				lineup.setSpielerAtPosition(playerMatch.getId(), playerMatch.getSpielerId(), playerMatch.getTaktik());
			}
		}
		
		Matchdetails det = matchData.getDetails();
		if (HOVerwaltung.instance().getModel().getBasics().getTeamId() == matchData.getInfo().getHeimID()) {
			lineup.setAttitude(det.getHomeEinstellung());
			lineup.setHeimspiel((short) 1);
			lineup.setTacticType(det.getHomeTacticType());
		} else {
			lineup.setAttitude(det.getGuestEinstellung());
			lineup.setHeimspiel((short) 0);
			lineup.setTacticType(det.getGuestTacticType());
		}

		int hrfID = DBManager.instance().getHrfIDSameTraining(matchData.getInfo().getMatchDateAsTimestamp());
		RatingPredictionManager rpm = new RatingPredictionManager(lineup, DBManager.instance().getTeam(hrfID), (short) DBManager.instance().getTrainerType(hrfID), RatingPredictionConfig.getInstance() );
		double[] offset = new double[7];		
		if (details.getHeimId() == HOVerwaltung.instance().getModel().getBasics().getTeamId()) {
					offset[0] = 0.875d + det.getHomeRightDef() / 4.0d - rpm.getRightDefenseRatings();                    
                    offset[1] = 0.875d + det.getHomeMidDef() / 4.0d - rpm.getCentralDefenseRatings();
					offset[2] = 0.875d + det.getHomeLeftDef() / 4.0d - rpm.getLeftDefenseRatings();
                    offset[3] = 0.875d + det.getHomeMidfield() / 4.0d - rpm.getMFRatings();
					offset[4] = 0.875d + det.getHomeRightAtt() / 4.0d - rpm.getRightAttackRatings();
					offset[5] = 0.875d + det.getHomeMidAtt() / 4.0d - rpm.getCentralAttackRatings();
                    offset[6] = 0.875d + det.getHomeLeftAtt() / 4.0d - rpm.getLeftAttackRatings();
                    
                    
                } else {
					offset[0] = 0.875d + det.getGuestRightDef() / 4.0d - rpm.getRightDefenseRatings();
                    offset[1] = 0.875d + det.getGuestMidDef() / 4.0d - rpm.getCentralDefenseRatings();
					offset[2] = 0.875d + det.getGuestLeftDef() / 4.0d - rpm.getLeftDefenseRatings();                    
                    offset[3] = 0.875d + det.getGuestMidfield() / 4.0d - rpm.getMFRatings();                    
					offset[4] = 0.875d + det.getGuestRightAtt() / 4.0d - rpm.getRightAttackRatings();
                    offset[5] = 0.875d + det.getGuestMidAtt() / 4.0d - rpm.getCentralAttackRatings();
					offset[6] = 0.875d + det.getGuestLeftAtt() / 4.0d - rpm.getLeftAttackRatings();
                    
                }
        debugDiffs ("cd", det, rpm, DBManager.instance().getTeam(hrfID));
        debugDiffs ("sd_l", det, rpm, DBManager.instance().getTeam(hrfID));
        debugDiffs ("sd_r", det, rpm, DBManager.instance().getTeam(hrfID));
        debugDiffs ("mid", det, rpm, DBManager.instance().getTeam(hrfID));
        debugDiffs ("sa_l", det, rpm, DBManager.instance().getTeam(hrfID));
        debugDiffs ("sa_r", det, rpm, DBManager.instance().getTeam(hrfID));
        debugDiffs ("ca", det, rpm, DBManager.instance().getTeam(hrfID));
		return offset;
	}

	private static void debugDiffs (String type, Matchdetails det, RatingPredictionManager rpm, Team team) {
		boolean home = false;
		if (HOVerwaltung.instance().getModel().getBasics().getTeamId() == det.getHeimId()) {
			home = true;
		}
		int attitude = 0;
		int tactic = 0;
		int stimmung = team.getStimmungAsInt();
		if (home) {
			attitude = det.getHomeEinstellung();
			tactic = det.getHomeTacticType();
		} else {
			attitude = det.getGuestEinstellung();
			tactic = det.getGuestTacticType();
		}
		double calc = 0;
		double real = 0;
		if (type.equals("cd")) {
			calc = rpm.getCentralDefenseRatings();
			if (home) {
				real = det.getHomeMidDef();
			} else {
				real = det.getGuestMidDef();				
			}
		} else if (type.equals("sd_l")) {
			calc = rpm.getLeftDefenseRatings();
			if (home) {
				real = det.getHomeLeftDef();
			} else {
				real = det.getGuestLeftDef();				
			}
		} else if (type.equals("sd_r")) {
			calc = rpm.getRightDefenseRatings();
			if (home) {
				real = det.getHomeRightDef();
			} else {
				real = det.getGuestRightDef();				
			}
		} else if (type.equals("mid")) {
			calc = rpm.getMFRatings();
			if (home) {
				real = det.getHomeMidfield();
			} else {
				real = det.getGuestMidfield();				
			}
		} else if (type.equals("ca")) {
			calc = rpm.getCentralAttackRatings();
			if (home) {
				real = det.getHomeMidAtt();
			} else {
				real = det.getGuestMidAtt();				
			}
		} else if (type.equals("sa_l")) {
			calc = rpm.getLeftAttackRatings();
			if (home) {
				real = det.getHomeLeftAtt();
			} else {
				real = det.getGuestLeftAtt();				
			}
		} else if (type.equals("sa_r")) {
			calc = rpm.getRightAttackRatings();
			if (home) {
				real = det.getHomeRightAtt();
			} else {
				real = det.getGuestRightAtt();				
			}
		}

	
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(3);
		nf.setMinimumFractionDigits(3);
		nf.setMaximumIntegerDigits(1);
		nf.setMinimumIntegerDigits(1);
		
		NumberFormat pf = NumberFormat.getPercentInstance();
		pf.setMaximumFractionDigits(2);
		pf.setMinimumFractionDigits(2);
		pf.setMinimumIntegerDigits(3);
		pf.setMaximumIntegerDigits(3);
		
		real = 0.875 + real / 4;
		String calcDiff = (calc-real>0?" ":"") + nf.format(calc-real);
		String calcPerc = (calc/real>0?" ":"") + pf.format(calc/real);
		
		HOLogger.instance().debug(RatingOptimizer.class, type+" diffs for Match "+det.getMatchID()
				+": (H"+(home?"1":"0")+"/T"+tactic+"/A"+attitude+"/S"+stimmung+") "
				+"real="+nf.format(real)+ " | "
				+"calc="+nf.format(calc)+" ("+calcDiff+" "+calcPerc+")");
		
	}
}
