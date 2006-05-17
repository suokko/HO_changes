package de.hattrickorganizer.simulation;


import java.util.Iterator;

import de.hattrickorganizer.logik.matchengine.TeamData;
import de.hattrickorganizer.logik.matchengine.TeamRatings;
import de.hattrickorganizer.logik.matchengine.engine.common.Action;
import de.hattrickorganizer.logik.matchengine.engine.common.MatchData;

import plugins.IMatchDetails;



/**
 * Class that Generates the Action and contains the resaults
 */
public class MatchFullSimulator {

		
	public static void main(String[] a) {
		int[] actions = new int[3];		
		int[] results = new int[3];
		int[] chances = new int[2];
		int[] chances2 = new int[2];
		int[] goals = new int[10];
		long t = System.currentTimeMillis();
		double N = 10000;
		for(int count=0; count<N; count++) {
			TeamData h = new TeamData("Team1",new TeamRatings(20,100,100,100,1,1,1),IMatchDetails.TAKTIK_NORMAL,7);
			TeamData h2 = new TeamData("Team2",new TeamRatings(16,100,100,100,1,1,1),IMatchDetails.TAKTIK_NORMAL,7);
			MatchData d = new MatchData(h,h2);		
			//long t = System.currentTimeMillis();	
			d.simulate();
			
			//System.out.print(System.currentTimeMillis()-t + " ");
			//System.out.println(d.getHomeTeamActionList().size()+d.getAwayTeamActionList().size());
			int home = 0;
			for (Iterator iter = d.getHomeTeamActionList().iterator(); iter.hasNext();) {
				Action action = (Action) iter.next();
				if (action.isScore()) {
					chances2[0]++;
					home++;
				}
				//System.out.println(action.getMinute());
				actions[action.getArea()+1]++;
				chances[0]++;	
			}
			int away = 0;
			for (Iterator iter = d.getAwayTeamActionList().iterator(); iter.hasNext();) {
				Action action = (Action) iter.next();
				chances[1]++;				
				if (action.isScore()) {
					chances2[1]++;
					away++;
				}
			}	
			
			goals[away+home]++;
			
			if (home>away) {
				results[0]++;							
			} else if(home<away) {
				results[2]++;
			} else {
				results[1]++;
			}
		}

		for (int i = 0; i < actions.length; i++) {
			System.out.println("Side: " + actions[i]);
		}
		System.out.println();
		for (int i = 0; i < chances.length; i++) {
			System.out.println("Actions: " + chances[i] + " " + chances2[i]);
		}
		System.out.println();
		for (int i = 0; i < goals.length; i++) {
			System.out.println("Goals: " + goals[i]);
		}
		System.out.println();		
				
		System.out.println("Wins " + results[0]/N*100+"%");
		System.out.println("Draw " + results[1]/N*100+"%");
		System.out.println("Loss " + results[2]/N*100+"%");
				
	}
}
