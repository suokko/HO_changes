// %1705119372:hoplugins.teamAnalyzer.manager%
package ho.module.teamAnalyzer.manager;

import ho.core.db.DBManager;
import ho.core.model.HOVerwaltung;
import ho.module.teamAnalyzer.vo.Team;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import plugins.ILigaTabellenEintrag;
import plugins.IMatchKurzInfo;
import plugins.IMatchLineup;
import plugins.IPaarung;
import plugins.ISpielePanel;
import plugins.ISpielplan;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class TeamManager {
    //~ Static fields/initializers -----------------------------------------------------------------

    private static Map<String, Team> teams = null;
    private static boolean updated = false;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static Team getNextCupOpponent() {
        Team team = new Team();
        int teamId = HOVerwaltung.instance().getModel().getBasics().getTeamId();
        IMatchKurzInfo[] cupMatches =DBManager.instance().getMatchesKurzInfo(teamId,
                                                                            ISpielePanel.NUR_EIGENE_POKALSPIELE,
                                                                            false);
        IMatchKurzInfo[] friendlyMatches = DBManager.instance().getMatchesKurzInfo(teamId,
                                                                                 ISpielePanel.NUR_EIGENE_FREUNDSCHAFTSSPIELE,
                                                                                 false);
        List<IMatchKurzInfo> l = new ArrayList<IMatchKurzInfo>();

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
            List<?> matches = league.getPaarungenBySpieltag(HOVerwaltung.instance().getModel().getBasics().getSpieltag());

            for (Iterator<?> iter = matches.iterator(); iter.hasNext();) {
                IPaarung element = (IPaarung) iter.next();

                if (element.getHeimId() == HOVerwaltung.instance().getModel().getBasics().getTeamId()) {
                    Team t = new Team();

                    t.setName(element.getGastName());
                    t.setTeamId(element.getGastId());

                    return t;
                }

                if (element.getGastId() == HOVerwaltung.instance().getModel().getBasics().getTeamId()) {
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
        return teams.get(teamId + "");
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
    public static Collection<Team> getTeams() {
        if (teams == null) {
            teams = new HashMap<String, Team>();

            List<Team> l = loadDivisionTeams();

            for (Iterator<Team> iter = l.iterator(); iter.hasNext();) {
                Team element = iter.next();

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
        ISpielplan league = DBManager.instance().getSpielplan(HOVerwaltung.instance().getModel().getXtraDaten()
                                                                   .getLeagueLevelUnitID(),
                                                                   HOVerwaltung.instance().getModel().getBasics()
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
        int teamId = HOVerwaltung.instance().getModel().getBasics().getTeamId();
        IMatchKurzInfo[] qualificationMatches = DBManager.instance().getMatchesKurzInfo(teamId,
                                                                                      ISpielePanel.NUR_EIGENE_PFLICHTSPIELE,
                                                                                      false);
        List<IMatchKurzInfo> l = new ArrayList<IMatchKurzInfo>();

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
    private static List<Team> loadDivisionTeams() {
        List<Team> loadedTeams = new ArrayList<Team>();
        ISpielplan league = getDivisionMatches();

        if (league != null) {
            List<?> eintraege = league.getTabelle().getEintraege();

            for (Iterator<?> iter = eintraege.iterator(); iter.hasNext();) {
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
