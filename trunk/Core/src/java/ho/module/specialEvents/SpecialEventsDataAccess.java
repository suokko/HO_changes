package ho.module.specialEvents;

import ho.core.db.DBManager;
import ho.core.model.HOVerwaltung;
import ho.core.model.match.MatchKurzInfo;
import ho.core.model.match.MatchType;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

class SpecialEventsDataAccess {

	private static final SpecialEventsDataAccess CURRENT = new SpecialEventsDataAccess();
	private static int saveSeasons = 0;
	private static Vector<MatchKurzInfo> kurzInfos = null;
	private static Vector<MatchKurzInfo> aktKurzInfos = null;

	public static SpecialEventsDataAccess getCurrent() {
		return CURRENT;
	}

	private SpecialEventsDataAccess() {
	}

	Vector<MatchKurzInfo> getAktMatchKurzInfos(int saisons, boolean friendlies) {
		if (saisons != saveSeasons) {
			getMatchKurzInfosForSaisons(saisons);
		}
		return filterMatches(friendlies);
	}

	private Vector<MatchKurzInfo> filterMatches(boolean friendlies) {
		if (friendlies) {
			aktKurzInfos = kurzInfos;
			return kurzInfos;
		}
		aktKurzInfos = new Vector<MatchKurzInfo>();
		for (Iterator<MatchKurzInfo> iter = kurzInfos.iterator(); iter
				.hasNext();) {
			MatchKurzInfo element = iter.next();
			if (element.getMatchTyp() != MatchType.LEAGUE 
					&& element.getMatchTyp() != MatchType.CUP
					&& element.getMatchTyp() != MatchType.INTFRIENDLYNORMAL 
					&& element.getMatchTyp() != MatchType.INTFRIENDLYCUPRULES) {
				aktKurzInfos.add(element);
			}
		}

		return aktKurzInfos;
	}

	private void getMatchKurzInfosForSaisons(int saisons) {
		Timestamp datumAb = getDatumAb(saisons);
		MatchKurzInfo modelKurzInfos[] = DBManager.instance()
				.getMatchesKurzInfo(
						HOVerwaltung.instance().getModel().getBasics()
								.getTeamId());
		kurzInfos = new Vector<MatchKurzInfo>();
		for (int i = 0; i < modelKurzInfos.length; i++) {
			if (datumAb != null) {
				Timestamp spDate = modelKurzInfos[i].getMatchDateAsTimestamp();
				if (spDate.compareTo(datumAb) < 0) {
					continue;
				}
			}
			if (modelKurzInfos[i].getMatchStatus() == 1) {
				kurzInfos.add(modelKurzInfos[i]);
			}
		}

	}

	private Timestamp getDatumAb(int saisonAnz) {
		Date date = null;
		if (saisonAnz == 1) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		// Date toDay = cal.getTime();
		int week = HOVerwaltung.instance().getModel().getBasics().getSpieltag();
		int tag = cal.get(7);
		int corrTag = 0;
		if (tag != 7) {
			corrTag = tag;
		}
		cal.add(7, -corrTag);
		cal.add(3, -(week - 1));
		if (saisonAnz == 2) {
			cal.add(3, -16);
		}
		date = cal.getTime();
		return new Timestamp(date.getTime());
	}

}
