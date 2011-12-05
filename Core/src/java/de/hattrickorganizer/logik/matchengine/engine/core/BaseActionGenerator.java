// %1762772776:de.hattrickorganizer.logik.matchengine.engine.core%
package de.hattrickorganizer.logik.matchengine.engine.core;

import plugins.IMatchDetails;
import de.hattrickorganizer.logik.matchengine.TeamData;
import de.hattrickorganizer.logik.matchengine.engine.common.TeamGameData;

/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class BaseActionGenerator {

	//~ Methods ------------------------------------------------------------------------------------

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param tactic TODO Missing Method Parameter Documentation
	 * @param level TODO Missing Method Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	protected final int getArea(int tactic, int level) {
		int attackMiddle = 40;

		if (tactic == IMatchDetails.TAKTIK_MIDDLE) {
			attackMiddle = attackMiddle + (level * 3);
		}

		if (tactic == IMatchDetails.TAKTIK_WINGS) {
			attackMiddle = (int) (attackMiddle - (level * 1.5));
		}

		final int area = getRandom(100, 60);

		if (area < attackMiddle) {
			return 0;
		}

		// Choose between left and Right
		if (area < (((100 - attackMiddle) / 2) + attackMiddle)) {
			return -1;
		}

		return 1;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param value TODO Missing Method Parameter Documentation
	 * @param count TODO Missing Method Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	//    protected final int getRandom(int value, int count) {
	//        final int[] counter = new int[value];
	//        int number;
	//        int i = 0;
	//
	//        while (true) {
	//            number = Utility.random(value);
	//            counter[number]++;
	//
	//            // To minimize randomness of events
	//            if (counter[number] > count) {
	//                break;
	//            }
	//
	//            i++;
	//        }
	//
	//        return number;
	//    }
	protected final int getRandom(int value, int count) {
		return Utility.random(value);
	}
	/**
	 * TODO Missing Method Documentation
	 *
	 * @param tgd TODO Missing Method Parameter Documentation
	 * @param area TODO Missing Method Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	protected boolean isScore(TeamGameData tgd, int area) {
		double chance = 0;

		switch (area) {
			case -1 :
				chance = tgd.getRatings().getLeftAttack();
				break;

			case 0 :
				chance = tgd.getRatings().getMiddleAttack();
				break;

			case 1 :
				chance = tgd.getRatings().getRightAttack();
				break;
		}

		int effectiveness = (int) getEffectiveness(chance);

		if (getRandom(100, 40) < effectiveness) {
			return true;
		}

		return false;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param tv TODO Missing Method Parameter Documentation
	 * @param ctv TODO Missing Method Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	protected final TeamGameData compare(TeamData tv, TeamData ctv) {
		final double possession = getLinearChance(tv.getRatings().getMidfield(), ctv.getRatings().getMidfield());
		final double rchance = getLinearChance(tv.getRatings().getRightAttack(), ctv.getRatings().getLeftDef());
		final double lchance = getLinearChance(tv.getRatings().getLeftAttack(), ctv.getRatings().getRightDef());
		final double mchance = getLinearChance(tv.getRatings().getMiddleAttack(), ctv.getRatings().getMiddleDef());
		final double rrisk = getLinearChance(tv.getRatings().getRightDef(), ctv.getRatings().getLeftAttack());
		final double lrisk = getLinearChance(tv.getRatings().getLeftDef(), ctv.getRatings().getRightAttack());
		final double mrisk = getLinearChance(tv.getRatings().getMiddleDef(), ctv.getRatings().getMiddleAttack());

		final int actionNumber = (int) (getEffectiveness(possession) / 10.0) + 1;

		final TeamGameData tgd = new TeamGameData(actionNumber, possession, rchance, lchance, mchance, rrisk, lrisk, mrisk, tv.getTacticType(), tv.getTacticLevel());
		return tgd;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param tgd TODO Missing Method Parameter Documentation
	 * @param minute TODO Missing Method Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	protected final boolean hasChance(TeamGameData tgd, int minute) {
		if (tgd.getActionAlreadyPlayed() >= tgd.getActionNumber()) {
			return false;
		}

		// Factor is used to increase chance of actions if the team has had less than expected as the game progress
		double factor = ((tgd.getActionNumber() - tgd.getActionAlreadyPlayed() + 1d) / (tgd.getActionNumber() + 1d) * (91 - minute)) / 90d * 6;

		if (factor > 1) {
			factor = 1;
		}

		return (getRandom((int) (90.0d * factor), 70) < tgd.getActionNumber());
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param value TODO Missing Method Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	protected double getEffectiveness(double value) {
		double x = value * 100d;
		boolean low = false;

		if (x < 50) {
			low = true;
			x = 100 - x;
		}

		double v = (-500000.0 / Math.pow(x, 2.0)) + (10000.0 / x) + 50.0;

		if (low) {
			return 100 - v;
		}

		return v;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param rate1 TODO Missing Method Parameter Documentation
	 * @param rate2 TODO Missing Method Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	private double getLinearChance(double rate1, double rate2) {
		double ret = rate1 / (rate1 + rate2);
		return ret;
	}
	
	protected int getRandomInt(double number) {
		int intPart = (int) (number / 1);		
		double decPart = number % 1.0;
		if (Utility.random(10)<decPart*10) {
			intPart++;
		} 						
		return intPart;
	}	
}
