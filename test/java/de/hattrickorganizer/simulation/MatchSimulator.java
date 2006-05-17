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
public class MatchSimulator {

		
	public static void main(String[] a) {
		int[] actions = new int[3];		
		int[] results = new int[3];
		int[] chances = new int[2];
		long t = System.currentTimeMillis();
		for(int count=0; count<100; count++) {
			TeamData h = new TeamData("Team1",new TeamRatings(8,20,18,16,7,15,17),IMatchDetails.TAKTIK_WINGS,10);
			TeamData h2 = new TeamData("Team2",new TeamRatings(11,9,15,13,10,10,15),IMatchDetails.TAKTIK_NORMAL,1);			
			MatchData d = new MatchData(h,h2);		
			//long t = System.currentTimeMillis();	
			for(int i = 0; i < 90; i++) {
				d.advance();
			}
			//System.out.print(System.currentTimeMillis()-t + " ");
			int home = 0;
			for (Iterator iter = d.getHomeTeamActionList().iterator(); iter.hasNext();) {
				Action action = (Action) iter.next();
				if (action.isScore()) {
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
					away++;
				}
			}	
			if (home>away) {
				results[0]++;							
			} else if(home<away) {
				results[2]++;
			} else {
				results[1]++;
			}
		}
		System.out.println((System.currentTimeMillis()-t));
		System.out.println();
		for (int i = 0; i < actions.length; i++) {
			System.out.println(actions[i]);
		}
		System.out.println();
		for (int i = 0; i < chances.length; i++) {
			System.out.println(chances[i]);
		}
		System.out.println();		
		System.out.println("Wins " + results[0]+"%");
		System.out.println("Draw " + results[1]+"%");
		System.out.println("Loss " + results[2]+"%");
		
		
	}
}
