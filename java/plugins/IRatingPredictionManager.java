package plugins;

import java.util.Date;
import java.util.GregorianCalendar;

public interface IRatingPredictionManager {
    public static final Date LAST_CHANGE = (new GregorianCalendar(2008, 2, 1)).getTime(); //01.03.2008
    public static final Date LAST_CHANGE_FRIENDLY = (new GregorianCalendar(2007, 10, 10)).getTime(); //10.11.2008

}
