package hoplugins.specialEvents;

import hoplugins.commons.utils.PluginProperty;

import java.awt.Dimension;
import java.awt.Point;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import javax.swing.ImageIcon;

import plugins.IDebugWindow;
import plugins.IHOMiniModel;
import plugins.IMatchDetails;
import plugins.IMatchHighlight;
import plugins.IMatchKurzInfo;
import plugins.IMatchLineup;
import plugins.IMatchLineupPlayer;
import plugins.IMatchLineupTeam;

public class SpecialEventsDM
{
//    private Properties props;
    private IHOMiniModel miniModel;
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

    private static final DateFormat DF = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());

    public SpecialEventsDM(IHOMiniModel miniModel)
    {
        this.miniModel = miniModel;
        homeEventIcon = createImageIcon(this, "/hoplugins/specialEvents/img/prechts.gif");
        guestEventIcon = createImageIcon(this, "/hoplugins/specialEvents/img/plinks.gif");
        homeEventIconNegative = createImageIcon(this, "/hoplugins/specialEvents/img/prechtsrot.gif");
        guestEventIconNegative = createImageIcon(this, "/hoplugins/specialEvents/img/plinksrot.gif");
        goalIcon = createImageIcon(this, "/hoplugins/specialEvents/img/goal.gif");
        wingIcon = createImageIcon(this, "/hoplugins/specialEvents/img/wing2.gif");
        babyIcon = createImageIcon(this, "/hoplugins/specialEvents/img/baby1.gif");
        chanceIcon = createImageIcon(this, "/hoplugins/specialEvents/img/chance.gif");
        leerIcon = createImageIcon(this, "/hoplugins/specialEvents/img/leer.gif");
        cornerIcon = createImageIcon(this, "/hoplugins/specialEvents/img/corner.gif");
        cannonIcon = createImageIcon(this, "/hoplugins/specialEvents/img/cannon.gif");
        downIcon = createImageIcon(this, "/hoplugins/specialEvents/img/down.gif");
        oldmanIcon = createImageIcon(this, "/hoplugins/specialEvents/img/oldman.gif");
        counterIcon = createImageIcon(this, "/hoplugins/specialEvents/img/counter.gif");
        ballZaubererIcon = miniModel.getHelper().getImageIcon4Spezialitaet(1);
        schnellIcon = miniModel.getHelper().getImageIcon4Spezialitaet(2);
        unberechenbarIcon = miniModel.getHelper().getImageIcon4Spezialitaet(4);
        weatherPositiveIcon= miniModel.getHelper().getImageIcon4WetterEffekt(1);
        weatherNegativeIcon= miniModel.getHelper().getImageIcon4WetterEffekt(-1);
        teamId = miniModel.getBasics().getTeamId();
    }

    public Vector<Vector<Object>> holeInfos(boolean allMatches, int saisonAnz, boolean friendlies) {
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		highlightText = new Vector<String>();
		try {
			Vector<IMatchKurzInfo> kurzInfos = SpecialEventsDataAccess.getCurrent().getAktMatchKurzInfos(saisonAnz, friendlies);
			int zInd = 1;
			for (Iterator<IMatchKurzInfo> iter = kurzInfos.iterator(); iter
					.hasNext();) {
				IMatchKurzInfo element = (IMatchKurzInfo) iter.next();
				Vector<Vector<Object>> v = getMatchlines(element, allMatches, saisonAnz);
				if (v != null && v.size() > 0) {
					for (int j = 0; j < v.size(); j++) {
						if (j == 0) {
							zInd *= -1;
						}
						Vector<Object> vTemp = v.elementAt(j);
						vTemp.setElementAt((new Integer(zInd)).toString(),
								SpecialEventsPanel.HIDDENCOLUMN);
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

    private Vector<Vector<Object>> getMatchlines(IMatchKurzInfo kurzInfos, boolean allMatches, int saisonAnz) {
    	IMatchDetails details = miniModel.getMatchDetails(kurzInfos.getMatchID());
        String datum = getDateAsString(kurzInfos.getMatchDateAsTimestamp());
        Integer matchId = new Integer(kurzInfos.getMatchID());
        String heimTaktik = getTaktik(details.getHomeTacticType());
        String heimName = kurzInfos.getHeimName();
        String ergebnis = (new Integer(kurzInfos.getHeimTore())).toString() + " - " + (new Integer(kurzInfos.getGastTore())).toString();
        int heimId = kurzInfos.getHeimID();
        String gastName = kurzInfos.getGastName();
        int gastId = kurzInfos.getGastID();
        String gastTaktik = getTaktik(details.getGuestTacticType());
        Vector<IMatchHighlight> vHighlights = details.getHighlights();
        Vector<IMatchHighlight> seHighlights = new Vector<IMatchHighlight>();
        int weather = details.getWetterId();

        for (Iterator<IMatchHighlight> iter = vHighlights.iterator(); iter.hasNext();) {
			IMatchHighlight highlight = (IMatchHighlight) iter.next();
			if (checkForSE(highlight)) {
				seHighlights.add(highlight);
			}
        }

        Vector<Vector<Object>> lines = new Vector<Vector<Object>>();
        if(allMatches && seHighlights.size() == 0) {
            Vector<Object> allNoSELine = new Vector<Object>();
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
            for(Iterator<IMatchHighlight> iter = vHighlights.iterator(); iter.hasNext();) {
                IMatchHighlight highlight = iter.next();
                String se = format_Highlights(highlight);
                if(se != null && !se.equals("")) {
                    Vector<Object> singleLine = new Vector<Object>();
                    if(++lCounter == 1)  {
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
                    if(highlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_ERFOLGREICH) {
                        singleLine.add(goalIcon);
                    } else if (highlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_FEHLGESCHLAGEN) {
                        singleLine.add(chanceIcon);
                    } else if (isWeatherSE(highlight)){
                        singleLine.add(miniModel.getHelper().getImageIcon4Wetter(weather));
                    } else {
                    	singleLine.add(null);
                    }
                    singleLine.add(getEventTypIcon(highlight));
                    singleLine.add(se);
                    singleLine.add(getSpielerName(highlight, kurzInfos.getMatchID()));
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

    private ImageIcon getEventTypIcon(IMatchHighlight highlight) {
    	if (isPositiveWeatherSE(highlight)) {
    		return weatherPositiveIcon;
    	} else if (isNegativeWeatherSE(highlight)) {
    		return weatherNegativeIcon;
    	} else if (highlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_ERFOLGREICH
    			|| highlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_FEHLGESCHLAGEN ) {
    		// Non-weather SE
    		switch(highlight.getHighlightSubTyp()) {
    		case IMatchHighlight.HIGHLIGHT_SUB_WEITSCHUSS_TOR:
    			return cannonIcon;
    		case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_PASS_VORLAGE_TOR:
    		case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_PASS_ABGEFANGEN_TOR:
    		case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_BALL_ERKAEMPFT_TOR:
    		case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_BALLVERLUST_TOR:
    		case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_EIGENTOR:
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
    			return miniModel.getHelper().getImageIcon4SpielHighlight(IMatchHighlight.HIGHLIGHT_ERFOLGREICH,
    					highlight.getHighlightSubTyp()); // Always return the icon for "SUCCESS" because we only want the chance type icon
    		}
    	}
    	return leerIcon;

    }

    private ImageIcon getOwnerIcon(IMatchHighlight highlight, boolean home, int heimId, int gastId)
    {
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

    public static boolean isNegativeSE(IMatchHighlight highlight)
    {
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

    private static int getEventType (IMatchHighlight highlight) {
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

    private static boolean checkForSE(IMatchHighlight highlight)
    {
    	int eventType = getEventType(highlight);
    	if (eventType < 0) {
    		return false;
    	} else if (!FilterPanel.showSpecialtySE() && eventType == SPECIALTYSE) {
    		return false;
    	} else if (!FilterPanel.showWeatherSE() && eventType == WEATHERSE) {
    		return false;
    	} else if (!FilterPanel.showCounter() && eventType == COUNTER) {
    		return false;
    	} else if (!FilterPanel.showFreekick() && eventType == FREEKICK) {
    		return false;
    	} else if (!FilterPanel.showPenalty() && eventType == PENALTY) {
    		return false;
    	} else if (!FilterPanel.showIFK() && eventType == IFK) {
    		return false;
    	} else if (!FilterPanel.showLongShot() && eventType == LONGSHOT) {
    		return false;
    	}
    	return true;
    }

    private static boolean isWeatherSE (IMatchHighlight highlight) {
    	return (isPositiveWeatherSE(highlight) || isNegativeWeatherSE(highlight));
    }

    private static boolean isPositiveWeatherSE (IMatchHighlight highlight) {
    	if (highlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_SPEZIAL) {
    		if (highlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_PLAYER_POWERFUL_RAINY
    				|| highlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_PLAYER_TECHNICAL_SUNNY) {
    			return true;
    		}
    	}
    	return false;
    }

    private static boolean isNegativeWeatherSE (IMatchHighlight highlight) {
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

    private String format_Highlights(IMatchHighlight highlight)
    {
        String rString = "";
        if(checkForSE (highlight))
        {
            rString = getSEText(highlight);
        }
        return rString;
    }

    private String getSEText (IMatchHighlight highlight)
    {

    	if (isWeatherSE(highlight)) {
    		switch (highlight.getHighlightSubTyp()) {
    		case IMatchHighlight.HIGHLIGHT_SUB_PLAYER_TECHNICAL_RAINY:
    			return PluginProperty.getString("WEATHER_TECHNICAL_RAINY");
    		case IMatchHighlight.HIGHLIGHT_SUB_PLAYER_POWERFUL_RAINY:
    			return PluginProperty.getString("WEATHER_POWERFUL_RAINY");
    		case IMatchHighlight.HIGHLIGHT_SUB_PLAYER_TECHNICAL_SUNNY:
    			return PluginProperty.getString("WEATHER_TECHNICAL_SUNNY");
    		case IMatchHighlight.HIGHLIGHT_SUB_PLAYER_POWERFUL_SUNNY:
    			return PluginProperty.getString("WEATHER_POWERFUL_SUNNY");
    		case IMatchHighlight.HIGHLIGHT_SUB_PLAYER_QUICK_RAINY:
    			return PluginProperty.getString("WEATHER_QUICK_RAINY");
    		case IMatchHighlight.HIGHLIGHT_SUB_PLAYER_QUICK_SUNNY:
    			return PluginProperty.getString("WEATHER_QUICK_SUNNY");
    		}
    	} else if (highlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_ERFOLGREICH
    			|| highlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_FEHLGESCHLAGEN ) {
    		// Non-weather SE
    		switch (highlight.getHighlightSubTyp())
    		{
    		case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_PASS_VORLAGE_TOR:
    			return PluginProperty.getString("UNVORHERSEHBAR_PASS_VORLAGE_TOR");

    		case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_PASS_ABGEFANGEN_TOR:
    			return PluginProperty.getString("UNVORHERSEHBAR_PASS_ABGEFANGEN_TOR");

    		case IMatchHighlight.HIGHLIGHT_SUB_WEITSCHUSS_TOR:
    			return PluginProperty.getString("WEITSCHUSS_TOR");

    		case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_BALL_ERKAEMPFT_TOR:
    			return PluginProperty.getString("UNVORHERSEHBAR_BALL_ERKAEMPFT_TOR");

    		case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_EIGENTOR:
    			return PluginProperty.getString("UNVORHERSEHBAR_EIGENTOR");
    		case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_BALLVERLUST_TOR:
    			return PluginProperty.getString("UNVORHERSEHBAR_BALLVERLUST_TOR");

    		case IMatchHighlight.HIGHLIGHT_SUB_SCHNELLER_ANGREIFER_TOR:
    			return PluginProperty.getString("SCHNELLER_ANGREIFER_TOR");

    		case IMatchHighlight.HIGHLIGHT_SUB_SCHNELLER_ANGREIFER_PASS_TOR:
    			return PluginProperty.getString("SCHNELLER_ANGREIFER_PASS_TOR");

    		case IMatchHighlight.HIGHLIGHT_SUB_SCHLECHTE_KONDITION_BALLVERLUST_TOR:
    			return PluginProperty.getString("SCHLECHTE_KONDITION_BALLVERLUST_TOR");

    		case IMatchHighlight.HIGHLIGHT_SUB_ECKBALL_TOR:
    			return PluginProperty.getString("ECKBALL_TOR");

    		case IMatchHighlight.HIGHLIGHT_SUB_ECKBALL_KOPFTOR:
    			return PluginProperty.getString("ECKBALL_KOPFTOR");

    		case IMatchHighlight.HIGHLIGHT_SUB_ERFAHRENER_ANGREIFER_TOR:
    			return PluginProperty.getString("ERFAHRENER_ANGREIFER_TOR");

    		case IMatchHighlight.HIGHLIGHT_SUB_UNERFAHREN_TOR:
    			return PluginProperty.getString("UNERFAHREN_TOR");

    		case IMatchHighlight.HIGHLIGHT_SUB_QUERPASS_TOR:
    			return PluginProperty.getString("QUERPASS_TOR");

    		case IMatchHighlight.HIGHLIGHT_SUB_AUSSERGEWOEHNLICHER_PASS_TOR:
    			return PluginProperty.getString("AUSSERGEWOEHNLICHER_PASS_TOR");

    		case IMatchHighlight.HIGHLIGHT_SUB_TECHNIKER_ANGREIFER_TOR:
    			return PluginProperty.getString("TECHNIKER_ANGREIFER_TOR");
    		case IMatchHighlight.HIGHLIGHT_SUB_QUICK_RUSH_STOPPED_BY_DEF:
    			return PluginProperty.getString("QUICK_RUSH_STOPPED_BY_DEF");
    		case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_EINS:
    		case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_ZWEI:
    		case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_DREI:
    		case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_VIER:
    		case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_FUENF:
    			return PluginProperty.getString("COUNTER");

    		case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS:
    		case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_2:
    		case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_3:
    		case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_4:
    		case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_5:
    		case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_6:
    		case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_7:
    		case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_8:
    			return PluginProperty.getString("FREEKICK");

    		case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER:
    		case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_2:
    		case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_3:
    		case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_4:
    		case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_5:
    		case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_6:
    		case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_7:
    		case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_8:
    			return PluginProperty.getString("PENALTY");

    		case IMatchHighlight.HIGHLIGHT_SUB_INDIRECT_FREEKICK_1:
    		case IMatchHighlight.HIGHLIGHT_SUB_INDIRECT_FREEKICK_2:
    			return PluginProperty.getString("IFK");

    		case IMatchHighlight.HIGHLIGHT_SUB_LONGHSHOT_1:
    			return PluginProperty.getString("LONGSHOT");
    		}


    	}
//		return PluginProperty.getString("Unbekannt");
		return "unknown";
    }

    private String getTaktik(int typ)
    {
        switch(typ)
        {
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

    private String findName(IMatchHighlight highlight, int matchId)
    {
        if (isWeatherSE(highlight)) {
        	return highlight.getSpielerName();
        } else if (highlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_ERFOLGREICH
        			|| highlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_FEHLGESCHLAGEN){
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

    private String getSpielerName(IMatchHighlight highlight, int matchId)
    {
    	String name = "";
    	//        if(highlight.getTeamID() == teamId && !isNegativeSE(highlight))
    	if(highlight.getTeamID() == teamId) {
    		// Our team has an SE
    		if (!isNegativeSE(highlight)) {
    			name = findName(highlight, matchId) + "|*"; // positive SE (our player) -> black
    		} else if (isNegativeWeatherSE(highlight)){
    			name = findName(highlight, matchId) + "|-"; // negative weather SE (our player) -> red
    		} else {
    			// Negative SE of other Team
                name = highlight.getGehilfeName() + "|#"; // negative SE (other player helps our team) -> gray
    		}
    	} else {
    		// other team has an SE
    		if (!isWeatherSE(highlight) && isNegativeSE(highlight)) {
                name = highlight.getGehilfeName() + "|-"; // negative SE (our player helps the other team) -> red
            } else {
            	name = findName (highlight, matchId) + "|#"; // SE from other team -> gray
            }
    	}
        return name;
    }

    private String getStandardsSpielerName(int matchId)
    {
        String name = "";
        IMatchLineup matchLineup = miniModel.getMatchLineup(matchId);
        IMatchLineupTeam teamLineup = null;
        if(matchLineup.getHeimId() == teamId)
        {
            teamLineup = matchLineup.getHeim();
        } else
        {
            teamLineup = matchLineup.getGast();
        }
        IMatchLineupPlayer player = teamLineup.getPlayerByPosition(17);
        name = player.getSpielerName();
        return name;
    }

    private void showDebug(String s) {
		IDebugWindow debugWindow = miniModel.getGUI().createDebugWindow(new Point(100, 200), new Dimension(700, 400));
		debugWindow.setVisible(true);
		debugWindow.append("SpecialEvents Plugin: " + s);
	}

    protected static ImageIcon createImageIcon(Object object, String path)
    {
        java.net.URL imageURL = object.getClass().getResource(path);
        if(imageURL == null)
        {
            System.err.println("Resource not found: " + path);
            return null;
        } else
        {
            return new ImageIcon(imageURL);
        }
    }

    public Vector<String> getHighlightText()
    {
        return highlightText;
    }
}
