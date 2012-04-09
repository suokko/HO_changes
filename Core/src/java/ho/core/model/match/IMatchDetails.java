// %2077366746:plugins%
/*
 * IMatchDetails.java
 *
 * Created on 18. Oktober 2004, 07:18
 */
package ho.core.model.match;


import java.util.List;
import java.util.Vector;


/**
 * Interface for severy match details.
 *
 * @author thomas.werth
 */
public interface IMatchDetails {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public static final int EINSTELLUNG_UNBEKANNT = -1000;
    /** Play it cool */
    public static final int EINSTELLUNG_PIC = -1;
    /** Normal */
    public static final int EINSTELLUNG_NORMAL = 0;
    /** Match of the Season */
    public static final int EINSTELLUNG_MOTS = 1;

    /** rain */
    public static final int WETTER_REGEN = 0;
    /** overcast */
    public static final int WETTER_BEWOELKT = 1;
    /** cloudy */
    public static final int WETTER_WOLKIG = 2;
    /** sun */
    public static final int WETTER_SONNE = 3;

    /** Normal tactic */
    public static final int TAKTIK_NORMAL = 0;
    /** Pressing tactic */
    public static final int TAKTIK_PRESSING = 1;
    /** Counter attack tactic */
    public static final int TAKTIK_KONTER = 2;
    /** AiM - Attack On Middle */
    public static final int TAKTIK_MIDDLE = 3;
    /** AoW - Attack On Wings */
    public static final int TAKTIK_WINGS = 4;
    /** Play creatively */
    public static final int TAKTIK_CREATIVE = 7;
    /** Long shots */
    public static final int TAKTIK_LONGSHOTS = 8;

    /** away match */
    public static final short LOCATION_AWAY = 0;
    /** home match */
    public static final short LOCATION_HOME = 1;
    /** away derby */
    public static final short LOCATION_AWAYDERBY = 2;


}
