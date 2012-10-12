package ho.module.specialEvents;

import ho.core.db.DBManager;
import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.HOVerwaltung;
import ho.core.model.match.IMatchDetails;
import ho.core.model.match.IMatchHighlight;
import ho.core.model.match.MatchHighlight;
import ho.core.model.match.MatchKurzInfo;
import ho.core.model.match.Matchdetails;
import ho.core.util.HOLogger;
import ho.module.matches.SpielHighlightPanel;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import javax.swing.ImageIcon;

class SpecialEventsDM {
	private static ImageIcon goalIcon;
	private static ImageIcon chanceIcon;
	private static ImageIcon leerIcon;
	private static ImageIcon homeEventIcon;
	private static ImageIcon guestEventIcon;
	private static ImageIcon wingIcon;
	private static ImageIcon babyIcon;
	private static ImageIcon homeEventIconNegative;
	private static ImageIcon guestEventIconNegative;
	private static ImageIcon ballZaubererIcon;
	private static ImageIcon schnellIcon;
	private static ImageIcon unberechenbarIcon;
	private static ImageIcon cornerIcon;
	private static ImageIcon cannonIcon;
	private static ImageIcon downIcon;
	private static ImageIcon oldmanIcon;
	private static ImageIcon counterIcon;
	private static ImageIcon weatherPositiveIcon;
	private static ImageIcon weatherNegativeIcon;
	private int teamId;
	private Vector<String> highlightText;

	public static final int SPECIALTYSE = 0;
	public static final int WEATHERSE = 1;
	public static final int COUNTER = 2;
	public static final int IFK = 3;
	public static final int LONGSHOT = 4;
	public static final int FREEKICK = 5;
	public static final int PENALTY = 6;
	private static final DateFormat DF = DateFormat.getDateInstance(DateFormat.MEDIUM,
			Locale.getDefault());
	private final Filter filter;

	public SpecialEventsDM(Filter filter) {
		this.filter = filter;
		homeEventIcon = ThemeManager.getIcon(HOIconName.ARROW_RIGHT1);
		guestEventIcon = ThemeManager.getIcon(HOIconName.ARROW_LEFT1);
		homeEventIconNegative = ThemeManager.getIcon(HOIconName.ARROW_RIGHT2);
		guestEventIconNegative = ThemeManager.getIcon(HOIconName.ARROW_LEFT2);
		goalIcon = ThemeManager.getIcon(HOIconName.GOAL);
		wingIcon = ThemeManager.getIcon(HOIconName.GOAL_RIGHT);
		babyIcon = ThemeManager.getIcon(HOIconName.GOAL_SPECIAL);
		chanceIcon = ThemeManager.getIcon(HOIconName.NOGOAL);
		leerIcon = ThemeManager.getIcon(HOIconName.EMPTY);
		cornerIcon = ThemeManager.getIcon(HOIconName.GOAL_SPECIAL);
		cannonIcon = ThemeManager.getIcon(HOIconName.GOAL_LONGSHOT);
		downIcon = ThemeManager.getIcon(HOIconName.GOAL_SPECIAL);
		oldmanIcon = ThemeManager.getIcon(HOIconName.GOAL_SPECIAL);
		counterIcon = ThemeManager.getIcon(HOIconName.GOAL_COUNTER);
		ballZaubererIcon = ThemeManager.getIcon(HOIconName.SPECIAL[1]);
		schnellIcon = ThemeManager.getIcon(HOIconName.SPECIAL[2]);
		unberechenbarIcon = ThemeManager.getIcon(HOIconName.SPECIAL[4]);
		weatherPositiveIcon = ThemeManager.getIcon(HOIconName.WEATHER_EFFECT_GOOD);
		weatherNegativeIcon = ThemeManager.getIcon(HOIconName.WEATHER_EFFECT_BAD);
		teamId = HOVerwaltung.instance().getModel().getBasics().getTeamId();
	}

