package ho.module.specialEvents;

import ho.core.db.DBManager;
import ho.core.model.HOVerwaltung;
import ho.core.model.match.MatchKurzInfo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

class SpecialEventsDataAccess {

	static List<MatchKurzInfo> getMatchKurzInfos(SeasonFilterValue period, boolean friendlies) {
		return getMatchKurzInfosForSaisons(period);
	}

	private static List<MatchKurzInfo> getMatchKurzInfosForSaisons(SeasonFilterValue period) {
		Timestamp datumAb = getDatumAb(period);
		MatchKurzInfo modelKurzInfos[] = DBManager.instance().getMatchesKurzInfo(
				HOVerwaltung.instance().getModel().getBasics().getTeamId());
		List<MatchKurzInfo> kurzInfos = new ArrayList<MatchKurzInfo>();
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
		return kurzInfos;
	}

	private static Timestamp getDatumAb(SeasonFilterValue period) {
		Date date = null;
		if (period.getId() == 1) {
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
		if (period.getId() == 2) {
			cal.add(3, -16);
		}
		date = cal.getTime();
		return new Timestamp(date.getTime());
	}

}
