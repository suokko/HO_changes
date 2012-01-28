// %3124307367:hoplugins.teamAnalyzer.vo%
package ho.module.teamAnalyzer.vo;

import java.util.ArrayList;
import java.util.List;


/**
 * Filter Object class that holds the user settings for selecting analyzed matches
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class Filter {
    //~ Instance fields ----------------------------------------------------------------------------

    /** List of manually selected match ids */
    private List<String> matches;

    /** Automatic or manual selection enabled */
    private boolean automatic;

    /** Consider away games */
    private boolean awayGames;

    /** Consider cup games */
    private boolean cup;

    /** Consider lost games */
    private boolean defeat;

    /** Consider draw games */
    private boolean draw;

    /** Consider friendly games */
    private boolean friendly;

    /** Consider home games */
    private boolean homeGames;

    /** Consider league games */
    private boolean league;

    /** Consider qualifier games */
    private boolean qualifier;

    /** Consider won games */
    private boolean win;

    /** Maximum number of games */
    private int number = 10;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new Filter object.
     */
    public Filter() {
        automatic = true;
        setNumber(10);
        setAwayGames(true);
        setHomeGames(true);
        setWin(true);
        setDefeat(true);
        setDraw(true);
        setLeague(true);
        setCup(false);
        setFriendly(false);
        setQualifier(false);
        matches = new ArrayList<String>();
    }

    //~ Methods ------------------------------------------------------------------------------------

    public void setAutomatic(boolean b) {
        automatic = b;
    }

    public boolean isAutomatic() {
        return automatic;
    }

    public void setAwayGames(boolean b) {
        awayGames = b;
    }

    public boolean isAwayGames() {
        return awayGames;
    }

    public void setCup(boolean b) {
        cup = b;
    }

    public boolean isCup() {
        return cup;
    }

    public void setDefeat(boolean b) {
        defeat = b;
    }

    public boolean isDefeat() {
        return defeat;
    }

    public void setDraw(boolean b) {
        draw = b;
    }

    public boolean isDraw() {
        return draw;
    }

    public void setFriendly(boolean b) {
        friendly = b;
    }

    public boolean isFriendly() {
        return friendly;
    }

    public void setHomeGames(boolean b) {
        homeGames = b;
    }

    public boolean isHomeGames() {
        return homeGames;
    }

    public void setLeague(boolean b) {
        league = b;
    }

    public boolean isLeague() {
        return league;
    }

    public void setMatches(List<String> list) {
        matches = list;
    }

    public List<String> getMatches() {
        return matches;
    }

    /** Maximum number of games */
    public void setNumber(int i) {
        number = i;
    }

    /** Maximum number of games */
    public int getNumber() {
        return number;
    }

    public void setQualifier(boolean b) {
        qualifier = b;
    }

    public boolean isQualifier() {
        return qualifier;
    }

    public void setWin(boolean b) {
        win = b;
    }

    public boolean isWin() {
        return win;
    }

    /**
     * toString methode: creates a String representation of the object
     *
     * @return the String representation
     */
    @Override
	public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("Filter[");
        buffer.append("number = " + number);
        buffer.append(", awayGames = " + awayGames);
        buffer.append(", homeGames = " + homeGames);
        buffer.append(", win = " + win);
        buffer.append(", draw = " + draw);
        buffer.append(", defeat = " + defeat);
        buffer.append(", automatic = " + automatic);
        buffer.append(", matches = " + matches);
        buffer.append("]");

        return buffer.toString();
    }
}
