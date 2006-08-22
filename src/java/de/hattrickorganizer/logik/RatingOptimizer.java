
/*
 * Created on Jan 6, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package de.hattrickorganizer.logik;

import java.util.List;

import plugins.IMatchDetails;
import plugins.IMatchLineupPlayer;
import plugins.IMatchLineupTeam;
import plugins.ISpieler;
import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.logik.exporter.ExportMatchData;
import de.hattrickorganizer.logik.exporter.MatchExporter;
import de.hattrickorganizer.model.AufstellungOld;
import de.hattrickorganizer.model.HOMiniModel;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.SpielerPosition;
import prediction.ratingPredictionManager;

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
		
		List matches = MatchExporter.getDataUsefullMatches(ratingPredictionManager.LAST_CHANGE);

		int i = 0;
		int count = 0;
		while ((count < maxNumber) && (i < matches.size())) {
			ExportMatchData matchData = (ExportMatchData) matches.get(i);
			i++;
			double[] diff = getOffset(matchData);
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
			r[j][0] = linear[j] / ((double) count);
			if (count > 1) {
                                // variance 
				double var = Math.abs(quadratic[j] - (double) count * r[j][0] * r[j][0]) / ((double)(count-1));
                                // (sample) standard deviation
                                double std = Math.sqrt(var);
				// print error of average? 
                                r[j][1] = std / Math.sqrt((double) count);
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
					
		IMatchLineupTeam lineupTeam = null;
		IMatchDetails details = matchData.getDetails();
		if (details.getHeimId() == HOMiniModel.instance().getBasics().getTeamId()) {
			lineupTeam = HOMiniModel.instance().getMatchLineup(details.getMatchID()).getHeim();
		} else {
			lineupTeam = HOMiniModel.instance().getMatchLineup(details.getMatchID()).getGast();
		}
		
		// Both teams have WO values, no diff if home/away match
		if (details.getHomeRightDef()==1 && details.getHomeMidfield()==1) {			
			double[] offset = new double[7];
			offset[0] = -2;
			return offset;			
		}
		
		final AufstellungOld lineup = new AufstellungOld();
		for (int k = 0;(lineupTeam.getAufstellung() != null) && (k < lineupTeam.getAufstellung().size()); k++) {					
			IMatchLineupPlayer playerMatch = (IMatchLineupPlayer) lineupTeam.getAufstellung().get(k);
			ISpieler playerData = (ISpieler) matchData.getPlayers().get(new Integer(playerMatch.getSpielerId()));
			
			if (playerMatch.getId() == SpielerPosition.standard) {
				lineup.setKicker(playerMatch.getSpielerId());
			} else if (playerMatch.getId() == SpielerPosition.spielfuehrer) {
				lineup.setKapitaen(playerMatch.getSpielerId());
			} else {
				lineup.setSpieler(playerMatch.getId(),playerMatch.getTaktik(),playerData);
			}
		}
		
		IMatchDetails det = matchData.getDetails();
		if (HOMiniModel.instance().getBasics().getTeamId() == matchData.getInfo().getHeimID()) {
			lineup.setAttitude(det.getHomeEinstellung());
			lineup.setHeimspiel((short) 1);
			lineup.setTacticType(det.getHomeTacticType());
		} else {
			lineup.setAttitude(det.getGuestEinstellung());
			lineup.setHeimspiel((short) 0);
			lineup.setTacticType(det.getGuestTacticType());
		}

		int hrfID = DBZugriff.instance().getHrfIDSameTraining(matchData.getInfo().getMatchDateAsTimestamp());
		ratingPredictionManager rpm = new ratingPredictionManager(lineup, DBZugriff.instance().getTeam(hrfID), (short) DBZugriff.instance().getTrainerType(hrfID), RatingPredictionConfig.getInstance() );
		double[] offset = new double[7];		
		if (details.getHeimId() == HOMiniModel.instance().getBasics().getTeamId()) {
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
                    
		return offset;
	}

}
