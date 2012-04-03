// %752552287:de.hattrickorganizer.tools%
package ho.core.util;

import ho.core.constants.player.PlayerAbilities;
import ho.core.model.HOVerwaltung;
import plugins.ISpieler;


/**
 * PlayerHelper contains some static help methods for the playerObjects(Spieler)
 *
 * @author Thorsten Dietz
 */
public class PlayerHelper {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Parameter Documentation
     *
     * @param sub TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */

    //public static int currentTacticType = IMatchDetails.TAKTIK_NORMAL;

    /**
     * TODO Missing Method Documentation
     *
     * @param sub TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static String getName4Sublevel(int sub) {
        if (sub == 1) {
            return " ("
                   + HOVerwaltung.instance().getLanguageString("verylow")
                   + ")";
        } else if (sub == 2) {
            return " ("
                   + HOVerwaltung.instance().getLanguageString("low")
                   + ")";
        } else if (sub == 3) {
            return " ("
                   + HOVerwaltung.instance().getLanguageString("high")
                   + ")";
        } else if (sub == 0) {
            return " ("
                   + HOVerwaltung.instance().getLanguageString("veryhigh")
                   + ")";
        } else {
            return "";
        }
    }

    /**
     * Gibt den Namen zu einer Bewertungzurück
     *
     * @param bewertung TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static String getNameForSkill(float bewertung) {
        return getNameForSkill(bewertung, ho.core.model.UserParameter.instance().zahlenFuerSkill);
    }

    /**
     * Gibt den Namen zu einer Bewertungzurück
     *
     * @param bewertungwert TODO Missing Constructuor Parameter Documentation
     * @param zahlen TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static String getNameForSkill(float bewertungwert, boolean zahlen) {
        return getNameForSkill(bewertungwert, zahlen, false);
    }

    /**
     * Gibt den Namen zu einer Bewertungzurück
     *
     * @param isMatch TODO Missing Constructuor Parameter Documentation
     * @param bewertungwert TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static String getNameForSkill(boolean isMatch, float bewertungwert) {
        return getNameForSkill(bewertungwert, ho.core.model.UserParameter.instance().zahlenFuerSkill, isMatch);
    }

    /**
     * Gibt den Namen zu einer Bewertungzurück
     *
     * @param bewertungwertfloat TODO Missing Constructuor Parameter Documentation
     * @param zahlen TODO Missing Constructuor Parameter Documentation
     * @param isMatch TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static String getNameForSkill(float bewertungwertfloat, boolean zahlen, boolean isMatch) {
        String bewertung = null;
        int bewertungwert = (int) bewertungwertfloat;

        //Für match
        int sublevel = 0;

        //Umrechnung für ein Spiel
        if (isMatch) {
            sublevel = (bewertungwert) % 4;

            //(int)Math.floor ( ( (float)bewertungwert)/4f ) +1; 
            bewertungwert = ((bewertungwert - 1) / 4) + 1;
        }

        bewertung = PlayerAbilities.toString(bewertungwert);

        if (isMatch) {
            bewertung += getName4Sublevel(sublevel);
        }

        if (zahlen) {
            if (isMatch) {
                bewertung += (" ("
                +  Helper.getNumberFormat(false, ho.core.model.UserParameter.instance().anzahlNachkommastellen)
        		.format(Helper.round(bewertungwert + getValue4Sublevel(sublevel), 2))
                 + ")");
            } else {
                bewertung += (" ("
                		+ Helper.getNumberFormat(false, ho.core.model.UserParameter.instance().anzahlNachkommastellen)
                 		.format(Helper.round(bewertungwertfloat, ho.core.model.UserParameter.instance().anzahlNachkommastellen)) + ")");
            }
        }

        return bewertung;
    }



    /**
     * TODO Missing Method Documentation
     *
     * @param sub TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static double getValue4Sublevel(int sub) {
        if (sub == 1) {
            return 0.125;
        } else if (sub == 2) {
            return 0.375;
        } else if (sub == 3) {
            return 0.625;
        } else if (sub == 0) {
            return 0.875;
        } else {
            return 0;
        }
    }

    

    ////////////////////////////////////////////////////////////////////////////////
    //  Calculation
    ////////////////////////////////////////////////////////////////////////////////         

    /**
     * check Skillup
     *
     * @param skill TODO Missing Constructuor Parameter Documentation
     * @param oldPlayer TODO Missing Constructuor Parameter Documentation
     * @param currentPlayer TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static boolean check4SkillUp(int skill, ISpieler oldPlayer, ISpieler currentPlayer) {
        if ((oldPlayer != null) && (oldPlayer.getSpielerID() > 0)) {
        	if (oldPlayer.getValue4Skill4(skill) < currentPlayer.getValue4Skill4(skill)) {
//        		HOLogger.instance().debug(PlayerHelper.class, "Skillup for "+currentPlayer.getName()+" ("+currentPlayer.getSpielerID()+"), skill="+skill+", oldVal="+oldPlayer.getValue4Skill4(skill)+" < cur="+currentPlayer.getValue4Skill4(skill));
        		return true;
        	}
        }
        return false;
    }
}
