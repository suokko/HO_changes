package ho.module.lineup.substitution;

import ho.core.datatype.CBItem;
import ho.core.model.HOVerwaltung;
import ho.core.model.player.ISpielerPosition;
import ho.core.model.player.Spieler;
import ho.module.lineup.Lineup;
import ho.module.lineup.substitution.model.Substitution;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SubstitutionDataProvider {

	public static void getSubstitutions() {
		List<Substitution> subs = HOVerwaltung.instance().getModel().getAufstellung()
				.getSubstitutionList();
	}

	public static Map<Integer, PlayerPositionItem> getLineupPositions() {
		return getPositionsMap(ISpielerPosition.startLineup, ISpielerPosition.substForward);
	}

	public static Map<Integer, PlayerPositionItem> getSubstitutionPositions() {
		return getPositionsMap(ISpielerPosition.substKeeper, ISpielerPosition.substForward);
	}

	public static Map<Integer, PlayerPositionItem> getPositionsMap(int start, int end) {

		LinkedHashMap<Integer, PlayerPositionItem> positionMap = new LinkedHashMap<Integer, PlayerPositionItem>();
		Lineup lineup = HOVerwaltung.instance().getModel().getAufstellung();

		for (int i = start; i <= end; i++) {
			Spieler player = lineup.getPlayerByPositionID(i);
			if (player != null) {
				positionMap
						.put(new Integer(i),
								new PlayerPositionItem(Integer.valueOf(i), lineup
										.getPlayerByPositionID(i)));
			}
		}

		return positionMap;
	}

	public static List<PlayerPositionItem> getFieldPositions(int start, int end, boolean includeEmptyPositions) {
		List<PlayerPositionItem> playerItems = new ArrayList<PlayerPositionItem>();

		Lineup lineup = HOVerwaltung.instance().getModel().getAufstellung();
		for (int i = start; i <= end; i++) {
			Spieler player = lineup.getPlayerByPositionID(i);
			if (player != null || includeEmptyPositions) {
				playerItems.add(new PlayerPositionItem(Integer.valueOf(i), player));
			}
		}
		return playerItems;
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
	public static List<CBItem> getBehaviourItems(boolean withInheritItem) {
		List<CBItem> behaviourValues = new ArrayList<CBItem>();
		HOVerwaltung hov = HOVerwaltung.instance();
		if (withInheritItem) {
			behaviourValues.add(new CBItem(hov.getLanguageString("subs.BehNoChange"), -1));
		}
		behaviourValues.add(new CBItem(hov.getLanguageString("ls.player.behaviour.normal"), 0));
		behaviourValues.add(new CBItem(hov.getLanguageString("ls.player.behaviour.offensive"), 1));
		behaviourValues.add(new CBItem(hov.getLanguageString("ls.player.behaviour.defensive"), 2));
		behaviourValues.add(new CBItem(hov.getLanguageString("ls.player.behaviour.towardsmiddle"), 3));
		behaviourValues.add(new CBItem(hov.getLanguageString("ls.player.behaviour.towardswing"), 4));
		return behaviourValues;
	}
}
