package de.hattrickorganizer.simulation;

import plugins.ISpieler;
import plugins.ISpielerPosition;
import de.hattrickorganizer.core.HOSetup;
import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.logik.ratingPredictionManager;
import de.hattrickorganizer.model.Aufstellung;

/*
 * Created on 5-lug-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author Mirtillo
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MatchRatingTest extends HOSetup {

	public static void main(String[] args) {
		new MatchRatingTest();
	}

	public MatchRatingTest() {
		int id = DBZugriff.instance().getLatestHrfId();
		Aufstellung lineup = DBZugriff.instance().getAufstellung(id, Aufstellung.DEFAULT_NAME);
		lineup.getPositionById(10).setTaktik(ISpielerPosition.DEFENSIV);
		for (int i = 5; i < 10; i++) {
			System.out.println("Passing " + i);
			ISpieler player = lineup.getPlayerByPositionID(10);
			player.setPasspiel(i);
			player.setSpielaufbau(11);
			player.setKondition(8);
			player.setSpezialitaet(0);
			ratingPredictionManager rpm = new ratingPredictionManager(lineup);
			System.out.println(rpm.getLeftAttackRatings());
			System.out.println(rpm.getCentralAttackRatings());
			player.setSpezialitaet(ISpieler.BALLZAUBERER);
			rpm = new ratingPredictionManager(lineup);
			System.out.println(rpm.getLeftAttackRatings());
			System.out.println(rpm.getCentralAttackRatings());
		}
	}
}
