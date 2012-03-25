package ho.module.lineup.substitution;

import ho.core.model.HOVerwaltung;
import ho.core.model.ISpielerPosition;

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

	public static String getStanding(byte id) {
		switch (id) {
		case -1:
			return HOVerwaltung.instance().getLanguageString("subs.GoalAny");
		case 0:
			return HOVerwaltung.instance().getLanguageString("subs.GoalTied");
		case 1:
			return HOVerwaltung.instance().getLanguageString("subs.GoalLead");
		case 2:
			return HOVerwaltung.instance().getLanguageString("subs.GoalDown");
		case 3:
			return HOVerwaltung.instance().getLanguageString("subs.GoalLeadMT1");
		case 4:
			return HOVerwaltung.instance().getLanguageString("subs.GoalDownMT1");
		case 5:
			return HOVerwaltung.instance().getLanguageString("subs.GoalNotDown");
		case 6:
			return HOVerwaltung.instance().getLanguageString("subs.GoalNotLead");
		case 7:
			return HOVerwaltung.instance().getLanguageString("subs.GoalLeadMT2");
		case 8:
			return HOVerwaltung.instance().getLanguageString("subs.GoalDownMT2");
		}
		return "";
	}

	public static String getRedCard(byte id) {
		switch (id) {
		case -1:
			return HOVerwaltung.instance().getLanguageString("subs.RedIgnore");
		case 1:
			return HOVerwaltung.instance().getLanguageString("subs.RedMy");
		case 2:
			return HOVerwaltung.instance().getLanguageString("subs.RedOpp");
		case 11:
			return HOVerwaltung.instance().getLanguageString("subs.RedMyCD");
		case 12:
			return HOVerwaltung.instance().getLanguageString("subs.RedMyMF");
		case 13:
			return HOVerwaltung.instance().getLanguageString("subs.RedMyFW");
		case 14:
			return HOVerwaltung.instance().getLanguageString("subs.RedMyWB");
		case 15:
			return HOVerwaltung.instance().getLanguageString("subs.RedMyWI");
		case 21:
			return HOVerwaltung.instance().getLanguageString("subs.RedOppCD");
		case 22:
			return HOVerwaltung.instance().getLanguageString("subs.RedOppMF");
		case 23:
			return HOVerwaltung.instance().getLanguageString("subs.RedOppFW");
		case 24:
			return HOVerwaltung.instance().getLanguageString("subs.RedOppWB");
		case 25:
			return HOVerwaltung.instance().getLanguageString("subs.RedOppWi");
		}
		return "";
	}
}
