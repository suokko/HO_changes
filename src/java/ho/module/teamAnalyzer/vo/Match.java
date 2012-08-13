// %888293640:hoplugins.teamAnalyzer.vo%
package ho.module.teamAnalyzer.vo;

import ho.core.model.match.MatchKurzInfo;
import ho.core.model.match.MatchType;
import ho.core.model.series.Paarung;
import ho.core.util.HTCalendar;
import ho.core.util.HTCalendarFactory;
import ho.module.teamAnalyzer.SystemManager;

import java.util.Date;



/**
 * Match Value object that holds relevant information about the game
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class Match {
    //~ Instance fields ----------------------------------------------------------------------------

    /** The match playing date */
    private Date matchDate;

    /** The away team name */
    private String awayTeam;

    /** The home team name */
    private String homeTeam;

    /** Goals scored by the home team */
    private int awayGoals;

    /** The away team id */
    private int awayId;

    /** Goals scored by the home team */
    private int homeGoals;

    /** The home team id */
    private int homeId;

    /** The match id */
    private int matchId;

    /** The match type */
    private MatchType matchType;

    /** HT Season of the game */
    private int season;

    /** HT Week of the game */
    private int week;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new Match object.
     *
     * @param matchInfo IMatckKurtzInfo from which a match object has to be created
     */
    public Match(MatchKurzInfo matchInfo) {
        homeId = matchInfo.getHeimID();
        awayId = matchInfo.getGastID();
        matchId = matchInfo.getMatchID();
        homeTeam = matchInfo.getHeimName();
        awayTeam = matchInfo.getGastName();
        homeGoals = matchInfo.getHeimTore();
        awayGoals = matchInfo.getGastTore();
        matchType = matchInfo.getMatchTyp();
        matchDate = matchInfo.getMatchDateAsTimestamp();

        Date matchDate = HTCalendar.resetDay(matchInfo.getMatchDateAsTimestamp());        
       
        week = HTCalendarFactory.getHTWeek(matchDate);
        season = HTCalendarFactory.getHTSeason(matchDate);
    }

    /**
     * Creates a new Match object.
     *
     * @param matchInfo IPaarung from which a match object has to be created
     */
    public Match(Paarung matchInfo) {
        homeId = matchInfo.getHeimId();
        awayId = matchInfo.getGastId();
        matchId = matchInfo.getMatchId();
        homeTeam = matchInfo.getHeimName();
        awayTeam = matchInfo.getGastName();
        homeGoals = matchInfo.getToreHeim();
        awayGoals = matchInfo.getToreGast();
        season = matchInfo.getSaison();
        week = matchInfo.getSpieltag();
        matchType = MatchType.LEAGUE;
        matchDate = matchInfo.getDatum();
    }

    /**
     * Creates a new empty Match object.
     */
    public Match() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Document Me!
     *
     * @param i
     */
    public void setAwayGoals(int i) {
        awayGoals = i;
    }

    /**
     * Document Me!
     *
     * @return
     */
    public int getAwayGoals() {
        return awayGoals;
    }

    /**
     * Document Me!
     *
     * @param i
     */
    public void setAwayId(int i) {
        awayId = i;
    }

    /**
     * Document Me!
     *
     * @return
     */
    public int getAwayId() {
        return awayId;
    }

    /**
     * Document Me!
     *
     * @param string
     */
    public void setAwayTeam(String string) {
        awayTeam = string;
    }

    /**
     * Document Me!
     *
     * @return
     */
    public String getAwayTeam() {
        return awayTeam;
    }

    /**
     * Document Me!
     *
     * @param i
     */
    public void setHomeGoals(int i) {
        homeGoals = i;
    }

    /**
     * Document Me!
     *
     * @return
     */
    public int getHomeGoals() {
        return homeGoals;
    }

    /**
     * Document Me!
     *
     * @param i
     */
    public void setHomeId(int i) {
        homeId = i;
    }

    /**
     * Document Me!
     *
     * @return
     */
    public int getHomeId() {
        return homeId;
    }

    /**
     * Document Me!
     *
     * @param string
     */
    public void setHomeTeam(String string) {
        homeTeam = string;
    }

    /**
     * Document Me!
     *
     * @return
     */
    public String getHomeTeam() {
        return homeTeam;
    }

    /**
     * Document Me!
     *
     * @param date
     */
    public void setMatchDate(Date date) {
        matchDate = date;
    }

    /**
     * Document Me!
     *
     * @return
     */
    public Date getMatchDate() {
        return matchDate;
    }

    /**
     * Document Me!
     *
     * @param i
     */
    public void setMatchId(int i) {
        matchId = i;
    }

    /**
     * Document Me!
     *
     * @return
     */
    public int getMatchId() {
        return matchId;
    }

    /**
     * Document Me!
     *
     * @param i
     */
    public void setMatchType(MatchType i) {
        matchType = i;
    }

    /**
     * Document Me!
     *
     * @return
     */
    public MatchType getMatchType() {
        return matchType;
    }

    /**
     * Document Me!
     *
     * @param i
     */
    public void setSeason(int i) {
        season = i;
    }

    /**
     * Document Me!
     *
     * @return
     */
    public int getSeason() {
        return season;
    }

    /**
     * Document Me!
     *
     * @param i
     */
    public void setWeek(int i) {
        week = i;
    }

    /**
     * Document Me!
     *
     * @return
     */
    public int getWeek() {
        return week;
    }

    /**
     * toString methode: creates a String representation of the object
     *
     * @return the String representation
     */
    @Override
	public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("Match[");
        buffer.append("homeId = " + homeId);
        buffer.append(", awayId = " + awayId);
        buffer.append(", matchId = " + matchId);
        buffer.append(", homeTeam = " + homeTeam);
        buffer.append(", awayTeam = " + awayTeam);
        buffer.append(", homeGoals = " + homeGoals);
        buffer.append(", awayGoals = " + awayGoals);
        buffer.append(", season = " + season);
        buffer.append(", week = " + week);
        buffer.append(", matchType = " + matchType);
        buffer.append(", matchDate = " + matchDate);
        buffer.append("]");

        return buffer.toString();
    }
    
    public boolean isHome() {
        return (getHomeId() == SystemManager.getActiveTeamId());
    }
}