	List<List<Object>> holeInfos(boolean allMatches, SeasonFilterValue period, boolean friendlies) {
		List<List<Object>> data = new ArrayList<List<Object>>();
		highlightText = new Vector<String>();
		try {
			List<MatchKurzInfo> kurzInfos = SpecialEventsDataAccess.getMatchKurzInfos(period,
					friendlies);
			int zInd = 1;
			for (Iterator<MatchKurzInfo> iter = kurzInfos.iterator(); iter.hasNext();) {
				MatchKurzInfo element = iter.next();
				List<List<Object>> v = getMatchlines(element, allMatches);
				if (v != null && v.size() > 0) {
					for (int j = 0; j < v.size(); j++) {
						if (j == 0) {
							zInd *= -1;
						}
						List<Object> vTemp = v.get(j);
						vTemp.set(SpecialEventsTableModel.HIDDENCOLUMN,
								(new Integer(zInd)).toString());
						data.add(vTemp);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			showDebug(e.toString());
		}
		return data;
	}

	private List<List<Object>> getMatchlines(MatchKurzInfo kurzInfos, boolean allMatches) {
		Matchdetails details = DBManager.instance().getMatchDetails(kurzInfos.getMatchID());
		String datum = getDateAsString(kurzInfos.getMatchDateAsTimestamp());
		Integer matchId = new Integer(kurzInfos.getMatchID());
		String heimTaktik = getTaktik(details.getHomeTacticType());
		String heimName = kurzInfos.getHeimName();
		String ergebnis = (new Integer(kurzInfos.getHeimTore())).toString() + " - "
				+ (new Integer(kurzInfos.getGastTore())).toString();
		int heimId = kurzInfos.getHeimID();
		String gastName = kurzInfos.getGastName();
		int gastId = kurzInfos.getGastID();
		String gastTaktik = getTaktik(details.getGuestTacticType());
		Vector<MatchHighlight> vHighlights = details.getHighlights();
		Vector<MatchHighlight> seHighlights = new Vector<MatchHighlight>();
		int weather = details.getWetterId();

		for (Iterator<MatchHighlight> iter = vHighlights.iterator(); iter.hasNext();) {
			MatchHighlight highlight = iter.next();
			if (checkForSE(highlight)) {
				seHighlights.add(highlight);
			}
		}

		List<List<Object>> lines = new ArrayList<List<Object>>();
		if (allMatches && seHighlights.size() == 0) {
			List<Object> allNoSELine = new ArrayList<Object>();
			allNoSELine.add(datum);
			allNoSELine.add(matchId);
			allNoSELine.add(heimTaktik);
			allNoSELine.add(null);
			allNoSELine.add(heimName);
			allNoSELine.add(ergebnis);
			allNoSELine.add(gastName);
			allNoSELine.add(null);
			allNoSELine.add(gastTaktik);
			allNoSELine.add("");
			allNoSELine.add("");
			allNoSELine.add("");
			allNoSELine.add("");
			allNoSELine.add("");
			allNoSELine.add("");
			lines.add(allNoSELine);
			highlightText.add("");
		} else {
			int lCounter = 0;
			for (Iterator<MatchHighlight> iter = vHighlights.iterator(); iter.hasNext();) {
				MatchHighlight highlight = iter.next();
				String se = format_Highlights(highlight);
				if (se != null && !se.equals("")) {
					Vector<Object> singleLine = new Vector<Object>();
					if (++lCounter == 1) {
						singleLine.add(datum);
						singleLine.add(matchId);
						singleLine.add(heimTaktik);
						singleLine.add(getOwnerIcon(highlight, true, heimId, gastId));
						singleLine.add(heimName);
						singleLine.add(ergebnis);
						singleLine.add(gastName);
						singleLine.add(getOwnerIcon(highlight, false, heimId, gastId));
						singleLine.add(gastTaktik);
					} else {
						singleLine.add("");
						singleLine.add("");
						singleLine.add("");
						singleLine.add(getOwnerIcon(highlight, true, heimId, gastId));
						singleLine.add("");
						singleLine.add("");
						singleLine.add("");
						singleLine.add(getOwnerIcon(highlight, false, heimId, gastId));
						singleLine.add("");
					}
					singleLine.add((new Integer(highlight.getMinute())).toString());
					if (highlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_ERFOLGREICH) {
						singleLine.add(goalIcon);
					} else if (highlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_FEHLGESCHLAGEN) {
						singleLine.add(chanceIcon);
					} else if (isWeatherSE(highlight)) {
						singleLine.add(ThemeManager.getIcon(HOIconName.WEATHER[weather]));
					} else {
						singleLine.add(null);
					}
					singleLine.add(getEventTypIcon(highlight));
					singleLine.add(se);
					singleLine.add(getSpielerName(highlight));
					singleLine.add("");
					lines.add(singleLine);
					highlightText.add(highlight.getEventText());
				}
			}

		}
		return lines;
	}

	private String getDateAsString(Timestamp date) {
		return DF.format(date);
	}

	private ImageIcon getEventTypIcon(MatchHighlight highlight) {
		if (isPositiveWeatherSE(highlight)) {
			return weatherPositiveIcon;
		} else if (isNegativeWeatherSE(highlight)) {
			return weatherNegativeIcon;
		} else if (highlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_ERFOLGREICH
				|| highlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_FEHLGESCHLAGEN) {
			// Non-weather SE
			switch (highlight.getHighlightSubTyp()) {
			case IMatchHighlight.HIGHLIGHT_SUB_WEITSCHUSS_TOR:
				return cannonIcon;
			case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_PASS_VORLAGE_TOR:
			case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_PASS_ABGEFANGEN_TOR:
			case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_BALL_ERKAEMPFT_TOR:
			case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_BALLVERLUST_TOR:
				return unberechenbarIcon;
			case IMatchHighlight.HIGHLIGHT_SUB_SCHNELLER_ANGREIFER_TOR:
			case IMatchHighlight.HIGHLIGHT_SUB_SCHNELLER_ANGREIFER_PASS_TOR:
				return schnellIcon;
			case IMatchHighlight.HIGHLIGHT_SUB_SCHLECHTE_KONDITION_BALLVERLUST_TOR:
				return downIcon;
			case IMatchHighlight.HIGHLIGHT_SUB_ECKBALL_TOR:
			case IMatchHighlight.HIGHLIGHT_SUB_ECKBALL_KOPFTOR:
				return cornerIcon;
			case IMatchHighlight.HIGHLIGHT_SUB_ERFAHRENER_ANGREIFER_TOR:
				return oldmanIcon;
			case IMatchHighlight.HIGHLIGHT_SUB_UNERFAHREN_TOR:
				return babyIcon;
			case IMatchHighlight.HIGHLIGHT_SUB_QUERPASS_TOR:
			case IMatchHighlight.HIGHLIGHT_SUB_AUSSERGEWOEHNLICHER_PASS_TOR:
				return wingIcon;
			case IMatchHighlight.HIGHLIGHT_SUB_TECHNIKER_ANGREIFER_TOR:
				return ballZaubererIcon;
			case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_EINS:
			case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_ZWEI:
			case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_DREI:
			case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_VIER:
			case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_FUENF:
				return counterIcon;
			case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS:
			case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_2:
			case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_3:
			case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_4:
			case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_5:
			case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_6:
			case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_7:
			case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_8:
			case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER:
			case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_2:
			case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_3:
			case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_4:
			case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_5:
			case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_6:
			case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_7:
			case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_8:
			case IMatchHighlight.HIGHLIGHT_SUB_INDIRECT_FREEKICK_1:
			case IMatchHighlight.HIGHLIGHT_SUB_INDIRECT_FREEKICK_2:
			case IMatchHighlight.HIGHLIGHT_SUB_LONGHSHOT_1:
			case IMatchHighlight.HIGHLIGHT_SUB_QUICK_RUSH_STOPPED_BY_DEF:
				return SpielHighlightPanel.getImageIcon4SpielHighlight(
						IMatchHighlight.HIGHLIGHT_ERFOLGREICH, highlight.getHighlightSubTyp()); // Always
																								// return
																								// the
																								// icon
																								// for
																								// "SUCCESS"
																								// because
																								// we
																								// only
																								// want
																								// the
																								// chance
																								// type
																								// icon
			}
		}
		return leerIcon;

	}

	private ImageIcon getOwnerIcon(MatchHighlight highlight, boolean home, int heimId, int gastId) {
		ImageIcon icon = null;
		if (home) {
			// Create home icon
			if (highlight.getTeamID() == heimId) {
				if (isPositiveWeatherSE(highlight)) {
					// Positive weather SE for home team
					icon = homeEventIcon;
				} else if (isNegativeWeatherSE(highlight)) {
					// Negative weather SE for home team
					icon = homeEventIconNegative;
				} else if (!isNegativeSE(highlight)) {
					// Positive non-weather SE for home
					icon = homeEventIcon;
				}
			} else {
				if (!isWeatherSE(highlight) && isNegativeSE(highlight)) {
					// Negative non-weather SE against home team
					icon = homeEventIconNegative;
				}
			}
		} else {
			// Create guest icon
			if (highlight.getTeamID() == gastId) {
				if (isPositiveWeatherSE(highlight)) {
					// Positive weather SE for guest team
					icon = guestEventIcon;
				} else if (isNegativeWeatherSE(highlight)) {
					// Negative weather SE for guest team
					icon = guestEventIconNegative;
				} else if (!isNegativeSE(highlight)) {
					// Positive non-weather SE for guest
					icon = guestEventIcon;
				}
			} else {
				if (!isWeatherSE(highlight) && isNegativeSE(highlight)) {
					// Negative non-weather SE against guest team
					icon = guestEventIconNegative;
				}
			}
		}

		return icon;
	}

	public static boolean isNegativeSE(MatchHighlight highlight) {
		if (highlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_ERFOLGREICH
				|| highlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_FEHLGESCHLAGEN) {
			// Chances (miss/goal)
			if (highlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_SCHLECHTE_KONDITION_BALLVERLUST_TOR
					|| highlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_UNERFAHREN_TOR
					|| highlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_BALLVERLUST_TOR) {
				return true;
			}
		} else if (isNegativeWeatherSE(highlight)) {
			// negative Weather SE
			return true;
		}
		return false;
	}

	private static int getEventType(MatchHighlight highlight) {
		if (isWeatherSE(highlight)) {
			return WEATHERSE;
		} else if (highlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_ERFOLGREICH
				|| highlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_FEHLGESCHLAGEN) {
			switch (highlight.getHighlightSubTyp()) {
			case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_PASS_VORLAGE_TOR:
			case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_PASS_ABGEFANGEN_TOR:
			case IMatchHighlight.HIGHLIGHT_SUB_WEITSCHUSS_TOR:
			case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_BALL_ERKAEMPFT_TOR:
			case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_BALLVERLUST_TOR:
			case IMatchHighlight.HIGHLIGHT_SUB_SCHNELLER_ANGREIFER_TOR:
			case IMatchHighlight.HIGHLIGHT_SUB_SCHNELLER_ANGREIFER_PASS_TOR:
			case IMatchHighlight.HIGHLIGHT_SUB_SCHLECHTE_KONDITION_BALLVERLUST_TOR:
			case IMatchHighlight.HIGHLIGHT_SUB_ECKBALL_TOR:
			case IMatchHighlight.HIGHLIGHT_SUB_ECKBALL_KOPFTOR:
			case IMatchHighlight.HIGHLIGHT_SUB_ERFAHRENER_ANGREIFER_TOR:
			case IMatchHighlight.HIGHLIGHT_SUB_UNERFAHREN_TOR:
			case IMatchHighlight.HIGHLIGHT_SUB_QUERPASS_TOR:
			case IMatchHighlight.HIGHLIGHT_SUB_AUSSERGEWOEHNLICHER_PASS_TOR:
			case IMatchHighlight.HIGHLIGHT_SUB_TECHNIKER_ANGREIFER_TOR:
				return SPECIALTYSE;
			case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_EINS:
			case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_ZWEI:
			case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_DREI:
			case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_VIER:
			case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_FUENF:
				return COUNTER;
			case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS:
			case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_2:
			case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_3:
			case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_4:
			case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_5:
			case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_6:
			case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_7:
			case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_8:
				return FREEKICK;
			case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER:
			case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_2:
			case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_3:
			case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_4:
			case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_5:
			case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_6:
			case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_7:
			case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_8:
				return PENALTY;
			case IMatchHighlight.HIGHLIGHT_SUB_INDIRECT_FREEKICK_1:
			case IMatchHighlight.HIGHLIGHT_SUB_INDIRECT_FREEKICK_2:
				return IFK;
			case IMatchHighlight.HIGHLIGHT_SUB_LONGHSHOT_1:
				return LONGSHOT;
			}
		}
		return -1;
	}

	private boolean checkForSE(MatchHighlight highlight) {
		int eventType = getEventType(highlight);
		if (eventType < 0) {
			return false;
		} else if (!this.filter.isShowSpecialitySE() && eventType == SPECIALTYSE) {
			return false;
		} else if (!this.filter.isShowWeatherSE() && eventType == WEATHERSE) {
			return false;
		} else if (!this.filter.isShowCounterAttack() && eventType == COUNTER) {
			return false;
		} else if (!this.filter.isShowFreeKick() && eventType == FREEKICK) {
			return false;
		} else if (!this.filter.isShowPenalty() && eventType == PENALTY) {
			return false;
		} else if (!this.filter.isShowFreeKickIndirect() && eventType == IFK) {
			return false;
		} else if (!this.filter.isShowLongShot() && eventType == LONGSHOT) {
			return false;
		}
		return true;
	}

	private static boolean isWeatherSE(MatchHighlight highlight) {
		return (isPositiveWeatherSE(highlight) || isNegativeWeatherSE(highlight));
	}

	private static boolean isPositiveWeatherSE(MatchHighlight highlight) {
		if (highlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_SPEZIAL) {
			if (highlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_PLAYER_POWERFUL_RAINY
					|| highlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_PLAYER_TECHNICAL_SUNNY) {
				return true;
			}
		}
		return false;
	}

	private static boolean isNegativeWeatherSE(MatchHighlight highlight) {
		if (highlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_SPEZIAL) {
			if (highlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_PLAYER_TECHNICAL_RAINY
					|| highlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_PLAYER_POWERFUL_SUNNY
					|| highlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_PLAYER_QUICK_RAINY
					|| highlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_PLAYER_QUICK_SUNNY) {
				return true;
			}
		}
		return false;
	}

	private String format_Highlights(MatchHighlight highlight) {
		String rString = "";
		if (checkForSE(highlight)) {
			rString = getSEText(highlight);
		}
		return rString;
	}

	private String getSEText(MatchHighlight highlight) {

		if (isWeatherSE(highlight)) {
			switch (highlight.getHighlightSubTyp()) {
			case IMatchHighlight.HIGHLIGHT_SUB_PLAYER_TECHNICAL_RAINY:
				return HOVerwaltung.instance().getLanguageString("WEATHER_TECHNICAL_RAINY");
			case IMatchHighlight.HIGHLIGHT_SUB_PLAYER_POWERFUL_RAINY:
				return HOVerwaltung.instance().getLanguageString("WEATHER_POWERFUL_RAINY");
			case IMatchHighlight.HIGHLIGHT_SUB_PLAYER_TECHNICAL_SUNNY:
				return HOVerwaltung.instance().getLanguageString("WEATHER_TECHNICAL_SUNNY");
			case IMatchHighlight.HIGHLIGHT_SUB_PLAYER_POWERFUL_SUNNY:
				return HOVerwaltung.instance().getLanguageString("WEATHER_POWERFUL_SUNNY");
			case IMatchHighlight.HIGHLIGHT_SUB_PLAYER_QUICK_RAINY:
				return HOVerwaltung.instance().getLanguageString("WEATHER_QUICK_RAINY");
			case IMatchHighlight.HIGHLIGHT_SUB_PLAYER_QUICK_SUNNY:
				return HOVerwaltung.instance().getLanguageString("WEATHER_QUICK_SUNNY");
			}
		} else if (highlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_ERFOLGREICH
				|| highlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_FEHLGESCHLAGEN) {
			// Non-weather SE
			switch (highlight.getHighlightSubTyp()) {
			case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_PASS_VORLAGE_TOR:
				return HOVerwaltung.instance().getLanguageString("UNVORHERSEHBAR_PASS_VORLAGE_TOR");

			case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_PASS_ABGEFANGEN_TOR:
				return HOVerwaltung.instance().getLanguageString(
						"UNVORHERSEHBAR_PASS_ABGEFANGEN_TOR");

			case IMatchHighlight.HIGHLIGHT_SUB_WEITSCHUSS_TOR:
				return HOVerwaltung.instance().getLanguageString("WEITSCHUSS_TOR");

			case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_BALL_ERKAEMPFT_TOR:
				return HOVerwaltung.instance().getLanguageString(
						"UNVORHERSEHBAR_BALL_ERKAEMPFT_TOR");

			case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_BALLVERLUST_TOR:
				return HOVerwaltung.instance().getLanguageString("UNVORHERSEHBAR_BALLVERLUST_TOR");

			case IMatchHighlight.HIGHLIGHT_SUB_SCHNELLER_ANGREIFER_TOR:
				return HOVerwaltung.instance().getLanguageString("SCHNELLER_ANGREIFER_TOR");

			case IMatchHighlight.HIGHLIGHT_SUB_SCHNELLER_ANGREIFER_PASS_TOR:
				return HOVerwaltung.instance().getLanguageString("SCHNELLER_ANGREIFER_PASS_TOR");

			case IMatchHighlight.HIGHLIGHT_SUB_SCHLECHTE_KONDITION_BALLVERLUST_TOR:
				return HOVerwaltung.instance().getLanguageString(
						"SCHLECHTE_KONDITION_BALLVERLUST_TOR");

			case IMatchHighlight.HIGHLIGHT_SUB_ECKBALL_TOR:
				return HOVerwaltung.instance().getLanguageString("ECKBALL_TOR");

			case IMatchHighlight.HIGHLIGHT_SUB_ECKBALL_KOPFTOR:
				return HOVerwaltung.instance().getLanguageString("ECKBALL_KOPFTOR");

			case IMatchHighlight.HIGHLIGHT_SUB_ERFAHRENER_ANGREIFER_TOR:
				return HOVerwaltung.instance().getLanguageString("ERFAHRENER_ANGREIFER_TOR");

			case IMatchHighlight.HIGHLIGHT_SUB_UNERFAHREN_TOR:
				return HOVerwaltung.instance().getLanguageString("UNERFAHREN_TOR");

			case IMatchHighlight.HIGHLIGHT_SUB_QUERPASS_TOR:
				return HOVerwaltung.instance().getLanguageString("QUERPASS_TOR");

			case IMatchHighlight.HIGHLIGHT_SUB_AUSSERGEWOEHNLICHER_PASS_TOR:
				return HOVerwaltung.instance().getLanguageString("AUSSERGEWOEHNLICHER_PASS_TOR");

			case IMatchHighlight.HIGHLIGHT_SUB_TECHNIKER_ANGREIFER_TOR:
				return HOVerwaltung.instance().getLanguageString("TECHNIKER_ANGREIFER_TOR");
			case IMatchHighlight.HIGHLIGHT_SUB_QUICK_RUSH_STOPPED_BY_DEF:
				return HOVerwaltung.instance().getLanguageString("QUICK_RUSH_STOPPED_BY_DEF");
			case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_EINS:
			case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_ZWEI:
			case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_DREI:
			case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_VIER:
			case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_FUENF:
				return HOVerwaltung.instance().getLanguageString("ls.match.event.counter-attack");

			case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS:
			case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_2:
			case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_3:
			case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_4:
			case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_5:
			case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_6:
			case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_7:
			case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_8:
				return HOVerwaltung.instance().getLanguageString("highlight_freekick");

			case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER:
			case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_2:
			case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_3:
			case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_4:
			case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_5:
			case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_6:
			case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_7:
			case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_8:
				return HOVerwaltung.instance().getLanguageString("highlight_penalty");

			case IMatchHighlight.HIGHLIGHT_SUB_INDIRECT_FREEKICK_1:
			case IMatchHighlight.HIGHLIGHT_SUB_INDIRECT_FREEKICK_2:
				return HOVerwaltung.instance().getLanguageString("highlight_freekick") + " "
						+ HOVerwaltung.instance().getLanguageString("indirect");

			case IMatchHighlight.HIGHLIGHT_SUB_LONGHSHOT_1:
				return HOVerwaltung.instance().getLanguageString("ls.match.event.longshot");
			}

		}
		// return HOVerwaltung.instance().getLanguageString("Unbekannt");
		return "unknown";
	}

	private String getTaktik(int typ) {
		switch (typ) {
		case IMatchDetails.TAKTIK_NORMAL:
			return " ";

		case IMatchDetails.TAKTIK_PRESSING:
			return "PR";

		case IMatchDetails.TAKTIK_KONTER:
			return "CA";

		case IMatchDetails.TAKTIK_MIDDLE:
			return "AIM";

		case IMatchDetails.TAKTIK_WINGS:
			return "AOW";

		case IMatchDetails.TAKTIK_CREATIVE:
			return "PC";

		case IMatchDetails.TAKTIK_LONGSHOTS:
			return "LS";

		default:
			return " ? " + (new Integer(typ)).toString();
		}
	}

	private String findName(MatchHighlight highlight) {
		if (isWeatherSE(highlight)) {
			return highlight.getSpielerName();
		} else if (highlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_ERFOLGREICH
				|| highlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_FEHLGESCHLAGEN) {
			switch (highlight.getHighlightSubTyp()) {
			case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_PASS_VORLAGE_TOR:
				return highlight.getGehilfeName() + " - " + highlight.getSpielerName();
			case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_PASS_ABGEFANGEN_TOR:
				return highlight.getSpielerName();
			case IMatchHighlight.HIGHLIGHT_SUB_WEITSCHUSS_TOR:
				return highlight.getSpielerName();
			case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_BALL_ERKAEMPFT_TOR:
				return highlight.getGehilfeName() + " - " + highlight.getSpielerName();
			case IMatchHighlight.HIGHLIGHT_SUB_SCHNELLER_ANGREIFER_TOR:
				return highlight.getSpielerName();
			case IMatchHighlight.HIGHLIGHT_SUB_SCHNELLER_ANGREIFER_PASS_TOR:
				return highlight.getGehilfeName() + " - " + highlight.getSpielerName();
			case IMatchHighlight.HIGHLIGHT_SUB_ECKBALL_TOR:
				return highlight.getGehilfeName() + " - " + highlight.getSpielerName();
			case IMatchHighlight.HIGHLIGHT_SUB_ECKBALL_KOPFTOR:
				return highlight.getGehilfeName() + " - " + highlight.getSpielerName();
			case IMatchHighlight.HIGHLIGHT_SUB_ERFAHRENER_ANGREIFER_TOR:
				return highlight.getSpielerName();
			case IMatchHighlight.HIGHLIGHT_SUB_QUERPASS_TOR:
				return highlight.getGehilfeName() + " - " + highlight.getSpielerName();
			case IMatchHighlight.HIGHLIGHT_SUB_AUSSERGEWOEHNLICHER_PASS_TOR:
				return highlight.getGehilfeName() + " - " + highlight.getSpielerName();
			case IMatchHighlight.HIGHLIGHT_SUB_TECHNIKER_ANGREIFER_TOR:
				return highlight.getSpielerName();
			case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_EINS:
			case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_ZWEI:
			case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_DREI:
			case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_VIER:
			case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_FUENF:
				return highlight.getSpielerName();

			case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS:
			case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_2:
			case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_3:
			case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_4:
			case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_5:
			case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_6:
			case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_7:
			case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_8:
				return highlight.getSpielerName();

			case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER:
			case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_2:
			case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_3:
			case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_4:
			case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_5:
			case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_6:
			case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_7:
			case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_8:
				return highlight.getSpielerName();

			case IMatchHighlight.HIGHLIGHT_SUB_INDIRECT_FREEKICK_1:
			case IMatchHighlight.HIGHLIGHT_SUB_INDIRECT_FREEKICK_2:
				return highlight.getSpielerName();

			case IMatchHighlight.HIGHLIGHT_SUB_LONGHSHOT_1:
				return highlight.getSpielerName();
			}
		}
		return "?";
	}

	private String getSpielerName(MatchHighlight highlight) {
		String name = "";
		// if(highlight.getTeamID() == teamId && !isNegativeSE(highlight))
		if (highlight.getTeamID() == teamId) {
			// Our team has an SE
			if (!isNegativeSE(highlight)) {
				name = findName(highlight) + "|*"; // positive SE (our player)
													// -> black
			} else if (isNegativeWeatherSE(highlight)) {
				name = findName(highlight) + "|-"; // negative weather SE (our
													// player) -> red
			} else {
				// Negative SE of other Team
				name = highlight.getGehilfeName() + "|#"; // negative SE (other
															// player helps our
															// team) -> gray
			}
		} else {
			// other team has an SE
			if (!isWeatherSE(highlight) && isNegativeSE(highlight)) {
				name = highlight.getGehilfeName() + "|-"; // negative SE (our
															// player helps the
															// other team) ->
															// red
			} else {
				name = findName(highlight) + "|#"; // SE from other team -> gray
			}
		}
		return name;
	}

	private void showDebug(String s) {
		HOLogger.instance().log(this.getClass(), s);
	}

	public Vector<String> getHighlightText() {
		return highlightText;
	}
}
