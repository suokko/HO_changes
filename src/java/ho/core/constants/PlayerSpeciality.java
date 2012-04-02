package ho.core.constants;

import ho.core.datatype.CBItem;
import ho.core.model.HOVerwaltung;

public final class PlayerSpeciality {

    public static final int NO_SPECIALITY 	= 0;
    public static final int TECHNICAL 		= 1;
    public static final int QUICK 			= 2;
    public static final int POWERFUL 		= 3;
    public static final int UNPREDICTABLE 	= 4;
    public static final int HEAD 			= 5;
    public static final int REGAINER 		= 6;

    //Weather
    public static final int SUN 				= 1;
    public static final int PARTIALLY_CLOUDY 	= 2;
    public static final int OVERCAST 			= 3;	
    public static final int RAIN 				= 4;
    
    public static final CBItem[] ITEMS = {
    	new CBItem("", NO_SPECIALITY),
    	new CBItem(HOVerwaltung.instance().getLanguageString("sp_Technical"), TECHNICAL),
    	new CBItem(HOVerwaltung.instance().getLanguageString("sp_Quick"),QUICK),
    	new CBItem(HOVerwaltung.instance().getLanguageString("sp_Powerful"), POWERFUL),
    	new CBItem(HOVerwaltung.instance().getLanguageString("sp_Unpredictable"), UNPREDICTABLE),
    	new CBItem(HOVerwaltung.instance().getLanguageString("sp_Head"), HEAD),
    	new CBItem(HOVerwaltung.instance().getLanguageString("sp_Regainer"), REGAINER)
    };
    
	private PlayerSpeciality(){};
	
	public static String toString(int speciality){
		if(speciality >= NO_SPECIALITY && speciality <= REGAINER)
			return ITEMS[speciality].getText();
		else
			return HOVerwaltung.instance().getLanguageString("Unbestimmt");
	}
}
