// %1705119372:hoplugins.teamAnalyzer.manager%
package hoplugins.teamAnalyzer.manager;

import hoplugins.Commons;

import hoplugins.teamAnalyzer.vo.Team;

import plugins.ILigaTabellenEintrag;
import plugins.IMatchKurzInfo;
import plugins.IMatchLineup;
import plugins.IPaarung;
import plugins.ISpielePanel;
import plugins.ISpielplan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class TeamManager {
    //~ Static fields/initializers -----------------------------------------------------------------

    private static Map teams = null;
    private static boolean updated = false;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static Team getNextCupOpponent() {
        Team team = new Team();
        int teamId = Commons.getModel().getBasics().getTeamId();
        IMatchKurzInfo[] cupMatches = Commons.getModel().getMatchesKurzInfo(teamId,
                                                                            ISpielePanel.NUR_EIGENE_POKALSPIELE,
                                                                            false);
        IMatchKurzInfo[] friendlyMatches = Commons.getModel().getMatchesKurzInfo(teamId,
                                                                                 ISpielePanel.NUR_EIGENE_FREUNDSCHAFTSSPIELE,
                                                                                 false);
        List l = new ArrayList();

        l.addAll(Arrays.asList(friendlyMatches));
        l.addAll(Arrays.asList(cupMatches));

        Object[] matches = l.toArray();

        for (int i = 0; i < matches.length; i++) {
            IMatchKurzInfo match = (IMatchKurzInfo) matches[i];

            if (match.getMatchStatus() != IMatchKurzInfo.FINISHED) {
                if (match.getHeimID() == teamId) {
                    team.setName(match.getGastName());
                    team.setTeamId(match.getGastID());
                } else {
                    team.setName(match.getHeimName());
                    team.setTeamId(match.getHeimID());
                }

                return team;
            }
        }

        return team;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static Team getNextLeagueOpponent() {
        ISpielplan league = getDivisionMatches();

        if (league != null) {
            List matches = league.getPaarungenBySpieltag(Commons.getModel().getBasics().getSpieltag());

            for (Iterator iter = matches.iterator(); iter.hasNext();) {
                IPaarung element = (IPaarung) iter.next();

                if (element.getHeimId() == Commons.getModel().getBasics().getTeamId()) {
                    Team t = new Team();

                    t.setName(element.getGastName());
                    t.setTeamId(element.getGastId());

                    return t;
                }

                if (element.getGastId() == Commons.getModel().getBasics().getTeamId()) {
                    Team t = new Team();

                    t.setName(element.getHeimName());
                    t.setTeamId(element.getHeimId());

                    return t;
                }
            }
        }

        return getNextQualificationOpponent();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param teamId TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static Team getTeam(int teamId) {
        return (Team) teams.get(teamId + "");
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param teamId TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static boolean isTeamInList(int teamId) {
        if (teams.get(teamId + "") != null) {
            return true;
        }

        return false;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static Collection getTeams() {
        if (teams == null) {
            teams = new HashMap();

            List l = loadDivisionTeams();

            for (Iterator iter = l.iterator(); iter.hasNext();) {
                Team element = (Team) iter.next();

                teams.put(element.getTeamId() + "", element);
            }

            Team qualTeam = getNextQualificationOpponent();

            if (qualTeam.getTeamId() != 0) {
                teams.put(qualTeam.getTeamId() + "", qualTeam);
            }

            Team cupTeam = getNextCupOpponent();

            if (cupTeam.getTeamId() != 0) {
                teams.put(cupTeam.getTeamId() + "", cupTeam);
            }
        }

        return teams.values();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static boolean isUpdated() {
        updated = !updated;

        return !updated;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param team TODO Missing Method Parameter Documentation
     */
    public static void addFavouriteTeam(Team team) {
        if (!isTeamInList(team.getTeamId())) {
            teams.put(team.getTeamId() + "", team);
        }

        forceUpdate();
    }

    /**
     * TODO Missing Method Documentation
     */
    public static void clean() {
        teams = null;
        updated = true;
    }

    /**
     * TODO Missing Method Documentation
     */
    public static void forceUpdate() {
        updated = true;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private static ISpielplan getDivisionMatches() {
        ISpielplan league = Commons.getModel().getSpielplan(Commons.getModel().getXtraDaten()
                                                                   .getLeagueLevelUnitID(),
                                                            Commons.getModel().getBasics()
                                                                   .getSeason());

        return league;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private static Team getNextQualificationOpponent() {
        Team team = new Team();
        int teamId = Commons.getModel().getBasics().getTeamId();
        IMatchKurzInfo[] qualificationMatches = Commons.getModel().getMatchesKurzInfo(teamId,
                                                                                      ISpielePanel.NUR_EIGENE_PFLICHTSPIELE,
                                                                                      false);
        List l = new ArrayList();

        l.addAll(Arrays.asList(qualificationMatches));

        Object[] matches = l.toArray();

        for (int i = 0; i < matches.length; i++) {
            IMatchKurzInfo match = (IMatchKurzInfo) matches[i];

            if ((match.getMatchStatus() != IMatchKurzInfo.FINISHED)
                && (match.getMatchTyp() == IMatchLineup.QUALISPIEL)) {
                if (match.getHeimID() == teamId) {
                    team.setName(match.getGastName());
                    team.setTeamId(match.getGastID());
                } else {
                    team.setName(match.getHeimName());
                    team.setTeamId(match.getHeimID());
                }

                return team;
            }
        }

        return team;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private static List loadDivisionTeams() {
        List loadedTeams = new ArrayList();
        ISpielplan league = getDivisionMatches();

        if (league != null) {
            List eintraege = league.getTabelle().getEintraege();

            for (Iterator iter = eintraege.iterator(); iter.hasNext();) {
                ILigaTabellenEintrag element = (ILigaTabellenEintrag) iter.next();
                Team t = new Team();

                t.setName(element.getTeamName());
                t.setTeamId(element.getTeamId());
                loadedTeams.add(t);
            }
        }

        return loadedTeams;
    }
}
