// %1705119372:hoplugins.teamAnalyzer.manager%
package ho.module.teamAnalyzer.manager;

import ho.core.db.DBManager;
import ho.core.model.HOVerwaltung;
import ho.core.model.match.MatchKurzInfo;
import ho.core.model.match.MatchType;
import ho.core.model.series.LigaTabellenEintrag;
import ho.core.model.series.Paarung;
import ho.module.matches.SpielePanel;
import ho.module.series.Spielplan;
import ho.module.teamAnalyzer.vo.Team;

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

    private static Map<Integer, Team> teams = null;
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
        MatchKurzInfo[] cupMatches =DBManager.instance().getMatchesKurzInfo(teamId,
                                                                            SpielePanel.NUR_EIGENE_POKALSPIELE,
                                                                            false);
        MatchKurzInfo[] friendlyMatches = DBManager.instance().getMatchesKurzInfo(teamId,
                                                                                 SpielePanel.NUR_EIGENE_FREUNDSCHAFTSSPIELE,
                                                                                 false);
        List<MatchKurzInfo> l = new ArrayList<MatchKurzInfo>();

        l.addAll(Arrays.asList(friendlyMatches));
        l.addAll(Arrays.asList(cupMatches));

        Object[] matches = l.toArray();

        for (int i = 0; i < matches.length; i++) {
            MatchKurzInfo match = (MatchKurzInfo) matches[i];

            if (match.getMatchStatus() != MatchKurzInfo.FINISHED) {
                if (match.getHeimID() == teamId) {
                    team.setName(match.getGastName());
                    team.setTeamId(match.getGastID());
                } else {
                    team.setName(match.getHeimName());
                    team.setTeamId(match.getHeimID());
                }
                team.setTime(match.getMatchDateAsTimestamp());

                return team;
            }
        }

        return team;
    }

    
    public static Team getNextTournamentOpponent() {
    	Team team = new Team();
    	int teamId = HOVerwaltung.instance().getModel().getBasics().getTeamId();
    	MatchKurzInfo[] tournamentMatches =DBManager.instance().getMatchesKurzInfo(teamId,
    			SpielePanel.NUR_EIGENE_TOURNAMENTSPIELE,
    			true);
    	List<MatchKurzInfo> l = new ArrayList<MatchKurzInfo>();

    	l.addAll(Arrays.asList(tournamentMatches));

    	Object[] matches = l.toArray();

    	for (int i = 0; i < matches.length; i++) {
    		MatchKurzInfo match = (MatchKurzInfo) matches[i];

    		if (match.getMatchStatus() != MatchKurzInfo.FINISHED) {
    			if (match.getHeimID() == teamId) {
    				team.setName(match.getGastName());
    				team.setTeamId(match.getGastID());
    			} else {
    				team.setName(match.getHeimName());
    				team.setTeamId(match.getHeimID());
    			}
    			team.setTime(match.getMatchDateAsTimestamp());
    			team.setTournament(true);
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
        Spielplan league = getDivisionMatches();

        if (league != null) {
            List<?> matches = league.getPaarungenBySpieltag(HOVerwaltung.instance().getModel().getBasics().getSpieltag());

            for (Iterator<?> iter = matches.iterator(); iter.hasNext();) {
                Paarung element = (Paarung) iter.next();

                if (element.getHeimId() == HOVerwaltung.instance().getModel().getBasics().getTeamId()) {
                    Team t = new Team();

                    t.setName(element.getGastName());
                    t.setTeamId(element.getGastId());
                    t.setTime(element.getDatum());

                    return t;
                }

                if (element.getGastId() == HOVerwaltung.instance().getModel().getBasics().getTeamId()) {
                    Team t = new Team();

                    t.setName(element.getHeimName());
                    t.setTeamId(element.getHeimId());
                    t.setTime(element.getDatum());

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
        return teams.get(teamId);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param teamId TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static boolean isTeamInList(int teamId) {
        if (teams.get(teamId) != null) {
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
            teams = new HashMap<Integer, Team>();

            List<Team> l = loadDivisionTeams();

            for (Iterator<Team> iter = l.iterator(); iter.hasNext();) {
                Team element = iter.next();

                teams.put(element.getTeamId(), element);
            }

            Team qualTeam = getNextQualificationOpponent();

            if (qualTeam.getTeamId() != 0) {
                teams.put(qualTeam.getTeamId(), qualTeam);
            }

            Team cupTeam = getNextCupOpponent();

            if (cupTeam.getTeamId() != 0) {
                teams.put(cupTeam.getTeamId(), cupTeam);
            }
            
            List<Team> teamlist = loadTournamentteams();
            for (Team tourneyteam: teamlist) {
            	teams.put(tourneyteam.getTeamId(), tourneyteam);
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
            teams.put(team.getTeamId(), team);
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
    private static Spielplan getDivisionMatches() {
        Spielplan league = DBManager.instance().getSpielplan(HOVerwaltung.instance().getModel().getXtraDaten()
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
        MatchKurzInfo[] qualificationMatches = DBManager.instance().getMatchesKurzInfo(teamId,
                                                                                      SpielePanel.NUR_EIGENE_PFLICHTSPIELE,
                                                                                      false);
        List<MatchKurzInfo> l = new ArrayList<MatchKurzInfo>();

        l.addAll(Arrays.asList(qualificationMatches));

        Object[] matches = l.toArray();

        for (int i = 0; i < matches.length; i++) {
            MatchKurzInfo match = (MatchKurzInfo) matches[i];

            if ((match.getMatchStatus() != MatchKurzInfo.FINISHED)
                && (match.getMatchTyp() == MatchType.QUALIFICATION)) {
                if (match.getHeimID() == teamId) {
                    team.setName(match.getGastName());
                    team.setTeamId(match.getGastID());
                } else {
                    team.setName(match.getHeimName());
                    team.setTeamId(match.getHeimID());
                }
                team.setTime(match.getMatchDateAsTimestamp());

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
        Spielplan league = getDivisionMatches();

        if (league != null) {
            List<?> eintraege = league.getTabelle().getEintraege();

            for (Iterator<?> iter = eintraege.iterator(); iter.hasNext();) {
                LigaTabellenEintrag element = (LigaTabellenEintrag) iter.next();
                Team t = new Team();

                t.setName(element.getTeamName());
                t.setTeamId(element.getTeamId());
                loadedTeams.add(t);
            }
        }

        return loadedTeams;
    }
    
    private static List<Team> loadTournamentteams() {
    	List<Team> loadedTeams = new ArrayList<Team>();
    	int teamId = HOVerwaltung.instance().getModel().getBasics().getTeamId();
    	MatchKurzInfo[] infoarray =  DBManager.instance().getMatchesKurzInfo(teamId, MatchKurzInfo.UPCOMING);
    		
    	for (int i = 0 ; i < infoarray.length ; i++) {
    		if ( (infoarray[i].getMatchTyp() == MatchType.TOURNAMENTGROUP)
    				|| (infoarray[i].getMatchTyp() == MatchType.TOURNAMENTPLAYOFF)) {
    			MatchKurzInfo info = infoarray[i];
    			Team t = new Team();
    			String teamName;
    			if (info.getGastID() == teamId) {
    				t.setName(info.getHeimName());
    				t.setTeamId(info.getHeimID());
    				t.setTournament(true);
    			} else if (info.getHeimID() == teamId) {
    				t.setName(info.getGastName());
    				t.setTeamId(info.getGastID());
    				t.setTournament(true);
    			} else {
    				// Huh?
    				continue;
    			}
    			loadedTeams.add(t);
    		}
    	}
    	return loadedTeams;
    }
}
