package de.hattrickorganizer.gui.lineup.substitution;

import java.util.HashMap;

import plugins.ISpieler;
import plugins.ISpielerPosition;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.Lineup;

public class PositionDataProvider {

	public static HashMap<Integer, PlayerPositionItem> getLineupPositions() {

		HashMap<Integer, PlayerPositionItem> positionMap = new HashMap<Integer, PlayerPositionItem>();
		Lineup lineup = HOVerwaltung.instance().getModel().getAufstellung();

		for (int i = ISpielerPosition.startLineup; i < ISpielerPosition.startReserves; i++) {
			ISpieler player = lineup.getPlayerByPositionID(i);
			if (player != null) {
				positionMap.put(new Integer(i),
						new PlayerPositionItem(Integer.valueOf(i), lineup.getPlayerByPositionID(i)));
			}
		}

		return positionMap;
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

}
