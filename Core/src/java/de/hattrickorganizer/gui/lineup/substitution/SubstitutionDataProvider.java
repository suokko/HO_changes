package de.hattrickorganizer.gui.lineup.substitution;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import plugins.ISpieler;
import plugins.ISpielerPosition;
import plugins.ISubstitution;
import de.hattrickorganizer.gui.model.CBItem;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.Lineup;

public class SubstitutionDataProvider {

	public static void getSubstitutions() {
		List<ISubstitution> subs = HOVerwaltung.instance().getModel().getAufstellung().getSubstitutionList();
	}
	
	public static Map<Integer, PlayerPositionItem> getLineupPositions() {

		LinkedHashMap<Integer, PlayerPositionItem> positionMap = new LinkedHashMap<Integer, PlayerPositionItem>();
		Lineup lineup = HOVerwaltung.instance().getModel().getAufstellung();

		for (int i = ISpielerPosition.startLineup; i < ISpielerPosition.substForward + 1; i++) {
			ISpieler player = lineup.getPlayerByPositionID(i);
			if (player != null) {
				positionMap.put(new Integer(i),
						new PlayerPositionItem(Integer.valueOf(i), lineup.getPlayerByPositionID(i)));
			}
		}

		return positionMap;
	}

	public static List<PlayerPositionItem> getFieldPositions(int start, int end) {
		List<PlayerPositionItem> playerItems = new ArrayList<PlayerPositionItem>();

		Lineup lineup = HOVerwaltung.instance().getModel().getAufstellung();
		for (int i = start; i < end; i++) {
			playerItems.add(new PlayerPositionItem(Integer.valueOf(i), lineup.getPlayerByPositionID(i)));
		}

		return playerItems;
	}
	
	public static String getPosNameById(int pos) {

		switch (pos) {
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

	/**
	 * Returns an {@link CBItem} array with all standings which can be chosen
	 * for a substitution.
	 * 
	 * @return an array with standing items
	 */
	public static CBItem[] getStandingItems() {
		CBItem[] standingValues = {
				new CBItem(HOVerwaltung.instance().getLanguageString("subs.GoalAny"), -1),
				new CBItem(HOVerwaltung.instance().getLanguageString("subs.GoalTied"), 0),
				new CBItem(HOVerwaltung.instance().getLanguageString("subs.GoalLead"), 1),
				new CBItem(HOVerwaltung.instance().getLanguageString("subs.GoalDown"), 2),
				new CBItem(HOVerwaltung.instance().getLanguageString("subs.GoalLeadMT1"), 3),
				new CBItem(HOVerwaltung.instance().getLanguageString("subs.GoalDownMT1"), 4),
				new CBItem(HOVerwaltung.instance().getLanguageString("subs.GoalNotDown"), 5),
				new CBItem(HOVerwaltung.instance().getLanguageString("subs.GoalNotLead"), 6),
				new CBItem(HOVerwaltung.instance().getLanguageString("subs.GoalLeadMT2"), 7),
				new CBItem(HOVerwaltung.instance().getLanguageString("subs.GoalDownMT2"), 8) };
		return standingValues;
	}

	/**
	 * Returns an {@link CBItem} array with all red card events which can be
	 * chosen for a substitution.
	 * 
	 * @return an array with red card items
	 */
	public static CBItem[] getRedCardItems() {
		CBItem[] redcardValues = {
				new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedIgnore"), -1),
				new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedMy"), 1),
				new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedOpp"), 2),
				new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedMyCD"), 11),
				new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedMyMF"), 12),
				new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedMyFW"), 13),
				new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedMyWB"), 14),
				new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedMyWI"), 15),
				new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedOppCD"), 21),
				new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedOppMF"), 22),
				new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedOppFW"), 23),
				new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedOppWB"), 24),
				new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedOppWi"), 25), };
		return redcardValues;
	}

	/**
	 * Returns an {@link CBItem} array with all behaviours which can be chosen
	 * for a substitution.
	 * 
	 * @return an array with behaviour items
	 */
	public static CBItem[] getBehaviourItems() {
		CBItem[] behaviourValues = {
				new CBItem(HOVerwaltung.instance().getLanguageString("subs.BehNoChange"), -1),
				new CBItem(HOVerwaltung.instance().getLanguageString("subs.BehNormal"), 0),
				new CBItem(HOVerwaltung.instance().getLanguageString("subs.BehOffensive"), 1),
				new CBItem(HOVerwaltung.instance().getLanguageString("subs.BehDefensive"), 2),
				new CBItem(HOVerwaltung.instance().getLanguageString("subs.BehToMid"), 3),
				new CBItem(HOVerwaltung.instance().getLanguageString("subs.BehToWi"), 4) };
		return behaviourValues;
	}
}
