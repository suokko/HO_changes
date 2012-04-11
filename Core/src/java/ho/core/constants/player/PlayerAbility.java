package ho.core.constants.player;

import ho.core.model.HOVerwaltung;
import ho.core.model.UserParameter;
import ho.core.util.Helper;

public final class PlayerAbility {
	private static final String[] languageKeys = {
		"nonexisting","katastrophal","erbaermlich","armselig","schwach",
		"durchschnittlich","passabel","gut","sehr_gut","hervorragend",
		"grossartig","brilliant","fantastisch","Weltklasse","uebernatuerlich",
		"gigantisch","ausserirdisch","mythisch","maerchenhaft","galaktisch",
		"goettlich"};
	
	public static final int NON_EXISTENT 	= 0;
	public static final int DISASTROUS 		= 1;
	public static final int WRETCHED 		= 2;
	public static final int POOR 			= 3;
	public static final int WEAK			= 4;
	public static final int INADEQUATE 		= 5;
	public static final int PASSABLE 		= 6;
	public static final int SOLID 			= 7;
	public static final int EXCELLENT 		= 8;
	public static final int FORMIDABLE 		= 9;
	public static final int OUTSTANDING 	= 10;
	public static final int BRILLIANT 		= 11;
	public static final int MAGNIFICENT 	= 12;
	public static final int WORLD_CLASS 	= 13;
	public static final int SUPERNATURAL 	= 14;
	public static final int TITANIC 		= 15;
	public static final int EXTRA_TERRESTRIAL = 16;
	public static final int MYTHICAL 		= 17;
	public static final int MAGICAL 		= 18;
	public static final int UTOPIAN 		= 19;
	public static final int DIVINE 			= 20;

	private PlayerAbility(){};
	
	public static String toString(int ability){
		if( ability >= NON_EXISTENT && ability <= DIVINE)
			return HOVerwaltung.instance().getLanguageString(languageKeys[ability]);
		else
			return HOVerwaltung.instance().getLanguageString(ability>DIVINE?languageKeys[DIVINE]:"Unbestimmt");
	}

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
	
	    bewertung = toString(bewertungwert);
	
	    if (isMatch) {
	        bewertung += PlayerAbility.getName4Sublevel(sublevel);
	    }
	
	    if (zahlen) {
	        if (isMatch) {
	            bewertung += (" ("
	            +  Helper.getNumberFormat(false, UserParameter.instance().anzahlNachkommastellen)
	    		.format(Helper.round(bewertungwert + PlayerAbility.getValue4Sublevel(sublevel), 2))
	             + ")");
	        } else {
	            bewertung += (" ("
	            		+ Helper.getNumberFormat(false, UserParameter.instance().anzahlNachkommastellen)
	             		.format(Helper.round(bewertungwertfloat, UserParameter.instance().anzahlNachkommastellen)) + ")");
	        }
	    }
	
	    return bewertung;
	}

	public static String getNameForSkill(boolean isMatch, float bewertungwert) {
	    return getNameForSkill(bewertungwert, UserParameter.instance().zahlenFuerSkill, isMatch);
	}

	public static String getNameForSkill(float bewertungwert, boolean zahlen) {
	    return getNameForSkill(bewertungwert, zahlen, false);
	}

	public static String getNameForSkill(float bewertung) {
	    return getNameForSkill(bewertung, UserParameter.instance().zahlenFuerSkill);
	}


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

	private static String getName4Sublevel(int sub) {
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
	
	
	
}
