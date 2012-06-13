package ho.module.lineup.substitution;

import ho.core.model.HOVerwaltung;
import ho.core.model.player.ISpielerPosition;
import ho.module.lineup.substitution.model.GoalDiffCriteria;
import ho.module.lineup.substitution.model.RedCardCriteria;

public class Lookup {

	public static String getOrderType(MatchOrderType orderType) {
		switch (orderType) {
		case NEW_BEHAVIOUR:
			return HOVerwaltung.instance().getLanguageString("subs.TypeOrder");
		case POSITION_SWAP:
			return HOVerwaltung.instance().getLanguageString("subs.TypeSwap");
		case SUBSTITUTION:
			return HOVerwaltung.instance().getLanguageString("subs.TypeSub");
		}
		return null;
	}

	public static String getBehaviour(byte id) {
		switch (id) {
		case (-1):
			return HOVerwaltung.instance().getLanguageString("subs.BehNoChange");
		case ISpielerPosition.NORMAL:
			return HOVerwaltung.instance().getLanguageString("subs.BehNormal");
		case ISpielerPosition.OFFENSIVE:
			return HOVerwaltung.instance().getLanguageString("subs.BehOffensive");
		case ISpielerPosition.DEFENSIVE:
			return HOVerwaltung.instance().getLanguageString("subs.BehDefensive");
		case ISpielerPosition.TOWARDS_MIDDLE:
			return HOVerwaltung.instance().getLanguageString("subs.BehToMid");
		case ISpielerPosition.TOWARDS_WING:
			return HOVerwaltung.instance().getLanguageString("subs.BehToWi");
		}
		return "UNKNOWN_BEHAVIOUR";
	}

	public static String getPosition(byte id) {
		switch (id) {
		case ISpielerPosition.keeper:
			return HOVerwaltung.instance().getLanguageString("subs.gk");
		case ISpielerPosition.rightBack:
			return HOVerwaltung.instance().getLanguageString("subs.rb");
		case ISpielerPosition.rightCentralDefender:
			return HOVerwaltung.instance().getLanguageString("subs.rcd");
		case ISpielerPosition.middleCentralDefender:
			return HOVerwaltung.instance().getLanguageString("subs.mcd");
		case ISpielerPosition.leftCentralDefender:
			return HOVerwaltung.instance().getLanguageString("subs.lcd");
		case ISpielerPosition.leftBack:
			return HOVerwaltung.instance().getLanguageString("subs.rb");
		case ISpielerPosition.rightWinger:
			return HOVerwaltung.instance().getLanguageString("subs.rw");
		case ISpielerPosition.rightInnerMidfield:
			return HOVerwaltung.instance().getLanguageString("subs.rim");
		case ISpielerPosition.centralInnerMidfield:
			return HOVerwaltung.instance().getLanguageString("subs.cim");
		case ISpielerPosition.leftInnerMidfield:
			return HOVerwaltung.instance().getLanguageString("subs.lim");
		case ISpielerPosition.leftWinger:
			return HOVerwaltung.instance().getLanguageString("subs.rw");
		case ISpielerPosition.rightForward:
			return HOVerwaltung.instance().getLanguageString("subs.rfw");
		case ISpielerPosition.centralForward:
			return HOVerwaltung.instance().getLanguageString("subs.cfw");
		case ISpielerPosition.leftForward:
			return HOVerwaltung.instance().getLanguageString("subs.lfw");
		case ISpielerPosition.substKeeper:
			return HOVerwaltung.instance().getLanguageString("subs.subgk");
		case ISpielerPosition.substDefender:
			return HOVerwaltung.instance().getLanguageString("subs.subdef");
		case ISpielerPosition.substInnerMidfield:
			return HOVerwaltung.instance().getLanguageString("subs.submid");
		case ISpielerPosition.substWinger:
			return HOVerwaltung.instance().getLanguageString("subs.subwing");
		case ISpielerPosition.substForward:
			return HOVerwaltung.instance().getLanguageString("subs.subfw");
		default:
			return "";
		}
	}

	public static String getStanding(GoalDiffCriteria standing) {
		switch (standing) {
		case ANY_STANDING:
			return HOVerwaltung.instance().getLanguageString("subs.GoalAny");
		case MATCH_IS_TIED:
			return HOVerwaltung.instance().getLanguageString("subs.GoalTied");
		case IN_THE_LEAD:
			return HOVerwaltung.instance().getLanguageString("subs.GoalLead");
		case DOWN:
			return HOVerwaltung.instance().getLanguageString("subs.GoalDown");
		case IN_THE_LEAD_BY_MORE_THAN_ONE:
			return HOVerwaltung.instance().getLanguageString("subs.GoalLeadMT1");
		case DOWN_BY_MORE_THAN_ONE:
			return HOVerwaltung.instance().getLanguageString("subs.GoalDownMT1");
		case NOT_DOWN:
			return HOVerwaltung.instance().getLanguageString("subs.GoalNotDown");
		case NOT_IN_THE_LEAD:
			return HOVerwaltung.instance().getLanguageString("subs.GoalNotLead");
		case IN_THE_LEAD_BY_MORE_THAN_TWO:
			return HOVerwaltung.instance().getLanguageString("subs.GoalLeadMT2");
		case DOWN_BY_MORE_THAN_TWO:
			return HOVerwaltung.instance().getLanguageString("subs.GoalDownMT2");
		}
		return "";
	}

	public static String getRedCard(RedCardCriteria redCardCriteria) {
		switch (redCardCriteria) {
		case IGNORE:
			return HOVerwaltung.instance().getLanguageString("subs.RedIgnore");
		case MY_PLAYER:
			return HOVerwaltung.instance().getLanguageString("subs.RedMy");
		case OPPONENT_PLAYER:
			return HOVerwaltung.instance().getLanguageString("subs.RedOpp");
		case MY_CENTRAL_DEFENDER:
			return HOVerwaltung.instance().getLanguageString("subs.RedMyCD");
		case MY_MIDFIELDER:
			return HOVerwaltung.instance().getLanguageString("subs.RedMyMF");
		case MY_FORWARD:
			return HOVerwaltung.instance().getLanguageString("subs.RedMyFW");
		case MY_WING_BACK:
			return HOVerwaltung.instance().getLanguageString("subs.RedMyWB");
		case MY_WINGER:
			return HOVerwaltung.instance().getLanguageString("subs.RedMyWI");
		case OPPONENT_CENTRAL_DEFENDER:
			return HOVerwaltung.instance().getLanguageString("subs.RedOppCD");
		case OPPONENT_MIDFIELDER:
			return HOVerwaltung.instance().getLanguageString("subs.RedOppMF");
		case OPPONENT_FORAWARD:
			return HOVerwaltung.instance().getLanguageString("subs.RedOppFW");
		case OPPONENT_WING_BACK:
			return HOVerwaltung.instance().getLanguageString("subs.RedOppWB");
		case OPPONENT_WINGER:
			return HOVerwaltung.instance().getLanguageString("subs.RedOppWi");
		}
		return "";
	}
}
