// %3904049365:hoplugins.teamAnalyzer.util%
package hoplugins.teamAnalyzer.util;

import hoplugins.teamAnalyzer.manager.NameManager;

import java.util.StringTokenizer;


/**
 * Team Player's name utility
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class NameUtil {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Returns only the first name initial(s) for a player name, separated by "."
     *
     * @param name The player fullname
     *
     * @return The first name initial(s)
     */
    public static String getInitials(String name) {
        String firstName = getFirstName(name);
        StringTokenizer st = new StringTokenizer(firstName, " ");
        String initials = "";

        while (st.hasMoreTokens()) {
            initials = initials + st.nextToken().charAt(0) + ".";
        }

        return initials.trim();
    }

    /**
     * Returns the player last name
     *
     * @param name The player fullname
     *
     * @return the lastname
     */
    public static String getLastName(String name) {
        String lastName = NameManager.getLastName(name);

        return lastName.trim();
    }

    /**
     * Returns the player description
     *
     * @param name player fullname
     *
     * @return
     */
    public static String getPlayerDesc(String name) {
        return getPlayerName(name, true);
    }

    /**
     * Returns the player first name
     *
     * @param name The player fullname
     *
     * @return the firstname
     */
    private static String getFirstName(String name) {
        String lastName = NameManager.getLastName(name);
        int index = name.indexOf(lastName);

        if (index > 0) {
            return name.substring(0, index);
        }

        return "";
    }

    /**
     * Returns the Player name formatted as specified
     *
     * @param name the player full name
     * @param detail true if you want initials, false if full
     *
     * @return the formatted player name
     */
    private static String getPlayerName(String name, boolean detail) {
        String playerName = "";

        if (name.length() > 22) {
            StringTokenizer st = new StringTokenizer(name, "/");

            while (st.hasMoreTokens()) {
                String nameStr = st.nextToken();

                if (detail) {
                    playerName = playerName + getInitials(nameStr) + " ";
                }

                playerName = playerName + getLastName(nameStr) + "/";
            }

            playerName = playerName.substring(0, playerName.length() - 1);
        } else {
            playerName = name;
        }

        if ((playerName.length() > 22) && detail) {
            playerName = getPlayerName(playerName, false);
        }

        return playerName;
    }
}
