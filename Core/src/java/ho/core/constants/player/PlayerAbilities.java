package ho.core.constants.player;

import ho.core.model.HOVerwaltung;

public final class PlayerAbilities {
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

	private PlayerAbilities(){};
	
	public static String toString(int ability){
		if( ability >= NON_EXISTENT && ability <= DIVINE)
			return HOVerwaltung.instance().getLanguageString(languageKeys[ability]);
		else
			return HOVerwaltung.instance().getLanguageString(ability>DIVINE?languageKeys[DIVINE]:"Unbestimmt");
	}
}
