package ho.core.constants.player;

import ho.core.model.HOVerwaltung;

public class PlayerAggressiveness {
	private static final String[] languageKeys = {"tranquil","calm","balanced","temperamental","fiery","Unbestimmt"};

	public static final int TRANQUIL 		= 0;
	public static final int CALM 			= 1;
	public static final int BALANCED 		= 2;
	public static final int TEMPERAMENTAL 	= 3;
	public static final int FIERY 			= 4;
	public static final int UNSTABLE 		= 5;


	private PlayerAggressiveness(){};
	
	public static String toString(int aggressiveness){
		if(aggressiveness >= TRANQUIL && aggressiveness <= UNSTABLE)
			return HOVerwaltung.instance().getLanguageString(languageKeys[aggressiveness]);
		return HOVerwaltung.instance().getLanguageString("Unbestimmt");
	}
}
